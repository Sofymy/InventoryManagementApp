package org.openapitools.client.apis

import org.openapitools.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody
import com.squareup.moshi.Json

import org.openapitools.client.models.SubscriptionRequest

interface NotificationApi {
    /**
     * 
     * Subscribe the given device to push notifications
     * Responses:
     *  - 200: OK
     *  - 409: Conflict
     *
     * @param subscriptionRequest 
     * @return [Call]<[Unit]>
     */
    @POST("notification/subscription")
    fun subscribeDevice(@Body subscriptionRequest: SubscriptionRequest): Call<Unit>

    /**
     * 
     * Unsubscribe the given device from push notifications
     * Responses:
     *  - 200: OK
     *
     * @param subscriptionRequest 
     * @return [Call]<[Unit]>
     */
    @DELETE("notification/subscription")
    fun unsubscribeDevice(@Body subscriptionRequest: SubscriptionRequest): Call<Unit>

}
