package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class ClearAllNotificationResponse {
    @SerializedName("result") val result : ClearAllNotificationResult?= null
    @SerializedName("responseMessage") val responseMessage : String?= null
    @SerializedName("responseCode") val responseCode : Int?= null
}


class ClearAllNotificationResult {

    @SerializedName("n")
    val n: Int?= null
    @SerializedName("nModified")
    val nModified: Int?= null
    @SerializedName("ok")
    val ok: Int?= null
}