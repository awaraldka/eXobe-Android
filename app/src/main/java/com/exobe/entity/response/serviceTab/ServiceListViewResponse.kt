package com.exobe.entity.response.serviceTab


import com.google.gson.annotations.SerializedName


class ServiceListViewResponse (
    @SerializedName("result") val result: ServiceListViewResult = ServiceListViewResult(),
    @SerializedName("responseMessage") val responseMessage: String = "",
    @SerializedName("responseCode") val responseCode: Int = 0
)

class ServiceListViewResult (
    @SerializedName("taxPrice") val taxPrice: Number = 0,
    @SerializedName("orderStatus") val orderStatus: String = "",
    @SerializedName("serviceStatus") val serviceStatus: String = "",
    @SerializedName("deliveryStatus") val deliveryStatus: String = "",
    @SerializedName("paymentStatus") val paymentStatus: String = "",
    @SerializedName("slots") val slots: ArrayList<String> = ArrayList(),
    @SerializedName("status") val status: String = "",
    @SerializedName("serviceOtpVerification") val serviceOtpVerification: Boolean = false,
    @SerializedName("_id") val id: String = "",
    @SerializedName("userId") val userId: ServiceListViewUserId = ServiceListViewUserId(),
    @SerializedName("serviceDetails") val serviceDetails: ArrayList<ServiceListViewServiceDetail> = ArrayList(),
    @SerializedName("orderPrice") val orderPrice: Number = 0,
    @SerializedName("actualPrice") val actualPrice: Number = 0,
    @SerializedName("duration") val duration: String = "",
    @SerializedName("orderId") val orderId: String = "",
    @SerializedName("orderType") val orderType: String = "",
    @SerializedName("shippingFixedAddress") val shippingFixedAddress: ServiceListViewShippingFixedAddress = ServiceListViewShippingFixedAddress(),
    @SerializedName("createdAt") val createdAt: String = "",
    @SerializedName("updatedAt") val updatedAt: String = "",
    @SerializedName("__v") val v: Int = 0,
    @SerializedName("otpService") val otpService: Int = 0,
    @SerializedName("otpServiceExpireTime") val otpServiceExpireTime: String = "",
    @SerializedName("purchaseServices") val purchaseServices: ArrayList<ServiceListViewPurchaseService> = ArrayList()

)

class ServiceListViewStoreLocation (
    @SerializedName("type") val type: String = "",
    @SerializedName("coordinates") val coordinates: ArrayList<Double> = ArrayList()
)

class ServiceListViewBusinessDetails {
    @SerializedName("companyName") val companyName: String = ""
    @SerializedName("businessRegNumber") val businessRegNumber: String = ""
    @SerializedName("websiteUrl") val websiteUrl: String = ""
    @SerializedName("socialMediaAccounts") val socialMediaAccounts: String = ""
    @SerializedName("isVatRegistered") val isVatRegistered: Boolean = false
    @SerializedName("vatNumber") val vatNumber: String = ""
    @SerializedName("monthlyRevenue") val monthlyRevenue: String = ""
}

class ServiceListViewBusinessBankingDetails {
    @SerializedName("bankName") val bankName: String = ""
    @SerializedName("branchName") val branchName: String = ""
    @SerializedName("branchCode") val branchCode: String = ""
    @SerializedName("swiftCode") val swiftCode: String = ""
    @SerializedName("accountType") val accountType: String = ""
    @SerializedName("accountName") val accountName: String = ""
    @SerializedName("accountNumber") val accountNumber: String = ""
}



class ServiceListViewServiceDetail {
    @SerializedName("_id") val id: String = ""
    @SerializedName("serviceId") val serviceId: ServiceListViewServiceId = ServiceListViewServiceId()
    @SerializedName("quantity") val quantity: Int = 0
    @SerializedName("price") val price: Number = 0

}
class ServiceListViewServiceId {
    @SerializedName("serviceLocation") val serviceLocation: ServiceListViewServiceLocation = ServiceListViewServiceLocation()
    @SerializedName("serviceImage") val serviceImage: ArrayList<String> = ArrayList()

