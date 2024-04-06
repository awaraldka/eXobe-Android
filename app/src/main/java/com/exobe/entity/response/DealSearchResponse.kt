package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class DealSearchResponse {
    @SerializedName("result"          ) var result          : DealSearchResult? = DealSearchResult()
    @SerializedName("responseMessage" ) var responseMessage : String? = ""
    @SerializedName("responseCode"    ) var responseCode    : Int?    = 0
}

class DealSearchResult {

    @SerializedName("docs")
    var docs: ArrayList<DealSearchDocs> = ArrayList()
    @SerializedName("total")
    var total: Int? = 0
    @SerializedName("limit")
    var limit: Int? = 0
    @SerializedName("page")
    var page: Int? = 0
    @SerializedName("pages")
    var pages: Int? = 0
}
class DealSearchDocs {

    @SerializedName("productImage")
    var productImage: ArrayList<String> = ArrayList()
    @SerializedName("discount")
    var discount: Int? = null
    @SerializedName("productFor")
    var productFor: String? = null
    @SerializedName("status")
    var status: String? = null
    @SerializedName("_id")
    var _id: String? = null
    @SerializedName("brand")
    var brand: String? = null
    @SerializedName("categoryId")
    var categoryId: DealSearchCategoryId? = DealSearchCategoryId()
    @SerializedName("description")
    var description: String? = null
    @SerializedName("price")
    var price: Number = 0
    @SerializedName("productName")
    var productName: String? = null
    @SerializedName("productReferenceId")
    var productReferenceId: String? = ""
    @SerializedName("quantity")
    var quantity: String? = null
    @SerializedName("subCategoryId")
    var subCategoryId: DealSearchSubCategoryId? = DealSearchSubCategoryId()
//    @SerializedName("unit")
//    var unit: String? = null
    @SerializedName("userId")
    var userId: DealSearchUserId? = DealSearchUserId()
    @SerializedName("createdAt")
    var createdAt: String? = null
    @SerializedName("updatedAt")
    var updatedAt: String? = null
    @SerializedName("__v")
    var _v: Int? = null
}

class DealSearchCategoryId {

    @SerializedName("status")
    var status: String? = null
    @SerializedName("_id")
    var Id: String? = null
    @SerializedName("categoryName")
    var categoryName: String? = null
    @SerializedName("categoryImage")
    var categoryImage: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("createdAt")
    var createdAt: String? = null
    @SerializedName("updatedAt")
    var updatedAt: String? = null
    @SerializedName("__v")
    var _v: Int? = null
}

class DealSearchSubCategoryId{

    @SerializedName("status"          ) var status          : String? = null
    @SerializedName("_id"             ) var Id              : String? = null
    @SerializedName("categoryId"      ) var categoryId      : String? = null
    @SerializedName("subCategoryName" ) var subCategoryName : String? = null
    @SerializedName("__v"             ) var _v              : Int?    = null
    @SerializedName("createdAt"       ) var createdAt       : String? = null
    @SerializedName("updatedAt"       ) var updatedAt       : String? = null
}

class DealSearchUserId {

//    @SerializedName("storeLocation"                   ) var storeLocation                   : StoreLocation?          = StoreLocation()
//    @SerializedName("businessDetails"                 ) var businessDetails                 : BusinessDetails?        = BusinessDetails()
//    @SerializedName("businessBankingDetails"          ) var businessBankingDetails          : BusinessBankingDetails? = BusinessBankingDetails()
//    @SerializedName("serviceDetails"                  ) var serviceDetails                  : ServiceDetails?         = ServiceDetails()
//    @SerializedName("address"                         ) var address                         : String?                 = null
//    @SerializedName("otpVerification"                 ) var otpVerification                 : Boolean?                = null
//    @SerializedName("userVerification"                ) var userVerification                : Boolean?                = null
//    @SerializedName("profilePic"                      ) var profilePic                      : String?                 = null
//    @SerializedName("websiteUrl"                      ) var websiteUrl                      : String?                 = null
//    @SerializedName("duration"                        ) var duration                        : String?                 = null
//    @SerializedName("userRequestStatus"               ) var userRequestStatus               : String?                 = null
//    @SerializedName("zipCode"                         ) var zipCode                         : String?                 = null
//    @SerializedName("eoriNumber"                      ) var eoriNumber                      : String?                 = null
//    @SerializedName("additionalDocName"               ) var additionalDocName               : String?                 = null
//    @SerializedName("additionalDocument"              ) var additionalDocument              : String?                 = null
//    @SerializedName("ownerFirstName"                  ) var ownerFirstName                  : String?                 = null
//    @SerializedName("ownerLastName"                   ) var ownerLastName                   : String?                 = null
//    @SerializedName("ownerEmail"                      ) var ownerEmail                      : String?                 = null
//    @SerializedName("noOfUniqueProducts"              ) var noOfUniqueProducts              : Int?                    = null
//    @SerializedName("listOfBrandOrProducts"           ) var listOfBrandOrProducts           : ArrayList<String>       = arrayListOf()
//    @SerializedName("keepStock"                       ) var keepStock                       : Boolean?                = null
//    @SerializedName("isPhysicalStore"                 ) var isPhysicalStore                 : Boolean?                = null
//    @SerializedName("howManyStoresDoYouHave"          ) var howManyStoresDoYouHave          : String?                 = null
//    @SerializedName("preferredSupplierOrWholeSalerId" ) var preferredSupplierOrWholeSalerId : ArrayList<String>       = arrayListOf()
//    @SerializedName("comments"                        ) var comments                        : String?                 = null
//    @SerializedName("completeProfile"                 ) var completeProfile                 : Boolean?                = null
//    @SerializedName("flag"                            ) var flag                            : Int?                    = null
//    @SerializedName("placeOrderCount"                 ) var placeOrderCount                 : Int?                    = null
//    @SerializedName("serviceOrderCount"               ) var serviceOrderCount               : Int?                    = null
//    @SerializedName("receiveOrderCount"               ) var receiveOrderCount               : Int?                    = null
//    @SerializedName("status"                          ) var status                          : String?                 = null
//    @SerializedName("_id"                             ) var Id                              : String?                 = null
//    @SerializedName("addressLine1"                    ) var addressLine1                    : String?                 = null
//    @SerializedName("addressLine2"                    ) var addressLine2                    : String?                 = null
//    @SerializedName("city"                            ) var city                            : String?                 = null
//    @SerializedName("country"                         ) var country                         : String?                 = null
//    @SerializedName("countryCode"                     ) var countryCode                     : String?                 = null
//    @SerializedName("email"                           ) var email                           : String?                 = null
//    @SerializedName("firstName"                       ) var firstName                       : String?                 = null
//    @SerializedName("lastName"                        ) var lastName                        : String?                 = null
//    @SerializedName("mobileNumber"                    ) var mobileNumber                    : String?                 = null
//    @SerializedName("password"                        ) var password                        : String?                 = null
//    @SerializedName("userType"                        ) var userType                        : String?                 = null
//    @SerializedName("createdAt"                       ) var createdAt                       : String?                 = null
//    @SerializedName("updatedAt"                       ) var updatedAt                       : String?                 = null
//    @SerializedName("__v"                             ) var _v                              : Int?                    = null
//    @SerializedName("userUniqueId"                    ) var userUniqueId                    : String?                 = null
}





