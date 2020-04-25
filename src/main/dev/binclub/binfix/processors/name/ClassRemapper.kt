package dev.binclub.binfix.processors.name

import dev.binclub.binfix.configuration.processor.ProcessorConfiguration
import dev.binclub.binfix.utils.CustomRemapper
import org.objectweb.asm.tree.ClassNode

/**
 * @author cookiedragon234 10/Apr/2020
 */
object ClassRemapper: AbstractRemapperProcessor<ClassRemapperConfiguration>(ClassRemapperConfiguration()) {
	override fun remap(remapper: CustomRemapper, classes: Collection<ClassNode>) {
		var i = 0
		for (classNode in classes) {
			remapper.map(classNode.name, this.configuration.nameProvider(classNode, i))
			i += 1
		}
	}
}

class ClassRemapperConfiguration: RemapperConfiguration() {
	var nameProvider: (ClassNode, Int) -> String = { cn, i ->
		if (flattenPackages) {
			"Class_$i"
		} else {
			"${cn.name.substringBeforeLast('/')}/Class_$i"
		}
	}
	var shouldRename: (ClassNode) -> Boolean = {true}
	var flattenPackages: Boolean = true
}
