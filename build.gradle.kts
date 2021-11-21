import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id ("jacoco")
	id( "org.sonarqube" ) version "3.3"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
}

group = "com.dbp"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}


tasks.test {
	useJUnitPlatform()
	// Correcion de la consola (https://github.com/junit-team/junit5/issues/1774)
	systemProperty ("java.util.logging.config.file", "${project.buildDir}/resources/test/logging-test.properties")
	ignoreFailures = (project.hasProperty("testIgnoreFailures") && project.property("testIgnoreFailures")=="true")
}

tasks.jacocoTestReport {
	reports {
		xml.required.set(true)
		csv.required.set(true)
	}
}


// Cuando se ejecute sonarqube  se lanzara test antes de sonarqube.
tasks.named("sonarqube").configure {
	dependsOn("test")
}

// Cuando termine la de test, generaremos el report de cobertura
tasks.named("test").configure{
	finalizedBy("jacocoTestReport")
}

sonarqube{
	properties {
		property ("sonar.projectKey", "blancoparis-tfc_tools")
		property ("sonar.organization", "blancoparis-tfc")
		property ("sonar.host.url", "https://sonarcloud.io")
	}
}

tasks.register<org.springframework.boot.gradle.tasks.run.BootRun>("bootRunDev") {
	group = "application"
	classpath = sourceSets["main"].runtimeClasspath
	mainClass.set("com.dbp.tools.ToolsApplicationKt")
	systemProperty ("spring.profiles.active", "dev")
}

tasks.register<org.springframework.boot.gradle.tasks.run.BootRun>("bootRunProd") {
	group = "application"
	classpath = sourceSets["main"].runtimeClasspath
	mainClass.set("com.dbp.tools.ToolsApplicationKt")
	systemProperty ("spring.profiles.active", "prod")
}

