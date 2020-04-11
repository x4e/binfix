package dev.binclub.binfix.configuration

import dev.binclub.binfix.Binfix
import dev.binclub.binfix.processors.IClassProcessor
import dev.binclub.binfix.utils.nonNullByDefault
import kotlin.properties.Delegates
import kotlin.reflect.KClass

/**
 * @author cookiedragon234 10/Apr/2020
 */
class ConfigurationContext {
	var input by Delegates.notNull<String>()
	var output by nonNullByDefault {
		"${input.substringBeforeLast('.')}-deobf.${input.substringAfterLast('.')}"
	}
	
	internal val libraries: MutableList<String> = arrayListOf()
	internal val processors: MutableList<IClassProcessor> = arrayListOf()
}

class LibraryContext(val configurationContext: ConfigurationContext) {
	operator fun String.unaryPlus() = configurationContext.libraries.add(this)
	operator fun String.unaryMinus() = configurationContext.libraries.remove(this)
}

class ProcessorContext(val configurationContext: ConfigurationContext) {
	operator fun String.unaryPlus() = configurationContext.processors.add(
		Class.forName(this) as IClassProcessor
	)
	operator fun String.unaryMinus() = configurationContext.processors.remove(
		Class.forName(this) as IClassProcessor
	)
	
	operator fun Class<out IClassProcessor>.unaryPlus() = configurationContext.processors.add(this.newInstance())
	operator fun Class<out IClassProcessor>.unaryMinus() = configurationContext.processors.remove(this.newInstance())
	
	operator fun KClass<out IClassProcessor>.unaryPlus() = configurationContext.processors.add(this.java.newInstance())
	operator fun KClass<out IClassProcessor>.unaryMinus() = configurationContext.processors.remove(this.java.newInstance())
	
	operator fun IClassProcessor.unaryPlus() = configurationContext.processors.add(this)
	operator fun IClassProcessor.unaryMinus() = configurationContext.processors.remove(this)
}

fun ConfigurationContext.processors(contextProvider: ProcessorContext.() -> Unit) {
	ProcessorContext(this).apply(contextProvider)
}

fun ConfigurationContext.libraries(contextProvider: LibraryContext.() -> Unit) {
	LibraryContext(this).apply(contextProvider)
}

fun deobfuscate(contextProvider: ConfigurationContext.() -> Unit) {
	ConfigurationContext().apply(contextProvider).also {
		Binfix(it)
	}
}
