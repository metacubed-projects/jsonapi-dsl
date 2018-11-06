import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    id("org.jetbrains.kotlin.jvm") version embeddedKotlinVersion apply false
}

tasks {
    withType<Wrapper> {
        gradleVersion = "4.10.1"
    }
}

allprojects {
    group = "org.metacubed"
    version = "1.0.0-SNAPSHOT"

    repositories {
        jcenter()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        add("implementation", "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$embeddedKotlinVersion")
        add("testImplementation", "org.junit.jupiter:junit-jupiter-api:5.3.1")
        add("testRuntimeOnly", "org.junit.jupiter:junit-jupiter-engine:5.3.1")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                allWarningsAsErrors = true
            }
        }
        withType<Test> {
            useJUnitPlatform()
        }
    }
}
