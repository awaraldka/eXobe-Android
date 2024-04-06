package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class ViewOrderResponse {

    @SerializedName("result") val result : ViewOrderResult?=null
    @SerializedName("responseMessage") val responseMessage : String?=null
    @SerializedName("responseCode") val responseCode : Int?=null
}
class ViewOrderResult {
    @SerializedName("orderTracking") val orderTracking  : ArrayList<OrderTracking>  = arrayListOf()
    @SerializedName("orderStatus")
    val orderStatus: String = ""
    @SerializedName("deliveryStatus")
    val deliveryStatus: String = ""
    @SerializedName("paymentStatus")
    val paymentStatus: String = ""
    @SerializedName("status")
    val status: String = ""
    @SerializedName("_id")
    val id: String = ""
    @SerializedName("actualPrice")
    val actualPrice: Number = 0
    @SerializedName("orderPrice")
    val orderPrice: Number = 0
    @SerializedName("userId")
    val userId: RetailerOrderManagementUserId = RetailerOrderManagementUserId()
    //    @SerializedName("productDetails")
//    val productDetails: List<RetailerOrderManagementProductDetail> = listOf()
    @SerializedName("shippingAddress")
    val shippingAddress: RetailerOrderManagementShippingAddress = RetailerOrderManagementShippingAddress()
    @SerializedName("shippingFixedAddress")
    val shippingFixedAddress: RetailerOrderManagementShippingFixedAddress = RetailerOrderManagementShippingFixedAddress()
    @SerializedName("orderType")
    val orderType: String = ""
    @SerializedName("orderId")
    val orderId: String = ""
    @SerializedName("createdAt")
    val createdAt: String = ""
    @SerializedName("updatedAt")
    val updatedAt: String = ""
    @SerializedName("__v")
    val v: Int = 0
    @SerializedName("myOrders")
    val myOrders: ArrayList<RetailerOrderManagementMyOrder> = ArrayList()
    @SerializedName("myOrderPrice")
    val myOrderPrice: Number = 0
}



class ViewOrderProductDetails {

    @SerializedName("_id")
    val _id: String?=null
    @SerializedName("productId")
    val productId: ViewOrderProductId?=null
    @SerializedName("quantity")
    val quantity: Int?=null
//    @SerializedName("price")
//    val price: Int?=null
}
class ViewOrderProductId {

    @SerializedName("productImage")
    val productImage: ArrayList<String>?=null
//    @SerializedName("discount")
//    val discount: Int?=null
//    @SerializedName("productFor")
//    val productFor: String?=null
//    @SerializedName("status")
//    val status: String?=null
    @SerializedName("_id")
    val _id: String?=null
//    @SerializedName("brand")
//    val brand: String?=null
//    @SerializedName("categoryId")
//    val categoryId: CategoryId?=null
//    @SerializedName("description")
//    val description: String?=null
    @SerializedName("price")
    val price: Number=0
    @SerializedName("productName")
    val productName: String?=null
//    @SerializedName("productReferenceId")
//    val productReferenceId: String?=null
    @SerializedName("quantity")
    val quantity: String?=null
//    @SerializedName("subCategoryId")
//    val subCategoryId: SubCategoryId?=null
//    @SerializedName("unit")
//    val unit: Int?=null
//    @SerializedName("userId")
//    val userId: UserId?=null
//    @SerializedName("createdAt")
//    val createdAt: String?=null
//    @SerializedName("updatedAt")
//    val updatedAt: String?=null
//    @SerializedName("__v")
//    val __v: Int?=null
}
class ViewOrderShippingAddress {

//    @SerializedName("status")
//    val status: String?=null
    @SerializedName("_id")
    val _id: String?=null
//    @SerializedName("countryCode")
//    val countryCode: String?=null
    @SerializedName("address")
    val address: String?=null
    @SerializedName("addressLine1")
    val addressLine1: String?=null
    @SerializedName("addressLine2")
    val addressLine2: String?=null
    @SerializedName("mobileNumber")
    val mobileNumber: String?=null
//    @SerializedName("city")
//    val city: String?=null
    @SerializedName("firstName")
    val firstName: String?=null
//    @SerializedName("state")
//    val state: String?=null
//    @SerializedName("country")
//    val country: String?=null
//    @SerializedName("zipCode")
//    val zipCode: String?=null
    @SerializedName("lastName")
    val lastName: String?=null
//    @SerializedName("userId")
//    val userId: String?=null
//    @SerializedName("createdAt")
//    val createdAt: String?=null
//    @SerializedName("updatedAt")
//    val updatedAt: String?=null
//    @SerializedName("__v")
//    val __v: Int?=null
}

class ViewOrderUserId {

    @SerializedName("storeLocation")
    val storeLocation: ViewOrderStoreLocation?=null
    @SerializedName("email") val email : String?=null
}

class ViewOrderStoreLocation (

    @SerializedName("type") val type : String,
    @SerializedName("coordinates") val coordinates : List<Double>
)

