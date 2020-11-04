import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

buildscript {
    repositories {
        mavenLocal()
        jcenter()
    }

    dependencies {
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
    }
}

plugins {
    java
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

group = "com.github.viclovsky.swagger.coverage"
version = version

val root = rootProject.projectDir
val gradleScriptDir by extra("$root/gradle")

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

configure(subprojects) {
    group = "com.github.viclovsky.swagger.coverage"
    version = version

    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(from = "$gradleScriptDir/bintray.gradle")
    apply(from = "$gradleScriptDir/maven.gradle")

    configure<DependencyManagementExtension> {
        imports {
            mavenBom("com.fasterxml.jackson:jackson-bom:2.9.8")
        }
        dependencies {
            dependency("org.freemarker:freemarker:2.3.29")
            dependency("io.swagger:swagger-models:1.6.2")
            dependency("io.swagger:swagger-compat-spec-parser:1.0.52")
            dependency("org.slf4j:slf4j-simple:1.7.12")
            dependency("log4j:log4j:1.2.17")
            dependency("com.beust:jcommander:1.78")
            dependency("junit:junit:4.12")
            dependency("org.hamcrest:hamcrest-all:1.3")
            dependency("io.rest-assured:rest-assured:4.3.1")
            dependency("com.github.tomakehurst:wiremock:2.24.1")
            dependency("com.fasterxml.jackson.core:jackson-core:2.10.1")
            dependency("com.fasterxml.jackson.core:jackson-databind:2.10.1")
            dependency("org.springframework:spring-web:5.2.6.RELEASE")
        }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    val sourceJar by tasks.creating(Jar::class) {
        from(sourceSets.getByName("main").allSource)
        archiveClassifier.set("sources")
    }

    val javadocJar by tasks.creating(Jar::class) {
        from(tasks.getByName("javadoc"))
        archiveClassifier.set("javadoc")
    }

    tasks.withType(Javadoc::class) {
        (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
    }

    artifacts.add("archives", sourceJar)
    artifacts.add("archives", javadocJar)


    tasks.compileJava {
        options.encoding = "UTF-8"
    }

    tasks.compileTestJava {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
    }

    tasks.jar {
        manifest {
            attributes(mapOf(
                    "Implementation-Title" to project.name,
                    "Implementation-Version" to project.version

            ))
        }
    }
}

repositories {
    mavenCentral()
}

