package com.exobe.entity.response

import com.exobe.entity.request.PriceSizeDetailsRequest
import com.exobe.entity.response.serviceTab.DealRefrenceId
import com.google.gson.annotations.SerializedName

class OrderViewResponse {


    @SerializedName("result")
    val result: OrderViewResult? = null
    @SerializedName("responseMessage")
    val responseMessage: String? = null
    @SerializedName("responseCode")
    val responseCode: Int? = null

}

class OrderViewResult {

    @SerializedName("orderTracking") val orderTracking  : ArrayList<OrderTracking>  = arrayListOf()


    @SerializedName("actualPrice")
    val actualPrice: Number = 0
    @SerializedName("orderPrice")
    val orderPrice: Number = 0

    @SerializedName("productDetails")
    val productDetails: ArrayList<OrderViewProductDetails>? = null
    @SerializedName("shippingFixedAddress")
    val shippingFixedAddress: NewOrderServiceReqShippingFixedAddress = NewOrderServiceReqShippingFixedAddress()
    @SerializedName("orderType")
    val orderType: String? = null
    @SerializedName("orderId")
    val orderId: String? = null
    @SerializedName("serviceDetails")
    val serviceDetails: List<String>? = null

    @SerializedName("orderStatus") val orderStatus: String = ""
    @SerializedName("deliveryStatus") val deliveryStatus: String = ""
    @SerializedName("paymentStatus") val paymentStatus: String = ""
    @SerializedName("deliveryFee") val deliveryFee: Number = 0


    @SerializedName("createdAt") val createdAt : String?=null
}


class OrderViewProductDetails {

    @SerializedName("_id")
    val _id: String? = null

    @SerializedName("priceSizeDetails")
    var priceSizeDetails : PriceSizeDetailsRequest? = null

    @SerializedName("productId")
    val productId: OrderViewProductId? = null


    @SerializedName("quantity")
    val quantity: Int? = null

    @SerializedName("createdAt") val createdAt : String?=null
}

class OrderViewProductId {
    @SerializedName("thumbnail")
    val thumbnail: String = ""

    @SerializedName("productImage")
    val productImage: List<String>? = null


    @SerializedName("dealReferenceId") val dealReferenceId: DealRefrenceId = DealRefrenceId()


    @SerializedName("discount")
    val discount: Int? = null

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
    @SerializedName("expectedDeliveryDays")
    var expectedDeliveryDays: String? = null
}


class OrderTracking{
    @SerializedName("orderType") val orderType:String = ""
    @SerializedName("orderStatus") val orderStatus:Boolean =false
    @SerializedName("orderDescription") val orderDescription:String = ""


    var progressValue = 50

}

