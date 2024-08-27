package com.zenitech.imaapp.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.theme.IMAAppTheme


@Composable
@Preview
fun PrimaryDialogPreview(){
    IMAAppTheme {
        PrimaryDialog(
            title = "Primary title",
            dismissText = "Dismiss",
            confirmText = "Confirm",
            onDismissRequest = {  }) {

        }
    }
}

@Composable
fun PrimaryDialog(
    title: String,
    dismissText: String,
    confirmText: String,
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit,
) {
    val noteText = remember {
        mutableStateOf("")
    }

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        RowWithBorder(Modifier.fillMaxWidth()) {
            Column {
                Text(title, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(40.dp))
                PrimaryBasicTextField(
                    focusRequester = FocusRequester.Default,
                    focusManager = LocalFocusManager.current,
                    placeholderValue = stringResource(R.string.type_note_here),
                    value = noteText.value) {
                    noteText.value = it
                }
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(Modifier.weight(1f)) {
                        PrimaryButton(onClick = onDismissRequest) {
                            Row {
                                Text(dismissText)
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    Row(Modifier.weight(1f)) {
                        Spacer(modifier = Modifier.width(10.dp))
                        SecondaryButton(onClick = { onConfirm(noteText.value) }) {
                            Row {
                                Text(confirmText)
                            }
                        }
                    }
                }
            }
        }
    }
}