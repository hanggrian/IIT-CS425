pluginManagement.repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}
dependencyResolutionManagement.repositories {
    mavenCentral()
    google()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

rootProject.name = "Homework 1.1"

include("app")
include("website")
