package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.entity.response.NewOrderServiceReqServiceDetail
import com.exobe.util.DateFormat

class ServiceViewitemAdapter(
    val context: Context,
    val data: List<NewOrderServiceReqServiceDetail>,
    val orderStatus: String,
    val orderPrice: Number,
    val orderId: String

) : RecyclerView.Adapter<ServiceViewitemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.order_history_items, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            val orderData = data[position]

            holder.item_name.text = orderData.serviceId.serviceName.capitalizeFirstLetter()

            holder.price.text = "R ${CommonFunctions.currencyFormatter(orderPrice.toDouble())}"

            holder.txtOrderID.text = "Booking ID: $orderId"

            holder.txtDeliveryDate.text = "Booking Date: ${DateFormat.deliveryDateFormat(orderData.serviceId.createdAt)}"

            holder.txtStatus.text = orderStatus

            if (holder.txtStatus.text == "PENDING" ||  holder.txtStatus.text == "CANCELLED"){
                holder.txtStatus.setTextColor(ContextCompat.getColor(context,R.color.cancelled))
            }else{
                holder.txtStatus.setTextColor(ContextCompat.getColor(context,R.color.delivered))
            }

            holder.qty.isVisible = false

            Glide.with(context).load(orderData.serviceId.userId.profilePic).placeholder(R.drawable.dummyproduct).into(holder.image)


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var item_name:TextView
        var price:TextView
        var txtOrderID:TextView
        var txtStatus:TextView
        var delete:ImageView
        var txtDeliveryDate:TextView
        var qty:TextView
        var image:ImageView
        var item: RelativeLayout

        init {
            item_name= itemView.findViewById(R.id.item_name)
            price= itemView.findViewById(R.id.price)
            txtOrderID= itemView.findViewById(R.id.txtOrderID)
            txtStatus= itemView.findViewById(R.id.txtStatus)
            delete= itemView.findViewById(R.id.cross_button)
            qty= itemView.findViewById(R.id.qty)
            image= itemView.findViewById(R.id.image)
            item= itemView.findViewById(R.id.item)
            txtDeliveryDate= itemView.findViewById(R.id.txtDeliveryDate)

        }

    }

}