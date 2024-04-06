package com.exobe.entity.request

import com.google.gson.annotations.SerializedName

class MightBeLikeRequest {
    @SerializedName("lng")
    var lng: Double? = 0.0

    @SerializedName("lat")
    var lat: Double? = 0.0

    @SerializedName("categoryIds")
    var categoryIds: ArrayList<String> = ArrayList()

    @SerializedName("similarSubCategoryIds")
    var subCategoryIds: ArrayList<String> = ArrayList()

    @SerializedName("userType")
    var userType: String = ""


}

class ProductMightBeLikeRequest {
    @SerializedName("lng")
    var lng: Double? = 0.0

    @SerializedName("lat")
    var lat: Double? = 0.0

    @SerializedName("categoryId")
    var categoryId: String = ""

    @SerializedName("userType")
    var userType: String = ""

    @SerializedName("similarSubCategoryIds")
    var subCategoryIds: ArrayList<String> = ArrayList()
}