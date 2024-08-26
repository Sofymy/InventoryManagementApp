package com.zenitech.imaapp.feature.admin.devices.device_info

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.zenitech.imaapp.feature.admin.devices.device_info.device_details.AdminDeviceDetailsScreen
import com.zenitech.imaapp.feature.admin.devices.device_info.device_history.AdminDeviceHistoryScreen
import com.zenitech.imaapp.ui.common.pulsate
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import kotlinx.coroutines.launch

@Composable
fun AdminDeviceInfoScreen(
    inventoryId: String,
    onNavigateToAdminDeviceTypes: () -> Unit,
    onNavigateToAdminDeviceManufacturers: () -> Unit,
    onNavigateToAdminDeviceAssets: () -> Unit,
    onNavigateToAdminDeviceSites: () -> Unit,
    site: String?,
    manufacturer: String?,
    type: String?,
    asset: String?
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AdminDeviceInfoContent(
            inventoryId = inventoryId,
            onNavigateToAdminDeviceSites = onNavigateToAdminDeviceSites,
            onNavigateToAdminDeviceAssets = onNavigateToAdminDeviceAssets,
            onNavigateToAdminDeviceTypes = onNavigateToAdminDeviceTypes,
            onNavigateToAdminDeviceManufacturers = onNavigateToAdminDeviceManufacturers,
            site = site,
            manufacturer = manufacturer,
            type = type,
            asset = asset
        )
    }
}

@Composable
fun AdminDeviceInfoContent(
    inventoryId: String,
    onNavigateToAdminDeviceTypes: () -> Unit,
    onNavigateToAdminDeviceManufacturers: () -> Unit,
    onNavigateToAdminDeviceAssets: () -> Unit,
    onNavigateToAdminDeviceSites: () -> Unit,
    site: String?,
    manufacturer: String?,
    type: String?,
    asset: String?
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val pagerTabs = createAdminDeviceInfoPagerTabs()
    val scope = rememberCoroutineScope()

    AdminDeviceInfoTabs(
        pagerTabs = pagerTabs,
        currentPage = pagerState.currentPage,
        onClick = {
            scope.launch {
                pagerState.scrollToPage(it)
            }
        }
    )

    HorizontalPager(
        state = pagerState
    ) { page ->
        Column(modifier = Modifier.fillMaxSize()) {
            AdminDeviceInfoDisplayPageContent(
                page = page,
                inventoryId = inventoryId,
                onNavigateToAdminDeviceSites = onNavigateToAdminDeviceSites,
                onNavigateToAdminDeviceAssets = onNavigateToAdminDeviceAssets,
                onNavigateToAdminDeviceTypes = onNavigateToAdminDeviceTypes,
                onNavigateToAdminDeviceManufacturers = onNavigateToAdminDeviceManufacturers,
                site = site,
                manufacturer = manufacturer,
                type = type,
                asset = asset
            )
        }
    }

}

@Composable
fun AdminDeviceInfoTabs(
    pagerTabs: List<AdminDeviceInfoPagerTab>,
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
fun AdminDeviceInfoDisplayPageContent(
    page: Int,
    inventoryId: String,
    onNavigateToAdminDeviceTypes: () -> Unit,
    onNavigateToAdminDeviceManufacturers: () -> Unit,
    onNavigateToAdminDeviceAssets: () -> Unit,
    onNavigateToAdminDeviceSites: () -> Unit,
    site: String?,
    manufacturer: String?,
    type: String?,
    asset: String?
) {
    when (page) {
        0 -> AdminDeviceDetailsScreen(
            inventoryId = inventoryId,
            onNavigateToAdminDeviceSites = onNavigateToAdminDeviceSites,
            onNavigateToAdminDeviceAssets = onNavigateToAdminDeviceAssets,
            onNavigateToAdminDeviceTypes = onNavigateToAdminDeviceTypes,
            onNavigateToAdminDeviceManufacturers = onNavigateToAdminDeviceManufacturers,
            site = site,
            manufacturer = manufacturer,
            type = type,
            asset = asset
        )
        1 -> AdminDeviceHistoryScreen(
            inventoryId = inventoryId
        )
    }
}

@Composable
fun AdminDeviceInfoButton(
    icon: ImageVector,
    tint: Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .padding(vertical = 20.dp)
            .height(IntrinsicSize.Max) ,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .pulsate()
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = null
                ) {
                    onClick()
                }
                .border(
                    width = 1.dp,
                    color = LocalCardColorsPalette.current.borderColor,
                    shape = RoundedCornerShape(20.dp)
                )
                .background(
                    color = LocalCardColorsPalette.current.containerColor,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                tint = tint,
                contentDescription = null,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
fun createAdminDeviceInfoPagerTabs(): List<AdminDeviceInfoPagerTab> = listOf(
    AdminDeviceInfoPagerTab.Details,
    AdminDeviceInfoPagerTab.History,
)

sealed class AdminDeviceInfoPagerTab(
    val title: String,
) {
    data object Details : AdminDeviceInfoPagerTab("Details")
    data object History : AdminDeviceInfoPagerTab("History",)
}
