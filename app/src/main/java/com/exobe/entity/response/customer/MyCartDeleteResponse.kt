package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class DeleteResponse{

    @SerializedName("result"          ) var result          : DeleteResult? = DeleteResult()
    @SerializedName("responseMessage" ) var responseMessage : String? = ""
    @SerializedName("responseCode"    ) var responseCode    : Int?    = 0
}




data class DeleteResult (

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