package org.openapitools.client.apis

import retrofit2.http.*
import retrofit2.Call

import org.openapitools.client.models.CreateTestDeviceRequest
import com.zenitech.imaapp.data.model.DeviceAssignRequest
import org.openapitools.client.models.DevicePage
import org.openapitools.client.models.RequestStatus
import org.openapitools.client.models.TestDeviceRequestPage

interface RequestApi {
    /**
     * 
     * Approve test device request
     * Responses:
     *  - 204: No Content
     *  - 404: Not Found
     *
     * @param requestId 
     * @param deviceAssignRequest 
     * @return [Call]<[Unit]>
     */
    @POST("admin/requests/{request-id}/approve")
    fun approveTestDevice(@Path("request-id") requestId: java.util.UUID, @Body deviceAssignRequest: DeviceAssignRequest): Call<Unit>

    /**
     * 
     * Returns the Devices that fulfill the requirements for the given Test Device Request
     * Responses:
     *  - 200: OK
     *
     * @param requestId 
     * @param page 
     * @param size 
     * @param sort  (optional)
     * @return [Call]<[DevicePage]>
     */
    @GET("admin/requests/{request-id}/available-devices")
    fun getAvailableDevices(@Path("request-id") requestId: java.util.UUID, @Query("page") page: Int, @Query("size") size: Int, @Query("sort") sort: kotlin.String? = null): Call<DevicePage>

    /**
     * 
     * Returns the test device requests with the provided status
     * Responses:
     *  - 200: OK
     *
     * @param requestStatus 
     * @param page 
     * @param size 
     * @param sort  (optional)
     * @return [Call]<[TestDeviceRequestPage]>
     */
    @GET("admin/requests/{request-status}")
    fun getRequests(@Path("request-status") requestStatus: RequestStatus, @Query("page") page: Int, @Query("size") size: Int, @Query("sort") sort: kotlin.String? = null): Call<TestDeviceRequestPage>

    /**
     * 
     * Lend the Test Device to the user associated with the request, it begins the Lease
     * Responses:
     *  - 204: No Content
     *  - 404: Not Found
     *
     * @param requestId 
     * @return [Call]<[Unit]>
     */
    @POST("admin/requests/{request-id}/lend")
    fun lendTestDevice(@Path("request-id") requestId: java.util.UUID): Call<Unit>

    /**
     * 
     * Reject test device request
     * Responses:
     *  - 204: No Content
     *  - 404: Not Found
     *
     * @param requestId 
     * @return [Call]<[Unit]>
     */
    @POST("admin/requests/{request-id}/reject")
    fun rejectTestDevice(@Path("request-id") requestId: java.util.UUID): Call<Unit>

    /**
     * 
     * Request test device with given parameters
     * Responses:
     *  - 200: OK
     *
     * @param createTestDeviceRequest 
     * @return [Call]<[Unit]>
     */
    @POST("requests")
    fun requestTestDevice(@Body createTestDeviceRequest: CreateTestDeviceRequest): Call<Unit>

    /**
     * 
     * The user has returned the Test Device, ends the Lease associated with the Device
     * Responses:
     *  - 204: No Content
     *  - 404: Not Found
     *
     * @param leaseId 
     * @return [Call]<[Unit]>
     */
    @POST("admin/leases/{lease-id}/return")
    fun returnTestDevice(@Path("lease-id") leaseId: java.util.UUID): Call<Unit>

}
