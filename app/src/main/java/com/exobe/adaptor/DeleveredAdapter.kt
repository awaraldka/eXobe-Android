package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.customClicks.CustomeClick
import com.exobe.entity.response.RetailerOrderManagementDoc
import com.exobe.util.DateFormat.Companion.NotificationDate


class DeleveredAdapter(
    val context: Context,
    var data: ArrayList<RetailerOrderManagementDoc>,
    var customeClick: CustomeClick
) :
    RecyclerView.Adapter<com.exobe.adaptor.DeleveredAdapter.ProductLatestViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductLatestViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.pendinglayout, parent, false)
        return ProductLatestViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductLatestViewHolder, position: Int) {
        data[position].apply {
            try {
                holder.cardview_Product.setSafeOnClickListener {
                    customeClick.click(id)
                }

                holder.customername.text="${userId?.firstName} ${userId?.lastName}".capitalizeFirstLetter()
                holder.orderid.text = "Order Id: ${orderId}"
                holder.orderdate.text= "Order Date: ${NotificationDate(createdAt)}"

                val paymentStatus = paymentStatus.uppercase()
                holder.paymentstatus.text = "Payment Status: "

                if (paymentStatus == "PENDING" || paymentStatus == "CANCELLED") {
                    val spannableString = SpannableString(paymentStatus)
                    spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.cancelled)), 0, spannableString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    holder.paymentstatus.append(spannableString)
                    holder.paymentstatus.setTextColor(ContextCompat.getColor(context, R.color.black))
                } else {
                    val spannableString = SpannableString(paymentStatus)
                    spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.delivered)), 0, spannableString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    holder.paymentstatus.append(spannableString)
                    holder.paymentstatus.setTextColor(ContextCompat.getColor(context, R.color.black))
                }

                var fullAddress = StringBuffer()
                if (shippingFixedAddress.addressLine1 != "") {
                    fullAddress.append(shippingFixedAddress.addressLine1)
                }
//                if(shippingFixedAddress.addressLine2 != "") {
//                    fullAddress.append(", ${shippingFixedAddress.addressLine1}")
//                }
                if(shippingFixedAddress.city != "") {
                    fullAddress.append(", ${shippingFixedAddress.city}")
                }
                if(shippingFixedAddress.state != "") {
                    fullAddress.append(", ${shippingFixedAddress.state}")
                }
                if(shippingFixedAddress.country != "") {
                    fullAddress.append(", ${shippingFixedAddress.country}")
                }
                holder.address.text="$fullAddress"
                holder.Total_amount.text="R ${CommonFunctions.currencyFormatter(myOrderPrice.toDouble())}"
                Glide.with(context).load(myOrders[0].productId.productImage.getOrNull(0)).placeholder(R.drawable.dummyproduct).into(holder.image)

            }catch (e:Exception){
                e.printStackTrace()
            }

        }
        

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ProductLatestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cardview_Product = view.findViewById<LinearLayout>(R.id.viewClick)
        var address = view.findViewById<TextView>(R.id.address)
        var paymentstatus = view.findViewById<TextView>(R.id.paymentstatus)
        var Total_amount = view.findViewById<TextView>(R.id.Total_amount)
        var orderdate = view.findViewById<TextView>(R.id.orderdate)
        var orderid = view.findViewById<TextView>(R.id.orderid)
        var customername = view.findViewById<TextView>(R.id.customername)
        var image = view.findViewById<ImageView>(R.id.image)
    }

}


