package com.exobe.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.customClicks.deleteCartApi

class deleteCartDialog(
    var message: String,
    var flag: String,
    var deleteCartApi: deleteCartApi,
    var _id: String,
    var position: Int,
    var price: Number?,
    var quantity: String,
    var quantity1: TextView,
   var i: Int
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_delete_cart_dialog, container, false)
        val popup_cross = view.findViewById<ImageView>(R.id.popup_cross)
        val EnquiryTv = view.findViewById<TextView>(R.id.EnquiryTv)
        val EnquiryTV = view.findViewById<TextView>(R.id.EnquiryTV)
        val productAdd = view.findViewById<TextView>(R.id.productAdd)
        val Notify = view.findViewById<LinearLayout>(R.id.Notify)
        val no_btn = view.findViewById<Button>(R.id.no_button)
        val yes_btn = view.findViewById<Button>(R.id.yes_button)


        no_btn.setSafeOnClickListener {
            dismiss()
        }
        yes_btn.setSafeOnClickListener {
            deleteCartApi.deleteCartList(_id,position, price, quantity, quantity1, i)
            dismiss()
        }

        if (flag.equals("Enquiry")) {
            EnquiryTV.visibility = View.VISIBLE
            EnquiryTv.visibility = View.VISIBLE
        } else if (flag.equals("Product")) {
            EnquiryTV.visibility = View.GONE
            EnquiryTv.visibility = View.GONE
            productAdd.visibility = View.VISIBLE
        } else if (flag.equals("OPPRESSED")) {
            EnquiryTV.visibility = View.GONE
            EnquiryTv.visibility = View.GONE
            productAdd.visibility = View.VISIBLE
            productAdd.setText(message)
        } else if (flag.equals("Notification")) {
            EnquiryTV.visibility = View.GONE
            EnquiryTv.visibility = View.GONE
            productAdd.visibility = View.VISIBLE
            Notify.visibility = View.VISIBLE
            productAdd.setText(message)
        } else if (flag.equals("DeleteItem")) {
            EnquiryTV.visibility = View.GONE
            EnquiryTv.visibility = View.GONE
            productAdd.visibility = View.VISIBLE
            Notify.visibility = View.VISIBLE
            productAdd.setText(message)
        }


        popup_cross.setSafeOnClickListener {
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
