package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class FeedbackResponse(
    @SerializedName("result") var result: ArrayList<FeedbackResult> = arrayListOf(),
    @SerializedName("responseMessage") var responseMessage: String = "",
    @SerializedName("responseCode") var responseCode: Int? = null
)


class FeedbackResult(
    @SerializedName("rating") val rating:Number = 0,
    @SerializedName("ratingToUserType") val ratingToUserType:String = "",
    @SerializedName("review") val review:String = "",
)