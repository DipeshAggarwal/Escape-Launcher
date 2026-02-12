package com.lumina.core.ui.theme

import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable

object LuminaCardDefaults {

    @Composable
    fun colors(): CardColors =
        CardDefaults.cardColors(
            containerColor = CardContainerColor,
            contentColor = ContentColor
        )

    @Composable
    fun errorColors(): CardColors =
        CardDefaults.cardColors(
            containerColor = ErrorContainerColor,
            contentColor = ErrorContentColor
        )
}
