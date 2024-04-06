package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class DocumentListResponse{
    @SerializedName("result") val result: ArrayList<DocumentListResult> = arrayListOf()
    @SerializedName("responseMessage") val responseMessage: String = ""
    @SerializedName("responseCode") val responseCode: Int = 0
}

class DocumentListResult{
    @SerializedName("name") val name: String = ""
    @SerializedName("key") val key: String = ""

}