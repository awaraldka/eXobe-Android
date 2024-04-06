package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.Model.Checkboxmodalclass
import com.exobe.R
import com.exobe.customClicks.switchValue


class fill_details_checkbox_Adapter(
    var context: Context,
    var data: ArrayList<Checkboxmodalclass>,
    var click: switchValue

) : RecyclerView.Adapter<fill_details_checkbox_Adapter.ViewHolder>() {
    var selectItem:Int = 0
    var flag = false
    var monthlyRevenue:String = ""
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // Define click listener for the ViewHolder's View.
        var Monthly_checkbox = view.findViewById<CheckBox>(R.id.Monthly_checkbox)



    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): fill_details_checkbox_Adapter.ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.fill_detail_checkbox, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: fill_details_checkbox_Adapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val Data = data.get(position)
        holder.Monthly_checkbox.setText(Data.Checkbox)

        holder.Monthly_checkbox.isChecked = Data.flag != false

        holder.Monthly_checkbox.setSafeOnClickListener {
            flag = true
            selectItem=position
            click.values(true, Data.Checkbox.toString(),position)
            notifyDataSetChanged()
        }


    }
    override fun getItemCount(): Int {
        return 5
    }
}