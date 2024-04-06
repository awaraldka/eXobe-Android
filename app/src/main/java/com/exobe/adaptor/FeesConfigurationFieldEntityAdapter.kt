package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.databinding.FieldEntityFeeConfigBinding
import com.exobe.entity.response.DeliveryFeesResult

class FeesConfigurationFieldEntityAdapter(
    val context: Context,
    val result: List<DeliveryFeesResult>) : RecyclerView.Adapter<FeesConfigurationFieldEntityAdapter.MyViewHolder>() {



    class MyViewHolder(val binding: FieldEntityFeeConfigBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = FieldEntityFeeConfigBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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


                binding.description.text = "$feeName Minimum Size-${sizeType!!.minimumSize} Maximum Size-${sizeType!!.maximumSize}"


                binding.CheckBox.isChecked = isSelected 
                binding.dailyfees.setText(dailyFee)
                binding.monthlyFees.setText(monthlyFee)
                binding.qtrly.setText(quarterlyFee)
                binding.weeklyFees.setText(weeklyFee)


                binding.dailyfees.hint =  setPriceInRed(amount = storageFee!!.daily.toDouble())
                binding.monthlyFees.hint = setPriceInRed(amount = storageFee.monthly.toDouble())
                binding.qtrly.hint = setPriceInRed(amount = storageFee.quarterly.toDouble())
                binding.weeklyFees.hint = setPriceInRed(amount = storageFee.weekly.toDouble())





                binding.CheckBox.setOnCheckedChangeListener { _, isChecked ->
                    isSelected = isChecked
                    if (!isChecked) {
                        dailyFee = ""
                        monthlyFee = ""
                        quarterlyFee = ""
                        weeklyFee = ""
                    }

                    if (isSelected && binding.dailyfees.text.isEmpty() && binding.monthlyFees.text.isEmpty()
                        && binding.qtrly.text.isEmpty()&& binding.weeklyFees.text.isEmpty()) {
                        dailyFee = ""
                        monthlyFee = ""
                        quarterlyFee = ""
                        weeklyFee = ""
                        binding.dailyfees.setBackgroundResource(R.drawable.input_error)
                        binding.monthlyFees.setBackgroundResource(R.drawable.input_error)
                        binding.qtrly.setBackgroundResource(R.drawable.input_error)
                        binding.weeklyFees.setBackgroundResource(R.drawable.input_error)
                    } else {
                        binding.dailyfees.setBackgroundResource(R.drawable.backgroundsearch)
                        binding.monthlyFees.setBackgroundResource(R.drawable.backgroundsearch)
                        binding.qtrly.setBackgroundResource(R.drawable.backgroundsearch)
                        binding.weeklyFees.setBackgroundResource(R.drawable.backgroundsearch)
                    }


                }


                binding.dailyfees.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {

                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        dailyFee = binding.dailyfees.text.toString()
                        isSelected = s.toString().isNotEmpty()
                        binding.CheckBox.setOnCheckedChangeListener(null)
                        binding.CheckBox.isChecked = s.toString().isNotEmpty()


                        binding.CheckBox.isChecked = !(binding.dailyfees.text.isEmpty() && binding.weeklyFees.text.isEmpty()
                                && binding.monthlyFees.text.isEmpty() && binding.qtrly.text.isEmpty())


                        if (binding.dailyfees.text.isEmpty()) {
                            binding.dailyfees.setBackgroundResource(R.drawable.input_error)
                        } else {
                            binding.dailyfees.setBackgroundResource(R.drawable.backgroundsearch)
                        }


                        }

                })


                binding.weeklyFees.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {

                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        weeklyFee = binding.weeklyFees.text.toString()
                        isSelected = s.toString().isNotEmpty()
                        binding.CheckBox.setOnCheckedChangeListener(null)
                        binding.CheckBox.isChecked = s.toString().isNotEmpty()


                        binding.CheckBox.isChecked = !(binding.dailyfees.text.isEmpty() && binding.weeklyFees.text.isEmpty()
                                && binding.monthlyFees.text.isEmpty() && binding.qtrly.text.isEmpty())


                        if (binding.weeklyFees.text.isEmpty()) {
                            binding.weeklyFees.setBackgroundResource(R.drawable.input_error)
                        } else {
                            binding.weeklyFees.setBackgroundResource(R.drawable.backgroundsearch)
                        }


                        }

                })


                binding.qtrly.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {

                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        quarterlyFee = binding.qtrly.text.toString()
                        isSelected = s.toString().isNotEmpty()
                        binding.CheckBox.setOnCheckedChangeListener(null)
                        binding.CheckBox.isChecked = s.toString().isNotEmpty()


                        binding.CheckBox.isChecked = !(binding.dailyfees.text.isEmpty() && binding.weeklyFees.text.isEmpty()
                                && binding.monthlyFees.text.isEmpty() && binding.qtrly.text.isEmpty())


                        if (binding.qtrly.text.isEmpty()) {
                            binding.qtrly.setBackgroundResource(R.drawable.input_error)
                        } else {
                            binding.qtrly.setBackgroundResource(R.drawable.backgroundsearch)
                        }


                        }

                })


                binding.monthlyFees.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {

                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        monthlyFee = binding.monthlyFees.text.toString()
                        isSelected = s.toString().isNotEmpty()
                        binding.CheckBox.setOnCheckedChangeListener(null)
                        binding.CheckBox.isChecked = s.toString().isNotEmpty()


                        binding.CheckBox.isChecked = !(binding.dailyfees.text.isEmpty() && binding.weeklyFees.text.isEmpty()
                                && binding.monthlyFees.text.isEmpty() && binding.qtrly.text.isEmpty())


                        if (binding.monthlyFees.text.isEmpty()) {
                            binding.monthlyFees.setBackgroundResource(R.drawable.input_error)
                        } else {
                            binding.monthlyFees.setBackgroundResource(R.drawable.backgroundsearch)
                        }


                        }

                })








            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun setPriceInRed(amount: Double): String {
        val deliveryAmount = CommonFunctions.currencyFormatter(amount)

        return "R $deliveryAmount"
    }



}