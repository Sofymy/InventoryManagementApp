package com.zenitech.imaapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zenitech.imaapp.navigation.BottomNavigationBar
import com.zenitech.imaapp.navigation.NavGraph
import com.zenitech.imaapp.navigation.TopNavigationBar
import com.zenitech.imaapp.ui.theme.IMAAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
){
    var topNavigationBarTitle by remember {
        mutableStateOf("")
    }

    IMAAppTheme() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopNavigationBar(
                    navController = navController,
                    topNavigationBarTitle = topNavigationBarTitle,
                )
            },
            bottomBar = {
                BottomNavigationBar(navController = navController)
            }
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .padding(innerPadding)
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