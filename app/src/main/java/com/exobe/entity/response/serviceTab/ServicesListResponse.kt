package com.exobe.entity.response.serviceTab


import com.exobe.entity.response.TransactionDetailsData
import com.google.gson.annotations.SerializedName


class ServicesListResponse {
    @SerializedName("result")
    val result: ServicesListResult = ServicesListResult()

    @SerializedName("responseMessage")
    val responseMessage: String = ""

    @SerializedName("responseCode")
    val responseCode: Int = 0

}

class ServicesListResult {
    @SerializedName("page")
    val page: Int = 0

    @SerializedName("limit")
    val limit: Int = 0

    @SerializedName("remainingItems")
    val remainingItems: Int = 0

    @SerializedName("count")
    val count: Int = 0

    @SerializedName("totalPages")
    val totalPages: Int = 0

    @SerializedName("docs")
    val docs: List<ServicesListDoc> = ArrayList()
}

class ServicesListDoc {
    @SerializedName("taxPrice")
    val taxPrice: Number = 0

    //    @SerializedName("dealId")
//    val dealId: ArrayList<ServicesListAny> = ArrayList()
    @SerializedName("orderStatus")
    val orderStatus: String = ""

//    @SerializedName("deliveryStatus")
//    val deliveryStatus: String = ""

    @SerializedName("paymentStatus")
    val paymentStatus: String = ""
//
//    @SerializedName("slots")
//    val slots: List<String> = listOf()

//    @SerializedName("status")
//    val status: String = ""

//    @SerializedName("serviceOtpVerification")
//    val serviceOtpVerification: Boolean = false

    @SerializedName("_id")
    val id: String = ""

    @SerializedName("userId")
    val userId: ServicesListUserId = ServicesListUserId()

    @SerializedName("serviceDetails")
    val serviceDetails: ArrayList<ServicesListServiceDetail> = ArrayList()

//    @SerializedName("orderPrice")
//    val orderPrice: Int = 0

//    @SerializedName("actualPrice")
//    val actualPrice: Int = 0

//    @SerializedName("shippingAddress")
//    val shippingAddress: ServicesListShippingAddress = ServicesListShippingAddress()

//    @SerializedName("duration")
//    val duration: String = ""

    @SerializedName("orderId")
    val orderId: String = ""

//    @SerializedName("orderType")
//    val orderType: String = ""

    @SerializedName("shippingFixedAddress")
    val shippingFixedAddress: ServicesListShippingFixedAddress = ServicesListShippingFixedAddress()

    //    @SerializedName("productDetails")
//    val productDetails: List<ServicesListAny> = listOf()
    @SerializedName("createdAt")
    val createdAt: String = ""

//    @SerializedName("updatedAt")
//    val updatedAt: String = ""

//    @SerializedName("__v")
//    val v: Int = 0
//
//    @SerializedName("otpService")
//    val otpService: Int = 0
//
//    @SerializedName("otpServiceExpireTime")
//    val otpServiceExpireTime: String = ""
    @SerializedName("transactionDetails")
    val transactionDetailsData: TransactionDetailsData? = null


}

class TransactionDetailsData(

    @SerializedName("trxId") val trxId: String,

    )

