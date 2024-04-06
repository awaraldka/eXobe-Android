package com.exobe.adaptor.customeradaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.SavedPrefManager
import com.exobe.databinding.CustomerWalletLayoutBinding
import com.exobe.entity.response.MyEarningDocs
import com.exobe.util.DateFormat

class CustomerWalletAdapter(val context: Context, private val data: ArrayList<MyEarningDocs>) : RecyclerView.Adapter<CustomerWalletAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CustomerWalletLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerWalletAdapter.ViewHolder, position: Int) {
        try {
            val userId =  SavedPrefManager.getStringPreferences(context, SavedPrefManager.USER_ID).toString()
            val binding =  holder.binding
            data[position].apply {

                binding.fromWalletEt.text =  "${fromUser.firstName}  ${fromUser.lastName} (${fromWalletType})"
                binding.toWalletEt.text = "${toUser.firstName}  ${toUser.lastName} (${toWalletType})"
                binding.txnStatus.text = transactionStatus
                binding.TaxDate.text = DateFormat.NotificationDate(createdAt)


                if (userId == toUser.id){
                    binding.amountEt.text =  "+ R ${CommonFunctions.currencyFormatter(transactionAmount.toDouble())}"
                    binding.amountEt.setTextColor(ContextCompat.getColor(context, R.color.delivered))
                }else{
                    binding.amountEt.setTextColor(ContextCompat.getColor(context, R.color.red))
                    binding.amountEt.text =  "- R ${CommonFunctions.currencyFormatter(transactionAmount.toDouble())}"
                }


            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }



    inner class ViewHolder(val binding: CustomerWalletLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

}