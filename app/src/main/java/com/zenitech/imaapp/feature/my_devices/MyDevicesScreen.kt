package com.zenitech.imaapp.feature.my_devices

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.automirrored.twotone.KeyboardArrowRight
import androidx.compose.material.icons.twotone.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenitech.imaapp.ui.common.pulsate
import com.zenitech.imaapp.ui.common.shimmerBrush
import com.zenitech.imaapp.ui.common.simpleVerticalScrollbar
import com.zenitech.imaapp.ui.model.DeviceResponseUi
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import com.zenitech.imaapp.ui.theme.RaspberryRed30White
import kotlinx.coroutines.launch

@Composable
fun MyDevicesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 0.dp)
    ) {
        MyDevicesContent()
    }
}

@Composable
fun MyDevicesContent(
    viewModel: MyDevicesViewModel = hiltViewModel()
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
            // Show loading indicator
        }
        is MyDevicesState.Success -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MyDevicesCounter((state as MyDevicesState.Success).myDeviceList.size)
                SortingComponent(SortingOption.entries.toTypedArray()) { sortingType ->
                    viewModel.setSortingOption(sortingType)
                }
            }
            MyDevicesList(deviceResponseUiList = (state as MyDevicesState.Success).myDeviceList)
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
        Text("$deviceNumber devices")
    }
}

@Composable
fun MyDevicesList(
    deviceResponseUiList: List<DeviceResponseUi>,
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
                key = { device -> device.inventoryNumber }
            ) { device ->
                MyDevicesDeviceItem(
                    deviceResponseUi = device,
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


enum class ComponentState { Pressed, Released }

@Composable
fun MyDevicesDeviceItem(
    deviceResponseUi: DeviceResponseUi
) {

    var toState by remember { mutableStateOf(ComponentState.Released) }
    val transition: Transition<ComponentState> = updateTransition(targetState = toState, label = "")

    val scaleX: Float by transition.animateFloat(
        transitionSpec = { spring(stiffness = Spring.StiffnessMedium) }, label = ""
    ) { state ->
        if (state == ComponentState.Pressed) 0.95f else 1f
    }
    val scaleY: Float by transition.animateFloat(
        transitionSpec = { spring(stiffness = Spring.StiffnessMedium) }, label = ""
    ) { state ->
        if (state == ComponentState.Pressed) 0.95f else 1f
    }

    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .pulsate(scaleX, scaleY, toState) {
                toState = it
            }
            .padding(bottom = 15.dp)
            .border(1.dp, LocalCardColorsPalette.current.borderColor, RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {

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
                Icon(imageVector = deviceResponseUi.assetName.icon, contentDescription = null)
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(deviceResponseUi.inventoryNumber)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(deviceResponseUi.assetName.label, color = LocalCardColorsPalette.current.secondaryContentColor)
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.TwoTone.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Transparent
            )
        }
    }
}

@Composable
fun SortingComponent(sortingOptions: Array<SortingOption>, onSortSelected: (SortingOption) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf<SortingOption>(SortingOption.Name) }

    Row(
        modifier = Modifier
            .padding(0.dp, 15.dp, 15.dp, 15.dp)
            .fillMaxWidth()
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        SortingDropDown(
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
fun SortingDropDown(
    selectedOption: String,
    expanded: Boolean,
    options: Array<SortingOption>,
    onOptionSelected: (SortingOption) -> Unit,
    onExpandedChange: () -> Unit
) {
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { onExpandedChange() }) {
        Row(
            modifier = Modifier
                .width(110.dp)
                .menuAnchor(),
            horizontalArrangement = Arrangement.End
        ) {
            Text("Sort")
            Spacer(modifier = Modifier.width(10.dp))
            Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = null, tint = RaspberryRed30White)
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
                            color = if (option.name == selectedOption) RaspberryRed30White else MaterialTheme.colorScheme.onBackground
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
fun ScrollToTopButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp, end = 30.dp)
        ,
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(onClick = { onClick() },
            contentPadding = PaddingValues(13.dp),
            shape = CircleShape,
            modifier = Modifier
                .shadow(10.dp, shape = CircleShape)
                .size(65.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.TwoTone.KeyboardArrowUp,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

enum class SortingOption {
    Name, Type
}
