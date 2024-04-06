package com.exobe.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.adaptor.customeradaptor.CustomerWalletAdapter
import com.exobe.adaptor.servicesAdaptor.AllPendingServicesAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.utils.CommonFunctions
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.bottomSheet.AddMoneyWalletBottomSheet
import com.exobe.customClicks.PaymentForWallet
import com.exobe.databinding.ActivityCustomerWalletBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.MyEarningDocs
import com.exobe.entity.response.MyEarningResponse
import com.exobe.entity.response.OzowPaymentResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceConstant
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.utils.Progresss
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

class CustomerWalletActivity : AppCompatActivity(),PaymentForWallet {

    private lateinit var binding: ActivityCustomerWalletBinding

    var page = 1
    var limit = 10
    var pages = 0
    lateinit var adapter: AllPendingServicesAdapter
    var dataLoadFlag = true
    var loaderFlag = true
    var data:ArrayList<MyEarningDocs> =  arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButtonClick.setOnClickListener {
            finishAfterTransition()
        }

        getMyWalletDetailsApi()


        binding.totalEarnings.setSafeOnClickListener {
            val bottomSheetFragment = AddMoneyWalletBottomSheet(this)
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }


        binding.swipeRefreshListener.setOnRefreshListener {
            dataLoadFlag = false
            loaderFlag = true
            page = 1
            limit = 10
            binding.swipeRefreshListener.isRefreshing = false
            getMyWalletDetailsApi()
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
                    getMyWalletDetailsApi()
                }
            }
        })





    }



    private fun getMyWalletDetailsApi() {
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
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onApiSuccess(response: MyEarningResponse, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        if (response.responseCode == 200) {
                            try {
                                binding.swipeRefreshListener.isRefreshing = false
                                binding.progressBar.visibility = View.GONE
                                pages = response.result.pages
                                page = response.result.page

                                if (!dataLoadFlag) {
                                    data.clear()
                                }
                                data.addAll(response.result.docs)
                                loaderFlag = false

                                if (data.isNotEmpty()){
                                    binding.swipeRefreshListener.isVisible = true
                                    binding.shimmerFrameLayout.isVisible = false

                                }else{
                                    binding.notFound.isVisible = true
                                    binding.swipeRefreshListener.isVisible =true
                                    binding.shimmerFrameLayout.isVisible = false
                                }


                                binding.walletBalance.text  = "R ${CommonFunctions.currencyFormatter(response.result.wallet.walletAmount.toDouble())}"

                                setAdapter()

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
                        binding.progressBar.visibility = View.GONE
                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@CustomerWalletActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.shimmerFrameLayout.isVisible = false
                        binding.notFound.isVisible = true
                        binding.swipeRefreshListener.isVisible = true
                        binding.progressBar.visibility = View.GONE
                    }

                }, "getAllPendingOrdersApi", this)


            try {


                serviceManager.getCustomerWalletTransactionApi(callBack,  page =page, limit = limit)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }



    fun setAdapter(){
        binding.walletTransaction.layoutManager = LinearLayoutManager(this)
        val adapter = CustomerWalletAdapter(this, data)
        binding.walletTransaction.adapter = adapter
    }

    override fun ozow(price:String) {
        payForWalletInApi(price)
    }

    override fun payfast(price:String) {

    }


    private fun payForWalletInApi(orderId: String) {
        if (androidextention.isOnline(this)) {
            Progresss.start(this)
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<OzowPaymentResponse> =
                ApiCallBack<OzowPaymentResponse>(object :
                    ApiResponseListener<OzowPaymentResponse> {
                    override fun onApiSuccess(response: OzowPaymentResponse, apiName: String?) {
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            val url = response.result
                            val intent = Intent(this@CustomerWalletActivity, OzowPaymentActivity::class.java)
                            intent.putExtra("websiteUrl", url)
                            startActivityForResult(intent, 1)
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        Progresss.stop()

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@CustomerWalletActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                        Toast.makeText(this@CustomerWalletActivity, "Server not responding, Try again later!!", Toast.LENGTH_SHORT).show()
                    }

                }, "PaymentApi", this)

            val jsonObject = JsonObject().apply {
                addProperty("amount", orderId)
                addProperty("cancelUrl", ServiceConstant.BASE_OWZO_URL)
                addProperty("errorUrl", ServiceConstant.BASE_OWZO_URL)
                addProperty("SuccessUrl", ServiceConstant.BASE_OWZO_URL)
                addProperty("testMode", true)
            }
            try {
                serviceManager.ozowCheckOutWalletOrderApi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == AppCompatActivity.RESULT_OK) {

                dataLoadFlag = false
                loaderFlag = true
                page = 1
                limit = 10
                getMyWalletDetailsApi()
            }
        }
    }


}