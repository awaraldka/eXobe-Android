package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

data class MyCartDeleteResponse (

//    @SerializedName("result"          ) var result          : MyCartDeleteResult? = MyCartDeleteResult(),
    @SerializedName("responseMessage" ) var responseMessage : String? = null,
    @SerializedName("responseCode"    ) var responseCode    : Int?    = null
)

data class MyCartDeleteResult (

    @SerializedName("buyStatus" ) var buyStatus : String? = null,
    @SerializedName("status"    ) var status    : String? = null,
    @SerializedName("_id"       ) var Id        : String? = null,
    @SerializedName("productId" ) var productId : String? = null,
    @SerializedName("quantity"  ) var quantity  : Int?    = null,
    @SerializedName("orderType" ) var orderType : String? = null,
    @SerializedName("userId"    ) var userId    : String? = null,
    @SerializedName("createdAt" ) var createdAt : String? = null,
    @SerializedName("updatedAt" ) var updatedAt : String? = null,
    @SerializedName("__v"       ) var _v        : Int?    = null

)