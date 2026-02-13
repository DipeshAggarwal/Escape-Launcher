plugins {
    id("convention.android.library")
}

dependencies {
    implementation(libs.androidx.compose.material.icons.extended)

    implementation(project(":core:core-common"))
    implementation(project(":core:core-ui"))

    implementation(project(":domain:domain-apps"))
}
