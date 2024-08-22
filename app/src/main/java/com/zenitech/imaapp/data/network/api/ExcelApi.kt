package org.openapitools.client.apis

import org.openapitools.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody
import com.squareup.moshi.Json

import org.openapitools.client.models.DeviceSearchRequest

interface ExcelApi {
    /**
     * 
     * Export requested Devices to excel
     * Responses:
     *  - 201: Created
     *
     * @param sort  (optional)
     * @param deviceSearchRequest  (optional)
     * @return [Call]<[kotlin.ByteArray]>
     */
    @POST("export")
    fun exportToExcel(@Query("sort") sort: kotlin.String? = null, @Body deviceSearchRequest: DeviceSearchRequest? = null): Call<kotlin.ByteArray>

}
