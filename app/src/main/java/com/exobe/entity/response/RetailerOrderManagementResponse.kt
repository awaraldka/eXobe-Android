package com.exobe.entity.response


import com.exobe.entity.request.PriceSizeDetailsRequest
import com.google.gson.annotations.SerializedName


class RetailerOrderManagementResponse {
    @SerializedName("result")
    val result: RetailerOrderManagementResult = RetailerOrderManagementResult()
    @SerializedName("responseMessage")
    val responseMessage: String = ""
    @SerializedName("responseCode")
    val responseCode: Int = 0
}

class RetailerOrderManagementResult {
    @SerializedName("page")
    val page: Int = 0
    @SerializedName("limit")
    val limit: Int = 0
    @SerializedName("remainingItems")
    val remainingItems: Int = 0
    @SerializedName("count")
    val count: Int = 0
    @SerializedName("docs")
    val docs: ArrayList<RetailerOrderManagementDoc> = ArrayList()
    @SerializedName("totalPages")
    val totalPages: Int = 0
}

class RetailerOrderManagementDoc {
    @SerializedName("orderStatus")
    val orderStatus: String = ""
    @SerializedName("deliveryStatus")
    val deliveryStatus: String = ""
    @SerializedName("paymentStatus")
    val paymentStatus: String = ""
    @SerializedName("status")
    val status: String = ""
    @SerializedName("_id")
    val id: String = ""
    @SerializedName("actualPrice")
    val actualPrice: Number = 0
    @SerializedName("orderPrice")
    val orderPrice: Number = 0
    @SerializedName("userId")
    val userId: RetailerOrderManagementUserId = RetailerOrderManagementUserId()
//    @SerializedName("productDetails")
//    val productDetails: List<RetailerOrderManagementProductDetail> = listOf()
    @SerializedName("shippingAddress")
    val shippingAddress: RetailerOrderManagementShippingAddress = RetailerOrderManagementShippingAddress()
    @SerializedName("shippingFixedAddress")
    val shippingFixedAddress: RetailerOrderManagementShippingFixedAddress = RetailerOrderManagementShippingFixedAddress()
    @SerializedName("orderType")
    val orderType: String = ""
    @SerializedName("orderId")
    val orderId: String = ""
    @SerializedName("createdAt")
    val createdAt: String = ""
    @SerializedName("updatedAt")
    val updatedAt: String = ""
    @SerializedName("__v")
    val v: Int = 0
    @SerializedName("myOrders")
    val myOrders: ArrayList<RetailerOrderManagementMyOrder> = ArrayList()
    @SerializedName("myOrderPrice")
    val myOrderPrice: Number = 0

}

class RetailerOrderManagementUserId {
    @SerializedName("storeLocation")
    val storeLocation: RetailerOrderManagementStoreLocation = RetailerOrderManagementStoreLocation()
    @SerializedName("businessDetails")
    val businessDetails: RetailerOrderManagementBusinessDetails = RetailerOrderManagementBusinessDetails()
    @SerializedName("businessBankingDetails")
    val businessBankingDetails: RetailerOrderManagementBusinessBankingDetails = RetailerOrderManagementBusinessBankingDetails()
    @SerializedName("serviceDetails")
    val serviceDetails: RetailerOrderManagementServiceDetails = RetailerOrderManagementServiceDetails()
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
    val noOfUniqueProducts: Number = 0

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
    @SerializedName("state")
    val state: String = ""
    @SerializedName("stateIsoCode")
    val stateIsoCode: String = ""
    @SerializedName("userType")
    val userType: String = ""
    @SerializedName("otp")
    val otp: String = ""
    @SerializedName("otpExpireTime")
    val otpExpireTime: String = ""
    @SerializedName("createdAt")
    val createdAt: String = ""
    @SerializedName("updatedAt")
    val updatedAt: String = ""
    @SerializedName("__v")
    val v: Int = 0
    @SerializedName("primaryAddressId")
    val primaryAddressId: String = ""

}


