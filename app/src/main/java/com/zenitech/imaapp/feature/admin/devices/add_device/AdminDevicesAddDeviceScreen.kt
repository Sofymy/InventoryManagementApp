@file:OptIn(ExperimentalMaterial3Api::class)

package com.zenitech.imaapp.feature.admin.devices.add_device

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.twotone.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.common.PrimaryBasicTextField
import com.zenitech.imaapp.ui.common.PrimaryButton
import com.zenitech.imaapp.ui.common.PrimaryInputField
import com.zenitech.imaapp.ui.common.SecondaryButton
import com.zenitech.imaapp.ui.common.conditional
import com.zenitech.imaapp.ui.common.fadingEdge
import com.zenitech.imaapp.ui.common.leftFade
import com.zenitech.imaapp.ui.common.progressStateBrush
import com.zenitech.imaapp.ui.common.rightFade
import com.zenitech.imaapp.ui.model.DeviceConditionUi
import com.zenitech.imaapp.ui.model.DeviceStatusUi
import com.zenitech.imaapp.ui.model.LeasingUi
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import com.zenitech.imaapp.ui.theme.Mint
import com.zenitech.imaapp.ui.utils.validation.ValidationError
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AdminDevicesAddDeviceScreen(
    onNavigateToAdminAddDeviceAssets: () -> Unit,
    asset: String,
    onNavigateToAdminAddDeviceTypes: () -> Unit,
    type: String,
    onNavigateToAdminAddDeviceManufacturers: () -> Unit,
    manufacturer: String,
    onNavigateToAdminAddDeviceSites: () -> Unit,
    site: String,
    onNavigateToAdminAddDeviceSuccessful: () -> Unit
) {
    Column(modifier = Modifier
        .padding(top = 15.dp)
        .fillMaxSize()) {
        AdminDevicesAddDeviceContent(
            onNavigateToAdminAddDeviceTypes = onNavigateToAdminAddDeviceTypes,
            type = type,
            onNavigateToAdminAddDeviceManufacturers = onNavigateToAdminAddDeviceManufacturers,
            manufacturer = manufacturer,
            onNavigateToAdminAddDeviceAssets = onNavigateToAdminAddDeviceAssets,
            asset = asset,
            onNavigateToAdminAddDeviceSites = onNavigateToAdminAddDeviceSites,
            site = site,
            onNavigateToAdminAddDeviceSuccessful = onNavigateToAdminAddDeviceSuccessful
        )
    }
}

@Composable
fun AdminDevicesAddDeviceContent(
    viewModel: AdminDevicesAddDeviceViewModel = hiltViewModel(),
    onNavigateToAdminAddDeviceTypes: () -> Unit,
    type: String,
    onNavigateToAdminAddDeviceManufacturers: () -> Unit,
    manufacturer: String,
    onNavigateToAdminAddDeviceAssets: () -> Unit,
    asset: String,
    onNavigateToAdminAddDeviceSites: () -> Unit,
    site: String,
    onNavigateToAdminAddDeviceSuccessful: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 4 })
    val state by viewModel.state.collectAsStateWithLifecycle()

    val errors = remember {
        mutableStateOf(emptyList<ValidationError?>())
    }

    LaunchedEffect(key1 = state) {
        when (state) {
            is AdminDevicesAddDeviceState.Success -> {
                onNavigateToAdminAddDeviceSuccessful()
            }
            is AdminDevicesAddDeviceState.Failure -> {
                errors.value = (state as AdminDevicesAddDeviceState.Failure).error
            }
            else -> {}
        }
    }
    AdminDevicesAddDeviceProgressBar(currentPageNumber = pagerState.currentPage)

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = false,
        state = pagerState
    ) { page ->
        Column(modifier = Modifier.fillMaxSize()) {
            AdminDevicesAddDeviceDisplayPageContent(
                page = page,
                pagerState = pagerState,
                onNavigateToAdminAddDeviceTypes = onNavigateToAdminAddDeviceTypes,
                type = type,
                onNavigateToAdminAddDeviceManufacturers = onNavigateToAdminAddDeviceManufacturers,
                manufacturer = manufacturer,
                onNavigateToAdminAddDeviceAssets = onNavigateToAdminAddDeviceAssets,
                asset = asset,
                onNavigateToAdminAddDeviceSites = onNavigateToAdminAddDeviceSites,
                site = site,
                onChange = { viewModel.onEvent(it) },
                onSave = { viewModel.onEvent(AdminDevicesAddDeviceUserEvent.SaveRequest) }
            )
        }
    }
}

