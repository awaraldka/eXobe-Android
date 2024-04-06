package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class ProductEnquiryResponse {
    @SerializedName("result") val result : ProductEnquiryResult?= null
    @SerializedName("responseMessage") val responseMessage : String?= null
    @SerializedName("responseCode") val responseCode : Int?= null
}

class ProductEnquiryResult {
    @SerializedName("status") val status : String?= null
    @SerializedName("_id") val _id : String?= null
    @SerializedName("email") val email : String?= null
    @SerializedName("userId") val userId : String?= null
    @SerializedName("createdAt") val createdAt : String?= null
    @SerializedName("updatedAt") val updatedAt : String?= null
    @SerializedName("__v") val __v : Int?= null
}