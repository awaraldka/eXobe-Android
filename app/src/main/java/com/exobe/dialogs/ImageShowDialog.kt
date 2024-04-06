package com.exobe.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.ZoomImageSliderAdaptor
import com.exobe.R
import me.relex.circleindicator.CircleIndicator3

class ImageShowDialog(var imageList: ArrayList<String>) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.image_view_layout, container, false)
        var multi_image = view.findViewById<ViewPager2>(R.id.multi_image)
        var indicator3 = view.findViewById<CircleIndicator3>(R.id.indicator)
        var cross = view.findViewById<ImageView>(R.id.cross)
        var imageAdaptor = ZoomImageSliderAdaptor(imageList, requireContext(), "viewdeals")
        multi_image.adapter = imageAdaptor
        if (imageList.size > 1) {
            indicator3.setViewPager(multi_image)
        }

        cross.setSafeOnClickListener {
            dismiss()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
//        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
//        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//        dialog!!.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val layoutParams: WindowManager.LayoutParams = dialog!!.getWindow()!!.getAttributes()
        layoutParams.dimAmount = 0.7f
        dialog!!.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog!!.window?.setGravity(Gravity.CENTER)
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window?.attributes = layoutParams
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog!!.window?.attributes?.windowAnimations = android.R.style.Animation_Dialog

    }


}