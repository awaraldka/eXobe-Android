package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class CountryListResponse (
    @SerializedName("result") val result : ArrayList<CountryListResult> = ArrayList(),
    @SerializedName("responseMessage") val responseMessage : String = "",
    @SerializedName("responseCode") val responseCode : Int = 0
)

class CountryListResult (

//    @SerializedName("timezones") val timezones : List<CountryListTimezones>,
//    @SerializedName("_id") val _id : String,
    @SerializedName("isoCode") val isoCode : String,
    @SerializedName("name") val name : String,
//    @SerializedName("phonecode") val phonecode : Int,
//    @SerializedName("flag") val flag : String,
//    @SerializedName("currency") val currency : String,
//    @SerializedName("latitude") val latitude : Double,
//    @SerializedName("longitude") val longitude : Double,
//    @SerializedName("__v") val __v : Int,
//    @SerializedName("createdAt") val createdAt : String,
//    @SerializedName("updatedAt") val updatedAt : String
)
class CountryListTimezones (

    @SerializedName("zoneName") val zoneName : String,
    @SerializedName("gmtOffset") val gmtOffset : Int,
    @SerializedName("gmtOffsetName") val gmtOffsetName : String,
    @SerializedName("abbreviation") val abbreviation : String,
    @SerializedName("tzName") val tzName : String
)

class StateListResponse(
    @SerializedName("result") val result : ArrayList<StateList_Result> = ArrayList(),
    @SerializedName("responseMessage") val responseMessage : String = "",
    @SerializedName("responseCode") val responseCode : Int = 0
)
class StateList_Result (

    @SerializedName("status") val status : String = "",
    @SerializedName("_id") val _id : String = "",
    @SerializedName("name") val name : String = "",
    @SerializedName("isoCode") val isoCode : String = "",
    @SerializedName("countryCode") val countryCode : String = "",
    @SerializedName("latitude") val latitude : Double,
    @SerializedName("longitude") val longitude : Double,
    @SerializedName("__v") val __v : Int = 0,
    @SerializedName("createdAt") val createdAt : String = "",
    @SerializedName("updatedAt") val updatedAt : String = "",
)

class CityListResponse(
    @SerializedName("result") val result : ArrayList<CityListResult> = ArrayList(),
    @SerializedName("responseMessage") val responseMessage : String = "",
    @SerializedName("responseCode") val responseCode : Int = 0
)
class CityListResult(
//    @SerializedName("status") val status : String,
//    @SerializedName("_id") val _id : String,
    @SerializedName("name") val name : String = "",
//    @SerializedName("countryCode") val countryCode : String,
//    @SerializedName("stateCode") val stateCode : String,
//    @SerializedName("latitude") val latitude : Double,
//    @SerializedName("longitude") val longitude : Double,
//    @SerializedName("__v") val __v : Int,
//    @SerializedName("createdAt") val createdAt : String,
//    @SerializedName("updatedAt") val updatedAt : String
)