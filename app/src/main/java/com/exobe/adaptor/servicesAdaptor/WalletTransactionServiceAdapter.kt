package com.exobe.adaptor.servicesAdaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.utils.SavedPrefManager
import com.exobe.databinding.WalletTransactionViewBinding
import com.exobe.entity.response.MyEarningDocs
import com.exobe.util.DateFormat

class WalletTransactionServiceAdapter(
    val context: Context,
    var data: ArrayList<MyEarningDocs>,
) : RecyclerView.Adapter<WalletTransactionServiceAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WalletTransactionViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            data[position].apply {
                val binding = holder.binding

                val userId =  SavedPrefManager.getStringPreferences(context,SavedPrefManager.USER_ID).toString()





                binding.FromET.text = "${fromUser.firstName}  ${fromUser.lastName} (${fromWalletType})"
                binding.ToET.text = "${toUser.firstName}  ${toUser.lastName} (${toWalletType})"
                binding.orderId.text =  orderReferenceId.orderId
                binding.orderDate.text = DateFormat.NotificationDate(orderReferenceId.createdAt)
                binding.name.text =  "${orderReferenceId.userId.firstName} ${orderReferenceId.userId.lastName}"
                binding.TaxDate.text = DateFormat.NotificationDate(orderReferenceId.createdAt)
                binding.orderStatus.text = orderReferenceId.orderStatus
                binding.txnStatus.text = transactionStatus


                if(orderReferenceId.orderStatus == "COMPLETED"){
                    binding.orderStatus.setTextColor(ContextCompat.getColor(context, R.color.delivered))
                }else{
                    binding.orderStatus.setTextColor(ContextCompat.getColor(context, R.color.red))
                }

                if(transactionStatus == "COMPLETED"){
                    binding.txnStatus.setTextColor(ContextCompat.getColor(context, R.color.delivered))
                }else{
                    binding.txnStatus.setTextColor(ContextCompat.getColor(context, R.color.red))
                }

                binding.receiveOrPending.text =  if(transactionStatus == "COMPLETED") "Received" else transactionStatus.capitalizeFirstLetter()


                if (userId == toUser.id){
                    binding.priceReceived.text =  "+ R ${CommonFunctions.currencyFormatter(transactionAmount.toDouble())}"
                    binding.priceReceived.setTextColor(ContextCompat.getColor(context, R.color.delivered))
                }else{
                    binding.priceReceived.setTextColor(ContextCompat.getColor(context, R.color.red))
                    binding.priceReceived.text =  "- R ${CommonFunctions.currencyFormatter(transactionAmount.toDouble())}"
                }


                if (transactionStatus.lowercase() == "pending") {
                    binding.receiveOrPending.setTextColor(ContextCompat.getColor(context, R.color.red))
                } else {
                    binding.receiveOrPending.setTextColor(ContextCompat.getColor(context, R.color.delivered))

                }




            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    inner class ViewHolder(val binding: WalletTransactionViewBinding) :
        RecyclerView.ViewHolder(binding.root)

}