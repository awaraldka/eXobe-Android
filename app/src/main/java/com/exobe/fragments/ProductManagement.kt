package com.exobe.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.ProductManagementAdapter
import com.exobe.fragments.retailr.ProductDetails
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.TabHandler
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.*
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


class ProductManagement : Fragment(), ProductManagementClick, CustomeClick2,
    ApiResponseListener<MyProductlist>, viewProductClick {
    lateinit var productListLL: LinearLayout
    lateinit var recyclerView: RecyclerView
    lateinit var addProduct: LinearLayout
    lateinit var mContext: Context
    lateinit var searchProducts: EditText
    lateinit var deals: LinearLayout

    lateinit var progressbar: ProgressBar
    lateinit var mainHeader: RelativeLayout
    var data: ArrayList<MyProductDocs> = ArrayList()
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    lateinit var nodata_tv: TextView
    lateinit var adapter: ProductManagementAdapter
    lateinit var pulltorefresh_dm: SwipeRefreshLayout
    lateinit var nsProductManagement: NestedScrollView
    lateinit var pbProductManagementPagination: ProgressBar
    lateinit var searchRL: RelativeLayout
    private var timer: Timer? = null

    var pages = 0
    var page = 1
    var limit = 20
    var dataLoadFlag = true
    var loaderFlag = true
    companion object {
        var apiProductManagementCallFlag = true
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_management, container, false)
        TabHandler.HandleTab(R.id.ll_product_tab, requireActivity())
        searchRL = view.findViewById(R.id.search)
        pbProductManagementPagination = view.findViewById(R.id.pbProductManagementPagination)
        nsProductManagement = view.findViewById(R.id.nsProductManagement)
        recyclerView = view.findViewById(R.id.ProductManagement_recycler)
        progressbar = view.findViewById(R.id.progressbar_productmanagement)
        addProduct = view.findViewById(R.id.add_product)
        pulltorefresh_dm = view.findViewById(R.id.pulltorefresh_dm)
        searchProducts = view.findViewById(R.id.searchProducts)
        nodata_tv = view.findViewById(R.id.nodata_tv)
        deals = view.findViewById(R.id.deals)
        productListLL = view.findViewById(R.id.productListLL)
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!

        mainHeader.visibility = View.VISIBLE


        deals.setSafeOnClickListener {
            DealsManagement.apiDealManagementCallFlag = true;
            parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, DealsManagement(""), "dealManagement").addToBackStack(null).commit()
        }

        addProduct.setSafeOnClickListener {
            
            val bundle = Bundle()
            bundle.putString("flag", "Add Product")
            val fragobj = AddProductsFragment()
            fragobj.arguments = bundle

            parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragobj, "addProducts").addToBackStack(null).commit()
        }


        nsProductManagement.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                dataLoadFlag = false
                loaderFlag = false

                pbProductManagementPagination.visibility = View.VISIBLE
                if (page == pages) {
                    pbProductManagementPagination.visibility = View.GONE
                } else {
                    page++
                    myproductsApi()
                }
            }
        })


        searchProducts.addTextChangedListener(object : TextWatcher {
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
                                myproductsApi()
                            } else {
                                myproductsApi()
                            }
                        }
                    }
                }, 600)


            }
        })


        pulltorefresh_dm.setOnRefreshListener {
            if (androidextention.isOnline(mContext)) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                resetPagination()
                myproductsApi()
            } else {
                recyclerView.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
            }
            pulltorefresh_dm.isRefreshing = false

        }

        return view
    }

    override fun onResume() {
        super.onResume()
        if(apiProductManagementCallFlag) {
            resetPagination()
            myproductsApi()
            apiProductManagementCallFlag = false
        } else {
            if (androidextention.isOnline(mContext)) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                if (data.size > 0) {
                    productListLL.visibility = View.VISIBLE
                    nodata_tv.visibility = View.GONE
                    setAdapater(data)
                } else {
                    nodata_tv.visibility = View.VISIBLE
                    productListLL.visibility = View.GONE
                }
            } else {
                recyclerView.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
            }
        }
    }

    override fun click2(_id: String?, servicename: String?) {

        val bundle = Bundle()
        bundle.putString("flag", "Product Details")
        val fragobj = ProductDetails()
        fragobj.setArguments(bundle)

        parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragobj, "productView").addToBackStack(null).commit()

    }

    fun myproductsApi() {
        if (androidextention.isOnline(mContext)) {
            GlobalScope.launch(Dispatchers.Main) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                if (loaderFlag) {
                    progressbar.visibility = View.VISIBLE
                }
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<MyProductlist> =
                ApiCallBack<MyProductlist>(this, "myproductsApi", mContext)
            try {


                val jsonObject = JsonObject()
                if (searchProducts.text.toString().isNotEmpty()) {
                    jsonObject.addProperty("search", searchProducts.text.toString())
                }
                jsonObject.addProperty("page", page)
                jsonObject.addProperty("limit", limit)
                serviceManager.myProductListApi(callBack, jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            recyclerView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }
    }

    override fun onApiSuccess(response: MyProductlist, apiName: String?) {
        progressbar.visibility = View.GONE

        if (response.responseCode == 200) {
            try {
                if (dataLoadFlag) {
                    data.clear()
                }
                page = response.result?.page!!
                pages = response.result.pages
                response.result.docs.let { data.addAll(it) }
                if (data.size > 0) {
                    productListLL.visibility = View.VISIBLE
                    nodata_tv.visibility = View.GONE
                    setAdapater(data)
                } else {
                    nodata_tv.visibility = View.VISIBLE
                    productListLL.visibility = View.GONE
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiErrorBody(response: String, apiName: String?) {

        try {
            progressbar.visibility = View.GONE
            nodata_tv.visibility = View.VISIBLE
            productListLL.visibility = View.GONE
            data.clear()
            adapter.notifyDataSetChanged()
            val gson = GsonBuilder().create()
            var pojo = response_modal_class()

            pojo = gson.fromJson(response, pojo::class.java)
            androidextention.alertBox(pojo.responseMessage, requireContext())

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        progressbar.visibility = View.GONE
    }

    fun setAdapater(data: ArrayList<MyProductDocs>) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ProductManagementAdapter(mContext, this, this, data, "product")
        recyclerView.adapter = adapter
    }


    override fun viewProductClick(itemId: String) {
        val bundle = Bundle()
        bundle.putString("productId", itemId)
        val fragobj = ProductDetails()
        fragobj.arguments = bundle

        parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragobj, "productView").addToBackStack(null).commit()
    }

    override fun editClick(id: String, flag: String) {

        val bundle = Bundle()
        bundle.putString("flag", "Edit Product")
        bundle.putString("productid", id)
        bundle.putString("productReference", flag)
        val fragobj = AddProductsFragment()
        fragobj.arguments = bundle

        parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragobj, "addProducts").addToBackStack(null).commit()
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Store the context in a member variable
        mContext = context
    }
}
