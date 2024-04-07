import org.springframework.boot.gradle.tasks.bundling.BootJar


dependencies {
    implementation(project(":order-service:order-domain:order-domain-core"))
    implementation(project(":infrastructure:kafka:kafka-config-data"))
    implementation(project(":infrastructure:kafka:kafka-model"))
    implementation(project(":common:common-domain"))
    implementation("org.springframework.kafka:spring-kafka")
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}
