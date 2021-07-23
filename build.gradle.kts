import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    kotlin("jvm") version "1.5.0"
    kotlin("kapt") version "1.5.0"

    id("idea")
    id("java")
    id("io.ebean") version "12.8.0"
    application
    id("org.liquibase.gradle") version "2.0.4"
}

group = "me.plevy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
val vertxVersion = "4.0.3"
val dbChangeLog = "/src/main/resources/liquibase/dbchangelog.xml"

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.vertx", "vertx-lang-kotlin")
    implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
    implementation("io.vertx:vertx-web:$vertxVersion")
    implementation(kotlin("stdlib-jdk8"))

    implementation("io.ebean", "ebean", "12.8.0")
    implementation(kapt("io.ebean", "kotlin-querybean-generator", "12.8.0"))
    implementation("org.postgresql:postgresql:42.2.20")
    liquibaseRuntime("org.liquibase:liquibase-core:4.2.2")
    liquibaseRuntime("org.postgresql:postgresql:42.2.20")

}
liquibase {
    activities.register("main") {
        arguments = mapOf(
            "logLevel" to "debug",
            "changeLogFile" to dbChangeLog,
            "url" to "jdbc:postgresql://localhost:4200/test",
            "username" to "postgres",
            "password" to "postgres",
            "driver" to "org.postgresql.Driver"

        )
    }
    runList = "main"
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}


