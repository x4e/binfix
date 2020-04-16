package dev.binclub.binfix.utils

import org.objectweb.asm.Type
import kotlin.reflect.KClass

/**
 * @author cookiedragon234 16/Apr/2020
 */

inline fun <T> block(block: () -> T): T = block()

val <T: Any> KClass<T>.internalName: String
	get() = Type.getInternalName(this.java)

val <T : Any> KClass<T>.asPrimitive: Class<T>
	get() = this.javaPrimitiveType ?: this.java
