package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class AddToWishlistResponse {
    @SerializedName("result") val result : AddToWishlistResult?=null
    @SerializedName("responseMessage") val responseMessage : String?=null
    @SerializedName("responseCode") val responseCode : Int?=null
}
class AddToWishlistResult {

    @SerializedName("isLike")
    val isLike: Boolean? = null
    @SerializedName("status")
    val status: String? = null
    @SerializedName("_id")
    val _id: String? = null
    @SerializedName("productId")
    val productId: String? = null
    @SerializedName("userId")
    val userId: String? = null
    @SerializedName("createdAt")
    val createdAt: String? = null
    @SerializedName("updatedAt")
    val updatedAt: String? = null
    @SerializedName("__v")
    val __v: Int? = null
}