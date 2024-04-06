package com.exobe.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.*
import com.exobe.customClicks.DealImageRemoveListener

class DealImageCollectionAdaptor(var context: Context, var data: ArrayList<String>, var dealImageRemoveListener: DealImageRemoveListener) :
    RecyclerView.Adapter<DealImageCollectionAdaptor.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var image = view.findViewById<ImageView>(R.id.image)
        var deleteImage = view.findViewById<ImageView>(R.id.delete_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.deal_images_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            var imageData = data[position]

            Glide.with(context).load(imageData).into(holder.image)

            holder.deleteImage.setSafeOnClickListener {
                dealImageRemoveListener.deleteImage(position)
            }

        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}