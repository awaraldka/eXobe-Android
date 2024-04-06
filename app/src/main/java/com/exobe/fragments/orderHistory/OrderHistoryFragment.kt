package com.exobe.fragments.orderHistory

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.OrderHistoryAdapter
import com.exobe.fragments.cart.MyCartFragment
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.OrderHistoryCustomClick
import com.exobe.customClicks.deleteCustomClick
import com.exobe.customClicks.viewOrder
import com.exobe.dialogs.CommonDeleteDialogBox
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.OrderHistoryRetailerDocs
import com.exobe.entity.response.OrderHistoryRetailerResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.JsonObject

class OrderHistoryFragment(var flagSide: String) : Fragment(), OrderHistoryCustomClick, deleteCustomClick,
    viewOrder {


    // intializations
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: OrderHistoryAdapter
    var flag = ""
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var noCatFound: TextView
    var orderType = ""
    lateinit var mContext: Context
    var data: ArrayList<OrderHistoryRetailerDocs> = ArrayList()
    var dataOrder = ArrayList<OrderHistoryRetailerDocs>()
    lateinit var internet_connection: RelativeLayout
    lateinit var nsOrderHistory: NestedScrollView
    lateinit var pbOrderHistoryPagination: ProgressBar
    var lottie: LottieAnimationView? = null

    var pages = 0
    var page = 1
    var limit = 20
    var dataLoadFlag = true
    var loaderFlag = true
    private var apiCallFlag = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_order_history, container, false)

        pbOrderHistoryPagination = view.findViewById(R.id.pbOrderHistoryPagination)
        nsOrderHistory = view.findViewById(R.id.nsOrderHistory)
        recyclerView = view.findViewById(R.id.recyclerViewOH)
        progressBar = view.findViewById(R.id.progressbar)
        noCatFound = view.findViewById(R.id.noCatFound)
        setToolbar()
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        mainHeader.visibility = View.VISIBLE
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!

        mContext = activity?.applicationContext!!


        back.setSafeOnClickListener {
            parentFragmentManager.popBackStack()
        }


        if (flagSide == "SideMenu") {
            MenuClick.visibility = View.VISIBLE
            back.visibility = View.GONE
        } else {
            back.visibility = View.VISIBLE
            MenuClick.visibility = View.GONE
        }
        val userType = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.USER_TYPE)
        if (userType.equals("RETAILER")) {
            nsOrderHistory.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                    dataLoadFlag = false
                    loaderFlag = false

                    pbOrderHistoryPagination.visibility = View.VISIBLE
                    if (page == pages) {
                        pbOrderHistoryPagination.visibility = View.GONE
                    } else {
                        page++
                        OrderListRetailerApi("PRODUCT")
                    }
                }
            })


        } else if (userType.equals("CUSTOMER")) {

            nsOrderHistory.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                    dataLoadFlag = false
                    loaderFlag = false

                    pbOrderHistoryPagination.visibility = View.VISIBLE
                    if (page == pages) {
                        pbOrderHistoryPagination.visibility = View.GONE
                    } else {
                        page++
                        OrderListRetailerApi("PRODUCT")
                    }
                }
            })
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        val userType =
            SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.USER_TYPE)
        if (apiCallFlag) {
            if (userType.equals("RETAILER")) {
                resetPagination()
                OrderListRetailerApi("PRODUCT")
            } else if (userType.equals("CUSTOMER")) {
                resetPagination()
                OrderListRetailerApi("PRODUCT")
            }
            apiCallFlag = false
        } else {
            if (userType.equals("RETAILER")) {
                if (androidextention.isOnline(mContext)) {
                    internet_connection.visibility = View.GONE
                    lottie!!.initLoader(false)
                    try {
                        setadapter(dataOrder)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    recyclerView.visibility = View.GONE
                    internet_connection.visibility = View.VISIBLE
                    lottie!!.initLoader(true)
                }
            } else if (userType.equals("CUSTOMER")) {
                if (androidextention.isOnline(mContext)) {
                    internet_connection.visibility = View.GONE
                    lottie!!.initLoader(false)
                    try {
                        setadapter(dataOrder)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    recyclerView.visibility = View.GONE
                    internet_connection.visibility = View.VISIBLE
                    lottie!!.initLoader(true)
                }
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
        title.text = "Order History"
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    override fun delete() {
        fragmentManager?.let {
            CommonDeleteDialogBox("orderHistory", this, "").show(
                it,
                "MyCustomFragment"
            )
        }
    }

    override fun reOrder() {

        fragmentManager?.beginTransaction()
            ?.replace(R.id.FrameLayout, MyCartFragment(""), "addCart")
            ?.addToBackStack(null)?.commit()

    }


    fun OrderListRetailerApi(orderType: String) {

        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            if (loaderFlag) {
                progressBar.visibility = View.VISIBLE
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<OrderHistoryRetailerResponse> =
                ApiCallBack<OrderHistoryRetailerResponse>(object :
                    ApiResponseListener<OrderHistoryRetailerResponse> {
                    override fun onApiSuccess(
                        response: OrderHistoryRetailerResponse,
                        apiName: String?
                    ) {
                        progressBar.visibility = View.GONE

                        if (response.responseCode == 200) {
                            try {
                                if (dataLoadFlag) {
                                    dataOrder.clear()
                                }
                                page = response.result?.page!!
                                pages = response.result.pages!!
                                response.result?.docs?.let { dataOrder.addAll(it) }
                                setadapter(dataOrder)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressBar.visibility = View.GONE
                        noCatFound.visibility = View.VISIBLE
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        androidextention.disMissProgressDialog(mContext)
                        progressBar.visibility = View.GONE
                    }

                }, "OrderListRetailerAPI", mContext)

            val jsonObject = JsonObject()
            jsonObject.addProperty("orderType", orderType)
            jsonObject.addProperty("page", page)
            jsonObject.addProperty("limit", limit)

            try {
                serviceManager.orderListOfOwnapi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            recyclerView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    fun setadapter(data: ArrayList<OrderHistoryRetailerDocs>) {

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = OrderHistoryAdapter(requireContext(), data, this, this)
        recyclerView.adapter = adapter

    }

    override fun viewORderiTEM(id: String,status:String) {

        val bundle = Bundle()
        bundle.putString("productId", id)
        bundle.putString("status", status)
        val fragobj = OrderHistoryViewFragment()
        fragobj.arguments = bundle
        parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragobj, "orderHistory")
            .addToBackStack(null).commit()

    }

    override fun deleteItem(id: String) {
    }

    private fun resetPagination() {
        page = 1
        dataLoadFlag = true
        loaderFlag = true
    }

}