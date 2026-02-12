package com.lumina.core.ui.components.settings

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lumina.core.ui.components.text.AutoResizingText
import com.lumina.core.ui.theme.ContentColor

private object SettingsHeaderDefaults {
    val RowHeight = 70.dp
    val BackIconSize = 48.dp
    val BackIconSpacing = 5.dp
    val LargeTopPadding = 120.dp
    val BottomPadding = 8.dp
}

@Composable
fun SettingsHeader(
    goBack: () -> Unit,
    title: String,
    hideBack: Boolean = false,
    color: Color = ContentColor,
    padding: Boolean = true
) {
    Row(
        modifier = Modifier
            .combinedClickable(onClick = { goBack() })
            .padding(
                top = if (padding) SettingsHeaderDefaults.LargeTopPadding else 0.dp,
                bottom = SettingsHeaderDefaults.BottomPadding
            )
            .height(SettingsHeaderDefaults.RowHeight)
    ) {
        if (!hideBack) {
            Icon(
                Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Go Back",
                tint = color,
                modifier = Modifier
                    .size(SettingsHeaderDefaults.BackIconSize)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(SettingsHeaderDefaults.BackIconSpacing))
        }
        AutoResizingText(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = color,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}
