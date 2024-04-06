package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class AddDealResponse {
    @SerializedName("result")
    var result: AddDealResult? = AddDealResult()

    @SerializedName("responseMessage")
    var responseMessage: String? = ""

    @SerializedName("responseCode")
    var responseCode: Int? = 0
}

class AddDealResult {
    @SerializedName("quantity")
    var quantity: Int? = 0

    @SerializedName("disCountType")
    var disCountType: String? = ""

    @SerializedName("dealsFor")
    var dealsFor: String? = ""

    @SerializedName("productId")
    var productId: ArrayList<String> = ArrayList()

    @SerializedName("serviceId")
    var serviceId: ArrayList<String> = ArrayList()

    @SerializedName("expired")
    var expired: Boolean? = false

    @SerializedName("status")
    var status: String? = ""

    @SerializedName("_id")
    var Id: String? = ""

    @SerializedName("dealName")
    var dealName: String? = ""

    @SerializedName("dealImage")
    var dealImage: ArrayList<String> = ArrayList()

    @SerializedName("dealPrice")
    var dealPrice: Number? = 0

    @SerializedName("description")
    var description: String? = ""

    @SerializedName("dealDiscount")
    var dealDiscount: String? = ""

    @SerializedName("dealStartTime")
    var dealStartTime: String? = ""

    @SerializedName("dealEndTime")
    var dealEndTime: String? = ""

    @SerializedName("userId")
    var userId: String? = ""

    @SerializedName("dealType")
    var dealType: String? = ""

    @SerializedName("createdAt")
    var createdAt: String? = ""

    @SerializedName("updatedAt")
    var updatedAt: String? = ""

    @SerializedName("__v")
    var _v: Int? = 0
}