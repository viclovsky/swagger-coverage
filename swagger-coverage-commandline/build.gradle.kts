plugins {
    java
    application
}

group = "ru.viclovsky.swagger.coverage"
version = "1.0-SNAPSHOT"

description = "Openapi-coverage Commandline"

application {
    mainClassName = "ru.viclovsky.swagger.coverage.CommandLine"
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
