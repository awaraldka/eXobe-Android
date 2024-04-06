package com.exobe.hostActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.fragments.orderHistory.MyBookingsFragment
import com.exobe.fragments.*
import com.exobe.fragments.cart.MyCartFragment
import com.exobe.fragments.allServices.payment_description_sp
import com.exobe.R
import com.exobe.activities.CustomerWalletActivity
import com.exobe.activities.Customers_Deals
import com.exobe.activities.Notification
import com.exobe.fragments.orderHistory.OrderHistoryFragment
import com.exobe.activities.PaymentHistoryActivity
import com.exobe.activities.ProductEnquiry
import com.exobe.fragments.products.ProductViewFragment
import com.exobe.activities.Services_DealsScreen
import com.exobe.utils.CommonFunctions
import com.exobe.utils.SavedPrefManager
import com.exobe.activities.services.MyEarningActivity
import com.exobe.androidextention
import com.exobe.bottomSheet.CustomerBottomSheet
import com.exobe.bottomSheet.DealsBottomSheet
import com.exobe.customClicks.CustomerDealListener
import com.exobe.customClicks.SetTitles
import com.exobe.customClicks.UpdateIsLoginListener
import com.exobe.databinding.ActivityMainBinding
import com.exobe.databinding.BottomTabItemsBinding
import com.exobe.databinding.NavHeaderBinding


class MainActivity : AppCompatActivity(), SetTitles, UpdateIsLoginListener, CustomerDealListener {


    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomTabBinding: BottomTabItemsBinding
    private lateinit var menuBinding: NavHeaderBinding

    private var flag = ""
    private var clickFlag = false
    private var dealFlag = false
    private var path: String = ""
    private var productId: String = ""
    private var userType = ""

    private var productIdNotification = ""
    private var serviceId = ""
    private var campaignOn = ""
    private var notifyType = ""
    private var serviceProvideId = ""
    private var categoryId = ""

