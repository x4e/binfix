package dev.binclub.binfix.processors.name

import dev.binclub.binfix.configuration.processor.ProcessorConfiguration
import dev.binclub.binfix.utils.CustomRemapper
import org.objectweb.asm.tree.ClassNode

/**
 * @author cookiedragon234 10/Apr/2020
 */
object ClassRemapper: AbstractRemapperProcessor<ProcessorConfiguration>() {
	override fun remap(remapper: CustomRemapper, classes: Collection<ClassNode>) {
		var i = 0
		for (classNode in classes) {
			remapper.map(classNode.name, "Class_$i")
			println("${classNode.name} -> Class_$i")
			i += 1
		}
	}
}