    @SerializedName("status") val status: String = ""
    @SerializedName("thumbnail") val thumbnail: String = ""
    @SerializedName("_id") val id: String = ""
    @SerializedName("description") val description: String = ""
    @SerializedName("categoryId") val categoryId: ServiceListViewCategoryId = ServiceListViewCategoryId()
    @SerializedName("subCategoryId") val subCategoryId: ServiceListViewSubCategoryId = ServiceListViewSubCategoryId()
    @SerializedName("serviceName") val serviceName: String = ""
    @SerializedName("__v") val v: Int = 0
    @SerializedName("serviceReferenceId") val serviceReferenceId: String = ""
    @SerializedName("price") val price: Number = 0
    @SerializedName("userId") val userId: ServiceListViewUserId = ServiceListViewUserId()
    @SerializedName("createdAt") val createdAt: String = ""
    @SerializedName("updatedAt") val updatedAt: String = ""

}

class ServiceListViewServiceLocation {
    @SerializedName("type") val type: String = ""
    @SerializedName("coordinates") val coordinates: ArrayList<Double> = ArrayList()
}



class ServiceListViewSubCategoryId {
    @SerializedName("thumbnail") val thumbnail: String = ""
    @SerializedName("status") val status: String = ""
    @SerializedName("_id") val id: String = ""
    @SerializedName("categoryId") val categoryId: String = ""
    @SerializedName("subCategoryName") val subCategoryName: String = ""
    @SerializedName("__v") val v: Int = 0
    @SerializedName("createdAt") val createdAt: String = ""
    @SerializedName("updatedAt") val updatedAt: String = ""
}

class ServiceListViewUserId (
    @SerializedName("storeLocation") val storeLocation: ServiceListViewStoreLocation = ServiceListViewStoreLocation(),
    @SerializedName("businessDetails") val businessDetails: ServiceListViewBusinessDetails = ServiceListViewBusinessDetails(),
    @SerializedName("businessBankingDetails") val businessBankingDetails: ServiceListViewBusinessBankingDetails = ServiceListViewBusinessBankingDetails(),
    @SerializedName("address") val address: String = "",
    @SerializedName("otpVerification") val otpVerification: Boolean = false,
    @SerializedName("userVerification") val userVerification: Boolean = false,
    @SerializedName("profilePic") val profilePic: String = "",
    @SerializedName("websiteUrl") val websiteUrl: String = "",
    @SerializedName("duration") val duration: String = "",
    @SerializedName("userRequestStatus") val userRequestStatus: String = "",
    @SerializedName("zipCode") val zipCode: String = "",
    @SerializedName("eoriNumber") val eoriNumber: String = "",
    @SerializedName("additionalDocName") val additionalDocName: String = "",
    @SerializedName("additionalDocument") val additionalDocument: String = "",
    @SerializedName("DOB") val dOB: String = "",
    @SerializedName("ownerFirstName") val ownerFirstName: String = "",
    @SerializedName("ownerLastName") val ownerLastName: String = "",
    @SerializedName("ownerEmail") val ownerEmail: String = "",
    @SerializedName("noOfUniqueProducts") val noOfUniqueProducts: Number = 0,

    @SerializedName("keepStock") val keepStock: Boolean = false,
    @SerializedName("isPhysicalStore") val isPhysicalStore: Boolean = false,
    @SerializedName("howManyStoresDoYouHave") val howManyStoresDoYouHave: String = "",

    @SerializedName("comments") val comments: String = "",
    @SerializedName("completeProfile") val completeProfile: Boolean = false,
    @SerializedName("flag") val flag: Int = 0,
    @SerializedName("placeOrderCount") val placeOrderCount: Int = 0,
    @SerializedName("serviceOrderCount") val serviceOrderCount: Int = 0,
    @SerializedName("receiveOrderCount") val receiveOrderCount: Int = 0,
    @SerializedName("status") val status: String = "",
    @SerializedName("thumbnail") val thumbnail: String = "",
    @SerializedName("updateDocumentStatus") val updateDocumentStatus: String = "",
    @SerializedName("_id") val id: String = "",
    @SerializedName("addressLine1") val addressLine1: String = "",
    @SerializedName("addressLine2") val addressLine2: String = "",
    @SerializedName("city") val city: String = "",
    @SerializedName("country") val country: String = "",
    @SerializedName("countryCode") val countryCode: String = "",
    @SerializedName("countryIsoCode") val countryIsoCode: String = "",
    @SerializedName("deviceToken") val deviceToken: String = "",
    @SerializedName("deviceType") val deviceType: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("firstName") val firstName: String = "",
    @SerializedName("lastName") val lastName: String = "",
    @SerializedName("mobileNumber") val mobileNumber: String = "",
    @SerializedName("password") val password: String = "",
    @SerializedName("phoneNumber") val phoneNumber: String = "",
    @SerializedName("state") val state: String = "",
    @SerializedName("stateIsoCode") val stateIsoCode: String = "",
    @SerializedName("userType") val userType: String = "",
    @SerializedName("createdAt") val createdAt: String = "",
    @SerializedName("updatedAt") val updatedAt: String = "",
    @SerializedName("__v") val v: Int = 0,
    @SerializedName("primaryAddressId") val primaryAddressId: String = "",
    @SerializedName("userUniqueId") val userUniqueId: String = ""
)



