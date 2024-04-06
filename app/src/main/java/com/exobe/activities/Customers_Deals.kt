package com.exobe.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.DealsOnServicesAdapter
import com.exobe.adaptor.OnServiceDealAdaptor
import com.exobe.adaptor.SubCategoryCustomerAdapter
import com.exobe.adaptor.dealsforcustomerAdaptor
import com.exobe.modelClass.ChooseServicesMyModel
import com.exobe.modelClass.ServicesMyModel
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.LocationClass
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.bottomSheet.CustomerBottomSheet
import com.exobe.customClicks.DealsForCustomer
import com.exobe.customClicks.RequestServiceListener
import com.exobe.customClicks.UpdateIsLoginListener
import com.exobe.customClicks.subserviceClick
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.DealstocustomerList_Result
import com.exobe.entity.response.*
import com.exobe.entity.response.product.DealBannerDocs
import com.exobe.entity.response.product.DealBannerResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

class Customers_Deals(
    var flag: String,
    var flagDeals: String,
    var s: String,
    var titletag: String,
    var s1: String,
    var tabFlag: String
) : Fragment(),
    DealsForCustomer, subserviceClick, RequestServiceListener, UpdateIsLoginListener {
    lateinit var DealsRecycler: RecyclerView
    lateinit var OnServiceDealsRecycler: RecyclerView
    lateinit var adapter1: dealsforcustomerAdaptor
    lateinit var onServiceDealAdaptor: OnServiceDealAdaptor
    lateinit var ServiceDealAdaptor: DealsOnServicesAdapter
    lateinit var progressbar: ProgressBar
    lateinit var back_customer: ImageView
    var data: ArrayList<DealstocustomerList_Result> = ArrayList()
    var itemList: ArrayList<DealsonCustomerResult> = ArrayList()
    var data2: ArrayList<DealBannerDocs> = ArrayList()
    lateinit var mContext: Context
    var title: TextView? = null
    var cart: ImageView? = null
    var filter: ImageView? = null
    lateinit var logoutButton: ImageView
    var back: LinearLayout? = null
    var DealsImageView: ImageView? = null
    var greyBellImageView: ImageView? = null
    var MenuClick: LinearLayout? = null
    var mainHeader: RelativeLayout? = null
    var CategoryId: String? = ""
    var dealtype = ""
    lateinit var noCatFound: TextView
    lateinit var DealScreen: LinearLayout
    lateinit var hurry: LinearLayout
    var onproductflag = ""
    var DealstocustomerList = ArrayList<DealstocustomerList_Result>()
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    var lat = ""
    var long = ""
    private val LOCATION_PERMISSION_REQ_CODE = 1000;
    lateinit var service_back: ImageView
    lateinit var service_MenuClick: ImageView

    var page = 1
    var limit = 20
    var dataLoadFlag = true
    var loaderFlag = true
    var remainingItems = 0
    lateinit var pbProductDealPagination: ProgressBar
    lateinit var productDealNestedScroll: NestedScrollView
    private var apiCallFlag = true
    var subCatItemList = ArrayList<Customerdeals_Result>()
    lateinit var adapter2: SubCategoryCustomerAdapter
    var chooseServicesMyModel = ArrayList<ChooseServicesMyModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_deals, container, false)
        mContext = activity?.applicationContext!!

        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        pbProductDealPagination = view.findViewById(R.id.pbProductDealPagination)
        productDealNestedScroll = view.findViewById(R.id.productDealNestedScroll)
        DealsRecycler = view.findViewById(R.id.DealsRecycler)
        OnServiceDealsRecycler = view.findViewById(R.id.OnServiceDealsRecycler)
        back_customer = view.findViewById(R.id.back_customer)
        progressbar = view.findViewById(R.id.progressbar)
        DealScreen = view.findViewById(R.id.DealScreen)
        noCatFound = view.findViewById(R.id.noCatFound)
        hurry = view.findViewById(R.id.hurry)
        locationpermission()
        try {
            lat = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.LAT)!!
            long = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.LONG)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (s == "customer") {
            MenuClick = activity?.findViewById(R.id.MenuClick)!!
            mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
            mainHeader?.visibility = View.VISIBLE
            setToolbar()

            if(titletag == "Deals on Bookings"){
                hurry.visibility = View.VISIBLE
            }

            back!!.setSafeOnClickListener {
                parentFragmentManager.popBackStack()

            }
        } 
        else {
            hurry.visibility = View.GONE
            service_back = activity?.findViewById(R.id.back)!!
            service_MenuClick = activity?.findViewById(R.id.MenuClick)!!
            service_back.visibility = View.VISIBLE
            service_MenuClick.visibility = View.GONE

            service_back.setSafeOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }

        dealtype = if (flagDeals == "customerOnProduct") {
            "PRODUCT"
        } else {
            "SERVICE"
        }

        when (flagDeals) {
            "onService" -> {
                DealsRecycler.visibility = View.GONE
                hurry.visibility = View.GONE
                OnServiceDealsRecycler.visibility = View.VISIBLE

            }
            "customerOnService" -> {
                DealsRecycler.visibility = View.VISIBLE
                OnServiceDealsRecycler.visibility = View.GONE

            }
            "customerOnProduct" -> {
                DealsRecycler.visibility = View.VISIBLE
                OnServiceDealsRecycler.visibility = View.GONE

                productDealNestedScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                    if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                        dataLoadFlag = false
                        loaderFlag = false

                        pbProductDealPagination.visibility = View.VISIBLE
                        if (remainingItems == 0) {
                            pbProductDealPagination.visibility = View.GONE
                        } else {
                            page++
                            DealsOnproductApi("PRODUCT", "RETAILER")

                        }
                    }
                })

            }
        }
        
        return view
    }

    override fun onResume() {
        super.onResume()
        if (apiCallFlag) {
            when (flagDeals) {
                "onService" -> {
                    DealstocustomerListApi()
                }

                "customerOnService" -> {
                    SubCategoryCustomer()
                }

                "customerOnProduct" -> {
                    resetPagination()
                    DealsOnproductApi("PRODUCT", "RETAILER")
                }
                "serviceProviderDeals" -> {

                }
            }
            apiCallFlag = false
        } else {
            when (flagDeals) {
                "onService" -> {
                    if (androidextention.isOnline(mContext)) {
                        internet_connection.visibility = View.GONE
                        lottie!!.initLoader(false)
                        if (DealstocustomerList.size > 0) {
                            noCatFound.visibility = View.GONE
                            DealScreen.visibility = View.VISIBLE
                            setadapterserviceprovider(DealstocustomerList)
                        } else {
                            DealScreen.visibility = View.GONE
                            noCatFound.visibility = View.VISIBLE
                        }
                    } else {
                        DealScreen.visibility = View.GONE
                        noCatFound.visibility = View.GONE
                        internet_connection.visibility = View.VISIBLE
                        lottie!!.initLoader(true)
                    }
                }

                "customerOnService" -> {
                    if (androidextention.isOnline(mContext)) {
                        internet_connection.visibility = View.GONE
                        lottie!!.initLoader(false)
                        if (subCatItemList.size > 0) {
                            noCatFound.visibility = View.GONE
                            DealScreen.visibility = View.VISIBLE
                            setCustomerApapter(subCatItemList)
                        } else {
                            noCatFound.visibility = View.VISIBLE
                            DealScreen.visibility = View.GONE

                        }
                    } else {
                        DealScreen.visibility = View.GONE
                        noCatFound.visibility = View.GONE
                        internet_connection.visibility = View.VISIBLE
                        lottie!!.initLoader(true)
                    }
                }

                "customerOnProduct" -> {
                    if (androidextention.isOnline(mContext)) {
                        internet_connection.visibility = View.GONE
                        lottie!!.initLoader(false)
                        if (data2.size > 0) {
                            noCatFound.visibility = View.GONE
                            DealScreen.visibility = View.VISIBLE
                            setadapter(data2)
                        } else {
                            noCatFound.visibility = View.VISIBLE
                            DealScreen.visibility = View.GONE
                        }
                    } else {
                        noCatFound.visibility = View.GONE
                        DealScreen.visibility = View.GONE
                        internet_connection.visibility = View.VISIBLE
                        lottie!!.initLoader(true)
                    }
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    val fm: FragmentManager = requireActivity().supportFragmentManager

                    for (i in 0 until fm.backStackEntryCount) {
                        fm.popBackStack()
                    }


                }
            })

    }

    fun DealstocustomerListApi() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)

            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<DealstocustomerList_Response> =
                ApiCallBack<DealstocustomerList_Response>(object :
                    ApiResponseListener<DealstocustomerList_Response> {
                    override fun onApiSuccess(
                        response: DealstocustomerList_Response,
                        apiName: String?
                    ) {
                        progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                DealstocustomerList.clear()
                                DealstocustomerList =
                                    response.result as ArrayList<DealstocustomerList_Result>
                                if (DealstocustomerList.size > 0) {
                                    noCatFound.visibility = View.GONE
                                    DealScreen.visibility = View.VISIBLE
                                    setadapterserviceprovider(DealstocustomerList)
                                } else {
                                    DealScreen.visibility = View.GONE
                                    noCatFound.visibility = View.VISIBLE

                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        DealScreen.visibility = View.GONE
                        progressbar.visibility = View.GONE
                        noCatFound.visibility = View.VISIBLE

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        DealScreen.visibility = View.GONE
                        progressbar.visibility = View.GONE
                        noCatFound.visibility = View.VISIBLE

                    }

                }, "DealstoCustomerApi", mContext)


            try {
                serviceManager.Dealstocustomerlistapi(callBack)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            DealScreen.visibility = View.GONE
            noCatFound.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }
    }


    fun setadapterserviceprovider(data: ArrayList<DealstocustomerList_Result>) {
        OnServiceDealsRecycler.layoutManager = GridLayoutManager(mContext, 2)
        onServiceDealAdaptor = OnServiceDealAdaptor(requireContext(), this, s, data)
        OnServiceDealsRecycler.adapter = onServiceDealAdaptor
    }

    fun setadapterservices(data: ArrayList<DealsonCustomerResult>) {
        OnServiceDealsRecycler.layoutManager = GridLayoutManager(mContext, 2)
        ServiceDealAdaptor = CategoryId?.let {
            DealsOnServicesAdapter(requireContext(), this, s, it, data)
        }!!
        OnServiceDealsRecycler.adapter = ServiceDealAdaptor
    }

    fun setadapter(data: ArrayList<DealBannerDocs>) {
        DealsRecycler.layoutManager = GridLayoutManager(mContext, 2)
        adapter1 = dealsforcustomerAdaptor(mContext, data, s, this)
        DealsRecycler.adapter = adapter1
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
        cart!!.visibility = View.GONE
        filter!!.visibility = View.GONE
        MenuClick!!.visibility = View.GONE
        back!!.visibility = View.VISIBLE

        DealsImageView!!.visibility = View.GONE
        greyBellImageView!!.visibility = View.GONE
        logoutButton.visibility = View.GONE
        title!!.text = titletag
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }


    override fun click(flag: String, flag1: Boolean, id: String, productId: String) {
        val bundle = Bundle()
        bundle.putString("flag", flag)
        bundle.putBoolean("flag1", flag1)
        bundle.putString("deal_id", id)
        bundle.putString("productid", productId)

        val fragobj = ViewAddDeals()
        fragobj.setArguments(bundle)
        parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragobj, "viewAddDeals").addToBackStack(null).commit()

    }

    override fun subservice(flag: String, CategoryId: String, categoryName: String) {
        subservices_fragment.apiCallFlag = true
        if (flag == "service") {
            val bundle = Bundle()
            bundle.putString("CategoryId", CategoryId)
            bundle.putString("CategoryName", categoryName)
            val fragobj = subservices_fragment(flag, tabFlag)
            fragobj.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.service_main_container, fragobj, "subservices_fragment").addToBackStack(null).commit()
        } else {
            val bundle = Bundle()
            bundle.putString("CategoryId", CategoryId)
            bundle.putString("CategoryName", categoryName)
            val fragobj = subservices_fragment(flag, tabFlag)
            fragobj.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragobj, "subservices_fragment").addToBackStack(null).commit()
        }
    }

