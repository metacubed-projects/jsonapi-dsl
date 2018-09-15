import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    kotlin("jvm") version "1.2.70" apply false
}

allprojects {
    group = "org.metacubed"
    version = "1.0.0-SNAPSHOT"

    tasks {
        withType<Wrapper> {
            gradleVersion = "4.10.1"
        }
        withType<KotlinCompile> {
            kotlinOptions {
                allWarningsAsErrors = true
            }
        }
        withType<Test> {
            useJUnitPlatform()
        }
    }

    repositories {
        jcenter()
    }
}

dependencies {
    subprojects.forEach {
        archives(it)
    }
}
