import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.4" apply false
	id("io.spring.dependency-management") version "1.1.4" apply false
	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23" apply false
}

allprojects {
	group = "com.food.ordering.system"
	version = "0.0.1"

	repositories {
		mavenCentral()
		gradlePluginPortal()
		maven {
			url = uri("https://packages.confluent.io/maven")
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

subprojects {

	apply {
		plugin("org.jetbrains.kotlin.jvm")
		plugin("org.jetbrains.kotlin.plugin.spring")
		plugin("org.springframework.boot")
		plugin("io.spring.dependency-management")

	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs += "-Xjsr305=strict"
			jvmTarget = "17"
		}
	}

	tasks.named("bootJar") {
		enabled = false
	}

	java {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

		testImplementation("org.springframework.boot:spring-boot-starter-test")

	}
}

val serializerVersion = "7.6.0"
val avroVersion = "1.11.3"

project(":infrastructure:kafka:kafka-config-data") {
	dependencies {
		implementation("org.springframework.kafka:spring-kafka")
		api("io.confluent:kafka-avro-serializer:$serializerVersion")
		api("org.apache.avro:avro:$avroVersion")
	}
}

project(":infrastructure:kafka:kafka-producer") {
	dependencies {
		api("io.confluent:kafka-avro-serializer:$serializerVersion")
		api("org.apache.avro:avro:$avroVersion")
	}
}

project(":infrastructure:kafka:kafka-consumer") {
	dependencies {
		api("io.confluent:kafka-avro-serializer:$serializerVersion")
		api("org.apache.avro:avro:$avroVersion")
	}
}

project(":infrastructure:kafka:kafka-model") {
	dependencies {
		api("org.springframework.kafka:spring-kafka")
		api("org.apache.avro:avro:${avroVersion}")
	}
}



