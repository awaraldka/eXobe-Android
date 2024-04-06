package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class ServiceList_Response {
    @SerializedName("result") val result : ServiceListResult?=null
    @SerializedName("responseMessage") val responseMessage : String?=null
    @SerializedName("responseCode") val responseCode : Int?=null
}

class ServiceListResult {

    @SerializedName("docs")
    val docs: ArrayList<ServiceListDocs>? = null
    @SerializedName("total")
    val total: Int? = null
    @SerializedName("limit")
    val limit: Int? = null
    @SerializedName("page")
    val page: Int? = null
    @SerializedName("pages")
    val pages: Int? = null
}
class ServiceListDocs {

    @SerializedName("serviceLocation")
    val serviceLocation: ServiceListServiceLocation? = null
    @SerializedName("status")
    val status: String? = null
    @SerializedName("_id")
    val _id: String? = null
    @SerializedName("serviceName")
    val serviceName: String? = null
    @SerializedName("serviceImage")
    val serviceImage : List<String>?=null
    @SerializedName("categoryId")
    val categoryId : ServiceListCategoryId?=null
//    @SerializedName("subCategoryDetails")
//    val subCategoryDetails: List<ServiceListSubCategoryDetails>? = null
    @SerializedName("duration")
    val duration: String? = null
    @SerializedName("description")
    val description: String? = null
//    @SerializedName("userId")
//    val userId : UserId?=null
    @SerializedName("createdAt")
    val createdAt: String? = null
    @SerializedName("updatedAt")
    val updatedAt: String? = null
    @SerializedName("__v")
    val __v: Int? = null
}

class ServiceListServiceLocation {

    @SerializedName("type")
    val type: String? = null
    @SerializedName("coordinates")
    val coordinates: List<Double>? = null
}
 class ServiceListSubCategoryDetails {

     @SerializedName("price")
     val price: Number = 0
     @SerializedName("quantity")
     val quantity: Int? = null
     @SerializedName("subCategoryId")
     val subCategoryId: String? = null
 }
 class ServiceListCategoryId {

     @SerializedName("status")
     val status: String?=null
     @SerializedName("_id")
     val _id: String? = null
     @SerializedName("categoryName")
     val categoryName: String? = null
     @SerializedName("categoryImage")
     val categoryImage: String? = null
     @SerializedName("description")
     val description: String? = null
     @SerializedName("createdAt")
     val createdAt: String? = null
     @SerializedName("updatedAt")
     val updatedAt: String? = null
     @SerializedName("__v")
     val __v: Int? = null
 }
class UserId {
    @SerializedName("_id") val _id : String?=null
//    @SerializedName("storeLocation") val storeLocation : StoreLocation?=null
//    @SerializedName("govtDocument") val govtDocument : GovtDocument?=null
//    @SerializedName("socialLink") val socialLink : SocialLink?=null
//    @SerializedName("businessDetails") val businessDetails : BusinessDetails?=null
//    @SerializedName("businessBankingDetails") val businessBankingDetails : BusinessBankingDetails?=null
//    @SerializedName("businessDocumentUpload") val businessDocumentUpload : BusinessDocumentUpload?=null
//    @SerializedName("serviceDetails") val serviceDetails : ServiceDetails?=null
//    @SerializedName("address") val address : String?=null
//    @SerializedName("otpVerification") val otpVerification : Boolean?=null
//    @SerializedName("userVerification") val userVerification : Boolean?=null
//    @SerializedName("profilePic") val profilePic : String?=null
//    @SerializedName("websiteUrl") val websiteUrl : String?=null
//    @SerializedName("duration") val duration : String?=null
//    @SerializedName("userRequestStatus") val userRequestStatus : String?=null
//    @SerializedName("zipCode") val zipCode : Int?=null
//    @SerializedName("eoriNumber") val eoriNumber : Int?=null
//    @SerializedName("additionalDocName") val additionalDocName : String?=null
//    @SerializedName("additionalDocument") val additionalDocument : String?=null
//    @SerializedName("ownerFirstName") val ownerFirstName : String?=null
//    @SerializedName("ownerLastName") val ownerLastName : String?=null
//    @SerializedName("ownerEmail") val ownerEmail : String?=null
//    @SerializedName("noOfUniqueProducts") val noOfUniqueProducts : Int?=null
//    @SerializedName("listOfBrandOrProducts") val listOfBrandOrProducts : List<String>?=null
//    @SerializedName("keepStock") val keepStock : Boolean?=null
//    @SerializedName("isPhysicalStore") val isPhysicalStore : Boolean?=null
//    @SerializedName("preferredSupplierOrWholeSalerId") val preferredSupplierOrWholeSalerId : List<String>?=null
//    @SerializedName("comments") val comments : String?=null
//    @SerializedName("completeProfile") val completeProfile : Boolean?=null
//    @SerializedName("flag") val flag : Int?=null
//    @SerializedName("placeOrderCount") val placeOrderCount : Int?=null
//    @SerializedName("serviceOrderCount") val serviceOrderCount : Int?=null
//    @SerializedName("receiveOrderCount") val receiveOrderCount : Int?=null
//    @SerializedName("status") val status : String?=null
//
//    @SerializedName("userType") val userType : String?=null
//    @SerializedName("firstName") val firstName : String?=null
//    @SerializedName("lastName") val lastName : String?=null
//    @SerializedName("countryCode") val countryCode : String?=null
//    @SerializedName("mobileNumber") val mobileNumber : String?=null
//    @SerializedName("email") val email : String?=null
//    @SerializedName("phoneNumber") val phoneNumber : String?=null
//    @SerializedName("password") val password : String?=null
//    @SerializedName("city") val city : String?=null
//    @SerializedName("state") val state : String?=null
//    @SerializedName("country") val country : String?=null
//    @SerializedName("addressLine1") val addressLine1 : String?=null
//    @SerializedName("addressLine2") val addressLine2 : String?=null
//    @SerializedName("createdAt") val createdAt : String?=null
//    @SerializedName("updatedAt") val updatedAt : String?=null
//    @SerializedName("__v") val __v : Int?=null
//    @SerializedName("userUniqueId") val userUniqueId : String?=null
}
class BusinessDocumentUpload {

    @SerializedName("cirtificationOfIncorporation")
    val cirtificationOfIncorporation: CirtificationOfIncorporation?=null
    @SerializedName("VatRegConfirmationDocs")
    val vatRegConfirmationDocs: VatRegConfirmationDocs?=null
    @SerializedName("directConsentForm")
    val directConsentForm: DirectConsentForm?=null
    @SerializedName("directorId")
    val directorId: DirectorId?=null
    @SerializedName("bankConfirmationLetter")
    val bankConfirmationLetter: BankConfirmationLetter?=null
}
 class CirtificationOfIncorporation {

     @SerializedName("frontImage")
     val frontImage: String? = null
     @SerializedName("backImage")
     val backImage: String? = null
 }
 class VatRegConfirmationDocs {

    @SerializedName("frontImage") val frontImage : String?=null
    @SerializedName("backImage") val backImage : String?=null
}
class DirectConsentForm {

    @SerializedName("frontImage") val frontImage : String?=null
    @SerializedName("backImage") val backImage : String?=null
}
 class DirectorId {

    @SerializedName("frontImage")
    val frontImage: String?=null
    @SerializedName("backImage")
    val backImage: String?=null
}
class BankConfirmationLetter {

    @SerializedName("frontImage") val frontImage : String?=null
    @SerializedName("backImage") val backImage : String?=null
}