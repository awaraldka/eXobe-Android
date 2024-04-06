package com.exobe.entity.response.product

import com.exobe.entity.response.PriceSizeDetails
import com.google.gson.annotations.SerializedName

class GuestProductResponse {
    @SerializedName("result") val result : GuestProductResult =GuestProductResult()
    @SerializedName("responseMessage") val responseMessage : String=""
    @SerializedName("responseCode") val responseCode : Int=0
}

class GuestProductResult {
    @SerializedName("docs") val docs : List<GuestProductDocs> = listOf()
//    @SerializedName("remainingItems") val remainingItems : Int=0
    @SerializedName("limit") val limit : Int=0
    @SerializedName("page") val page : Int=0
    @SerializedName("count") val count : Int=0
    @SerializedName("totalPages") val totalPages : Int=0
}

class GuestProductDocs {
    @SerializedName("productImage") val productImage : ArrayList<String> = ArrayList<String>()
//    @SerializedName("discount") val discount : Int=0
//    @SerializedName("productFor") val productFor : String=""
//    @SerializedName("status") val status : String=""
    @SerializedName("_id") val _id : String=""
    @SerializedName("productName") val productName : String=""
    @SerializedName("price") val price : Number=0
//    @SerializedName("unit") val unit : Int=0
//    @SerializedName("brand") val brand : String=""
//    @SerializedName("description") val description : String=""
    @SerializedName("thumbnail") val thumbnail : String=""
//    @SerializedName("quantity") val quantity : Int=0
//    @SerializedName("productReferenceId") val productReferenceId : String=""
//    @SerializedName("createdAt") val createdAt : String=""
//    @SerializedName("updatedAt") val updatedAt : String=""
//    @SerializedName("__v") val __v : Int=0
    @SerializedName("isLike") val isLike : Boolean?=null
    @SerializedName("isDealActive") val isDealActive : Boolean?=null
    @SerializedName("dealId") val dealId : String=""
    @SerializedName("dealPrice") val dealPrice : Number=0
    @SerializedName("dealDiscount") val dealDiscount : Number=0
    @SerializedName("priceSizeDetails")
    val priceSizeDetails: ArrayList<PriceSizeDetails> = ArrayList<PriceSizeDetails>()
}

