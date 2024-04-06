package com.exobe.adaptor.servicesAdaptor

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.exobe.fragments.allServices.CompletedServices
import com.exobe.fragments.allServices.PendingServices
import com.exobe.customClicks.ServicesClick
import com.exobe.customClicks.viewserviceclick

class ServicesTabAdapter(
    fm1: Context,
    fm: FragmentManager,
    var totalTabs: Int,
    var servicesClick: ServicesClick,
    var viewserviceclick: viewserviceclick,

    ) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 ->{
                PendingServices.apiPendingServiceCallFlag = true
                PendingServices(servicesClick, viewserviceclick)
            }

            else -> {
                CompletedServices.apiCompleteServiceCallFlag = true
                CompletedServices(servicesClick)
            }

        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}


