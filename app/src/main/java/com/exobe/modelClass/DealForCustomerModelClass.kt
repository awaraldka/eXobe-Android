package com.exobe.modelClass

data class DealForCustomerModelClass(

    val itemValue1: String,
    val itemValue2: String,
    val date: String,
    val images: Int,
    val ExprieTime: String,
    val ExprieTimeIn: String


)

data class cat(
    var name: String? = null,
    var subCat: ArrayList<subCat>? = null
)

data class subCat(
    var name: String? = null,
    var price: String? = null
)
data class service_request(
    var name: String? = null,
    var price: String? = null
)
data class subcategory_service(
    var name: String? = null
)
data class Dealservices_spModelClass(


    val images: Int,
    val name:String


)