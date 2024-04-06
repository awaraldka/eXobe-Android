package com.exobe.bottomSheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.exobe.R
import com.exobe.customClicks.PaymentForWallet
import com.exobe.databinding.AddMoneyToWalletBinding
import com.exobe.extension.diasplay_toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddMoneyWalletBottomSheet(val click: PaymentForWallet) : BottomSheetDialogFragment() {
    private var _binding: AddMoneyToWalletBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddMoneyToWalletBinding.inflate(inflater, container, false)

        binding.payfastPaymentClick.setOnClickListener {
            if (binding.amountWallet.text.isEmpty()) {
                diasplay_toast("Please enter amount")
                return@setOnClickListener
            }

            click.payfast(binding.amountWallet.text.toString())
            dismiss()
        }

        binding.ozowPaymentClick.setOnClickListener {
            if (binding.amountWallet.text.isEmpty()) {
                diasplay_toast("Please enter amount")
                return@setOnClickListener
            }

            click.ozow(binding.amountWallet.text.toString())
            dismiss()
        }

        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val contentView =
            View.inflate(context, R.layout.add_money_to_wallet, null)
        dialog.setContentView(contentView)

        val coordinatorLayout =
            contentView.findViewById<View>(R.id.coordinatorLayout) as CoordinatorLayout

        coordinatorLayout.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                coordinatorLayout.viewTreeObserver.removeOnPreDrawListener(this)

                val contentHeight = coordinatorLayout.height
                val peekHeightFraction = 0.5
                val peekHeight = (contentHeight * peekHeightFraction).toInt()

                val params =
                    (coordinatorLayout.parent as View).layoutParams as CoordinatorLayout.LayoutParams
                val behavior = params.behavior
                if (behavior != null && behavior is BottomSheetBehavior) {
                    behavior.peekHeight = peekHeight
                }

                return true
            }
        })

        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
}
