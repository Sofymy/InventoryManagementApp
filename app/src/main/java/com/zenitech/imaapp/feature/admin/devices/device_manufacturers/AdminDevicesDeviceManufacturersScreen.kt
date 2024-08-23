package com.zenitech.imaapp.feature.admin.devices.device_manufacturers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenitech.imaapp.feature.admin.devices.add_device.AdminDevicesAddDeviceList
import com.zenitech.imaapp.feature.admin.devices.add_device.AdminDevicesAddDeviceSearchField
import com.zenitech.imaapp.ui.common.CircularLoadingIndicator

@Composable
fun AdminDevicesDeviceManufacturersScreen(
    onNavigateToAdminAddDevice: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AdminDevicesDeviceManufacturersContent(
            onNavigateToAdminAddDevice = onNavigateToAdminAddDevice
        )
    }
}

@Composable
fun AdminDevicesDeviceManufacturersContent(
    viewModel: AdminDevicesDeviceManufacturersViewModel = hiltViewModel(),
    onNavigateToAdminAddDevice: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadDeviceManufacturers()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    when (state) {
        is AdminDevicesDeviceManufacturersState.Error -> {
            Text((state as AdminDevicesDeviceManufacturersState.Error).error.message.toString())
        }
        is AdminDevicesDeviceManufacturersState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                CircularLoadingIndicator()
            }
        }
        is AdminDevicesDeviceManufacturersState.Success -> {
            AdminDevicesDeviceManufacturers(
                list = (state as AdminDevicesDeviceManufacturersState.Success).deviceManufacturers,
                onNavigateToAdminAddDevice = onNavigateToAdminAddDevice,
            )
        }
    }
}

@Composable
fun AdminDevicesDeviceManufacturers(
    list: List<String>,
    onNavigateToAdminAddDevice: (String) -> Unit
) {
    val filteredList = remember { mutableStateOf(list) }
    val filterQuery = remember { mutableStateOf("") }

    Column {
        AdminDevicesAddDeviceSearchField(
            filterQuery = filterQuery.value,
            onFilterQueryChanged = { newQuery ->
                filterQuery.value = newQuery
                filteredList.value = list.filter { item ->
                    item.contains(newQuery, ignoreCase = true)
                }
            },
            onNavigateToAdminAddDevice = onNavigateToAdminAddDevice
        )
        AdminDevicesAddDeviceList(
            filteredList = filteredList.value,
            onNavigateToAdminAddDevice = onNavigateToAdminAddDevice
        )
    }
}