//    fun CustomerDealApi() {
//        if (androidextention.isOnline(mContext)) {
//            internet_connection.visibility = View.GONE
//            lottie!!.initLoader(false)
//            progressbar.visibility = View.VISIBLE
//            val serviceManager = ServiceManager(mContext)
//            val callBack: ApiCallBack<DealsonCustomerResponse> =
//                ApiCallBack<DealsonCustomerResponse>(this, "CustomerDealApi", mContext)
//
//            try {
//                serviceManager.customerdealsListApi(callBack)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        } else {
//            DealScreen.visibility = View.GONE
//            noCatFound.visibility = View.GONE
//            internet_connection.visibility = View.VISIBLE
//            lottie!!.initLoader(true)
//        }
//    }
//
//
//    override fun onApiSuccess(response: DealsonCustomerResponse, apiName: String?) {
//        if (response.responseCode == 200) {
//            progressbar.visibility = View.GONE
//            try {
//                if (dealtype == "SERVICE") {
//                    itemList.clear()
//                    itemList = response.result
//                    if (itemList.size > 0) {
//                        noCatFound.visibility = View.GONE
//                        DealScreen.visibility = View.VISIBLE
//                        setadapterservices(itemList)
//                    } else {
//                        DealScreen.visibility = View.GONE
//                        noCatFound.visibility = View.VISIBLE
//
//                    }
//
//                }
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//        }
//    }
//
//    override fun onApiErrorBody(response: String, apiName: String?) {
//        progressbar.visibility = View.GONE
//        DealScreen.visibility = View.GONE
//        noCatFound.visibility = View.VISIBLE
//        val gson = GsonBuilder().create()
//        var pojo = response_modal_class()
//
//        try {
//            pojo = gson.fromJson(response, pojo::class.java)
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    override fun onApiFailure(failureMessage: String?, apiName: String?) {
//        progressbar.visibility = View.GONE
//        DealScreen.visibility = View.GONE
//        noCatFound.visibility = View.VISIBLE
//
//    }

    fun DealsOnproductApi(dealtype: String, userType: String) {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            if (loaderFlag) {
                progressbar.visibility = View.VISIBLE
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<DealBannerResponse> =
                ApiCallBack<DealBannerResponse>(object : ApiResponseListener<DealBannerResponse> {
                    override fun onApiSuccess(response: DealBannerResponse, apiName: String?) {
                        if (response.responseCode == 200) {
                            progressbar.visibility = View.GONE

                            try {
                                if (dataLoadFlag) {
                                    data2.clear()
                                }
                                remainingItems = response.result.remainingItems
                                page = response.result.page
                                data2.addAll(response.result.docs)

                                if (data2.size > 0) {
                                    noCatFound.visibility = View.GONE
                                    DealScreen.visibility = View.VISIBLE
                                    setadapter(data2)
                                } else {
                                    noCatFound.visibility = View.VISIBLE
                                    DealScreen.visibility = View.GONE

                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar.visibility = View.GONE
                        noCatFound.visibility = View.VISIBLE

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                        noCatFound.visibility = View.VISIBLE

                    }

                }, "CustomerDealApi", mContext)
            val jsonObject = JsonObject()
            jsonObject.addProperty("dealType", dealtype)
            jsonObject.addProperty("userType", userType)
            jsonObject.addProperty("lng", long)
            jsonObject.addProperty("lat", lat)
            jsonObject.addProperty("page", page)
            jsonObject.addProperty("limit", limit)
            try {
                serviceManager.customerdealsListApi(callBack, jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            noCatFound.visibility = View.GONE
            DealScreen.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }


    private fun locationpermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

    private fun resetPagination() {
        page = 1
        dataLoadFlag = true
        loaderFlag = true
    }



    //services deals
    fun SubCategoryCustomer() {
        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
                progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<CustomerdealsSubcategory> =
                ApiCallBack<CustomerdealsSubcategory>(object :
                    ApiResponseListener<CustomerdealsSubcategory> {
                    override fun onApiSuccess(
                        response: CustomerdealsSubcategory,
                        apiName: String?
                    ) {
                        if (response.responseCode == 200) {
                            progressbar.visibility = View.GONE

                            try {

                                subCatItemList.clear()
                                subCatItemList.addAll(response.result)

                                if (subCatItemList.size > 0) {
                                    noCatFound.visibility = View.GONE
                                    DealScreen.visibility = View.VISIBLE
                                    setCustomerApapter(subCatItemList)
                                } else {
                                    noCatFound.visibility = View.VISIBLE
                                    DealScreen.visibility = View.GONE

                                }

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

                }, "SubCategoryViewApi", requireContext())


            try {
                serviceManager.subCategoryCustomer(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            DealsRecycler.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }
    }

    fun setCustomerApapter(itemList: ArrayList<Customerdeals_Result>) {
        DealsRecycler.layoutManager = GridLayoutManager(mContext, 2)
        adapter2 = SubCategoryCustomerAdapter(mContext, itemList, "", this)
        DealsRecycler.adapter = adapter2
    }

    override fun RequestService(
        _id: String,
        serviceName: String,
        dealPrice: Double,
        firstName: String,
        lastName: String,
        categoryName: String,
        categoryImage: String,
        subCategoryName: String,
        _id1: String,
        priceTag:String,
        actualPrice:String,
        discount:String

    ) {
        if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
            ServiceRequestReview.apiServiceRequestCallFlag = true
            chooseServicesMyModel.clear()
            var servicesMyModel = ArrayList<ServicesMyModel>()
            servicesMyModel.add(ServicesMyModel(_id1, _id1, serviceName, dealPrice,priceTag,actualPrice,discount,true))
            chooseServicesMyModel.add(ChooseServicesMyModel(subCategoryName, _id1, servicesMyModel))
            var serviceProviderName = "$firstName $lastName"
            var bundle = Bundle()
            bundle.putSerializable("CHOOSE_LIST", chooseServicesMyModel)
            bundle.putString("name", serviceProviderName)
            bundle.putString("serviceImage", categoryImage)
            bundle.putString("userId", _id)
            val frgObj = ServiceRequestReview()
            frgObj.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, frgObj, "serviceRequest").addToBackStack(null).commit()

        } else {
            parentFragmentManager.let { it1 ->
                CustomerBottomSheet("Customer", mContext,this).show(it1, "ModalBottomSheet")
            }
        }
    }

    override fun isLoginListener() {
        var name = activity?.findViewById<TextView>(R.id.name)
        var userProfile = activity?.findViewById<ImageView>(R.id.Profile_picture)
        CommonFunctions.getProfileApiApi(mContext, name, userProfile)
    }
}