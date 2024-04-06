package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.modelClass.WholesalerList
import com.exobe.R
import com.exobe.customClicks.deleteBrand

class SupplierAdaptor(
    var context: Context,
    var BrandList: ArrayList<WholesalerList>,
    var deleteBrand: deleteBrand
)  :
    RecyclerView.Adapter<SupplierAdaptor.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.add_product_brands_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.brandName.text = BrandList[position].wholesalerName

            holder.cross.setOnClickListener {
                deleteBrand.deleteRetailer(position)
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return BrandList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var brandName = itemView.findViewById<TextView>(R.id.brand_name)
        var cross = itemView.findViewById<ImageView>(R.id.brand_cross)

    }

}