package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.customClicks.categorynameclick
import com.exobe.databinding.ServicesModalLayoutBinding
import com.exobe.entity.response.Docs

class CategoryAdapter(val context: Context, val itemList: ArrayList<Docs>, var categorynameclick: categorynameclick) : RecyclerView.Adapter<CategoryAdapter.CategoryItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val binding = ServicesModalLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        try {
            val Data = itemList[position]
            Glide.with(context).load(Data.categoryImage).into(holder.binding.serviceImage)
            holder.binding.serviceCategoryName.text = Data.categoryName?.capitalizeFirstLetter()
            holder.binding.serviceCategoryDec.text = Data.description?.replace("\\s+".toRegex(), " ")?.trim()?.capitalizeFirstLetter()
            holder.binding.clickLL.setSafeOnClickListener {
                categorynameclick.categoryname(Data._id, Data.categoryName!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class CategoryItemViewHolder(val binding: ServicesModalLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}