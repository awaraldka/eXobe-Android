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
import com.exobe.entity.response.CountryListResult

class CountryListPopUp(
    var context: Context,
    var data: ArrayList<CountryListResult>,
    var flag: String,
    var click: popupItemClickListnerCountry
) :
    RecyclerView.Adapter<CountryListPopUp.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(R.layout.lists, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            var ListData = data.get(position)


            if (flag == "Country") {
                holder.Names.text = ListData.name
                holder.click_ll.setSafeOnClickListener {
                    ListData.name?.let { it1 -> click.getCountry(it1, flag, ListData.isoCode) }
                }
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }

    fun filterList(filteredList: ArrayList<CountryListResult>) {
        data = filteredList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var Names: TextView
        var click_ll: LinearLayout


        init {
            Names = itemView.findViewById(R.id.content_textView)
            click_ll = itemView.findViewById(R.id.click_ll)
        }
    }
}


