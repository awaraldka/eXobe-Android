package com.exobe.activities.services

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.exobe.adaptor.servicesAdaptor.AllPendingServicesAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.androidextention
import com.exobe.customClicks.CommonListenerServices
import com.exobe.databinding.ActivityAllRejectedServicesBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.serviceTab.GetAllOrdersCommonDocs
import com.exobe.entity.response.serviceTab.GetAllOrdersCommonResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder


class AllRejectedServicesActivity : AppCompatActivity(), CommonListenerServices {

    private lateinit var binding: ActivityAllRejectedServicesBinding

    var page = 1
    var limit = 10
    var pages = 0
    lateinit var adapter:AllPendingServicesAdapter
    var dataLoadFlag = true
    var loaderFlag = true
    var data = ArrayList<GetAllOrdersCommonDocs>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllRejectedServicesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.attributes.windowAnimations = R.style.Fade

        binding.backButtonClick.setOnClickListener {
            finishAfterTransition()
        }


        getAllRejectedOrdersApi()

        binding.swipeToRefreshPendingServices.setOnRefreshListener {
            page = 1
            limit = 10
            loaderFlag = true
            dataLoadFlag = false
            getAllRejectedOrdersApi()

            binding.swipeToRefreshPendingServices.isRefreshing = false
        }


        binding.nestedscrollviewPs.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                dataLoadFlag = true
                loaderFlag = false
                page++
                binding.paginationProgressbarPs.visibility = View.VISIBLE
                if (page > pages) {
                    binding.paginationProgressbarPs.visibility = View.GONE
                } else {

                    getAllRejectedOrdersApi()
                }
            }
        })


    }


    private fun getAllRejectedOrdersApi() {
        if (androidextention.isOnline(this)) {
            if (loaderFlag) {
                binding.notFound.isVisible = false
                binding.shimmerFrameLayout.isVisible = true
                binding.shimmerFrameLayout.startShimmerAnimation()
                binding.swipeToRefreshPendingServices.isVisible = false
            }
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<GetAllOrdersCommonResponse> =
                ApiCallBack(object : ApiResponseListener<GetAllOrdersCommonResponse> {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onApiSuccess(response: GetAllOrdersCommonResponse, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        if (response.responseCode == 200) {
                            try {
                                binding.paginationProgressbarPs.visibility = View.GONE
                                pages = response.result.pages
                                page = response.result.page

                                if (!dataLoadFlag) {
                                    data.clear()
                                }
                                data.addAll(response.result.docs)
                                loaderFlag = false

                                if (data.isNotEmpty()){
                                    binding.swipeToRefreshPendingServices.isVisible = true
                                    binding.shimmerFrameLayout.isVisible = false


                                }else{
                                    binding.notFound.isVisible = true
                                    binding.swipeToRefreshPendingServices.isVisible =true
                                    binding.shimmerFrameLayout.isVisible = false

                                }
                                setAdaptor()


                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()
                        binding.shimmerFrameLayout.isVisible = false
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.notFound.isVisible = true
                        binding.swipeToRefreshPendingServices.isVisible = true
                        binding.paginationProgressbarPs.visibility = View.GONE
                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@AllRejectedServicesActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.shimmerFrameLayout.isVisible = false
                        binding.notFound.isVisible = true
                        binding.swipeToRefreshPendingServices.isVisible = true
                        binding.paginationProgressbarPs.visibility = View.GONE
                    }

                }, "getAllPendingOrdersApi", this)


            try {


                serviceManager.getAllOrdersApi(callBack, orderStatus = "REJECTED", page =page, limit = limit, userType = "ALL")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }







    fun setAdaptor() {
        binding.RejectedRecycler.layoutManager = LinearLayoutManager(this)
        val adapter = AllPendingServicesAdapter(this, data, this,)
        binding.RejectedRecycler.adapter = adapter
    }

    override fun serviceProvidersPendingClick(_id:String,orderId: String,userType:String) {
        val intent = Intent(this, ServiceCommonViewActivity::class.java)
        intent.putExtra("_id", _id)
        intent.putExtra("orderId", orderId)
        intent.putExtra("userType", userType)
        intent.putExtra("isFrom", "Rejected")
        startActivity(intent)
    }

    override fun serviceProvidersCompletedClick(_id:String,orderId: String,userType:String) {

    }

}