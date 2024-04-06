package com.exobe.fragments

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
import com.exobe.adaptor.DeleveredAdapter
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


class DeleveredOrder(var orderManagementListener: OrderManagementListener) : Fragment(), CustomeClick {
    lateinit var recyclerView: RecyclerView
    lateinit var swipe_to_refresh_deliverd_order: SwipeRefreshLayout
    var deliveredListItem: ArrayList<RetailerOrderManagementDoc> = ArrayList()
    var usertype = ""
    var status = ""
    lateinit var no_notification: LinearLayout
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    lateinit var progressBar: ProgressBar
    var remainingItems = 0
    var page = 1
    var limit = 20
    var dataLoadFlag = true
    var loaderFlag = true
    lateinit var nestedScrollDeliver: NestedScrollView
    lateinit var pbDeliverPagination: ProgressBar
    companion object{
        var apiCallFlag = true
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_delevered_order, container, false)

        recyclerView = view.findViewById(R.id.Order_recycler)
        swipe_to_refresh_deliverd_order = view.findViewById(R.id.swipe_to_refresh_deliverd_order)
        pbDeliverPagination = view.findViewById(R.id.pbDeliverPagination)
        nestedScrollDeliver = view.findViewById(R.id.nestedScrollDeliver)
        progressBar = view.findViewById(R.id.progressbar_completed)
        no_notification = view.findViewById(R.id.no_notification)
        recyclerView.layoutManager = LinearLayoutManager(context)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!


        usertype = "RETAILER"
        status = "DELIVERED"

        swipe_to_refresh_deliverd_order.setOnRefreshListener {
            if (androidextention.isOnline(requireContext())) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                resetPagination()
                DeliveredOrderApi()
            } else {
                no_notification.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
            }
            swipe_to_refresh_deliverd_order.isRefreshing = false
        }

        nestedScrollDeliver.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                dataLoadFlag = false
                loaderFlag = false


                pbDeliverPagination.visibility = View.VISIBLE
                if (remainingItems == 0) {
                    pbDeliverPagination.visibility = View.GONE
                } else {
                    page++
                    DeliveredOrderApi()
                }
            }
        })


        return view
    }

    override fun onResume() {
        super.onResume()
        if(apiCallFlag) {
            resetPagination()
            DeliveredOrderApi()
            apiCallFlag = false
        } else {
            if (androidextention.isOnline(requireContext())) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)

                if (deliveredListItem.size > 0) {
                    no_notification.visibility = View.GONE
                    setAdapter(deliveredListItem)
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

    private fun setAdapter(deliveredListItem: ArrayList<RetailerOrderManagementDoc>) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = DeleveredAdapter(requireContext(), deliveredListItem, this)
        recyclerView.adapter = adapter
    }

    override fun click(_id: String?) {
        orderManagementListener.orderManagementClick(_id, "DELIVERED")
    }

    fun DeliveredOrderApi() {
        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            if (loaderFlag) {
                progressBar.visibility = View.VISIBLE
            }
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<RetailerOrderManagementResponse> =
                ApiCallBack<RetailerOrderManagementResponse>(object :
                    ApiResponseListener<RetailerOrderManagementResponse> {
                    override fun onApiSuccess(
                        response: RetailerOrderManagementResponse,
                        apiName: String?
                    ) {
                        progressBar.visibility = View.GONE
                        println("delivered order response:- $response")

                        if (response.responseCode == 200) {
                            no_notification.visibility = View.GONE
                            remainingItems = response.result.remainingItems
                            page = response.result.page
                            if (dataLoadFlag) {
                                deliveredListItem.clear()
                            }
                            try {
                                deliveredListItem.addAll(response.result.docs)
                                setAdapter(deliveredListItem)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressBar.visibility = View.GONE
                        no_notification.visibility = View.VISIBLE
                        deliveredListItem.clear()
                        setAdapter(deliveredListItem)

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressBar.visibility = View.GONE

                    }

                }, "DeliveredOrder", requireContext())

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
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }

    }


    private fun resetPagination() {
        page = 1
        dataLoadFlag = true
        loaderFlag = true
    }

}