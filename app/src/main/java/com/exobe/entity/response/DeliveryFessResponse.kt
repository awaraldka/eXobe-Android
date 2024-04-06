package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class DeliveryFessResponse(
    @SerializedName("result") val result: ArrayList<DeliveryFeesResult>,
    @SerializedName("responseMessage") val responseMessage: String,
    @SerializedName("responseCode") val responseCode: Int
)

class DeliveryFeesResult(
    @SerializedName("description") val description: String,
    @SerializedName("deliveryFee") val deliveryAmount: Number,



    @SerializedName("status") val status: String,
    @SerializedName("_id") val _id: String,
    @SerializedName("pickupFee") val pickupFee: Number? = null,
    @SerializedName("feeName") val feeName: String,
    @SerializedName("deliveryType") val deliveryType: String,
    @SerializedName("userType") val userType: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("sizeType") val sizeType: SizeType? = null,
    @SerializedName("storageFee") val storageFee: StorageFee? = null,
    @SerializedName("standardFee") val standardFee: Number? = null,
    @SerializedName("__v") val __v: Int,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("deliveryOption") val deliveryOption: String,


    var isSelected: Boolean = false,
    var fees: String = "",
    var dailyFee:String = "",
    var weeklyFee:String = "",
    var monthlyFee:String = "",
    var quarterlyFee:String = ""



    )


class SizeType(
    @SerializedName("minimumSize") val minimumSize: String,
    @SerializedName("maximumSize") val maximumSize: String,
)
class StorageFee(
    @SerializedName("daily") val daily: Number,
    @SerializedName("weekly") val weekly: Number,
    @SerializedName("monthly") val monthly: Number,
    @SerializedName("quarterly") val quarterly: Number,
    @SerializedName("yearly") val yearly: Number,

    )
