plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8", "1.2.70"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
}
