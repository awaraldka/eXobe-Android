package com.exobe.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exobe.adaptor.ServicesAvaliableAdapter_SP
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.entity.ApiCallBack
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.JsonObject
import android.widget.TextView.OnEditorActionListener
import com.exobe.customClicks.ServiceListing
import android.view.View.OnFocusChangeListener
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.ServiceListSubCategory
import com.exobe.modelClass.SubCategoryModalClass
import com.exobe.modelClass.response_modal_class
import com.exobe.utils.CommonFunctions
import com.exobe.utils.LocationClass
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.SubCategoryClick
import com.exobe.entity.response.ServiceSubCategoryResponse
import com.exobe.entity.response.customerservices.ListOfServiceProviderDoc
import com.exobe.entity.response.customerservices.ListOfServiceProviderResponse
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class ServicesAvailableListing : Fragment(), ServiceListing,
    ApiResponseListener<ListOfServiceProviderResponse>, SubCategoryClick {
    lateinit var service_provider_RecyclerView: RecyclerView
    lateinit var MenuClick: LinearLayout
    lateinit var search: RelativeLayout
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var mainHeader: RelativeLayout
    lateinit var progressbar: ProgressBar
    lateinit var serviceListPB: ProgressBar
    lateinit var DFsearch: EditText
    lateinit var noService: LinearLayout
    lateinit var AllProvidersList_TextView: TextView

    lateinit var nestedScrollView: NestedScrollView
    lateinit var SL_pull_to_refresh: SwipeRefreshLayout
    var itemList = ArrayList<ListOfServiceProviderDoc>()
    var name = ""
    var searchFlag = false
    var id = ""
    var categoryName = ""
    lateinit var mContext: Context
    var pages = 0
    var page = 1
    var limit = 20
    var dataLoadFlag = true
    var loaderFlag = true
    var lat = ""
    var long = ""
    private val LOCATION_PERMISSION_REQ_CODE = 1000;
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    lateinit var serviceProviderLL: LinearLayout
    private var timer: Timer? = null
    private var apiCallFlag = true
    var subCategoryArray: MutableList<SubCategoryModalClass> = mutableListOf()
    lateinit var adapterSubCategory: ServiceListSubCategory
    lateinit var serviceSubCategoryRV: RecyclerView
    private var subCategoryId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_services_available_listing, container, false)
        setToolbar()
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        search = view.findViewById(R.id.search)
        SL_pull_to_refresh = view.findViewById(R.id.SL_pull_to_refresh)
        service_provider_RecyclerView = view.findViewById(R.id.service_provider_RecyclerView)
        progressbar = view.findViewById(R.id.progressbar)
        serviceListPB = view.findViewById(R.id.serviceListPB)
        nestedScrollView = view.findViewById(R.id.nestedScrollView)
        DFsearch = view.findViewById(R.id.DFsearch)
        noService = view.findViewById(R.id.no_service)
        AllProvidersList_TextView = view.findViewById(R.id.AllProvidersList_TextView)
        serviceProviderLL = view.findViewById(R.id.serviceProviderLL)
        serviceSubCategoryRV = view.findViewById(R.id.serviceSubCategoryRV)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!

        getParsingData()
        locationpermission()
        mainHeader.visibility = View.VISIBLE

        if (requireArguments().getString("servicename") != null) {
            name = requireArguments().getString("servicename").toString()
        }


        try {
            lat = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.LAT)!!
            long = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.LONG)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }

        AllProvidersList_TextView.text = "All $name Booking Providers:"



        SL_pull_to_refresh.setOnRefreshListener {
            resetPagination()
            DFsearch.setText("")
            ListServiceProvideNearMeApi("", subCategoryId)
            SL_pull_to_refresh.isRefreshing = false
        }

        back.setSafeOnClickListener {
            fragmentManager?.popBackStack()
        }

        DFsearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                CommonFunctions.hideKeyboard(requireActivity())
                return@OnEditorActionListener true
            }
            false
        })

        DFsearch.tag = false
        DFsearch.onFocusChangeListener = OnFocusChangeListener { _, _ -> DFsearch.tag = true }

        DFsearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                timer?.cancel()
            }

            override fun afterTextChanged(s: Editable?) {
                timer = Timer()
                timer!!.schedule(object : TimerTask() {
                    override fun run() {
                        if (androidextention.isOnline(mContext)) {
                            resetPagination()
                            if (s.toString() == "" && DFsearch.tag as Boolean) {
                                searchFlag = false
                                CommonFunctions.hideKeyboard(requireActivity())
                                ListServiceProvideNearMeApi("", subCategoryId)
                            } else if (s.toString() != "") {
                                searchFlag = true
                                ListServiceProvideNearMeApi(s.toString(), subCategoryId)
                            }
                        }
                    }
                }, 600)

            }

        })

        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                dataLoadFlag = false
                loaderFlag = false

                serviceListPB.visibility = View.VISIBLE
                if (page == pages) {
                    serviceListPB.visibility = View.GONE
                } else {
                    page++
                    ListServiceProvideNearMeApi("", subCategoryId)
                }
            }
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        if (apiCallFlag) {
            resetPagination()
            ListServiceProvideNearMeApi("", subCategoryId)
            getAllSubCategory(id)
            apiCallFlag = false
        } else {
            if (androidextention.isOnline(mContext)) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)

                if (itemList.size > 0) {
                    search.visibility = View.VISIBLE
                    noService.visibility = View.GONE
                    nestedScrollView.visibility = View.VISIBLE
                    serviceProviderLL.visibility = View.VISIBLE
                    setAdpater(itemList)
                } else {
                    serviceProviderLL.visibility = View.GONE
                    noService.visibility = View.VISIBLE
                    nestedScrollView.visibility = View.GONE
                    setAdpater(itemList)
                }

                setAdapterSubCategory()
            } else {
                noService.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
                nestedScrollView.visibility = View.GONE
            }
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

