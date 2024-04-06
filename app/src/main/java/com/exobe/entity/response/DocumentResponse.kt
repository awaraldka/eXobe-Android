package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class DocumentResponse (
    @SerializedName("result") val result : DocumentResult,
    @SerializedName("responseMessage") val responseMessage : String,
    @SerializedName("responseCode") val responseCode : Int
        )
class DocumentResult(
    @SerializedName("mediaUrl") val mediaUrl : String,
    @SerializedName("mediaType") val mediaType : String,
    @SerializedName("thumbnail") val thumbnail : String? = null

)