package com.exobe.hostActivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.activities.*
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.activities.services.AllRejectedServicesActivity
import com.exobe.activities.services.MyEarningActivity
import com.exobe.fragments.allServices.Requested_Services
import com.exobe.fragments.allServices.SelectedServicesFragment
import com.exobe.fragments.allServices.ServiceViewFragment
import com.exobe.fragments.allServices.ServicesManagement
import com.exobe.fragments.allServices.ServicesSettingsFragment
import com.exobe.fragments.allServices.payment_description_sp
import com.exobe.androidextention
import com.exobe.customClicks.ServicesClick
import com.exobe.customClicks.viewserviceclick
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.MyProfileResponse
import com.exobe.entity.response.serviceTab.SelectedServiceResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso

class ServicesMainActivity : AppCompatActivity(), ServicesClick, viewserviceclick {
    lateinit var back: ImageView
    lateinit var title: TextView
    lateinit var edit_deal: LinearLayout

    lateinit var home: ImageView
    lateinit var homeRed: ImageView
    lateinit var homeText: TextView
    lateinit var settings: ImageView
    lateinit var settingsRed: ImageView
    lateinit var MenuClick: ImageView
    lateinit var settingsText: TextView
    lateinit var drawerlayout: DrawerLayout

    lateinit var ll_home_tab: LinearLayout
    lateinit var ll_settings_tab: LinearLayout
    lateinit var Serviseprovider_LL: LinearLayout
    lateinit var ll_service_tab: LinearLayout
    lateinit var service_red_sp: ImageView
    lateinit var service_sp: ImageView
    lateinit var TVservuce_sp: TextView
    lateinit var mContext: Context
    lateinit var Profile_picture: ImageView
    lateinit var name: TextView


    lateinit var MyDeals_serviseprovider: RelativeLayout
    lateinit var ll_deals_sp: LinearLayout
    lateinit var PaymentHistory_serviseprovider: RelativeLayout
    lateinit var ll_paymenthistorysp: LinearLayout
    lateinit var downarrowsp1: ImageView
    lateinit var rightarrow_sp1: ImageView
    lateinit var rightarrow_sp: ImageView
    lateinit var downarrow_sp: ImageView
    lateinit var RequestedServices_serviseprovider: RelativeLayout
    lateinit var Home_RelativeLayout: RelativeLayout
    lateinit var Login_RelativeLayout: RelativeLayout
    lateinit var Logout_RelativeLayout: RelativeLayout

    //    lateinit var paymenttowholesalerssp: LinearLayout
    lateinit var paymentfromcustomersp: LinearLayout
    lateinit var dealsfromcustomersp: LinearLayout
    lateinit var v: ImageView


    lateinit var myEarnings: RelativeLayout
    lateinit var rejectedOrders: RelativeLayout


    var paymentflag = false
    var dealsflag = false