//class ServicesListUserId {
//    @SerializedName("address")
//    val address: String = ""
//    @SerializedName("otpVerification")
//    val otpVerification: Boolean = false
//    @SerializedName("userVerification")
//    val userVerification: Boolean = false
//    @SerializedName("profilePic")
//    val profilePic: String = ""
//    @SerializedName("websiteUrl")
//    val websiteUrl: String = ""
//    @SerializedName("duration")
//    val duration: String = ""
//    @SerializedName("userRequestStatus")
//    val userRequestStatus: String = ""
//    @SerializedName("zipCode")
//    val zipCode: String = ""
//    @SerializedName("eoriNumber")
//    val eoriNumber: String = ""
//    @SerializedName("additionalDocName")
//    val additionalDocName: String = ""
//    @SerializedName("additionalDocument")
//    val additionalDocument: String = ""
//    @SerializedName("DOB")
//    val dOB: String = ""
//    @SerializedName("ownerFirstName")
//    val ownerFirstName: String = ""
//    @SerializedName("ownerLastName")
//    val ownerLastName: String = ""
//    @SerializedName("ownerEmail")
//    val ownerEmail: String = ""
//    @SerializedName("noOfUniqueProducts")
//    val noOfUniqueProducts: Int = 0
////    @SerializedName("listOfBrandOrProducts")
////    val listOfBrandOrProducts: List<ServicesListAny> = listOf()
//    @SerializedName("keepStock")
//    val keepStock: Boolean = false
//    @SerializedName("isPhysicalStore")
//    val isPhysicalStore: Boolean = false
//    @SerializedName("howManyStoresDoYouHave")
//    val howManyStoresDoYouHave: String = ""
////    @SerializedName("preferredSupplierOrWholeSalerId")
////    val preferredSupplierOrWholeSalerId: List<ServicesListAny> = listOf()
//    @SerializedName("comments")
//    val comments: String = ""
//    @SerializedName("completeProfile")
//    val completeProfile: Boolean = false
//    @SerializedName("flag")
//    val flag: Int = 0
//    @SerializedName("placeOrderCount")
//    val placeOrderCount: Int = 0
//    @SerializedName("serviceOrderCount")
//    val serviceOrderCount: Int = 0
//    @SerializedName("receiveOrderCount")
//    val receiveOrderCount: Int = 0
//    @SerializedName("status")
//    val status: String = ""
//    @SerializedName("thumbnail")
//    val thumbnail: String = ""
//    @SerializedName("updateDocumentStatus")
//    val updateDocumentStatus: String = ""
//    @SerializedName("_id")
//    val id: String = ""
//    @SerializedName("addressLine1")
//    val addressLine1: String = ""
//    @SerializedName("addressLine2")
//    val addressLine2: String = ""
//    @SerializedName("city")
//    val city: String = ""
//    @SerializedName("country")
//    val country: String = ""
//    @SerializedName("countryCode")
//    val countryCode: String = ""
//    @SerializedName("countryIsoCode")
//    val countryIsoCode: String = ""
//    @SerializedName("deviceToken")
//    val deviceToken: String = ""
//    @SerializedName("deviceType")
//    val deviceType: String = ""
//    @SerializedName("email")
//    val email: String = ""
//    @SerializedName("firstName")
//    val firstName: String = ""
//    @SerializedName("lastName")
//    val lastName: String = ""
//    @SerializedName("mobileNumber")
//    val mobileNumber: String = ""
//    @SerializedName("password")
//    val password: String = ""
//    @SerializedName("state")
//    val state: String = ""
//    @SerializedName("stateIsoCode")
//    val stateIsoCode: String = ""
//    @SerializedName("userType")
//    val userType: String = ""
//    @SerializedName("otp")
//    val otp: String = ""
//    @SerializedName("otpExpireTime")
//    val otpExpireTime: String = ""
//    @SerializedName("createdAt")
//    val createdAt: String = ""
//    @SerializedName("updatedAt")
//    val updatedAt: String = ""
//    @SerializedName("__v")
//    val v: Int = 0
//    @SerializedName("primaryAddressId")
//    val primaryAddressId: String = ""
////    class ServicesListStoreLocation {
////        @SerializedName("type")
////        val type: String = ""
////        @SerializedName("coordinates")
////        val coordinates: List<Double> = listOf()
////    }
////
////    class ServicesListBusinessDetails {
////        @SerializedName("companyName")
////        val companyName: String = ""
////        @SerializedName("businessRegNumber")
////        val businessRegNumber: String = ""
////        @SerializedName("websiteUrl")
////        val websiteUrl: String = ""
////        @SerializedName("socialMediaAccounts")
////        val socialMediaAccounts: String = ""
////        @SerializedName("isVatRegistered")
////        val isVatRegistered: Boolean = false
////        @SerializedName("vatNumber")
////        val vatNumber: String = ""
////        @SerializedName("monthlyRevenue")
////        val monthlyRevenue: String = ""
////    }
////
////    class ServicesListBusinessBankingDetails {
////        @SerializedName("bankName")
////        val bankName: String = ""
////        @SerializedName("branchName")
////        val branchName: String = ""
////        @SerializedName("branchCode")
////        val branchCode: String = ""
////        @SerializedName("swiftCode")
////        val swiftCode: String = ""
////        @SerializedName("accountType")
////        val accountType: String = ""
////        @SerializedName("accountName")
////        val accountName: String = ""
////        @SerializedName("accountNumber")
////        val accountNumber: String = ""
////    }
////
////    class ServicesListServiceDetails {
////        @SerializedName("noOfUniqueService")
////        val noOfUniqueService: Int = 0
////        @SerializedName("preferredSupplierOrWholeSalerId")
////        val preferredSupplierOrWholeSalerId: List<ServicesListAny> = listOf()
////        @SerializedName("comments")
////        val comments: String = ""
////        @SerializedName("listOfServices")
////        val listOfServices: List<ServicesListAny> = listOf()
////    }
//}

