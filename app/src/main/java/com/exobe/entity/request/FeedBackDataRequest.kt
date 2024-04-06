package com.exobe.entity.request

import com.google.gson.annotations.SerializedName

class FeedBackDataRequest(
    @SerializedName("feedBackData") val feedBackData:ArrayList<FeedBackData> = arrayListOf()
)

class FeedBackData(
        @SerializedName("ratingToUserType") val ratingToUserType:String,
        @SerializedName("orderId") val orderId:String,
        @SerializedName("rating") val rating:Float,
        @SerializedName("review") val review:String,

)