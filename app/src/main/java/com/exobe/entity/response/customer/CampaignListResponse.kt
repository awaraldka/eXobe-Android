package com.exobe.entity.response.customer

import com.exobe.entity.response.PriceSizeDetails
import com.exobe.entity.response.ProductId
import com.exobe.entity.response.ServiceProvider_Orderview_Result
import com.google.gson.annotations.SerializedName

class CampaignListResponse(
    @SerializedName("result") val result : CampaignListResult =CampaignListResult(),
    @SerializedName("responseMessage") val responseMessage : String ="",
    @SerializedName("responseCode") val responseCode : Int= 0,
)

class CampaignListResult(

    @SerializedName("docs") val docs : ArrayList<CampaignListDocs> = arrayListOf(),
    @SerializedName("total") val total : Int= 0,
    @SerializedName("limit") val limit : Int= 0,
    @SerializedName("page") val page : Int= 0,
    @SerializedName("pages") val pages : Int= 0,
)


class CampaignListDocs(
    @SerializedName("campaignOn") val campaignOn:String = "",
    @SerializedName("createdAt") val   createdAt:String = "",
    @SerializedName("productSizeId") val   productSizeId:String = "",
    @SerializedName("spordicType") val   spordicType:String = "",
    @SerializedName("status") val   status:String = "",
    @SerializedName("updatedAt") val   updatedAt:String = "",
    @SerializedName("userId") val   userId:String = "",
    @SerializedName("expired") val  expired:Boolean = false,
    @SerializedName("interestedPrice") val  interestedPrice:Number = 0,
    @SerializedName("quantity") val  quantity:Number = 0,
    @SerializedName("priceSizeDetails") val  priceSizeDetails:PriceSizeDetails = PriceSizeDetails(),
    @SerializedName("productId") val  productId:ProductIdCampaign = ProductIdCampaign(),
    @SerializedName("serviceId") val  serviceId:ServiceIdCampaign = ServiceIdCampaign(),
)

class ProductIdCampaign(
    @SerializedName("productName") val   productName:String = "",
    @SerializedName("productImage") val   productImage:ArrayList<String> =  arrayListOf(),
)
class ServiceIdCampaign(
    @SerializedName("serviceName") val serviceName:String = "",
    @SerializedName("price") val price:Number = 0,
    @SerializedName("serviceImage") val serviceImage:ArrayList<String> =  arrayListOf(),
    @SerializedName("categoryId") val categoryId:CategoryIdCampaign = CategoryIdCampaign(),
)
class CategoryIdCampaign(
    @SerializedName("categoryName") val categoryName:String = "",
)