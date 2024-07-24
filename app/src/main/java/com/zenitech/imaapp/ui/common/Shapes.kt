package com.zenitech.imaapp.ui.common

import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


class TrapezoidShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width * 1.2f, size.height)
            lineTo(size.width * 0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}

fun RoundedHexagonShape(cornerRadius: Float): GenericShape {
    return GenericShape { size, _ ->
        val path = Path()
        val angle = 60.0
        val radius = size.minDimension / 2
        val centerX = size.width / 2
        val centerY = size.height / 2

        for (i in 0..5) {
            val theta = Math.toRadians(angle * i - 30)
            val x = centerX + radius * cos(theta)
            val y = centerY + radius * sin(theta)
            if (i == 0) {
                path.moveTo(x.toFloat(), y.toFloat())
            } else {
                path.lineTo(x.toFloat(), y.toFloat())
            }
        }
        path.close()
        path.addRoundRect(
            RoundRect(
                left = 0f,
                top = 0f,
                right = size.width,
                bottom = size.height,
                radiusX = cornerRadius,
                radiusY = cornerRadius
            )
        )
        path.close()
    }
}

@Composable
fun RotatedHexagonBackground(
    modifier: Modifier = Modifier,
    color: Color,
    cornerRadius: Float,
    rotationAngle: Float
) {
    Canvas(modifier = modifier) {
        val path = Path()
        val radius = size.minDimension / 2
        val centerX = size.width / 2
        val centerY = size.height / 2
        val hexagonPoints = List(6) { i ->
            val angle = Math.toRadians((60 * i).toDouble() - 30)
            Pair(centerX + radius * cos(angle), centerY + radius * sin(angle))
        }

        // Draw hexagon with rounded corners
        for (i in hexagonPoints.indices) {
            val start = hexagonPoints[i]
            val end = hexagonPoints[(i + 1) % hexagonPoints.size]

            if (i == 0) {
                path.moveTo(start.first.toFloat(), start.second.toFloat())
            } else {
                // Draw line segment
                path.lineTo(start.first.toFloat(), start.second.toFloat())
            }

            // Draw rounded corner
            val next = hexagonPoints[(i + 2) % hexagonPoints.size]
            val midX = (start.first + end.first) / 2
            val midY = (start.second + end.second) / 2
            val angle = Math.atan2(end.second - start.second, end.first - start.first).toFloat()

            path.addArc(
                Rect(left = (end.first - cornerRadius).toFloat(),
                    top = (end.second - cornerRadius).toFloat(),
                    right = (end.first + cornerRadius).toFloat(),
                    bottom = (end.second + cornerRadius).toFloat(),),
                        startAngleDegrees = angle * 180 / PI.toFloat() - 90,
                sweepAngleDegrees = 180f
            )
        }

        path.close()

        rotate(rotationAngle) {
            drawPath(path = path, color = color)
        }
    }
}