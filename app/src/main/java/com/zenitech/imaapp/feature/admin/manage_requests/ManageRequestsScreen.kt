@file:OptIn(ExperimentalMaterialApi::class)

package com.zenitech.imaapp.feature.admin.manage_requests

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenitech.imaapp.feature.admin.devices.devices.AdminDevicesState
import com.zenitech.imaapp.ui.common.CircularLoadingIndicator
import com.zenitech.imaapp.ui.model.RequestStatusUi
import com.zenitech.imaapp.ui.model.TestDeviceRequestUi
import com.zenitech.imaapp.ui.theme.IMAAppTheme
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun ManageRequestsTabLayoutPreview() {
    val mockedPullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = { }
    )

    IMAAppTheme {
        ManageRequestsTabLayout(
            state = ManageRequestsState.Success(listOf(TestDeviceRequestUi())),
            pullRefreshState = mockedPullRefreshState,
            onClickReject = { },
            onClickApprove = { }
        )
    }
}

@Composable
fun ManageRequestsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ManageRequestsContent()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ManageRequestsContent(
    viewModel: ManageRequestsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state == ManageRequestsState.Loading::isRefresh,
        onRefresh = {
            viewModel.onEvent(ManageRequestsUserEvent.Refresh)
        }
    )

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadRequests()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column {
        ManageRequestsTabLayout(
            state = state,
            pullRefreshState = pullRefreshState,
            onClickApprove = { viewModel.onEvent(ManageRequestsUserEvent.ApproveRequest(it)) },
            onClickReject = { viewModel.onEvent(ManageRequestsUserEvent.RejectRequest(it)) }
        )
    }
}

@Composable
fun ManageRequestsTabLayout(
    onClickApprove: (String) -> Unit,
    onClickReject: (String) -> Unit,
    state: ManageRequestsState,
    pullRefreshState: PullRefreshState,
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val pagerTabs = createPagerTabs()
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        ManageRequestsTabs(
            currentPage = selectedTabIndex.value,
            onClick = {
                scope.launch {
                    pagerState.scrollToPage(it)
                }
            },
            pagerTabs = pagerTabs
        )
        ManageRequestsTabContent(
            state = state,
            pagerState = pagerState,
            pagerTabs = pagerTabs,
            pullRefreshState = pullRefreshState,
            onClickReject = onClickReject,
            onClickApprove = onClickApprove
        )
    }
}

@Composable
fun ManageRequestsTabs(
    pagerTabs: List<ManageRequestsPagerTab>,
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
fun ManageRequestsTabContent(
    state: ManageRequestsState,
    pagerState: PagerState,
    pagerTabs: List<ManageRequestsPagerTab>,
    onClickApprove: (String) -> Unit,
    onClickReject: (String) -> Unit,
    pullRefreshState: PullRefreshState
) {
    when (state) {
        is ManageRequestsState.Error -> ManageRequestsError(state.error.message.toString())
        is ManageRequestsState.Loading -> ManageRequestsLoading()
        is ManageRequestsState.Success -> {
            ManageRequestsTabsContent(
                pagerState = pagerState,
                pagerTabs = pagerTabs,
                isRefreshing = state == AdminDevicesState.Loading::isRefresh,
                requests = state.requests,
                pullRefreshState = pullRefreshState,
                onClickReject = onClickReject,
                onClickApprove = onClickApprove
            )
        }
    }
}

@Composable
fun ManageRequestsError(message: String) {
    Text(message)
}

@Composable
fun ManageRequestsLoading() {
    CircularLoadingIndicator()
}

@Composable
fun ManageRequestsTabsContent(
    pagerState: PagerState,
    requests: List<TestDeviceRequestUi>,
    pagerTabs: List<ManageRequestsPagerTab>,
    pullRefreshState: PullRefreshState,
    onClickApprove: (String) -> Unit,
    onClickReject: (String) -> Unit,
    isRefreshing: Boolean
) {
    HorizontalPager(
        state = pagerState,
        userScrollEnabled = true
    ) { page ->

        val selectedTab = pagerTabs[page]
        val filteredRequests by remember {
            derivedStateOf {
                filterRequests(page, requests)
            }
        }

        ManageRequestsList(
            isRefreshing = isRefreshing,
            requests = filteredRequests,
            selectedTab = selectedTab,
            pullRefreshState = pullRefreshState,
            onClickApprove = onClickApprove,
            onClickReject = onClickReject
        )
    }
}

fun filterRequests(
    page: Int,
    requests: List<TestDeviceRequestUi>
): List<TestDeviceRequestUi> {
    return requests.filter { request ->
        when (page) {
            0 -> request.status == RequestStatusUi.PENDING
            1 -> request.status == RequestStatusUi.APPROVED
            2 -> request.status == RequestStatusUi.REJECTED
            else -> true
        }
    }
}

@Composable
fun createPagerTabs(): List<ManageRequestsPagerTab> = listOf(
    ManageRequestsPagerTab.Pending,
    ManageRequestsPagerTab.Approved,
    ManageRequestsPagerTab.Rejected,
)

sealed class ManageRequestsPagerTab(
    val title: String,
    val padding: PaddingValues,
    val shape: RoundedCornerShape
) {
    data object Pending : ManageRequestsPagerTab("Pending", PaddingValues(start = 15.dp), RoundedCornerShape(topStart = 15.dp))
    data object Approved : ManageRequestsPagerTab("Approved", PaddingValues(0.dp), RoundedCornerShape(0.dp))
    data object Rejected : ManageRequestsPagerTab("Rejected", PaddingValues(end = 15.dp), RoundedCornerShape(topEnd = 15.dp))
}