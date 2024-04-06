package com.exobe.entity.response.addproduct

import com.google.gson.annotations.SerializedName

class MultipartImageResponse {
    @SerializedName("result") val result : List<MultipartImageResult>? = null
    @SerializedName("responseMessage") val responseMessage : String? = null
    @SerializedName("responseCode") val responseCode : Int? = null
}

class MultipartImageResult {
    @SerializedName("mediaUrl") val mediaUrl : String? = null
    @SerializedName("mediaType") val mediaType : String? = null
    @SerializedName("thumbnail") val thumbnail : String? = null
}