package com.exobe.adaptor.servicesAdaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exobe.databinding.ServicesConfigSubcatgegoryBinding
import com.exobe.entity.response.ServiceConfigServiceDetails


class ServiceConfigSubAdapter(val context:Context, val docs:List<ServiceConfigServiceDetails>):RecyclerView.Adapter<ServiceConfigSubAdapter.ViewHolder>(){
    inner class ViewHolder(val binding:ServicesConfigSubcatgegoryBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ServicesConfigSubcatgegoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return docs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val binding = holder.binding
            docs[position].apply {

                binding.subServiceName.text = subCategoryData.subCategoryName
                binding.checkbox.isChecked = isSelected


                binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                    isSelected = isChecked
                }


                binding.serviceRv.layoutManager = LinearLayoutManager(context)
                val adapter = ServiceConfigSubListAdapter(context,services)
                binding.serviceRv.adapter = adapter



            }




        }catch (e:Exception){
            e.printStackTrace()
        }
    }





}