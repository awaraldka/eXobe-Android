package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class ServiceConfigResponse {

    @SerializedName("result") val result: ServiceConfigResult = ServiceConfigResult()
    @SerializedName("responseMessage") val responseMessage: String = ""
    @SerializedName("responseCode") val responseCode: Int = 0

}


class  ServiceConfigResult{
    @SerializedName("categories")val categories :List<ServiceConfigCategories> = listOf()
}

class ServiceConfigCategories{
    @SerializedName("categoryData") val categoryData : ServiceConfigCategoryId = ServiceConfigCategoryId()
    @SerializedName("subCategories") val subCategories: List<ServiceConfigServiceDetails> = listOf()
    @SerializedName("expanded")
    var expanded: Boolean =  false
    var isSelected: Boolean =  false
}

class ServiceConfigServiceDetails{
    @SerializedName("subCategoryData") val subCategoryData : ServiceConfigSubCategoryId=  ServiceConfigSubCategoryId()
    @SerializedName("services") val services: List<SubCategoryServiceDetails> = listOf()
    @SerializedName("expanded")
    var expanded: Boolean =  false
    var isSelected: Boolean =  false
}

class SubCategoryServiceDetails{
    @SerializedName("_id") val id : String = ""
    @SerializedName("serviceName") val serviceName : String = ""
    @SerializedName("categoryEnum") val categoryEnum : String = ""
    @SerializedName("storageFeeId") val storageFeeId : String = ""
    @SerializedName("pickupFeeId") val pickupFeeId : String = ""
    @SerializedName("deliveryFeeId") val deliveryFeeId  : String = ""
    var isSelected: Boolean =  false
    var fees :String = ""
}

class ServiceConfigSubCategoryId{
    @SerializedName("_id") val id : String = ""
    @SerializedName("subCategoryName") val subCategoryName : String= ""
    @SerializedName("serviceName") val serviceName : String=""
    @SerializedName("thumbnail") val thumbnail : String=""
    @SerializedName("deliveryFeeId") val deliveryFeeId : String=""
}


class ServiceConfigCategoryId{
    @SerializedName("status")
    val status: String?=null
    @SerializedName("_id")
    val _id: String?=null
    @SerializedName("categoryName")
    val categoryName: String?=null
    @SerializedName("categoryImage")
    val categoryImage: String?=null
    @SerializedName("createdAt")
    val createdAt: String?=null
    @SerializedName("updatedAt")
    val updatedAt: String?=null
    @SerializedName("__v") val __v: Int?=null
    @SerializedName("categoryType") val categoryType:String= ""
}