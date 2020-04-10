package dev.binclub.binfix.processors

import org.objectweb.asm.tree.ClassNode

/**
 * @author cookiedragon234 10/Apr/2020
 */
interface IClassProcessor {
	fun process(classes: List<ClassNode>)
}
