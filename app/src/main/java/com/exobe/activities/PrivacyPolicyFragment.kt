package com.exobe.activities

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.StaticContentResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager

class PrivacyPolicyFragment : Fragment() {

    lateinit var ivgroup: TextView
    lateinit var privacyPolicyFaqs: TextView
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var service_back: ImageView
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    var flag = ""
    lateinit var text: TextView
    lateinit var progressbar: ProgressBar
    lateinit var mContext: Context

    lateinit var PP_NestedScrollView: NestedScrollView
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.activity_privacy_policy, container, false)
        text = view.findViewById(R.id.text)
        progressbar = view.findViewById(R.id.progressbar)
        PP_NestedScrollView = view.findViewById(R.id.PP_NestedScrollView)
        privacyPolicyFaqs = view.findViewById(R.id.privacyPolicyFaqs)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!


        mContext = activity?.applicationContext!!
        if (requireArguments().getString("flag") != null) {
            flag = requireArguments().getString("flag").toString()

        }

        if (flag.equals("services")) {

            title = activity?.findViewById(R.id.title)!!
            service_back = activity?.findViewById(R.id.back)!!
            title!!.setText("Privacy Policy")
            service_back!!.visibility = View.VISIBLE
            Privacy_PolicyApi()
            service_back!!.setSafeOnClickListener {
                fragmentManager?.popBackStack()
            }

        } else {
            setToolbar()

            mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
            mainHeader.visibility = View.VISIBLE
            Privacy_PolicyApi()
            back.setSafeOnClickListener {
                fragmentManager?.popBackStack()
            }
        }

        return view
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
        title.setText("Privacy Policy")
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    fun Privacy_PolicyApi() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(activity)
            val callBack: ApiCallBack<StaticContentResponse> =
                ApiCallBack<StaticContentResponse>(object :
                    ApiResponseListener<StaticContentResponse> {
                    override fun onApiSuccess(response: StaticContentResponse, apiName: String?) {
                        progressbar.visibility = View.GONE

                        if (response.responseCode == 200) {
                            try {
                                if (!response.result!!.description.equals("")) {
                                    privacyPolicyFaqs.visibility = View.GONE
                                    text.text = Html.fromHtml(response.result.description)
                                } else {
                                    privacyPolicyFaqs.visibility = View.VISIBLE
                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        privacyPolicyFaqs.visibility = View.VISIBLE
                        progressbar.visibility = View.GONE

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        privacyPolicyFaqs.visibility = View.VISIBLE
                        progressbar.visibility = View.GONE

                    }


                }, "Privacy_PolicyApi", mContext)

            try {
                serviceManager.staticDataApi(callBack, "privacyPolicy")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            PP_NestedScrollView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }


}