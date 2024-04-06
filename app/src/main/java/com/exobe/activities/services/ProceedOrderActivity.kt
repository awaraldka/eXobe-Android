package com.exobe.activities.services

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.exobe.activities.OtpVerification
import com.exobe.adaptor.servicesAdaptor.OrderListServicesAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.Progresss
import com.exobe.androidextention
import com.exobe.customClicks.OrderProcessClick
import com.exobe.databinding.ActivityProccedOrderBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.CommonResponseForAll
import com.exobe.entity.response.serviceTab.AllProducts
import com.exobe.entity.response.serviceTab.AllRequestedProductsResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder

class ProceedOrderActivity : AppCompatActivity() , OrderProcessClick {

    private lateinit var binding: ActivityProccedOrderBinding
    var dataSubDetails = ArrayList<AllProducts>()

    var retailerId = ""
    var orderIdRequest = ""
    var userRole = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProccedOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.attributes.windowAnimations = R.style.Fade

        intent.getStringExtra("userRole")?.let{userRole = it}

        intent.getStringExtra("retailerId")?.let { retailerId = it }
        intent.getStringExtra("viewId")?.let { orderIdRequest = it  }
        binding.ProceedButtonOrder.isEnabled = false



        binding.backButtonClick.setOnClickListener {
            finishAfterTransition()
        }

        binding.ProceedButtonOrder.setOnClickListener {
            val filterData = dataSubDetails.filter { it.isSelected }
            if (filterData.size != dataSubDetails.size){
                androidextention.alertBox("Please select all the products.",this)
                return@setOnClickListener
            }
            sendOtpApi()
        }

        getAllPendingOrdersApi()
    }



    private fun getAllPendingOrdersApi() {
        if (androidextention.isOnline(this)) {
            binding.shimmerFrameLayout.isVisible = true
            binding.shimmerFrameLayout.startShimmerAnimation()
            binding.nestedScroll.isVisible = false
            binding.ProceedButtonOrder.isVisible = false

            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<AllRequestedProductsResponse> =
                ApiCallBack(object : ApiResponseListener<AllRequestedProductsResponse> {
                    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
                    override fun onApiSuccess(response: AllRequestedProductsResponse, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.nestedScroll.isVisible = true
                        binding.shimmerFrameLayout.isVisible = false
                        if (response.responseCode == 200) {
                            try {
                                dataSubDetails.clear()
                                dataSubDetails = response.result.products

                                if (dataSubDetails.isNotEmpty()) {
                                    binding.nestedScroll.isVisible = true
                                    binding.shimmerFrameLayout.isVisible = false
                                    binding.ProceedButtonOrder.isVisible = true
                                    setAdapter()
                                } else {
                                    binding.notFound.isVisible = true
                                    binding.nestedScroll.isVisible = false
                                    binding.shimmerFrameLayout.isVisible = false
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
                        binding.nestedScroll.isVisible = false
                        binding.ProceedButtonOrder.isVisible = false
                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@ProceedOrderActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.shimmerFrameLayout.isVisible = false
                        binding.notFound.isVisible = true
                        binding.nestedScroll.isVisible = false
                        binding.ProceedButtonOrder.isVisible = false
                    }

                }, apiName = "viewAssignedOrderApi", this)


            try {

                serviceManager.viewAssignedProductApi(callBack, _id =orderIdRequest, retailerId = retailerId)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }
    
    
    fun setAdapter(){
        binding.processOrder.layoutManager = LinearLayoutManager(this)
        val adapter = OrderListServicesAdapter(this, dataSubDetails, visibility = true,
            retailerId="", orderIdRequest = "", click = this,isOrderPickedUp = false,isOrderDelivered= false,requestStatus = "", userRole = userRole)
        binding.processOrder.adapter = adapter
    }

    override fun orderProcess() {
        val filterData = dataSubDetails.filter { it.isSelected }
          if(dataSubDetails.size== filterData.size){
            binding.ProceedButtonOrder.isEnabled = true
            binding.ProceedButtonOrder.setBackgroundResource(R.drawable.login_button)
        }else{
              binding.ProceedButtonOrder.isEnabled = false
              binding.ProceedButtonOrder.setBackgroundResource(R.drawable.cancel_button)

        }

    }

    override fun sendOtpForDeliveredItemToFE(retailerId: String) {

    }

    override fun mapOpen(lat: Double, long: Double) {
    }


    private fun sendOtpApi() {
        if (androidextention.isOnline(this)) {
            Progresss.start(this)

            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<CommonResponseForAll> =
                ApiCallBack(object : ApiResponseListener<CommonResponseForAll> {
                    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
                    override fun onApiSuccess(response: CommonResponseForAll, apiName: String?) {
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            try {

                                val intent = Intent(this@ProceedOrderActivity,OtpVerification::class.java)
                                intent.putExtra("flag","Service provider")
                                intent.putExtra("userFor","Retailer")
                                intent.putExtra("userRole",userRole)
                                intent.putExtra("retailerId",retailerId)
                                intent.putExtra("viewId",orderIdRequest)
                                startActivity(intent)
                                finish()

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()
                        Progresss.stop()
                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@ProceedOrderActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, apiName = "sendOtpApi", this)


            try {

                serviceManager.sendOtpApi(callBack, _id =orderIdRequest, retailerId = retailerId)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }


}