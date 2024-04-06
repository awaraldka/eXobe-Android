package com.exobe.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.customClicks.addressDelete

class DeleteDialog(var addressDelete: addressDelete) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view= inflater.inflate(R.layout.delete_address_popup, container, false)
        val no_btn = view.findViewById<Button>(R.id.no_button)
        val yes_btn = view.findViewById<Button>(R.id.yes_button)
//        val bodyTitle = dialog.findViewById<TextView>(R.id.TextView1)
//        bodyTitle.text = title
        no_btn.setSafeOnClickListener {
            dismiss()
        }
        yes_btn.setSafeOnClickListener {
            addressDelete.deleteAdd()
            dismiss()
        }
        return view
    }
    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }




}