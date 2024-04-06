package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

data class PaymentRetailerCustomerResponse (
    @SerializedName("result"          ) var result          : PaymentRetailerCustomerResult? = PaymentRetailerCustomerResult(),
    @SerializedName("responseMessage" ) var responseMessage : String? = null,
    @SerializedName("responseCode"    ) var responseCode    : Int?    = null
)
data class PaymentRetailerCustomerResult (

    @SerializedName("docs"  ) var docs  : ArrayList<PaymentRetailerCustomerDocs> = arrayListOf(),
    @SerializedName("total" ) var total : Int?            = null,
    @SerializedName("limit" ) var limit : Int?            = null,
    @SerializedName("page"  ) var page  : Int?            = null,
    @SerializedName("pages" ) var pages : Int?            = null

)
data class PaymentRetailerCustomerDocs (

    @SerializedName("_id"                ) var Id                 : String?                  = null,
    @SerializedName("status"             ) var status             : String?                  = null,
    @SerializedName("transactionStatus"  ) var transactionStatus  : String?                  = null,
    @SerializedName("transactionDetails" ) var transactionDetails : PaymentRetailerCustomerTransactionDetails?      = PaymentRetailerCustomerTransactionDetails(),
    @SerializedName("userId"             ) var userId             : String?                  = null,
    @SerializedName("orderId"            ) var orderId            : String?                  = null,
    @SerializedName("transactionMethod"  ) var transactionMethod  : String?                  = null,
    @SerializedName("orderAmount"        ) var orderAmount        : Int?                     = null,
    @SerializedName("shippingAddress"    ) var shippingAddress    : String?                  = null,
    @SerializedName("currencyCode"       ) var currencyCode       : String?                  = null,
    @SerializedName("createdAt"          ) var createdAt          : String?                  = null,
    @SerializedName("updatedAt"          ) var updatedAt          : String?                  = null,
    @SerializedName("__v"                ) var _v                 : Int?                     = null,
//    @SerializedName("userDetails"        ) var userDetails        : PaymentRetailerCustomerUserDetails?             = PaymentRetailerCustomerUserDetails(),
//    @SerializedName("orderDetails"       ) var orderDetails       : PaymentRetailerCustomerOrderDetails?            = PaymentRetailerCustomerOrderDetails(),
//    @SerializedName("orders"             ) var orders             : ArrayList<PaymentRetailerCustomerOrders>        = arrayListOf(),
//    @SerializedName("PaidToDetails"      ) var PaidToDetails      : ArrayList<PaymentRetailerCustomerPaidToDetails> = arrayListOf()

)
data class PaymentRetailerCustomerTransactionDetails (

    @SerializedName("id"                              ) var id                            : String?               = null,
    @SerializedName("object"                          ) var object1                        : String?               = null,
    @SerializedName("amount"                          ) var amount                        : Int?                  = null,
    @SerializedName("amount_captured"                 ) var amountCaptured                : Int?                  = null,
    @SerializedName("amount_refunded"                 ) var amountRefunded                : Int?                  = null,
    @SerializedName("application"                     ) var application                   : String?               = null,
    @SerializedName("application_fee"                 ) var applicationFee                : String?               = null,
    @SerializedName("application_fee_amount"          ) var applicationFeeAmount          : String?               = null,
    @SerializedName("balance_transaction"             ) var balanceTransaction            : String?               = null,
    @SerializedName("billing_details"                 ) var billingDetails                : PaymentRetailerCustomerBillingDetails?       = PaymentRetailerCustomerBillingDetails(),
    @SerializedName("calculated_statement_descriptor" ) var calculatedStatementDescriptor : String?               = null,
    @SerializedName("captured"                        ) var captured                      : Boolean?              = null,
    @SerializedName("created"                         ) var created                       : Int?                  = null,
    @SerializedName("currency"                        ) var currency                      : String?               = null,
    @SerializedName("customer"                        ) var customer                      : String?               = null,
    @SerializedName("description"                     ) var description                   : String?               = null,
    @SerializedName("destination"                     ) var destination                   : String?               = null,
    @SerializedName("dispute"                         ) var dispute                       : String?               = null,
    @SerializedName("disputed"                        ) var disputed                      : Boolean?              = null,
    @SerializedName("failure_balance_transaction"     ) var failureBalanceTransaction     : String?               = null,
    @SerializedName("failure_code"                    ) var failureCode                   : String?               = null,
    @SerializedName("failure_message"                 ) var failureMessage                : String?               = null,
    @SerializedName("invoice"                         ) var invoice                       : String?               = null,
    @SerializedName("livemode"                        ) var livemode                      : Boolean?              = null,
    @SerializedName("on_behalf_of"                    ) var onBehalfOf                    : String?               = null,
    @SerializedName("order"                           ) var order                         : String?               = null,
    @SerializedName("outcome"                         ) var outcome                       : PaymentRetailerCustomerOutcome?              = PaymentRetailerCustomerOutcome(),
    @SerializedName("paid"                            ) var paid                          : Boolean?              = null,
    @SerializedName("payment_intent"                  ) var paymentIntent                 : String?               = null,
    @SerializedName("payment_method"                  ) var paymentMethod                 : String?               = null,
    @SerializedName("payment_method_details"          ) var paymentMethodDetails          : PaymentRetailerCustomerPaymentMethodDetails? = PaymentRetailerCustomerPaymentMethodDetails(),
    @SerializedName("receipt_email"                   ) var receiptEmail                  : String?               = null,
    @SerializedName("receipt_number"                  ) var receiptNumber                 : String?               = null,
    @SerializedName("receipt_url"                     ) var receiptUrl                    : String?               = null,
    @SerializedName("refunded"                        ) var refunded                      : Boolean?              = null,
    @SerializedName("refunds"                         ) var refunds                       : PaymentRetailerCustomerRefunds?              = PaymentRetailerCustomerRefunds(),
    @SerializedName("review"                          ) var review                        : String?               = null,
    @SerializedName("shipping"                        ) var shipping                      : String?               = null,
    @SerializedName("source"                          ) var source                        : PaymentRetailerCustomerSource?               = PaymentRetailerCustomerSource(),
    @SerializedName("source_transfer"                 ) var sourceTransfer                : String?               = null,
    @SerializedName("statement_descriptor"            ) var statementDescriptor           : String?               = null,
    @SerializedName("statement_descriptor_suffix"     ) var statementDescriptorSuffix     : String?               = null,
    @SerializedName("status"                          ) var status                        : String?               = null,
    @SerializedName("transfer_data"                   ) var transferData                  : String?               = null,
    @SerializedName("transfer_group"                  ) var transferGroup                 : String?               = null

)
data class PaymentRetailerCustomerSource (

    @SerializedName("id"                  ) var id                 : String? = null,
    @SerializedName("object"              ) var object3             : String? = null,
    @SerializedName("address_city"        ) var addressCity        : String? = null,
    @SerializedName("address_country"     ) var addressCountry     : String? = null,
    @SerializedName("address_line1"       ) var addressLine1       : String? = null,
    @SerializedName("address_line1_check" ) var addressLine1Check  : String? = null,
    @SerializedName("address_line2"       ) var addressLine2       : String? = null,
    @SerializedName("address_state"       ) var addressState       : String? = null,
    @SerializedName("address_zip"         ) var addressZip         : String? = null,
    @SerializedName("address_zip_check"   ) var addressZipCheck    : String? = null,
    @SerializedName("brand"               ) var brand              : String? = null,
    @SerializedName("country"             ) var country            : String? = null,
    @SerializedName("customer"            ) var customer           : String? = null,
    @SerializedName("cvc_check"           ) var cvcCheck           : String? = null,
    @SerializedName("dynamic_last4"       ) var dynamicLast4       : String? = null,
    @SerializedName("exp_month"           ) var expMonth           : Int?    = null,
    @SerializedName("exp_year"            ) var expYear            : Int?    = null,
    @SerializedName("fingerprint"         ) var fingerprint        : String? = null,
    @SerializedName("funding"             ) var funding            : String? = null,
    @SerializedName("last4"               ) var last4              : String? = null,
    @SerializedName("name"                ) var name               : String? = null,
    @SerializedName("tokenization_method" ) var tokenizationMethod : String? = null

)

