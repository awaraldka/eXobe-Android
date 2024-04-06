package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class wholesaler_list_response (
    @SerializedName("result") val result : wholesaler_listResult?=null,
    @SerializedName("responseMessage") val responseMessage : String="",
    @SerializedName("responseCode") val responseCode : Int=0
    )

class wholesaler_listResult {

    @SerializedName("docs")
    val docs: List<wholesaler_listDocs>?=null
}

class wholesaler_listDocs {

    @SerializedName("_id")
    val _id: String=""

    @SerializedName("firstName")
    val firstName: String=""

    @SerializedName("lastName")
    val lastName: String=""

}