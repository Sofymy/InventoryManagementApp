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
import com.google.firebase.analytics.FirebaseAnalytics
import com.zenitech.imaapp.feature.admin.devices.add_device.AdminDevicesAddDeviceScreen
import com.zenitech.imaapp.feature.admin.devices.add_device_successful.AdminDevicesAddDeviceSuccessfulScreen
import com.zenitech.imaapp.feature.admin.devices.device_types.AdminDevicesAddDeviceTypesScreen
import com.zenitech.imaapp.feature.admin.devices.devices.AdminDevicesScreen
import com.zenitech.imaapp.feature.admin.AdminScreen
import com.zenitech.imaapp.feature.admin.devices.device_assets.AdminDevicesDeviceAssetsScreen
import com.zenitech.imaapp.feature.admin.devices.device_details.AdminDeviceDetailsScreen
import com.zenitech.imaapp.feature.admin.devices.device_manufacturers.AdminDevicesDeviceManufacturersScreen
import com.zenitech.imaapp.feature.admin.devices.device_sites.AdminDevicesDeviceSitesScreen
import com.zenitech.imaapp.feature.admin.manage_requests.ManageRequestsScreen
import com.zenitech.imaapp.feature.my_devices.device_details.DeviceDetailsScreen
import com.zenitech.imaapp.feature.my_devices.MyDevicesScreen
import com.zenitech.imaapp.feature.qr_reader.QRReaderScreen
import com.zenitech.imaapp.feature.request_test_device.RequestTestDeviceManufacturerScreen
import com.zenitech.imaapp.feature.request_test_device.RequestTestDeviceScreen
import com.zenitech.imaapp.feature.request_test_device.RequestTestDeviceSuccessfulScreen
import com.zenitech.imaapp.feature.request_test_device.RequestTestDeviceTypeScreen
import com.zenitech.imaapp.feature.sign_in.SignInScreen
import kotlinx.serialization.Serializable


