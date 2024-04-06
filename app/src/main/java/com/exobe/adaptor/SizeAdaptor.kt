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
import com.exobe.entity.response.PriceSizeDetails


class SizeAdaptor(
    var context: Context,
    var data:ArrayList<PriceSizeDetails>,
    var dismissSizeListener: DismissSizeListener
) :
    RecyclerView.Adapter<SizeAdaptor.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(R.layout.lists, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            var ListData = data[position]
            holder.Names.text = "${ListData.value} ${ListData.unit.lowercase().takeIf { it != "other" }?:""}"


            val productUnit = ListData.unit.ifBlank { "" }

            holder.click_ll.setSafeOnClickListener {
                dismissSizeListener.dismissListener(
                    ListData.value,
                    ListData.id,
                    ListData.price,
                    productUnit,
                    ListData.value,
                    0,
                    ListData.quantity
                )
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
//            out_of_stock = itemView.findViewById(R.id.out_of_stock)
            click_ll = itemView.findViewById(R.id.click_ll)

        }
    }




}