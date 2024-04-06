package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exobe.utils.CommonFunctions
import com.exobe.customClicks.ClickForDeliveryFees
import com.exobe.databinding.FeesViewForAllBinding
import com.exobe.entity.response.DeliveryFessCustomerResult

class DeliveryFeesAdapter(
    val context: Context,
    val result: List<DeliveryFessCustomerResult>,
    val click: ClickForDeliveryFees,
    private var deliveryTypeCheck :String
) : RecyclerView.Adapter<DeliveryFeesAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: FeesViewForAllBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            FeesViewForAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return result.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {
            result[position].apply {
                val binding = holder.binding



                val deliveryAmount = CommonFunctions.currencyFormatter(deliveryAmount.toDouble())
                val redPart = "R $deliveryAmount"

                binding.feesCharges.text = deliveryOption
                binding.feesType.text = "($description)"
                binding.fees.text = redPart

                if (isSelected) {

                    binding.radioButton.isEnabled = false
                    binding.radioButton.isChecked = true
                } else {
                    if (deliveryTypeCheck == deliveryType){
                        binding.radioButton.isChecked = true
                        isSelected = true
                        click.deliveryFees(deliveryAmount,deliveryType)

                    }else{
                        binding.radioButton.isEnabled = true
                        binding.radioButton.isChecked = false
                        isSelected = false
                    }



                }


                binding.root.setOnClickListener {
                    deliveryTypeCheck = ""
                    if (!isSelected) {
                        val previouslySelectedItem = getSelectedItem()
                        previouslySelectedItem?.isSelected = false
                        isSelected = true
                        notifyDataSetChanged()
                    } else {
                        val previouslySelectedItem = getSelectedItem()
                        previouslySelectedItem?.isSelected = true
                        isSelected = false
                        notifyDataSetChanged()
                    }
                    click.deliveryFees(deliveryAmount,deliveryType)

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun getSelectedItem(): DeliveryFessCustomerResult? {
        return result.find { it.isSelected }
    }

}