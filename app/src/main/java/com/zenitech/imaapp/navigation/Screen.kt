package com.zenitech.imaapp.navigation

import androidx.annotation.StringRes
import com.zenitech.imaapp.R
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(
    @StringRes val resourceId: Int
){
    @Serializable
    data object SignIn: Screen(R.string.sign_in)

    @Serializable
    data object QRReader: Screen(R.string.qrreader)

    @Serializable
    data object Admin: Screen(R.string.admin)

    @Serializable
    data object MyDevices: Screen(R.string.mydevices)

    @Serializable
    data class DeviceDetails(val id: String) : Screen(R.string.devicedetails)

    @Serializable
    data object DeviceList: Screen(R.string.devicelist)

    @Serializable
    data class RequestTestDevice(
        val type: String? = null,
        val manufacturer: String? = null,
        val requestDate: String? = null,
        val returnDate: String? = null,
        val additionalRequests: String? = null
    ) : Screen(R.string.request) {

        companion object {
            fun withType(
                type: String,
                requestDate: String,
                returnDate: String,
                additionalRequests: String
            ): RequestTestDevice {
                return RequestTestDevice(
                    type = type,
                    requestDate = requestDate,
                    returnDate = returnDate,
                    additionalRequests = additionalRequests
                )
            }

            fun withManufacturer(
                manufacturer: String,
                requestDate: String,
                returnDate: String,
                additionalRequests: String
            ): RequestTestDevice {
                return RequestTestDevice(
                    manufacturer = manufacturer,
                    requestDate = requestDate,
                    returnDate = returnDate,
                    additionalRequests = additionalRequests
                )
            }
        }

    }


    @Serializable
    data class RequestTestDeviceType(val manufacturer: String? = null): Screen(R.string.request_type) {

        companion object {
            fun withManufacturer(
                manufacturer: String?
            ): RequestTestDeviceType {
                return RequestTestDeviceType(manufacturer = manufacturer)
            }
        }

    }

    @Serializable
    data class RequestTestDeviceManufacturer(val type: String ?= null): Screen(R.string.request_manufacturer){

        companion object {
            fun withType(
                type: String?
            ): RequestTestDeviceManufacturer {
                return RequestTestDeviceManufacturer(type = type)
            }
        }

    }

    @Serializable
    data object RequestTestDeviceSuccessful: Screen(R.string.devicelist)
}