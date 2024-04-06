package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class PayFastResponse {
    @SerializedName("responseMessage") val responseMessage : String? = null
    @SerializedName("responseCode") val responseCode : Int? = null
    @SerializedName("result") val payFastResult : PayFastResult = PayFastResult()
}

class PayFastResult {
    @SerializedName("file") val file:String = ""
}