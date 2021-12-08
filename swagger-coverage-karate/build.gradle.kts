plugins {
    java
    `java-library`
}

description = "Swagger Coverage Karate"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":swagger-coverage-commons"))
    api(project(":swagger-coverage-commandline"))
    implementation("io.swagger:swagger-models")
    implementation("io.swagger.core.v3:swagger-models")
    implementation("com.intuit.karate:karate-core")
    //testImplementation -> karate-junit5
}

tasks {
    test {
        workingDir(buildDir)
    }
}