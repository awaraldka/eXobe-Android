package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class ViewAddressResponse(

    @SerializedName("result") val result: ViewAddressResult,
    @SerializedName("responseMessage") val responseMessage: String,
    @SerializedName("responseCode") val responseCode: Int
)


class ViewAddressResult(

    @SerializedName("status") val status: String,
    @SerializedName("_id") val _id: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("mobileNumber") val mobileNumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("countryCode") val countryCode: String,
    @SerializedName("address") val address: String,
    @SerializedName("addressLine1") val addressLine1: String,
    @SerializedName("addressLine2") val addressLine2: String,
    @SerializedName("zipCode") val zipCode: String,
    @SerializedName("city") val city: String,
    @SerializedName("country") val country: String,
    @SerializedName("countryIsoCode") val countryIsoCode: String,
    @SerializedName("stateIsoCode") val stateIsoCode: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("__v") val __v: Int,
    @SerializedName("state")
    val state: String? = null,
    @SerializedName("location")
    var location: ViewAddressLocation = ViewAddressLocation()
)

class ViewAddressLocation {
    @SerializedName("type")
    var type: String = ""
    @SerializedName("coordinates")
    var coordinates: ArrayList<Double> = ArrayList()
}