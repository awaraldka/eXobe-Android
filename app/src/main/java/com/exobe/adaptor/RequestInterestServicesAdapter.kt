package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exobe.customClicks.RequestedServiceClick
import com.exobe.databinding.ListsBinding
import com.exobe.entity.response.CountryListResult
import com.exobe.entity.response.customerservices.ServicesListNearMeDoc
import com.exobe.entity.response.customerservices.ServicesListNearMeServiceArray

class RequestInterestServicesAdapter(
    val context: Context,
    var data: ArrayList<ServicesListNearMeDoc>,
    private var dataForSubcategory: ArrayList<ServicesListNearMeServiceArray>,
    private val clickForPopUp:String,
    private val viewServiceClick: RequestedServiceClick
) : RecyclerView.Adapter<RequestInterestServicesAdapter.ViewHolder>() {


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
                            viewServiceClick.requestedServiceCategory(subCategory.subCategoryName, subCategory.id, serviceArray)
                        }
                    }
                }else{
                    dataForSubcategory[position].apply {
                        holder.binding.contentTextView.text =  serviceName
                        val price  = if (isDealActive) dealPrice  else price

                        holder.binding.click.setOnClickListener {
                            viewServiceClick.subCategory(serviceName,"$price",id)
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
    fun filterListCategory(filteredList: ArrayList<ServicesListNearMeDoc>) {
        data = filteredList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterListSubCategory(filteredList: ArrayList<ServicesListNearMeServiceArray>) {
        dataForSubcategory = filteredList
        notifyDataSetChanged()
    }





}