package com.exobe.adaptor.servicesAdaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exobe.customClicks.CampaignOnServiceClick
import com.exobe.databinding.ListsBinding
import com.exobe.entity.response.serviceTab.NewServicesResponseDoc
import com.exobe.entity.response.serviceTab.NewServicesResponseServiceArray

class AddCampaignServicesAdapter(
    val context: Context,
    var data: ArrayList<NewServicesResponseDoc>,
    private var dataForSubcategory: List<NewServicesResponseServiceArray>,
    private val clickForPopUp:String,
    private val viewServiceClick: CampaignOnServiceClick
) : RecyclerView.Adapter<AddCampaignServicesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  if (clickForPopUp =="Category") data.size else dataForSubcategory.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {

            if (clickForPopUp =="Category"){
                    data[position].apply {
                        holder.binding.contentTextView.text = subCategory.subCategoryName
                        holder.binding.click.setOnClickListener {
                            viewServiceClick.categoryClick(subCategory.subCategoryName,subCategory.id,serviceArray)
                        }
                    }
                }else{
                    dataForSubcategory[position].apply {
                        holder.binding.contentTextView.text =  serviceDetails.serviceName


                        holder.binding.click.setOnClickListener {
                            viewServiceClick.subCategoryClick(serviceDetails.serviceName,catalogueId, price)
                        }
                    }

                }


        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    inner class ViewHolder(val binding: ListsBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun filterListCategory(filteredList: ArrayList<NewServicesResponseDoc>) {
        data = filteredList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterListSubCategory(filteredList: ArrayList<NewServicesResponseServiceArray>) {
        dataForSubcategory = filteredList
        notifyDataSetChanged()
    }





}