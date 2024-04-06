package com.exobe.bottomSheet

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.customClicks.CustomerDealListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import kotlin.collections.ArrayList

class DealsBottomSheet(var flag: String, val customerDealListener: CustomerDealListener) : BottomSheetDialogFragment() {

    lateinit var cancel: TextView
    lateinit var dealsOnService: TextView
    lateinit var dealsOnProduct: TextView
    lateinit var dealsOnWholesaler: TextView
    lateinit var dealsOnWholesalerView: View


    private var openFlag = ""
    var imageList: ArrayList<Bitmap?> = ArrayList()
    lateinit var bitmap: Bitmap
    val threeImageFlag = 0
    lateinit var image: Uri
    lateinit var mContext: Context
    var imageFile: File? = null
    var imagePath = ""
    lateinit var serviceManager: ServiceManager


    companion object {
        var count = 0
        var captureVideoCount = 0

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.bottom_drawer_deals, container, false)
        mContext = requireActivity().applicationContext
        serviceManager = ServiceManager(mContext)
        dealsOnProduct = v.findViewById(R.id.dealsOnProduct)
        dealsOnService = v.findViewById(R.id.dealsOnService)
        dealsOnWholesaler = v.findViewById(R.id.dealsOnWholesaler)
        dealsOnWholesalerView = v.findViewById(R.id.dealsOnWholesalerView)
        cancel = v.findViewById(R.id.cancel)

        if(flag=="dealsRetailer")
        {
            dealsOnWholesaler.isVisible = false
            dealsOnWholesalerView.isVisible = false
//            dealsOnService.text= "Deals from wholesalers"
        }

        cancel.setSafeOnClickListener {
            dismiss()
        }

        dealsOnProduct.setSafeOnClickListener {
            customerDealListener.dealsOnProduct(flag)
            dismiss()
        }

        dealsOnService.setSafeOnClickListener {
            customerDealListener.dealsOnServices(flag)
            dismiss()
        }

        dealsOnWholesaler.setSafeOnClickListener {
            customerDealListener.dealsFromWholesaler()
            dismiss()
        }



        return v
    }


    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)



}

