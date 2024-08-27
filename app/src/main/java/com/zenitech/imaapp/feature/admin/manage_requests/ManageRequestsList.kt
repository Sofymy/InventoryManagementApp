@file:OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)

package com.zenitech.imaapp.feature.admin.manage_requests

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.common.PrimaryButton
import com.zenitech.imaapp.ui.common.SecondaryButton
import com.zenitech.imaapp.ui.model.TestDeviceRequestUi
import com.zenitech.imaapp.ui.theme.IMAAppTheme
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette

@Preview(showBackground = true)
@Composable
fun ManageRequestListPreview(){
    val mockedPullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = { }
    )

    val mockedRequests = listOf(
        TestDeviceRequestUi(
            manufacturer = "Company A",
            asset = "Device 1",
            startDate = "2024-08-01",
            endDate = "2024-08-07",
            note = "First test device request"
        ),
        TestDeviceRequestUi(
            manufacturer = "Company B",
            asset = "Device 2",
            startDate = "2024-08-08",
            endDate = "2024-08-14",
            note = "Second test device request"
        ),
        TestDeviceRequestUi(
            manufacturer = "Company C",
            asset = "Device 3",
            startDate = "2024-08-15",
            endDate = "2024-08-21",
            note = "Third test device request"
        )
    )

    IMAAppTheme {
        ManageRequestsList(
            requests = mockedRequests,
            selectedTab = ManageRequestsPagerTab.Pending,
            pullRefreshState = mockedPullRefreshState,
            isRefreshing = false,
            onClickReject = { },
            onClickApprove = { }
        )
    }
}

@Composable
fun ManageRequestsList(
    requests: List<TestDeviceRequestUi>,
    selectedTab: ManageRequestsPagerTab,
    pullRefreshState: PullRefreshState,
    onClickReject: (String) -> Unit,
    onClickApprove: (String) -> Unit,
    isRefreshing: Boolean
) {
    var sortType by remember { mutableStateOf(SortType.ASSET) }
    var sortAscending by remember { mutableStateOf(true) }

    val sortedRequests = sortRequests(requests, sortType, sortAscending)

    ManageRequestsListContent(
        sortedRequests = sortedRequests,
        selectedTab = selectedTab,
        pullRefreshState = pullRefreshState,
        isRefreshing = isRefreshing,
        sortType = sortType,
        sortAscending = sortAscending,
        onClickReject = onClickReject,
        onClickApprove = onClickApprove,
        onSortChanged = { newSortType, newSortAscending ->
            sortType = newSortType
            sortAscending = newSortAscending
        }
    )
}

fun sortRequests(
    requests: List<TestDeviceRequestUi>,
    sortType: SortType,
    sortAscending: Boolean
): List<TestDeviceRequestUi> {
    return when (sortType) {
        SortType.ASSET -> if (sortAscending) requests.sortedBy { it.asset } else requests.sortedByDescending { it.asset }
        SortType.START_DATE -> if (sortAscending) requests.sortedBy { it.startDate } else requests.sortedByDescending { it.startDate }
        SortType.END_DATE -> if (sortAscending) requests.sortedBy { it.endDate } else requests.sortedByDescending { it.endDate }
    }
}

