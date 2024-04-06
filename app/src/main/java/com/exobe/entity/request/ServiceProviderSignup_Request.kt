package com.exobe.entity.request

import com.google.gson.annotations.SerializedName

class ServiceProviderSignup_Request {
    @SerializedName("userType") var userType : String?=null
    @SerializedName("firstName") var firstName : String?=null
    @SerializedName("lastName") var lastName : String?=null
    @SerializedName("countryCode") var countryCode : String?=null
    @SerializedName("mobileNumber") var mobileNumber : String?=null
    @SerializedName("email") var email : String?=null
    @SerializedName("phoneNumber") var phoneNumber : String?=null
    @SerializedName("password") var password : String?=null
    @SerializedName("profilePic") var profilePic : String?=null
    @SerializedName("city") var city : String?=null
    @SerializedName("storeLocation")
    var storeLocation : ServiceProvider_StoreLocation?=null
    @SerializedName("state") var state : String?=null
    @SerializedName("country") var country : String?=null
    @SerializedName("zipCode") var zipCode : String?=null
    @SerializedName("address") var address : String?=null
    @SerializedName("addressLine1") var addressLine1 : String?=null
    @SerializedName("addressLine2") var addressLine2 : String?=null
    @SerializedName("deviceToken") var deviceToken : String?=null
    @SerializedName("deviceType") var deviceType : String?=null
    @SerializedName("countryIsoCode")
    var countryIsoCode: String? = null
    @SerializedName("stateIsoCode")
    var stateIsoCode: String? = null
    @SerializedName("pickupFeeArray")
    var pickupFeeArray: ArrayList<PickupFeeData> =  arrayListOf()
}

class ServiceProvider_StoreLocation {

    @SerializedName("type")
    var type: String?=null
    @SerializedName("coordinates")
    var coordinates: ArrayList<Double>? = null
}


class PickupFeeData(
    @SerializedName("deliveryType") var deliveryType:String = "",
    @SerializedName("feeName") var feeName:String = "",
    @SerializedName("pickupFee") var pickupFee:String = "",
    @SerializedName("dailyFee") var dailyFee:String = "",
    @SerializedName("weeklyFee") var weeklyFee:String = "",
    @SerializedName("monthlyFee") var monthlyFee:String = "",
    @SerializedName("quarterlyFee") var quarterlyFee:String = "",
    @SerializedName("deliveryFee") var deliveryFee:String = "",
    @SerializedName("standardFee") var standardFee:String = "",
    @SerializedName("type") var type:String = "",
    @SerializedName("sizeType") val sizeType: SizeTypeRequest = SizeTypeRequest(),
    )

class SizeTypeRequest{
    @SerializedName("minimumSize")
    var minimumSize: String=""
    @SerializedName("maximumSize")
    var maximumSize: String = ""
}