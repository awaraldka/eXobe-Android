package com.exobe.activities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.Adapter.NotificationAdapter
import com.exobe.fragments.ProductManagement
import com.exobe.modelClass.NotificationModel
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.NotificationType
import com.exobe.customClicks.delete2
import com.exobe.customClicks.deleteCustomClick
import com.exobe.dialogs.CommonDeleteDialogBox
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.ClearAllNotificationResponse
import com.exobe.entity.response.ParticularNotificationListResponse
import com.exobe.entity.response.listNotificationDocs
import com.exobe.entity.response.listNotificationResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.fragments.orderHistory.OrderHistoryFragment
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

class Notification : Fragment(), delete2, deleteCustomClick, NotificationType {

    lateinit var My_RecyclerView: RecyclerView
    lateinit var Adapter: NotificationAdapter
    lateinit var back: LinearLayout
    var array: ArrayList<NotificationModel> = ArrayList()
    lateinit var title: TextView
    var mainHeader: RelativeLayout? = null
    lateinit var progressbar: ProgressBar
    lateinit var pulltorefresh_notification: SwipeRefreshLayout
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var no_notification: LinearLayout
    lateinit var itemView: LinearLayout
    lateinit var clearAll: Button
    lateinit var mContext: Context
    var data = ArrayList<listNotificationDocs>()
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    var pages = 0
    var page = 1
    var limit = 20
    var dataLoadFlag = false
    var loaderFlag = false
    lateinit var paginationProgressbar: ProgressBar
    lateinit var nested_scroll_view: NestedScrollView
    private var apiCallFlag = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.activity_notification, container, false)
        setToolbar()
        My_RecyclerView = view.findViewById(R.id.notification_recycler)
        pulltorefresh_notification = view.findViewById(R.id.pulltorefresh_notification)
        progressbar = view.findViewById(R.id.progressbar)
        clearAll = view.findViewById(R.id.clearAll)
        no_notification = view.findViewById(R.id.no_notification)
        itemView = view.findViewById(R.id.itemView)
        paginationProgressbar = view.findViewById(R.id.paginationProgressbar)
        nested_scroll_view = view.findViewById(R.id.nested_scroll_view)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!

        mContext = activity?.applicationContext!!


        pulltorefresh_notification.setOnRefreshListener {
            dataLoadFlag = false
            loaderFlag = false
            page = 1
            if (androidextention.isOnline(mContext)) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                listNotificationAPI()
            } else {
                no_notification.visibility = View.GONE
                itemView.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)

            }
            pulltorefresh_notification.isRefreshing = false
        }
        back.setSafeOnClickListener {
            parentFragmentManager.popBackStack()
        }

        clearAll.setSafeOnClickListener {
            clearAllNotificationApi()
        }


        nested_scroll_view.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                dataLoadFlag = true
                loaderFlag = true
                page++
                paginationProgressbar.visibility = View.VISIBLE
                if (page > pages) {
                    paginationProgressbar.visibility = View.GONE
                } else {

                    listNotificationAPI()
                }
            }
        })


        return view
    }

    override fun onResume() {
        super.onResume()
        if (apiCallFlag) {
            listNotificationAPI()
            apiCallFlag = false
        } else {
            if (androidextention.isOnline(mContext)) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                setNotificationData()
            } else {
                no_notification.visibility = View.GONE
                itemView.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)

            }
        }
    }

    override fun delete2() {
        parentFragmentManager.let {
            CommonDeleteDialogBox("notification", this, "").show(it, "MyCustomFragment")
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

    fun setToolbar() {
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        title = activity?.findViewById(R.id.PreLoginTitle_TextView2)!!


        cart = activity?.findViewById(R.id.cart_icon)!!
        filter = activity?.findViewById(R.id.filter_icon)!!
        back = activity?.findViewById(R.id.imageView_back)!!
        MenuClick = activity?.findViewById(R.id.MenuClick)!!
        DealsImageView = activity?.findViewById(R.id.Deals_ImageView)!!
        greyBellImageView = activity?.findViewById(R.id.greyBell_ImageView)!!
        logoutButton = activity?.findViewById(R.id.logoutButton)!!



        back.visibility = View.VISIBLE
        mainHeader!!.visibility = View.VISIBLE

        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
        cartCount.visibility = View.GONE
        cart.visibility = View.GONE
        filter.visibility = View.GONE
        MenuClick.visibility = View.GONE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        title.setText("Notifications")
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }


    fun listNotificationAPI() {

        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            if (!loaderFlag) {
                progressbar.visibility = View.VISIBLE
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<listNotificationResponse> =
                ApiCallBack<listNotificationResponse>(object :
                    ApiResponseListener<listNotificationResponse> {
                    override fun onApiSuccess(
                        response: listNotificationResponse,
                        apiName: String?
                    ) {
                        progressbar.visibility = View.GONE

                        if (response.responseCode == 200) {
                            try {
//                                page = response.result?.page!!
                                pages = response.result?.pages!!
                                if (!dataLoadFlag) {
                                    data.clear()
                                }
                                data.addAll(response.result?.docs as ArrayList<listNotificationDocs>)
                                if (data.size > 0) {
                                    no_notification.visibility = View.GONE
                                    clearAll.visibility = View.VISIBLE
                                    setNotificationData()
                                } else {
                                    no_notification.visibility = View.VISIBLE
                                    clearAll.visibility = View.GONE
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        } else {
                            data.clear()
                            setNotificationData()
                            no_notification.visibility = View.VISIBLE
                            clearAll.visibility = View.GONE
                        }


                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar.visibility = View.GONE
                        clearAll.visibility = View.GONE
                        no_notification.visibility = View.VISIBLE
                        data.clear()
                        setNotificationData()

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                        clearAll.visibility = View.GONE
                        no_notification.visibility = View.VISIBLE
                    }

                }, "listNotificationAPI", mContext)


            var jsonObject = JsonObject().apply {
                addProperty("page", page)
                addProperty("limit", limit)
            }

            try {
                serviceManager.apiNotification(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            no_notification.visibility = View.GONE
            itemView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }

    }

    private fun setNotificationData() {
        try {
            if (data.size > 0) {
                no_notification.visibility = View.GONE
                itemView.visibility = View.VISIBLE
                listNotificationAdapter(data)
            } else {
                data.clear()
                listNotificationAdapter(data)
                no_notification.visibility = View.VISIBLE
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clearAllNotificationApi() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<ClearAllNotificationResponse> =
                ApiCallBack<ClearAllNotificationResponse>(object :
                    ApiResponseListener<ClearAllNotificationResponse> {
                    override fun onApiSuccess(
                        response: ClearAllNotificationResponse,
                        apiName: String?
                    ) {
                        progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            dataLoadFlag = false
                            loaderFlag = false
                            page = 1
                            androidextention.alertBox(
                                response.responseMessage.toString(),
                                requireContext()

                            )
                            listNotificationAPI()
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

                }, "clearAllNotificationApi", mContext)


            try {
                serviceManager.clearAllNotification(callBack)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            no_notification.visibility = View.GONE
            itemView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }

    }

    fun deleteNotificationApi(id: String) {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Any> =
                ApiCallBack<Any>(object :
                    ApiResponseListener<Any> {
                    override fun onApiSuccess(response: Any, apiName: String?) {
                        progressbar.visibility = View.GONE
                        try {
                            dataLoadFlag = false
                            loaderFlag = false
                            page = 1
                            listNotificationAPI()
                        } catch (e: Exception) {
                            e.printStackTrace()
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

                }, "clearAllNotificationApi", mContext)


            try {
                serviceManager.deleteNotification(callBack, id)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            no_notification.visibility = View.GONE
            itemView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }

    }


    private fun listNotificationAdapter(List: ArrayList<listNotificationDocs>) {
        My_RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        Adapter = NotificationAdapter(requireContext(), List, this, fragmentManager, this)
        My_RecyclerView.adapter = Adapter
    }


    override fun deleteItem(id: String) {
        deleteNotificationApi(id)
    }

    override fun notificationClick(position: Int, data: listNotificationDocs) {
        if (data.notifyType == "ORDER") {
            viewNotification(data._id)
        } else if (data.notifyType == "ADD_PRODUCT") {
            viewNotification(data._id)
        } else if (data.notifyType == "ADD_DEAL") {
            viewNotification(data._id)
        } else if (data.notifyType == "PAYMENT_DONE") {

        } else if (data.notifyType == "DELIVER_ORDER") {

        } else if (data.notifyType == "COMPLETE_SERVICE") {

        }
    }

    fun viewNotification(notificationId: String) {

        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<ParticularNotificationListResponse> =
                ApiCallBack<ParticularNotificationListResponse>(
                    object : ApiResponseListener<ParticularNotificationListResponse> {

                        override fun onApiSuccess(
                            response: ParticularNotificationListResponse,
                            apiName: String?
                        ) {
                            androidextention.disMissProgressDialog(activity)
                            if (response.responseCode == 200) {

                                try {

                                    var finalData = response.result

                                    if (finalData!!.notifyType == "ORDER") {
                                        val bundle = Bundle()
                                        val fragobj = OrderHistoryFragment("")
                                        fragobj.arguments = bundle
                                        parentFragmentManager.beginTransaction()
                                            .replace(R.id.FrameLayout, fragobj, "viewAddDeals")
                                            .addToBackStack(null).commit()

                                    } else if (finalData?.notifyType == "ADD_PRODUCT") {
                                        ProductManagement.apiProductManagementCallFlag = true
                                        val bundle = Bundle()
                                        val fragobj = ProductManagement()
                                        fragobj.arguments = bundle
                                        parentFragmentManager.beginTransaction()
                                            .replace(R.id.FrameLayout, fragobj, "viewAddDeals")
                                            .addToBackStack(null).commit()

                                    } else if (finalData?.notifyType == "ADD_DEAL") {
                                        val bundle = Bundle()
                                        bundle.putString("deal_id", finalData.dealId?._id)
                                        bundle.putString(
                                            "productid",
                                            finalData.dealId?.productId?.get(0)
                                        )
                                        val fragobj = ViewAddDeals()
                                        fragobj.arguments = bundle
                                        parentFragmentManager.beginTransaction()
                                            .replace(R.id.FrameLayout, fragobj, "viewAddDeals")
                                            .addToBackStack(null).commit()

                                    }

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }

                            } else {
                                androidextention.alertBox(
                                    response.responseMessage,
                                    requireContext()
                                )
                            }
                        }

                        override fun onApiErrorBody(response: String, apiName: String?) {
                            androidextention.disMissProgressDialog(activity)
                            val gson = GsonBuilder().create()
                            var pojo = response_modal_class()

                            try {
                                pojo = gson.fromJson(response, pojo::class.java)

                            } catch (e: Exception) {
                            }
                        }

                        override fun onApiFailure(failureMessage: String?, apiName: String?) {
                            androidextention.disMissProgressDialog(activity)
                            Toast.makeText(requireContext(), "onApiFailure", Toast.LENGTH_SHORT)
                                .show()

                        }
                    }, "viewNotification", mContext
                )

            try {
                serviceManager.viewParticularNotificationApi(callBack, notificationId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            no_notification.visibility = View.GONE
            itemView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }
    }


}