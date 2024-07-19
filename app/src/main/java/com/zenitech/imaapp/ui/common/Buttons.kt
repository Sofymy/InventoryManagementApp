package com.zenitech.imaapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zenitech.imaapp.ui.theme.EerieBlack
import com.zenitech.imaapp.ui.theme.EerieBlack40Black
import com.zenitech.imaapp.ui.theme.EerieBlack40BlackTransparent
import com.zenitech.imaapp.ui.theme.EerieBlack40White
import com.zenitech.imaapp.ui.theme.LocalButtonColorsPalette
import com.zenitech.imaapp.ui.theme.TeaGreen
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = onClick
            )
            .fillMaxWidth()
            .pulsate()
            .border(1.dp, LocalButtonColorsPalette.current.borderColor, RoundedCornerShape(15.dp))
            .background(LocalButtonColorsPalette.current.containerColor, RoundedCornerShape(15.dp))
            .padding(15.dp)
        ,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        content = { content() }
    )
}

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    text: String
) {
    Button(onClick = { onClick() }) {
        Text(text)
    }
}