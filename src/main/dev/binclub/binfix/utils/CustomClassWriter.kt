package dev.binclub.binfix.utils

import dev.binclub.binfix.classpath.ClassPath
import org.objectweb.asm.ClassWriter
import java.lang.reflect.Modifier
import java.util.Stack

/**
 * @author cookiedragon234 16/Apr/2020
 */
class CustomClassWriter(flags: Int): ClassWriter(flags) {
	private val OBJECT = "java/lang/Object"
	private val warnings = hashSetOf<String>()
	
	private fun <T> warn(type: String, out: T): T {
		if (warnings.add(type)) {
			System.err.println("WARNING: $type was not found in the classpath, may cause sideaffects")
		}
		return out
	}
	
	override fun getCommonSuperClass(type1: String, type2: String): String {
		if (type1 == OBJECT || type2 == OBJECT) {
			return OBJECT
		}
		
		if (type1 == type2) {
			return type1
		}
		
		if (isAssignableFrom(type1, type2)) {
			return type1
		}
		if (isAssignableFrom(type2, type1)) {
			return type2
		}
		
		var first = ClassPath.getTreeEntry(type1) ?: return warn(type1, OBJECT)
		val second = ClassPath.getTreeEntry(type2) ?: return warn(type1, OBJECT)
		if (Modifier.isInterface(first.access) || Modifier.isInterface(second.access)) {
			return OBJECT
		}
		
		do {
			first = ClassPath.getTreeEntry(first.directSuper) ?: return warn(first.directSuper, OBJECT)
		} while (!isAssignableFrom(first.name, second.name))
		return first.name
	}
	
	private fun isAssignableFrom(type1: String, type2: String): Boolean {
		if (type1 == OBJECT)
			return true
		if (type1 == type2)
			return true
		
		val firstTree = ClassPath.getHierarchy(type1) ?: return warn(type1, false)
		return firstTree.children.any {
			it.name == type2
		}.also {
			if (it) {
				println("$type1 is assignable from $type2")
			}
		}
	}
}
