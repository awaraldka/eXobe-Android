package com.exobe.Adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.exobe.R
import com.exobe.entity.response.PaymentFromCustyomer_Docs
import com.exobe.util.DateFormat
import java.lang.Exception

class PaymentWholesalerAdapter (
    var context: Context,
    var data: ArrayList<PaymentFromCustyomer_Docs>
): RecyclerView.Adapter<PaymentWholesalerAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // Define click listener for the ViewHolder's View.
        var txt1 = view.findViewById<TextView>(R.id.txt1)
        var txt2 = view.findViewById<TextView>(R.id.txt2)
        var txt8 = view.findViewById<TextView>(R.id.txt8)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentWholesalerAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.notification_modallayout, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PaymentWholesalerAdapter.ViewHolder, position: Int) {
        try {
            var userData = data[position]

            holder.txt1.text = "Hi ${userData.paidToDetails[0].firstName + " " + userData.paidToDetails[0].lastName} ,R ${userData.orderAmount.toString()}  is received from " +
                    "${userData.userDetails.firstName+ " " +userData.userDetails.lastName} and order ID is ${userData.orderDetails.orderId}"
            holder.txt2.text = "+R " + userData.orderAmount.toString()+""
//        holder.txt3.text =
//        holder.txt4.text = "received from"
//        holder.txt5.text =
//        holder.txt6.text = "for order ID"
//        holder.txt7.text =
            holder.txt8.text = DateFormat.covertTimeOtherFormat(userData.createdAt)

            // Glide.with(context).load(userData.image).into(holder.image);
        } catch ( e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}
