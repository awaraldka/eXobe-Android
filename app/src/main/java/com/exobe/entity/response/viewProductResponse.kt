package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

data class viewProductResponse(
    @SerializedName("result") var result: viewProductResult = viewProductResult(),
    @SerializedName("responseMessage") var responseMessage: String? = null,
    @SerializedName("responseCode") var responseCode: Int? = null
)

data class viewProductResult(

    @SerializedName("productImage") var productImage: ArrayList<String> = arrayListOf(),
    @SerializedName("dealImage") var dealImage: ArrayList<String> = arrayListOf(),
    @SerializedName("discount") var discount: Int? = null,
    @SerializedName("productFor") var productFor: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("brand") var brand: String? = null,
    @SerializedName("categoryId") var categoryId: ProductCategoryId? = ProductCategoryId(),
    @SerializedName("description") var description: String? = null,
    @SerializedName("price") var price: Number = 0,
    @SerializedName("productName") var productName: String? = null,
    @SerializedName("thumbnail") var thumbnail: String? = "",
    @SerializedName("quantity") var quantity: String? = null,
    @SerializedName("expectedDeliveryDays")
    val expectedDeliveryDays: String? = "",
    @SerializedName("subCategoryId") var subCategoryId: ProductSubCategoryId? = ProductSubCategoryId(),
    @SerializedName("priceSizeDetails")
    val priceSizeDetails: ArrayList<PriceSizeDetails> = ArrayList<PriceSizeDetails>(),
//    @SerializedName("unit") var unit: String? = null,
    @SerializedName("userId") var userId: ProductUserId? = ProductUserId(),
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("__v") var _v: Int? = null

)

data class ProductUserId(

    @SerializedName("businessDetails") var businessDetails: ProductBusinessDetails? = ProductBusinessDetails(),
    @SerializedName("businessBankingDetails") var businessBankingDetails: ProductBusinessBankingDetails? = ProductBusinessBankingDetails(),
    @SerializedName("serviceDetails") var serviceDetails: ProductServiceDetails? = ProductServiceDetails(),
    @SerializedName("address") var address: String? = null,
    @SerializedName("otpVerification") var otpVerification: Boolean? = null,
    @SerializedName("userVerification") var userVerification: Boolean? = null,
    @SerializedName("profilePic") var profilePic: String? = null,
    @SerializedName("websiteUrl") var websiteUrl: String? = null,
    @SerializedName("duration") var duration: String? = null,
    @SerializedName("userRequestStatus") var userRequestStatus: String? = null,
    @SerializedName("zipCode") var zipCode: String? = null,
    @SerializedName("eoriNumber") var eoriNumber: String? = null,
    @SerializedName("additionalDocName") var additionalDocName: String? = null,
    @SerializedName("additionalDocument") var additionalDocument: String? = null,
    @SerializedName("ownerFirstName") var ownerFirstName: String? = null,
    @SerializedName("ownerLastName") var ownerLastName: String? = null,
    @SerializedName("ownerEmail") var ownerEmail: String? = null,
    @SerializedName("noOfUniqueProducts") var noOfUniqueProducts: Int? = null,
    @SerializedName("listOfBrandOrProducts") var listOfBrandOrProducts: ArrayList<String> = arrayListOf(),
    @SerializedName("keepStock") var keepStock: Boolean? = null,
    @SerializedName("isPhysicalStore") var isPhysicalStore: Boolean? = null,
    @SerializedName("preferredSupplierOrWholeSalerId") var preferredSupplierOrWholeSalerId: ArrayList<String> = arrayListOf(),
    @SerializedName("comments") var comments: String? = null,
    @SerializedName("completeProfile") var completeProfile: Boolean? = null,
    @SerializedName("flag") var flag: Int? = null,
    @SerializedName("placeOrderCount") var placeOrderCount: Int? = null,
    @SerializedName("serviceOrderCount") var serviceOrderCount: Int? = null,
    @SerializedName("receiveOrderCount") var receiveOrderCount: Int? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("userType") var userType: String? = null,
    @SerializedName("firstName") var firstName: String? = null,
    @SerializedName("lastName") var lastName: String? = null,
    @SerializedName("countryCode") var countryCode: String? = null,
    @SerializedName("mobileNumber") var mobileNumber: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("phoneNumber") var phoneNumber: String? = null,
    @SerializedName("storeAddress") var storeAddress: String? = null,
    @SerializedName("storeName") var storeName: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("addressLine1") var addressLine1: String? = null,
    @SerializedName("addressLine2") var addressLine2: String? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("__v") var _v: Int? = null,
    @SerializedName("otp") var otp: String? = null,
    @SerializedName("otpExpireTime") var otpExpireTime: String? = null

)

data class ProductServiceDetails(

    @SerializedName("noOfUniqueService") var noOfUniqueService: Int? = null,
    @SerializedName("preferredSupplierOrWholeSalerId") var preferredSupplierOrWholeSalerId: ArrayList<String> = arrayListOf(),
    @SerializedName("comments") var comments: String? = null,
    @SerializedName("listOfServices") var listOfServices: ArrayList<String> = arrayListOf()

)

data class ProductBusinessBankingDetails(

    @SerializedName("bankName") var bankName: String? = null,
    @SerializedName("branchName") var branchName: String? = null,
    @SerializedName("branchCode") var branchCode: String? = null,
    @SerializedName("swiftCode") var swiftCode: String? = null,
    @SerializedName("accountType") var accountType: String? = null,
    @SerializedName("accountName") var accountName: String? = null,
    @SerializedName("accountNumber") var accountNumber: String? = null

)

data class ProductBusinessDetails(

    @SerializedName("companyName") var companyName: String? = null,
    @SerializedName("businessRegNumber") var businessRegNumber: String? = null,
    @SerializedName("websiteUrl") var websiteUrl: String? = null,
    @SerializedName("socialMediaAccounts") var socialMediaAccounts: String? = null,
    @SerializedName("isVatRegistered") var isVatRegistered: Boolean? = null,
    @SerializedName("vatNumber") var vatNumber: String? = null,
    @SerializedName("monthlyRevenue") var monthlyRevenue: String? = null

)

data class ProductCategoryId(

    @SerializedName("status") var status: String? = null,
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("categoryName") var categoryName: String? = null,
    @SerializedName("categoryImage") var categoryImage: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("__v") var _v: Int? = null

)

data class ProductSubCategoryId(

    @SerializedName("status") var status: String? = null,
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("categoryId") var categoryId: String? = null,
    @SerializedName("subCategoryName") var subCategoryName: String? = null,
    @SerializedName("__v") var _v: Int? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null

)
