package com.exobe.entity.response.customer

import GovtDocument
import SocialLink
import com.google.gson.annotations.SerializedName

class SuggestionResponse {
    @SerializedName("result") val result : SuggestionResult= SuggestionResult()
    @SerializedName("responseMessage") val responseMessage : String= ""
    @SerializedName("responseCode") val responseCode : Int= 0
}

class SuggestionResult{



    @SerializedName("docs") val docs : ArrayList<SuggestionDocs> = ArrayList<SuggestionDocs>()
    @SerializedName("total") val total : Int= 0
    @SerializedName("limit") val limit : Int= 0
    @SerializedName("page") val page : Int= 0
    @SerializedName("pages") val pages : Int= 0






}

class SuggestionDocs{
    @SerializedName("productImage") val productImage : ArrayList<String> = ArrayList<String>()
    @SerializedName("discount") val discount : Int= 0
    @SerializedName("productFor") val productFor : String= ""
    @SerializedName("status") val status : String= ""
    @SerializedName("_id") val _id : String= ""
    @SerializedName("productName") val productName : String= ""
    @SerializedName("price") val price : Number?= null
//    @SerializedName("unit") val unit :String= ""
    @SerializedName("brand") val brand : String= ""
    @SerializedName("description") val description : String= ""
    @SerializedName("categoryId") val categoryId : String= ""
    @SerializedName("subCategoryId") val subCategoryId : String= ""
    @SerializedName("quantity") val quantity : Int= 0
    @SerializedName("productReferenceId") val productReferenceId : String= ""
    @SerializedName("createdAt") val createdAt : String= ""
    @SerializedName("updatedAt") val updatedAt : String= ""
    @SerializedName("__v") val __v : Int= 0

    @SerializedName("userId") val userId : String= ""
    @SerializedName("userDetails") val userDetails : SuggetionsUserDetails = SuggetionsUserDetails()
    @SerializedName("categoryDetails") val categoryDetails : SuggetionCategoryDetails =SuggetionCategoryDetails()
    @SerializedName("subCategoryDetails") val subCategoryDetails : SuggetionSubCategoryDetails =SuggetionSubCategoryDetails()
    @SerializedName("isLike") val isLike : Boolean = false


}

class SuggetionCategoryDetails{
    @SerializedName("_id") val _id : String= ""
    @SerializedName("status") val status : String= ""
    @SerializedName("categoryId") val categoryId : String= ""
    @SerializedName("subCategoryName") val subCategoryName : String= ""
    @SerializedName("__v") val __v : Int = 0
    @SerializedName("createdAt") val createdAt : String= ""
    @SerializedName("updatedAt") val updatedAt : String= ""
}
class SuggetionSubCategoryDetails{
    @SerializedName("_id") val _id : String= ""
    @SerializedName("status") val status : String= ""
    @SerializedName("categoryId") val categoryId : String= ""
    @SerializedName("subCategoryName") val subCategoryName : String= ""
    @SerializedName("__v") val __v : Int = 0
    @SerializedName("createdAt") val createdAt : String= ""
    @SerializedName("updatedAt") val updatedAt : String= ""
}

class SuggetionsUserDetails{
    @SerializedName("_id") val _id : String =""
    @SerializedName("storeLocation") val storeLocation : ReviewStoreLocation = ReviewStoreLocation()
    @SerializedName("businessDetails") val businessDetails : ReviewBusinessDetails= ReviewBusinessDetails()
    @SerializedName("businessBankingDetails") val businessBankingDetails : ReviewBusinessBankingDetails =ReviewBusinessBankingDetails()
    @SerializedName("serviceDetails") val serviceDetails : ReviewServiceDetails =ReviewServiceDetails()
    @SerializedName("address") val address : String =""
    @SerializedName("otpVerification") val otpVerification : Boolean = false
    @SerializedName("userVerification") val userVerification : Boolean = false
    @SerializedName("profilePic") val profilePic : String =""
    @SerializedName("websiteUrl") val websiteUrl : String =""
    @SerializedName("duration") val duration :String =""
    @SerializedName("userRequestStatus") val userRequestStatus : String ="" 
    @SerializedName("zipCode") val zipCode : String =""
    @SerializedName("eoriNumber") val eoriNumber : String =""
    @SerializedName("additionalDocName") val additionalDocName : String =""
    @SerializedName("additionalDocument") val additionalDocument : String =""
    @SerializedName("ownerFirstName") val ownerFirstName : String = ""
    @SerializedName("ownerLastName") val ownerLastName : String = ""
    @SerializedName("ownerEmail") val ownerEmail : String = ""
    @SerializedName("noOfUniqueProducts") val noOfUniqueProducts : Int = 0
    @SerializedName("listOfBrandOrProducts") val listOfBrandOrProducts : ArrayList<String> = ArrayList<String>()
    @SerializedName("keepStock") val keepStock : Boolean = false
    @SerializedName("isPhysicalStore") val isPhysicalStore : Boolean = false
    @SerializedName("preferredSupplierOrWholeSalerId") val preferredSupplierOrWholeSalerId = ArrayList<String>()
    @SerializedName("comments") val comments : String = ""
    @SerializedName("completeProfile") val completeProfile : Boolean = false
    @SerializedName("flag") val flag : Int = 0
    @SerializedName("placeOrderCount") val placeOrderCount : Int = 0
    @SerializedName("serviceOrderCount") val serviceOrderCount : Int = 0
    @SerializedName("receiveOrderCount") val receiveOrderCount : Int = 0
    @SerializedName("status") val status : String = ""
    @SerializedName("userType") val userType : String = ""
    @SerializedName("firstName") val firstName : String = ""
    @SerializedName("lastName") val lastName : String = ""
    @SerializedName("countryCode") val countryCode : String = ""
    @SerializedName("mobileNumber") val mobileNumber : String = ""
    @SerializedName("email") val email : String = ""
    @SerializedName("phoneNumber") val phoneNumber : String = ""
    @SerializedName("storeAddress") val storeAddress : String = ""
    @SerializedName("storeName") val storeName : String = ""
    @SerializedName("storeContactNo") val storeContactNo : String = ""
    @SerializedName("storeBrand") val storeBrand : String = ""
    @SerializedName("socialLink") val socialLink : SocialLink = SocialLink()
    @SerializedName("password") val password : String = ""
    @SerializedName("govtDocument") val govtDocument : GovtDocument = GovtDocument()
    @SerializedName("city") val city : String = ""
    @SerializedName("state") val state : String = ""
    @SerializedName("country") val country : String = ""
    @SerializedName("addressLine1") val addressLine1 : String = ""
    @SerializedName("createdAt") val createdAt : String = ""
    @SerializedName("updatedAt") val updatedAt : String = ""
    @SerializedName("__v") val __v : Int = 0
    @SerializedName("userUniqueId") val userUniqueId : String = ""
}