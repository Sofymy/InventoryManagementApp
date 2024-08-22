package com.zenitech.imaapp.feature.admin.devices.device_details

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Environment
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material.icons.twotone.IosShare
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenitech.imaapp.R
import com.zenitech.imaapp.feature.admin.devices.devices.generateExcelReport
import com.zenitech.imaapp.feature.admin.devices.devices.shareFile
import com.zenitech.imaapp.ui.common.CircularLoadingIndicator
import com.zenitech.imaapp.ui.common.conditional
import com.zenitech.imaapp.ui.common.pulsate
import com.zenitech.imaapp.ui.common.simpleVerticalScrollbar
import com.zenitech.imaapp.ui.model.DeviceAssetUi
import com.zenitech.imaapp.ui.model.DeviceConditionUi
import com.zenitech.imaapp.ui.model.DeviceSearchRequestUi
import com.zenitech.imaapp.ui.model.DeviceStatusUi
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import java.io.File

@Preview(showBackground = true)
@Composable
fun AdminDeviceDetailsListPreview() {
    AdminDeviceDetailsList(
        device = DeviceSearchRequestUi(),
        onClickExport = { },
        onClickDelete = { },
        onClickSave = { },
        onNavigateToAdminDeviceManufacturers = { },
        onNavigateToAdminDeviceSites = { },
        onNavigateToAdminDeviceAssets = { },
        onNavigateToAdminDeviceTypes = { },
        site = "BME",
        manufacturer = "Dell",
        type = "Inspiron",
        asset = "Laptop"
    )
}

@Composable
fun AdminDeviceDetailsScreen(
    inventoryId: String,
    onNavigateToAdminDeviceTypes: () -> Unit,
    onNavigateToAdminDeviceManufacturers: () -> Unit,
    onNavigateToAdminDeviceAssets: () -> Unit,
    onNavigateToAdminDeviceSites: () -> Unit,
    site: String?,
    manufacturer: String?,
    type: String?,
    asset: String?
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AdminDeviceDetailsContent(
            inventoryId = inventoryId,
            onNavigateToAdminDeviceSites = onNavigateToAdminDeviceSites,
            onNavigateToAdminDeviceAssets = onNavigateToAdminDeviceAssets,
            onNavigateToAdminDeviceTypes = onNavigateToAdminDeviceTypes,
            onNavigateToAdminDeviceManufacturers = onNavigateToAdminDeviceManufacturers,
            site = site,
            manufacturer = manufacturer,
            type = type,
            asset = asset
        )
    }
}

@Composable
fun AdminDeviceDetailsContent(
    inventoryId: String,
    viewModel: AdminDeviceDetailsViewModel = hiltViewModel(),
    onNavigateToAdminDeviceTypes: () -> Unit,
    onNavigateToAdminDeviceManufacturers: () -> Unit,
    onNavigateToAdminDeviceAssets: () -> Unit,
    onNavigateToAdminDeviceSites: () -> Unit,
    site: String?,
    manufacturer: String?,
    type: String?,
    asset: String?
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) generateAndShareReport(inventoryId, state, context)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) viewModel.loadDeviceDetails(inventoryId)
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    when (state) {
        is AdminDeviceDetailsState.Error -> Text(text = (state as AdminDeviceDetailsState.Error).error.message.toString())
        is AdminDeviceDetailsState.Loading -> Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) { CircularLoadingIndicator() }
        is AdminDeviceDetailsState.Success -> AdminDeviceDetailsList(
            onClickSave = {
                viewModel.onEvent(AdminDevicesUserEvent.SaveModifications(it))
            },
            onClickDelete = {
                viewModel.onEvent(AdminDevicesUserEvent.DeleteDevice(it)) },
            onClickExport = {
                if (ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED) generateAndShareReport(inventoryId, state, context)
                else permissionLauncher.launch(WRITE_EXTERNAL_STORAGE)
            },
            device = (state as AdminDeviceDetailsState.Success).device,
            onNavigateToAdminDeviceSites = onNavigateToAdminDeviceSites,
            onNavigateToAdminDeviceAssets = onNavigateToAdminDeviceAssets,
            onNavigateToAdminDeviceTypes = onNavigateToAdminDeviceTypes,
            onNavigateToAdminDeviceManufacturers = onNavigateToAdminDeviceManufacturers,
            site = site,
            manufacturer = manufacturer,
            type = type,
            asset = asset
        )
    }
}

private fun generateAndShareReport(inventoryId: String, state: AdminDeviceDetailsState, context: Context) {
    val fileName = "IMADeviceReport_${inventoryId}.xlsx"
    val externalStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
    val file = File(externalStorageDir, fileName)
    if (!file.exists()) file.createNewFile()
    generateExcelReport(listOf((state as AdminDeviceDetailsState.Success).device), file.absolutePath)
    shareFile(context, file)
}

