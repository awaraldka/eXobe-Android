package com.exobe.entity.response.serviceTab

import com.google.gson.annotations.SerializedName

class ServiceDetailsResponse {
    @SerializedName("result") val result : ArrayList<ServiceDetailResult>?= null
    @SerializedName("responseMessage") val responseMessage : String?= ""
    @SerializedName("responseCode") val responseCode : Int?= 0
}

class ServiceDetailResult{


    @SerializedName("serviceLocation") val serviceLocation : ServiceDetailserviceLocation?= ServiceDetailserviceLocation()
    @SerializedName("serviceImage") val serviceImage : ArrayList<String>?= null
    @SerializedName("slots") val slots : ArrayList<String>?= null
    @SerializedName("status") val status : String?=""
    @SerializedName("_id") val _id : String?=""
    @SerializedName("categoryId") val categoryId : ServiceDetailCategoryId?= ServiceDetailCategoryId()
    @SerializedName("subCategoryDetails") val subCategoryDetails : ArrayList<ServiceDetailSubCategoryDetails>?= null
    @SerializedName("userId") val userId : ServiceDetailUserId?= ServiceDetailUserId()
    @SerializedName("__v") val __v : Int?= 0
    @SerializedName("createdAt") val createdAt : String?= ""
    @SerializedName("updatedAt") val updatedAt : String?=""

}

class ServiceDetailserviceLocation{
    @SerializedName("type") val type : String?= ""
    @SerializedName("coordinates") val coordinates : ArrayList<Double>?=null
}

class ServiceDetailCategoryId{
    @SerializedName("status") val status : String?=""
    @SerializedName("_id") val _id : String?=""
    @SerializedName("categoryName") val categoryName : String?=""
    @SerializedName("categoryImage") val categoryImage : String?=""
    @SerializedName("description") val description : String?=""
    @SerializedName("createdAt") val createdAt : String?=""
    @SerializedName("updatedAt") val updatedAt : String?=""
    @SerializedName("__v") val __v : Int?=0

}

class ServiceDetailSubCategoryDetails{
    @SerializedName("price") val price : Number?=0
    @SerializedName("subCategoryId") val subCategoryId : subcategory?= subcategory()
}


class subcategory{
    @SerializedName("status") var status: String?= ""
    @SerializedName("_id") var _id: String?= ""
    @SerializedName("categoryId") var categoryId: String?= ""
    @SerializedName("subCategoryName") var subCategoryName: String?= ""
    @SerializedName("__v") var __v: Int?= 0
    @SerializedName("createdAt") var createdAt: String?= ""
    @SerializedName("updatedAt") var updatedAt: String?= ""
}

class ServiceDetailUserId{
    @SerializedName("storeLocation") val storeLocation : ServiceStoreLocation?= ServiceStoreLocation()
    @SerializedName("govtDocument") val govtDocument : ServiceGovtDocument?= ServiceGovtDocument()
    @SerializedName("socialLink") val socialLink : ServiceSocialLink?= ServiceSocialLink()
    @SerializedName("businessDetails") val businessDetails : ServiceBusinessDetails?= ServiceBusinessDetails()
    @SerializedName("businessBankingDetails") val businessBankingDetails : ServiceBusinessBankingDetails?= ServiceBusinessBankingDetails()
    @SerializedName("businessDocumentUpload") val businessDocumentUpload : ServiceBusinessDocumentUpload?= ServiceBusinessDocumentUpload()
    @SerializedName("serviceDetails") val serviceDetails : A_ServiceDetails?= A_ServiceDetails()
    @SerializedName("address") val address : String?= ""
    @SerializedName("otpVerification") val otpVerification : Boolean?= null
    @SerializedName("userVerification") val userVerification : Boolean?= null
    @SerializedName("profilePic") val profilePic : String?= ""
    @SerializedName("websiteUrl") val websiteUrl : String?=""
    @SerializedName("duration") val duration : String?=""
    @SerializedName("userRequestStatus") val userRequestStatus : String?=""
    @SerializedName("zipCode") val zipCode : Int?=0
    @SerializedName("eoriNumber") val eoriNumber : Int?=0
    @SerializedName("additionalDocName") val additionalDocName : String?=""
    @SerializedName("additionalDocument") val additionalDocument : String?=""
    @SerializedName("ownerFirstName") val ownerFirstName : String?=""
    @SerializedName("ownerLastName") val ownerLastName : String?=""
    @SerializedName("ownerEmail") val ownerEmail : String?=""
    @SerializedName("noOfUniqueProducts") val noOfUniqueProducts : Int?=0
    @SerializedName("listOfBrandOrProducts") val listOfBrandOrProducts : List<String>?=null
    @SerializedName("keepStock") val keepStock : Boolean?= null
    @SerializedName("isPhysicalStore") val isPhysicalStore : Boolean?= null
    @SerializedName("preferredSupplierOrWholeSalerId") val preferredSupplierOrWholeSalerId : List<String>?= null
    @SerializedName("comments") val comments : String?= ""
    @SerializedName("completeProfile") val completeProfile : Boolean?=null
    @SerializedName("flag") val flag : Int?= null
    @SerializedName("placeOrderCount") val placeOrderCount : Int?=0
    @SerializedName("serviceOrderCount") val serviceOrderCount : Int?= 0
    @SerializedName("receiveOrderCount") val receiveOrderCount : Int?=0
    @SerializedName("status") val status : String?=""
    @SerializedName("_id") val _id : String?= ""
    @SerializedName("userType") val userType : String?=""
    @SerializedName("firstName") val firstName : String?=""
    @SerializedName("lastName") val lastName : String?=""
    @SerializedName("countryCode") val countryCode :String?=""
    @SerializedName("mobileNumber") val mobileNumber : String?=""
    @SerializedName("email") val email : String?=""
    @SerializedName("phoneNumber") val phoneNumber : String?=""
    @SerializedName("password") val password : String?= ""
    @SerializedName("city") val city : String?=""
    @SerializedName("state") val state : String?=""
    @SerializedName("country") val country : String?=""
    @SerializedName("addressLine1") val addressLine1 : String?=""
    @SerializedName("addressLine2") val addressLine2 : String?=""
    @SerializedName("createdAt") val createdAt : String?=""
    @SerializedName("updatedAt") val updatedAt : String?=""
    @SerializedName("__v") val __v : Int?=0
    @SerializedName("userUniqueId") val userUniqueId : String?=""
    @SerializedName("isReset") val isReset : Boolean?= null

}
class ServiceStoreLocation{
    @SerializedName("type") val type : String?=""
    @SerializedName("coordinates") val coordinates : ArrayList<Double>?= null
}
class ServiceGovtDocument{
    @SerializedName("frontImage") val frontImage : String?= ""
    @SerializedName("backImage") val backImage : String?= ""
}
class ServiceSocialLink{
    @SerializedName("faceBook") val faceBook : String?=""
    @SerializedName("linkedIn") val linkedIn : String?= ""
    @SerializedName("twitter") val twitter : String?=""
    @SerializedName("instagram") val instagram : String?=""

}
class ServiceBusinessDetails{
    @SerializedName("companyName") val companyName : String?=""
    @SerializedName("businessRegNumber") val businessRegNumber : String?= ""
    @SerializedName("websiteUrl") val websiteUrl : String?= ""
    @SerializedName("socialMediaAccounts") val socialMediaAccounts : String?= ""
    @SerializedName("isVatRegistered") val isVatRegistered : Boolean?= null
    @SerializedName("vatNumber") val vatNumber : String?=""
    @SerializedName("monthlyRevenue") val monthlyRevenue : String?=""
}


