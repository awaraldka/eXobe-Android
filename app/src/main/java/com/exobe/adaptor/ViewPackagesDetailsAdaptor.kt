package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.customClicks.ChangePriceListener
import com.exobe.databinding.ProductSizeDesignBinding
import com.exobe.entity.response.PriceSizeDetails

class ViewPackagesDetailsAdaptor(
    val context: Context,
    val priceSizeDetails: ArrayList<PriceSizeDetails>,
    val changePriceListener: ChangePriceListener
) :
    RecyclerView.Adapter<ViewPackagesDetailsAdaptor.ViewHolder>() {
    var myPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductSizeDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.binding.size.text = "${priceSizeDetails[position].value} ${priceSizeDetails[position].unit}"

            if (myPosition == position) {
                holder.binding.mainLl.setBackgroundResource(R.drawable.product_size_button)
                holder.binding.size.setTextColor(context.resources.getColor(R.color.white))
            }else {
                holder.binding.mainLl.setBackgroundResource(R.drawable.white_product_size_button)
                holder.binding.size.setTextColor(context.resources.getColor(R.color.black))
            }

            holder.binding.mainLl.setSafeOnClickListener {
                myPosition = position
                changePriceListener.changePrice(priceSizeDetails[position].price, priceSizeDetails[position].quantity, holder.binding.size.text.toString())
                notifyDataSetChanged()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return priceSizeDetails.size
    }
    class ViewHolder(val binding : ProductSizeDesignBinding) : RecyclerView.ViewHolder(binding.root)


}