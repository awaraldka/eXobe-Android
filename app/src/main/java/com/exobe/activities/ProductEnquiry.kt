package com.exobe.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.dialogs.productDialog
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.ProductEnquiryResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject


class ProductEnquiry(var flagSide: String) : Fragment() {

    lateinit var btnSave: Button
    lateinit var backPE: ImageView
    lateinit var EmailTV: TextView
    lateinit var PhoneTV: TextView
    lateinit var ivgroup: TextView
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var SubjectET: EditText
    lateinit var Description: EditText
    lateinit var SubjectST: TextView
    lateinit var DescrTV: TextView
    lateinit var mContext: Context
    lateinit var progressbar: ProgressBar
    lateinit var btnCancel: Button
    lateinit var internet_connection: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_product_enquiry, container, false)
        mContext = activity?.applicationContext!!
        btnCancel = view.findViewById(R.id.btnCancel)
        progressbar = view.findViewById(R.id.progressbar)

        EmailTV = view.findViewById(R.id.EmailTV)
        PhoneTV = view.findViewById(R.id.PhoneTV)
        btnSave = view.findViewById(R.id.btnSave)
        SubjectET = view.findViewById(R.id.SubjectET)
        SubjectST = view.findViewById(R.id.Subject)
        Description = view.findViewById(R.id.Description)
        DescrTV = view.findViewById(R.id.DescrTV)
        setToolbar()
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        mainHeader.visibility = View.VISIBLE
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        internet_connection.visibility = View.GONE

        EmailTV.setSafeOnClickListener {
            shareToGMail()
        }

        PhoneTV.setSafeOnClickListener {
            val number = PhoneTV.text.toString()
            val intent = Intent(Intent.ACTION_DIAL)
            intent.setData(Uri.parse("tel:" + number))
            startActivity(intent)
        }
        btnCancel.setSafeOnClickListener {
            fragmentManager?.popBackStack()
        }

        back.setSafeOnClickListener {
            fragmentManager?.popBackStack()
        }

        btnSave.setSafeOnClickListener {
            validations()
            if (SubjectET.text.isNotEmpty() && Description.text.isNotEmpty()) {
                productEnquiryApi()
            }

        }


        if (flagSide.equals("SideMenu")) {
            MenuClick.visibility = View.GONE
            back.visibility = View.VISIBLE

        } else {
            back.visibility = View.GONE
            MenuClick.visibility = View.VISIBLE
        }

        return view
    }

    private fun validations() {

        SubjectST.text = ""
        DescrTV.text = ""
        SubjectST.visibility = View.VISIBLE
        DescrTV.visibility = View.GONE

        if (SubjectET.text.isEmpty()) {
            SubjectET.requestFocus()
            SubjectST.visibility = View.VISIBLE
            SubjectST.text = "*Please enter subject."
        } else if (Description.text.isEmpty()) {
            Description.requestFocus()
            DescrTV.visibility = View.VISIBLE
            DescrTV.text = "*Enter description."
        } else {

        }
    }

    fun setToolbar() {
        title = activity?.findViewById(R.id.PreLoginTitle_TextView2)!!


        cart = activity?.findViewById(R.id.cart_icon)!!
        filter = activity?.findViewById(R.id.filter_icon)!!
        back = activity?.findViewById(R.id.imageView_back)!!
        MenuClick = activity?.findViewById(R.id.MenuClick)!!
        DealsImageView = activity?.findViewById(R.id.Deals_ImageView)!!
        greyBellImageView = activity?.findViewById(R.id.greyBell_ImageView)!!
        logoutButton = activity?.findViewById(R.id.logoutButton)!!
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
        cartCount.visibility = View.GONE
        cart.visibility = View.GONE
        filter.visibility = View.GONE
        back.visibility = View.VISIBLE

        MenuClick.visibility = View.GONE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        title.setText("Product Enquiry")
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    fun productEnquiryApi() {
        if (androidextention.isOnline(mContext)) {
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<ProductEnquiryResponse> =
                ApiCallBack<ProductEnquiryResponse>(object :
                    ApiResponseListener<ProductEnquiryResponse> {
                    override fun onApiSuccess(response: ProductEnquiryResponse, apiName: String?) {
                        progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                fragmentManager?.let {
                                    productDialog("", "Enquiry").show(
                                        it,
                                        "MyCustomFragment"
                                    )
                                }
                                fragmentManager?.popBackStack()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar.visibility = View.GONE

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, requireContext())

                        } catch (e: Exception) {

                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                    }

                }, "productEnquiryApi", mContext)


            var jsonObject = JsonObject()
            jsonObject.addProperty("mobileNumber ", "9818355940")
            jsonObject.addProperty(
                "email", SavedPrefManager.getStringPreferences(
                    mContext,
                    SavedPrefManager.EMAIL
                )
            )
            jsonObject.addProperty("subject", SubjectET.text.toString())
            jsonObject.addProperty("description ", Description.text.toString())

            try {
                serviceManager.productEnquiry(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }

    }


    fun shareToGMail() {
        val emailIntent =
            Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${EmailTV.text.trim().toString()}"))
        requireActivity().startActivity(emailIntent)
    }


}


