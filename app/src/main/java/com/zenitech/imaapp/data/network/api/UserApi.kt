package org.openapitools.client.apis

import org.openapitools.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody
import com.squareup.moshi.Json

import org.openapitools.client.models.UserPage
import org.openapitools.client.models.UserSearchRequest

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
