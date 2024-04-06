package com.exobe.entity.response


import com.exobe.entity.request.DriverDetail
import com.exobe.entity.request.FieldEntityDetails
import com.google.gson.annotations.SerializedName

class RetailerFillDeatilsViewResponse {
    @SerializedName("result")
    val result: RetailerFillDeatilsResult = RetailerFillDeatilsResult()

    @SerializedName("responseMessage")
    val responseMessage: String = ""

    @SerializedName("responseCode")
    val responseCode: Int = 0

}



class RetailerFillDeatilsResult {

    @SerializedName("storeLocation") val storeLocation: RetailerFillDeatilsStoreLocation = RetailerFillDeatilsStoreLocation()
    @SerializedName("businessDetails") val businessDetails: RetailerFillDeatilsBusinessDetails = RetailerFillDeatilsBusinessDetails()
    @SerializedName("businessBankingDetails") val businessBankingDetails: RetailerFillDeatilsBusinessBankingDetails = RetailerFillDeatilsBusinessBankingDetails()
    @SerializedName("serviceDetails") val serviceDetails: RetailerFillDeatilsServiceDetails = RetailerFillDeatilsServiceDetails()
    @SerializedName("driverDetail") var driverDetail : DriverDetail? = DriverDetail()
    @SerializedName("fieldEntityDetails") var fieldEntityDetails : FieldEntityDetails? = FieldEntityDetails()

    @SerializedName("address") val address :  String = ""
    @SerializedName("otpVerification") val otpVerification : Boolean = false
    @SerializedName("userVerification") val userVerification : Boolean = false
    @SerializedName("profilePic") val profilePic :  String = ""
    @SerializedName("websiteUrl") val websiteUrl :  String = ""
    @SerializedName("duration") val duration :  String = ""
    @SerializedName("userRequestStatus") val userRequestStatus :  String = ""
    @SerializedName("zipCode") val zipCode : String = ""
    @SerializedName("eoriNumber") val eoriNumber :  String = ""
    @SerializedName("additionalDocName") val additionalDocName :  String = ""
    @SerializedName("additionalDocument") val additionalDocument :  String = ""
    @SerializedName("ownerFirstName") val ownerFirstName :  String = ""
    @SerializedName("ownerLastName") val ownerLastName :  String = ""
    @SerializedName("ownerEmail") val ownerEmail :  String = ""
    @SerializedName("noOfUniqueProducts") val noOfUniqueProducts : Int =  0
    @SerializedName("listOfBrandOrProducts") val listOfBrandOrProducts : ArrayList<String> = ArrayList()
    @SerializedName("keepStock") val keepStock : Boolean = false
    @SerializedName("isPhysicalStore") val isPhysicalStore : Boolean = false
    @SerializedName("howManyStoresDoYouHave") val howManyStoresDoYouHave : String = ""
    @SerializedName("preferredSupplierOrWholeSalerId") val preferredSupplierOrWholeSalerId : ArrayList<RetailerPreferredSupplierOrWholeSalerId> = ArrayList()
    @SerializedName("comments") val comments :  String = ""
    @SerializedName("completeProfile") val completeProfile : Boolean = false
    @SerializedName("flag") val flag : Int =  0
    @SerializedName("placeOrderCount") val placeOrderCount : Int =  0
    @SerializedName("serviceOrderCount") val serviceOrderCount :  String = ""
    @SerializedName("receiveOrderCount") val receiveOrderCount :  String = ""
    @SerializedName("status") val status :  String = ""
    @SerializedName("_id") val _id :  String = ""
    @SerializedName("addressLine1") val addressLine1 :  String = ""
    @SerializedName("addressLine2") val addressLine2 :  String = ""
    @SerializedName("city") val city :  String = ""
    @SerializedName("country") val country :  String = ""
    @SerializedName("countryCode") val countryCode : String = ""
    @SerializedName("email") val email :  String = ""
    @SerializedName("firstName") val firstName :  String = ""
    @SerializedName("lastName") val lastName :  String = ""
    @SerializedName("mobileNumber") val mobileNumber : String = ""
    @SerializedName("password") val password :  String = ""
    @SerializedName("userType") val userType :  String = ""
    @SerializedName("createdAt") val createdAt :  String = ""
    @SerializedName("updatedAt") val updatedAt :  String = ""
    @SerializedName("__v") val __v : Int =  0
    @SerializedName("deviceToken") val deviceToken :  String = ""
    @SerializedName("deviceType") val deviceType : String  = ""
    @SerializedName("storeAddress") val storeAddress : String  = ""
    @SerializedName("storeName") val storeName : String  = ""
    @SerializedName("storeContactNo") val storeContactNo : String  = ""



}

class RetailerPreferredSupplierOrWholeSalerId {
    @SerializedName("firstName")
    val firstName: String = ""
    @SerializedName("lastName")
    val lastName: String = ""
    @SerializedName("_id")
    val _id: String = ""
}

class RetailerFillDeatilsServiceDetails {

    @SerializedName("noOfUniqueService")
    val noOfUniqueService: Int = 0
    @SerializedName("preferredSupplierOrWholeSalerId")
    val preferredSupplierOrWholeSalerId: ArrayList<String> = ArrayList()
    @SerializedName("comments")
    val comments: String = ""
    @SerializedName("listOfServices")
    val listOfServices: ArrayList<String> = ArrayList()
}

class RetailerFillDeatilsStoreLocation {
    //
    @SerializedName("type")
    val type: String = ""
    @SerializedName("coordinates")
    val coordinates: ArrayList<Double> = ArrayList()
}

class RetailerFillDeatilsBusinessDetails {

    @SerializedName("companyName") val companyName : String = ""
    @SerializedName("businessRegNumber") val businessRegNumber : String = ""
    @SerializedName("websiteUrl") val websiteUrl : String = ""
    @SerializedName("socialMediaAccounts") val socialMediaAccounts : String = ""
    @SerializedName("isVatRegistered") val isVatRegistered : Boolean = false
    @SerializedName("vatNumber") val vatNumber : String = ""
    @SerializedName("monthlyRevenue") val monthlyRevenue : String = ""
}

class RetailerFillDeatilsBusinessBankingDetails {

    @SerializedName("bankName")
    val bankName: String = ""
    @SerializedName("branchName")
    val branchName: String = ""
    @SerializedName("branchCode")
    val branchCode: String = ""
    @SerializedName("swiftCode")
    val swiftCode: String = ""
    @SerializedName("accountType")
    val accountType: String = ""
    @SerializedName("accountName")
    val accountName: String = ""
    @SerializedName("accountNumber")
    val accountNumber: String = ""
}