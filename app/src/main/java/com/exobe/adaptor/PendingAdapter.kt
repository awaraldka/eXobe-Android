package com.exobe.adaptor

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


class PendingAdapter(val context: Context,
                     var click: CustomeClick,
                     var data:ArrayList<RetailerOrderManagementDoc>) :
    RecyclerView.Adapter<com.exobe.adaptor.PendingAdapter.ProductLatestViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductLatestViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.pendinglayout, parent, false)
        return ProductLatestViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductLatestViewHolder, position: Int) {
        data[position].apply { 
            try {
                holder.cardview_Product.setSafeOnClickListener {
                    click.click(id)
                }

                holder.customername.text="${userId.firstName} ${userId.lastName}".capitalizeFirstLetter()
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
                if (shippingFixedAddress.addressLine1 != "" && shippingFixedAddress.addressLine1 != null) {
                    fullAddress.append("${shippingFixedAddress.addressLine1},")
                }
//                if (shippingFixedAddress.addressLine2 != "" && shippingFixedAddress.addressLine2 != null) {
//                    fullAddress.append("${shippingFixedAddress.addressLine2},")
//                }
                if (shippingFixedAddress.city != "" && shippingFixedAddress.city != null) {
                    fullAddress.append("${shippingFixedAddress.city},")
                }
                if (shippingFixedAddress.state != "" && shippingFixedAddress.state != null) {
                    fullAddress.append("${shippingFixedAddress.state},")
                }
                holder.address.text="$fullAddress"


                var totalPrice = 0.0
                for (order in myOrders) {
                    totalPrice += order.price.toDouble() // Convert price to Double if necessary
                }




                holder.Total_amount.text="R ${CommonFunctions.currencyFormatter(totalPrice)}"
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
