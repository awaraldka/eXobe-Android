package com.exobe.adaptor.servicesAdaptor

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.exobe.fragments.allServices.AllCompletedServicesFragment
import com.exobe.fragments.allServices.AllPendingServicesFragment
import com.exobe.customClicks.CommonListenerServices

class AllServicesAdapter(
    fm1: Context,
    fm: FragmentManager,
    var totalTabs: Int,
    var viewServiceClick: CommonListenerServices,
    var pageRefresh :Boolean

    ) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 ->{

                AllPendingServicesFragment(viewServiceClick,pageRefresh)
            }

            else -> {
                AllCompletedServicesFragment(viewServiceClick,pageRefresh)
            }


        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}


