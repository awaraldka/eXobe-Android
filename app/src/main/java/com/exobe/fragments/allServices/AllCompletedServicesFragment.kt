package com.exobe.fragments.allServices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.exobe.activities.services.ServicePage
import com.exobe.adaptor.servicesAdaptor.AllCompletedServicesAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.androidextention
import com.exobe.customClicks.CommonListenerServices
import com.exobe.databinding.FragmentAllCompletedServicesBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.serviceTab.GetAllOrdersCommonDocs
import com.exobe.entity.response.serviceTab.GetAllOrdersCommonResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder


class AllCompletedServicesFragment(private val viewServiceClick: CommonListenerServices, var pageRefresh :Boolean) : Fragment() {

    private var _binding: FragmentAllCompletedServicesBinding? = null
    private val binding get() = _binding!!

    var data = ArrayList<GetAllOrdersCommonDocs>()

    var page = 1
    var limit = 10
    var pages = 0
    lateinit var adapter: AllCompletedServicesAdapter
    var dataLoadFlag = true
    var loaderFlag = true

    var userType = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllCompletedServicesBinding.inflate(layoutInflater, container, false)

        userType = when(ServicePage.isSelectedFilter){
            "Fulfillment Services" -> {"FIELD_ENTITY"}
            "Transportation Services" -> {"TRANS"}
            else -> {"DELIVERY_PARTNER"}
        }

        if (ServicePage.isRefreshed){
            page = 1
            limit = 10
            loaderFlag = true
            data.clear()
            dataLoadFlag = true
            ServicePage.isRefreshed = false
            getAllDeliveredOrdersApi()

        }else{
            getAllDeliveredOrdersApi()
        }



        binding.swipeToRefreshCompletedServices.setOnRefreshListener {
            page = 1
            limit = 10
            loaderFlag = true
            dataLoadFlag = true
            getAllDeliveredOrdersApi()

            binding.swipeToRefreshCompletedServices.isRefreshing = false
        }


        binding.nestedscrollviewPs.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                dataLoadFlag = false
                loaderFlag = false
                page++
                binding.paginationProgressbarPs.visibility = View.VISIBLE
                if (page > pages) {
                    binding.paginationProgressbarPs.visibility = View.GONE
                } else {

                    getAllDeliveredOrdersApi()
                }
            }
        })




        return binding.root
    }


    private fun getAllDeliveredOrdersApi() {
        if (androidextention.isOnline(requireContext())) {
            if (loaderFlag) {
                binding.notFound.isVisible = false
                binding.shimmerFrameLayout.isVisible = true
                binding.shimmerFrameLayout.startShimmerAnimation()
                binding.swipeToRefreshCompletedServices.isVisible = false
            }
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<GetAllOrdersCommonResponse> =
                ApiCallBack(object : ApiResponseListener<GetAllOrdersCommonResponse> {
                    override fun onApiSuccess(response: GetAllOrdersCommonResponse, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.paginationProgressbarPs.visibility = View.GONE
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
                                    binding.swipeToRefreshCompletedServices.isVisible = true
                                    binding.shimmerFrameLayout.isVisible = false



                                } else {
                                    binding.notFound.isVisible = true
                                    binding.swipeToRefreshCompletedServices.isVisible = true
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
                        binding.swipeToRefreshCompletedServices.isVisible = true
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
                        binding.swipeToRefreshCompletedServices.isVisible = true
                        binding.paginationProgressbarPs.visibility = View.GONE
                    }

                }, "getAllDeliveredOrdersApi", requireContext())


            try {

                serviceManager.getAllOrdersApi(
                    callBack,
                    orderStatus = "COMPLETED",
                    page = page,
                    limit = limit,
                    userType = userType
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }
    }


    fun setAdaptor() {
        binding.CompletedRecycler.layoutManager = LinearLayoutManager(context)
        adapter = AllCompletedServicesAdapter(requireContext(), data, viewServiceClick)
        binding.CompletedRecycler.adapter = adapter
    }



}