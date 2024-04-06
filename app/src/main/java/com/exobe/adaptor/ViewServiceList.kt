package com.exobe.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.customClicks.BookingDetailListener
import com.exobe.databinding.OrderHistoryItemsBinding
import com.exobe.entity.response.NewOrderServiceReqDoc
import com.exobe.util.DateFormat


class ViewServiceList(val mContext: Context, val itemList: ArrayList<NewOrderServiceReqDoc> , val bookingDetailListener: BookingDetailListener) :
    RecyclerView.Adapter<ViewServiceList.ViewHolder>() {

    var flag1 = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = OrderHistoryItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        try {
            val serviceListData = itemList[position]

            holder.binding.itemName.text = serviceListData.serviceDetails[0].serviceId.serviceName

            holder.binding.price.text = "R ${CommonFunctions.currencyFormatter(serviceListData.actualPrice.toDouble())}"

            holder.binding.txtOrderID.text = "Booking ID: ${serviceListData.orderId}"

            holder.binding.txtDeliveryDate.text = "Booking Date: ${DateFormat.deliveryDateFormat(serviceListData.createdAt)}"

            holder.binding.qty.isVisible = true
            holder.binding.qty.text= serviceListData.serviceDetails.getOrNull(0)?.quantity.toString()




            holder.binding.txtStatus.text = when(serviceListData.serviceStatus){
                "PENDING" -> {
                    "Pending"
                }
                "BOOKING_RECEIVED" -> {
                    "Booking confirmed"
                }
                "ONTHEWAY" -> {
                    "On the way"
                }
                "EXECUTION" -> {
                    "Execution"
                }

                else -> {
                    "Completed"
                }
            }



            if (serviceListData.serviceStatus == "PENDING" || serviceListData.serviceStatus == "CANCELLED"){
                holder.binding.txtStatus.setTextColor(ContextCompat.getColor(mContext,R.color.cancelled))
            }else{
                holder.binding.txtStatus.setTextColor(ContextCompat.getColor(mContext,R.color.delivered))
            }



            Glide.with(mContext).load(serviceListData.serviceDetails.getOrNull(0)?.serviceId?.userId?.profilePic).placeholder(R.drawable.dummyproduct).into(holder.binding.image)

            val status =  if (serviceListData.serviceStatus =="COMPLETED") "DELIVERED" else serviceListData.serviceStatus


            holder.binding.item.setSafeOnClickListener {
                bookingDetailListener.bookingDetail("customerServiceDetails", serviceListData,status)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(val binding : OrderHistoryItemsBinding) : RecyclerView.ViewHolder(binding.root)
}