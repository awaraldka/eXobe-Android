package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class getprofile_response {
    @SerializedName("result")
    val result: getprofileResult? = null
    @SerializedName("responseMessage")
    val responseMessage: String? = null
    @SerializedName("responseCode")
    val responseCode: Int? = null
}
 class getprofileResult {

     @SerializedName("storeLocation")
     val storeLocation: StoreLocation?=null
     @SerializedName("businessDetails")
     val businessDetails: BusinessDetails?=null
     @SerializedName("businessBankingDetails")
     val businessBankingDetails: BusinessBankingDetails?=null
     @SerializedName("serviceDetails")
     val serviceDetails: ServiceDetails?=null
     @SerializedName("address")
     val address: String?=null
     @SerializedName("otpVerification")
     val otpVerification: Boolean?=null
     @SerializedName("userVerification")
     val userVerification: Boolean?=null
     @SerializedName("profilePic")
     val profilePic: String?=null
     @SerializedName("websiteUrl")
     val websiteUrl: String?=null
     @SerializedName("duration")
     val duration: String?=null
     @SerializedName("userRequestStatus")
     val userRequestStatus: String?=null
     @SerializedName("zipCode")
     val zipCode: String?=null
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
     val noOfUniqueProducts: Int?=null
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
     @SerializedName("status")
     val status: String?=null
     @SerializedName("_id")
     val _id: String?=null
     @SerializedName("countryCode")
     val countryCode: Int?=null
     @SerializedName("email")
     val email: String?=null
     @SerializedName("firstName")
     val firstName: String?=null
     @SerializedName("lastName")
     val lastName: String?=null
     @SerializedName("mobileNumber")
     val mobileNumber: String?=null
     @SerializedName("password")
     val password: String?=null
     @SerializedName("userType")
     val userType: String?=null
     @SerializedName("otp")
     val otp: Int?=null
     @SerializedName("otpExpireTime")
     val otpExpireTime: String?=null
     @SerializedName("createdAt")
     val createdAt: String?=null
     @SerializedName("updatedAt")
     val updatedAt: String?=null
     @SerializedName("__v")
     val __v: Int?=null
 }
 class getprofileStoreLocation {

    @SerializedName("type")
    val type: String?=null
    @SerializedName("coordinates")
    val coordinates: ArrayList<Double>?=null
}
 class BusinessDetails {

    @SerializedName("companyName")
    val companyName: String?=null
    @SerializedName("businessRegNumber")
    val businessRegNumber: String?=null
    @SerializedName("websiteUrl")
    val websiteUrl: String?=null
    @SerializedName("socialMediaAccounts")
    val socialMediaAccounts: String?=null
    @SerializedName("isVatRegistered")
    val isVatRegistered: Boolean?=null
    @SerializedName("vatNumber")
    val vatNumber: String?=null
    @SerializedName("monthlyRevenue")
    val monthlyRevenue: String?=null
}
class BusinessBankingDetails {

    @SerializedName("bankName")
    val bankName: String?=null
    @SerializedName("branchName")
    val branchName: String?=null
    @SerializedName("branchCode")
    val branchCode: String?=null
    @SerializedName("swiftCode")
    val swiftCode: String?=null
    @SerializedName("accountType")
    val accountType: String?=null
    @SerializedName("accountName")
    val accountName: String?=null
    @SerializedName("accountNumber")
    val accountNumber: String?=null
}