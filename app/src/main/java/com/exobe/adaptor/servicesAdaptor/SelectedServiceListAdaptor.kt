package com.exobe.adaptor.servicesAdaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.exobe.R
import com.exobe.customClicks.ClickSelectedServices
import com.exobe.databinding.ProductDetailsItemsBinding
import com.exobe.entity.response.serviceTab.SelectedServiceServices
import com.exobe.extension.load
import kotlin.Exception

class SelectedServiceListAdaptor(
    var mcontext: Context,
    var data: ArrayList<SelectedServiceServices>,
    val click:ClickSelectedServices

    ) : RecyclerView.Adapter<SelectedServiceListAdaptor.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SelectedServiceListAdaptor.MyViewHolder {
        val binding = ProductDetailsItemsBinding.inflate(LayoutInflater.from(mcontext), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectedServiceListAdaptor.MyViewHolder, position: Int) {
        try {
            data[position].apply {
                holder.binding.nameCategory.text = name
                if (name.contains("Standard Service")){
                    Glide.with(mcontext).load(R.drawable.booking_provider).into(holder.binding.image)
                }else{
                    holder.binding.image.load(category.categoryImage)
                }

                holder.binding.root.setOnClickListener {
                    click.getSelectedService(name = name, categoryId = category.id, categoryType = category.categoryType)
                }


            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }


    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder(val binding:ProductDetailsItemsBinding):RecyclerView.ViewHolder(binding.root)


}