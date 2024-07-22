package com.zenitech.imaapp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zenitech.imaapp.navigation.NavGraph
import com.zenitech.imaapp.navigation.Screen
import com.zenitech.imaapp.ui.common.BottomNavigationBar
import com.zenitech.imaapp.ui.common.TopNavigationBar
import com.zenitech.imaapp.ui.theme.IMAAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
){
    var topNavigationBarTitle by remember {
        mutableStateOf("")
    }

    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val topBarState = rememberSaveable { (mutableStateOf(true)) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        null -> {
            bottomBarState.value = false
            topBarState.value = false
        }
        Screen.SignIn::class.qualifiedName.toString() -> {
            bottomBarState.value = false
            topBarState.value = false
        }
        else -> {
            bottomBarState.value = true
            topBarState.value = true
        }
    }


    IMAAppTheme() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopNavigationBar(
                    navController = navController,
                    topNavigationBarTitle = topNavigationBarTitle,
                    topBarState = topBarState
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    navController = navController,
                    bottomBarState = bottomBarState
                )
            }
        ) {  innerPadding ->
            Surface(
                modifier = Modifier
                    .padding(innerPadding),
            ) {
                NavGraph(
                    navController = navController,
                    onTopNavigationBarTitleChange = {
                        topNavigationBarTitle = it
                    }
                )
            }
        }
    }


}