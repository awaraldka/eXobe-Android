package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class ViewAddProductByAdminResponse {
    @SerializedName("result") val result : ViewAddProductByAdminResult?=null
    @SerializedName("responseMessage") val responseMessage : String?=null
    @SerializedName("responseCode") val responseCode : Int?=null
}
class ViewAddProductByAdminResult {

    @SerializedName("productImage")
    val productImage: ArrayList<String>?=null
    @SerializedName("status")
    val status: String?=null
    @SerializedName("_id")
    val _id: String?=null
    @SerializedName("productName")
    val productName: String?=null
    @SerializedName("description")
    val description: String?=null
    @SerializedName("thumbnail")
    val thumbnail: String?=null

    @SerializedName("categoryId")
    val categoryId: ViewAddProductByAdminCategoryId?=null
    @SerializedName("subCategoryId")
    val subCategoryId : SubCategoryId?=null
    @SerializedName("createdAt")
    val createdAt: String?=null
    @SerializedName("updatedAt")
    val updatedAt: String?=null
    @SerializedName("__v")
    val __v: Int?=null
}

class ViewAddProductByAdminCategoryId {

    @SerializedName("status")
    val status: String?=null
    @SerializedName("_id")
    val _id: String?=null
    @SerializedName("categoryName")
    val categoryName: String?=null
    @SerializedName("categoryImage")
    val categoryImage: String?=null
    @SerializedName("description")
    val description: String?=null
    @SerializedName("createdAt")
    val createdAt: String?=null
    @SerializedName("updatedAt")
    val updatedAt: String?=null
    @SerializedName("__v")
    val __v: Int?=null
}
class ViewAddProductByAdminSubCategoryId {

    @SerializedName("status")
    val status: String?=null
    @SerializedName("_id")
    val _id: String?=null
    @SerializedName("categoryId")
    val categoryId: String?=null
    @SerializedName("subCategoryName")
    val subCategoryName: String?=null
    @SerializedName("__v")
    val __v: Int?=null
    @SerializedName("createdAt")
    val createdAt: String?=null
    @SerializedName("updatedAt")
    val updatedAt: String?=null
}