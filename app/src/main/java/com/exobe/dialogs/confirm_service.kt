package com.exobe.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.customClicks.servicedeleteclick
import com.exobe.customClicks.viewserviceclick

class confirm_service(

    var requireContext: Context,
    var servicedeleteclick: servicedeleteclick,
    var position: Int,
    var viewserviceclick: viewserviceclick,
    var id: String,
   var s: String
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.confirm_service, container, false)
        val cancel_button = view.findViewById<Button>(R.id.cancel_button)
        val submit_button = view.findViewById<Button>(R.id.submit_button)

        cancel_button.setSafeOnClickListener {
            servicedeleteclick.pendingdeleteclick(position)
            dismiss()
        }
        submit_button.setSafeOnClickListener {
            viewserviceclick.viewservice(id, s)
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