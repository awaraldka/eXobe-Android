package com.exobe.entity.response

import com.exobe.entity.response.customer.AddDealsOfServicesResult
import com.exobe.entity.response.customer.CategoryIdCampaign
import com.google.gson.annotations.SerializedName

class AddedCampaignListResponse(
    @SerializedName("result") val result: AddedCampaignListResult = AddedCampaignListResult(),
    @SerializedName("responseMessage") val responseMessage: String = "",
    @SerializedName("responseCode") val responseCode: Int = 0
)

class AddedCampaignListResult(
    @SerializedName("docs") val docs: ArrayList<AddedCampaignListDocs> = arrayListOf(),
    @SerializedName("total") val total: Int = 0,
    @SerializedName("limit") val limit: Int = 0,
    @SerializedName("page") val page: Int = 0,
    @SerializedName("pages") val pages: Int = 0,
)

class AddedCampaignListDocs(
    @SerializedName("_id") val id:String = "",
    @SerializedName("campaignOn") val campaignOn:String = "",
    @SerializedName("createdAt") val createdAt:String = "",
    @SerializedName("endDate") val endDate:String = "",
    @SerializedName("startDate") val startDate:String = "",
    @SerializedName("serviceDiscountedPrice") val serviceDiscountedPrice:String = "",
    @SerializedName("expired") val expired:Boolean = false,
    @SerializedName("isAutomaticUserNotify") val isAutomaticUserNotify:Boolean = false,
    @SerializedName("isManualUserNotify") val isManualUserNotify:Boolean = false,
    @SerializedName("isSemiAutomaticUserNotify") val isSemiAutomaticUserNotify:Boolean = false,
    @SerializedName("productId") val productId:ProductIdAddedCampaign = ProductIdAddedCampaign(),
    @SerializedName("serviceId") val serviceId:ServiceIdAddedCampaign = ServiceIdAddedCampaign(),
    @SerializedName("userId") val userId:AddedCampaignUserId = AddedCampaignUserId(),
    @SerializedName("campaignDetail") val campaignDetail:ArrayList<CampaignDetailData> = arrayListOf(),
)

class ProductIdAddedCampaign(
    @SerializedName("_id") val id:String = "",
    @SerializedName("productName") val productName: String = "",
    @SerializedName("productImage") val productImage: ArrayList<String> = arrayListOf()
)
class ServiceIdAddedCampaign(
    @SerializedName("_id") val id:String = "",
    @SerializedName("categoryId") val categoryId: CategoryIdCampaign =  CategoryIdCampaign(),
    @SerializedName("serviceName") val serviceName: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("price") val price: Number = 0,
    @SerializedName("serviceImage") val serviceImage: ArrayList<String> = arrayListOf()
)


class AddedCampaignUserId(
    @SerializedName("_id") val id:String = "",
    )


class CampaignDetailData(
    @SerializedName("_id") val id:String = "",
    @SerializedName("value") val value:String = "",
    @SerializedName("unit") val unit:String = "",
    @SerializedName("releaseQuantity") val releaseQuantity:String = "",
    @SerializedName("quantity") val quantity:String = "",
    @SerializedName("price") val price:String = "",
    @SerializedName("id") val idDetail:String = "",
    @SerializedName("discountedPrice") val discountedPrice:String = "",
    @SerializedName("discountedPercentage") val discountedPercentage:String = "",
    )