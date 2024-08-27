package com.zenitech.imaapp.data.network.api

import retrofit2.http.*
import retrofit2.Call

import com.zenitech.imaapp.data.model.DeviceSearchRequest

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
