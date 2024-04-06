package com.exobe.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.customClicks.CategoryClick
import com.exobe.entity.response.Docs

class HomeCategory(
    var context: Context,
    var data: ArrayList<Docs>,
    var customeClick: CategoryClick

) : RecyclerView.Adapter<HomeCategory.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mInflater = LayoutInflater.from(context)
        val view: View = mInflater.inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            val Data = data[position]
//            Glide.with(context).load(Data.thumbnail).placeholder(R.drawable.dummyproduct).into(holder.ImageCategory)
//            private val shimmer = Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
//                .setDuration(1800) // how long the shimmering animation takes to do one full sweep
//                .setBaseAlpha(0.7f) //the alpha of the underlying children
//                .setHighlightAlpha(0.6f) // the shimmer alpha amount
//                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
//                .setAutoStart(true)
//                .build()
//
//// This is the placeholder for the imageView
//            val shimmerDrawable = ShimmerDrawable().apply {
//                setShimmer(shimmer)
//            }
//
//
//            Glide.with(image.context).load(url)
//                .placeholder(shimmerDrawable)
//                .error(context.getDrawable(R.drawable.placeholder))
//                .into(image)
            Glide.with(context).load(Data.categoryImage).placeholder(R.drawable.dummyproduct).into(holder.ImageCategory)
            holder.Description.text = Data.categoryName!!.capitalizeFirstLetter()

            holder.ImageCategory.setSafeOnClickListener {
                customeClick.categoryClick(Data._id, Data.categoryName)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    override fun getItemCount(): Int {

        return if (data.size > 10) {
            10
        } else {
            data.size
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ImageCategory: ImageView
        var Description: TextView

        init {
            ImageCategory = itemView.findViewById(R.id.ImageCategory)
            Description = itemView.findViewById(R.id.Description)
        }
    }

}