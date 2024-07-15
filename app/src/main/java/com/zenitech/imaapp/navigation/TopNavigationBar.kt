package com.zenitech.imaapp.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zenitech.imaapp.ui.theme.EerieBlack10White
import com.zenitech.imaapp.ui.theme.NavigationBarCustomColorsPalette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(
    navController: NavHostController,
    topNavigationBarTitle: String
) {
    TopAppBar(
        modifier = Modifier.shadow(0.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = NavigationBarCustomColorsPalette.current.topContainerBar,
            titleContentColor = NavigationBarCustomColorsPalette.current.containerTextColor,
        ),
        title = {
            Text(topNavigationBarTitle, fontWeight = FontWeight.Bold)
        },
        actions = {
            IconButton(
                onClick = {  },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = if(isSystemInDarkTheme()) EerieBlack10White else Color.LightGray.copy(alpha = 0.3f)
                ),
                modifier = Modifier

            ) {
                Column {
                    Text("Z")
                }
            }
        }
    )
}
