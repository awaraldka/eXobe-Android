package com.exobe.modelClass

import com.google.gson.annotations.SerializedName

data class ProductPackModel(
    @SerializedName("value") var value: String,
    @SerializedName("unit") var unit: String,
    @SerializedName("price") var amount: Number,
    @SerializedName("quantity") var qty: String
)