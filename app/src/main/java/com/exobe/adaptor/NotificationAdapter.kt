package com.exobe.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.customClicks.NotificationType
import com.exobe.customClicks.deleteCustomClick
import com.exobe.dialogs.CommonDeleteDialogBox
import com.exobe.entity.response.listNotificationDocs
import com.exobe.util.DateFormat

class NotificationAdapter(
    var context: Context,
    var data: ArrayList<listNotificationDocs>,
    var deleteItem: deleteCustomClick,
    var fragmentManager: FragmentManager?,
    var notificationType: NotificationType
    ): RecyclerView.Adapter<NotificationAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var txt1 = view.findViewById<TextView>(R.id.txt1)
        var txt2 = view.findViewById<TextView>(R.id.txt8)
        var deleteImage = view.findViewById<ImageView>(R.id.deleteImage)
        var navigate_notification_ll = view.findViewById<LinearLayout>(R.id.navigate_notification_ll)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.notification_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder, position: Int) {
        val userData = data[position]

        holder.txt1.text = userData.body
        holder.txt2.text=DateFormat.NotificationDate(userData.createdAt)
        holder.deleteImage.setSafeOnClickListener {
            fragmentManager?.let { CommonDeleteDialogBox("notification", deleteItem, userData._id).show(it, "MyCustomFragment") }

        }
        holder.navigate_notification_ll.setSafeOnClickListener{
            notificationType.notificationClick(position,userData)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}