package com.zenitech.imaapp.data.network.api

import retrofit2.http.*
import retrofit2.Call

import com.zenitech.imaapp.data.model.HistoryNoteRequest
import com.zenitech.imaapp.data.model.HistoryPage

interface HistoryApi {
    /**
     * 
     * Add new note in history
     * Responses:
     *  - 200: OK
     *  - 404: Not Found
     *  - 409: Conflict
     *
     * @param inventoryId 
     * @param historyNoteRequest 
     * @return [Call]<[Unit]>
     */
    @POST("history/{inventoryId}/note")
    fun addHistoryNote(@Path("inventoryId") inventoryId: kotlin.String, @Body historyNoteRequest: HistoryNoteRequest): Call<Unit>

    /**
     * 
     * Get device history
     * Responses:
     *  - 200: OK
     *
     * @param inventoryId 
     * @param page 
     * @param size 
     * @param sort  (optional)
     * @return [Call]<[HistoryPage]>
     */
    @GET("history/{inventoryId}")
    fun getHistory(@Path("inventoryId") inventoryId: kotlin.String, @Query("page") page: kotlin.Int, @Query("size") size: kotlin.Int, @Query("sort") sort: kotlin.String? = null): Call<HistoryPage>

}
