package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class SocialSignInResponse (
    @SerializedName("responseCode") val responseCode: Int,
    @SerializedName("responseMessage") val responseMessage: String,
    @SerializedName("result") val result: SocialSignInResult,
)



class  SocialSignInResult{
    @SerializedName("isAlreadyUser") val isAlreadyUser:Boolean = false
    @SerializedName("completeProfile") val completeProfile:Boolean = false
    @SerializedName("email") val email: String = ""
    @SerializedName("userType") val userType: String = ""
    @SerializedName("profilePic") val profilePic: String = ""
    @SerializedName("token") val token: String = ""
    @SerializedName("_id") val _id: String = ""
    @SerializedName("flag") val flag: Int = 0

    @SerializedName("rejectReason") val rejectReason: String = ""
    @SerializedName("userRequestStatus") val userRequestStatus: String = ""
    @SerializedName("primaryAddressId") val primaryAddressId: String = ""
    @SerializedName("campainPrefrences") val campainPrefrences: String = ""


    @SerializedName("isTransportationService") val isTransportationService:Boolean = false
    @SerializedName("isFullfillmentService") val isFullfillmentService:Boolean = false
    @SerializedName("isStandardService") val isStandardService:Boolean = false
}