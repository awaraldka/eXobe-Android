package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.entity.response.OrderViewProductDetails
import com.exobe.util.DateFormat

class OrderViewitemAdapter(
    val context: Context,
    val data: ArrayList<OrderViewProductDetails>,
    val createdAt: String?

) : RecyclerView.Adapter<OrderViewitemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.orderviewcardview, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val orderData = data[position]

            Glide.with(context).load(orderData.productId?.thumbnail).placeholder(R.drawable.dummyproduct).into(holder.itemImage)
            holder.ItemName.text = orderData.productId?.productName?.capitalizeFirstLetter()


            if (orderData.productId!!.dealReferenceId != null && (orderData.productId!!.dealReferenceId.dealDiscount != ""
                        && orderData.productId!!.dealReferenceId.dealPrice != 0.0)){
                holder.Price.text =  "R ${CommonFunctions.currencyFormatter(orderData.priceSizeDetails?.price!!.toDouble())}"
                holder.Price.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.DealPrice.text =  "R ${CommonFunctions.currencyFormatter(orderData.productId!!.dealReferenceId.dealPrice)}"
                holder.Price.setTextColor(ContextCompat.getColor(context, R.color.black))
                holder.DealPrice.setTextColor(ContextCompat.getColor(context, R.color.red))
                holder.offPercent.isVisible = true
                holder.offPercent.text = "${orderData.productId.dealReferenceId.dealDiscount}% Off"
            }else{
                holder.offPercent.isVisible = false
                holder.Price.text =  "R ${CommonFunctions.currencyFormatter(orderData.priceSizeDetails?.price!!.toDouble())}"
                holder.Price.setTextColor(ContextCompat.getColor(context, R.color.red))
            }

            holder.value.text = "${orderData.priceSizeDetails?.value} ${orderData.priceSizeDetails?.unit!!.lowercase().takeIf { it != "other" }?:""}"

            holder.quantity.text = "Qty: ${orderData.quantity.toString()}"


            val daysOfDate = expectedDays(orderData.productId?.expectedDeliveryDays!!)

            holder.deliveryDate.text = DateFormat.deliveryExpectedDateNew(createdAt.toString(),daysOfDate).toString()


        }catch(e : Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemImage: ImageView = itemView.findViewById(R.id.image)
        var ItemName: TextView = itemView.findViewById(R.id.item_name)
        var Price: TextView = itemView.findViewById(R.id.price)
        var quantity: TextView = itemView.findViewById(R.id.quantityText)
        var deliveryDate: TextView = itemView.findViewById(R.id.date_deliverydate)
        var valueSize: TextView = itemView.findViewById(R.id.valueSize)
        var value: TextView = itemView.findViewById(R.id.value)
        var offPercent: TextView = itemView.findViewById(R.id.offPercent)
        var DealPrice: TextView = itemView.findViewById(R.id.DealPrice)


    }

    fun expectedDays(value : String) : Int {
        val rangeString = value

        val regex = Regex("\\d+")
        val matches = regex.findAll(rangeString)

        val values = matches.map { it.value.toInt() }

        val maxValue = values.maxOrNull()

        return maxValue!!
    }

}