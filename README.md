# Binfix

An open source java bytecode deobfuscator.

This is meant to be a successor to the now unmaintained [Java Deobfuscator](https://github.com/java-deobfuscator/deobfuscator) project.

## Usage
Create a config called `config.kts` with the following template:
```kotlin
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

```

Run using `java -jar binfix.jar config.kts`

## Contributing
You can create your own deobfuscating transformer by implementing the `IClassProcessor` interface.

If you then wish you can create a pull request with the new transformer.
