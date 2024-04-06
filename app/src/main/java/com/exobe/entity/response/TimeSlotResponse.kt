package com.exobe.entity.response


import com.google.gson.annotations.SerializedName


class TimeSlotResponse {
    @SerializedName("result")
    val result: TimeSlotResult = TimeSlotResult()
    @SerializedName("responseMessage")
    val responseMessage: String = ""
    @SerializedName("responseCode")
    val responseCode: Int = 0
    class TimeSlotResult {
        @SerializedName("docs")
        val docs: List<String> = listOf()
    }
}