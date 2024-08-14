package com.zenitech.imaapp.feature.admin.devices.add_device

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.common.PrimaryBasicTextField
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette

@Composable
@Preview(showBackground = true)
fun PreviewAdminDevicesAddDeviceSearchField() {
    AdminDevicesAddDeviceSearchField(
        filterQuery = "Search query",
        onFilterQueryChanged = { },
        onNavigateToAdminAddDevice = { }
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewAdminDevicesAddDeviceList() {
    AdminDevicesAddDeviceList(
        filteredList = listOf("Device 1", "Device 2", "Device 3"),
        onNavigateToAdminAddDevice = { }
    )
}

@Composable
fun AdminDevicesAddDeviceList(
    filteredList: List<String>,
    onNavigateToAdminAddDevice: (String) -> Unit
) {
    LazyColumn {
        items(filteredList) { item ->
            AdminDevicesAddDeviceItem(text = item) {
                onNavigateToAdminAddDevice(item)
            }
        }
    }
}

@Composable
fun AdminDevicesAddDeviceSearchField(
    filterQuery: String,
    onFilterQueryChanged: (String) -> Unit,
    onNavigateToAdminAddDevice: (String) -> Unit
) {
    val addContentColor = animateColorAsState(
        targetValue = if(filterQuery.isEmpty()) LocalCardColorsPalette.current.borderColor else LocalCardColorsPalette.current.contentColor,
        label = ""
    )
    Row(
        modifier = Modifier
            .padding(start = 30.dp, top = 20.dp, end = 15.dp, bottom = 15.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(imageVector = Icons.TwoTone.Search, contentDescription = null)
            Spacer(modifier = Modifier.width(10.dp))
            PrimaryBasicTextField(
                placeholderValue = stringResource(id = R.string.search),
                focusRequester = FocusRequester(),
                focusManager = LocalFocusManager.current,
                value = filterQuery,
                onValueChanged = { newQuery ->
                    onFilterQueryChanged(newQuery)
                }
            )
        }
        Row(
            Modifier
                .clip(RoundedCornerShape(15.dp))
                .clickable {
                    if (filterQuery.isNotBlank()) onNavigateToAdminAddDevice(filterQuery)
                }
                .border(1.dp, LocalCardColorsPalette.current.borderColor, RoundedCornerShape(15.dp))
                .background(
                    LocalCardColorsPalette.current.containerColor,
                    RoundedCornerShape(15.dp)
                )
                .padding(5.dp)
        ) {
            Icon(
                imageVector = Icons.TwoTone.Add,
                contentDescription = null,
                tint = addContentColor.value
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = stringResource(R.string.add), color = addContentColor.value)
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}


@Composable
fun AdminDevicesAddDeviceItem(
    text: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(LocalCardColorsPalette.current.containerColor)
            .clickable {
                onClick()
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 20.dp)
                .fillMaxWidth()
        ) {
            Text(text = text)
        }
        HorizontalDivider(color = LocalCardColorsPalette.current.borderColor)
    }
}
