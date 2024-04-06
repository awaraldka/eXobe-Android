package com.exobe.dialogs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.hostActivity.ServicesMainActivity
import com.exobe.R
import com.exobe.customClicks.RejectOrderClick

class CustomerRating(context: Context) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view= inflater.inflate(R.layout.rate_this_customer, container, false)
        val no_btn = view.findViewById<Button>(R.id.btnCancel)
        val yes_btn = view.findViewById<Button>(R.id.btnSave)
//        val bodyTitle = dialog.findViewById<TextView>(R.id.TextView1)
//        bodyTitle.text = title
        no_btn.setSafeOnClickListener {
            dismiss()
        }
        yes_btn.setSafeOnClickListener {
            Toast.makeText(context, "Feedback sent successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, ServicesMainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
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
class RejectOrders(context: Context, val click: RejectOrderClick) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.reject_reason, container, false)
        val rejectReasonET = view.findViewById<EditText>(R.id.rejectReasonET)
        val submitButton = view.findViewById<TextView>(R.id.submitButton)



        submitButton.setSafeOnClickListener {
            click.rejectOrder(rejectReasonET.text.toString())
            dismiss()
        }
        return view
    }
    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}





