package com.exobe.adaptor.servicesAdaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exobe.databinding.ServicesConfigCatgegoryBinding
import com.exobe.entity.response.ServiceConfigCategories

class ServiceConfigAdapter(val context:Context, val docs:List<ServiceConfigCategories>):RecyclerView.Adapter<ServiceConfigAdapter.ViewHolder>() {
    inner class ViewHolder(val binding:ServicesConfigCatgegoryBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ServicesConfigCatgegoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
      return docs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val binding = holder.binding
            docs[position].apply {

                binding.subServiceName.text = categoryData.categoryName



                binding.serviceRv.layoutManager = LinearLayoutManager(context)
                val adapter = ServiceConfigSubAdapter(context,subCategories)
                binding.serviceRv.adapter = adapter





            }




        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}