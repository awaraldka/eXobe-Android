package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.exobe.R
import com.exobe.entity.response.customerservices.LSNMDocs

class ServiceDetailsAdapterSubCategory(
    var context: Context,
    var itemList: ArrayList<LSNMDocs>
) : RecyclerView.Adapter<ServiceDetailsAdapterSubCategory.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.subcategory_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {

            var subServices = itemList.get(position)

            holder.subCategory.text =
                subServices.subCategoryDetails.serviceSubCategoriesDetails.serviceName
            holder.price.text = subServices.subCategoryDetails.price.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var subCategoryCK: CheckBox
        var subCategory: TextView
        var price: TextView

        init {

            subCategory = itemView.findViewById(R.id.subCategory)
            price = itemView.findViewById(R.id.price)
            subCategoryCK = itemView.findViewById(R.id.subCategoryCk)
        }

    }


}