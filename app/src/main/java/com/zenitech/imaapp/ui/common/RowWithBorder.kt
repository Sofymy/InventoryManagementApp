package com.zenitech.imaapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette

@Composable
fun RowWithBorder(
    modifier: Modifier = Modifier,
    isError: Boolean,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, if(isError) MaterialTheme.colorScheme.primary else LocalCardColorsPalette.current.borderColor, RoundedCornerShape(15.dp))
            .background(
                LocalCardColorsPalette.current.containerColor,
                RoundedCornerShape(15.dp)
            )
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}