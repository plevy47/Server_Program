import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    //kotlin("kapt") version "1.5.0"
    kotlin("jvm") version "1.5.0"
}

group = "me.plevy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
val vertxVersion = "4.0.3"

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.vertx", "vertx-lang-kotlin")
    implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
    implementation("io.vertx:vertx-web:$vertxVersion")
    implementation(kotlin("stdlib-jdk8"))

}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

