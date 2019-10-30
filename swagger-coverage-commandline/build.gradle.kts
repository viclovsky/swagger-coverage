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
    compile("io.swagger", "swagger-compat-spec-parser", "1.0.36")
    compile("org.slf4j", "slf4j-simple", "1.7.12")
    compile("log4j", "log4j", "1.2.17")
    testCompile("junit:junit:4.12")
}
