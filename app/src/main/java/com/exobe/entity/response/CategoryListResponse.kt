package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class CategoryListResponse (

    @SerializedName("result") val result : CategoryList_Result,
    @SerializedName("responseMessage") val responseMessage : String,
    @SerializedName("responseCode") val responseCode : Int
)
class CategoryList_Result(
    @SerializedName("docs") val docs : List<CategoryList_docs>
)
class CategoryList_docs(
    @SerializedName("status") val status : String,
    @SerializedName("_id") val _id : String,
    @SerializedName("categoryName") val categoryName : String,
    @SerializedName("categoryImage") val categoryImage : String,
    @SerializedName("description") val description : String,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int
)