package com.exobe.entity.request


import com.google.gson.annotations.SerializedName


class AddAddressRequest {
    @SerializedName("addressId")
    var addressId: String = ""
    @SerializedName("address")
    var address: String = ""
    @SerializedName("addressLine1")
    var addressLine1: String = ""
    @SerializedName("addressLine2")
    var addressLine2: String = ""
    @SerializedName("zipCode")
    var zipCode: String = ""
    @SerializedName("state")
    var state: String = ""
    @SerializedName("countryIsoCode")
    var countryIsoCode: String = ""
    @SerializedName("stateIsoCode")
    var stateIsoCode: String = ""
    @SerializedName("city")
    var city: String = ""
    @SerializedName("country")
    var country: String = ""
    @SerializedName("firstName")
    var firstName: String = ""
    @SerializedName("lastName")
    var lastName: String = ""
    @SerializedName("mobileNumber")
    var mobileNumber: String = ""
    @SerializedName("countryCode")
    var countryCode: String = ""
    @SerializedName("email")
    var email: String = ""
    @SerializedName("transportName")
    var transportName: String = ""
    @SerializedName("gstNumber")
    var gstNumber: String = ""
    @SerializedName("location")
    var location: AddAddressLocation = AddAddressLocation()
}
class AddAddressLocation {
    @SerializedName("type")
    var type: String = ""
    @SerializedName("coordinates")
    var coordinates: ArrayList<Double> = ArrayList()
}
