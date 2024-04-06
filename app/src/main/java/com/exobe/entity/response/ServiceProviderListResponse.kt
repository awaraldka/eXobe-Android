package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class ServiceProviderListResponse(
    @SerializedName("result")
    val result: ArrayList<ServiceProviderListData>,
    @SerializedName("responseMessage")
    val responseMessage: String = "",
    @SerializedName("responseCode")
    val responseCode: Int = 0
)

data class ServiceProviderListData(
    @SerializedName("serviceLocation")
    val serviceLocation: ServiceLocation,
    @SerializedName("serviceImage")
    val serviceImage: List<String>,
    @SerializedName("slots")
    val slots: List<Any>,
    @SerializedName("status")
    val status: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("serviceName")
    val serviceName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("categoryId")
    val categoryId: ServiceProviderListCategoryId,
    @SerializedName("subCategoryId")
    val subCategoryId: ServiceProviderListSubCategoryId,
    @SerializedName("__v")
    val v: Int,
    @SerializedName("serviceReferenceId")
    val serviceReferenceId: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)

data class ServiceLocation(
    @SerializedName("type")
    val type: String,
    @SerializedName("coordinates")
    val coordinates: List<Double>
)

data class ServiceProviderListCategoryId(
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("categoryName")
    val categoryName: String,
    @SerializedName("categoryImage")
    val categoryImage: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("__v")
    val v: Int
)

data class ServiceProviderListSubCategoryId(
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("serviceImage")
    val serviceImage: List<Any>,
    @SerializedName("status")
    val status: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("categoryId")
    val categoryId: String,
    @SerializedName("subCategoryName")
    val subCategoryName: String,
    @SerializedName("__v")
    val v: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)