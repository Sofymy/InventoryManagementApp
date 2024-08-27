package com.zenitech.imaapp.data.network.api

import retrofit2.http.*
import retrofit2.Call

import com.zenitech.imaapp.data.model.SubscriptionRequest

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
