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
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.customClicks.RemoveService
import com.exobe.customClicks.ServicesAddListener
import com.exobe.entity.request.SPServiceDetailsArray
import com.exobe.entity.response.serviceTab.NewServicesResponseDoc
import com.exobe.entity.response.serviceTab.NewServicesResponseServiceArray

class SubServicesAdaptor(
    var context: Context,
    var docs: ArrayList<NewServicesResponseDoc>,
    var Data: ArrayList<SPServiceDetailsArray>,
    val removeService: RemoveService,
    val servicesAddListener: ServicesAddListener,
    val isScreenFor: String
) :
    RecyclerView.Adapter<SubServicesAdaptor.SubServicesViewHolder>() {
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
            holder.subServiceName.text = docs[position].subCategory.subCategoryName
            holder.serviceRv.visibility = if (docs[position].expanded) View.VISIBLE else View.GONE
            holder.imageDown.visibility = if (docs[position].expanded) View.VISIBLE else View.GONE
            holder.imageRight.visibility = if (docs[position].expanded) View.GONE else View.VISIBLE
            holder.subServiceClick.setSafeOnClickListener {
                docs[position].expanded = !docs[position].expanded
                notifyItemChanged(position)
            }
            setChildAdaptor(context, docs[position].serviceArray, holder)


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return docs.size
    }

    private fun setChildAdaptor(
        context: Context,
        serviceArray: List<NewServicesResponseServiceArray>,
        holder: SubServicesViewHolder
    ) {
        holder.serviceRv.layoutManager = LinearLayoutManager(this.context)
        val productLatestAdapter = ServiceSubCategoryAdaptor(context, serviceArray)
        holder.serviceRv.adapter = productLatestAdapter
    }


}