package com.zenitech.imaapp.feature.admin.devices.add_device

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.common.PrimaryBasicTextField
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import com.zenitech.imaapp.ui.utils.validation.ValidationError

@Composable
@Preview(showBackground = true)
fun PreviewAdminDevicesAddDeviceGeneral() {
    val mockedPagerState = rememberPagerState(pageCount = { 4 })

    AdminDevicesAddDeviceGeneral(
        pagerState = mockedPagerState,
        page = 0,
        onNavigateToAdminDeviceTypes = { },
        type = "Laptop",
        onNavigateToAdminDeviceManufacturers = { },
        manufacturer = "Apple",
        onNavigateToAdminDeviceAssets = { },
        asset = "MacBook Pro",
        onChange = {},
        errors = emptyList()
    )
}

@Composable
fun AdminDevicesAddDeviceGeneral(
    pagerState: PagerState,
    page: Int,
    onNavigateToAdminDeviceTypes: () -> Unit,
    type: String,
    onNavigateToAdminDeviceManufacturers: () -> Unit,
    manufacturer: String,
    onNavigateToAdminDeviceAssets: () -> Unit,
    asset: String,
    onChange: (AdminDevicesAddDeviceUserEvent) -> Unit,
    errors: List<ValidationError?>
) {
    val assetState by remember { mutableStateOf(asset) }
    val typeState by remember { mutableStateOf(type) }
    val manufacturerState by remember { mutableStateOf(manufacturer) }

    val invoiceNumberState = rememberSaveable { mutableStateOf("") }
    val serialNumberState = rememberSaveable { mutableStateOf("") }

    val invoiceNumberFocusRequester = remember { FocusRequester() }
    val serialNumberFocusRequester = remember { FocusRequester() }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(assetState, typeState, manufacturerState) {
        onChange(AdminDevicesAddDeviceUserEvent.ChangeDeviceAsset(assetState))
        onChange(AdminDevicesAddDeviceUserEvent.ChangeDeviceType(typeState))
        onChange(AdminDevicesAddDeviceUserEvent.ChangeDeviceManufacturer(manufacturerState))
    }

    LazyColumn {
        item{
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Column(Modifier.padding(horizontal = 15.dp)) {
                AdminDevicesAddDeviceInputField(
                    label = stringResource(R.string.asset),
                    value = {
                        Text(
                            text = assetState,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onClick = onNavigateToAdminDeviceAssets,
                    isError = errors.any { it?.property == "assetName" },
                )
                AdminDevicesAddDeviceInputField(
                    label = stringResource(id = R.string.type),
                    value = {
                        Text(
                            text = typeState,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onClick = onNavigateToAdminDeviceTypes,
                    isError = errors.any { it?.property == "type" },
                )
                AdminDevicesAddDeviceInputField(
                    label = stringResource(id = R.string.manufacturer),
                    value = {
                        Text(
                            text = manufacturerState,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onClick = onNavigateToAdminDeviceManufacturers,
                    isError = errors.any { it?.property == "manufacturer" },
                )
            }
        }

        item {
            HorizontalDivider(color = LocalCardColorsPalette.current.borderColor)
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            Column(Modifier.padding(horizontal = 15.dp)) {
                AdminDevicesAddDeviceInputField(
                    label = stringResource(R.string.invoice_number),
                    value = {
                        PrimaryBasicTextField(
                            modifier = Modifier.focusRequester(invoiceNumberFocusRequester),
                            placeholderValue = stringResource(R.string.enter_invoice_number),
                            focusRequester = FocusRequester(),
                            focusManager = LocalFocusManager.current,
                            value = invoiceNumberState.value,
                            onValueChanged = {
                                invoiceNumberState.value = it
                                onChange(AdminDevicesAddDeviceUserEvent.ChangeInvoiceNumber(invoiceNumberState.value))
                            },
                            keyboardType = KeyboardType.Number,
                        )
                    },
                    onClick = {
                        focusManager.clearFocus()
                        invoiceNumberFocusRequester.requestFocus()
                    },
                    showArrowDown = false,
                    isError = errors.any { it?.property == "invoiceNumber" },
                )
                AdminDevicesAddDeviceInputField(
                    label = stringResource(id = R.string.serial_number),
                    value = {
                        PrimaryBasicTextField(
                            modifier = Modifier.focusRequester(serialNumberFocusRequester),
                            placeholderValue = stringResource(R.string.enter_serial_number),
                            focusRequester = FocusRequester(),
                            focusManager = LocalFocusManager.current,
                            value = serialNumberState.value,
                            onValueChanged = {
                                serialNumberState.value = it
                                onChange(AdminDevicesAddDeviceUserEvent.ChangeSerialNumber(serialNumberState.value))
                            },
                            keyboardType = KeyboardType.Number,
                        )
                    },
                    onClick = {
                        focusManager.clearFocus()
                        serialNumberFocusRequester.requestFocus()
                    },
                    showArrowDown = false,
                    isError = errors.any { it?.property == "serialNumber" },
                )
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