    @SuppressLint("CutPasteId", "RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bottomTabBinding = binding.list
        menuBinding = binding.menuOptions
        setContentView(binding.root)



        intent.getStringExtra("productId")?.let { productIdNotification= it }
        intent.getStringExtra("serviceId")?.let { serviceId= it }
        intent.getStringExtra("campainOn")?.let { campaignOn= it }
        intent.getStringExtra("notifyType")?.let { notifyType= it }
        intent.getStringExtra("serviceProvideId")?.let { serviceProvideId= it }
        intent.getStringExtra("categoryId")?.let { categoryId= it }





        window.attributes.windowAnimations = R.style.Fade
        intent.getStringExtra("flag")?.let {  flag = it }

        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.isLogin) == "true") {
            CommonFunctions.getProfileApiApi(this, menuBinding.name, menuBinding.ProfilePicture)
        }

        openDefaultPage()
        callDeepLink()
        ifLogin()



        userType = SavedPrefManager.getStringPreferences(this, SavedPrefManager.USER_TYPE)!!
        when (userType) {
            "CUSTOMER" -> {
                ifLogin()

                bottomTabBinding.llCategoryTab.visibility = View.VISIBLE
                bottomTabBinding.llServiceTab.visibility = View.VISIBLE
                bottomTabBinding.llOrderTab.visibility = View.GONE
                bottomTabBinding.llProductTab.visibility = View.GONE
                menuBinding.CustomerLL.visibility = View.VISIBLE
                customerClicks()
            }

            "RETAILER" -> {
                ifLogin()

                bottomTabBinding.llCategoryTab.visibility = View.GONE
                bottomTabBinding.llServiceTab.visibility = View.GONE
                bottomTabBinding.llOrderTab.visibility = View.VISIBLE
                bottomTabBinding.llProductTab.visibility = View.VISIBLE

                menuBinding.RetailerLL.visibility = View.VISIBLE
                retailerClicks()
            }

            "SERVICE_PROVIDER" -> {
                ifLogin()

                supportFragmentManager.beginTransaction().replace(
                    R.id.FrameLayout,
                    Service_homescreen()
                ).commit()
                menuBinding.ServiseproviderLL.visibility = View.VISIBLE

            }
        }

        binding.MenuClick.setSafeOnClickListener {
            bottomTabBinding.homeGrey.isEnabled = true
            bottomTabBinding.llServiceTab.isEnabled = true
            bottomTabBinding.llCategoryTab.isEnabled = true
            bottomTabBinding.WishlistGrey.isEnabled = true
            bottomTabBinding.SettingLinearLayout.isEnabled = true
            bottomTabBinding.llOrderTab.isEnabled = true
            bottomTabBinding.llProductTab.isEnabled = true
            binding.drawerLayout.openDrawer(Gravity.LEFT)
            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.isLogin) == "true") {
                CommonFunctions.getProfileApiApi(this,  menuBinding.name, menuBinding.ProfilePicture)
            }
        }


        menuBinding.MyEarningsRetailer.setOnClickListener {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
            startActivity(Intent(this, MyEarningActivity::class.java))
        }

    }

    private fun callDeepLink() {
        if (intent.data != null) {
//            if (intent.data?.toString()?.contains(getString(R.string.APP_LINKS_URL)) == true) {
//                deeplinking()
//            }
        }
    }

    private fun openDefaultPage() {
        val bundle  = Bundle()
        bundle.putString("productId",productIdNotification)
        bundle.putString("serviceId",serviceId)
        bundle.putString("campainOn",campaignOn)
        bundle.putString("notifyType",notifyType)
        bundle.putString("serviceProvideId",serviceProvideId)
        bundle.putString("categoryId",categoryId)
        val obj =  RetailerHomepage(this, flag)
        obj.arguments = bundle

        supportFragmentManager.beginTransaction().replace(
            R.id.FrameLayout,obj ).commit()

        bottomTabBinding.TVhome.setTextColor(Color.parseColor("#FFFFFF"))
        binding.PreLoginTitleTextView2.text = "Home"
    }



    private fun ifLogin() {
        if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.isLogin) == "true") {
            menuBinding.LogoutRelativeLayout.visibility = View.VISIBLE
            menuBinding.LoginRelativeLayout.visibility = View.GONE
        } else {
            menuBinding.LogoutRelativeLayout.visibility = View.GONE
            menuBinding.LoginRelativeLayout.visibility = View.VISIBLE
        }

    }


    fun deepLinking() {
        val loginStatus = SavedPrefManager.getStringPreferences(this, SavedPrefManager.isLogin)
        val uri: Uri? = intent.data
        path = uri.toString().trim()
        println("link path:-$path")

        if (uri != null) {

            try {
                if (userType != "CUSTOMER" || userType != "RETAILER" || userType != "SERVICE_PROVIDER") {
                    deepLinkingNavigation()
                } else if (userType == "CUSTOMER" || userType == "RETAILER") {
                    if (loginStatus.equals("true")) {
                        deepLinkingNavigation()
                    }
                } else {
                    Toast.makeText(this, "Link can't open", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ServicesMainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun deepLinkingNavigation() {
        if (path != "") {
            val startDate: String = path
            val splitLink = startDate.split("=").toTypedArray()
            val start = splitLink[0]
            val end = splitLink[1]
            val splitStart = start.split("?").toTypedArray()
            val idType = splitStart[1]
            if (idType == "productID") {
                productId = end

                val bundle = Bundle()
                bundle.putString("productId2", productId)
                val fragObj = ProductViewFragment()
                fragObj.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FrameLayout, fragObj, "productView")
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    @SuppressLint("RtlHardcoded", "SetTextI18n")
    fun customerClicks() {

        menuBinding.MyCartCustomer.setSafeOnClickListener {

            if (userType == "CUSTOMER") {
                if (SavedPrefManager.getStringPreferences(
                        this,
                        SavedPrefManager.isLogin
                    ) == "true"
                ) {

                    val bundle = Bundle()

                    val fragobj = MyCartFragment("")
                    fragobj.arguments = bundle

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.FrameLayout, fragobj, "addCart")
                        .addToBackStack(null)
                        .commit()
                    binding.drawerLayout.closeDrawer(Gravity.LEFT)
                    menuBinding.llDealscustomer.visibility = View.GONE
                    menuBinding.rightarrow.visibility = View.VISIBLE
                    menuBinding.downarrow.visibility = View.GONE
                    binding.logoutButton.visibility = View.GONE

                } else {
                    supportFragmentManager.let { it1 ->
                        CustomerBottomSheet("Customer", this, this).show(
                            it1,
                            "ModalBottomSheet"
                        )
                    }

                }

            }

        }

        menuBinding.PaymentHistoryCustomer.setSafeOnClickListener {
            if (userType == "CUSTOMER") {
                if (SavedPrefManager.getStringPreferences(
                        this,
                        SavedPrefManager.isLogin
                    ) == "true"
                ) {

                    val bundle = Bundle()

                    val fragObj = PaymentHistoryActivity()
                    fragObj.arguments = bundle

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.FrameLayout, fragObj, "PaymentHistory")
                        .addToBackStack(null)
                        .commit()
                    binding.drawerLayout.closeDrawer(Gravity.LEFT)
                    menuBinding.llDealscustomer.visibility = View.GONE
                    menuBinding.downarrow.visibility = View.GONE
                    binding.logoutButton.visibility = View.GONE

                } else {
                    supportFragmentManager.let { it1 ->
                        CustomerBottomSheet("Customer", this, this).show(
                            it1,
                            "ModalBottomSheet"
                        )
                    }

                }

            }
        }



        menuBinding.OrderHistory.setSafeOnClickListener {

            if (userType == "CUSTOMER") {
                if (SavedPrefManager.getStringPreferences(
                        this,
                        SavedPrefManager.isLogin
                    ) == "true"
                ) {

                    binding.drawerLayout.closeDrawer(Gravity.LEFT)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.FrameLayout, OrderHistoryFragment(""), "OrderHistory")
                        .addToBackStack(null).commit()

                } else {
                    supportFragmentManager.let { it1 ->
                        CustomerBottomSheet("Customer", this, this).show(
                            it1,
                            "ModalBottomSheet"
                        )
                    }

                }

            }

        }


        menuBinding.MyDealsCustomer.setSafeOnClickListener {

            if (!clickFlag) {
                clickFlag = true
                menuBinding.llDealscustomer.visibility = View.VISIBLE
                menuBinding.rightarrow.visibility = View.GONE
                menuBinding.downarrow.visibility = View.VISIBLE


            } else {
                clickFlag = false
                menuBinding.llDealscustomer.visibility = View.GONE
                menuBinding.rightarrow.visibility = View.VISIBLE
                menuBinding.downarrow.visibility = View.GONE

            }


        }


        menuBinding.onproducts.setSafeOnClickListener {


            binding.drawerLayout.closeDrawer(Gravity.LEFT)

            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.FrameLayout, Customers_Deals(
                        "",
                        "customerOnProduct",
                        "customer",
                        "Deals on products",
                        "Deals to customer",
                        ""
                    ), "CustomersDeals"
                )
                .addToBackStack(null)
                .commit()
            menuBinding.llDealscustomer.visibility = View.GONE
            menuBinding.rightarrow.visibility = View.VISIBLE
            menuBinding.downarrow.visibility = View.GONE
            binding.logoutButton.visibility = View.GONE


        }


        menuBinding.onservices.setSafeOnClickListener {

            binding.drawerLayout.closeDrawer(Gravity.LEFT)

            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.FrameLayout,
                    Customers_Deals(
                        "",
                        "customerOnService",
                        "customer",
                        "Deals on Bookings",
                        "Deals to customer",
                        ""
                    ),
                    "CustomersDeals"
                )
                .addToBackStack(null)
                .commit()
            menuBinding.llDealscustomer.visibility = View.GONE
            menuBinding.rightarrow.visibility = View.VISIBLE
            menuBinding.downarrow.visibility = View.GONE
            binding.logoutButton.visibility = View.GONE

        }


        menuBinding.RequestedServicesCustomer.setSafeOnClickListener {


            binding.drawerLayout.closeDrawer(Gravity.LEFT)



            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.isLogin) == "true") {


                supportFragmentManager.beginTransaction()
                    .replace(R.id.FrameLayout, MyBookingsFragment(""), "viewService")
                    .addToBackStack(null).commit()
                menuBinding.llDealscustomer.visibility = View.GONE
                menuBinding.rightarrow.visibility = View.VISIBLE
                menuBinding.downarrow.visibility = View.GONE
                binding.logoutButton.visibility = View.GONE

            } else {
                supportFragmentManager.let { it1 ->
                    CustomerBottomSheet("Customer", this, this).show(
                        it1,
                        "ModalBottomSheet"
                    )
                }

            }

        }


        menuBinding.ProductInquiryCustomer.setSafeOnClickListener {

            binding.drawerLayout.closeDrawer(Gravity.LEFT)
            supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, ProductEnquiry("SideMenu"), "ProductEnquiry")
                .addToBackStack(null)
                .commit()
            binding.logoutButton.visibility = View.GONE

        }



        menuBinding.LoginRelativeLayout.setSafeOnClickListener {

            binding.drawerLayout.closeDrawer(Gravity.LEFT)
            supportFragmentManager.let { it1 ->
                CustomerBottomSheet("Customer", this, this).show(
                    it1,
                    "ModalBottomSheet"
                )
            }

        }


        menuBinding.myWalletClick.setSafeOnClickListener {

            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.isLogin) == "true") {
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
                startActivity(Intent(this, CustomerWalletActivity::class.java))

            } else {
                supportFragmentManager.let { it1 ->
                    CustomerBottomSheet("Customer", this, this).show(it1, "ModalBottomSheet") }

            }





        }





