package com.exobe.entity.response.serviceTab

import com.exobe.entity.response.MyProfileStoreLocation
import com.google.gson.annotations.SerializedName

class GetAllOrdersCommonResponse {
    @SerializedName("responseCode") val responseCode :Int = 0
    @SerializedName("responseMessage") val responseMessage :String = ""
    @SerializedName("result") val result : GetAllOrdersCommonResult =  GetAllOrdersCommonResult()
}



class GetAllOrdersCommonResult{
    @SerializedName("docs") val docs : ArrayList<GetAllOrdersCommonDocs> =  arrayListOf()
    @SerializedName("limit") val limit :Int = 0
    @SerializedName("page") val page :Int = 0
    @SerializedName("pages") val pages :Int = 0
    @SerializedName("total") val total :Int = 0
    @SerializedName("walletBalance") val walletBalance :WalletBalance = WalletBalance()
}

class GetAllOrdersCommonDocs{
    @SerializedName("requestStatus") val requestStatus:String = ""
    @SerializedName("_id") val _id:String = ""
    @SerializedName("createdAt") val createdAt:String = ""
    @SerializedName("assignedUser") val assignedUser:AssignedUser = AssignedUser()
    @SerializedName("orderId") val orderId:OrderId = OrderId()
    @SerializedName("userType") val userType:String = ""


}

class AssignedUser{
    @SerializedName("storeLocation") val storeLocation: MyProfileStoreLocation? = null
    @SerializedName("_id") val id:String = ""
    @SerializedName("firstName") val firstName:String = ""
    @SerializedName("lastName") val lastName:String = ""
    @SerializedName("mobileNumber") val mobileNumber:String = ""
    @SerializedName("addressLine1") val addressLine1:String = ""
    @SerializedName("zipCode") val zipCode:String = ""
    @SerializedName("email") val email:String = ""
    @SerializedName("city") val city:String = ""
    @SerializedName("state") val state:String = ""
    @SerializedName("country") val country:String = ""
}



class OrderId{
    @SerializedName("productDetails") val productDetails : ArrayList<ProductDetailsCommon> =  arrayListOf()
    @SerializedName("orderPrice") val orderPrice:Number = 0
    @SerializedName("orderId") val orderId:String = ""
    @SerializedName("deliveryType") val deliveryType:String = ""
    @SerializedName("userId") val userId:UserIdCommon = UserIdCommon()

}


class ProductDetailsCommon{
    @SerializedName("productId") val productId:ProductIdCommon = ProductIdCommon()
    @SerializedName("quantity") val quantity:Int = 0
}


class ProductIdCommon{
    @SerializedName("userId") val userId:UserIdCommon = UserIdCommon()
}

class UserIdCommon{
    @SerializedName("firstName") val firstName:String = ""
    @SerializedName("lastName") val lastName:String = ""
}

class WalletBalance{
    @SerializedName("totalWalletAmount") val totalWalletAmount:Number = 0
    @SerializedName("walletAmount") val walletAmount:Number = 0
}