package com.exobe.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.StaticContentResponse
import com.exobe.entity.response.StaticContentResult
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager

class AboutUsFragment : Fragment() {

    lateinit var ivgroup: TextView
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var cartCount: TextView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var service_back: ImageView
    lateinit var text: TextView
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var instaClick: ImageView
    lateinit var twiClick: ImageView
    lateinit var fbClick: ImageView
    lateinit var printClick: ImageView
    lateinit var ttClick: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var mContext: Context
    lateinit var progressbar: ProgressBar
    var array: ArrayList<StaticContentResult>? = null
    var flag = ""
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.activity_about_us, container, false)
        mContext = activity?.applicationContext!!
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        text = view.findViewById(R.id.text)
        progressbar = view.findViewById(R.id.progressbar)
        instaClick = view.findViewById(R.id.insta)
        twiClick = view.findViewById(R.id.twi)
        fbClick = view.findViewById(R.id.fb)
        printClick = view.findViewById(R.id.print)
        ttClick = view.findViewById(R.id.tt)
        if (requireArguments().getString("flag") != null) {
            flag = requireArguments().getString("flag").toString()

        }
        AboutusApi()

        if (flag.equals("services")) {
            title = activity?.findViewById(R.id.title)!!
            service_back = activity?.findViewById(R.id.back)!!
            title.setText("About Us")
            service_back.visibility = View.VISIBLE
            service_back.setSafeOnClickListener {
                fragmentManager?.popBackStack()
            }
        } else {
            setToolbar()
            mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
            mainHeader.visibility = View.VISIBLE
            back.setSafeOnClickListener {
                fragmentManager?.popBackStack()
            }
        }

        instaClick.setSafeOnClickListener {
            navigateScreen("https://www.instagram.com/exobe.africa")
        }

        twiClick.setSafeOnClickListener {
            navigateScreen("https://twitter.com/exobe")
        }

        fbClick.setSafeOnClickListener {
            navigateScreen("https://facebook.com/exobe.africa")
        }

        printClick.setSafeOnClickListener {
            navigateScreen("https://pinterest.com/exobe")
        }

        ttClick.setSafeOnClickListener {
            navigateScreen("https://www.tiktok.com/exobe")
        }

        return view
    }

    private fun navigateScreen(url: String) {
        val defaultBrowser =
            Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
        defaultBrowser.data = Uri.parse(url)
        startActivity(defaultBrowser)
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
        cartCount = activity?.findViewById(R.id.cartCount)!!

        cartCount.visibility = View.GONE
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
        cartCount.visibility = View.GONE
        cart.visibility = View.GONE
        filter.visibility = View.GONE
        back.visibility = View.VISIBLE
        MenuClick.visibility = View.GONE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        title.setText("About Us")

        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE


    }

    fun AboutusApi() {
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

                        if (response!!.responseCode == 200) {
                            try {
                                text.text = Html.fromHtml(response.result!!.description)

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar.visibility = View.GONE

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE

                    }


                }, "AboutusApi", mContext)

            try {
                serviceManager.staticDataApi(callBack, "aboutUs")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }


}