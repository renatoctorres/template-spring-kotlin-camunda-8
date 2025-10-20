
plugins {
    id("org.springframework.boot") version "3.5.6"
    id("dev.detekt") version "2.0.0-alpha.0"
    id("org.jlleitschuh.gradle.ktlint") version "13.1.0"
    id("io.spring.dependency-management") version "1.1.7"
    java
    application
    kotlin("jvm") version "2.2.10"
    kotlin("plugin.spring") version "2.2.10"
}

group = "io.rct.camunda"
version = "1.0.0"
description = "Camunda 8 - Kotlin and Spring Boot Template - Zeebe Client"

val camundaVersion = "8.8.0"
val camundaOperateVersion = "8.3.0.1"
val kotlinVersion = "2.2.10"
val jacksonModuleKotlinVersion = "3.0.0"
val springBootStarterCamundaVersion = "8.5.22"
val springBootVersion = "3.5.6"
val springDocOpenApiVersion = "3.0.0-M1"

val springCoreVersion = "7.0.0-RC1"
val sl4jVersion = "2.0.17"
val logbackVersion = "1.5.19"
val detektVersion = "2.0.0-alpha.0"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}


dependencies {
    runtimeOnly(group = "org.springframework", name = "spring-core", version = springCoreVersion)

    implementation("org.springframework.boot:spring-boot-starter:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-web:${springBootVersion}")
    implementation("io.camunda:zeebe-client-java:${camundaVersion}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("tools.jackson.module:jackson-module-kotlin:${jacksonModuleKotlinVersion}")
    implementation("io.camunda:camunda-spring-boot-starter:${camundaVersion}")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${springDocOpenApiVersion}")
    implementation("org.springframework.boot:spring-boot-starter-websocket:${springBootVersion}")
    implementation("io.camunda:camunda-tasklist-client-java:${camundaVersion}")
    implementation("io.camunda:camunda-operate-client-java:${camundaOperateVersion}")
    implementation("org.slf4j:slf4j-api:${sl4jVersion}")
    implementation("ch.qos.logback:logback-core:${logbackVersion}")
    testImplementation("ch.qos.logback:logback-classic:${logbackVersion}")
    testImplementation ("org.springframework.boot:spring-boot-starter-test:${springBootVersion}")

//    implementation("org.camunda.community:camunda-8-tasklist-rest-java-client:${camundaVersion}")
//    implementation("org.camunda.community:camunda-8-operate-rest-java-client:${camundaVersion}")
//    testImplementation(kotlin("test"))
}

kotlin {
    compilerOptions {
        freeCompilerArgs.set(mutableListOf<String>("-Xjsr305=strict"))
    }
    jvmToolchain(24)
}

configurations.matching { it.name != "detekt" }.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlin") {
            useVersion(kotlinVersion)
        }
    }
}
