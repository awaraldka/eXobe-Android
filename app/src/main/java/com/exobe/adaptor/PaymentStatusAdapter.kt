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

class PaymentStatusAdapter (
    var context: Context,
    var data: ArrayList<PaymentFromCustyomer_Docs>
): RecyclerView.Adapter<PaymentStatusAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // Define click listener for the ViewHolder's View.
        var txt1 = view.findViewById<TextView>(R.id.txt1)
        var txt2 = view.findViewById<TextView>(R.id.txt2)
        var txt8 = view.findViewById<TextView>(R.id.txt8)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentStatusAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.notification_modallayout, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PaymentStatusAdapter.ViewHolder, position: Int) {
        try {
            var userData = data[position]

            holder.txt1.text = "Hi ${userData.userDetails.firstName + " " + userData.userDetails.lastName} ,R ${userData.orderAmount.toString()}  paid to " +
                    "${userData.userDetails.ownerFirstName +" " +userData.userDetails.ownerFirstName} for Order ID ${userData.orderDetails.orderId}"
            holder.txt2.text = "+R " + userData.orderAmount.toString()+""

            holder.txt8.text = DateFormat.covertTimeOtherFormat(userData.createdAt)

        } catch ( e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}
