package com.exobe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.exobe.adaptor.MyAdapter
import com.exobe.fragments.retailr.RetailerOderDetails
import com.exobe.R
import com.exobe.utils.TabHandler
import com.exobe.customClicks.OrderManagementListener
import com.google.android.material.tabs.TabLayout


class OrderManagement : Fragment() , OrderManagementListener {
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var mainHeader: RelativeLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.fragment_order_management, container, false)
        TabHandler.HandleTab(R.id.ll_order_tab, requireActivity())

        tabLayout = view.findViewById(R.id.tablayout)

        viewPager = view.findViewById(R.id.viewPager)

        tabLayout.addTab(tabLayout.newTab().setText("Pending"))
        tabLayout.addTab(tabLayout.newTab().setText("Delivered"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        mainHeader.visibility = View.VISIBLE



        val adapter = MyAdapter(requireContext(), childFragmentManager, tabLayout.tabCount,this)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })


        return view
    }

    override fun orderManagementClick(_id: String?, flag: String?) {
        val bundle = Bundle()
        bundle.putString("productId", _id)
        bundle.putString("flag", flag)
        val fragObj = RetailerOderDetails()
        fragObj.arguments = bundle


        parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragObj,"retailerOrder" ).addToBackStack(null).commit()
    }


}