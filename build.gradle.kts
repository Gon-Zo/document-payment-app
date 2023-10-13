import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("org.springframework.boot") version "3.1.1"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("io.projectreactor:reactor-test")


    // client .. !!
    implementation("io.vertx:vertx-mysql-client:4.4.5")
    // https://mvnrepository.com/artifact/org.hibernate.reactive/hibernate-reactive-core
    implementation("org.hibernate.reactive:hibernate-reactive-core:2.0.6.Final")
    // https://mvnrepository.com/artifact/org.hibernate/hibernate-jpamodelgen
    compileOnly("org.hibernate:hibernate-jpamodelgen:4.3.7.Final")

    // https://mvnrepository.com/artifact/io.smallrye.reactive/mutiny-reactor
    implementation("io.smallrye.reactive:mutiny-reactor:2.5.1")

    // https://mvnrepository.com/artifact/org.hibernate/hibernate-jpamodelgen
    compileOnly("org.hibernate:hibernate-jpamodelgen:6.3.1.Final")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
