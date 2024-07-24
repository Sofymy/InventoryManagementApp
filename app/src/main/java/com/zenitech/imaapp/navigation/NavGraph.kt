package com.zenitech.imaapp.navigation

import android.os.Bundle
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.google.firebase.analytics.FirebaseAnalytics
import com.zenitech.imaapp.feature.admin.AdminScreen
import com.zenitech.imaapp.feature.device_details.DeviceDetailsScreen
import com.zenitech.imaapp.feature.devicelist.DeviceListScreen
import com.zenitech.imaapp.feature.my_devices.MyDevicesScreen
import com.zenitech.imaapp.feature.qr_reader.QRReaderScreen
import com.zenitech.imaapp.feature.request.RequestScreen
import com.zenitech.imaapp.feature.sign_in.SignInScreen
import kotlinx.serialization.Serializable


@Serializable object Main
@Serializable object MyDevices
@Serializable object Request
@Serializable object QRReader
@Serializable object Admin


@OptIn(ExperimentalSharedTransitionApi::class)
@ExperimentalMaterial3Api
@Composable
fun NavGraph(
    navController: NavHostController,
    onTopNavigationBarTitleChange: (String) -> Unit,
) {

    val context = LocalContext.current
    val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    setupScreenTracking(navController, firebaseAnalytics)

    SharedTransitionLayout(
    ) {
        NavHost(navController, startDestination = Screen.SignIn) {


            composable<Screen.SignIn> {
                SignInScreen(
                    onNavigateToMyDevices = {
                        navController.navigate(MyDevices){
                            popUpTo(Screen.SignIn) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            navigation<Request>(startDestination = Screen.Request) {

                composable<Screen.Request> {
                    onTopNavigationBarTitleChange("Request Test Device")
                    RequestScreen()
                }
            }

            navigation<QRReader>(startDestination = Screen.QRReader) {

                composable<Screen.QRReader> {
                    onTopNavigationBarTitleChange("QR Reader")
                    QRReaderScreen()
                }
            }

            navigation<Admin>(startDestination = Screen.Admin) {

                composable<Screen.Admin> {
                    onTopNavigationBarTitleChange("Admin")
                    AdminScreen()
                }
            }

            navigation<MyDevices>(startDestination = Screen.MyDevices) {
                composable<Screen.MyDevices> {
                    onTopNavigationBarTitleChange("My Devices")
                    MyDevicesScreen(
                        onNavigateToDeviceDetails = { device ->
                            navController.navigate(Screen.DeviceDetails(id = device))
                        },
                        this@SharedTransitionLayout,
                        this@composable
                    )
                }

                composable<Screen.DeviceDetails> { backStackEntry ->
                    onTopNavigationBarTitleChange("Device Details")
                    val device: Screen.DeviceDetails = backStackEntry.toRoute()
                    DeviceDetailsScreen(
                        device = device.id,
                        this@SharedTransitionLayout,
                        this@composable
                    )
                }
            }

            composable<Screen.DeviceList> {
                onTopNavigationBarTitleChange("Device List")
                DeviceListScreen()
            }
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