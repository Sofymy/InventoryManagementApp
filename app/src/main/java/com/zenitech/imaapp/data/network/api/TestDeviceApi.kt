package org.openapitools.client.apis

import org.openapitools.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody
import com.squareup.moshi.Json

import org.openapitools.client.models.GetTestDeviceManufacturersResponse

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
