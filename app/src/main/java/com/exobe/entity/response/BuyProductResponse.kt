package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class BuyProductResponse (
    @SerializedName("result") val result : BuyProductResult?=null,
    @SerializedName("responseMessage") val responseMessage : String="",
    @SerializedName("responseCode") val responseCode : Int = 0
    )
class BuyProductResult {

    @SerializedName("taxPrice") val taxPrice : Number=0
    @SerializedName("dealId") val dealId : List<String>?=null
    @SerializedName("orderStatus") val orderStatus : String=""
    @SerializedName("deliveryStatus") val deliveryStatus : String=""
    @SerializedName("paymentStatus") val paymentStatus : String=""
    @SerializedName("slots") val slots : List<String>?=null
    @SerializedName("status") val status : String=""
    @SerializedName("serviceOtpVerification") val serviceOtpVerification : Boolean=false
    @SerializedName("_id") val _id : String=""
    @SerializedName("actualPrice") val actualPrice : Number=0
    @SerializedName("orderPrice") val orderPrice : Number=0
    @SerializedName("userId") val userId : String=""
    @SerializedName("productDetails") val productDetails : List<BuyProductProductDetails>?=null
    @SerializedName("shippingAddress") val shippingAddress : String=""
    @SerializedName("orderType") val orderType : String=""
    @SerializedName("orderId") val orderId : String=""
//    @SerializedName("serviceDetails") val serviceDetails : List<String>?=null
    @SerializedName("createdAt") val createdAt : String=""
    @SerializedName("updatedAt") val updatedAt : String=""
    @SerializedName("__v") val __v : Int=0
}
class BuyProductProductDetails {

    @SerializedName("_id")
    val _id: String = ""
    @SerializedName("productId")
    val productId: BuyProductProductId?=null
    @SerializedName("quantity")
    val quantity: Int=0
    @SerializedName("price")
    val price: Number=0
}
class BuyProductProductId {

    @SerializedName("productImage")
    val productImage: List<String>?=null
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
    val categoryId: BuyProductCategoryId?=null
    @SerializedName("description")
    val description: String=""
    @SerializedName("price")
    val price: Number=0
    @SerializedName("productName")
    val productName: String=""
    @SerializedName("productReferenceId")
    val productReferenceId: String=""
    @SerializedName("quantity")
    val quantity: Int=0
    @SerializedName("subCategoryId")
    val subCategoryId: BuyProductSubCategoryId?=null
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
class BuyProductCategoryId {

    @SerializedName("status")
    val status: String=""
    @SerializedName("_id")
    val _id: String=""
    @SerializedName("categoryName")
    val categoryName: String=""
    @SerializedName("categoryImage")
    val categoryImage: String=""
    @SerializedName("description")
    val description: String=""
    @SerializedName("createdAt")
    val createdAt: String=""
    @SerializedName("updatedAt")
    val updatedAt: String=""
    @SerializedName("__v")
    val __v: Int=0
}
class BuyProductSubCategoryId {

    @SerializedName("status")
    val status: String=""
    @SerializedName("_id")
    val _id: String=""
    @SerializedName("categoryId")
    val categoryId: String=""
    @SerializedName("subCategoryName")
    val subCategoryName: String=""
    @SerializedName("__v")
    val __v: Int=0
    @SerializedName("createdAt")
    val createdAt: String=""
    @SerializedName("updatedAt")
    val updatedAt: String=""
}