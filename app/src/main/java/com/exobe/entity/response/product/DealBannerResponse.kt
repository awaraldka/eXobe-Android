package com.exobe.entity.response.product

import ServiceId
import com.exobe.entity.response.DealDetails
import com.google.gson.annotations.SerializedName

class DealBannerResponse {
    @SerializedName("result") val result : DealBannerResult =DealBannerResult()
    @SerializedName("responseMessage") val responseMessage : String=""
    @SerializedName("responseCode") val responseCode : Int=0
}

class DealBannerResult {
    @SerializedName("docs") val docs : ArrayList<DealBannerDocs> = ArrayList<DealBannerDocs>()
    @SerializedName("count") val count : Int=0
    @SerializedName("limit") val limit : Int=0
    @SerializedName("page") val page : Int=0
    @SerializedName("remainingItems") val remainingItems : Int=0
}
class DealBannerDocs {

    @SerializedName("quantity") val quantity : Int=0
    @SerializedName("disCountType") val disCountType : String=""
    @SerializedName("dealsFor") val dealsFor : String=""
    @SerializedName("productId") val productId : ArrayList<ProductId> = ArrayList()
    @SerializedName("serviceId") val serviceId : ArrayList<ServiceId> = ArrayList()
    @SerializedName("expired") val expired : Boolean=false
    @SerializedName("status") val status : String=""
    @SerializedName("_id") val _id : String=""
    @SerializedName("dealDiscount") val dealDiscount : String=""
    @SerializedName("dealEndTime") val dealEndTime : String=""
    @SerializedName("serverTime") val serverTime : String=""
    @SerializedName("dealName") val dealName : String=""
    @SerializedName("dealPrice") val dealPrice : Number=0
    @SerializedName("dealStartTime") val dealStartTime : String=""
    @SerializedName("description") val description : String=""
    @SerializedName("dealType") val dealType : String=""
    @SerializedName("createdAt") val createdAt : String=""
    @SerializedName("updatedAt") val updatedAt : String=""
    @SerializedName("dealDetails")
    val dealDetails: ArrayList<DealDetails>? = null

    @SerializedName("__v") val __v : Int=0
}
class ProductId {

    @SerializedName("productImage")
    val productImage: ArrayList<String> =ArrayList()

    @SerializedName("thumbnail") var thumbnail : String = ""

    @SerializedName("discount")
    val discount: Int=0
    @SerializedName("productFor")
    val productFor: String=""
    @SerializedName("status")
    val status: String=""
    @SerializedName("_id")
    val _id: String=""
    @SerializedName("brand")
    val brand: String=""
    @SerializedName("description")
    val description: String=""
    @SerializedName("price")
    val price: Number=0
    @SerializedName("productName")
    val productName: String=""
    @SerializedName("productReferenceId")
    val productReferenceId: String=""
    @SerializedName("quantity")
    val quantity: Int=0

//    @SerializedName("unit")
//    val unit: String=""
    @SerializedName("userId")
    val userId: String=""
    @SerializedName("createdAt")
    val createdAt: String=""
    @SerializedName("updatedAt")
    val updatedAt: String=""
    @SerializedName("__v")
    val __v: Int=0
}