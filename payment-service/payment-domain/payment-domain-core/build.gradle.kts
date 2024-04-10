import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":common:common-domain"))
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}