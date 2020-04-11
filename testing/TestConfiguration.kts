import dev.binclub.binfix.configuration.*
import dev.binclub.binfix.processors.name.ClassNameProcessor

deobfuscate {
	input = "input.jar"
	output = "input-deobf.jar"
	
	libraries {
		+"library.jar"
	}
	
	processors {
		+ClassNameProcessor
	}
}
