package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.customClicks.subserviceClick
import com.exobe.databinding.ServicesModalLayoutBinding
import com.exobe.entity.response.DealstocustomerList_Result

class OnServiceDealAdaptor(var Context: Context, var subserviceClick: subserviceClick,var flag: String,var data : ArrayList<DealstocustomerList_Result>) : RecyclerView.Adapter<OnServiceDealAdaptor.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnServiceDealAdaptor.MyViewHolder {
        val binding = ServicesModalLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)


    }

    override fun onBindViewHolder(holder: OnServiceDealAdaptor.MyViewHolder, position: Int) {
        try {
            val userData = data[position]

            Glide.with(Context).load(userData.categoryImage).into(holder.binding.serviceImage)
            holder.binding.serviceCategoryName.text = userData.categoryName.capitalizeFirstLetter()
            holder.binding.serviceCategoryDec.text = userData.description.replace("\\s+".toRegex(), " ").trim().capitalizeFirstLetter()
            holder.binding.clickLL.setSafeOnClickListener {
                subserviceClick.subservice(flag, data[position]._id, data[position].categoryName)
            }

        }catch (e:Exception){
            e.printStackTrace()
        }
    }



    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewHolder(val binding: ServicesModalLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}