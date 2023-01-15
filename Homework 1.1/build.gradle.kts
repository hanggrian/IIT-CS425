import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

plugins {
    kotlin("jvm") version libs.versions.kotlin apply false
    kotlin("android") version libs.versions.kotlin apply false
    kotlin("android.extensions") version libs.versions.kotlin apply false
    kotlin("kapt") version libs.versions.kotlin apply false
}

allprojects {
    group = "com.example"
    version = "0.1"
}

subprojects {
    plugins.withType<KotlinPluginWrapper> {
        kotlinExtension.jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

tasks.register(LifecycleBasePlugin.CLEAN_TASK_NAME) {
    delete(buildDir)
}
