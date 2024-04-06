package com.exobe.entity.response

import com.exobe.entity.request.PriceSizeDetailsRequest
import com.google.gson.annotations.SerializedName

data class MyCartListResponse(
    @SerializedName("result") var result: MyCartListResult = MyCartListResult(),
    @SerializedName("responseMessage") var responseMessage: String? = null,
    @SerializedName("responseCode") var responseCode: Int? = null

)

data class MyCartListResult(
    @SerializedName("cartList") var cartList: ArrayList<MyCartList> = arrayListOf(),
    @SerializedName("payOutAmount") var payOutAmount: PayOutAmount = PayOutAmount(),

)

class PayOutAmount(
    @SerializedName("totalAmount") val totalAmountRes : Number = 0,
    @SerializedName("totalDiscount") val totalDiscount: Number= 0,
    @SerializedName("walletAmount") val walletAmount: Number= 0,
    @SerializedName("deliveryFee") val deliveryFee: Number= 0,
    @SerializedName("subTotal") val subTotal: Number= 0,
    @SerializedName("vat") val vat: Number = 0,
    @SerializedName("totalAmountWithVat") val totalAmountWithVat: Number= 0,
    @SerializedName("deliveryMode") val deliveryMode: String = ""
)


data class MyCartList(
    @SerializedName("priceSizeDetails")
    var priceSizeDetails: PriceSizeDetailsRequest? = null,
    @SerializedName("buyStatus") var buyStatus: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("productId") var productId: MyCartListproductID? = MyCartListproductID(),
    @SerializedName("quantity") var quantity: Int? = null,
    @SerializedName("availableQuantity") var availableQuantity: Int? = null,
    @SerializedName("orderType") var orderType: String? = null,
//    @SerializedName("userId") var userId: String? = null,
    @SerializedName("isDealActive") val isDealActive: Boolean = false,
    @SerializedName("dealId") val dealId: String = "",
    @SerializedName("dealPrice") val dealPrice: Number = 0,
    @SerializedName("dealDiscount") val dealDiscount: Number = 0,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("totalPrice") var totalPrice: Number = 0,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("__v") var _v: Int? = null
)

data class MyCartListproductID(
    @SerializedName("productImage") var productImage: ArrayList<String> = arrayListOf(),
    @SerializedName("discount") var discount: Int? = null,
    @SerializedName("thumbnail") var thumbnail: String? = null,
    @SerializedName("productFor") var productFor: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("productName") var productName: String? = null,
    @SerializedName("price") var price: Number = 0,
//    @SerializedName("unit") var unit: String? = null,
    @SerializedName("brand") var brand: String? = null,
    @SerializedName("expectedDeliveryDays") var expectedDeliveryDays: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("categoryId") var categoryId: CategoryId? = null,
    @SerializedName("priceSizeDetails") var priceSizeDetails: ArrayList<PriceSizeDetailsCartList>? = null,
    @SerializedName("subCategoryId") var subCategoryId: SubCategoryId? = null,
    @SerializedName("quantity") var quantity: String? = null,
    @SerializedName("productReferenceId") var productReferenceId: String? = null,
    @SerializedName("userId") var userId: String? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("__v") var _v: Int? = null
)

data class PriceSizeDetailsCartList(
    @SerializedName("_id")
    val _id: String = "",

    @SerializedName("value")
    var value: String = "",

//    @SerializedName("unit")
//    var unit: String= "",

    @SerializedName("price")
    var price: Number = 0,

    @SerializedName("quantity")
    var quantity: String = "",

    @SerializedName("id")
    val id: String = ""
)