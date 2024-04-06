package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class EditDealsResponse {
//    @SerializedName("result") val result : Result? = null
    @SerializedName("responseMessage") val responseMessage : String?= ""
    @SerializedName("responseCode") val responseCode : Int? = 0
}
