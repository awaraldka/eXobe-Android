package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.customClicks.popupItemClickListnerCountry
import com.exobe.entity.response.CityListResult


class CityListPopUp(
    var context: Context,
    var data: ArrayList<CityListResult>,
    var flag: String,
    var click: popupItemClickListnerCountry
) :
    RecyclerView.Adapter<CityListPopUp.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(R.layout.lists, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            data[position].apply {
                if (flag == "City") {
                    holder.Names.text = name
                    holder.click_ll.setSafeOnClickListener {
                        name.let { it1 -> click.getCountry(it1, flag, name) }
                    }
                }
            }



        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var Names: TextView
        var click_ll: LinearLayout


        init {
            Names = itemView.findViewById(R.id.content_textView)
            click_ll = itemView.findViewById(R.id.click_ll)
        }
    }

    fun filterList(filteredList: ArrayList<CityListResult>) {
        data = filteredList
        notifyDataSetChanged()

    }

}


