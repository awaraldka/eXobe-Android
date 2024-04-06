package com.exobe.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exobe.activities.ServicesAvailableListing
import com.exobe.adaptor.ServicesAdapter
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.customClicks.SetTitles
import com.exobe.customClicks.serviceAvailableCategoryClick
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.customerservices.ListCategoryServiceDocs
import com.exobe.entity.response.customerservices.ListCategoryServiceResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.JsonObject
import android.widget.TextView.OnEditorActionListener
import androidx.core.widget.NestedScrollView
import android.view.View.OnFocusChangeListener
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.utils.CommonFunctions
import com.exobe.utils.NotificationApi
import com.exobe.utils.TabHandler
import com.exobe.androidextention.initLoader
import com.exobe.utils.Progresss
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class ServiceAvailable(var setTitles: SetTitles, var flag: String) : Fragment(), serviceAvailableCategoryClick,
    ApiResponseListener<ListCategoryServiceResponse> {
    lateinit var mContext: Context
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ServicesAdapter
    var itemList: ArrayList<ListCategoryServiceDocs> = ArrayList()
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var progressbar: ProgressBar
    lateinit var DFsearch: EditText
    var searchFlag = false
    lateinit var NoReview: LinearLayout
    lateinit var VisibleItem: LinearLayout

    lateinit var nestedScrollView: NestedScrollView
    lateinit var SC_pull_to_refresh: SwipeRefreshLayout
    lateinit var pagination_progressbar: ProgressBar
    var searchText = ""

    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null

    var pages = 0
    var page = 1
    var limit = 20
    var dataLoadFlag = true
    var loaderFlag = true

    private var timer: Timer? = null
    private var apiCallFlag = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_service_available, container, false)
        SC_pull_to_refresh = view.findViewById(R.id.SC_pull_to_refresh)
        nestedScrollView = view.findViewById(R.id.nestedScrollView)
        recyclerView = view.findViewById(R.id.serviceAvailableRecycler)
        progressbar = view.findViewById(R.id.progressbar)
        DFsearch = view.findViewById(R.id.DFsearch)
        NoReview = view.findViewById(R.id.NoReview)
        VisibleItem = view.findViewById(R.id.VisibleItem)
        pagination_progressbar = view.findViewById(R.id.pagination_progressbar)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        mainHeader.visibility = View.VISIBLE
        setToolbar()
        TabHandler.HandleTab(R.id.ll_service_tab, requireActivity())


        SC_pull_to_refresh.setOnRefreshListener {
            Progresss.start(requireContext())
            handlePullToRefresh()
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
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

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
                                CommonFunctions.hideKeyboard(requireActivity())
                                ServiceListApi("")
                            } else if (s.toString() != "") {
                                ServiceListApi(s.toString())
                            }
                        }
                    }
                }, 600)
            }
        })

        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                dataLoadFlag = false
                loaderFlag = false


                pagination_progressbar.visibility = View.VISIBLE
                if (page <= pages) {
                    pagination_progressbar.visibility = View.GONE
                } else {
                    page++
                    ServiceListApi(searchText)
                }
            }
        })






        return view

    }

    override fun onResume() {
        super.onResume()
        if(apiCallFlag) {
            resetPagination()
            ServiceListApi(searchText)
            apiCallFlag = false
        } else {
            try {
                if (androidextention.isOnline(mContext)) {
                    apihandleOnlineState()
                } else {
                    apihandleOfflineState()
                }
            } catch (e: Exception) {
                e.printStackTrace()
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

    private fun handlePullToRefresh() {
        NoReview.visibility = View.GONE
        if (androidextention.isOnline(mContext)) {
            handleOnlineState()
        } else {
            handleOfflineState()
        }
        SC_pull_to_refresh.isRefreshing = false
    }

    private fun handleOnlineState() {
        internet_connection.visibility = View.GONE
        lottie!!.initLoader(false)
        nestedScrollView.visibility = View.VISIBLE
        searchFlag = false
        resetPagination()
        searchText = ""
        loaderFlag = false
        ServiceListApi(searchText)
    }

    private fun handleOfflineState() {
        internet_connection.visibility = View.VISIBLE
        lottie!!.initLoader(true)
        nestedScrollView.visibility = View.GONE
        NoReview.visibility = View.GONE
    }


    private fun apihandleOnlineState() {
        internet_connection.visibility = View.GONE
        lottie!!.initLoader(false)
        NoReview.visibility = View.GONE
        VisibleItem.visibility = View.VISIBLE
        setAdpater(itemList)
    }

    private fun apihandleOfflineState() {
        NoReview.visibility = View.GONE
        nestedScrollView.visibility = View.GONE
        internet_connection.visibility = View.VISIBLE
        lottie!!.initLoader(true)
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
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
        cartCount.visibility = View.GONE
        cart.visibility = View.VISIBLE
        filter.visibility = View.GONE
        if(flag == "TAB") {
            back.visibility = View.GONE
            MenuClick.visibility = View.GONE
        } else {
            back.visibility = View.VISIBLE
            MenuClick.visibility = View.GONE
        }

        DealsImageView.visibility = View.VISIBLE
        greyBellImageView.visibility = View.VISIBLE
        title.text = "Bookings"

        back.setSafeOnClickListener{
            parentFragmentManager.popBackStack()
        }

        if (SavedPrefManager.getStringPreferences(
                requireContext(),
                SavedPrefManager.isLogin
            ) == "true"
        ) {
            NotificationApi.notifiactionCountApi(
                requireContext(),
                greyBellImageView,
                logoutButton
            )
            SavedPrefManager.savePreferenceBoolean(context, SavedPrefManager.NOTIFICATION_VISIBLE, true)

        }
    }



    fun ServiceListApi(searchText: String) {
        if (androidextention.isOnline(mContext)) {
           GlobalScope.launch(Dispatchers.Main) {
                NoReview.visibility = View.GONE
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                if (loaderFlag) {
                    Progresss.start(requireContext())
                }
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<ListCategoryServiceResponse> =
                ApiCallBack<ListCategoryServiceResponse>(this, "ServiceListApi", mContext)

            var jsonObject = JsonObject()
            if (!searchText.equals("")) {
                jsonObject.addProperty("search", searchText)
            }
            jsonObject.addProperty("page", page)
            jsonObject.addProperty("limit", limit)

            try {
                serviceManager.listCategoryServiceApi(callBack, jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }

    }

    override fun onApiSuccess(response: ListCategoryServiceResponse, apiName: String?) {
        Progresss.stop()
        if (response.responseCode == 200) {
            try {
                page = response.result?.page!!
                pages = response.result.pages
                if (dataLoadFlag) {
                    itemList.clear()
                }
                pagination_progressbar.visibility = View.GONE
                itemList.addAll(response.result.docs as ArrayList<ListCategoryServiceDocs>)
                if (itemList.size > 0) {
                    NoReview.visibility = View.GONE
                    VisibleItem.visibility = View.VISIBLE
                    setAdpater(itemList)
                } else {
                    NoReview.visibility = View.VISIBLE

                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            NoReview.visibility = View.VISIBLE

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onApiErrorBody(response: String, apiName: String?) {
        try {
            Progresss.stop()
            pagination_progressbar.visibility = View.GONE
            NoReview.visibility = View.VISIBLE
            itemList.clear()
            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Progresss.stop()
        NoReview.visibility = View.VISIBLE
        pagination_progressbar.visibility = View.GONE

    }

    fun setAdpater(itemList: ArrayList<ListCategoryServiceDocs>) {
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter = ServicesAdapter(requireContext(), itemList, this)
        recyclerView.adapter = adapter


    }

    override fun serviceAvailableCategory(_id: String?, categoryName: String?) {
        val bundle = Bundle()
        bundle.putString("id", _id)
        bundle.putString("servicename", categoryName)
        val fragObj = ServicesAvailableListing()
        fragObj.arguments = bundle
        
        parentFragmentManager.beginTransaction()
            .replace(R.id.FrameLayout, fragObj, "ServicesAvailableListing")
            .addToBackStack(null).commit()
    }

    private fun resetPagination() {
        page = 1
        dataLoadFlag = true
        loaderFlag = true
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

    override fun onDestroy() {
        super.onDestroy()
        DealsImageView.visibility = View.VISIBLE
        greyBellImageView.visibility = View.VISIBLE
    }
}