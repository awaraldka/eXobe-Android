package com.exobe.activities.services

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.exobe.adaptor.servicesAdaptor.ServiceConfigAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.activities.retailer.NewUploadDocumentActivity
import com.exobe.utils.Progresss
import com.exobe.androidextention
import com.exobe.databinding.ActivityServiceConfigurationBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.ServiceFeeConfigRequest
import com.exobe.entity.response.CommonResponseForAll
import com.exobe.entity.response.ServiceConfigCategories
import com.exobe.entity.response.ServiceConfigResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.extension.displayToast
import com.google.gson.GsonBuilder

class ServiceConfigurationActivity : AppCompatActivity() {

    private lateinit var binding :ActivityServiceConfigurationBinding

    var categories :List<ServiceConfigCategories> = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceConfigurationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.attributes.windowAnimations = R.style.Fade

        serviceConfigApi()

        binding.ProceedButton.setOnClickListener {



            val filterData = categories.filter { parent ->
                parent.subCategories.any { child ->
                    child.isSelected && child.services.any { subChild ->
                        subChild.isSelected && subChild.fees.isNotEmpty()
                    }
                }
            }


            if (filterData.isEmpty()){
                displayToast("Please select atLeast one service category and sub category")
                return@setOnClickListener
            }




            val dataRequest = categories.flatMap { category ->
                category.subCategories
                    .filter { it.isSelected }
                    .flatMap { subCategory ->
                        subCategory.services
                            .filter { it.isSelected }
                            .mapNotNull { service ->
                                val categoryId = category.categoryData._id
                                val subCategoryId = subCategory.subCategoryData.id
                                if (categoryId != null) {
                                    ServiceFeeConfigRequest(
                                        serviceId = service.id,
                                        categoryId = categoryId,
                                        subCategoryId = subCategoryId,
                                        price = service.fees,
                                        categoryType =category.categoryData.categoryType,
                                        categoryEnum = service.categoryEnum,
                                        isSelected = service.isSelected,
                                        pickupFeeId = service.pickupFeeId,
                                        storageFeeId = service.storageFeeId,
                                        deliveryFeeId = service.deliveryFeeId
                                    )
                                } else {
                                    null
                                }
                            }
                    }
            }

            serviceConfigAddApi(dataRequest)



        }




    }




    private fun serviceConfigApi() {
        if (androidextention.isOnline(this)) {
            Progresss.start(this)
            binding.ProceedButton.isVisible = false
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<ServiceConfigResponse> =
                ApiCallBack(object : ApiResponseListener<ServiceConfigResponse> {
                    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
                    override fun onApiSuccess(response: ServiceConfigResponse, apiName: String?) {
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            try {
                                categories = response.result.categories
                                binding.ProceedButton.isVisible = response.result.categories.isNotEmpty()
                                setDataAdapter()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()
                        Progresss.stop()
                        binding.ProceedButton.isVisible = false
                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@ServiceConfigurationActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                        binding.ProceedButton.isVisible = false
                    }

                }, apiName = "serviceConfigApi", this)


            try {

                serviceManager.allServiceListApi(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }


    private fun setDataAdapter() {
        binding.serviceCustomization.layoutManager = LinearLayoutManager(this)
        val adapter = ServiceConfigAdapter(this,categories)
        binding.serviceCustomization.adapter = adapter
    }




    private fun serviceConfigAddApi(dataRequest: List<ServiceFeeConfigRequest>) {
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
                                val intent = Intent(this@ServiceConfigurationActivity, NewUploadDocumentActivity::class.java)
                                intent.putExtra("flag", "Service_Provider")
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
                            androidextention.alertBox(pojo.responseMessage, this@ServiceConfigurationActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, apiName = "updateServiceUserApi", this)


            try {

                serviceManager.updateServiceUserApi(callBack, request = dataRequest, flag = 2)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }

}