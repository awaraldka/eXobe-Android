package com.exobe.entity.response.customer

import com.google.gson.annotations.SerializedName

class SimilarProducts {

    @SerializedName("result")
    val result: List<SimilarProductsResult>? = null
    @SerializedName("responseMessage")
    val responseMessage: String? = ""
    @SerializedName("responseCode")
    val responseCode: Int? = 0

}

class SimilarProductsResult {
    @SerializedName("productImage")
    val productImage: List<String>? = null
    @SerializedName("discount")
    val discount: Int? = 0
    @SerializedName("productFor")
    val productFor: String? = ""
    @SerializedName("status")
    val status: String? = ""
    @SerializedName("_id")
    val _id: String? = ""
    @SerializedName("productName")
    val productName: String? = ""
    @SerializedName("price")
    val price: Number? = null
//    @SerializedName("unit")
//    val unit: String? = ""
    @SerializedName("brand")
    val brand: String? = ""
    @SerializedName("description")
    val description: String? = ""
    @SerializedName("categoryId")
    val categoryId: SimilarProductsCategoryId? = SimilarProductsCategoryId()
    @SerializedName("subCategoryId")
    val subCategoryId: SimilarProductsSubCategoryId? = SimilarProductsSubCategoryId()
    @SerializedName("quantity")
    val quantity: String? = ""
    @SerializedName("productReferenceId")
    val productReferenceId: String? = ""
    @SerializedName("userId")
    val userId: SimilarProductsUserId? = SimilarProductsUserId()
    @SerializedName("createdAt")
    val createdAt: String? = ""
    @SerializedName("updatedAt")
    val updatedAt: String? = ""
    @SerializedName("__v")
    val __v: Int? = 0
}

class SimilarProductsCategoryId {
    @SerializedName("status")
    val status: String? = ""
    @SerializedName("_id")
    val _id: String? = ""
    @SerializedName("categoryName")
    val categoryName: String? = ""
    @SerializedName("categoryImage")
    val categoryImage: String? = ""
    @SerializedName("description")
    val description: String? = ""
    @SerializedName("createdAt")
    val createdAt: String? = ""
    @SerializedName("updatedAt")
    val updatedAt: String? = ""
    @SerializedName("__v")
    val __v: Int? = 0
}

class SimilarProductsSubCategoryId {
    @SerializedName("status")
    val status: String? = ""
    @SerializedName("_id")
    val _id: String? = ""
    @SerializedName("categoryId")
    val categoryId: String? = ""
    @SerializedName("subCategoryName")
    val subCategoryName: String? = ""
    @SerializedName("__v")
    val __v: Int? = 0
    @SerializedName("createdAt")
    val createdAt: String? = ""
    @SerializedName("updatedAt")
    val updatedAt: String? = ""
}

class SimilarProductsUserId {
    @SerializedName("storeLocation")
    val storeLocation: SimilarProductsStoreLocation? = SimilarProductsStoreLocation()
    @SerializedName("govtDocument")
    val govtDocument: SimilarProductsGovtDocument? = SimilarProductsGovtDocument()
    @SerializedName("socialLink")
    val socialLink: SimilarProductsSocialLink? = SimilarProductsSocialLink()
    @SerializedName("businessDetails")
    val businessDetails: SimilarProductsBusinessDetails? = SimilarProductsBusinessDetails()
    @SerializedName("businessBankingDetails")
    val businessBankingDetails: SimilarProductsBusinessBankingDetails? =
        SimilarProductsBusinessBankingDetails()
    @SerializedName("businessDocumentUpload")
    val businessDocumentUpload: SimilarProductsBusinessDocumentUpload? =
        SimilarProductsBusinessDocumentUpload()
    @SerializedName("serviceDetails")
    val serviceDetails: SimilarProductsServiceDetails? = SimilarProductsServiceDetails()
    @SerializedName("address")
    val address: String? = ""
    @SerializedName("otpVerification")
    val otpVerification: Boolean? = null
    @SerializedName("userVerification")
    val userVerification: Boolean? = null
    @SerializedName("profilePic")
    val profilePic: String? = ""
    @SerializedName("websiteUrl")
    val websiteUrl: String? = ""
    @SerializedName("duration")
    val duration: String? = ""
    @SerializedName("userRequestStatus")
    val userRequestStatus: String? = ""
    @SerializedName("zipCode")
    val zipCode: String? = ""
    @SerializedName("eoriNumber")
    val eoriNumber: String? = ""
    @SerializedName("additionalDocName")
    val additionalDocName: String? = ""
    @SerializedName("additionalDocument")
    val additionalDocument: String? = ""
    @SerializedName("ownerFirstName")
    val ownerFirstName: String? = ""
    @SerializedName("ownerLastName")
    val ownerLastName: String? = ""
    @SerializedName("ownerEmail")
    val ownerEmail: String? = ""
    @SerializedName("noOfUniqueProducts")
    val noOfUniqueProducts: Number? = 0
    @SerializedName("listOfBrandOrProducts")
    val listOfBrandOrProducts: List<String>? = null
    @SerializedName("keepStock")
    val keepStock: Boolean? = null
    @SerializedName("isPhysicalStore")
    val isPhysicalStore: Boolean? = null
    @SerializedName("preferredSupplierOrWholeSalerId")
    val preferredSupplierOrWholeSalerId: List<String>? = null
    @SerializedName("comments")
    val comments: String? = ""
    @SerializedName("completeProfile")
    val completeProfile: Boolean? = null
    @SerializedName("flag")
    val flag: Int? = 0
    @SerializedName("placeOrderCount")
    val placeOrderCount: Int? = 0
    @SerializedName("serviceOrderCount")
    val serviceOrderCount: Int? = 0
    @SerializedName("receiveOrderCount")
    val receiveOrderCount: Int? = 0
    @SerializedName("status")
    val status: String? = ""
    @SerializedName("_id")
    val _id: String? = ""
    @SerializedName("userType")
    val userType: String? = ""
    @SerializedName("firstName")
    val firstName: String? = ""
    @SerializedName("lastName")
    val lastName: String? = ""
    @SerializedName("countryCode")
    val countryCode: String? = ""
    @SerializedName("mobileNumber")
    val mobileNumber: String? = ""
    @SerializedName("email")
    val email: String? = ""
    @SerializedName("phoneNumber")
    val phoneNumber: String? = ""
    @SerializedName("storeAddress")
    val storeAddress: String? = ""
    @SerializedName("storeName")
    val storeName: String? = ""
    @SerializedName("storeContactNo")
    val storeContactNo: String? = ""
    @SerializedName("storeBrand")
    val storeBrand: String? = ""
    @SerializedName("password")
    val password: String? = ""
    @SerializedName("city")
    val city: String? = ""
    @SerializedName("state")
    val state: String? = ""
    @SerializedName("country")
    val country: String? = ""
    @SerializedName("addressLine1")
    val addressLine1: String? = ""
    @SerializedName("createdAt")
    val createdAt: String? = ""
    @SerializedName("updatedAt")
    val updatedAt: String? = ""
    @SerializedName("__v")
    val __v: Int? = -0
    @SerializedName("userUniqueId")
    val userUniqueId: String = ""
}

