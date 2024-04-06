package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class ServiceProvider_Orderview_Response (

    @SerializedName("result") val result : ServiceProvider_Orderview_Result? = null,
    @SerializedName("responseMessage") val responseMessage : String? = null,
    @SerializedName("responseCode") val responseCode : Int? = null,
    )
class ServiceProvider_Orderview_Result(
    @SerializedName("taxPrice") val taxPrice : Number = 0,
    @SerializedName("dealId") val dealId : List<String>? = null,
    @SerializedName("orderStatus") val orderStatus : String? = null,
    @SerializedName("deliveryStatus") val deliveryStatus : String? = null,
    @SerializedName("paymentStatus") val paymentStatus : String? = null,
    @SerializedName("slots") val slots : List<String>? = null,
    @SerializedName("status") val status : String? = null,
    @SerializedName("_id") val _id : String? = null,
    @SerializedName("userId") val userId : ServiceProvider_Orderview_UserId? = null,
    @SerializedName("serviceDetails") val serviceDetails : ArrayList<ServiceProvider_Orderview_ServiceDetails>? = null,
    @SerializedName("orderPrice") val orderPrice : Number = 0,
    @SerializedName("actualPrice") val actualPrice : Number = 0,
    @SerializedName("orderId") val orderId : String? = null,
    @SerializedName("orderType") val orderType : String,
    @SerializedName("productDetails")
    val productDetails: ArrayList<OrderHistoryRetailerProductDetails>? = ArrayList(),
    @SerializedName("createdAt") val createdAt : String? = null,
    @SerializedName("updatedAt") val updatedAt : String? = null,
    @SerializedName("__v") val __v : Int? = null,
    @SerializedName("shippingAddress") val shippingAddress : ServiceProvider_Orderview_ShippingAddress? = null,
    @SerializedName("shippingFixedAddress")
    var shippingFixedAddress: ShippingFixedAddress? = ShippingFixedAddress()
)

class ShippingFixedAddress {
    @SerializedName("location")
    val location: Location = Location()
    @SerializedName("isPrimary")
    val isPrimary: Boolean = false
    @SerializedName("status")
    val status: String = ""
    @SerializedName("address")
    val address: String = ""
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
    @SerializedName("email")
    val email: String = ""
    @SerializedName("firstName")
    val firstName: String = ""
    @SerializedName("lastName")
    val lastName: String = ""
    @SerializedName("mobileNumber")
    val mobileNumber: String = ""
    @SerializedName("state")
    val state: String = ""
    @SerializedName("stateIsoCode")
    val stateIsoCode: String = ""
    @SerializedName("zipCode")
    val zipCode: String = ""
    class Location {
        @SerializedName("type")
        val type: String = ""
        @SerializedName("coordinates")
        val coordinates: ArrayList<Double> = ArrayList()
    }
}

class ServiceProviderProductId {


    @SerializedName("discount")
    val discount: Int? = null
    @SerializedName("productImage")
    val productImage: List<String>? = null

    @SerializedName("productFor")
    val productFor: String? = null

    @SerializedName("status")
    val status: String? = null

    @SerializedName("_id")
    val _id: String? = null


    @SerializedName("productName")
    val productName: String? = ""


    @SerializedName("price")
    val price: Number = 0

//    @SerializedName("unit")
//    val unit: String? = null

    @SerializedName("brand")
    val brand: String? = null

    @SerializedName("description")
    val description: String? = null

    @SerializedName("quantity")
    val quantity: String? = null

    @SerializedName("productReferenceId")
    val productReferenceId: String? = null


    @SerializedName("createdAt")
    val createdAt: String? = null

    @SerializedName("updatedAt")
    val updatedAt: String? = null

    @SerializedName("__v")
    val __v: Int? = null

}

