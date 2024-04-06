package com.exobe.activities.services

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.exobe.adaptor.servicesAdaptor.NotificationServicesCommonAdapter
import com.exobe.R
import com.exobe.androidextention
import com.exobe.databinding.ActivityNotificationServicesBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.listNotificationDocs
import com.exobe.entity.response.listNotificationResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.JsonObject

class NotificationServicesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationServicesBinding

    var page = 1
    var limit = 10
    var pages = 0
    var dataLoadFlag = false
    lateinit var notificationAdapter: NotificationServicesCommonAdapter
    private var  dataNotification : ArrayList<listNotificationDocs> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationServicesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.attributes.windowAnimations = R.style.Fade

        binding.backButtonClick.setOnClickListener {
            finishAfterTransition()
        }


        binding.swipeRefresh.setOnRefreshListener{
            page = 1
            limit = 10
            dataLoadFlag = false
            listNotificationAPI()
        }

        listNotificationAPI()

        binding.nestedScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                dataLoadFlag = true
                page++
                binding.paginationProgressbarPs.visibility = View.VISIBLE
                if (page > pages) {
                    binding.paginationProgressbarPs.visibility = View.GONE
                } else {
                    listNotificationAPI()                }
            }
        })


    }




    private fun listNotificationAPI() {

        if (androidextention.isOnline(this)) {
            if (page == 1) {
                binding.shimmerFrameLayout.isVisible = true
                binding.shimmerFrameLayout.startShimmerAnimation()

            }
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<listNotificationResponse> =
                ApiCallBack(object :
                    ApiResponseListener<listNotificationResponse> {
                    override fun onApiSuccess(response: listNotificationResponse, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.shimmerFrameLayout.isVisible = false
                        binding.paginationProgressbarPs.visibility = View.GONE


                        if (response.responseCode == 200) {
                            try {
                                page = response.result!!.page
                                pages = response.result.pages

                                if (!dataLoadFlag) {
                                    dataNotification.clear()
                                }
                                dataNotification.addAll(response.result.docs!!)
                                if (dataNotification.size > 0) {
                                    binding.notFound.visibility = View.GONE
                                    binding.clearAll.visibility = View.VISIBLE
                                    binding.swipeRefresh.isVisible = true
                                    setNotificationData()
                                } else {
                                    binding.nestedScroll.isVisible = false
                                    binding.notFound.visibility = View.VISIBLE
                                    binding.clearAll.visibility = View.GONE
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }


                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.shimmerFrameLayout.isVisible = false
                        binding.swipeRefresh.isVisible = false
                        binding.paginationProgressbarPs.visibility = View.GONE
                        binding.notFound.isVisible = true
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.shimmerFrameLayout.isVisible = false
                        binding.paginationProgressbarPs.visibility = View.GONE
                        binding.swipeRefresh.isVisible = false
                        binding.notFound.isVisible = true
                    }

                }, "listNotificationAPI", this)


            val jsonObject = JsonObject().apply {
                addProperty("page", page)
                addProperty("limit", limit)
            }

            try {
                serviceManager.apiNotification(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setNotificationData() {
        binding.notificationRecycler.layoutManager = LinearLayoutManager(this)
        notificationAdapter = NotificationServicesCommonAdapter(this,dataNotification)
        binding.notificationRecycler.adapter = notificationAdapter
    }


}