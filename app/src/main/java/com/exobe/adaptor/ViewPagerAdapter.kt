package com.exobe.adaptor

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.exobe.fragments.DeleveredOrder
import com.exobe.fragments.PendingOrder
import com.exobe.customClicks.OrderManagementListener

class MyAdapter(
    val myContext: Context,
    val fm: FragmentManager,
    var totalTabs: Int,
    var orderManagementListener: OrderManagementListener
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 ->{
                PendingOrder.apiCallFlag = true
                PendingOrder(orderManagementListener)
            }

            else -> {
                DeleveredOrder.apiCallFlag = true
                DeleveredOrder(orderManagementListener)
            }

        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}


