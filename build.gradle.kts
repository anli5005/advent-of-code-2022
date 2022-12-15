/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * Learn more about Gradle by exploring our samples at https://docs.gradle.org/7.6/samples
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    kotlin("jvm") version "1.7.21"
    kotlin("plugin.serialization") version "1.7.20"
    application
}

repositories {
    mavenCentral()
}

fun createSourceSet(name: String) {
    sourceSets.create(name) {
        kotlin.srcDir(name.padStart(2, '0'))
        dependencies {
            implementation(kotlin("test"))
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
        }
    }
}

for (i in 4..25) {
    createSourceSet(i.toString())
}