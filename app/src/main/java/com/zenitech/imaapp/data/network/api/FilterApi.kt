package com.zenitech.imaapp.data.network.api

import retrofit2.http.*
import retrofit2.Call

import com.zenitech.imaapp.data.model.Filter

interface FilterApi {
    /**
     * 
     * Create a new filter
     * Responses:
     *  - 201: Created
     *  - 409: Conflict
     *
     * @param filter 
     * @return [Call]<[Unit]>
     */
    @POST("filters")
    fun createFilter(@Body filter: Filter): Call<Unit>

    /**
     * 
     * Delete a filter by userId
     * Responses:
     *  - 204: No Content
     *  - 404: Not Found
     *
     * @param name 
     * @return [Call]<[Unit]>
     */
    @DELETE("filters/{name}")
    fun deleteFilterByUserId(@Path("name") name: kotlin.String): Call<Unit>

    /**
     * 
     * Get all filter by user Response list is ordered by filter creation date
     * Responses:
     *  - 200: OK
     *  - 404: Not Found
     *
     * @return [Call]<[kotlin.collections.List<Filter>]>
     */
    @GET("filters")
    fun getFiltersByUserId(): Call<kotlin.collections.List<Filter>>

}
