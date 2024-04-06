package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class RetailerFillformResponse {
//    @SerializedName("result") val result : RetailerFillformResult?=null
    @SerializedName("responseMessage") val responseMessage : String?=null
    @SerializedName("responseCode") val responseCode : Int?=null

}
class RetailerFillformResult {

//    @SerializedName("storeLocation")
//    val storeLocation: RetailerFillformStoreLocation?=null
    @SerializedName("businessDetails")
    val businessDetails: RetailerFillformBusinessDetails?=null
    @SerializedName("businessBankingDetails")
    val businessBankingDetails: RetailerFillformBusinessBankingDetails?=null
//    @SerializedName("businessDocumentUpload")
//    val businessDocumentUpload: RetailerFillformBusinessDocumentUpload?=null
//    @SerializedName("serviceDetails")
//    val serviceDetails: RetailerFillformServiceDetails?=null
//    @SerializedName("address")
//    val address: String?=null
//    @SerializedName("otpVerification")
//    val otpVerification: Boolean?=null
//    @SerializedName("userVerification")
//    val userVerification: Boolean?=null
//    @SerializedName("profilePic")
//    val profilePic: String?=null
//    @SerializedName("websiteUrl")
//    val websiteUrl: String?=null
//    @SerializedName("duration")
//    val duration: String?=null
//    @SerializedName("userRequestStatus")
//    val userRequestStatus: String?=null
//    @SerializedName("zipCode")
//    val zipCode: String?=null
//    @SerializedName("eoriNumber")
//    val eoriNumber: String?=null
//    @SerializedName("additionalDocName")
//    val additionalDocName: String?=null
//    @SerializedName("additionalDocument")
//    val additionalDocument: String?=null
    @SerializedName("ownerFirstName")
    val ownerFirstName: String?=null
    @SerializedName("ownerLastName")
    val ownerLastName: String?=null
    @SerializedName("ownerEmail")
    val ownerEmail: String?=null
//    @SerializedName("noOfUniqueProducts")
//    val noOfUniqueProducts: Int?=null
//    @SerializedName("listOfBrandOrProducts")
//    val listOfBrandOrProducts: List<String>?=null
//    @SerializedName("keepStock")
//    val keepStock: Boolean?=null
//    @SerializedName("isPhysicalStore")
//    val isPhysicalStore: Boolean?=null
//    @SerializedName("preferredSupplierOrWholeSalerId")
//    val preferredSupplierOrWholeSalerId: List<String>?=null
//    @SerializedName("comments")
//    val comments: String?=null
//    @SerializedName("completeProfile")
//    val completeProfile: Boolean?=null
//    @SerializedName("flag")
//    val flag: Int?=null
//    @SerializedName("placeOrderCount")
//    val placeOrderCount: Int?=null
//    @SerializedName("serviceOrderCount")
//    val serviceOrderCount: Int?=null
//    @SerializedName("receiveOrderCount")
//    val receiveOrderCount: Int?=null
//    @SerializedName("status")
//    val status: String?=null
//    @SerializedName("_id")
//    val _id: String?=null
//    @SerializedName("userType")
//    val userType: String?=null
//    @SerializedName("firstName")
//    val firstName: String?=null
//    @SerializedName("lastName")
//    val lastName: String?=null
//    @SerializedName("countryCode")
//    val countryCode: String?=null
//    @SerializedName("mobileNumber")
//    val mobileNumber: String?=null
//    @SerializedName("email")
//    val email: String?=null
//    @SerializedName("phoneNumber")
//    val phoneNumber: String?=null
//    @SerializedName("password")
//    val password: String?=null
//    @SerializedName("city")
//    val city: String?=null
//    @SerializedName("state")
//    val state: String?=null
//    @SerializedName("country")
//    val country: String?=null
//    @SerializedName("addressLine1")
//    val addressLine1: String?=null
//    @SerializedName("addressLine2")
//    val addressLine2: String?=null
//    @SerializedName("createdAt")
//    val createdAt: String?=null
//    @SerializedName("updatedAt")
//    val updatedAt: String?=null
//    @SerializedName("__v")
//    val __v: Int?=null
}
//class RetailerFillformStoreLocation {
//
//    @SerializedName("type")
//    val type: String?=null
//    @SerializedName("coordinates")
//    val coordinates: ArrayList<Double>?=null
//}
class RetailerFillformBusinessDetails {

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
class RetailerFillformBusinessBankingDetails {

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
class RetailerFillformBusinessDocumentUpload {
//
//    @SerializedName("cirtificationOfIncorporation")
//    val cirtificationOfIncorporation: RetailerFillformCirtificationOfIncorporation?=null
//    @SerializedName("VatRegConfirmationDocs")
//    val vatRegConfirmationDocs: RetailerFillformVatRegConfirmationDocs?=null
//    @SerializedName("directConsentForm")
//    val directConsentForm: RetailerFillformDirectConsentForm?=null
//    @SerializedName("directorId")
//    val directorId: RetailerFillformDirectorId?=null
//    @SerializedName("bankConfirmationLetter")
//    val bankConfirmationLetter: RetailerFillformBankConfirmationLetter?=null
}
class RetailerFillformCirtificationOfIncorporation {

//    @SerializedName("frontImage")
//    val frontImage: String?=null
//    @SerializedName("backImage")
//    val backImage: String?=null
}
class RetailerFillformDirectConsentForm {

//    @SerializedName("frontImage")
//    val frontImage: String?=null
//    @SerializedName("backImage")
//    val backImage: String?=null
}
class RetailerFillformDirectorId {

//    @SerializedName("frontImage")
//    val frontImage: String?=null
//    @SerializedName("backImage")
//    val backImage: String?=null
}
class RetailerFillformServiceDetails {

//    @SerializedName("noOfUniqueService")
//    var noOfUniqueService: Int?=null
//    @SerializedName("preferredSupplierOrWholeSalerId")
//    var preferredSupplierOrWholeSalerId: List<String>?=null
//    @SerializedName("comments")
//    var comments: String?=null
//    @SerializedName("listOfServices")
//    var listOfServices: List<String>?=null
}

class RetailerFillformVatRegConfirmationDocs {
//
//    @SerializedName("frontImage")
//    val frontImage: String?=null
//    @SerializedName("backImage")
//    val backImage: String?=null
}
class RetailerFillformBankConfirmationLetter {
//
//    @SerializedName("frontImage")
//    val frontImage: String?=null
//    @SerializedName("backImage")
//    val backImage: String?=null
}