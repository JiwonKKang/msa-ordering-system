import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("plugin.jpa") version "1.9.23"
}

apply {
    plugin("kotlin-jpa")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {
    implementation(project(":payment-service:payment-domain:payment-domain-core"))
    implementation(project(":payment-service:payment-domain:payment-application-service"))
    implementation(project(":common:common-domain"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql:42.6.0")
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}