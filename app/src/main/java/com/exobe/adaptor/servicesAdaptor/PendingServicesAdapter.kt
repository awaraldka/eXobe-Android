package com.exobe.adaptor.servicesAdaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.customClicks.serviceselectedclick
import com.exobe.entity.response.serviceTab.ServicesListDoc

class PendingServicesAdapter(
    val context: Context,
    var serviceclick: serviceselectedclick,
    var data: ArrayList<ServicesListDoc>,
    var id: String = ""
) :
    RecyclerView.Adapter<PendingServicesAdapter.ProductLatestViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductLatestViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.services_pending_list, parent, false)
        return ProductLatestViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductLatestViewHolder, position: Int) {
        try {
            val Data = data[position]
            var dateTime = Data.createdAt
            var date = dateTime?.split("T")?.toTypedArray()
            var dateone = date?.get(0)
            var dateTwo = date?.get(1)
            holder.cardview_Product.setSafeOnClickListener {

                serviceclick.pendingclick(
                    position, Data.id.toString()
                )
            }
            holder.service_date.text = dateone
            holder.customername.text = "${Data.userId?.firstName} ${Data.userId?.lastName}"
            holder.service_id.text = Data.orderId
            holder.category.text = Data.serviceDetails[0].serviceId.categoryId.categoryName
            holder.paymentstatus.text = Data.paymentStatus

            if (Data.paymentStatus.lowercase() == "pending") {
                holder.paymentstatus.setTextColor(ContextCompat.getColor(context, R.color.red))
            } else {
                holder.paymentstatus.setTextColor(ContextCompat.getColor(context, R.color.delivered))

            }






        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return data.size

    }

    class ProductLatestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cardview_Product = view.findViewById<LinearLayout>(R.id.Pending_cardview)

        var customername = view.findViewById<TextView>(R.id.customername)
        var service_id = view.findViewById<TextView>(R.id.service_id)
        var service_date = view.findViewById<TextView>(R.id.service_date)

        //        var orderststus = view.findViewById<TextView>(R.id.orderststus)
        var category = view.findViewById<TextView>(R.id.category)
        var paymentstatus = view.findViewById<TextView>(R.id.paymentstatus)


    }

}