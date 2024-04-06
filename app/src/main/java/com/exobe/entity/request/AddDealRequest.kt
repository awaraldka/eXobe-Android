package com.exobe.entity.request

import com.google.gson.annotations.SerializedName


class AddDealRequest {

    @SerializedName("dealName"      ) var dealName      : String? = ""
    //    @SerializedName("dealImage"     ) var dealImage     : String? = ""
    @SerializedName("dealPrice"     ) var dealPrice     : Number    = 0
    @SerializedName("description"   ) var description   : String? = ""
    @SerializedName("thumbnail") var thumbnail : String?=""
    @SerializedName("dealImage") var dealImage : ArrayList<String> = ArrayList()
    @SerializedName("productId"     ) var productId     : String? = ""
    @SerializedName("dealsFor"      ) var dealsFor      : String? = ""
//    @SerializedName("quantity"      ) var quantity      : Int?    = 0
    @SerializedName("dealDiscount"  ) var dealDiscount  : Int?    = 0
    @SerializedName("dealStartTime" ) var dealStartTime : String? = ""
    @SerializedName("dealEndTime"   ) var dealEndTime   : String? = ""
}

class ServicecategoryRequst{
    @SerializedName("categoryId") var categoryId   : String = ""
//    @SerializedName("serviceDetailsArray") var serviceDetailsArray : ArrayList<serviceDetailsArray> =  ArrayList()

}
class EditDealRequest {

    @SerializedName("dealId"      ) var dealId      : String? = ""
    @SerializedName("dealName"      ) var dealName      : String? = ""
    @SerializedName("thumbnail") var thumbnail : String?=""
    @SerializedName("dealPrice"     ) var dealPrice     : Number    = 0
    @SerializedName("description"   ) var description   : String? = ""
    @SerializedName("productId"     ) var productId     : ArrayList<String>? = ArrayList()
    @SerializedName("dealImage"     ) var dealImage     : ArrayList<String>? = ArrayList()
    @SerializedName("dealsFor"      ) var dealsFor      : String? = ""
    @SerializedName("dealDiscount"  ) var dealDiscount  : Int?    = 0
    @SerializedName("dealStartTime" ) var dealStartTime : String? = ""
    @SerializedName("dealEndTime"   ) var dealEndTime   : String? = ""
}
