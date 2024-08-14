package com.zenitech.imaapp.feature.admin.devices.add_device

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.common.PrimaryBasicTextField
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette

@Composable
@Preview(showBackground = true)
fun PreviewAdminDevicesAddDeviceLocation() {
    val mockedPagerState = rememberPagerState(pageCount = { 4 })

    AdminDevicesAddDeviceLocation(
        pagerState = mockedPagerState,
        page = 0,
        onNavigateToAdminAddDeviceSites = { },
        site = "BME",
        onChange = { },
        onSave = { }
    )
}

@Composable
fun AdminDevicesAddDeviceLocation(
    pagerState: PagerState,
    page: Int,
    onNavigateToAdminAddDeviceSites: () -> Unit,
    site: String,
    onChange: (AdminDevicesAddDeviceUserEvent) -> Unit,
    onSave: (() -> Unit)?
) {
    val siteState by remember { mutableStateOf(site) }

    val supplierState = rememberSaveable {
        mutableStateOf("")
    }

    val noteState = rememberSaveable {
        mutableStateOf("")
    }

    val noteFocusRequester = remember {
        FocusRequester()
    }

    val supplierFocusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current

    LazyColumn {
        item{
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Column(Modifier.padding(horizontal = 15.dp)) {
                Row {
                    Row(
                        Modifier
                            .weight(1f)) {
                        AdminDevicesAddDeviceInputField(
                            label = stringResource(R.string.location),
                            showArrowDown = false,
                            value = {
                                Text(
                                    text = stringResource(R.string.budapest),
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            onClick = null
                        )
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Row(
                        Modifier
                        .weight(1f)) {
                        AdminDevicesAddDeviceInputField(
                            label = stringResource(R.string.site),
                            value = {
                                Text(
                                    text = siteState,
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            onClick = onNavigateToAdminAddDeviceSites,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                AdminDevicesAddDeviceInputField(
                    showArrowDown = false,
                    label = stringResource(R.string.supplier),
                    value = {
                        PrimaryBasicTextField(
                            modifier = Modifier.focusRequester(supplierFocusRequester),
                            placeholderValue = stringResource(R.string.enter_supplier),
                            focusRequester = FocusRequester(),
                            focusManager = LocalFocusManager.current,
                            value = supplierState.value,
                            onValueChanged = {
                                supplierState.value = it
                                onChange(AdminDevicesAddDeviceUserEvent.ChangeSupplier(supplierState.value))
                            },
                        )
                    },
                    onClick = {
                        focusManager.clearFocus()
                        supplierFocusRequester.requestFocus()
                    }
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
            Row(Modifier.padding(horizontal = 15.dp)) {
                AdminDevicesAddDeviceInputField(
                    label = stringResource(id = R.string.note),
                    showArrowDown = false,
                    value = {
                        PrimaryBasicTextField(
                            modifier = Modifier.focusRequester(noteFocusRequester),
                            placeholderValue = stringResource(R.string.enter_note),
                            focusRequester = FocusRequester(),
                            focusManager = LocalFocusManager.current,
                            value = noteState.value,
                            onValueChanged = {
                                noteState.value = it
                                onChange(AdminDevicesAddDeviceUserEvent.ChangeNote(noteState.value))
                            },
                        )
                    },
                    onClick = {
                        focusManager.clearFocus()
                        noteFocusRequester.requestFocus()
                    },
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            HorizontalDivider(color = LocalCardColorsPalette.current.borderColor)
        }
        item{
            AdminDevicesAddDeviceNavigationButtons(
                pagerState = pagerState,
                currentPage = page,
                onSave = onSave
            )
        }
    }
}
