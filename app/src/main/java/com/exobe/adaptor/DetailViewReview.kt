package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.exobe.R
import com.exobe.entity.response.customer.ReviewResult
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class DetailViewReview(val context: Context, var data: ArrayList<ReviewResult>): RecyclerView.Adapter<DetailViewReview.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.review_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try{
            var userData = data.get(position)
            holder.name.text = "${userData.userId?.firstName } ${userData.userId?.lastName}"
            holder.review.text = userData.comment.toString()
            holder.ratingBar.numStars = userData.ratingCount?.toInt()!!
            holder.ratingBar.setStepSize(0.5.toFloat())
            holder.ratingBar.setMax(5)
            Picasso.get()
                .load("${userData.userId?.profilePic}")
                .into(holder.user_image)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int {
        if (data.size < 4){
        return data.size
    }else{
        return 3
    }
    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        var image: ImageView = itemView.findViewById(R.id.user_image)
        var name: TextView = itemView.findViewById(R.id.user_name)
        var date: TextView = itemView.findViewById(R.id.user_date)
        var review: TextView = itemView.findViewById(R.id.user_review)
        var ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        var user_image: CircleImageView = itemView.findViewById(R.id.user_image)
    }


}