//////// ICON ///////////////

        binding.greyBellImageView.setSafeOnClickListener {

            if (userType.equals("CUSTOMER")) {

                if (SavedPrefManager.getStringPreferences(
                        this,
                        SavedPrefManager.isLogin
                    ) == "true"
                ) {

                    binding.logoutButton.visibility = View.GONE

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.FrameLayout, Notification(), "notification")
                        .addToBackStack(null)
                        .commit()
                } else {
                    supportFragmentManager.let { it1 ->
                        CustomerBottomSheet("Customer", this, this).show(
                            it1,
                            "ModalBottomSheet"
                        )
                    }

                }
            }
        }
        binding.logoutButton.setSafeOnClickListener {
            androidextention.logOutDialog(this)

        }

        binding.DealsImageView.setSafeOnClickListener {

            val bottomsheet = DealsBottomSheet("dealsCustomer", this)
            bottomsheet.show(supportFragmentManager, "bottomSheet")

        }

//////////BOTTOM ICON//////////////////////
        menuBinding.HomeRelativeLayout.setSafeOnClickListener {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
        }

        bottomTabBinding.homeGrey.setSafeOnClickListener {

            val bundle  = Bundle()
            bundle.putString("productId","")
            bundle.putString("serviceId","")
            bundle.putString("campainOn","")
            bundle.putString("notifyType","notifyType")
            val obj =  RetailerHomepage(this, flag)
            obj.arguments = bundle


            supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout,obj , "home")
                .addToBackStack(null).commit()

        }
        bottomTabBinding.llServiceTab.setSafeOnClickListener {

            supportFragmentManager.beginTransaction().replace(
                R.id.FrameLayout, ServiceAvailable(
                    this, "TAB"
                ), "serviceAvailable"
            ).addToBackStack(null).commit()

        }

        bottomTabBinding.llCategoryTab.setSafeOnClickListener {

            val bundle = Bundle()
            bundle.putString("seeall", "")
            val fragObj = Category(this)
            fragObj.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, fragObj, "category").addToBackStack(null)
                .commit()

        }
        bottomTabBinding.WishlistGrey.setSafeOnClickListener {

            if (userType == "CUSTOMER") {

                if (SavedPrefManager.getStringPreferences(
                        this,
                        SavedPrefManager.isLogin
                    ) == "true"
                ) {


                    supportFragmentManager.beginTransaction()
                        .replace(R.id.FrameLayout, Wishlist("wishlist"), "wishlist")
                        .addToBackStack(null)
                        .commit()

                } else {
                    supportFragmentManager.let { it1 ->
                        CustomerBottomSheet("Customer", this, this).show(
                            it1,
                            "ModalBottomSheet"
                        )
                    }

                }
            }
        }


        bottomTabBinding.SettingLinearLayout.setSafeOnClickListener {

            supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, SettingsFragment(), "settings").addToBackStack(null)
                .commit()
        }


        menuBinding.LogoutRelativeLayout.setSafeOnClickListener {
            androidextention.logOutDialog(this)
        }

    }


    @SuppressLint("RtlHardcoded", "SetTextI18n")
    fun retailerClicks() {

        menuBinding.onServicesRetailer.setSafeOnClickListener {

            binding.drawerLayout.closeDrawer(Gravity.LEFT)

            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.FrameLayout,
                    Customers_Deals(
                        "",
                        "customerOnService",
                        "customer",
                        "Deals on Bookings",
                        "Deals to customer",
                        ""
                    ),
                    "CustomersDeals"
                )
                .addToBackStack(null)
                .commit()
            menuBinding.llDealscustomer.visibility = View.GONE
            menuBinding.rightarrow.visibility = View.VISIBLE
            menuBinding.downarrow.visibility = View.GONE
            binding.logoutButton.visibility = View.GONE

        }

        menuBinding.requestedServicesRetailer.setSafeOnClickListener {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
            supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, MyBookingsFragment(""), "viewService")
                .addToBackStack(null).commit()
        }

        menuBinding.bookingsServicesRetailer.setSafeOnClickListener {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
            supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, ServiceAvailable(this, ""), "serviceAvailable")
                .addToBackStack(null).commit()
        }

        menuBinding.myWalletClickRetailer.setSafeOnClickListener {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
            startActivity(Intent(this, CustomerWalletActivity::class.java))

        }

        menuBinding.productCategoryRetailerRL.setSafeOnClickListener {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
            val bundle = Bundle()
            bundle.putString("seeall", "seeallcategory")
            val fragObj = Category(this)
            fragObj.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, fragObj, "category").addToBackStack(null)
                .commit()
        }

        menuBinding.MyCartRetailerRelativeLayout.setSafeOnClickListener {

            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.isLogin) == "true") {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.FrameLayout, MyCartFragment(""), "addCart")
                    .addToBackStack(null)
                    .commit()
                binding.drawerLayout.closeDrawer(Gravity.LEFT)

                menuBinding.llPaymenthistory.visibility = View.GONE
                menuBinding.rightarrowretailer1.visibility = View.VISIBLE
                menuBinding.downarrowretailer1.visibility = View.GONE
                menuBinding.llDealsRetailers.visibility = View.GONE
                menuBinding.rightarrowretailer.visibility = View.VISIBLE
                menuBinding.downarrowretailer.visibility = View.GONE

                binding.logoutButton.visibility = View.GONE


            } else {
                supportFragmentManager.let { it1 ->
                    CustomerBottomSheet("Retailer", this, this).show(
                        it1,
                        "ModalBottomSheet"
                    )
                }
            }

        }
        menuBinding.PaymentHistoryRetailerRelativeLayout.setSafeOnClickListener {

            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.isLogin) == "true") {


                supportFragmentManager.beginTransaction()
                    .replace(R.id.FrameLayout, PaymentDescription("SideMenu"), "payment")
                    .addToBackStack(null)
                    .commit()
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
                menuBinding.llPaymenthistory.visibility = View.GONE
                menuBinding.rightarrowretailer1.visibility = View.VISIBLE
                menuBinding.downarrowretailer1.visibility = View.GONE
                menuBinding.llDealsRetailers.visibility = View.GONE
                menuBinding.rightarrowretailer.visibility = View.VISIBLE
                menuBinding.downarrowretailer.visibility = View.GONE
                binding.logoutButton.visibility = View.GONE

            } else {
                supportFragmentManager.let { it1 ->
                    CustomerBottomSheet("Retailer", this, this).show(
                        it1,
                        "ModalBottomSheet"
                    )
                }
            }
        }



        menuBinding.LoginRelativeLayout.setSafeOnClickListener {

            supportFragmentManager.let { it1 ->
                CustomerBottomSheet("Retailer", this, this).show(
                    it1,
                    "ModalBottomSheet"
                )
            }

        }

        binding.greyBellImageView.setSafeOnClickListener {

            supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, Notification(), "notification")
                .addToBackStack(null)
                .commit()
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
            menuBinding.llPaymenthistory.visibility = View.GONE
            menuBinding.rightarrowretailer1.visibility = View.VISIBLE
            menuBinding.downarrowretailer1.visibility = View.GONE
            binding.logoutButton.visibility = View.GONE
            binding.greyBellImageView.visibility = View.VISIBLE

            binding.logoutButton.visibility = View.GONE


        }

        binding.DealsImageView.setSafeOnClickListener {

            val bottomsheet = DealsBottomSheet("dealsRetailer", this)
            bottomsheet.show(supportFragmentManager, "bottomSheet")


        }

        menuBinding.LogoutRelativeLayout.setSafeOnClickListener {
            androidextention.logOutDialog(this)
        }

        binding.logoutButton.setSafeOnClickListener {
            androidextention.logOutDialog(this)

        }