@Composable
fun AdminDeviceDetailsList(
    device: DeviceSearchRequestUi,
    onClickSave: (DeviceSearchRequestUi) -> Unit,
    onClickExport: () -> Unit,
    onClickDelete: (DeviceSearchRequestUi) -> Unit,
    onNavigateToAdminDeviceTypes: () -> Unit,
    onNavigateToAdminDeviceManufacturers: () -> Unit,
    onNavigateToAdminDeviceAssets: () -> Unit,
    onNavigateToAdminDeviceSites: () -> Unit,
    site: String?,
    manufacturer: String?,
    type: String?,
    asset: String?
) {
    val listState = rememberLazyListState()
    val isEditable = rememberSaveable { mutableStateOf(false) }
    val editableDevice = rememberSaveable { mutableStateOf(device) }

    Box(contentAlignment = Alignment.BottomEnd) {
        LazyColumn(
            modifier = Modifier
                .simpleVerticalScrollbar(
                    listState,
                    color = LocalCardColorsPalette.current.secondaryContentColor
                )
                .fillMaxWidth(),
            state = listState
        ) {
            item { AdminDeviceDetailsButtonsRow(device, onClickDelete, onClickExport) {
                isEditable.value = !isEditable.value }
            }
            item {
                AdminDeviceDetailsItems(
                    device = device,
                    site = site,
                    manufacturer = manufacturer,
                    type = type,
                    asset = asset,
                    isEditable = isEditable.value,
                    onNavigateToAdminDeviceTypes = onNavigateToAdminDeviceTypes,
                    onNavigateToAdminDeviceManufacturers = onNavigateToAdminDeviceManufacturers,
                    onNavigateToAdminDeviceAssets = onNavigateToAdminDeviceAssets,
                    onNavigateToAdminDeviceSites = onNavigateToAdminDeviceSites,
                    onEditDevice = {
                        editableDevice.value = it
                    }
                )
            }
        }
        AnimatedVisibility(visible = isEditable.value) {
            ExtendedFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(20.dp),
                onClick = {
                    onClickSave(editableDevice.value)
                    isEditable.value = false },
                icon = { Icon(Icons.Filled.Save, null) },
                text = { Text(text = stringResource(R.string.save_modifications)) },
            )
        }
    }
}

@Composable
fun AdminDeviceDetailsButtonsRow(
    device: DeviceSearchRequestUi,
    onClickDelete: (DeviceSearchRequestUi) -> Unit,
    onClickExport: () -> Unit,
    onClickEdit: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.End, modifier = Modifier
        .padding(horizontal = 15.dp, vertical = 10.dp)
        .fillMaxWidth()
    ) {
        AdminDeviceDetailsButton(icon = Icons.TwoTone.Delete) { onClickDelete(device) }
        AdminDeviceDetailsButton(icon = Icons.TwoTone.Edit) { onClickEdit() }
        AdminDeviceDetailsButton(icon = Icons.TwoTone.IosShare) { onClickExport() }
    }
}

