import android.util.Log
import com.lumina.core.ui.theme.AppTheme
import com.geecee.escapelauncher.R
import com.lumina.core.common.AppDefaults.DEFAULT_THEME

private const val TAG = "AppTheme"

fun AppTheme.displayNameRes(): Int = when (this) {
    AppTheme.DARK -> R.string.dark
    AppTheme.LIGHT -> R.string.light
    AppTheme.PITCH_DARK -> R.string.pitch_black

    AppTheme.LIGHT_RED -> R.string.light_red
    AppTheme.DARK_RED -> R.string.dark_red

    AppTheme.LIGHT_GREEN -> R.string.light_green
    AppTheme.DARK_GREEN -> R.string.dark_green

    AppTheme.LIGHT_BLUE -> R.string.light_blue
    AppTheme.DARK_BLUE -> R.string.dark_blue

    AppTheme.LIGHT_YELLOW -> R.string.light_yellow
    AppTheme.DARK_YELLOW -> R.string.dark_yellow

    AppTheme.OFF_LIGHT -> R.string.off_white
    AppTheme.SYSTEM -> R.string.system
}

fun AppTheme.toStorageValue(): String = name

fun appThemeFromStorage(value: String?): AppTheme {
    if (value == null) return AppTheme.valueOf(DEFAULT_THEME)

    return try {
        AppTheme.valueOf(value)
    } catch (e:IllegalArgumentException) {
        Log.w(TAG, "Invalid theme in store: $value. Defaulting to DARK")
        AppTheme.valueOf(DEFAULT_THEME)
    }
}
