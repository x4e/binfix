import dev.binclub.binfix.configuration.*
import dev.binclub.binfix.processors.name.ClassRemapper

deobfuscate {
	input = "input.jar"
	output = "input-deobf.jar"
	
	libraries {
		+"library.jar"
	}
	
	processors {
		+ClassRemapper {
			flattenPackages = false
		}
	}
}
