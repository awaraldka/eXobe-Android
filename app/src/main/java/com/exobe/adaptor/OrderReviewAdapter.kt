package com.exobe.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.customClicks.*
import com.exobe.entity.response.MyCartList
import com.exobe.util.DateFormat

class OrderReviewAdapter(
    var context: Context,
    var itemList: ArrayList<MyCartList>
) : RecyclerView.Adapter<OrderReviewAdapter.ViewHolder>() {
    var quantity = ""

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderReviewAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.review_order_review_items_modallayout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        try {
            val userData = itemList[position]
            holder.Decrement.visibility = View.GONE
            holder.Increment.visibility = View.GONE
            holder.deleteItem.visibility = View.GONE
            holder.incDesign.visibility = View.GONE
            holder.quantity.visibility = View.VISIBLE
            Glide.with(context).load(userData.productId?.thumbnail).placeholder(R.drawable.dummyproduct)
                .into(holder.itemImage)
            holder.ItemName.text = userData.productId?.productName?.capitalizeFirstLetter()
            var price = userData.totalPrice.toDouble() * userData.quantity!!

            if (userData.isDealActive){
                holder.priceTag.isVisible = true
                holder.priceTag.text = "R ${CommonFunctions.currencyFormatter(userData.priceSizeDetails?.price!!.toDouble())}"
                holder.priceTag.paintFlags = holder.priceTag.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.Price.setTextColor(ContextCompat.getColor(context,R.color.red))
                holder.Price.text = "R ${CommonFunctions.currencyFormatter(userData.dealPrice.toDouble())}  ${userData.dealDiscount}% Off"
            }else{
                holder.priceTag.isVisible = false
                holder.Price.setTextColor(ContextCompat.getColor(context,R.color.black))
                holder.Price.text = "R ${CommonFunctions.currencyFormatter(userData.priceSizeDetails?.price!!.toDouble())}"
            }
            holder.itemQty.text = "Available Qty: ${userData.availableQuantity.toString()}"
//            holder.valueSize.text = "Value/Size: ${userData.priceSizeDetails?.value}-${userData.priceSizeDetails?.unit}"
//            holder.valueSize.text = "Value/Size: ${userData.priceSizeDetails?.value}"
            holder.value.text = "${userData.priceSizeDetails?.value} ${userData.priceSizeDetails?.unit!!.lowercase().takeIf { it != "other" }?:""}"

            holder.quantity.text = "Qty: ${userData.quantity.toString()}"
            var expectedDateOne = ""
            if(userData.productId?.expectedDeliveryDays?.isNotEmpty() == true) {
                var splitValues = userData.productId?.expectedDeliveryDays.toString().split(" ")
                if (splitValues.size > 3) {
                    expectedDateOne = DateFormat.expectedDate(userData.createdAt.toString(), Integer.parseInt(splitValues[2]))
                }
            }

            holder.deliveryDate.text = "$expectedDateOne"


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var ItemName: TextView
        var Price: TextView
        var Increment: RelativeLayout
        var Decrement: RelativeLayout
        var itemQty: TextView
        var deleteItem: ImageView
        var quantity: TextView
        var deliveryDate: TextView
        var valueSize: TextView
        var value: TextView
        var incDesign: LinearLayout
        var priceTag: TextView

        init {
            deleteItem = itemView.findViewById(R.id.DeleteItem)
            itemImage = itemView.findViewById(R.id.image)
            ItemName = itemView.findViewById(R.id.item_name)
            Price = itemView.findViewById(R.id.price)
            Increment = itemView.findViewById(R.id.increment)
            Decrement = itemView.findViewById(R.id.decrement)
            itemQty = itemView.findViewById(R.id.itemQty)
            quantity = itemView.findViewById(R.id.quantityText)
            deliveryDate = itemView.findViewById(R.id.date_deliverydate)
            valueSize = itemView.findViewById(R.id.valueSize)
            incDesign = itemView.findViewById(R.id.incDesign)
            value = itemView.findViewById(R.id.value)
            priceTag = itemView.findViewById(R.id.priceTag)


        }
    }


    override fun getItemCount(): Int {
        return itemList.size
    }

}



