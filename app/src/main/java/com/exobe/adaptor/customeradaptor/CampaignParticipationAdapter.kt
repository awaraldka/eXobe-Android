package com.exobe.adaptor.customeradaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.exobe.R
import com.exobe.databinding.CampaignParticipationLayoutBinding
import com.exobe.entity.response.customer.CampaignListDocs
import com.exobe.util.DateFormat
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter

class CampaignParticipationAdapter(
    val context: Context,
    val dataCampaign: ArrayList<CampaignListDocs>,
    val campaignOn: String
): RecyclerView.Adapter<CampaignParticipationAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CampaignParticipationLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataCampaign.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            dataCampaign[position].apply {

                if (campaignOn =="PRODUCT"){
                    holder.binding.productName.text = productId.productName.capitalizeFirstLetter()
                    holder.binding.size.text = "Unit/Size: ${priceSizeDetails.value + priceSizeDetails.unit}"
                    holder.binding.qty.text = "Qty: $quantity"

                    holder.binding.actualPrice.text = "Actual Price: R ${CommonFunctions.currencyFormatter(priceSizeDetails.price.toDouble())}"
                    holder.binding.intrestedPrice.text = "Interested Price: R ${CommonFunctions.currencyFormatter(interestedPrice.toDouble())}"
                    holder.binding.campaignType.text = "Campaign Type: ${spordicType.capitalizeFirstLetter()}"
                    holder.binding.date.text = "Date: ${DateFormat.NotificationDate(createdAt)}"
                    if (productId.productImage.isNotEmpty()){
                        Glide.with(context).load(productId.productImage[0]).thumbnail(0.25f).placeholder(
                            R.drawable.dummyproduct).into(holder.binding.image)
                    }
                }else{
                    holder.binding.productName.text = serviceId.serviceName.capitalizeFirstLetter()
                    holder.binding.size.text = "Service Category: ${serviceId.categoryId.categoryName}"
                    holder.binding.qty.isVisible = false

                    holder.binding.actualPrice.text = "Actual Price: R ${CommonFunctions.currencyFormatter(serviceId.price.toDouble())}"
                    holder.binding.intrestedPrice.text = "Interested Price: R ${CommonFunctions.currencyFormatter(interestedPrice.toDouble())}"
                    holder.binding.campaignType.text = "Campaign Type: ${spordicType.capitalizeFirstLetter()}"
                    holder.binding.date.text = "Date: ${DateFormat.NotificationDate(createdAt)}"
                    if (serviceId.serviceImage.isNotEmpty()){
                        Glide.with(context).load(serviceId.serviceImage[0]).thumbnail(0.25f).placeholder(
                            R.drawable.dummyproduct).into(holder.binding.image)
                    }
                }



            }

        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    inner class ViewHolder(val binding: CampaignParticipationLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)






}