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
    //dirty fix https://github.com/viclovsky/swagger-coverage/issues/90
    implementation("io.swagger.core.v3:swagger-core")
    implementation("io.swagger.core.v3:swagger-models")
    implementation("org.slf4j:slf4j-api")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.10.1")
    testImplementation("junit:junit")
}
