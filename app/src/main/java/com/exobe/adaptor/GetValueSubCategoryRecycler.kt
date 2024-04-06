package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.modelClass.myModel
import com.exobe.R
import com.exobe.customClicks.GetValue

class GetValueSubCategoryRecycler(
    var context: Context?,
    var data: ArrayList<myModel>,
    var getValue: GetValue


): RecyclerView.Adapter<GetValueSubCategoryRecycler.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mInflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = mInflater.inflate(R.layout.textview_operational, parent, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {

        try {
            if (data[position].flag == true) {
                holder.TextOperational.text = data[position].country
            }

            holder.Delete.setSafeOnClickListener {
//            Toast.makeText(context, " delete", Toast.LENGTH_SHORT ).show()

//                data.removeAt(position)
                getValue.Names(position, false, data[position].country)

            }
            notifyDataSetChanged()
        } catch (e: Exception){
            e.printStackTrace()
        }


    }



    override fun getItemCount(): Int {
        return data.size
    }


    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        var TextOperational = itemView.findViewById<TextView>(R.id.TextOperational)
        var Delete = itemView.findViewById<ImageView>(R.id.delete)

    }





}