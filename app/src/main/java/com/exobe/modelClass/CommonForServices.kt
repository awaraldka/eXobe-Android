package com.exobe.modelClass

import okhttp3.RequestBody

class CommonForServices(var oderid:String,var retailersName:String,var FieldEntity:String,var price:String,var quantity:String,var orderDate:String)


class ViewAllOrders(var name:String,var address:String,var zipcode:String,var phoneNumber:String,var email:String,var details: ArrayList<SubDetails>)




class SubDetails(var name:String,var image:Int,var price:String,var quantity: String,var size:String,var productId:String)




class WalletTransactionData(var orderid:String,var orderDate:String,var orderAmount:String,var RetailersName:String, var receivedMoney:String, var status:String)



class SelectDocumentData(var name:String, var pathName:String,var showDeleteIcon:Boolean,var documentList: RequestBody?, var requestKey:String)

