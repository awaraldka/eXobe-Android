package com.exobe.entity.response


import com.google.gson.annotations.SerializedName
import java.io.Serializable


class NewOrderServiceReqResponse : Serializable{
    @SerializedName("result") val result: NewOrderServiceReqResult = NewOrderServiceReqResult()
    @SerializedName("responseMessage") val responseMessage: String = ""
    @SerializedName("responseCode") val responseCode: Int = 0
}

class NewOrderServiceReqResult : Serializable{
    @SerializedName("docs") val docs: List<NewOrderServiceReqDoc> = listOf()
    @SerializedName("total") val total: Int = 0
    @SerializedName("limit") val limit: Int = 0
    @SerializedName("page") val page: Int = 0
    @SerializedName("pages") val pages: Int = 0
}

class NewOrderServiceReqDoc : Serializable{
    @SerializedName("taxPrice") val taxPrice: Number = 0
    @SerializedName("orderStatus") val orderStatus: String = ""
    @SerializedName("serviceStatus") val serviceStatus: String = ""
    @SerializedName("deliveryStatus") val deliveryStatus: String = ""
    @SerializedName("paymentStatus") val paymentStatus: String = ""
    @SerializedName("slots") val slots: List<String> = listOf()
    @SerializedName("status") val status: String = ""
    @SerializedName("serviceOtpVerification") val serviceOtpVerification: Boolean = false
    @SerializedName("_id") val id: String = ""
    @SerializedName("userId") val userId: NewOrderServiceReqUserId = NewOrderServiceReqUserId()
    @SerializedName("serviceDetails") val serviceDetails: List<NewOrderServiceReqServiceDetail> = listOf()
    @SerializedName("orderPrice") val orderPrice: Number = 0
    @SerializedName("actualPrice") val actualPrice: Number = 0
    @SerializedName("deliveryFee") val deliveryFee: Number = 0
    @SerializedName("shippingAddress") val shippingAddress: NewOrderServiceReqShippingAddress = NewOrderServiceReqShippingAddress()
    @SerializedName("duration") val duration: String = ""
    @SerializedName("orderId") val orderId: String = ""
    @SerializedName("orderType") val orderType: String = ""
    @SerializedName("shippingFixedAddress") val shippingFixedAddress: NewOrderServiceReqShippingFixedAddress = NewOrderServiceReqShippingFixedAddress()

    @SerializedName("createdAt") val createdAt: String = ""
    @SerializedName("updatedAt") val updatedAt: String = ""
    @SerializedName("__v") val v: Int = 0
    @SerializedName("otpService") val otpService: Int = 0
    @SerializedName("otpServiceExpireTime") val otpServiceExpireTime: String = ""
    @SerializedName("orderTracking") val orderTracking  : ArrayList<OrderTracking>  = arrayListOf()
}

