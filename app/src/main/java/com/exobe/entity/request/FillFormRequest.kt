package com.exobe.entity.request

import com.google.gson.annotations.SerializedName

class FillFormRequest {
    @SerializedName("flag")
    var flag : Int? = null
    @SerializedName("ownerFirstName")
    var ownerFirstName : String? = null
    @SerializedName("ownerLastName")
    var ownerLastName : String? = null
    @SerializedName("ownerEmail")
    var ownerEmail : String? = null
    @SerializedName("noOfUniqueProducts")
    var noOfUniqueProducts : Int? = null
    @SerializedName("listOfBrandOrProducts")
    var listOfBrandOrProducts : ArrayList<String>? = null
    @SerializedName("keepStock")
    var keepStock : Boolean? = null
    @SerializedName("isPhysicalStore")
    var isPhysicalStore : Boolean? = null
    @SerializedName("preferredSupplierOrWholeSalerId")
    var preferredSupplierOrWholeSalerId : ArrayList<String>? = null
    @SerializedName("comments")
    var comments : String? = null
    @SerializedName("businessDetails")
    var businessDetails : BusinessDetails? = null
    @SerializedName("businessBankingDetails")
    var businessBankingDetails : BusinessBankingDetails? = null
    @SerializedName("businessDocumentUpload") val businessDocumentUpload : BusinessDocumentUpload? = null
    @SerializedName("storeAddress")
    var storeAddress: String? = null
    @SerializedName("storeName")
    var storeName: String? = null
    @SerializedName("storeContactNo")
    var storeContactNo: String? = null
//    @SerializedName("storeLocation")
//    var storeLocation: requestStoreLocation = requestStoreLocation()
}

class SP_FillFormRequest {
    @SerializedName("flag")
    var flag : Int? = null
    @SerializedName("ownerFirstName")
    var ownerFirstName : String? = null
    @SerializedName("ownerLastName")
    var ownerLastName : String? = null
    @SerializedName("ownerEmail")
    var ownerEmail : String? = null
    @SerializedName("noOfUniqueProducts")
    var noOfUniqueProducts : Int? = null
    @SerializedName("listOfBrandOrProducts")
    var listOfBrandOrProducts : ArrayList<String>? = null
    @SerializedName("keepStock")
    var keepStock : Boolean? = null
    @SerializedName("isPhysicalStore")
    var isPhysicalStore : Boolean? = null
    @SerializedName("preferredSupplierOrWholeSalerId")
    var preferredSupplierOrWholeSalerId : ArrayList<String>? = null
    @SerializedName("comments")
    var comments : String? = null
    @SerializedName("businessDetails")
    var businessDetails : BusinessDetails? = null
    @SerializedName("businessBankingDetails")
    var businessBankingDetails : BusinessBankingDetails? = null
    @SerializedName("businessDocumentUpload") val businessDocumentUpload : BusinessDocumentUpload? = null
}



class requestStoreLocation {
    //
    @SerializedName("type")
    var type: String = ""
    @SerializedName("coordinates")
    var coordinates: ArrayList<Double> = ArrayList()
}

class BankConfirmationLetter {

    @SerializedName("frontImage") val frontImage : String? = null
    @SerializedName("backImage") val backImage : String? = null
}

class BusinessBankingDetails {

    @SerializedName("bankName")
    var bankName: String? = null
    @SerializedName("branchName")
    var branchName: String? = null
    @SerializedName("branchCode")
    var branchCode: String? = null
    @SerializedName("swiftCode")
    var swiftCode: String? = null
    @SerializedName("accountType")
    var accountType: String? = null
    @SerializedName("accountName")
    var accountName: String? = null
    @SerializedName("accountNumber")
    var accountNumber: String? = null
}

class BusinessDetails {

    @SerializedName("companyName")
    var companyName: String? = null
    @SerializedName("businessRegNumber")
    var businessRegNumber: String? = null
    @SerializedName("categoryId")
    var categoryId: String? = null
    @SerializedName("websiteUrl")
    var websiteUrl: String? = null
    @SerializedName("socialMediaAccounts")
    var socialMediaAccounts: String? = null
    @SerializedName("isVatRegistered")
    var isVatRegistered: Boolean? = null
    @SerializedName("vatNumber")
    var vatNumber: String? = null
    @SerializedName("monthlyRevenue")
    var monthlyRevenue: String? = null

}

