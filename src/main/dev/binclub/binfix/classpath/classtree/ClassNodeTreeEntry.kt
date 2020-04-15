package dev.binclub.binfix.classpath.classtree

import org.objectweb.asm.tree.ClassNode

/**
 * A class tree entry representing
 *
 * @author cookiedragon234 15/Apr/2020
 */
class ClassNodeTreeEntry(val classNode: ClassNode): ClassTreeEntry() {
	override val name: String = classNode.name
	override val access: Int = classNode.access
	override val directSuper: String = classNode.superName ?: "java/lang/Object"
	override val superClasses: Set<String> by lazy {
		mutableSetOf<String>().also {
			if (classNode.superName != null) it.add(classNode.superName)
			it.addAll(classNode.interfaces)
		}
	}
	override val methods: Set<MethodInfo> by lazy {
		mutableSetOf<MethodInfo>().also {
			for (method in classNode.methods) {
				it.add(MethodInfo(classNode.name, method.name, method.desc))
			}
		}
	}
	override val fields: Set<FieldInfo> by lazy {
		mutableSetOf<FieldInfo>().also {
			for (field in classNode.fields) {
				it.add(FieldInfo(classNode.name, field.name, field.desc))
			}
		}
	}
}