class ServiceReqUserId : Serializable{
    @SerializedName("storeLocation") val storeLocation: NewOrderServiceReqStoreLocation = NewOrderServiceReqStoreLocation()
    @SerializedName("businessDetails") val businessDetails: NewOrderServiceReqBusinessDetails = NewOrderServiceReqBusinessDetails()
    @SerializedName("businessBankingDetails") val businessBankingDetails: NewOrderServiceReqBusinessBankingDetails = NewOrderServiceReqBusinessBankingDetails()
    @SerializedName("serviceDetails") val serviceDetails: NewOrderServiceReqServiceDetails = NewOrderServiceReqServiceDetails()
    @SerializedName("address") val address: String = ""
    @SerializedName("otpVerification") val otpVerification: Boolean = false
    @SerializedName("userVerification") val userVerification: Boolean = false
    @SerializedName("profilePic") val profilePic: String = ""
    @SerializedName("websiteUrl") val websiteUrl: String = ""
    @SerializedName("duration") val duration: String = ""
    @SerializedName("addressLine1") val addressLine1: String = ""
    @SerializedName("addressLine2") val addressLine2: String = ""
    @SerializedName("zipCode") val zipCode: String = ""
    @SerializedName("eoriNumber") val eoriNumber: String = ""
    @SerializedName("additionalDocName") val additionalDocName: String = ""
    @SerializedName("additionalDocument") val additionalDocument: String = ""
    @SerializedName("DOB") val dOB: String = ""
    @SerializedName("ownerFirstName") val ownerFirstName: String = ""
    @SerializedName("ownerLastName") val ownerLastName: String = ""
    @SerializedName("ownerEmail") val ownerEmail: String = ""
    @SerializedName("noOfUniqueProducts") val noOfUniqueProducts: Number = 0
    @SerializedName("listOfBrandOrProducts") val listOfBrandOrProducts: List<String> = listOf()
    @SerializedName("keepStock") val keepStock: Boolean = false
    @SerializedName("isPhysicalStore") val isPhysicalStore: Boolean = false
    @SerializedName("howManyStoresDoYouHave") val howManyStoresDoYouHave: String = ""
    @SerializedName("comments") val comments: String = ""
    @SerializedName("completeProfile") val completeProfile: Boolean = false
    @SerializedName("flag") val flag: Int = 0
    @SerializedName("placeOrderCount") val placeOrderCount: Int = 0
    @SerializedName("serviceOrderCount") val serviceOrderCount: Int = 0
    @SerializedName("receiveOrderCount") val receiveOrderCount: Int = 0
    @SerializedName("status") val status: String = ""
    @SerializedName("thumbnail") val thumbnail: String = ""
    @SerializedName("_id") val id: String = ""
    @SerializedName("countryCode") val countryCode: String = ""
    @SerializedName("email") val email: String = ""
    @SerializedName("firstName") val firstName: String = ""
    @SerializedName("lastName") val lastName: String = ""
    @SerializedName("mobileNumber") val mobileNumber: String = ""
    @SerializedName("password") val password: String = ""
    @SerializedName("userType") val userType: String = ""
    @SerializedName("otp") val otp: String = ""
    @SerializedName("otpExpireTime") val otpExpireTime: String = ""
    @SerializedName("createdAt") val createdAt: String = ""
    @SerializedName("updatedAt") val updatedAt: String = ""
    @SerializedName("__v") val v: Int = 0
    @SerializedName("isReset") val isReset: Boolean = false
    @SerializedName("deviceToken") val deviceToken: String = ""
    @SerializedName("deviceType") val deviceType: String = ""

}
class NewOrderServiceReqServiceDetail : Serializable{
    @SerializedName("_id") val id: String = ""
    @SerializedName("serviceId") val serviceId: NewOrderServiceReqServiceId = NewOrderServiceReqServiceId()
    @SerializedName("quantity") val quantity: Int = 0
    @SerializedName("orderPrice") val orderPrice: Number = 0
    @SerializedName("orderId") val orderId: Number = 0
    @SerializedName("price") val price: Number = 0
    @SerializedName("serviceImage") val serviceImage: ArrayList<String> = arrayListOf()
}

class NewOrderServiceReqServiceId : Serializable{
    @SerializedName("serviceLocation") val serviceLocation: NewOrderServiceReqServiceLocation = NewOrderServiceReqServiceLocation()
    @SerializedName("serviceImage") val serviceImage: List<String> = listOf()
    @SerializedName("slots") val slots: List<String> = listOf()
    @SerializedName("status") val status: String = ""
    @SerializedName("serviceName") val serviceName: String = ""
    @SerializedName("thumbnail") val thumbnail: String = ""
    @SerializedName("_id") val id: String = ""
    @SerializedName("categoryId") val categoryId: NewOrderServiceReqCategoryId = NewOrderServiceReqCategoryId()
    @SerializedName("subCategoryDetails") val subCategoryDetails: List<NewOrderServiceReqSubCategoryDetail> = listOf()
    @SerializedName("userId") val userId: ServiceReqUserId = ServiceReqUserId()
    @SerializedName("__v") val v: Int = 0
    @SerializedName("createdAt") val createdAt: String = ""
    @SerializedName("updatedAt") val updatedAt: String = ""

}
class NewOrderServiceReqServiceLocation : Serializable{
    @SerializedName("type") val type: String = ""
    @SerializedName("coordinates") val coordinates: List<Double> = listOf()
}

