package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.customClicks.serviceAvailableCategoryClick
import com.exobe.entity.response.customerservices.ListCategoryServiceDocs

class ServicesAdapter(
    val context: Context,
    val itemList: ArrayList<ListCategoryServiceDocs>,
    var click: serviceAvailableCategoryClick
) :
    RecyclerView.Adapter<ServicesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.services_modal_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val Data = itemList[position]
            Glide.with(context).load(Data.categoryImage).into(holder.image)
            holder.text.text = Data.categoryName?.capitalizeFirstLetter()
            holder.serviceCategoryDec.text = Data.description?.replace("\\s+".toRegex(), " ")?.trim()?.capitalizeFirstLetter()
            holder.clickLL.setSafeOnClickListener {
                click.serviceAvailableCategory(Data._id, Data.categoryName)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var image: ImageView
        var text: TextView
        var serviceCategoryDec: TextView
        var clickLL: LinearLayout

        init {
            image = itemView.findViewById(R.id.serviceImage)
            text = itemView.findViewById(R.id.serviceCategoryName)
            serviceCategoryDec = itemView.findViewById(R.id.serviceCategoryDec)
            clickLL = itemView.findViewById(R.id.clickLL)
        }
    }
}
