package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class HomeSearchResponce {
    @SerializedName("result"          ) var result          : HomeSearchResult? = HomeSearchResult()
    @SerializedName("responseMessage" ) var responseMessage : String? = ""
    @SerializedName("responseCode"    ) var responseCode    : Int?    = 0
}

class HomeSearchResult {
    @SerializedName("category")
    var category: ArrayList<HomeSearchCategory> = ArrayList()
    @SerializedName("products")
    var products: ArrayList<String> = ArrayList()
    @SerializedName("services")
    var services: ArrayList<String> = ArrayList()
    @SerializedName("serviceCategory")
    var serviceCategory: ArrayList<HomeSearchServiceCategory> = ArrayList()
}

class HomeSearchCategory {
    @SerializedName("status")
    var status: String? = ""
    @SerializedName("_id")
    var Id: String? = ""
    @SerializedName("categoryName")
    var categoryName: String? = ""
    @SerializedName("categoryImage")
    var categoryImage: String? = ""
    @SerializedName("description")
    var description: String? = ""
    @SerializedName("createdAt")
    var createdAt: String? = ""
    @SerializedName("updatedAt")
    var updatedAt: String? = ""
    @SerializedName("__v")
    var _v: Int? = 0
}

class HomeSearchServiceCategory{
    @SerializedName("status"        ) var status        : String? = ""
    @SerializedName("_id"           ) var Id            : String? = ""
    @SerializedName("categoryName"  ) var categoryName  : String? = ""
    @SerializedName("categoryImage" ) var categoryImage : String? = ""
    @SerializedName("description"   ) var description   : String? = ""
    @SerializedName("createdAt"     ) var createdAt     : String? = ""
    @SerializedName("updatedAt"     ) var updatedAt     : String? = ""
    @SerializedName("__v"           ) var _v            : Int?    = 0
}


