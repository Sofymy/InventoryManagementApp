@file:OptIn(ExperimentalMaterialApi::class)

package com.zenitech.imaapp.feature.admin.devices.devices

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.common.CircularLoadingIndicator
import com.zenitech.imaapp.ui.model.DeviceSearchRequestUi
import com.zenitech.imaapp.ui.theme.IMAAppTheme
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import kotlinx.coroutines.launch
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Preview(showBackground = true)
@Composable
fun AdminDevicesTabLayoutPreview() {
    val mockedPullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = { }
    )

    IMAAppTheme {
        AdminDevicesTabLayout(
            state = AdminDevicesState.Success(listOf(DeviceSearchRequestUi())),
            onNavigateToAdminDeviceInfo = {},
            pullRefreshState = mockedPullRefreshState,
            onClickAddDevice = {},
            onClickExport = {}
        )
    }
}

@Composable
fun AdminDevicesScreen(
    onNavigateToAdminDeviceInfo: (String) -> Unit,
    onNavigateToAdminDevicesAddDevice: () -> Unit
) {
    AdminDevicesScreenContent(
        onNavigateToAdminDeviceInfo = onNavigateToAdminDeviceInfo,
        onNavigateToAdminDevicesAddDevice = onNavigateToAdminDevicesAddDevice,
    )
}

@Composable
fun AdminDevicesScreenContent(
    viewModel: AdminDevicesViewModel = hiltViewModel(),
    onNavigateToAdminDeviceInfo: (String) -> Unit,
    onNavigateToAdminDevicesAddDevice: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state == AdminDevicesState.Loading::isRefresh,
        onRefresh = {
            viewModel.onEvent(AdminDevicesUserEvent.Refresh)
        }
    )
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            val fileName = "IMADeviceListReport.xlsx"
            val externalStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            val file = File(externalStorageDir, fileName)

            if (!file.exists()) {
                file.createNewFile()
            }

            generateExcelReport((state as AdminDevicesState.Success).adminDevices, file.absolutePath)
            shareFile(context, file)
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadAdminDevices()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column {
        AdminDevicesTabLayout(
            state = state,
            pullRefreshState = pullRefreshState,
            onClickAddDevice = onNavigateToAdminDevicesAddDevice,
            onNavigateToAdminDeviceInfo = onNavigateToAdminDeviceInfo,
            onClickExport = {
                when {
                    ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                        val fileName = "IMADeviceListReport.xlsx"
                        val externalStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                        val file = File(externalStorageDir, fileName)

                        if (!file.exists()) {
                            file.createNewFile()
                        }

                        generateExcelReport((state as AdminDevicesState.Success).adminDevices, file.absolutePath)
                        shareFile(context, file)
                    }
                    else -> {
                        permissionLauncher.launch(WRITE_EXTERNAL_STORAGE)
                    }
                }
            }
        )
    }
}

@Composable
fun AdminDevicesTabLayout(
    state: AdminDevicesState,
    onNavigateToAdminDeviceInfo: (String) -> Unit,
    pullRefreshState: PullRefreshState,
    onClickAddDevice: () -> Unit,
    onClickExport: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 5 })
    val pagerTabs = createAdminDevicesPagerTabs()
    val searchQuery = remember { mutableStateOf("") }
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    val scope = rememberCoroutineScope()
    val tagsExpanded = remember { mutableStateOf(false) }
    val selectedTags = remember { mutableStateListOf<String>() }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            AdminDevicesIcons(
                onSearchValueChanged = {
                    searchQuery.value = it
                },
                onClickTags = {
                    tagsExpanded.value = !tagsExpanded.value
                },
                onClickExport = onClickExport,
                onClickAddDevice = onClickAddDevice,
                state = state
            )
        }
        AnimatedVisibility(
            visible = tagsExpanded.value,
        ){
            Column {
                HorizontalDivider(
                    color = LocalCardColorsPalette.current.borderColor,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                AdminDevicesTags(
                    selectedTags = selectedTags.toList(),
                    onSelectedTagsChanged = {
                        if(selectedTags.contains(it))
                            selectedTags.remove(it)
                        else
                            selectedTags.add(it)
                    }
                )
            }
        }
        HorizontalDivider(
            color = LocalCardColorsPalette.current.borderColor,
            modifier = Modifier.padding(vertical = 20.dp)
        )
        AdminDevicesTabs(
            currentPage = selectedTabIndex.value,
            onClick = {
               scope.launch {
                   pagerState.scrollToPage(it)
                   // animateScrollToPage is lagging :(
                   // pagerState.animateScrollToPage(it)
               }
            },
            pagerTabs = pagerTabs
        )
        AdminDevicesTabContent(
            state = state,
            pagerState = pagerState,
            pagerTabs = pagerTabs,
            searchQuery = searchQuery.value,
            onNavigateToAdminDeviceInfo = onNavigateToAdminDeviceInfo,
            pullRefreshState = pullRefreshState
        )
    }
}

