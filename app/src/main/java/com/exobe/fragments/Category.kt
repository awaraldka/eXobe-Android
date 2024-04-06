package com.exobe.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.exobe.activities.CategoryItem
import com.exobe.adaptor.CategoryAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.CommonFunctions.hideKeyboard
import com.exobe.utils.NotificationApi
import com.exobe.utils.SavedPrefManager
import com.exobe.utils.TabHandler
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.SetTitles
import com.exobe.customClicks.categorynameclick
import com.exobe.databinding.FragmentCategoryBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.CategoryList_response
import com.exobe.entity.response.Docs
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class Category(var setTitles: SetTitles) : Fragment(), categorynameclick, ApiResponseListener<CategoryList_response> {
    private lateinit var binding: FragmentCategoryBinding
    private var apiCallFlag = true
    lateinit var ProductLatestAdapter: CategoryAdapter
    lateinit var mContext: Context
    var latestProductList: ArrayList<Docs> = ArrayList()


    lateinit var mainHeader: RelativeLayout
    var searchFlag = false
    lateinit var logoutButton: ImageView
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var MenuClick: LinearLayout

    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    var seeall = ""

    var pages = 0
    var page = 1
    var limit = 10
    var dataLoadFlag = true
    var loaderFlag = true

    private var timer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!

        arguments?.getString("seeall").let {
            seeall = it.toString()
        }

        setToolbar()
        TabHandler.HandleTab(R.id.ll_category_tab, requireActivity())



        setTitles.title("Categories")

        mainHeader.visibility = View.VISIBLE

        binding.pulltorefresh.setOnRefreshListener {
            resetPagination()
            if (androidextention.isOnline(mContext)) {

                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                searchFlag = false
                CategoryListApi("")

            } else {
                binding.NoReview.visibility = View.GONE
                binding.VisibleItem.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
            }

            binding.pulltorefresh.isRefreshing = false

        }
        binding.DFsearch.tag = false
        binding.DFsearch.onFocusChangeListener = OnFocusChangeListener { _, _ -> binding.DFsearch.tag = true }

        binding.DFsearch.setOnEditorActionListener(OnEditorActionListener
        { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard(requireActivity())
                    return@OnEditorActionListener true
            }

            false
        })

        binding.DFsearch.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    timer?.cancel()
                }

                override fun afterTextChanged(s: Editable?) {
                    timer = Timer()
                    timer!!.schedule(object : TimerTask() {
                        override fun run() {
                            if (androidextention.isOnline(mContext)) {
                                resetPagination()
                                if (s.toString() == "" && binding.DFsearch.tag as Boolean) {
                                    hideKeyboard(requireActivity())
                                    searchFlag = false
                                    CategoryListApi("")
                                } else if (s.toString() != "") {
                                    searchFlag = true
                                    CategoryListApi(s.toString())
                                }
                            }
                        }
                    }, 600)

                }

            })


        binding.categoryNestedscrollview.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                dataLoadFlag = false
                loaderFlag = false
                page++

                binding.paginationProgressbar.visibility = View.VISIBLE
                if (page > pages) {
                    binding.paginationProgressbar.visibility = View.GONE
                } else {
                    CategoryListApi("")
                }
            }
        })

        back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        if(apiCallFlag) {
            resetPagination()
            CategoryListApi("")
            apiCallFlag = false
        } else {
            if (androidextention.isOnline(mContext)) {
                handleOnlineState()
            } else {
                handleOfflineState()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    val fm: FragmentManager = requireActivity().supportFragmentManager

                    for (i in 0 until fm.backStackEntryCount) {
                        fm.popBackStack()
                    }


                }
            })

    }

    private fun handleOnlineState() {
        internet_connection.visibility = View.GONE
        lottie!!.initLoader(false)
        try {
            if (latestProductList.size > 0) {
                binding.catSearch.visibility = View.VISIBLE
                binding.NoReview.visibility = View.GONE
                binding.VisibleItem.visibility = View.VISIBLE
                setAdaptor(latestProductList)
            } else {
                binding.catSearch.visibility = View.GONE
                binding.VisibleItem.visibility = View.GONE
                binding.NoReview.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun handleOfflineState() {
        binding.NoReview.visibility = View.GONE
        binding.VisibleItem.visibility = View.GONE
        internet_connection.visibility = View.VISIBLE
        lottie!!.initLoader(true)
    }


    override fun categoryname(_id: String?, categoryname: String) {

        val bundle = Bundle()
        bundle.putString("id", _id)
        bundle.putString("name", categoryname)
        val fragObj = CategoryItem()
        fragObj.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.FrameLayout, fragObj, "categoryItem").addToBackStack(null)
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

        back.isVisible = seeall == "seeallcategory"
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
        cartCount.visibility = View.GONE
        cart.visibility = View.VISIBLE
        filter.visibility = View.GONE
        MenuClick.visibility = View.GONE
        DealsImageView.visibility = View.VISIBLE
        greyBellImageView.visibility = View.VISIBLE
        title.text = "Categories"
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
        if (SavedPrefManager.getStringPreferences(
                activity,
                SavedPrefManager.isLogin
            ) == "true"
        ) {
            NotificationApi.notifiactionCountApi(requireContext(), greyBellImageView, logoutButton)
            SavedPrefManager.savePreferenceBoolean(context, SavedPrefManager.NOTIFICATION_VISIBLE, true)
        }
    }



    fun setAdaptor(latestProductList: ArrayList<Docs>) {
        binding.recyclerViewProduct.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewProduct.layoutManager = GridLayoutManager(context, 2)
        ProductLatestAdapter = CategoryAdapter(activity as Context, latestProductList, this)
        binding.recyclerViewProduct.adapter = ProductLatestAdapter

    }

    fun CategoryListApi(searchText: String) {
        if (androidextention.isOnline(mContext)) {
            GlobalScope.launch(Dispatchers.Main) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                if (loaderFlag) {
                    binding.progressbar.visibility = View.VISIBLE
                }
            }

            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<CategoryList_response> =
                ApiCallBack(this, "CategoryListApi", mContext)

            val jsonObject = JsonObject()
            if (searchFlag) {
                jsonObject.addProperty("search", searchText)

            }
            jsonObject.addProperty("page", page.toString())
            jsonObject.addProperty("limit", limit.toString())

            try {
                serviceManager.listCategorySearchApi(callBack, jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }

    }

    override fun onApiSuccess(response: CategoryList_response, apiName: String?) {
        binding.progressbar.visibility = View.GONE
        if (response.responseCode == 200) {
            try {
                searchFlag = false

                if (dataLoadFlag) {
                    latestProductList.clear()
                }


                if(response.result!!.docs!!.isNotEmpty()){
                    latestProductList.addAll(response.result.docs!!)
                    page = response.result.page
                    pages = response.result.pages

                    binding.catSearch.visibility = View.VISIBLE
                    binding.NoReview.visibility = View.GONE
                    binding.VisibleItem.visibility = View.VISIBLE
                    setAdaptor(latestProductList)

                }else{
                    binding.catSearch.visibility = View.GONE
                    binding.VisibleItem.visibility = View.GONE
                    binding.NoReview.visibility = View.VISIBLE
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            binding.VisibleItem.visibility = View.GONE
            binding.NoReview.visibility = View.VISIBLE
        }
    }

    override fun onApiErrorBody(response: String, apiName: String?) {
        try {
            binding.progressbar.visibility = View.GONE
            binding.VisibleItem.visibility = View.GONE
            binding.NoReview.visibility = View.VISIBLE
            latestProductList.clear()
            setAdaptor(latestProductList)
            binding.NoReview.visibility = View.VISIBLE
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val gson = GsonBuilder().create()
        var pojo = response_modal_class()
        try {
            pojo = gson.fromJson(response, pojo::class.java)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        binding.progressbar.visibility = View.GONE
        binding.VisibleItem.visibility = View.GONE
        binding.NoReview.visibility = View.VISIBLE

    }

    private fun resetPagination() {
        page = 1
        dataLoadFlag = true
        loaderFlag = true
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard(requireActivity())
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDestroy() {
        super.onDestroy()
        DealsImageView.visibility = View.VISIBLE
        greyBellImageView.visibility = View.VISIBLE
    }
}
