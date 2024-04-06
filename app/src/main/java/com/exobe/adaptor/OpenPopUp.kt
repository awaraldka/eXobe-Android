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
import com.exobe.customClicks.ServiceNameListener
import com.exobe.customClicks.popupItemClickListnerDeals
import com.exobe.entity.response.customer.AddDealsCategoryResult


class OpenPopUp(
    var context: Context,
    var data:ArrayList<AddDealsCategoryResult>,
    var flag: String,
    var click: popupItemClickListnerDeals,
    var serviceNameListener : ServiceNameListener
) :
    RecyclerView.Adapter<OpenPopUp.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(R.layout.lists, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        try {
            var ListData = data.get(position)


            if (flag == "Category") {
                holder.Names.text = ListData.categoryName
                holder.click_ll.setSafeOnClickListener {
                    ListData.categoryName.let { it1 -> click.getDatas(it1, flag, ListData._id) }
                }

            } else if (flag == "SubCategory") {
                holder.Names.text = ListData.subCategoryName
                holder.click_ll.setSafeOnClickListener {
                    ListData.subCategoryName.let { it1 -> click.getDatas(it1, flag, ListData._id) }
                }

            } else if (flag == "SelectService") {
                holder.Names.text = ListData.serviceName
                holder.click_ll.setSafeOnClickListener {
                    ListData.serviceName.let { it1 -> serviceNameListener.serviceNameWithPrice(it1, flag, ListData._id, ListData.price.toDouble().toString()) }
                }
            }
//        } catch (e : Exception) {
//            e.printStackTrace()
//        }
    }

    override fun getItemCount(): Int {
            return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredStateList: ArrayList<AddDealsCategoryResult>) {
        data = filteredStateList
        notifyDataSetChanged()
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