package com.exobe.adaptor.servicesAdaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exobe.databinding.NotificationLayoutBinding
import com.exobe.entity.response.listNotificationDocs
import com.exobe.util.DateFormat

class NotificationServicesCommonAdapter(val context:Context, private val notificationData: ArrayList<listNotificationDocs>): RecyclerView.Adapter<NotificationServicesCommonAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: NotificationLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationServicesCommonAdapter.ViewHolder {
        val binding = NotificationLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationServicesCommonAdapter.ViewHolder, position: Int) {
        try {
            val binding = holder.binding
            notificationData[position].apply {
                binding.txt1.text = body
                binding.txt8.text= DateFormat.NotificationDate(createdAt)


            }

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return notificationData.size
    }
}