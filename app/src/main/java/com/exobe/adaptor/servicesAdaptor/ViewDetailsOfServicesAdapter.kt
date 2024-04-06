package com.exobe.adaptor.servicesAdaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exobe.customClicks.OrderProcessClick
import com.exobe.customClicks.OutForDelivery
import com.exobe.databinding.AllDetailRetailerAndProductForServicesBinding
import com.exobe.entity.response.serviceTab.Retailers

class ViewDetailsOfServicesAdapter(
    val context: Context,
    var data: ArrayList<Retailers>,
    private val orderIdRequest:String,
    private val orderProcessClick: OrderProcessClick,
    val requestStatus :String,
    val click: OutForDelivery,
    val userRole: String
) : RecyclerView.Adapter<ViewDetailsOfServicesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AllDetailRetailerAndProductForServicesBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            data[position].apply {

                holder.binding.RetailerDetails.text = "Retailer ${position + 1}:"


                with(userId){
                    holder.binding.userName.text = "$firstName $lastName"
                    var finalAddress = StringBuffer()
                    if (addressLine1 != null && addressLine1 != "") {
                        finalAddress.append("${addressLine1},")
                    }

                    if (city != null && city != "") {
                        finalAddress.append("${city},")
                    }
                    if (state != null && state != "") {
                        finalAddress.append("${state},")
                    }
                    if (country != null && country != "") {
                        finalAddress.append(country)
                    }
                    holder.binding.deliveryAddress.text = "$finalAddress"
                    holder.binding.deliveryPincode.text = zipCode
                    holder.binding.deliveryPh.text = mobileNumber
                    holder.binding.Email.text = email


                    if (requestStatus =="Rejected"){
                        holder.binding.findRoute.isVisible = false
                    }




                    holder.binding.findRoute.setOnClickListener {
                        with(storeLocation!!.coordinates){
                            val lat = this?.getOrNull(1)!!
                            val long = this.getOrNull(0)!!
                            orderProcessClick.mapOpen(lat=lat,long=long)
                        }

                    }


                }







                holder.binding.productlist.layoutManager = LinearLayoutManager(context)
                val adapter = OrderListServicesAdapter(context, products,visibility = false,userId.id,orderIdRequest, click =orderProcessClick,isOrderPickedUp= isOrderPickedUp,
                    isOrderDelivered=isOrderDelivered, requestStatus = requestStatus, userRole = userRole)
                holder.binding.productlist.adapter = adapter

            }



        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    inner class ViewHolder(val binding: AllDetailRetailerAndProductForServicesBinding) :
        RecyclerView.ViewHolder(binding.root)

}