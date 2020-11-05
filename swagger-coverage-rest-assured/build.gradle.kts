plugins {
    java
    `java-library`
}

description = "Swagger Coverage Rest-Assured"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":swagger-coverage-commons"))
    implementation("io.rest-assured:rest-assured")
    implementation("io.swagger:swagger-compat-spec-parser")
    testImplementation("junit:junit")
    testImplementation("com.github.tomakehurst:wiremock")
    testImplementation("org.hamcrest:hamcrest")
}