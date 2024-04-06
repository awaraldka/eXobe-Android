package com.exobe.adaptor.servicesAdaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exobe.adaptor.orderrecycler_Adapter
import com.exobe.adaptor.service_selected_adaptor
import com.exobe.R
import com.exobe.entity.response.serviceTab.ServiceListViewPurchaseService
import com.exobe.entity.response.serviceTab.ServiceListViewServicesDetail

class ViewServiceAdaptor(
    var context: Context,
    var docs: ArrayList<ServiceListViewPurchaseService>
) :
    RecyclerView.Adapter<ViewServiceAdaptor.SubServicesViewHolder>() {
    lateinit var servicesAdaptor : service_selected_adaptor
    class SubServicesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val subServiceName: TextView = view.findViewById(R.id.subServiceName)
        val imageRight: ImageView = view.findViewById(R.id.imageRight)
        val imageDown: ImageView = view.findViewById(R.id.imageDown)
        val serviceRv: RecyclerView = view.findViewById(R.id.serviceRv)
        val subServiceClick: LinearLayout = view.findViewById(R.id.subServiceClick)
        val header: LinearLayout = view.findViewById(R.id.header)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubServicesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.sub_services_design, parent, false)
        return SubServicesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubServicesViewHolder, position: Int) {
        try {
            holder.serviceRv.visibility = View.VISIBLE
            holder.header.visibility = View.VISIBLE
            holder.imageDown.visibility = View.GONE
            holder.imageRight.visibility = View.GONE
            holder.subServiceName.text = docs[position].subCategoryDetails.subCategoryName

            setChildAdaptor(context,docs[position].servicesDetails , holder)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return docs.size
    }

    fun setChildAdaptor(
        context: Context,
        servicesDetails: ArrayList<ServiceListViewServicesDetail>,
        holder: SubServicesViewHolder,

        ) {
        holder.serviceRv.layoutManager = LinearLayoutManager(context)
        var adapter = orderrecycler_Adapter(context, servicesDetails)

        holder.serviceRv.adapter = adapter
    }

//    fun deleteItem(data : ArrayList<ChooseServicesMyModel>) {
//        docs = data
//        notifyDataSetChanged()
//    }
}