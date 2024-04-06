package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class ResendOtp {

    @SerializedName("result") val result : ResendOtpResult?=null
    @SerializedName("responseMessage") val responseMessage : String?=null
    @SerializedName("responseCode") val responseCode : Int?=null
}
class ResendOtpResult {

//    @SerializedName("storeLocation") val storeLocation : ResendOtpStoreLocation?=null
//    @SerializedName("isReset") val isReset : Boolean?=null
//    @SerializedName("otpVerification") val otpVerification : Boolean?=null
//    @SerializedName("userVerification") val userVerification : Boolean?=null
//    @SerializedName("userRequestStatus") val userRequestStatus : String?=null
//    @SerializedName("resetUserPassword") val resetUserPassword : Boolean?=null
//    @SerializedName("status") val status : String?=null
//    @SerializedName("_id") val _id : String?=null
//    @SerializedName("userType") val userType : String?=null
//    @SerializedName("firstName") val firstName : String?=null
//    @SerializedName("lastName") val lastName : String?=null
//    @SerializedName("countryCode") val countryCode : Int?=null
//    @SerializedName("mobileNumber") val mobileNumber : Int?=null
//    @SerializedName("email") val email : String?=null
//    @SerializedName("address") val address : String?=null
//    @SerializedName("password") val password : String?=null
//    @SerializedName("profilePic") val profilePic : String?=null
//    @SerializedName("city") val city : String?=null
//    @SerializedName("state") val state : String?=null
//    @SerializedName("country") val country : String?=null
//    @SerializedName("zipCode") val zipCode : String?=null
    @SerializedName("otp") val otp : Int?=null
//    @SerializedName("otpExpireTime") val otpExpireTime : Int?=null
//    @SerializedName("subCategoryDetails") val subCategoryDetails : List<String>?=null
//    @SerializedName("createdAt") val createdAt : String?=null
//    @SerializedName("updatedAt") val updatedAt : String?=null
//    @SerializedName("__v") val __v : Int?=null
}
class ResendOtpStoreLocation {

    @SerializedName("type")
    val type: String?=null
    @SerializedName("coordinates")
    val coordinates: ArrayList<Double>?=null
}