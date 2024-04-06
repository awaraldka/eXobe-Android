package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class FeeListResponse {
    @SerializedName("result") val result: ArrayList<FeeListResult> = arrayListOf()
    @SerializedName("responseMessage") val responseMessage: String = ""
    @SerializedName("responseCode") val responseCode: Int = 0
}

class FeeListResult{
    @SerializedName("isDefault") var isDefault:Boolean =  false
    @SerializedName("_id") var id:String = ""
    @SerializedName("deliveryType") var deliveryType:String = ""
    @SerializedName("feeName") var feeName:String = ""
    @SerializedName("userType") var userType:String = ""
    @SerializedName("userId") var userId:String = ""
    @SerializedName("pickupFee") var pickupFee:Number = 0


    @SerializedName("deliveryFee") var deliveryFee:Number = 0
    @SerializedName("standardFee") var standardFee:Number = 0


    @SerializedName("storageFee") val storageFee: FeeListStorageFee = FeeListStorageFee()
    @SerializedName("sizeType") val sizeType: FeeListSizeType = FeeListSizeType()


}
class FeeListStorageFee {
    @SerializedName("daily")
    val daily: Number = 0
    @SerializedName("weekly")
    val weekly: Number = 0
    @SerializedName("monthly")
    val monthly: Number= 0
    @SerializedName("quarterly")
    val quarterly: Number= 0

}


class FeeListSizeType{
    @SerializedName("minimumSize") val minimumSize: String = ""
    @SerializedName("maximumSize") val maximumSize: String = ""
}