@Serializable object MyDevices
@Serializable object RequestTestDevice
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

    SharedTransitionLayout {
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

            navigation<RequestTestDevice>(startDestination = Screen.RequestTestDevice()) {

                composable<Screen.RequestTestDevice> {backStackEntry ->
                    val type = backStackEntry.savedStateHandle.get<String>("type")
                    val manufacturer = backStackEntry.savedStateHandle.get<String>("manufacturer")

                    onTopNavigationBarTitleChange("Request Test Device")
                    RequestTestDeviceScreen(
                        onNavigateToDeviceType = {
                            navController.navigate(Screen.RequestTestDeviceType(manufacturer = manufacturer))
                        },
                        onNavigateToDeviceManufacturer = {
                            navController.navigate(Screen.RequestTestDeviceManufacturer(type = type))
                        },
                        onNavigateTestDeviceSuccessful = {
                            navController.navigate(Screen.RequestTestDeviceSuccessful){
                                popUpTo(Screen.RequestTestDevice()){
                                    inclusive = true
                                }
                            }
                        },
                        type = type?:"",
                        manufacturer = manufacturer?:"",
                        )
                }

                composable<Screen.RequestTestDeviceType> {backStackEntry ->

                    onTopNavigationBarTitleChange("Test Device Type")
                    RequestTestDeviceTypeScreen(
                        onNavigateToRequestTestDevice = { it ->
                            navController.previousBackStackEntry?.savedStateHandle?.set("type", it)
                            navController.popBackStack()
                        },
                    )
                }

                composable<Screen.RequestTestDeviceManufacturer> { backStackEntry ->

                    onTopNavigationBarTitleChange("Device Type Manufacturer")
                    RequestTestDeviceManufacturerScreen(
                        onNavigateToRequestTestDevice = {
                            navController.previousBackStackEntry?.savedStateHandle?.set("manufacturer", it)
                            navController.popBackStack()
                        }
                    )
                }

                composable<Screen.RequestTestDeviceSuccessful> {
                    onTopNavigationBarTitleChange("Request Test Device")

                    RequestTestDeviceSuccessfulScreen(
                        onNavigateToRequestTestDevice = {
                            navController.navigate(Screen.RequestTestDevice()){
                                popUpTo(Screen.RequestTestDeviceSuccessful){
                                    inclusive = true
                                }
                            }
                        }
                    )
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
                    onTopNavigationBarTitleChange("Admin Panel")
                    AdminScreen(
                        onNavigateToAdminDevices = {
                            navController.navigate(Screen.AdminDevices)
                        },
                        onNavigateToManageRequests = {
                            navController.navigate(Screen.ManageRequests)
                        }
                    )
                }

                composable<Screen.AdminDevices> {
                    onTopNavigationBarTitleChange("Devices")
                    AdminDevicesScreen(
                        onNavigateToAdminDeviceDetails = {
                            navController.navigate(Screen.AdminDeviceDetails(it))
                        },
                        onNavigateToAdminDevicesAddDevice = {
                            navController.navigate(Screen.AdminDevicesAddDevice)
                        }
                    )
                }

                composable<Screen.ManageRequests> {
                    onTopNavigationBarTitleChange("Test device requests")
                    ManageRequestsScreen()
                }

                composable<Screen.AdminDeviceDetails> {backStackEntry ->
                    onTopNavigationBarTitleChange("Device Details")

                    val type = backStackEntry.savedStateHandle.get<String>("type")
                    val manufacturer = backStackEntry.savedStateHandle.get<String>("manufacturer")
                    val asset = backStackEntry.savedStateHandle.get<String>("asset")
                    val site = backStackEntry.savedStateHandle.get<String>("site")
                    val inventoryId = backStackEntry.arguments?.getString("inventoryId")

                    AdminDeviceDetailsScreen(
                        inventoryId = inventoryId ?: "",
                        type = type,
                        manufacturer = manufacturer,
                        asset = asset,
                        site = site,
                        onNavigateToAdminDeviceTypes = {
                            navController.navigate(
                                Screen.AdminDevicesDeviceTypes
                            )
                        },
                        onNavigateToAdminDeviceManufacturers = {
                            navController.navigate(
                                Screen.AdminDevicesDeviceManufacturers
                            )
                        },
                        onNavigateToAdminDeviceAssets = {
                            navController.navigate(
                                Screen.AdminDevicesDeviceAssets
                            )
                        },
                        onNavigateToAdminDeviceSites = {
                            navController.navigate(
                                Screen.AdminDevicesDeviceSites
                            )
                        }
                    )
                }

                composable<Screen.AdminDevicesAddDevice> {backStackEntry ->
                    val type = backStackEntry.savedStateHandle.get<String>("type")
                    val manufacturer = backStackEntry.savedStateHandle.get<String>("manufacturer")
                    val asset = backStackEntry.savedStateHandle.get<String>("asset")
                    val site = backStackEntry.savedStateHandle.get<String>("site")

                    onTopNavigationBarTitleChange("Add device")
                    AdminDevicesAddDeviceScreen(
                        onNavigateToAdminDeviceTypes = {
                            navController.navigate(
                                Screen.AdminDevicesDeviceTypes
                            )
                        },
                        type = type ?: "",
                        onNavigateToAdminDeviceManufacturers = {
                            navController.navigate(
                                Screen.AdminDevicesDeviceManufacturers
                            )
                        },
                        manufacturer = manufacturer ?: "",
                        onNavigateToAdminDeviceAssets = {
                            navController.navigate(
                                Screen.AdminDevicesDeviceAssets
                            )
                        },
                        asset = asset ?: "",
                        onNavigateToAdminDeviceSites = {
                            navController.navigate(
                                Screen.AdminDevicesDeviceSites
                            )
                        },
                        site = site ?: "",
                        onNavigateToAdminAddDeviceSuccessful = {
                            navController.navigate(Screen.AdminDevicesAddDeviceSuccessful){
                                popUpTo(Screen.AdminDevices){
                                    inclusive = true
                                }
                            }
                        }
                    )
                }

                composable<Screen.AdminDevicesDeviceTypes> {backStackEntry ->

                    onTopNavigationBarTitleChange("Add device > type")
                    AdminDevicesAddDeviceTypesScreen(
                        onNavigateToAdminAddDevice = {
                            navController.previousBackStackEntry?.savedStateHandle?.set("type", it)
                            navController.popBackStack()
                    })
                }

                composable<Screen.AdminDevicesDeviceManufacturers> {backStackEntry ->

                    onTopNavigationBarTitleChange("Add device > manufacturer")
                    AdminDevicesDeviceManufacturersScreen(
                        onNavigateToAdminAddDevice = {
                            navController.previousBackStackEntry?.savedStateHandle?.set("manufacturer", it)
                            navController.popBackStack()
                        })
                }

                composable<Screen.AdminDevicesDeviceAssets> {backStackEntry ->

                    onTopNavigationBarTitleChange("Add device > asset")
                    AdminDevicesDeviceAssetsScreen(
                        onNavigateToAdminAddDevice = {
                            navController.previousBackStackEntry?.savedStateHandle?.set("asset", it)
                            navController.popBackStack()
                        })
                }

                composable<Screen.AdminDevicesDeviceSites> {backStackEntry ->

                    onTopNavigationBarTitleChange("Add device > site")
                    AdminDevicesDeviceSitesScreen(
                        onNavigateToAdminAddDevice = {
                            navController.previousBackStackEntry?.savedStateHandle?.set("site", it)
                            navController.popBackStack()
                        })
                }

                composable<Screen.AdminDevicesAddDeviceSuccessful> {
                    onTopNavigationBarTitleChange("Success!")

                    AdminDevicesAddDeviceSuccessfulScreen(
                        onNavigateToAdminDevices = {
                            navController.navigate(Screen.AdminDevices){
                                popUpTo(Screen.AdminDevicesAddDeviceSuccessful){
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
            }

            navigation<MyDevices>(startDestination = Screen.MyDevices) {
                composable<Screen.MyDevices> {
                    onTopNavigationBarTitleChange("My Devices")
                    MyDevicesScreen(
                        onNavigateToDeviceDetails = { inventoryId ->
                            navController.navigate(Screen.DeviceDetails(inventoryId = inventoryId))
                        },
                    )
                }

                composable<Screen.DeviceDetails> { backStackEntry ->
                    onTopNavigationBarTitleChange("Device Details")
                    val inventoryId = backStackEntry.arguments?.getString("inventoryId")
                    DeviceDetailsScreen(
                        inventoryId = inventoryId ?: "",
                        this@SharedTransitionLayout,
                        this@composable
                    )
                }
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