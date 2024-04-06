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
import com.exobe.adaptor.UnitAdaptor
import com.exobe.R
import com.exobe.customClicks.DismissListener
import com.exobe.customClicks.UnitListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectUnitBottomSheet(var mContext: Context, var unitListener: UnitListener) : BottomSheetDialogFragment() ,
    DismissListener {
    lateinit var search: EditText
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
        setUnitAdaptor()

        return view
    }

    private fun setUnitAdaptor() {
        var units = ArrayList<String>()
        units.add("kg")
        units.add("gm")
        units.add("mg")
        units.add("mm")
        units.add("cm")
        units.add("m")
        units.add("in")
        units.add("ft")
        units.add("l")
        units.add("ml")
        units.add("Yrs")
        units.add("Size")
        units.add("Pieces")
        units.add("other")
        unit_rv.layoutManager = LinearLayoutManager(mContext)
        var adapter = UnitAdaptor(mContext, units, this)
        unit_rv.adapter = adapter
    }

    override fun dismissListener(name : String) {
        dismiss()
        unitListener.getUnit(name)
    }
}





