package com.lumina.data.settings

import com.lumina.core.common.AppDefaults.DEFAULT_THEME
import com.lumina.core.common.AppTheme

fun appThemeFromStorage(value: String?): AppTheme {
    if (value == null) return AppTheme.valueOf(DEFAULT_THEME)

    return try {
        AppTheme.valueOf(value)
    } catch (e: IllegalArgumentException) {
        AppTheme.valueOf(DEFAULT_THEME)
    }
}
