package com.zenitech.imaapp.ui.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ArrowBack
import androidx.compose.material.icons.automirrored.twotone.Sort
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zenitech.imaapp.ui.theme.EerieBlack10White
import com.zenitech.imaapp.ui.theme.LocalNavigationBarColorsPalette
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(
    navController: NavHostController,
    topNavigationBarTitle: String,
    onShowBottomSheet: () -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val show = remember { mutableStateOf(false) }

    LaunchedEffect(currentDestination?.route) {
        delay(1500)
        show.value = true
    }

    Column {
        TopAppBar(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .shadow(0.dp),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = LocalNavigationBarColorsPalette.current.topContainerBarColor,
                titleContentColor = LocalNavigationBarColorsPalette.current.containerTextColor,
            ),
            title = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AnimatedContent(
                        targetState = topNavigationBarTitle,
                        transitionSpec = {
                            fadeIn(tween(durationMillis = 100)) togetherWith fadeOut(tween(durationMillis = 100))
                        }, label = ""
                    ) { targetTitle ->
                        Text(
                            text = targetTitle,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            },
            navigationIcon = {
                if (navController.previousBackStackEntry != null && (items.none {
                        it.screen::class.qualifiedName.toString() == currentDestination?.route?.substringBefore(
                            "/"
                        ).toString()
                    }
                            )) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.TwoTone.ArrowBack,
                            contentDescription = null
                        )
                    }
                } else {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.TwoTone.Sort,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        onShowBottomSheet()
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = if (isSystemInDarkTheme()) EerieBlack10White else Color.LightGray.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier
                ) {
                    Column {
                        Icon(imageVector = Icons.Rounded.Person, contentDescription = null)
                    }
                }
            }
        )

        if(topNavigationBarTitle != "Devices"){
            HorizontalDivider(color = LocalNavigationBarColorsPalette.current.bottomContainerBarDividerColor)
        }
    }
}