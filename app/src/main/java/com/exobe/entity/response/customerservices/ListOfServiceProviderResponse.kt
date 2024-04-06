package com.exobe.entity.response.customerservices


import com.google.gson.annotations.SerializedName


class ListOfServiceProviderResponse (
    @SerializedName("result")
    val result: ListOfServiceProviderResult = ListOfServiceProviderResult(),
    @SerializedName("responseMessage")
    val responseMessage: String = "",
    @SerializedName("responseCode")
    val responseCode: Int = 0
    )

class ListOfServiceProviderResult (
    @SerializedName("docs")
    val docs: ArrayList<ListOfServiceProviderDoc> = ArrayList(),
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("totalPages")
    val totalPages: Int = 1

)

class ListOfServiceProviderDoc {
    @SerializedName("storeLocation")
    val storeLocation: ListOfServiceProviderStoreLocation = ListOfServiceProviderStoreLocation()
    @SerializedName("address")
    val address: String = ""
    @SerializedName("otpVerification")
    val otpVerification: Boolean = false
    @SerializedName("userVerification")
    val userVerification: Boolean = false
    @SerializedName("profilePic")
    val profilePic: String = ""
    @SerializedName("websiteUrl")
    val websiteUrl: String = ""
    @SerializedName("duration")
    val duration: String = ""
    @SerializedName("userRequestStatus")
    val userRequestStatus: String = ""
    @SerializedName("zipCode")
    val zipCode: String = ""
    @SerializedName("eoriNumber")
    val eoriNumber: String = ""
    @SerializedName("additionalDocName")
    val additionalDocName: String = ""
    @SerializedName("additionalDocument")
    val additionalDocument: String = ""
    @SerializedName("DOB")
    val dOB: String = ""
    @SerializedName("ownerFirstName")
    val ownerFirstName: String = ""
    @SerializedName("ownerLastName")
    val ownerLastName: String = ""
    @SerializedName("ownerEmail")
    val ownerEmail: String = ""
    @SerializedName("noOfUniqueProducts")
    val noOfUniqueProducts: Int = 0
    @SerializedName("keepStock")
    val keepStock: Boolean = false
    @SerializedName("isPhysicalStore")
    val isPhysicalStore: Boolean = false
    @SerializedName("howManyStoresDoYouHave")
    val howManyStoresDoYouHave: String = ""

    @SerializedName("comments")
    val comments: String = ""
    @SerializedName("completeProfile")
    val completeProfile: Boolean = false
    @SerializedName("flag")
    val flag: Int = 0
    @SerializedName("placeOrderCount")
    val placeOrderCount: Int = 0
    @SerializedName("serviceOrderCount")
    val serviceOrderCount: Int = 0
    @SerializedName("receiveOrderCount")
    val receiveOrderCount: Int = 0
    @SerializedName("status")
    val status: String = ""
    @SerializedName("thumbnail")
    val thumbnail: String = ""
    @SerializedName("updateDocumentStatus")
    val updateDocumentStatus: String = ""
    @SerializedName("_id")
    val id: String = ""
    @SerializedName("addressLine1")
    val addressLine1: String = ""
    @SerializedName("addressLine2")
    val addressLine2: String = ""
    @SerializedName("city")
    val city: String = ""
    @SerializedName("country")
    val country: String = ""
    @SerializedName("countryCode")
    val countryCode: String = ""
    @SerializedName("countryIsoCode")
    val countryIsoCode: String = ""
    @SerializedName("deviceToken")
    val deviceToken: String = ""
    @SerializedName("deviceType")
    val deviceType: String = ""
    @SerializedName("email")
    val email: String = ""
    @SerializedName("firstName")
    val firstName: String = ""
    @SerializedName("lastName")
    val lastName: String = ""
    @SerializedName("mobileNumber")
    val mobileNumber: String = ""
    @SerializedName("password")
    val password: String = ""
    @SerializedName("phoneNumber")
    val phoneNumber: String = ""
    @SerializedName("state")
    val state: String = ""
    @SerializedName("stateIsoCode")
    val stateIsoCode: String = ""
    @SerializedName("userType")
    val userType: String = ""
    @SerializedName("createdAt")
    val createdAt: String = ""
    @SerializedName("updatedAt")
    val updatedAt: String = ""
    @SerializedName("__v")
    val v: Int = 0
    @SerializedName("primaryAddressId")
    val primaryAddressId: String = ""
    @SerializedName("userUniqueId")
    val userUniqueId: String = ""
    @SerializedName("distance")
    val distance: Double = 0.0

//    @SerializedName("allServiceDetails") val allServiceDetails : ArrayList<ListOfServiceProviderAllServiceDetails> = ArrayList<ListOfServiceProviderAllServiceDetails>()

}

class ListOfServiceProviderStoreLocation {
    @SerializedName("type")
    val type: String = ""
    @SerializedName("coordinates")
    val coordinates: ArrayList<Double> = ArrayList()
}

//class ListOfServiceProviderAllServiceDetails{
//    @SerializedName("_id") val _id : String=""
////    @SerializedName("serviceLocation") val serviceLocation : ServiceLocation = ServiceLocation()
//    @SerializedName("serviceImage") val serviceImage : ArrayList<String> = ArrayList<String>()
//    @SerializedName("slots") val slots : ArrayList<String> = ArrayList<String>()
//    @SerializedName("status") val status : String= ""
//    @SerializedName("categoryId") val categoryId : String= ""
////    @SerializedName("subCategoryDetails") val subCategoryDetails : ArrayList<SubCategoryDetails> = ArrayList<SubCategoryDetails>()
//    @SerializedName("userId") val userId :String= ""
//    @SerializedName("__v") val __v : Int=0
//    @SerializedName("createdAt") val createdAt : String= ""
//    @SerializedName("updatedAt") val updatedAt : String= ""
//}
