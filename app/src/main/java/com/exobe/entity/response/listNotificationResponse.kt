package com.exobe.entity.response

import com.google.gson.annotations.SerializedName


class listNotificationResponse {

    @SerializedName("result")
    val result:listNotificationResult? = null

    @SerializedName("responseMessage")
    val responseMessage: String? = null

    @SerializedName("responseCode")
    val responseCode: Int? = null
}

class listNotificationResult {
    @SerializedName("docs")
    val docs: ArrayList<listNotificationDocs>? = null
    @SerializedName("total")
    val total: Int = 0
    @SerializedName("limit")
    val limit: Int = 0
    @SerializedName("page")
    val page: Int = 0
    @SerializedName("pages")
    val pages: Int = 0
    @SerializedName("notificationCount")
    val notificationCount: Int = 0

}

class listNotificationDocs {

    //    @SerializedName("isRead") val isRead : Boolean=false
    @SerializedName("status")
    val status: String = ""

    @SerializedName("_id")
    val _id: String = ""

    @SerializedName("subject")
    val subject: String = ""

    @SerializedName("body")
    val body: String = ""

    @SerializedName("notifyType")
    val notifyType: String = ""

    @SerializedName("createdAt")
    val createdAt: String = ""

    @SerializedName("updatedAt")
    val updatedAt: String = ""

    @SerializedName("__v")
    val __v: Int = 0
}