package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.databinding.ZomSliderImageBinding

class ZoomImageSliderAdaptor(
    var imageList: ArrayList<String>,
    var context: Context,
    var flag: String


) : RecyclerView.Adapter<ZoomImageSliderAdaptor.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val binding = ZomSliderImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (flag == "viewdeals") {
            Glide.with(context).load(imageList[position]).into(holder.binding.img)
        } else {
            holder.binding.img.setSafeOnClickListener {
            }
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class MyViewHolder(val binding: ZomSliderImageBinding) : RecyclerView.ViewHolder(binding.root)

}