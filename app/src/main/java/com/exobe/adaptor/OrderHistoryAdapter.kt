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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.customClicks.OrderHistoryCustomClick
import com.exobe.customClicks.viewOrder
import com.exobe.entity.response.OrderHistoryRetailerDocs
import com.exobe.util.DateFormat

class OrderHistoryAdapter(
    val context: Context,
    val itemList: ArrayList<OrderHistoryRetailerDocs>,
    var orderHistoryCustomClick: OrderHistoryCustomClick,
    var viewOrder: viewOrder
) : RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewModel {
        val view = LayoutInflater.from(context).inflate(R.layout.order_history_items, parent, false)
        return OrderHistoryViewModel(view)
    }

    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: OrderHistoryViewModel, position: Int) {
        val orderData = itemList[position]

        holder.item_name.text = orderData.productDetails?.get(0)?.productId?.productName?.capitalizeFirstLetter()


        holder.price.text = "R ${CommonFunctions.currencyFormatter(orderData.orderPrice.toDouble())}"
        holder.txtOrderID.text = "Order ID: ${orderData.orderId}"
        holder.txtDeliveryDate.text = "Delivery Date: ${DateFormat.deliveryDateFormat(orderData.createdAt)}"
        holder.txtStatus.text = orderData.deliveryStatus

        if (orderData.deliveryStatus == "PENDING" || orderData.deliveryStatus == "CANCELLED"){
            holder.txtStatus.setTextColor(ContextCompat.getColor(context,R.color.cancelled))
        }else{
            holder.txtStatus.setTextColor(ContextCompat.getColor(context,R.color.delivered))
        }

        holder.qty.text = "Qty:${orderData.productDetails?.get(0)?.quantity}"
        Glide.with(context).load(orderData.productDetails?.get(0)?.productId?.thumbnail).placeholder(R.drawable.dummyproduct).into(holder.image)

        holder.delete.setSafeOnClickListener{
            orderHistoryCustomClick.delete()
        }

        holder.item.setSafeOnClickListener {
            viewOrder.viewORderiTEM(orderData._id.toString(),orderData.deliveryStatus.toString())
        }


    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class OrderHistoryViewModel(view : View) : RecyclerView.ViewHolder(view) {

        var item_name:TextView
        var price:TextView
        var txtOrderID:TextView
        var txtStatus:TextView
        var delete:ImageView
        var txtDeliveryDate:TextView
        var qty:TextView
        var image:ImageView
        var item:RelativeLayout

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