data class PaymentRetailerCustomerRefunds (

    @SerializedName("object"      ) var object2     : String?           = null,
    @SerializedName("data"        ) var data       : ArrayList<String> = arrayListOf(),
    @SerializedName("has_more"    ) var hasMore    : Boolean?          = null,
    @SerializedName("total_count" ) var totalCount : Int?              = null,
    @SerializedName("url"         ) var url        : String?           = null

)
data class PaymentRetailerCustomerUserDetails (

    @SerializedName("_id"                             ) var Id                              : String?                 = null,
    @SerializedName("storeLocation"                   ) var storeLocation                   : StoreLocation?          = StoreLocation(),
    @SerializedName("businessDetails"                 ) var businessDetails                 : BusinessDetails?        = BusinessDetails(),
    @SerializedName("businessBankingDetails"          ) var businessBankingDetails          : BusinessBankingDetails? = BusinessBankingDetails(),
    @SerializedName("serviceDetails"                  ) var serviceDetails                  : ServiceDetails?         = ServiceDetails(),
    @SerializedName("address"                         ) var address                         : String?                 = null,
    @SerializedName("otpVerification"                 ) var otpVerification                 : Boolean?                = null,
    @SerializedName("userVerification"                ) var userVerification                : Boolean?                = null,
    @SerializedName("profilePic"                      ) var profilePic                      : String?                 = null,
    @SerializedName("websiteUrl"                      ) var websiteUrl                      : String?                 = null,
    @SerializedName("duration"                        ) var duration                        : String?                 = null,
    @SerializedName("userRequestStatus"               ) var userRequestStatus               : String?                 = null,
    @SerializedName("zipCode"                         ) var zipCode                         : String?                 = null,
    @SerializedName("eoriNumber"                      ) var eoriNumber                      : String?                 = null,
    @SerializedName("additionalDocName"               ) var additionalDocName               : String?                 = null,
    @SerializedName("additionalDocument"              ) var additionalDocument              : String?                 = null,
    @SerializedName("ownerFirstName"                  ) var ownerFirstName                  : String?                 = null,
    @SerializedName("ownerLastName"                   ) var ownerLastName                   : String?                 = null,
    @SerializedName("ownerEmail"                      ) var ownerEmail                      : String?                 = null,
    @SerializedName("noOfUniqueProducts"              ) var noOfUniqueProducts              : Int?                    = null,
    @SerializedName("listOfBrandOrProducts"           ) var listOfBrandOrProducts           : ArrayList<String>       = arrayListOf(),
    @SerializedName("keepStock"                       ) var keepStock                       : Boolean?                = null,
    @SerializedName("isPhysicalStore"                 ) var isPhysicalStore                 : Boolean?                = null,
    @SerializedName("preferredSupplierOrWholeSalerId" ) var preferredSupplierOrWholeSalerId : ArrayList<String>       = arrayListOf(),
    @SerializedName("comments"                        ) var comments                        : String?                 = null,
    @SerializedName("completeProfile"                 ) var completeProfile                 : Boolean?                = null,
    @SerializedName("flag"                            ) var flag                            : Int?                    = null,
    @SerializedName("placeOrderCount"                 ) var placeOrderCount                 : Int?                    = null,
    @SerializedName("serviceOrderCount"               ) var serviceOrderCount               : Int?                    = null,
    @SerializedName("receiveOrderCount"               ) var receiveOrderCount               : Int?                    = null,
    @SerializedName("status"                          ) var status                          : String?                 = null,
    @SerializedName("addressLine1"                    ) var addressLine1                    : String?                 = null,
    @SerializedName("addressLine2"                    ) var addressLine2                    : String?                 = null,
    @SerializedName("city"                            ) var city                            : String?                 = null,
    @SerializedName("country"                         ) var country                         : String?                 = null,
    @SerializedName("countryCode"                     ) var countryCode                     : String?                 = null,
    @SerializedName("email"                           ) var email                           : String?                 = null,
    @SerializedName("firstName"                       ) var firstName                       : String?                 = null,
    @SerializedName("lastName"                        ) var lastName                        : String?                 = null,
    @SerializedName("mobileNumber"                    ) var mobileNumber                    : String?                 = null,
    @SerializedName("password"                        ) var password                        : String?                 = null,
    @SerializedName("userType"                        ) var userType                        : String?                 = null,
    @SerializedName("createdAt"                       ) var createdAt                       : String?                 = null,
    @SerializedName("updatedAt"                       ) var updatedAt                       : String?                 = null,
    @SerializedName("__v"                             ) var _v                              : Int?                    = null,
    @SerializedName("otp"                             ) var otp                             : String?                 = null,
    @SerializedName("otpExpireTime"                   ) var otpExpireTime                   : String?                 = null,
    @SerializedName("isReset"                         ) var isReset                         : Boolean?                = null

)
data class PaymentRetailerCustomerOrderDetails (

    @SerializedName("_id"                    ) var Id                     : String?                   = null,
    @SerializedName("taxPrice"               ) var taxPrice               : Number                      = 0,
    @SerializedName("dealId"                 ) var dealId                 : ArrayList<String>         = arrayListOf(),
    @SerializedName("orderStatus"            ) var orderStatus            : String?                   = null,
    @SerializedName("deliveryStatus"         ) var deliveryStatus         : String?                   = null,
    @SerializedName("paymentStatus"          ) var paymentStatus          : String?                   = null,
    @SerializedName("status"                 ) var status                 : String?                   = null,
    @SerializedName("actualPrice"            ) var actualPrice            : Number                      = 0,
    @SerializedName("orderPrice"             ) var orderPrice             : Number                      = 0,
    @SerializedName("userId"                 ) var userId                 : String?                   = null,
    @SerializedName("productDetails"         ) var productDetails         : ArrayList<ProductDetails> = arrayListOf(),
    @SerializedName("shippingAddress"        ) var shippingAddress        : String?                   = null,
    @SerializedName("orderType"              ) var orderType              : String?                   = null,
    @SerializedName("orderId"                ) var orderId                : String?                   = null,
    @SerializedName("serviceDetails"         ) var serviceDetails         : ArrayList<String>         = arrayListOf(),
    @SerializedName("createdAt"              ) var createdAt              : String?                   = null,
    @SerializedName("updatedAt"              ) var updatedAt              : String?                   = null,
    @SerializedName("__v"                    ) var _v                     : Int?                      = null,
    @SerializedName("otpService"             ) var otpService             : Int?                      = null,
    @SerializedName("otpServiceExpireTime"   ) var otpServiceExpireTime   : String?                   = null,
    @SerializedName("serviceOtpVerification" ) var serviceOtpVerification : Boolean?                  = null,
    @SerializedName("products"               ) var products               : ArrayList<PaymentRetailerCustomerProducts>       = arrayListOf()

)
data class PaymentRetailerCustomerProducts (

    @SerializedName("_id"                ) var Id                 : String?           = null,
    @SerializedName("productImage"       ) var productImage       : ArrayList<String> = arrayListOf(),
    @SerializedName("discount"           ) var discount           : Int?              = null,
    @SerializedName("productFor"         ) var productFor         : String?           = null,
    @SerializedName("status"             ) var status             : String?           = null,
    @SerializedName("productName"        ) var productName        : String?           = null,
    @SerializedName("price"              ) var price              : Number              = 0,
    @SerializedName("unit"               ) var unit               : String?           = null,
    @SerializedName("brand"              ) var brand              : String?           = null,
    @SerializedName("description"        ) var description        : String?           = null,
    @SerializedName("categoryId"         ) var categoryId         : String?           = null,
    @SerializedName("subCategoryId"      ) var subCategoryId      : String?           = null,
    @SerializedName("quantity"           ) var quantity           : String?           = null,
    @SerializedName("productReferenceId" ) var productReferenceId : String?           = null,
    @SerializedName("userId"             ) var userId             : String?           = null,
    @SerializedName("createdAt"          ) var createdAt          : String?           = null,
    @SerializedName("updatedAt"          ) var updatedAt          : String?           = null,
    @SerializedName("__v"                ) var _v                 : Int?              = null

)

