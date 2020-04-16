package dev.binclub.binfix.processors.name

import dev.binclub.binfix.classpath.ClassPath
import dev.binclub.binfix.configuration.processor.ProcessorConfiguration
import dev.binclub.binfix.processors.ClassProcessor
import dev.binclub.binfix.utils.AnnotationFieldRemapper
import dev.binclub.binfix.utils.CustomRemapper
import org.objectweb.asm.commons.ClassRemapper
import org.objectweb.asm.tree.ClassNode

/**
 * @author cookiedragon234 16/Apr/2020
 */
abstract class AbstractRemapperProcessor<T: ProcessorConfiguration>: ClassProcessor<T> {
	override fun process(classes: Collection<ClassNode>) {
		val remapper = CustomRemapper()
		remap(remapper, classes)
		val replacements = mutableMapOf<ClassNode, ClassNode>()
		for (classNode in classes) {
			val newNode = ClassNode()
			val classMapper = ClassRemapper(newNode, remapper)
			classNode.accept(classMapper)
			replacements[classNode] = newNode
			AnnotationFieldRemapper.remap(newNode, remapper)
		}
		
		for ((old, new) in replacements) {
			ClassPath.classes.remove(old)
			ClassPath.classes.add(new)
			ClassPath.classPath.remove(old.name)
			ClassPath.classPath[new.name] = new
		}
	}
	abstract fun remap(remapper: CustomRemapper, classes: Collection<ClassNode>)
}

open class RemapperConfiguration {
}
