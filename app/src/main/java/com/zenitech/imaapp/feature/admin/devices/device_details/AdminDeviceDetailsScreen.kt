package com.zenitech.imaapp.feature.admin.devices.device_details

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material.icons.twotone.IosShare
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenitech.imaapp.feature.admin.devices.devices.generateExcelReport
import com.zenitech.imaapp.feature.admin.devices.devices.shareFile
import com.zenitech.imaapp.ui.common.CircularLoadingIndicator
import com.zenitech.imaapp.ui.common.pulsate
import com.zenitech.imaapp.ui.common.simpleVerticalScrollbar
import com.zenitech.imaapp.ui.model.DeviceSearchRequestUi
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import java.io.File

@Preview(showBackground = true)
@Composable
fun AdminDeviceDetailsListPreview() {
    AdminDeviceDetailsList(
        device = DeviceSearchRequestUi(),
        onClickExport = {}
    )
}


@Composable
fun AdminDeviceDetailsScreen(
    inventoryId: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AdminDeviceDetailsContent(
            inventoryId = inventoryId,
        )
    }
}

@Composable
fun AdminDeviceDetailsContent(
    inventoryId: String,
    viewModel: AdminDeviceDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            val fileName = "IMADeviceReport_${inventoryId}.xlsx"
            val externalStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            val file = File(externalStorageDir, fileName)

            if (!file.exists()) {
                file.createNewFile()
            }

            generateExcelReport(listOf((state as AdminDeviceDetailsState.Success).deviceDetailsList), file.absolutePath)
            shareFile(context, file)
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadDeviceDetails(inventoryId)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    when (state) {
        is AdminDeviceDetailsState.Error -> {
            Text((state as AdminDeviceDetailsState.Error).error.message.toString())
        }

        is AdminDeviceDetailsState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                CircularLoadingIndicator()
            }
        }

        is AdminDeviceDetailsState.Success -> {
            AdminDeviceDetailsList(
                onClickExport = {
                    when {
                        ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                            val fileName = "IMADeviceReport_${inventoryId}.xlsx"
                            val externalStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                            val file = File(externalStorageDir, fileName)

                            if (!file.exists()) {
                                file.createNewFile()
                            }

                            generateExcelReport(listOf((state as AdminDeviceDetailsState.Success).deviceDetailsList), file.absolutePath)
                            shareFile(context, file)
                        }
                        else -> {
                            permissionLauncher.launch(WRITE_EXTERNAL_STORAGE)
                        }
                    }
                },
                device = (state as AdminDeviceDetailsState.Success).deviceDetailsList,
            )
        }
    }
}

@Composable
fun AdminDeviceDetailsList(
    device: DeviceSearchRequestUi,
    onClickExport: () -> Unit,
) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .simpleVerticalScrollbar(
                listState,
                color = LocalCardColorsPalette.current.secondaryContentColor
            )
            .fillMaxWidth()
            .padding(horizontal = 0.dp),
        state = listState
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .fillMaxWidth()
            ) {
                AdminDeviceDetailsButton(icon = Icons.TwoTone.Delete) {
                    // TODO
                }
                AdminDeviceDetailsButton(icon = Icons.TwoTone.IosShare) {
                    onClickExport()
                }
            }
        }
        items(device.getAdminDeviceDetails()[0].details) { detail -> AdminDeviceDetailsItem(
            fieldName = detail.first, fieldValue = detail.second) }
        item { HorizontalDivider(thickness = 20.dp, color = MaterialTheme.colorScheme.background) }
        items(device.getAdminDeviceDetails()[1].details) { detail -> AdminDeviceDetailsItem(
            fieldName = detail.first, fieldValue = detail.second) }
        item { HorizontalDivider(thickness = 20.dp, color = MaterialTheme.colorScheme.background) }
        items(device.getAdminDeviceDetails()[2].details) { detail -> AdminDeviceDetailsItem(
            fieldName = detail.first, fieldValue = detail.second) }
        item { HorizontalDivider(thickness = 20.dp, color = MaterialTheme.colorScheme.background) }
        items(device.getAdminDeviceDetails()[3].details) { detail -> AdminDeviceDetailsItem(
            fieldName = detail.first, fieldValue = detail.second) }
        item { HorizontalDivider(thickness = 20.dp, color = MaterialTheme.colorScheme.background) }

    }
}

@Composable
fun AdminDeviceDetailsButton(
    icon: ImageVector,
    onClick: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 15.dp)
            .pulsate()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            }
            .border(
                width = 1.dp,
                color = LocalCardColorsPalette.current.borderColor,
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                color = LocalCardColorsPalette.current.containerColor,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            tint = MaterialTheme.colorScheme.onBackground,
            contentDescription = null,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun AdminDeviceDetailsItem(fieldName: String, fieldValue: Any) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(LocalCardColorsPalette.current.containerColor)
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .fillMaxWidth()
    ){
        Text(text = fieldName)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = fieldValue.toString())
            Spacer(modifier = Modifier.width(20.dp))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.TwoTone.Edit, 
                    contentDescription = null, 
                    tint = LocalCardColorsPalette.current.secondaryContentColor
                )
            }
        }
    }
    HorizontalDivider(
        color = LocalCardColorsPalette.current.borderColor
    )
}