class BusinessDocumentUpload {

    @SerializedName("cirtificationOfIncorporation")
    val cirtificationOfIncorporation: CirtificationOfIncorporation? = null
    @SerializedName("directConsentForm")
    val directConsentForm: DirectConsentForm? = null
    @SerializedName("VatRegConfirmationDocs")
    val vatRegConfirmationDocs: VatRegConfirmationDocs? = null
    @SerializedName("directorId")
    val directorId: DirectorId? = null
    @SerializedName("bankConfirmationLetter")
    val bankConfirmationLetter: BankConfirmationLetter? = null
}

class CirtificationOfIncorporation {

    @SerializedName("frontImage")
    val frontImage: String? = null
    @SerializedName("backImage")
    val backImage: String? = null
}

class DirectConsentForm {

    @SerializedName("frontImage")
    val frontImage: String? = null
    @SerializedName("backImage")
    val backImage: String? = null
}

class DirectorId {

    @SerializedName("frontImage")
    val frontImage: String? = null
    @SerializedName("backImage")
    val backImage: String? = null
}

class ServiceCategoryArray {

    @SerializedName("price")
    val price: Int? = null
    @SerializedName("subCategoryId")
    val subCategoryId: String? = null
}

class ServiceDetails {

    @SerializedName("noOfUniqueService")
    val noOfUniqueService: Int? = null
    @SerializedName("preferredSupplierOrWholeSalerId")
    val preferredSupplierOrWholeSalerId: List<String>? = null
    @SerializedName("comments")
    val comments: String? = null
}

class ServiceDetailsArray {

    @SerializedName("categoryId")
    val categoryId: String? = null
    @SerializedName("serviceCategoryArray")
    val serviceCategoryArray: ArrayList<ServiceCategoryArray>? = null
}


class VatRegConfirmationDocs {

    @SerializedName("frontImage")
    val frontImage: String? = null
    @SerializedName("backImage")
    val backImage: String? = null
}



class CommonBusinessFormRequest {
    @SerializedName("flag") var flag : Int? = null
    @SerializedName("firstName") var firstName : String? = ""
    @SerializedName("lastName") var lastName : String? = ""
    @SerializedName("email") var email : String? = ""
    @SerializedName("mobileNumber") var mobileNumber : String? = ""
    @SerializedName("driverDetail") var driverDetail : DriverDetail? = DriverDetail()
    @SerializedName("businessBankingDetails") var businessBankingDetails : BusinessBankingDetails? = BusinessBankingDetails()
    @SerializedName("fieldEntityDetails") var filedEnity : FieldEntityDetails? = FieldEntityDetails()

}

class DriverDetail(
    @SerializedName("licenceNumber") var licenceNumber: String? = "",
    @SerializedName("vehicleRegistartionNumber") var vehicleRegistrationNumber: String? = "",
    @SerializedName("vehicleModel") var vehicleModel: String? = "",
    @SerializedName("vehicleYear") var vehicleYear: String? = "",
    @SerializedName("vehicleType") var vehicleType: String? = "",
    @SerializedName("vehicleColour") var vehicleColour: String? = "",
    @SerializedName("insuranceNumber") var insuranceNumber: String? = ""


)

class FieldEntityDetails(
    @SerializedName("businessName") var businessName: String? = "",
    @SerializedName("businessType") var businessType: String? = "",
    @SerializedName("businessAddress") var businessAddress: String? = "",
    @SerializedName("businessWebsite") var businessWebsite: String? = "",
    @SerializedName("businessEmail") var businessEmail: String? = "",
    @SerializedName("einNum") var einNum: String? = "",
    @SerializedName("salesTaxId") var salesTaxId: String? = "",
    @SerializedName("monthlyOrderVolume") var monthlyOrderVolume: String? = "",
)


