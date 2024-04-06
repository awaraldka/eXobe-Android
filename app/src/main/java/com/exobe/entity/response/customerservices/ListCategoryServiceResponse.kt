package com.exobe.entity.response.customerservices

import com.google.gson.annotations.SerializedName

class ListCategoryServiceResponse {

    @SerializedName("result")
    val result: ListCategoryServiceResult? = null
    @SerializedName("responseMessage")
    val responseMessage: String? = null
    @SerializedName("responseCode")
    val responseCode: Int? = null
}


class ListCategoryServiceResult {

    @SerializedName("docs")
    val docs: List<ListCategoryServiceDocs>? = null
    @SerializedName("total") val total : Int=0
    @SerializedName("limit") val limit : Int=0
    @SerializedName("page") val page : Int=0
    @SerializedName("pages") val pages : Int=0
}

class ListCategoryServiceDocs {

    @SerializedName("status")
    val status: String? = null


    @SerializedName("_id")
    val _id: String? = null

    @SerializedName("categoryName")
    val categoryName: String? = null

    @SerializedName("categoryImage")
    val categoryImage: String? = null

    @SerializedName("thumbnail")
    val thumbnail: String? = null

    @SerializedName("description")
    val description: String? = null

    @SerializedName("createdAt")
    val createdAt: String? = null

    @SerializedName("updatedAt")
    val updatedAt: String? = null

    @SerializedName("__v")
    val __v: Int? = null
}