    @SuppressLint("SetTextI18n", "RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services_main)
        window.attributes.windowAnimations = R.style.Fade
        back = findViewById(R.id.back)
        title = findViewById(R.id.title)
        mContext = this.applicationContext
        edit_deal = findViewById(R.id.edit_deal)!!
        dealsfromcustomersp = findViewById(R.id.dealsfromcustomersp)
        home = findViewById(R.id.home_grey_sp)
        homeRed = findViewById(R.id.home_sp)
        homeText = findViewById(R.id.TVhome_sp)
        settings = findViewById(R.id.setting_grey_sp)
        settingsRed = findViewById(R.id.setting_red_sp)
        settingsText = findViewById(R.id.TVsetting_sp)
        ll_home_tab = findViewById(R.id.ll_home_tab)
        ll_settings_tab = findViewById(R.id.ll_settings_tab)
        MenuClick = findViewById(R.id.MenuClick)
        drawerlayout = findViewById(R.id.drawer_layout)
        Serviseprovider_LL = findViewById(R.id.Serviseprovider_LL)
        ll_service_tab = findViewById(R.id.ll_service_tab)
        service_red_sp = findViewById(R.id.service_red_sp)
        service_sp = findViewById(R.id.service_sp)
        TVservuce_sp = findViewById(R.id.TVservuce_sp)
        MyDeals_serviseprovider = findViewById(R.id.MyDeals_serviseprovider)
        ll_deals_sp = findViewById(R.id.ll_deals_sp)
        PaymentHistory_serviseprovider = findViewById(R.id.PaymentHistory_serviseprovider)
        ll_paymenthistorysp = findViewById(R.id.ll_paymenthistorysp)
        downarrowsp1 = findViewById(R.id.downarrowsp1)
        rightarrow_sp1 = findViewById(R.id.rightarrow_sp1)
        rightarrow_sp = findViewById(R.id.rightarrow_sp)
        downarrow_sp = findViewById(R.id.downarrow_sp)
        name = findViewById(R.id.name)
        Profile_picture = findViewById(R.id.Profile_picture)

        paymentfromcustomersp = findViewById(R.id.paymentfromcustomersp)
//        paymenttowholesalerssp = findViewById(R.id.paymenttowholesalerssp)

        RequestedServices_serviseprovider = findViewById(R.id.RequestedServices_serviseprovider)
        Home_RelativeLayout = findViewById(R.id.Home_RelativeLayout)
        Login_RelativeLayout = findViewById(R.id.Login_RelativeLayout)
        Logout_RelativeLayout = findViewById(R.id.Logout_RelativeLayout)
        drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        back.visibility = View.GONE
        MenuClick.visibility = View.VISIBLE

        myEarnings = findViewById(R.id.MyEarnings)
        rejectedOrders = findViewById(R.id.RejectedOrders)




        title.text = "Service Management"


//        mySelectedServiceListApi()

        supportFragmentManager.beginTransaction()
            .replace(R.id.service_main_container, ServicesManagement(),"servicemanagement").commit()












        dealsfromcustomersp.setSafeOnClickListener {
            ll_home_tab.isEnabled = true
            ll_settings_tab.isEnabled = true
            ll_service_tab.isEnabled = true

            drawerlayout.closeDrawer(Gravity.LEFT)
            home.visibility = View.GONE
            homeRed.visibility = View.VISIBLE
            homeText.setTextColor(Color.parseColor("#FFFFFF"))
            settings.visibility = View.VISIBLE
            settingsRed.visibility = View.GONE
            settingsText.setTextColor(Color.parseColor("#AAAAAA"))

            title.setText("Deals to customers")
            ll_paymenthistorysp.visibility = View.GONE
            downarrowsp1.visibility = View.GONE
            rightarrow_sp1.visibility = View.VISIBLE
            ll_deals_sp.visibility = View.GONE
            downarrow_sp.visibility = View.GONE
            rightarrow_sp.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction().replace(R.id.service_main_container, Customers_Deals(
                        "",
                        "onService",
                        "service",
                        "headertitle",
                        "Deals to customer",
                        "sideMenu"
                    ), "CustomersDeals"
                )
                .addToBackStack(null)
                .commit()
        }


        Home_RelativeLayout.setSafeOnClickListener {
            ll_home_tab.isEnabled = true
            ll_settings_tab.isEnabled = true
            ll_service_tab.isEnabled = true
            drawerlayout.closeDrawer(Gravity.LEFT)
            supportFragmentManager.beginTransaction()
                .replace(R.id.service_main_container, ServicesManagement(), "servicemanagement")
                .addToBackStack(null).commit()
        }

        ll_home_tab.setSafeOnClickListener {
            ll_home_tab.isEnabled = false
            ll_settings_tab.isEnabled = true
            ll_service_tab.isEnabled = true
//            title.setText("Services")
            home.visibility = View.VISIBLE
            homeRed.visibility = View.GONE
            homeText.setTextColor(Color.parseColor("#FFFFFF"))
            settings.visibility = View.VISIBLE
            settingsRed.visibility = View.GONE
            service_sp.visibility = View.VISIBLE
            service_red_sp.visibility = View.GONE
            settingsText.setTextColor(Color.parseColor("#FFFFFF"))
            TVservuce_sp.setTextColor(Color.parseColor("#FFFFFF"))
            supportFragmentManager.beginTransaction()
                .replace(R.id.service_main_container, ServicesManagement(), "servicemanagement")
                .addToBackStack(null).commit()
        }

        ll_settings_tab.setSafeOnClickListener {
            ll_home_tab.isEnabled = true
            ll_settings_tab.isEnabled = false
            ll_service_tab.isEnabled = true

            home.visibility = View.GONE
            homeRed.visibility = View.VISIBLE
            homeText.setTextColor(Color.parseColor("#FFFFFF"))
            service_sp.visibility = View.VISIBLE
            service_red_sp.visibility = View.GONE
            settings.visibility = View.GONE
            settingsRed.visibility = View.VISIBLE
            settingsText.setTextColor(Color.parseColor("#FFFFFF"))
            TVservuce_sp.setTextColor(Color.parseColor(
                "#FFFFFF"))

            supportFragmentManager.beginTransaction().replace(
                R.id.service_main_container,
                ServicesSettingsFragment(),
                "servicesettingmanagement"
            ).addToBackStack(null).commit()

        }
        ll_service_tab.setSafeOnClickListener {
            ll_home_tab.isEnabled = true
            ll_settings_tab.isEnabled = true
            ll_service_tab.isEnabled = false

            home.visibility = View.GONE
            homeRed.visibility = View.VISIBLE
            homeText.setTextColor(Color.parseColor("#FFFFFF"))
            service_sp.visibility = View.GONE
            service_red_sp.visibility = View.VISIBLE
            settings.visibility = View.VISIBLE
            settingsRed.visibility = View.GONE
            settingsText.setTextColor(Color.parseColor("#FFFFFF"))
            TVservuce_sp.setTextColor(Color.parseColor("#FFFFFF"))




            supportFragmentManager.beginTransaction().replace(
                R.id.service_main_container,
                SelectedServicesFragment(),
                "SelectedServicesFragment"
            ).addToBackStack("SelectedServicesFragment").commit()


        }

        myEarnings.setOnClickListener {
            drawerlayout.closeDrawer(Gravity.LEFT)
            startActivity(Intent(this, MyEarningActivity::class.java))
        }

        rejectedOrders.setOnClickListener {
            drawerlayout.closeDrawer(Gravity.LEFT)
            startActivity(Intent(this, AllRejectedServicesActivity::class.java))
        }



        MenuClick.setSafeOnClickListener {
            ll_home_tab.isEnabled = true
            ll_settings_tab.isEnabled = true
            ll_service_tab.isEnabled = true

            myprofileApi()
            drawerlayout.openDrawer(Gravity.LEFT)
            Serviseprovider_LL.visibility = View.VISIBLE
            Logout_RelativeLayout.visibility = View.VISIBLE
            Login_RelativeLayout.visibility = View.GONE

        }
        paymentfromcustomersp.setSafeOnClickListener {
            ll_home_tab.isEnabled = true
            ll_settings_tab.isEnabled = true
            ll_service_tab.isEnabled = true

            drawerlayout.closeDrawer(Gravity.LEFT)
            home.visibility = View.GONE
            homeRed.visibility = View.VISIBLE
            homeText.setTextColor(Color.parseColor("#FFFFFF"))
            settings.visibility = View.VISIBLE
            settingsRed.visibility = View.GONE
            settingsText.setTextColor(Color.parseColor("#AAAAAA"))

            title.setText("Payment from customers")
            ll_paymenthistorysp.visibility = View.GONE
            downarrowsp1.visibility = View.GONE
            rightarrow_sp1.visibility = View.VISIBLE
            ll_deals_sp.visibility = View.GONE
            downarrow_sp.visibility = View.GONE
            rightarrow_sp.visibility = View.VISIBLE
            val bundle = Bundle()
            bundle.putString("flagSide", "service_provider")
            bundle.putString("paymentFlag", "PaymentFromCustomerService")
            bundle.putString("title", "Payment From Customers")
            val fragobj = payment_description_sp()
            fragobj.setArguments(bundle)
            supportFragmentManager.beginTransaction()
                .replace(R.id.service_main_container, fragobj, "payment_description")
                .addToBackStack(null).commit()
//            supportFragmentManager.beginTransaction().replace(R.id.service_main_container, fragobj,"payment_description")?.addToBackStack(null)?.commit()

        }


        RequestedServices_serviseprovider.setSafeOnClickListener {
            ll_home_tab.isEnabled = true
            ll_settings_tab.isEnabled = true
            ll_service_tab.isEnabled = true

            drawerlayout.closeDrawer(Gravity.LEFT)
            supportFragmentManager.beginTransaction()
                .replace(R.id.service_main_container, Requested_Services(this,this), "servicemanagement")
                .addToBackStack(null).commit()
        }

        MyDeals_serviseprovider.setSafeOnClickListener {
            ll_home_tab.isEnabled = true
            ll_settings_tab.isEnabled = true
            ll_service_tab.isEnabled = true

            if (dealsflag == false) {
                dealsflag = true
                paymentflag = false
                ll_deals_sp.visibility = View.VISIBLE
                downarrow_sp.visibility = View.VISIBLE
                rightarrow_sp.visibility = View.GONE
                ll_paymenthistorysp.visibility = View.GONE
                downarrowsp1.visibility = View.GONE
                rightarrow_sp1.visibility = View.VISIBLE

            } else {
                paymentflag = true
                dealsflag = false
                ll_deals_sp.visibility = View.GONE
                downarrow_sp.visibility = View.GONE
                rightarrow_sp.visibility = View.VISIBLE

            }

        }
        Logout_RelativeLayout.setSafeOnClickListener {
            ll_home_tab.isEnabled = true
            ll_settings_tab.isEnabled = true
            ll_service_tab.isEnabled = true

            SavedPrefManager.saveStringPreferences(this, SavedPrefManager.isLogin, "false")
            androidextention.logOutDialog(this)
            drawerlayout.closeDrawer(Gravity.LEFT)
            ll_paymenthistorysp.visibility = View.GONE
            downarrowsp1.visibility = View.GONE
            rightarrow_sp1.visibility = View.VISIBLE
            ll_deals_sp.visibility = View.GONE
            downarrow_sp.visibility = View.GONE
            rightarrow_sp.visibility = View.VISIBLE
        }
        PaymentHistory_serviseprovider.setSafeOnClickListener {
            ll_home_tab.isEnabled = true
            ll_settings_tab.isEnabled = true
            ll_service_tab.isEnabled = true

            if (paymentflag == false) {
                paymentflag = true
                dealsflag = false
                ll_paymenthistorysp.visibility = View.VISIBLE
                downarrowsp1.visibility = View.VISIBLE
                rightarrow_sp1.visibility = View.GONE
                ll_deals_sp.visibility = View.GONE
                downarrow_sp.visibility = View.GONE
                rightarrow_sp.visibility = View.VISIBLE

            } else {
                dealsflag = true
                paymentflag = false
                ll_paymenthistorysp.visibility = View.GONE
                downarrowsp1.visibility = View.GONE
                rightarrow_sp1.visibility = View.VISIBLE

            }
        }
        myprofileApi()

    }

