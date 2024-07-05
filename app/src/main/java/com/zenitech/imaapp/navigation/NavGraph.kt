package com.zenitech.imaapp.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zenitech.imaapp.feature.devicelist.DeviceListScreen
import com.zenitech.imaapp.feature.login.LoginScreen
import com.zenitech.imaapp.feature.qr_reader.QRReaderScreen

@ExperimentalMaterial3Api
@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.Login) {

        composable<Screen.Login> {
            LoginScreen()
        }

        composable<Screen.DeviceList> {
            DeviceListScreen()
        }

        composable<Screen.QRReader> {
            QRReaderScreen()
        }

    }
}