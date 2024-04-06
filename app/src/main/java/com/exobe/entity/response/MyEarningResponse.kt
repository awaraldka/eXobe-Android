package com.exobe.entity.response

import com.exobe.entity.response.serviceTab.AssignedUser
import com.google.gson.annotations.SerializedName

class MyEarningResponse(
    @SerializedName("result") val result: MyEarningResult = MyEarningResult(),
    @SerializedName("responseMessage") val responseMessage: String = "",
    @SerializedName("responseCode") val responseCode: Int = 0
)

class MyEarningResult(
    @SerializedName("docs") val docs: ArrayList<MyEarningDocs> = arrayListOf(),
    @SerializedName("total") val total: Int = 0,
    @SerializedName("limit") val limit: Int = 0,
    @SerializedName("page") val page: Int = 0,
    @SerializedName("pages") val pages: Int = 0,
    @SerializedName("totalEarning") val totalEarning: Int = 0,
    @SerializedName("wallet") val wallet: Wallet = Wallet()
)

class MyEarningDocs(
    @SerializedName("orderReferenceId") val orderReferenceId: OrderReferenceId = OrderReferenceId(),
    @SerializedName("transactionStatus") val transactionStatus: String = "",
    @SerializedName("transactionAmount") val transactionAmount: Number = 0,
    @SerializedName("fromUser") val fromUser: FromUser = FromUser(),
    @SerializedName("toUser") val toUser: FromUser = FromUser(),
    @SerializedName("isCommission") val isCommission: Boolean = false,
    @SerializedName("fromWalletType") val fromWalletType: String = "",
    @SerializedName("toWalletType") val toWalletType: String = "",
    @SerializedName("createdAt") val createdAt: String = "",

    )


class Wallet(
    @SerializedName("walletAmount") val walletAmount: Number = 0,
    @SerializedName("totalWalletAmount") val totalWalletAmount: Number = 0,
    @SerializedName("totalCommissionPaid") val totalCommissionPaid: Number = 0,

    )


class OrderReferenceId(
    @SerializedName("orderId") val orderId: String = "",
    @SerializedName("orderStatus") val orderStatus: String = "",
    @SerializedName("userId") val userId: AssignedUser = AssignedUser(),
    @SerializedName("orderPrice") val orderPrice: Double = 0.0,
    @SerializedName("createdAt") val createdAt: String = "",


    )

class FromUser(
    @SerializedName("_id") val id: String = "",
    @SerializedName("firstName") val firstName: String = "",
    @SerializedName("lastName") val lastName: String = "",
)