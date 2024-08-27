package com.zenitech.imaapp.feature.admin.devices.devices

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.IosShare
import androidx.compose.material.icons.twotone.Tag
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.common.conditional
import com.zenitech.imaapp.ui.common.pulsate
import com.zenitech.imaapp.ui.model.DeviceSearchRequestUi
import com.zenitech.imaapp.ui.theme.IMAAppTheme
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette

@Preview(showBackground = true)
@Composable
fun PreviewAdminDevicesIcons() {
    IMAAppTheme {
        AdminDevicesIcons(
            state = AdminDevicesState.Success(listOf(DeviceSearchRequestUi())),
            onSearchValueChanged = {},
            onClickTags = {},
            onClickAddDevice = {},
            onClickExport = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAdminDevicesTags() {
    AdminDevicesTags(
        onSelectedTagsChanged = {},
        selectedTags = listOf("Tag1", "Tag2")
    )
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
            AdminDevicesSearch(
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
