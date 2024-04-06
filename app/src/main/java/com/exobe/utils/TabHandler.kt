package com.exobe.utils

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.exobe.R

object TabHandler {
    fun HandleTab(tabId: Int, activity: FragmentActivity) {
        val homeGreyImageView = activity.findViewById<ImageView>(R.id.home_grey_ImageView)!!
        val homeSelectedImageView = activity.findViewById<ImageView>(R.id.home)!!
        val homeTV = activity.findViewById<TextView>(R.id.TVhome)!!

        val serviceGreyImageView = activity.findViewById<ImageView>(R.id.service_grey_ImageView)!!
        val serviceSelectedImageView = activity.findViewById<ImageView>(R.id.service_red)!!
        val serviceTV = activity.findViewById<TextView>(R.id.TVservices)!!

        val categoryGreyImageView = activity.findViewById<ImageView>(R.id.Category_grey_ImageView)!!
        val categorySelectedImageView = activity.findViewById<ImageView>(R.id.Category_red)!!
        val categoryTV = activity.findViewById<TextView>(R.id.TVcategory)!!

        val wishlistGreyImageView = activity.findViewById<ImageView>(R.id.Wishlist_grey_ImageView)!!
        val wishlistSelectedImageView = activity.findViewById<ImageView>(R.id.Wishlist_red)!!
        val wishlistTV = activity.findViewById<TextView>(R.id.TVwishlist)!!

        val settingsGreyImageView = activity.findViewById<ImageView>(R.id.greySetting_ImageView)!!
        val settingsSelectedImageView =
            activity.findViewById<ImageView>(R.id.red_setting_ImageView)!!
        val settingsTV = activity.findViewById<TextView>(R.id.TVsettings)!!

        val order = activity.findViewById<View>(R.id.order)!!
        val TVorder = activity.findViewById<TextView>(R.id.TVorder)!!
        val orderRed = activity.findViewById<View>(R.id.order_red)!!

        val order1 = activity.findViewById<View>(R.id.order1)!!
        val TVorder1 = activity.findViewById<TextView>(R.id.TVorder1)!!
        val orderRed1 = activity.findViewById<View>(R.id.order_red1)!!

        val product = activity.findViewById<View>(R.id.product)!!
        val productRed = activity.findViewById<View>(R.id.product_red)!!
        val TVproduct = activity.findViewById<TextView>(R.id.TVproduct)

        val title = activity.findViewById<TextView>(R.id.PreLoginTitle_TextView2)!!
        val cart = activity.findViewById<View>(R.id.cart_icon)!!
        val filter = activity.findViewById<View>(R.id.filter_icon)!!
        val back = activity.findViewById<View>(R.id.imageView_back)!!
        val MenuClick = activity.findViewById<View>(R.id.MenuClick)!!
        val DealsImageView = activity.findViewById<View>(R.id.Deals_ImageView)!!
        val greyBellImageView = activity.findViewById<ImageView>(R.id.greyBell_ImageView)!!
        val logoutButton = activity.findViewById<ImageView>(R.id.logoutButton)!!

        homeGreyImageView.visibility = View.GONE
        homeSelectedImageView.visibility = View.VISIBLE
        homeTV.setTextColor(Color.parseColor("#FFFFFF"))

        serviceGreyImageView.visibility = View.VISIBLE
        serviceSelectedImageView.visibility = View.GONE
        serviceTV.setTextColor(Color.parseColor("#FFFFFF"))

        categoryGreyImageView.visibility = View.VISIBLE
        categorySelectedImageView.visibility = View.GONE
        categoryTV.setTextColor(Color.parseColor("#FFFFFF"))

        wishlistGreyImageView.visibility = View.VISIBLE
        wishlistSelectedImageView.visibility = View.GONE
        wishlistTV.setTextColor(Color.parseColor("#FFFFFF"))

        settingsGreyImageView.visibility = View.VISIBLE
        settingsSelectedImageView.visibility = View.GONE
        settingsTV.setTextColor(Color.parseColor("#FFFFFF"))

        order.visibility = View.VISIBLE
        orderRed.visibility = View.GONE
        TVorder.setTextColor(Color.parseColor("#FFFFFF"))

        product.visibility = View.VISIBLE
        productRed.visibility = View.GONE
        TVproduct.setTextColor(Color.parseColor("#FFFFFF"))

        when (tabId) {
            R.id.home_grey -> {
                homeGreyImageView.visibility = View.GONE
                homeSelectedImageView.visibility = View.VISIBLE
                homeTV.setTextColor(Color.parseColor("#FFFFFF"))
                val cartCount = activity.findViewById<TextView>(R.id.cartCount)!!
                cartCount.visibility = View.GONE
                cart.visibility = View.VISIBLE
                filter.visibility = View.GONE
                back.visibility = View.GONE
                MenuClick.visibility = View.VISIBLE
                DealsImageView.visibility = View.VISIBLE
                greyBellImageView.visibility = View.VISIBLE
                title.text = "Home"

            }

            R.id.ll_service_tab -> {
                serviceGreyImageView.visibility = View.GONE
                serviceSelectedImageView.visibility = View.VISIBLE

                homeGreyImageView.visibility = View.VISIBLE
                homeSelectedImageView.visibility = View.GONE

                serviceTV.setTextColor(Color.parseColor("#FFFFFF"))
            }

            R.id.ll_category_tab -> {
                categoryGreyImageView.visibility = View.GONE
                categorySelectedImageView.visibility = View.VISIBLE

                homeGreyImageView.visibility = View.VISIBLE
                homeSelectedImageView.visibility = View.GONE

                categoryTV.setTextColor(Color.parseColor("#FFFFFF"))


            }

            R.id.Wishlist_grey -> {
                wishlistGreyImageView.visibility = View.GONE
                wishlistSelectedImageView.visibility = View.VISIBLE

                homeGreyImageView.visibility = View.VISIBLE
                homeSelectedImageView.visibility = View.GONE
                wishlistTV.setTextColor(Color.parseColor("#FFFFFF"))
                val cartCount = activity.findViewById<TextView>(R.id.cartCount)!!
                cartCount.visibility = View.GONE
                cart.visibility = View.VISIBLE
                filter.visibility = View.GONE
                back.visibility = View.GONE

                MenuClick.visibility = View.GONE
                DealsImageView.visibility = View.VISIBLE
                greyBellImageView.visibility = View.VISIBLE
                title.text = "Wishlist"

            }

            R.id.Setting_LinearLayout -> {
                settingsGreyImageView.visibility = View.GONE
                settingsSelectedImageView.visibility = View.VISIBLE

                homeGreyImageView.visibility = View.VISIBLE
                homeSelectedImageView.visibility = View.GONE

                settingsTV.setTextColor(Color.parseColor("#FFFFFF"))

                val cartCount = activity.findViewById<TextView>(R.id.cartCount)!!
                cartCount.visibility = View.GONE
                cart.visibility = View.GONE
                filter.visibility = View.GONE
                back.visibility = View.GONE

                MenuClick.visibility = View.GONE
                DealsImageView.visibility = View.GONE
                greyBellImageView.visibility = View.GONE
                logoutButton.visibility = View.GONE
                title.text = "Settings"
            }

            R.id.ll_order_tab -> {
                order.visibility = View.GONE
                orderRed.visibility = View.VISIBLE

                homeGreyImageView.visibility = View.VISIBLE
                homeSelectedImageView.visibility = View.GONE

                TVorder.setTextColor(Color.parseColor("#FFFFFF"))

                val cartCount = activity.findViewById<TextView>(R.id.cartCount)!!
                cartCount.visibility = View.GONE
                cart.visibility = View.GONE
                filter.visibility = View.GONE
                back.visibility = View.GONE

                MenuClick.visibility = View.GONE
                DealsImageView.visibility = View.GONE
                greyBellImageView.visibility = View.GONE
                logoutButton.visibility = View.GONE
                title.text = "Order Management"
            }

            R.id.ll_order_tab1 -> {
                order1.visibility = View.GONE
                orderRed1.visibility = View.VISIBLE

                homeGreyImageView.visibility = View.VISIBLE
                homeSelectedImageView.visibility = View.GONE

                TVorder1.setTextColor(Color.parseColor("#FFFFFF"))

                val cartCount = activity.findViewById<TextView>(R.id.cartCount)!!
                cartCount.visibility = View.GONE
                cart.visibility = View.GONE
                filter.visibility = View.GONE
                back.visibility = View.GONE

                MenuClick.visibility = View.GONE
                DealsImageView.visibility = View.GONE
                greyBellImageView.visibility = View.GONE
                logoutButton.visibility = View.GONE
                title.text = "Payment History"
            }

            R.id.ll_product_tab -> {
                product.visibility = View.GONE
                productRed.visibility = View.VISIBLE

                homeGreyImageView.visibility = View.VISIBLE
                homeSelectedImageView.visibility = View.GONE

                TVproduct.setTextColor(Color.parseColor("#FFFFFF"))

                val cartCount = activity.findViewById<TextView>(R.id.cartCount)!!
                cartCount.visibility = View.GONE
                cart.visibility = View.GONE
                filter.visibility = View.GONE
                back.visibility = View.GONE
                MenuClick.visibility = View.GONE
                DealsImageView.visibility = View.GONE
                greyBellImageView.visibility = View.GONE
                logoutButton.visibility = View.GONE
                title.text = "Product Management"
            }
        }
    }

}