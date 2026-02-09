package convention.android

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply("com.android.library")
        target.pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

        val baseNamespace = target.providers
            .gradleProperty("BASE_NAMESPACE")
            .get()

        val moduleNamespace = target.path
            .removePrefix(":")
            .replace(":", ".")
            .replace("-", ".")

        val fullNamespace = "$baseNamespace.$moduleNamespace"

        target.extensions.configure<LibraryExtension> {
            namespace = fullNamespace

            compileSdk = 36
            defaultConfig {
                minSdk = 26
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            buildFeatures {
                compose = true
            }
        }

        target.extensions.configure<KotlinAndroidProjectExtension> {
            jvmToolchain(17)
        }

        target.dependencies {
            add("implementation", platform("androidx.compose:compose-bom:2026.01.01"))

            add("implementation", "androidx.core:core-ktx:1.17.0")
            add("implementation", "androidx.lifecycle:lifecycle-runtime-ktx:2.10.0")

            add("implementation", "androidx.compose.ui:ui")
            add("implementation", "androidx.compose.material3:material3")
        }
    }
}