@Composable
fun AdminDevicesTabs(
    pagerTabs: List<AdminDevicesPagerTab>,
    currentPage: Int,
    onClick: (Int) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = currentPage,
        indicator = { tabPositions ->
            if (currentPage < tabPositions.size) {
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[currentPage]),
                    height = 5.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        divider = {},
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        pagerTabs.forEachIndexed { index, tab ->
            Tab(
                selectedContentColor = MaterialTheme.colorScheme.onBackground,
                unselectedContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                text = { Text(tab.title) },
                selected = currentPage == index,
                onClick = {
                    onClick(index)
                }
            )
        }
    }
}

@Composable
fun AdminDevicesTabContent(
    state: AdminDevicesState,
    pagerState: PagerState,
    pagerTabs: List<AdminDevicesPagerTab>,
    searchQuery: String,
    onNavigateToAdminDeviceInfo: (String) -> Unit,
    pullRefreshState: PullRefreshState
) {
    when (state) {
        is AdminDevicesState.Error -> AdminDevicesError(state.error.message.toString())
        is AdminDevicesState.Loading -> AdminDevicesLoading()
        is AdminDevicesState.Success -> {
            AdminDevicesTabsContent(
                pagerState = pagerState,
                pagerTabs = pagerTabs,
                isRefreshing = state == AdminDevicesState.Loading::isRefresh,
                adminDevices = state.adminDevices,
                searchQuery = searchQuery,
                pullRefreshState = pullRefreshState,
                onNavigateToAdminDeviceInfo = onNavigateToAdminDeviceInfo,
            )
        }
    }
}

@Composable
fun AdminDevicesError(message: String) {
    Text(message)
}

@Composable
fun AdminDevicesLoading() {
    CircularLoadingIndicator()
}

@Composable
fun AdminDevicesTabsContent(
    pagerState: PagerState,
    adminDevices: List<DeviceSearchRequestUi>,
    pagerTabs: List<AdminDevicesPagerTab>,
    searchQuery: String,
    onNavigateToAdminDeviceInfo: (String) -> Unit,
    pullRefreshState: PullRefreshState,
    isRefreshing: Boolean
) {
    val adminFields = listOf(
        stringResource(R.string.type),
        stringResource(R.string.asset_name),
        stringResource(R.string.manufacturer),
        stringResource(R.string.serial_number),
        stringResource(R.string.supplier),
        stringResource(R.string.invoice_number),
        stringResource(R.string.condition),
        stringResource(R.string.status),
        stringResource(R.string.lease_start_date),
        stringResource(R.string.lease_end_date),
        stringResource(R.string.user_name),
        stringResource(R.string.site),
        stringResource(R.string.location),
        stringResource(R.string.is_test_device)
    )

    var selectedField by remember { mutableStateOf(adminFields[0]) }

    HorizontalPager(
        state = pagerState,
        userScrollEnabled = true
    ) { page ->
        val selectedTab = pagerTabs[page]

        val filteredDevices by remember(adminDevices, page, selectedField, searchQuery) {
            derivedStateOf {
                filterDevices(adminDevices, page, selectedField, searchQuery)
            }
        }

        AdminDevicesDeviceList(
            isRefreshing = isRefreshing,
            filteredDevices = filteredDevices,
            adminFields = adminFields,
            selectedField = selectedField,
            onFieldSelected = { selectedField = it },
            selectedTab = selectedTab,
            pullRefreshState = pullRefreshState,
            onNavigateToAdminDeviceInfo = onNavigateToAdminDeviceInfo
        )
    }
}

