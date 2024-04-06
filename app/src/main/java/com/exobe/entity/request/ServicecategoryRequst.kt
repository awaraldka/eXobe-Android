package com.exobe.entity.request

import com.google.gson.annotations.SerializedName


class SPServiceCategoryRequest{
    @SerializedName("categoryId") var categoryId   : String = ""
    @SerializedName("serviceDetailsArray") var serviceDetailsArray : List<SPServiceDetailsArray> =  ArrayList()

}


class SPServiceDetailsArray(
    @SerializedName("price" ) var price : String = "",
    @SerializedName("serviceId" ) var serviceId : String = "",
    @SerializedName("isSelected" ) var isSelected : Boolean = false


)
