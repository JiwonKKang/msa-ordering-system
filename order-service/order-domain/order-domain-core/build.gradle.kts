import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

dependencies {
    implementation(project(":common:common-domain"))
}

bootJar.enabled = false
jar.enabled = true