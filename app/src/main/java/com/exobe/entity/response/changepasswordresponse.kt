package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class changepasswordresponse (

    @SerializedName("result") val result : changepasswordResult,
    @SerializedName("responseMessage") val responseMessage : String,
    @SerializedName("responseCode") val responseCode : Int
)
data class changepasswordResult (

//    @SerializedName("storeLocation") val storeLocation : changepasswordStoreLocation,
    @SerializedName("isReset") val isReset : Boolean,
    @SerializedName("otpVerification") val otpVerification : Boolean,
    @SerializedName("userVerification") val userVerification : Boolean,
    @SerializedName("profilePic") val profilePic : String,
    @SerializedName("userType") val userType : String,
    @SerializedName("userRequestStatus") val userRequestStatus : String,
    @SerializedName("resetUserPassword") val resetUserPassword : Boolean,
    @SerializedName("additionalDocName") val additionalDocName : String,
    @SerializedName("status") val status : String,
    @SerializedName("_id") val _id : String,
    @SerializedName("firstName") val firstName : String,
    @SerializedName("lastName") val lastName : String,
    @SerializedName("countryCode") val countryCode : String,
    @SerializedName("mobileNumber") val mobileNumber : String,
    @SerializedName("email") val email : String,
    @SerializedName("address") val address : String,
    @SerializedName("storeAddress") val storeAddress : String,
    @SerializedName("storeName") val storeName : String,
    @SerializedName("password") val password : String,
    @SerializedName("otp") val otp : Int,
    @SerializedName("otpExpireTime") val otpExpireTime : Long,
    @SerializedName("subCategoryDetails") val subCategoryDetails : List<String>,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int
)
//data class changepasswordStoreLocation (

//    @SerializedName("type") val type : String,
//    @SerializedName("coordinates") val coordinates : List<Int>
//)