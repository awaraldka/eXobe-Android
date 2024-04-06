package com.exobe.activities

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.view.View.OnFocusChangeListener
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
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.exobe.Adapter.CategoryProductItemAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.bottomSheet.CustomerBottomSheet
import com.exobe.customClicks.wishlistcustomclick
import com.exobe.customClicks.wishlistcustomclick2
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.*
import com.exobe.entity.response.product.GuestProductDocs
import com.exobe.entity.response.product.GuestProductResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import android.widget.TextView.OnEditorActionListener
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.PopupforCategory
import com.exobe.adaptor.ProductListSubCategory
import com.exobe.modelClass.SubCategoryModalClass
import com.exobe.utils.CommonFunctions
import com.exobe.utils.LocationClass
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.ProductViewListener
import com.exobe.customClicks.SubCategoryClick
import com.exobe.customClicks.UpdateIsLoginListener
import com.exobe.customClicks.popupItemClickListnerDeals
import com.exobe.fragments.products.ProductViewFragment
import com.exobe.validations.DialogUtils
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class CategoryItem : Fragment(), ProductViewListener, wishlistcustomclick, wishlistcustomclick2,
    UpdateIsLoginListener, SubCategoryClick, popupItemClickListnerDeals {
    lateinit var ItemCategory: RecyclerView
    lateinit var adpter: CategoryProductItemAdapter
    lateinit var imageView2: ImageView
    var data: List<GuestProductDocs> = ArrayList()
    lateinit var mainHeader: RelativeLayout
    lateinit var mContext: Context
    lateinit var progressbar: ProgressBar
    lateinit var pagination_progressbar: ProgressBar
    lateinit var pulltorefresh: SwipeRefreshLayout
    lateinit var categoryitem_nestedscrollview: NestedScrollView
    var id = ""
    var name = ""
    lateinit var category_products: LinearLayout
    lateinit var VisibleItem: LinearLayout
    lateinit var DFsearch: EditText
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    lateinit var logoutButton: ImageView
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var MenuClick: LinearLayout
    var searchFlag = false
    var totalCount = 0
    var pages = 0
    var page = 1
    var limit = 10
    var dataLoadFlag = true
    var loaderFlag = true
    var userType = ""
    private var timer: Timer? = null
    private val LOCATION_PERMISSION_REQ_CODE = 1000;
    private var apiCallFlag = true

    lateinit var subCategoryRecyclerL:RecyclerView
    var idSubCategory = ""
    var subCategoryArray: MutableList<SubCategoryModalClass> = mutableListOf()
    lateinit var adapterSubCategory : ProductListSubCategory
    var isLoading = false // Add this boolean flag to control pagination



    private lateinit var dialog: Dialog
    private lateinit var recyclerView: RecyclerView
    private lateinit var search_bar_edittext_popuplist: EditText
    lateinit var progressbar_pop: ProgressBar
    var pop_lottie: LottieAnimationView? = null
    lateinit var pop_internet_connection: RelativeLayout
    var categoryListData = ArrayList<CategoryList_docs>()
    var flag = ""
    lateinit var adapterforcategory: PopupforCategory
    var categoryId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_category_item, container, false)

        pulltorefresh = view.findViewById(R.id.pulltorefresh)
        ItemCategory = view.findViewById(R.id.ItemCategory)
        progressbar = view.findViewById(R.id.progressbar)
        subCategoryRecyclerL = view.findViewById(R.id.subCategoryRecycler)
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        mContext = activity?.applicationContext!!
        DFsearch = view.findViewById(R.id.DFsearch)
        category_products = view.findViewById(R.id.category_products)
        VisibleItem = view.findViewById(R.id.VisibleItem)
        pagination_progressbar = view.findViewById(R.id.pagination_progressbar)
        categoryitem_nestedscrollview = view.findViewById(R.id.categoryitem_nestedscrollview)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        mainHeader.visibility = View.VISIBLE
        userType = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.USER_TYPE).toString()

        if (requireArguments().getString("id") != null) {
            id = requireArguments().getString("id").toString()
        }


        if (requireArguments().getString("name") != null) {
            name = requireArguments().getString("name").toString()
        }
        locationpermission()
        setToolbar()

        filter.setSafeOnClickListener {
            dataLoadFlag = true
            loaderFlag = true
            searchFlag = false
            page = 1
            flag = "CategoryList"
            openPopUp(flag)
        }

        back.setSafeOnClickListener {
            fragmentManager?.popBackStack()
        }

        DFsearch.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                CommonFunctions.hideKeyboard(requireActivity())
                return@OnEditorActionListener true
            }
            false
        })

        DFsearch.tag = false
        DFsearch.onFocusChangeListener = OnFocusChangeListener { _, _ -> DFsearch.tag = true }

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
                        if (s.toString() == "" && DFsearch.tag as Boolean) {
                            searchFlag = true
                            dataLoadFlag = true
                            loaderFlag = true
                            page = 1
                            CommonFunctions.hideKeyboard(requireActivity())
                            if (userType == "CUSTOMER") {
                                if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                                    productListByCategoryRetailerApi(id, "", "RETAILER",idSubCategory)
                                } else {
                                    productListByCategoryCustomerApi(id, "", "RETAILER",idSubCategory)
                                }
                            } else {
                                productListByCategoryRetailerApi(id, "", "WHOLE_SALER",idSubCategory)
                            }

                        } else if (s.toString() != "") {
                            searchFlag = true
                            dataLoadFlag = true
                            loaderFlag = true
                            page = 1

                            if (userType == "CUSTOMER") {
                                if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                                    productListByCategoryRetailerApi(id, s.toString(), "RETAILER",idSubCategory)
                                } else {
                                    productListByCategoryCustomerApi(id, s.toString(), "RETAILER",idSubCategory)
                                }
                            } else {
                                productListByCategoryRetailerApi(id, s.toString(), "WHOLE_SALER",idSubCategory)
                            }
                        }

                    }
                    }
                }, 600)

            }
        })

        pulltorefresh.setOnRefreshListener {
//            idSubCategory = ""
//            getAllSubCategory(id)

            if (androidextention.isOnline(mContext)) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                dataLoadFlag = true
                loaderFlag = true
                searchFlag = false
                DFsearch.setText("")
                page = 1
                if (userType == "CUSTOMER") {
                    if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                        productListByCategoryRetailerApi(id, "", "RETAILER",idSubCategory)
                    } else {
                        productListByCategoryCustomerApi(id, "", "RETAILER",idSubCategory)
                    }
                } else {
                    productListByCategoryRetailerApi(id, "", "WHOLE_SALER",idSubCategory)
                }
            } else {
                category_products.visibility = View.GONE
                VisibleItem.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
            }
            pulltorefresh.isRefreshing = false

        }



        categoryitem_nestedscrollview.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
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
                    if (userType == "CUSTOMER") {
                        if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                            productListByCategoryRetailerApi(id, "", "RETAILER", idSubCategory)
                        } else {
                            productListByCategoryCustomerApi(id, "", "RETAILER", idSubCategory)
                        }
                    } else {
                        productListByCategoryRetailerApi(id, "", "WHOLE_SALER", idSubCategory)
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
        if(apiCallFlag) {
            handleCallbackFalse()
            apiCallFlag = false
            if(id != "") {
                getAllSubCategory(id)
            } else {
                subCategoryRecyclerL.isVisible = false
            }
        } else {
            handleCallbackTrue()
            if(subCategoryArray.isNotEmpty()) {
                setAdapterSubCategory()
            } else {
                subCategoryRecyclerL.isVisible = false
            }
        }

    }




    private fun handleCallbackFalse() {

        if (userType == "CUSTOMER") {
            if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                productListByCategoryRetailerApi(id, "", "RETAILER",idSubCategory)
            } else {
                productListByCategoryCustomerApi(id, "", "RETAILER",idSubCategory)
            }
        } else {
            productListByCategoryRetailerApi(id, "", "WHOLE_SALER",idSubCategory)
        }
    }

    private fun handleCallbackTrue() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)

            if (data.isNotEmpty()) {
                category_products.visibility = View.GONE
                VisibleItem.visibility = View.VISIBLE
                categoryitem(data)
            } else {
                category_products.visibility = View.VISIBLE
                VisibleItem.visibility = View.GONE
            }
        } else {
            category_products.visibility = View.GONE
            VisibleItem.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
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

        back.visibility = View.VISIBLE
        MenuClick.visibility = View.GONE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        title.text = name
        if(id != "") {
            filter.visibility = View.GONE
        } else{
            filter.visibility = View.VISIBLE

        }

        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }


    fun categoryitem(data: List<GuestProductDocs>) {
        ItemCategory.layoutManager = GridLayoutManager(context, 2)
        adpter = CategoryProductItemAdapter(mContext, data, this, this, this)
        ItemCategory.adapter = adpter
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun productListByCategoryRetailerApi(id: String, searchText: String, usertype: String, subCategoryId:String) {
        if (androidextention.isOnline(mContext)) {
            GlobalScope.launch(Dispatchers.Main){
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
                                    page = response.result.page
                                    pages = response.result.totalPages

                                    if (dataLoadFlag) {
                                        data = emptyList()
                                    }

                                    data =  data + response.result.docs

                                    if (data.isNotEmpty()) {
                                        category_products.visibility = View.GONE
                                        VisibleItem.visibility = View.VISIBLE
                                        categoryitem(data)
                                    } else {
                                        data = emptyList()
                                        categoryitem(data)
                                        VisibleItem.visibility = View.GONE
                                        category_products.visibility = View.VISIBLE
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }

                        override fun onApiErrorBody(response: String, apiName: String?) {
                            category_products.visibility = View.VISIBLE
                            VisibleItem.visibility = View.GONE
                            progressbar.visibility = View.GONE
                            try {
                                data= emptyList()
                                categoryitem(data)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            val gson = GsonBuilder().create()
                            var pojo = response_modal_class()


                            try {
                                pojo = gson.fromJson(response, pojo::class.java)
//                                androidextention.alertBox(pojo.responseMessage, requireContext())
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                        override fun onApiFailure(
                            failureMessage: String?,
                            apiName: String?
                        ) {
                            progressbar.visibility = View.GONE
                        }

                    },
                    "seeallproductsApi",
                    mContext
                )
            val jsonObject = JsonObject()
            if (searchFlag) {
                jsonObject.addProperty("search", searchText)
            }
            if (id != "") {
                jsonObject.addProperty("categoryId", id)
            }
            jsonObject.addProperty("subCategoryId", subCategoryId)
            jsonObject.addProperty("userType", usertype)
            jsonObject.addProperty("page", page.toString())
            jsonObject.addProperty("limit", limit.toString())
            jsonObject.addProperty(
                "lng",
                SavedPrefManager.getLongitudeLocation().toString()
            )
            jsonObject.addProperty("lat", SavedPrefManager.getLatitudeLocation().toString())
            try {
                serviceManager.productListFOrretailer(callBack, jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            category_products.visibility = View.GONE
            VisibleItem.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }

    }

    fun productListByCategoryCustomerApi(id: String, searchText: String, usertype: String,subCategoryId: String) {
        if (androidextention.isOnline(mContext)) {
            GlobalScope.launch(Dispatchers.Main) {
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
                                    pages = response.result.totalPages
                                    page = response.result.page
                                    if (dataLoadFlag) {
                                        data = emptyList()
                                    }


                                    data = data + response.result.docs

                                    if (data.isNotEmpty()) {
                                        category_products.visibility = View.GONE
                                        VisibleItem.visibility = View.VISIBLE
                                        categoryitem(data)
                                    } else {
                                        category_products.visibility = View.VISIBLE
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }

                        override fun onApiErrorBody(response: String, apiName: String?) {
                            category_products.visibility = View.VISIBLE
                            VisibleItem.visibility = View.GONE
                            progressbar.visibility = View.GONE
                            val gson = GsonBuilder().create()
                            var pojo = response_modal_class()
                            try {
                                data = emptyList()
                                categoryitem(data)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            try {
                                pojo = gson.fromJson(response, pojo::class.java)
//                                androidextention.alertBox(pojo.responseMessage, requireContext())
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                        override fun onApiFailure(
                            failureMessage: String?,
                            apiName: String?
                        ) {
                            progressbar.visibility = View.GONE
                        }

                    },
                    "seeallproductsApi",
                    mContext
                )
            val jsonObject = JsonObject()
            if (searchFlag) {
                jsonObject.addProperty("search", searchText)
            }
            if (id != "") {
                jsonObject.addProperty("categoryId", id)
            }
            jsonObject.addProperty("subCategoryId", subCategoryId)
            jsonObject.addProperty("userType", usertype)
            jsonObject.addProperty("page", page.toString())
            jsonObject.addProperty("limit", limit.toString())
            jsonObject.addProperty(
                "lng",
                SavedPrefManager.getLongitudeLocation().toString()
            )
            jsonObject.addProperty("lat", SavedPrefManager.getLatitudeLocation().toString())
            try {
                serviceManager.productListForCustomer(callBack, jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            category_products.visibility = View.GONE
            VisibleItem.visibility = View.GONE
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
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<MainAddToWishListResponse> =
                ApiCallBack<MainAddToWishListResponse>(object :
                    ApiResponseListener<MainAddToWishListResponse> {
                    override fun onApiSuccess(
                        response: MainAddToWishListResponse,
                        apiName: String?
                    ) {
                        progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            if (userType == "CUSTOMER") {
                                productListByCategoryRetailerApi(id, DFsearch.text.toString(), "RETAILER",idSubCategory)
                            } else {
                                productListByCategoryRetailerApi(id, DFsearch.text.toString(), "WHOLE_SALER",idSubCategory)
                            }
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

                }, "Addtowishlist", mContext)


            try {
                serviceManager.addTowishlistApi(callBack, productId)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            category_products.visibility = View.GONE
            VisibleItem.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    override fun wishlist2() {
    }

    private fun showCustomerBottomSheet(type: String) {
        parentFragmentManager.let { it1 -> CustomerBottomSheet(type, requireContext(), this).show(it1, "ModalBottomSheet") }
    }

    override fun isLoginListener() {
        val name = activity?.findViewById<TextView>(R.id.name)
        val userProfile = activity?.findViewById<ImageView>(R.id.Profile_picture)
        CommonFunctions.getProfileApiApi(mContext, name, userProfile)

        if (userType == "CUSTOMER") {
            productListByCategoryRetailerApi(id, DFsearch.text.toString(), "RETAILER",idSubCategory)
        } else {
            productListByCategoryRetailerApi(id, DFsearch.text.toString(), "WHOLE_SALER",idSubCategory)
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

    override fun onPause() {
        super.onPause()
        CommonFunctions.hideKeyboard(requireActivity())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Store the context in a member variable
        mContext = context
    }

    override fun viewProduct(productId: String, dealId: String) {
//        if(dealId == "") {
            loaderFlag = false
            val bundle = Bundle()
            bundle.putString("productId2", productId)
            val fragobj = ProductViewFragment()
            fragobj.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.FrameLayout, fragobj, "productView")?.addToBackStack(null)
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

                            val subCategoryData = response.result.subCategory
                            subCategoryArray.add(SubCategoryModalClass("All","",true))
                            if (subCategoryData!!.isNotEmpty()){
                                subCategoryRecyclerL.isVisible = true
                                for (item in subCategoryData.indices){
                                    subCategoryArray.add(SubCategoryModalClass(subCategoryData[item].subCategoryName,subCategoryData[item]._id))
                                }
                            } else {
                                subCategoryRecyclerL.isVisible = false
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
            category_products.visibility = View.GONE
            VisibleItem.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }



    fun setAdapterSubCategory() {
        subCategoryRecyclerL.layoutManager = LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false)
        adapterSubCategory = ProductListSubCategory(requireContext(), subCategoryArray,this)
        subCategoryRecyclerL.adapter = adapterSubCategory
    }

    override fun subCategoryClick(name: String, subCategoryId: String) {
        idSubCategory = subCategoryId
        page = 1
        dataLoadFlag = true
        loaderFlag = true
        searchFlag = false

        if (userType == "CUSTOMER") {
            if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                productListByCategoryRetailerApi(id, "", "RETAILER",idSubCategory)
            }else {
                productListByCategoryCustomerApi(id, "", "RETAILER",idSubCategory)
            }
        } else {
            productListByCategoryRetailerApi(id, "", "WHOLE_SALER",idSubCategory)




        }
    }


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


    private fun filterData(searchText: String) {
        var filteredList: java.util.ArrayList<CategoryList_docs> = java.util.ArrayList()

        if (flag.equals("CategoryList")) {
            if (data != null) {
                for (item in categoryListData) {
                    try {
                        if (item.categoryName.lowercase().contains(searchText.lowercase())) {
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
    fun setadapterforCategory(data1: ArrayList<CategoryList_docs>) {
        adapterforcategory = PopupforCategory(requireContext(), data1, flag, this)
        recyclerView.adapter = adapterforcategory
    }

    override fun getDatas(data: String, flag: String, id: String) {
        categoryId = data
        dialog.dismiss()
        getAllSubCategory(categoryId)

        if (userType == "CUSTOMER") {
            if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                productListByCategoryRetailerApi(id, "", "RETAILER",idSubCategory)
            }else {
                productListByCategoryCustomerApi(id, "", "RETAILER",idSubCategory)
            }
        } else {
            productListByCategoryRetailerApi(id, "", "WHOLE_SALER",idSubCategory)

        }
    }

}