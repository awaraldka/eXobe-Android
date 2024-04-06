package com.exobe.adaptor

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.exobe.modelClass.ServicesMyModel
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.customClicks.DeleteServiceItem
import com.exobe.customClicks.ServiceDetailsAdd
import com.exobe.customClicks.ServicesTotalAmount
import kotlin.collections.ArrayList

class service_selected_adaptor(
    val context: Context,
    var itemList: ArrayList<ServicesMyModel>,
    val servicesTotalAmount: ServicesTotalAmount,
    val deleteServiceItem: DeleteServiceItem,
    val serviceDetailsAdd: ServiceDetailsAdd
) : RecyclerView.Adapter<service_selected_adaptor.ViewHolder>() {
    var quantity = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.selected_service, parent, false)
        return ViewHolder(view)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        try {
            val userData = itemList[position]
            holder.CategoryName.text = userData.title


            if (userData.isDealActive){
                holder.priceTag.text =  userData.priceTag
                holder.actual_amount.text =  "${userData.actualPrice}/unit"
                holder.offOnProduct.text =  userData.discount


                holder.priceTag.paintFlags = holder.priceTag.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            }
            else{
                holder.priceTag.text =  "R ${CommonFunctions.currencyFormatter(userData.price.toDouble())}/unit"
            }



            val reqPrice =   if (userData.isDealActive) userData.actualPrice.replace("R ","").replace("/unit","").replace(" ", "").toDouble() else userData.price.toDouble()

            holder.increment.setOnClickListener {
                var count = Integer.parseInt(holder.quantity.text.toString())
                if (count < 100) {
                    count++
                    holder.quantity.text = count.toString()
                    quantity = holder.quantity.text.toString()
                    servicesTotalAmount.incrementAmount(count,userData.id, userData.title,reqPrice,quantity)
                    serviceDetailsAdd.addServiceDetails(userData.mainId,count)
                }

            }

            holder.decrement.setOnClickListener {
                var count = Integer.parseInt(holder.quantity.text.toString())
                if (holder.quantity.text.toString() == "1") {
                    try {
                        val builder = AlertDialog.Builder(context)
                        builder.setCancelable(true)
                        builder.setIcon(R.drawable.ic_launcher)
                        builder.setTitle("Delete")
                        builder.setMessage("Are you sure you want to delete?")
                        builder.setPositiveButton("Yes") { dialog, which ->
                            deleteServiceItem.deleteServiceItem(count,userData.id, position)
                        }
                        builder.setNegativeButton("No") { dialog, which -> }

                        val dialog = builder.create()
                        dialog.show()
                        count = 1
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                } else {
                    count--
                    holder.quantity.text = count.toString()
                    quantity = holder.quantity.text.toString()
                    servicesTotalAmount.decrementAmount(count,userData.id, userData.title,reqPrice,quantity)
                    serviceDetailsAdd.addServiceDetails(userData.mainId,count)
                }
            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var CategoryName: TextView
        var priceTag: TextView
        var actual_amount: TextView
        var offOnProduct: TextView
        var quantity: TextView
        var decrement: RelativeLayout
        var increment: RelativeLayout

        init {

            CategoryName = itemView.findViewById(R.id.CategoryName)
            priceTag = itemView.findViewById(R.id.priceTag)
            actual_amount = itemView.findViewById(R.id.actual_amount)
            offOnProduct = itemView.findViewById(R.id.offOnProduct)
            quantity = itemView.findViewById(R.id.quantity)
            increment = itemView.findViewById(R.id.increment)
            decrement = itemView.findViewById(R.id.decrement)

        }

    }


}
