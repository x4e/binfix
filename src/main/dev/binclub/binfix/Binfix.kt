package dev.binclub.binfix

import dev.binclub.binfix.classpath.ClassPath
import dev.binclub.binfix.classpath.ClassPathIO
import dev.binclub.binfix.configuration.ConfigurationContext
import dev.binclub.binfix.processors.name.ClassRemapper
import java.io.File
import javax.script.ScriptEngineManager
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.host.BasicScriptingHost
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.BasicJvmScriptEvaluator
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

/**
 * @author cookiedragon234 10/Apr/2020
 */
object Binfix {
	@JvmStatic
	fun main(args: Array<String>) {
		if (args.isEmpty()) {
			error("Please provide a config file")
		}
		
		val engine = ScriptEngineManager().getEngineByExtension("kts")
		engine.eval(File(args[0]).reader())
		println("Finished")
	}
	
	operator fun invoke(configurationContext: ConfigurationContext) {
		ClassPathIO.loadInput(File(configurationContext.input))
		configurationContext.libraries.forEach {
			ClassPathIO.loadClassPath(File(it))
		}
		
		for (processor in configurationContext.processors) {
			processor.process(ClassPath.classes)
		}
		
		ClassPathIO.saveInput(File(configurationContext.output))
	}
}
