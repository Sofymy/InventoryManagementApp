package com.zenitech.imaapp.feature.admin.devices.add_device

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.common.progressStateBrush
import com.zenitech.imaapp.ui.theme.IMAAppTheme
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import com.zenitech.imaapp.ui.theme.Mint


@Composable
@Preview(showBackground = true)
fun PreviewAdminDevicesAddDeviceProgressBar() {
    IMAAppTheme {
        AdminDevicesAddDeviceProgressBar(currentPageNumber = 2)
    }
}


@Composable
fun AdminDevicesAddDeviceProgressBar(
    currentPageNumber: Int
) {
    val progressStates = listOf(
        stringResource(R.string.general),
        stringResource(R.string.dates),
        stringResource(R.string.status),
        stringResource(R.string.location)
    )

    AdminDevicesAddDeviceProgressBarStates(
        currentPageNumber = currentPageNumber,
        progressStates = progressStates
    )

    HorizontalDivider(
        color = LocalCardColorsPalette.current.borderColor
    )

}

@Composable
fun AdminDevicesAddDeviceProgressBarStates(
    currentPageNumber: Int,
    progressStates: List<String>
) {
    val iconSize = 30.dp

    Column(modifier = Modifier.padding(20.dp)) {
        Box {
            AnimatedContent(
                modifier = Modifier.align(Alignment.TopCenter),
                targetState = currentPageNumber,
                transitionSpec = {
                    fadeIn().togetherWith(fadeOut())
                }, label = ""
            ) {
                AdminDevicesAddDeviceProgressBarIndicator(currentPageNumber = it, iconSize = iconSize)
            }
            AdminDevicesAddDeviceProgressBarIcons(
                currentPageNumber = currentPageNumber,
                progressStates = progressStates,
                iconSize = iconSize
            )
        }
    }
}

@Composable
fun AdminDevicesAddDeviceProgressBarIndicator(currentPageNumber: Int, iconSize: Dp) {
    val progressBarIndicatorHeight = 4.dp

    Box(
        modifier = Modifier
            .padding(
                start = iconSize / 2,
                end = iconSize / 2,
                top = iconSize / 2 - progressBarIndicatorHeight / 2
            )
            .height(progressBarIndicatorHeight)
            .fillMaxWidth()
            .background(progressStateBrush(progressState = currentPageNumber + 1))
    )
}

@Composable
fun AdminDevicesAddDeviceProgressBarIcons(
    iconSize: Dp,
    currentPageNumber: Int,
    progressStates: List<String>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        progressStates.forEachIndexed { index, progressState ->
            val progressStateColor by animateColorAsState(
                targetValue = if (currentPageNumber >= index) Mint else LocalCardColorsPalette.current.borderColor,
                label = ""
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Rounded.CheckCircle,
                        tint = progressStateColor,
                        contentDescription = null,
                        modifier = Modifier
                            .background(progressStateColor, CircleShape)
                            .size(iconSize)
                    )
                    this@Row.AnimatedVisibility(visible = currentPageNumber > index) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            tint = Color.White,
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = progressState,
                    fontSize = 10.sp,
                    color = progressStateColor
                )
            }
        }
    }
}
