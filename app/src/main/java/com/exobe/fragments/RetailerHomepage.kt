package com.exobe.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.exobe.activities.*
import com.exobe.Adapter.HomeCategory
import com.exobe.Adapter.ProductAdpter
import com.exobe.adaptor.ImageSliderAdaptorbanner
import com.exobe.adaptor.home_services_adapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.androidextention
import com.exobe.customClicks.*
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.*
import com.exobe.entity.response.customerservices.ListCategoryServiceDocs
import com.exobe.entity.response.product.DealBannerDocs
import com.exobe.entity.response.product.GuestProductDocs
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import me.relex.circleindicator.CircleIndicator3
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.HomeProductDealsAdaptor
import com.exobe.adaptor.ServiceDealCustomerAdaptor
import com.exobe.fragments.cart.MyCartFragment
import com.exobe.modelClass.ChooseServicesMyModel
import com.exobe.modelClass.ServicesMyModel
import com.exobe.utils.*
import com.exobe.utils.CommonFunctions.hideKeyboard
import com.exobe.androidextention.initLoader
import com.exobe.bottomSheet.CustomerBottomSheet
import com.exobe.extension.diasplay_toast
import com.exobe.fragments.orderHistory.MyBookingsFragment
import com.exobe.fragments.orderHistory.OrderHistoryFragment
import com.exobe.fragments.products.ProductViewFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class RetailerHomepage(var setTitles: SetTitles, var flag: String) : Fragment(),
    ProductViewListener,
    CustomeClick2, CategoryClick, CustomeClick4, UpdateIsLoginListener, DealsForCustomer,
    ServiceDealsForCustomer, BannerClickListener {

    var chooseServicesMyModel = ArrayList<ChooseServicesMyModel>()
    var productCategoryData: ArrayList<Docs> = ArrayList()

    private var productIdNotification = ""
    private var serviceId = ""
    private var campaignOn = ""
    private var notifyType = ""
    private var categoryId = ""
    private var serviceProvideId = ""

    var productListData: ArrayList<GuestProductDocs> = ArrayList()
    lateinit var CategoryRecycler: RecyclerView
    lateinit var services_recycler: RecyclerView
    lateinit var ProductsRecyclerview: RecyclerView
    lateinit var adapter: HomeCategory
    lateinit var SeeAllCategories_TV: TextView
    lateinit var SeeAllProducts_TV: TextView
    lateinit var categorytext: LinearLayout
    lateinit var producttext: LinearLayout
    lateinit var servicestext: LinearLayout
    lateinit var seeAllServices_TV: TextView
    lateinit var productAdpter: ProductAdpter
    lateinit var imageAdaptor: ImageSliderAdaptorbanner
    lateinit var mContext: Context
    lateinit var multi_image: ViewPager2
    lateinit var indicator3: CircleIndicator3
    var images = ArrayList<RetailerHomePageDealDetails>()
    private val sliderHandler: Handler = Handler()
    var bookingListData: ArrayList<ListCategoryServiceDocs> = ArrayList()
    lateinit var home_services_adapter: home_services_adapter


    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var search: RelativeLayout

    lateinit var textViewHome: TextView
    lateinit var textViewServices: TextView
    lateinit var textViewCategory: TextView
    lateinit var textViewWishlist: TextView
    lateinit var textViewSetting: TextView
    lateinit var textViewOrder: TextView
    lateinit var textViewProduct: TextView
    lateinit var dealbanner: RelativeLayout
    lateinit var productDealView: LinearLayout


    lateinit var pulltorefresh: SwipeRefreshLayout

    lateinit var searchHome: EditText
    var searchFlag = false
    var isHomeRefresh = false

    lateinit var noProductData: TextView
    lateinit var noDataFound: TextView

    lateinit var seeAllSerButton: LinearLayout
    lateinit var seeAllCatButton: LinearLayout
    lateinit var seeAllProButton: LinearLayout
    lateinit var seeAllDeals: LinearLayout
    lateinit var seeAllServiceDeals: LinearLayout


    var homeBannerData: ArrayList<HomeBannerDoc> = ArrayList()
    var dealOnProductsData: ArrayList<DealBannerDocs> = ArrayList()
    var dealtype = ""

    lateinit var HomeScreen: LinearLayout
    private val LOCATION_PERMISSION_REQ_CODE = 1000;


    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null

    var lat = ""
    var long = ""
    var userType = ""
    private var timer: Timer? = null
    private var apiCallFlag = true


    lateinit var serviceDealView: LinearLayout
    lateinit var dealsRecycler: RecyclerView
    lateinit var serviceDealsRecycler: RecyclerView
    lateinit var dealAdpater: HomeProductDealsAdaptor
    lateinit var serviceDealCustomerAdaptor: ServiceDealCustomerAdaptor
    var delasOnBookingData = ArrayList<Customerdeals_Result>()

    private var searchJob: Job? = null
    lateinit var searchProgress: ProgressBar



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_retailer_homepage, container, false)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        textViewHome = activity?.findViewById(R.id.TVhome)!!
        textViewServices = activity?.findViewById(R.id.TVservices)!!
        textViewCategory = activity?.findViewById(R.id.TVcategory)!!
        textViewWishlist = activity?.findViewById(R.id.TVwishlist)!!
        textViewSetting = activity?.findViewById(R.id.TVsettings)!!
        textViewOrder = activity?.findViewById(R.id.TVorder)!!
        textViewProduct = activity?.findViewById(R.id.TVproduct)!!
        seeAllSerButton = view.findViewById(R.id.seeAllSerButton)
        seeAllCatButton = view.findViewById(R.id.seeAllCatButton)
        seeAllProButton = view.findViewById(R.id.seeAllProButton)
        noDataFound = view.findViewById(R.id.noDataFound)!!
        searchHome = view.findViewById(R.id.searchHome)
        searchHome = view.findViewById(R.id.searchHome)
        CategoryRecycler = view.findViewById(R.id.CategoryRecycler)
        pulltorefresh = view.findViewById(R.id.pulltorefresh)
        ProductsRecyclerview = view.findViewById(R.id.ProductsRecyclerview)
        multi_image = view.findViewById(R.id.multi_image)
        SeeAllCategories_TV = view.findViewById(R.id.SeeAllCategories_TextView)
        SeeAllProducts_TV = view.findViewById(R.id.SeeAllProducts_TextView)
        seeAllServices_TV = view.findViewById(R.id.seeAllServices_TextView)
        indicator3 = view.findViewById(R.id.indicator)
        services_recycler = view.findViewById(R.id.services_recycler)
        search = view.findViewById(R.id.search)
        dealbanner = view.findViewById(R.id.dealbanner)
        categorytext = view.findViewById(R.id.categorytext)
        producttext = view.findViewById(R.id.producttext)
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        noProductData = view.findViewById(R.id.noProductData)
        servicestext = view.findViewById(R.id.servicestext)
        HomeScreen = view.findViewById(R.id.HomeScreen)
        dealsRecycler = view.findViewById(R.id.DealsRecycler)
        serviceDealsRecycler = view.findViewById(R.id.serviceDealsRecycler)
        productDealView = view.findViewById(R.id.productDealView)
        serviceDealView = view.findViewById(R.id.serviceDealView)
        seeAllDeals = view.findViewById(R.id.seeAllDeals)
        seeAllServiceDeals = view.findViewById(R.id.seeAllServiceDeals)
        searchProgress = view.findViewById(R.id.search_progress)

        setToolbar()
        TabHandler.HandleTab(R.id.home_grey, requireActivity())
        locationpermission()

        try {
            lat = SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.LAT)!!
            long = SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.LONG)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }


        arguments?.getString("productId")?.let { productIdNotification = it }
        arguments?.getString("serviceId")?.let { serviceId = it }
        arguments?.getString("campainOn")?.let { campaignOn = it }
        arguments?.getString("notifyType")?.let { notifyType = it }
        arguments?.getString("serviceProvideId")?.let { serviceProvideId = it }
        arguments?.getString("categoryId")?.let { categoryId = it }

        if (campaignOn.isNotEmpty()){
            if (campaignOn.uppercase() =="PRODUCT"){
                when(notifyType){
                    "MANUAL" ->{
                        val bundle = Bundle()
                        bundle.putString("productId2", productIdNotification)
                        val fragObj = ProductViewFragment()
                        fragObj.arguments = bundle
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.FrameLayout, fragObj, "productView").addToBackStack(null)
                            .commit()
                        campaignOn = ""
                    }
                    "SEMI_AUTOMATIC" ->{
                        val bundle = Bundle()

                        val fragObj = MyCartFragment("")
                        fragObj.arguments = bundle

                        parentFragmentManager.beginTransaction()
                            .replace(R.id.FrameLayout, fragObj, "addCart")
                            .addToBackStack(null)
                            .commit()
                        campaignOn = ""
                    }
                    "AUTOMATIC" ->{
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.FrameLayout, OrderHistoryFragment(""), "OrderHistory")
                            .addToBackStack(null).commit()
                        campaignOn = ""
                    }
                }

            }else{
                when(notifyType){

                    "SEMI_AUTOMATIC" ->{
                        var bundle = Bundle()
                        bundle.putString("flag", "service")
                        bundle.putString("id", categoryId)
                        bundle.putString("userId", serviceProvideId)
                        bundle.putString("comment", "")
                        var fragObj = ServiceDetailPage()
                        fragObj.arguments = bundle
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.FrameLayout, fragObj, "ServiceDetailPage")
                            .addToBackStack(null).commit()
                    }
                    "AUTOMATIC" ->{
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.FrameLayout, MyBookingsFragment(""), "viewService")
                            .addToBackStack(null).commit()
                    }
                }

            }
        }


        mainHeader.visibility = View.VISIBLE
        noDataFound.visibility = View.GONE
        dealtype = "PRODUCT"
        searchHome.setText("")



        userType = SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.USER_TYPE)!!
        searchHome.tag = false

        searchHome.addTextChangedListener(textWatcher)





        cart.setSafeOnClickListener {
            val isLogin = SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.isLogin)
            if (isLogin == "true") {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.FrameLayout, MyCartFragment(""), "addCart").addToBackStack(null)
                    .commit()
            } else {
                if (userType == "RETAILER") {
                    parentFragmentManager.let { it1 ->
                        CustomerBottomSheet(
                            "Retailer",
                            mContext,
                            this
                        ).show(it1, "ModalBottomSheet")
                    }
                } else {
                    parentFragmentManager.let { it1 ->
                        CustomerBottomSheet(
                            "Customer",
                            mContext,
                            this
                        ).show(it1, "ModalBottomSheet")
                    }
                }

            }
        }


        seeAllDeals.setSafeOnClickListener {
            parentFragmentManager.beginTransaction()
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

        seeAllServiceDeals.setSafeOnClickListener {
            parentFragmentManager.beginTransaction()
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


        SeeAllCategories_TV.setSafeOnClickListener {

            textViewHome.setTextColor(Color.parseColor("#FFFFFF"))
            textViewSetting.setTextColor(Color.parseColor("#FFFFFF"))
            textViewWishlist.setTextColor(Color.parseColor("#FFFFFF"))
            textViewCategory.setTextColor(Color.parseColor("#FFFFFF"))
            textViewServices.setTextColor(Color.parseColor("#FFFFFF"))
            textViewOrder.setTextColor(Color.parseColor("#FFFFFF"))
            textViewProduct.setTextColor(Color.parseColor("#FFFFFF"))
            val bundle = Bundle()
            bundle.putString("seeall", "seeallcategory")
            val fragobj = Category(setTitles)
            fragobj.setArguments(bundle)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.FrameLayout, fragobj, "category")?.addToBackStack(null)
                ?.commit()
        }

        SeeAllProducts_TV.setSafeOnClickListener {

            val bundle = Bundle()
            bundle.putString("id", "")
            bundle.putString("name", "Products")
            val fragObj = CategoryItem()
            fragObj.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, fragObj, "categoryItem").addToBackStack(null)
                .commit()


        }

        seeAllServices_TV.setSafeOnClickListener {
            textViewHome.setTextColor(Color.parseColor("#FFFFFF"))
            textViewSetting.setTextColor(Color.parseColor("#FFFFFF"))
            textViewWishlist.setTextColor(Color.parseColor("#FFFFFF"))
            textViewCategory.setTextColor(Color.parseColor("#FFFFFF"))
            textViewServices.setTextColor(Color.parseColor("#FFFFFF"))
            textViewOrder.setTextColor(Color.parseColor("#FFFFFF"))
            textViewProduct.setTextColor(Color.parseColor("#FFFFFF"))
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.FrameLayout, ServiceAvailable(setTitles, ""), "serviceAvailable")
                ?.addToBackStack(null)?.commit()
        }

        val sliderRunnable = Runnable {
            val currentItem = multi_image.currentItem
            val lastItem = multi_image.adapter?.itemCount?.minus(1) ?: 0
            if (currentItem == lastItem) {
                multi_image.currentItem = 0
            } else {
                multi_image.currentItem = currentItem + 1
            }
        }

        val callback: ViewPager2.OnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        }

        multi_image.registerOnPageChangeCallback(callback)

        pulltorefresh.setOnRefreshListener {
            if (androidextention.isOnline(mContext)) {
                HomeScreen.visibility = View.VISIBLE
                lat = SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.LAT)!!
                long = SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.LONG)!!

                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                searchHome.setText("")
                searchFlag = false
                if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.USER_TYPE).equals("CUSTOMER")) {
                    homePageApi(lat = lat.toDouble(),lng = long.toDouble(), dealType = "", userType = "RETAILER", search = "")

                } else if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.USER_TYPE).equals("RETAILER")) {


                    homePageApi(lat = lat.toDouble(),lng = long.toDouble(),dealType="PRODUCT", userType = "WHOLE_SALER", search = "")
                }
            } else {
                HomeScreen.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
            }
            pulltorefresh.isRefreshing = false
        }
        return view

    }


    private fun withoutCustomerData() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            HomeScreen.visibility = View.VISIBLE
            categorytext.visibility = View.VISIBLE
            producttext.visibility = View.VISIBLE
            servicestext.visibility = View.VISIBLE
            noProductData.visibility = View.GONE
            ProductsRecyclerview.visibility = View.VISIBLE


            if (homeBannerData.size > 0) {
                dealbanner.visibility = View.VISIBLE
                setImageAdaptor(homeBannerData)
            } else {
                dealbanner.visibility = View.GONE
            }
            //product data
            if (productListData.size in 1..3) {
                HomeScreen.visibility = View.VISIBLE
                producttext.visibility = View.VISIBLE
                seeAllProButton.visibility = View.GONE
                productAdapter(productListData)
            } else if (productListData.size > 3) {
                HomeScreen.visibility = View.VISIBLE
                producttext.visibility = View.VISIBLE
                seeAllProButton.visibility = View.VISIBLE
                productAdapter(productListData)
            } else {
                producttext.visibility = View.GONE
                seeAllProButton.visibility = View.GONE
                if (productCategoryData.size == 0 && dealOnProductsData.size == 0 && productListData.size == 0 && bookingListData.size == 0) {
                }
            }


            // category data
            if (productCategoryData.size == 0 && dealOnProductsData.size == 0 && productListData.size == 0 && bookingListData.size == 0) {
            } else {
                HomeScreen.visibility = View.GONE
                if (productCategoryData.size == 0 && dealOnProductsData.size == 0 && productListData.size == 0 && bookingListData.size == 0) {
                }
            }
            if (productCategoryData.size in 1..3) {
                categorytext.visibility = View.VISIBLE
                HomeScreen.visibility = View.VISIBLE
                textViewCategory.visibility = View.VISIBLE
                seeAllCatButton.visibility = View.GONE
                categoryAdapter(productCategoryData)
            } else if (productCategoryData.size > 4) {
                categorytext.visibility = View.VISIBLE
                HomeScreen.visibility = View.VISIBLE
                textViewCategory.visibility = View.VISIBLE
                seeAllCatButton.visibility = View.VISIBLE
                categoryAdapter(productCategoryData)
            } else {
                categorytext.visibility = View.VISIBLE
                HomeScreen.visibility = View.VISIBLE
                textViewCategory.visibility = View.VISIBLE
                seeAllCatButton.visibility = View.GONE
                categoryAdapter(productCategoryData)
            }


            // services data
            if (productCategoryData.size == 0 && dealOnProductsData.size == 0 && productListData.size == 0 && bookingListData.size == 0) {
            }

            if (bookingListData.size in 1..2) {
                servicestext.visibility = View.VISIBLE
                seeAllSerButton.visibility = View.GONE
                services(bookingListData)

            } else if (bookingListData.size > 2) {
                servicestext.visibility = View.VISIBLE
                seeAllSerButton.visibility = View.VISIBLE
                services(bookingListData)
            } else {
                servicestext.visibility = View.GONE
                seeAllSerButton.visibility = View.GONE
            }


            //banner data
            if (dealOnProductsData.size > 0) {
                productDealView.visibility = View.VISIBLE
                setAdapterOfDeal(dealOnProductsData)
            } else {
                productDealView.visibility = View.GONE
            }

            if (delasOnBookingData.size > 0) {
                serviceDealView.isVisible = true
                setServiceDealAdapterOfDeal(delasOnBookingData)
            } else {
                serviceDealView.isVisible = false
            }


        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    fun setImageAdaptor(imageList: ArrayList<HomeBannerDoc>) {
        imageAdaptor = ImageSliderAdaptorbanner(imageList, mContext, this, "", this)
        multi_image.adapter = imageAdaptor
        if (imageList.size > 1) {
            indicator3.setViewPager(multi_image)
        }

    }


    override fun click2(_id: String?, servicename: String?) {

        val bundle = Bundle()
        bundle.putString("id", _id)
        bundle.putString("servicename", servicename)
        val frgobj = ServicesAvailableListing()
        frgobj.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.FrameLayout, frgobj, "serviceAvailable")
            ?.addToBackStack(null)?.commit()
    }

    override fun click4(dealId: String, productId: String) {


        val bundle = Bundle()
        bundle.putString("flag", "")
        bundle.putBoolean("flag1", false)
        bundle.putString("deal_id", dealId)
        bundle.putString("productid", productId)

        val fragObj = ViewAddDeals()
        fragObj.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.FrameLayout, fragObj, "viewAddDeals").addToBackStack(null)
            .commit()

    }

    fun setToolbar() {
        title = activity?.findViewById(R.id.PreLoginTitle_TextView2)!!


        cart = activity?.findViewById(R.id.cart_icon)!!
        filter = activity?.findViewById(R.id.filter_icon)!!
        back = activity?.findViewById(R.id.imageView_back)!!
        MenuClick = activity?.findViewById(R.id.MenuClick)!!
        DealsImageView = activity?.findViewById(R.id.Deals_ImageView)!!
        greyBellImageView = activity?.findViewById(R.id.greyBell_ImageView)!!
        logoutButton = activity?.findViewById(R.id.logoutButton)!!
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
        cartCount.visibility = View.GONE
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
        logoutButton.visibility = View.GONE


        if (SavedPrefManager.getStringPreferences(activity, SavedPrefManager.isLogin) == "true") {
            SavedPrefManager.savePreferenceBoolean(context, SavedPrefManager.NOTIFICATION_VISIBLE, true)
            NotificationApi.notifiactionCountApi(mContext, greyBellImageView, logoutButton)

        }

    }

    fun categoryAdapter(data: ArrayList<Docs>) {
        CategoryRecycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        adapter = HomeCategory(mContext, data, this)
        CategoryRecycler.adapter = adapter
    }

    fun productAdapter(Productdata: ArrayList<GuestProductDocs>) {
        ProductsRecyclerview.layoutManager = LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
        productAdpter = ProductAdpter(mContext, Productdata, this, flag)
        ProductsRecyclerview.adapter = productAdpter

    }

    fun services(Services: ArrayList<ListCategoryServiceDocs>) {
        services_recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        home_services_adapter = home_services_adapter(mContext, Services, this)
        services_recycler.adapter = home_services_adapter
    }

    override fun categoryClick(_id: String?, categoryName: String?) {

        val bundle = Bundle()
        bundle.putString("id", _id)
        bundle.putString("name", categoryName)
        val fragObj = CategoryItem()
        fragObj.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.FrameLayout, fragObj, "categoryItem").addToBackStack(null).commit()
    }

    override fun onResume() {
        super.onResume()
        try {
            if (apiCallFlag) {
                if (userType == "RETAILER") {
                    homePageApi(lat = lat.toDouble(),lng = long.toDouble(), dealType = "PRODUCT", userType = "WHOLE_SALER",search = "")
                } else if (userType == "CUSTOMER") {
                    homePageApi(lat = lat.toDouble(),lng = long.toDouble(),dealType="PRODUCT", userType = "RETAILER", search = "")
                }
                apiCallFlag = false
            } else {
                setToolbar()
                withoutCustomerData()

            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    private fun locationpermission() {
        if (ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQ_CODE
            )
        } else {
            LocationClass.getCurrentLocation(mContext)
            LocationClass.displayLocationSettingsRequest(mContext)
        }

    }

    override fun isLoginListener() {
        var name = activity?.findViewById<TextView>(R.id.name)
        var userProfile = activity?.findViewById<ImageView>(R.id.Profile_picture)
        CommonFunctions.getProfileApiApi(mContext, name, userProfile)
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard(requireActivity())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

// Deal Section


    fun setAdapterOfDeal(data: ArrayList<DealBannerDocs>) {
        dealsRecycler.layoutManager = LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
        dealAdpater = HomeProductDealsAdaptor(mContext, data, "Home", this)
        dealsRecycler.adapter = dealAdpater
    }


    override fun click(flag: String, flag1: Boolean, _id: String, productid: String) {
        val bundle = Bundle()
        bundle.putString("flag", flag)
        bundle.putBoolean("flag1", flag1)
        bundle.putString("deal_id", _id)
        bundle.putString("productid", productid)

        val fragobj = ViewAddDeals()
        fragobj.setArguments(bundle)
        parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragobj, "viewAddDeals")
            .addToBackStack(null).commit()

    }


    fun setServiceDealAdapterOfDeal(data: ArrayList<Customerdeals_Result>) {
        serviceDealsRecycler.layoutManager = LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
        serviceDealCustomerAdaptor = ServiceDealCustomerAdaptor(mContext, data, "Home", this)
        serviceDealsRecycler.adapter = serviceDealCustomerAdaptor
    }

    override fun serviceDealClick(
        _id: String,
        serviceName: String,
        dealPrice: Double,
        firstName: String,
        lastName: String,
        categoryName: String,
        categoryImage: String,
        subCategoryName: String,
        _id1: String,
        dealId: String,
        priceTag:String,
        actualPrice:String,
        discount:String
    ) {

            ServiceRequestReview.apiServiceRequestCallFlag = true
            chooseServicesMyModel.clear()
            val servicesMyModel = ArrayList<ServicesMyModel>()
            servicesMyModel.add(ServicesMyModel(_id1, _id1, serviceName, dealPrice,priceTag,actualPrice,discount, true))
            chooseServicesMyModel.add(ChooseServicesMyModel(subCategoryName, _id1, servicesMyModel))
            val serviceProviderName = "$firstName $lastName"
            val bundle = Bundle()
            bundle.putSerializable("CHOOSE_LIST", chooseServicesMyModel)
            bundle.putString("name", serviceProviderName)
            bundle.putString("serviceImage", categoryImage)
            bundle.putString("categoryName", categoryName)
            bundle.putString("userId", _id)
            bundle.putString("dealId", dealId)
            val frgObj = ServiceRequestReview()
            frgObj.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, frgObj, "serviceRequest").addToBackStack(null).commit()


    }

    override fun onDestroy() {
        super.onDestroy()
        DealsImageView.visibility = View.VISIBLE
        greyBellImageView.visibility = View.VISIBLE
    }

    override fun viewProduct(productId: String, dealId: String) {
            val bundle = Bundle()
            bundle.putString("productId2", productId)
            val fragObj = ProductViewFragment()
            fragObj.arguments = bundle

            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.FrameLayout, fragObj, "productView")?.addToBackStack(null)
                ?.commit()

    }

    override fun bannerClick(url: String) {
        if(url.contains("http")) {
            redirectUrl(url)
        }
    }

    fun redirectUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }


    private fun homePageApi(lat:Double, lng:Double,dealType:String,userType:String, search:String) {
        if (androidextention.isOnline(requireContext())) {
            if(searchFlag){
                searchProgress.isVisible = true
            }else{
                if (!isHomeRefresh){
                    Progresss.start(requireContext())
                }

            }
            internet_connection.visibility = View.GONE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<HomePageResponse> =
                ApiCallBack<HomePageResponse>(object :
                    ApiResponseListener<HomePageResponse> {
                    override fun onApiSuccess(
                        response: HomePageResponse,
                        apiName: String?
                    ) {
                        searchProgress.isVisible = false
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            try {

                                isHomeRefresh = true

                                productCategoryData.clear()
                                homeBannerData.clear()
                                productListData.clear()
                                dealOnProductsData.clear()
                                bookingListData.clear()
                                delasOnBookingData.clear()

                                productCategoryData =  response.result.categories
                                homeBannerData = response.result.banner
                                productListData = response.result.products
                                dealOnProductsData =  response.result.dealsOnProduct
                                bookingListData  = response.result.bookingCategories
                                delasOnBookingData = response.result.dealsOnBooking


                                if(productCategoryData.isEmpty() && homeBannerData.isEmpty() &&
                                    productListData.isEmpty() && dealOnProductsData.isEmpty() &&
                                    bookingListData.isEmpty() && delasOnBookingData.isEmpty()){
                                    noDataFound.isVisible =true
                                    HomeScreen.isVisible =false

                                }else{
                                    HomeScreen.isVisible =true
                                    noDataFound.isVisible =false
                                }


                                categorytext.isVisible = productCategoryData.isNotEmpty()
                                seeAllCatButton.isVisible = productCategoryData.isNotEmpty()
                                CategoryRecycler.isVisible = productCategoryData.isNotEmpty()

                                categoryAdapter(productCategoryData)


                                dealbanner.isVisible = homeBannerData.isNotEmpty()
                                setImageAdaptor(homeBannerData)



                                producttext.isVisible = productListData.isNotEmpty()
                                seeAllProButton.isVisible = productListData.isNotEmpty()


                                productAdapter(productListData)



                                productDealView.isVisible = dealOnProductsData.isNotEmpty()
                                setAdapterOfDeal(dealOnProductsData)


                                servicestext.isVisible = bookingListData.isNotEmpty()
                                seeAllSerButton.isVisible = bookingListData.isNotEmpty()
                                services_recycler.isVisible = bookingListData.isNotEmpty()
                                services(bookingListData)


                                serviceDealView.isVisible = delasOnBookingData.isNotEmpty()
                                setServiceDealAdapterOfDeal(delasOnBookingData)





                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        noDataFound.isVisible = true
                        searchProgress.isVisible = false
                        Progresss.stop()
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        noDataFound.isVisible = true
                        searchProgress.isVisible = false
                        Progresss.stop()
                    }

                }, "homepageApi", requireContext())


            try {
                searchJob?.cancel()
                lifecycleScope.launch{
                    searchJob = launch {
                        serviceManager.homepageApi(callBack,lat=lat, lng=lng, dealType=dealType, userType=userType, page=1, limit=10,search = search)
                    }

                }



            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            diasplay_toast("Please check your internet connection.")
        }
    }




    private val textWatcher = object: TextWatcher {
        private var searchJob: Job? = null
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(text: Editable?) {
            if (text!!.length < 3 ){
                if (!searchFlag){
                    return
                }
                if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.USER_TYPE).equals("CUSTOMER")) {

                    homePageApi(lat = lat.toDouble(),lng = long.toDouble(), dealType = "", userType = "RETAILER", search ="")
                }else{
                    homePageApi(lat = lat.toDouble(),lng = long.toDouble(),dealType="PRODUCT", userType = "WHOLE_SALER", search = "")
                }
                searchFlag = false
                return
            }


            searchJob?.cancel() // Cancel previous search job if it exists

            searchFlag = true
            searchJob = CoroutineScope(Dispatchers.Main).launch {
                if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.USER_TYPE).equals("CUSTOMER")) {
                    homePageApi(lat = lat.toDouble(),lng = long.toDouble(), dealType = "PRODUCT", userType = "RETAILER", search = text.toString().trim())
            }else{
                    homePageApi(lat = lat.toDouble(),lng = long.toDouble(),dealType="PRODUCT", userType = "WHOLE_SALER", search = text.toString().trim())
            }

        }
    }

}
}
