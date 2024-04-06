package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class DealsonCustomerResponse {
    @SerializedName("result")
    val result: ArrayList<DealsonCustomerResult> = ArrayList()
    @SerializedName("responseMessage")
    val responseMessage: String = ""
    @SerializedName("responseCode")
    val responseCode: Int = 0
}

class DealsonCustomerResult {
    @SerializedName("status")
    val status: String = ""
    @SerializedName("_id")
    val _id: String = ""
    @SerializedName("categoryName")
    val categoryName: String = ""
    @SerializedName("thumbnail")
    val thumbnail: String = ""
    @SerializedName("")
    val categoryImage: String = ""
    @SerializedName("description")
    val description: String = ""
    @SerializedName("createdAt")
    val createdAt: String = ""
    @SerializedName("updatedAt")
    val updatedAt: String = ""
}
