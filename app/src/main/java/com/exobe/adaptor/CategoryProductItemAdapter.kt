package com.exobe.Adapter

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

class CategoryProductItemAdapter(
    val context: Context,
    var data: List<GuestProductDocs>,
    var productViewListener: ProductViewListener,
    var wishlist: wishlistcustomclick,
    var wishList2: wishlistcustomclick2
) : RecyclerView.Adapter<CategoryProductItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.see_all_product_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       try {
           var loginflag = SavedPrefManager.getStringPreferences(context, SavedPrefManager.isLogin)
           val orderData = data[position]

           Glide.with(context).load(orderData.thumbnail).placeholder(R.drawable.dummyproduct)
               .into(holder.ImageCategory)

           holder.itemTitle1.text = orderData.productName.capitalizeFirstLetter()
           var orderPrice = CommonFunctions.currencyFormatter(orderData.priceSizeDetails[0].price.toDouble())

           holder.Price.text = "R $orderPrice"

           if(orderData.isDealActive == true) {
               holder.discountLL.isVisible = true
               holder.priceTag.isVisible = true
               holder.offOnProduct.text = "${CommonFunctions.formatPercentage(orderData.dealDiscount.toDouble())} Off"
               holder.priceTag.text = "R ${CommonFunctions.currencyFormatter(orderData.priceSizeDetails[0].price.toDouble())}"
               holder.Price.text =
                   "R ${CommonFunctions.currencyFormatter(orderData.dealPrice.toDouble())}"
               holder.priceTag.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
           } else {
               holder.Price.setTextColor(ContextCompat.getColor(context, R.color.black))
               holder.Price.text =
                   "R ${CommonFunctions.currencyFormatter(orderData.priceSizeDetails[0].price.toDouble())}"
           }
           holder.CategoryCV.setSafeOnClickListener {
               productViewListener.viewProduct(orderData._id, orderData.dealId)

           }
           if (loginflag.equals("true")) {
               if (orderData.isLike == true) {
                   holder.heartOutline.visibility = View.GONE
                   holder.red_heartFill.visibility = View.VISIBLE
               } else {
                   holder.red_heartFill.visibility = View.GONE
                   holder.heartOutline.visibility = View.VISIBLE
               }
           } else {
               holder.heartOutline.visibility = View.VISIBLE
           }



           holder.red_heartFill.setSafeOnClickListener {
               if (loginflag.equals("true")) {
                   wishlist.wishlist(orderData._id)
                   holder.red_heartFill.visibility = View.GONE
                   holder.heartOutline.visibility = View.VISIBLE
               } else {
                   wishlist.wishlist(orderData._id)

               }

           }

           holder.heartOutline.setSafeOnClickListener {
               if (loginflag.equals("true")) {
                   wishlist.wishlist(orderData._id)
                   holder.heartOutline.visibility = View.GONE
                   holder.red_heartFill.visibility = View.VISIBLE
               } else {
                   wishlist.wishlist(orderData._id)
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
        var Price: TextView = itemView.findViewById(R.id.itemValue2)
        var heartOutline: ImageView = itemView.findViewById(R.id.heart)
        var red_heartFill: ImageView = itemView.findViewById(R.id.heartfill)
        var CategoryCV: RelativeLayout = itemView.findViewById(R.id.CategoryCV)
//            var priceTag : TextView =  itemView.findViewById(R.id.priceTag)

        var discountLL: LinearLayout = itemView.findViewById(R.id.discountLL)
        var offOnProduct: TextView = itemView.findViewById(R.id.offOnProduct)
        var priceTag: TextView = itemView.findViewById(R.id.priceTag)


    }


}