data class PaymentRetailerCustomerOrders (

    @SerializedName("_id"                ) var Id                 : String?           = null,
    @SerializedName("productImage"       ) var productImage       : ArrayList<String> = arrayListOf(),
    @SerializedName("discount"           ) var discount           : Int?              = null,
    @SerializedName("productFor"         ) var productFor         : String?           = null,
    @SerializedName("status"             ) var status             : String?           = null,
    @SerializedName("productName"        ) var productName        : String?           = null,
    @SerializedName("price"              ) var price              : Number              = 0,
    @SerializedName("unit"               ) var unit               : String?           = null,
    @SerializedName("brand"              ) var brand              : String?           = null,
    @SerializedName("description"        ) var description        : String?           = null,
    @SerializedName("categoryId"         ) var categoryId         : String?           = null,
    @SerializedName("subCategoryId"      ) var subCategoryId      : String?           = null,
    @SerializedName("quantity"           ) var quantity           : String?           = null,
    @SerializedName("productReferenceId" ) var productReferenceId : String?           = null,
    @SerializedName("userId"             ) var userId             : String?           = null,
    @SerializedName("createdAt"          ) var createdAt          : String?           = null,
    @SerializedName("updatedAt"          ) var updatedAt          : String?           = null,
    @SerializedName("__v"                ) var _v                 : Int?              = null

)

