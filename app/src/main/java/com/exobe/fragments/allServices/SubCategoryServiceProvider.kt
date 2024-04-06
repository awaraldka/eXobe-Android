package com.exobe.fragments.allServices

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.servicesAdaptor.SubServicesAdaptor
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.activities.services.ServiceCampaignAddActivity
import com.exobe.utils.CommonFunctions
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.RemoveService
import com.exobe.customClicks.ServicesAddListener
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.SPServiceCategoryRequest
import com.exobe.entity.request.SPServiceDetailsArray
import com.exobe.entity.response.serviceTab.NewServicesResponseDoc
import com.exobe.entity.response.serviceTab.NewServicesResponseResponse
import com.exobe.entity.response.serviceTab.NewServicesResponseServiceArray
import com.exobe.entity.response.serviceTab.UpdateSubCategoryService
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.extension.diasplay_toast
import com.google.gson.GsonBuilder


class SubCategoryServiceProvider : Fragment(), RemoveService, ServicesAddListener {

    var latestProductList: ArrayList<NewServicesResponseDoc> = ArrayList()
    var filterData: List<NewServicesResponseServiceArray> = ArrayList()
    lateinit var recyclerViewProduct: RecyclerView

    //    lateinit var ProductLatestAdapter: sub_categoryAdaptor
    lateinit var subServicesAdaptor: SubServicesAdaptor
    lateinit var title: TextView
    lateinit var back: ImageView
    lateinit var cart: ImageView
    lateinit var filter: ImageView

    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var MenuClick: ImageView
    lateinit var mainHeader: RelativeLayout
    lateinit var add: Button
    lateinit var update: Button
    lateinit var deals: LinearLayout
    var id = ""
    var HeaderTitle = ""
    lateinit var Datanotfound: LinearLayout
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null

    var price = ""
    var description = ""
    var isScreenFor = ""
    var categoryType = ""
    var checkSelection = false
    var showButton = false
    var Data: ArrayList<SPServiceDetailsArray> = ArrayList()
    private var apiCallFlag = true

    lateinit var normaltext:TextView
    lateinit var addCampaign:TextView
    lateinit var buttonUI:LinearLayout

    lateinit var progressbar_subcategory: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =
            inflater.inflate(R.layout.fragment_sub_category_service_provider, container, false)
//        setToolbar()
        recyclerViewProduct = view.findViewById(R.id.recyclerview)
        Datanotfound = view.findViewById(R.id.Datanotfound)!!
        normaltext = view.findViewById(R.id.normaltext)!!
        buttonUI = view.findViewById(R.id.buttonUI)!!
        addCampaign = view.findViewById(R.id.addCampaign)!!

        title = activity?.findViewById(R.id.title)!!
        back = activity?.findViewById(R.id.back)!!
        MenuClick = activity?.findViewById(R.id.MenuClick)!!
        deals = view.findViewById(R.id.deals)!!
        update = view.findViewById(R.id.update)!!
        add = view.findViewById(R.id.add)!!
        progressbar_subcategory = view.findViewById(R.id.progressbar_subcategory)!!
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!

        if (requireArguments().getString("id") != null) {
            id = requireArguments().getString("id").toString()
        }

        arguments?.getString("description")?.let { description = it }
        arguments?.getString("isScreenFor")?.let { isScreenFor = it.lowercase().trim() }
        arguments?.getString("categoryType")?.let { categoryType = it }

        if (requireArguments().getString("title") != null) {
            HeaderTitle = requireArguments().getString("title").toString()
            title.text = HeaderTitle
        }

        // Standard Services

        addCampaign.setSafeOnClickListener {
            val intent =  Intent(requireContext(),ServiceCampaignAddActivity::class.java)
            intent.putExtra("categoryName", HeaderTitle)
            intent.putExtra("categoryId", id)
            startActivity(intent)
        }



        CommonFunctions.setDescriptionFun(requireContext(),description,normaltext)

        back.visibility = View.VISIBLE
        MenuClick.visibility = View.GONE

        back.setSafeOnClickListener {
            parentFragmentManager.popBackStack()

        }

        add.setSafeOnClickListener {
            filterData =  latestProductList.flatMap { it.serviceArray }.filter { it.isSelected && it.price != "0" }
            if (filterData.isEmpty()) {
                androidextention.alertBox("Please select at least one service.", requireContext())
            } else {
                updateSubcategory()
            }
        }

