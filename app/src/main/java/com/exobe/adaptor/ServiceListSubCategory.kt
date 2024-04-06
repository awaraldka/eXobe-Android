package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.exobe.modelClass.SubCategoryModalClass
import com.exobe.R
import com.exobe.customClicks.SubCategoryClick
import com.exobe.databinding.SubCategoryDesignBinding

class ServiceListSubCategory(
    val context: Context,
    var data: MutableList<SubCategoryModalClass>,
    val click: SubCategoryClick
) : RecyclerView.Adapter<ServiceListSubCategory.ViewHolder>() {

    var selectItem: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SubCategoryDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataList = data[position]
        try {
            holder.binding.subCategoryName.text = dataList.subCategoryName

            holder.binding.subCategoryNameClick.setOnClickListener {

                if (!dataList.isSelected) {
                    val previouslySelectedItem = getSelectedItem()
                    previouslySelectedItem?.isSelected = false
                    dataList.isSelected = true
                    notifyDataSetChanged()
                } else {
                    val previouslySelectedItem = getSelectedItem()
                    previouslySelectedItem?.isSelected = true
                    dataList.isSelected = false
                    notifyDataSetChanged()
                }
                click.subCategoryClick(dataList.subCategoryName, dataList.id)

            }

            if (dataList.isSelected) {

                holder.binding.subCategoryNameClick.isEnabled = false
                holder.binding.subCategoryNameClick.background = context.getDrawable(R.drawable.selectedcategoryborder)
                holder.binding.subCategoryName.setTextColor(context.getColor(R.color.white))
            } else {
                holder.binding.subCategoryNameClick.isEnabled = true
                holder.binding.subCategoryNameClick.background = context.getDrawable(R.drawable.subcategoryborder)
                holder.binding.subCategoryName.setTextColor(context.getColor(R.color.black))

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(val binding: SubCategoryDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    private fun getSelectedItem(): SubCategoryModalClass? {
        return data.find { it.isSelected }
    }
}