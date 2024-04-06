package com.exobe.entity.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class MyProductlist {

    @SerializedName("result") val result : MyProductResult? = null
    @SerializedName("responseMessage") val responseMessage : String?=null
    @SerializedName("responseCode") val responseCode : Int?=null
}

class MyProductResult {

    @SerializedName("docs")
    val docs: ArrayList<MyProductDocs> = ArrayList<MyProductDocs>()
    @SerializedName("total")
    val total: Int = 0
    @SerializedName("limit")
    val limit: Int = 0
    @SerializedName("page")
    val page: Int = 0
    @SerializedName("pages")
    val pages: Int = 0
}
class MyProductDocs {

    @SerializedName("productImage")
    val productImage: ArrayList<String> = ArrayList<String>()
    @SerializedName("thumbnail")
    val thumbnail: String = ""
    @SerializedName("productFor")
    val productFor: String = ""
    @SerializedName("status")
    val status: String = ""
    @SerializedName("_id")
    val _id: String = ""
    @SerializedName("brand")
    val brand: String = ""
    @SerializedName("categoryId")
    val categoryId: MyProductCategoryId = MyProductCategoryId()
    @SerializedName("description")
    val description: String = ""
    @SerializedName("price")
    val price: Number=0
    @SerializedName("productName")
    val productName: String = ""
    @SerializedName("quantity")
    val quantity: String = ""
    @SerializedName("subCategoryId")
    val subCategoryId: MyProductSubCategoryId = MyProductSubCategoryId()

    @SerializedName("priceSizeDetails")
    val priceSizeDetails: ArrayList<PriceSizeDetails> = ArrayList<PriceSizeDetails>()
    @SerializedName("unit")
    val unit: String = ""
//    @SerializedName("userId")
//    val userId: MyProductUserId = MyProductUserId()
    @SerializedName("productReferenceId")
    val productReferenceId: String = ""
    @SerializedName("createdAt")
    val createdAt: String = ""
    @SerializedName("updatedAt")
    val updatedAt: String = ""
    @SerializedName("__v")
    val __v: Int = 0
}

data class PriceSizeDetails (

    @SerializedName("value")
    var value: String= "",

    @SerializedName("unit")
    var unit: String= "",

    @SerializedName("price")
    var price: Number= 0,

    @SerializedName("quantity")
    var quantity: String= "",

    @SerializedName("id")
    val id: String= ""




)

class MyProductCategoryId {

    @SerializedName("status")
    val status: String= ""
    @SerializedName("_id")
    val _id: String= ""
    @SerializedName("categoryName")
    val categoryName: String= ""
    @SerializedName("categoryImage")
    val categoryImage: String= ""
    @SerializedName("description")
    val description: String= ""
    @SerializedName("createdAt")
    val createdAt: String= ""
    @SerializedName("updatedAt")
    val updatedAt: String= ""
    @SerializedName("__v")
    val __v: Int= 0
}

class MyProductSubCategoryId {

    @SerializedName("status") val status : String= ""
    @SerializedName("_id") val _id : String= ""
    @SerializedName("categoryId") val categoryId : String= ""
    @SerializedName("subCategoryName") val subCategoryName : String= ""
    @SerializedName("__v") val __v : Int= 0
    @SerializedName("createdAt") val createdAt : String= ""
    @SerializedName("updatedAt") val updatedAt : String= ""
}
class MyProductUserId (

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
//    @SerializedName("zipCode") val zipCode : String,
//    @SerializedName("eoriNumber") val eoriNumber : String,
//    @SerializedName("ownerFirstName") val ownerFirstName : String,
//    @SerializedName("ownerLastName") val ownerLastName : String,
//    @SerializedName("ownerEmail") val ownerEmail : String,
//    @SerializedName("noOfUniqueProducts") val noOfUniqueProducts : Int,
//    @SerializedName("listOfBrandOrProducts") val listOfBrandOrProducts : List<String>,
//    @SerializedName("keepStock") val keepStock : Boolean,
//    @SerializedName("isPhysicalStore") val isPhysicalStore : Boolean,
//    @SerializedName("comments") val comments : String,
//    @SerializedName("completeProfile") val completeProfile : Boolean,
//    @SerializedName("flag") val flag : Int,
//
//    @SerializedName("status") val status : String,
//    @SerializedName("_id") val _id : String,
//    @SerializedName("userType") val userType : String,
//    @SerializedName("firstName") val firstName : String,
//    @SerializedName("lastName") val lastName : String,
//    @SerializedName("countryCode") val countryCode : String,
//
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

)