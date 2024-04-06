package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class NotifiactionCountResponse {

    @SerializedName("result") val result : NotifiactionCountResult?=null
    @SerializedName("responseMessage") val responseMessage : String?=null
    @SerializedName("responseCode") val responseCode : Int?=null
}
class NotifiactionCountResult (

    @SerializedName("notificationCount") val notificationCount : Int=0
)