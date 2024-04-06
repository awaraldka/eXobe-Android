package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class viewdeals_response {
    @SerializedName("result")
    val result: viewdealsResult? = null
    @SerializedName("responseMessage")
    val responseMessage: String? = null
    @SerializedName("responseCode")
    val responseCode: Int? = null
}

class viewdealsResult {

    @SerializedName("disCountType")
    val disCountType: String? = null

    @SerializedName("dealsFor")
    val dealsFor: String? = null

    @SerializedName("productId")
    val productId: List<viewdealsProductId>? = null

    @SerializedName("serviceId")
    val serviceId: Customerdeals_ServiceId? = null
    @SerializedName("serviceCategoryId")
    val serviceCategoryId: ServiceCategoryId? = null
    @SerializedName("serviceSubCategoryId")
    val serviceSubCategoryId: ServiceSubCategoryId? = null

    @SerializedName("expired")
    val expired: Boolean? = null

    @SerializedName("status")
    val status: String? = null

    @SerializedName("_id")
    val _id: String? = null

    @SerializedName("dealName")
    val dealName: String? = null

    @SerializedName("thumbnail")
    val thumbnail: String = ""

    @SerializedName("dealImage")
    val dealImage: ArrayList<String> = ArrayList()

    @SerializedName("description")
    val description: String? = null

    @SerializedName("dealPrice")
    val dealPrice: Number = 0

    @SerializedName("dealStartTime")
    val dealStartTime: String? = null

    @SerializedName("dealEndTime")
    val dealEndTime: String? = null

    @SerializedName("dealDiscount")
    val dealDiscount: String? = null

    @SerializedName("dealType")
    val dealType: String? = null

    //    @SerializedName("userId")
//    val userId: String?=null
    @SerializedName("createdAt")
    val createdAt: String? = null

    @SerializedName("dealDetails")
    val dealDetails: ArrayList<DealDetails>? = null

    @SerializedName("updatedAt")
    val updatedAt: String? = null

    @SerializedName("__v")
    val __v: Int? = null
}

class DealDetails {
    @SerializedName("_id")
    val _id: String? = null

    @SerializedName("price")
    val price: Number? = null

    @SerializedName("dealPrice")
    val dealPrice: Number? = null

    @SerializedName("quantity")
    val quantity: String? = null

    @SerializedName("unit")
    val unit: String? = null

    @SerializedName("value")
    val value: String? = null

    @SerializedName("id")
    val id: String? = null
}

class viewdealsProductId {

    @SerializedName("priceSizeDetails")
    val priceSizeDetails: ArrayList<PriceSizeDetails> = ArrayList<PriceSizeDetails>()

    @SerializedName("productImage")
    val productImage: ArrayList<String>? = null

    @SerializedName("productFor")
    val productFor: String? = null

    @SerializedName("status")
    val status: String? = null

    @SerializedName("_id")
    val _id: String? = null

    @SerializedName("productName")
    val productName: String? = null

    @SerializedName("price")
    val price: Number = 0

//    @SerializedName("unit")
//    val unit: Int? = null

    @SerializedName("description")
    val description: String? = null

    @SerializedName("brand")
    val brand: String? = null

    @SerializedName("categoryId")
    val categoryId: CategoryId? = null

    @SerializedName("subCategoryId")
    val subCategoryId: SubCategoryId? = null

    @SerializedName("quantity")
    val quantity: Int? = null

    @SerializedName("productReferenceId")
    val productReferenceId: String? = null

    @SerializedName("userId")
    val userId: String? = null

    @SerializedName("createdAt")
    val createdAt: String? = null

    @SerializedName("updatedAt")
    val updatedAt: String? = null

    @SerializedName("__v")
    val __v: Int? = null
}