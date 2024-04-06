package com.exobe.fragments.products

import android.Manifest
import android.annotation.SuppressLint
import android.view.View.OnFocusChangeListener
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exobe.Adapter.CategoryItemAdapter
import com.exobe.adaptor.OpenPopUp
import com.exobe.adaptor.PopupforCategory
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.bottomSheet.CustomerBottomSheet
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.*
import com.exobe.entity.response.customer.AddDealsCategoryResult
import com.exobe.entity.response.product.GuestProductDocs
import com.exobe.entity.response.product.GuestProductResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.validations.DialogUtils
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import android.widget.TextView.OnEditorActionListener
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.ProductListSubCategory
import com.exobe.fragments.cart.MyCartFragment
import com.exobe.modelClass.SubCategoryModalClass
import com.exobe.utils.CommonFunctions
import com.exobe.utils.LocationClass
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.*
import com.futuremind.recyclerviewfastscroll.RecyclerViewScrollListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class SeeAllProductsFragment : Fragment(),
    wishlistcustomclick, wishlistcustomclick2, popupItemClickListnerDeals, UpdateIsLoginListener, ProductViewListener, ServiceNameListener,SubCategoryClick {
    lateinit var mContext: Context
    lateinit var ItemCategory: RecyclerView
    lateinit var adpter: CategoryItemAdapter
    var nextPageToken: String? = null
    var Productdata: ArrayList<GuestProductDocs> = ArrayList()
    lateinit var producttext: LinearLayout
    lateinit var title: TextView
    lateinit var nestedScrollview_seeallproducts: NestedScrollView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var DFsearch: EditText
    lateinit var noProductData: TextView
    private lateinit var dialog: Dialog
    private lateinit var recyclerView: RecyclerView
    private lateinit var search_bar_edittext_popuplist: EditText
    private lateinit var swipe: SwipeRefreshLayout
    lateinit var adapter: OpenPopUp
    lateinit var adapterforcategory: PopupforCategory
    var data: ArrayList<GuestProductDocs> = ArrayList()
    var categoryListData = ArrayList<CategoryList_docs>()
    lateinit var progressbar: ProgressBar
    var searchFlag = false
    lateinit var progressbar_pop: ProgressBar
    lateinit var pagination_progressbar: ProgressBar
    var product: String = ""
    var CategoryId = ""
    var flag: String = "CategoryList"
    var totalCount = 0
    var remainingItems = 0
    var pages = 0
    var page = 1
    var limit = 10
    var dataLoadFlag = true
    var loaderFlag = true

    var count = 0
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    lateinit var pop_internet_connection: RelativeLayout
    var pop_lottie: LottieAnimationView? = null
    var pageIncreaseFlag = false
    var userType = ""
    private var timer: Timer? = null
    private val LOCATION_PERMISSION_REQ_CODE = 1000;
    private var apiCallFlag = true

    lateinit var subCategoryRecycler:RecyclerView
    var idSubCategory = ""
    var subCategoryArray: MutableList<SubCategoryModalClass> = mutableListOf()
    lateinit var adapterSubCategory : ProductListSubCategory



    private lateinit var scrollListener: RecyclerViewScrollListener
    var isLoading = false // Add this boolean flag to control pagination

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.activity_see_all_products, container, false)
        setToolbar()
        locationpermission()
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        ItemCategory = view.findViewById(R.id.ItemCategory)
        progressbar = view.findViewById(R.id.progressbar)
        nestedScrollview_seeallproducts = view.findViewById(R.id.nestedScrollview_seeallproducts)
        mainHeader.visibility = View.VISIBLE
        DFsearch = view.findViewById(R.id.DFsearch)
        noProductData = view.findViewById(R.id.noProductData)
        pagination_progressbar = view.findViewById(R.id.pagination_progressbar)
        swipe = view.findViewById(R.id.swipe)
        subCategoryRecycler = view.findViewById(R.id.subCategoryRecycler)

        userType = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.USER_TYPE).toString()
        DFsearch.tag = false
        DFsearch.onFocusChangeListener = OnFocusChangeListener { _, _ -> DFsearch.tag = true }

        when (userType) {
            "RETAILER" -> {
                swipe.setOnRefreshListener {
                    subCategoryArray.clear()
                    subCategoryRecycler.isVisible = false
                    resetPagination()
                    if (androidextention.isOnline(mContext)) {
                        internet_connection.visibility = View.GONE
                        lottie!!.initLoader(false)
                        CategoryId = ""

                        seeallproductsRetailerApi("", "WHOLE_SALER")
                    } else {
                        noProductData.visibility = View.GONE
                        ItemCategory.visibility = View.GONE
                        internet_connection.visibility = View.VISIBLE
                        lottie!!.initLoader(true)
                    }

                    swipe.isRefreshing = false

                }

                DFsearch.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        CommonFunctions.hideKeyboard(requireActivity())
                        return@OnEditorActionListener true
                    }
                    false
                })

                DFsearch.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        timer?.cancel()
                    }

                    override fun afterTextChanged(s: Editable?) {
                        timer = Timer()
                        timer!!.schedule(object : TimerTask() {
                            override fun run() {
                                if (androidextention.isOnline(mContext)) {
                                    resetPagination()
                                    if (s.toString() == "" && DFsearch.tag as Boolean) {

                                        CategoryId = ""
                                        searchFlag = false
                                        CommonFunctions.hideKeyboard(requireActivity())
                                        seeallproductsRetailerApi(s.toString(), "WHOLE_SALER")
                                    } else if (s.toString() != "") {
                                        CategoryId = ""
                                        searchFlag = true
                                        seeallproductsRetailerApi(s.toString(), "WHOLE_SALER")
                                    }
                                }
                            }
                        }, 600)



                    }
                })
            }
            "CUSTOMER" -> {
                swipe.setOnRefreshListener {

//                    subCategoryArray.clear()
//                    subCategoryRecycler.isVisible = false
                    if (androidextention.isOnline(mContext)) {
                        internet_connection.visibility = View.GONE
//                        CategoryId = ""
                        resetPagination()
                        if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                            seeallproductsRetailerApi("", "RETAILER")

                        } else {
                            seeallproductsCustomerApi("", "RETAILER")
                        }
                    } else {
                        noProductData.visibility = View.GONE
                        ItemCategory.visibility = View.GONE
                        internet_connection.visibility = View.VISIBLE
                        lottie!!.initLoader(true)
                    }
                    swipe.isRefreshing = false

                }

                DFsearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        CommonFunctions.hideKeyboard(requireActivity())
                        return@OnEditorActionListener true
                    }
                    false
                })

                DFsearch.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        timer?.cancel()
                    }

                    override fun afterTextChanged(s: Editable?) {
                        timer = Timer()
                        timer!!.schedule(object : TimerTask() {
                            override fun run() {
                                if (androidextention.isOnline(mContext)) {
                                    resetPagination()
                                    if (s.toString() == "" && DFsearch.getTag() as Boolean) {
                                        CategoryId = ""
                                        searchFlag = false
                                        CommonFunctions.hideKeyboard(requireActivity())
                                        if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                                            seeallproductsRetailerApi("", "RETAILER")

                                        } else {
                                            seeallproductsCustomerApi("", "RETAILER")
                                        }
                                    } else if (s.toString() != "") {
                                        CategoryId = ""
                                        searchFlag = true
                                        if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                                            seeallproductsRetailerApi(s.toString(), "RETAILER")

                                        } else {
                                            seeallproductsCustomerApi(s.toString(), "RETAILER")
                                        }
                                    }
                                }
                            }
                        }, 600)


                    }
                })
            }
            else -> {

            }
        }



        filter.setSafeOnClickListener {
            dataLoadFlag = true
            loaderFlag = true
            searchFlag = false
            page = 1
            openPopUp(flag)
        }

        cart.setSafeOnClickListener {

            if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                openMyCartActivity()
            } else {
                val userType = SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.USER_TYPE)

                if (userType == "RETAILER") {
                    showCustomerBottomSheet("Retailer")
                } else {
                    showCustomerBottomSheet("Customer")
                }
            }
        }

        back.setSafeOnClickListener {
            parentFragmentManager.popBackStack()
        }

        nestedScrollview_seeallproducts.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            val totalHeight = v.getChildAt(0).measuredHeight
            val scrollViewHeight = v.measuredHeight
            val diff = totalHeight - scrollViewHeight

            if (scrollY >= diff && !isLoading) {
                dataLoadFlag = false
                loaderFlag = false
                isLoading = true // Set the flag to indicate that pagination is in progress

                // Check if there are more pages to load
                if (page < pages) {
                    page++
                    pagination_progressbar.visibility = View.VISIBLE
                    if (userType == "RETAILER") {
                        if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                            seeallproductsRetailerApi("", "WHOLE_SALER")
                        }
                    }
                    else if (userType == "CUSTOMER") {
                        if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                            seeallproductsRetailerApi("", "RETAILER")
                        } else {
                            seeallproductsCustomerApi("", "RETAILER")
                        }
                    }
                } else {
                    // All pages have been loaded
                    pagination_progressbar.visibility = View.GONE
                }
            }
        })







        return view
    }

    override fun onResume() {
        super.onResume()
        if (apiCallFlag) {
            when (userType) {
                "RETAILER" -> {
                    if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                        seeallproductsRetailerApi("", "WHOLE_SALER")
                    }
                }

                "CUSTOMER" -> {
                    if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                        resetPagination()
                        seeallproductsRetailerApi("", "RETAILER")
                    } else {
                        resetPagination()
                        seeallproductsCustomerApi("", "RETAILER")
                    }
                }

                else -> {
                }
            }
            apiCallFlag = false
        } else {
            setAdapterSubCategory()
            when (userType) {
                "RETAILER" -> {
                    setDataWithoutApi()
                }

                "CUSTOMER" -> {
                    if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                        setDataWithoutApi()
                    } else {
                        setDataWithoutApi()
                    }
                }

                else -> {
                }
            }
        }
    }

    private fun openMyCartActivity() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.FrameLayout, MyCartFragment(""), "addCart").addToBackStack(null).commit()
    }

    private fun showCustomerBottomSheet(customerType: String) {
        parentFragmentManager.let { CustomerBottomSheet(customerType, mContext, this).show(it, "ModalBottomSheet") }
    }

    fun setDataWithoutApi() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            try {
                if (data.isNotEmpty()) {
                    noProductData.visibility = View.GONE
                    ItemCategory.visibility = View.VISIBLE
                    setAdapater(data)
                } else {
                    noProductData.visibility = View.VISIBLE
                    ItemCategory.visibility = View.GONE
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            noProductData.visibility = View.GONE
            ItemCategory.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }

    }

    @SuppressLint("InflateParams", "SetTextI18n")
    fun openPopUp(flag: String) {

        try {
            val binding = LayoutInflater.from(requireContext()).inflate(R.layout.pop_lists, null)
            dialog = DialogUtils().createDialog(requireContext(), binding.rootView, 0)!!
            recyclerView = binding.findViewById(R.id.popup_recyclerView)
            search_bar_edittext_popuplist = binding.findViewById(R.id.search_bar_edittext_popuplist)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            progressbar_pop = binding.findViewById(R.id.progressbar_pop)
            pop_lottie = binding.findViewById(R.id.pop_lottie)
            pop_internet_connection = binding.findViewById(R.id.pop_internet_connection)

            var dialougTitle = binding.findViewById<TextView>(R.id.popupTitle)
            var dialougbackButton = binding.findViewById<ImageView>(R.id.BackButton)
            dialougbackButton.setSafeOnClickListener { dialog.dismiss() }




            when (flag) {
                "Category" -> {
                    dialougTitle.text = flag
                }
                "SubCategory" -> {
                    dialougTitle.text = flag
                }
                "CategoryList" -> {
                    dialougTitle.text = "Category List"
                    CategotyListApi()
                }
            }

            search_bar_edittext_popuplist.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


                }

                override fun afterTextChanged(s: Editable?) {
                    filterData(s.toString())

                }
            })



            dialog.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun filterData(searchText: String) {
        var filteredList: java.util.ArrayList<CategoryList_docs> = java.util.ArrayList()

        if (flag.equals("CategoryList")) {
            if (data != null) {
                for (item in categoryListData) {
                    try {
                        if (item.categoryName.toLowerCase().contains(searchText.toLowerCase())) {
                            filteredList.add(item)
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }

        }

        try {
            if (flag.equals("CategoryList")) {
                adapterforcategory.filterList(filteredList)
            }

        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
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
        cart.visibility = View.VISIBLE
        filter.visibility = View.VISIBLE
        back.visibility = View.VISIBLE
        MenuClick.visibility = View.GONE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        title.text = "Products"
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    fun setAdapater(data: ArrayList<GuestProductDocs>) {
        ItemCategory.layoutManager = GridLayoutManager(mContext, 2)
        adpter = CategoryItemAdapter(mContext, data, this, this, this)
        ItemCategory.adapter = adpter

    }

    fun setadapter(data: ArrayList<AddDealsCategoryResult>) {
        adapter = this?.let { OpenPopUp(requireContext(), data, flag, this, this) }!!
        recyclerView.adapter = adapter
    }

    fun setadapterforCategory(data1: ArrayList<CategoryList_docs>) {
        adapterforcategory = PopupforCategory(requireContext(), data1, flag, this)
        recyclerView.adapter = adapterforcategory
    }

    fun CategotyListApi() {
        if (androidextention.isOnline(mContext)) {
            pop_internet_connection.visibility = View.GONE
            pop_lottie!!.initLoader(false)
            progressbar_pop.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<CategoryListResponse> =
                ApiCallBack<CategoryListResponse>(object :
                    ApiResponseListener<CategoryListResponse> {
                    override fun onApiSuccess(
                        response: CategoryListResponse,
                        apiName: String?
                    ) {
                        progressbar_pop.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                categoryListData.clear()
                                categoryListData =
                                    response.result?.docs as ArrayList<CategoryList_docs>
                                setadapterforCategory(categoryListData)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_pop.visibility = View.GONE
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)

                        } catch (e: Exception) {

                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar_pop.visibility = View.GONE
                    }

                }, "CategotyListApi", mContext)
            try {
                serviceManager.CategoryList(callBack)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {

            pop_internet_connection.visibility = View.VISIBLE
            pop_lottie!!.initLoader(true)

        }
    }

    fun seeallproductsCustomerApi(searchText: String, userType: String) {
        if (androidextention.isOnline(mContext)) {
            GlobalScope.launch(Dispatchers.Main) {
                noProductData.visibility = View.GONE
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                if (loaderFlag) {
                    progressbar.visibility = View.VISIBLE
                }
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<GuestProductResponse> =
                ApiCallBack<GuestProductResponse>(
                    object : ApiResponseListener<GuestProductResponse> {
                        override fun onApiSuccess(
                            response: GuestProductResponse,
                            apiName: String?
                        ) {
                            progressbar.visibility = View.GONE
                            if (response.responseCode == 200) {

                                try {
                                    isLoading = false
                                    searchFlag = false
                                    page = response.result.page
                                    pages = response.result.totalPages

                                    if (dataLoadFlag) {
                                        data.clear()
                                    }
                                    data.addAll(response.result?.docs as ArrayList<GuestProductDocs>)

                                    if (data.isNotEmpty()) {
                                        noProductData.visibility = View.GONE
                                        ItemCategory.visibility = View.VISIBLE
                                        setAdapater(data)
                                    } else {
                                        noProductData.visibility = View.VISIBLE
                                        ItemCategory.visibility = View.GONE
                                        setAdapater(data)
                                    }

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            } else {
                                noProductData.visibility = View.VISIBLE
                            }
                        }

                        override fun onApiErrorBody(response: String, apiName: String?) {
                            progressbar.visibility = View.GONE
                            val gson = GsonBuilder().create()
                            var pojo = response_modal_class()
                            noProductData.visibility = View.VISIBLE
                            try {
                                data.clear()
                                setAdapater(data)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            try {
                                pojo = gson.fromJson(response, pojo::class.java)

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                        override fun onApiFailure(failureMessage: String?, apiName: String?) {
                            progressbar.visibility = View.GONE
                            noProductData.visibility = View.VISIBLE
                        }
                    },
                    "seeallproductsApi",
                    mContext
                )
            val jsonObject = JsonObject()
            if (searchFlag) {
                jsonObject.addProperty("search", searchText)
            }
            if (!CategoryId.equals("")) {
                jsonObject.addProperty("categoryId", CategoryId)
            }
            jsonObject.addProperty("subCategoryId", idSubCategory)
            jsonObject.addProperty("userType", userType)
            jsonObject.addProperty("page", page.toString())
            jsonObject.addProperty("limit", limit.toString())
            jsonObject.addProperty("lng", SavedPrefManager.getLongitudeLocation().toString())
            jsonObject.addProperty("lat", SavedPrefManager.getLatitudeLocation().toString())
            try {
                serviceManager.productListForCustomer(callBack, jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            noProductData.visibility = View.GONE
            ItemCategory.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }

    }

    fun seeallproductsRetailerApi(searchText: String, userType: String) {
        if (androidextention.isOnline(mContext)) {

           GlobalScope.launch(Dispatchers.Main) {
                noProductData.visibility = View.GONE
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                if (loaderFlag) {
                    progressbar.visibility = View.VISIBLE
                }
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<GuestProductResponse> =
                ApiCallBack<GuestProductResponse>(
                    object : ApiResponseListener<GuestProductResponse> {
                        override fun onApiSuccess(
                            response: GuestProductResponse,
                            apiName: String?
                        ) {
                            progressbar.visibility = View.GONE
                            if (response.responseCode == 200) {

                                try {
                                    isLoading = false
                                    searchFlag = false
                                    page = response.result.page
                                    pages = response.result.totalPages


                                    if (dataLoadFlag) {
                                        data.clear()
                                    }
                                    data.addAll(response.result?.docs as ArrayList<GuestProductDocs>)

                                    if (response.result.docs.isNotEmpty()) {
                                        noProductData.visibility = View.GONE
                                        ItemCategory.visibility = View.VISIBLE
                                        setAdapater(data)
                                    } else {
                                        noProductData.visibility = View.VISIBLE
                                        ItemCategory.visibility = View.GONE
                                    }

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }


                        override fun onApiErrorBody(response: String, apiName: String?) {
                            progressbar.visibility = View.GONE
                            val gson = GsonBuilder().create()
                            var pojo = response_modal_class()
                            noProductData.visibility = View.GONE
                            try {
                                pojo = gson.fromJson(response, pojo::class.java)

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                        override fun onApiFailure(failureMessage: String?, apiName: String?) {
                            progressbar.visibility = View.GONE
                            noProductData.visibility = View.VISIBLE
                        }
                    },
                    "seeallproductsApi",
                    mContext
                )
            val jsonObject = JsonObject()
            if (searchFlag) {
                jsonObject.addProperty("search", searchText)
            }
            if (CategoryId != "") {
                jsonObject.addProperty("categoryId", CategoryId)
            }
            jsonObject.addProperty("subCategoryId", idSubCategory)
            jsonObject.addProperty("userType", userType)
            jsonObject.addProperty("page", page.toString())
            jsonObject.addProperty("limit", limit.toString())
            jsonObject.addProperty("lng", SavedPrefManager.getLongitudeLocation().toString())
            jsonObject.addProperty("lat", SavedPrefManager.getLatitudeLocation().toString())

            try {
                serviceManager.productListFOrretailer(callBack, jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            noProductData.visibility = View.GONE
            ItemCategory.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }

    }


    override fun wishlist(_id: String?) {
        if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
            Addtowishlist(_id!!)
        } else {
            when (userType) {
                "CUSTOMER" -> showCustomerBottomSheet("Customer")
                "RETAILER" -> showCustomerBottomSheet("Retailer")
            }
        }
    }

    fun Addtowishlist(productId: String) {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)

            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<MainAddToWishListResponse> =
                ApiCallBack<MainAddToWishListResponse>(object :
                    ApiResponseListener<MainAddToWishListResponse> {
                    override fun onApiSuccess(
                        response: MainAddToWishListResponse,
                        apiName: String?
                    ) {
                        if (response.responseCode == 200) {
                            if (userType.equals("RETAILER")) {
                                seeallproductsRetailerApi("", "WHOLE_SALER")
                            } else if (userType.equals("CUSTOMER")) {
                                seeallproductsRetailerApi("", "RETAILER")
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)


                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                    }

                }, "Addtowishlist", mContext)


            try {
                serviceManager.addTowishlistApi(callBack, productId)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            noProductData.visibility = View.GONE
            ItemCategory.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }
    }

    override fun wishlist2() {
    }



    override fun getDatas(data: String, flag: String, id: String) {
        CategoryId = data
        dialog.dismiss()
        getAllSubCategory(CategoryId)
        if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.USER_TYPE).equals("CUSTOMER")) {
            if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                seeallproductsCustomerApi("", "RETAILER")
            }
            seeallproductsRetailerApi("", "RETAILER")
        } else {
            seeallproductsRetailerApi("", "WHOLE_SALER")

        }
    }

    private fun locationpermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQ_CODE
            )
        } else {
            LocationClass.getCurrentLocation(requireContext())
            LocationClass.displayLocationSettingsRequest(requireContext())
        }

    }

    private fun resetPagination() {
        page = 1
        dataLoadFlag = true
        loaderFlag = true
    }

    override fun isLoginListener() {
        var name = activity?.findViewById<TextView>(R.id.name)
        var userProfile = activity?.findViewById<ImageView>(R.id.Profile_picture)
        CommonFunctions.getProfileApiApi(mContext, name, userProfile)
        if (userType == "RETAILER") {
            seeallproductsRetailerApi("", "WHOLE_SALER")
        } else if (userType == "CUSTOMER") {
            seeallproductsRetailerApi("", "RETAILER")
        }
    }

    override fun onPause() {
        super.onPause()
        CommonFunctions.hideKeyboard(requireActivity())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun viewProduct(productId: String, dealId: String) {
//        if(dealId == "") {
            loaderFlag = true
            val bundle = Bundle()
            bundle.putString("productId2", productId)
            val fragObj = ProductViewFragment()
            fragObj.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.FrameLayout, fragObj, "productView")?.addToBackStack(null)
                ?.commit()
//        } else {
//            val bundle = Bundle()
//            bundle.putString("flag", "")
//            bundle.putBoolean("flag1", false)
//            bundle.putString("deal_id", dealId)
//            bundle.putString("productid", productId)
//
//            val fragobj = ViewAddDeals()
//            fragobj.setArguments(bundle)
//            parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragobj, "viewAddDeals").addToBackStack(null).commit()
//
//        }
    }

    override fun serviceNameWithPrice(data: String, flag: String, id: String, price: String) {
        TODO("Not yet implemented")
    }

    private fun getAllSubCategory(categoryId: String) {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<ProductSubCategoryResponse> =
                ApiCallBack(object :
                    ApiResponseListener<ProductSubCategoryResponse> {
                    override fun onApiSuccess(
                        response: ProductSubCategoryResponse,
                        apiName: String?
                    ) {
                        progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            subCategoryArray.clear()
                            subCategoryRecycler.isVisible = true
                            subCategoryArray.add(SubCategoryModalClass("All","",true))
                            val subCategoryData = response.result.subCategory
                            if (subCategoryData!!.isNotEmpty()){
                                for (item in subCategoryData.indices){
                                    subCategoryArray.add(SubCategoryModalClass(subCategoryData[item].subCategoryName,subCategoryData[item]._id))
                                }
                            }
                            setAdapterSubCategory()
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar.visibility = View.GONE

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                    }

                }, "subcategoryListWithCategory", mContext)


            try {
                serviceManager.subcategoryListWithCategory(callBack,categoryId)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }



    fun setAdapterSubCategory() {
        subCategoryRecycler.layoutManager = LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false)
        adapterSubCategory = ProductListSubCategory(requireContext(), subCategoryArray,this)
        subCategoryRecycler.adapter = adapterSubCategory
    }

    override fun subCategoryClick(name: String, subCategoryId: String) {
        idSubCategory = subCategoryId
        page = 1
        page = 1
        dataLoadFlag = true
        loaderFlag = true
        searchFlag = false
        if (userType.uppercase() == "CUSTOMER") {
            seeallproductsCustomerApi("", "RETAILER")

        } else {
            seeallproductsRetailerApi("", "WHOLE_SALER")

        }
    }


}