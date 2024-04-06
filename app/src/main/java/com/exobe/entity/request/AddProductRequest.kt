package com.exobe.entity.request

import com.exobe.modelClass.ProductPackModel
import com.exobe.entity.response.PriceSizeDetails
import com.google.gson.annotations.SerializedName

class AddProductRequest {
    @SerializedName("productId") var productId : String?=null
    @SerializedName("productName") var productName : String?=null
    @SerializedName("thumbnail") var thumbnail : String?=null
    @SerializedName("productImage") var productImage : ArrayList<String>?=null
    @SerializedName("priceSizeDetails") var priceSizeDetails : ArrayList<ProductPackModel>?=null
    @SerializedName("price") var price : Number=0
    @SerializedName("unit") var unit : String?=null
    @SerializedName("brand") var brand : String?=null
    @SerializedName("description") var description : String?=null
    @SerializedName("categoryId") var categoryId : String?=null
    @SerializedName("subCategoryId") var subCategoryId : String?=null
    @SerializedName("expectedDeliveryDays") var expectedDeliveryDays : String?=null
    @SerializedName("quantity") var quantity : String?=null
    @SerializedName("productFor") var productFor : String?=null
    @SerializedName("productReferenceId") var productReferenceId : String?=null
}

class EditProductRequest {
    @SerializedName("productId") var productId : String?=null
    @SerializedName("productName") var productName : String?=null
    @SerializedName("thumbnail") var thumbnail : String?=null
    @SerializedName("productImage") var productImage : ArrayList<String>?=null
    @SerializedName("priceSizeDetails") var priceSizeDetails : ArrayList<PriceSizeDetails>?=null
    @SerializedName("price") var price : Number=0
    @SerializedName("unit") var unit : String?=null
    @SerializedName("brand") var brand : String?=null
    @SerializedName("description") var description : String?=null
    @SerializedName("categoryId") var categoryId : String?=null
    @SerializedName("subCategoryId") var subCategoryId : String?=null
    @SerializedName("expectedDeliveryDays") var expectedDeliveryDays : String?=null
    @SerializedName("quantity") var quantity : String?=null
    @SerializedName("productFor") var productFor : String?=null
    @SerializedName("productReferenceId") var productReferenceId : String?=null
}

class Suggestions{
    @SerializedName("categoryId") var categoryId: ArrayList<String> = ArrayList<String>()
}
