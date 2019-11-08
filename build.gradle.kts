import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    java
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

group = "ru.viclovsky.swagger.coverage"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

configure(subprojects) {
    apply(plugin = "io.spring.dependency-management")

    configure<DependencyManagementExtension> {
        imports {
            mavenBom("com.fasterxml.jackson:jackson-bom:2.9.8")
        }
        dependencies {
            dependency("io.swagger:swagger-compat-spec-parser:1.0.36")
            dependency("org.slf4j:slf4j-simple:1.7.12")
            dependency("log4j:log4j:1.2.17")
            dependency("com.beust:jcommander:1.78")
            dependency("junit:junit:4.12")
            dependency("org.hamcrest:hamcrest-all:1.3")
            dependency("io.rest-assured:rest-assured:4.0.0")
            dependency("com.github.tomakehurst:wiremock:2.24.1")
        }
    }
}

repositories {
    mavenCentral()
}

