package com.zenitech.imaapp.ui.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

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
                Row(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    items.forEach { item ->
                        NavigationItem(
                            item = item,
                            currentDestination = currentDestination,
                            navController = navController,
                            modifier = Modifier.weight(1f)
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
    modifier: Modifier
) {
    val selected =
        currentDestination?.hierarchy?.any { currentDestination.parent?.startDestinationRoute?.substringBefore("/").toString() == item.screen::class.qualifiedName } == true

    val contentColor =
        if (selected) LocalNavigationBarColorsPalette.current.selectedTextColor
        else LocalNavigationBarColorsPalette.current.unselectedTextColor

    val animatedColor by animateColorAsState(
        targetValue = contentColor,
        animationSpec = tween(
            durationMillis = 0,
            easing = LinearEasing
        ),
        label = ""
    )

    Box(
        modifier = modifier,
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
            AnimatedContent(
                selected,
                transitionSpec = {
                    fadeIn(animationSpec = tween(durationMillis = 0)) togetherWith
                            fadeOut(animationSpec = tween(durationMillis = 0))
                }, label = ""
            ) { selected ->
                Icon(imageVector = if (selected) item.selectedIcon else item.unselectedIcon, contentDescription = "", tint = animatedColor)
            }
            Spacer(Modifier.height(5.dp))
            Text(text = stringResource(item.screen.resourceId), color = animatedColor, textAlign = TextAlign.Center)
        }
    }
}