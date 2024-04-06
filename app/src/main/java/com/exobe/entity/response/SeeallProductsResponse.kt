package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class SeeallProductsResponse {

    @SerializedName("result") val result : SeeallProductsResult?=null
    @SerializedName("responseMessage") val responseMessage : String?=null
    @SerializedName("responseCode") val responseCode : Int?=null

}

class SeeallProductsResult {

    @SerializedName("docs")
    val docs: List<SeeallProductsDocs>?=null
    @SerializedName("total")
    val total: Int?=null
    @SerializedName("limit")
    val limit: Int?=null
    @SerializedName("page")
    val page: Int?=null
    @SerializedName("pages")
    val pages: Int?=null
}
class SeeallProductsDocs (

    @SerializedName("_id") val _id : String,
    @SerializedName("productImage") val productImage : List<String>,
    @SerializedName("productFor") val productFor : String,
    @SerializedName("status") val status : String,
    @SerializedName("productName") val productName : String,
    @SerializedName("price") val price : Number=0,
//    @SerializedName("unit") val unit : Int,
    @SerializedName("brand") val brand : String,
    @SerializedName("description") val description : String,
    @SerializedName("quantity") val quantity : Int,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int,

)