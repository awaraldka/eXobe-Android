package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class ServiceStatus_Tab_Response {

    @SerializedName("result") val result : ServiceStatus_Tab_Result? = null
    @SerializedName("responseMessage") val responseMessage : String? = null
    @SerializedName("responseCode") val responseCode : Int? = null
}
class ServiceStatus_Tab_Result{
    @SerializedName("docs") val docs : List<ServiceStatus_Tab_Docs>? = null
    @SerializedName("total") val total : Int? = null
    @SerializedName("limit") val limit : Int? = null
    @SerializedName("page") val page : Int? = null
    @SerializedName("pages") val pages : Int? = null
}
class ServiceStatus_Tab_Docs{
    @SerializedName("_id") val _id : String? = null
//    @SerializedName("taxPrice") val taxPrice : Int? = null
//    @SerializedName("dealId") val dealId : List<String>? = null
    @SerializedName("orderStatus") val orderStatus : String? = null
//    @SerializedName("deliveryStatus") val deliveryStatus : String? = null
    @SerializedName("paymentStatus") val paymentStatus : String? = null
    @SerializedName("status") val status : String? = null
//    @SerializedName("userId") val userId : String? = null
    @SerializedName("serviceDetails") val serviceDetails : ServiceStatus_Tab_ServiceDetails? = null
//    @SerializedName("orderPrice") val orderPrice : Int? = null
//    @SerializedName("actualPrice") val actualPrice : Int? = null
    @SerializedName("orderId") val orderId : String? = null
//    @SerializedName("orderType") val orderType : String? = null
//    @SerializedName("productDetails") val productDetails : List<String>? = null
    @SerializedName("createdAt") val createdAt : String? = null
    @SerializedName("updatedAt") val updatedAt : String? = null
//    @SerializedName("__v") val __v : Int? = null
//    @SerializedName("shippingAddress") val shippingAddress : String? = null
    @SerializedName("userDetails") val userDetails : ServiceStatus_Tab_UserDetails? = null
    @SerializedName("transactionDetailsData") val transactionDetailsData : List<TransactionDetailsData>? = null
}
class TransactionDetailsData (

    @SerializedName("trxId") val  trxId: String,

)
class TransactionDetails (

    @SerializedName("id") val id : String,
//    @SerializedName("object") val object : String,
    @SerializedName("amount") val amount : Int,
    @SerializedName("amount_captured") val amount_captured : Int,
    @SerializedName("amount_refunded") val amount_refunded : Int,
    @SerializedName("application") val application : String,
    @SerializedName("application_fee") val application_fee : String,
    @SerializedName("application_fee_amount") val application_fee_amount : String,
    @SerializedName("balance_transaction") val balance_transaction : String,
//    @SerializedName("billing_details") val billing_details : Billing_details,
    @SerializedName("calculated_statement_descriptor") val calculated_statement_descriptor : String,
    @SerializedName("captured") val captured : Boolean,
    @SerializedName("created") val created : Int,
    @SerializedName("currency") val currency : String,
    @SerializedName("customer") val customer : String,
    @SerializedName("description") val description : String,
    @SerializedName("destination") val destination : String,
    @SerializedName("dispute") val dispute : String,
    @SerializedName("disputed") val disputed : Boolean,
    @SerializedName("failure_balance_transaction") val failure_balance_transaction : String,
    @SerializedName("failure_code") val failure_code : String,
    @SerializedName("failure_message") val failure_message : String,
    @SerializedName("invoice") val invoice : String,
    @SerializedName("livemode") val livemode : Boolean,
    @SerializedName("on_behalf_of") val on_behalf_of : String,
    @SerializedName("order") val order : String,
//    @SerializedName("outcome") val outcome : Outcome,
    @SerializedName("paid") val paid : Boolean,
    @SerializedName("payment_intent") val payment_intent : String,
    @SerializedName("payment_method") val payment_method : String,
//    @SerializedName("payment_method_details") val payment_method_details : Payment_method_details,
    @SerializedName("receipt_email") val receipt_email : String,
    @SerializedName("receipt_number") val receipt_number : String,
    @SerializedName("receipt_url") val receipt_url : String,
    @SerializedName("refunded") val refunded : Boolean,
//    @SerializedName("refunds") val refunds : Refunds,
    @SerializedName("review") val review : String,
    @SerializedName("shipping") val shipping : String,
//    @SerializedName("source") val source : Source,
    @SerializedName("source_transfer") val source_transfer : String,
    @SerializedName("statement_descriptor") val statement_descriptor : String,
    @SerializedName("statement_descriptor_suffix") val statement_descriptor_suffix : String,
    @SerializedName("status") val status : String,
    @SerializedName("transfer_data") val transfer_data : String,
    @SerializedName("transfer_group") val transfer_group : String
)
data class ServiceStatus_Tab_ServiceDetails (
//
//    @SerializedName("_id") val _id : String,
//    @SerializedName("serviceId") val serviceId : String,
//    @SerializedName("quantity") val quantity : Int,
//    @SerializedName("price") val price : Int,
    @SerializedName("service") val service : Service
)
class ServiceStatus_Tab_UserDetails (

    @SerializedName("_id") val _id : String,
//    @SerializedName("storeLocation") val storeLocation : StoreLocation,
//    @SerializedName("businessDetails") val businessDetails : BusinessDetails,
//    @SerializedName("businessBankingDetails") val businessBankingDetails : BusinessBankingDetails,
//    @SerializedName("serviceDetails") val serviceDetails : ServiceDetails,
//    @SerializedName("address") val address : String,
//    @SerializedName("otpVerification") val otpVerification : Boolean,
//    @SerializedName("userVerification") val userVerification : Boolean,
//    @SerializedName("profilePic") val profilePic : String,
//    @SerializedName("websiteUrl") val websiteUrl : String,
//    @SerializedName("duration") val duration : String,
//    @SerializedName("userRequestStatus") val userRequestStatus : String,
//    @SerializedName("zipCode") val zipCode : Int,
//    @SerializedName("eoriNumber") val eoriNumber : String,
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
//    @SerializedName("userType") val userType : String,
    @SerializedName("firstName") val firstName : String,
    @SerializedName("lastName") val lastName : String,
//    @SerializedName("countryCode") val countryCode : String,
//    @SerializedName("mobileNumber") val mobileNumber : String,
//    @SerializedName("email") val email : String,
//    @SerializedName("phoneNumber") val phoneNumber : String,
//    @SerializedName("storeAddress") val storeAddress : String,
//    @SerializedName("storeName") val storeName : String,
//    @SerializedName("password") val password : String,
//    @SerializedName("city") val city : String,
//    @SerializedName("country") val country : String,
//    @SerializedName("addressLine1") val addressLine1 : String,
//    @SerializedName("addressLine2") val addressLine2 : String,
//    @SerializedName("createdAt") val createdAt : String,
//    @SerializedName("updatedAt") val updatedAt : String,
//    @SerializedName("__v") val __v : Int,
//    @SerializedName("otp") val otp : Int,
//    @SerializedName("otpExpireTime") val otpExpireTime : Int,
//    @SerializedName("isReset") val isReset : Boolean,
//    @SerializedName("userUniqueId") val userUniqueId : String,
//    @SerializedName("businessDocumentUpload") val businessDocumentUpload : BusinessDocumentUpload
)
data class Service (

    @SerializedName("_id") val _id : String,
//    @SerializedName("serviceLocation") val serviceLocation : ServiceLocation,
//    @SerializedName("serviceImage") val serviceImage : List<String>,
//    @SerializedName("slots") val slots : List<String>,
    @SerializedName("status") val status : String,
//    @SerializedName("categoryId") val categoryId : String,
//    @SerializedName("subCategoryDetails") val subCategoryDetails : ServiceStatus_Tab_SubCategoryDetails,
//    @SerializedName("userId") val userId : String,
//    @SerializedName("__v") val __v : Int,
//    @SerializedName("createdAt") val createdAt : String,
//    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("userDetails") val userDetails : ServiceStatus_Tab_UserDetails,
    @SerializedName("categoryDetails") val categoryDetails : ServiceStatus_Tab_CategoryDetails
)
class ServiceStatus_Tab_CategoryDetails (

//    @SerializedName("_id") val _id : String,
//    @SerializedName("status") val status : String,
    @SerializedName("categoryName") val categoryName : String,
//    @SerializedName("categoryImage") val categoryImage : String,
//    @SerializedName("description") val description : String,
//    @SerializedName("createdAt") val createdAt : String,
//    @SerializedName("updatedAt") val updatedAt : String,
//    @SerializedName("__v") val __v : Int
)
//data class ServiceStatus_Tab_SubCategoryDetails (
//
//    @SerializedName("price") val price : Int,
//    @SerializedName("subCategoryId") val subCategoryId : String
//)