import org.springframework.boot.gradle.tasks.bundling.BootJar


dependencies {
    implementation(project(":order-service:order-domain:order-application-service"))
    implementation(project(":infrastructure:kafka:kafka-producer"))
    implementation(project(":infrastructure:kafka:kafka-consumer"))
    implementation(project(":infrastructure:kafka:kafka-model"))
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}
