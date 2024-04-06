package com.exobe.modelClass

import java.io.Serializable

data class ChooseServicesMyModel (val subServicesName : String, val subServicesId : String,var servicesMyModel : ArrayList<ServicesMyModel>) : Serializable

data class ServicesMyModel (val mainId : String,var id : String, var title : String , var price : Number, var priceTag: String,var actualPrice: String,var discount: String,var isDealActive: Boolean) : Serializable