@Composable
fun AdminDevicesAddDeviceDisplayPageContent(
    onChange: (AdminDevicesAddDeviceUserEvent) -> Unit,
    page: Int,
    onNavigateToAdminAddDeviceTypes: () -> Unit,
    type: String,
    onNavigateToAdminAddDeviceManufacturers: () -> Unit,
    manufacturer: String,
    onNavigateToAdminAddDeviceAssets: () -> Unit,
    asset: String,
    onNavigateToAdminAddDeviceSites: () -> Unit,
    site: String,
    pagerState: PagerState,
    onSave: (() -> Unit)?
) {
    when (page) {
        0 -> AdminDevicesAddDeviceGeneral(
            onChange = onChange,
            pagerState = pagerState,
            page = page,
            onNavigateToAdminAddDeviceTypes = onNavigateToAdminAddDeviceTypes,
            type = type,
            onNavigateToAdminAddDeviceManufacturers = onNavigateToAdminAddDeviceManufacturers,
            manufacturer = manufacturer,
            onNavigateToAdminAddDeviceAssets = onNavigateToAdminAddDeviceAssets,
            asset = asset
        )
        1 -> AdminDevicesAddDeviceDates(
            onChange = onChange,
            pagerState = pagerState,
            page = page,
        )
        2 -> AdminDevicesAddDeviceStatus(
            onChange = onChange,
            pagerState = pagerState,
            page = page,
        )
        else -> AdminDevicesAddDeviceLocation(
            onChange = onChange,
            pagerState = pagerState,
            page = page,
            site = site,
            onNavigateToAdminAddDeviceSites = onNavigateToAdminAddDeviceSites,
            onSave = onSave
        )
    }
}

@Composable
fun AdminDevicesAddDeviceNavigationButtons(
    pagerState: PagerState,
    currentPage: Int,
    onSave: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(Modifier.weight(1f)) {
            AdminDevicesAddDeviceBackButton(page = currentPage, pagerState = pagerState)
            Spacer(modifier = Modifier.width(10.dp))
        }
        Row(Modifier.weight(1f)) {
            Spacer(modifier = Modifier.width(10.dp))
            AdminDevicesAddDeviceNextOrAddButton(
                page = currentPage,
                pagerState = pagerState,
                onSave = onSave
            )
        }
    }
}

@Composable
fun AdminDevicesAddDeviceBackButton(
    page: Int,
    pagerState: PagerState
) {
    val scope = rememberCoroutineScope()

    if (page != 0) {
        PrimaryButton(onClick = {
            scope.launch {
                pagerState.animateScrollToPage(page - 1) }
        }) {
            Text(text = "Back")
        }
    }
}

@Composable
fun AdminDevicesAddDeviceNextOrAddButton(
    page: Int,
    pagerState: PagerState,
    onSave: (() -> Unit)? = null
) {
    val scope = rememberCoroutineScope()

    if (page != 3) {
        SecondaryButton(
            onClick = {
            scope.launch { pagerState.animateScrollToPage(page + 1) }
        }) {
            Text(text = "Next")
        }
    } else {
        SecondaryButton(
            onClick = {
                if (onSave != null) {
                    onSave()
                }
        }) {
            Text(text = "Add device")
        }
    }
}