//        title.text = "Bookings Providers"
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    fun ListServiceProvideNearMeApi(filterText: String, subCategoryId: String) {
        var text = ""
        if (androidextention.isOnline(mContext)) {
            GlobalScope.launch(Dispatchers.Main) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                if (loaderFlag) {
                    progressbar.visibility = View.VISIBLE
                }
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<ListOfServiceProviderResponse> =
                ApiCallBack<ListOfServiceProviderResponse>(
                    this,
                    "ListServiceProvideNearMeApi",
                    mContext
                )


            var jsonObject = JsonObject()
            if (searchFlag) {
                text = filterText
                jsonObject.addProperty("search", text)
            }
            jsonObject.addProperty("subCategoryId", subCategoryId)
            jsonObject.addProperty("categoryId", id)


            jsonObject.addProperty("lat", lat)
            jsonObject.addProperty("lng", long)
            jsonObject.addProperty("page", page)
            jsonObject.addProperty("limit", limit)
            try {
                serviceManager.ListServiceProvideNearMeApi(callBack, jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            serviceProviderLL.visibility = View.GONE
            noService.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }

    }

    override fun onApiSuccess(response: ListOfServiceProviderResponse, apiName: String?) {

        progressbar.visibility = View.GONE
        if (response.responseCode == 200) {
            try {

                if (dataLoadFlag) {
                    itemList.clear()
                }
                page = response.result.page
                pages = response.result.totalPages

                itemList = response.result.docs

                if (itemList.size > 0) {
                    search.visibility = View.VISIBLE
                    noService.visibility = View.GONE
                    serviceProviderLL.visibility = View.VISIBLE
                    setAdpater(itemList)
                } else {
                    serviceProviderLL.visibility = View.GONE
                    noService.visibility = View.VISIBLE
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            serviceProviderLL.visibility = View.GONE
            noService.visibility = View.VISIBLE
        }
    }

    override fun onApiErrorBody(response: String, apiName: String?) {
        try {
            itemList.clear()
            setAdpater(itemList)
            progressbar.visibility = View.GONE
            serviceProviderLL.visibility = View.GONE
            noService.visibility = View.VISIBLE
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        itemList.clear()
        setAdpater(itemList)
        progressbar.visibility = View.GONE
        serviceProviderLL.visibility = View.GONE
        noService.visibility = View.VISIBLE
    }

    fun setAdpater(itemList: ArrayList<ListOfServiceProviderDoc>) {
        service_provider_RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        service_provider_RecyclerView.adapter = ServicesAvaliableAdapter_SP(itemList, requireContext(), this, name, id)

    }

    fun getParsingData() {
        try {
            if (requireArguments().getString("id") != null) {
                id = requireArguments().getString("id").toString()
            }
            if (requireArguments().getString("servicename") != null) {
                categoryName = requireArguments().getString("servicename").toString()
                title.text = categoryName
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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

    override fun serviceListingClick(_id: String, comment: String, userId: String) {

        loaderFlag = true
        var bundle = Bundle()
        bundle.putString("flag", "service")
        bundle.putString("id", id)
        bundle.putString("userId", userId)
        bundle.putString("comment", comment)
        var fragObj = ServiceDetailPage()
        fragObj.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.FrameLayout, fragObj, "ServiceDetailPage")
            .addToBackStack(null).commit()

    }

    private fun resetPagination() {
        page = 1
        limit = 20
        dataLoadFlag = true
        loaderFlag = true
        searchFlag = false
    }

    override fun onPause() {
        super.onPause()
        CommonFunctions.hideKeyboard(requireActivity())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Store the context in a member variable
        mContext = context
    }


    private fun getAllSubCategory(categoryId: String) {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<ServiceSubCategoryResponse> =
                ApiCallBack(object :
                    ApiResponseListener<ServiceSubCategoryResponse> {
                    override fun onApiSuccess(
                        response: ServiceSubCategoryResponse,
                        apiName: String?
                    ) {
                        progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            subCategoryArray.clear()

                            val subCategoryData = response.result.docs
                            subCategoryArray.add(SubCategoryModalClass("All", "", true))
                            if (subCategoryData!!.isNotEmpty()) {
                                for (item in subCategoryData.indices) {
                                    subCategoryArray.add(
                                        SubCategoryModalClass(
                                            subCategoryData[item].subCategoryName,
                                            subCategoryData[item]._id
                                        )
                                    )
                                }

                            }
                            setAdapterSubCategory()
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar.visibility = View.GONE

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                    }

                }, "subcategoryListWithCategory", mContext)

            val jsonObject = JsonObject().apply {
                addProperty("categoryId", categoryId)
            }
            try {
                serviceManager.serviceSubcategoryListWithCategory(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    fun setAdapterSubCategory() {
        serviceSubCategoryRV.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        adapterSubCategory = ServiceListSubCategory(requireContext(), subCategoryArray, this)
        serviceSubCategoryRV.adapter = adapterSubCategory
    }

    override fun subCategoryClick(name: String, id: String) {
        resetPagination()
        subCategoryId = id
        ListServiceProvideNearMeApi("", subCategoryId)
    }
}
