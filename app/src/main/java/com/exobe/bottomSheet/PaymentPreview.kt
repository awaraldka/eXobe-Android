package com.exobe.bottomSheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.customClicks.PaymentForOrderClick
import com.exobe.databinding.PaymentPreviewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PaymentPreview(private val cartPayment:String, val click:PaymentForOrderClick,
                     private val aboutToPay:String, private val walletAmount:String): BottomSheetDialogFragment() {
    private var _binding: PaymentPreviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PaymentPreviewBinding.inflate(inflater, container, false)

        if (cartPayment =="CartPayment"){
            binding.PayWithPayFast.isVisible = false
            binding.paymentOptionForCart.isVisible = true
            binding.aboutToPay.text = "You are about to pay $aboutToPay for this order"
            binding.walletClick.text = "eXobe Wallet $walletAmount"
        }



        binding.ozowClick.setSafeOnClickListener {
            dismiss()
            click.ozow()
        }

        binding.walletClick.setSafeOnClickListener {
            dismiss()
            click.walletClick()
        }



        binding.payfastClick.setSafeOnClickListener {
            dismiss()
            click.payfast()
        }


        return binding.root
    }






    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.payment_preview, null)
        dialog.setContentView(contentView)


        contentView.post {
            val contentHeight = contentView.height
            val peekHeightFraction = 0.75
            val peekHeight = (contentHeight * peekHeightFraction).toInt()

            val params = (contentView.parent as View).layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            if (behavior != null && behavior is BottomSheetBehavior) {
                behavior.peekHeight = peekHeight
            }
        }
    }


    override fun getTheme(): Int = R.style.BottomSheetDialogTheme


    private fun getHTMLString(): String {
        return """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>PayFast Form</title>
        </head>
        <body>
            <form id="payfast-form" action="https://sandbox.payfast.co.za/eng/process" method="post">
                <input name="merchant_id" type="hidden" value="10026670">
                <input name="merchant_key" type="hidden" value="cdxcpi0cr0ysa">
                <input name="return_url" type="hidden" value="http://localhost:4200/customer-payment-success">
                <input name="cancel_url" type="hidden" value="http://localhost:4200/customer-payment-cancel">
                <input name="notify_url" type="hidden" value="https://eaaf-182-71-75-106.ngrok-free.apphttps://node-exobe.mobiloitte.com/api/v1/payment/notifyResponse">
                <input name="name_first" type="hidden" value="Raghav">
                <input name="name_last" type="hidden" value="Tripathi">
                <input name="email_address" type="hidden" mailto:value="raghav@mailinator.com">
                <input name="m_payment_id" type="hidden" value="C251H3JX9Q">
                <input name="amount" type="hidden" value="122.5">
                <input name="item_name" type="hidden" value="#PROD801">
                <input name="custom_str1" type="hidden" value="64c3b84fa4251e7832bc391b">
                <input name="custom_str2" type="hidden" value="654c6badbc157517e5f31023">
                <input name="custom_str4" type="hidden" value="PAY_FAST_116">
                <input name="signature" type="hidden" value="09e5d8a2ba77d5b5072a8c1a1f25ff43">
                <input type="submit"
                    style="font-style: normal;
                        font-weight: 600;
                        font-size: 18px;
                        line-height: 27px;
                        color: #ffffff;
                        background: #bf1e2e;
                        height: 48px;
                        width: 260px;
                        border: none;
                        cursor: pointer"
                value="Pay Now">
            </form>
        </body>
    </html>
    """.trimIndent()
    }


}