package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("result")
    val result: loginResult?=null
    @SerializedName("responseMessage")
    val responseMessage: String?=null
    @SerializedName("responseCode")
    var responseCode: Int?=null
}

class loginResult {

    @SerializedName("_id")
    val _id: String?=null
    @SerializedName("email")
    val email: String?=null
    @SerializedName("userType")
    val userType: String?=null
    @SerializedName("firstName")
    val firstName: String?=null
    @SerializedName("lastName")
    val lastName: String?=null
    @SerializedName("userRequestStatus")
    val userRequestStatus: String?=null
    @SerializedName("token")
    val token: String?=null
    @SerializedName("rejectReason")
    val rejectReason: String?=null
    @SerializedName("primaryAddressId")
    val primaryAddressId: String?=""
    @SerializedName("otpVerification")
    val otpVerification : Boolean?=false
    @SerializedName("flag")
    val flag : Int?=0


    @SerializedName("isTransportationService") val isTransportationService:Boolean = false
    @SerializedName("isFullfillmentService") val isFullfillmentService:Boolean = false
    @SerializedName("isStandardService") val isStandardService:Boolean = false
    @SerializedName("campainPrefrences") val campainPrefrences:String?=""

}