package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.customClicks.CustomeClick2
import com.exobe.databinding.ServicesModalLayoutBinding
import com.exobe.entity.response.DealSearchDocs

class DealProductAdapter(var context: Context, var click: CustomeClick2, var search:ArrayList<DealSearchDocs>):
    RecyclerView.Adapter<DealProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ServicesModalLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            search[position].apply {
                Glide.with(context).load(productImage?.get(0)).into(holder.binding.serviceImage)
                holder.binding.serviceCategoryName.text = productName?.capitalizeFirstLetter()
                holder.binding.serviceCategoryDec.text = description?.replace("\\s+".toRegex(), " ")?.trim()?.capitalizeFirstLetter()
                holder.binding.clickLL.setSafeOnClickListener {
                    click.click2(_id, "")
                }
            }
           
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return search.size
    }

    class ViewHolder(val binding: ServicesModalLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}