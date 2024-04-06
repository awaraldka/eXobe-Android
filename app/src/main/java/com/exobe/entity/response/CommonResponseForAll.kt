package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class CommonResponseForAll {
    @SerializedName("responseMessage") val responseMessage : String? = null
    @SerializedName("responseCode") val responseCode : Int? = null
}
