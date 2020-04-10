package dev.binclub.binfix

import dev.binclub.binfix.classpath.ClassPathIO
import dev.binclub.binfix.configuration.ConfigurationContext
import java.io.File

/**
 * @author cookiedragon234 10/Apr/2020
 */
object Binfix {
	fun main(args: Array<String>) {
		println("hi")
	}
	
	operator fun invoke(configurationContext: ConfigurationContext) {
		ClassPathIO.loadInput(File(configurationContext.input))
		configurationContext.libraries.forEach {
			ClassPathIO.loadClassPath(File(it))
		}
	}
}
