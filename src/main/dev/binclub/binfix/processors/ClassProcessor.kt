package dev.binclub.binfix.processors

import dev.binclub.binfix.configuration.processor.ProcessorConfiguration
import org.objectweb.asm.tree.ClassNode
import kotlin.properties.Delegates

/**
 * @author cookiedragon234 10/Apr/2020
 */
abstract class ClassProcessor<T: ProcessorConfiguration>(val configuration: T) {
	abstract fun process(classes: Collection<ClassNode>)
}
