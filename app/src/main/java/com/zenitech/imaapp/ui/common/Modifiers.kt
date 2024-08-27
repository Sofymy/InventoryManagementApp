package com.zenitech.imaapp.ui.common

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


enum class ComponentState { Pressed, Released }

@Composable
fun Modifier.pulsate(): Modifier {
    var toState by remember { mutableStateOf(ComponentState.Released) }
    val transition: Transition<ComponentState> = updateTransition(targetState = toState, label = "")

    val scaleX: Float by transition.animateFloat(
        transitionSpec = { spring(stiffness = Spring.StiffnessMedium) }, label = ""
    ) { state ->
        if (state == ComponentState.Pressed) 0.95f else 1f
    }
    val scaleY: Float by transition.animateFloat(
        transitionSpec = { spring(stiffness = Spring.StiffnessMedium) }, label = ""
    ) { state ->
        if (state == ComponentState.Pressed) 0.95f else 1f
    }

    return this then Modifier.graphicsLayer{
        this.scaleX = scaleX
        this.scaleY = scaleY
    }
        .pointerInput(toState) {
            awaitPointerEventScope {
                if (toState == ComponentState.Pressed) {
                    waitForUpOrCancellation()
                    toState = ComponentState.Released
                } else {
                    awaitFirstDown(false)
                    toState = ComponentState.Pressed
                }
            }
        }
}

@Composable
fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp = 5.dp,
    color: Color
): Modifier {
    val targetAlpha = if (state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration),
        label = ""
    )

    return drawWithContent {
        drawContent()

        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index
        val needDrawScrollbar = state.isScrollInProgress || alpha > 0.0f

        if (needDrawScrollbar && firstVisibleElementIndex != null) {
            val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
            val scrollbarOffsetY = firstVisibleElementIndex * elementHeight
            val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight

            drawRoundRect(
                cornerRadius = CornerRadius(50f, 50f),
                color = color,
                topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                size = Size(width.toPx(), scrollbarHeight),
                alpha = alpha
            )
        }
    }
}

@Composable
fun Modifier.conditional(condition : Boolean, modifier : @Composable Modifier.() -> Modifier) : Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}

fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }