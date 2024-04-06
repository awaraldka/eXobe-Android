package com.exobe.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.utils.SavedPrefManager
import com.exobe.customClicks.ProductViewListener
import com.exobe.customClicks.wishlistcustomclick
import com.exobe.customClicks.wishlistcustomclick2
import com.exobe.entity.response.product.GuestProductDocs

class CategoryItemAdapter(
    val context: Context,
    var data: ArrayList<GuestProductDocs>,
    var productViewListener: ProductViewListener,
    var wishList: wishlistcustomclick,
    var wishList2: wishlistcustomclick2
) : RecyclerView.Adapter<CategoryItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.see_all_product_list, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            var loginflag = SavedPrefManager.getStringPreferences(context, SavedPrefManager.isLogin)

            val orderData = data[position]
            if (orderData.thumbnail == "") {
                Glide.with(context).load(R.drawable.dummyproduct)
                    .into(holder.ImageCategory)
            } else {
                Glide.with(context)
                    .load(orderData.thumbnail)
                    .thumbnail(0.25f) // Load a thumbnail initially (fraction of the full image)
                    .placeholder(R.drawable.dummyproduct)
                    .error(R.drawable.dummyproduct)
                    .into(holder.ImageCategory)
            }

            holder.itemTitle1.text = orderData.productName.capitalizeFirstLetter()


            if(orderData.isDealActive == true) {
                holder.discountLL.isVisible = true
                holder.priceTag.isVisible = true
                holder.offOnProduct.text = "${CommonFunctions.formatPercentage(orderData.dealDiscount.toDouble())} Off"
                holder.priceTag.text = "R ${CommonFunctions.currencyFormatter(orderData.priceSizeDetails[0].price.toDouble())}"
                holder.itemValue2.text =
                    "R ${CommonFunctions.currencyFormatter(orderData.dealPrice.toDouble())}"
                holder.priceTag.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                holder.itemValue2.setTextColor(ContextCompat.getColor(context, R.color.black))
                holder.itemValue2.text =
                    "R ${CommonFunctions.currencyFormatter(orderData.priceSizeDetails[0].price.toDouble())}"
            }

            holder.CategoryCV.setSafeOnClickListener {
                productViewListener.viewProduct(orderData._id, orderData.dealId)
            }

            if (orderData.isLike == true) {
                holder.heart.visibility = View.GONE
                holder.heartFill.visibility = View.VISIBLE
            } else {
                holder.heartFill.visibility = View.GONE
                holder.heart.visibility = View.VISIBLE
            }

            holder.heartFill.setSafeOnClickListener {
                if (loginflag.equals("true")) {
                    wishList.wishlist(orderData._id)
                    holder.heartFill.visibility = View.GONE
                    holder.heart.visibility = View.VISIBLE
                } else {
                    wishList.wishlist(orderData._id)

                }
            }

            holder.heart.setSafeOnClickListener {
                if (loginflag.equals("true")) {
                    wishList.wishlist(orderData._id)

                    holder.heart.visibility = View.GONE
                    holder.heartFill.visibility = View.VISIBLE
                } else {
                    wishList.wishlist(orderData._id)
                }
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        var ImageCategory: ImageView = itemView.findViewById(R.id.item_image)
        var itemTitle1: TextView = itemView.findViewById(R.id.itemTitle1)
        var heart: ImageView = itemView.findViewById(R.id.heart)
        var heartFill: ImageView = itemView.findViewById(R.id.heartfill)
        var CategoryCV: RelativeLayout = itemView.findViewById(R.id.CategoryCV)
        var priceTag: TextView = itemView.findViewById(R.id.priceTag)
        var itemValue2: TextView = itemView.findViewById(R.id.itemValue2)
        var discountLL: LinearLayout = itemView.findViewById(R.id.discountLL)
        var offOnProduct: TextView = itemView.findViewById(R.id.offOnProduct)
    }

}
