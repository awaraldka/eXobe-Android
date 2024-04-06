package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.customClicks.DismissListener
import com.exobe.databinding.ListsBinding


class UnitAdaptor(val context: Context, var data:ArrayList<String>, val dismissListener: DismissListener) : RecyclerView.Adapter<UnitAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            var unitData = data[position]
            holder.binding.contentTextView.text = unitData
            holder.binding.click.setSafeOnClickListener {
                dismissListener.dismissListener(unitData)
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
            return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredStateList: ArrayList<String>) {
        data = filteredStateList
        notifyDataSetChanged()
    }


    class ViewHolder (val binding : ListsBinding) : RecyclerView.ViewHolder(binding.root)


}