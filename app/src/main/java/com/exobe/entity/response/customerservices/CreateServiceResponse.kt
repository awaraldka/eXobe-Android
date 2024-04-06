package com.exobe.entity.response.customerservices

import com.google.gson.annotations.SerializedName

class CreateServiceResponse (
    @SerializedName("result") val result : CreateServiceResult = CreateServiceResult(),
    @SerializedName("responseMessage") val responseMessage : String="",
    @SerializedName("responseCode") val responseCode : Int=0

    )
class CreateServiceResult {
    @SerializedName("_id") val _id : String=""
}
class CreateServiceDetails {
    @SerializedName("_id") val _id : String=""
    @SerializedName("serviceId") val serviceId : String=""
    @SerializedName("quantity") val quantity : Int=0
    @SerializedName("price") val price : Number=0
}