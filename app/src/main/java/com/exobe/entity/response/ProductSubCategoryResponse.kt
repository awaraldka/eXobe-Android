package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class ProductSubCategoryResponse(
    @SerializedName("result") val result : ProductSubCategoryResult = ProductSubCategoryResult(),
    @SerializedName("responseMessage") val responseMessage : String? = null,
    @SerializedName("responseCode") val responseCode : Int? = null,
)


class ProductSubCategoryResult(
    @SerializedName("subCategory") val subCategory : List<ProductSubCategoryDoc>? = listOf(),
    )


class ProductSubCategoryDoc(
    @SerializedName("_id") var _id:String = "",
    @SerializedName("thumbnail") var thumbnail:String = "",
    @SerializedName("subCategoryName") var subCategoryName:String = "",
)
