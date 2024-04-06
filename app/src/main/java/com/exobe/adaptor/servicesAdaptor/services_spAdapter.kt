package com.exobe.adaptor.servicesAdaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.customClicks.categoryserviceClick
import com.exobe.databinding.ServicesModalLayoutBinding
import com.exobe.entity.response.Services_ServiceproviderDocResponce


class services_spAdapter(
    val context: Context,
    val itemList: ArrayList<Services_ServiceproviderDocResponce>,
    var categoryserviceClick: categoryserviceClick,
): RecyclerView.Adapter<services_spAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): services_spAdapter.ViewHolder {
        val binding = ServicesModalLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: services_spAdapter.ViewHolder, position: Int) {
        val userData = itemList[position]

        Glide.with(context).load(userData.categoryImage).into(holder.binding.serviceImage)
        holder.binding.serviceCategoryName.text = userData.categoryName?.capitalizeFirstLetter()
        holder.binding.serviceCategoryDec.text = userData.description?.replace("\\s+".toRegex(), " ")?.trim()?.capitalizeFirstLetter()
        holder.binding.clickLL.setSafeOnClickListener {
            userData.categoryName?.let { it1 ->
                userData._id?.let { it2 ->
                    userData.description?.let { it3->   categoryserviceClick.viewsubcategoryservice(
                        it2,
                        it1,
                        it3
                    ) }

                }
            }
        }

}


    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(val binding: ServicesModalLayoutBinding) : RecyclerView.ViewHolder(binding.root)



}