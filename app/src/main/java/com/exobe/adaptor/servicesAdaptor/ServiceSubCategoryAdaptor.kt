package com.exobe.adaptor.servicesAdaptor

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.androidextention
import com.exobe.entity.response.serviceTab.NewServicesResponseServiceArray

class ServiceSubCategoryAdaptor(
    val context: Context,
    val itemList: List<NewServicesResponseServiceArray>,
) :
    RecyclerView.Adapter<ServiceSubCategoryAdaptor.ProductLatestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductLatestViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.subcategory_details, parent, false)
        return ProductLatestViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductLatestViewHolder, position: Int) {

        try {
            val orderData = itemList[position]
            holder.checkboxtext.text = orderData.serviceDetails.serviceName.capitalizeFirstLetter()



            if(orderData.price.toInt() > 0) {
                holder.PriceField.setText(orderData.price)
                holder.checkboxtext.isChecked = true
                orderData.serviceId = orderData.serviceDetails.id
            }


            holder.checkboxtext.setSafeOnClickListener {
                if(!holder.checkboxtext.isChecked){
                    orderData.isSelected = true
                    orderData.serviceId = orderData.serviceDetails.id
                    holder.PriceField.setText("")
                    holder.PriceField.setBackgroundResource(R.drawable.delivery_background)
                }else{
                    if(holder.PriceField.text.isEmpty()){
                        holder.PriceField.setBackgroundResource(R.drawable.input_error)
                        androidextention.alertBox("Please add amount", context)
                    }

                }

            }
            holder.PriceField.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {

                    if(s.toString().startsWith("0")) {
                        androidextention.alertBox("Please add valid amount", context)
                    } else if (s.toString().isEmpty()){
                        holder.checkboxtext.isChecked = false
                        orderData.price = ""
                        orderData.isSelected = orderData.isSelected
                        orderData.serviceId = orderData.serviceDetails.id

                    }else{
                        holder.checkboxtext.isChecked = true
                        holder.PriceField.setBackgroundResource(R.drawable.delivery_background)
                        orderData.price = s.toString()
                        orderData.isSelected = true
                        orderData.serviceId = orderData.serviceDetails.id

                    }
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    class ProductLatestViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var checkboxtext: CheckBox = itemView.findViewById(R.id.checkboxtext)
        var PriceField: EditText = itemView.findViewById(R.id.PriceField)

    }

}