package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.modelClass.myModel
import com.exobe.R
import com.exobe.customClicks.GetValue

class OperationalSubCategoryRecycler(
    var context: Context?,
    var data: ArrayList<myModel>,
    var getValue: GetValue


): RecyclerView.Adapter<OperationalSubCategoryRecycler.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mInflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = mInflater.inflate(R.layout.chechk_box_textview, parent, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        if(data.get(position).flag == true)
        {
            holder.CountryCheckBox.isChecked=true
        }
        else
        {
            holder.CountryCheckBox.isChecked=false
        }
        holder.CountryCheckBox.text = data[position].country
        holder.CountryCheckBox.setSafeOnClickListener{
            getValue.Addlist(position, true, data[position].country)
            getValue.FilterArrayList(position, true, data[position].country,data[position].countryCode)
//       

        }
    }



    override fun getItemCount(): Int {
        return data.size
    }


    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        var CountryCheckBox = itemView.findViewById<CheckBox>(R.id.countries_checkbox)

    }



    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredList: ArrayList<myModel>) {
        data = filteredList
        notifyDataSetChanged()
    }


}