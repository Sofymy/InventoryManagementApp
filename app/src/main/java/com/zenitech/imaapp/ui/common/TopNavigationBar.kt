package com.zenitech.imaapp.ui.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ArrowBack
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zenitech.imaapp.R
import com.zenitech.imaapp.ui.theme.EerieBlack10White
import com.zenitech.imaapp.ui.theme.LocalNavigationBarColorsPalette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(
    navController: NavHostController,
    topNavigationBarTitle: String,
    topBarState: MutableState<Boolean>
) {
    AnimatedVisibility(
        visible = topBarState.value,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            TopAppBar(
                modifier = Modifier
                    .shadow(0.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LocalNavigationBarColorsPalette.current.topContainerBarColor,
                    titleContentColor = LocalNavigationBarColorsPalette.current.containerTextColor,
                ),
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(topNavigationBarTitle, fontWeight = FontWeight.Bold, fontSize = 30.sp)
                    }
                },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.TwoTone.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
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
    )
}
