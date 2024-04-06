package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.exobe.R
import com.exobe.customClicks.BannerClickListener
import com.exobe.customClicks.CustomeClick4
import com.exobe.entity.response.HomeBannerDoc

class ImageSliderAdaptorbanner(
    var data: ArrayList<HomeBannerDoc>,
    var context: Context,
    var click: CustomeClick4,
    var flag: String,
    var bannerClickListener: BannerClickListener


) : RecyclerView.Adapter<ImageSliderAdaptorbanner.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        var view: View? = LayoutInflater.from(parent.context).inflate(R.layout.dealscardview, null)
        view?.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )!!
        return ImageSliderAdaptorbanner.MyViewHolder(view!!)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ImageSliderAdaptorbanner.MyViewHolder, position: Int) {

        try {
            Glide.with(context).load(data[position].bannerImage).placeholder(R.drawable.dummyproduct).into(holder.image)
            holder.image.setOnClickListener {
                bannerClickListener.bannerClick(data[position].description)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.image)

    }
}