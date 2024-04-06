package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class AddProductByAdminResponse {


    @SerializedName("result")
    val result: AddProductByAdminResult?=null
    @SerializedName("responseMessage")
    val responseMessage: String?=null
    @SerializedName("responseCode")
    val responseCode: Int?=null

}

class AddProductByAdminResult {

    @SerializedName("docs")
    val docs: List<AddProductByAdminDocs>?=null

    @SerializedName("total")
    val total: Int?=null

    @SerializedName("limit")
    val limit: Int?=null

    @SerializedName("page")
    val page: Int?=null

    @SerializedName("pages")
    val pages: Int?=null
}

class AddProductByAdminDocs {

    @SerializedName("productImage")
    val productImage: List<String>?=null

    @SerializedName("status")
    val status: String?=null

    @SerializedName("_id")
    val _id: String?=null

    @SerializedName("productName")
    val productName: String?=null

    @SerializedName("description")
    val description: String?=null

    @SerializedName("categoryId")
    val categoryId: CategoryId?=null

    @SerializedName("subCategoryId")
    val subCategoryId: SubCategoryId?=null

    @SerializedName("createdAt")
    val createdAt: String?=null

    @SerializedName("updatedAt")
    val updatedAt: String?=null

    @SerializedName("__v")
    val __v: Int?=null
}