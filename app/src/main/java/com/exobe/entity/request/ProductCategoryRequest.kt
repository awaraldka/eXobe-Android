package com.exobe.entity.request

import com.google.gson.annotations.SerializedName

class ProductCategoryRequest {
    @SerializedName("categoryId")
    var categoryId :ArrayList<String> = ArrayList<String>()

    @SerializedName("search")
    var search :String =""

}