@Composable
fun ManageRequestsListContent(
    sortedRequests: List<TestDeviceRequestUi>,
    selectedTab: ManageRequestsPagerTab,
    pullRefreshState: PullRefreshState,
    isRefreshing: Boolean,
    sortType: SortType,
    sortAscending: Boolean,
    onClickReject: (String) -> Unit,
    onClickApprove: (String) -> Unit,
    onSortChanged: (SortType, Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(selectedTab.padding)
                .clip(selectedTab.shape)
                .border(1.dp, LocalCardColorsPalette.current.borderColor)
                .background(LocalCardColorsPalette.current.containerColor)
        ) {
            stickyHeader {
                ManageRequestsListHeader(
                    sortType = sortType,
                    sortAscending = sortAscending,
                    onClickSortByAsset = {
                        val newAscending = if (sortType == SortType.ASSET) !sortAscending else true
                        onSortChanged(SortType.ASSET, newAscending)
                    },
                    onClickSortByStart = {
                        val newAscending = if (sortType == SortType.START_DATE) !sortAscending else true
                        onSortChanged(SortType.START_DATE, newAscending)
                    },
                    onClickSortByEnd = {
                        val newAscending = if (sortType == SortType.END_DATE) !sortAscending else true
                        onSortChanged(SortType.END_DATE, newAscending)
                    }
                )
            }

            items(items = sortedRequests) { request ->
                ManageRequestsListItem(
                    request = request,
                    selectedTab = selectedTab,
                    onClickReject = onClickReject,
                    onClickApprove = onClickApprove
                )
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = LocalCardColorsPalette.current.containerColor,
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun ManageRequestsListHeader(
    sortType: SortType,
    sortAscending: Boolean,
    onClickSortByAsset: () -> Unit,
    onClickSortByStart: () -> Unit,
    onClickSortByEnd: () -> Unit,
) {
    Column {
        Row(
            Modifier
                .background(LocalCardColorsPalette.current.arrowColor),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ManageRequestsHeaderTableCell(
                content = { Text("Asset") },
                onClickSort = onClickSortByAsset,
                modifier = Modifier.weight(0.30f),
                isSorted = sortType == SortType.ASSET,
                ascending = sortAscending
            )
            ManageRequestsHeaderTableCell(
                content = { Text("Start") },
                onClickSort = onClickSortByStart,
                modifier = Modifier.weight(0.30f),
                isSorted = sortType == SortType.START_DATE,
                ascending = sortAscending
            )
            ManageRequestsHeaderTableCell(
                content = { Text("End") },
                onClickSort = onClickSortByEnd,
                modifier = Modifier.weight(0.30f),
                isSorted = sortType == SortType.END_DATE,
                ascending = sortAscending
            )
            Spacer(modifier = Modifier.weight(0.1f))
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ManageRequestsHeaderTableCell(
    content: @Composable () -> Unit,
    onClickSort: () -> Unit,
    modifier: Modifier,
    isSorted: Boolean,
    ascending: Boolean
) {
    val sortIconAngle = animateFloatAsState(targetValue = if (ascending) 0f else 180f, label = "")
    val sortIconColor = animateColorAsState(targetValue = if(isSorted) MaterialTheme.colorScheme.primary else LocalCardColorsPalette.current.arrowColor,
        label = ""
    )

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable {
                onClickSort()
            }
            .padding(start = 15.dp)
            .fillMaxWidth()
    ) {
        content()
        IconButton(
            onClick = onClickSort,
        ){
            Icon(
                imageVector = Icons.Default.ArrowDownward,
                tint = sortIconColor.value,
                contentDescription = null,
                modifier = Modifier
                    .graphicsLayer {
                        rotationZ = if (isSorted) sortIconAngle.value else 180f
                    }
                )
        }
    }
}


enum class SortType {
    ASSET, START_DATE, END_DATE
}

@Composable
fun ManageRequestsTableCell(
    content: @Composable () -> Unit,
    modifier: Modifier
) {
    Row(
        modifier.padding(15.dp)
    ) {
        content()

    }
}

@Composable
fun ManageRequestsListItem(
    request: TestDeviceRequestUi,
    onClickReject: (String) -> Unit,
    onClickApprove: (String) -> Unit,
    selectedTab: ManageRequestsPagerTab,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val listItemExpanded = remember{
        mutableStateOf(false)
    }
    val listItemArrowAngle = animateFloatAsState(
        targetValue = if(listItemExpanded.value) 90f else 0f,
        label = ""
    )

    Column(
        Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                listItemExpanded.value = !listItemExpanded.value
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ManageRequestsTableCell(
                content = { Text(request.asset) },
                modifier = Modifier.weight(0.30f)
            )
            ManageRequestsTableCell(
                content = { Text(request.startDate) },
                modifier = Modifier
                    .weight(0.30f)
            )
            ManageRequestsTableCell(
                content = { Text(request.endDate) },
                modifier = Modifier
                    .weight(0.30f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.TwoTone.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .graphicsLayer {
                        rotationZ = listItemArrowAngle.value
                    }
                    .weight(0.1f),
                tint = LocalCardColorsPalette.current.arrowColor
            )
        }
        AnimatedVisibility(visible = listItemExpanded.value) {
            ManageRequestsListItemDetails(
                request = request,
                selectedTab = selectedTab,
                onClickReject = onClickReject,
                onClickApprove = onClickApprove
            )
        }
    }
    HorizontalDivider(color = LocalCardColorsPalette.current.borderColor)
}

@Composable
fun ManageRequestsListItemDetails(
    request: TestDeviceRequestUi,
    onClickApprove: (String) -> Unit,
    onClickReject: (String) -> Unit,
    selectedTab: ManageRequestsPagerTab
) {
    Column(modifier = Modifier.padding(15.dp)) {
        ManageRequestListItemDetailsRow(
            field = stringResource(R.string.request_id),
            value = request.requestId
        )
        ManageRequestListItemDetailsRow(
            stringResource(id = R.string.manufacturer),
            request.manufacturer
        )
        ManageRequestListItemDetailsRow(
            stringResource(id = R.string.note),
            request.note
        )
        if(selectedTab == ManageRequestsPagerTab.Pending) {
            ManageRequestListDecisionButtons(
                onClickApprove = { onClickApprove(request.requestId) },
                onClickReject = { onClickReject(request.requestId) }
            )
        }
    }
}

@Composable
fun ManageRequestListItemDetailsRow(
    field: String,
    value: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(vertical = 3.dp)
            .fillMaxWidth()
    ) {
        Text(text = field)
        Text(text = value)
    }
}

@Composable
fun ManageRequestListDecisionButtons(
    onClickApprove: () -> Unit,
    onClickReject: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(Modifier.weight(1f)) {
            PrimaryButton(onClick = onClickReject) {
                Row {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text("Reject")
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
        Row(Modifier.weight(1f)) {
            Spacer(modifier = Modifier.width(10.dp))
            SecondaryButton(onClick = onClickApprove) {
                Row {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text("Approve")
                }
            }
        }
    }
}
