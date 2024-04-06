package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class ServicesServiceproviderResponce {

    @SerializedName("result") val result : Services_ServiceproviderResult = Services_ServiceproviderResult()
    @SerializedName("responseMessage") val responseMessage : String = ""
    @SerializedName("responseCode") val responseCode : Int = 0
}

class Services_ServiceproviderResult {
    @SerializedName("docs") val docs : ArrayList<Services_ServiceproviderDocs> = ArrayList()
}

class Services_ServiceproviderDocs {
    @SerializedName("subCategory") val subCategory : Services_ServiceproviderSubCategory =Services_ServiceproviderSubCategory()
    @SerializedName("price") val price : Number=0
    @SerializedName("isSelected") val isSelected : Boolean = false

}

class Services_ServiceproviderSubCategory{
    @SerializedName("thumbnail") val thumbnail : String= ""
    @SerializedName("serviceImage") val serviceImage : ArrayList<String> = ArrayList()
    @SerializedName("status") val status : String= ""
    @SerializedName("_id") val _id : String= ""
    @SerializedName("categoryId") val categoryId : String= ""
    @SerializedName("description") val description : String= ""
    @SerializedName("serviceName") val serviceName : String= ""
//    @SerializedName("__v") val __v : Int=0
//    @SerializedName("createdAt") val createdAt : String= ""
//    @SerializedName("updatedAt") val updatedAt : String=""


    @SerializedName("flag")
    var isSelected : Boolean = false

    @SerializedName("ID")
    var id : String = ""

    @SerializedName("price")
    var price : Number = 0

    @SerializedName("isUpdate")
    var isUpdate : Boolean = false

    @SerializedName("isRemove")
    var isRemove : Boolean = false










}