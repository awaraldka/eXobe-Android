package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.customClicks.ViewImages

class ImageSliderAdaptor(
    var imageList: ArrayList<String>,
    var context: Context,
    var flag: String,
    var click: ViewImages


) : RecyclerView.Adapter<ImageSliderAdaptor.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val view : View? =  LayoutInflater.from(parent.context).inflate(R.layout.slider_image, null)
        view?.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return MyViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (flag == "viewDeals"){
            Glide.with(context).load(imageList[position]).into(holder.image)
        }else{

            holder.image.setSafeOnClickListener {
            }
        }

        holder.image.setSafeOnClickListener {
            click.viewImage(imageList)
        }

    }

    override fun getItemCount(): Int {
        return imageList.size
    }


    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.img)

    }



}