class NewOrderServiceReqCategoryId : Serializable{
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

class NewOrderServiceReqSubCategoryDetail : Serializable {
    @SerializedName("price") val price: Int = 0
    @SerializedName("subCategoryId") val subCategoryId: NewOrderServiceReqSubCategoryId = NewOrderServiceReqSubCategoryId()
    class NewOrderServiceReqSubCategoryId {
        @SerializedName("thumbnail")
        val thumbnail: String = ""
        @SerializedName("status")
        val status: String = ""
        @SerializedName("_id")
        val id: String = ""
        @SerializedName("categoryId")
        val categoryId: String = ""
        @SerializedName("subCategoryName")
        val subCategoryName: String = ""
        @SerializedName("__v")
        val v: Int = 0
        @SerializedName("createdAt")
        val createdAt: String = ""
        @SerializedName("updatedAt")
        val updatedAt: String = ""
    }
}

class NewOrderServiceReqUserId : Serializable{
    @SerializedName("storeLocation") val storeLocation: NewOrderServiceReqStoreLocation = NewOrderServiceReqStoreLocation()
    @SerializedName("govtDocument") val govtDocument: NewOrderServiceReqGovtDocument = NewOrderServiceReqGovtDocument()
    @SerializedName("socialLink") val socialLink: NewOrderServiceReqSocialLink = NewOrderServiceReqSocialLink()
    @SerializedName("businessDetails") val businessDetails: NewOrderServiceReqBusinessDetails = NewOrderServiceReqBusinessDetails()
    @SerializedName("businessBankingDetails") val businessBankingDetails: NewOrderServiceReqBusinessBankingDetails = NewOrderServiceReqBusinessBankingDetails()
    @SerializedName("businessDocumentUpload") val businessDocumentUpload: NewOrderServiceReqBusinessDocumentUpload = NewOrderServiceReqBusinessDocumentUpload()
    @SerializedName("serviceDetails") val serviceDetails: NewOrderServiceReqServiceDetails = NewOrderServiceReqServiceDetails()
    @SerializedName("address") val address: String = ""
    @SerializedName("otpVerification") val otpVerification: Boolean = false
    @SerializedName("userVerification") val userVerification: Boolean = false
    @SerializedName("profilePic") val profilePic: String = ""
    @SerializedName("websiteUrl") val websiteUrl: String = ""
    @SerializedName("duration") val duration: String = ""
    @SerializedName("userRequestStatus") val userRequestStatus: String = ""
    @SerializedName("zipCode") val zipCode: String = ""
    @SerializedName("eoriNumber") val eoriNumber: String = ""
    @SerializedName("additionalDocName") val additionalDocName: String = ""
    @SerializedName("additionalDocument") val additionalDocument: String = ""
    @SerializedName("DOB") val dOB: String = ""
    @SerializedName("ownerFirstName") val ownerFirstName: String = ""
    @SerializedName("ownerLastName") val ownerLastName: String = ""
    @SerializedName("ownerEmail") val ownerEmail: String = ""
    @SerializedName("noOfUniqueProducts") val noOfUniqueProducts: Number = 0
//    @SerializedName("listOfBrandOrProducts")
//    val listOfBrandOrProducts: List<NewOrderServiceReqAny> = listOf()
    @SerializedName("keepStock") val keepStock: Boolean = false
    @SerializedName("isPhysicalStore") val isPhysicalStore: Boolean = false
    @SerializedName("howManyStoresDoYouHave") val howManyStoresDoYouHave: String = ""
//    @SerializedName("preferredSupplierOrWholeSalerId")
//    val preferredSupplierOrWholeSalerId: List<NewOrderServiceReqAny> = listOf()
    @SerializedName("comments") val comments: String = ""
    @SerializedName("completeProfile") val completeProfile: Boolean = false
    @SerializedName("flag") val flag: Int = 0
    @SerializedName("placeOrderCount") val placeOrderCount: Int = 0
    @SerializedName("serviceOrderCount") val serviceOrderCount: Int = 0
    @SerializedName("receiveOrderCount") val receiveOrderCount: Int = 0
    @SerializedName("status") val status: String = ""
    @SerializedName("thumbnail") val thumbnail: String = ""
    @SerializedName("_id") val id: String = ""
    @SerializedName("userType") val userType: String = ""
    @SerializedName("firstName") val firstName: String = ""
    @SerializedName("lastName") val lastName: String = ""
    @SerializedName("countryCode") val countryCode: String = ""
    @SerializedName("mobileNumber") val mobileNumber: String = ""
    @SerializedName("email") val email: String = ""
    @SerializedName("phoneNumber") val phoneNumber: String = ""
    @SerializedName("password") val password: String = ""
    @SerializedName("city") val city: String = ""
    @SerializedName("state") val state: String = ""
    @SerializedName("country") val country: String = ""
    @SerializedName("addressLine1") val addressLine1: String = ""
    @SerializedName("addressLine2") val addressLine2: String = ""
    @SerializedName("createdAt") val createdAt: String = ""
    @SerializedName("updatedAt") val updatedAt: String = ""
    @SerializedName("__v") val v: Int = 0
    @SerializedName("userUniqueId") val userUniqueId: String = ""
    @SerializedName("isReset") val isReset: Boolean = false
    @SerializedName("otp") val otp: String = ""
    @SerializedName("otpExpireTime") val otpExpireTime: String = ""
    @SerializedName("deviceToken") val deviceToken: String = ""
    @SerializedName("deviceType") val deviceType: String = ""

}

class NewOrderServiceReqStoreLocation : Serializable{
    @SerializedName("type") val type: String = ""
    @SerializedName("coordinates") val coordinates: List<Double> = listOf()
}

class NewOrderServiceReqGovtDocument : Serializable{
    @SerializedName("frontImage") val frontImage: String = ""
    @SerializedName("backImage") val backImage: String = ""
}

class NewOrderServiceReqSocialLink : Serializable{
    @SerializedName("faceBook") val faceBook: String = ""
    @SerializedName("instagram") val instagram: String = ""
    @SerializedName("linkedIn") val linkedIn: String = ""
    @SerializedName("twitter") val twitter: String = ""
}

class NewOrderServiceReqBusinessDetails : Serializable{
    @SerializedName("companyName") val companyName: String = ""
    @SerializedName("businessRegNumber") val businessRegNumber: String = ""
    @SerializedName("websiteUrl") val websiteUrl: String = ""
    @SerializedName("socialMediaAccounts") val socialMediaAccounts: String = ""
    @SerializedName("isVatRegistered") val isVatRegistered: Boolean = false
    @SerializedName("vatNumber") val vatNumber: String = ""
    @SerializedName("monthlyRevenue") val monthlyRevenue: String = ""
}

class NewOrderServiceReqBusinessBankingDetails : Serializable{
    @SerializedName("bankName") val bankName: String = ""
    @SerializedName("branchName") val branchName: String = ""
    @SerializedName("branchCode") val branchCode: String = ""
    @SerializedName("swiftCode") val swiftCode: String = ""
    @SerializedName("accountType") val accountType: String = ""
    @SerializedName("accountName") val accountName: String = ""
    @SerializedName("accountNumber") val accountNumber: String = ""
}

class NewOrderServiceReqBusinessDocumentUpload : Serializable{
    @SerializedName("cirtificationOfIncorporation") val cirtificationOfIncorporation: NewOrderServiceReqCirtificationOfIncorporation = NewOrderServiceReqCirtificationOfIncorporation()
    @SerializedName("VatRegConfirmationDocs") val vatRegConfirmationDocs: NewOrderServiceReqVatRegConfirmationDocs = NewOrderServiceReqVatRegConfirmationDocs()
    @SerializedName("directConsentForm") val directConsentForm: NewOrderServiceReqDirectConsentForm = NewOrderServiceReqDirectConsentForm()
    @SerializedName("directorId") val directorId: NewOrderServiceReqDirectorId = NewOrderServiceReqDirectorId()
    @SerializedName("bankConfirmationLetter") val bankConfirmationLetter: NewOrderServiceReqBankConfirmationLetter = NewOrderServiceReqBankConfirmationLetter()
    class NewOrderServiceReqCirtificationOfIncorporation {
        @SerializedName("frontImage")
        val frontImage: String = ""
        @SerializedName("backImage")
        val backImage: String = ""
    }

