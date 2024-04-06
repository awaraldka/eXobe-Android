package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class GetInvoiceDownloadResponse {
    @SerializedName("responseMessage") val responseMessage : String? = null
    @SerializedName("responseCode") val responseCode : Int? = null
    @SerializedName("result") val result : String = ""
}