package org.openapitools.client.apis

import org.openapitools.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody
import com.squareup.moshi.Json

import org.openapitools.client.models.Tag
import org.openapitools.client.models.TagPage

interface TagApi {
    /**
     * 
     * Create a new tag
     * Responses:
     *  - 201: Created
     *  - 409: Conflict
     *
     * @param tag 
     * @return [Call]<[Unit]>
     */
    @POST("tags")
    fun createTag(@Body tag: Tag): Call<Unit>

    /**
     * 
     * Delete tag
     * Responses:
     *  - 204: No Content
     *
     * @param tagName 
     * @return [Call]<[Unit]>
     */
    @DELETE("tags/{tagName}")
    fun deleteTag(@Path("tagName") tagName: kotlin.String): Call<Unit>

    /**
     * 
     * Edit tag
     * Responses:
     *  - 204: No Content
     *  - 404: Not Found
     *  - 409: Conflict
     *
     * @param tagName 
     * @param tag 
     * @return [Call]<[Unit]>
     */
    @PUT("tags/{tagName}")
    fun editTag(@Path("tagName") tagName: kotlin.String, @Body tag: Tag): Call<Unit>

    /**
     * 
     * Search tags
     * Responses:
     *  - 200: OK
     *
     * @param page 
     * @param size 
     * @param sort  (optional)
     * @param tag  (optional)
     * @return [Call]<[TagPage]>
     */
    @POST("tags/search")
    fun searchTags(@Query("page") page: kotlin.Int, @Query("size") size: kotlin.Int, @Query("sort") sort: kotlin.String? = null, @Body tag: Tag? = null): Call<TagPage>

}
