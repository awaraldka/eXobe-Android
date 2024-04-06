package com.exobe.adaptor.servicesAdaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.customClicks.statusselectedclick
import com.exobe.entity.response.serviceTab.ServicesListDoc

class RequestedAdapter(
    val context: Context,
    var serviceclick: statusselectedclick,
    var data: ArrayList<ServicesListDoc>,


    ): RecyclerView.Adapter<RequestedAdapter.ViewHolder>() {
    class ViewHolder(view: View) :RecyclerView.ViewHolder(view){
        var cardview_Product = view.findViewById<LinearLayout>(R.id.Pending_cardview)

        var customername = view.findViewById<TextView>(R.id.customername)
        var service_id = view.findViewById<TextView>(R.id.service_id)
        var service_date = view.findViewById<TextView>(R.id.service_date)
        var orderststus = view.findViewById<TextView>(R.id.orderststus)
        var category = view.findViewById<TextView>(R.id.category)
        var paymentstatus = view.findViewById<TextView>(R.id.paymentstatus)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.all_services,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val Data = data[position]
            var dateTime = Data.createdAt
            var date = dateTime?.split("T")?.toTypedArray()
            var dateone = date?.get(0)
            var dateTwo = date?.get(1)
            holder.cardview_Product.setSafeOnClickListener {

                serviceclick.pendingclick(
                    position, Data.id.toString(),Data.orderStatus.toString()
                )
            }

            holder.orderststus.visibility = View.VISIBLE
            holder.orderststus.text = Data.orderStatus
            holder.service_date.text = dateone
            holder.customername.text = "${Data.userId?.firstName} ${Data.userId?.lastName}"
            holder.service_id.text = Data.orderId
            holder.category.text = Data.serviceDetails[0].serviceId.categoryId.categoryName
            holder.paymentstatus.text = Data.paymentStatus

        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return data.size
  }
}