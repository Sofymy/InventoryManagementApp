package com.zenitech.imaapp.feature.request_test_device

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenitech.imaapp.ui.common.CircularLoadingIndicator
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette

@Composable
fun RequestTestDeviceManufacturerScreen(
    onNavigateToRequestTestDevice: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        RequestTestDeviceManufacturerContent(
            onNavigateToRequestTestDevice = onNavigateToRequestTestDevice
        )
    }
}

@Composable
fun RequestTestDeviceManufacturerContent(
    viewModel: RequestTestDeviceManufacturerViewModel = hiltViewModel(),
    onNavigateToRequestTestDevice: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadTestDeviceManufacturers()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    when (state) {
        is RequestTestDeviceManufacturerState.Error -> {
            Text((state as RequestTestDeviceManufacturerState.Error).error.message.toString())
        }
        is RequestTestDeviceManufacturerState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                CircularLoadingIndicator()
            }
        }
        is RequestTestDeviceManufacturerState.Success -> {
            RequestTestDeviceManufacturerList(
                list = (state as RequestTestDeviceManufacturerState.Success).testDeviceManufacturers,
                onNavigateToRequestTestDevice = onNavigateToRequestTestDevice,
            )
        }
    }
}

@Composable
fun RequestTestDeviceManufacturerList(
    list: List<String>,
    onNavigateToRequestTestDevice: (String) -> Unit
) {
    LazyColumn {
        items(list){
            RequestTestManufacturerTypeItem(text = it) {
                onNavigateToRequestTestDevice(it)
            }
        }
    }
}

@Composable
fun RequestTestManufacturerTypeItem(
    text: String, onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(LocalCardColorsPalette.current.containerColor)
            .clickable {
                onClick()
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 20.dp)
                .fillMaxWidth()
        ) {
            Text(text = text)
        }
        HorizontalDivider(color = LocalCardColorsPalette.current.borderColor)
    }
}
