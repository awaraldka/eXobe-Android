package com.exobe.activities.retailer

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.activities.services.ServiceCampaignAddActivity
import com.exobe.adaptor.CampaignAddedRetailerAdapter
import com.exobe.androidextention
import com.exobe.customClicks.CampaignEdit
import com.exobe.databinding.ActivityAddedCampaignBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.AddedCampaignListDocs
import com.exobe.entity.response.AddedCampaignListResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.extension.displayToast
import com.exobe.utils.Progresss

class AddedCampaignActivity : AppCompatActivity(), CampaignEdit {

    lateinit var binding: ActivityAddedCampaignBinding
    private var page= 1
    private var pages= 0
    private var limit = 10
    private var dataLoadFlag = false
    private var loaderFlag = true
    private var dataCampaign = ArrayList<AddedCampaignListDocs>()
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private lateinit var adapterCampaign: CampaignAddedRetailerAdapter
    private var isUser = ""
    private var campaignOn = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityAddedCampaignBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.attributes.windowAnimations = R.style.Fade

        intent.getStringExtra("isUser")?.let { isUser = it  }


        binding.backButtonClick.setSafeOnClickListener {
            finishAfterTransition()
        }

        campaignOn = if (isUser =="Service") "SERVICE" else "PRODUCT"

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val addedCoin = data?.getBooleanExtra("editCampaign",false)
                displayToast("Campaign updated successfully.")
                if (addedCoin == true){
                    page = 1
                    pages = 0
                    limit = 10
                    dataLoadFlag = false
                    loaderFlag = true
                    getCampaignListApi()
                }
            }
        }




        getCampaignListApi()


        binding.nestedScrolling.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {

                dataLoadFlag = true
                page++
                binding.progressBarScroll.visibility = View.VISIBLE
                if (page > pages) {
                    binding.progressBarScroll.visibility = View.GONE
                } else {
                    getCampaignListApi()
                }
            }
        })

        binding.pullToRefresh.setOnRefreshListener {
            page = 1
            pages = 0
            limit = 10
            dataLoadFlag = false
            loaderFlag = true
            getCampaignListApi()
            binding.pullToRefresh.isRefreshing = false
        }




    }






    private fun getCampaignListApi() {
        if (androidextention.isOnline(this)) {
            if (loaderFlag){
                Progresss.start(this)
            }

            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<AddedCampaignListResponse> =
                ApiCallBack(object :
                    ApiResponseListener<AddedCampaignListResponse> {
                    override fun onApiSuccess(response: AddedCampaignListResponse, apiName: String?) {

                        Progresss.stop()

                        if (response.responseCode == 200) {
                            try {

                                loaderFlag = false
                                if (!dataLoadFlag) {
                                    dataCampaign.clear()
                                }
                                if (response.result.docs.isNotEmpty()) {
                                    binding.nestedScrolling.isVisible = true
                                    binding.notFound.isVisible = false
                                    dataCampaign.addAll(response.result.docs)
                                    pages = response.result.pages
                                    page = response.result.page
                                    setAdapterList()
                                } else {
                                    binding.nestedScrolling.isVisible = dataCampaign.isEmpty()
                                    binding.notFound.isVisible = dataCampaign.isEmpty()

                                }


                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        Progresss.stop()
                        binding.nestedScrolling.isVisible = dataCampaign.isEmpty()
                        binding.notFound.isVisible = dataCampaign.isEmpty()

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                        binding.nestedScrolling.isVisible = dataCampaign.isEmpty()
                        binding.notFound.isVisible = dataCampaign.isEmpty()
                    }


                }, "listCampaignApi", this)

            try {

                serviceManager.listCampaignApi(callBack,page, limit,campaignOn=campaignOn)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setAdapterList() {
        binding.listCampaign.layoutManager =  LinearLayoutManager(this)
        adapterCampaign = CampaignAddedRetailerAdapter(this,dataCampaign,campaignOn,this)
        binding.listCampaign.adapter =  adapterCampaign
    }

    override fun editCampaignClick(productId: String, id: String) {
        val intent = Intent(this, StartCampaignRetailerActivity::class.java)
        intent.putExtra("edit", true)
        intent.putExtra("productId", productId)
        intent.putExtra("id", id)
        startForResult.launch(intent)


    }


}