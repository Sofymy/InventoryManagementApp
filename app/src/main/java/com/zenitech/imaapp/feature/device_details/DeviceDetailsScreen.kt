@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.zenitech.imaapp.feature.device_details

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.DateRange
import androidx.compose.material.icons.twotone.LocationOn
import androidx.compose.material.icons.twotone.Warehouse
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.common.CircularLoadingIndicator
import com.zenitech.imaapp.ui.common.simpleVerticalScrollbar
import com.zenitech.imaapp.ui.model.DeviceSearchRequestUi
import com.zenitech.imaapp.ui.model.DeviceStatusUi
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import com.zenitech.imaapp.ui.theme.RaspberryRed

@Composable
fun DeviceDetailsScreen(
    device: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DeviceDetailsContent(
            device = device,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = animatedVisibilityScope
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DeviceDetailsContent(
    device: String,
    viewModel: DeviceDetailsViewModel = hiltViewModel(),
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadDeviceDetails()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    when (state) {
        is DeviceDetailsState.Error -> {
            Text((state as DeviceDetailsState.Error).error.message.toString())
        }

        is DeviceDetailsState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                CircularLoadingIndicator()
            }
        }

        is DeviceDetailsState.Success -> {
            DeviceDetailsList(
                device = (state as DeviceDetailsState.Success).deviceDetailsList,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope
            )
        }
    }
}

@Composable
fun DeviceDetailsList(
    device: DeviceSearchRequestUi,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
) {
    val listState = rememberLazyListState()

    val visible = remember {
        mutableStateOf(false)
    }
    val hexagonHeight = animateFloatAsState(
        targetValue = if (visible.value) 1f else 0.6f,
        animationSpec = spring(stiffness = Spring.StiffnessVeryLow), label = ""
    )

    LaunchedEffect(Unit) {
        visible.value = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)

    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .simpleVerticalScrollbar(
                    listState,
                    color = LocalCardColorsPalette.current.secondaryContentColor
                )
                .fillMaxWidth()
                .padding(horizontal = 0.dp),
            state = listState
        ) {
            item { DeviceDetailsTopSection(hexagonHeight.value, device, listState) }
            item { DeviceDetailsHeaderIcon(Icons.TwoTone.Warehouse) }
            items(device.getDeviceDetails()[0].details) { detail -> DeviceDetailsItem(modifier = Modifier, fieldName = detail.first, fieldValue = detail.second) }
            item { HorizontalDivider(thickness = 20.dp, color = MaterialTheme.colorScheme.background) }
            item { DeviceDetailsHeaderIcon(Icons.TwoTone.LocationOn) }
            items(device.getDeviceDetails()[1].details) { detail -> DeviceDetailsItem(modifier = Modifier, fieldName = detail.first, fieldValue = detail.second) }
            item { HorizontalDivider(thickness = 20.dp, color = MaterialTheme.colorScheme.background) }
            item { DeviceDetailsHeaderIcon(Icons.TwoTone.DateRange) }
            items(device.getDeviceDetails()[2].details) { detail -> DeviceDetailsItem(modifier = Modifier, fieldName = detail.first, fieldValue = detail.second) }
            item { HorizontalDivider(thickness = 20.dp, color = MaterialTheme.colorScheme.background) }
        }
    }
}

@Composable
fun DeviceDetailsTopSection(scale: Float, device: DeviceSearchRequestUi, listState: LazyListState) {
    val state = remember { mutableStateOf(HexagonFace.Back) }
    val clickCounter = remember { mutableIntStateOf(0) }

    DeviceDetailsFlippingHexagonSection(
        state = state.value,
        scale = scale,
        listState = listState,
        device = device,
        onStateChange = { stateValue ->
            state.value = when {
                (stateValue.angle == 180f && clickCounter.intValue.mod(3) == 0 && clickCounter.intValue != 0) -> HexagonFace.Front
                else -> stateValue.next
            }
            clickCounter.intValue++
        },
    )
}

@Composable
fun DeviceDetailsFlippingHexagonSection(
    state: HexagonFace,
    scale: Float,
    listState: LazyListState,
    device: DeviceSearchRequestUi,
    onStateChange: (HexagonFace) -> Unit,
) {
    DeviceDetailsFlippingHexagon(
        hexagonFace = state,
        onClick = {
            onStateChange(state)
        },
        axis = RotationAxis.AxisY,
        back = {
            DeviceDetailsHexagonBackContent(
                scale,
                listState,
                device,
            )
        },
        front = {
            DeviceDetailsHexagonFrontContent(
                scale,
                listState,
            )
        }
    )
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun DeviceDetailsFlippingHexagon(
    hexagonFace: HexagonFace,
    onClick: (HexagonFace) -> Unit,
    modifier: Modifier = Modifier,
    axis: RotationAxis = RotationAxis.AxisY,
    back: @Composable () -> Unit = {},
    front: @Composable () -> Unit = {},
) {
    val offsetX = remember { mutableFloatStateOf(0f) }
    val offsetY = remember { mutableFloatStateOf(0f) }

    val rotation = animateFloatAsState(
        targetValue = hexagonFace.angle,
        animationSpec = tween(
            durationMillis = if(hexagonFace.angle == 140f) 300 else 1500,
            easing = FastOutSlowInEasing,
        ), label = "", finishedListener = {
            if(hexagonFace.angle == 140f){
                onClick(hexagonFace)
            }
        }
    )

    Column(
        modifier = modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = {
                    onClick(hexagonFace)
                }
            )
            .pointerInput(Unit) {
                detectDragGestures { _, dragAmount ->
                    val original = Offset(offsetX.floatValue, offsetY.floatValue)
                    val summed = original + dragAmount
                    val newValue = Offset(
                        x = summed.x.coerceIn(-100f, 100.dp.toPx() / density),
                        y = summed.y.coerceIn(-15f, 50.dp.toPx() / density)
                    )
                    offsetX.floatValue = newValue.x
                    offsetY.floatValue = newValue.y
                }
            }
            .graphicsLayer {
                translationX = offsetX.floatValue
                translationY = offsetY.floatValue
            }
            .graphicsLayer {
                if (axis == RotationAxis.AxisX) {
                    rotationX = rotation.value
                } else {
                    rotationY = rotation.value
                }
                cameraDistance = 12f * density
            },
    ) {
        if (rotation.value <= 90f) {
            Box(
                Modifier.fillMaxSize()
            ) {
                front()
            }
        } else {
            Box(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        if (axis == RotationAxis.AxisX) {
                            rotationX = 180f
                        } else {
                            rotationY = 180f
                        }
                    },
            ) {
                back()
            }
        }
    }
}

