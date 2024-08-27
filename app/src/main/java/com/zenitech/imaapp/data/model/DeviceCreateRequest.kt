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
 * @param type 
 * @param manufacturer 
 * @param serialNumber 
 * @param assetName 
 * @param supplier 
 * @param invoiceNumber 
 * @param shipmentDate 
 * @param warranty 
 * @param condition 
 * @param status 
 * @param site 
 * @param note 
 * @param isTestDevice 
 * @param lease 
 * @param location 
 */


data class DeviceCreateRequest (

    @Json(name = "type")
    val type: kotlin.String,

    @Json(name = "manufacturer")
    val manufacturer: kotlin.String,

    @Json(name = "serialNumber")
    val serialNumber: kotlin.String,

    @Json(name = "assetName")
    val assetName: kotlin.String,

    @Json(name = "supplier")
    val supplier: kotlin.String,

    @Json(name = "invoiceNumber")
    val invoiceNumber: kotlin.String,

    @Json(name = "shipmentDate")
    val shipmentDate: java.time.LocalDate,

    @Json(name = "warranty")
    val warranty: java.time.LocalDate,

    @Json(name = "condition")
    val condition: DeviceCondition,

    @Json(name = "status")
    val status: DeviceStatus,

    @Json(name = "site")
    val site: kotlin.String,

    @Json(name = "note")
    val note: kotlin.String,

    @Json(name = "isTestDevice")
    val isTestDevice: kotlin.Boolean,

    @Json(name = "lease")
    val lease: Leasing? = null,

    @Json(name = "location")
    val location: kotlin.String? = null

) {


}

