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
fun RequestTestDeviceTypeScreen(
    onNavigateToRequestTestDevice: (String?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        RequestTestDeviceTypeContent(
            onNavigateToRequestTestDevice = onNavigateToRequestTestDevice,
        )
    }
}

@Composable
fun RequestTestDeviceTypeContent(
    viewModel: RequestTestDeviceTypeViewModel = hiltViewModel(),
    onNavigateToRequestTestDevice: (String?) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadTestDeviceTypes()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    when (state) {
        is RequestTestDeviceTypeState.Error -> {
            Text((state as RequestTestDeviceTypeState.Error).error.message.toString())
        }
        is RequestTestDeviceTypeState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                CircularLoadingIndicator()
            }
        }
        is RequestTestDeviceTypeState.Success -> {
            RequestTestDeviceTypeList(
                list = (state as RequestTestDeviceTypeState.Success).testDeviceTypes,
                onNavigateToRequestTestDevice = onNavigateToRequestTestDevice,
            )
        }
    }
}

@Composable
fun RequestTestDeviceTypeList(
    list: List<String>,
    onNavigateToRequestTestDevice: (String?) -> Unit,
) {
    LazyColumn {
        items(list){
            RequestTestDeviceTypeItem(text = it, onClick = { onNavigateToRequestTestDevice(
                it,
            ) })
        }
    }
}

@Composable
fun RequestTestDeviceTypeItem(
    text: String, 
    onClick: () -> Unit
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
