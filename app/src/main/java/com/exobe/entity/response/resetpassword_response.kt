package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class resetpassword_response {
    @SerializedName("result") val result : resetpasswordResult?=null
    @SerializedName("responseMessage") val responseMessage : String?=null
    @SerializedName("responseCode") val responseCode : Int?=null
}
class resetpasswordResult {

    @SerializedName("storeLocation") val storeLocation : resetpasswordStoreLocation?=null
    @SerializedName("isReset") val isReset : Boolean?=null
    @SerializedName("otpVerification") val otpVerification : Boolean?=null
    @SerializedName("userVerification") val userVerification : Boolean?=null
    @SerializedName("userRequestStatus") val userRequestStatus : String?=null
    @SerializedName("resetUserPassword") val resetUserPassword : Boolean?=null
    @SerializedName("status") val status : String?=null
    @SerializedName("_id") val _id : String?=null
    @SerializedName("countryCode") val countryCode : String?=null
    @SerializedName("email") val email : String?=null
    @SerializedName("firstName") val firstName : String?=null
    @SerializedName("lastName") val lastName : String?=null
    @SerializedName("mobileNumber") val mobileNumber : String?=null
    @SerializedName("password") val password : String?=null
    @SerializedName("userType") val userType : String?=null
    @SerializedName("otp") val otp : String?=null
    @SerializedName("otpExpireTime") val otpExpireTime : String?=null
    @SerializedName("subCategoryDetails") val subCategoryDetails : List<String>?=null
    @SerializedName("createdAt") val createdAt : String?=null
    @SerializedName("updatedAt") val updatedAt : String?=null
}
class resetpasswordStoreLocation {

    @SerializedName("type")
    val type: String?=null
    @SerializedName("coordinates")
    val coordinates: List<Double>?=null
}