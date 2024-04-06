package com.exobe.adaptor

import android.annotation.SuppressLint
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
import com.exobe.entity.response.StateList_Result


class StateListPopUp(
    var context: Context,
    var data: ArrayList<StateList_Result>,
    var flag: String,
    var click: popupItemClickListnerCountry
) :
    RecyclerView.Adapter<StateListPopUp.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(R.layout.lists, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {

            var ListData = data.get(position)


            if (flag == "State") {
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

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredStateList: ArrayList<StateList_Result>) {
        data = filteredStateList
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


