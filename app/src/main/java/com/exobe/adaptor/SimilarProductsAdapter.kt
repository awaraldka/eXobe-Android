package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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
import com.exobe.entity.response.product.GuestProductDocs


class SimilarProductsAdapter(
    var context: Context,
    var data: ArrayList<GuestProductDocs>,
    var productViewListener: ProductViewListener,
    var wishlist: wishlistcustomclick
) : RecyclerView.Adapter<SimilarProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.see_all_product_list, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            var userData = data[position]
            Glide.with(context).load(userData.productImage.getOrNull(0)).placeholder(R.drawable.dummyproduct).into(holder.ImageCategory)
            holder.itemTitle1.text=userData.productName.capitalizeFirstLetter()

            if(userData.isDealActive == true) {
                holder.discountLL.isVisible = true
                holder.priceTag.isVisible = true
                holder.offOnProduct.text = "${CommonFunctions.formatPercentage(userData.dealDiscount.toDouble())} Off"
                holder.priceTag.text = "R ${CommonFunctions.currencyFormatter(userData.priceSizeDetails[0].price.toDouble())}"
                holder.price.text = "R ${CommonFunctions.currencyFormatter(userData.dealPrice.toDouble())}"
                holder.priceTag.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                holder.price.setTextColor(ContextCompat.getColor(context, R.color.black))
                holder.price.text =
                    "R ${CommonFunctions.currencyFormatter(userData.priceSizeDetails[0].price.toDouble())}"
            }

            if (userData.isLike == true) {
                holder.heart.visibility = View.GONE
                holder.heartfill.visibility = View.VISIBLE
            } else {
                holder.heartfill.visibility = View.GONE
                holder.heart.visibility = View.VISIBLE
            }

            holder.click.setSafeOnClickListener {

                productViewListener.viewProduct(userData._id, userData.dealId)
            }

            holder.heart.setSafeOnClickListener {

                wishlist.wishlist(userData._id)
                if (SavedPrefManager.getStringPreferences(context, SavedPrefManager.isLogin)
                        .equals("true")
                ) {
                    holder.heart.visibility = View.GONE
                    holder.heartfill.visibility = View.VISIBLE
                }
            }

            holder.heartfill.setSafeOnClickListener {
                wishlist.wishlist(userData._id)
                if (SavedPrefManager.getStringPreferences(context, SavedPrefManager.isLogin)
                        .equals("true")
                ) {
                    holder.heartfill.visibility = View.GONE
                    holder.heart.visibility = View.VISIBLE
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ImageCategory:ImageView =itemView.findViewById(R.id.item_image)
        var itemTitle1:TextView =itemView.findViewById(R.id.itemTitle1)
        var price:TextView =itemView.findViewById(R.id.itemValue2)
        var heartOutline: ConstraintLayout =itemView.findViewById(R.id.heartOutline)
        var heart:ImageView =itemView.findViewById(R.id.heart)
        var heartfill:ImageView =itemView.findViewById(R.id.heartfill)
        var click : LinearLayout =  itemView.findViewById(R.id.viewProductView)
        var discountLL: LinearLayout = itemView.findViewById(R.id.discountLL)
        var offOnProduct: TextView = itemView.findViewById(R.id.offOnProduct)
        var priceTag: TextView = itemView.findViewById(R.id.priceTag)
    }
}

