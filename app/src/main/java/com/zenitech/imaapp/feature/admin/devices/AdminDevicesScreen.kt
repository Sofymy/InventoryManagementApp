@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)

package com.zenitech.imaapp.feature.admin.devices

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ArrowForwardIos
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.IosShare
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material.icons.twotone.Tag
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenitech.imaapp.R
import com.zenitech.imaapp.domain.model.asDeviceAsset
import com.zenitech.imaapp.ui.common.CircularLoadingIndicator
import com.zenitech.imaapp.ui.common.RowWithBorder
import com.zenitech.imaapp.ui.common.conditional
import com.zenitech.imaapp.ui.common.pulsate
import com.zenitech.imaapp.ui.model.DeviceSearchRequestUi
import com.zenitech.imaapp.ui.model.DeviceStatusUi
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import kotlinx.coroutines.launch
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun AdminDevicesScreen(
    onNavigateToAdminDeviceDetails: (String) -> Unit,
    onNavigateToAdminDevicesAddDevice: () -> Unit
) {
    AdminDevicesScreenContent(
        onNavigateToAdminDeviceDetails = onNavigateToAdminDeviceDetails,
        onNavigateToAdminDevicesAddDevice = onNavigateToAdminDevicesAddDevice,
    )
}

@Composable
fun AdminDevicesScreenContent(
    viewModel: AdminDevicesViewModel = hiltViewModel(),
    onNavigateToAdminDeviceDetails: (String) -> Unit,
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
            onNavigateToAdminDeviceDetails = onNavigateToAdminDeviceDetails,
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
    onNavigateToAdminDeviceDetails: (String) -> Unit,
    pullRefreshState: PullRefreshState,
    onClickAddDevice: () -> Unit,
    onClickExport: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 5 })
    val pagerTabs = createPagerTabs()
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
            onNavigateToAdminDeviceDetails = onNavigateToAdminDeviceDetails,
            pullRefreshState = pullRefreshState
        )
    }
}

