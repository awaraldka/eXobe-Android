package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class DealListResponse {

    @SerializedName("result"          ) var result          : DealListResult?=null
    @SerializedName("responseMessage" ) var responseMessage : String?           = ""
    @SerializedName("responseCode"    ) var responseCode    : Int?              = 0
}

class DealListResult {

    @SerializedName("docs"          ) var docs          : ArrayList<DealListDoc>?=null
    @SerializedName("total")
    val total: Int = 0
    @SerializedName("limit")
    val limit: Int = 0
    @SerializedName("page")
    val page: Int = 0
    @SerializedName("pages")
    val pages: Int = 0
}

class DealListDoc {
    @SerializedName("quantity"      ) var quantity      : Int?                 = 0
    @SerializedName("disCountType"  ) var disCountType  : String?              = ""
    @SerializedName("thumbnail"  ) var thumbnail  : String?              = ""
    @SerializedName("dealsFor"      ) var dealsFor      : String?              = ""
    @SerializedName("productId"     ) var productId     : ArrayList<DealListProductId> = ArrayList()
    @SerializedName("expired"       ) var expired       : Boolean?             = false
    @SerializedName("status"        ) var status        : String?              = ""
    @SerializedName("_id"           ) var Id            : String?              = ""
    @SerializedName("dealName"      ) var dealName      : String?              = ""
    @SerializedName("dealImage"     ) var dealImage     : ArrayList<String> = ArrayList()
    @SerializedName("description"   ) var description   : String?              = ""
    @SerializedName("dealPrice"     ) var dealPrice     : Number?                 = 0
    @SerializedName("dealStartTime" ) var dealStartTime : String?              = ""
    @SerializedName("dealEndTime"   ) var dealEndTime   : String?              = ""
    @SerializedName("dealType"      ) var dealType      : String?              = ""
    @SerializedName("dealDiscount"      ) var dealDiscount      : String?              = ""
    //    @SerializedName("userId"        ) var userId        : DealListUserId?              = DealListUserId()
    @SerializedName("createdAt"     ) var createdAt     : String?              = ""
    @SerializedName("updatedAt"     ) var updatedAt     : String?              = ""
    @SerializedName("__v"           ) var _v            : Int?                 = 0
}

class DealListProductId {

    @SerializedName("productImage")
    var productImage: ArrayList<String> = ArrayList()
    @SerializedName("discount")
    var discount: Int? = 0
    @SerializedName("productFor")
    var productFor: String? = ""
    @SerializedName("status")
    var status: String? = ""
    @SerializedName("_id")
    var Id: String? = ""
    @SerializedName("productName")
    var productName: String? = ""
    @SerializedName("price")
    var price: Number? = 0
//    @SerializedName("unit")
//    var unit: String? = ""
    @SerializedName("brand")
    var brand: String? = ""
    @SerializedName("description")
    var description: String? = ""
    @SerializedName("categoryId")
    var categoryId: DealListCategoryId? = DealListCategoryId()
    @SerializedName("subCategoryId")
    var subCategoryId: DealListSubCategoryId? = DealListSubCategoryId()
    @SerializedName("quantity")
    var quantity: String? = ""
    @SerializedName("productReferenceId")
    var productReferenceId: String? = ""
    @SerializedName("userId")
    var userId: String? = ""
    @SerializedName("createdAt")
    var createdAt: String? = ""
    @SerializedName("updatedAt")
    var updatedAt: String? = ""
    @SerializedName("__v")
    var _v: Int? = 0
}

class DealListCategoryId {
    @SerializedName("status")
    var status: String? = ""
    @SerializedName("_id")
    var Id: String? = ""
    @SerializedName("categoryName")
    var categoryName: String? = ""
    @SerializedName("categoryImage")
    var categoryImage: String? = ""
    @SerializedName("description")
    var description: String? = ""
    @SerializedName("createdAt")
    var createdAt: String? = ""
    @SerializedName("updatedAt")
    var updatedAt: String? = ""
    @SerializedName("__v")
    var _v: Int? = 0
}

class DealListSubCategoryId {
    @SerializedName("status"          ) var status          : String? = ""
    @SerializedName("_id"             ) var Id              : String? = ""
    @SerializedName("categoryId"      ) var categoryId      : String? = ""
    @SerializedName("subCategoryName" ) var subCategoryName : String? = ""
    @SerializedName("__v"             ) var _v              : Int?    = 0
    @SerializedName("createdAt"       ) var createdAt       : String? = ""
    @SerializedName("updatedAt"       ) var updatedAt       : String? = ""
}



