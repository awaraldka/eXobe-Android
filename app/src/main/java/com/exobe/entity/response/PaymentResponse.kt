package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class PaymentResponse {
    @SerializedName("result") val result : String = ""
    @SerializedName("responseMessage") val responseMessage : String = ""
    @SerializedName("responseCode") val responseCode : Int= 0
}