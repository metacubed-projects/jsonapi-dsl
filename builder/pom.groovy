project {
    modelVersion '4.0.0'

    name 'JSON:API Builder'

    parent {
        groupId 'org.metacubed'
        artifactId 'jsonapi-dsl'
        version '1.0.0-SNAPSHOT'
        relativePath '../pom.groovy'
    }

    artifactId 'jsonapi-builder'
    packaging 'jar'

    dependencies {
        // project
        dependency {
            groupId 'org.metacubed'
            artifactId 'jsonapi-model'
            version '${project.version}'
        }
        // test
        dependency {
            groupId 'org.junit.jupiter'
            artifactId 'junit-jupiter-api'
            version '${junit-jupiter.version}'
            scope 'test'
        }
        // test - util
        dependency {
            groupId 'com.fasterxml.jackson.module'
            artifactId 'jackson-module-kotlin'
            version '${jackson.version}'
            scope 'test'
        }
        dependency {
            groupId 'com.fasterxml.jackson.datatype'
            artifactId 'jackson-datatype-jsr310'
            version '${jackson.version}'
            scope 'test'
        }
    }
}
