dependencies {
    implementation(project(":jsonapi-model"))
    testImplementation("com.fasterxml.jackson.module", "jackson-module-kotlin", "2.9.7")
    testImplementation("com.fasterxml.jackson.datatype", "jackson-datatype-jsr310", "2.9.7")
}
