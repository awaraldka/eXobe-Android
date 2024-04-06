package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.entity.response.serviceTab.ServiceListViewServicesDetail


class orderrecycler_Adapter(
    var context: Context,
    var data: ArrayList<ServiceListViewServicesDetail>
) :
    RecyclerView.Adapter<orderrecycler_Adapter.ViewHolder>() {

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.orderlistrecycler_layout, parent, false)
        return ViewHolder(view)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: orderrecycler_Adapter.ViewHolder, position: Int) {
        try {
            holder.productQuantity.text = data[position].quantity.toString()
            holder.productprice.text = "R ${CommonFunctions.currencyFormatter(data[position].price.toDouble())}"
            holder.productname.text = "${position+1}. ${data[position].serviceId.serviceName.capitalizeFirstLetter()}"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    // total number of rows
    override fun getItemCount(): Int {
        return data.size
    }
    // stores and recycles views as they are scrolled off screen
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var productname: TextView = itemView.findViewById(R.id.productname)
        var productQuantity: TextView = itemView.findViewById(R.id.productQuantity)
        var productprice: TextView = itemView.findViewById(R.id.productprice)

    }
}