class SimilarProductsStoreLocation {
    @SerializedName("type")
    val type: String? = ""
    @SerializedName("coordinates")
    val coordinates: List<Double>? = null
}

class SimilarProductsGovtDocument {
    @SerializedName("frontImage")
    val frontImage: String? = ""
    @SerializedName("backImage")
    val backImage: String? = ""
}

class SimilarProductsSocialLink {
    @SerializedName("faceBook")
    val faceBook: String? = ""
    @SerializedName("linkedIn")
    val linkedIn: String? = ""
    @SerializedName("twitter")
    val twitter: String? = ""
    @SerializedName("instagram")
    val instagram: String? = ""
}

class SimilarProductsBusinessDetails {
    @SerializedName("companyName")
    val companyName: String? = ""
    @SerializedName("businessRegNumber")
    val businessRegNumber: String? = ""
    @SerializedName("websiteUrl")
    val websiteUrl: String? = ""
    @SerializedName("socialMediaAccounts")
    val socialMediaAccounts: String? = ""
    @SerializedName("isVatRegistered")
    val isVatRegistered: Boolean? = null
    @SerializedName("vatNumber")
    val vatNumber: String? = ""
    @SerializedName("monthlyRevenue")
    val monthlyRevenue: String? = ""
}

class SimilarProductsBusinessBankingDetails {
    @SerializedName("bankName")
    val bankName: String? = ""
    @SerializedName("branchName")
    val branchName: String? = ""
    @SerializedName("branchCode")
    val branchCode: String? = ""
    @SerializedName("swiftCode")
    val swiftCode: String = ""
    @SerializedName("accountType")
    val accountType: String? = ""
    @SerializedName("accountName")
    val accountName: String? = ""
    @SerializedName("accountNumber")
    val accountNumber: String? = ""
}

class SimilarProductsBusinessDocumentUpload {
    @SerializedName("cirtificationOfIncorporation")
    val cirtificationOfIncorporation: SimilarProductsCirtificationOfIncorporation? =
        SimilarProductsCirtificationOfIncorporation()
    @SerializedName("VatRegConfirmationDocs")
    val vatRegConfirmationDocs: SimilarProductsVatRegConfirmationDocs? =
        SimilarProductsVatRegConfirmationDocs()
    @SerializedName("directConsentForm")
    val directConsentForm: SimilarProductsDirectConsentForm? = SimilarProductsDirectConsentForm()
    @SerializedName("directorId")
    val directorId: SimilarProductsDirectorId? = SimilarProductsDirectorId()
    @SerializedName("bankConfirmationLetter")
    val bankConfirmationLetter: SimilarProductsBankConfirmationLetter? =
        SimilarProductsBankConfirmationLetter()

}

class SimilarProductsServiceDetails {
    @SerializedName("noOfUniqueService")
    val noOfUniqueService: Int? = 0
    @SerializedName("preferredSupplierOrWholeSalerId")
    val preferredSupplierOrWholeSalerId: List<String>? = null
    @SerializedName("comments")
    val comments: String? = ""
    @SerializedName("listOfServices")
    val listOfServices: List<String>? = null
}

class SimilarProductsCirtificationOfIncorporation {
    @SerializedName("frontImage")
    val frontImage: String? = ""
    @SerializedName("backImage")
    val backImage: String? = ""
}

class SimilarProductsVatRegConfirmationDocs {
    @SerializedName("frontImage")
    val frontImage: String? = ""
    @SerializedName("backImage")
    val backImage: String? = ""
}

class SimilarProductsDirectConsentForm {
    @SerializedName("frontImage")
    val frontImage: String? = ""
    @SerializedName("backImage")
    val backImage: String? = ""
}

class SimilarProductsDirectorId {
    @SerializedName("frontImage")
    val frontImage: String? = ""
    @SerializedName("backImage")
    val backImage: String? = ""
}

class SimilarProductsBankConfirmationLetter {
    @SerializedName("frontImage")
    val frontImage: String? = ""
    @SerializedName("backImage")
    val backImage: String? = ""
}