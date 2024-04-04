import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude("commons-logging:commons-logging")
    }
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation(project(":common:common-domain"))
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}