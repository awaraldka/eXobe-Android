package com.exobe.Model

data class OrderHistoryModel (
    val itemName : String,
    val itemPrice : String,
    val itemOrderID : String,
    val itemDeliveryDate : String,
    val itemStatus : String

)

data class LastOrderModel(
    val itemName : String,
    val itemDesc : String,
    val itemImage : Int,
    val status : Boolean,
    val animating:Boolean
)
