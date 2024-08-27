package com.zenitech.imaapp.feature.admin.devices.add_device

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.twotone.KeyboardArrowRight
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.common.PrimaryBasicTextField
import com.zenitech.imaapp.ui.common.conditional
import com.zenitech.imaapp.ui.common.fadingEdge
import com.zenitech.imaapp.ui.common.leftFade
import com.zenitech.imaapp.ui.common.rightFade
import com.zenitech.imaapp.ui.model.DeviceConditionUi
import com.zenitech.imaapp.ui.model.DeviceStatusUi
import com.zenitech.imaapp.ui.model.LeasingUi
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PreviewAdminDevicesAddDeviceStatus() {
    val mockedPagerState = rememberPagerState(pageCount = { 4 })

    AdminDevicesAddDeviceStatus(
        pagerState = mockedPagerState,
        page = 2,
        onChange = {},
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAdminDevicesAddDeviceStatusInputField() {
    AdminDevicesAddDeviceStatusInputField(
        selectedStatus = DeviceStatusUi.DRAFT,
        onStatusChanged = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAdminDevicesAddDeviceConditionInputField() {
    AdminDevicesAddDeviceConditionInputField(
        selectedCondition = DeviceConditionUi.NEW,
        onConditionChanged = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAdminDevicesAddDeviceLeasingForm() {
    AdminDevicesAddDeviceLeasingForm(
        leasing = LeasingUi(
            leaseId = "12345",
            startDate = "2024-08-14",
            endDate = "2025-08-14",
            userId = "user123",
            userName = "John Doe"
        ),
        onLeasingChanged = {}
    )
}

@Composable
fun AdminDevicesAddDeviceStatus(
    pagerState: PagerState,
    page: Int,
    onChange: (AdminDevicesAddDeviceUserEvent) -> Unit,
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
                    },
                    isError = false)

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
                    },
                    isError = false
                )
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
                    },
                    isError = false
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