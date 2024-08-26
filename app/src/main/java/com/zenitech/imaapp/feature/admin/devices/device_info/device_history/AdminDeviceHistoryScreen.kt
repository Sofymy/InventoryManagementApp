@file:OptIn(ExperimentalMaterial3Api::class)

package com.zenitech.imaapp.feature.admin.devices.device_info.device_history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CalendarMonth
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenitech.imaapp.feature.admin.devices.device_info.AdminDeviceInfoButton
import com.zenitech.imaapp.ui.common.CircularLoadingIndicator
import com.zenitech.imaapp.ui.model.HistoryResponseUi
import com.zenitech.imaapp.ui.theme.IMAAppTheme
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Preview(showBackground = true)
@Composable
fun AdminDeviceHistoryScreenPreview() {
    val mockedDeviceHistory = listOf(
        HistoryResponseUi(
            timestamp = "2024-08-26",
            action = "Lease",
            modifierEmail = "admin1@zenitech.co.uk",
            description = "Leased device to user 1.",
            actorEmail = "user1@zenitech.co.uk",
        ),
        HistoryResponseUi(
            timestamp = "2024-08-25",
            action = "Modify",
            modifierEmail = "admin2@zenitech.co.uk",
            description = "Modified device condition.",
            actorEmail = "user1@zenitech.co.uk",
        ),
        HistoryResponseUi(
            timestamp = "2024-08-24",
            action = "Update",
            modifierEmail = "admin1@zenitech.co.uk",
            description = "",
            actorEmail = null
        )
    )

    IMAAppTheme {
        AdminDeviceHistoryList(deviceHistory = mockedDeviceHistory)
    }
}

@Composable
fun AdminDeviceHistoryScreen(inventoryId: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AdminDeviceHistoryContent(inventoryId = inventoryId)
    }
}

@Composable
fun AdminDeviceHistoryContent(
    viewModel: AdminDeviceHistoryViewModel = hiltViewModel(),
    inventoryId: String
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) viewModel.loadDeviceHistory(inventoryId)
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    when (val currentState = state) {
        is AdminDeviceHistoryState.Error -> Text(text = currentState.error.message.toString())
        is AdminDeviceHistoryState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularLoadingIndicator()
        }
        is AdminDeviceHistoryState.Success -> AdminDeviceHistoryList(deviceHistory = currentState.deviceHistory)
    }
}

@Composable
fun AdminDeviceHistoryList(deviceHistory: List<HistoryResponseUi>) {
    var isCalendarExpanded by remember { mutableStateOf(false) }
    val dateRangeState = rememberDateRangePickerState(initialDisplayMode = DisplayMode.Picker)
    val isCalendarValueSet = remember(deviceHistory, dateRangeState.selectedStartDateMillis, dateRangeState.selectedEndDateMillis) {
        mutableStateOf(dateRangeState.selectedStartDateMillis != null || dateRangeState.selectedEndDateMillis != null)
    }

    val filteredHistory = remember(deviceHistory, dateRangeState.selectedStartDateMillis, dateRangeState.selectedEndDateMillis) {
        deviceHistory.filter {
            val timestampMillis = stringToDateMillis(it.timestamp)
            val startMillis = dateRangeState.selectedStartDateMillis ?: 0
            val endMillis = dateRangeState.selectedEndDateMillis ?: Long.MAX_VALUE
            timestampMillis in startMillis..endMillis
        }
    }

    Column {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(end = 15.dp)
                .fillMaxWidth()
        ) {
            AdminDeviceInfoButton(
                icon = Icons.TwoTone.CalendarMonth,
                tint = if(isCalendarValueSet.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
            ) {
                isCalendarExpanded = !isCalendarExpanded
            }
            AnimatedVisibility(visible = isCalendarValueSet.value) {
                AdminDeviceInfoButton(
                    icon = Icons.TwoTone.Clear,
                ) {
                    dateRangeState.setSelection(null, null)
                }
            }
        }

        AnimatedVisibility(isCalendarExpanded) {
            DateRangePicker(state = dateRangeState)
        }

        LazyColumn {
            items(filteredHistory) { item ->
                AdminDeviceHistoryListItem(item)
            }
        }
    }
}

@Composable
fun AdminDeviceHistoryListItem(item: HistoryResponseUi) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(LocalCardColorsPalette.current.containerColor)
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 20.dp)
        ) {
            AdminDeviceHistoryListItemElement(text = item.timestamp, Modifier.weight(1f))
            AdminDeviceHistoryListItemElement(text = item.action, Modifier.weight(1f))
            AdminDeviceHistoryListItemElement(text = item.modifierEmail, Modifier.weight(2f), MaterialTheme.typography.bodySmall)
        }
        HorizontalDivider(thickness = 1.dp, color = LocalCardColorsPalette.current.borderColor)
    }
}

@Composable
fun AdminDeviceHistoryListItemElement(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge
) {
    Text(text = text, modifier = modifier, style = style)
}

fun stringToDateMillis(dateString: String): Long {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val localDate = LocalDate.parse(dateString, formatter)
    return localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
}
