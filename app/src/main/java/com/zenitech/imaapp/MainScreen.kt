package com.zenitech.imaapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zenitech.imaapp.feature.sign_in.SignInUserEvent
import com.zenitech.imaapp.feature.sign_in.SignInViewModel
import com.zenitech.imaapp.navigation.NavGraph
import com.zenitech.imaapp.navigation.Screen
import com.zenitech.imaapp.ui.common.BottomNavigationBar
import com.zenitech.imaapp.ui.common.PrimaryButton
import com.zenitech.imaapp.ui.common.TopNavigationBar
import com.zenitech.imaapp.ui.theme.IMAAppTheme
import com.zenitech.imaapp.ui.theme.LocalCardColorsPalette
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    signInViewModel: SignInViewModel = hiltViewModel()
) {
    var topNavigationBarTitle by remember {
        mutableStateOf("")
    }

    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    val topBarState = rememberSaveable { (mutableStateOf(false)) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(navBackStackEntry?.destination?.route) {
        when (navBackStackEntry?.destination?.route) {
            null, Screen.SignIn::class.qualifiedName.toString() -> {
                bottomBarState.value = false
                topBarState.value = false
            }
            else -> {
                bottomBarState.value = true
                topBarState.value = true
            }
        }
    }

    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    IMAAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if(topBarState.value)
                    TopNavigationBar(
                        navController = navController,
                        topNavigationBarTitle = topNavigationBarTitle,
                        onShowBottomSheet = {
                            showBottomSheet = true
                        },
                    )
            },
            bottomBar = {
                if(bottomBarState.value)
                    BottomNavigationBar(navController = navController)
            }
        ) { innerPadding ->
            Surface(
                modifier = Modifier.padding(innerPadding),
            ) {
                NavGraph(
                    navController = navController,
                    onTopNavigationBarTitleChange = {
                        topNavigationBarTitle = it
                    }
                )
            }
            if(showBottomSheet){
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    containerColor = LocalCardColorsPalette.current.containerColor,
                    contentColor = LocalCardColorsPalette.current.contentColor
                ) {
                    Column(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PrimaryButton(onClick = {
                            scope.launch {
                                showBottomSheet = false
                                signInViewModel.onEvent(SignInUserEvent.SignOut)
                                navController.navigate(Screen.SignIn) {
                                    popUpTo(Screen.MyDevices) {
                                        inclusive = true
                                    }
                                }
                            }
                        }) {
                            Text(stringResource(R.string.sign_out))
                        }
                    }
                }
            }
        }
    }
}
