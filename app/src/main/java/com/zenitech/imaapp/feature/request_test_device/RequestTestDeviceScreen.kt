package com.zenitech.imaapp.feature.request_test_device

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Devices
import androidx.compose.material.icons.twotone.Factory
import androidx.compose.material.icons.twotone.Textsms
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.common.PrimaryBasicTextField
import com.zenitech.imaapp.ui.common.PrimaryInputField
import com.zenitech.imaapp.ui.common.SecondaryButton
import com.zenitech.imaapp.ui.common.simpleVerticalScrollbar
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import com.zenitech.imaapp.ui.utils.validation.ValidationError
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val spacerHeight = 20.dp

@Composable
fun RequestTestDeviceScreen(
    onNavigateToDeviceType: () -> Unit,
    onNavigateToDeviceManufacturer: () -> Unit,
    onNavigateTestDeviceSuccessful: () -> Unit,
    manufacturer: String,
    type: String,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        RequestTestDeviceContent(
            onNavigateToDeviceType = onNavigateToDeviceType,
            onNavigateToDeviceManufacturer = onNavigateToDeviceManufacturer,
            onNavigateTestDeviceSuccessful = onNavigateTestDeviceSuccessful,
            manufacturer = manufacturer,
            type = type,
        )
    }
}

@Composable
fun RequestTestDeviceContent(
    viewModel: RequestTestDeviceViewModel = hiltViewModel(),
    onNavigateToDeviceType: () -> Unit,
    onNavigateToDeviceManufacturer: () -> Unit,
    onNavigateTestDeviceSuccessful: () -> Unit,
    manufacturer: String?,
    type: String?,
) {
    val listState = rememberLazyListState()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val errors = remember {
        mutableStateOf(emptyList<ValidationError?>())
    }

    LaunchedEffect(key1 = state) {
        when (state) {
            is RequestTestDeviceState.Success -> {
                onNavigateTestDeviceSuccessful()
            }
            is RequestTestDeviceState.Failure -> {
                errors.value = (state as RequestTestDeviceState.Failure).error
            }
            else -> {}
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.simpleVerticalScrollbar(
            state = listState,
            color = LocalCardColorsPalette.current.containerColor
        )
    ) {
        item {
            RequestDeviceInputFields(
                onEvent = { viewModel.onEvent(it) },
                onNavigateToDeviceType = onNavigateToDeviceType,
                onNavigateToDeviceManufacturer = onNavigateToDeviceManufacturer,
                manufacturer = manufacturer,
                type = type,
                errors = errors.value
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            RequestTestDeviceSendRequestButton(
                onClick = { viewModel.onEvent(RequestTestDeviceUserEvent.SaveRequest) }
            )
        }
    }

}


@Composable
fun RequestDeviceInputFields(
    onNavigateToDeviceManufacturer: () -> Unit,
    onNavigateToDeviceType: () -> Unit,
    type: String?,
    manufacturer: String?,
    errors: List<ValidationError?>,
    onEvent: (RequestTestDeviceUserEvent) -> Unit
) {
    Column {
        Section(title = stringResource(id = R.string.device_details)) {
            RequestTestDeviceDeviceTypeInput(
                onNavigateToDeviceType = onNavigateToDeviceType,
                onRequestTestDeviceTypeChange = { text ->
                    onEvent(RequestTestDeviceUserEvent.ChangeDeviceType(text))
                },
                type = type,
                errors = errors
            )
            Spacer(modifier = Modifier.height(spacerHeight))

            RequestTestDeviceDeviceManufacturerInput(
                onNavigateToDeviceManufacturer = onNavigateToDeviceManufacturer,
                onRequestTestDeviceManufacturerChange = { text ->
                    onEvent(RequestTestDeviceUserEvent.ChangeDeviceManufacturer(text))
                },
                manufacturer = manufacturer,
                errors = errors
            )
        }

        HorizontalDivider(color = LocalCardColorsPalette.current.borderColor)
        Section(title = stringResource(R.string.date_details)) {
            RequestTestDeviceRequestDateInput(
                onRequestTestDeviceRequestDateChange = {
                    onEvent(RequestTestDeviceUserEvent.ChangeRequestDate(it))
                },
            )
            Spacer(modifier = Modifier.height(spacerHeight))
            RequestTestDeviceReturnDateInput(
                onRequestTestDeviceReturnDateChange = {
                    onEvent(RequestTestDeviceUserEvent.ChangeReturnDate(it))
                },
            )
        }

        HorizontalDivider(color = LocalCardColorsPalette.current.borderColor)
        Section(title = stringResource(R.string.optional_details)) {
            RequestTestDeviceAdditionalRequestsInput(
                onTestDeviceAdditionalRequestsChange = {
                    onEvent(RequestTestDeviceUserEvent.ChangeAdditionalRequests(it))
                },
            )
        }

        HorizontalDivider(color = LocalCardColorsPalette.current.borderColor)
    }
}

@Composable
fun Section(
    title: String,
    content: @Composable () -> Unit
) {
    Column(Modifier.padding(horizontal = 15.dp, vertical = 20.dp)) {
        Text(title, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        content()
    }
}

@Composable
fun RequestTestDeviceSendRequestButton(
    onClick: () -> Unit
) {
    Box(modifier = Modifier.padding(start = 15.dp, bottom = 15.dp, end = 15.dp)) {
        SecondaryButton(onClick = onClick) {
            Text(stringResource(R.string.send_request), fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun RequestTestDeviceDeviceTypeInput(
    onNavigateToDeviceType: () -> Unit,
    type: String?,
    onRequestTestDeviceTypeChange: (String) -> Unit,
    errors: List<ValidationError?>
) {
    LaunchedEffect(type) {
        if (type != null) {
            onRequestTestDeviceTypeChange(type)
        }
    }

    RequestDeviceInput(
        title = stringResource(R.string.device_type),
        value = {
            Icon(imageVector = Icons.TwoTone.Devices, contentDescription = null, tint = LocalCardColorsPalette.current.borderColor)
            Spacer(modifier = Modifier.width(10.dp))
            type?.let {
                Text(it.ifBlank { "" },
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold)
            } },
        onClick = { onNavigateToDeviceType() },
        isError = errors.any { it?.property == "type" }
    )
}

@Composable
fun RequestTestDeviceReturnDateInput(
    onRequestTestDeviceReturnDateChange: (String) -> Unit,
) {
    RequestDeviceDateInput(
        type = stringResource(R.string.return_date),
        onValueChange = onRequestTestDeviceReturnDateChange,
    )
}

@Composable
fun RequestTestDeviceAdditionalRequestsInput(
    onTestDeviceAdditionalRequestsChange: (String) -> Unit,
) {
    val additionalRequestsField = rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        RequestDeviceInput(
            onClick = { focusRequester.requestFocus() },
            title = stringResource(R.string.additional_requests),
            value = {
                Icon(
                    imageVector = Icons.TwoTone.Textsms,
                    contentDescription = null,
                    tint = LocalCardColorsPalette.current.borderColor
                )
                Spacer(modifier = Modifier.width(10.dp))
                PrimaryBasicTextField(
                    focusRequester = focusRequester,
                    focusManager = focusManager,
                    value = additionalRequestsField.value,
                    onValueChanged = {
                        additionalRequestsField.value = it
                        onTestDeviceAdditionalRequestsChange(additionalRequestsField.value)
                    })
            },
            showArrowDown = false,
        )
    }
}

@Composable
fun RequestTestDeviceRequestDateInput(
    onRequestTestDeviceRequestDateChange: (String) -> Unit,
) {
    RequestDeviceDateInput(
        type = stringResource(R.string.request_date),
        onValueChange = onRequestTestDeviceRequestDateChange
    )
}

@Composable
fun RequestDeviceDateInput(
    type: String,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    RequestDeviceInput(
        title = type,
        value = {
            RequestTestDeviceDatePickerWithDialog(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                onValueChange = onValueChange
            )
        },
        onClick = { expanded = !expanded },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestTestDeviceDatePickerWithDialog(
    expanded: Boolean,
    onExpandedChange: () -> Unit,
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
                onDismissRequest = onExpandedChange,
                confirmButton = {
                    Button(onClick = {
                        onExpandedChange()
                        onValueChange(dateToString)
                    }) { Text(text = stringResource(R.string.ok)) }
                },
                dismissButton = {
                    Button(onClick = onExpandedChange) { Text(text = stringResource(R.string.cancel)) }
                }
            ) {
                DatePicker(state = dateState, showModeToggle = true)
            }
        }
    }
}

@Composable
fun RequestTestDeviceDeviceManufacturerInput(
    onNavigateToDeviceManufacturer: () -> Unit,
    manufacturer: String?,
    onRequestTestDeviceManufacturerChange: (String) -> Unit,
    errors: List<ValidationError?>
) {
    LaunchedEffect(manufacturer) {
        if (manufacturer != null) {
            onRequestTestDeviceManufacturerChange(manufacturer)
        }
    }

    RequestDeviceInput(
        title = stringResource(R.string.device_manufacturer),
        value = {
            Icon(imageVector = Icons.TwoTone.Factory, contentDescription = null, tint = LocalCardColorsPalette.current.borderColor)
            Spacer(modifier = Modifier.width(10.dp))
            manufacturer?.let { Text(it.ifBlank { "" },
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold)
            } },
        onClick = { onNavigateToDeviceManufacturer() },
        isError = errors.any { it?.property == "manufacturer" }
    )
}

@Composable
fun RequestDeviceInput(
    title: String,
    value: @Composable () -> Unit,
    onClick: () -> Unit = {},
    showArrowDown: Boolean = true,
    isError: Boolean = false
) {
    PrimaryInputField(
        label = title,
        value = { value() },
        onClick = { onClick() },
        showArrowDown = showArrowDown,
        isError = isError,

    )
}

