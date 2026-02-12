package com.lumina.core.ui.components.text

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.sp
import com.lumina.core.ui.theme.ContentColor

private object AutoResizingTextDefaults {
    const val SHRINK_FACTOR = 0.9f

    val MinFontSize = 10.sp
    val FallbackFontSize = 16.sp
}

@Composable
fun AutoResizingText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    minFontSize: TextUnit = AutoResizingTextDefaults.MinFontSize,
    maxFontSize: TextUnit = style.fontSize,
    softWrap: Boolean = false,
    maxLines: Int = 1,
    color: Color = ContentColor,
    fontFamily: FontFamily? = MaterialTheme.typography.bodyMedium.fontFamily,
    textAlign: TextAlign? = null
) {
    BoxWithConstraints(modifier = modifier) {
        val updatedText by rememberUpdatedState(text)

        val density = LocalDensity.current
        val availableWidthPx = with(density) { maxWidth.toPx() }
        val textMeasurer = rememberTextMeasurer()

        var currentFontSize by remember(updatedText, style, minFontSize, maxFontSize) {
            mutableStateOf(maxFontSize)
        }

        LaunchedEffect(
            updatedText,
            style,
            minFontSize,
            maxFontSize,
            availableWidthPx,
            maxLines,
            softWrap
        ) {
            if (availableWidthPx <= 0) return@LaunchedEffect

            var tempFontSize = maxFontSize
            if (tempFontSize.isUnspecified || tempFontSize.value <= 0) {
                tempFontSize = AutoResizingTextDefaults.FallbackFontSize
            }

            val textLayoutResult = textMeasurer.measure(
                text = updatedText,
                style = style.copy(fontSize = tempFontSize),
                overflow = TextOverflow.Clip,
                softWrap = softWrap,
                maxLines = maxLines,
                constraints = Constraints(maxWidth = availableWidthPx.toInt())
            )

            if (textLayoutResult.didOverflowWidth) {
                var shrunkFontSize = tempFontSize

                while (shrunkFontSize > minFontSize) {
                    shrunkFontSize = (shrunkFontSize.value * AutoResizingTextDefaults.SHRINK_FACTOR).sp

                    if (shrunkFontSize < minFontSize) {
                        shrunkFontSize = minFontSize
                    }

                    val shrunkLayoutResult = textMeasurer.measure(
                        text = updatedText,
                        style = style.copy(fontSize = shrunkFontSize),
                        overflow = TextOverflow.Clip,
                        softWrap = softWrap,
                        maxLines = maxLines,
                        constraints = Constraints(maxWidth = availableWidthPx.toInt())
                    )

                    if (!shrunkLayoutResult.didOverflowWidth) {
                        tempFontSize = shrunkFontSize
                        break
                    }

                    if (shrunkFontSize == minFontSize) {
                        tempFontSize = minFontSize
                        break
                    }
                }
            }

            if (currentFontSize != tempFontSize) {
                currentFontSize = tempFontSize
            }
        }

        Text(
            text = updatedText,
            style = style.copy(fontSize = currentFontSize),
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            softWrap = softWrap,
            color = color,
            fontFamily = fontFamily,
            textAlign = textAlign
        )
    }
}
