package com.exobe.entity.request

import com.google.gson.annotations.SerializedName

class BuyProductRequest {


        @SerializedName("actualPrice") var actualPrice : Number=0
        @SerializedName("orderPrice") var orderPrice : Number=0
        @SerializedName("address") var address : String=""
        @SerializedName("deliveryType") var deliveryType : String=""
        @SerializedName("deliveryFee") var deliveryFee : Number=0
        @SerializedName("cartId") var cartId : List<String>?=null

}



class ServiceFeeConfigRequest(
        @SerializedName("serviceId") var serviceId : String="",
        @SerializedName("categoryId") var categoryId : String="",
        @SerializedName("subCategoryId") var subCategoryId : String="",
        @SerializedName("price") var price : String="",
        @SerializedName("categoryType") var categoryType : String="",
        @SerializedName("categoryEnum") var categoryEnum : String="",
        @SerializedName("isSelected") var isSelected : Boolean = false,
        @SerializedName("pickupFeeId") var pickupFeeId  : String="",
        @SerializedName("storageFeeId") var storageFeeId   : String="",
        @SerializedName("deliveryFeeId") var deliveryFeeId    : String="",

)