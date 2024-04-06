package com.exobe.entity.request

import com.google.gson.annotations.SerializedName

class AddToCartRequest {
    @SerializedName("productId")
    var productId :String? = null

    @SerializedName("priceSizeDetails")
    var priceSizeDetails :PriceSizeDetailsRequest? = null

    @SerializedName("orderType")
    var orderType :String? = null

    @SerializedName("quantity")
    var quantity :Int? = null

    @SerializedName("totalPrice")
    var totalPrice :Number? = null

    @SerializedName("dealAmount")
    var dealAmount :Number? = null

    @SerializedName("addType")
    var addType :String? = null


}

class PriceSizeDetailsRequest {
    @SerializedName("value")
    var value :String? = null

    @SerializedName("price")
    var price :Number? = null

    @SerializedName("unit")
    var unit :String? = null

    @SerializedName("id")
    var id :String? = null
}

class UpdateQuantity {
    @SerializedName("priceSizeDetails")
    var priceSizeDetails :PriceSizeDetailsRequest? = null

    @SerializedName("quantity")
    var quantity :Int? = null
}