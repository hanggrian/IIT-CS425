import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

plugins {
    kotlin("jvm") version libs.versions.kotlin apply false
    kotlin("android") version libs.versions.kotlin apply false
    kotlin("android.extensions") version libs.versions.kotlin apply false
    kotlin("kapt") version libs.versions.kotlin apply false
    alias(libs.plugins.dokka)
}

allprojects {
    group = RELEASE_GROUP
    version = RELEASE_VERSION
}

subprojects {
    plugins.withType<KotlinPluginWrapper> {
        kotlinExtension.jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

tasks {
    register(LifecycleBasePlugin.CLEAN_TASK_NAME) {
        delete(buildDir)
    }
    dokkaHtmlMultiModule {
        outputDirectory.set(buildDir.resolve("dokka/dokka/"))
    }
}
