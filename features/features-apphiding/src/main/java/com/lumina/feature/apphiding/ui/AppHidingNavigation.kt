package com.lumina.feature.apphiding.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val APP_HIDING_ROUTE = "app_hiding"

fun NavGraphBuilder.appHidingNavigation(
    onBack: () -> Unit
) {
    composable(APP_HIDING_ROUTE) {
        AppHidingScreen()
    }
}
