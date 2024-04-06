package com.exobe.adaptor.servicesAdaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.customClicks.UpdateFeeConfig
import com.exobe.databinding.FieldEntityFeeUpdateBinding
import com.exobe.entity.response.FeeListResult

class FeeConfigFieldEntityUpdateAdapter(
    val context: Context,
    var data: ArrayList<FeeListResult>,
    val click:UpdateFeeConfig
) : RecyclerView.Adapter<FeeConfigFieldEntityUpdateAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FieldEntityFeeUpdateBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            data[position].apply {
                val binding = holder.binding

                binding.dailyfees.setText(storageFee.daily.toString())
                binding.weeklyFees.setText(storageFee.weekly.toString())
                binding.monthlyFees.setText(storageFee.monthly.toString())
                binding.qtrly.setText(storageFee.quarterly.toString())





                binding.feeType.text = feeName
                if (position <= 9) binding.countTV.text =  "0${position + 1}" else position.toString()


                binding.ChangeFeesButton.setSafeOnClickListener {
                    click.updateFeeFieldEntity(id = id, dailyFee =binding.dailyfees.text.toString() , monthlyFee =binding.monthlyFees.text.toString(), weeklyFee = binding.weeklyFees.text.toString()
                        , quarterlyFee= binding.qtrly.text.toString(), minimumFee=sizeType.minimumSize, maximumFee=sizeType.maximumSize )
                }





            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    inner class ViewHolder(val binding: FieldEntityFeeUpdateBinding) :
        RecyclerView.ViewHolder(binding.root)





}