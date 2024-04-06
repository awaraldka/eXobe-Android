package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class MarkAsDoneOtpResponse {


//    @SerializedName("result")
//    val result: MarkAsDoneOtpResult?=MarkAsDoneOtpResult()
    @SerializedName("responseMessage")
    val responseMessage: String = ""
    @SerializedName("responseCode")
    val responseCode: Int = 0

}

class MarkAsDoneOtpResult {

    @SerializedName("taxPrice")
    val taxPrice: Number=0



    @SerializedName("orderStatus")
    val orderStatus: String=""

    @SerializedName("deliveryStatus")
    val deliveryStatus: String=""

    @SerializedName("paymentStatus")
    val paymentStatus: String=""


    @SerializedName("status")
    val status: String=""

    @SerializedName("serviceOtpVerification")
    val serviceOtpVerification: Boolean=false

    @SerializedName("_id")
    val _id: String=""

    @SerializedName("userId")
    val userId: String=""

    @SerializedName("serviceDetails")
    val serviceDetails: List<MarkAsDoneOtpServiceDetails>?=ArrayList()

    @SerializedName("orderPrice")
    val orderPrice: Number=0

    @SerializedName("actualPrice")
    val actualPrice: Number=0

    @SerializedName("orderId")
    val orderId: String=""

    @SerializedName("orderType")
    val orderType: String=""

    @SerializedName("productDetails")
    val productDetails: List<String>?=ArrayList()

    @SerializedName("createdAt")
    val createdAt: String=""

    @SerializedName("updatedAt")
    val updatedAt: String=""

    @SerializedName("__v")
    val __v: Int=0

    @SerializedName("shippingAddress")
    val shippingAddress: String=""

    @SerializedName("otpService")
    val otpService: Int=0

    @SerializedName("otpServiceExpireTime")
    val otpServiceExpireTime: Int=0
}
class MarkAsDoneOtpServiceDetails {

    @SerializedName("_id")
    val _id: String=""
    @SerializedName("serviceId")
    val serviceId: String=""
    @SerializedName("quantity")
    val quantity: Int=0
    @SerializedName("price")
    val price: Number=0
}