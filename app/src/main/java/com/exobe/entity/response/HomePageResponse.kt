package com.exobe.entity.response

import com.exobe.entity.response.customerservices.ListCategoryServiceDocs
import com.exobe.entity.response.product.DealBannerDocs
import com.exobe.entity.response.product.GuestProductDocs
import com.google.gson.annotations.SerializedName

class HomePageResponse{
    @SerializedName("result") val result : HomePageResult = HomePageResult()
    @SerializedName("responseMessage") val responseMessage : String=""
    @SerializedName("responseCode") val responseCode : Int=0
}

class HomePageResult{
    @SerializedName("products") val products: ArrayList<GuestProductDocs> = arrayListOf()
    @SerializedName("categories") val categories: ArrayList<Docs> = arrayListOf()
    @SerializedName("banner") val banner: ArrayList<HomeBannerDoc> = arrayListOf()
    @SerializedName("deals_on_product") val dealsOnProduct: ArrayList<DealBannerDocs> = arrayListOf()
    @SerializedName("booking_categories") val bookingCategories: ArrayList<ListCategoryServiceDocs> = arrayListOf()
    @SerializedName("dealsOnBooking") val dealsOnBooking: ArrayList<Customerdeals_Result> = arrayListOf()
}

