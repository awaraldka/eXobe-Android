package com.exobe.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.DealsManagement_Adapter
import com.exobe.fragments.retailr.ProductDetails
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.CustomeClick2
import com.exobe.customClicks.editDeal
import com.exobe.customClicks.viewProductClick
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.*
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class DealsManagement(var flagSide: String) : Fragment(), editDeal, CustomeClick2,
    ApiResponseListener<DealListResponse>, viewProductClick {
    lateinit var mContext: Context
    lateinit var dealManagementLL: LinearLayout
    lateinit var recyclerView: RecyclerView
    lateinit var addProduct: LinearLayout
    lateinit var adapter: DealsManagement_Adapter
    lateinit var title: TextView
    lateinit var searchDeals: EditText
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var deals: TextView
    lateinit var nodata_tv: TextView
    lateinit var progressbar: ProgressBar
    lateinit var pulltorefresh_dm: SwipeRefreshLayout
    lateinit var internet_connection: RelativeLayout
    lateinit var nsDealManagement: NestedScrollView
    lateinit var pbDealManagementPagination: ProgressBar
    var lottie: LottieAnimationView? = null
    var dealsData: ArrayList<DealListDoc> = ArrayList()

    var pages = 0
    var page = 1
    var limit = 20
    var dataLoadFlag = true
    var loaderFlag = true
    private var timer: Timer? = null

    companion object {
        var apiDealManagementCallFlag = true
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_deals_management, container, false)
        setToolbar()
        pbDealManagementPagination = view.findViewById(R.id.pbDealManagementPagination)
        nsDealManagement = view.findViewById(R.id.nsDealManagement)
        recyclerView = view.findViewById(R.id.ProductManagement_recycler)
        searchDeals = view.findViewById(R.id.searchDeals)
        addProduct = view.findViewById(R.id.add_product)
        deals = view.findViewById(R.id.deals)
        nodata_tv = view.findViewById(R.id.nodata_tv)
        progressbar = view.findViewById(R.id.progressbar_dealsmanagement)
        pulltorefresh_dm = view.findViewById(R.id.pulltorefresh_dm)
        dealManagementLL = view.findViewById(R.id.dealManagementLL)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!


        if (flagSide.equals("SideMenu")) {
            back.visibility = View.GONE
            MenuClick.visibility = View.VISIBLE
        } else {
            MenuClick.visibility = View.GONE
            back.visibility = View.VISIBLE
        }

        back.setSafeOnClickListener {
            parentFragmentManager.popBackStack()
        }

        deals.setSafeOnClickListener {
            addProduct.visibility = View.GONE
        }

        //    Textwatcher poducts

        searchDeals.addTextChangedListener(object : TextWatcher {
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
                            if (s.toString() == "") {
                                CommonFunctions.hideKeyboard(requireActivity())
                                dealListApi()
                            } else {
                                dealListApi()
                            }
                        }
                    }
                }, 600)


            }
        })

        nsDealManagement.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                dataLoadFlag = false
                loaderFlag = false

                pbDealManagementPagination.visibility = View.VISIBLE
                if (page == pages) {
                    pbDealManagementPagination.visibility = View.GONE
                } else {
                    page++
                    dealListApi()
                }
            }
        })


        addProduct.setSafeOnClickListener {
            val bundle = Bundle()
            bundle.putString("flag", "Add Deal")
            val fragobj = AddDealsFragments()
            fragobj.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragobj, "addDeals")
                .addToBackStack(null).commit()
        }

        pulltorefresh_dm.setOnRefreshListener {
            if (androidextention.isOnline(mContext)) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                resetPagination()
                dealListApi()
            } else {
                nodata_tv.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
            }
            pulltorefresh_dm.isRefreshing = false

        }
        return view
    }

    override fun onResume() {
        super.onResume()
        if (apiDealManagementCallFlag) {
            resetPagination()
            dealListApi()
            apiDealManagementCallFlag = false
        } else {
            if (androidextention.isOnline(mContext)) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                if (dealsData.size > 0) {
                    dealManagementLL.visibility = View.VISIBLE
                    nodata_tv.visibility = View.GONE
                    setAdapter(dealsData)
                } else {
                    dealManagementLL.visibility = View.GONE
                    nodata_tv.visibility = View.VISIBLE
                }
            } else {
                nodata_tv.visibility = View.GONE
                dealManagementLL.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Store the context in a member variable
        mContext = context
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

    fun setAdapter(dealsData: ArrayList<DealListDoc>) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = DealsManagement_Adapter(activity as Context, this, this, dealsData, "deals")
        recyclerView.adapter = adapter
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
        cart.visibility = View.GONE
        filter.visibility = View.GONE
        back.visibility = View.VISIBLE

        MenuClick.visibility = View.GONE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        title.setText("Deals to customers")

        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }


    override fun click2(_id: String?, servicename: String?) {
        val bundle = Bundle()
        bundle.putString("flag", "Deal Details")
        val fragobj = ProductDetails()
        fragobj.arguments = bundle

        parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragobj, "dealView")
            .addToBackStack(null).commit()
    }

    override fun viewProductClick(itemId: String) {
        val bundle = Bundle()
        bundle.putString("productId", itemId)
        bundle.putString("dealsmanagement", "dealsmanagement")
        val fragobj = ProductDetails()
        fragobj.arguments = bundle

        parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragobj, "dealsView")
            .addToBackStack(null).commit()

    }

    fun dealListApi() {
        if (androidextention.isOnline(mContext)) {
            GlobalScope.launch(Dispatchers.Main) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                if (loaderFlag) {
                    progressbar.visibility = View.VISIBLE
                }
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<DealListResponse> =
                ApiCallBack<DealListResponse>(this, "dealListApi", mContext)

            try {


                val jsonObject = JsonObject()
                if (searchDeals.text.toString().isNotEmpty()) {
                    jsonObject.addProperty("search", searchDeals.text.toString())
                }
                jsonObject.addProperty("page", page)
                jsonObject.addProperty("limit", limit)
                serviceManager.dealListApi(callBack, jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    override fun onApiSuccess(response: DealListResponse, apiName: String?) {
        progressbar.visibility = View.GONE

        if (response.responseCode == 200) {
            try {
                if (dataLoadFlag) {
                    dealsData.clear()
                }
                page = response.result?.page!!
                pages = response.result!!.pages

                response.result!!.docs?.let { dealsData.addAll(it) }
                if (dealsData.size > 0) {
                    dealManagementLL.visibility = View.VISIBLE
                    nodata_tv.visibility = View.GONE
                    setAdapter(dealsData)
                } else {
                    nodata_tv.visibility = View.VISIBLE
                    dealManagementLL.visibility = View.GONE
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("LongLogTag")
    override fun onApiErrorBody(response: String, apiName: String?) {
        try {
            nodata_tv.visibility = View.VISIBLE
            progressbar.visibility = View.GONE
            dealsData.clear()
            setAdapter(dealsData)
            val gson = GsonBuilder().create()
            var pojo = response_modal_class()

            pojo = gson.fromJson(response, pojo::class.java)
            Log.d("retailer deal management error", pojo.responseMessage)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        progressbar.visibility = View.GONE
    }

    override fun click(deal_id: String?, product_id: String?) {
        val bundle = Bundle()
        bundle.putString("flag", "Edit Deal")
        bundle.putString("productid", product_id)
        bundle.putString("dealid", deal_id)
        val fragobj = AddDealsFragments()
        fragobj.setArguments(bundle)

        parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragobj, "addDeals")
            .addToBackStack(null).commit()
    }

    private fun resetPagination() {
        page = 1
        dataLoadFlag = true
        loaderFlag = true
    }

    override fun onPause() {
        super.onPause()
        CommonFunctions.hideKeyboard(requireActivity())
    }
}