@Composable
fun createAdminDevicesPagerTabs(): List<AdminDevicesPagerTab> = listOf(
    AdminDevicesPagerTab.All,
    AdminDevicesPagerTab.InStock,
    AdminDevicesPagerTab.Leased,
    AdminDevicesPagerTab.UnderRepair,
    AdminDevicesPagerTab.Test
)

fun generateExcelReport(data: List<DeviceSearchRequestUi>, filePath: String) {
    val workbook = XSSFWorkbook()
    val sheet = workbook.createSheet("Device Report")

    val headerRow = sheet.createRow(0)
    val headers = listOf(
        "Inventory ID", "Type", "Asset Name", "Manufacturer", "Serial Number",
        "Supplier", "Invoice Number", "Shipment Date Begin", "Shipment Date End",
        "Warranty Begin", "Warranty End", "Condition", "Status",
        "Lease Start Date", "Lease End Date", "User Name", "Site",
        "Location", "Tags", "Is Test Device"
    )

    headers.forEachIndexed { index, header ->
        val cell = headerRow.createCell(index)
        cell.setCellValue(header)
    }

    data.forEachIndexed { rowIndex, device ->
        val row = sheet.createRow(rowIndex + 1)
        row.createCell(0).setCellValue(device.inventoryId)
        row.createCell(1).setCellValue(device.type)
        row.createCell(2).setCellValue(device.assetName.label)
        row.createCell(3).setCellValue(device.manufacturer)
        row.createCell(4).setCellValue(device.serialNumber)
        row.createCell(5).setCellValue(device.supplier)
        row.createCell(6).setCellValue(device.invoiceNumber)
        row.createCell(7).setCellValue(device.shipmentDateBegin)
        row.createCell(8).setCellValue(device.shipmentDateEnd)
        row.createCell(9).setCellValue(device.warrantyBegin)
        row.createCell(10).setCellValue(device.warrantyEnd)
        row.createCell(11).setCellValue(device.condition.label)
        row.createCell(12).setCellValue(device.status.toString())
        row.createCell(13).setCellValue(device.leaseStartDate)
        row.createCell(14).setCellValue(device.leaseEndDate)
        row.createCell(15).setCellValue(device.userName)
        row.createCell(16).setCellValue(device.site)
        row.createCell(17).setCellValue(device.location)
        row.createCell(18).setCellValue(device.tags.joinToString(", "))
        row.createCell(19).setCellValue(device.isTestDevice.toString())
    }


    try {
        FileOutputStream(filePath).use { outputStream ->
            workbook.write(outputStream)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            workbook.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

fun shareFile(context: Context, file: File) {
    val uri: Uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(Intent.createChooser(intent, "Share file"))
}

sealed class AdminDevicesPagerTab(
    val title: String,
    val padding: PaddingValues,
    val shape: RoundedCornerShape
) {
    data object All : AdminDevicesPagerTab("All", PaddingValues(start = 15.dp), RoundedCornerShape(topStart = 15.dp))
    data object InStock : AdminDevicesPagerTab("In stock", PaddingValues(0.dp), RoundedCornerShape(0.dp))
    data object Leased : AdminDevicesPagerTab("Leased", PaddingValues(0.dp), RoundedCornerShape(0.dp))
    data object UnderRepair : AdminDevicesPagerTab("Under repair", PaddingValues(0.dp), RoundedCornerShape(0.dp))
    data object Test : AdminDevicesPagerTab("Test", PaddingValues(end = 15.dp), RoundedCornerShape(topEnd = 15.dp))
}