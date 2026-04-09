plugins {
    kotlin("jvm") version "2.3.20"
    kotlin("plugin.spring") version "2.3.20"
    kotlin("plugin.jpa") version "2.3.20"

    id("org.springframework.boot") version "4.0.5"
    id("io.spring.dependency-management") version "1.1.7"

    id("org.jlleitschuh.gradle.ktlint") version "14.2.0"
}

group = "org.cherrypick.picker"
version = "0.1.0-SNAPSHOT"
description = "AI-ready Spring Modulith backend for Copilot"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

extra["springAiVersion"] = "2.0.0-M4"
extra["springModulithVersion"] = "2.0.5"

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.spring.io/milestone")
    }
}

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")

    // Spring AI
    implementation("org.springframework.ai:spring-ai-advisors-vector-store")
    implementation("org.springframework.ai:spring-ai-starter-model-openai")
    implementation("org.springframework.ai:spring-ai-starter-vector-store-qdrant")

    // Spring Modulith
    implementation("org.springframework.modulith:spring-modulith-starter-core")
    implementation("org.springframework.modulith:spring-modulith-starter-jpa")

    // Kotlin / Jackson
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("tools.jackson.module:jackson-module-kotlin")

    // Database / migration
    implementation("org.flywaydb:flyway-database-postgresql")

    // Other libraries
    implementation("org.apache.pdfbox:pdfbox:3.0.5")

    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.flywaydb:flyway-database-postgresql")
    runtimeOnly("org.springframework.modulith:spring-modulith-actuator")
    runtimeOnly("org.springframework.modulith:spring-modulith-observability")
    runtimeOnly("org.springframework.modulith:spring-modulith-runtime")

    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    developmentOnly("org.springframework.ai:spring-ai-spring-boot-docker-compose")

    // Spring Boot test
    testImplementation("org.springframework.boot:spring-boot-starter-actuator-test")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
    testImplementation("org.springframework.boot:spring-boot-starter-flyway-test")
    testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")

    // Spring AI test
    testImplementation("org.springframework.ai:spring-ai-spring-boot-testcontainers")

    // Spring Modulith test
    testImplementation("org.springframework.modulith:spring-modulith-starter-test")

    // Kotlin test
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    // Database / in-memory test
    testImplementation("com.h2database:h2")

    // Testcontainers
    testImplementation("org.testcontainers:testcontainers-junit-jupiter")
    testImplementation("org.testcontainers:testcontainers-ollama")
    testImplementation("org.testcontainers:testcontainers-postgresql")
    testImplementation("org.testcontainers:testcontainers-qdrant")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

ktlint {
    filter {
        exclude("**/build/**")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named("check") {
    dependsOn("ktlintCheck")
}

tasks.register("lint") {
    group = "verification"
    description = "Runs the repository lint suite."
    dependsOn("ktlintCheck")
}
