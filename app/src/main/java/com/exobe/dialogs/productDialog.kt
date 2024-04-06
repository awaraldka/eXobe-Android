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
class productDialog(var message:String,var flag:String) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//            getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);
        var view = inflater.inflate(R.layout.product_enquiry_dialog, container, false)
        val popup_cross = view.findViewById<ImageView>(R.id.popup_cross)
        val EnquiryTv = view.findViewById<TextView>(R.id.EnquiryTv)
        val EnquiryTV = view.findViewById<TextView>(R.id.EnquiryTV)
        val productAdd = view.findViewById<TextView>(R.id.productAdd)
        val Notify = view.findViewById<LinearLayout>(R.id.Notify)
        val no_btn = view.findViewById<Button>(R.id.no_button)
        val yes_btn = view.findViewById<Button>(R.id.yes_button)
        val dealAdd = view.findViewById<TextView>(R.id.dealAdd)

        no_btn.setSafeOnClickListener {
            dismiss()
        }
        yes_btn.setSafeOnClickListener {
            dismiss()
        }

        if (flag.equals("Enquiry")){
            EnquiryTV.visibility =View.VISIBLE
            EnquiryTv.visibility =View.VISIBLE
            productAdd.setText(message)
        }else if (flag.equals("Product")){
            EnquiryTV.visibility =View.GONE
            EnquiryTv.visibility =View.GONE
            productAdd.visibility =View.VISIBLE
            productAdd.setText(message)
        }else if (flag.equals("OPPRESSED")){
            EnquiryTV.visibility =View.GONE
            EnquiryTv.visibility =View.GONE
            productAdd.visibility =View.VISIBLE
            productAdd.setText(message)
        }else if (flag.equals("Notification")){
            EnquiryTV.visibility =View.GONE
            EnquiryTv.visibility =View.GONE
            productAdd.visibility =View.VISIBLE
            Notify.visibility =View.VISIBLE
            productAdd.setText(message)
        }else if (flag.equals("DeleteItem")){
            EnquiryTV.visibility =View.GONE
            EnquiryTv.visibility =View.GONE
            productAdd.visibility =View.VISIBLE
            Notify.visibility =View.VISIBLE
            productAdd.setText(message)
        }else if (flag.equals("Deal")){
        EnquiryTV.visibility =View.GONE
        EnquiryTv.visibility =View.GONE
        productAdd.visibility =View.GONE
        Notify.visibility =View.GONE
        dealAdd.visibility = View.VISIBLE
        dealAdd.setText(message)
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