//////////BOTTOM ICON//////////////////////


        bottomTabBinding.homeGrey.setSafeOnClickListener {

            val bundle  = Bundle()
            bundle.putString("productId","")
            bundle.putString("serviceId","")
            bundle.putString("campainOn","")
            bundle.putString("notifyType","notifyType")
            val obj =  RetailerHomepage(this, flag)
            obj.arguments = bundle


            supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, obj, "home")
                .addToBackStack(null).commit()

        }

        bottomTabBinding.llOrderTab.setSafeOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, OrderManagement(), "ordermanagement")
                .addToBackStack(null).commit()

        }

        bottomTabBinding.llProductTab.setSafeOnClickListener {
            ProductManagement.apiProductManagementCallFlag = true
            supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, ProductManagement(), "productmanagement")
                .addToBackStack(null).commit()

        }


        bottomTabBinding.WishlistGrey.setSafeOnClickListener {


            supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, Wishlist("wishlist"), "wishlist").addToBackStack(null)
                .commit()


        }
        bottomTabBinding.SettingLinearLayout.setSafeOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, SettingsFragment(), "settings").addToBackStack(null)
                .commit()

        }

        menuBinding.HomeRelativeLayout.setSafeOnClickListener {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
        }


        menuBinding.DealsRetailerRelativeLayout.setSafeOnClickListener {

            if (!clickFlag) {
                clickFlag = true
                dealFlag = false
                menuBinding.llDealsRetailers.visibility = View.VISIBLE
                menuBinding.rightarrowretailer.visibility = View.GONE
                menuBinding.downarrowretailer.visibility = View.VISIBLE
                menuBinding.llPaymenthistory.visibility = View.GONE
                menuBinding.rightarrowretailer1.visibility = View.VISIBLE
                menuBinding.downarrowretailer1.visibility = View.GONE
            } else {
                clickFlag = false
                dealFlag = true
                menuBinding.llDealsRetailers.visibility = View.GONE
                menuBinding.rightarrowretailer.visibility = View.VISIBLE
                menuBinding.downarrowretailer.visibility = View.GONE

            }


        }

        menuBinding.PaymentHistoryRetailerRelativeLayout.setSafeOnClickListener {


            binding.logoutButton.visibility = View.GONE

            if (dealFlag == false) {
                dealFlag = true
                clickFlag = false
                menuBinding.llPaymenthistory.visibility = View.VISIBLE
                menuBinding.downarrowretailer1.visibility = View.VISIBLE
                menuBinding.rightarrowretailer1.visibility = View.GONE
                menuBinding.llDealsRetailers.visibility = View.GONE
                menuBinding.rightarrowretailer.visibility = View.VISIBLE
                menuBinding.downarrowretailer.visibility = View.GONE

            } else {
                clickFlag = true
                dealFlag = false
                menuBinding.llPaymenthistory.visibility = View.GONE
                menuBinding.rightarrowretailer1.visibility = View.VISIBLE
                menuBinding.downarrowretailer1.visibility = View.GONE

            }


        }
        menuBinding.dealsfromcustomer.setSafeOnClickListener {
            DealsManagement.apiDealManagementCallFlag = true


            binding.drawerLayout.closeDrawer(Gravity.LEFT)
            supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, DealsManagement(""), "serviceDeals").addToBackStack(null)
                .commit()
            menuBinding.llPaymenthistory.visibility = View.GONE
            menuBinding.rightarrowretailer1.visibility = View.VISIBLE
            menuBinding.downarrowretailer1.visibility = View.GONE
            menuBinding.llDealsRetailers.visibility = View.GONE
            menuBinding.rightarrowretailer.visibility = View.VISIBLE
            menuBinding.downarrowretailer.visibility = View.GONE
            binding.logoutButton.visibility = View.GONE

        }

        menuBinding.dealsfromwholesalers.setSafeOnClickListener {


            binding.drawerLayout.closeDrawer(Gravity.LEFT)
            supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, Services_DealsScreen(""), "serviceDeals")
                .addToBackStack(null).commit()
            menuBinding.llPaymenthistory.visibility = View.GONE
            menuBinding.rightarrowretailer1.visibility = View.VISIBLE
            menuBinding.downarrowretailer1.visibility = View.GONE
            menuBinding.llDealsRetailers.visibility = View.GONE
            menuBinding.rightarrowretailer.visibility = View.VISIBLE
            menuBinding.downarrowretailer.visibility = View.GONE
            binding.logoutButton.visibility = View.GONE

        }
        menuBinding.ordersRelativeLayout.setSafeOnClickListener {


            binding.drawerLayout.closeDrawer(Gravity.LEFT)
            supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, OrderHistoryFragment(""), "OrderHistory").addToBackStack(null)
                .commit()
            menuBinding.llPaymenthistory.visibility = View.GONE
            menuBinding.rightarrowretailer1.visibility = View.VISIBLE
            menuBinding.downarrowretailer1.visibility = View.GONE
            menuBinding.llDealsRetailers.visibility = View.GONE
            menuBinding.rightarrowretailer.visibility = View.VISIBLE
            menuBinding.downarrowretailer.visibility = View.GONE
            binding.logoutButton.visibility = View.GONE

        }

        menuBinding.paymentfromcustomer.setSafeOnClickListener {


            binding.drawerLayout.closeDrawer(Gravity.LEFT)

            val bundle = Bundle()
            bundle.putString("flagSide", "retailer")
            bundle.putString("paymentFlag", "PaymentFromCustomer")
            bundle.putString("title", "Payment From Customers")
            val fragobj = payment_description_sp()
            fragobj.arguments = bundle

            supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, fragobj, "payment_description").addToBackStack(null)
                .commit()
            menuBinding.llPaymenthistory.visibility = View.GONE
            menuBinding.rightarrowretailer1.visibility = View.VISIBLE
            menuBinding.downarrowretailer1.visibility = View.GONE
            menuBinding.llDealsRetailers.visibility = View.GONE
            menuBinding.rightarrowretailer.visibility = View.VISIBLE
            menuBinding.downarrowretailer.visibility = View.GONE
            binding.logoutButton.visibility = View.GONE

        }

        menuBinding.paymenttowholesalers.setSafeOnClickListener {


            binding.drawerLayout.closeDrawer(Gravity.LEFT)


            val bundle = Bundle()
            bundle.putString("flagSide", "retailer")
            bundle.putString("paymentFlag", "PaymentFromWholesalers")
            bundle.putString("title", "Payment to Wholesalers")

            val fragobj = payment_description_sp()
            fragobj.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, fragobj, "payment_description").addToBackStack(null)
                .commit()
            menuBinding.llPaymenthistory.visibility = View.GONE
            menuBinding.rightarrowretailer1.visibility = View.VISIBLE
            menuBinding.downarrowretailer1.visibility = View.GONE
            menuBinding.llDealsRetailers.visibility = View.GONE
            menuBinding.rightarrowretailer.visibility = View.VISIBLE
            menuBinding.downarrowretailer.visibility = View.GONE
            binding.logoutButton.visibility = View.GONE

        }

    }


    @SuppressLint("SetTextI18n")
    override fun title(s: String) {
        if (s == "Category") {
            binding.PreLoginTitleTextView2.text = "Category"
            bottomTabBinding.CategoryGreyImageView.visibility = View.GONE
            bottomTabBinding.CategoryRed.visibility = View.VISIBLE

            bottomTabBinding.home.visibility = View.GONE
            bottomTabBinding.serviceRed.visibility = View.GONE
            bottomTabBinding.WishlistRed.visibility = View.GONE
            bottomTabBinding.redSettingImageView.visibility = View.GONE

            bottomTabBinding.homeGreyImageView.visibility = View.VISIBLE
            bottomTabBinding.serviceGreyImageView.visibility = View.VISIBLE
            bottomTabBinding.WishlistGreyImageView.visibility = View.VISIBLE
            bottomTabBinding.greySettingImageView.visibility = View.VISIBLE
            binding.greyBellImageView.visibility = View.VISIBLE
        } else if (s == "Services") {

            binding.PreLoginTitleTextView2.text = "Services"
            bottomTabBinding.serviceGreyImageView.visibility = View.GONE
            bottomTabBinding.serviceRed.visibility = View.VISIBLE

            bottomTabBinding.home.visibility = View.GONE
            bottomTabBinding.CategoryRed.visibility = View.GONE
            bottomTabBinding.WishlistRed.visibility = View.GONE
            bottomTabBinding.redSettingImageView.visibility = View.GONE

            bottomTabBinding.homeGreyImageView.visibility = View.VISIBLE
            bottomTabBinding.CategoryGreyImageView.visibility = View.VISIBLE
            bottomTabBinding.WishlistGreyImageView.visibility = View.VISIBLE
            bottomTabBinding.greySettingImageView.visibility = View.VISIBLE
            binding.greyBellImageView.visibility = View.VISIBLE

        }


    }








    @SuppressLint("RtlHardcoded")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
        } else {
            super.onBackPressed()
        }

    }


    override fun isLoginListener() {
        CommonFunctions.getProfileApiApi(this,  menuBinding.name, menuBinding.ProfilePicture)
    }

    override fun dealsOnProduct(flag: String) {

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.FrameLayout, Customers_Deals(
                    "",
                    "customerOnProduct",
                    "customer",
                    "Deals on products",
                    "Deals to customer",
                    ""
                ), "CustomersDeals"
            )
            .addToBackStack(null)
            .commit()

    }

    override fun dealsOnServices(flag: String) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.FrameLayout,
                Customers_Deals(
                    "",
                    "customerOnService",
                    "customer",
                    "Deals on Bookings",
                    "Deals to customer",
                    ""
                ),
                "CustomersDeals"
            )
            .addToBackStack(null)
            .commit()

    }

    override fun dealsFromWholesaler() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.FrameLayout, Services_DealsScreen(""), "serviceDeals")
            .addToBackStack(null).commit()
    }


}
