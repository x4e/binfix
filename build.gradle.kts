import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	repositories {
		mavenCentral()
	}
	
	dependencies {
		classpath(kotlin("gradle-plugin", version = "1.3.71"))
	}
}

group = "org.example"
version = "1.0-SNAPSHOT"

plugins {
	kotlin("jvm") version "1.3.71"
}

sourceSets {
	getByName("main").java.srcDirs("src/main/")
}

kotlin.sourceSets {
	getByName("main").kotlin.srcDirs("src/main/")
}
dependencies {
	implementation(kotlin("stdlib-jdk8"))
}
repositories {
	mavenCentral()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
	jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
	jvmTarget = "1.8"
}
