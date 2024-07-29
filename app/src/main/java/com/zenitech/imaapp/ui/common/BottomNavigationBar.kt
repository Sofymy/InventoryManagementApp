package com.zenitech.imaapp.ui.common

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zenitech.imaapp.navigation.Screen
import com.zenitech.imaapp.ui.theme.LocalNavigationBarColorsPalette


val items = listOf(
    BottomNavigationBarItem.MyDevices,
    BottomNavigationBarItem.Request,
    BottomNavigationBarItem.QRReader,
    BottomNavigationBarItem.Admin,
)

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalDivider(color = LocalNavigationBarColorsPalette.current.bottomContainerBarDividerColor)
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth(),
                containerColor = LocalNavigationBarColorsPalette.current.bottomContainerBarColor,
                tonalElevation = 0.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    items.forEach { item ->
                        NavigationItem(
                            item = item,
                            currentDestination = currentDestination,
                            navController = navController,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationItem(
    item: BottomNavigationBarItem,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    val selected =
        currentDestination?.hierarchy?.any { currentDestination.parent?.startDestinationRoute?.substringBefore("/").toString() == item.screen::class.qualifiedName } == true

    val contentColor =
        if (selected) LocalNavigationBarColorsPalette.current.selectedTextColor
        else LocalNavigationBarColorsPalette.current.unselectedTextColor

    val animatedColor by animateColorAsState(
        targetValue = contentColor,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing
        ),
        label = ""
    )

    Box(
        modifier = Modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .pulsate()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        navController.navigate(item.screen) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    }
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = if (selected) item.icon else item.icon,
                contentDescription = "icon",
                tint = animatedColor,
                modifier = Modifier
            )
            Spacer(Modifier.height(5.dp))
            Text(text = stringResource(item.screen.resourceId), color = animatedColor)
        }
    }
}