package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class ServiceSubCategoryResponse {

    @SerializedName("result") val result : ServiceSubCategoryResult = ServiceSubCategoryResult()
    @SerializedName("responseMessage") val responseMessage : String?=null
    @SerializedName("responseCode") val responseCode : Int?=null
}

class ServiceSubCategoryResult {
    @SerializedName("docs") val docs : List<ServiceSubCategoryDoc>? = listOf()
}

class ServiceSubCategoryDoc(
    @SerializedName("_id") var _id:String = "",
    @SerializedName("thumbnail") var thumbnail:String = "",
    @SerializedName("subCategoryName") var subCategoryName:String = "",
)
