package dev.binclub.binfix.classpath.classtree

import org.objectweb.asm.Type

/**
 * A tree entry that represents a class present in the JVM class path
 *
 * @author cookiedragon234 15/Apr/2020
 */
class ClassPathTreeEntry(val clazz: Class<*>): ClassTreeEntry() {
	override val name: String = Type.getInternalName(clazz)
	override val access: Int = clazz.modifiers
	override val directSuper: String = clazz.superclass?.let { Type.getInternalName(it) } ?: "java/lang/Object"
	override val superClasses: Set<String> by lazy {
		mutableSetOf<String>().also {
			for (aInterface in clazz.interfaces) {
				it.add(Type.getInternalName(aInterface))
			}
			it.add(directSuper)
		}
	}
	override val methods: Set<MethodInfo> by lazy {
		mutableSetOf<MethodInfo>().also {
			for (declaredMethod in clazz.declaredMethods) {
				it.add(MethodInfo(name, declaredMethod.name, Type.getMethodDescriptor(declaredMethod)))
			}
		}
	}
	override val fields: Set<FieldInfo> by lazy {
		mutableSetOf<FieldInfo>().also {
			for (field in clazz.declaredFields) {
				it.add(FieldInfo(name, field.name, Type.getDescriptor(field.type)))
			}
		}
	}
	
}
