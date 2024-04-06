package com.exobe.entity.response

import com.google.gson.annotations.SerializedName

class ViewTransaction_Respomse (
    @SerializedName("result") val result : ViewTransaction_Result,
    @SerializedName("responseMessage") val responseMessage : String,
    @SerializedName("responseCode") val responseCode : Int

        )
class ViewTransaction_Result(
//    @SerializedName("status") val status : String,
//    @SerializedName("_id") val _id : String,
//    @SerializedName("transactionStatus") val transactionStatus : String,
//    @SerializedName("transactionDetails") val transactionDetails : ViewTransaction_TransactionDetiails,
//    @SerializedName("userId") val userId : UserId,
//    @SerializedName("orderId") val orderId : OrderId,
    @SerializedName("trxId") val trxId : String,
    @SerializedName("transactionMethod") val transactionMethod : String,
    @SerializedName("orderAmount") val orderAmount : Number,
//    @SerializedName("shippingAddress") val shippingAddress : ShippingAddress,
//    @SerializedName("currencyCode") val currencyCode : String,
//    @SerializedName("createdAt") val createdAt : String,
//    @SerializedName("updatedAt") val updatedAt : String,
//    @SerializedName("__v") val __v : Int

)
class ViewTransaction_TransactionDetiails(
    @SerializedName("id") val id : String,
//    @SerializedName("object") val object : String,
//    @SerializedName("amount") val amount : Int,
//    @SerializedName("amount_captured") val amount_captured : Int,
//    @SerializedName("amount_refunded") val amount_refunded : Int,
//    @SerializedName("application") val application : String,
//    @SerializedName("application_fee") val application_fee : String,
//    @SerializedName("application_fee_amount") val application_fee_amount : String,
//    @SerializedName("balance_transaction") val balance_transaction : String,
//    @SerializedName("billing_details") val billing_details : Billing_details,
//    @SerializedName("calculated_statement_descriptor") val calculated_statement_descriptor : String,
//    @SerializedName("captured") val captured : Boolean,
//    @SerializedName("created") val created : Int,
//    @SerializedName("currency") val currency : String,
//    @SerializedName("customer") val customer : String,
//    @SerializedName("description") val description : String,
//    @SerializedName("destination") val destination : String,
//    @SerializedName("dispute") val dispute : String,
//    @SerializedName("disputed") val disputed : Boolean,
//    @SerializedName("failure_balance_transaction") val failure_balance_transaction : String,
//    @SerializedName("failure_code") val failure_code : String,
//    @SerializedName("failure_message") val failure_message : String,
//    @SerializedName("invoice") val invoice : String,
//    @SerializedName("livemode") val livemode : Boolean,
//    @SerializedName("on_behalf_of") val on_behalf_of : String,
//    @SerializedName("order") val order : String,
//    @SerializedName("outcome") val outcome : Outcome,
//    @SerializedName("paid") val paid : Boolean,
//    @SerializedName("payment_intent") val payment_intent : String,
//    @SerializedName("payment_method") val payment_method : String,
//    @SerializedName("payment_method_details") val payment_method_details : Payment_method_details,
//    @SerializedName("receipt_email") val receipt_email : String,
//    @SerializedName("receipt_number") val receipt_number : String,
//    @SerializedName("receipt_url") val receipt_url : String,
//    @SerializedName("refunded") val refunded : Boolean,
//    @SerializedName("refunds") val refunds : Refunds,
//    @SerializedName("review") val review : String,
//    @SerializedName("shipping") val shipping : String,
//    @SerializedName("source") val source : Source,
//    @SerializedName("source_transfer") val source_transfer : String,
//    @SerializedName("statement_descriptor") val statement_descriptor : String,
//    @SerializedName("statement_descriptor_suffix") val statement_descriptor_suffix : String,
//    @SerializedName("status") val status : String,
//    @SerializedName("transfer_data") val transfer_data : String,
//    @SerializedName("transfer_group") val transfer_group : String
)