package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.customClicks.CustomeClick2
import com.exobe.entity.response.AddProductByAdminDocs

class SearchProduct(var context: Context, var click: CustomeClick2,var search:ArrayList<AddProductByAdminDocs>):RecyclerView.Adapter<SearchProduct.ViewHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.serach_product,parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {

            val orderData = search[position]
            Glide.with(context).load(orderData.productImage?.get(0)).into(holder.product_image)
            holder.productName.text = orderData.productName.toString().capitalizeFirstLetter()
            holder.ProductDetails.setSafeOnClickListener {
                click.click2(orderData._id, "")
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return search.size
    }


    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        var ProductDetails: LinearLayout
        var product_image: ImageView
        var productName: TextView


        init {
            ProductDetails= view.findViewById(R.id.ProductDetails)
            product_image= view.findViewById(R.id.product_image)
            productName= view.findViewById(R.id.productName)
        }
    }

}