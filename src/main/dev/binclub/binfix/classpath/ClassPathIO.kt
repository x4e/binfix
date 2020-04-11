package dev.binclub.binfix.classpath

import dev.binclub.binfix.utils.PatchedZipOutputStream
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.ClassNode
import java.io.File
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

/**
 * @author cookiedragon234 10/Apr/2020
 */
object ClassPathIO {
	val classes: MutableList<ClassNode> = arrayListOf()
	val passThrough: MutableMap<String, ByteArray> = linkedMapOf()
	val classPath: MutableMap<String, ClassNode> = hashMapOf()
	
	fun loadInput(file: File) = loadFile(file, true)
	
	fun loadClassPath(file: File) = loadFile(file, false)
	
	fun saveInput(file: File) {
		PatchedZipOutputStream(file.outputStream()).use {
			classes.forEach { classNode ->
				it.putNextEntry(ZipEntry("${classNode.name}.class"))
				
				val bytes = try {
					ClassWriter(ClassWriter.COMPUTE_FRAMES).also {
						classNode.accept(it)
					}
				} catch (t: Throwable) {
					t.printStackTrace()
					ClassWriter(0).also {
						classNode.accept(it)
					}
				}.toByteArray()
				
				it.write(bytes)
				it.closeEntry()
			}
			
			passThrough.forEach { (name, bytes) ->
				it.putNextEntry(ZipEntry(name))
				it.write(bytes)
			}
		}
	}
	
	private fun loadFile(file: File, isInput: Boolean) {
		try {
			if (file.extension == "jar" || file.extension == "zip") {
				ZipFile(file).use {
					for (entry in it.entries()) {
						val name = entry.name.removeSuffix("/")
						if (name.endsWith(".class") && entry.size > 1) {
							val bytes = it.getInputStream(entry).readBytes()
							
							try {
								val classNode = ClassNode()
								ClassReader(bytes).accept(classNode, ClassReader.EXPAND_FRAMES)
								if (isInput) {
									classes.add(classNode)
								}
								classPath[name.removeSuffix(".class")] = classNode
							} catch (t: Throwable) {
								t.printStackTrace()
								passThrough[entry.name] = bytes
							}
						}
					}
				}
			} else {
				error("Unsupported file extension ${file.extension}")
			}
		} catch (t: Throwable) {
			t.printStackTrace()
		}
	}
}
