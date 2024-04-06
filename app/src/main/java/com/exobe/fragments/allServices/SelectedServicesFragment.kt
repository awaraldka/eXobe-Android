package com.exobe.fragments.allServices

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.exobe.adaptor.servicesAdaptor.SelectedServiceListAdaptor
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.Progresss
import com.exobe.androidextention
import com.exobe.customClicks.ClickSelectedServices
import com.exobe.databinding.FragmentSelectedServicesBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.serviceTab.SelectedServiceResponse
import com.exobe.entity.response.serviceTab.SelectedServiceServices
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder


class SelectedServicesFragment : Fragment(),ClickSelectedServices {

    private var _binding: FragmentSelectedServicesBinding? = null
    private val binding get() = _binding!!

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

    lateinit var back: ImageView
    lateinit var title: TextView
    var menuClick: ImageView? = null
    lateinit var editDeal: LinearLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentSelectedServicesBinding.inflate(layoutInflater, container, false)
        title = activity?.findViewById(R.id.title)!!
        back = activity?.findViewById(R.id.back)!!
        menuClick = activity?.findViewById(R.id.MenuClick)!!
        editDeal = activity?.findViewById(R.id.edit_deal)!!

        title.text = "Selected Services"
        back.isVisible = false
        menuClick!!.isVisible = true
        editDeal.visibility = View.GONE

        handleTab()

        mySelectedServiceListApi()


        return  binding.root
    }


    private fun mySelectedServiceListApi() {
        if (androidextention.isOnline(requireContext())) {
            Progresss.start(requireContext())
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<SelectedServiceResponse> =
                ApiCallBack(object : ApiResponseListener<SelectedServiceResponse> {
                    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
                    override fun onApiSuccess(response: SelectedServiceResponse, apiName: String?) {
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            try {

                                if (response.result.services.size == 1){
                                    when(response.result.services[0].name.trim()){
                                        "Transportation Services","Transportation Service"-> {
                                            val bundle = Bundle()
                                            bundle.putString("id", response.result.services[0].category.id)
                                            bundle.putString("title", response.result.services[0].name)
                                            bundle.putString("description", "Please configure the services (less price more opportunity)")
                                            bundle.putString("isScreenFor", "Transportation Services")

                                            val obj = SubCategoryServiceProvider()
                                            obj.arguments = bundle

                                            activity?.supportFragmentManager?.beginTransaction()
                                                ?.replace(R.id.service_main_container, obj, "subcategory")?.addToBackStack(null)
                                                ?.commit()
                                        }
                                        "Fulfilment Service","Fulfillment Services","Fulfillment Service"  -> {

                                            val bundle = Bundle()
                                            bundle.putString("id", response.result.services[0].category.id)
                                            bundle.putString("title", response.result.services[0].name)
                                            bundle.putString("description", "Please configure the services (less price more opportunity)")
                                            bundle.putString("isScreenFor", "Fulfillment Services")


                                            val obj = SubCategoryServiceProvider()
                                            obj.arguments = bundle

                                            activity?.supportFragmentManager?.beginTransaction()
                                                ?.replace(R.id.service_main_container, obj, "subcategory")?.addToBackStack(null)
                                                ?.commit()
                                        }
                                        else -> {

                                            val bundle =  Bundle()
                                            bundle.putString("isFrom","SelectedServices")
                                            val obj = ServiceProviderFragment()
                                            obj.arguments = bundle

                                            parentFragmentManager.beginTransaction().replace(
                                                R.id.service_main_container,
                                                obj,
                                                "serviceervicesmanagement").addToBackStack(null).commit()
                                        }
                                    }

                                }

                                setAdapter(response.result.services)
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
                            androidextention.alertBox(pojo.responseMessage, requireContext())

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, apiName = "mySelectedServiceListApi", requireContext())


            try {

                serviceManager.mySelectedServiceListApi(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }
    }

    private fun setAdapter(data: ArrayList<SelectedServiceServices>) {
        val layoutManager = GridLayoutManager(activity, 2)

        when (data.size) {
            3 -> {
                layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (position) {

                            0 -> 2
                            else -> {
                                1
                            }
                        }
                    }
                }
            }
            2 -> {
                layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (position) {

                            0 -> 1
                            else -> {
                                1
                            }
                        }
                    }
                }
            }
            else -> {
                layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (position) {

                            0 -> 2
                            else -> {
                                2
                            }
                        }
                    }
                }
            }
        }






        binding.itemList.layoutManager = layoutManager
        val adapter = SelectedServiceListAdaptor(requireContext(),data, click = this)
        binding.itemList.adapter = adapter
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

    override fun getSelectedService(name: String, categoryId:String,categoryType:String) {
        when (categoryType) {
            "PICKUP_PARTNER" -> {
                val bundle = Bundle()
                bundle.putString("id", categoryId)
                bundle.putString("title", name)
                bundle.putString("categoryType", categoryType)
                bundle.putString("description", "Please configure the services (less price more opportunity)")
                bundle.putString("isScreenFor", name)

                val obj = SubCategoryServiceProvider()
                obj.arguments = bundle

                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.service_main_container, obj, "subcategory")?.addToBackStack(null)
                    ?.commit()
            }
            "FIELD_ENTITY" -> {
                val bundle = Bundle()
                bundle.putString("id", categoryId)
                bundle.putString("title", name)
                bundle.putString("categoryType", categoryType)
                bundle.putString("description", "Please configure the services (less price more opportunity)")
                bundle.putString("isScreenFor", name)


                val obj = SubCategoryServiceProvider()
                obj.arguments = bundle

                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.service_main_container, obj, "subcategory")?.addToBackStack(null)
                    ?.commit()
            }
            else -> {
                val bundle =  Bundle()
                bundle.putString("isFrom","SelectedServices")
                bundle.putString("categoryType", categoryType)
                bundle.putString("isScreenFor", name)



                val obj = ServiceProviderFragment()
                obj.arguments = bundle

                parentFragmentManager.beginTransaction().replace(
                    R.id.service_main_container,
                    obj,
                    "serviceervicesmanagement").addToBackStack(null).commit()
            }
        }






    }


}