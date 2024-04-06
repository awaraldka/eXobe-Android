package com.exobe.adaptor

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.exobe.fragments.OnBoardingFragment1
import com.exobe.fragments.OnBoardingFragment2
import com.exobe.fragments.onBoardFragment3


class FragAdaptor(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var fragmentList: ArrayList<Fragment> = ArrayList()
    private var titleList: ArrayList<String> = ArrayList()

    fun add(fragment: Fragment, title: String) {
        titleList.add(title)
        fragmentList.add(fragment)
    }

    override fun getItem(position: Int): Fragment {

        return when(position){
            0->{
                OnBoardingFragment1()
            }

            1->{
                OnBoardingFragment2()
            }

            else->{
                onBoardFragment3()
            }
    //            0->{return OnBoardingFragment2()
    //            }
    //            else->{return OnBoardingFragment3()
    //            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    }