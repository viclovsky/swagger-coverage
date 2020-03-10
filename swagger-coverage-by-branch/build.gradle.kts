plugins {
    java
    `java-library`
}

description = "Swagger-coverage Commandline"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":swagger-coverage-commons"))
    implementation("org.freemarker:freemarker")
    implementation("io.swagger:swagger-compat-spec-parser")
    implementation("com.beust:jcommander")
    testImplementation("junit:junit")
    testImplementation("org.hamcrest:hamcrest-all")
}
