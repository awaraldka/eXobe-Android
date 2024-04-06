package com.exobe.activities.services

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.exobe.adaptor.servicesAdaptor.AllServicesAdapter
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.customClicks.CommonListenerServices
import com.exobe.databinding.ActivityCommonForServicesBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.MyProfileResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.android.material.tabs.TabLayout


object ServicePage {

    var isRefreshed = false
    var isSelectedFilter = "Standard Services"
    var previousSelectedFilter = "Standard Services"

}


class CommonForServicesActivity : AppCompatActivity(), CommonListenerServices {


    private lateinit var binding: ActivityCommonForServicesBinding
    lateinit var homeClick: RelativeLayout
    lateinit var myEarnings: RelativeLayout
    lateinit var rejectedOrders: RelativeLayout
    lateinit var ConfigurationFee: RelativeLayout
    lateinit var logoutClick: RelativeLayout
    lateinit var profilePicture: ImageView
    lateinit var nameServices: TextView


    @SuppressLint("RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommonForServicesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.attributes.windowAnimations = R.style.Fade


        homeClick = findViewById(R.id.HomeClick)
        myEarnings = findViewById(R.id.MyEarnings)
        rejectedOrders = findViewById(R.id.RejectedOrders)
        logoutClick = findViewById(R.id.LogoutClick)
        profilePicture = findViewById(R.id.profilePictureService)
        nameServices = findViewById(R.id.nameServices)
        ConfigurationFee = findViewById(R.id.ConfigurationFee)

        val userRole =
            SavedPrefManager.getStringPreferences(this, SavedPrefManager.USER_TYPE).toString()

        if (userRole == "FIELD_ENTITY") {
            rejectedOrders.isVisible = false
        }


        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        getProfileApiApi(nameServices, profilePicture)

        binding.menuOptions.setOnClickListener {
            binding.drawerLayout.openDrawer(Gravity.LEFT)
        }


        homeClick.setOnClickListener {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
        }

        myEarnings.setOnClickListener {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
            startActivity(Intent(this, MyEarningActivity::class.java))
        }

        ConfigurationFee.setOnClickListener {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
            startActivity(Intent(this, FeeConfigurationUpdateActivity::class.java))
        }

        rejectedOrders.setOnClickListener {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
            startActivity(Intent(this, AllRejectedServicesActivity::class.java))
        }

        profilePicture.setOnClickListener {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)

            startActivity(Intent(this, ProfileServiceProviderActivity::class.java))


        }

        logoutClick.setOnClickListener {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
            androidextention.logOutDialog(this)

        }


        binding.notificationClick.setOnClickListener {
            startActivity(Intent(this, NotificationServicesActivity::class.java))
        }

        binding.tablayout.addTab(binding.tablayout.newTab().setText("Pending Orders"))
        binding.tablayout.addTab(binding.tablayout.newTab().setText("Completed Orders"))
        binding.tablayout.tabGravity = TabLayout.GRAVITY_FILL
        setTabs()

    }

    private fun setTabs() {


        val adapter = AllServicesAdapter(this, supportFragmentManager, binding.tablayout.tabCount, this,pageRefresh = ServicePage.isRefreshed)
        binding.viewPager.adapter = adapter

        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tablayout))

        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }


    override fun onResume() {
        super.onResume()
        if (ServicePage.isRefreshed) {
            setTabs()

        }
    }

    override fun serviceProvidersPendingClick(_id: String, orderId: String, userType :String) {
        val intent = Intent(this, ServiceCommonViewActivity::class.java)
        intent.putExtra("_id", _id)
        intent.putExtra("orderId", orderId)
        intent.putExtra("userType", userType)
        intent.putExtra("isFrom", "PENDING")
        startActivity(intent)
    }

    override fun serviceProvidersCompletedClick(_id: String, orderId: String, userType: String) {
        val intent = Intent(this, ServiceCommonViewActivity::class.java)
        intent.putExtra("_id", _id)
        intent.putExtra("orderId", orderId)
        intent.putExtra("userType", userType)
        intent.putExtra("isFrom", "COMPLETED")
        startActivity(intent)
    }

    fun getProfileApiApi(name: TextView?, userProfile: ImageView?) {
        if (androidextention.isOnline(this)) {
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<MyProfileResponse> =
                ApiCallBack(object : ApiResponseListener<MyProfileResponse> {
                    override fun onApiSuccess(response: MyProfileResponse, apiName: String?) {

                        if (name != null) {
                            name.text = SavedPrefManager.getStringPreferences(
                                this@CommonForServicesActivity,
                                SavedPrefManager.USER_NAME.toString()
                            )
                        }
                        if (userProfile != null) {
                            Glide.with(this@CommonForServicesActivity).load(
                                SavedPrefManager.getStringPreferences(
                                    this@CommonForServicesActivity,
                                    SavedPrefManager.USER_PROFILE
                                ).toString()
                            ).placeholder(R.drawable.side_menu_profile).into(userProfile)
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

        } else {
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show()
        }
    }


}