@Composable
fun AdminDevicesAddDeviceGeneral(
    pagerState: PagerState,
    page: Int,
    onNavigateToAdminAddDeviceTypes: () -> Unit,
    type: String,
    onNavigateToAdminAddDeviceManufacturers: () -> Unit,
    manufacturer: String,
    onNavigateToAdminAddDeviceAssets: () -> Unit,
    asset: String,
    onChange: (AdminDevicesAddDeviceUserEvent) -> Unit
) {
    val assetState by remember { mutableStateOf(asset) }
    val typeState by remember { mutableStateOf(type) }
    val manufacturerState by remember { mutableStateOf(manufacturer) }
    val invoiceNumberState = rememberSaveable { mutableStateOf("") }
    val serialNumberState = rememberSaveable { mutableStateOf("") }

    val invoiceNumberFocusRequester = remember { FocusRequester() }
    val serialNumberFocusRequester = remember { FocusRequester() }

    val focusManager = LocalFocusManager.current

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
                    onClick = onNavigateToAdminAddDeviceAssets,
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
                    onClick = onNavigateToAdminAddDeviceTypes,
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
                    onClick = onNavigateToAdminAddDeviceManufacturers,
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
                    showArrowDown = false,
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
                )
                AdminDevicesAddDeviceInputField(
                    label = stringResource(id = R.string.serial_number),
                    showArrowDown = false,
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

@Composable
fun AdminDevicesAddDeviceInputField(
    label: String,
    value: @Composable () -> Unit,
    onClick: (() -> Unit)?,
    showArrowDown: Boolean = true
) {
    PrimaryInputField(
        label = label,
        value = {
            value()
        },
        onClick = onClick,
        showArrowDown = showArrowDown
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun AdminDevicesAddDeviceProgressBar(
    currentPageNumber: Int
) {
    val progressStates = listOf(
        stringResource(R.string.general),
        stringResource(R.string.dates),
        stringResource(R.string.status),
        stringResource(R.string.location)
    )

    AdminDevicesAddDeviceProgressBarStates(
        currentPageNumber = currentPageNumber,
        progressStates = progressStates
    )

    HorizontalDivider(
        color = LocalCardColorsPalette.current.borderColor
    )

}

@Composable
fun AdminDevicesAddDeviceProgressBarStates(
    currentPageNumber: Int,
    progressStates: List<String>
) {
    val iconSize = 30.dp

    Column(modifier = Modifier.padding(20.dp)) {
        Box {
            AnimatedContent(
                modifier = Modifier.align(Alignment.TopCenter),
                targetState = currentPageNumber,
                transitionSpec = {
                    fadeIn().togetherWith(fadeOut())
                }, label = ""
            ) {
                AdminDevicesAddDeviceProgressBarIndicator(currentPageNumber = it, iconSize = iconSize)
            }
            AdminDevicesAddDeviceProgressBarIcons(
                currentPageNumber = currentPageNumber,
                progressStates = progressStates,
                iconSize = iconSize
            )
        }
    }
}

@Composable
fun AdminDevicesAddDeviceProgressBarIndicator(currentPageNumber: Int, iconSize: Dp) {
    val progressBarIndicatorHeight = 4.dp

    Box(
        modifier = Modifier
            .padding(
                start = iconSize / 2,
                end = iconSize / 2,
                top = iconSize / 2 - progressBarIndicatorHeight / 2
            )
            .height(progressBarIndicatorHeight)
            .fillMaxWidth()
            .background(progressStateBrush(progressState = currentPageNumber + 1))
    )
}

@Composable
fun AdminDevicesAddDeviceProgressBarIcons(
    iconSize: Dp,
    currentPageNumber: Int,
    progressStates: List<String>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        progressStates.forEachIndexed { index, progressState ->
            val progressStateColor by animateColorAsState(
                targetValue = if (currentPageNumber >= index) Mint else LocalCardColorsPalette.current.borderColor,
                label = ""
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Rounded.CheckCircle,
                        tint = progressStateColor,
                        contentDescription = null,
                        modifier = Modifier
                            .background(progressStateColor, CircleShape)
                            .size(iconSize)
                    )
                    this@Row.AnimatedVisibility(visible = currentPageNumber > index) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            tint = Color.White,
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = progressState,
                    fontSize = 10.sp,
                    color = progressStateColor
                )
            }
        }
    }
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
                    Row(Modifier
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

@Composable
fun AdminDevicesAddDeviceStatus(
    pagerState: PagerState,
    page: Int,
    onChange: (AdminDevicesAddDeviceUserEvent) -> Unit
) {
    val selectedStatus = rememberSaveable {
        mutableStateOf(DeviceStatusUi.DRAFT)
    }

    val selectedCondition = rememberSaveable {
        mutableStateOf(DeviceConditionUi.NEW)
    }

    val leasing = rememberSaveable {
        mutableStateOf(LeasingUi())
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
                    label = stringResource(R.string.status),
                    value = {
                        AdminDevicesAddDeviceStatusInputField(
                            selectedStatus.value,
                            onStatusChanged = {
                                selectedStatus.value = it
                                onChange(AdminDevicesAddDeviceUserEvent.ChangeStatus(selectedStatus.value))
                                onChange(AdminDevicesAddDeviceUserEvent.ChangeStatus(selectedStatus.value))
                            }
                        )
                    },
                    onClick = {
                    })

                AdminDevicesAddDeviceInputField(
                    label = stringResource(R.string.condition),
                    value = {
                        AdminDevicesAddDeviceConditionInputField(
                            selectedCondition.value,
                            onConditionChanged = {
                                selectedCondition.value = it
                                onChange(AdminDevicesAddDeviceUserEvent.ChangeCondition(selectedCondition.value))
                            }
                        )
                    },
                    onClick = {
                    })
            }
        }
        item {
            HorizontalDivider(color = LocalCardColorsPalette.current.borderColor)
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item{
            Column(
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                AdminDevicesAddDeviceInputField(
                    label = stringResource(R.string.lease),
                    value = {
                        AdminDevicesAddDeviceLeasingForm(
                            leasing.value,
                            onLeasingChanged = {
                                leasing.value = it
                                onChange(AdminDevicesAddDeviceUserEvent.ChangeLease(leasing.value))
                            }
                        )
                    },
                    onClick = {
                    }
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

@Composable
fun AdminDevicesAddDeviceTextFieldRow(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Row {
            Text(text = label)
            Spacer(modifier = Modifier.width(10.dp))
        }
        PrimaryBasicTextField(
            focusRequester = FocusRequester(),
            focusManager = LocalFocusManager.current,
            value = value,
            onValueChanged = onValueChanged,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 10.dp),
            placeholderValue = placeholder
        )
    }
}

@Composable
fun AdminDevicesAddDeviceRowDivider() {
    Spacer(modifier = Modifier.height(10.dp))
    HorizontalDivider(color = LocalCardColorsPalette.current.borderColor)
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun AdminDevicesAddDeviceLeasingForm(
    leasing: LeasingUi,
    onLeasingChanged: (LeasingUi) -> Unit
) {
    Column {
        AdminDevicesAddDeviceTextFieldRow(
            label = stringResource(R.string.lease_id),
            value = leasing.leaseId,
            onValueChanged = { onLeasingChanged(leasing.copy(leaseId = it)) },
            placeholder = stringResource(R.string.enter_lease_id)
        )
        AdminDevicesAddDeviceRowDivider()

        AdminDevicesAddDeviceTextFieldRow(
            label = stringResource(R.string.start_date),
            value = leasing.startDate,
            onValueChanged = { onLeasingChanged(leasing.copy(startDate = it)) },
            placeholder = stringResource(R.string.enter_start_date)
        )
        AdminDevicesAddDeviceRowDivider()

        AdminDevicesAddDeviceTextFieldRow(
            label = stringResource(R.string.end_date),
            value = leasing.endDate,
            onValueChanged = { onLeasingChanged(leasing.copy(endDate = it)) },
            placeholder = stringResource(R.string.enter_end_date)
        )
        AdminDevicesAddDeviceRowDivider()

        AdminDevicesAddDeviceTextFieldRow(
            label = stringResource(R.string.user_id),
            value = leasing.userId,
            onValueChanged = { onLeasingChanged(leasing.copy(userId = it)) },
            placeholder = stringResource(R.string.enter_user_id)
        )
        AdminDevicesAddDeviceRowDivider()

        AdminDevicesAddDeviceTextFieldRow(
            label = stringResource(R.string.user_name),
            value = leasing.userName,
            onValueChanged = { onLeasingChanged(leasing.copy(userName = it)) },
            placeholder = stringResource(R.string.enter_user_name)
        )
    }
}

@Composable
fun AdminDevicesAddDeviceConditionInputField(
    selectedCondition: DeviceConditionUi,
    onConditionChanged: (DeviceConditionUi) -> Unit
) {
    val conditionOptions = DeviceConditionUi.entries.toTypedArray()

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
       items(conditionOptions) { option ->
            FilterChip(
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = LocalCardColorsPalette.current.borderColor,
                    enabled = true,
                    selected = option == selectedCondition
                ),
                selected = option == selectedCondition,
                label = { Text(option.label, textAlign = TextAlign.Center) },
                onClick = {
                    onConditionChanged(option)
                },
                colors = SelectableChipColors(
                    containerColor = LocalCardColorsPalette.current.containerColor,
                    labelColor = LocalCardColorsPalette.current.contentColor,
                    disabledContainerColor = Color.Unspecified,
                    disabledLabelColor = Color.Unspecified,
                    disabledLeadingIconColor = Color.Unspecified,
                    disabledSelectedContainerColor = Color.Unspecified,
                    disabledTrailingIconColor = Color.Unspecified,
                    leadingIconColor = Color.Unspecified,
                    selectedContainerColor = MaterialTheme.colorScheme.secondary,
                    selectedLabelColor = Color.White,
                    selectedLeadingIconColor = Color.Unspecified,
                    selectedTrailingIconColor = Color.Unspecified,
                    trailingIconColor = Color.Unspecified
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun AdminDevicesAddDeviceStatusInputField(
    selectedStatus: DeviceStatusUi,
    onStatusChanged: (DeviceStatusUi) -> Unit
) {
    val statusOptions = DeviceStatusUi.entries.toTypedArray()

    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val firstVisibleItemIndex = remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }

    LaunchedEffect(Unit) {
        delay(200)
        lazyListState.animateScrollToItem(statusOptions.indexOf(selectedStatus))
    }

    Row(
        modifier = Modifier, 
        verticalAlignment = Alignment.CenterVertically
    ){
        AnimatedVisibility(lazyListState.canScrollBackward){
            IconButton(
                onClick =  {
                    scope.launch {
                        lazyListState.animateScrollToItem(
                            when(lazyListState.firstVisibleItemIndex){
                                0 -> { 0 }
                                else -> { lazyListState.firstVisibleItemIndex -1}
                            }, 0)
                    }
                }
            ){
                Icon(
                    imageVector = Icons.AutoMirrored.TwoTone.KeyboardArrowLeft,
                    contentDescription = null
                )
            }
        }

        LazyRow(
            state = lazyListState,
            modifier = Modifier
                .conditional(firstVisibleItemIndex.value != 0) {
                    Modifier.fadingEdge(leftFade)
                }
                .fadingEdge(rightFade)
                .weight(1f)
        ){
            item {
                Spacer(modifier = Modifier.width(5.dp))
            }
            items(statusOptions) { option ->
                FilterChip(
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = LocalCardColorsPalette.current.borderColor,
                        enabled = true,
                        selected = option == selectedStatus
                    ),
                    selected = option == selectedStatus,
                    label = { Text(option.name.replace("_", " ")) },
                    onClick = {
                        onStatusChanged(option)
                    },
                    colors = SelectableChipColors(
                        containerColor = LocalCardColorsPalette.current.containerColor,
                        labelColor = LocalCardColorsPalette.current.contentColor,
                        disabledContainerColor = Color.Unspecified,
                        disabledLabelColor = Color.Unspecified,
                        disabledLeadingIconColor = Color.Unspecified,
                        disabledSelectedContainerColor = Color.Unspecified,
                        disabledTrailingIconColor = Color.Unspecified,
                        leadingIconColor = Color.Unspecified,
                        selectedContainerColor = option.color,
                        selectedLabelColor = Color.White,
                        selectedLeadingIconColor = Color.Unspecified,
                        selectedTrailingIconColor = Color.Unspecified,
                        trailingIconColor = Color.Unspecified
                    )
                )
                Spacer(Modifier.width(10.dp))
            }
        }
        AnimatedVisibility(lazyListState.canScrollForward){
            IconButton(
                onClick = {
                    scope.launch {
                        lazyListState.animateScrollToItem(
                            when(lazyListState.firstVisibleItemIndex){
                                statusOptions.size -> { lazyListState.firstVisibleItemIndex }
                                else -> { lazyListState.firstVisibleItemIndex + 1}
                            }, 0)
                    }
                }
            ){
                Icon(imageVector = Icons.AutoMirrored.TwoTone.KeyboardArrowRight, contentDescription = null)
            }
        }
    }
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

@Composable
fun AdminDevicesAddDeviceSearchField(
    filterQuery: String,
    onFilterQueryChanged: (String) -> Unit,
    onNavigateToAdminAddDevice: (String) -> Unit
) {
    val addContentColor = animateColorAsState(
        targetValue = if(filterQuery.isEmpty()) LocalCardColorsPalette.current.borderColor else LocalCardColorsPalette.current.contentColor,
        label = ""
    )
    Row(
        modifier = Modifier
            .padding(start = 30.dp, top = 20.dp, end = 15.dp, bottom = 15.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(imageVector = Icons.TwoTone.Search, contentDescription = null)
            Spacer(modifier = Modifier.width(10.dp))
            PrimaryBasicTextField(
                placeholderValue = stringResource(id = R.string.search),
                focusRequester = FocusRequester(),
                focusManager = LocalFocusManager.current,
                value = filterQuery,
                onValueChanged = { newQuery ->
                    onFilterQueryChanged(newQuery)
                }
            )
        }
        Row(
            Modifier
                .clip(RoundedCornerShape(15.dp))
                .clickable {
                    if (filterQuery.isNotBlank()) onNavigateToAdminAddDevice(filterQuery)
                }
                .border(1.dp, LocalCardColorsPalette.current.borderColor, RoundedCornerShape(15.dp))
                .background(
                    LocalCardColorsPalette.current.containerColor,
                    RoundedCornerShape(15.dp)
                )
                .padding(5.dp)
        ) {
            Icon(
                imageVector = Icons.TwoTone.Add,
                contentDescription = null,
                tint = addContentColor.value
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = stringResource(R.string.add), color = addContentColor.value)
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}

@Composable
fun AdminDevicesAddDeviceList(
    filteredList: List<String>,
    onNavigateToAdminAddDevice: (String) -> Unit
) {
    LazyColumn {
        items(filteredList) { item ->
            AdminDevicesAddDeviceItem(text = item) {
                onNavigateToAdminAddDevice(item)
            }
        }
    }
}


@Composable
fun AdminDevicesAddDeviceItem(
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
