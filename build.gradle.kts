// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val isFoss = gradle.startParameter.taskNames.any { it.contains("foss", ignoreCase = true) }
    if (!isFoss) {
        repositories {
            google()
            mavenCentral()
        }
        dependencies {
            classpath(libs.google.services.gradle)
            classpath(libs.firebase.crashlytics.gradle)
        }
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
}
