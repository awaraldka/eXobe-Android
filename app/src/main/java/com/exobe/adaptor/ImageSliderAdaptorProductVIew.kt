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

class ImageSliderAdaptorProductVIew(
    var imageList: ArrayList<String>,
    var context: Context,
    var click: CustomeClick4,
    var flag: String

) : RecyclerView.Adapter<ImageSliderAdaptorProductVIew.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        var view : View? =  LayoutInflater.from(parent.context).inflate(R.layout.slider_image, null)
        view?.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)!!
        return ImageSliderAdaptorProductVIew.MyViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ImageSliderAdaptorProductVIew.MyViewHolder, position: Int) {
        Glide.with(context).load(imageList.get(position)).into(holder.image)
        if (flag.equals("viewdeals")){

        }else{

            holder.image.setSafeOnClickListener {
//                click.click4(dealData._id, dealData.productId[0]._id)
            }
        }


    }

    override fun getItemCount(): Int {
        return imageList.size
    }


    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.img)

    }
}