package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class DealstocustomerList_Response (

    @SerializedName("result") val result : List<DealstocustomerList_Result>,
    @SerializedName("responseMessage") val responseMessage : String,
    @SerializedName("responseCode") val responseCode : Int
)
class DealstocustomerList_Result(
    @SerializedName("status") val status : String,
    @SerializedName("_id") val _id : String,
    @SerializedName("categoryName") val categoryName : String,
    @SerializedName("thumbnail") val thumbnail : String,
    @SerializedName("categoryImage") val categoryImage : String,
    @SerializedName("description") val description : String,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int
)
