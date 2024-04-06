package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class EditProfile_Response (
    @SerializedName("result") val result : EditProfile_Result,
    @SerializedName("responseMessage") val responseMessage : String,
    @SerializedName("responseCode") val responseCode : Int
    )
class EditProfile_Result(
//    @SerializedName("storeLocation") val storeLocation : EditProfile_StoreLocation,
    @SerializedName("govtDocument") val govtDocument : EditProfile_GovtDocument,
    @SerializedName("businessDetails") val businessDetails : EditProfile_BusinessDetails,
    @SerializedName("businessBankingDetails") val businessBankingDetails : BusinessBankingDetails,
//    @SerializedName("serviceDetails") val serviceDetails : EditProfile_ServiceDetails,
    @SerializedName("address") val address : String,
    @SerializedName("otpVerification") val otpVerification : Boolean,
    @SerializedName("userVerification") val userVerification : Boolean,
    @SerializedName("profilePic") val profilePic : String,
    @SerializedName("websiteUrl") val websiteUrl : String,
    @SerializedName("duration") val duration : String,
    @SerializedName("userRequestStatus") val userRequestStatus : String,
    @SerializedName("zipCode") val zipCode : String,
    @SerializedName("eoriNumber") val eoriNumber : String,
    @SerializedName("additionalDocName") val additionalDocName : String,
    @SerializedName("additionalDocument") val additionalDocument : String,
    @SerializedName("ownerFirstName") val ownerFirstName : String,
    @SerializedName("ownerLastName") val ownerLastName : String,
    @SerializedName("ownerEmail") val ownerEmail : String,
    @SerializedName("noOfUniqueProducts") val noOfUniqueProducts : Int,
    @SerializedName("listOfBrandOrProducts") val listOfBrandOrProducts : List<String>,
    @SerializedName("keepStock") val keepStock : Boolean,
    @SerializedName("isPhysicalStore") val isPhysicalStore : Boolean,
    @SerializedName("howManyStoresDoYouHave") val howManyStoresDoYouHave : Int,
    @SerializedName("preferredSupplierOrWholeSalerId") val preferredSupplierOrWholeSalerId : List<String>,
    @SerializedName("comments") val comments : String,
    @SerializedName("completeProfile") val completeProfile : Boolean,
    @SerializedName("flag") val flag : Int,
    @SerializedName("placeOrderCount") val placeOrderCount : Int,
    @SerializedName("serviceOrderCount") val serviceOrderCount : Int,
    @SerializedName("receiveOrderCount") val receiveOrderCount : Int,
    @SerializedName("status") val status : String,
    @SerializedName("_id") val _id : String,
    @SerializedName("addressLine1") val addressLine1 : String,
    @SerializedName("addressLine2") val addressLine2 : String,
    @SerializedName("city") val city : String,
    @SerializedName("country") val country : String,
    @SerializedName("countryCode") val countryCode : Int,
    @SerializedName("email") val email : String,
    @SerializedName("firstName") val firstName : String,
    @SerializedName("lastName") val lastName : String,
    @SerializedName("mobileNumber") val mobileNumber : String,
    @SerializedName("password") val password : String,
    @SerializedName("phoneNumber") val phoneNumber : String,
    @SerializedName("state") val state : String,
    @SerializedName("userType") val userType : String,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("isReset") val isReset : Boolean,
    @SerializedName("storeAddress") val storeAddress : String,
    @SerializedName("storeName") val storeName : String
)
//class EditProfile_ServiceDetails (
//
//    @SerializedName("noOfUniqueService") val noOfUniqueService : Int,
//    @SerializedName("preferredSupplierOrWholeSalerId") val preferredSupplierOrWholeSalerId : List<String>,
//    @SerializedName("comments") val comments : String,
//    @SerializedName("listOfServices") val listOfServices : List<String>
//)
//class EditProfile_StoreLocation (
//
//    @SerializedName("type") val type : String,
//    @SerializedName("coordinates") val coordinates : List<Int>
//)
class EditProfile_GovtDocument (

    @SerializedName("frontImage") val frontImage : String,
    @SerializedName("backImage") val backImage : String
)
class EditProfile_BusinessDetails (

    @SerializedName("companyName") val companyName : String,
    @SerializedName("businessRegNumber") val businessRegNumber : String,
    @SerializedName("websiteUrl") val websiteUrl : String,
    @SerializedName("socialMediaAccounts") val socialMediaAccounts : String,
    @SerializedName("isVatRegistered") val isVatRegistered : Boolean,
    @SerializedName("vatNumber") val vatNumber : String,
    @SerializedName("monthlyRevenue") val monthlyRevenue : String
)


