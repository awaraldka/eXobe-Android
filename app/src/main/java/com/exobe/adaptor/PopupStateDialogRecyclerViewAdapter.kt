package com.exobe.adaptor

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.Model.ServicesList
import com.exobe.R
import com.exobe.customClicks.popupItemClickListner

class PopupStateDialogRecyclerViewAdapter(
    var context: Context?,
    var data: ArrayList<ServicesList>,
    var dialog: Dialog,
    var flag: String,
    var click: popupItemClickListner
) :
    RecyclerView.Adapter<PopupStateDialogRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =  LayoutInflater.from(context).inflate(R.layout.popup_inside_lists, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val Data = data[position]
            if (flag == "Service") {
                holder.Names.text = Data.Name
                holder.click_ll.setSafeOnClickListener {
                    click.getData(Data.Name, flag)
                }

            } else if (flag == "SubCategory") {
                holder.Names.text = Data.Name
                holder.click_ll.setSafeOnClickListener {
                    click.getData(Data.Name, flag)
                }

            }
        } catch (e : Exception) {
            e.printStackTrace()
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }

     class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var Names: TextView
        var click_ll: LinearLayout



        init {
            Names = itemView.findViewById(R.id.content_textView)
            click_ll = itemView.findViewById(R.id.click_ll)

        }
    }




}