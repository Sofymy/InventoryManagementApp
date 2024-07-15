/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.zenitech.imaapp.data.model

import com.squareup.moshi.Json

/**
 * 
 *
 * @param id 
 * @param inventoryId 
 * @param type 
 * @param manufacturer 
 * @param serialNumber 
 * @param itemNumber 
 * @param supplier 
 * @param invoiceNumber 
 * @param shipmentDate 
 * @param warranty 
 * @param status 
 * @param lease 
 * @param site 
 * @param location 
 */


data class DeviceResponse (

    @Json(name = "id")
    val id: java.util.UUID? = null,

    @Json(name = "inventoryId")
    val inventoryId: kotlin.String? = null,

    @Json(name = "type")
    val type: kotlin.String? = null,

    @Json(name = "manufacturer")
    val manufacturer: kotlin.String? = null,

    @Json(name = "serialNumber")
    val serialNumber: kotlin.String? = null,

    @Json(name = "itemNumber")
    val itemNumber: kotlin.String? = null,

    @Json(name = "supplier")
    val supplier: kotlin.String? = null,

    @Json(name = "invoiceNumber")
    val invoiceNumber: kotlin.String? = null,

    @Json(name = "shipmentDate")
    val shipmentDate: java.time.LocalDate? = null,

    @Json(name = "warranty")
    val warranty: java.time.LocalDate? = null,

    @Json(name = "status")
    val status: DeviceStatus? = null,

    @Json(name = "lease")
    val lease: Leasing? = null,

    @Json(name = "site")
    val site: kotlin.String? = null,

    @Json(name = "location")
    val location: kotlin.String? = null

)

