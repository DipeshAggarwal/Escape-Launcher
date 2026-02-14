package com.lumina.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private fun textStyle(
    fontFamily: FontFamily,
    fontSizeValue: Int,
    fontWeight: FontWeight = FontWeight.Light
) = TextStyle(
    fontFamily = fontFamily,
    fontSize = fontSizeValue.sp,
    fontWeight = fontWeight,
    lineHeight = (fontSizeValue * TypographyTokens.LINE_HEIGHT_RATIO).sp,
    letterSpacing = TypographyTokens.LINE_SPACING.sp
)

fun RiktaTypography(fontFamily: FontFamily) = Typography(
    headlineLarge = textStyle(fontFamily, TypographyTokens.HEADLINE_LARGE_SIZE),
    headlineMedium = textStyle(fontFamily, TypographyTokens.HEADLINE_LARGE_SIZE - TypographyTokens.SCALE_STEP),
    headlineSmall = textStyle(fontFamily, TypographyTokens.HEADLINE_LARGE_SIZE - (TypographyTokens.SCALE_STEP * 2)),

    titleLarge = textStyle(fontFamily, TypographyTokens.TITLE_LARGE_SIZE),
    titleMedium = textStyle(fontFamily, TypographyTokens.TITLE_LARGE_SIZE - TypographyTokens.SCALE_STEP),
    titleSmall = textStyle(fontFamily, TypographyTokens.TITLE_LARGE_SIZE - (TypographyTokens.SCALE_STEP * 2)),

    bodyLarge = textStyle(fontFamily, TypographyTokens.BODY_LARGE_SIZE, FontWeight.Normal),
    bodyMedium = textStyle(fontFamily, TypographyTokens.BODY_LARGE_SIZE - TypographyTokens.SCALE_STEP, FontWeight.Normal),
    bodySmall = textStyle(fontFamily, TypographyTokens.BODY_LARGE_SIZE - (TypographyTokens.SCALE_STEP * 2), FontWeight.Normal),
)
