package dev.binclub.binfix.classpath

import org.objectweb.asm.tree.ClassNode

/**
 * @author cookiedragon234 11/Apr/2020
 */
object ClassPath {
	val classes: MutableList<ClassNode> = arrayListOf()
	val passThrough: MutableMap<String, ByteArray> = linkedMapOf()
	val classPath: MutableMap<String, ClassNode> = hashMapOf()
}
