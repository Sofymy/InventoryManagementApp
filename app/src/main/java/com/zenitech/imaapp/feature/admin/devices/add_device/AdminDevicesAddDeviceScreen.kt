package com.zenitech.imaapp.feature.admin.devices.add_device

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenitech.imaapp.R
import com.zenitech.imaapp.feature.sign_in.SignInAnimatedBackground
import com.zenitech.imaapp.feature.sign_in.SignInContent
import com.zenitech.imaapp.ui.common.PrimaryButton
import com.zenitech.imaapp.ui.common.PrimaryInputField
import com.zenitech.imaapp.ui.common.SecondaryButton
import com.zenitech.imaapp.ui.theme.IMAAppTheme
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import com.zenitech.imaapp.ui.utils.validation.ValidationError
import kotlinx.coroutines.launch
import kotlin.time.Duration

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
        onClick = null,
        isError = false
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AdminDevicesAddDeviceScreen(
    onNavigateToAdminDeviceAssets: () -> Unit,
    asset: String,
    onNavigateToAdminDeviceTypes: () -> Unit,
    type: String,
    onNavigateToAdminDeviceManufacturers: () -> Unit,
    manufacturer: String,
    onNavigateToAdminDeviceSites: () -> Unit,
    site: String,
    onNavigateToAdminAddDeviceSuccessful: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {
        Column(modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxSize()) {
            AdminDevicesAddDeviceContent(
                onNavigateToAdminDeviceTypes = onNavigateToAdminDeviceTypes,
                type = type,
                onNavigateToAdminDeviceManufacturers = onNavigateToAdminDeviceManufacturers,
                manufacturer = manufacturer,
                onNavigateToAdminDeviceAssets = onNavigateToAdminDeviceAssets,
                asset = asset,
                onNavigateToAdminDeviceSites = onNavigateToAdminDeviceSites,
                site = site,
                onNavigateToAdminAddDeviceSuccessful = onNavigateToAdminAddDeviceSuccessful,
                snackBarHostState = snackBarHostState
            )
        }
    }
}

@Composable
fun AdminDevicesAddDeviceContent(
    snackBarHostState: SnackbarHostState,
    viewModel: AdminDevicesAddDeviceViewModel = hiltViewModel(),
    onNavigateToAdminDeviceAssets: () -> Unit,
    asset: String,
    onNavigateToAdminDeviceTypes: () -> Unit,
    type: String,
    onNavigateToAdminDeviceManufacturers: () -> Unit,
    manufacturer: String,
    onNavigateToAdminDeviceSites: () -> Unit,
    site: String,
    onNavigateToAdminAddDeviceSuccessful: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 4 })
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

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
                scope.launch {
                    snackBarHostState.showSnackbar("Required field is empty.")
                }
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
                errors = errors.value,
                page = page,
                pagerState = pagerState,
                onNavigateToAdminDeviceTypes = onNavigateToAdminDeviceTypes,
                type = type,
                onNavigateToAdminDeviceManufacturers = onNavigateToAdminDeviceManufacturers,
                manufacturer = manufacturer,
                onNavigateToAdminDeviceAssets = onNavigateToAdminDeviceAssets,
                asset = asset,
                onNavigateToAdminDeviceSites = onNavigateToAdminDeviceSites,
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
    onNavigateToAdminDeviceAssets: () -> Unit,
    asset: String,
    onNavigateToAdminDeviceTypes: () -> Unit,
    type: String,
    onNavigateToAdminDeviceManufacturers: () -> Unit,
    manufacturer: String,
    onNavigateToAdminDeviceSites: () -> Unit,
    site: String,
    pagerState: PagerState,
    onSave: (() -> Unit)?,
    errors: List<ValidationError?>
) {
    when (page) {
        0 -> AdminDevicesAddDeviceGeneral(
            errors = errors,
            onChange = onChange,
            pagerState = pagerState,
            page = page,
            onNavigateToAdminDeviceTypes = onNavigateToAdminDeviceTypes,
            type = type,
            onNavigateToAdminDeviceManufacturers = onNavigateToAdminDeviceManufacturers,
            manufacturer = manufacturer,
            onNavigateToAdminDeviceAssets = onNavigateToAdminDeviceAssets,
            asset = asset,
        )
        1 -> AdminDevicesAddDeviceDates(
            errors = errors,
            onChange = onChange,
            pagerState = pagerState,
            page = page,
        )
        2 -> AdminDevicesAddDeviceStatus(
            errors = errors,
            onChange = onChange,
            pagerState = pagerState,
            page = page,
        )
        else -> AdminDevicesAddDeviceLocation(
            errors = errors,
            onChange = onChange,
            pagerState = pagerState,
            page = page,
            site = site,
            onNavigateToAdminDeviceSites = onNavigateToAdminDeviceSites,
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
    showArrowDown: Boolean = true,
    isError: Boolean
) {
    PrimaryInputField(
        label = label,
        value = {
            value()
        },
        isError = isError,
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
