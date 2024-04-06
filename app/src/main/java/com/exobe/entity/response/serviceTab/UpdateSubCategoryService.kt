package com.exobe.entity.response.serviceTab

import com.google.gson.annotations.SerializedName

class UpdateSubCategoryService (
    @SerializedName("responseMessage") val responseMessage : String = "",
    @SerializedName("responseCode") val responseCode : Int= 0
)
