package com.exobe.entity.response.customer

import com.google.gson.annotations.SerializedName

class ReviewOfProducts {
    @SerializedName("result") val result : List<ReviewResult>?= null
    @SerializedName("responseMessage") val responseMessage : String?= ""
    @SerializedName("responseCode") val responseCode : Int ?=0
}

class ReviewResult{
    @SerializedName("status") val status : String?= ""
    @SerializedName("_id") val _id : String?= ""
    @SerializedName("userId") val userId : ReviewUserId?= ReviewUserId()
    @SerializedName("productId") val productId : ReviewProductId?= ReviewProductId()
    @SerializedName("ratingCount") val ratingCount : Float?= null
    @SerializedName("comment") val comment : String?= ""
    @SerializedName("ratingType") val ratingType : String?= ""
    @SerializedName("createdAt") val createdAt : String?= ""
    @SerializedName("updatedAt") val updatedAt : String?= ""
    @SerializedName("__v") val __v : Int?= 0
}

class ReviewProductId{
    @SerializedName("productImage") val productImage : List<String>?= null
    @SerializedName("discount") val discount : Int?= 0
    @SerializedName("productFor") val productFor : String?= ""
    @SerializedName("status") val status : String?= ""
    @SerializedName("_id") val _id : String?= ""
    @SerializedName("productName") val productName : String?= ""
    @SerializedName("price") val price : Number?= 0
//    @SerializedName("unit") val unit : String?= ""
    @SerializedName("brand") val brand : String?= ""
    @SerializedName("description") val description : String?= ""
    @SerializedName("categoryId") val categoryId : String?= ""
    @SerializedName("subCategoryId") val subCategoryId : String?= ""
    @SerializedName("quantity") val quantity : String?= ""
    @SerializedName("productReferenceId") val productReferenceId : String?= ""
    @SerializedName("userId") val userId : String?= ""
    @SerializedName("createdAt") val createdAt : String?= ""
    @SerializedName("updatedAt") val updatedAt : String?= ""
    @SerializedName("__v") val __v : Int?= 0
    @SerializedName("avgRatingsProduct") val avgRatingsProduct : Float?=null
}

class ReviewUserId{
    @SerializedName("storeLocation") val storeLocation : ReviewStoreLocation?= ReviewStoreLocation()
    @SerializedName("businessDetails") val businessDetails : ReviewBusinessDetails ?= ReviewBusinessDetails()
    @SerializedName("businessBankingDetails") val businessBankingDetails : ReviewBusinessBankingDetails?= ReviewBusinessBankingDetails()
    @SerializedName("serviceDetails") val serviceDetails : ReviewServiceDetails?= ReviewServiceDetails()
    @SerializedName("address") val address : String?= ""
    @SerializedName("otpVerification") val otpVerification : Boolean?= null
    @SerializedName("userVerification") val userVerification : Boolean?= null
    @SerializedName("profilePic") val profilePic : String?= ""
    @SerializedName("websiteUrl") val websiteUrl : String?= ""
    @SerializedName("duration") val duration : String?= ""
    @SerializedName("userRequestStatus") val userRequestStatus : String?= ""
    @SerializedName("zipCode") val zipCode : String?= ""
    @SerializedName("eoriNumber") val eoriNumber : String?= ""
    @SerializedName("additionalDocName") val additionalDocName : String?= ""
    @SerializedName("additionalDocument") val additionalDocument : String?= ""
    @SerializedName("ownerFirstName") val ownerFirstName :String?= ""
    @SerializedName("ownerLastName") val ownerLastName : String?= ""
    @SerializedName("ownerEmail") val ownerEmail : String?= ""
    @SerializedName("noOfUniqueProducts") val noOfUniqueProducts : Int?= 0
    @SerializedName("listOfBrandOrProducts") val listOfBrandOrProducts : List<String>?= null
    @SerializedName("keepStock") val keepStock : Boolean?= null
    @SerializedName("isPhysicalStore") val isPhysicalStore : Boolean?= null
    @SerializedName("howManyStoresDoYouHave") val howManyStoresDoYouHave : String?= ""
    @SerializedName("preferredSupplierOrWholeSalerId") val preferredSupplierOrWholeSalerId : List<String>?= null
    @SerializedName("comments") val comments : String?= ""
    @SerializedName("completeProfile") val completeProfile : Boolean?= null
    @SerializedName("flag") val flag : Int?= 0
    @SerializedName("placeOrderCount") val placeOrderCount : Int?= 0
    @SerializedName("serviceOrderCount") val serviceOrderCount : Int?= 0
    @SerializedName("receiveOrderCount") val receiveOrderCount : Int?= 0
    @SerializedName("status") val status : String?= ""
    @SerializedName("serviceOtpVerification") val serviceOtpVerification : Boolean?= null
    @SerializedName("_id") val _id : String?= ""
    @SerializedName("email") val email : String?= ""
    @SerializedName("firstName") val firstName : String?= ""
    @SerializedName("mobileNumber") val mobileNumber : String?= ""
    @SerializedName("lastName") val lastName : String?= ""
    @SerializedName("userType") val userType : String?= ""
    @SerializedName("password") val password : String?= ""
    @SerializedName("countryCode") val countryCode : String?= ""
    @SerializedName("otp") val otp : String?= ""
    @SerializedName("otpExpireTime") val otpExpireTime : String?= ""
    @SerializedName("createdAt") val createdAt : String?= ""
    @SerializedName("updatedAt") val updatedAt : String?= ""
    @SerializedName("__v") val __v : Int?= 0
    @SerializedName("isReset") val isReset : Boolean?= null
}




class ReviewStoreLocation{
    @SerializedName("type") val type : String?= ""
    @SerializedName("coordinates") val coordinates : ArrayList<Double>?= null
}
class ReviewBusinessDetails{
    @SerializedName("companyName") val companyName : String?= ""
    @SerializedName("businessRegNumber") val businessRegNumber :String?= ""
    @SerializedName("websiteUrl") val websiteUrl : String?= ""
    @SerializedName("socialMediaAccounts") val socialMediaAccounts : String?= ""
    @SerializedName("isVatRegistered") val isVatRegistered : Boolean?= null
    @SerializedName("vatNumber") val vatNumber : String?= ""
    @SerializedName("monthlyRevenue") val monthlyRevenue : String?= ""
}
class ReviewBusinessBankingDetails{

    @SerializedName("bankName") val bankName : String?= ""
    @SerializedName("branchName") val branchName : String?= ""
    @SerializedName("branchCode") val branchCode : String?= ""
    @SerializedName("swiftCode") val swiftCode : String?= ""
    @SerializedName("accountType") val accountType : String?= ""
    @SerializedName("accountName") val accountName : String?= ""
    @SerializedName("accountNumber") val accountNumber : String?= ""
}
class ReviewServiceDetails{

    @SerializedName("noOfUniqueService") val noOfUniqueService : Int?= 0
    @SerializedName("preferredSupplierOrWholeSalerId") val preferredSupplierOrWholeSalerId : List<String>?= null
    @SerializedName("comments") val comments : String?= ""
    @SerializedName("listOfServices") val listOfServices : List<String>?= null
}



