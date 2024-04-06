package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.customClicks.CampaignEdit
import com.exobe.databinding.CampaignParticipationLayoutBinding
import com.exobe.entity.response.AddedCampaignListDocs
import com.exobe.util.DateFormat
import com.exobe.utils.CommonFunctions

class CampaignAddedRetailerAdapter(
    val context: Context,
    private val dataCampaign: ArrayList<AddedCampaignListDocs>,
    val campaignOn:String,
    val click:CampaignEdit
): RecyclerView.Adapter<CampaignAddedRetailerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CampaignParticipationLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataCampaign.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val binding =  holder.binding

            dataCampaign[position].apply {




                val formattedDateTimePair = DateFormat.formatDateTime(DateFormat.convertAndAddTime(startDate))
                val formattedEndDateTimePair = DateFormat.formatDateTime(DateFormat.convertAndAddTime(endDate))

                if (formattedDateTimePair != null ) {
                    val (formattedDate, formattedTime) = formattedDateTimePair
                    binding.campaignType.text =  "Start Date: $formattedDate, $formattedTime"
                }
                if (formattedEndDateTimePair != null ) {
                    val (formattedDate, formattedTime) = formattedEndDateTimePair
                    binding.date.text =  "End Date: $formattedDate, $formattedTime"
                }

                if(campaignOn =="SERVICE"){

                    binding.qty.isSingleLine = true
                    binding.productName.text =  serviceId.serviceName
                    binding.qty.text =  serviceId.description
                    binding.size.text =  serviceId.categoryId.categoryName

                    val actualPrice =CommonFunctions.colorSubstring("Actual Price: R ${serviceId.price}", "R ${serviceId.price}")
                    binding.actualPrice.text = actualPrice

                    binding.intrestedPrice.text = "Campaign Price: R $serviceDiscountedPrice"


                    if (serviceId.serviceImage.isNotEmpty()){
                        Glide.with(context).load(serviceId.serviceImage[0]).thumbnail(0.25f).placeholder(
                            R.drawable.dummyproduct).into(holder.binding.image)
                    }


                    binding.editButton.setSafeOnClickListener {
                        click.editCampaignClick(serviceId.id,id)

                    }

                }else{
                    binding.editButton.isVisible = true
                    binding.productName.text =  productId.productName

                    if (campaignDetail.isNotEmpty()){

                        val actualPrice =CommonFunctions.colorSubstring("Actual Price: R ${campaignDetail[0].price}", "R ${campaignDetail[0].price}")



                        binding.size.text =  "Size/Value: ${campaignDetail[0].value} ${campaignDetail[0].unit}"
                        binding.qty.text = actualPrice
                        binding.actualPrice.text =  "Campaign Price: R ${campaignDetail[0].discountedPrice}"
                        binding.intrestedPrice.text =  "Discount: ${campaignDetail[0].discountedPercentage} %"
                    }


                    if (productId.productImage.isNotEmpty()){
                        Glide.with(context).load(productId.productImage[0]).thumbnail(0.25f).placeholder(
                            R.drawable.dummyproduct).into(holder.binding.image)
                    }


                    binding.editButton.setSafeOnClickListener {
                        click.editCampaignClick(productId.id,id)

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