package com.exobe.adaptor.servicesAdaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.customClicks.CommonListenerServices
import com.exobe.databinding.CommonPendingServicesBinding
import com.exobe.entity.response.serviceTab.GetAllOrdersCommonDocs
import com.exobe.util.DateFormat

class AllCompletedServicesAdapter(
    val context: Context,
    var data: ArrayList<GetAllOrdersCommonDocs>,
    val viewServiceClick: CommonListenerServices
) : RecyclerView.Adapter<AllCompletedServicesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CommonPendingServicesBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            data[position].apply {
                val binding = holder.binding

                binding.name.text =  "${orderId.productDetails.getOrNull(0)!!.productId.userId.firstName} ${orderId.productDetails.getOrNull(0)!!.productId.userId.lastName}"
                binding.orderId.text =  orderId.orderId
                binding.entity.text =  when (userType){
                    "FIELD_ENTITY" -> {"Fulfillment Entity"}
                    "PICKUP_PARTNER" -> {"Pickup Driver"}
                    else  -> {"Delivery Driver"}
                }
                binding.price.text =  "R ${CommonFunctions.currencyFormatter(orderId.orderPrice.toDouble())}"
                binding.orderDate.text = DateFormat.getDateAndTime(createdAt)
                val quantity = orderId.productDetails.sumOf { it.quantity }
                binding.quantity.text = quantity.toString()
                binding.deliveryType.text = getDeliveryName(orderId.deliveryType)
                binding.orderStatus.text = requestStatus
                binding.orderStatus.setTextColor(ContextCompat.getColor(context, R.color.delivered))


                binding.root.setOnClickListener {
                    viewServiceClick.serviceProvidersCompletedClick(_id,orderId.orderId,userType= userType)
                }

            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    inner class ViewHolder(val binding: CommonPendingServicesBinding) :
        RecyclerView.ViewHolder(binding.root)



    fun getDeliveryName(type: String):String{
        val  deliveryOption = when(type){
            "SUNDAY" ->{ "Deliver on Sunday"}
            "SAMEDAY" ->{ "Same day delivery"}
            "BUISNESSDAY" ->{ "Next business day"}
            "SATURDAY" ->{ "Delivery on Saturday"}
            else ->{ "Standard"}

        }
        return deliveryOption

    }



}