package com.exobe.modelClass

import com.google.gson.annotations.SerializedName

class CartItems {
    @SerializedName("product") var product :ArrayList<MyProduct> = ArrayList<MyProduct>()
    @SerializedName("TotalPrice") var TotalPrice :String = ""
}

class MyProduct{
    @SerializedName("id") var id :String = ""
    @SerializedName("quantity") var quantity :String = ""
}