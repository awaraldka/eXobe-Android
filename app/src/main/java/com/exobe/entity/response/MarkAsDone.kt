package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class MarkAsDone_Response (
//    @SerializedName("result") val result : MarkAsDone_Result?=null,
    @SerializedName("responseMessage") val responseMessage : String?=null,
    @SerializedName("responseCode") val responseCode : Int?=null
        )
class MarkAsDone_Result(
    @SerializedName("taxPrice") val taxPrice : Number=0,
    @SerializedName("dealId") val dealId : List<String>?=null,
    @SerializedName("orderStatus") val orderStatus : String?=null,
    @SerializedName("deliveryStatus") val deliveryStatus : String?=null,
    @SerializedName("paymentStatus") val paymentStatus : String?=null,
    @SerializedName("slots") val slots : List<String>?=null,
    @SerializedName("status") val status : String,
    @SerializedName("serviceOtpVerification") val serviceOtpVerification : Boolean?=null,
    @SerializedName("_id") val _id : String?=null,
    @SerializedName("actualPrice") val actualPrice : Number=0,
    @SerializedName("orderPrice") val orderPrice :Number=0,
    @SerializedName("userId") val userId : String?=null,
    @SerializedName("productDetails") val productDetails : List<MarkAsDone_ProductDetails>?=null,
    @SerializedName("shippingAddress") val shippingAddress : String?=null,
    @SerializedName("orderType") val orderType : String?=null,
    @SerializedName("orderId") val orderId : String?=null,
//    @SerializedName("serviceDetails") val serviceDetails : List<String>?=null,
    @SerializedName("createdAt") val createdAt : String?=null,
    @SerializedName("updatedAt") val updatedAt : String?=null,
    @SerializedName("__v") val __v : Int?=null,
    @SerializedName("otpService") val otpService : Int?=null,
    @SerializedName("otpServiceExpireTime") val otpServiceExpireTime : Int?=null
)
class MarkAsDone_ProductDetails(
    @SerializedName("_id") val _id : String?=null,
    @SerializedName("productId") val productId : String?=null,
    @SerializedName("quantity") val quantity : Int?=null

)