package com.zenitech.imaapp.feature.request_test_device

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Check
import androidx.compose.material.icons.twotone.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun RequestTestDeviceSuccessfulScreen(
    onNavigateToRequestTestDevice: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        RequestTestDeviceSuccessfulContent(
            onNavigateToRequestTestDevice = onNavigateToRequestTestDevice
        )
    }
}

@Composable
fun RequestTestDeviceSuccessfulContent(
    onNavigateToRequestTestDevice: () -> Unit
) {
    val showCheckmark = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1000)
        showCheckmark.value = true
    }

    AnimatedCheckmark(showCheckmark.value, onNavigateToRequestTestDevice)
}

@Composable
fun AnimatedCheckmark(
    value: Boolean,
    onNavigateToRequestTestDevice: () -> Unit
) {
    val redirectSeconds = remember { mutableIntStateOf(10) }
    val iconSize: Dp by animateDpAsState(
        if (value) 200.dp else 70.dp, label = "",
        animationSpec = tween(10000, delayMillis = 1000)
    )

    LaunchedEffect(Unit) {
        while (redirectSeconds.intValue > 0) {
            delay(1000)
            redirectSeconds.intValue -= 1
        }
        // Ensure the navigation call is made on the main thread
        withContext(Dispatchers.Main) {
            onNavigateToRequestTestDevice()
        }
    }

    Box {
        Icon(
            imageVector = Icons.TwoTone.Circle,
            contentDescription = null,
            modifier = Modifier.size(iconSize),
            tint = LocalCardColorsPalette.current.borderColor
        )
        AnimatedVisibility(
            visible = value,
            enter = fadeIn(tween(1000))
        ) {
            Icon(
                imageVector = Icons.TwoTone.Check,
                contentDescription = null,
                modifier = Modifier.size(iconSize),
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }

    Spacer(modifier = Modifier.height(30.dp))
    Text(
        text = "Successful test device request!",
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(6.dp))
    Text(
        text = "You will be redirected in ${redirectSeconds.intValue} seconds",
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(70.dp))
}
