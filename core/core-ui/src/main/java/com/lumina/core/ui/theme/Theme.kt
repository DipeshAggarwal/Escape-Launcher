package com.lumina.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import com.lumina.core.common.AppTheme
import com.materialkolor.DynamicMaterialTheme

@Composable
fun RiktaTheme(
    theme: AppTheme,
    fontFamily: FontFamily,
    content: @Composable (() -> Unit)
) {
    val themeSeed = theme.resolveColorSeed()

    DynamicMaterialTheme(
        seedColor = themeSeed.seedColor,
        isDark = themeSeed.isDark,
        isAmoled = themeSeed.isAmoled,
        typography = RiktaTypography(fontFamily),
        animate = true,
        content = content
    )
}
