package com.exobe.entity.response.customerservices

import ServiceLocation
import SubCategoryDetails
import com.exobe.entity.response.CategoryDetails
import com.google.gson.annotations.SerializedName

class ListServiceNearMeResponse {
    @SerializedName("result") val result : LSNMResult = LSNMResult()
    @SerializedName("responseMessage") val responseMessage : String= ""
    @SerializedName("responseCode") val responseCode : Int= 0
}

class LSNMResult {
    @SerializedName("docs") val docs : ArrayList<LSNMDocs> =  ArrayList<LSNMDocs>()
    @SerializedName("total") val total : Int=0
    @SerializedName("limit") val limit : Int=0
    @SerializedName("page") val page : Int=0
    @SerializedName("pages") val pages : Int=0
}

class LSNMDocs {
    @SerializedName("_id") val _id : String= ""
    @SerializedName("serviceLocation") val serviceLocation : ServiceLocation = ServiceLocation()
    @SerializedName("serviceImage") val serviceImage : ArrayList<String> = ArrayList<String>()
    @SerializedName("slots") val slots : ArrayList<String> = ArrayList<String>()
    @SerializedName("status") val status : String= ""
    @SerializedName("categoryId") val categoryId : String= ""
    @SerializedName("subCategoryDetails") val subCategoryDetails : SubCategoryDetails = SubCategoryDetails()
    @SerializedName("userId") val userId : String= ""
    @SerializedName("__v") val __v : Int= 0
    @SerializedName("createdAt") val createdAt : String= ""
    @SerializedName("updatedAt") val updatedAt : String= ""
    @SerializedName("description") val description : String= ""
    @SerializedName("duration") val duration : String= ""
    @SerializedName("serviceName") val serviceName : String= ""
    @SerializedName("distance") val distance : Double= 0.0
    @SerializedName("categoryDetails") val categoryDetails : CategoryDetails = CategoryDetails()
    @SerializedName("ownerDetails") val ownerDetails : OwnerDetails = OwnerDetails()
}


class OwnerDetails {

    @SerializedName("_id")
    val _id: String= ""

    //    @SerializedName("storeLocation") val storeLocation : StoreLocation,
//    @SerializedName("businessDetails") val businessDetails : BusinessDetails,
//    @SerializedName("businessBankingDetails") val businessBankingDetails : BusinessBankingDetails,
//    @SerializedName("serviceDetails") val serviceDetails : ServiceDetails,
//    @SerializedName("address") val address : String,
//    @SerializedName("otpVerification") val otpVerification : Boolean,
//    @SerializedName("userVerification") val userVerification : Boolean,
    @SerializedName("profilePic") val profilePic : String=""
//    @SerializedName("websiteUrl") val websiteUrl : String,
//    @SerializedName("duration") val duration : String,
//    @SerializedName("userRequestStatus") val userRequestStatus : String,
//    @SerializedName("zipCode") val zipCode : Int,
//    @SerializedName("eoriNumber") val eoriNumber : Int,
//    @SerializedName("additionalDocName") val additionalDocName : String,
//    @SerializedName("additionalDocument") val additionalDocument : String,
//    @SerializedName("ownerFirstName") val ownerFirstName : String,
//    @SerializedName("ownerLastName") val ownerLastName : String,
//    @SerializedName("ownerEmail") val ownerEmail : String,
//    @SerializedName("noOfUniqueProducts") val noOfUniqueProducts : Int,
//    @SerializedName("listOfBrandOrProducts") val listOfBrandOrProducts : List<String>,
//    @SerializedName("keepStock") val keepStock : Boolean,
//    @SerializedName("isPhysicalStore") val isPhysicalStore : Boolean,
//    @SerializedName("preferredSupplierOrWholeSalerId") val preferredSupplierOrWholeSalerId : List<String>,
//    @SerializedName("comments") val comments : String,
//    @SerializedName("completeProfile") val completeProfile : Boolean,
//    @SerializedName("flag") val flag : Int,
//    @SerializedName("placeOrderCount") val placeOrderCount : Int,
//    @SerializedName("serviceOrderCount") val serviceOrderCount : Int,
//    @SerializedName("receiveOrderCount") val receiveOrderCount : Int,
//    @SerializedName("status") val status : String,
    @SerializedName("userType")
    val userType: String= ""
    @SerializedName("ownerFirstName")
    val ownerFirstName: String= ""
    @SerializedName("ownerLastName")
    val ownerLastName: String= ""
//    @SerializedName("countryCode") val countryCode : Int,
//    @SerializedName("mobileNumber") val mobileNumber : Int,
//    @SerializedName("email") val email : String,
//    @SerializedName("phoneNumber") val phoneNumber : Int,
//    @SerializedName("socialLink") val socialLink : SocialLink,
//    @SerializedName("password") val password : String,
//    @SerializedName("govtDocument") val govtDocument : GovtDocument,
//    @SerializedName("city") val city : String,
//    @SerializedName("state") val state : String,
//    @SerializedName("country") val country : String,
//    @SerializedName("addressLine1") val addressLine1 : String,
//    @SerializedName("addressLine2") val addressLine2 : String,
//    @SerializedName("createdAt") val createdAt : String,
//    @SerializedName("updatedAt") val updatedAt : String,
//    @SerializedName("__v") val __v : Int,
//    @SerializedName("businessDocumentUpload") val businessDocumentUpload : BusinessDocumentUpload,
//    @SerializedName("userUniqueId") val userUniqueId : String,
//    @SerializedName("isReset") val isReset : Boolean
}