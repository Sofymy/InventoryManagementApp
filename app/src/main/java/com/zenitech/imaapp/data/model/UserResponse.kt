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
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param id 
 * @param email 
 * @param username 
 */


data class UserResponse (

    @Json(name = "id")
    val id: java.util.UUID,

    @Json(name = "email")
    val email: kotlin.String,

    @Json(name = "username")
    val username: kotlin.String

) {


}

