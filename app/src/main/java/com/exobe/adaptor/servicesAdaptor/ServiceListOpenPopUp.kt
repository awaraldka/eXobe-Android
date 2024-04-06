package com.exobe.adaptor.servicesAdaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.customClicks.serviceNameClickLisntner
import com.exobe.entity.response.ServiceProviderListData


class ServiceListOpenPopUp(
    var context: Context,
    var data: List<ServiceProviderListData>,
    var flag: String,
    var serviceNameListener: serviceNameClickLisntner
) :
    RecyclerView.Adapter<ServiceListOpenPopUp.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(R.layout.lists, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            data[position].apply {
                if (flag == "SelectService") {
                    holder.Names.text = serviceName
                    holder.click_ll.setSafeOnClickListener {
                        val image = if(serviceImage.isNotEmpty()) serviceImage[0] else ""
                        serviceNameListener.serviceNameClick(categoryId.categoryName,categoryId.id,subCategoryId.id,subCategoryId.subCategoryName,price,serviceName,image,id)
                    }
                }
            }


        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
            return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredStateList: List<ServiceProviderListData>) {
        data = filteredStateList
        notifyDataSetChanged()
    }


    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var Names: TextView
        var click_ll: LinearLayout



        init {
            Names = itemView.findViewById(R.id.content_textView)
            click_ll = itemView.findViewById(R.id.click_ll)

        }
    }




}