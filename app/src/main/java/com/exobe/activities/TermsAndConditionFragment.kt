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

class TermsAndConditionFragment : Fragment() {

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
    lateinit var progressbar:ProgressBar
    lateinit var mContext:Context
    lateinit var internet_connection: RelativeLayout
    lateinit var TAC_NestedScrollView: NestedScrollView
    var lottie: LottieAnimationView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.activity_terms_and_condition, container, false)
        text=view.findViewById(R.id.text)
        progressbar=view.findViewById(R.id.progressbar)
        TAC_NestedScrollView=view.findViewById(R.id.TAC_NestedScrollView)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        mContext=activity?.applicationContext!!
        if (requireArguments().getString("flag") != null) {
            flag = requireArguments().getString("flag").toString()

        }

        if(flag.equals("services")) {
            title = activity?.findViewById(R.id.title)
            service_back = activity?.findViewById(R.id.back)
            title!!.setText("Terms and Conditions")
            service_back!!.visibility = View.VISIBLE
            Terms_and_conditionApi()
            service_back!!.setSafeOnClickListener {
                parentFragmentManager.popBackStack()
            }

        } else {
            setToolbar()
            mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
            mainHeader!!.visibility = View.VISIBLE
            back!!.visibility = View.VISIBLE
            Terms_and_conditionApi()
            back!!.setSafeOnClickListener {
                parentFragmentManager.popBackStack()
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
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
        cartCount.visibility = View.GONE
        cart!!.visibility = View.GONE
        filter!!.visibility = View.GONE
        back!!.visibility = View.VISIBLE

        MenuClick!!.visibility = View.GONE
        DealsImageView!!.visibility = View.GONE
        greyBellImageView!!.visibility = View.GONE
        title!!.setText("Terms of Conditions")
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    fun Terms_and_conditionApi() {
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
                                text.text= Html.fromHtml(response.result!!.description)

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar.visibility=View.GONE

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility=View.GONE

                    }


                }, "Terms_and_conditionApi", mContext)

            try {
                serviceManager.staticDataApi(callBack,"termsConditions")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            TAC_NestedScrollView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

}



