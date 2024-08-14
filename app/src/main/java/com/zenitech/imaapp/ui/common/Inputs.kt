package com.zenitech.imaapp.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.twotone.Preview
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zenitech.imaapp.ui.theme.IMAAppTheme
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette

@Composable
@Preview(showBackground = true)
fun OutlinedTextFieldsPreview(){
    IMAAppTheme {
        Surface {
            val focusManager = LocalFocusManager.current
            Column(
                Modifier.padding(20.dp)
            ) {
                PrimaryBasicTextField(
                    focusManager = focusManager,
                    focusRequester = FocusRequester(),
                    value = "Primary field",
                    onValueChanged = { },
                )
                PrimaryInputField(
                    label = "Primary Input field label",
                    value = { Text("Primary Input Field") },
                    icon = Icons.TwoTone.Preview,
                    onClick = { },
                    isError = false,
                    showArrowDown = true
                )
            }
        }
    }
}

@Composable
fun PrimaryBasicTextField(
    modifier: Modifier? = Modifier,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    value: String,
    placeholderValue: String? = null,
    keyboardType: KeyboardType? = null,
    onValueChanged: (String) -> Unit,
){
    Box(
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            modifier = modifier ?: Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
            singleLine = true,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold),
            value = value,
            onValueChange = onValueChanged,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = keyboardType ?: KeyboardType.Unspecified),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )
        if (placeholderValue != null) {
            if (value.isEmpty() && placeholderValue.isNotBlank()) {
                Text(
                    text = placeholderValue,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                    ),
                )
            }
        }
    }
}

@Composable
fun PrimaryInputField(
    modifier: Modifier = Modifier,
    label: String,
    value: @Composable () -> Unit,
    icon: ImageVector? = null,
    onClick: (() -> Unit)?,
    isError: Boolean = false,
    showArrowDown: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick ?: {}
            )
            .fillMaxWidth(),
    ) {
        Column {
            RowWithBorder(
                modifier = Modifier.fillMaxWidth(),
                isError = isError
            ) {
                Column {
                    Text(label, style = MaterialTheme.typography.bodySmall)
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 10.dp, bottom = 0.dp)
                    ) {
                        if (icon != null) {
                            Icon(imageVector = icon, contentDescription = null, tint = LocalCardColorsPalette.current.borderColor)
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        value()
                    }
                }
                if (showArrowDown) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = LocalCardColorsPalette.current.arrowColor
                    )
                }
            }
        }
    }
}