data class PaymentRetailerCustomerPaidToDetails (

    @SerializedName("_id"                             ) var Id                              : String?                 = null,
    @SerializedName("storeLocation"                   ) var storeLocation                   : StoreLocation?          = StoreLocation(),
    @SerializedName("businessDetails"                 ) var businessDetails                 : BusinessDetails?        = BusinessDetails(),
    @SerializedName("businessBankingDetails"          ) var businessBankingDetails          : BusinessBankingDetails? = BusinessBankingDetails(),
    @SerializedName("serviceDetails"                  ) var serviceDetails                  : ServiceDetails?         = ServiceDetails(),
    @SerializedName("address"                         ) var address                         : String?                 = null,
    @SerializedName("otpVerification"                 ) var otpVerification                 : Boolean?                = null,
    @SerializedName("userVerification"                ) var userVerification                : Boolean?                = null,
    @SerializedName("profilePic"                      ) var profilePic                      : String?                 = null,
    @SerializedName("websiteUrl"                      ) var websiteUrl                      : String?                 = null,
    @SerializedName("duration"                        ) var duration                        : String?                 = null,
    @SerializedName("userRequestStatus"               ) var userRequestStatus               : String?                 = null,
    @SerializedName("zipCode"                         ) var zipCode                         : String?                 = null,
    @SerializedName("eoriNumber"                      ) var eoriNumber                      : String?                 = null,
    @SerializedName("additionalDocName"               ) var additionalDocName               : String?                 = null,
    @SerializedName("additionalDocument"              ) var additionalDocument              : String?                 = null,
    @SerializedName("ownerFirstName"                  ) var ownerFirstName                  : String?                 = null,
    @SerializedName("ownerLastName"                   ) var ownerLastName                   : String?                 = null,
    @SerializedName("ownerEmail"                      ) var ownerEmail                      : String?                 = null,
    @SerializedName("noOfUniqueProducts"              ) var noOfUniqueProducts              : Int?                    = null,
    @SerializedName("listOfBrandOrProducts"           ) var listOfBrandOrProducts           : ArrayList<String>       = arrayListOf(),
    @SerializedName("keepStock"                       ) var keepStock                       : Boolean?                = null,
    @SerializedName("isPhysicalStore"                 ) var isPhysicalStore                 : Boolean?                = null,
    @SerializedName("preferredSupplierOrWholeSalerId" ) var preferredSupplierOrWholeSalerId : ArrayList<String>       = arrayListOf(),
    @SerializedName("comments"                        ) var comments                        : String?                 = null,
    @SerializedName("completeProfile"                 ) var completeProfile                 : Boolean?                = null,
    @SerializedName("flag"                            ) var flag                            : Int?                    = null,
    @SerializedName("placeOrderCount"                 ) var placeOrderCount                 : Int?                    = null,
    @SerializedName("serviceOrderCount"               ) var serviceOrderCount               : Int?                    = null,
    @SerializedName("receiveOrderCount"               ) var receiveOrderCount               : Int?                    = null,
    @SerializedName("status"                          ) var status                          : String?                 = null,
    @SerializedName("userType"                        ) var userType                        : String?                 = null,
    @SerializedName("firstName"                       ) var firstName                       : String?                 = null,
    @SerializedName("lastName"                        ) var lastName                        : String?                 = null,
    @SerializedName("countryCode"                     ) var countryCode                     : String?                 = null,
    @SerializedName("mobileNumber"                    ) var mobileNumber                    : String?                 = null,
    @SerializedName("email"                           ) var email                           : String?                 = null,
    @SerializedName("phoneNumber"                     ) var phoneNumber                     : String?                 = null,
    @SerializedName("storeAddress"                    ) var storeAddress                    : String?                 = null,
    @SerializedName("storeName"                       ) var storeName                       : String?                 = null,
    @SerializedName("storeContactNo"                  ) var storeContactNo                  : String?                 = null,
    @SerializedName("storeBrand"                      ) var storeBrand                      : String?                 = null,
    @SerializedName("socialLink"                      ) var socialLink                      : SocialLink?             = SocialLink(),
    @SerializedName("password"                        ) var password                        : String?                 = null,
    @SerializedName("govtDocument"                    ) var govtDocument                    : GovtDocument?           = GovtDocument(),
    @SerializedName("city"                            ) var city                            : String?                 = null,
    @SerializedName("state"                           ) var state                           : String?                 = null,
    @SerializedName("country"                         ) var country                         : String?                 = null,
    @SerializedName("addressLine1"                    ) var addressLine1                    : String?                 = null,
    @SerializedName("addressLine2"                    ) var addressLine2                    : String?                 = null,
    @SerializedName("createdAt"                       ) var createdAt                       : String?                 = null,
    @SerializedName("updatedAt"                       ) var updatedAt                       : String?                 = null,
    @SerializedName("__v"                             ) var _v                              : Int?                    = null,
    @SerializedName("userUniqueId"                    ) var userUniqueId                    : String?                 = null

)
data class PaymentRetailerCustomerBillingDetails (

    @SerializedName("address" ) var address : PaymentRetailerCustomerAddress? = PaymentRetailerCustomerAddress(),
    @SerializedName("email"   ) var email   : String?  = null,
    @SerializedName("name"    ) var name    : String?  = null,
    @SerializedName("phone"   ) var phone   : String?  = null

)
data class PaymentRetailerCustomerOutcome (

    @SerializedName("network_status" ) var networkStatus : String? = null,
    @SerializedName("reason"         ) var reason        : String? = null,
    @SerializedName("risk_level"     ) var riskLevel     : String? = null,
    @SerializedName("risk_score"     ) var riskScore     : Int?    = null,
    @SerializedName("seller_message" ) var sellerMessage : String? = null,
    @SerializedName("type"           ) var type          : String? = null

)

