package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class EditProductResponse {

    @SerializedName("result") val result : EditProductResult? =EditProductResult()
    @SerializedName("responseMessage") val responseMessage : String=""
    @SerializedName("responseCode") val responseCode : Int=0
}
class EditProductResult {

    @SerializedName("productImage")
    val productImage: ArrayList<String>? = ArrayList()
    @SerializedName("discount")
    val discount: Int=0
    @SerializedName("productFor")
    val productFor: String=""
    @SerializedName("status")
    val status: String=""
    @SerializedName("_id")
    val _id: String=""
    @SerializedName("brand")
    val brand: String=""
    @SerializedName("categoryId")
    val categoryId: String=""
    @SerializedName("description")
    val description: String=""
    @SerializedName("price")
    val price: Number=0
    @SerializedName("productName")
    val productName: String=""
    @SerializedName("quantity")
    val quantity: Int=0
    @SerializedName("subCategoryId")
    val subCategoryId: String=""
//    @SerializedName("unit")
//    val unit: Int=0
    @SerializedName("userId")
    val userId: String=""
    @SerializedName("createdAt")
    val createdAt: String=""
    @SerializedName("updatedAt")
    val updatedAt: String=""
    @SerializedName("__v")
    val __v: Int=0
}