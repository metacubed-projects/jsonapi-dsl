project {
    modelVersion '4.0.0'

    name 'JSON:API DSL'

    organization {
        name 'metacubed'
    }

    groupId 'org.metacubed'
    artifactId 'jsonapi-dsl'
    version '1.0.0-SNAPSHOT'
    packaging 'pom'

    properties {
        // lang
        'java.version' '1.8'
        'kotlin.version' '1.2.61'
        // util
        'jackson.version' '2.9.7'
        // test
        'junit-platform.version' '1.3.1'
        'junit-jupiter.version' '5.3.1'
        // plugins
        'maven-compiler-plugin.version' '3.8.0'
        'maven-surefire-plugin.version' '2.22.1'
    }

    dependencyManagement {
        dependencies {
            dependency {
                groupId 'org.jetbrains.kotlin'
                artifactId 'kotlin-stdlib-jdk8'
                version '${kotlin.version}'
            }
        }
    }
    dependencies {
        dependency {
            groupId 'org.jetbrains.kotlin'
            artifactId 'kotlin-stdlib-jdk8'
        }
    }

    build {
        pluginManagement {
            plugins {
                plugin {
                    groupId 'org.jetbrains.kotlin'
                    artifactId 'kotlin-maven-plugin'
                    version '${kotlin.version}'
                    executions {
                        execution {
                            id 'compile'
                            goals 'compile'
                            configuration {
                                sourceDirs {
                                    sourceDir '${project.basedir}/src/main/kotlin'
                                    sourceDir '${project.build.directory}/generated-sources/kotlin'
                                }
                            }
                        }
                        execution {
                            id 'test-compile'
                            goals 'test-compile'
                            configuration {
                                sourceDirs {
                                    sourceDir '${project.basedir}/src/test/kotlin'
                                    sourceDir '${project.build.directory}/generated-test-sources/kotlin'
                                }
                            }
                        }
                    }
                    dependencies {
                        dependency {
                            groupId 'org.jetbrains.kotlin'
                            artifactId 'kotlin-maven-allopen'
                            version '${kotlin.version}'
                        }
                    }
                    configuration {
                        jvmTarget '${java.version}'
                        compilerPlugins {
                            plugin 'spring'
                        }
                        args {
                            arg '-Xjsr305=strict'
                            arg '-Werror'
                        }
                    }
                }
                plugin {
                    artifactId 'maven-compiler-plugin'
                    version '${maven-compiler-plugin.version}'
                    executions {
                        execution {
                            id 'default-compile'
                            phase 'none'
                        }
                        execution {
                            id 'default-testCompile'
                            phase 'none'
                        }
                        execution {
                            id 'java-compile'
                            phase 'compile'
                            goals 'compile'
                        }
                        execution {
                            id 'java-test-compile'
                            phase 'test-compile'
                            goals 'testCompile'
                        }
                    }
                    configuration {
                        failOnWarning 'true'
                    }
                }
                plugin {
                    artifactId 'maven-surefire-plugin'
                    version '${maven-surefire-plugin.version}'
                    dependencies {
                        dependency {
                            groupId 'org.junit.platform'
                            artifactId 'junit-platform-surefire-provider'
                            version '${junit-platform.version}'
                        }
                        dependency {
                            groupId 'org.junit.jupiter'
                            artifactId 'junit-jupiter-engine'
                            version '${junit-jupiter.version}'
                        }
                    }
                }
            }
        }
        plugins {
            plugin {
                groupId 'org.jetbrains.kotlin'
                artifactId 'kotlin-maven-plugin'
            }
            plugin {
                artifactId 'maven-compiler-plugin'
            }
            plugin {
                artifactId 'maven-surefire-plugin'
            }
        }
    }

    modules {
        module 'model'
        module 'builder'
    }
}
