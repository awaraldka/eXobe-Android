package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exobe.R



class   ServiceProviderFragmentAdapter(val context: Context) :
    RecyclerView.Adapter<ServiceProviderFragmentAdapter.ProductLatestViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductLatestViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.serviceprovider_adaptermodellayout, parent, false)
            return ProductLatestViewHolder(view)
        }

        override fun onBindViewHolder(holder: ProductLatestViewHolder, position: Int) {


        }

        override fun getItemCount(): Int {
            return 10
        }

        class ProductLatestViewHolder(view: View) : RecyclerView.ViewHolder(view) {



        }

    }
