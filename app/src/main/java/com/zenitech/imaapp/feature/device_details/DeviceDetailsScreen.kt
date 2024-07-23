package com.zenitech.imaapp.feature.device_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DeviceDetailsScreen(inventoryNumber: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        DeviceDetailsContent(
            inventoryNumber
        )
    }
}

@Composable
fun DeviceDetailsContent(inventoryNumber: String) {
    Text(inventoryNumber)
}