data class PaymentRetailerCustomerPaymentMethodDetails (

    @SerializedName("card" ) var card : PaymentRetailerCustomerCard?   = PaymentRetailerCustomerCard(),
    @SerializedName("type" ) var type : String? = null

)

data class PaymentRetailerCustomerAddress (

    @SerializedName("city"        ) var city       : String? = null,
    @SerializedName("country"     ) var country    : String? = null,
    @SerializedName("line1"       ) var line1      : String? = null,
    @SerializedName("line2"       ) var line2      : String? = null,
    @SerializedName("postal_code" ) var postalCode : String? = null,
    @SerializedName("state"       ) var state      : String? = null

)

data class PaymentRetailerCustomerCard (

    @SerializedName("brand"          ) var brand        : String? = null,
    @SerializedName("checks"         ) var checks       : PaymentRetailerCustomerChecks? = PaymentRetailerCustomerChecks(),
    @SerializedName("country"        ) var country      : String? = null,
    @SerializedName("exp_month"      ) var expMonth     : Int?    = null,
    @SerializedName("exp_year"       ) var expYear      : Int?    = null,
    @SerializedName("fingerprint"    ) var fingerprint  : String? = null,
    @SerializedName("funding"        ) var funding      : String? = null,
    @SerializedName("installments"   ) var installments : String? = null,
    @SerializedName("last4"          ) var last4        : String? = null,
    @SerializedName("mandate"        ) var mandate      : String? = null,
    @SerializedName("network"        ) var network      : String? = null,
    @SerializedName("three_d_secure" ) var threeDSecure : String? = null,
    @SerializedName("wallet"         ) var wallet       : String? = null

)

data class PaymentRetailerCustomerChecks (

    @SerializedName("address_line1_check"       ) var addressLine1Check      : String? = null,
    @SerializedName("address_postal_code_check" ) var addressPostalCodeCheck : String? = null,
    @SerializedName("cvc_check"                 ) var cvcCheck               : String? = null

)