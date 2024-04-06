package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.customClicks.LikeUnlikeClick
import com.exobe.customClicks.ProductViewListener
import com.exobe.databinding.SeeAllProductListBinding
import com.exobe.entity.response.WishListDocs

class WishListAdapter(
    var context: Context,
    var data: ArrayList<WishListDocs>,
    var productViewListener: ProductViewListener,
    var flag: String,
    var resources: Resources,
    var wishlist: LikeUnlikeClick

):RecyclerView.Adapter<WishListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SeeAllProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val wishlistData = data[position]
            Glide.with(context).load(wishlistData.productId?.thumbnail).placeholder(R.drawable.dummyproduct).into(holder.binding.itemImage)
            holder.binding.itemTitle1.text=wishlistData.productId?.productName?.capitalizeFirstLetter()
            holder.binding.itemValue2.text= "R ${wishlistData.productId?.priceSizeDetails?.get(0)?.price?.let {
                CommonFunctions.currencyFormatter(it.toDouble())
            }}"

            if(wishlistData.isLike == true){
                holder.binding.heart.visibility=View.GONE
                holder.binding.heartfill.visibility=View.VISIBLE
            }

            if(wishlistData.isDealActive) {
                holder.binding.discountLL.isVisible = true
                holder.binding.priceTag.isVisible = true
                holder.binding.offOnProduct.text = "${CommonFunctions.formatPercentage(wishlistData.dealDiscount.toDouble())} Off"
                holder.binding.priceTag.text = "R ${CommonFunctions.currencyFormatter(wishlistData.productId?.priceSizeDetails?.getOrNull(0)!!.price.toDouble())}"
                holder.binding.itemValue2.text =
                    "R ${CommonFunctions.currencyFormatter(wishlistData.dealPrice.toDouble())}"
                holder.binding.priceTag.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                holder.binding.itemValue2.setTextColor(ContextCompat.getColor(context, R.color.black))
                holder.binding.itemValue2.text =
                    "R ${wishlistData.productId?.priceSizeDetails?.getOrNull(0)?.price?.let {
                        CommonFunctions.currencyFormatter(
                            it.toDouble())
                    }}"
            }

            holder.binding.viewProductView.setSafeOnClickListener {
                wishlistData.productId?._id?.let { it1 -> productViewListener.viewProduct(it1, wishlistData.dealId) }
            }


            holder.binding.heartOutline.setSafeOnClickListener {
                wishlist.wishlistClickListener(wishlistData.productId?._id.toString(),position)
            }


        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(val binding : SeeAllProductListBinding):RecyclerView.ViewHolder(binding.root)

}