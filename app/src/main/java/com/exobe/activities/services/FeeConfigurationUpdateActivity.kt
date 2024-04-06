package com.exobe.activities.services

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.exobe.adaptor.servicesAdaptor.FeeConfigFieldEntityUpdateAdapter
import com.exobe.adaptor.servicesAdaptor.FeeConfigUpdateAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.Progresss
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.customClicks.UpdateFeeConfig
import com.exobe.databinding.ActivityFeeConfigurationUpdateBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.CommonResponseForAll
import com.exobe.entity.response.FeeListResponse
import com.exobe.entity.response.FeeListResult
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.extension.displayToast
import com.google.gson.GsonBuilder

class FeeConfigurationUpdateActivity : AppCompatActivity(),UpdateFeeConfig {

    private lateinit var binding: ActivityFeeConfigurationUpdateBinding
    lateinit var adapter:FeeConfigUpdateAdapter
    lateinit var adapterFieldEntity:FeeConfigFieldEntityUpdateAdapter


    var userType = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeeConfigurationUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.attributes.windowAnimations = R.style.Fade


        userType = SavedPrefManager.getStringPreferences(this,SavedPrefManager.USER_TYPE).toString()



        binding.backButtonClick.setOnClickListener{
            finishAfterTransition()
        }

        binding.swipeRefresh.setOnRefreshListener {
            when (userType) {


                "DELIVERY_PARTNER" -> {
                    listFeeViewDeliveryDriverApi()
                }
                "FIELD_ENTITY" -> {
                    listFeeViewFieldEntityApi()
                }
                "PICKUP_PARTNER" -> {
                    listFeeViewPickUpDriverApi()
                }

            }

            binding.swipeRefresh.isRefreshing =false
        }


