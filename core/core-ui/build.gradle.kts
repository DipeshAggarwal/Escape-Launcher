plugins {
    id("convention.android.library")
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.text)
    implementation(libs.androidx.compose.material3)

    implementation(libs.google.material)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.appcompat)
}
