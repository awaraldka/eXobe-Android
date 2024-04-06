package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class DeliveryFessCustomerResponse(
    @SerializedName("result") val result: ArrayList<DeliveryFessCustomerResult>,
    @SerializedName("responseMessage") val responseMessage: String,
    @SerializedName("responseCode") val responseCode: Int
)

class DeliveryFessCustomerResult(
    @SerializedName("description") val description: String,
    @SerializedName("deliveryOption") val deliveryOption: String,
    @SerializedName("deliveryAmount") val deliveryAmount: Number,

    @SerializedName("status") val status: String,
    @SerializedName("_id") val _id: String,
    @SerializedName("deliveryType") val deliveryType: String,
    @SerializedName("userType") val userType: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("createdAt") val createdAt: String,


    var isSelected: Boolean = false,




    )