    class NewOrderServiceReqVatRegConfirmationDocs {
        @SerializedName("frontImage")
        val frontImage: String = ""
        @SerializedName("backImage")
        val backImage: String = ""
    }

    class NewOrderServiceReqDirectConsentForm {
        @SerializedName("frontImage")
        val frontImage: String = ""
        @SerializedName("backImage")
        val backImage: String = ""
    }

    class NewOrderServiceReqDirectorId {
        @SerializedName("frontImage")
        val frontImage: String = ""
        @SerializedName("backImage")
        val backImage: String = ""
    }

    class NewOrderServiceReqBankConfirmationLetter {
        @SerializedName("frontImage")
        val frontImage: String = ""
        @SerializedName("backImage")
        val backImage: String = ""
    }
}

class NewOrderServiceReqServiceDetails : Serializable{
    @SerializedName("noOfUniqueService") val noOfUniqueService: Int = 0
//    @SerializedName("preferredSupplierOrWholeSalerId")
//    val preferredSupplierOrWholeSalerId: List<NewOrderServiceReqAny> = listOf()
    @SerializedName("comments") val comments: String = ""
//    @SerializedName("listOfServices")
//    val listOfServices: List<NewOrderServiceReqAny> = listOf()
}

class NewOrderServiceReqShippingAddress : Serializable{
    @SerializedName("isPrimary") val isPrimary: Boolean = false
    @SerializedName("status") val status: String = ""
    @SerializedName("_id") val id: String = ""
    @SerializedName("firstName") val firstName: String = ""
    @SerializedName("lastName") val lastName: String = ""
    @SerializedName("mobileNumber") val mobileNumber: String = ""
    @SerializedName("email") val email: String = ""
    @SerializedName("countryCode") val countryCode: String = ""
    @SerializedName("address") val address: String = ""
    @SerializedName("zipCode") val zipCode: String = ""
    @SerializedName("city") val city: String = ""
    @SerializedName("country") val country: String = ""
    @SerializedName("state") val state: String = ""
    @SerializedName("userId") val userId: String = ""
    @SerializedName("createdAt") val createdAt: String = ""
    @SerializedName("updatedAt") val updatedAt: String = ""
    @SerializedName("__v") val v: Int = 0
}

class NewOrderServiceReqShippingFixedAddress : Serializable{
    @SerializedName("isPrimary") val isPrimary: Boolean = false
    @SerializedName("status") val status: String = ""
    @SerializedName("firstName") val firstName: String = ""
    @SerializedName("lastName") val lastName: String = ""
    @SerializedName("mobileNumber") val mobileNumber: String = ""
    @SerializedName("email") val email: String = ""
    @SerializedName("countryCode") val countryCode: String = ""
    @SerializedName("address") val address: String = ""
    @SerializedName("addressLine1") val addressLine1: String = ""
    @SerializedName("addressLine2") val addressLine2: String = ""
    @SerializedName("zipCode") val zipCode: String = ""
    @SerializedName("city") val city: String = ""
    @SerializedName("country") val country: String = ""
    @SerializedName("state") val state: String = ""
}