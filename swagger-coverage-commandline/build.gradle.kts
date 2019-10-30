plugins {
    java
    application
}

group = "ru.vicdev.swagger.coverage"
version = "1.0-SNAPSHOT"

description = "Openapi-coverage Commandline"

application {
    mainClassName = "ru.vicdev.swagger.coverage.CommandLine"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    testCompile("junit:junit:4.12")
}
