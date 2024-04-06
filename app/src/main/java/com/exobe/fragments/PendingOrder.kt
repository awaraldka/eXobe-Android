package com.exobe.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.exobe.adaptor.PendingAdapter
import com.exobe.R
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.CustomeClick
import com.exobe.customClicks.OrderManagementListener
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.RetailerOrderManagementDoc
import com.exobe.entity.response.RetailerOrderManagementResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.JsonObject


class PendingOrder(var orderManagementListener: OrderManagementListener) : Fragment(), CustomeClick {
    lateinit var recyclerView: RecyclerView
    lateinit var mContext: Context
    lateinit var swipe_to_refresh: SwipeRefreshLayout
    var usertype = ""
    var status = ""
    lateinit var no_notification: LinearLayout
    lateinit var progressbar: ProgressBar
    var data: ArrayList<RetailerOrderManagementDoc> = ArrayList()
    lateinit var internet_connection: RelativeLayout
    lateinit var nestedScrollPending: NestedScrollView
    lateinit var pbPendingPagination: ProgressBar
    var lottie: LottieAnimationView? = null

    var remainingItems = 0
    var page = 1
    var limit = 20
    var dataLoadFlag = true
    var loaderFlag = true
    companion object{
        var apiCallFlag = true
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pending_order, container, false)
        mContext = activity?.applicationContext!!
        pbPendingPagination = view.findViewById(R.id.pbPendingPagination)
        nestedScrollPending = view.findViewById(R.id.nestedScrollPending)
        recyclerView = view.findViewById(R.id.PendingRecycler)
        progressbar = view.findViewById(R.id.progressbar)
        no_notification = view.findViewById(R.id.no_notification)
        swipe_to_refresh = view.findViewById(R.id.swipe_to_refresh)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!

        usertype = "RETAILER"
        status = "PENDING"


        swipe_to_refresh.setOnRefreshListener {
            if (androidextention.isOnline(mContext)) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                resetPagination()
                PendingOrderAPI()
            } else {
                no_notification.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
            }
            swipe_to_refresh.isRefreshing = false
        }

        nestedScrollPending.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                dataLoadFlag = false
                loaderFlag = false


                pbPendingPagination.visibility = View.VISIBLE
                if (remainingItems == 0) {
                    pbPendingPagination.visibility = View.GONE
                } else {
                    page++
                    PendingOrderAPI()
                }
            }
        })


        return view
    }

    override fun onResume() {
        super.onResume()
        if (apiCallFlag) {
            resetPagination()
            PendingOrderAPI()
            apiCallFlag = false
        } else {
            if (androidextention.isOnline(mContext)) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)

                if (data.size > 0) {
                    no_notification.visibility = View.GONE
                    setadapter(data)
                } else {
                    no_notification.visibility = View.VISIBLE
                }
            } else {
                no_notification.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
            }
        }
    }


    override fun click(_id: String?) {
        orderManagementListener.orderManagementClick(_id, "PENDING")
    }

    fun setadapter(data: ArrayList<RetailerOrderManagementDoc>) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = PendingAdapter(requireContext(), this, data)
        recyclerView.adapter = adapter

    }

    fun PendingOrderAPI() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            if (loaderFlag) {
                progressbar.visibility = View.VISIBLE
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<RetailerOrderManagementResponse> =
                ApiCallBack<RetailerOrderManagementResponse>(object :
                    ApiResponseListener<RetailerOrderManagementResponse> {
                    override fun onApiSuccess(
                        response: RetailerOrderManagementResponse,
                        apiName: String?
                    ) {
                        progressbar.visibility = View.GONE
                        println("pending order response:- $response")
                        if (response.responseCode == 200) {
                            remainingItems = response.result.remainingItems
                            page = response.result.page
                            if (dataLoadFlag) {
                                data.clear()
                            }
                            try {
                                response.result.docs?.let { data.addAll(it) }
                                if (data.size > 0) {
                                    no_notification.visibility = View.GONE
                                    setadapter(data)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar.visibility = View.GONE
                        no_notification.visibility = View.VISIBLE
                        data.clear()
                        setadapter(data)
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE

                    }

                }, "PendingOrder", mContext)

            val jsonObject = JsonObject()
            jsonObject.addProperty("deliveredStatus", status)
            jsonObject.addProperty("orderType", "PRODUCT")
            jsonObject.addProperty("page", page)
            jsonObject.addProperty("limit", limit)

            try {
                serviceManager.pendingorderapi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            no_notification.visibility = View.GONE
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
        }
    }

    private fun resetPagination() {
        page = 1
        dataLoadFlag = true
        loaderFlag = true
    }

}
