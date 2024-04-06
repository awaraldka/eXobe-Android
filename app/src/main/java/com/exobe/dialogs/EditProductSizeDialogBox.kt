package com.exobe.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.bottomSheet.SelectUnitBottomSheet
import com.exobe.customClicks.PopUpEditPriceDetails
import com.exobe.customClicks.UnitListener


class EditProductSizeDialogBox(
    context: Context,
    var value_popup: String,
    var unit_popup: String,
    var amount_popup: Number,
    var qty_popup: String,
    var id: String,
    var popUpEditPriceDetails: PopUpEditPriceDetails
) : DialogFragment(), UnitListener {
    lateinit var submit_button: Button
    lateinit var value: EditText
    lateinit var value_error: TextView
    lateinit var spinner_ll: LinearLayout
    lateinit var unit_spinner_tv: TextView
    lateinit var unit_error: TextView
    lateinit var amount: EditText
    lateinit var amount_error: TextView
    lateinit var qty: EditText
    lateinit var qty_available_error: TextView
    lateinit var packages_rv: RecyclerView
    lateinit var cross_img: ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.edit_product_size_design, container, false)
          submit_button = view.findViewById(R.id.submit_button)
          value= view.findViewById(R.id.value)

          value_error= view.findViewById(R.id.value_error)
          spinner_ll= view.findViewById(R.id.spinner_ll)
          unit_spinner_tv= view.findViewById(R.id.unit_spinner_tv)
          unit_error= view.findViewById(R.id.unit_error)
          amount= view.findViewById(R.id.amount)
          amount_error= view.findViewById(R.id.amount_error)
          qty= view.findViewById(R.id.qty)
          qty_available_error= view.findViewById(R.id.qty_available_error)
          cross_img= view.findViewById(R.id.cross_img)

        value.setText(value_popup)
        unit_spinner_tv.text = unit_popup
        amount.setText(amount_popup.toString())
        qty.setText(qty_popup)
        cross_img.setSafeOnClickListener {
            dismiss()
        }

        spinner_ll.setSafeOnClickListener{
            fragmentManager?.let { it1 ->
                SelectUnitBottomSheet(requireContext(), this).show(
                    it1,
                    "ModalBottomSheet"
                )
            }
        }
        submit_button.setSafeOnClickListener {
            var priceRegex = "^\\d{0,8}(\\.\\d{1,4})?\$"
            if (value.text.isEmpty()) {
                value_error.text = "*Please enter value."
            } else if (unit_spinner_tv.text.isEmpty()) {
                value_error.text = ""
                unit_error.text = "*Please select unit."
            } else if (amount.text.isEmpty()) {
                value_error.text = ""
                unit_error.text = ""
                amount_error.text = "*Please enter amount."
            } else if (amount.text.startsWith("0")) {
                value_error.text = ""
                unit_error.text = ""
                amount_error.text = "*Please enter valid amount."
            } else if (!amount.text.toString().matches(Regex(priceRegex))) {
                value_error.text = ""
                unit_error.text = ""
                amount_error.text = "*Please enter valid amount."
            } else if (qty.text.isEmpty()) {
                value_error.text = ""
                amount_error.text = ""
                unit_error.text = ""
                qty_available_error.text = "*Please enter qty. available."
            } else if (qty.text.startsWith("0")) {
                value_error.text = ""
                amount_error.text = ""
                unit_error.text = ""
                qty_available_error.text = "*Please enter valid qty. available."
            } else {
                value_error.text = ""
                amount_error.text = ""
                unit_error.text = ""
                qty_available_error.text = ""
                popUpEditPriceDetails.popupEditDetails(value.text.toString(), unit_spinner_tv.text.toString(), amount.text.toString(), qty.text.toString(), id)
                value.text.clear()
                unit_spinner_tv.text = ""
                amount.text.clear()
                qty.text.clear()
            }
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

    override fun getUnit(unitName: String) {
        unit_spinner_tv.text = unitName

    }


}