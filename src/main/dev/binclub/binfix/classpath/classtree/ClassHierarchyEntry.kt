package dev.binclub.binfix.classpath.classtree

/**
 * @author cookiedragon234 16/Apr/2020
 */
class ClassHierarchyEntry(val thisClass: ClassTreeEntry) {
	val parents = mutableSetOf<ClassTreeEntry>()
	val children = mutableSetOf<ClassTreeEntry>()
}
