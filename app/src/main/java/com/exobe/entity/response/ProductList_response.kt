package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class ProductList_response {

        @SerializedName("result") val result : ProductListResult?=null
        @SerializedName("responseMessage") val responseMessage : String?=null
        @SerializedName("responseCode") val responseCode : Int?=null

}
class ProductListResult {

    @SerializedName("docs") val docs : List<ProductListDocs>?=null
    @SerializedName("total") val total : Int?=null
    @SerializedName("limit") val limit : Int?=null
    @SerializedName("page") val page : Int?=null
    @SerializedName("pages") val pages : Int?=null
}
class ProductListDocs {

    @SerializedName("productImage")
    val productImage: List<String>? = null

    @SerializedName("productFor")
    val productFor: String? = null

    @SerializedName("status")
    val status: String? = null

    @SerializedName("_id")
    val _id: String? = null

    @SerializedName("productName")
    val productName: String? = null

    @SerializedName("price")
    val price: Number = 0

//    @SerializedName("unit")
//    val unit: String? = null

    @SerializedName("brand")
    val brand: String? = null

    @SerializedName("description")
    val description: String? = null

    @SerializedName("categoryId")
    val categoryId: ProductListCategoryId? = null

    @SerializedName("subCategoryId")
    val subCategoryId: SubCategoryId? = null

    @SerializedName("quantity")
    val quantity: Int? = null

    @SerializedName("productReferenceId")
    val productReferenceId: String? = null

    @SerializedName("userId")
    val userId: ProductListUserId? = null

    @SerializedName("createdAt")
    val createdAt: String? = null

    @SerializedName("updatedAt")
    val updatedAt: String? = null

    @SerializedName("__v")
    val __v: Int? = null
}
class ProductListUserId {

    @SerializedName("storeLocation")
    val storeLocation: StoreLocation? = null
    @SerializedName("govtDocument")
    val govtDocument: GovtDocument? = null
    @SerializedName("socialLink")
    val socialLink: SocialLink? = null
    @SerializedName("isReset")
    val isReset: Boolean? = null
    @SerializedName("otpVerification")
    val otpVerification: Boolean? = null
    @SerializedName("userVerification")
    val userVerification: Boolean? = null
    @SerializedName("userRequestStatus")
    val userRequestStatus: String? = null
    @SerializedName("resetUserPassword")
    val resetUserPassword: Boolean? = null
    @SerializedName("status")
    val status: String? = null
    @SerializedName("_id")
    val _id: String? = null
    @SerializedName("firstName")
    val firstName: String? = null
    @SerializedName("lastName")
    val lastName: String? = null
    @SerializedName("countryCode")
    val countryCode: String? = null
    @SerializedName("mobileNumber")
    val mobileNumber: String? = null
    @SerializedName("storeAddress")
    val storeAddress: String? = null
    @SerializedName("email")
    val email: String? = null
    @SerializedName("storeName")
    val storeName: String? = null
    @SerializedName("storeContactNo")
    val storeContactNo: String? = null
    @SerializedName("storeBrand")
    val storeBrand: String? = null
    @SerializedName("websiteUrl")
    val websiteUrl: String? = null
    @SerializedName("userType")
    val userType: String? = null
    @SerializedName("password")
    val password: String? = null
    @SerializedName("profilePic")
    val profilePic: String? = null
    @SerializedName("addressLine1")
    val addressLine1: String? = null
    @SerializedName("addressLine2")
    val addressLine2: String? = null
    @SerializedName("city")
    val city: String? = null
    @SerializedName("state")
    val state: String? = null
    @SerializedName("country")
    val country: String? = null
    @SerializedName("companyName")
    val companyName: String? = null
    @SerializedName("zipCode")
    val zipCode: String? = null
    @SerializedName("vatNumber")
    val vatNumber: String? = null
    @SerializedName("eoriNumber")
    val eoriNumber: String? = null
    @SerializedName("subCategoryDetails")
    val subCategoryDetails: List<String>? = null
    @SerializedName("createdAt")
    val createdAt: String? = null
    @SerializedName("updatedAt")
    val updatedAt: String? = null
    @SerializedName("__v")
    val __v: Int? = null
    @SerializedName("userUniqueId")
    val userUniqueId: String? = null
}
class ProductListCategoryId {

    @SerializedName("status")
    val status: String? = null
    @SerializedName("_id")
    val _id: String? = null
    @SerializedName("categoryName")
    val categoryName: String? = null
    @SerializedName("categoryImage")
    val categoryImage: String? = null
    @SerializedName("createdAt")
    val createdAt: String? = null
    @SerializedName("updatedAt")
    val updatedAt: String? = null
    @SerializedName("__v")
    val __v: Int? = null
}