class ServiceBusinessBankingDetails{
    @SerializedName("bankName") val bankName : String?= ""
    @SerializedName("branchName") val branchName : String?= ""
    @SerializedName("branchCode") val branchCode : String?= ""
    @SerializedName("swiftCode") val swiftCode : String?= ""
    @SerializedName("accountType") val accountType : String?=""
    @SerializedName("accountName") val accountName : String?= ""
    @SerializedName("accountNumber") val accountNumber : String?= ""

}
class ServiceBusinessDocumentUpload{

    @SerializedName("cirtificationOfIncorporation") val cirtificationOfIncorporation : SrviceCirtificationOfIncorporation?= SrviceCirtificationOfIncorporation()
    @SerializedName("VatRegConfirmationDocs") val vatRegConfirmationDocs : SrviceVatRegConfirmationDocs?= SrviceVatRegConfirmationDocs()
    @SerializedName("directConsentForm") val directConsentForm : SrviceDirectConsentForm ?= SrviceDirectConsentForm()
    @SerializedName("directorId") val directorId : SrviceDirectorId ?= SrviceDirectorId()
    @SerializedName("bankConfirmationLetter") val bankConfirmationLetter : SrviceBankConfirmationLetter ?= SrviceBankConfirmationLetter()

}
class A_ServiceDetails{
    @SerializedName("noOfUniqueService") val noOfUniqueService : Int?= 0
    @SerializedName("preferredSupplierOrWholeSalerId") val preferredSupplierOrWholeSalerId : List<String>?= null
    @SerializedName("comments") val comments : String?= ""
    @SerializedName("listOfServices") val listOfServices : List<ServiceListOfServices>?= null
}

class SrviceCirtificationOfIncorporation{
    @SerializedName("frontImage") val frontImage : String?= ""
    @SerializedName("backImage") val backImage : String?=""
}
class SrviceVatRegConfirmationDocs{
    @SerializedName("frontImage") val frontImage : String?= ""
    @SerializedName("backImage") val backImage : String?= ""
}
class SrviceDirectConsentForm{
    @SerializedName("frontImage") val frontImage : String?=""
    @SerializedName("backImage") val backImage : String?=""
}
class SrviceDirectorId{
    @SerializedName("frontImage") val frontImage : String?=""
    @SerializedName("backImage") val backImage : String?=""
}
class SrviceBankConfirmationLetter{
    @SerializedName("frontImage") val frontImage : String?=""
    @SerializedName("backImage") val backImage : String?= ""
}
class ServiceListOfServices{
    @SerializedName("subCategoryDetails") val subCategoryDetails : ServiceSubCategoryDetails?= ServiceSubCategoryDetails()
    @SerializedName("_id") val _id : String?= ""
    @SerializedName("categoryId") val categoryId : String?= ""
}

class ServiceSubCategoryDetails{
    @SerializedName("price") val price : Number?=0
    @SerializedName("subCategoryId") val subCategoryId : String?= ""
}
