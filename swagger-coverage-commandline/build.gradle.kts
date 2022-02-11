plugins {
    java
    `java-library`
    application
}

description = "Swagger-coverage Commandline"

application {
    mainClassName = "com.github.viclovsky.swagger.coverage.CommandLine"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    api(project(":swagger-coverage-commons"))
    implementation("io.swagger.parser.v3:swagger-parser")
    implementation("org.slf4j:slf4j-api")
    implementation("ch.qos.logback:logback-classic")
    implementation("com.beust:jcommander")
    implementation("org.springframework:spring-web")
    testImplementation("junit:junit")
    testImplementation("org.hamcrest:hamcrest")
}

tasks {
    test {
        //set the workingDir to the build dir so we don't pollute the main project dir
        //with generated test files
        workingDir(buildDir)
    }
}
