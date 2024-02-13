import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.gitlab.arturbosch.detekt") version "1.23.5"
    id("io.spring.dependency-management") version "1.1.4"
    id("java")
    id("application")
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
}

group = "io.camunda"
version = "8.4.0"
description = "Camunda 8 - Kotlin and Spring Boot Template - Zeebe Client"
java.sourceCompatibility = JavaVersion.VERSION_19

val camundaVersion = "8.4.0"
val camundaTasklistVersion = "8.4.0"
val camundaOperateVersion = "8.3.0"
val springBootStarterCamundaVersion = "8.4.0"
val springBootVersion = "3.2.2"
val springDocOpenApiVersion = "2.3.0"

val springCoreVersion = "6.1.3"
val sl4jVersion = "2.0.12"
val logbackVersion = "1.4.14"
val detektVersion = "1.23.5"

repositories {
    mavenCentral()
}


dependencies {
    runtimeOnly(group = "org.springframework", name = "spring-core", version = springCoreVersion)

    implementation("org.springframework.boot:spring-boot-starter:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-web:${springBootVersion}")
    implementation("io.camunda:zeebe-client-java:${camundaVersion}")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.camunda.spring:spring-boot-starter-camunda:${springBootStarterCamundaVersion}")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${springDocOpenApiVersion}")
    implementation("org.springframework.boot:spring-boot-starter-websocket:${springBootVersion}")
    implementation("io.camunda:camunda-tasklist-client-java:${camundaTasklistVersion}")
    implementation("io.camunda:camunda-operate-client-java:${camundaOperateVersion}")
    implementation("org.slf4j:slf4j-api:${sl4jVersion}")
    implementation("ch.qos.logback:logback-core:${logbackVersion}")
    testImplementation("ch.qos.logback:logback-classic:${logbackVersion}")
    testImplementation ("org.springframework.boot:spring-boot-starter-test:${springBootVersion}")

//    implementation("org.camunda.community:camunda-8-tasklist-rest-java-client:${camundaVersion}")
//    implementation("org.camunda.community:camunda-8-operate-rest-java-client:${camundaVersion}")
//    testImplementation(kotlin("test"))
}

apply(plugin = "org.jetbrains.kotlin.plugin.spring")

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "19"
        freeCompilerArgs += "-Xjsr305=strict"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


tasks.test {
    useJUnitPlatform()
}

allOpen {
    annotation("io.camunda")
}

detekt {
    toolVersion = "1.23.5"
    source.setFrom(files("src/main/kotlin"))
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    parallel = true
    buildUponDefaultConfig = true
}

kotlin {
    jvmToolchain(19)
}
//
