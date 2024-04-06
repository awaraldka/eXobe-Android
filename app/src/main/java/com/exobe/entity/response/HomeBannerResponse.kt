package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class HomeBannerResponse {
    @SerializedName("result") val result : HomeBannerResult = HomeBannerResult()
    @SerializedName("responseMessage") val responseMessage : String=""
    @SerializedName("responseCode") val responseCode : Int=0
}

class HomeBannerResult {
    @SerializedName("docs") val docs : ArrayList<HomeBannerDoc> = ArrayList()
}

class HomeBannerDoc {
    @SerializedName("bannerImage") val bannerImage : String=""
    @SerializedName("bannerName") val bannerName : String=""
    @SerializedName("description") val description : String=""
    @SerializedName("_id") val _id : String=""
}