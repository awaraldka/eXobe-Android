package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class Home_response {


    @SerializedName("result")
    val Homeresult: Homeresult?=null
    @SerializedName("responseMessage")
    val responseMessage: String?=null
    @SerializedName("responseCode")
    val responseCode: Int?=null

}
class Homeresult {

    @SerializedName("categoryDetails")
    val categoryDetails: ArrayList<CategoryDetails>?=null
    @SerializedName("bannerDetails")
    val bannerDetails: ArrayList<BannerDetails>?=null
    @SerializedName("productDetails")
    val productDetails: ArrayList<ProductDetails>?=null
    @SerializedName("serviceDetails")
    val serviceDetails: ArrayList<ServiceDetails>?=null
    @SerializedName("dealDetails")
    val dealDetails :ArrayList<RetailerHomePageDealDetails>?=null
}
class CategoryDetails {

    @SerializedName("status")
    val status: String?=null
    @SerializedName("_id")
    val _id: String?=null
    @SerializedName("categoryName")
    val categoryName: String?=null
    @SerializedName("categoryImage")
    val categoryImage: String?=null
    @SerializedName("createdAt")
    val createdAt: String?=null
    @SerializedName("description")
    val description :String?=""
}

 class BannerDetails {

    @SerializedName("bannerImage")
    val bannerImage: ArrayList<String>?=null
    @SerializedName("status")
    val status: String?=null
    @SerializedName("_id")
    val _id: String?=null
    @SerializedName("bannerName")
    val bannerName: String?=null

}
class ProductDetails {

    @SerializedName("productId") val productId : String?=null
    @SerializedName("product") val product : Product?=null
    @SerializedName("productImage") val productImage : List<String>?=null
    @SerializedName("productFor") val productFor : String?=null
    @SerializedName("status") val status : String?=null
    @SerializedName("_id") val _id : String?=null
    @SerializedName("productName") val productName : String?=null
    @SerializedName("price") val price : Number=0
//    @SerializedName("unit") val unit : String?=null
    @SerializedName("description") val description : String?=null
    @SerializedName("brand") val brand : String?=null
    @SerializedName("quantity") val quantity : String?=null
    @SerializedName("productReferenceId") val productReferenceId : String?=null
//    @SerializedName("userId") val userId : String?=null
    @SerializedName("createdAt") val createdAt : String?=null
    @SerializedName("updatedAt") val updatedAt : String?=null
    @SerializedName("__v") val __v : Int?=null
//    @SerializedName("userDetails")
//    val userDetails :RetailerHomePageUserDetails?=null
}
class RetailerHomePageUserDetails {

    @SerializedName("_id")
    val _id: String?=null
    @SerializedName("storeLocation")
    val storeLocation: StoreLocation?=null
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
    @SerializedName("userRequestStatus")
    val userRequestStatus: String?=null
    @SerializedName("zipCode")
    val zipCode: String?=null
    @SerializedName("listOfBrandOrProducts")
    val listOfBrandOrProducts: List<String>?=null
    @SerializedName("preferredSupplierOrWholeSalerId")
    val preferredSupplierOrWholeSalerId: List<String>?=null
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
    @SerializedName("userType")
    val userType: String?=null
    @SerializedName("firstName")
    val firstName: String?=null
    @SerializedName("lastName")
    val lastName: String?=null
    @SerializedName("countryCode")
    val countryCode: Int?=null
    @SerializedName("mobileNumber")
    val mobileNumber: String?=null
    @SerializedName("email")
    val email: String?=null
    @SerializedName("phoneNumber")
    val phoneNumber: String?=null
    @SerializedName("storeAddress")
    val storeAddress: String?=null
    @SerializedName("storeName")
    val storeName: String?=null
    @SerializedName("storeContactNo")
    val storeContactNo: Int?=null
    @SerializedName("storeBrand")
    val storeBrand: String?=null
    @SerializedName("socialLink")
    val socialLink: SocialLink?=null
    @SerializedName("websiteUrl")
    val websiteUrl: String?=null
    @SerializedName("password")
    val password: String?=null
    @SerializedName("govtDocument")
    val govtDocument: GovtDocument?=null
    @SerializedName("city")
    val city: String?=null
    @SerializedName("state")
    val state: String?=null
    @SerializedName("eoriNumber")
    val eoriNumber: String?=null
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
    @SerializedName("businessBankingDetails")
    val businessBankingDetails: RetailerHomePageBusinessBankingDetails?=null
    @SerializedName("businessDetails")
    val businessDetails: RetailerHomePageBusinessDetails?=null
    @SerializedName("businessDocumentUpload")
    val businessDocumentUpload: RetailerHomePageBusinessDocumentUpload?=null
    @SerializedName("comments")
    val comments: String?=null
    @SerializedName("isPhysicalStore")
    val isPhysicalStore: Boolean?=null
    @SerializedName("keepStock")
    val keepStock: Boolean?=null
    @SerializedName("noOfUniqueProducts")
    val noOfUniqueProducts: Number?=null
    @SerializedName("ownerEmail")
    val ownerEmail: String?=null
    @SerializedName("ownerFirstName")
    val ownerFirstName: String?=null
    @SerializedName("ownerLastName")
    val ownerLastName: String?=null
}
data class RetailerHomePageBusinessBankingDetails (

    @SerializedName("bankName") val bankName : String,
    @SerializedName("branchName") val branchName : String,
    @SerializedName("branchCode") val branchCode : Int,
    @SerializedName("swiftCode") val swiftCode : String,
    @SerializedName("accountType") val accountType : String,
    @SerializedName("accountName") val accountName : String,
    @SerializedName("accountNumber") val accountNumber : String
)
class RetailerHomePageDealDetails {

