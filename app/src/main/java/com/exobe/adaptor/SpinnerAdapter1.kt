package com.example.complyany

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner

class SpinnerAdapter1 {

    fun mySpinner(context: Context,spinner: Spinner, array: Array<String>){
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(context, R.layout.simple_spinner_item , array  )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapter)
    }

    fun mySpinnerArray(context: Context,spinner: Spinner, array: ArrayList<String>){
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(context, R.layout.simple_spinner_item , array  )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapter)
    }

}