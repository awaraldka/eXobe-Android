package com.exobe.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.dealForServiceAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.LocationClass
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.CustomeClick
import com.exobe.customClicks.DealsFromwholesaler
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.product.DealBannerDocs
import com.exobe.entity.response.product.DealBannerResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

class Services_DealsScreen(var flagSide: String) : Fragment(), DealsFromwholesaler, CustomeClick,
    ApiResponseListener<DealBannerResponse> {

    lateinit var adapter1: dealForServiceAdapter
    lateinit var CustomerRecyclerView: RecyclerView
    lateinit var wholesellersRecyclerView: RecyclerView
    lateinit var addDealsButton: Button

    lateinit var AddDeals_ll: LinearLayout
    lateinit var BackServices: ImageView
    var itemList: ArrayList<DealBannerDocs> = ArrayList()
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var mContext: Context
    lateinit var progressBar: ProgressBar
    lateinit var retailer_deals: TextView
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    var lat = ""
    var long = ""
    private val LOCATION_PERMISSION_REQ_CODE = 1000;
    private var apiCallFlag = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.activity_deals_for_customer_screen, container, false)

        mContext = activity?.applicationContext!!
        setToolbar()

        retailer_deals = view.findViewById(R.id.retailer_deals)
        CustomerRecyclerView = view.findViewById(R.id.CustomerRecycler)
        addDealsButton = view.findViewById(R.id.addDealsButton)
        progressBar = view.findViewById(R.id.progressbar)

        AddDeals_ll = view.findViewById(R.id.AddDeals_ll)
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!

        mainHeader.visibility = View.VISIBLE

        locationpermission()
        try {
            lat = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.LAT)!!
            long = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.LONG)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }


        back.setSafeOnClickListener {
            parentFragmentManager.popBackStack()
        }

        if (flagSide == "SideMenu") {
            back.visibility = View.GONE
            MenuClick.visibility = View.VISIBLE
        } else {
            MenuClick.visibility = View.GONE
            back.visibility = View.VISIBLE
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        val userType = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.USER_TYPE)
        if(apiCallFlag) {
            if (userType.equals("RETAILER")) {
                CustomerDealApi("PRODUCT", "WHOLE_SALER")
                apiCallFlag = false
            } else if (userType.equals("CUSTOMER")) {

            } else {

            }
        } else {
            if (userType.equals("RETAILER")) {
                if (androidextention.isOnline(mContext)) {
                    internet_connection.visibility = View.GONE
                    lottie!!.initLoader(false)
                    if (itemList.size > 0) {
                        retailer_deals.visibility = View.GONE
                        setadapter(itemList)
                    } else {
                        retailer_deals.visibility = View.VISIBLE
                    }
                } else {
                    retailer_deals.visibility = View.GONE
                    internet_connection.visibility = View.VISIBLE
                    lottie!!.initLoader(true)
                }
            } else if (userType.equals("CUSTOMER")) {

            } else {

            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                val fm: FragmentManager = requireActivity().supportFragmentManager

                for (i in 0 until fm.backStackEntryCount){
                    fm.popBackStack()
                }


            }
        })

    }

    fun setadapter(data: ArrayList<DealBannerDocs>) {
        CustomerRecyclerView.layoutManager = GridLayoutManager(mContext, 2)
        adapter1 = dealForServiceAdapter(mContext, data, "", this)
        CustomerRecyclerView.adapter = adapter1
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
        back.visibility = View.GONE

        MenuClick.visibility = View.VISIBLE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        title.setText("Deals")
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    override fun click(flag: String, flag1: Boolean, id: String, productid: String) {
        val bundle = Bundle()
        bundle.putString("flag", flag)
        bundle.putBoolean("flag1", flag1)
        bundle.putString("deal_id", id)
        bundle.putString("productid", productid)

        val fragobj = ViewAddDeals()
        fragobj.arguments = bundle
        parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragobj, "viewAddDeals").addToBackStack(null).commit()
    }

    override fun click(_id: String?) {
        val bundle = Bundle()
        bundle.putString("WholeSeller", "WholeSeller")
        val fragobj = ViewAddDeals()
        fragobj.arguments = bundle

        parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragobj, "viewAddDeals").addToBackStack(null).commit()
    }

    fun CustomerDealApi(dealtype: String, userType: String) {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)

            progressBar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<DealBannerResponse> =
                ApiCallBack<DealBannerResponse>(this, "CustomerDealApi", mContext)
            val jsonObject = JsonObject()
            jsonObject.addProperty("dealType", dealtype)
            jsonObject.addProperty("userType", userType)
            jsonObject.addProperty("lng", long)
            jsonObject.addProperty("lat", lat)
            try {
                serviceManager.customerDealsListWithTokenApi(callBack, jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            retailer_deals.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }


    override fun onApiSuccess(response: DealBannerResponse, apiName: String?) {
        if (response.responseCode == 200) {
            progressBar.visibility = View.GONE

            try {
                itemList.clear()
                itemList = response.result?.docs!!
                if (itemList.size > 0) {
                    retailer_deals.visibility = View.GONE
                    setadapter(itemList)
                } else {
                    retailer_deals.visibility = View.VISIBLE
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onApiErrorBody(response: String, apiName: String?) {
        retailer_deals.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        itemList.clear()
        setadapter(itemList)

        val gson = GsonBuilder().create()
        var pojo = response_modal_class()

        try {
            pojo = gson.fromJson(response, pojo::class.java)
            Log.d("retailer deals error", pojo.responseMessage)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        progressBar.visibility = View.GONE
        retailer_deals.visibility = View.VISIBLE

    }

    private fun locationpermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQ_CODE
            )
        } else {
            LocationClass.getCurrentLocation(requireContext())
            LocationClass.displayLocationSettingsRequest(requireContext())
        }

    }

}