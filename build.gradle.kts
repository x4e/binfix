import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	repositories {
		mavenCentral()
	}
	
	dependencies {
		classpath(kotlin("gradle-plugin", version = "1.3.71"))
	}
}

group = "dev.binclub"
version = "1.0"

plugins {
	kotlin("jvm") version "1.3.71"
}
sourceSets {
	getByName("main").java.srcDirs("src/main/")
}

kotlin.sourceSets {
	getByName("main").kotlin.srcDirs("src/main/")
}
repositories {
	mavenCentral()
}
tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "1.8"
}


dependencies {
	compile(kotlin("stdlib-jdk8"))
	
	compile("org.jetbrains.kotlin:kotlin-scripting-jsr223:1.3.71")
	
	compile("org.ow2.asm:asm:7.1")
	compile("org.ow2.asm:asm-tree:7.1")
	compile("org.ow2.asm:asm-commons:7.1")
	compile("org.ow2.asm:asm-util:7.1")
}

tasks.withType<Jar> {
	manifest {
		attributes["Main-Class"] = "dev.binclub.binfix.Binfix"
	}
	
	from(configurations.compile.get().map {if (it.isDirectory) it else zipTree(it)})
}
