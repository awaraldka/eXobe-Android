package com.exobe.Adapter

import android.annotation.SuppressLint
import android.content.Context
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
import com.exobe.customClicks.ProductViewListener
import com.exobe.databinding.ProductListBinding
import com.exobe.entity.response.product.GuestProductDocs

class ProductAdpter(
    var context: Context,
    var data: ArrayList<GuestProductDocs>,
    var productViewListener: ProductViewListener,
    var flag: String
    ) : RecyclerView.Adapter<ProductAdpter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val itemData = data[position]
            holder.binding.priceTag.text = ""
            holder.binding.itemValue2.text = ""
            holder.binding.heartOutline.visibility = View.GONE
            Glide.with(context).load(itemData.thumbnail).thumbnail(0.25f)
                .placeholder(R.drawable.dummyproduct).into(holder.binding.itemImage)
            holder.binding.itemTitle1.text = itemData.productName.capitalizeFirstLetter()


            if (itemData.isDealActive == true) {
                holder.binding.discountLL.isVisible = true
                holder.binding.priceTag.isVisible = true
                holder.binding.dealDiscountText.text = "Deal Discount:"

                holder.binding.offOnProduct.text = "${CommonFunctions.formatPercentage(itemData.dealDiscount.toDouble())} Off"
                holder.binding.priceTag.text = "R ${CommonFunctions.currencyFormatter(itemData.priceSizeDetails[0].price.toDouble())}"
                holder.binding.itemValue2.text = "R ${CommonFunctions.currencyFormatter(itemData.dealPrice.toDouble())}"
                holder.binding.priceTag.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                holder.binding.discountLL.isVisible = false
                holder.binding.priceTag.isVisible = false
                holder.binding.itemValue2.setTextColor(ContextCompat.getColor(context, R.color.black))
                holder.binding.itemValue2.text =
                    "R ${CommonFunctions.currencyFormatter(itemData.priceSizeDetails[0].price.toDouble())}"

            }

            if (flag == "wishlist") {
                holder.binding.heart.visibility = View.GONE
                holder.binding.heartfill.visibility = View.VISIBLE
            } else {
                holder.binding.heart.visibility = View.VISIBLE
                holder.binding.heartfill.visibility = View.GONE
            }

            holder.binding.viewProductView.setSafeOnClickListener {
                productViewListener.viewProduct(itemData._id, itemData.dealId)
            }

            holder.binding.heart.setSafeOnClickListener {
                holder.binding.heart.visibility = View.GONE
                holder.binding.heartfill.visibility = View.VISIBLE
            }

            holder.binding.heartfill.setSafeOnClickListener {
                holder.binding.heartfill.visibility = View.GONE
                holder.binding.heart.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return data.size

    }


    class ViewHolder(val binding: ProductListBinding) : RecyclerView.ViewHolder(binding.root)
}