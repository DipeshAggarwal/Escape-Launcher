plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(kotlin("gradle-plugin"))
}

gradlePlugin {
    plugins {
        register("androidLibraryConvention") {
            id = "convention.android.library"
            implementationClass = "convention.android.AndroidLibraryConventionPlugin"
        }

        register("kotlinLibraryConvention") {
            id = "convention.kotlin.library"
            implementationClass = "convention.kotlin.KotlinLibraryConventionPlugin"
        }
    }
}
