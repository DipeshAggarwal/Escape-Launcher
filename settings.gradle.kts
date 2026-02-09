includeBuild("build-logic")

pluginManagement {
    includeBuild("build-logic")

    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        google()
    }

    plugins {
        id("com.android.application") version "9.0.0"
        id("com.android.library") version "9.0.0"
        id("org.jetbrains.kotlin.android") version "2.3.10"
        id("org.jetbrains.kotlin.jvm") version "2.3.10"
        id("org.jetbrains.kotlin.plugin.compose") version "2.3.10"
        id("com.google.dagger.hilt.android") version "2.59.1"
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Escape Launcher"

// app
include(":app")

// core module
include(":core:core-android")
include(":core:core-common")
include(":core:core-datastore")
include(":core:core-testing")
include(":core:core-ui")

// domain module
include(":domain:domain-apps")
include(":domain:domain-challenges")
include(":domain:domain-screentime")
include(":domain:domain-settings")

// data module
include(":data:data-apps")
include(":data:data-challenges")
include(":data:data-screentime")
include(":data:data-settings")

// features module
include(":features:features-home")
include(":features:features-onboarding")
include(":features:features-screentime")
include(":features:features-settings")
