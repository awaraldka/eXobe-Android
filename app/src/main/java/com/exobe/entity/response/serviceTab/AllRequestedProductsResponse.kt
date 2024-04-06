package com.exobe.entity.response.serviceTab

import com.google.gson.annotations.SerializedName

class AllRequestedProductsResponse {
    @SerializedName("responseCode") val responseCode:Int = 0
    @SerializedName("responseMessage") val responseMessage:String = ""
    @SerializedName("result") val result : AllRequestedProductsResult = AllRequestedProductsResult()
}


class AllRequestedProductsResult{
    @SerializedName("products") val products : ArrayList<AllProducts> = arrayListOf()
    @SerializedName("userId") val userid : AssignedUser = AssignedUser()
}
