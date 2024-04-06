package com.exobe.activities.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.adaptor.customeradaptor.CampaignParticipationAdapter
import com.exobe.androidextention
import com.exobe.databinding.ActivityCampaignListBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.customer.CampaignListDocs
import com.exobe.entity.response.customer.CampaignListResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.utils.Progresss

class CampaignListActivity : AppCompatActivity() {
    lateinit var binding:ActivityCampaignListBinding

    private var page= 1
    private var pages= 0
    private var limit = 10
    private var campaignOn = "PRODUCT"
    private lateinit var adapterCampaign:CampaignParticipationAdapter
    private var dataLoadFlag = false
    private var loaderFlag = true
    private var dataCampaign = ArrayList<CampaignListDocs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCampaignListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.attributes.windowAnimations = R.style.Fade

        binding.backButtonClick.setSafeOnClickListener {
            finishAfterTransition()
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
            setTabsClick()
            binding.pullToRefresh.isRefreshing = false
        }



        binding.productClick.setOnClickListener {
            campaignOn = "PRODUCT"
            setTabsClick()
        }


        binding.serviceClick.setOnClickListener {
            campaignOn = "SERVICE"
            setTabsClick()
        }


    }


    private fun setTabsClick(){
        page = 1
        limit= 10
        dataLoadFlag = false
        loaderFlag = true

        when(campaignOn) {
            "SERVICE" ->{
                binding.serviceClick.setBackgroundResource(R.drawable.login_button)
                binding.productClick.setBackgroundResource(R.drawable.red_border)
                binding.txtProduct.setTextColor(ContextCompat.getColor(this,R.color.red))
                binding.txtService.setTextColor(ContextCompat.getColor(this,R.color.white))
            }

            else ->{
                binding.productClick.setBackgroundResource(R.drawable.login_button)
                binding.serviceClick.setBackgroundResource(R.drawable.red_border)
                binding.txtProduct.setTextColor(ContextCompat.getColor(this,R.color.white))
                binding.txtService.setTextColor(ContextCompat.getColor(this,R.color.red))
            }
        }

        getCampaignListApi()

    }










    private fun getCampaignListApi() {
        if (androidextention.isOnline(this)) {
            if (loaderFlag){
                binding.notFound.isVisible = false
                Progresss.start(this)
            }

            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<CampaignListResponse> =
                ApiCallBack(object :
                    ApiResponseListener<CampaignListResponse> {
                    override fun onApiSuccess(response: CampaignListResponse, apiName: String?) {

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


                }, "getCampaignListApi", this)

            try {
                serviceManager.getIntrestedPriceList(callBack,page, limit, campaignOn)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



    private fun setAdapterList(){
        binding.listCampaign.layoutManager =  LinearLayoutManager(this)
        adapterCampaign = CampaignParticipationAdapter(this,dataCampaign,campaignOn)
        binding.listCampaign.adapter =  adapterCampaign
    }


}