package com.exobe.entity.response

import com.google.gson.annotations.SerializedName



class FilldetailsServiceProvider_Response(
    @SerializedName("result") val result : FilldetailsServiceProvider_Results,
    @SerializedName("responseMessage") val responseMessage : String,
    @SerializedName("responseCode") val responseCode : Int?=null
)

class FilldetailsServiceProvider_Results(
    @SerializedName("flag") val flag: Int,
    @SerializedName("ownerFirstName") val ownerFirstName: String,
    @SerializedName("ownerLastName") val ownerLastName: String,
    @SerializedName("ownerEmail") val ownerEmail: String,
    @SerializedName("noOfUniqueProducts") val noOfUniqueProducts: Int,
    @SerializedName("listOfBrandOrProducts") val listOfBrandOrProducts: List<String>,
    @SerializedName("keepStock") val keepStock: Boolean,
    @SerializedName("isPhysicalStore") val isPhysicalStore: Boolean,
    @SerializedName("preferredSupplierOrWholeSalerId") val preferredSupplierOrWholeSalerId: List<String>,
    @SerializedName("comments") val comments: String,
    @SerializedName("businessDetails") val businessDetails: ServiceProvider_BusinessDetails,
    @SerializedName("businessDocumentUpload") val businessDocumentUpload: BusinessDocumentUpload,
    @SerializedName("serviceDetails") val serviceDetails: ServiceDetails,
    @SerializedName("serviceDetailsArray") val serviceDetailsArray: List<ServiceProvider_ServicedetailsArray>
)
class ServiceProvider_ServicedetailsArray(
    @SerializedName("categoryId") val categoryId : String,
    @SerializedName("serviceCategoryArray") val serviceCategoryArray : List<ServiceCategoryArray>
)
class ServiceCategoryArray(
    @SerializedName("price") val price : Number=0,
    @SerializedName("subCategoryId") val subCategoryId : String
)
class ServiceProvider_BusinessBankingDetails (

    @SerializedName("bankName") val bankName : String,
    @SerializedName("branchName") val branchName : String,
    @SerializedName("branchCode") val branchCode : String,
    @SerializedName("swiftCode") val swiftCode : String,
    @SerializedName("accountType") val accountType : String,
    @SerializedName("accountName") val accountName : String,
    @SerializedName("accountNumber") val accountNumber : String
)
class ServiceProvider_BusinessDetails(
    @SerializedName("companyName") val companyName : String,
    @SerializedName("businessRegNumber") val businessRegNumber : String,
    @SerializedName("categoryId") val categoryId : String,
    @SerializedName("websiteUrl") val websiteUrl : String,
    @SerializedName("socialMediaAccounts") val socialMediaAccounts : String,
    @SerializedName("isVatRegistered") val isVatRegistered : Boolean,
    @SerializedName("vatNumber") val vatNumber : String,
    @SerializedName("monthlyRevenue") val monthlyRevenue : String
)
class ServiceProvider_Service_Details(
    @SerializedName("noOfUniqueService") val noOfUniqueService : Int,
    @SerializedName("preferredSupplierOrWholeSalerId") val preferredSupplierOrWholeSalerId : List<String>,
    @SerializedName("comments") val comments : String
)