@Composable
fun AdminDevicesTags(
    onSelectedTagsChanged: (String) -> Unit,
    selectedTags: List<String>
) {
    val tags = remember { mutableStateListOf("Tag1", "Tag2") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val newTagValue = remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val removeTagExpanded = remember { mutableStateOf(false) }
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle = infiniteTransition.animateFloat(
        initialValue = -2F,
        targetValue = 2F,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Reverse,
            animation = tween(200, easing = LinearEasing)
        ), label = ""
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        item { Spacer(modifier = Modifier.width(15.dp)) }
        items(tags) { tag ->
            AdminDevicesTagsItem(
                tag = tag,
                selectedTags = selectedTags,
                onSelectedTagsChanged = onSelectedTagsChanged,
                removeTagExpanded = removeTagExpanded.value,
                angle = angle.value,
                onRemoveTagExpandedChanged = {
                    removeTagExpanded.value = !removeTagExpanded.value
                },
                onRemoveItem = {
                    tags.remove(it)
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
        item { Spacer(modifier = Modifier.width(10.dp)) }
        item {
            AdminDevicesTagsAddNewTag(
                newTagValue = newTagValue.value,
                focusRequester = focusRequester,
                focusManager = focusManager,
                interactionSource = interactionSource,
                onNewTagValueChanged = {
                    newTagValue.value = it
                },
                onAddItem = {
                    tags.add((newTagValue.value))
                }
            )
        }
        item { Spacer(modifier = Modifier.width(5.dp)) }
        item { Spacer(modifier = Modifier.width(15.dp)) }
    }
}

@Composable
fun AdminDevicesTagsItem(
    tag: String,
    selectedTags: List<String>,
    onSelectedTagsChanged: (String) -> Unit,
    removeTagExpanded: Boolean,
    onRemoveTagExpandedChanged: () -> Unit,
    onRemoveItem: (String) -> Unit,
    angle: Float,
) {
    val inputChipInteractionSource = remember { MutableInteractionSource() }
    Box {
        FilterChip(
            modifier = Modifier
                .conditional(removeTagExpanded) {
                    Modifier.graphicsLayer {
                        rotationZ = angle
                    }
                },
            border = FilterChipDefaults.filterChipBorder(
                borderColor = LocalCardColorsPalette.current.borderColor,
                enabled = true,
                selected = tag in selectedTags
            ),
            selected = tag in selectedTags,
            label = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(tag)
                    AnimatedVisibility(removeTagExpanded) {
                        Spacer(modifier = Modifier.width(30.dp))
                    }
                }
            },
            onClick = { if (!removeTagExpanded) onSelectedTagsChanged(tag) },
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
        AdminDevicesTagsDeleteIcon(
            removeTagExpanded = removeTagExpanded,
            angle = angle,
            tag = tag,
            inputChipInteractionSource = inputChipInteractionSource,
            onSelectedTagsChanged = onSelectedTagsChanged,
            modifier = Modifier.matchParentSize(),
            onRemoveTagExpandedChanged = onRemoveTagExpandedChanged,
            onRemoveItem = onRemoveItem
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdminDevicesTagsDeleteIcon(
    modifier: Modifier,
    removeTagExpanded: Boolean,
    angle: Float,
    tag: String,
    inputChipInteractionSource: MutableInteractionSource,
    onRemoveItem: (String) -> Unit,
    onSelectedTagsChanged: (String) -> Unit,
    onRemoveTagExpandedChanged: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = modifier
            .combinedClickable(
                onLongClick = { onRemoveTagExpandedChanged() },
                onClick = { if (!removeTagExpanded) onSelectedTagsChanged(tag) },
                interactionSource = inputChipInteractionSource,
                indication = null
            )
    ) {
        AnimatedVisibility(removeTagExpanded) {
            Icon(
                imageVector = Icons.TwoTone.Delete,
                contentDescription = null,
                modifier = Modifier
                    .graphicsLayer { rotationZ = angle }
                    .padding(end = 10.dp)
                    .clickable(
                        interactionSource = inputChipInteractionSource,
                        indication = null
                    ) { onRemoveItem(tag) }
            )
        }
    }
}

@Composable
fun AdminDevicesTagsAddNewTag(
    newTagValue: String,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource,
    onAddItem: (String) -> Unit,
    onNewTagValueChanged: (String) -> Unit
) {
    FilterChip(
        border = FilterChipDefaults.filterChipBorder(
            borderColor = LocalCardColorsPalette.current.borderColor,
            enabled = true,
            selected = false
        ),
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
        ),
        selected = false,
        onClick = {},
        label = {
            Row(
                modifier = Modifier.padding(top = 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AdminDevicesTagsTextField(newTagValue, onNewTagValueChanged, focusRequester, focusManager)
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = stringResource(R.string.add_tag),
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onAddItem(newTagValue)
                        onNewTagValueChanged("")
                    }
                )
            }
        }
    )
}

@Composable
fun AdminDevicesTagsTextField(
    newTagValue: String,
    onNewTagValueChanged: (String) -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager
) {
    Box {
        BasicTextField(
            value = newTagValue,
            modifier = Modifier
                .padding(start = 10.dp)
                .focusRequester(focusRequester),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
            singleLine = true,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
            onValueChange = { onNewTagValueChanged(it) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )
        if (newTagValue.isEmpty()) {
            Text(
                text = stringResource(R.string.enter_a_new_tag),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                ),
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}


@Composable
fun AdminDevicesTabs(
    pagerTabs: List<PagerTab>,
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
fun AdminDevicesIcons(
    state: AdminDevicesState,
    onSearchValueChanged: (String) -> Unit,
    onClickTags: () -> Unit,
    onClickAddDevice: () -> Unit,
    onClickExport: () -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val searchBoxExpanded = remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    LazyRow(
        modifier = Modifier.padding(horizontal = 0.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        item {
            AdminDevicesFilterSearch(
                searchQuery = searchQuery,
                searchBoxExpanded = searchBoxExpanded.value,
                interactionSource = interactionSource,
                focusRequester = focusRequester,
                focusManager = focusManager,
                onValueChange = { query ->
                    searchQuery = query
                    onSearchValueChanged(query)
                },
                onToggleSearchBox = {
                    searchBoxExpanded.value = !searchBoxExpanded.value
                    if (!searchBoxExpanded.value) {
                        searchQuery = ""
                        onSearchValueChanged(searchQuery)
                    }
                }
            )
        }
        item {
            AdminDevicesFilterTags(
                interactionSource = interactionSource,
                onClick = onClickTags
            )
        }
        item{
            AdminDevicesExport(
                interactionSource = interactionSource,
                onClick = onClickExport,
                state = state
            )
        }
        item {
            AdminDevicesAddDevice(
                interactionSource = interactionSource,
                onClick = onClickAddDevice,
            )
        }
    }
}

@Composable
fun AdminDevicesAddDevice(
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit
) {
    AdminDevicesIcon(
        interactionSource = interactionSource,
        icon = Icons.TwoTone.Add,
        onClick = onClick,
    )
}

@Composable
fun AdminDevicesExport(
    state: AdminDevicesState,
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit
) {
    AdminDevicesIcon(
        icon = Icons.TwoTone.IosShare,
        interactionSource = interactionSource,
        onClick = {
            if(state is AdminDevicesState.Success) {
                onClick()
            }
        },
        enabled = state is AdminDevicesState.Success
    )
}

@Composable
fun AdminDevicesFilterSearch(
    searchQuery: String,
    searchBoxExpanded: Boolean,
    interactionSource: MutableInteractionSource,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    onValueChange: (String) -> Unit,
    onToggleSearchBox: () -> Unit
) {
    AnimatedVisibility(visible = searchBoxExpanded) {
        Spacer(modifier = Modifier.width(15.dp))
    }
    AdminDevicesFilterSearch(
        value = {
            Icon(
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onToggleSearchBox
                    )
                    .padding(5.dp),
                imageVector = Icons.TwoTone.Search,
                contentDescription = null,
            )
            AnimatedVisibility(visible = searchBoxExpanded) {
                Box(modifier = Modifier.padding(top = 0.dp)) {
                    BasicTextField(
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .focusRequester(focusRequester),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                        singleLine = true,
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                        value = searchQuery,
                        onValueChange = onValueChange,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
                    )
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                            ),
                            modifier = Modifier.padding(start = 15.dp)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun AdminDevicesFilterTags(
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit
) {
    AdminDevicesIcon(
        icon = Icons.TwoTone.Tag,
        interactionSource = interactionSource,
        onClick = onClick
    )
}

@Composable
fun AdminDevicesFilterSearch(
    value: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
    ) {
        Column {
            RowWithBorder(
                shape = RoundedCornerShape(20.dp),
                content = {
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            value()
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun AdminDevicesTabContent(
    state: AdminDevicesState,
    pagerState: PagerState,
    pagerTabs: List<PagerTab>,
    searchQuery: String,
    onNavigateToAdminDeviceDetails: (String) -> Unit,
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
                onNavigateToAdminDeviceDetails = onNavigateToAdminDeviceDetails,
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
    pagerTabs: List<PagerTab>,
    searchQuery: String,
    onNavigateToAdminDeviceDetails: (String) -> Unit,
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
            onNavigateToAdminDeviceDetails = onNavigateToAdminDeviceDetails
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdminDevicesDeviceList(
    filteredDevices: List<DeviceSearchRequestUi>,
    adminFields: List<String>,
    selectedField: String,
    onFieldSelected: (String) -> Unit,
    selectedTab: PagerTab,
    onNavigateToAdminDeviceDetails: (String) -> Unit,
    pullRefreshState: PullRefreshState,
    isRefreshing: Boolean
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
                .background(LocalCardColorsPalette.current.containerColor),
        ) {
            stickyHeader {
                AdminDevicesDeviceListHeader(
                    selectedField = selectedField,
                    adminFields = adminFields,
                    onFieldSelected = onFieldSelected
                )
            }

            items(
                items = filteredDevices,
                key = { it.inventoryId }
            ) { device ->
                AdminDevicesDeviceListItem(
                    device = device,
                    selectedField = selectedField,
                    onNavigateToAdminDeviceDetails = onNavigateToAdminDeviceDetails
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
fun AdminDevicesDeviceListHeader(
    adminFields: List<String>,
    onFieldSelected: (String) -> Unit,
    selectedField: String
) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .background(LocalCardColorsPalette.current.arrowColor),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AdminDevicesTableCell(
                text = stringResource(id = R.string.inventory_id),
                modifier = Modifier.weight(0.5f)
            )
            AdminDevicesFields(
                selectedField = selectedField,
                fields = adminFields,
                modifier = Modifier
                    .weight(0.5f)
            ) { onFieldSelected(it) }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun AdminDevicesDeviceListItem(
    device: DeviceSearchRequestUi,
    selectedField: String,
    onNavigateToAdminDeviceDetails: (String) -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Row(
        Modifier
            .pulsate()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onNavigateToAdminDeviceDetails(device.inventoryId)
            }
    ) {
        AdminDevicesTableCell(
            text = device.inventoryId,
            modifier = Modifier.weight(0.5f)
        )
        AdminDevicesTableCell(
            text = getFieldValue(device, selectedField),
            modifier = Modifier
                .weight(0.5f)
        )
    }
    HorizontalDivider(color = LocalCardColorsPalette.current.borderColor)
}

@Composable
fun AdminDevicesFields(
    selectedField: String,
    fields: List<String>,
    modifier: Modifier,
    onFieldSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(modifier = modifier.fillMaxWidth()) {
        AdminDevicesDropDown(
            selectedOption = selectedField,
            expanded = expanded,
            options = fields,
            onOptionSelected = {
                onFieldSelected(it)
                expanded = false
            },
            onExpandedChange = { expanded = !expanded },
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDevicesDropDown(
    selectedOption: String,
    expanded: Boolean,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    onExpandedChange: () -> Unit,
    modifier: Modifier
) {
    val iconAngle = animateFloatAsState(
        targetValue = if(expanded) -90f else 90f,
        animationSpec = tween(durationMillis = 500),
        label = ""
    )

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { onExpandedChange() }) {
        Row(
            modifier = modifier
                .padding(15.dp)
                .fillMaxWidth()
                .menuAnchor(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(selectedOption)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.TwoTone.ArrowForwardIos,
                contentDescription = null,
                tint = LocalCardColorsPalette.current.secondaryContentColor,
                modifier = Modifier
                    .size(20.dp)
                    .graphicsLayer {
                        rotationZ = iconAngle.value
                    }
            )
            Spacer(modifier = Modifier.width(15.dp))
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange() }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            option,
                            color = if (option == selectedOption) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                    },
                    onClick = { onOptionSelected(option) },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Composable
fun AdminDevicesTableCell(
    text: String,
    modifier: Modifier
) {
    Box(
        modifier.padding(15.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun AdminDevicesIcon(
    icon: ImageVector,
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit,
    enabled: Boolean = true
){
    Spacer(modifier = Modifier.width(10.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            Modifier.height(IntrinsicSize.Max) ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            VerticalDivider(
                color = LocalCardColorsPalette.current.borderColor,
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .width(1.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .pulsate()
                    .clickable(
                        interactionSource = interactionSource,
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
                    tint = if(enabled) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    contentDescription = null,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}

@Composable
fun createPagerTabs(): List<PagerTab> = listOf(
    PagerTab.All,
    PagerTab.InStock,
    PagerTab.Leased,
    PagerTab.UnderRepair,
    PagerTab.Test
)

fun matchesSearchQuery(
    fieldValue: String,
    searchQuery: String
): Boolean {
    return fieldValue.contains(searchQuery, ignoreCase = true)
}

fun filterDevices(
    devices: List<DeviceSearchRequestUi>,
    page: Int,
    selectedField: String,
    searchQuery: String
): List<DeviceSearchRequestUi> {
    return devices.filter { device ->
        val statusMatches = when (page) {
            1 -> device.status == DeviceStatusUi.IN_STORAGE
            2 -> device.status == DeviceStatusUi.LEASED
            3 -> device.status == DeviceStatusUi.ON_REPAIR
            else -> true
        }

        val fieldValue = getFieldValue(device, selectedField)
        statusMatches && (
                matchesSearchQuery(device.inventoryId, searchQuery) ||
                        matchesSearchQuery(fieldValue, searchQuery))
    }
}

fun getFieldValue(
    device: DeviceSearchRequestUi,
    field: String
): String {
    return when (field) {
        "Inventory ID" -> device.inventoryId
        "Type" -> device.type
        "Asset name" -> device.assetName.label
        "Manufacturer" -> device.manufacturer
        "Serial number" -> device.serialNumber
        "Supplier" -> device.supplier
        "Invoice number" -> device.invoiceNumber
        "Condition" -> device.condition.toString()
        "Status" -> device.status.toString()
        "User name" -> device.userName
        "Site" -> device.site
        "Location" -> device.location
        "Test" -> device.isTestDevice.toString()
        else -> "N/A"
    }
}


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

sealed class PagerTab(
    val title: String,
    val padding: PaddingValues,
    val shape: RoundedCornerShape
) {
    data object All : PagerTab("All", PaddingValues(start = 15.dp), RoundedCornerShape(topStart = 15.dp))
    data object InStock : PagerTab("In stock", PaddingValues(0.dp), RoundedCornerShape(0.dp))
    data object Leased : PagerTab("Leased", PaddingValues(0.dp), RoundedCornerShape(0.dp))
    data object UnderRepair : PagerTab("Under repair", PaddingValues(0.dp), RoundedCornerShape(0.dp))
    data object Test : PagerTab("Test", PaddingValues(end = 15.dp), RoundedCornerShape(topEnd = 15.dp))
}