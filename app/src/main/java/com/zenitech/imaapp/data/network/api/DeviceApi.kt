package com.zenitech.imaapp.data.network.api

import retrofit2.http.*
import retrofit2.Call

import com.zenitech.imaapp.data.model.DeviceCreateRequest
import com.zenitech.imaapp.data.model.DevicePage
import com.zenitech.imaapp.data.model.DeviceResponse
import com.zenitech.imaapp.data.model.DeviceSearchRequest

interface DeviceApi {
    /**
     * 
     * Create Device
     * Responses:
     *  - 200: OK
     *
     * @param deviceCreateRequest 
     * @return [Call]<[DeviceResponse]>
     */
    @POST("devices")
    fun createDevice(@Body deviceCreateRequest: DeviceCreateRequest): Call<DeviceResponse>

    /**
     * 
     * Discard Device
     * Responses:
     *  - 204: OK
     *
     * @param inventoryId 
     * @return [Call]<[Unit]>
     */
    @PATCH("devices/{inventory-id}/discard")
    fun discardDevice(@Path("inventory-id") inventoryId: String): Call<Unit>

    /**
     * 
     * Get all users
     * Responses:
     *  - 200: OK
     *
     * @param page 
     * @param size 
     * @param deviceSearchRequest 
     * @param sort  (optional)
     * @return [Call]<[DevicePage]>
     */
    @POST("devices/search")
    fun getAllDevices(@Query("page") page: Int, @Query("size") size: Int, @Body deviceSearchRequest: DeviceSearchRequest, @Query("sort") sort: String? = null): Call<DevicePage>

    /**
     * 
     * Get Device
     * Responses:
     *  - 200: OK
     *
     * @param inventoryId 
     * @return [Call]<[DeviceResponse]>
     */
    @GET("devices/{inventory-id}")
    fun getDevice(@Path("inventory-id") inventoryId: String): Call<DeviceResponse>

}
