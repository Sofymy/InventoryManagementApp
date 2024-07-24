package com.zenitech.imaapp.ui.common

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zenitech.imaapp.ui.theme.LocalButtonColorsPalette
import com.zenitech.imaapp.ui.theme.LocalNavigationBarColorsPalette
import kotlinx.coroutines.delay


val items = listOf(
    BottomNavigationBarItem.MyDevices,
    BottomNavigationBarItem.Request,
    BottomNavigationBarItem.QRReader,
    BottomNavigationBarItem.Admin,
)

@Composable
fun BottomNavigationBar(
    navController: NavController,
    bottomBarState: MutableState<Boolean>
) {


    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically (
            initialOffsetY = {
                it
            },
        ),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            Column {
                HorizontalDivider(
                    color = LocalNavigationBarColorsPalette.current.bottomContainerBarDividerColor,
                    thickness = 1.dp
                )
                BottomAppBar(
                    modifier = Modifier
                        .shadow(0.dp)
                    ,
                    containerColor = LocalNavigationBarColorsPalette.current.bottomContainerBarColor,
                    tonalElevation = 0.dp
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    items.forEach { item ->

                        NavigationBarItem(
                            modifier = Modifier.pulsate(),
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = LocalNavigationBarColorsPalette.current.selectedIconColor,
                                selectedTextColor = LocalNavigationBarColorsPalette.current.selectedTextColor,
                                indicatorColor = Color.Transparent,
                                unselectedIconColor = LocalNavigationBarColorsPalette.current.unselectedIconColor,
                                unselectedTextColor = LocalNavigationBarColorsPalette.current.unselectedTextColor,
                            ),
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(stringResource(item.resourceId), fontWeight = FontWeight.Bold) },
                            selected = currentDestination?.hierarchy?.any { currentDestination.parent?.startDestinationRoute == item.screen::class.qualifiedName} == true,
                            onClick = {
                                navController.navigate(item.screen) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}