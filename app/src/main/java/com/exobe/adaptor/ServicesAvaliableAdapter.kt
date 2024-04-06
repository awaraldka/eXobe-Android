package com.exobe.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.modelClass.Services_Available
import com.exobe.R
import com.exobe.customClicks.CustomeClick


class ServicesAvaliableAdapter(

    var itemList: ArrayList<Services_Available>,
    var context: Context,
    var customeClick: CustomeClick


) : RecyclerView.Adapter<ServicesAvaliableAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ServicesAvaliableAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.services_avaliable, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServicesAvaliableAdapter.ViewHolder, position: Int) {
        val userData = itemList[position]
        holder.itemImage.setImageResource(userData.name!!)
//        Glide.with(context).load(userData.categoryImage).into(holder.itemImage)
        holder.ServicesAvaliableAdapter_LL.setSafeOnClickListener {
            customeClick.click("")
        }

    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var ServicesAvaliableAdapter_LL: LinearLayout


        init {
            itemImage = itemView.findViewById(R.id.image)
            ServicesAvaliableAdapter_LL = itemView.findViewById(R.id.ServicesAvaliableAdapter_LinearLayout)
        }
    }

}