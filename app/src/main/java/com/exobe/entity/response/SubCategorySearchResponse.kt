package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class SubCategorySearchResponse {
    @SerializedName("result"          ) var result          : SubCategorySearchResult? = SubCategorySearchResult()
    @SerializedName("responseMessage" ) var responseMessage : String? = ""
    @SerializedName("responseCode"    ) var responseCode    : Int?    = 0
}

class SubCategorySearchResult {
    @SerializedName("docs"  ) var docs  : ArrayList<SubCategorySearchDocs> = ArrayList()
    @SerializedName("total" ) var total : Int?            = 0
    @SerializedName("limit" ) var limit : Int?            = 0
    @SerializedName("page"  ) var page  : Int?            = 0
    @SerializedName("pages" ) var pages : Int?            = 0
}

class SubCategorySearchDocs {
    @SerializedName("productImage"  ) var productImage  : ArrayList<String> = ArrayList()
    @SerializedName("discount"      ) var discount      : Int?              = 0
    @SerializedName("productFor"    ) var productFor    : String?           = ""
    @SerializedName("status"        ) var status        : String?           = ""
    @SerializedName("_id"           ) var Id            : String?           = ""
    @SerializedName("brand"         ) var brand         : String?           = ""
    @SerializedName("categoryId"    ) var categoryId    : SubCategorySearchCategoryId?       = SubCategorySearchCategoryId()
    @SerializedName("description"   ) var description   : String?           = ""
    @SerializedName("price"         ) var price         : Number              = 0
    @SerializedName("productName"   ) var productName   : String?           = ""
    @SerializedName("quantity"      ) var quantity      : String?           = ""
    @SerializedName("subCategoryId" ) var subCategoryId : SubCategorySearchSubCategoryId?    = SubCategorySearchSubCategoryId()
    @SerializedName("unit"          ) var unit          : String?           = ""
    @SerializedName("isLike"          ) var isLike      : Boolean? = false
    @SerializedName("userId"        ) var userId        : SubCategorySearchUserId?           = SubCategorySearchUserId()
    @SerializedName("createdAt"     ) var createdAt     : String?           = ""
    @SerializedName("updatedAt"     ) var updatedAt     : String?           = ""
    @SerializedName("__v"           ) var _v            : Int?              = 0
}
class SubCategorySearchCategoryId {
    @SerializedName("status"        ) var status        : String? = ""
    @SerializedName("_id"           ) var Id            : String? = ""
    @SerializedName("categoryName"  ) var categoryName  : String? = ""
    @SerializedName("categoryImage" ) var categoryImage : String? = ""
    @SerializedName("description"   ) var description   : String? = ""
    @SerializedName("createdAt"     ) var createdAt     : String? = ""
    @SerializedName("updatedAt"     ) var updatedAt     : String? = ""
    @SerializedName("__v"           ) var _v            : Int?    = 0
}
class SubCategorySearchSubCategoryId {
    @SerializedName("status"          ) var status          : String? = ""
    @SerializedName("_id"             ) var Id              : String? = ""
    @SerializedName("categoryId"      ) var categoryId      : String? = ""
    @SerializedName("subCategoryName" ) var subCategoryName : String? = ""
    @SerializedName("__v"             ) var _v              : Int?    = 0
    @SerializedName("createdAt"       ) var createdAt       : String? = ""
    @SerializedName("updatedAt"       ) var updatedAt       : String? = ""
}
class SubCategorySearchUserId {

//    @SerializedName("storeLocation"                   ) var storeLocation                   : StoreLocation?          = StoreLocation()
//    @SerializedName("businessDetails"                 ) var businessDetails                 : BusinessDetails?        = BusinessDetails()
//    @SerializedName("businessBankingDetails"          ) var businessBankingDetails          : BusinessBankingDetails? = BusinessBankingDetails()
//    @SerializedName("serviceDetails"                  ) var serviceDetails                  : ServiceDetails?         = ServiceDetails()
//    @SerializedName("address"                         ) var address                         : String?                 = null,
//    @SerializedName("otpVerification"                 ) var otpVerification                 : Boolean?                = null,
//    @SerializedName("userVerification"                ) var userVerification                : Boolean?                = null,
//    @SerializedName("profilePic"                      ) var profilePic                      : String?                 = null,
//    @SerializedName("websiteUrl"                      ) var websiteUrl                      : String?                 = null,
//    @SerializedName("duration"                        ) var duration                        : String?                 = null,
//    @SerializedName("userRequestStatus"               ) var userRequestStatus               : String?                 = null,
//    @SerializedName("zipCode"                         ) var zipCode                         : String?                 = null,
//    @SerializedName("eoriNumber"                      ) var eoriNumber                      : String?                 = null,
//    @SerializedName("additionalDocName"               ) var additionalDocName               : String?                 = null,
//    @SerializedName("additionalDocument"              ) var additionalDocument              : String?                 = null,
//    @SerializedName("ownerFirstName"                  ) var ownerFirstName                  : String?                 = null,
//    @SerializedName("ownerLastName"                   ) var ownerLastName                   : String?                 = null,
//    @SerializedName("ownerEmail"                      ) var ownerEmail                      : String?                 = null,
//    @SerializedName("noOfUniqueProducts"              ) var noOfUniqueProducts              : Int?                    = null,
//    @SerializedName("listOfBrandOrProducts"           ) var listOfBrandOrProducts           : ArrayList<String>       = arrayListOf()
//    @SerializedName("keepStock"                       ) var keepStock                       : Boolean?                = null,
//    @SerializedName("isPhysicalStore"                 ) var isPhysicalStore                 : Boolean?                = null,
//    @SerializedName("howManyStoresDoYouHave"          ) var howManyStoresDoYouHave          : String?                 = null,
//    @SerializedName("preferredSupplierOrWholeSalerId" ) var preferredSupplierOrWholeSalerId : ArrayList<String>       = arrayListOf()
//    @SerializedName("comments"                        ) var comments                        : String?                 = null,
//    @SerializedName("completeProfile"                 ) var completeProfile                 : Boolean?                = null,
//    @SerializedName("flag"                            ) var flag                            : Int?                    = null,
//    @SerializedName("placeOrderCount"                 ) var placeOrderCount                 : Int?                    = null,
//    @SerializedName("serviceOrderCount"               ) var serviceOrderCount               : Int?                    = null,
//    @SerializedName("receiveOrderCount"               ) var receiveOrderCount               : Int?                    = null,
//    @SerializedName("status"                          ) var status                          : String?                 = null,
//    @SerializedName("_id"                             ) var Id                              : String?                 = null,
//    @SerializedName("addressLine1"                    ) var addressLine1                    : String?                 = null,
//    @SerializedName("addressLine2"                    ) var addressLine2                    : String?                 = null,
//    @SerializedName("city"                            ) var city                            : String?                 = null,
//    @SerializedName("country"                         ) var country                         : String?                 = null,
//    @SerializedName("countryCode"                     ) var countryCode                     : String?                 = null,
//    @SerializedName("email"                           ) var email                           : String?                 = null,
//    @SerializedName("firstName"                       ) var firstName                       : String?                 = null,
//    @SerializedName("lastName"                        ) var lastName                        : String?                 = null,
//    @SerializedName("mobileNumber"                    ) var mobileNumber                    : String?                 = null,
//    @SerializedName("password"                        ) var password                        : String?                 = null,
//    @SerializedName("userType"                        ) var userType                        : String?                 = null,
//    @SerializedName("createdAt"                       ) var createdAt                       : String?                 = null,
//    @SerializedName("updatedAt"                       ) var updatedAt                       : String?                 = null,
//    @SerializedName("__v"                             ) var _v                              : Int?                    = null,
//    @SerializedName("userUniqueId"                    ) var userUniqueId                    : String?                 = null

}






