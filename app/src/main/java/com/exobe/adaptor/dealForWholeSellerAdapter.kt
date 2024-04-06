package com.exobe.adaptor

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.modelClass.DealForCustomerModelClass
import com.exobe.R
import com.exobe.customClicks.CustomeClick

class dealForWholeSellerAdapter(
    val context: Context,
    val itemList: ArrayList<DealForCustomerModelClass>,
    var customeClick: CustomeClick
) : RecyclerView.Adapter<dealForWholeSellerAdapter.ViewHolder>() {

    var flag1 = false
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): dealForWholeSellerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.deal_for_customer_modallayout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: dealForWholeSellerAdapter.ViewHolder, position: Int) {
        val userData = itemList[position]
        holder.itemValue1.text = userData.itemValue1
        holder.itemValue2.text = userData.itemValue2
        holder.date.text = userData.date
        holder.itemImage.setImageResource(userData.images)
        holder.cardView.setSafeOnClickListener {
        }
        holder.viewButton.setSafeOnClickListener {
            customeClick.click("")


        }

        holder.itemValue1.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

//        holder.imageView3.setSafeOnClickListener {
//            holder.heartFill.visibility = View.VISIBLE
//            holder.heartOutline.visibility = View.GONE
//        }
//
//        holder.heartFill.setSafeOnClickListener {
//            holder.heartFill.visibility = View.GONE
//            holder.heartOutline.visibility = View.VISIBLE
//        }

        holder.imageView3.setSafeOnClickListener {

            if (flag1 == true) {
                holder.heartFill.visibility = View.VISIBLE
                holder.heartOutline.visibility = View.GONE
                flag1 = false
            } else {
                holder.heartFill.visibility = View.GONE
                holder.heartOutline.visibility = View.VISIBLE
                flag1 = true
            }

        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemValue1: TextView
        var itemValue2: TextView
        var date: TextView
        var cardView: CardView
        var heartFill: ImageView
        var heartOutline: ImageView
        var imageView3: ImageView
        var viewButton: LinearLayout


        init {
            itemImage = itemView.findViewById(R.id.item_image)
            itemValue1 = itemView.findViewById(R.id.itemValue1)
            itemValue2 = itemView.findViewById(R.id.itemValue2)
            date = itemView.findViewById(R.id.date)
            cardView = itemView.findViewById(R.id.dealForCustomerCardView)
            heartFill = itemView.findViewById(R.id.heartFill)
            heartOutline = itemView.findViewById(R.id.heartOutline)
            imageView3 = itemView.findViewById(R.id.imageView3)
            viewButton = itemView.findViewById(R.id.viewButton)


        }
    }


}