package com.exobe.bottomSheet

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exobe.adaptor.DealSizeAdaptor
import com.exobe.adaptor.SizeAdaptor
import com.exobe.R
import com.exobe.customClicks.DismissSizeListener
import com.exobe.customClicks.SizeListener
import com.exobe.entity.response.DealDetails
import com.exobe.entity.response.PriceSizeDetails
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectSizeBottomSheet(var mContext: Context,var flag : String, var dealDetails : ArrayList<DealDetails>,var priceSizeDetails : ArrayList<PriceSizeDetails>, var sizeListener: SizeListener) : BottomSheetDialogFragment() ,
    DismissSizeListener {
    lateinit var search: EditText
    lateinit var search_RL: RelativeLayout
    lateinit var unit_rv: RecyclerView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.peekHeight = ViewGroup.LayoutParams.MATCH_PARENT
        }
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.select_unit_list_design, null)
        search= view.findViewById(R.id.searchUnit)
        unit_rv= view.findViewById(R.id.unit_rv)
        search_RL= view.findViewById(R.id.search)
        search_RL.visibility = View.GONE
        if(flag == "Deal") {
            setDealUnitAdaptor()
        } else {
            setUnitAdaptor()
        }

        return view
    }

    private fun setUnitAdaptor() {
        unit_rv.layoutManager = LinearLayoutManager(mContext)
        var adapter = SizeAdaptor(mContext, priceSizeDetails, this)
        unit_rv.adapter = adapter
    }

 private fun setDealUnitAdaptor() {
        unit_rv.layoutManager = LinearLayoutManager(mContext)
        val adapter = DealSizeAdaptor(mContext, dealDetails, this)
        unit_rv.adapter = adapter
    }

    override fun dismissListener(
        name: String,
        id: String,
        price: Number,
        unit: String,
        value: String,
        dealPrice: Number?,
        quantity: String?
    ) {
        dismiss()
        sizeListener.getSize(name, id, price, unit, value, dealPrice, quantity)
    }
}