@Composable
fun AdminDeviceDetailsItems(
    device: DeviceSearchRequestUi,
    site: String?,
    manufacturer: String?,
    type: String?,
    asset: String?,
    isEditable: Boolean,
    onNavigateToAdminDeviceTypes: () -> Unit,
    onNavigateToAdminDeviceManufacturers: () -> Unit,
    onNavigateToAdminDeviceAssets: () -> Unit,
    onNavigateToAdminDeviceSites: () -> Unit,
    onEditDevice: (DeviceSearchRequestUi) -> Unit
) {
    Column(
        modifier = Modifier.background(LocalCardColorsPalette.current.containerColor)
    ) {
        AdminDeviceDetailsNavigableRow(label = stringResource(id = R.string.asset), value = asset ?: device.assetName.label, isEditable = isEditable, onClick = onNavigateToAdminDeviceAssets, onValueChange = { onEditDevice(device.copy(assetName = DeviceAssetUi.valueOf(it))) })
        AdminDeviceDetailsNavigableRow(label = stringResource(id = R.string.type), value = type ?: device.type, isEditable = isEditable, onClick = onNavigateToAdminDeviceTypes, onValueChange = { onEditDevice(device.copy(type = it)) })
        AdminDeviceDetailsNavigableRow(label = stringResource(id = R.string.manufacturer), value = manufacturer ?: device.manufacturer, isEditable = isEditable, onClick = onNavigateToAdminDeviceManufacturers, onValueChange = { onEditDevice(device.copy(manufacturer = it)) })
        AdminDeviceDetailRow(label = stringResource(id = R.string.invoice_number), value = device.invoiceNumber, isEditable = isEditable, onValueChange = { onEditDevice(device.copy(invoiceNumber = it)) })
        AdminDeviceDetailRow(label = stringResource(id = R.string.serial_number), value = device.serialNumber, isEditable = isEditable, onValueChange = { onEditDevice(device.copy(serialNumber = it)) })

        HorizontalDivider(thickness = 20.dp, color = MaterialTheme.colorScheme.background)

        AdminDeviceDetailRow(label = stringResource(id = R.string.shipment_date_begin), value = device.shipmentDateBegin, isEditable = isEditable, onValueChange = { onEditDevice(device.copy(shipmentDateBegin = it)) })
        AdminDeviceDetailRow(label = stringResource(id = R.string.shipment_date_end), value = device.shipmentDateEnd, isEditable = isEditable, onValueChange = { onEditDevice(device.copy(shipmentDateEnd = it)) })

        HorizontalDivider(thickness = 20.dp, color = MaterialTheme.colorScheme.background)

        AdminDeviceDetailRow(label = stringResource(id = R.string.status), value = device.status.name, isEditable = isEditable, onValueChange = { onEditDevice(device.copy(status = DeviceStatusUi.valueOf(it))) })
        AdminDeviceDetailRow(label = stringResource(id = R.string.condition), value = device.condition.label, isEditable = isEditable, onValueChange = { onEditDevice(device.copy(condition = DeviceConditionUi.valueOf(it))) })
        AdminDeviceDetailRow(label = stringResource(id = R.string.lease_start_date), value = device.leaseStartDate, isEditable = isEditable, onValueChange = { onEditDevice(device.copy(leaseStartDate = it)) })
        AdminDeviceDetailRow(label = stringResource(id = R.string.lease_end_date), value = device.leaseEndDate, isEditable = isEditable, onValueChange = { onEditDevice(device.copy(leaseEndDate = it)) })
        AdminDeviceDetailRow(label = stringResource(id = R.string.user_name), value = device.userName, isEditable = isEditable, onValueChange = { onEditDevice(device.copy(userName = it)) })

        HorizontalDivider(thickness = 20.dp, color = MaterialTheme.colorScheme.background)

        AdminDeviceDetailsNavigableRow(label = stringResource(id = R.string.site), value = site ?: device.site, isEditable = isEditable, onClick = onNavigateToAdminDeviceSites, onValueChange = { onEditDevice(device.copy(site = it)) })
        AdminDeviceDetailRow(label = stringResource(id = R.string.supplier), value = device.supplier, isEditable = isEditable, onValueChange = { onEditDevice(device.copy(supplier = it)) })
        AdminDeviceDetailRow(label = stringResource(id = R.string.note), value = device.note, isEditable = isEditable, onValueChange = { onEditDevice(device.copy(note = it)) })

        HorizontalDivider(thickness = 100.dp, color = MaterialTheme.colorScheme.background)
    }
}

@Composable
fun AdminDeviceDetailRow(
    label: String,
    value: String?,
    isEditable: Boolean,
    onValueChange: (String) -> Unit
) {
    val editableValue = remember {
        mutableStateOf(value)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 20.dp)) {
        Text(text = label)
        Row {
            BasicTextField(
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End, color = MaterialTheme.colorScheme.onBackground),
                value = editableValue.value.orEmpty(),
                onValueChange = {
                    onValueChange(it)
                    editableValue.value = it },
                readOnly = !isEditable,
                singleLine = true,
            )
            AnimatedVisibility(isEditable){
                Row {
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = LocalCardColorsPalette.current.secondaryContentColor)
                }
            }
        }
    }
    HorizontalDivider(thickness = 1.dp, color = LocalCardColorsPalette.current.borderColor)
}

@Composable
fun AdminDeviceDetailsNavigableRow(
    label: String,
    value: String,
    onClick: () -> Unit,
    onValueChange: (String) -> Unit,
    isEditable: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .conditional(
                isEditable, modifier = {
                    Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onClick()
                    }
                }
            )
            .padding(horizontal = 15.dp, vertical = 20.dp))
    {
        Text(text = label)
        Row {
            BasicTextField(
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End, color = MaterialTheme.colorScheme.onBackground),
                value = value,
                onValueChange = {
                    onValueChange(it) },
                enabled = false,
                singleLine = true,
            )
            AnimatedVisibility(isEditable){
                Row {
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = LocalCardColorsPalette.current.secondaryContentColor)
                }
            }
        }
    }
    HorizontalDivider(thickness = 1.dp, color = LocalCardColorsPalette.current.borderColor)
}

@Composable
fun AdminDeviceDetailsButton(
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .padding(vertical = 20.dp)
            .height(IntrinsicSize.Max) ,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .pulsate()
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
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
                contentDescription = null,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}
