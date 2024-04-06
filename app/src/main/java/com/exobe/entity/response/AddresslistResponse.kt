package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class AddresslistResponse {

    @SerializedName("result") val result : AddresslistResult?=null
    @SerializedName("responseMessage") val responseMessage : String?=null
    @SerializedName("responseCode") val responseCode : Int?=null
}
class AddresslistResult {

    @SerializedName("docs")
    val docs: List<AddresslistDocs>? = null
    @SerializedName("total")
    val total: Int? = null
    @SerializedName("limit")
    val limit: Int? = null
    @SerializedName("page")
    val page: Int?  = null
    @SerializedName("pages")
    val pages: Int? = null
}
class AddresslistDocs {

    @SerializedName("status")
    val status: String? = null
    @SerializedName("_id")
    val _id: String? = null
    @SerializedName("address")
    val address: String? = null
    @SerializedName("addressLine1")
    val addressLine1: String? = null
    @SerializedName("addressLine2")
    val addressLine2: String? = null
    @SerializedName("zipCode")
    val zipCode: String? = null
    @SerializedName("state")
    val state: String? = null
    @SerializedName("city")
    val city: String? = null
    @SerializedName("country")
    val country: String? = null
    @SerializedName("firstName")
    val firstName: String? = null
    @SerializedName("lastName")
    val lastName: String? = null
    @SerializedName("mobileNumber")
    val mobileNumber: String? = null
    @SerializedName("countryCode")
    val countryCode: String? = null
    @SerializedName("email")
    val email: String? = null
    @SerializedName("transportName")
    val transportName: String? = null
    @SerializedName("gstNumber")
    val gstNumber: String? = null
    @SerializedName("userId")
    val userId: String? = null
    @SerializedName("isPrimary")
    val isPrimary: Boolean? = null
    @SerializedName("createdAt")
    val createdAt: String? = null
    @SerializedName("updatedAt")
    val updatedAt: String? = null
    @SerializedName("__v")
    val __v: Int? = null


    var isSelected = false

}