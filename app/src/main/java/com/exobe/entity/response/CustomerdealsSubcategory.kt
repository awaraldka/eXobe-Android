package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class CustomerdealsSubcategory (
    @SerializedName("result") val result : ArrayList<Customerdeals_Result> = ArrayList(),
    @SerializedName("responseMessage") val responseMessage : String,
    @SerializedName("responseCode") val responseCode : Int
        )

class Customerdeals_Result(
//    @SerializedName("quantity") val quantity : Int,
//    @SerializedName("disCountType") val disCountType : String,
//    @SerializedName("dealsFor") val dealsFor : String,
//    @SerializedName("productId") val productId : ArrayList<Customerdeals_ProductId>,
    @SerializedName("serviceId") val serviceId : Customerdeals_ServiceId? = null,
    @SerializedName("expired") val expired : Boolean,
    @SerializedName("status") val status : String,
    @SerializedName("_id") val _id : String,
    @SerializedName("serviceCategoryId") val serviceCategoryId : ServiceCategoryId,
    @SerializedName("userId") val dealUserId : dealUserId,
    @SerializedName("serviceSubCategoryId") val serviceSubCategoryId : ServiceSubCategoryId,
    @SerializedName("dealPrice") val dealPrice : Number,
    @SerializedName("dealStartTime") val dealStartTime : String,
    @SerializedName("dealEndTime") val dealEndTime : String,
    @SerializedName("serverTime") val serverTime : String,
//    @SerializedName("userId") val userId : UserId,
    @SerializedName("dealType") val dealType : String,
    @SerializedName("userType") val userType : String,
    @SerializedName("dealDiscount") val dealDiscount : String,
    @SerializedName("dealImage") val dealImage : ArrayList<String> = ArrayList(),
    @SerializedName("thumbnail") val thumbnail : String = "",
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int
)
class Customerdeals_ServiceId (

//    @SerializedName("serviceLocation") val serviceLocation : ServiceLocation,
    @SerializedName("serviceImage") val serviceImage : ArrayList<String>,
//    @SerializedName("slots") val slots : List<String>,
//    @SerializedName("status") val status : String,
    @SerializedName("_id") val _id : String,
    @SerializedName("categoryId") val categoryId : CategoryId,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("price") val price : Number,

//    @SerializedName("subCategoryDetails") val subCategoryDetails : ArrayList<Customerdeals_SubCategoryDetails> = ArrayList(),
    @SerializedName("userId") val userId : String,
    @SerializedName("serviceName") val serviceName: String,
    @SerializedName("__v") val __v : Int,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String
)
class Customerdeals_SubCategoryDetails(
    @SerializedName("price") val price : Number=0,
    @SerializedName("subCategoryId") val subCategoryId : Customerdeals_SubCategoryId
)
class Customerdeals_SubCategoryId(
//    @SerializedName("status") val status : String,
    @SerializedName("_id") val _id : String,
    @SerializedName("categoryId") val categoryId : String,
    @SerializedName("serviceName") val serviceName : String="",
    @SerializedName("thumbnail") val thumbnail : String="",
//    @SerializedName("__v") val __v : Int,
//    @SerializedName("createdAt") val createdAt : String,
//    @SerializedName("updatedAt") val updatedAt : String

)

class dealUserId {
    @SerializedName("userType")
    val userType: String= ""
    @SerializedName("firstName")
    val firstName: String= ""
    @SerializedName("lastName")
    val lastName: String= ""
    @SerializedName("_id")
    val _id: String= ""
}

class ServiceCategoryId {
    @SerializedName("_id")
    val _id: String= ""

    @SerializedName("categoryName")
    val categoryName: String= ""

    @SerializedName("categoryImage")
    val categoryImage: String= ""
}
class ServiceSubCategoryId {
    @SerializedName("_id")
    val _id: String= ""

    @SerializedName("subCategoryName")
    val subCategoryName: String= ""
}

class Customerdeals_ProductId {

    @SerializedName("productImage")
    val productImage: List<String>?=null
    @SerializedName("productFor")
    val productFor: String?=null
    @SerializedName("status")
    val status: String?=null
    @SerializedName("_id")
    val _id: String?=null
    @SerializedName("productName")
    val productName: String?=null
    @SerializedName("price")
    val price: Number?=null
//    @SerializedName("unit")
//    val unit: String?=null
    @SerializedName("brand")
    val brand: String?=null
    @SerializedName("description")
    val description: String?=null
    @SerializedName("categoryId")
    val categoryId : customerdealsCategoryId?=null
    @SerializedName("subCategoryId")
    val subCategoryId : customerdealsSubCategoryId?=null
    @SerializedName("quantity")
    val quantity: Int?=null
    @SerializedName("productReferenceId")
    val productReferenceId: String?=null
    @SerializedName("userId")
    val userId: String?=null
    @SerializedName("createdAt")
    val createdAt: String?=null
    @SerializedName("updatedAt")
    val updatedAt: String?=null
    @SerializedName("__v")
    val __v: Int?=null

}