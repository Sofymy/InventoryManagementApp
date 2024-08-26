package com.zenitech.imaapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zenitech.imaapp.ui.theme.IMAAppTheme
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette


@Composable
@Preview
fun RowWithBorderPreview(){
    IMAAppTheme {
        Column {
            RowWithBorder(
                content = {
                    Text(text = "Row with border Preview")
                }
            )
        }
    }
}

@Composable
fun RowWithBorder(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(15.dp),
    isError: Boolean = false,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .border(
                1.dp,
                if (isError) MaterialTheme.colorScheme.primary else LocalCardColorsPalette.current.borderColor,
                shape
            )
            .background(
                LocalCardColorsPalette.current.containerColor,
                shape
            )
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}