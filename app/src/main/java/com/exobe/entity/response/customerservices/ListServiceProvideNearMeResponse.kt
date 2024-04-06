package com.exobe.entity.response.customerservices

import BusinessDetails
import GovtDocument
import ServiceDetails
import ServiceLocation
import SocialLink
import SubCategoryDetails
import com.google.gson.annotations.SerializedName

class ListServiceProvideNearMeResponse {
    @SerializedName("result") val result : ListServiceProvideNearMeResult= ListServiceProvideNearMeResult()
    @SerializedName("responseMessage") val responseMessage : String =""
    @SerializedName("responseCode") val responseCode : Int=0
}

class ListServiceProvideNearMeResult{
    @SerializedName("docs") val docs : ArrayList<ListServiceProvideNearMeDocs> = ArrayList<ListServiceProvideNearMeDocs>()
    @SerializedName("total") val total : Int=0
    @SerializedName("limit") val limit : Int=0
    @SerializedName("page") val page : Int=0
    @SerializedName("pages") val pages : Int=0
}


class ListServiceProvideNearMeDocs{
    @SerializedName("_id") val _id :String = ""
//    @SerializedName("storeLocation") val storeLocation : StoreLocation = StoreLocation()
    @SerializedName("businessDetails") val businessDetails : BusinessDetails = BusinessDetails()
//    @SerializedName("businessBankingDetails") val businessBankingDetails : BusinessBankingDetails = BusinessBankingDetails()
    @SerializedName("serviceDetails") val serviceDetails : ServiceDetails = ServiceDetails()
    @SerializedName("address") val address : String =""
    @SerializedName("otpVerification") val otpVerification : Boolean= false
    @SerializedName("userVerification") val userVerification : Boolean= false
    @SerializedName("profilePic") val profilePic :String =""
    @SerializedName("websiteUrl") val websiteUrl : String =""
    @SerializedName("duration") val duration : String =""
    @SerializedName("userRequestStatus") val userRequestStatus : String =""
    @SerializedName("zipCode") val zipCode : String =""
    @SerializedName("eoriNumber") val eoriNumber :String =""
    @SerializedName("additionalDocName") val additionalDocName : String =""
    @SerializedName("additionalDocument") val additionalDocument :  String =""
    @SerializedName("ownerFirstName") val ownerFirstName :  String =""
    @SerializedName("ownerLastName") val ownerLastName :  String =""
    @SerializedName("ownerEmail") val ownerEmail :  String =""
    @SerializedName("noOfUniqueProducts") val noOfUniqueProducts : Int=0
    @SerializedName("listOfBrandOrProducts") val listOfBrandOrProducts : ArrayList<String> = ArrayList<String>()
    @SerializedName("keepStock") val keepStock : Boolean= false
    @SerializedName("isPhysicalStore") val isPhysicalStore : Boolean= false
    @SerializedName("preferredSupplierOrWholeSalerId") val preferredSupplierOrWholeSalerId : ArrayList<String> = ArrayList<String>()
    @SerializedName("comments") val comments :  String =""
    @SerializedName("completeProfile") val completeProfile : Boolean= false
    @SerializedName("flag") val flag : Int=0
    @SerializedName("placeOrderCount") val placeOrderCount : Int=0
    @SerializedName("serviceOrderCount") val serviceOrderCount : Int=0
    @SerializedName("receiveOrderCount") val receiveOrderCount :Int=0
    @SerializedName("status") val status : String = ""
    @SerializedName("userType") val userType : String = ""
    @SerializedName("firstName") val firstName : String = ""
    @SerializedName("lastName") val lastName : String = ""
    @SerializedName("countryCode") val countryCode : String = ""
    @SerializedName("mobileNumber") val mobileNumber : String = ""
    @SerializedName("email") val email :String = ""
    @SerializedName("phoneNumber") val phoneNumber : String = ""
    @SerializedName("socialLink") val socialLink : SocialLink = SocialLink()
    @SerializedName("password") val password : String = ""
    @SerializedName("govtDocument") val govtDocument : GovtDocument= GovtDocument()
    @SerializedName("city") val city : String = ""
    @SerializedName("state") val state : String = ""
    @SerializedName("country") val country : String = ""
    @SerializedName("addressLine1") val addressLine1 :String = ""
    @SerializedName("addressLine2") val addressLine2 : String = ""
    @SerializedName("createdAt") val createdAt : String = ""
    @SerializedName("updatedAt") val updatedAt :String = ""
    @SerializedName("__v") val __v : Int=0
    @SerializedName("businessDocumentUpload") val businessDocumentUpload : ListBusinessDocumentUpload= ListBusinessDocumentUpload()
    @SerializedName("userUniqueId") val userUniqueId : String = ""
    @SerializedName("isReset") val isReset : Boolean =false
    @SerializedName("distance") val distance : Double=0.0
    @SerializedName("allServiceDetails") val allServiceDetails : ArrayList<AllServiceDetails> = ArrayList<AllServiceDetails>()
}

class ListBusinessDocumentUpload{
    @SerializedName("cirtificationOfIncorporation") val cirtificationOfIncorporation : ListCirtificationOfIncorporation= ListCirtificationOfIncorporation()
    @SerializedName("directConsentForm") val directConsentForm :ListDirectConsentForm = ListDirectConsentForm()
    @SerializedName("VatRegConfirmationDocs") val vatRegConfirmationDocs : ListVatRegConfirmationDocs= ListVatRegConfirmationDocs()
    @SerializedName("directorId") val directorId : ListDirectorId= ListDirectorId()
    @SerializedName("bankConfirmationLetter") val bankConfirmationLetter : ListBankConfirmationLetter= ListBankConfirmationLetter()
}
class AllServiceDetails{
    @SerializedName("_id") val _id : String=""
    @SerializedName("serviceLocation") val serviceLocation : ServiceLocation = ServiceLocation()
    @SerializedName("serviceImage") val serviceImage : ArrayList<String> = ArrayList<String>()
    @SerializedName("slots") val slots : ArrayList<String> = ArrayList<String>()
    @SerializedName("status") val status : String= ""
    @SerializedName("categoryId") val categoryId : String= ""
    @SerializedName("subCategoryDetails") val subCategoryDetails : ArrayList<SubCategoryDetails> = ArrayList<SubCategoryDetails>()
    @SerializedName("userId") val userId :String= ""
    @SerializedName("__v") val __v : Int=0
    @SerializedName("createdAt") val createdAt : String= ""
    @SerializedName("updatedAt") val updatedAt : String= ""
}

class ListCirtificationOfIncorporation{
    @SerializedName("frontImage") val frontImage : String= ""
    @SerializedName("backImage") val backImage : String= ""
}
class ListDirectConsentForm{
    @SerializedName("frontImage") val frontImage : String= ""
    @SerializedName("backImage") val backImage : String= ""
}
class ListVatRegConfirmationDocs{
    @SerializedName("frontImage") val frontImage : String= ""
    @SerializedName("backImage") val backImage : String= ""
}
class ListDirectorId{
    @SerializedName("frontImage") val frontImage : String= ""
    @SerializedName("backImage") val backImage : String= ""
}
class ListBankConfirmationLetter{
    @SerializedName("frontImage") val frontImage : String= ""
    @SerializedName("backImage") val backImage : String= ""
}