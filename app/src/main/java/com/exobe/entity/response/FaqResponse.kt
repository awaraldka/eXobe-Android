package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

data class FaqResponse(

    @SerializedName("result") var result: ArrayList<FaqResult>? = null,
    @SerializedName("responseMessage") var responseMessage: String? = null,
    @SerializedName("responseCode") var responseCode: Int? = null


)

class FaqResult {
    @SerializedName("status")
    var status: String? = null
    @SerializedName("_id")
    var Id: String? = null
    @SerializedName("question")
    var question: String? = null
    @SerializedName("answer")
    var answer: String? = null
    @SerializedName("createdAt")
    var createdAt: String? = null
    @SerializedName("updatedAt")
    var updatedAt: String? = null
    @SerializedName("__v")
    var _v: Int? = null
}