package com.exobe.adaptor.servicesAdaptor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.activities.services.ProceedOrderActivity
import com.exobe.customClicks.OrderProcessClick
import com.exobe.databinding.AllOrderItemsBinding
import com.exobe.entity.response.serviceTab.AllProducts

class OrderListServicesAdapter(
    val context: Context,
    var data: ArrayList<AllProducts>,
    var visibility:Boolean,
    private val retailerId :String,
    private val orderIdRequest :String,
    var click:OrderProcessClick,
    val isOrderPickedUp:Boolean,
    val isOrderDelivered:Boolean,
    val requestStatus:String,
    val userRole:String
) : RecyclerView.Adapter<OrderListServicesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AllOrderItemsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            data[position].apply {



                Glide.with(context).load(productId.thumbnail).placeholder(R.drawable.dummyproduct).into(holder.binding.image)

                // TODO On Same product view if check box needs to show then visibility is true either false
                holder.binding.orderCheckBox.isVisible = visibility

                holder.binding.orderCheckBox.isChecked = isSelected



                holder.binding.itemName.text =  productId.productName
                holder.binding.ProductId.text =  "Product Id: ${priceSizeDetails.id}"


                if (productId.dealReferenceId != null && (productId.dealReferenceId.dealDiscount != "" && productId.dealReferenceId.dealPrice != 0.0)){
                    holder.binding.price.text =  "R ${CommonFunctions.currencyFormatter(price)}"
                    holder.binding.price.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    holder.binding.DealPrice.text =  "R ${CommonFunctions.currencyFormatter(productId.dealReferenceId.dealPrice)}"
                    holder.binding.price.setTextColor(ContextCompat.getColor(context, R.color.black))
                    holder.binding.DealPrice.setTextColor(ContextCompat.getColor(context, R.color.red))
                    holder.binding.offPercent.isVisible = true
                    holder.binding.offPercent.text = "${productId.dealReferenceId.dealDiscount}% Off"
                }else{
                    holder.binding.offPercent.isVisible = false
                    holder.binding.price.text =  "R ${CommonFunctions.currencyFormatter(price)}"
                    holder.binding.price.setTextColor(ContextCompat.getColor(context, R.color.red))
                }





                holder.binding.qty.text =  "Qty: $quantity"
                holder.binding.sizeValue.text =  "Size/value: ${priceSizeDetails.value} ${priceSizeDetails.unit}"

                when(userRole){
                    "DELIVERY_PARTNER" -> {
                        if (requestStatus == "COMPLETED" || requestStatus =="Rejected"){
                            holder.binding.ProceedButton.isVisible = false
                        }else{
                            holder.binding.ProceedButton.isVisible = data.size -1 == position && visibility == false
                            holder.binding.ProceedButton.isVisible = data.size -1 == position && visibility == false
                            if (isOrderPickedUp && !isOrderDelivered){
                                holder.binding.deliverdToFE.text = "Delivery To Fulfilment Entity"
                                holder.binding.ProceedButton.isVisible = true
                                holder.binding.ProceedButton.isVisible = data.size -1 == position && visibility == false
                            }
                        }
                    }
                    "FIELD_ENTITY" -> {
                        holder.binding.ProceedButton.isVisible = false
                    }
                    "PICKUP_PARTNER" -> {
                        if (requestStatus == "COMPLETED" || requestStatus =="Rejected"){
                            holder.binding.ProceedButton.isVisible = false
                        }else{
                            if (isOrderPickedUp && !isOrderDelivered){
                                holder.binding.deliverdToFE.text = "Delivery To Fulfilment Entity"
                                holder.binding.ProceedButton.isVisible = data.size -1 == position && visibility == false
                            }
                            if (!isOrderPickedUp && !isOrderDelivered){
                                holder.binding.ProceedButton.isVisible = data.size -1 == position && visibility == false
                            }
                        }
                    }
                }






                holder.binding.ProceedButton.setOnClickListener {
                    if (isOrderPickedUp){
                        click.sendOtpForDeliveredItemToFE(retailerId)
                    }else{
                        val intent = Intent(context, ProceedOrderActivity::class.java)
                        intent.putExtra("retailerId",retailerId)
                        intent.putExtra("viewId",orderIdRequest)
                        intent.putExtra("userRole",userRole)
                        context.startActivity(intent)
                    }

                }


                holder.binding.root.setOnClickListener {
                    if (visibility){
                        isSelected = !isSelected
                        notifyDataSetChanged()
                        click.orderProcess()
                    }

                }




            }








        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    inner class ViewHolder(val binding: AllOrderItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

}