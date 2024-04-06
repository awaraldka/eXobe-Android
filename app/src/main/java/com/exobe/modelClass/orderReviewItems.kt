package com.exobe.modelClass



class orderReviewItems {
    var doc:ArrayList<doc>?=null

}

data class NotificationModel (
    var txt1 : String,
    var txt2:String
)
data class CartProductModel (

    var background:Int,
    var image:Int,
    var name:String,
    var price:String
)
data class PaymentStatusModel (
    var txt1 : String,
    var txt2:String
)

class doc(
    val images: Int?=null,
    val productname: String?=null,
    val price: String?=null,
    val quantity: Int?=0
)
class addressItem {
    var doc:ArrayList<doc>?=null

}

class Name(

    val name: String?=null,
    val address: String?=null,
    val pincode: String?=null,
    val number: Int?=0

)

class Services_Available(
    val name: Int?=null,
    val services: String?=null,
    val Years: String?=null,
    val address: String?=null
)

