package com.zenitech.imaapp

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.zenitech.imaapp.feature.sign_in.SignInUserEvent
import com.zenitech.imaapp.feature.sign_in.SignInViewModel
import com.zenitech.imaapp.navigation.NavGraph
import com.zenitech.imaapp.navigation.Screen
import com.zenitech.imaapp.ui.common.BottomNavigationBar
import com.zenitech.imaapp.ui.common.TopNavigationBar
import com.zenitech.imaapp.ui.theme.IMAAppTheme
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                        }
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
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                ) {
                    Button(onClick = {
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
                        Text("Sign out")
                    }
                }
            }
        }
    }
}
