package dev.binclub.binfix.classpath.classtree

/**
 * @author cookiedragon234 15/Apr/2020
 */
abstract class ClassTreeEntry {
	abstract val name: String
	abstract val access: Int
	abstract val directSuper: String
	abstract val superClasses: Set<String>
	abstract val methods: Set<MethodInfo>
	abstract val fields: Set<FieldInfo>
}

data class FieldInfo(val owner: String, val name: String, val description: String)
data class MethodInfo(val owner: String, val name: String, val desc: String)
