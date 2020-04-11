package dev.binclub.binfix.utils

import java.io.OutputStream
import java.util.HashSet
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * A patched zip outputstream that allows multiple entries with the same name
 *
 * @author cookiedragon234 11/Apr/2020
 */
class PatchedZipOutputStream(outputStream: OutputStream): ZipOutputStream(outputStream) {
	@Suppress("UNCHECKED_CAST")
	val names: HashSet<String> = ZipOutputStream::class.java.getDeclaredField("names").let {
		it.isAccessible = true
		it.get(this) as HashSet<String>
	}
	
	override fun putNextEntry(e: ZipEntry?) {
		names.clear()
		super.putNextEntry(e)
	}
}
