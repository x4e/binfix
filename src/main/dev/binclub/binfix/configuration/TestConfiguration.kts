import dev.binclub.binfix.configuration.deobfuscate
import dev.binclub.binfix.configuration.libraries
import dev.binclub.binfix.configuration.processors
import dev.binclub.binfix.processors.name.ClassNameProcessor

deobfuscate {
	input = "input.jar"
	output = "input-deobf.jar"
	
	libraries {
		+"library.jar"
	}
	
	processors {
		+"dev.binclub.binfix.processors.name.ClassNameProcessor"
		+ClassNameProcessor::class.java
		+ClassNameProcessor::class
		+ClassNameProcessor
		
		-"dev.binclub.binfix.processors.name.ClassNameProcessor"
		-ClassNameProcessor::class.java
		-ClassNameProcessor::class
		-ClassNameProcessor
	}
}
