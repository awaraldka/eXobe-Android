package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class SignupResponse {
     @SerializedName("result")
     val result: Result?=null
     @SerializedName("responseMessage")
     val responseMessage: String?=null
     @SerializedName("responseCode")
     val responseCode: Int?=null
 }
class Result {

     @SerializedName("storeLocation")
     val storeLocation: StoreLocation?=null
     @SerializedName("city")
     val city: String?=null
     @SerializedName("state")
     val state: String?=null
     @SerializedName("isReset")
     val isReset: Boolean?=null
     @SerializedName("otpVerification")
     val otpVerification: Boolean?=null
     @SerializedName("userVerification")
     val userVerification: Boolean?=null
     @SerializedName("userRequestStatus")
     val userRequestStatus: String?=null
     @SerializedName("status")
     val status: String?=null
     @SerializedName("_id")
     val _id: String?=null
     @SerializedName("userType")
     val userType: String?=null
     @SerializedName("firstName")
     val firstName: String?=null
     @SerializedName("lastName")
     val lastName: String?=null
     @SerializedName("countryCode")
     val countryCode: String?=null
     @SerializedName("mobileNumber")
     val mobileNumber: String?=null
     @SerializedName("email")
     val email: String?=null
     @SerializedName("socialLink")
     val socialLink: SocialLink?=null
     @SerializedName("websiteUrl")
     val websiteUrl: String?=null
     @SerializedName("password")
     val password: String?=null
     @SerializedName("govtDocument")
     val govtDocument: GovtDocument?=null
     @SerializedName("profilePic")
     val profilePic: String?=null
     @SerializedName("companyName")
     val companyName: String?=null
     @SerializedName("zipCode")
     val zipCode: String?=null
     @SerializedName("addressLine1")
     val addressLine1: String?=null
     @SerializedName("addressLine2")
     val addressLine2: String?=null
     @SerializedName("otp")
     val otp: String?=null
     @SerializedName("otpExpireTime")
     val otpExpireTime: String?=null
     @SerializedName("subCategoryDetails")
     val subCategoryDetails: List<String>?=null
     @SerializedName("createdAt")
     val createdAt: String?=null
     @SerializedName("updatedAt")
     val updatedAt: String?=null
     @SerializedName("__v")
     val __v: Int?=null
    @SerializedName("businessDetails")
    val businessDetails: RetailerRegisterBusinessDetails?=null
    @SerializedName("businessBankingDetails")
    val businessBankingDetails: RetailerRegisterBusinessBankingDetails?=null
    @SerializedName("serviceDetails")
    val serviceDetails: RetailerRegisterServiceDetails?=null
    @SerializedName("address")
    val address: String?=null

    @SerializedName("duration")
    val duration: String?=null
    @SerializedName("eoriNumber")
    val eoriNumber: String?=null
    @SerializedName("additionalDocName")
    val additionalDocName: String?=null
    @SerializedName("additionalDocument")
    val additionalDocument: String?=null
    @SerializedName("ownerFirstName")
    val ownerFirstName: String?=null
    @SerializedName("ownerLastName")
    val ownerLastName: String?=null
    @SerializedName("ownerEmail")
    val ownerEmail: String?=null
    @SerializedName("noOfUniqueProducts")
    val noOfUniqueProducts: Number?=null
    @SerializedName("listOfBrandOrProducts")
    val listOfBrandOrProducts: List<String>?=null
    @SerializedName("keepStock")
    val keepStock: Boolean?=null
    @SerializedName("isPhysicalStore")
    val isPhysicalStore: Boolean?=null
    @SerializedName("preferredSupplierOrWholeSalerId")
    val preferredSupplierOrWholeSalerId: List<String>?=null
    @SerializedName("comments")
    val comments: String?=null
    @SerializedName("completeProfile")
    val completeProfile: Boolean?=null
    @SerializedName("flag")
    val flag: Int?=null
    @SerializedName("placeOrderCount")
    val placeOrderCount: Int?=null
    @SerializedName("serviceOrderCount")
    val serviceOrderCount: Int?=null
    @SerializedName("receiveOrderCount")
    val receiveOrderCount: Int?=null

    @SerializedName("phoneNumber")
    val phoneNumber: String?=null

    @SerializedName("country")
    val country: String?=null

 }
class RetailerRegisterBusinessBankingDetails {

    @SerializedName("bankName") val bankName : String?=null
    @SerializedName("branchName") val branchName : String?=null
    @SerializedName("branchCode") val branchCode : String?=null
    @SerializedName("swiftCode") val swiftCode : String?=null
    @SerializedName("accountType") val accountType : String?=null
    @SerializedName("accountName") val accountName : String?=null
    @SerializedName("accountNumber") val accountNumber : String?=null
}
class SocialLink {

    @SerializedName("faceBook")
    val faceBook: String?=null
    @SerializedName("linkedIn")
    val linkedIn: String?=null
    @SerializedName("twitter")
    val twitter: String?=null
    @SerializedName("instagram")
    val instagram: String?=null
}
class StoreLocation {

    @SerializedName("type")
    val type: String?=null
    @SerializedName("coordinates")
    val coordinates: List<Double>?=null
}
class RetailerRegisterBusinessDetails {

    @SerializedName("companyName") val companyName : String?=null
    @SerializedName("businessRegNumber") val businessRegNumber : String?=null
    @SerializedName("websiteUrl") val websiteUrl : String?=null
    @SerializedName("socialMediaAccounts") val socialMediaAccounts : String?=null
    @SerializedName("isVatRegistered") val isVatRegistered : Boolean?=null
    @SerializedName("vatNumber") val vatNumber : String?=null
    @SerializedName("monthlyRevenue") val monthlyRevenue : String?=null
}
 class GovtDocument {

    @SerializedName("frontImage")
    val frontImage: String?=null
    @SerializedName("backImage")
    val backImage: String?=null
}

class RetailerRegisterServiceDetails {

    @SerializedName("noOfUniqueService")
    val noOfUniqueService: Int?=null
    @SerializedName("preferredSupplierOrWholeSalerId")
    val preferredSupplierOrWholeSalerId: List<String>?=null
    @SerializedName("comments")
    val comments: String?=null
    @SerializedName("listOfServices")
    val listOfServices: List<String>?=null
}

