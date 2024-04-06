package com.exobe.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.PaymentDoneListener

class PaymentCancelDialogBox(var paymentDoneListener : PaymentDoneListener) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.faild_payment, container, false)
        val yesBtn = view.findViewById<Button>(R.id.Continue_button_popup)
        val sorryText = view.findViewById<TextView>(R.id.sorryText)
        val message = view.findViewById<TextView>(R.id.message)
        val paymentLottie = view.findViewById<LottieAnimationView>(R.id.lottie_payment)
        sorryText.isVisible = false
        message.text = "Payment Cancelled."
        message.setTextColor(resources.getColor(R.color.red))
        paymentLottie.initLoader(true)
        yesBtn.setSafeOnClickListener {
            paymentDoneListener.cancelPayment()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.setCancelable(false)
    }

}

