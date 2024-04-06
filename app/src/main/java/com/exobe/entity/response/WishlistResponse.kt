package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class WishlistResponse {

    @SerializedName("result") val result : WishlistResult?=null
    @SerializedName("responseMessage") val responseMessage : String?=null
    @SerializedName("responseCode") val responseCode : Int?=null
}


class WishlistResult {

    @SerializedName("docs") val doc : ArrayList<WishListDocs>?=null
    @SerializedName("limit") val limit : Int=0
    @SerializedName("page") val page : Int =0
    @SerializedName("pages") val pages : Int =0
    @SerializedName("total") val total : Int =0

}

class WishListDocs {
    @SerializedName("isLike")
    val isLike: Boolean? = null
    @SerializedName("status")
    val status: String? = null
    @SerializedName("_id")
    val _id: String? = null
    @SerializedName("productId")
    val productId: WishlistProductId? = null
    //    @SerializedName("userId")
//    val userId: UserId? = null
    @SerializedName("isDealActive") val isDealActive : Boolean=false
    @SerializedName("dealId") val dealId : String=""
    @SerializedName("dealPrice") val dealPrice : Number=0
    @SerializedName("dealDiscount") val dealDiscount : Number=0

    @SerializedName("createdAt")
    val createdAt: String? = null
    @SerializedName("updatedAt")
    val updatedAt: String? = null
    @SerializedName("__v")
    val __v: Int? = null
}

class WishlistProductId {

    @SerializedName("productImage")
    val productImage: ArrayList<String>? = ArrayList<String>()
    @SerializedName("thumbnail") val thumbnail : String=""

    @SerializedName("priceSizeDetails")
    val priceSizeDetails: ArrayList<PriceSizeDetails> = ArrayList<PriceSizeDetails>()
    @SerializedName("productFor")
    val productFor: String? = null
    @SerializedName("status")
    val status: String? = null
    @SerializedName("_id")
    val _id: String? = null
    @SerializedName("productName")
    val productName: String? = null
    @SerializedName("price")
    val price: Number = 0
//    @SerializedName("unit")
//    val unit: String? = null
    @SerializedName("brand")
    val brand: String? = null
    @SerializedName("description")
    val description: String? = null
    @SerializedName("quantity")
    val quantity: String? = null
    @SerializedName("productReferenceId")
    val productReferenceId: String? = null
    @SerializedName("userId")
    val userId: String? = null
    @SerializedName("createdAt")
    val createdAt: String? = null
    @SerializedName("updatedAt")
    val updatedAt: String? = null
    @SerializedName("__v")
    val __v: Int? = null
}


//class AddToWishListResponse {
//
////    @SerializedName("result") val result : ArrayList<WishlistResult>?=null
//    @SerializedName("responseMessage") val responseMessage : String?=null
//    @SerializedName("responseCode") val responseCode : Int?=null
//}