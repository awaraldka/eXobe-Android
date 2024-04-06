package com.exobe.entity.request

import com.google.gson.annotations.SerializedName

class EditProfile_Request {
    @SerializedName("userType") var userType : String?=null
    @SerializedName("firstName") var firstName : String?=null
    @SerializedName("lastName") var lastName : String?=null
    @SerializedName("countryCode") var countryCode : String?=null
    @SerializedName("mobileNumber") var mobileNumber : String?=null
    @SerializedName("email") var email : String?=null
    @SerializedName("address") var address : String?=null
    @SerializedName("phoneNumber") var phoneNumber : Int?=null
    @SerializedName("storeAddress") var storeAddress : String?=null
    @SerializedName("storeName") var storeName : String?=null
    @SerializedName("serviceName") var serviceName : String?=null
    @SerializedName("serviceImage") var serviceImage : String?=null
    @SerializedName("description") var description : String?=null
    @SerializedName("categoryId") var categoryId : String?=null
    @SerializedName("websiteUrl") var websiteUrl : String?=null
    @SerializedName("deviceType") var deviceType : String?=null
    @SerializedName("password") var password : String?=null
    @SerializedName("govtDocument") var govtDocument : EditProfile_GovtDocument?=null
    @SerializedName("profilePic") var profilePic : String?=null
    @SerializedName("city") var city : String?=null
    @SerializedName("state") var state : String?=null
    @SerializedName("companyName") var companyName : String?=null
    @SerializedName("vatNumber") var vatNumber : String?=null
    @SerializedName("eoriNumber") var eoriNumber : String?=null
    @SerializedName("country") var country : String?=null
    @SerializedName("zipCode") var zipCode : String?=null
    @SerializedName("addressLine1") var addressLine1 : String?=null
    @SerializedName("addressLine2") var addressLine2 : String?=null
    @SerializedName("DOB") var DOB : String?=null
    @SerializedName("additionalDocument") var additionalDocument : String?=null
}

 class EditProfile_GovtDocument (

    @SerializedName("frontImage") val frontImage : String,
    @SerializedName("backImage") val backImage : String
)