package com.exobe.adaptor.customeradaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exobe.adaptor.service_selected_adaptor
import com.exobe.modelClass.ChooseServicesMyModel
import com.exobe.modelClass.ServicesMyModel
import com.exobe.R
import com.exobe.customClicks.DeleteServiceItem
import com.exobe.customClicks.ServiceDetailsAdd
import com.exobe.customClicks.ServicesTotalAmount

class CreateServiceAdaptor(
    var context: Context,
    var docs: ArrayList<ChooseServicesMyModel>,
    val servicesTotalAmount: ServicesTotalAmount,
    val deleteServiceItem: DeleteServiceItem,
    val serviceDetailsAdd: ServiceDetailsAdd
) :
    RecyclerView.Adapter<CreateServiceAdaptor.SubServicesViewHolder>() {
    lateinit var servicesAdaptor : service_selected_adaptor
    class SubServicesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val subServiceName: TextView = view.findViewById(R.id.subServiceName)
        val imageRight: ImageView = view.findViewById(R.id.imageRight)
        val imageDown: ImageView = view.findViewById(R.id.imageDown)
        val serviceRv: RecyclerView = view.findViewById(R.id.serviceRv)
        val subServiceClick: LinearLayout = view.findViewById(R.id.subServiceClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubServicesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.sub_services_design, parent, false)
        return SubServicesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubServicesViewHolder, position: Int) {
        try {
            holder.serviceRv.visibility = View.VISIBLE
            holder.imageDown.visibility = View.GONE
            holder.imageRight.visibility = View.GONE
            holder.subServiceName.text = docs[position].subServicesName

            setChildAdaptor(context, docs[position].servicesMyModel, holder,servicesTotalAmount,deleteServiceItem,serviceDetailsAdd)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return docs.size
    }

    fun setChildAdaptor(
        context: Context,
        servicesMyModel: ArrayList<ServicesMyModel>,
        holder: SubServicesViewHolder,
        servicesTotalAmount: ServicesTotalAmount,
        deleteServiceItem: DeleteServiceItem,
        serviceDetailsAdd: ServiceDetailsAdd,

        ) {
        holder.serviceRv.layoutManager = LinearLayoutManager(this.context)
        servicesAdaptor = service_selected_adaptor(context, servicesMyModel, servicesTotalAmount, deleteServiceItem, serviceDetailsAdd)
        holder.serviceRv.adapter = servicesAdaptor
    }

    fun deleteItem(data : ArrayList<ChooseServicesMyModel>) {
        docs = data
        notifyDataSetChanged()
    }
}