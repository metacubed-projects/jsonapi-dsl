plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8", "1.2.21"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.2.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.2.0")
}
