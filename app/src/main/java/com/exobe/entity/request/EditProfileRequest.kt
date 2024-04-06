package com.exobe.entity.request

import com.google.gson.annotations.SerializedName

class EditProfileRequest {
    @SerializedName("userType") var userType : String?=""
    @SerializedName("firstName") var firstName : String?=""
    @SerializedName("lastName") var lastName : String?=""
    @SerializedName("email") var email : String?=""
    @SerializedName("mobileNumber") var mobileNumber : String?=""
    @SerializedName("zipCode") var zipCode : String?=""
    @SerializedName("addressLine1") var addressLine1 : String?=""
    @SerializedName("addressLine2") var addressLine2 : String?=""
    @SerializedName("country") var country : String?=""
    @SerializedName("state") var state : String?=""
    @SerializedName("city") var city : String?=""
    @SerializedName("countryIsoCode") var countryIsoCode : String?=""
    @SerializedName("stateIsoCode") var stateIsoCode : String?=""
    @SerializedName("profilePic") var profilePic : String?=null
    @SerializedName("countryCode") var countryCode : String?=""
    @SerializedName("storeLocation") var storeLocation : EditProfileStoreLocation?=null
    @SerializedName("campainPrefrences") var campainPrefrences : String = ""
}

class EditProfileStoreLocation{
    @SerializedName("type") var type : String?=""
    @SerializedName("coordinates") var coordinates : ArrayList<Double> =  ArrayList()
}