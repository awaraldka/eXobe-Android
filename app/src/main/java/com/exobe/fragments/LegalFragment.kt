package com.exobe.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import android.widget.*
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.StaticContentResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager


class LegalFragment : Fragment() {
    lateinit var ivgroup: TextView
    var title: TextView? = null
    var cart: ImageView? = null
    var filter: ImageView? = null
    var back: LinearLayout? = null
    var service_back: ImageView? = null
    var DealsImageView: ImageView? = null
    var greyBellImageView: ImageView? = null
    var MenuClick: LinearLayout? = null
    var mainHeader: RelativeLayout? = null
    var flag = ""
    lateinit var text:TextView
    lateinit var cu_email:TextView
    lateinit var cu_mobile:TextView
    lateinit var cu_link:TextView
    lateinit var progressbar: ProgressBar
    lateinit var mContext: Context
    lateinit var internet_connection: RelativeLayout
    lateinit var noDataFoundContact: TextView
    lateinit var contact_ll: LinearLayout
    var lottie: LottieAnimationView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_legal, container, false)

        text=view.findViewById(R.id.text)
        cu_email=view.findViewById(R.id.cu_email)
        cu_mobile=view.findViewById(R.id.cu_mobile)
        cu_link=view.findViewById(R.id.cu_link)
        progressbar=view.findViewById(R.id.progressbar)
        noDataFoundContact=view.findViewById(R.id.noDataFoundContact)
        contact_ll=view.findViewById(R.id.contact_ll)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!

        mContext=activity?.applicationContext!!
        if (requireArguments().getString("flag") != null) {
            flag = requireArguments().getString("flag").toString()

        }
        if (flag == "services") {
            title = activity?.findViewById(R.id.title)
            service_back = activity?.findViewById(R.id.back)
            service_back!!.visibility = View.VISIBLE
            title!!.text = "Contact Us"
            ContactUsApi()
            service_back!!.setSafeOnClickListener {
                parentFragmentManager.popBackStack()
            }

        } else {
            setToolbar()
            mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
            mainHeader!!.visibility = View.VISIBLE
            back!!.visibility = View.VISIBLE
            ContactUsApi()
            back!!.setSafeOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }

        cu_link.setSafeOnClickListener{
            try {
                if (cu_link.text.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(cu_link.text.toString()))
                    startActivity(intent)
                }
            }catch (e : Exception) {
                e.printStackTrace()
            }
        }

        cu_email.setSafeOnClickListener{
            shareToGMail()
        }

        return view
    }

    fun shareToGMail() {
        val emailIntent =
            Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${cu_email.text.trim().toString()}"))
        requireActivity().startActivity(emailIntent)
    }

    fun setToolbar() {
        title = activity?.findViewById(R.id.PreLoginTitle_TextView2)!!


        cart = activity?.findViewById(R.id.cart_icon)!!
        filter = activity?.findViewById(R.id.filter_icon)!!
        back = activity?.findViewById(R.id.imageView_back)!!
        MenuClick = activity?.findViewById(R.id.MenuClick)!!
        DealsImageView = activity?.findViewById(R.id.Deals_ImageView)!!
        greyBellImageView = activity?.findViewById(R.id.greyBell_ImageView)!!
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
        cartCount.visibility = View.GONE
        cart!!.visibility = View.GONE
        filter!!.visibility = View.GONE
        back!!.visibility = View.VISIBLE

        MenuClick!!.visibility = View.GONE
        DealsImageView!!.visibility = View.GONE
        greyBellImageView!!.visibility = View.GONE
        title!!.text = "Contact Us"
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    fun ContactUsApi() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)

            progressbar.visibility=View.VISIBLE
            val serviceManager = ServiceManager(activity)
            val callBack: ApiCallBack<StaticContentResponse> =
                ApiCallBack<StaticContentResponse>(object :
                    ApiResponseListener<StaticContentResponse> {
                    override fun onApiSuccess(response: StaticContentResponse, apiName: String?) {
                        progressbar.visibility=View.GONE

                        if (response!!.responseCode == 200) {
                            try {
                                contact_ll.visibility = View.VISIBLE
                                noDataFoundContact.visibility=View.GONE

                                cu_email.text = response.result?.email
                                cu_mobile.text = response.result?.mobileNumber
                                cu_link.text = response.result?.link
                                text.text= Html.fromHtml(response.result?.description)

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar.visibility=View.GONE
                        noDataFoundContact.visibility=View.VISIBLE

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility=View.GONE
                        noDataFoundContact.visibility=View.VISIBLE

                    }


                }, "ContactUsApi", mContext)

            try {
                serviceManager.staticDataApi(callBack,"contactUs")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }
    }


}



