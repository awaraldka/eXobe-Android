package com.exobe.entity.response

import com.exobe.entity.request.PriceSizeDetailsRequest
import com.google.gson.annotations.SerializedName

class OrderHistoryRetailerResponse {
    @SerializedName("result")
    val result: OrderHistoryRetailerResult? = null
    @SerializedName("responseMessage")
    val responseMessage: String? = ""
    @SerializedName("responseCode")
    val responseCode: Int? = 0
}

class OrderHistoryRetailerResult {

    @SerializedName("docs")
    val docs: ArrayList<OrderHistoryRetailerDocs>? = ArrayList()

    @SerializedName("total")
    val total: Int? = 0

    @SerializedName("limit")
    val limit: Int? = 0

    @SerializedName("page")
    val page: Int? = 0

    @SerializedName("pages")
    val pages: Int? = 0
}

class OrderHistoryRetailerDocs {

    @SerializedName("_id")
    val _id: String? = ""

    @SerializedName("deliveryStatus")
    val deliveryStatus: String? = ""

    @SerializedName("actualPrice")
    val actualPrice: Number = 0

    @SerializedName("orderPrice")
    val orderPrice: Number = 0

    @SerializedName("productDetails")
    val productDetails: ArrayList<OrderHistoryRetailerProductDetails>? = ArrayList()

    @SerializedName("orderId")
    val orderId: String? = ""


    @SerializedName("createdAt")
    val createdAt: String? = ""

}

class OrderHistoryRetailerProductDetails {

    @SerializedName("_id")
    var Id: String? = ""

    @SerializedName("priceSizeDetails")
    var priceSizeDetails : PriceSizeDetailsRequest? = null

    @SerializedName("productId")
    var productId: OrderHistoryRetailerProductId? = null

    @SerializedName("quantity")
    var quantity: Int? = 0

    @SerializedName("price")
    val price: Number = 0

}

class OrderHistoryRetailerProductId {


    @SerializedName("discount")
    val discount: Int? = null
    @SerializedName("productImage")
    val productImage: List<String>? = null

    @SerializedName("thumbnail")
    val thumbnail: String? = ""

    @SerializedName("productFor")
    val productFor: String? = null

    @SerializedName("status")
    val status: String? = null

    @SerializedName("_id")
    val _id: String? = null


    @SerializedName("productName")
    val productName: String? = ""


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


    @SerializedName("createdAt")
    val createdAt: String? = null

    @SerializedName("updatedAt")
    val updatedAt: String? = null

    @SerializedName("__v")
    val __v: Int? = null

}