    private fun myprofileApi() {
        if (androidextention.isOnline(this)) {
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<MyProfileResponse> =
                ApiCallBack<MyProfileResponse>(object : ApiResponseListener<MyProfileResponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onApiSuccess(response: MyProfileResponse, apiName: String?) {
                        if (response.responseCode == 200) {
                            try {
                                name.text = "${response.result.firstName} ${response.result.lastName}"

                                if (!response.result.profilePic.equals("")) {
                                    Glide.with(this@ServicesMainActivity).load(response.result.profilePic).thumbnail(0.25f).into(Profile_picture)

//                                    Picasso.get().load(response.result.profilePic)
//                                        .into(Profile_picture)
                                }


                                if (!response.result.isConfig){
                                    home.visibility = View.GONE
                                    homeRed.visibility = View.VISIBLE
                                    homeText.setTextColor(Color.parseColor("#FFFFFF"))
                                    service_sp.visibility = View.GONE
                                    service_red_sp.visibility = View.VISIBLE
                                    settings.visibility = View.VISIBLE
                                    settingsRed.visibility = View.GONE
                                    settingsText.setTextColor(Color.parseColor("#FFFFFF"))
                                    TVservuce_sp.setTextColor(Color.parseColor("#FFFFFF"))


                                    supportFragmentManager.beginTransaction().replace(
                                        R.id.service_main_container,
                                        SelectedServicesFragment(),
                                        "SelectedServicesFragment"
                                    ).addToBackStack("SelectedServicesFragment").commit()
                                }




                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    }


                }, "myprofileApi", this)
            try {
                serviceManager.MyProfileApi(callBack)
            } catch (e: Exception) {

                e.printStackTrace()
            }
        }

    }

    @SuppressLint("RtlHardcoded")
    override fun onBackPressed() {
        if (drawerlayout.isDrawerOpen(Gravity.LEFT)) {
            drawerlayout.closeDrawer(Gravity.LEFT)
        } else {
            edit_deal.visibility = View.GONE
            super.onBackPressed()
        }

    }

    override fun click(flag: String, id: String?) {

        val bundle = Bundle()
        bundle.putString("flag", "ACCEPTED")
        bundle.putString("id", id)
        val fragobj = ServiceViewFragment()
        fragobj.arguments = bundle
        supportFragmentManager.beginTransaction().replace(
            R.id.service_main_container, fragobj,"service_view"
        ).addToBackStack(null)
            .commit()
    }

    override fun viewservice(id: String?, flag: String) {

        val bundle = Bundle()
        bundle.putString("flag", flag)
        bundle.putString("id", id)
        val fragobj = ServiceViewFragment()
        fragobj.arguments = bundle
        supportFragmentManager.beginTransaction().replace(
            R.id.service_main_container,  fragobj, "ServiceView"
        ).addToBackStack(null)
            .commit()
    }

    private fun mySelectedServiceListApi() {
        if (androidextention.isOnline(this)) {

            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<SelectedServiceResponse> =
                ApiCallBack(object : ApiResponseListener<SelectedServiceResponse> {
                    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
                    override fun onApiSuccess(response: SelectedServiceResponse, apiName: String?) {

                        if (response.responseCode == 200) {
                            try {

                                if (!response.result.isConfig){
                                    home.visibility = View.GONE
                                    homeRed.visibility = View.VISIBLE
                                    homeText.setTextColor(Color.parseColor("#FFFFFF"))
                                    service_sp.visibility = View.GONE
                                    service_red_sp.visibility = View.VISIBLE
                                    settings.visibility = View.VISIBLE
                                    settingsRed.visibility = View.GONE
                                    settingsText.setTextColor(Color.parseColor("#FFFFFF"))
                                    TVservuce_sp.setTextColor(Color.parseColor("#FFFFFF"))


                                    supportFragmentManager.beginTransaction().replace(
                                        R.id.service_main_container,
                                        SelectedServicesFragment(),
                                        "SelectedServicesFragment"
                                    ).addToBackStack("SelectedServicesFragment").commit()
                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@ServicesMainActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {

                    }

                }, apiName = "mySelectedServiceListApi", this)


            try {

                serviceManager.mySelectedServiceListApi(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }
    

}