class RetailerOrderManagementShippingAddress {
    @SerializedName("location")
    val location: RetailerOrderManagementLocation = RetailerOrderManagementLocation()
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
    class RetailerOrderManagementLocation {
        @SerializedName("type")
        val type: String = ""
        @SerializedName("coordinates")
        val coordinates: ArrayList<Double> = ArrayList()
    }
}

class RetailerOrderManagementShippingFixedAddress {
    @SerializedName("location")
    val location: RetailerOrderManagementLocation = RetailerOrderManagementLocation()
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
    class RetailerOrderManagementLocation {
        @SerializedName("type")
        val type: String = ""
        @SerializedName("coordinates")
        val coordinates: ArrayList<Double> = ArrayList()
    }
}

class RetailerOrderManagementMyOrder {
    @SerializedName("_id")
    val id: String = ""
    @SerializedName("priceSizeDetails")
    var priceSizeDetails : PriceSizeDetailsRequest? = null
    @SerializedName("productId")
    val productId: RetailerOrderManagementProductId = RetailerOrderManagementProductId()
    @SerializedName("quantity")
    val quantity: Int = 0
    @SerializedName("price")
    val price: Number = 0

}
class RetailerOrderManagementProductId {
    @SerializedName("productImage")
    val productImage: List<String> = listOf()
    @SerializedName("avgRatingsProduct")
    val avgRatingsProduct: Int = 0
    @SerializedName("discount")
    val discount: Int = 0
    @SerializedName("productFor")
    val productFor: String = ""
    @SerializedName("thumbnail")
    val thumbnail: String = ""
    @SerializedName("expectedDeliveryDays")
    val expectedDeliveryDays: String = ""
    @SerializedName("status")
    val status: String = ""
    @SerializedName("_id")
    val id: String = ""
//    @SerializedName("categoryId")
//    val categoryId: String = ""
    @SerializedName("description")
    val description: String = ""
    @SerializedName("price")
    val price: Number = 0
    @SerializedName("productName")
    val productName: String = ""
    @SerializedName("quantity")
    val quantity: String = ""
//    @SerializedName("subCategoryId")
//    val subCategoryId: String = ""
    @SerializedName("userId")
    val userId: RetailerOrderManagementUserId = RetailerOrderManagementUserId()
    @SerializedName("createdAt")
    val createdAt: String = ""
    @SerializedName("updatedAt")
    val updatedAt: String = ""
    @SerializedName("__v")
    val v: Int = 0
    class RetailerOrderManagementUserId {
        @SerializedName("storeLocation")
        val storeLocation: RetailerOrderManagementStoreLocation = RetailerOrderManagementStoreLocation()
        @SerializedName("businessDetails")
        val businessDetails: RetailerOrderManagementBusinessDetails = RetailerOrderManagementBusinessDetails()
        @SerializedName("businessBankingDetails")
        val businessBankingDetails: RetailerOrderManagementBusinessBankingDetails = RetailerOrderManagementBusinessBankingDetails()
        @SerializedName("businessDocumentUpload")
        val businessDocumentUpload: RetailerOrderManagementBusinessDocumentUpload = RetailerOrderManagementBusinessDocumentUpload()
        @SerializedName("serviceDetails")
        val serviceDetails: RetailerOrderManagementServiceDetails = RetailerOrderManagementServiceDetails()
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
        @SerializedName("listOfBrandOrProducts")
        val listOfBrandOrProducts: List<String> = listOf()
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
        @SerializedName("deviceToken")
        val deviceToken: String = ""
        @SerializedName("deviceType")
        val deviceType: String = ""
        @SerializedName("storeAddress")
        val storeAddress: String = ""
        @SerializedName("storeContactNo")
        val storeContactNo: String = ""
        @SerializedName("storeName")
        val storeName: String = ""
        @SerializedName("userUniqueId")
        val userUniqueId: String = ""
        @SerializedName("otp")
        val otp: String = ""
        @SerializedName("otpExpireTime")
        val otpExpireTime: String = ""
        @SerializedName("isReset")
        val isReset: Boolean = false

    }
}

