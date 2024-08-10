package com.zenitech.imaapp.feature.admin

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
import androidx.compose.material.icons.twotone.FilterAlt
import androidx.compose.material.icons.twotone.SupervisorAccount
import androidx.compose.material.icons.twotone.Tag
import androidx.compose.material.icons.twotone.Textsms
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zenitech.imaapp.ui.common.TrapezoidShapeLower
import com.zenitech.imaapp.ui.common.TrapezoidShapeUpper
import com.zenitech.imaapp.ui.common.pulsate
import com.zenitech.imaapp.ui.theme.LocalAdminFolderColorsPalette

@Composable
fun AdminScreen(
    onNavigateToAdminDevices: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AdminContent(onNavigateToAdminDevices)
    }
}

@Composable
fun AdminContent(
    onNavigateToAdminDevices: () -> Unit
) {
    val folderColor = LocalAdminFolderColorsPalette.current.contentColor
    val headColor = LocalAdminFolderColorsPalette.current.headColor

    val folders = listOf(
        Folder(text = "Devices", icon = Icons.TwoTone.Devices, onClick = onNavigateToAdminDevices),
        Folder(text = "Users", icon = Icons.TwoTone.SupervisorAccount) {},
        Folder(text = "Manage Requests", icon = Icons.TwoTone.Textsms) {},
        Folder(text = "Tags", icon = Icons.TwoTone.Tag) {},
        Folder(text = "Filters", icon = Icons.TwoTone.FilterAlt) {}
    )

    LazyColumn {
        items(folders.chunked(2)) { folderRow ->
            Row {
                folderRow.forEach { folder ->
                    AdminFolderCard(
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
    modifier: Modifier = Modifier
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
        AdminFolderContent(icon = folderIcon, headColor = headColor, containerColor = containerColor)
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
    containerColor: Color
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
            AdminTrapezoid(headColor)
        }
    }
}

@Composable
fun AdminTrapezoid(headColor: Color) {
    Box {
        Box(
            modifier = Modifier
                .height(13.dp)
                .fillMaxWidth(0.5f)
                .clip(TrapezoidShapeLower(0.75f))
                .background(headColor)
        )
        Box(
            Modifier
                .padding(start = 10.dp, end = 10.dp, top = 8.dp)
                .clip(RoundedCornerShape(4.dp, 0.dp, 0.dp, 0.dp))
                .clip(TrapezoidShapeLower(0.85f))
                .fillMaxWidth(0.43f)
                .height(5.dp)
                .background(Color.White)
        )
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
    val onClick: () -> Unit
)