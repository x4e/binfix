package dev.binclub.binfix.classpath

import dev.binclub.binfix.classpath.ClassPath.classPath
import dev.binclub.binfix.classpath.ClassPath.classes
import dev.binclub.binfix.classpath.ClassPath.passThrough
import dev.binclub.binfix.utils.CustomClassWriter
import dev.binclub.binfix.utils.PatchedZipOutputStream
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.ClassNode
import java.io.File
import java.nio.file.Files
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

/**
 * @author cookiedragon234 10/Apr/2020
 */
object ClassPathIO {
	fun loadInput(file: File) = loadFile(file, true)
	fun loadClassPath(file: File) = loadFile(file, false)
	
	fun saveInput(file: File) {
		PatchedZipOutputStream(file.outputStream()).use {
			passThrough.forEach { (name, bytes) ->
				it.putNextEntry(ZipEntry(name))
				it.write(bytes)
			}
			
			classes.forEach { classNode ->
				it.putNextEntry(ZipEntry("${classNode.name}.class"))
				
				val bytes = try {
					CustomClassWriter(ClassWriter.COMPUTE_FRAMES).also {
						classNode.accept(it)
					}
				} catch (t: Throwable) {
					t.printStackTrace()
					CustomClassWriter(0).also {
						classNode.accept(it)
					}
				}.toByteArray()
				
				it.write(bytes)
				it.closeEntry()
			}
		}
	}
	
	private fun loadFile(file: File, isInput: Boolean) {
		try {
			if (file.extension == "jar" || file.extension == "zip") {
				ZipFile(file).use {
					for (entry in it.entries()) {
						val name = entry.name.removeSuffix("/")
						val bytes = it.getInputStream(entry).readBytes()
						if (name.endsWith(".class") && entry.size > 1) {
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
						} else {
							passThrough[entry.name] = bytes
						}
					}
				}
			} else if(file.isDirectory && !isInput) {
				Files.walk(file.toPath())
						.filter(Files::isRegularFile)
						.filter { it.endsWith(".jar") || it.endsWith(".zip") }
						.forEach {
					loadFile(it.toFile(),false)
				}
			} else {
				error("Unsupported file extension ${file.extension}")
			}
		} catch (t: Throwable) {
			t.printStackTrace()
		}
	}
}
