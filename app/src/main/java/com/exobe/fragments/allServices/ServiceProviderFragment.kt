package com.exobe.fragments.allServices

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.exobe.adaptor.servicesAdaptor.services_spAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.categoryserviceClick
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.Services_ServiceproviderDocResponce
import com.exobe.entity.response.Services_ServiceproviderResponce
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlin.collections.ArrayList

class ServiceProviderFragment : Fragment(), categoryserviceClick {
    lateinit var back: ImageView
    lateinit var title: TextView
    lateinit var recyclerview: RecyclerView
    var MenuClick: ImageView? = null
    var itemList: ArrayList<Services_ServiceproviderDocResponce> = ArrayList()
    lateinit var mContext: Context
    lateinit var adapter1: services_spAdapter
    lateinit var progressbar: ProgressBar

    //added tab handle
    lateinit var home: ImageView
    lateinit var homeRed: ImageView
    lateinit var homeText: TextView

    lateinit var service_red_sp: ImageView
    lateinit var service_sp: ImageView
    lateinit var TVservuce_sp: TextView


    lateinit var settings: ImageView
    lateinit var settingsRed: ImageView
    lateinit var settingsText: TextView
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null

    lateinit var edit_deal: LinearLayout
    private var apiCallFlag = true
    var isFrom = ""
    var categoryType = ""
    var isScreenFor = ""



    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_services_serviceprovider, container, false)
        mContext = activity?.applicationContext!!

        arguments?.getString("isFrom")?.let{ isFrom = it}
        arguments?.getString("categoryType")?.let{ categoryType = it}
        arguments?.getString("isScreenFor")?.let{ isScreenFor = it}
        handleTab()

        title = activity?.findViewById(R.id.title)!!
        back = activity?.findViewById(R.id.back)!!
        MenuClick = activity?.findViewById(R.id.MenuClick)!!
        recyclerview = view?.findViewById(R.id.recyclerview)!!
        progressbar = view.findViewById(R.id.progressbar)!!
        lottie = activity?.findViewById(R.id.lottie)!!
        edit_deal = activity?.findViewById(R.id.edit_deal)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!

        title.text = "Services"
        back.isVisible = isFrom == "SelectedServices"
        MenuClick!!.isVisible = isFrom != "SelectedServices"
        edit_deal.visibility = View.GONE

        back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }



        return view
    }

    override fun onResume() {
        super.onResume()
        if(apiCallFlag) {
            serviceService_providerApi()
            apiCallFlag = false
        } else {
            if (androidextention.isOnline(mContext)) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                setAdapter(itemList)
            } else {
                recyclerview.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                if (isFrom == "SelectedServices"){
                    parentFragmentManager.popBackStack()
                }else{
                    val fm: FragmentManager = requireActivity().supportFragmentManager

                    for (i in 0 until fm.backStackEntryCount){
                        fm.popBackStack()
                    }
                }



            }
        })

    }


    private fun setAdapter(itemList: ArrayList<Services_ServiceproviderDocResponce>) {
        recyclerview.layoutManager = GridLayoutManager(mContext, 2)
        adapter1 = services_spAdapter(mContext, itemList, this)
        recyclerview.adapter = adapter1
    }


    fun serviceService_providerApi() {

        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Services_ServiceproviderResponce> =
                ApiCallBack<Services_ServiceproviderResponce>(object :
                    ApiResponseListener<Services_ServiceproviderResponce> {


                    override fun onApiSuccess(
                        response: Services_ServiceproviderResponce,
                        apiName: String?
                    ) {
                        progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                itemList.clear()
                                itemList =
                                    response.result?.docs as ArrayList<Services_ServiceproviderDocResponce>
                                setAdapter(itemList)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        } else {
                            response.responseMessage?.let {
                                androidextention.alertBox(
                                    it,
                                    requireContext()
                                )
                            }
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar.visibility = View.GONE

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, requireContext())

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE

                    }

                }, "ServicesServiceAva", mContext)

            val jsonObject = JsonObject()
//            jsonObject.addProperty("search", "plumber")

            try {
                serviceManager.Services_Service_provider_Api(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            recyclerview.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    override fun viewsubcategoryservice(id: String, title: String,description:String) {
        val bundle = Bundle()
        bundle.putString("id", id)
        bundle.putString("title", title)
        bundle.putString("description", description)
        bundle.putString("categoryType", categoryType)
        bundle.putString("isScreenFor", "standard services")

        val obj = SubCategoryServiceProvider()
        obj.arguments = bundle

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.service_main_container, obj, "subcategory")?.addToBackStack(null)
            ?.commit()
    }

    private fun handleTab() {

        homeRed = activity?.findViewById(R.id.home_grey_sp)!!
        home = activity?.findViewById(R.id.home_sp)!!
        homeText = activity?.findViewById(R.id.TVhome_sp)!!

        settings = activity?.findViewById(R.id.setting_grey_sp)!!
        settingsRed = activity?.findViewById(R.id.setting_red_sp)!!
        settingsText = activity?.findViewById(R.id.TVsetting_sp)!!

        service_red_sp = activity?.findViewById(R.id.service_red_sp)!!
        service_sp = activity?.findViewById(R.id.service_sp)!!
        TVservuce_sp = activity?.findViewById(R.id.TVservuce_sp)!!

        home.visibility = View.VISIBLE
        homeRed.visibility = View.GONE
        homeText.setTextColor(Color.parseColor("#FFFFFF"))

        service_sp.visibility = View.GONE
        service_red_sp.visibility = View.VISIBLE
        TVservuce_sp.setTextColor(Color.parseColor("#FFFFFF"))

        settingsRed.visibility = View.GONE
        settings.visibility = View.VISIBLE
        settingsText.setTextColor(Color.parseColor("#FFFFFF"))

    }
}