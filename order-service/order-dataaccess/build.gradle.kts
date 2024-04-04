import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {

    id("io.kotest.multiplatform") version "5.0.2"
    kotlin("plugin.jpa") version "1.8.22"
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    //mysql
    implementation("org.postgresql:postgresql:42.6.0")
    implementation(project(":order-service:order-domain:order-domain-core"))
    implementation(project(":order-service:order-domain:order-application-service"))
    implementation(project(":common:common-domain"))

//    api(project(":common:common-dataaccess"))

}


tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}