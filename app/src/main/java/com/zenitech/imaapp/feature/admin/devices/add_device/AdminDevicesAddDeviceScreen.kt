package com.zenitech.imaapp.feature.admin.devices.add_device

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.common.PrimaryBasicTextField
import com.zenitech.imaapp.ui.common.PrimaryButton
import com.zenitech.imaapp.ui.common.PrimaryInputField
import com.zenitech.imaapp.ui.common.SecondaryButton
import com.zenitech.imaapp.ui.theme.IMAAppTheme
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import com.zenitech.imaapp.ui.utils.validation.ValidationError
import kotlinx.coroutines.launch

@Composable
@Preview(showBackground = true)
fun PreviewAdminDevicesAddDeviceNavigationButtons() {
    IMAAppTheme {
        AdminDevicesAddDeviceNavigationButtons(
            pagerState = rememberPagerState(pageCount = { 4 }),
            currentPage = 2
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAdminDevicesAddDeviceInputField() {
    AdminDevicesAddDeviceInputField(
        label = "Label",
        value = { Text(text = "Value") },
        onClick = null
    )
}

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
            Text(text = stringResource(R.string.back))
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
            Text(text = stringResource(R.string.next))
        }
    } else {
        SecondaryButton(
            onClick = {
                if (onSave != null) {
                    onSave()
                }
        }) {
            Text(text = stringResource(R.string.add_device))
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
fun AdminDevicesAddDeviceRowDivider() {
    Spacer(modifier = Modifier.height(10.dp))
    HorizontalDivider(color = LocalCardColorsPalette.current.borderColor)
    Spacer(modifier = Modifier.height(10.dp))
}
