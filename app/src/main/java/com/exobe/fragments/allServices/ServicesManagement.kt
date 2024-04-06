package com.exobe.fragments.allServices

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.exobe.adaptor.servicesAdaptor.AllServicesAdapter
import com.exobe.adaptor.servicesAdaptor.ServicesTabAdapter
import com.exobe.R
import com.exobe.activities.services.ServiceCommonViewActivity
import com.exobe.activities.services.ServicePage
import com.exobe.activities.services.ServicePage.isSelectedFilter
import com.exobe.activities.services.ServicePage.previousSelectedFilter
import com.exobe.customClicks.CommonListenerServices
import com.exobe.customClicks.ServicesClick
import com.exobe.customClicks.viewserviceclick
import com.exobe.utils.SavedPrefManager
import com.google.android.material.tabs.TabLayout

class ServicesManagement : Fragment(), ServicesClick, viewserviceclick , CommonListenerServices {
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var tabLayout: TabLayout
    lateinit var typeFilter: Spinner
    lateinit var viewPager: ViewPager
    lateinit var back: ImageView
    var id: String = ""
    var MenuClick: ImageView? = null




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

    lateinit var edit_deal: LinearLayout
    lateinit var llServiceTab: LinearLayout
    lateinit var llSettingsTab: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_services_management, container, false)

        handleTab()

        tabLayout = view.findViewById(R.id.tablayout)
        typeFilter = view.findViewById(R.id.typeFilter)
        back = activity?.findViewById(R.id.back)!!
        title = activity?.findViewById(R.id.title)!!
        edit_deal = activity?.findViewById(R.id.edit_deal)!!
        viewPager = view.findViewById(R.id.viewPager)
        MenuClick = activity?.findViewById(R.id.MenuClick)!!
        back.visibility = View.GONE
        edit_deal.visibility = View.GONE
        MenuClick!!.visibility = View.VISIBLE

        title.text = "Services"
        tabLayout.addTab(tabLayout.newTab().setText("Pending"))
        tabLayout.addTab(tabLayout.newTab().setText("Completed"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL


        val isStandardService = SavedPrefManager.getBooleanPreferences(requireContext(), SavedPrefManager.isStandardService)
        val isTransportationService = SavedPrefManager.getBooleanPreferences(requireContext(), SavedPrefManager.isTransportationService)
        val isFulfillmentService = SavedPrefManager.getBooleanPreferences(requireContext(), SavedPrefManager.isFulfillmentService)


        when {
            isStandardService && isTransportationService && isFulfillmentService -> {
                val filterOptions = arrayOf("Standard Services", "Transportation Services", "Fulfillment Services")
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, filterOptions)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                typeFilter.adapter = adapter
            }
            isStandardService && isTransportationService -> {
                val filterOptions = arrayOf("Standard Services", "Transportation Services")
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, filterOptions)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                typeFilter.adapter = adapter
            }
            isTransportationService && isFulfillmentService -> {
                val filterOptions = arrayOf("Transportation Services", "Fulfillment Services")
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, filterOptions)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                typeFilter.adapter = adapter
            }
            isStandardService && isFulfillmentService -> {
                val filterOptions = arrayOf("Standard Services", "Fulfillment Services")
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, filterOptions)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                typeFilter.adapter = adapter
            }
            isStandardService -> {
                val filterOptions = arrayOf("Standard Services")
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, filterOptions)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                typeFilter.adapter = adapter
            }
            isFulfillmentService -> {
                val filterOptions = arrayOf("Fulfillment Services")
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, filterOptions)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                typeFilter.adapter = adapter
            }
            else -> {
                val filterOptions = arrayOf("Standard Services", "Transportation Services", "Fulfillment Services")
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, filterOptions)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                typeFilter.adapter = adapter
            }
        }



        llServiceTab = activity?.findViewById(R.id.ll_service_tab)!!
        llSettingsTab = activity?.findViewById(R.id.ll_settings_tab)!!
        llServiceTab.isEnabled = true
        llSettingsTab.isEnabled = true


        if (isSelectedFilter == "Standard Services"){
            setAdaptor()
        }else{
            setTabs()
        }


        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })





        when(isSelectedFilter){
            "Standard Services" ->{
                typeFilter.setSelection(0)
            }
            "Transportation Services" -> {
                typeFilter.setSelection(1)
            }
            else -> {
                typeFilter.setSelection(2)
            }

        }




        typeFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val item = parent.getItemAtPosition(pos)

                if (item != previousSelectedFilter) {
                    isSelectedFilter = item.toString()
                    previousSelectedFilter = isSelectedFilter

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.service_main_container, ServicesManagement(), "servicemanagement")
                        .commit()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }






        return view
    }

    private fun setAdaptor() {
        val adapter = ServicesTabAdapter(requireContext(), childFragmentManager, tabLayout.tabCount, this, this)
        viewPager.adapter = adapter
    }

    private fun setTabs() {
        val adapter = AllServicesAdapter(requireContext(), childFragmentManager, tabLayout.tabCount, this,pageRefresh = ServicePage.isRefreshed)
        viewPager.adapter = adapter

    }


    override fun onResume() {
        super.onResume()
        if (ServicePage.isRefreshed) {
            setTabs()

        }
    }



    override fun click(flag: String, id: String?) {

        val bundle = Bundle()
        bundle.putString("flag", "ACCEPTED")
        bundle.putString("id", id)
        val fragObj = ServiceViewFragment()
        fragObj.arguments = bundle
        parentFragmentManager.beginTransaction().replace(
            R.id.service_main_container, fragObj, "service_view"
        ).addToBackStack(null).commit()
    }

    override fun viewservice(id: String?, flag: String) {

        val bundle = Bundle()
        bundle.putString("flag", "PENDING")
        bundle.putString("id", id)
        val fragObj = ServiceViewFragment()
        fragObj.arguments = bundle
        parentFragmentManager.beginTransaction().replace(
            R.id.service_main_container, fragObj, "ServiceView"
        ).addToBackStack(null).commit()
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

        home.visibility = View.GONE
        homeRed.visibility = View.VISIBLE
        homeText.setTextColor(Color.parseColor("#FFFFFF"))

        service_sp.visibility = View.VISIBLE
        service_red_sp.visibility = View.GONE
        TVservuce_sp.setTextColor(Color.parseColor("#FFFFFF"))

        settingsRed.visibility = View.GONE
        settings.visibility = View.VISIBLE
        settingsText.setTextColor(Color.parseColor("#FFFFFF"))

    }

    override fun serviceProvidersPendingClick(_id: String, orderId: String, userType: String) {
        val intent = Intent(requireContext(), ServiceCommonViewActivity::class.java)
        intent.putExtra("_id", _id)
        intent.putExtra("orderId", orderId)
        intent.putExtra("userType", userType)
        intent.putExtra("isFrom", "PENDING")
        startActivity(intent)
    }

    override fun serviceProvidersCompletedClick(_id: String, orderId: String,userType: String) {
        val intent = Intent(requireContext(), ServiceCommonViewActivity::class.java)
        intent.putExtra("_id", _id)
        intent.putExtra("orderId", orderId)
        intent.putExtra("userType", userType)
        intent.putExtra("isFrom", "COMPLETED")
        startActivity(intent)
    }


}