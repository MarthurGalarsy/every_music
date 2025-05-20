import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("kapt") version "1.9.23"
}

group = "com.everymusic"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    // Spring 基本
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")

    // MyBatis (Spring Boot 3.2.x に完全対応したバージョン)
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")
    kapt("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")

    // セキュリティ（BCryptのみ利用）
    implementation("org.springframework.security:spring-security-crypto")
    implementation("org.springframework.security:spring-security-config")

    // Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // DB
    runtimeOnly("com.mysql:mysql-connector-j")

    // テスト
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
        javaParameters = true
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}