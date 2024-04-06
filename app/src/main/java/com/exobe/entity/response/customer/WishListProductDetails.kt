package com.exobe.entity.response.customer

import com.exobe.entity.response.PriceSizeDetails
import com.google.gson.annotations.SerializedName

class WishListProductDetails (
    @SerializedName("result")
    val result: ViewResult,
    @SerializedName("responseMessage")
    val responseMessage: String,
    @SerializedName("responseCode")
    val responseCode: Int

    )

class ViewResult {


    @SerializedName("productImage")
    val productImage: ArrayList<String>? = null

    @SerializedName("dealImage")
    val dealImage: ArrayList<String>? = null

    @SerializedName("isDealActive") val isDealActive: Boolean = false
    @SerializedName("isCampaignActive") val isCampaignActive: Boolean = false


    @SerializedName("dealPrice") val dealPrice: Number = 0
    @SerializedName("dealDiscount") val dealDiscount: Number = 0

    @SerializedName("thumbnail")
    var thumbnail: String? = null

    @SerializedName("discount")
    val discount: Int? = 0
    @SerializedName("productFor")
    val productFor: String? = ""
    @SerializedName("status")
    val status: String? = ""

    @SerializedName("isLike")
    var isLike: Boolean? = true

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
    val categoryId: ViewCategoryId? = ViewCategoryId()
    @SerializedName("subCategoryId")
    val subCategoryId: ViewSubCategoryId? = ViewSubCategoryId()
    @SerializedName("priceSizeDetails")
    val priceSizeDetails: ArrayList<PriceSizeDetails> = ArrayList<PriceSizeDetails>()
    @SerializedName("quantity")
    val quantity: String? = ""
    @SerializedName("productReferenceId")
    val productReferenceId: String? = ""
    @SerializedName("userId")
    val userId: ViewUserId? = ViewUserId()
    @SerializedName("createdAt")
    val createdAt: String? = ""
    @SerializedName("updatedAt")
    val updatedAt: String? = ""
    @SerializedName("reviewCount")
    val reviewCount: Int? = 0
    @SerializedName("__v")
    val __v: Int = 0
}

class ViewCategoryId {


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


class ViewSubCategoryId {
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

class ViewUserId {
    @SerializedName("storeLocation")
    val storeLocation: ViewStoreLocation? = ViewStoreLocation()
    @SerializedName("govtDocument")
    val govtDocument: ViewGovtDocument? = ViewGovtDocument()
    @SerializedName("socialLink")
    val socialLink: ViewSocialLink? = ViewSocialLink()
    @SerializedName("businessDetails")
    val businessDetails: ViewBusinessDetails? = ViewBusinessDetails()
    @SerializedName("businessBankingDetails")
    val businessBankingDetails: ViewBusinessBankingDetails? = ViewBusinessBankingDetails()
    @SerializedName("serviceDetails")
    val serviceDetails: ViewServiceDetails? = ViewServiceDetails()
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


class ViewStoreLocation {


    @SerializedName("type")
    val type: String? = ""
    @SerializedName("coordinates")
    val coordinates: List<Double>? = null
}

class ViewGovtDocument {
    @SerializedName("frontImage")
    val frontImage: String? = ""
    @SerializedName("backImage")
    val backImage: String? = ""
}

class ViewSocialLink {
    @SerializedName("faceBook")
    val faceBook: String? = ""
    @SerializedName("linkedIn")
    val linkedIn: String? = ""
    @SerializedName("twitter")
    val twitter: String? = ""
    @SerializedName("instagram")
    val instagram: String? = ""
}

class ViewBusinessDetails {
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

class ViewBusinessBankingDetails {
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

class ViewServiceDetails {
    @SerializedName("noOfUniqueService")
    val noOfUniqueService: Int? = 0
    @SerializedName("preferredSupplierOrWholeSalerId")
    val preferredSupplierOrWholeSalerId: List<String>? = null
    @SerializedName("comments")
    val comments: String? = ""
    @SerializedName("listOfServices")
    val listOfServices: List<String>? = null

}
