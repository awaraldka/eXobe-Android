package com.exobe.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exobe.adaptor.ServiceProviderFragmentAdapter
import com.exobe.R


class Service_Provider_fragment : Fragment() {

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_service__provider_fragment, container, false)

        // Inflate the layout for this fragment

        recyclerView = view.findViewById(R.id.acceptedRecycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
       var adapter = ServiceProviderFragmentAdapter(activity as Context)
        recyclerView.adapter = adapter
        return  view
    }
}