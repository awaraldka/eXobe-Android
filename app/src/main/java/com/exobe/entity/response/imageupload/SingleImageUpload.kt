package com.exobe.entity.response.imageupload

import com.google.gson.annotations.SerializedName

class SingleImageUpload {
    @SerializedName("result") val result : SingleImageResult? = null
    @SerializedName("responseMessage") val responseMessage : String? = null
    @SerializedName("responseCode") val responseCode : Int? = null
}

class SingleImageResult {
    @SerializedName("mediaUrl") val mediaUrl : String? = null
    @SerializedName("mediaType") val mediaType : String? = null
    @SerializedName("thumbnail") val thumbnail : String? = null

}