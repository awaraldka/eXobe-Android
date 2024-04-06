package com.exobe.activities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.exobe.adaptor.DealProductAdapter
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.CustomeClick2
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.DealSearchDocs
import com.exobe.entity.response.DealSearchResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.JsonObject

class search_deals_composer : Fragment(), CustomeClick2 {

    lateinit var RecyclerOfSearch: RecyclerView
    lateinit var dealProductAdapter: DealProductAdapter
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var cartCount: TextView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var nodatafound_deals: TextView
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var progressbar: ProgressBar
    lateinit var save: Button
    lateinit var mContext: Context
    var dealData: ArrayList<DealSearchDocs> = ArrayList()
    var pages = 0
    var page = 1
    var limit = 30
    var dataLoadFlag = false
    var loaderFlag = true
    var searchtext = ""
    var dealsSearchText = ""
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_search_deals_composer, container, false)

        RecyclerOfSearch = view!!.findViewById(R.id.RecyclerOfSearch)
        progressbar = view.findViewById(R.id.progressbar)
        nodatafound_deals = view.findViewById(R.id.nodatafound_deals)

        mContext = activity?.applicationContext!!

        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        mainHeader.visibility = View.VISIBLE
        RecyclerOfSearch.visibility = View.VISIBLE
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!


        if (requireArguments().getString("DEAL_SEARCH_TEXT") != null) {
            dealsSearchText = requireArguments().getString("DEAL_SEARCH_TEXT").toString()
        }

        dealSearchListApi()
        hideKeyboard()
        return view
    }

    override fun click2(_id: String?, servicename: String?) {
        SavedPrefManager.saveStringPreferences(
            requireContext(),
            SavedPrefManager.ADMIN_DEALS_ID, _id
        )
        fragmentManager?.popBackStack()
    }

    fun setDealAdapter(data: ArrayList<DealSearchDocs>) {
        RecyclerOfSearch.layoutManager = GridLayoutManager(context, 2)
        dealProductAdapter = DealProductAdapter(requireContext(), this, data)
        RecyclerOfSearch.adapter = dealProductAdapter
    }


    fun setToolbar() {
        title = activity?.findViewById(R.id.PreLoginTitle_TextView2)!!


        cart = activity?.findViewById(R.id.cart_icon)!!
        cartCount = activity?.findViewById(R.id.cartCount)!!
        filter = activity?.findViewById(R.id.filter_icon)!!
        back = activity?.findViewById(R.id.imageView_back)!!
        MenuClick = activity?.findViewById(R.id.MenuClick)!!
        DealsImageView = activity?.findViewById(R.id.Deals_ImageView)!!
        greyBellImageView = activity?.findViewById(R.id.greyBell_ImageView)!!
        logoutButton = activity?.findViewById(R.id.logoutButton)!!
        cart.visibility = View.GONE
        cartCount.visibility = View.GONE
        filter.visibility = View.GONE
        back.visibility = View.VISIBLE

        MenuClick.visibility = View.GONE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        title.setText("Deals")
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }


    fun dealSearchListApi() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)

            if (loaderFlag) {
                progressbar.visibility = View.VISIBLE
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<DealSearchResponse> =
                ApiCallBack<DealSearchResponse>(object :
                    ApiResponseListener<DealSearchResponse> {
                    override fun onApiSuccess(response: DealSearchResponse, apiName: String?) {
                        progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {

                                loaderFlag = false
                                if (!dataLoadFlag) {
                                    dealData.clear()
                                }
                                if (response.result?.docs?.size == 0) {
                                    nodatafound_deals.visibility = View.VISIBLE
                                } else {
                                    pages = response.result?.pages!!
                                    dealData = response.result?.docs as ArrayList<DealSearchDocs>
                                    setDealAdapter(dealData)
                                    nodatafound_deals.visibility = View.GONE
                                }

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
                }, "dealSearchListApi", mContext)

            val jsonObject = JsonObject()
            jsonObject.addProperty("search", dealsSearchText)

            try {
                serviceManager.dealSearchApi(callBack, jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            nodatafound_deals.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }

    }

    fun hideKeyboard() {
        try {
            val `in`: InputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val view = requireActivity().findViewById<View>(android.R.id.content)
            assert(`in` != null)
            `in`.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        } catch (ignored: Throwable) {
        }
    }
}