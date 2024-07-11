package com.zenitech.imaapp.navigation

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zenitech.imaapp.ui.theme.NavigationBarCustomColorsPalette


val items = listOf<BottomNavigationBarItem>(
    BottomNavigationBarItem.MyDevices,
    BottomNavigationBarItem.Request,
    BottomNavigationBarItem.QRReader,
    BottomNavigationBarItem.Admin,
)

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    BottomAppBar(
        modifier = Modifier.shadow(0.dp),
        containerColor = NavigationBarCustomColorsPalette.current.bottomContainerBar,
        tonalElevation = 0.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { item ->

            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavigationBarCustomColorsPalette.current.selectedIconColor,
                    selectedTextColor = NavigationBarCustomColorsPalette.current.selectedTextColor,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = NavigationBarCustomColorsPalette.current.unselectedIconColor,
                    unselectedTextColor = NavigationBarCustomColorsPalette.current.unselectedTextColor,
                ),
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(stringResource(item.resourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == item.screen::class.qualifiedName } == true,
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