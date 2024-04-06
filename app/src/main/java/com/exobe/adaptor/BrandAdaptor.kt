package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.modelClass.BrandList
import com.exobe.R
import com.exobe.customClicks.deleteBrand

class BrandAdaptor(
    var filldetailsRetailer: Context,
    var BrandList: ArrayList<BrandList>,
    var deleteBrand: deleteBrand
) :
    RecyclerView.Adapter<BrandAdaptor.ViewHolder>() {


    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_product_brands_list, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val Brand = data[position]
        try {
            holder.brandName.text = BrandList.get(position).brandName

            holder.cross.setSafeOnClickListener {
                deleteBrand.deleteItem(position)
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return BrandList.size
    }

    // stores and recycles views as they are scrolled off screen

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var brandName = itemView.findViewById<TextView>(R.id.brand_name)
        var cross = itemView.findViewById<ImageView>(R.id.brand_cross)

    }

}