@Composable
fun DeviceDetailsHexagonBackContent(
    scale: Float,
    listState: LazyListState,
    device: DeviceSearchRequestUi,
) {
    DeviceDetailsHexagonContent(
        scale = scale,
        listState = listState,
        hexagonBackground = {
            Image(
                modifier = Modifier.size(272.dp)
                ,
                painter = painterResource(R.drawable.shape_hexagon),
                colorFilter = ColorFilter.tint(LocalCardColorsPalette.current.borderColor),
                contentDescription = null,
            )
        },
        hexagonForeground = {
            Image(
                modifier = Modifier.size(270.dp),
                painter = painterResource(R.drawable.shape_hexagon),
                colorFilter = ColorFilter.tint(LocalCardColorsPalette.current.containerColor),
                contentDescription = null,
            )
            DeviceDetailsIconContent(device)
        }
    )
}

@Composable
fun DeviceDetailsHexagonFrontContent(
    scale: Float,
    listState: LazyListState,
) {
    DeviceDetailsHexagonContent(
        scale = scale,
        listState = listState,
        hexagonBackground = {
            Image(
                modifier = Modifier.size(272.dp),
                painter = painterResource(R.drawable.shape_hexagon),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
                contentDescription = null,
            )
        },
        hexagonForeground = {
            Image(
                modifier = Modifier.size(270.dp),
                painter = painterResource(R.drawable.shape_hexagon_logo),
                colorFilter = ColorFilter.tint(RaspberryRed),
                contentDescription = null,
            )
        }
    )
}

@Composable
fun DeviceDetailsHexagonContent(
    scale: Float,
    listState: LazyListState,
    hexagonBackground: @Composable () -> Unit,
    hexagonForeground: @Composable () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                this.scaleX = scale
                this.scaleY = scale
                this.translationY = 0.7f * listState.firstVisibleItemScrollOffset.toFloat()
            }
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            hexagonBackground()
            hexagonForeground()
        }
    }
}


@Composable
fun DeviceDetailsIconContent(device: DeviceSearchRequestUi) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            modifier = Modifier.size(80.dp),
            imageVector = device.asset.icon,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(25.dp))
        Text(device.type, fontWeight = FontWeight.Bold, fontSize = 20.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(25.dp))
        DeviceDetailsStatusBox(status = device.status)
    }
}


@Composable
fun DeviceDetailsHeaderIcon(icon: ImageVector) {
    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .border(
                    1.dp,
                    LocalCardColorsPalette.current.borderColor,
                    RoundedCornerShape(10.dp, 0.dp)
                )
                .background(
                    LocalCardColorsPalette.current.containerColor,
                    RoundedCornerShape(10.dp, 0.dp)
                )
                .padding(30.dp, 5.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null)
        }
    }
    HorizontalDivider(color = LocalCardColorsPalette.current.borderColor)
}

@Composable
fun DeviceDetailsItem(
    fieldName: String,
    fieldValue: String,
    modifier: Modifier
) {

    Column(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(LocalCardColorsPalette.current.containerColor)
                .padding(horizontal = 30.dp, vertical = 20.dp)
                .fillMaxWidth()
        ) {
            Text(text = fieldName)
            Text(text = fieldValue, fontWeight = FontWeight.Bold)
        }
        HorizontalDivider(color = LocalCardColorsPalette.current.borderColor)
    }
}

@Composable
fun ColumnScope.DeviceDetailsStatusBox(status: DeviceStatusUi) {
    Box(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .background(status.color, RoundedCornerShape(5.dp))
            .padding(8.dp, 2.dp)
    ) {
        Text(
            text = status.name,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

enum class HexagonFace(val angle: Float) {
    Back(180f) {
        override val next: HexagonFace
            get() = BackSmall
    },
    BackSmall(140f) {
        override val next: HexagonFace
        get() = Back
    },
    Front(0f) {
        override val next: HexagonFace
        get() = Back
    };

    abstract val next: HexagonFace
}

enum class RotationAxis {
    AxisX,
    AxisY,
}