package com.exobe.adaptor

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.entity.response.customerservices.ServicesListNearMeServiceArray

class ServiceDetailsAdapter(
    val context: Context,
    val itemList: ArrayList<ServicesListNearMeServiceArray>,
    val subCategoryName: String,
    val subCategoryId: String
): RecyclerView.Adapter<ServiceDetailsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.subcategory_list,parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {


            var subServices = itemList.get(position)

            holder.subCategory.text =
                subServices.serviceName

            if (subServices.isDealActive){
                holder.priceTag.text = "R ${CommonFunctions.currencyFormatter(subServices.price.toDouble())}"
                holder.actual_amount.text =  "R ${CommonFunctions.currencyFormatter(subServices.dealPrice.toDouble())}"
                holder.offOnProduct.text =  "${subServices.dealDiscount}% Off"


                holder.priceTag.paintFlags = holder.priceTag.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            }
            else{
                holder.priceTag.text =  "R ${CommonFunctions.currencyFormatter(subServices.price.toDouble())}/unit"
            }


//            holder.actual_amount.text =
//                "R ${CommonFunctions.currencyFormatter(subServices.price.toDouble())}/Unit"
            holder.subCategoryCK.isChecked = subServices.isSelected

            holder.subCategoryCK.setSafeOnClickListener {
                subServices.isSelected = holder.subCategoryCK.isChecked
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {


        var subCategoryCK: CheckBox
        var subCategory:TextView
//        var price: TextView
        var priceTag: TextView
        var actual_amount: TextView
        var offOnProduct: TextView
        init {

            subCategory=itemView.findViewById(R.id.subCategory)
//            price=itemView.findViewById(R.id.price)
            subCategoryCK=itemView.findViewById(R.id.subCategoryCk)
            priceTag = itemView.findViewById(R.id.priceTag)
            actual_amount = itemView.findViewById(R.id.price)
            offOnProduct = itemView.findViewById(R.id.offOnProduct)
        }

    }




}