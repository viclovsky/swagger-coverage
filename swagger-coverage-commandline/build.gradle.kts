plugins {
    java
    application
}

group = "ru.viclovsky.swagger.coverage"
version = "1.0-SNAPSHOT"

description = "Swagger-coverage Commandline"

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
    implementation("io.swagger:swagger-compat-spec-parser")
    implementation("org.slf4j:slf4j-simple")
    implementation("log4j:log4j")
    implementation("com.beust:jcommander")
    testImplementation("junit:junit")
    testImplementation("org.hamcrest:hamcrest-all")
}
