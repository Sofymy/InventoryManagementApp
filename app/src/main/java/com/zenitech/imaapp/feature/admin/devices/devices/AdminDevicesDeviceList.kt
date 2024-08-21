@file:OptIn(ExperimentalMaterialApi::class)

package com.zenitech.imaapp.feature.admin.devices.devices

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ArrowForwardIos
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.common.pulsate
import com.zenitech.imaapp.ui.model.DeviceAssetUi
import com.zenitech.imaapp.ui.model.DeviceConditionUi
import com.zenitech.imaapp.ui.model.DeviceSearchRequestUi
import com.zenitech.imaapp.ui.model.DeviceStatusUi
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette

@Preview(showBackground = true)
@Composable
fun PreviewAdminDevicesDeviceList() {
    val mockedPullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = { }
    )

    AdminDevicesDeviceList(
        filteredDevices = listOf(
            DeviceSearchRequestUi(
                inventoryId = "123",
                type = "Laptop",
                assetName = DeviceAssetUi.Laptop,
                manufacturer = "Dell",
                serialNumber = "SN12345",
                supplier = "Supplier A",
                invoiceNumber = "INV123",
                condition = DeviceConditionUi.NEW,
                status = DeviceStatusUi.IN_STORAGE,
                userName = "John Doe",
                site = "Site A",
                location = "Location A",
                isTestDevice = false
            )
        ),
        adminFields = listOf("Inventory ID", "Type", "Status"),
        selectedField = "Type",
        onFieldSelected = {},
        selectedTab = AdminDevicesPagerTab.Leased,
        onNavigateToAdminDeviceDetails = {},
        pullRefreshState = mockedPullRefreshState,
        isRefreshing = false
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdminDevicesDeviceList(
    filteredDevices: List<DeviceSearchRequestUi>,
    adminFields: List<String>,
    selectedField: String,
    onFieldSelected: (String) -> Unit,
    selectedTab: AdminDevicesPagerTab,
    onNavigateToAdminDeviceDetails: (String) -> Unit,
    pullRefreshState: PullRefreshState,
    isRefreshing: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(selectedTab.padding)
                .clip(selectedTab.shape)
                .border(1.dp, LocalCardColorsPalette.current.borderColor)
                .background(LocalCardColorsPalette.current.containerColor),
        ) {
            stickyHeader {
                AdminDevicesDeviceListHeader(
                    selectedField = selectedField,
                    adminFields = adminFields,
                    onFieldSelected = onFieldSelected
                )
            }

            items(
                items = filteredDevices,
                key = { it.inventoryId }
            ) { device ->
                AdminDevicesDeviceListItem(
                    device = device,
                    selectedField = selectedField,
                    onNavigateToAdminDeviceDetails = onNavigateToAdminDeviceDetails
                )
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = LocalCardColorsPalette.current.containerColor,
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun AdminDevicesDeviceListHeader(
    adminFields: List<String>,
    onFieldSelected: (String) -> Unit,
    selectedField: String
) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .background(LocalCardColorsPalette.current.arrowColor),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AdminDevicesTableCell(
                text = stringResource(id = R.string.inventory_id),
                modifier = Modifier.weight(0.5f)
            )
            AdminDevicesFields(
                selectedField = selectedField,
                fields = adminFields,
                modifier = Modifier
                    .weight(0.5f)
            ) { onFieldSelected(it) }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun AdminDevicesFields(
    selectedField: String,
    fields: List<String>,
    modifier: Modifier,
    onFieldSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(modifier = modifier.fillMaxWidth()) {
        AdminDevicesDropDown(
            selectedOption = selectedField,
            expanded = expanded,
            options = fields,
            onOptionSelected = {
                onFieldSelected(it)
                expanded = false
            },
            onExpandedChange = { expanded = !expanded },
            modifier = modifier
        )
    }
}

@Composable
fun AdminDevicesDeviceListItem(
    device: DeviceSearchRequestUi,
    selectedField: String,
    onNavigateToAdminDeviceDetails: (String) -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Row(
        Modifier
            .pulsate()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onNavigateToAdminDeviceDetails(device.inventoryId)
            }
    ) {
        AdminDevicesTableCell(
            text = device.inventoryId,
            modifier = Modifier.weight(0.5f)
        )
        AdminDevicesTableCell(
            text = getFieldValue(device, selectedField),
            modifier = Modifier
                .weight(0.5f)
        )
    }
    HorizontalDivider(color = LocalCardColorsPalette.current.borderColor)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDevicesDropDown(
    selectedOption: String,
    expanded: Boolean,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    onExpandedChange: () -> Unit,
    modifier: Modifier
) {
    val iconAngle = animateFloatAsState(
        targetValue = if(expanded) -90f else 90f,
        animationSpec = tween(durationMillis = 500),
        label = ""
    )

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { onExpandedChange() }) {
        Row(
            modifier = modifier
                .padding(15.dp)
                .fillMaxWidth()
                .menuAnchor(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(selectedOption)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.TwoTone.ArrowForwardIos,
                contentDescription = null,
                tint = LocalCardColorsPalette.current.secondaryContentColor,
                modifier = Modifier
                    .size(20.dp)
                    .graphicsLayer {
                        rotationZ = iconAngle.value
                    }
            )
            Spacer(modifier = Modifier.width(15.dp))
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange() }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            option,
                            color = if (option == selectedOption) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                    },
                    onClick = { onOptionSelected(option) },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Composable
fun AdminDevicesTableCell(
    text: String,
    modifier: Modifier
) {
    Box(
        modifier.padding(15.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

fun filterDevices(
    devices: List<DeviceSearchRequestUi>,
    page: Int,
    selectedField: String,
    searchQuery: String
): List<DeviceSearchRequestUi> {
    return devices.filter { device ->
        val statusMatches = when (page) {
            1 -> device.status == DeviceStatusUi.IN_STORAGE
            2 -> device.status == DeviceStatusUi.LEASED
            3 -> device.status == DeviceStatusUi.ON_REPAIR
            else -> true
        }

        val fieldValue = getFieldValue(device, selectedField)
        statusMatches && (
                matchesSearchQuery(device.inventoryId, searchQuery) ||
                        matchesSearchQuery(fieldValue, searchQuery))
    }
}

fun getFieldValue(
    device: DeviceSearchRequestUi,
    field: String
): String {
    return when (field) {
        "Inventory ID" -> device.inventoryId
        "Type" -> device.type
        "Asset name" -> device.assetName.label
        "Manufacturer" -> device.manufacturer
        "Serial number" -> device.serialNumber
        "Supplier" -> device.supplier
        "Invoice number" -> device.invoiceNumber
        "Condition" -> device.condition.toString()
        "Status" -> device.status.toString()
        "User name" -> device.userName
        "Site" -> device.site
        "Location" -> device.location
        "Test" -> device.isTestDevice.toString()
        else -> "N/A"
    }
}