package com.exobe.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.exobe.adaptor.MyAdapter
import com.exobe.R
import com.exobe.utils.TabHandler
import com.exobe.customClicks.OrderManagementListener
import com.google.android.material.tabs.TabLayout

class PaymentHistoryActivity() : Fragment(), OrderManagementListener {

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

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        var view = inflater.inflate(R.layout.activity_payment_history, container, false)

        TabHandler.HandleTab(R.id.ll_order_tab1, requireActivity())

        tabLayout = view.findViewById(R.id.tabLayoutPaymentHistory)

        viewPager = view.findViewById(R.id.viewPagerPaymentHistory)

        tabLayout.addTab(tabLayout.newTab().setText("Product"))
        tabLayout.addTab(tabLayout.newTab().setText("Service"))
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

    }
}