class ServicesListServiceDetail {
    @SerializedName("_id")
    val id: String = ""

    @SerializedName("serviceId")
    val serviceId: ServicesListServiceId = ServicesListServiceId()

    @SerializedName("quantity")
    val quantity: Int = 0

    @SerializedName("price")
    val price: Number = 0
}

class ServicesListServiceId {
//    @SerializedName("serviceLocation")
//    val serviceLocation: ServicesListServiceLocation = ServicesListServiceLocation()

//    @SerializedName("serviceImage")
//    val serviceImage: ArrayList<String> = ArrayList()
//
//    @SerializedName("slots")
//    val slots: ArrayList<String> = ArrayList()
//
//    @SerializedName("status")
//    val status: String = ""
//
//    @SerializedName("thumbnail")
//    val thumbnail: String = ""
//
//    @SerializedName("_id")
//    val id: String = ""
//
//    @SerializedName("description")
//    val description: String = ""

    @SerializedName("categoryId")
    val categoryId = ServicesListCategoryId()
//
//    @SerializedName("subCategoryId")
//    val subCategoryId: String = ""

//    @SerializedName("serviceName")
//    val serviceName: String = ""
//
//    @SerializedName("__v")
//    val v: Int = 0
//
//    @SerializedName("serviceReferenceId")
//    val serviceReferenceId: String = ""
//
//    @SerializedName("price")
//    val price: Int = 0

    @SerializedName("userId")
    val userId: ServicesListUserId = ServicesListUserId()
//
//    @SerializedName("createdAt")
//    val createdAt: String = ""
//
//    @SerializedName("updatedAt")
//    val updatedAt: String = ""

}

class ServicesListServiceLocation {
    @SerializedName("type")
    val type: String = ""

    @SerializedName("coordinates")
    val coordinates: List<Double> = listOf()
}

class ServicesListCategoryId {
    @SerializedName("categoryName")
    val categoryName: String = ""

}

