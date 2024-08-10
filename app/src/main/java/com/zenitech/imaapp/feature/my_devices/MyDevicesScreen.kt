@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.zenitech.imaapp.feature.my_devices

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.automirrored.twotone.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.common.CircularLoadingIndicator
import com.zenitech.imaapp.ui.common.ScrollToTopButton
import com.zenitech.imaapp.ui.common.pulsate
import com.zenitech.imaapp.ui.common.shimmerBrush
import com.zenitech.imaapp.ui.common.simpleVerticalScrollbar
import com.zenitech.imaapp.ui.model.DeviceSearchRequestUi
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import kotlinx.coroutines.launch

@Composable
fun MyDevicesScreen(
    onNavigateToDeviceDetails: (String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(horizontal = 0.dp)
    ) {
        MyDevicesContent(
            onNavigateToDeviceDetails = onNavigateToDeviceDetails,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = animatedVisibilityScope
        )
    }
}

@Composable
fun MyDevicesContent(
    viewModel: MyDevicesViewModel = hiltViewModel(),
    onNavigateToDeviceDetails: (String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadMyDevices()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    when (state) {
        is MyDevicesState.Error -> {
            Text((state as MyDevicesState.Error).error.message.toString())
        }
        is MyDevicesState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                CircularLoadingIndicator()
            }
        }
        is MyDevicesState.Success -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MyDevicesCounter((state as MyDevicesState.Success).myDeviceList.size)
                MyDevicesSorting(SortingOption.entries.toTypedArray()) { sortingOption ->
                    viewModel.onEvent(MyDevicesUserEvent.ChangeSortingOption(sortingOption))
                }
            }
            MyDevicesList(
                deviceResponseUiList = (state as MyDevicesState.Success).myDeviceList,
                onNavigateToDeviceDetails = onNavigateToDeviceDetails,
                animatedVisibilityScope = animatedVisibilityScope,
                sharedTransitionScope = sharedTransitionScope
            )
        }
    }
}

@Composable
fun MyDevicesCounter(
    deviceNumber: Int
) {
    Column(
        Modifier.padding(15.dp, 15.dp, 0.dp, 15.dp),
    ) {
        Text(stringResource(R.string.devices, deviceNumber))
    }
}

@Composable
fun MyDevicesList(
    deviceResponseUiList: List<DeviceSearchRequestUi>,
    onNavigateToDeviceDetails: (String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val listState = rememberLazyListState()
    val showButtonAndDivider by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(deviceResponseUiList.first()) {
        scope.launch {
            listState.animateScrollToItem(0)
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .simpleVerticalScrollbar(
                    color = LocalCardColorsPalette.current.secondaryContentColor,
                    state = listState
                )
                .padding(horizontal = 15.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            items(
                items = deviceResponseUiList,
                contentType = { it },
                key = { device -> device.inventoryId }
            ) { device ->
                MyDevicesDeviceItem(
                    deviceResponseUi = device,
                    modifier = Modifier,
                    onNavigateToDeviceDetails = onNavigateToDeviceDetails,
                    animatedVisibilityScope = animatedVisibilityScope,
                    sharedTransitionScope = sharedTransitionScope
                )
            }
        }

        AnimatedVisibility(
            visible = showButtonAndDivider,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            HorizontalDivider(
                color = LocalCardColorsPalette.current.borderColor
            )
        }

        AnimatedVisibility(
            visible = showButtonAndDivider,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(onClick = {
                scope.launch {
                    listState.animateScrollToItem(0)
                }
            })
        }

    }
}

@Composable
fun MyDevicesShimmerItem() {
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(bottom = 15.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(shimmerBrush(targetValue = 1300f))
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("")
                    Spacer(modifier = Modifier.height(5.dp))
                    Text("")
                }
            }
        }
    }
}

@Composable
fun MyDevicesDeviceItem(
    deviceResponseUi: DeviceSearchRequestUi,
    modifier: Modifier,
    onNavigateToDeviceDetails: (String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
            .pulsate()
            .padding(bottom = 15.dp)
            .border(1.dp, LocalCardColorsPalette.current.borderColor, RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    onNavigateToDeviceDetails(deviceResponseUi.inventoryId)
                }
            )
        ,
        colors = CardDefaults.cardColors(
            containerColor = LocalCardColorsPalette.current.containerColor,
            contentColor = LocalCardColorsPalette.current.contentColor
        )
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = deviceResponseUi.assetName.icon,
                    contentDescription = null,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(deviceResponseUi.assetName.name )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(deviceResponseUi.manufacturer, color = LocalCardColorsPalette.current.secondaryContentColor)
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.TwoTone.KeyboardArrowRight,
                contentDescription = null,
                tint = LocalCardColorsPalette.current.arrowColor
            )
        }
    }

}

@Composable
fun MyDevicesSorting(
    sortingOptions: Array<SortingOption>,
    onSortSelected: (SortingOption) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(SortingOption.Asset) }

    Row(
        modifier = Modifier
            .padding(0.dp, 15.dp, 15.dp, 15.dp)
            .fillMaxWidth()
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        MyDevicesSortingDropDown(
            selectedOption = selectedOption.name,
            expanded = expanded,
            options = sortingOptions,
            onOptionSelected = {
                selectedOption = it
                onSortSelected(it)
                expanded = false
            },
            onExpandedChange = { expanded = !expanded }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDevicesSortingDropDown(
    selectedOption: String,
    expanded: Boolean,
    options: Array<SortingOption>,
    onOptionSelected: (SortingOption) -> Unit,
    onExpandedChange: () -> Unit
) {
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { onExpandedChange() }) {
        Row(
            modifier = Modifier
                .width(150.dp)
                .menuAnchor(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(stringResource(id = R.string.sort))
            Spacer(modifier = Modifier.width(10.dp))
            Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange() }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            option.name,
                            color = if (option.name == selectedOption) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                    },
                    onClick = { onOptionSelected(option) },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}


enum class SortingOption {
    Asset, Manufacturer
}
