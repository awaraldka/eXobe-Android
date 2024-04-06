package com.exobe.entity.response.serviceTab


import com.google.gson.annotations.SerializedName


class NewServicesResponseResponse (
    @SerializedName("result")
    val result: NewServicesResponseResult = NewServicesResponseResult(),

    @SerializedName("responseMessage")
    val responseMessage: String = "",

    @SerializedName("responseCode")
    val responseCode: Int = 0
)
class NewServicesResponseResult {
    @SerializedName("docs")
    val docs: ArrayList<NewServicesResponseDoc> = ArrayList()
}

class NewServicesResponseDoc {
    @SerializedName("subCategory")
    val subCategory: NewServicesResponseSubCategory = NewServicesResponseSubCategory()

    @SerializedName("serviceArray")
    val serviceArray: List<NewServicesResponseServiceArray> = listOf()
    @SerializedName("isSelected")
    val isSelected: Boolean = false
    var expanded: Boolean = false

}

class NewServicesResponseSubCategory {
    @SerializedName("thumbnail")
    val thumbnail: String = ""

    @SerializedName("serviceImage")
    val serviceImage: ArrayList<String> = ArrayList()

    @SerializedName("status")
    val status: String = ""

    @SerializedName("_id")
    val id: String = ""

    @SerializedName("categoryId")
    val categoryId: String = ""

    @SerializedName("subCategoryName")
    val subCategoryName: String = ""

    @SerializedName("__v")
    val v: Int = 0

    @SerializedName("createdAt")
    val createdAt: String = ""

    @SerializedName("updatedAt")
    val updatedAt: String = ""
}

class NewServicesResponseServiceArray {
    @SerializedName("serviceDetails")
    val serviceDetails: NewServicesResponseServiceDetails = NewServicesResponseServiceDetails()

    @SerializedName("catalogueId") var catalogueId: String = ""
    @SerializedName("price") var price: String = ""
    @SerializedName("isSelected") var isSelected: Boolean = false
    var serviceId: String = ""




}

class NewServicesResponseServiceDetails {
    @SerializedName("serviceImage")
    val serviceImage: ArrayList<String> = ArrayList()

    @SerializedName("thumbnail")
    val thumbnail: String = ""

    @SerializedName("status")
    val status: String = ""

    @SerializedName("_id")
    val id: String = ""

    @SerializedName("description")
    val description: String = ""

    @SerializedName("categoryId")
    val categoryId: NewServicesResponseCategoryId = NewServicesResponseCategoryId()

    @SerializedName("subCategoryId")
    val subCategoryId: NewServicesResponseSubCategoryId = NewServicesResponseSubCategoryId()

    @SerializedName("serviceName")
    val serviceName: String = ""

    @SerializedName("createdAt")
    val createdAt: String = ""

    @SerializedName("updatedAt")
    val updatedAt: String = ""

    @SerializedName("__v")
    val v: Int = 0


    @SerializedName("flag")
    var isSelected: Boolean = false

    @SerializedName("ID")
    var _id: String = ""

    @SerializedName("price")
    var price: Number = 0

    @SerializedName("isUpdate")
    var isUpdate: Boolean = false

    @SerializedName("isRemove")
    var isRemove: Boolean = false

}

class NewServicesResponseCategoryId {
    @SerializedName("thumbnail")
    val thumbnail: String = ""

    @SerializedName("status")
    val status: String = ""

    @SerializedName("_id")
    val id: String = ""

    @SerializedName("categoryName")
    val categoryName: String = ""

    @SerializedName("categoryImage")
    val categoryImage: String = ""

    @SerializedName("description")
    val description: String = ""

    @SerializedName("createdAt")
    val createdAt: String = ""

    @SerializedName("updatedAt")
    val updatedAt: String = ""

    @SerializedName("__v")
    val v: Int = 0
}

class NewServicesResponseSubCategoryId {
    @SerializedName("thumbnail")
    val thumbnail: String = ""

    @SerializedName("serviceImage")
    val serviceImage: ArrayList<String> = ArrayList()

    @SerializedName("status")
    val status: String = ""

    @SerializedName("_id")
    val id: String = ""

    @SerializedName("categoryId")
    val categoryId: String = ""

    @SerializedName("subCategoryName")
    val subCategoryName: String = ""

    @SerializedName("__v")
    val v: Int = 0

    @SerializedName("createdAt")
    val createdAt: String = ""

    @SerializedName("updatedAt")
    val updatedAt: String = ""
}


