package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.customClicks.DeletePackageListener
import com.exobe.entity.response.PriceSizeDetails


class ProductPackageAdaptor(
    val context: Context,
    val data: ArrayList<PriceSizeDetails>,
    val deletePackageListener: DeletePackageListener,
    val flag: String
) :
    RecyclerView.Adapter<ProductPackageAdaptor.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(R.layout.package_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
//            if(flag == "Edit Product") {
//                holder.edit.visibility = View.VISIBLE
//            }
            var ListData = data.get(position)
            holder.value_tv.text = ListData.value
            holder.unit_tv.text = ListData.unit
            holder.amount_tv.text = CommonFunctions.currencyFormatter(ListData.price.toDouble())
            holder.qty_tv.text = ListData.quantity.toString()
            holder.delete.setSafeOnClickListener {
                deletePackageListener.isDelete(ListData.value, ListData.unit, ListData.price, ListData.quantity)
            }

            holder.edit.setSafeOnClickListener {
                deletePackageListener.isEdit(ListData.value, ListData.unit, ListData.price, ListData.quantity, ListData.id)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var value_tv: TextView
        var unit_tv: TextView
        var amount_tv: TextView
        var qty_tv: TextView
        var delete: ImageView
        var edit: ImageView

        init {
            value_tv = itemView.findViewById(R.id.value_tv)
            unit_tv = itemView.findViewById(R.id.unit_tv)
            amount_tv = itemView.findViewById(R.id.amount_tv)
            qty_tv = itemView.findViewById(R.id.qty_tv)
            delete = itemView.findViewById(R.id.delete)
            edit = itemView.findViewById(R.id.edit)

        }
    }


}