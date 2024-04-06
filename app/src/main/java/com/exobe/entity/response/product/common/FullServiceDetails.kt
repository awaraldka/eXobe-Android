package com.exobe.entity.response.product.common

import com.exobe.entity.response.CategoryDetails
import com.google.gson.annotations.SerializedName

class FullServiceDetails {
    @SerializedName("service") var service = Service()
}

class Service {
    @SerializedName("categoryDetails") var categoryDetails = CategoryDetails()
}