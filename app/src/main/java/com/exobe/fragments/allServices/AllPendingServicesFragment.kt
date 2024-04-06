package com.exobe.fragments.allServices

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.exobe.activities.services.ServicePage
import com.exobe.adaptor.servicesAdaptor.AllPendingServicesAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.androidextention
import com.exobe.customClicks.CommonListenerServices
import com.exobe.databinding.FragmentAllPendingServicesBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.serviceTab.GetAllOrdersCommonDocs
import com.exobe.entity.response.serviceTab.GetAllOrdersCommonResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder


class AllPendingServicesFragment(val viewServiceClick: CommonListenerServices,var pageRefresh :Boolean) : Fragment() {

    private var _binding: FragmentAllPendingServicesBinding? =  null
    private val binding get() = _binding!!

    var page = 1
    var limit = 10
    var pages = 0
    lateinit var adapter:AllPendingServicesAdapter
    var dataLoadFlag = true
    var loaderFlag = true



    var data = ArrayList<GetAllOrdersCommonDocs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentAllPendingServicesBinding.inflate(layoutInflater, container, false)


        if (ServicePage.isRefreshed){
            page = 1
            limit = 10
            loaderFlag = true
            data.clear()
            dataLoadFlag = false
            ServicePage.isRefreshed = false
            getAllPendingOrdersApi()

        }else{
            getAllPendingOrdersApi()
        }




        binding.swipeToRefreshPendingServices.setOnRefreshListener {
            page = 1
            limit = 10
            loaderFlag = true
            dataLoadFlag = false
            getAllPendingOrdersApi()

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

                    getAllPendingOrdersApi()
                }
            }
        })


        return binding.root
    }


    private fun getAllPendingOrdersApi() {
        if (androidextention.isOnline(requireContext())) {
            if (loaderFlag) {
                binding.notFound.isVisible = false
                binding.shimmerFrameLayout.isVisible = true
                binding.shimmerFrameLayout.startShimmerAnimation()
                binding.swipeToRefreshPendingServices.isVisible = false
            }
            val serviceManager = ServiceManager(requireContext())
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
                            androidextention.alertBox(pojo.responseMessage, requireContext())

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

                }, "getAllPendingOrdersApi", requireContext())

          
            try {
                val userType = when(ServicePage.isSelectedFilter){
                    "Fulfillment Services" -> {"FIELD_ENTITY"}
                    "Transportation Services" -> {"TRANS"}
                    else -> {""}
                }
                serviceManager.getAllOrdersApi(callBack, orderStatus = "PENDING", page =page, limit = limit, userType = userType)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }
    }
    


    fun setAdaptor() {
        binding.PendingRecycler.layoutManager = LinearLayoutManager(context)
        adapter = AllPendingServicesAdapter(requireContext(), data, viewServiceClick)
        binding.PendingRecycler.adapter = adapter
    }



}