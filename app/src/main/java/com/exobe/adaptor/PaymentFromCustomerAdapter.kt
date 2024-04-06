package com.exobe.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.entity.response.RetailerOrderManagementDoc
import com.exobe.util.DateFormat

class PaymentFromCustomerAdapter(
    var context: Context,
    var data: ArrayList<RetailerOrderManagementDoc>
): RecyclerView.Adapter<PaymentFromCustomerAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // Define click listener for the ViewHolder's View.
        var txt1 = view.findViewById<TextView>(R.id.txt1)
        var txt2 = view.findViewById<TextView>(R.id.txt2)
        var txt8 = view.findViewById<TextView>(R.id.txt8)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentFromCustomerAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.notification_modallayout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PaymentFromCustomerAdapter.ViewHolder, position: Int) {
        var userData = data[position]

        holder.txt1.text = "Hi ${userData.myOrders[0].productId.userId.firstName  + " " + userData.myOrders[0].productId.userId.lastName}, R ${CommonFunctions.currencyFormatter(userData.myOrderPrice.toDouble())} received from " +
                "${userData.userId.firstName+ " " +userData.userId.lastName} for Order ID ${userData.orderId}"
        holder.txt2.text = "+R " + CommonFunctions.currencyFormatter(userData.myOrderPrice.toDouble())+""

        holder.txt8.text = DateFormat.covertTimeOtherFormat(userData.createdAt)

        // Glide.with(context).load(userData.image).into(holder.image);
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
