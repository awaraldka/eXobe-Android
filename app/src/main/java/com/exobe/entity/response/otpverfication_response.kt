package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class otpverfication_response {
    @SerializedName("result") val result : otpverficationResult?=null
    @SerializedName("responseMessage") val responseMessage : String?=null
    @SerializedName("responseCode") val responseCode : Int?=null
}
class otpverficationResult {

    @SerializedName("_id")
    val _id: String? = null
    @SerializedName("firstName")
    val firstName: String? = null
    @SerializedName("email")
    val email: String? = null
    @SerializedName("countryCode")
    val countryCode: String? = null

}