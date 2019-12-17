plugins {
    java
    `java-library`
}

description = "Swagger Coverage Commons"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":swagger-coverage-model"))
    implementation("org.freemarker:freemarker")
    implementation("io.swagger:swagger-models")
    implementation("org.slf4j:slf4j-simple")
    implementation("log4j:log4j")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    testImplementation("junit:junit")
}