class ServiceProvider_Orderview_ShippingAddress(
    @SerializedName("status") val status : String? = null,
    @SerializedName("_id") val _id : String? = null,
    @SerializedName("city") val city : String? = null,
    @SerializedName("mobileNumber") val mobileNumber : String? = null,
    @SerializedName("firstName") val firstName : String? = null,
    @SerializedName("address") val address : String? = null,
    @SerializedName("email") val email : String? = null,
    @SerializedName("zipCode") val zipCode : String? = null,
    @SerializedName("country") val country : String? = null,
    @SerializedName("lastName") val lastName : String? = null,
    @SerializedName("countryCode") val countryCode : String? = null,
    @SerializedName("userId") val userId : String? = null,
    @SerializedName("createdAt") val createdAt : String? = null,
    @SerializedName("updatedAt") val updatedAt : String? = null,
    @SerializedName("__v") val __v : Int? = null,

)
//class ServiceProvider_Orderview_subCategoryDetails(
//    @SerializedName("price") val price : Int? = null,
//    @SerializedName("subCategoryId") val subCategoryId : String? = null,
//
//)
class ServiceProvider_Orderview_UserId (

    @SerializedName("storeLocation") val storeLocation : ServiceProvider_Orderview_StoreLocation? = null,
//    @SerializedName("govtDocument") val govtDocument : GovtDocument? = null,
//    @SerializedName("socialLink") val socialLink : SocialLink? = null,
//    @SerializedName("businessDetails") val businessDetails : BusinessDetails? = null,
//    @SerializedName("businessBankingDetails") val businessBankingDetails : BusinessBankingDetails? = null,
//    @SerializedName("businessDocumentUpload") val businessDocumentUpload : BusinessDocumentUpload? = null,
    @SerializedName("serviceDetails") val serviceDetails : ServiceProvider_Orderview_ServiceDetails? = null,
//    @SerializedName("address") val address : String? = null,
//    @SerializedName("otpVerification") val otpVerification : Boolean? = null,
//    @SerializedName("userVerification") val userVerification : Boolean? = null,
//    @SerializedName("profilePic") val profilePic : String? = null,
//    @SerializedName("websiteUrl") val websiteUrl : String? = null,
//    @SerializedName("duration") val duration : String? = null,
//    @SerializedName("userRequestStatus") val userRequestStatus : String? = null,
//    @SerializedName("zipCode") val zipCode : Int? = null,
//    @SerializedName("eoriNumber") val eoriNumber : Int? = null,
//    @SerializedName("additionalDocName") val additionalDocName : String? = null,
//    @SerializedName("additionalDocument") val additionalDocument : String? = null,
//    @SerializedName("ownerFirstName") val ownerFirstName : String? = null,
//    @SerializedName("ownerLastName") val ownerLastName : String? = null,
//    @SerializedName("ownerEmail") val ownerEmail : String? = null,
//    @SerializedName("noOfUniqueProducts") val noOfUniqueProducts : Int? = null,
//    @SerializedName("listOfBrandOrProducts") val listOfBrandOrProducts : List<String>? = null,
//    @SerializedName("keepStock") val keepStock : Boolean? = null,
//    @SerializedName("isPhysicalStore") val isPhysicalStore : Boolean? = null,
//    @SerializedName("howManyStoresDoYouHave") val howManyStoresDoYouHave : Int? = null,
//    @SerializedName("preferredSupplierOrWholeSalerId") val preferredSupplierOrWholeSalerId : List<String>? = null,
//    @SerializedName("comments") val comments : String? = null,
//    @SerializedName("completeProfile") val completeProfile : Boolean? = null,
//    @SerializedName("flag") val flag : Int? = null,
//    @SerializedName("placeOrderCount") val placeOrderCount : Int? = null,
//    @SerializedName("serviceOrderCount") val serviceOrderCount : Int? = null,
//    @SerializedName("receiveOrderCount") val receiveOrderCount : Int? = null,
//    @SerializedName("status") val status : String? = null,
//    @SerializedName("serviceOtpVerification") val serviceOtpVerification : Boolean? = null,
//    @SerializedName("_id") val _id : String? = null,
//    @SerializedName("userType") val userType : String? = null,
    @SerializedName("firstName") val firstName : String? = null,
    @SerializedName("lastName") val lastName : String? = null,
//    @SerializedName("countryCode") val countryCode : Int? = null,
    @SerializedName("mobileNumber") val mobileNumber : String? = null,
    @SerializedName("email") val email : String? = null,
//    @SerializedName("phoneNumber") val phoneNumber : Int? = null,
//    @SerializedName("password") val password : String? = null,
//    @SerializedName("city") val city : String? = null,
//    @SerializedName("state") val state : String? = null,
//    @SerializedName("country") val country : String? = null,
    @SerializedName("addressLine1") val addressLine1 : String? = null,
    @SerializedName("addressLine2") val addressLine2 : String? = null,
//    @SerializedName("createdAt") val createdAt : String? = null,
//    @SerializedName("updatedAt") val updatedAt : String? = null,
//    @SerializedName("__v") val __v : Int? = null,
//    @SerializedName("userUniqueId") val userUniqueId : String? = null,
//    @SerializedName("isReset") val isReset : Boolean? = null,
)
class ServiceProvider_Orderview_ServiceDetails(
//    @SerializedName("_id") val _id : String? = null,
    @SerializedName("serviceId") val serviceId : ServiceProvider_Orderview_ServiceID? = null,
    @SerializedName("quantity") val quantity : Int? = null,
    @SerializedName("price") val price : Number = 0,

)
class ServiceProvider_Orderview_ServiceID(
//    @SerializedName("serviceLocation") val serviceLocation : ServiceLocation? = null,
//    @SerializedName("serviceImage") val serviceImage : List<String>? = null,
//    @SerializedName("slots") val slots : List<String>? = null,
//    @SerializedName("status") val status : String? = null,
//    @SerializedName("_id") val _id : String? = null,
    @SerializedName("categoryId") val categoryId : ServiceProvider_Orderview_CategoryId? = null,
//    @SerializedName("subCategoryDetails") val subCategoryDetails : ArrayList<ServiceProvider_Orderview_SubCategoryDetails>? = null,
    @SerializedName("subCategoryId") val subCategoryId : String? = "",
    @SerializedName("serviceName") val serviceName : String? = "",
    @SerializedName("price") val price : String? = "",
    @SerializedName("description") val description : String? = "",
//    @SerializedName("userId") val userId : UserId? = null,
//    @SerializedName("__v") val __v : Int? = null,
//    @SerializedName("createdAt") val createdAt : String? = null,
//    @SerializedName("updatedAt") val upd
//    atedAt : String,
//    @SerializedName("description") val description : String? = null,
//    @SerializedName("duration") val duration : String? = null,
//    @SerializedName("serviceName") val serviceName : String? = null,

)
class ServiceProvider_Orderview_CategoryId (

//    @SerializedName("status") val status : String? = null,
//    @SerializedName("_id") val _id : String,
    @SerializedName("categoryName") val categoryName : String? = null,
//    @SerializedName("categoryImage") val categoryImage : String? = null,
//    @SerializedName("description") val description : String? = null,
//    @SerializedName("createdAt") val createdAt : String? = null,
//    @SerializedName("updatedAt") val updatedAt : String? = null,
//    @SerializedName("__v") val __v : Int? = null,
)
class ServiceProvider_Orderview_SubCategoryDetails(
//    @SerializedName("price") val price : Int? = null,
   @SerializedName("subCategoryId") val subCategoryId : ServiceProvider_Orderview_SubCategoryId? = null,
)
class ServiceProvider_Orderview_SubCategoryId (

//    @SerializedName("status") val status : String? = null,
//    @SerializedName("_id") val _id : String? = null,
//    @SerializedName("categoryId") val categoryId : String? = null,
    @SerializedName("subCategoryName") val subCategoryName : String? = null,
//    @SerializedName("createdAt") val createdAt : String? = null,
//    @SerializedName("updatedAt") val updatedAt : String? = null,
)

class ServiceProvider_Orderview_StoreLocation {

    @SerializedName("type")
    val type: String?=null
    @SerializedName("coordinates")
    val coordinates: List<Double>?=null
}
