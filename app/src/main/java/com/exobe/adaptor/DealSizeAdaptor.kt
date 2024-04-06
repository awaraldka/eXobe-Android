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
import com.exobe.customClicks.*
import com.exobe.entity.response.DealDetails


class DealSizeAdaptor(
    var context: Context,
    var data:ArrayList<DealDetails>,
    var dismissSizeListener: DismissSizeListener
) :
    RecyclerView.Adapter<DealSizeAdaptor.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(R.layout.lists, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            var ListData = data[position]
            holder.Names.text = "${ListData.value} ${ListData.unit!!.lowercase().takeIf { it != "other" }?:""}"
//            holder.Names.text = "${ListData.value}"
            holder.click_ll.setSafeOnClickListener {
                dismissSizeListener.dismissListener(
                    holder.Names.text.toString(),
                    ListData.id!!,
                    ListData.price!!,
                    ListData.unit!!,
                    ListData.value!!,
                    ListData.dealPrice,
                    ListData.quantity
                )
            }
//            var qty=Integer.parseInt(ListData.quantity)
//            if(qty > 0) {
//                holder.click_ll.setSafeOnClickListener {
//                    dismissSizeListener.dismissListener(
//                        holder.Names.text.toString(),
//                        ListData.id!!,
//                        ListData.price!!,
//                        ListData.unit!!,
//                        ListData.value!!,
//                        ListData.dealPrice,
//                        ListData.quantity
//                    )
//                }
//            } else {
//                holder.out_of_stock.visibility = View.VISIBLE
////                holder.Names.text = "${ListData.value}-${ListData.unit}"
//                holder.Names.text = "${ListData.value}"
//            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
            return data.size
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var Names: TextView
//        var out_of_stock: TextView
        var click_ll: LinearLayout
        init {
            Names = itemView.findViewById(R.id.content_textView)
            click_ll = itemView.findViewById(R.id.click_ll)
//            out_of_stock = itemView.findViewById(R.id.out_of_stock)

        }
    }




}