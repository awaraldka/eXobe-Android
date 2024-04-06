package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.customClicks.CustomeClick2
import com.exobe.entity.response.customerservices.ListCategoryServiceDocs

class home_services_adapter(
    var context: Context,
    var data: ArrayList<ListCategoryServiceDocs>,
    var customeClick: CustomeClick2
) : RecyclerView.Adapter<home_services_adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mInflater = LayoutInflater.from(context)
        val view: View = mInflater.inflate(R.layout.services_modal_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            val Data = data[position]
            Glide.with(context).load(Data.categoryImage).thumbnail(0.25f).into(holder.image)
            holder.text.text = Data.categoryName?.capitalizeFirstLetter()
            holder.serviceCategoryDec.text = Data.description?.replace("\\s+".toRegex(), " ")?.trim()?.capitalizeFirstLetter()
            holder.image.setSafeOnClickListener {
                customeClick.click2(Data._id, Data.categoryName)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int {

        return if (data.size > 10) {
            10
        } else {
            data.size
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var text: TextView
        var serviceCategoryDec: TextView

        init {
            image = itemView.findViewById(R.id.serviceImage)
            text = itemView.findViewById(R.id.serviceCategoryName)
            serviceCategoryDec = itemView.findViewById(R.id.serviceCategoryDec)
        }
    }
}
