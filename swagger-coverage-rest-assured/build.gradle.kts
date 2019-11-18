plugins {
    java
}

group = "ru.viclovsky.swagger.coverage"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":swagger-coverage-commons"))
    implementation("io.rest-assured:rest-assured")
    implementation("io.swagger:swagger-compat-spec-parser")
    testImplementation("junit:junit")
    testImplementation("com.github.tomakehurst:wiremock")
    testImplementation("org.hamcrest:hamcrest-all")
}