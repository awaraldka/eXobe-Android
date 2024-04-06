package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class SubCategoryResponse {

    @SerializedName("result") val result : SubCategoryResult?=null
    @SerializedName("responseMessage") val responseMessage : String?=null
    @SerializedName("responseCode") val responseCode : Int?=null
}
class SubCategoryResult {

    @SerializedName("categoryDetails")
    val categoryDetails: CategoryDetails?=null
    @SerializedName("subCategory")
    val subCategory: ArrayList<SubCategory>?=null
}
class SubCategory {

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
    @SerializedName("categoryImage")
    val categoryImage: String?=null

}