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
    testCompile("com.github.tomakehurst", "wiremock", "2.24.1")
    testCompile("org.hamcrest", "hamcrest-all", "1.3")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}