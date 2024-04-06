package com.exobe.adaptor.servicesAdaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.utils.SavedPrefManager
import com.exobe.customClicks.UpdateFeeConfig
import com.exobe.databinding.UpdateFeesLayoutBinding
import com.exobe.entity.response.FeeListResult

class FeeConfigUpdateAdapter(
    val context: Context,
    var data: ArrayList<FeeListResult>,
    val click:UpdateFeeConfig
) : RecyclerView.Adapter<FeeConfigUpdateAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UpdateFeesLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val userType = SavedPrefManager.getStringPreferences(context, SavedPrefManager.USER_TYPE).toString()
            data[position].apply {
                val binding = holder.binding


                when (userType) {


                    "DELIVERY_PARTNER" -> {
                        binding.feeCustomizeET.setText(deliveryFee.toString())
                    }
                    "PICKUP_PARTNER" -> {
                        binding.feeCustomizeET.setText(pickupFee.toString())
                    }

                }


                binding.feeType.text = feeName
                if (position <= 9) binding.countTV.text =  "0${position + 1}" else position.toString()


                binding.ChangeFeesButton.setSafeOnClickListener {
                    when (userType) {


                        "DELIVERY_PARTNER" -> {
                            click.updateFeeDeliveryDriver(id = id, fee = binding.feeCustomizeET.text.toString(), standard = standardFee.toString())
                        }

                        "PICKUP_PARTNER" -> {
                            click.updateFeePickUpDriver(id = id, fee = binding.feeCustomizeET.text.toString())
                        }

                    }

                }





            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    inner class ViewHolder(val binding: UpdateFeesLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)





}