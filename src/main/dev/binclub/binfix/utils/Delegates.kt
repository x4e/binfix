package dev.binclub.binfix.utils

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author cookiedragon234 10/Apr/2020
 */
fun <T: Any> nonNullByDefault(default: T): ReadWriteProperty<Any?, T> = NonNullByDefault(default)

private class NonNullByDefault<T : Any>(val default: T) : ReadWriteProperty<Any?, T> {
	private var value: T? = null
	
	override fun getValue(thisRef: Any?, property: KProperty<*>): T {
		return value ?: default
	}
	
	override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
		this.value = value
	}
}