    @SerializedName("_id")
    val _id :String?=null
    @SerializedName("disCountType")
    val disCountType :String?=null
    @SerializedName("dealsFor")
    val dealsFor :String?=null
//    @SerializedName("productId")
//    val productId :List<String>?=null

    @SerializedName("expired")
    val expired :Boolean?=null
    @SerializedName("status")
    val status :String?=null
    @SerializedName("dealName")
    val dealName :String?=null
    @SerializedName("dealImage")
    val dealImage :ArrayList<String> = ArrayList()
    @SerializedName("description")
    val description :String?=null
    @SerializedName("dealPrice")
    val dealPrice :Number?=0
    @SerializedName("dealStartTime")
    val dealStartTime :String?=null
    @SerializedName("dealEndTime")
    val dealEndTime :String?=null
    @SerializedName("dealType")
    val dealType :String?=null

    @SerializedName("createdAt")
    val createdAt :String?=null
    @SerializedName("updatedAt")
    val updatedAt :String?=null
    @SerializedName("__v")
    val __v :Int?=null

}

class ServiceDetails {
    @SerializedName("serviceLocation")
    val serviceLocation: Home_responseStoreLocation?=null
    @SerializedName("status")
    val status: String?=null
    @SerializedName("_id")
    val _id: String?=null
    @SerializedName("serviceName")
    val serviceName: String?=null
    @SerializedName("serviceImage")
   val serviceImage : List<String>?=null
    @SerializedName("description")
    val description: String?=null
    @SerializedName("price")
    val price: Number=0
    @SerializedName("duration")
    val duration: String?=null
    @SerializedName("serviceCategory")
    val serviceCategory: String?=null
    @SerializedName("createdAt")
    val createdAt: String?=null
    @SerializedName("updatedAt")
    val updatedAt: String?=null
    @SerializedName("__v")
    val __v: Int?=null
    @SerializedName("subCategoryDetails")
    val subCategoryDetails: List<subCategoryDetails>?=null
    @SerializedName("preferredSupplierOrWholeSalerId")
    val preferredSupplierOrWholeSalerId :List<String>?=null
    @SerializedName("listOfServices")
    val listOfServices :List<String>?=null
  @SerializedName("categoryId")
    val categoryId :CategoryId?=null

}

class subCategoryDetails {
    @SerializedName("price")
    val price: Number = 0

    @SerializedName("quantity")
    val quantity: Int? = null

    @SerializedName("subCategoryId")
    val subCategoryId: SubCategoryId? = null
}


class Home_responseStoreLocation {

    @SerializedName("type")
    val type: String? = null
    @SerializedName("coordinates")
    val coordinates: List<Double>? = null
}

class SubCategoryId {

    @SerializedName("status") val status : String?=null
    @SerializedName("_id") val _id : String?=null
    @SerializedName("categoryId") val categoryId : String?=null
    @SerializedName("subCategoryName") val subCategoryName : String?=null
    @SerializedName("__v") val __v : Int?=null
    @SerializedName("createdAt") val createdAt : String?=null
    @SerializedName("updatedAt") val updatedAt : String?=null
}
 class Home_responseUserId {

     @SerializedName("storeLocation")
     val storeLocation: Home_responseStoreLocation?=null
     @SerializedName("govtDocument")
     val govtDocument: Home_responseGovtDocument?=null
     @SerializedName("socialLink")
     val socialLink: Home_responseSocialLink?=null
     @SerializedName("isReset")
     val isReset: Boolean?=null
     @SerializedName("otpVerification")
     val otpVerification: Boolean?=null
     @SerializedName("userVerification")
     val userVerification: Boolean?=null
     @SerializedName("userRequestStatus")
     val userRequestStatus: String?=null
     @SerializedName("resetUserPassword")
     val resetUserPassword: Boolean?=null
     @SerializedName("status")
     val status: String?=null
     @SerializedName("_id")
     val _id: String?=null
     @SerializedName("firstName")
     val firstName: String?=null
     @SerializedName("lastName")
     val lastName: String?=null
     @SerializedName("countryCode")
     val countryCode: String?=null
     @SerializedName("mobileNumber")
     val mobileNumber: String?=null
     @SerializedName("storeAddress")
     val storeAddress: String?=null
     @SerializedName("email")
     val email: String?=null
     @SerializedName("storeName")
     val storeName: String?=null
     @SerializedName("storeContactNo")
     val storeContactNo: String?=null
     @SerializedName("storeBrand")
     val storeBrand: String?=null
     @SerializedName("websiteUrl")
     val websiteUrl: String?=null
     @SerializedName("userType")
     val userType: String?=null
     @SerializedName("password")
     val password: String?=null
     @SerializedName("profilePic")
     val profilePic: String?=null
     @SerializedName("addressLine1")
     val addressLine1: String?=null
     @SerializedName("addressLine2")
     val addressLine2: String?=null
     @SerializedName("city")
     val city: String?=null
     @SerializedName("state")
     val state: String?=null
     @SerializedName("country")
     val country: String?=null
     @SerializedName("companyName")
     val companyName: String?=null
     @SerializedName("zipCode")
     val zipCode: String?=null
     @SerializedName("vatNumber")
     val vatNumber: String?=null
     @SerializedName("eoriNumber")
     val eoriNumber: String?=null
     @SerializedName("subCategoryDetails")
     val subCategoryDetails: List<subCategoryDetails>?=null
     @SerializedName("createdAt")
     val createdAt: String?=null
     @SerializedName("updatedAt")
     val updatedAt: String?=null
     @SerializedName("__v")
     val __v: Int?=null
     @SerializedName("userUniqueId")
     val userUniqueId: String?=null
 }