class RetailerOrderManagementStoreLocation {
    @SerializedName("type")
    val type: String = ""
    @SerializedName("coordinates")
    val coordinates: List<Double> = listOf()
}

class RetailerOrderManagementBusinessDetails {
    @SerializedName("companyName")
    val companyName: String = ""
    @SerializedName("businessRegNumber")
    val businessRegNumber: String = ""
    @SerializedName("websiteUrl")
    val websiteUrl: String = ""
    @SerializedName("socialMediaAccounts")
    val socialMediaAccounts: String = ""
    @SerializedName("isVatRegistered")
    val isVatRegistered: Boolean = false
    @SerializedName("vatNumber")
    val vatNumber: String = ""
    @SerializedName("monthlyRevenue")
    val monthlyRevenue: String = ""
}

class RetailerOrderManagementBusinessBankingDetails {
    @SerializedName("bankName")
    val bankName: String = ""
    @SerializedName("branchName")
    val branchName: String = ""
    @SerializedName("branchCode")
    val branchCode: String = ""
    @SerializedName("swiftCode")
    val swiftCode: String = ""
    @SerializedName("accountType")
    val accountType: String = ""
    @SerializedName("accountName")
    val accountName: String = ""
    @SerializedName("accountNumber")
    val accountNumber: String = ""
}

class RetailerOrderManagementBusinessDocumentUpload {
    @SerializedName("cirtificationOfIncorporation")
    val cirtificationOfIncorporation: RetailerOrderManagementCirtificationOfIncorporation = RetailerOrderManagementCirtificationOfIncorporation()
    @SerializedName("VatRegConfirmationDocs")
    val vatRegConfirmationDocs: RetailerOrderManagementVatRegConfirmationDocs = RetailerOrderManagementVatRegConfirmationDocs()
    @SerializedName("directConsentForm")
    val directConsentForm: RetailerOrderManagementDirectConsentForm = RetailerOrderManagementDirectConsentForm()
    @SerializedName("directorId")
    val directorId: RetailerOrderManagementDirectorId = RetailerOrderManagementDirectorId()
    @SerializedName("bankConfirmationLetter")
    val bankConfirmationLetter: RetailerOrderManagementBankConfirmationLetter = RetailerOrderManagementBankConfirmationLetter()
    class RetailerOrderManagementCirtificationOfIncorporation {
        @SerializedName("frontImage")
        val frontImage: String = ""
        @SerializedName("backImage")
        val backImage: String = ""
        @SerializedName("pdf")
        val pdf: String = ""
    }

    class RetailerOrderManagementVatRegConfirmationDocs {
        @SerializedName("frontImage")
        val frontImage: String = ""
        @SerializedName("backImage")
        val backImage: String = ""
        @SerializedName("pdf")
        val pdf: String = ""
    }

    class RetailerOrderManagementDirectConsentForm {
        @SerializedName("frontImage")
        val frontImage: String = ""
        @SerializedName("backImage")
        val backImage: String = ""
        @SerializedName("pdf")
        val pdf: String = ""
    }

    class RetailerOrderManagementDirectorId {
        @SerializedName("frontImage")
        val frontImage: String = ""
        @SerializedName("backImage")
        val backImage: String = ""
        @SerializedName("pdf")
        val pdf: String = ""
    }

    class RetailerOrderManagementBankConfirmationLetter {
        @SerializedName("frontImage")
        val frontImage: String = ""
        @SerializedName("backImage")
        val backImage: String = ""
        @SerializedName("pdf")
        val pdf: String = ""
    }
}

class RetailerOrderManagementServiceDetails {
    @SerializedName("noOfUniqueService")
    val noOfUniqueService: Int = 0

    @SerializedName("comments")
    val comments: String = ""

}



