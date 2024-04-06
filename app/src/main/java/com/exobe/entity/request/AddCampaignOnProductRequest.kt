package com.exobe.entity.request

import com.google.gson.annotations.SerializedName

class AddCampaignOnProductRequest{
    @SerializedName("productId") var productId:String = ""
    @SerializedName("startDate") var startDate:String = ""
    @SerializedName("endDate") var endDate:String = ""
    @SerializedName("campaignOn") var campaignOn:String = ""
    @SerializedName("campaignDetail") var campaignDetail:ArrayList<CampaignDetail> = arrayListOf()
}

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


class AddCampaignOnServiceRequest{
    @SerializedName("serviceId") var serviceId:String = ""
    @SerializedName("startDate") var startDate:String = ""
    @SerializedName("endDate") var endDate:String = ""
    @SerializedName("serviceDiscountedPrice") var serviceDiscountedPrice:String = ""
    @SerializedName("campaignOn") var campaignOn:String = ""
}

