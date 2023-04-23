plugins {
    application
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.packaging)
    alias(libs.plugins.shadow)
}

application.mainClass.set("com.example.App")

packaging {
    appName.set("Cheapest Merchandise")
    modules.set(listOf("javafx.controls"))
    verbose.set(true)
}

dependencies {
    ktlint(libs.ktlint, ::configureKtlint)
    ktlint(libs.rulebook.ktlint)
    implementation(libs.kotlinx.coroutines.javafx)
    implementation(libs.bundles.ktorm)
    implementation(libs.ktfx)
    implementation(libs.commons.lang3)
    testImplementation(kotlin("test-junit", libs.versions.kotlin.get()))
    testImplementation(libs.truth)
}
