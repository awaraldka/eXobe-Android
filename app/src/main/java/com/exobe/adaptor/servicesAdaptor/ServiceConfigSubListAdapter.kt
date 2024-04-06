package com.exobe.adaptor.servicesAdaptor

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exobe.R
import com.exobe.databinding.CustomizeFeesBinding
import com.exobe.entity.response.SubCategoryServiceDetails

class ServiceConfigSubListAdapter(
    val context: Context,
    val result: List<SubCategoryServiceDetails>
) : RecyclerView.Adapter<ServiceConfigSubListAdapter.MyViewHolder>() {



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




                binding.feesCharges.text = serviceName


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
                            binding.CheckBox.setOnCheckedChangeListener(null)
                            binding.CheckBox.isChecked = s.toString().isNotEmpty()

                            if (binding.priceValue.text.isEmpty()) {
                                binding.priceValue.setBackgroundResource(R.drawable.input_error)
                            } else {
                                binding.priceValue.setBackgroundResource(R.drawable.backgroundsearch)
                            }


                            binding.CheckBox.setOnCheckedChangeListener { _, isChecked ->
                                isSelected = isChecked
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