package dev.binclub.binfix.classpath

import dev.binclub.binfix.classpath.classtree.ClassHierarchyEntry
import dev.binclub.binfix.classpath.classtree.ClassNodeTreeEntry
import dev.binclub.binfix.classpath.classtree.ClassPathTreeEntry
import dev.binclub.binfix.classpath.classtree.ClassTreeEntry
import dev.binclub.binfix.utils.block
import org.objectweb.asm.tree.ClassNode
import java.util.LinkedList

/**
 * @author cookiedragon234 11/Apr/2020
 */
object ClassPath {
	val classes: MutableList<ClassNode> = LinkedList()
	val passThrough: MutableMap<String, ByteArray> = linkedMapOf()
	val classPath: MutableMap<String, ClassNode> = hashMapOf()
	private val hierarchy = hashMapOf<String, ClassHierarchyEntry>()
	private val treeEntries = mutableMapOf<String, ClassTreeEntry>()
	
	
	fun constructHierarchy() {
		treeEntries.clear()
		hierarchy.clear()
		for (classNode in classPath.values) {
			val entry = ClassNodeTreeEntry(classNode)
			treeEntries[classNode.name] = entry
			constructTreeSuperClasses(entry)
			constructTreeHiearchy(classNode.name, entry)
		}
	}
	
	
	fun getHierarchy(className: String): ClassHierarchyEntry? {
		hierarchy[className]?.let {
			return it
		}
		
		getTreeEntry(className)?.let { tree ->
			constructTreeSuperClasses(tree)
			return constructTreeHiearchy(className, tree)
		}
		
		return null
	}
	
	private fun constructTreeSuperClasses(treeEntry: ClassTreeEntry) {
		for (aSuper in treeEntry.superClasses) {
			if (!classPath.containsKey(aSuper)) {
				getTreeEntry(aSuper)
			}
		}
	}
	
	private fun constructTreeHiearchy(className: String, entry: ClassTreeEntry): ClassHierarchyEntry {
		val tree = ClassHierarchyEntry(entry)
		hierarchy[className] = tree
		for (aSuper in entry.superClasses) {
			getTreeEntry(aSuper)?.let {
				tree.parents.add(it)
			}
		}
		for (entry2 in treeEntries.values) {
			if (entry2.superClasses.contains(className)) {
				tree.children.add(entry2)
			}
		}
		return tree
	}
	
	
	
	fun getTreeEntry(className: String): ClassTreeEntry? {
		treeEntries[className]?.let {
			return it
		}
		return block {
			classPath[className]?.let {
				return@block ClassNodeTreeEntry(it)
			}
			try {
				return@block ClassPathTreeEntry(Class.forName(className.replace('/', '.')))
			} catch (ignored: Throwable){}
			null
		}?.also {
			treeEntries[className] = it
		}
	}
}
