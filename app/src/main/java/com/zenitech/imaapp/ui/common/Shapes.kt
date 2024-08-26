package com.zenitech.imaapp.ui.common

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection


class TrapezoidShapeUpper : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(
        Path().apply {
            moveTo(size.width * 0f, 0f) // Top left
            lineTo(size.width * 0.75f, 0f) // Top right
            lineTo(size.width, size.height) // Bottom right
            lineTo(0f, size.height) // Bottom left
            close()
        }
    )
}

class TrapezoidShapeLower(radius: Float) : Shape {

    private val _radius = radius
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(
        Path().apply {
            moveTo(size.width * 0f, 0f) // Top left
            lineTo(size.width, 0f) // Top right
            lineTo(size.width * _radius, size.height * 1f) // Bottom right
            lineTo(0f, size.height) // Bottom left
            close()
        }
    )
}