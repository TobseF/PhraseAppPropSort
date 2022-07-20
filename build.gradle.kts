import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    kotlin("plugin.serialization") version "1.6.10"
}

group = "de.tfr.tool"
version = "1.0.6"

application {
    applicationName = "PhraseAppPropSort"
    mainClassName = "tools.paps.RunKt"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.klogging:klogging-jvm:0.4.9")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("com.github.kittinunf.fuel:fuel-kotlinx-serialization:2.3.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.3.2")

    implementation("net.mamoe.yamlkt:yamlkt:0.10.2")
    testImplementation("org.junit.jupiter", "junit-jupiter", "5.4.0")
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("app")
    minimize()
}