        when (userType) {


            "DELIVERY_PARTNER" -> {
                listFeeViewDeliveryDriverApi()
            }
            "FIELD_ENTITY" -> {
                listFeeViewFieldEntityApi()
            }
            "PICKUP_PARTNER" -> {
                listFeeViewPickUpDriverApi()
            }

        }





    }


    private fun listFeeViewPickUpDriverApi() {
        if (androidextention.isOnline(this)) {
            binding.shimmerFrameLayout.isVisible = true
            binding.shimmerFrameLayout.startShimmerAnimation()
            binding.swipeRefresh.isVisible = false

            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<FeeListResponse> =
                ApiCallBack(object : ApiResponseListener<FeeListResponse> {
                    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
                    override fun onApiSuccess(response: FeeListResponse, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.shimmerFrameLayout.isVisible = false
                        if (response.responseCode == 200) {
                            try {
                                if (response.result.size > 0) {
                                    binding.notFound.visibility = View.GONE
                                    binding.swipeRefresh.isVisible = true

                                } else {
                                    binding.nestedScroll.isVisible = false
                                    binding.notFound.visibility = View.VISIBLE
                                }


                                setAdaptor(response.result)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {

                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.shimmerFrameLayout.isVisible = false
                        binding.swipeRefresh.isVisible = true
                        binding.notFound.isVisible = true

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()
                        Progresss.stop()
                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@FeeConfigurationUpdateActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.shimmerFrameLayout.isVisible = false
                        binding.swipeRefresh.isVisible = true
                        binding.notFound.isVisible = true
                    }

                }, apiName = "listFeeViewApi", this)


            try {

                serviceManager.listUserFeeApi(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }
    private fun listFeeViewDeliveryDriverApi() {
        if (androidextention.isOnline(this)) {
            binding.shimmerFrameLayout.isVisible = true
            binding.shimmerFrameLayout.startShimmerAnimation()
            binding.swipeRefresh.isVisible = false

            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<FeeListResponse> =
                ApiCallBack(object : ApiResponseListener<FeeListResponse> {
                    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
                    override fun onApiSuccess(response: FeeListResponse, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.shimmerFrameLayout.isVisible = false
                        if (response.responseCode == 200) {
                            try {
                                if (response.result.size > 0) {
                                    binding.notFound.visibility = View.GONE
                                    binding.swipeRefresh.isVisible = true

                                } else {
                                    binding.nestedScroll.isVisible = false
                                    binding.notFound.visibility = View.VISIBLE
                                }


                                setAdaptor(response.result)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {

                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.shimmerFrameLayout.isVisible = false
                        binding.swipeRefresh.isVisible = true
                        binding.notFound.isVisible = true

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()
                        Progresss.stop()
                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@FeeConfigurationUpdateActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.shimmerFrameLayout.isVisible = false
                        binding.swipeRefresh.isVisible = true
                        binding.notFound.isVisible = true
                    }

                }, apiName = "listUserFeeByUserIdAPI", this)


            try {

                serviceManager.listUserFeeByUserIdAPI(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }


    private fun listFeeViewFieldEntityApi() {
        if (androidextention.isOnline(this)) {
            binding.shimmerFrameLayout.isVisible = true
            binding.shimmerFrameLayout.startShimmerAnimation()
            binding.swipeRefresh.isVisible = false

            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<FeeListResponse> =
                ApiCallBack(object : ApiResponseListener<FeeListResponse> {
                    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
                    override fun onApiSuccess(response: FeeListResponse, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.shimmerFrameLayout.isVisible = false
                        if (response.responseCode == 200) {
                            try {
                                if (response.result.size > 0) {
                                    binding.notFound.visibility = View.GONE
                                    binding.swipeRefresh.isVisible = true

                                } else {
                                    binding.nestedScroll.isVisible = false
                                    binding.notFound.visibility = View.VISIBLE
                                }


                                setAdaptorFieldEntity(response.result)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {

                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.shimmerFrameLayout.isVisible = false
                        binding.swipeRefresh.isVisible = true
                        binding.notFound.isVisible = true

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()
                        Progresss.stop()
                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@FeeConfigurationUpdateActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.shimmerFrameLayout.isVisible = false
                        binding.swipeRefresh.isVisible = true
                        binding.notFound.isVisible = true
                    }

                }, apiName = "listFeeViewFieldEntityApi", this)


            try {

                serviceManager.listUserStorageFeeAPI(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }


    fun setAdaptor(result: ArrayList<FeeListResult>) {
        binding.FeeConfigRecycler.layoutManager = LinearLayoutManager(this)
        adapter = FeeConfigUpdateAdapter(this, result,click= this)
        binding.FeeConfigRecycler.adapter = adapter
    }


    fun setAdaptorFieldEntity(result: ArrayList<FeeListResult>) {
        binding.FeeConfigRecycler.layoutManager = LinearLayoutManager(this)
        adapterFieldEntity= FeeConfigFieldEntityUpdateAdapter(this, result,click = this)
        binding.FeeConfigRecycler.adapter = adapterFieldEntity
    }

    override fun updateFeePickUpDriver(id: String, fee: String) {
        updateFeeApi(id = id, fee = fee)
    }

    override fun updateFeeDeliveryDriver(id: String, fee: String, standard: String) {
        updateFeeDeliverDriverApi(id = id, standardFee = standard, deliveryFee = fee)
    }

    override fun updateFeeFieldEntity(
        id: String,
        dailyFee: String,
        monthlyFee: String,
        weeklyFee: String,
        quarterlyFee: String,
        minimumFee: String,
        maximumFee: String
    ) {
        updateFeeFieldEntityApi(id = id, dailyFee =dailyFee , monthlyFee =monthlyFee, weeklyFee = weeklyFee
            , quarterlyFee= quarterlyFee, minimumFee=minimumFee, maximumFee=maximumFee )
    }

    private fun updateFeeApi(id: String, fee: String) {
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
                                displayToast("Fee updated successfully.")

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
                            androidextention.alertBox(pojo.responseMessage, this@FeeConfigurationUpdateActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, apiName = "rejectOrdersApi", this)


            try {

                serviceManager.updateUserFeeDetail(callBack,userType="PICKUP_PARTNER", _id =id, pickupFee =  fee)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }



    private fun updateFeeDeliverDriverApi(id: String,standardFee:String,deliveryFee:String) {
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
                                displayToast("Fee updated successfully.")

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
                            androidextention.alertBox(pojo.responseMessage, this@FeeConfigurationUpdateActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, apiName = "rejectOrdersApi", this)


            try {

                serviceManager.updateFeeDetailDeliveryDriverApi(callBack, _id =id, standardFee =standardFee , deliveryFee =deliveryFee)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }


    private fun updateFeeFieldEntityApi( id: String, dailyFee: String, monthlyFee: String, weeklyFee: String, quarterlyFee: String, minimumFee: String, maximumFee: String) {
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
                                displayToast("Fee updated successfully.")

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
                            androidextention.alertBox(pojo.responseMessage, this@FeeConfigurationUpdateActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, apiName = "rejectOrdersApi", this)


            try {

                serviceManager.updateStorageFeeDetailApi(callBack, _id =id, dailyFee =dailyFee , monthlyFee =monthlyFee,
                    weeklyFee= quarterlyFee, quarterlyFee = weeklyFee,minimumFee= maximumFee , maximumFee = minimumFee)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }



}