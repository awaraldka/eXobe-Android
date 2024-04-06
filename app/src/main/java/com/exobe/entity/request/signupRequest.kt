package com.exobe.entity.request

import com.google.gson.annotations.SerializedName

class SignupRequest {

    @SerializedName("userType")
    var userType: String?=null
    @SerializedName("firstName")
    var firstName: String?=null
    @SerializedName("lastName")
    var lastName: String?=null
    @SerializedName("countryCode")
    var countryCode: String?=null
    @SerializedName("mobileNumber")
    var mobileNumber: String?=null
    @SerializedName("address")
    var address: String?=null
    @SerializedName("email")
    var email: String?=null
    @SerializedName("storeAddress")
    var storeAddress: String?=null
    @SerializedName("storeName")
    var storeName: String?=null
    @SerializedName("storeLocation")
    var storeLocation: StoreLocation?=null
    @SerializedName("storeContactNo")
    var storeContactNo: String?=null
    @SerializedName("storeBrand")
    var storeBrand: String?=null
    @SerializedName("socialLink")
    var socialLink: SocialLink?=null
    @SerializedName("serviceName")
    var serviceName: String?=null
    @SerializedName("serviceImage")
    var serviceImage: String?=null
    @SerializedName("description")
    var description: String?=null
    @SerializedName("categoryId")
    var categoryId: String?=null
    @SerializedName("phoneNumber")
    var phoneNumber: String?=null
    @SerializedName("subCategoryDetails")
    var subCategoryDetails: List<SubCategoryDetails>?=null
    @SerializedName("websiteUrl")
    var websiteUrl: String?=null
    @SerializedName("deviceType")
    var deviceType: String?=null
    @SerializedName("deviceToken")
    var deviceToken: String?=null
    @SerializedName("password")
    var password: String?=null
    @SerializedName("confirmPassword")
    var confirmPassword: String?=null
    @SerializedName("govtDocument")
    var govtDocument: GovtDocument?=null
    @SerializedName("profilePic")
    var profilePic: String?=null
    @SerializedName("city")
    var city: String?=null
    @SerializedName("state")
    var state: String?=null
    @SerializedName("companyName")
    var companyName: String?=null
    @SerializedName("vatNumber")
    var vatNumber: String?=null
    @SerializedName("eoriNumber")
    var eoriNumber: String?=null
    @SerializedName("zipCode")
    var zipCode: String?=null
    @SerializedName("addressLine1")
    var addressLine1: String?=null
    @SerializedName("addressLine2")
    var addressLine2: String?=null
    @SerializedName("country")
    var country: String?=null
    @SerializedName("countryIsoCode")
    var countryIsoCode: String? = null
    @SerializedName("stateIsoCode")
    var stateIsoCode: String? = null
    @SerializedName("campainPrefrences")
    var campainPrefrences: String? = null
}
class SocialLink {

    @SerializedName("faceBook")
    var faceBook: String? = null

    @SerializedName("linkedIn")
    var linkedIn: String? = null
    @SerializedName("twitter")
    var twitter: String?=null
    @SerializedName("instagram")
    var instagram: String?=null
}
 class StoreLocation {

     @SerializedName("type")
     var type: String?=null
     @SerializedName("coordinates")
     var coordinates: ArrayList<Double>?=null
 }

 class SubCategoryDetails {

    @SerializedName("price")
    var price: Int?=null
    @SerializedName("quantity")
    var quantity: Int?=null
    @SerializedName("subCategoryId")
    var subCategoryId: String?=null
}



class GovtDocument {
    @SerializedName("frontImage")
    var frontImage: String?=null
    @SerializedName("backImage")
    var backImage: String?=null
}


