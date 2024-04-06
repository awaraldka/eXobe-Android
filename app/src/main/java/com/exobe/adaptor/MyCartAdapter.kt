package com.exobe.Adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Paint
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
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.customClicks.CartTotalAmount
import com.exobe.customClicks.CustomClick6
import com.exobe.customClicks.DeleteCartItem
import com.exobe.customClicks.ProductViewListener
import com.exobe.entity.response.MyCartList
import com.exobe.util.DateFormat

class MyCartAdapter(
    var context: Context,
    var itemList: ArrayList<MyCartList>,
    var CartTotalAmount: CartTotalAmount,
    var click: CustomClick6,
    var deleteCartItem: DeleteCartItem,
    var productViewListener: ProductViewListener
) : RecyclerView.Adapter<MyCartAdapter.ViewHolder>() {

    var quantity = ""
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.order_review_items_modallayout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userData = itemList[position]

        try {
            Glide.with(context).load(userData.productId?.thumbnail).placeholder(R.drawable.dummyproduct).into(holder.itemImage)
            holder.ItemName.text = userData.productId?.productName?.capitalizeFirstLetter()

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


            var price = userData.totalPrice.toDouble() * userData.quantity!!

//            holder.Price.text = "R ${CommonFunctions.currencyFormatter(price)}"

            var expectedDateOne = ""
            if(userData.productId?.expectedDeliveryDays?.isNotEmpty() == true) {
                var splitValues = userData.productId?.expectedDeliveryDays.toString().split(" ")
                if (splitValues.size > 3) {
                    expectedDateOne = DateFormat.expectedDate(userData.createdAt.toString(), Integer.parseInt(splitValues[2]))
                }
                holder.date_deliverydate.text = "$expectedDateOne"
            } else {
                holder.date_deliverydate.text = "Not defined"
            }


            holder.itemQty.text = "Available Qty: ${userData.availableQuantity}"
            holder.Quantity.text = userData.quantity.toString()
            holder.value.text = "${userData.priceSizeDetails?.value} ${userData.priceSizeDetails?.unit!!.lowercase().takeIf { it != "other" }?:""}"

            holder.Decrement.setSafeOnClickListener {
                holder.Decrement.isEnabled = false
                var count = Integer.parseInt(holder.Quantity.text.toString())

                if (holder.Quantity.text.toString() == "1") {
                    quantity = holder.Quantity.text.toString()
                    try {
                        val builder = AlertDialog.Builder(context)
                        builder.setCancelable(true)
                        builder.setIcon(R.drawable.ic_launcher)
                        builder.setTitle("Delete")
                        builder.setCancelable(false)
                        builder.setMessage("Are you sure you want to Delete?")
                        builder.setPositiveButton("Yes") { dialog, which ->
                            deleteCartItem.deleteCartItem(
                                userData.Id.toString(),
                                userData.totalPrice.toDouble(),
                                position,
                                quantity,
                                holder.Quantity,
                                count
                            )
                        }
                        builder.setNegativeButton("No") { dialog, which ->
                            holder.Decrement.isEnabled = true
                        }

                        val dialog = builder.create()
                        dialog.show()

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    count--
                    quantity = holder.Quantity.text.toString()
                    CartTotalAmount.decrementAmmount(
                        count,
                        userData.totalPrice.toDouble(),
                        userData.Id,
                        quantity,
                        holder.Quantity,
                        holder.Price,
                        holder.Decrement,
                        userData.priceSizeDetails
                    )
                }
            }

            holder.Increment.setSafeOnClickListener {
                holder.Increment.isEnabled = false
                var count = Integer.parseInt(holder.Quantity.text.toString())
                count++
                quantity = holder.Quantity.text.toString()

                CartTotalAmount.incrementAmmount(
                    count,
                    userData.totalPrice.toDouble(),
                    userData.Id,
                    quantity,
                    holder.Quantity,
                    holder.Price,
                    holder.Increment,
                    userData.priceSizeDetails
                )
            }

            holder.DeleteItem.setSafeOnClickListener {
                quantity = holder.Quantity.text.toString()

                click.click5(
                    userData.Id.toString(),
                    position,
                    userData.totalPrice.toDouble(),
                    quantity,
                    holder.Quantity,
                    0
                )
            }

            holder.itemImage.setSafeOnClickListener {
                productViewListener.viewProduct(userData.productId?.Id.toString() , userData.dealId)
            }


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
        var Quantity: TextView
        var DeleteItem: ImageView
        var itemQty: TextView
        var date_deliverydate: TextView
        var valueSize: TextView
        var value: TextView
        var priceTag: TextView

        init {
            itemImage = itemView.findViewById(R.id.image)
            ItemName = itemView.findViewById(R.id.item_name)
            Price = itemView.findViewById(R.id.price)
            Increment = itemView.findViewById(R.id.increment)
            Decrement = itemView.findViewById(R.id.decrement)
            Quantity = itemView.findViewById(R.id.quantity)
            DeleteItem = itemView.findViewById(R.id.DeleteItem)
            itemQty = itemView.findViewById(R.id.itemQty)
            date_deliverydate = itemView.findViewById(R.id.date_deliverydate)
            valueSize = itemView.findViewById(R.id.valueSize)
            value = itemView.findViewById(R.id.value)
            priceTag = itemView.findViewById(R.id.priceTag)


        }
    }


    override fun getItemCount(): Int {
        return itemList.size
    }

}


