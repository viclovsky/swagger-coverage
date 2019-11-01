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
    implementation("io.swagger:swagger-compat-spec-parser:1.0.36")
    implementation("org.slf4j:slf4j-simple:1.7.12")
    implementation("log4j:log4j:1.2.17")
    implementation("com.beust:jcommander:1.78")
    testImplementation("junit:junit:4.12")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
}
