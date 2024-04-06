package com.exobe.entity.response.customer

import com.google.gson.annotations.SerializedName

class AddToCart (
    @SerializedName("result") val result : AddTocartResult?= AddTocartResult(),
    @SerializedName("responseMessage") val responseMessage : String?="",
    @SerializedName("responseCode") val responseCode : Int?=0,
    )

class AddTocartResult{
    @SerializedName("buyStatus") val buyStatus : String?=""
    @SerializedName("status") val status : String?=""
    @SerializedName("_id") val _id : String?=""
    @SerializedName("productId") val productId : String?=""
    @SerializedName("quantity") val quantity : Int?=0
    @SerializedName("orderType") val orderType : String?= ""
    @SerializedName("userId") val userId : String?=""
    @SerializedName("createdAt") val createdAt : String?=""
    @SerializedName("updatedAt") val updatedAt : String?=""
    @SerializedName("__v") val __v : Int?=0
}