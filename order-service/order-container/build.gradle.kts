import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":order-service:order-domain:order-application-service"))
    implementation(project(":order-service:order-domain:order-domain-core"))

    implementation(project(":order-service:order-application"))
    implementation(project(":order-service:order-dataaccess"))
    implementation(project(":order-service:order-messaging"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

tasks.getByName<BootBuildImage>("bootBuildImage") {
    imageName.set("jiwonkkang/order-container:latest")
    environment = mapOf("--platform" to "linux/arm64")
}

tasks.named<BootJar>("bootJar") {
    enabled = true
}

tasks.named<Jar>("jar") {
    enabled = true
}
