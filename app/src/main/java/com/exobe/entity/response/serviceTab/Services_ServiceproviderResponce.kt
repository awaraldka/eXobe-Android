package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class Services_ServiceproviderResponce {

    @SerializedName("result") val result : Services_ServiceproviderResultResponce?=null
    @SerializedName("responseMessage") val responseMessage : String?=null
    @SerializedName("responseCode") val responseCode : Int?=null
}

class Services_ServiceproviderResultResponce {

    @SerializedName("docs") val docs : List<Services_ServiceproviderDocResponce>?=null

}

class Services_ServiceproviderDocResponce {

    @SerializedName("status") val status : String?=null
    @SerializedName("_id") val _id : String?=null
    @SerializedName("categoryName") val categoryName : String?=null
    @SerializedName("categoryImage") val categoryImage : String?=null
    @SerializedName("description") val description : String?=null
    @SerializedName("createdAt") val createdAt : String?=null
    @SerializedName("updatedAt") val updatedAt : String?=null
    @SerializedName("__v") val __v : Int?=null
}