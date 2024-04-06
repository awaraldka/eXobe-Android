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
import com.exobe.customClicks.CustomeClick4

class dealviewimage_adaptor(
    var imageList: ArrayList<String>,
    var mcontext: Context,
    var click: CustomeClick4,
    var flag: String


) : RecyclerView.Adapter<dealviewimage_adaptor.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        var view: View? = LayoutInflater.from(parent.context).inflate(R.layout.slider_image, null)
        view?.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )!!
        return dealviewimage_adaptor.MyViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: dealviewimage_adaptor.MyViewHolder, position: Int) {
        try {
            if (flag.equals("viewdeals1")) {
                Glide.with(mcontext).load(imageList.get(position)).into(holder.image)
//                Picasso.get().load(imageList[position]).into(holder.image)
            }
            else {
                holder.image.setSafeOnClickListener {
                    click.click4("", "")
                }
            }

        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.img)

    }
}