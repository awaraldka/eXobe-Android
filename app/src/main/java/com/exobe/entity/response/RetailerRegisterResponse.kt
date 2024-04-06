package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class RetailerRegisterResponse {

    @SerializedName("result") val result : RetailerRegisterResult?=null
    @SerializedName("responseMessage") val responseMessage : String?=null
    @SerializedName("responseCode") val responseCode : Int?=null
}
class RetailerRegisterResult {

    @SerializedName("businessDetails")
    val businessDetails: RetailerRegisterBusinessDetails?=null
    @SerializedName("businessBankingDetails")
    val businessBankingDetails: RetailerRegisterBusinessBankingDetails?=null
    @SerializedName("serviceDetails")
    val serviceDetails: RetailerRegisterServiceDetails?=null
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
    val zipCode: Int?=null
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
    val countryCode: Int?=null
    @SerializedName("mobileNumber")
    val mobileNumber: Int?=null
    @SerializedName("email")
    val email: String?=null
    @SerializedName("phoneNumber")
    val phoneNumber: String?=null
    @SerializedName("password")
    val password: String?=null
    @SerializedName("city")
    val city: String?=null
    @SerializedName("country")
    val country: String?=null
    @SerializedName("addressLine1")
    val addressLine1: String?=null
    @SerializedName("addressLine2")
    val addressLine2: String?=null
    @SerializedName("createdAt")
    val createdAt: String?=null
    @SerializedName("updatedAt")
    val updatedAt: String?=null
    @SerializedName("__v")
    val __v: Int?=null
}


