package com.zenitech.imaapp.navigation

import android.os.Bundle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.analytics.FirebaseAnalytics
import com.zenitech.imaapp.feature.admin.AdminScreen
import com.zenitech.imaapp.feature.devicelist.DeviceListScreen
import com.zenitech.imaapp.feature.my_devices.MyDevicesScreen
import com.zenitech.imaapp.feature.qr_reader.QRReaderScreen
import com.zenitech.imaapp.feature.request.RequestScreen
import com.zenitech.imaapp.feature.sign_in.SignInScreen

@ExperimentalMaterial3Api
@Composable
fun NavGraph(
    navController: NavHostController,
    onTopNavigationBarTitleChange: (String) -> Unit,
) {

    val context = LocalContext.current
    val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    setupScreenTracking(navController, firebaseAnalytics)

    NavHost(navController, startDestination = Screen.MyDevices) {

        composable<Screen.SignIn> {
            onTopNavigationBarTitleChange("Sign In")
            SignInScreen()
        }

        composable<Screen.DeviceList> {
            onTopNavigationBarTitleChange("Device List")
            DeviceListScreen()
        }

        composable<Screen.QRReader> {
            onTopNavigationBarTitleChange("QR Reader")
            QRReaderScreen()
        }

        composable<Screen.MyDevices> {
            onTopNavigationBarTitleChange("My Devices")
            MyDevicesScreen()
        }

        composable<Screen.Admin> {
            onTopNavigationBarTitleChange("Admin")
            AdminScreen()
        }

        composable<Screen.Request> {
            onTopNavigationBarTitleChange("Request Test Device")
            RequestScreen()
        }

    }
}

private fun setupScreenTracking(navController: NavController, firebaseAnalytics: FirebaseAnalytics) {
    navController.addOnDestinationChangedListener { _, destination, _ ->
        val params = Bundle()
        params.putString(FirebaseAnalytics.Param.SCREEN_NAME, destination.route)
        params.putString(FirebaseAnalytics.Param.SCREEN_CLASS, destination.route)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
    }
}