package com.zenitech.imaapp.data.network.api

import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody
import com.squareup.moshi.Json

import org.openapitools.client.models.DeviceCreateRequest
import org.openapitools.client.models.DevicePage
import org.openapitools.client.models.DeviceResponse
import org.openapitools.client.models.DeviceSearchRequest
import org.openapitools.client.models.MyDeviceResponse
import org.openapitools.client.models.Tag

interface DeviceApi {
    /**
     * 
     * Assign tags to device
     * Responses:
     *  - 204: No Content
     *  - 404: Not Found
     *  - 409: Conflict
     *
     * @param inventoryId 
     * @param tag 
     * @return [Call]<[Unit]>
     */
    @PUT("devices/{inventoryId}/tags")
    fun assignTags(@Path("inventoryId") inventoryId: String, @Body tag: List<Tag>): Call<Unit>

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
    @PATCH("devices/{inventoryId}/discard")
    fun discardDevice(@Path("inventoryId") inventoryId: String): Call<Unit>

    /**
     * 
     * Returns all existing Asset Name, ordered alphabetically
     * Responses:
     *  - 200: OK
     *
     * @return [Call]<[kotlin.collections.List<kotlin.String>]>
     */
    @GET("devices/filters/assetNames")
    fun getAllAssetName(): Call<List<String>>

    /**
     * 
     * Returns all existing Condition
     * Responses:
     *  - 200: OK
     *
     * @return [Call]<[kotlin.collections.List<kotlin.String>]>
     */
    @GET("devices/filters/conditions")
    fun getAllCondition(): Call<List<String>>

    /**
     * 
     * Returns all existing Location, ordered alphabetically
     * Responses:
     *  - 200: OK
     *
     * @return [Call]<[kotlin.collections.List<kotlin.String>]>
     */
    @GET("devices/filters/locations")
    fun getAllLocation(): Call<List<String>>

    /**
     * 
     * Returns all existing Manufacturer, ordered alphabetically
     * Responses:
     *  - 200: OK
     *
     * @return [Call]<[kotlin.collections.List<kotlin.String>]>
     */
    @GET("devices/filters/manufacturers")
    fun getAllManufacturer(): Call<List<String>>

    /**
     * 
     * Returns all existing Site, ordered alphabetically
     * Responses:
     *  - 200: OK
     *
     * @return [Call]<[kotlin.collections.List<kotlin.String>]>
     */
    @GET("devices/filters/sites")
    fun getAllSite(): Call<List<String>>

    /**
     * 
     * Returns all existing Supplier, ordered alphabetically
     * Responses:
     *  - 200: OK
     *
     * @return [Call]<[kotlin.collections.List<kotlin.String>]>
     */
    @GET("devices/filters/suppliers")
    fun getAllSupplier(): Call<List<String>>

    /**
     * 
     * Returns all existing Device Type, ordered alphabetically
     * Responses:
     *  - 200: OK
     *
     * @return [Call]<[kotlin.collections.List<kotlin.String>]>
     */
    @GET("devices/filters/types")
    suspend fun getAllType(): List<String>

    /**
     * 
     * Get Device
     * Responses:
     *  - 200: OK
     *
     * @param inventoryId 
     * @return [Call]<[DeviceResponse]>
     */
    @GET("devices/{inventoryId}")
    fun getDevice(@Path("inventoryId") inventoryId: String): Call<DeviceResponse>

    /**
     * 
     * Get devices currently leased by the user. The identity of the user is determined by the JWT token.
     * Responses:
     *  - 200: List of devices currently leased by the user
     *  - 404: Not Found
     *
     * @return [Call]<[kotlin.collections.List<MyDeviceResponse>]>
     */
    @GET("mydevices")
    suspend fun getMyDevices(): List<MyDeviceResponse>

    /**
     * 
     * Remove tags from device
     * Responses:
     *  - 204: No Content
     *  - 404: Not Found
     *
     * @param inventoryId 
     * @param tag 
     * @return [Call]<[Unit]>
     */
    @DELETE("devices/{inventoryId}/tags")
    fun removeTags(@Path("inventoryId") inventoryId: String, @Body tag: List<Tag>): Call<Unit>

    /**
     * 
     * Get requested Devices
     * Responses:
     *  - 200: OK
     *
     * @param page 
     * @param size 
     * @param sort  (optional)
     * @param deviceSearchRequest  (optional)
     * @return [Call]<[DevicePage]>
     */
    @POST("devices/search")
    fun searchDevices(@Query("page") page: Int, @Query("size") size: Int, @Query("sort") sort: String? = null, @Body deviceSearchRequest: DeviceSearchRequest? = null): Call<DevicePage>

}
