package com.zenitech.imaapp.ui.common

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class TriangleEdgeShape() : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {
            moveTo(size.width / 2f, 0f)  // position pencil on top center
            lineTo(size.width, size.height)  // draw line to bottom right
            lineTo(0f, size.height)  // draw line to bottom left
            close()  // close the path - draws a line to the top center
        }
        return Outline.Generic(path = trianglePath)
    }
}