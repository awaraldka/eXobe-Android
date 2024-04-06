package com.exobe.entity.response

import com.exobe.entity.response.customer.ProductIdCampaign
import com.google.gson.annotations.SerializedName

class ViewProductCampaignResponse(
    @SerializedName("result") var result: ViewProductCampaignResult = ViewProductCampaignResult(),
    @SerializedName("responseMessage") var responseMessage: String= "",
    @SerializedName("responseCode") var responseCode: Int=0
)

class ViewProductCampaignResult(
    @SerializedName("_id") var id: String = "",
    @SerializedName("campaignDetail") var campaignDetail:ArrayList<CampaignDetail> = arrayListOf(),
    @SerializedName("campaignOn") var campaignOn: String = "",
    @SerializedName("endDate") var endDate: String = "",
    @SerializedName("startDate") var startDate: String = "",
    @SerializedName("status") var status: String = "",
    @SerializedName("productId") var productId: ProductIdCampaign = ProductIdCampaign()
)

class CampaignDetail(

    @SerializedName("id") var id:String = "",
    @SerializedName("quantity") var quantity:String = "",
    @SerializedName("value") var value:String = "",
    @SerializedName("price") var price:String = "",
    @SerializedName("unit") var unit:String = "",
    @SerializedName("discountedPrice") var discountedPrice:String = "",
    @SerializedName("discountedPercentage") var discountedPercentage:String = "",
    @SerializedName("releaseQuantity") var releaseQuantity:String = ""
)