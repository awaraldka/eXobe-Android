package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class ParticularNotificationListResponse {

    @SerializedName("result") val result : ParticularNotificationListResult?=ParticularNotificationListResult()
    @SerializedName("responseMessage") val responseMessage : String=""
    @SerializedName("responseCode") val responseCode : Int=0

}
class ParticularNotificationListResult {
    @SerializedName("isRead")
    val isRead: Boolean=false
    @SerializedName("status")
    val status: String=""
    @SerializedName("_id")
    val _id: String=""
    @SerializedName("subject")
    val subject: String=""
    @SerializedName("body")
    val body: String=""
    @SerializedName("notifyType")
    val notifyType: String=""
    @SerializedName("dealId")
    val dealId: DealId? = DealId()
    @SerializedName("createdAt")
    val createdAt: String=""
    @SerializedName("updatedAt")
    val updatedAt: String=""
    @SerializedName("__v")
    val __v: Int=0
}
class DealId {
    @SerializedName("productId")
    val productId: ArrayList<String>?=ArrayList()
    @SerializedName("_id")
    val _id: String=""
}
