package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class AddAddressResponse {
    @SerializedName("result") val result : AddAddressResult?=null
    @SerializedName("responseMessage") val responseMessage : String?=null
    @SerializedName("responseCode") val responseCode : Int?=null
}

class AddAddressResult {

    @SerializedName("status")
    val status: String?=null
    @SerializedName("_id")
    val _id: String?=null
    @SerializedName("address")
    val address: String?=null
    @SerializedName("zipCode")
    val zipCode: String?=null
    @SerializedName("state")
    val state: String?=null
    @SerializedName("city")
    val city: String?=null
    @SerializedName("country")
    val country: String?=null
    @SerializedName("firstName")
    val firstName: String?=null
    @SerializedName("lastName")
    val lastName: String?=null
    @SerializedName("mobileNumber")
    val mobileNumber: String?=null
    @SerializedName("countryCode")
    val countryCode: String?=null
    @SerializedName("email")
    val email: String?=null
    @SerializedName("userId")
    val userId: String?=null
    @SerializedName("createdAt")
    val createdAt: String?=null
    @SerializedName("updatedAt")
    val updatedAt: String?=null
    @SerializedName("__v")
    val __v: Int?=null
}