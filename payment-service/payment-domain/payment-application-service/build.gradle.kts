import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation("org.springframework:spring-tx")
    implementation(project(":common:common-domain"))
    implementation(project(":payment-service:payment-domain:payment-domain-core"))
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}