package com.exobe.entity.response.serviceTab

import com.google.gson.annotations.SerializedName

class SelectedServiceResponse (
    @SerializedName("result") val result: SelectedServiceResult = SelectedServiceResult(),
    @SerializedName("responseMessage") val responseMessage: String = "",
    @SerializedName("responseCode") val responseCode: Int = 0
)

class SelectedServiceResult{
    @SerializedName("isConfig") val isConfig:Boolean = false
    @SerializedName("services") val services:ArrayList<SelectedServiceServices> = arrayListOf()
}

class SelectedServiceServices{
    @SerializedName("name") val name:String = ""
    @SerializedName("category") val category: SelectedServiceCategory = SelectedServiceCategory()
}

class SelectedServiceCategory{
    @SerializedName("thumbnail") val thumbnail:String = ""
    @SerializedName("categoryType") val categoryType:String = ""
    @SerializedName("status") val status:String = ""
    @SerializedName("_id") val id:String = ""
    @SerializedName("categoryName") val categoryName:String = ""
    @SerializedName("categoryImage") val categoryImage:String = ""
    @SerializedName("description") val description:String = ""
    @SerializedName("createdAt") val createdAt:String = ""
    @SerializedName("updatedAt") val updatedAt:String = ""
}

