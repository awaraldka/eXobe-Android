package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.customClicks.selectWholeSalerListener
import com.exobe.databinding.ListsBinding
import com.exobe.entity.response.wholesaler_listDocs


class WholeSalersListPopUpAdaptor(
    var context: Context,
    var wholesalerdata: List<wholesaler_listDocs>,
    var selectWholeSalerListener: selectWholeSalerListener
) :
    RecyclerView.Adapter<WholeSalersListPopUpAdaptor.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.binding.contentTextView.text = "${wholesalerdata[position].firstName} ${wholesalerdata[position].lastName}"

            holder.binding.click.setSafeOnClickListener {
                var fullName = "${wholesalerdata[position].firstName} ${wholesalerdata[position].lastName}"
                selectWholeSalerListener.selectWholeSalerClick(fullName, wholesalerdata[position]._id)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return wholesalerdata.size
    }

    class ViewHolder(val binding : ListsBinding) : RecyclerView.ViewHolder(binding.root)

    fun filterList(filteredList: ArrayList<wholesaler_listDocs>) {
        wholesalerdata = filteredList
        notifyDataSetChanged()
    }

}


