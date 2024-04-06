package com.exobe.entity.response.product.common

import BusinessBankingDetails
import BusinessDetails
import GovtDocument
import ServiceDetails
import SocialLink
import StoreLocation
import com.exobe.entity.response.BusinessDocumentUpload
import com.google.gson.annotations.SerializedName

class UserDetails {
    @SerializedName("_id") val _id : String=""
    @SerializedName("storeLocation") val storeLocation : StoreLocation = StoreLocation()
    @SerializedName("businessDetails") val businessDetails : BusinessDetails = BusinessDetails()
    @SerializedName("businessBankingDetails") val businessBankingDetails : BusinessBankingDetails = BusinessBankingDetails()
    @SerializedName("serviceDetails") val serviceDetails : ServiceDetails = ServiceDetails()
    @SerializedName("address") val address : String=""
    @SerializedName("otpVerification") val otpVerification : Boolean= false
    @SerializedName("userVerification") val userVerification : Boolean= false
    @SerializedName("profilePic") val profilePic : String=""
    @SerializedName("websiteUrl") val websiteUrl : String=""
    @SerializedName("duration") val duration : String=""
    @SerializedName("userRequestStatus") val userRequestStatus : String=""
    @SerializedName("zipCode") val zipCode : String=""
    @SerializedName("eoriNumber") val eoriNumber : String=""
    @SerializedName("additionalDocName") val additionalDocName : String=""
    @SerializedName("additionalDocument") val additionalDocument : String=""
    @SerializedName("ownerFirstName") val ownerFirstName : String=""
    @SerializedName("ownerLastName") val ownerLastName : String=""
    @SerializedName("ownerEmail") val ownerEmail : String=""
    @SerializedName("noOfUniqueProducts") val noOfUniqueProducts : Int=0
    @SerializedName("listOfBrandOrProducts") val listOfBrandOrProducts : ArrayList<String> = ArrayList<String>()
    @SerializedName("keepStock") val keepStock : Boolean = false
    @SerializedName("isPhysicalStore") val isPhysicalStore : Boolean= false
    @SerializedName("preferredSupplierOrWholeSalerId") val preferredSupplierOrWholeSalerId : ArrayList<String> = ArrayList()
    @SerializedName("comments") val comments : String=""
    @SerializedName("completeProfile") val completeProfile : Boolean= false
    @SerializedName("flag") val flag : Int=0
    @SerializedName("placeOrderCount") val placeOrderCount : Int=0
    @SerializedName("serviceOrderCount") val serviceOrderCount : Int=0
    @SerializedName("receiveOrderCount") val receiveOrderCount : Int=0
    @SerializedName("status") val status : String=""
    @SerializedName("userType") val userType : String=""
    @SerializedName("firstName") val firstName : String=""
    @SerializedName("lastName") val lastName : String=""
    @SerializedName("countryCode") val countryCode : String=""
    @SerializedName("mobileNumber") val mobileNumber :String=""
    @SerializedName("email") val email : String=""
    @SerializedName("phoneNumber") val phoneNumber : String=""
    @SerializedName("storeAddress") val storeAddress : String=""
    @SerializedName("storeName") val storeName : String=""
    @SerializedName("storeContactNo") val storeContactNo : String=""
    @SerializedName("storeBrand") val storeBrand : String=""
    @SerializedName("socialLink") val socialLink : SocialLink = SocialLink()
    @SerializedName("password") val password : String=""
    @SerializedName("govtDocument") val govtDocument : GovtDocument = GovtDocument()
    @SerializedName("city") val city : String=""
    @SerializedName("state") val state : String=""
    @SerializedName("country") val country : String=""
    @SerializedName("addressLine1") val addressLine1 : String=""
    @SerializedName("addressLine2") val addressLine2 : String=""
    @SerializedName("createdAt") val createdAt : String=""
    @SerializedName("updatedAt") val updatedAt : String=""
    @SerializedName("__v") val __v : Int=0
    @SerializedName("businessDocumentUpload") val businessDocumentUpload : BusinessDocumentUpload = BusinessDocumentUpload()
    @SerializedName("userUniqueId") val userUniqueId : String=""
}