//data class SocialLink (
//
//    @SerializedName("faceBook") val faceBook : String,
//    @SerializedName("linkedIn") val linkedIn : String,
//    @SerializedName("twitter") val twitter : String,
//    @SerializedName("instagram") val instagram : String
//)


 class Home_responseGovtDocument {

    @SerializedName("frontImage")
    val frontImage: String?=null
    @SerializedName("backImage")
    val backImage: String?=null
}

class Home_responseSocialLink {

    @SerializedName("faceBook")
    val faceBook: String?=null
    @SerializedName("linkedIn")
    val linkedIn: String?=null
    @SerializedName("twitter")
    val twitter: String?=null
    @SerializedName("instagram")
    val instagram: String?=null
}
 class CategoryId {

     @SerializedName("status")
     val status: String?=null
     @SerializedName("_id")
     val _id: String?=null
     @SerializedName("categoryName")
     val categoryName: String?=null
     @SerializedName("categoryImage")
     val categoryImage: String?=null
     @SerializedName("createdAt")
     val createdAt: String?=null
     @SerializedName("updatedAt")
     val updatedAt: String?=null
     @SerializedName("__v")
     val __v: Int?=null
 }
class RetailerHomePageBusinessDocumentUpload {

    @SerializedName("cirtificationOfIncorporation") val cirtificationOfIncorporation : CirtificationOfIncorporation?=null
    @SerializedName("directConsentForm") val directConsentForm : DirectConsentForm?=null
    @SerializedName("VatRegConfirmationDocs") val vatRegConfirmationDocs : VatRegConfirmationDocs?=null
    @SerializedName("directorId") val directorId : DirectorId?=null
    @SerializedName("bankConfirmationLetter") val bankConfirmationLetter : BankConfirmationLetter?=null
}
class RetailerHomePageBusinessDetails {

    @SerializedName("companyName")
    val companyName: String? = null
    @SerializedName("businessRegNumber")
    val businessRegNumber: String? = null
    @SerializedName("categoryId")
    val categoryId: String? = null
    @SerializedName("websiteUrl")
    val websiteUrl: String? = null
    @SerializedName("socialMediaAccounts")
    val socialMediaAccounts: String? = null
    @SerializedName("isVatRegistered")
    val isVatRegistered: Boolean? = null
    @SerializedName("vatNumber")
    val vatNumber: String? = null
    @SerializedName("monthlyRevenue")
    val monthlyRevenue: String? = null
}
 class Product {

     @SerializedName("_id")
     val _id: String?=null
     @SerializedName("productImage")
     val productImage: List<String>?=null
     @SerializedName("productFor")
     val productFor: String?=null
     @SerializedName("status")
     val status: String?=null
     @SerializedName("productName")
     val productName: String?=null
     @SerializedName("price")
     val price: Number=0
//     @SerializedName("unit")
//     val unit: Int?=null
     @SerializedName("brand")
     val brand: String?=null
     @SerializedName("description")
     val description: String?=null
     @SerializedName("categoryId")
     val categoryId: String?=null
     @SerializedName("subCategoryId")
     val subCategoryId: String?=null
     @SerializedName("quantity")
     val quantity: Int?=null
     @SerializedName("userId")
     val userId: String?=null
     @SerializedName("createdAt")
     val createdAt: String?=null
     @SerializedName("updatedAt")
     val updatedAt: String?=null
     @SerializedName("__v")
     val __v: Int?=null
//     @SerializedName("userDetails")
//     val userDetails: UserDetails?=null
 }