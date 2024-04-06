package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class AddAppRatingResponse {

        @SerializedName("result") val result : AddAppRatingResultResponse?=null
        @SerializedName("responseMessage") val responseMessage : String?=null
        @SerializedName("responseCode") val responseCode : Int?=null

}

class AddAppRatingResultResponse {


    @SerializedName("ratingCount") val ratingCount :  Double?=null
    @SerializedName("status") val status : String?=null
    @SerializedName("_id") val _id : String?=null
    @SerializedName("suggestion") val suggestion : String?=null
    @SerializedName("userId") val userId : String?=null
    @SerializedName("createdAt") val createdAt : String?=null
    @SerializedName("updatedAt") val updatedAt : String?=null
    @SerializedName("__v") val __v : Int?=null


}

