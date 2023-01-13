plugins {
    application
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.dokka)
}

application.mainClass.set("com.example.App")

dependencies {
    ktlint(libs.ktlint, ::configureKtlint)
    ktlint(libs.rulebook.ktlint)
    implementation(libs.kotlinx.coroutines.javafx)
    implementation(libs.bundles.exposed)
    implementation(libs.ktfx)
    implementation(libs.commons.lang3)
    testImplementation(kotlin("test-junit", libs.versions.kotlin.get()))
    testImplementation(libs.truth)
}
