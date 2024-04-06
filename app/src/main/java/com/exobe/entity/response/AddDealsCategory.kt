package com.exobe.entity.response.customer

import com.google.gson.annotations.SerializedName

class AddDealsCategory {
    @SerializedName("result") val result = ArrayList<AddDealsCategoryResult>()
    @SerializedName("responseMessage") val responseMessage : String=""
    @SerializedName("responseCode") val responseCode : Int=0
}


class AddDealsCategoryResult{

    @SerializedName("thumbnail") val thumbnail : String= ""
    @SerializedName("status") val status : String= ""
    @SerializedName("_id") val _id : String= ""
    @SerializedName("categoryName") val categoryName : String= ""
    @SerializedName("serviceName") val serviceName : String= ""
    @SerializedName("subCategoryName") val subCategoryName : String= ""
    @SerializedName("description") val description : String= ""
    @SerializedName("price") val price : Number= 0.0
//    @SerializedName("createdAt") val createdAt : String= ""
//    @SerializedName("updatedAt") val updatedAt : String= ""
//    @SerializedName("__v") val __v : Int= 0
}