package com.exobe.fragments.allServices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.exobe.adaptor.servicesAdaptor.PendingServicesAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.ServicesClick
import com.exobe.customClicks.servicedeleteclick
import com.exobe.customClicks.serviceselectedclick
import com.exobe.customClicks.viewserviceclick
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.serviceTab.ServicesListDoc
import com.exobe.entity.response.serviceTab.ServicesListResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject


class PendingServices(var servicesClick: ServicesClick, var viewserviceclick: viewserviceclick) :
    Fragment(), serviceselectedclick,
    servicedeleteclick {
    var usertype = ""
    var status = ""
    var receverid = ""
    lateinit var no_notification: LinearLayout
    lateinit var swipe_to_refresh_pending_services: SwipeRefreshLayout
    lateinit var progressbar_pending: ProgressBar
    lateinit var nestedscrollview_ps: NestedScrollView
    lateinit var pagination_progressbar_ps: ProgressBar

    lateinit var recyclerView: RecyclerView
    var data: ArrayList<ServicesListDoc> = ArrayList()
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null

    var remainingItems = 0
    var page = 1
    var limit = 20
    var dataLoadFlag = true
    var loaderFlag = true
    companion object {
        var apiPendingServiceCallFlag = true
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_pending_services, container, false)
        pagination_progressbar_ps = view.findViewById(R.id.pagination_progressbar_ps)
        nestedscrollview_ps = view.findViewById(R.id.nestedscrollview_ps)
        progressbar_pending = view.findViewById(R.id.progressbar_pending)
        swipe_to_refresh_pending_services = view.findViewById(R.id.swipe_to_refresh_pending_services)

        recyclerView = view.findViewById(R.id.PendingRecycler)
        no_notification = view.findViewById(R.id.no_notification)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!

        status = "PENDING"
        receverid = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.ID).toString()

        swipe_to_refresh_pending_services.setOnRefreshListener {
            if (androidextention.isOnline(requireContext())) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                resetPagination()
                PendingOrderAPI()

            } else {
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
                no_notification.visibility = View.GONE
            }
            swipe_to_refresh_pending_services.isRefreshing = false
        }

        nestedscrollview_ps.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                dataLoadFlag = false
                loaderFlag = false


                pagination_progressbar_ps.visibility = View.VISIBLE
                if (remainingItems == 0) {
                    pagination_progressbar_ps.visibility = View.GONE
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
        if(apiPendingServiceCallFlag) {
            resetPagination()
            PendingOrderAPI()
            apiPendingServiceCallFlag = false
        } else {
            if (androidextention.isOnline(requireContext())) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                if (data.size > 0) {
                    no_notification.visibility = View.GONE
                    setAdaptor()
                } else {
                    no_notification.visibility = View.VISIBLE
                }
            } else {
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
                no_notification.visibility = View.GONE
            }
        }
    }

    override fun pendingclick(position: Int, id: String) {
        viewserviceclick.viewservice(id, "")
    }

    fun setAdaptor() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        var adapter = PendingServicesAdapter(requireContext(), this, data)
        recyclerView.adapter = adapter
    }

    override fun pendingdeleteclick(position: Int) {
        setAdaptor()
    }


    fun PendingOrderAPI() {
        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            if(loaderFlag) {
                progressbar_pending.visibility = View.VISIBLE
            }
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<ServicesListResponse> =
                ApiCallBack<ServicesListResponse>(object :
                    ApiResponseListener<ServicesListResponse> {

                    override fun onApiSuccess(
                        response: ServicesListResponse,
                        apiName: String?
                    ) {
                        progressbar_pending.visibility = View.GONE
                        println("Pending service list ${response.toString()}")
                        if (response.responseCode == 200) {
                            try {
                                no_notification.visibility = View.GONE
                                page = response.result.page
                                remainingItems = response.result.remainingItems
                                if (dataLoadFlag) {
                                    data.clear()
                                }
                                data.addAll(response.result.docs)
                                setAdaptor()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_pending.visibility = View.GONE
                        data.clear()
                        setAdaptor()
                        no_notification.visibility = View.VISIBLE
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar_pending.visibility = View.GONE
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