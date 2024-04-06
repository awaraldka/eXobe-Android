package com.exobe.entity.request

import com.google.gson.annotations.SerializedName

class MakeServiceRequest {
    @SerializedName("serviceDetails")
    var serviceDetails : ArrayList<MakeServiceDetails> = ArrayList<MakeServiceDetails>()
    @SerializedName("orderPrice") var orderPrice : Number=0
    @SerializedName("actualPrice") var actualPrice : Number=0
    @SerializedName("taxPrice") var taxPrice : Number=0
    @SerializedName("duration") var duration : String=""
    @SerializedName("slots") var slots : String=""
    @SerializedName("address") var address : String=""
    @SerializedName("dealId") var dealId : String=""
}

class MakeServiceDetails {
    @SerializedName("serviceId")
    var serviceId : String=""
    @SerializedName("quantity")
    var quantity : Int=0
    @SerializedName("price")
    var price : Number=0
}