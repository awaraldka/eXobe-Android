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
import com.exobe.customClicks.popupItemClickListnerDeals
import com.exobe.entity.response.CategoryList_docs


class PopupforCategory(
    var context: Context,
    var data1: ArrayList<CategoryList_docs>,
    var flag: String,
    var click: popupItemClickListnerDeals
) :
    RecyclerView.Adapter<PopupforCategory.ViewHolder>() {
    var Categoryid:String = " "


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(R.layout.lists, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            var CategoryDate = data1.get(position)


            if (flag == "CategoryList") {
                holder.Names.text = CategoryDate.categoryName
                Categoryid = CategoryDate._id
                holder.click_ll.setSafeOnClickListener {
                    CategoryDate._id?.let { it1 -> click.getDatas(it1, flag, Categoryid) }
                }
            }
        }catch (e : Exception) {
            e.printStackTrace()
        }
    }
    override fun getItemCount(): Int {
        return data1.size
    }

    fun filterList(filteredList: ArrayList<CategoryList_docs>) {
        data1 = filteredList
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


