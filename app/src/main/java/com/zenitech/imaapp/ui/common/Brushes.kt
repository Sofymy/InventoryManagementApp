package com.zenitech.imaapp.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import com.zenitech.imaapp.ui.theme.Mint

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

