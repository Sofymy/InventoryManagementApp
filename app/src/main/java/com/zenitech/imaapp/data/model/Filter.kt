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
 * @param name 
 * @param tableSort The filtered table's sort Must be provided in spring format
 * @param inventoryId 
 * @param assetName 
 * @param manufacturer 
 * @param type 
 * @param serialNumber 
 * @param supplier 
 * @param shipmentDateBegin 
 * @param shipmentDateEnd 
 * @param status 
 * @param condition 
 * @param site 
 * @param location 
 * @param leaseStartDate 
 * @param leaseEndDate 
 * @param userName 
 * @param invoiceNumber 
 * @param warrantyBegin 
 * @param warrantyEnd 
 * @param tags 
 */


data class Filter (

    @Json(name = "name")
    val name: kotlin.String,

    /* The filtered table's sort Must be provided in spring format */
    @Json(name = "tableSort")
    val tableSort: kotlin.String? = null,

    @Json(name = "inventoryId")
    val inventoryId: kotlin.collections.List<kotlin.String>? = null,

    @Json(name = "assetName")
    val assetName: kotlin.collections.List<kotlin.String>? = null,

    @Json(name = "manufacturer")
    val manufacturer: kotlin.collections.List<kotlin.String>? = null,

    @Json(name = "type")
    val type: kotlin.collections.List<kotlin.String>? = null,

    @Json(name = "serialNumber")
    val serialNumber: kotlin.collections.List<kotlin.String>? = null,

    @Json(name = "supplier")
    val supplier: kotlin.collections.List<kotlin.String>? = null,

    @Json(name = "shipmentDateBegin")
    val shipmentDateBegin: java.time.LocalDate? = null,

    @Json(name = "shipmentDateEnd")
    val shipmentDateEnd: java.time.LocalDate? = null,

    @Json(name = "status")
    val status: kotlin.collections.List<DeviceStatus>? = null,

    @Json(name = "condition")
    val condition: kotlin.collections.List<DeviceCondition>? = null,

    @Json(name = "site")
    val site: kotlin.collections.List<kotlin.String>? = null,

    @Json(name = "location")
    val location: kotlin.collections.List<kotlin.String>? = null,

    @Json(name = "leaseStartDate")
    val leaseStartDate: java.time.LocalDate? = null,

    @Json(name = "leaseEndDate")
    val leaseEndDate: java.time.LocalDate? = null,

    @Json(name = "userName")
    val userName: kotlin.collections.List<kotlin.String>? = null,

    @Json(name = "invoiceNumber")
    val invoiceNumber: kotlin.collections.List<kotlin.String>? = null,

    @Json(name = "warrantyBegin")
    val warrantyBegin: java.time.LocalDate? = null,

    @Json(name = "warrantyEnd")
    val warrantyEnd: java.time.LocalDate? = null,

    @Json(name = "tags")
    val tags: kotlin.collections.List<Tag>? = null

) {


}

