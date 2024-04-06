package com.exobe.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.viewpager2.widget.ViewPager2
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.ImageSliderAdaptor
import com.exobe.R
import com.exobe.customClicks.CustomeClick4
import com.exobe.customClicks.ViewImages
import me.relex.circleindicator.CircleIndicator3

class AddproductSearch_details : Fragment(), CustomeClick4 , ViewImages{
    lateinit var multi_image: ViewPager2
    lateinit var imageAdaptor:ImageSliderAdaptor
    lateinit var indicator3: CircleIndicator3
    private val sliderHandler: Handler = Handler()
    var images = ArrayList<Int>()
    lateinit var viewAddDealsBack:ImageView
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var addDealsField: LinearLayout
    lateinit var save: Button
    lateinit var productCategory_spinner: Spinner
    lateinit var cancel_button:Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_addproduct_search_details, container, false)
        setToolbar()
        indicator3 = view.findViewById(R.id.indicator)
        multi_image = view.findViewById(R.id.multi_image)
        viewAddDealsBack = view.findViewById(R.id.viewAddDealsBack)
        productCategory_spinner = view.findViewById(R.id.productCategory_spinner)
        cancel_button = view.findViewById(R.id.cancel_button)
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        mainHeader.visibility = View.GONE

        viewAddDealsBack.setSafeOnClickListener {
            fragmentManager?.popBackStack()
        }



        cancel_button.setSafeOnClickListener{
            fragmentManager?.popBackStack()
        }

        return view
    }
    fun setImageAdaptor(imageList: ArrayList<String>) {
        imageAdaptor = ImageSliderAdaptor(imageList, requireContext(),  "viewDeals", this)
        multi_image.adapter = imageAdaptor
        if (imageList.size > 1) {
            indicator3.setViewPager(multi_image)
        }
    }

    override fun click4(_id: String, _id1: String) {
    }


    fun setToolbar() {
        title = activity?.findViewById(R.id.PreLoginTitle_TextView2)!!


        cart = activity?.findViewById(R.id.cart_icon)!!
        filter = activity?.findViewById(R.id.filter_icon)!!
        back = activity?.findViewById(R.id.imageView_back)!!
        MenuClick = activity?.findViewById(R.id.MenuClick)!!
        DealsImageView = activity?.findViewById(R.id.Deals_ImageView)!!
        greyBellImageView = activity?.findViewById(R.id.greyBell_ImageView)!!
        logoutButton = activity?.findViewById(R.id.logoutButton)!!
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
cartCount.visibility = View.GONE
cart.visibility = View.GONE
        filter.visibility = View.GONE
        back.visibility = View.VISIBLE

        MenuClick.visibility = View.GONE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    override fun viewImage(imageList: ArrayList<String>) {

    }


}