class ServiceListViewShippingFixedAddress (
    @SerializedName("location") val location: ServiceListViewLocation = ServiceListViewLocation(),
    @SerializedName("isPrimary") val isPrimary: Boolean = false,
    @SerializedName("status") val status: String = "",
    @SerializedName("address") val address: String = "",
    @SerializedName("addressLine1") val addressLine1: String = "",
    @SerializedName("addressLine2") val addressLine2: String = "",
    @SerializedName("city") val city: String = "",
    @SerializedName("country") val country: String = "",
    @SerializedName("countryCode") val countryCode: String = "",
    @SerializedName("countryIsoCode") val countryIsoCode: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("firstName") val firstName: String = "",
    @SerializedName("lastName") val lastName: String = "",
    @SerializedName("mobileNumber") val mobileNumber: String = "",
    @SerializedName("state") val state: String = "",
    @SerializedName("stateIsoCode") val stateIsoCode: String = "",
    @SerializedName("zipCode") val zipCode: String = ""

)


class ServiceListViewLocation {
    @SerializedName("type") val type: String = ""
    @SerializedName("coordinates") val coordinates: ArrayList<Double> = ArrayList()
}


class ServiceListViewPurchaseService {
    @SerializedName("subCategoryDetails") val subCategoryDetails: ServiceListViewSubCategoryDetails = ServiceListViewSubCategoryDetails()
    @SerializedName("servicesDetails") val servicesDetails: ArrayList<ServiceListViewServicesDetail> = ArrayList()

}

class ServiceListViewSubCategoryDetails {
    @SerializedName("thumbnail") val thumbnail: String = ""
    @SerializedName("status") val status: String = ""
    @SerializedName("_id") val id: String = ""
    @SerializedName("categoryId") val categoryId: String = ""
    @SerializedName("subCategoryName") val subCategoryName: String = ""
    @SerializedName("__v") val v: Int = 0
    @SerializedName("createdAt") val createdAt: String = ""
    @SerializedName("updatedAt") val updatedAt: String = ""
}

class ServiceListViewServicesDetail {
    @SerializedName("_id") val id: String = ""
    @SerializedName("serviceId") val serviceId: PServiceListViewServiceId = PServiceListViewServiceId()
    @SerializedName("quantity") val quantity: Int = 0
    @SerializedName("price") val price: Number = 0
}
class PServiceListViewServiceId {
    @SerializedName("serviceLocation") val serviceLocation: ServiceListViewServiceLocation = ServiceListViewServiceLocation()
    @SerializedName("serviceImage") val serviceImage: ArrayList<String> = ArrayList()

    @SerializedName("status") val status: String = ""
    @SerializedName("thumbnail") val thumbnail: String = ""
    @SerializedName("_id") val id: String = ""
    @SerializedName("description") val description: String = ""
    @SerializedName("categoryId") val categoryId: ServiceListViewCategoryId = ServiceListViewCategoryId()
    @SerializedName("subCategoryId") val subCategoryId: ServiceListViewSubCategoryId = ServiceListViewSubCategoryId()
    @SerializedName("serviceName") val serviceName: String = ""
    @SerializedName("__v") val v: Int = 0
    @SerializedName("serviceReferenceId") val serviceReferenceId: String = ""
    @SerializedName("price") val price: Number = 0
    @SerializedName("userId") val userId: ServiceListViewUserId = ServiceListViewUserId()
    @SerializedName("createdAt") val createdAt: String = ""
    @SerializedName("updatedAt") val updatedAt: String = ""


}

class ServiceListViewCategoryId {
    @SerializedName("thumbnail") val thumbnail: String = ""
    @SerializedName("status") val status: String = ""
    @SerializedName("_id") val id: String = ""
    @SerializedName("categoryName") val categoryName: String = ""
    @SerializedName("categoryImage") val categoryImage: String = ""
    @SerializedName("description") val description: String = ""
    @SerializedName("createdAt") val createdAt: String = ""
    @SerializedName("updatedAt") val updatedAt: String = ""
    @SerializedName("__v") val v: Int = 0
}
