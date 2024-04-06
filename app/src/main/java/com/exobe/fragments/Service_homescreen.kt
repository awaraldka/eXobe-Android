package com.exobe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R


class Service_homescreen : Fragment() {

    lateinit var mainClick : LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_service_homescreen, container, false)

        var mainClick = view.findViewById<LinearLayout>(R.id.card_click)
        mainClick.setSafeOnClickListener{
            fragmentManager?.beginTransaction()?.replace(
                R.id.FrameLayout,
                Service_Provider_fragment()
            )?.commit()
        }
        return view
    }

}