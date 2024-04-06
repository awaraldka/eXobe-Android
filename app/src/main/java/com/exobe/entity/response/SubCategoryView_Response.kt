package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class SubCategoryView_Response (
    @SerializedName("result") val result : ArrayList<SubCategoryView_Result> = ArrayList(),
    @SerializedName("responseMessage") val responseMessage : String = "",
    @SerializedName("responseCode") val responseCode : Int = 0
)
class SubCategoryView_Result(
//    @SerializedName("quantity") val quantity : Int,
    @SerializedName("disCountType") val disCountType : String = "",
//    @SerializedName("dealsFor") val dealsFor : String,
//    @SerializedName("productId") val productId : List<String>,
    @SerializedName("serviceId") val serviceId : ArrayList<SubCategoryView_ServiceId> = ArrayList(),
    @SerializedName("expired") val expired : Boolean,
//    @SerializedName("status") val status : String,
    @SerializedName("_id") val _id : String= "",
//    @SerializedName("serviceCategoryId") val serviceCategoryId : ServiceCategoryId,
    @SerializedName("serviceSubCategoryId") val serviceSubCategoryId : SubCategoryView_ServiceSubCategoryId,
    @SerializedName("dealPrice") val dealPrice : Number=0,
//    @SerializedName("dealStartTime") val dealStartTime : String,
    @SerializedName("dealEndTime") val dealEndTime : String= "",
    @SerializedName("dealImage") val dealImage : ArrayList<String> = ArrayList(),
    @SerializedName("thumbnail") val thumbnail : String="",
    //    @SerializedName("userId") val userId : UserId,
//    @SerializedName("dealType") val dealType : String,
//    @SerializedName("userType") val userType : String,
//    @SerializedName("createdAt") val createdAt : String,
//    @SerializedName("updatedAt") val updatedAt : String,
//    @SerializedName("__v") val __v : Int
)

data class SubCategoryView_ServiceId (

//    @SerializedName("serviceLocation") val serviceLocation : ServiceLocation,
//    @SerializedName("serviceImage") val serviceImage : List<String>,
//    @SerializedName("slots") val slots : List<String>,
//    @SerializedName("status") val status : String,
//    @SerializedName("_id") val _id : String,
    @SerializedName("categoryId") val categoryId : SubCategoryView_CategoryId,
    @SerializedName("subCategoryDetails") val subCategoryDetails : ArrayList<SubCategoryView_SubCategoryDetails> = ArrayList(),
//    @SerializedName("userId") val userId : String,
//    @SerializedName("__v") val __v : Int,
//    @SerializedName("createdAt") val createdAt : String,
//    @SerializedName("updatedAt") val updatedAt : String
)
class SubCategoryView_ServiceSubCategoryId (

//    @SerializedName("status") val status : String,
//    @SerializedName("_id") val _id : String,
//    @SerializedName("categoryId") val categoryId : String,
    @SerializedName("subCategoryName") val subCategoryName : String= "",
//    @SerializedName("__v") val __v : Int,
//    @SerializedName("createdAt") val createdAt : String,
//    @SerializedName("updatedAt") val updatedAt : String
)

data class SubCategoryView_SubCategoryDetails (

    @SerializedName("price") val price : Number = 0,
//    @SerializedName("subCategoryId") val subCategoryId : SubCategoryId
)
class SubCategoryView_CategoryId (

    @SerializedName("status") val status : String= "",
    @SerializedName("_id") val _id : String= "",
    @SerializedName("categoryName") val categoryName : String= "",
    @SerializedName("categoryImage") val categoryImage : String= "",
    @SerializedName("description") val description : String= "",
    @SerializedName("createdAt") val createdAt : String= "",
    @SerializedName("updatedAt") val updatedAt : String= "",
    @SerializedName("__v") val __v : Int= 0
)