        deals.setSafeOnClickListener {
            if(showButton) {
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.service_main_container, AddDealsServiceProvider(HeaderTitle, ""), "adddeals")?.addToBackStack(null)?.commit()
            } else {
                androidextention.alertBox("Please add at least one service.", requireContext())
            }
        }

        update.setSafeOnClickListener {

            updateSubcategory()

        }


        return view
    }

    override fun onResume() {
        super.onResume()
        if(apiCallFlag) {
            GetDetails()
            apiCallFlag = false
        } else {
            if (androidextention.isOnline(requireContext())) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                add.visibility = View.VISIBLE
                buttonUI.isVisible = isScreenFor.lowercase() == "standard services"

                setServicesAdapter(latestProductList)
            } else {
                recyclerViewProduct.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
            }
        }
    }


    fun GetDetails() {

        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            normaltext.isVisible = false
            lottie!!.initLoader(false)
            progressbar_subcategory.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<NewServicesResponseResponse> =
                ApiCallBack<NewServicesResponseResponse>(object :
                    ApiResponseListener<NewServicesResponseResponse> {
                    override fun onApiSuccess(
                        response: NewServicesResponseResponse,
                        apiName: String?
                    ) {
                        progressbar_subcategory.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                normaltext.isVisible = true
                                Data.clear()
                                latestProductList.clear()
                                latestProductList = response.result.docs
                                if (latestProductList.size > 0) {
                                    buttonUI.isVisible =  isScreenFor.lowercase() == "standard services"

                                    for (i in 0 until latestProductList.size)
                                        for (j in 0 until latestProductList[i].serviceArray.size) {
                                            if (latestProductList[i].serviceArray[j].isSelected) {
                                                showButton = true
                                            }
                                        }
                                    if (showButton) {
                                        add.visibility = View.GONE
                                        update.visibility = View.VISIBLE
                                    } else {
                                        add.visibility = View.VISIBLE
                                        update.visibility = View.GONE
                                    }

                                    setServicesAdapter(latestProductList)

                                } else {
                                    Datanotfound.visibility = View.VISIBLE
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_subcategory.visibility = View.GONE
                        Datanotfound.visibility = View.VISIBLE
                        normaltext.isVisible = true
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar_subcategory.visibility = View.GONE
                        Datanotfound.visibility = View.VISIBLE
                        normaltext.isVisible = true
                    }
                }, "getDetails", requireContext())
            try {
                serviceManager.serviceListByCategory(callBack, id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            recyclerViewProduct.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }


    fun setServicesAdapter(docs: ArrayList<NewServicesResponseDoc>) {
        recyclerViewProduct.layoutManager = LinearLayoutManager(context)
        subServicesAdaptor = SubServicesAdaptor(requireContext(), docs, Data, this, this,categoryType)
        recyclerViewProduct.adapter = subServicesAdaptor

    }

    private fun updateSubcategory() {

        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar_subcategory.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<UpdateSubCategoryService> =
                ApiCallBack<UpdateSubCategoryService>(object :
                    ApiResponseListener<UpdateSubCategoryService> {
                    override fun onApiSuccess(
                        response: UpdateSubCategoryService,
                        apiName: String?
                    ) {
                        progressbar_subcategory.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                Toast.makeText(requireContext(), response.responseMessage, Toast.LENGTH_SHORT).show()
                                parentFragmentManager.popBackStack()

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_subcategory.visibility = View.GONE

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
//
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar_subcategory.visibility = View.GONE
                    }
                }, "updateSubcategory", requireContext())
            try {

                val serviceCategoryRequest = SPServiceCategoryRequest()
                serviceCategoryRequest.categoryId = id


                filterData =  latestProductList.flatMap { it.serviceArray }.filter { it.isSelected && it.price != "0" }
                val finalData: ArrayList<SPServiceDetailsArray> = ArrayList()


                for (i in filterData.indices) {
                    finalData.add(SPServiceDetailsArray(filterData[i].price, filterData[i].serviceDetails.id, filterData[i].isSelected))
                }

                serviceCategoryRequest.serviceDetailsArray = finalData

                serviceManager.updateServiceUser(callBack, serviceCategoryRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            recyclerViewProduct.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    override fun removeService(position: Int, _id: String) {
        checkSelection = true
    }

    override fun amountErrorService(position: Int, _id: String) {
    }

    override fun addServices(id: String, price: String, isRemove: Boolean, isUpdate: Boolean, isSelected: Boolean) {


    }

    override fun removeServices(position: Int, id: String, isDelete: Boolean, isSelected: Boolean, isUpdate: Boolean) {

    }



}