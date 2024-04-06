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
import com.exobe.customClicks.RemoveService
import com.exobe.customClicks.ServicesAddListener
import com.exobe.entity.request.SPServiceDetailsArray
import com.exobe.entity.response.serviceTab.NewServicesResponseServiceArray

class SelectedSubCategoryAdaptor(
    val context: Context,
    val itemList: ArrayList<NewServicesResponseServiceArray>,
    var Data: ArrayList<SPServiceDetailsArray>,
    val removeService: RemoveService,
    val servicesAddListener: ServicesAddListener

) :
    RecyclerView.Adapter<SelectedSubCategoryAdaptor.ProductLatestViewHolder>() {
    var isRemove = false
    var isSelected = false
    var isUpdate = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductLatestViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.selected_sub_category_view, parent, false)
        return ProductLatestViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductLatestViewHolder, position: Int) {

        try {
            val orderData = itemList[position]
            holder.checkboxtext.text = orderData.serviceDetails.serviceName.capitalizeFirstLetter()


            if (orderData.isSelected){
                removeService.removeService(0,"")

                orderData.isSelected = orderData.isSelected
                orderData.serviceId = orderData.serviceDetails.id
                holder.checkboxtext.isChecked = true
            } else {

                orderData.isSelected = orderData.isSelected
                orderData.serviceId = orderData.serviceDetails.id
                holder.checkboxtext.isChecked = false
            }
//            holder.PriceField.setTextValue(orderData.price_D)
//            holder.weeklyFees.setTextValue(orderData.price_W)
//            holder.monthlyFees.setTextValue(orderData.price_M)
//            holder.qtrly.setTextValue(orderData.price_Q)

//            orderData.priceDaily =  if (orderData.price_D !=  null) orderData.price_D else ""
//            orderData.priceMonthly =  if (orderData.price_M !=  null) orderData.price_M else ""
//            orderData.priceWeekly =  if (orderData.price_W !=  null) orderData.price_W else ""
//            orderData.priceQ =  if (orderData.price_Q !=  null) orderData.price_Q else ""
//


            holder.checkboxtext.setSafeOnClickListener {
                removeService.removeService(0,"")
                if(!holder.checkboxtext.isChecked){
               //     orderData.isDelete = true
                    orderData.isSelected = orderData.isSelected
                  //  orderData.isUpdate = false
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
                        var price = ""
                     //   orderData.isUpdate = false
                        orderData.isSelected = orderData.isSelected
                    //    orderData.isDelete = true
                        orderData.serviceId = orderData.serviceDetails.id

                    }else{
                        removeService.removeService(0,"")
                        holder.checkboxtext.isChecked = true
                        var price = s.toString()
                        holder.PriceField.setBackgroundResource(R.drawable.delivery_background)
                      //  orderData.priceDaily = s.toString()
                        orderData.serviceId = orderData.serviceDetails.id

                        if(!orderData.isSelected) {
                       //     orderData.isUpdate = false
                       //     orderData.isDelete = false

                        } else {
                      //      orderData.isUpdate = true
                          //  orderData.isDelete = false
                        }
                        holder.checkboxtext.isChecked = true
                    }
                }
            })
            holder.weeklyFees.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {

                    if(s.toString().startsWith("0")) {
                        androidextention.alertBox("Please add valid amount", context)
                    } else if (s.toString().isEmpty()){
                        holder.checkboxtext.isChecked = false
                        var price = ""
                     //   orderData.isUpdate = false
                        orderData.isSelected = orderData.isSelected
                      //  orderData.isDelete = true
                        orderData.serviceId = orderData.serviceDetails.id

                    }else{
                        removeService.removeService(0,"")
                        holder.checkboxtext.isChecked = true
                        holder.weeklyFees.setBackgroundResource(R.drawable.delivery_background)
                       // orderData.priceWeekly = s.toString()
                        orderData.serviceId = orderData.serviceDetails.id

                        if(!orderData.isSelected) {
                       //     orderData.isUpdate = false
                       //     orderData.isDelete = false

                        } else {
                       //     orderData.isUpdate = true
                       //     orderData.isDelete = false
                        }

                    }
                }
            })
            holder.monthlyFees.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {

                    if(s.toString().startsWith("0")) {
                        androidextention.alertBox("Please add valid amount", context)
                    } else if (s.toString().isEmpty()){
                        holder.checkboxtext.isChecked = false
                        var price = ""
                     //   orderData.isUpdate = false
                        orderData.isSelected = orderData.isSelected
                      //  orderData.isDelete = true
                        orderData.serviceId = orderData.serviceDetails.id

                    }else{
                        removeService.removeService(0,"")
                        holder.checkboxtext.isChecked = true
                        holder.monthlyFees.setBackgroundResource(R.drawable.delivery_background)
                     //   orderData.priceMonthly = s.toString()
                        orderData.serviceId = orderData.serviceDetails.id

                        if(!orderData.isSelected) {
                      //      orderData.isUpdate = false
                      //      orderData.isDelete = false
//
                        } else {
                  //          orderData.isUpdate = true
                         //   orderData.isDelete = false
                        }

                    }
                }
            })
            holder.qtrly.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {

                    if(s.toString().startsWith("0")) {
                        androidextention.alertBox("Please add valid amount", context)
                    } else if (s.toString().isEmpty()){
                        holder.checkboxtext.isChecked = false
                        var price = ""
                 //       orderData.isUpdate = false
                        orderData.isSelected = orderData.isSelected
                   //     orderData.isDelete = true
                        orderData.serviceId = orderData.serviceDetails.id

                    }else{
                        removeService.removeService(0,"")
                        holder.checkboxtext.isChecked = true
                        holder.qtrly.setBackgroundResource(R.drawable.delivery_background)
                      //  orderData.priceQ = s.toString()
                        orderData.serviceId = orderData.serviceDetails.id

                        if(!orderData.isSelected) {
                      //      orderData.isUpdate = false
                      //      orderData.isDelete = false

                        } else {
                   //         orderData.isUpdate = true
                    //        orderData.isDelete = false
                        }

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
        var PriceField: EditText = itemView.findViewById(R.id.dailyfees)
        var weeklyFees: EditText = itemView.findViewById(R.id.weeklyFees)
        var monthlyFees: EditText = itemView.findViewById(R.id.monthlyFees)
        var qtrly: EditText = itemView.findViewById(R.id.qtrly)

    }

}