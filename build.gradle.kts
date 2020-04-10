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
	runtime(kotlin("stdlib-jdk8"))
}

tasks.withType<Jar> {
	manifest {
		attributes["Main-Class"] = "dev.binclub.binfix.MainKt"
	}
	
	from(configurations.runtime.get().map {if (it.isDirectory) it else zipTree(it)})
}



