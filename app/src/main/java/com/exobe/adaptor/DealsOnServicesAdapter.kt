package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.customClicks.subserviceClick
import com.exobe.entity.response.DealsonCustomerResult

class DealsOnServicesAdapter(var Context: Context, var subserviceClick: subserviceClick, var flag: String, var CategoryId : String, var data : ArrayList<DealsonCustomerResult>) : RecyclerView.Adapter<DealsOnServicesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DealsOnServicesAdapter.MyViewHolder {
        val mInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = mInflater.inflate(R.layout.deals_on_service_customer, parent, false)


        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: DealsOnServicesAdapter.MyViewHolder, position: Int) {
        try {
            val userData = data[position]
            for (i in data.indices){
                holder.productName.text = userData.categoryName.capitalizeFirstLetter()
//                Glide.with(Context).load(userData.categoryImage).placeholder(R.drawable.sofa_img).into(holder.product_image)
                Glide.with(Context).load(userData.thumbnail).placeholder(R.drawable.deal_image).into(holder.product_image)

            }

            holder.view_subservice.setSafeOnClickListener {
                subserviceClick.subservice(
                    "customer",
                    userData._id,
                    data.get(position).categoryName
                )
            }
        }catch (e:Exception){
            e.printStackTrace()
        }


    }



    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var productName = item.findViewById<TextView>(R.id.productName)
        var view_subservice = item.findViewById<CardView>(R.id.view_subservice)
        var product_image = item.findViewById<ImageView>(R.id.product_image)

    }

}