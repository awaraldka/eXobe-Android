package com.exobe.entity.response.customerservices


import com.exobe.entity.response.TransactionDetailsData
import com.exobe.entity.response.product.common.FullServiceDetails
import com.exobe.entity.response.product.common.UserDetails
import com.google.gson.annotations.SerializedName

class ServiceOrderListResponse {
    @SerializedName("result") val result : ServiceOrderListResult= ServiceOrderListResult()
    @SerializedName("responseMessage") val responseMessage : String =""
    @SerializedName("responseCode") val responseCode : Int=0
}

class ServiceOrderListResult {
    @SerializedName("docs") val docs : ArrayList<ServiceOrderListMeDocs> = ArrayList<ServiceOrderListMeDocs>()
    @SerializedName("total") val total : Int=0
    @SerializedName("limit") val limit : Int=0
    @SerializedName("page") val page : Int=0
    @SerializedName("pages") val pages : Int=0
}

class ServiceOrderListMeDocs {
    @SerializedName("_id") val _id : String =""
    @SerializedName("taxPrice") val taxPrice : Number=0
    @SerializedName("dealId") val dealId : ArrayList<String> = ArrayList<String>()
    @SerializedName("orderStatus") val orderStatus : String =""
    @SerializedName("deliveryStatus") val deliveryStatus : String =""
    @SerializedName("paymentStatus") val paymentStatus : String =""
    @SerializedName("status") val status : String =""
    @SerializedName("userId") val userId : String =""
    @SerializedName("serviceDetails") val fullServiceDetails : FullServiceDetails = FullServiceDetails()
    @SerializedName("orderPrice") val orderPrice : Number=0
    @SerializedName("actualPrice") val actualPrice : Number=0
    @SerializedName("orderId") val orderId : String =""
    @SerializedName("orderType") val orderType : String =""
    @SerializedName("productDetails") val productDetails : ArrayList<String> = ArrayList<String>()
    @SerializedName("createdAt") val createdAt : String =""
    @SerializedName("updatedAt") val updatedAt : String =""
    @SerializedName("__v") val __v : Int=0
    @SerializedName("shippingAddress") val shippingAddress : String =""
    @SerializedName("userDetails") val userDetails : UserDetails =  UserDetails()
    @SerializedName("transactionDetailsData") val transactionDetailsData : ArrayList<TransactionDetailsData> = ArrayList()
}