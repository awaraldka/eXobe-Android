package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.exobe.R
import com.exobe.entity.response.PaymentRetailerCustomerDocs
import com.exobe.util.DateFormat.Companion.exobeAllScreenDateFormat

class PaymentRetailerCustomerAdapter (
    var context: Context,
    var data: ArrayList<PaymentRetailerCustomerDocs>
): RecyclerView.Adapter<PaymentRetailerCustomerAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var txt1 = view.findViewById<TextView>(R.id.txt1)
        var txt2 = view.findViewById<TextView>(R.id.txt2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentRetailerCustomerAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.notification_modallayout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaymentRetailerCustomerAdapter.ViewHolder, position: Int) {
        val userData = data[position]

        holder.txt1.text = exobeAllScreenDateFormat(userData.createdAt)
        holder.txt2.text = userData.transactionDetails?.sourceTransfer
    }

    override fun getItemCount(): Int {
        return data.size
    }
}