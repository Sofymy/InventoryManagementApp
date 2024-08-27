package com.zenitech.imaapp.data.network.api

import retrofit2.http.*
import retrofit2.Call

import com.zenitech.imaapp.data.model.ReportFormat
import com.zenitech.imaapp.data.model.ReportResponse
import com.zenitech.imaapp.data.model.ReportType

interface ReportApi {
    /**
     * 
     * Generate requested report
     * Responses:
     *  - 201: Created
     *
     * @param reportFormat 
     * @param reportType 
     * @return [Call]<[ReportResponse]>
     */
    @GET("report/{report-type}/{report-format}")
    fun generateReport(@Path("report-format") reportFormat: ReportFormat, @Path("report-type") reportType: ReportType): Call<ReportResponse>

}
