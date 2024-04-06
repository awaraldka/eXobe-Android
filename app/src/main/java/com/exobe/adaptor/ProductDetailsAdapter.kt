package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.databinding.OrderdetailItemsRetailerBinding
import com.exobe.entity.response.RetailerOrderManagementMyOrder
import java.util.ArrayList

class ProductDetailsAdapter(
    var context: Context,
    var data: ArrayList<RetailerOrderManagementMyOrder>
): RecyclerView.Adapter<ProductDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OrderdetailItemsRetailerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            data[position].apply {
                holder.binding.productname.text= productId.productName.capitalizeFirstLetter()



                holder.binding.price.text="R ${CommonFunctions.currencyFormatter(price.toDouble())}"

                holder.binding.qty.text="Qty: $quantity"
                holder.binding.valueSize.text="Value/Size: ${priceSizeDetails?.value} ${priceSizeDetails?.unit!!.lowercase().takeIf { it != "other" }?:""}"
                Glide.with(context).load(productId.thumbnail).placeholder(R.drawable.dummyproduct).into(holder.binding.image)


            }

           
        }catch (e:Exception){
            e.printStackTrace()
        }



    }

    override fun getItemCount(): Int {
        return data.size

    }

    inner class ViewHolder(val binding:OrderdetailItemsRetailerBinding) : RecyclerView.ViewHolder(binding.root)






}