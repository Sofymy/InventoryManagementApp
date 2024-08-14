package com.zenitech.imaapp.ui.common

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import com.zenitech.imaapp.ui.theme.Mint

@Composable
fun shimmerBrush(
    showShimmer: Boolean = true,
    targetValue: Float = 1000f
): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f)
        )

        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(800),
                repeatMode = RepeatMode.Reverse
            ),""
        )

        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}

@Composable
fun progressStateBrush(
    progressState: Int
): Brush {
    return when (progressState) {
        1 -> Brush.linearGradient(
            listOf(
                Mint,
                LocalCardColorsPalette.current.borderColor,
                LocalCardColorsPalette.current.borderColor,
                LocalCardColorsPalette.current.borderColor
            )
        )

        2 -> Brush.linearGradient(
            listOf(
                Mint,
                Mint,
                LocalCardColorsPalette.current.borderColor,
                LocalCardColorsPalette.current.borderColor
            )
        )

        3 -> Brush.linearGradient(
            listOf(
                Mint,
                Mint,
                Mint,
                LocalCardColorsPalette.current.borderColor
            )
        )

        else -> Brush.linearGradient(
            listOf(
                Mint,
                Mint,
                Mint,
                Mint
            )
        )
    }
}

val leftFade = Brush.horizontalGradient(0f to Color.Transparent, 0.1f to Color.Red)
val rightFade = Brush.horizontalGradient(0.9f to Color.Red, 1f to Color.Transparent)

