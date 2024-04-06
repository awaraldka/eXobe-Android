package com.exobe.activities.services

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.exobe.adaptor.servicesAdaptor.WalletTransactionServiceAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.androidextention
import com.exobe.databinding.ActivityMyEarningBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.MyEarningDocs
import com.exobe.entity.response.MyEarningResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder

class MyEarningActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyEarningBinding

    var data:ArrayList<MyEarningDocs> =  arrayListOf()

    var page = 1
    var limit = 10
    var pages = 0
    var dataLoadFlag = true
    var loaderFlag = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyEarningBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.attributes.windowAnimations = R.style.Fade

        binding.backButtonClick.setOnClickListener {
            finishAfterTransition()
        }

        getAllDeliveredOrdersApi()

        binding.swipeRefreshListener.setOnRefreshListener {
            dataLoadFlag = true
            loaderFlag = true
            page = 1
            limit = 10
            getAllDeliveredOrdersApi()
            binding.swipeRefreshListener.isRefreshing =false

        }

        binding.nestedScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                dataLoadFlag = false
                loaderFlag = false
                page++
                binding.progressBar.visibility = View.VISIBLE
                if (page > pages) {
                    binding.progressBar.visibility = View.GONE
                } else {

                    getAllDeliveredOrdersApi()
                }
            }
        })






    }



    fun setAdapter(){
        binding.walletTransaction.layoutManager = LinearLayoutManager(this)
        val adapter = WalletTransactionServiceAdapter(this, data)
        binding.walletTransaction.adapter = adapter
    }

    private fun getAllDeliveredOrdersApi() {
        if (androidextention.isOnline(this)) {
            if (loaderFlag) {
                binding.notFound.isVisible = false
                binding.shimmerFrameLayout.isVisible = true
                binding.shimmerFrameLayout.startShimmerAnimation()
                binding.swipeRefreshListener.isVisible = false
            }
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<MyEarningResponse> =
                ApiCallBack(object : ApiResponseListener<MyEarningResponse> {
                    override fun onApiSuccess(response: MyEarningResponse, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.progressBar.isVisible = false
                        if (response.responseCode == 200) {
                            try {
                                pages = response.result.pages
                                page = response.result.page
                                if (dataLoadFlag) {
                                    data.clear()
                                }
                                data.addAll(response.result.docs)
                                loaderFlag = false


                                if (data.isNotEmpty()) {
                                    binding.swipeRefreshListener.isVisible = true
                                    binding.shimmerFrameLayout.isVisible = false

                                } else {
                                    binding.notFound.isVisible = true
                                    binding.swipeRefreshListener.isVisible = true
                                    binding.shimmerFrameLayout.isVisible = false
                                    binding.walletHeading.isVisible = false
                                    binding.walletAmmount.isVisible = false
                                    binding.walletView.isVisible = false
                                }

                                setAdapter()

                                if (response.result.wallet != null){
                                    binding.walletBalance.text = "R ${CommonFunctions.currencyFormatter(response.result.wallet.walletAmount.toDouble())}"
                                    binding.totalEarnings.text = "R ${CommonFunctions.currencyFormatter(response.result.wallet.totalWalletAmount.toDouble())}"
                                    binding.commissionBalance.text = "R ${CommonFunctions.currencyFormatter(response.result.wallet.totalCommissionPaid.toDouble())}"
                                }
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
                        binding.swipeRefreshListener.isVisible = true
                        binding.progressBar.isVisible = false

                        binding.walletHeading.isVisible = false
                        binding.walletAmmount.isVisible = false
                        binding.walletView.isVisible = false
                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@MyEarningActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.shimmerFrameLayout.isVisible = false
                        binding.notFound.isVisible = true
                        binding.swipeRefreshListener.isVisible = true
                        binding.progressBar.isVisible = false

                        binding.walletHeading.isVisible = false
                        binding.walletAmmount.isVisible = false
                        binding.walletView.isVisible = false

                    }

                }, "getAllDeliveredOrdersApi", this)


            try {

                serviceManager.getWalletTransactionApi(
                    callBack,
                    page = page,
                    limit = limit
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }


    
    

}