package com.zenitech.imaapp.feature.admin.devices.add_device_successful

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.theme.IMAAppTheme
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Preview(showBackground = true)
@Composable
fun AdminDevicesAddDeviceSuccessfulScreenPreview() {
    IMAAppTheme {
        AdminDevicesAddDeviceSuccessfulScreen(
            onNavigateToAdminDevices = { }
        )
    }
}

@Composable
fun AdminDevicesAddDeviceSuccessfulScreen(
    onNavigateToAdminDevices: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        AdminDevicesAddDeviceSuccessfulContent(
            onNavigateToAdminDevices = onNavigateToAdminDevices
        )
    }
}

@Composable
fun AdminDevicesAddDeviceSuccessfulContent(
    onNavigateToAdminDevices: () -> Unit
) {
    val showCheckmark = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1000)
        showCheckmark.value = true
    }

    AnimatedCheckmark(showCheckmark.value, onNavigateToAdminDevices)
}

@Composable
fun AnimatedCheckmark(
    value: Boolean,
    onNavigateToAdminDevices: () -> Unit
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

        withContext(Dispatchers.Main) {
            onNavigateToAdminDevices()
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
        text = stringResource(R.string.successful_device_creation),
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(6.dp))
    Text(
        text = stringResource(R.string.you_will_be_redirected_in_seconds, redirectSeconds.intValue),
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(70.dp))
}
