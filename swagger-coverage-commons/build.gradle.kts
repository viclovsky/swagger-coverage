plugins {
    java
    `java-library`
}

description = "Swagger Coverage Commons"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.freemarker:freemarker")
    implementation("io.swagger:swagger-models")
    implementation("io.swagger.core.v3:swagger-models")
    implementation("org.slf4j:slf4j-simple")
    implementation("log4j:log4j")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.10.1")
    testImplementation("junit:junit")
}
