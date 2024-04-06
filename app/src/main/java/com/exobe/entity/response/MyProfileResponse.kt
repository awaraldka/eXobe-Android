package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class MyProfileResponse (
    @SerializedName("result") val result: MyProfileResult = MyProfileResult(),
    @SerializedName("responseMessage") val responseMessage: String = "",
    @SerializedName("responseCode") val responseCode: Int = 0
)
class MyProfileResult(
    @SerializedName("isConfig") val isConfig:Boolean = false,
    @SerializedName("campainPrefrences") var campainPrefrences : String = "",
    @SerializedName("storeLocation") val storeLocation: MyProfileStoreLocation? = null,
    @SerializedName("address") val address: String = "",
    @SerializedName("profilePic") val profilePic: String? = null,

    @SerializedName("countryCode")
    val countryCode: String? = "",
    @SerializedName("zipCode") val zipCode: String? = null,
    @SerializedName("addressLine1") val addressLine1: String? = "",
    @SerializedName("addressLine2") val addressLine2: String? = "",
    @SerializedName("city") val city: String? = null,
    @SerializedName("countryIsoCode") val countryIsoCode: String? = null,
    @SerializedName("stateIsoCode") val stateIsoCode: String? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("state") val state: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("firstName") val firstName: String? = null,
    @SerializedName("lastName") val lastName: String? = null,
    @SerializedName("mobileNumber") val mobileNumber: String? = null
)


class MyProfileStoreLocation (
    @SerializedName("type") val type: String?= null,
    @SerializedName("coordinates") val coordinates: ArrayList<Double>? = null
)
