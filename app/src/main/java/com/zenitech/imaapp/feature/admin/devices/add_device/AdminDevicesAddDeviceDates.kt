@file:OptIn(ExperimentalMaterial3Api::class)

package com.zenitech.imaapp.feature.admin.devices.add_device

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
@Preview(showBackground = true)
fun PreviewAdminDevicesAddDeviceDates() {
    val mockedPagerState = rememberPagerState(pageCount = { 4 })

    AdminDevicesAddDeviceDates(
        pagerState = mockedPagerState,
        page = 0,
        onChange = { }
    )
}

@Composable
fun AdminDevicesAddDeviceDates(
    pagerState: PagerState,
    page: Int,
    onChange: (AdminDevicesAddDeviceUserEvent) -> Unit
) {
    val shipmentDate = remember {
        mutableStateOf("")
    }
    val warranty = remember {
        mutableStateOf("")
    }
    val shipmentDateExpanded = remember {
        mutableStateOf(false)
    }
    val warrantyExpanded = remember {
        mutableStateOf(false)
    }

    LazyColumn {
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item{
            Column(
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                AdminDevicesAddDeviceInputField(
                    label = stringResource(R.string.shipment_date),
                    value = {
                        AdminDevicesAddDeviceDateInput(
                            shipmentDateExpanded.value,
                            onExpandedChange = {
                                shipmentDateExpanded.value = it
                            },
                            onValueChange = {
                                shipmentDate.value = it
                                onChange(AdminDevicesAddDeviceUserEvent.ChangeShipmentDate(shipmentDate.value))
                            }
                        )
                    },
                    onClick = {
                        shipmentDateExpanded.value = true
                    })

                AdminDevicesAddDeviceInputField(
                    label = stringResource(R.string.warranty),
                    value = {
                        AdminDevicesAddDeviceDateInput(
                            warrantyExpanded.value,
                            onExpandedChange = {
                                warrantyExpanded.value = it
                            },
                            onValueChange = {
                                warranty.value = it
                                onChange(AdminDevicesAddDeviceUserEvent.ChangeWarranty(warranty.value))
                            }
                        )
                    },
                    onClick = {
                        warrantyExpanded.value = true
                    })
            }
        }
        item {
            HorizontalDivider(color = LocalCardColorsPalette.current.borderColor)
        }
        item{
            AdminDevicesAddDeviceNavigationButtons(
                pagerState = pagerState,
                currentPage = page,
            )
        }
    }
}

@Composable
fun AdminDevicesAddDeviceDateInput(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit
) {
    val dateState = rememberDatePickerState()
    val dateToString = dateState.selectedDateMillis?.let {
        val localDate = LocalDate.ofEpochDay(it / (24 * 60 * 60 * 1000))
        localDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
    } ?: LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = dateToString,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold
        )
        if (expanded) {
            DatePickerDialog(
                properties = DialogProperties(
                    usePlatformDefaultWidth = false
                ),
                onDismissRequest = {
                    onExpandedChange(false) },
                confirmButton = {
                    Button(onClick = {
                        onExpandedChange(false)
                        onValueChange(dateToString)
                    }) { Text(text = stringResource(R.string.ok)) }
                },
                dismissButton = {
                    Button(onClick = { onExpandedChange(false) }) { Text(text = stringResource(R.string.cancel)) }
                }
            ) {
                DatePicker(state = dateState, showModeToggle = true)
            }
        }
    }
}
