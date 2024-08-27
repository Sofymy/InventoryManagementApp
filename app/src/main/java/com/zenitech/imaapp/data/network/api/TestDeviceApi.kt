package com.zenitech.imaapp.data.network.api

import retrofit2.http.*
import retrofit2.Call

import com.zenitech.imaapp.data.model.GetTestDeviceManufacturersResponse

interface TestDeviceApi {
    /**
     * 
     * Get the all the test device manufacturers and their manufactured assets.
     * Responses:
     *  - 200: A list of test device manufacturers and their assets.
     *
     * @return [Call]<[kotlin.collections.List<GetTestDeviceManufacturersResponse>]>
     */
    @GET("test-device-manufacturers")
    fun getTestDeviceManufacturers(): Call<List<GetTestDeviceManufacturersResponse>>

}
