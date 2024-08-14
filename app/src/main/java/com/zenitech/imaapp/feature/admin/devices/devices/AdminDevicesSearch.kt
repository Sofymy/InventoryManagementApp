package com.zenitech.imaapp.feature.admin.devices.devices

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.common.RowWithBorder
import com.zenitech.imaapp.ui.theme.IMAAppTheme

@Preview(showBackground = true)
@Composable
fun AdminDevicesSearchPreview() {
    val searchQuery = "example query"
    val searchBoxExpanded = true
    val interactionSource = remember { MutableInteractionSource() }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    IMAAppTheme {
        AdminDevicesSearch(
            searchQuery = searchQuery,
            searchBoxExpanded = searchBoxExpanded,
            interactionSource = interactionSource,
            focusRequester = focusRequester,
            focusManager = focusManager,
            onValueChange = {},
            onToggleSearchBox = {}
        )
    }
}

@Composable
fun AdminDevicesSearch(
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
    AdminDevicesSearchContent(
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
fun AdminDevicesSearchContent(
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


fun matchesSearchQuery(
    fieldValue: String,
    searchQuery: String
): Boolean {
    return fieldValue.contains(searchQuery, ignoreCase = true)
}
