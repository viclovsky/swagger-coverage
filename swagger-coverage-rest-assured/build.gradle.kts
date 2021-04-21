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
    implementation("io.swagger:swagger-models")
    implementation("io.swagger.core.v3:swagger-models")
    testImplementation("junit:junit")
    testImplementation("com.github.tomakehurst:wiremock")
    testImplementation("org.hamcrest:hamcrest")
}

tasks {
    test {
        //set the workingDir to the build dir so we don't pollute the main project dir
        //with generated test files
        workingDir(buildDir)
    }
}