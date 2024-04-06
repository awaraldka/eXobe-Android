package com.exobe.entity.response.customer

import com.google.gson.annotations.SerializedName

class AddDealsOfServices {
    @SerializedName("result") val result : AddDealsOfServicesResult = AddDealsOfServicesResult()
    @SerializedName("responseMessage") val responseMessage : String= ""
    @SerializedName("responseCode") val responseCode : Int = 0
}


class AddDealsOfServicesResult{
    @SerializedName("quantity") val quantity : Int = 0
    @SerializedName("disCountType") val disCountType : String= ""
    @SerializedName("dealsFor") val dealsFor : String= ""
    @SerializedName("productId") val productId = ArrayList<String>()
//    @SerializedName("serviceId") val serviceId = ArrayList<String>()
    @SerializedName("expired") val expired : Boolean = false
    @SerializedName("status") val status : String= ""
    @SerializedName("_id") val _id : String= ""
    @SerializedName("serviceCategoryId") val serviceCategoryId : String= ""
    @SerializedName("serviceSubCategoryId") val serviceSubCategoryId : String= ""
    @SerializedName("dealPrice") val dealPrice : Number = 0
    @SerializedName("dealStartTime") val dealStartTime : String= ""
    @SerializedName("dealEndTime") val dealEndTime : String= ""
    @SerializedName("userId") val userId : String= ""
    @SerializedName("dealType") val dealType : String= ""
    @SerializedName("userType") val userType : String= ""
    @SerializedName("createdAt") val createdAt : String= ""
    @SerializedName("updatedAt") val updatedAt : String= ""
    @SerializedName("__v") val __v : Int= 0
}