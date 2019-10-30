plugins {
    java
}

group = "ru.viclovsky.swagger.coverage"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compile("io.rest-assured", "rest-assured", "4.0.0")
    compile("io.swagger", "swagger-compat-spec-parser", "1.0.36")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}