class DealListUserId {
////    @SerializedName("storeLocation"                   ) var storeLocation                   : StoreLocation?          = StoreLocation()
////    @SerializedName("govtDocument"                    ) var govtDocument                    : GovtDocument?           = GovtDocument()
////    @SerializedName("socialLink"                      ) var socialLink                      : SocialLink?             = SocialLink()
////    @SerializedName("businessDetails"                 ) var businessDetails                 : BusinessDetails?        = BusinessDetails()
////    @SerializedName("businessBankingDetails"          ) var businessBankingDetails          : BusinessBankingDetails? = BusinessBankingDetails()
////    @SerializedName("businessDocumentUpload"          ) var businessDocumentUpload          : BusinessDocumentUpload? = BusinessDocumentUpload()
////    @SerializedName("serviceDetails"                  ) var serviceDetails                  : ServiceDetails?         = ServiceDetails()
//    @SerializedName("address"                         ) var address                         : String?                 = ""
//    @SerializedName("otpVerification"                 ) var otpVerification                 : Boolean?                = false
//    @SerializedName("userVerification"                ) var userVerification                : Boolean?                = false
//    @SerializedName("profilePic"                      ) var profilePic                      : String?                 = ""
//    @SerializedName("websiteUrl"                      ) var websiteUrl                      : String?                 = ""
//    @SerializedName("duration"                        ) var duration                        : String?                 = ""
//    @SerializedName("userRequestStatus"               ) var userRequestStatus               : String?                 = ""
//    @SerializedName("zipCode"                         ) var zipCode                         : String?                 = ""
//    @SerializedName("eoriNumber"                      ) var eoriNumber                      : String?                 = ""
//    @SerializedName("additionalDocName"               ) var additionalDocName               : String?                 = ""
//    @SerializedName("additionalDocument"              ) var additionalDocument              : String?                 = ""
//    @SerializedName("ownerFirstName"                  ) var ownerFirstName                  : String?                 = ""
//    @SerializedName("ownerLastName"                   ) var ownerLastName                   : String?                 = ""
//    @SerializedName("ownerEmail"                      ) var ownerEmail                      : String?                 = ""
//    @SerializedName("noOfUniqueProducts"              ) var noOfUniqueProducts              : Int?                    = 0
//    @SerializedName("listOfBrandOrProducts"           ) var listOfBrandOrProducts           : ArrayList<String>       = ArrayList()
//    @SerializedName("keepStock"                       ) var keepStock                       : Boolean?                = false
//    @SerializedName("isPhysicalStore"                 ) var isPhysicalStore                 : Boolean?                = false
//    @SerializedName("howManyStoresDoYouHave"          ) var howManyStoresDoYouHave          : String?                 = ""
//    @SerializedName("preferredSupplierOrWholeSalerId" ) var preferredSupplierOrWholeSalerId : ArrayList<String>       = ArrayList()
//    @SerializedName("comments"                        ) var comments                        : String?                 = ""
//    @SerializedName("completeProfile"                 ) var completeProfile                 : Boolean?                = false
//    @SerializedName("flag"                            ) var flag                            : Int?                    = 0
//    @SerializedName("placeOrderCount"                 ) var placeOrderCount                 : Int?                    = 0
//    @SerializedName("serviceOrderCount"               ) var serviceOrderCount               : Int?                    = 0
//    @SerializedName("receiveOrderCount"               ) var receiveOrderCount               : Int?                    = 0
//    @SerializedName("status"                          ) var status                          : String?                 = ""
//    @SerializedName("_id"                             ) var Id                              : String?                 = ""
//    @SerializedName("userType"                        ) var userType                        : String?                 = ""
//    @SerializedName("firstName"                       ) var firstName                       : String?                 = ""
//    @SerializedName("lastName"                        ) var lastName                        : String?                 = ""
//    @SerializedName("countryCode"                     ) var countryCode                     : String?                 = ""
//    @SerializedName("mobileNumber"                    ) var mobileNumber                    : String?                 = ""
//    @SerializedName("email"                           ) var email                           : String?                 = ""
//    @SerializedName("phoneNumber"                     ) var phoneNumber                     : String?                 = ""
//    @SerializedName("storeAddress"                    ) var storeAddress                    : String?                 = ""
//    @SerializedName("storeName"                       ) var storeName                       : String?                 = ""
//    @SerializedName("storeContactNo"                  ) var storeContactNo                  : String?                 = ""
//    @SerializedName("storeBrand"                      ) var storeBrand                      : String?                 = ""
//    @SerializedName("password"                        ) var password                        : String?                 = ""
//    @SerializedName("city"                            ) var city                            : String?                 = ""
//    @SerializedName("state"                           ) var state                           : String?                 = ""
//    @SerializedName("country"                         ) var country                         : String?                 = ""
//    @SerializedName("addressLine1"                    ) var addressLine1                    : String?                 = ""
//    @SerializedName("addressLine2"                    ) var addressLine2                    : String?                 = ""
//    @SerializedName("createdAt"                       ) var createdAt                       : String?                 = ""
//    @SerializedName("updatedAt"                       ) var updatedAt                       : String?                 = ""
//    @SerializedName("__v"                             ) var _v                              : Int?                    = 0
//    @SerializedName("userUniqueId"                    ) var userUniqueId                    : String?                 = ""
}
