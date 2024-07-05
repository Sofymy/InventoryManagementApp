package com.zenitech.imaapp.data.network.api

import retrofit2.http.*
import retrofit2.Call

import com.zenitech.imaapp.data.model.UserPage
import com.zenitech.imaapp.data.model.UserSearchRequest

interface UserApi {
    /**
     * 
     * Get all users
     * Responses:
     *  - 200: OK
     *
     * @param page 
     * @param size 
     * @param userSearchRequest 
     * @param sort  (optional)
     * @return [Call]<[UserPage]>
     */
    @POST("users")
    fun getAllUsers(@Query("page") page: Int, @Query("size") size: Int, @Body userSearchRequest: UserSearchRequest, @Query("sort") sort: String? = null): Call<UserPage>

}
