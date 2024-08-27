package com.zenitech.imaapp.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.twotone.AddComment
import androidx.compose.material.icons.twotone.AdminPanelSettings
import androidx.compose.material.icons.twotone.Devices
import androidx.compose.material.icons.twotone.QrCode
import androidx.compose.ui.graphics.vector.ImageVector
import com.zenitech.imaapp.navigation.Screen
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
sealed class BottomNavigationBarItem(
    val screen: Screen,
    val selectedIcon: @Contextual ImageVector,
    val unselectedIcon: @Contextual ImageVector
) {
    @Serializable
    data object MyDevices : BottomNavigationBarItem(
        screen = Screen.MyDevices,
        selectedIcon = Icons.Filled.Devices,
        unselectedIcon = Icons.TwoTone.Devices
    )

    @Serializable
    data object QRReader : BottomNavigationBarItem(
        screen = Screen.QRReader,
        selectedIcon = Icons.Filled.QrCode,
        unselectedIcon = Icons.TwoTone.QrCode
    )

    @Serializable
    data object Request : BottomNavigationBarItem(
        screen = Screen.RequestTestDevice(),
        selectedIcon = Icons.Filled.AddComment,
        unselectedIcon = Icons.TwoTone.AddComment
    )

    @Serializable
    data object Admin : BottomNavigationBarItem(
        screen = Screen.Admin,
        selectedIcon = Icons.Filled.AdminPanelSettings,
        unselectedIcon = Icons.TwoTone.AdminPanelSettings
    )
}