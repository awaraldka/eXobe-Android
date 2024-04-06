package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class ServiceProviderSignup_Response (
    @SerializedName("result") val result : ServiceProviderSignup_Result,
    @SerializedName("responseMessage") val responseMessage : String,
    @SerializedName("responseCode") val responseCode : Int
        )

class ServiceProviderSignup_Result(
    @SerializedName("storeLocation") val storeLocation : ServiceProviderSignup_StoreLocation,
    @SerializedName("businessDetails") val businessDetails : BusinessDetails,
    @SerializedName("businessBankingDetails") val businessBankingDetails : BusinessBankingDetails,
    @SerializedName("serviceDetails") val serviceDetails : ServiceProviderSignup_Service_Details,
    @SerializedName("address") val address : String,
    @SerializedName("isReset") val isReset : Boolean,
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
    @SerializedName("preferredSupplierOrWholeSalerId") val preferredSupplierOrWholeSalerId : List<String>,
    @SerializedName("comments") val comments : String,
    @SerializedName("completeProfile") val completeProfile : Boolean,
    @SerializedName("flag") val flag : Int,
    @SerializedName("placeOrderCount") val placeOrderCount : Int,
    @SerializedName("serviceOrderCount") val serviceOrderCount : Int,
    @SerializedName("receiveOrderCount") val receiveOrderCount : Int,
    @SerializedName("status") val status : String,
    @SerializedName("_id") val _id : String,
    @SerializedName("userType") val userType : String,
    @SerializedName("firstName") val firstName : String,
    @SerializedName("lastName") val lastName : String,
    @SerializedName("countryCode") val countryCode : Int,
    @SerializedName("mobileNumber") val mobileNumber : String,
    @SerializedName("email") val email : String,
    @SerializedName("phoneNumber") val phoneNumber : String,
    @SerializedName("password") val password : String,
    @SerializedName("city") val city : String,
    @SerializedName("state") val state : String,
    @SerializedName("country") val country : String,
    @SerializedName("addressLine1") val addressLine1 : String,
    @SerializedName("addressLine2") val addressLine2 : String,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int
)
class ServiceProviderSignup_Service_Details(
    @SerializedName("noOfUniqueService") val noOfUniqueService : Int,
    @SerializedName("preferredSupplierOrWholeSalerId") val preferredSupplierOrWholeSalerId : List<String>,
    @SerializedName("comments") val comments : String,
    @SerializedName("listOfServices") val listOfServices : List<String>
)
data class ServiceProviderSignup_StoreLocation (

    @SerializedName("type") val type : String,
    @SerializedName("coordinates") val coordinates : List<Double>
)
