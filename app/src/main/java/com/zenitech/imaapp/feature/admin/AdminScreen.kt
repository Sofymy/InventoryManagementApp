package com.zenitech.imaapp.feature.admin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Devices
import androidx.compose.material.icons.twotone.Preview
import androidx.compose.material.icons.twotone.SupervisorAccount
import androidx.compose.material.icons.twotone.Textsms
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenitech.imaapp.ui.common.TrapezoidShapeLower
import com.zenitech.imaapp.ui.common.TrapezoidShapeUpper
import com.zenitech.imaapp.ui.common.pulsate
import com.zenitech.imaapp.ui.theme.IMAAppTheme
import com.zenitech.imaapp.ui.theme.LocalAdminFolderColorsPalette

@Preview(showBackground = true)
@Composable
fun AdminFolderPreview() {
    val folderColor = LocalAdminFolderColorsPalette.current.contentColor
    val headColor = LocalAdminFolderColorsPalette.current.headColor

    IMAAppTheme {
        AdminFolderCard(
            headColor = headColor,
            containerColor = folderColor,
            folderName = "Preview name",
            folderIcon = Icons.TwoTone.Preview,
            onClick = { },
            state = null
        )
    }
}

@Composable
fun AdminScreen(
    onNavigateToAdminDevices: () -> Unit,
    onNavigateToManageRequests: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AdminContent(onNavigateToAdminDevices, onNavigateToManageRequests)
    }
}

@Composable
fun AdminContent(
    onNavigateToAdminDevices: () -> Unit,
    onNavigateToManageRequests: () -> Unit,
    viewModel: AdminViewModel = hiltViewModel()
) {
    val folderColor = LocalAdminFolderColorsPalette.current.contentColor
    val headColor = LocalAdminFolderColorsPalette.current.headColor
    val state by viewModel.state.collectAsStateWithLifecycle()

    val folders = listOf(
        Folder(text = "Devices", icon = Icons.TwoTone.Devices, onClick = onNavigateToAdminDevices),
        Folder(text = "Users", icon = Icons.TwoTone.SupervisorAccount, onClick = {}),
        Folder(text = "Manage Requests", icon = Icons.TwoTone.Textsms, onClick = onNavigateToManageRequests, state = state),
        //Folder(text = "Tags", icon = Icons.TwoTone.Tag) {},
        //Folder(text = "Filters", icon = Icons.TwoTone.FilterAlt) {}
    )

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadRequests()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LazyColumn {
        items(folders.chunked(2)) { folderRow ->
            Row {
                folderRow.forEach { folder ->
                    AdminFolderCard(
                        state = folder.state,
                        folderName = folder.text,
                        folderIcon = folder.icon,
                        containerColor = folderColor,
                        headColor = headColor,
                        onClick = folder.onClick,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (folderRow.size < 2) {
                    Box(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun AdminFolderCard(
    headColor: Color,
    containerColor: Color,
    folderName: String,
    folderIcon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: AdminState?
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .padding(15.dp)
            .pulsate()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AdminFolderHead(modifier = Modifier.align(Alignment.Start), headColor = headColor)
        AdminFolderContent(icon = folderIcon, headColor = headColor, containerColor = containerColor, state = state)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = folderName)
    }
}

@Composable
fun AdminFolderHead(
    headColor: Color,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .height(20.dp)
            .fillMaxWidth(0.5f)
            .clip(RoundedCornerShape(8.dp, 0.dp, 0.dp, 0.dp))
            .clip(TrapezoidShapeUpper())
            .background(headColor)
    )
}

@Composable
fun AdminFolderContent(
    icon: ImageVector,
    headColor: Color,
    containerColor: Color,
    state: AdminState?
) {
    Box {
        Box(
            modifier = Modifier
                .height(124.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(0.dp, 8.dp, 8.dp, 8.dp))
                .background(headColor)
        )
        Box(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(containerColor, RoundedCornerShape(10.dp))
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = LocalAdminFolderColorsPalette.current.iconColor.copy(alpha = 0.3f),
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxSize()
                )
            }
            AdminTrapezoid(headColor, state)
        }
    }
}

@Composable
fun AdminTrapezoid(
    headColor: Color,
    state: AdminState?
) {
    val showFolderContent = (state is AdminState.Success && state.requestsSize != 0) || state == null

    Box {
        Box(
            modifier = Modifier
                .height(13.dp)
                .fillMaxWidth(0.5f)
                .clip(TrapezoidShapeLower(0.75f))
                .background(headColor)
        )
        AnimatedVisibility (
            visible = showFolderContent,
            enter = slideInVertically { it } + expandVertically { it }
        ) {
            Box(
                Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 8.dp)
                    .clip(RoundedCornerShape(4.dp, 0.dp, 0.dp, 0.dp))
                    .clip(TrapezoidShapeLower(0.85f))
                    .fillMaxWidth(0.43f)
                    .height(5.dp)
                    .background(Color.White)
            )
        }
        Box(
            Modifier
                .clip(RoundedCornerShape(0.dp, 8.dp, 0.dp, 0.dp))
                .background(headColor)
                .height(4.dp)
                .fillMaxWidth()
        )
    }
}

data class Folder(
    val text: String,
    val icon: ImageVector,
    val onClick: () -> Unit,
    val state: AdminState? = null
)