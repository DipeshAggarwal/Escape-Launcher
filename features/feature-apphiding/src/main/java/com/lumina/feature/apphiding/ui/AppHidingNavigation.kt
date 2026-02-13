package com.lumina.feature.apphiding.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lumina.core.ui.Motion.SCREEN_TRANSITION_DURATION

const val APP_HIDING_ROUTE = "hidden_apps"

fun NavGraphBuilder.appHidingNavigation(
    launcherPackageName: String,
    onBack: () -> Unit
) {
    composable(
        APP_HIDING_ROUTE,
        enterTransition = { fadeIn(tween(SCREEN_TRANSITION_DURATION)) },
        exitTransition = { fadeOut(tween(SCREEN_TRANSITION_DURATION)) }
    ) {
        AppHidingScreen(launcherPackageName, onBack)
    }
}