class ServicesListUserId {
//    @SerializedName("address")
//    val address: String = ""
//
//    @SerializedName("otpVerification")
//    val otpVerification: Boolean = false
//
//    @SerializedName("userVerification")
//    val userVerification: Boolean = false
//
//    @SerializedName("profilePic")
//    val profilePic: String = ""
//
//    @SerializedName("websiteUrl")
//    val websiteUrl: String = ""
//
//    @SerializedName("duration")
//    val duration: String = ""
//
//    @SerializedName("userRequestStatus")
//    val userRequestStatus: String = ""
//
//    @SerializedName("zipCode")
//    val zipCode: String = ""
//
//    @SerializedName("eoriNumber")
//    val eoriNumber: String = ""
//
//    @SerializedName("additionalDocName")
//    val additionalDocName: String = ""
//
//    @SerializedName("additionalDocument")
//    val additionalDocument: String = ""
//
//    @SerializedName("DOB")
//    val dOB: String = ""
//
//    @SerializedName("ownerFirstName")
//    val ownerFirstName: String = ""
//
//    @SerializedName("ownerLastName")
//    val ownerLastName: String = ""
//
//    @SerializedName("ownerEmail")
//    val ownerEmail: String = ""
//
//    @SerializedName("noOfUniqueProducts")
//    val noOfUniqueProducts: Int = 0
//
//    @SerializedName("listOfBrandOrProducts")
//    val listOfBrandOrProducts: ArrayList<String> = ArrayList()
//
//    @SerializedName("keepStock")
//    val keepStock: Boolean = false
//
//    @SerializedName("isPhysicalStore")
//    val isPhysicalStore: Boolean = false
//
//    @SerializedName("howManyStoresDoYouHave")
//    val howManyStoresDoYouHave: String = ""
//
//    @SerializedName("preferredSupplierOrWholeSalerId")
//    val preferredSupplierOrWholeSalerId: ArrayList<String> = ArrayList()
//
//    @SerializedName("comments")
//    val comments: String = ""
//
//    @SerializedName("completeProfile")
//    val completeProfile: Boolean = false
//
//    @SerializedName("flag")
//    val flag: Int = 0
//
//    @SerializedName("placeOrderCount")
//    val placeOrderCount: Int = 0
//
//    @SerializedName("serviceOrderCount")
//    val serviceOrderCount: Int = 0
//
//    @SerializedName("receiveOrderCount")
//    val receiveOrderCount: Int = 0
//
//    @SerializedName("status")
//    val status: String = ""
//
//    @SerializedName("thumbnail")
//    val thumbnail: String = ""
//
//    @SerializedName("updateDocumentStatus")
//    val updateDocumentStatus: String = ""
//
//    @SerializedName("_id")
//    val id: String = ""
//
//    @SerializedName("addressLine1")
//    val addressLine1: String = ""
//
//    @SerializedName("addressLine2")
//    val addressLine2: String = ""
//
//    @SerializedName("city")
//    val city: String = ""
//
//    @SerializedName("country")
//    val country: String = ""
//
//    @SerializedName("countryCode")
//    val countryCode: String = ""
//
//    @SerializedName("countryIsoCode")
//    val countryIsoCode: String = ""
//
//    @SerializedName("deviceToken")
//    val deviceToken: String = ""
//
//    @SerializedName("deviceType")
//    val deviceType: String = ""
//
//    @SerializedName("email")
//    val email: String = ""

    @SerializedName("firstName")
    val firstName: String = ""

    @SerializedName("lastName")
    val lastName: String = ""

//    @SerializedName("mobileNumber")
//    val mobileNumber: String = ""
//
//    @SerializedName("password")
//    val password: String = ""
//
//    @SerializedName("phoneNumber")
//    val phoneNumber: String = ""
//
//    @SerializedName("state")
//    val state: String = ""
//
//    @SerializedName("stateIsoCode")
//    val stateIsoCode: String = ""
//
//    @SerializedName("userType")
//    val userType: String = ""
//
//    @SerializedName("createdAt")
//    val createdAt: String = ""
//
//    @SerializedName("updatedAt")
//    val updatedAt: String = ""
//
//    @SerializedName("__v")
//    val v: Int = 0
//
//    @SerializedName("primaryAddressId")
//    val primaryAddressId: String = ""
//
//    @SerializedName("userUniqueId")
//    val userUniqueId: String = ""

}


class ServicesListShippingAddress {
    @SerializedName("location")
    val location: ServicesListLocation = ServicesListLocation()

    @SerializedName("isPrimary")
    val isPrimary: Boolean = false

    @SerializedName("status")
    val status: String = ""

    @SerializedName("_id")
    val id: String = ""

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

    @SerializedName("userId")
    val userId: String = ""

    @SerializedName("createdAt")
    val createdAt: String = ""

    @SerializedName("updatedAt")
    val updatedAt: String = ""

    @SerializedName("__v")
    val v: Int = 0

    class ServicesListLocation {
        @SerializedName("type")
        val type: String = ""

        @SerializedName("coordinates")
        val coordinates: ArrayList<Double> = ArrayList()
    }
}

class ServicesListShippingFixedAddress {
    @SerializedName("location")
    val location: ServicesListLocation = ServicesListLocation()

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

    class ServicesListLocation {
        @SerializedName("type")
        val type: String = ""

        @SerializedName("coordinates")
        val coordinates: ArrayList<Double> = ArrayList()
    }
}
