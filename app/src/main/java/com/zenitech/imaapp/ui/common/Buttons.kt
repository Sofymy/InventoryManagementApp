package com.zenitech.imaapp.ui.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.zenitech.imaapp.ui.theme.LocalButtonColorsPalette

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

@Composable
fun ScrollToTopButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp, end = 30.dp)
        ,
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(onClick = { onClick() },
            contentPadding = PaddingValues(13.dp),
            shape = CircleShape,
            modifier = Modifier
                .shadow(10.dp, shape = CircleShape)
                .size(65.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.TwoTone.KeyboardArrowUp,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
fun RoundedButton(
    onClick: () -> Unit,
    iconImageVector: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

    val iconColor: Color by animateColorAsState(
        if (enabled) MaterialTheme.colorScheme.onBackground else LocalButtonColorsPalette.current.roundedButtonColorDisabledContentColor,
        label = "",
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
    )

    Row(
        modifier = modifier
            .clickable(
                enabled = enabled,
                indication = null,
                interactionSource = interactionSource,
                onClick = onClick
            )
            .pulsate()
            .border(1.dp, LocalButtonColorsPalette.current.roundedButtonBorderColor, CircleShape)
            .background(LocalButtonColorsPalette.current.roundedButtonColor, CircleShape)
            .padding(15.dp)
        ,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Icon(
                imageVector = iconImageVector,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(40.dp)
            )
        }
    )

}