package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.databinding.CustomizeFeesBinding
import com.exobe.entity.response.DeliveryFeesResult

class FeesConfigurationAdapter(
    val context: Context,
    val result: List<DeliveryFeesResult>, val userRole:String) : RecyclerView.Adapter<FeesConfigurationAdapter.MyViewHolder>() {



    class MyViewHolder(val binding: CustomizeFeesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            CustomizeFeesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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


                val commonFee = if (userRole == "Pickup Driver") pickupFee!! else deliveryAmount

                val deliveryAmount = CommonFunctions.currencyFormatter(commonFee.toDouble())
                val fullText = "$feeName R $deliveryAmount"
                val redPart = "R $deliveryAmount"
                val spannable = SpannableString(fullText)
                val redColor = ContextCompat.getColor(context, R.color.red)
                val redStart = fullText.indexOf(redPart)
                val redEnd = redStart + redPart.length
                spannable.setSpan(
                    ForegroundColorSpan(redColor),
                    redStart,
                    redEnd,
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding.feesCharges.text = spannable


                binding.CheckBox.isChecked = isSelected && fees.isNotEmpty()
                binding.priceValue.setText(fees)

                var isCheckBoxProgrammaticallyUpdated = false

                binding.CheckBox.setOnCheckedChangeListener { _, isChecked ->
                    isSelected = isChecked
                    if (!isChecked) { fees = "" }

                    if (isSelected && binding.priceValue.text.isEmpty()) {
                        fees = ""
                        binding.priceValue.setBackgroundResource(R.drawable.input_error)
                    } else {
                        binding.priceValue.setBackgroundResource(R.drawable.backgroundsearch)
                    }

                    // Update the flag when CheckBox state is changed programmatically
                    isCheckBoxProgrammaticallyUpdated = true
                }

                binding.priceValue.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {

                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        if (!isCheckBoxProgrammaticallyUpdated) {
                            fees = binding.priceValue.text.toString()
                            isSelected = s.toString().isNotEmpty()
                            binding.CheckBox.setOnCheckedChangeListener(null) // Temporarily remove the listener
                            binding.CheckBox.isChecked = s.toString().isNotEmpty()

                            if (binding.priceValue.text.isEmpty()) {
                                binding.priceValue.setBackgroundResource(R.drawable.input_error)
                            } else {
                                binding.priceValue.setBackgroundResource(R.drawable.backgroundsearch)
                            }


                            binding.CheckBox.setOnCheckedChangeListener { _, isChecked ->

                                if (!isChecked) { fees = "" }


                                isCheckBoxProgrammaticallyUpdated = true
                            }
                        } else {
                            isCheckBoxProgrammaticallyUpdated = false
                            fees = ""
                        }
                    }

                })

            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }




}