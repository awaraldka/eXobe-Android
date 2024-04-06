package com.exobe.fragments.allServices

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
import com.exobe.adaptor.servicesAdaptor.CompletedServicesAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.CustomeClick
import com.exobe.customClicks.ServicesClick
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.serviceTab.ServicesListDoc
import com.exobe.entity.response.serviceTab.ServicesListResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject


class CompletedServices(var servicesClick: ServicesClick) : Fragment(), CustomeClick {
    lateinit var recyclerView: RecyclerView
    var usertype = ""
    var status = ""
    var receverid = ""
    lateinit var no_notification: LinearLayout
    lateinit var progressbar_completed: ProgressBar
    lateinit var swipe_to_refresh_deliverd_order: SwipeRefreshLayout
    lateinit var pbDeliverPagination: ProgressBar
    lateinit var nestedScrollDeliver: NestedScrollView
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    var data: ArrayList<ServicesListDoc> = ArrayList()

    var remainingItems = 0
    var page = 1
    var limit = 20
    var dataLoadFlag = true
    var loaderFlag = true
    companion object {
        var apiCompleteServiceCallFlag = true
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_delevered_order, container, false)
        recyclerView = view.findViewById(R.id.Order_recycler)
        no_notification = view.findViewById(R.id.no_notification)
        swipe_to_refresh_deliverd_order = view.findViewById(R.id.swipe_to_refresh_deliverd_order)

        pbDeliverPagination = view.findViewById(R.id.pbDeliverPagination)
        nestedScrollDeliver = view.findViewById(R.id.nestedScrollDeliver)

        progressbar_completed = view.findViewById(R.id.progressbar_completed)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        usertype = "SERVICE_PROVIDER"
        status = "ACCEPTED"
        receverid = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.ID).toString()

        swipe_to_refresh_deliverd_order.setOnRefreshListener {
            if (androidextention.isOnline(requireContext())) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                resetPagination()
                PendingOrderAPI()
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
                    PendingOrderAPI()
                }

            }
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        if(apiCompleteServiceCallFlag) {
            resetPagination()
            PendingOrderAPI()
            apiCompleteServiceCallFlag = false
        } else {
            if (androidextention.isOnline(requireContext())) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                if (data.size > 0) {
                    recyclerView.visibility = View.VISIBLE
                    no_notification.visibility = View.GONE
                    setAdaptor()
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

    fun setAdaptor() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        var adapter = CompletedServicesAdapter(activity as Context, this, data)
        recyclerView.adapter = adapter
    }

    override fun click(_id: String?) {
        servicesClick.click("completed", _id)

    }

    fun PendingOrderAPI() {
        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            if (loaderFlag) {
                progressbar_completed.visibility = View.VISIBLE
            }
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<ServicesListResponse> =
                ApiCallBack<ServicesListResponse>(object :
                    ApiResponseListener<ServicesListResponse> {

                    override fun onApiSuccess(
                        response: ServicesListResponse,
                        apiName: String?
                    ) {
                        progressbar_completed.visibility = View.GONE
                        println("complete service list ${response.toString()}")
                        if (response.responseCode == 200) {
                            try {
                                data.clear()
                                remainingItems = response.result.remainingItems
                                page = response.result.page
                                if (dataLoadFlag) {
                                    data.clear()
                                }
                                data.addAll(response.result.docs)
                                recyclerView.visibility = View.VISIBLE
                                no_notification.visibility = View.GONE
                                setAdaptor()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_completed.visibility = View.GONE
                        no_notification.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar_completed.visibility = View.GONE
                        no_notification.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }, "PendingOrder", requireContext())

            var jsonObject = JsonObject()
            jsonObject.addProperty("statusType", status)
            jsonObject.addProperty("orderType", "SERVICE")
            jsonObject.addProperty("page", page)
            jsonObject.addProperty("limit", limit)

            try {
                serviceManager.pendingstatusapi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
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