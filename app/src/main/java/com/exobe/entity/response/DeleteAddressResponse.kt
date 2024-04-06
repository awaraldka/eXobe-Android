package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class DeleteAddressResponse(

    @SerializedName("result") val result: Result,
    @SerializedName("responseMessage") val responseMessage: String,
    @SerializedName("responseCode") val responseCode: Int
)

class DeleteAddressResult(

    @SerializedName("status") val status: String,
    @SerializedName("_id") val _id: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("mobileNumber") val mobileNumber: Int,
    @SerializedName("email") val email: String,
    @SerializedName("countryCode") val countryCode: String,
    @SerializedName("address") val address: String,
    @SerializedName("zipCode") val zipCode: Int,
    @SerializedName("city") val city: String,
    @SerializedName("country") val country: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("__v") val __v: Int
)