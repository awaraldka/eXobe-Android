package com.exobe.entity.response.serviceTab

import com.exobe.entity.response.MyProfileStoreLocation
import com.google.gson.annotations.SerializedName

class VerifyOtpServicesResponse {
    @SerializedName("responseCode") val responseCode:Int = 0
    @SerializedName("responseMessage") val responseMessage:String = ""
    @SerializedName("result") val result : VerifyOtpServicesResult = VerifyOtpServicesResult()
}


class VerifyOtpServicesResult{
    @SerializedName("storeLocation") val storeLocation: MyProfileStoreLocation? = null
}
