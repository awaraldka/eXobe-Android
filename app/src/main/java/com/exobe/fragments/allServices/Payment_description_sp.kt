package com.exobe.fragments.allServices

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.Adapter.PaymentFromCustomerAdapter
import com.exobe.Adapter.PaymentServiceAdapter
import com.exobe.Adapter.PaymentStatusAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.*
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject


class payment_description_sp() : Fragment() {
    lateinit var My_RecyclerView: RecyclerView
    lateinit var Adapter: PaymentStatusAdapter
    lateinit var paymentServiceAdapter: PaymentServiceAdapter
    lateinit var progressbar_payment: ProgressBar
    lateinit var noData_tv: TextView
    var back: LinearLayout? = null
    var data: ArrayList<PaymentFromCustomer> = ArrayList()
    lateinit var mContext: Context
    var MenuClick: LinearLayout? = null

    var title: TextView? = null
    var cart: ImageView? = null
    var filter: ImageView? = null
    var DealsImageView: ImageView? = null
    var greyBellImageView: ImageView? = null
    var mainHeader: RelativeLayout? = null
    var flagSide = ""
    var paymentFlag = ""
    var Subtitle = ""
    lateinit var internet_connection: RelativeLayout
    lateinit var nsPaymentHistory: NestedScrollView
    lateinit var pbPaymentHistoryPagination: ProgressBar
    var lottie: LottieAnimationView? = null
    var pages = 0
    var page = 1
    var limit = 20
    var dataLoadFlag = true
    var loaderFlag = true
    var remainingItems = 0
    var paymentFromCustomer = ArrayList<PaymentFromCustyomer_Docs>()
    lateinit var service_back: ImageView
    lateinit var service_MenuClick: ImageView
    var pendingOrder = ArrayList<RetailerOrderManagementDoc>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_payment_description, container, false)
        mContext = activity?.applicationContext!!
        pbPaymentHistoryPagination = view.findViewById(R.id.pbPaymentHistoryPagination)
        nsPaymentHistory = view.findViewById(R.id.nsPaymentHistory)
        My_RecyclerView = view.findViewById(R.id.payment_recycler)
        progressbar_payment = view.findViewById(R.id.progressbar_payment)
        noData_tv = view.findViewById(R.id.noData_tv)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!


        if (requireArguments().getString("paymentFlag") != null) {
            paymentFlag = requireArguments().getString("paymentFlag").toString()
        }
        if (requireArguments().getString("title") != null) {
            Subtitle = requireArguments().getString("title").toString()
        }


        if (requireArguments().getString("flagSide") != null) {
            flagSide = requireArguments().getString("flagSide").toString()

            if (flagSide == "retailer") {
                setToolbar()
                if (paymentFlag.equals("PaymentFromWholesalers")){
                    resetPagination()
                    PaymentTowholesalerApi("RETAILER")

                    nsPaymentHistory.setOnScrollChangeListener(object :
                        NestedScrollView.OnScrollChangeListener {
                        override fun onScrollChange(
                            v: NestedScrollView,
                            scrollX: Int,
                            scrollY: Int,
                            oldScrollX: Int,
                            oldScrollY: Int
                        ) {
                            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                                dataLoadFlag = false
                                loaderFlag = false

                                pbPaymentHistoryPagination.visibility = View.VISIBLE
                                if (page == pages) {
                                    pbPaymentHistoryPagination.visibility = View.GONE
                                } else {
                                    page++
                                    PaymentTowholesalerApi("RETAILER")
                                }
                            }
                        }
                    })
                }
                else {
                    resetPagination()
                    PendingOrderAPI()
                    nsPaymentHistory.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                        if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                            dataLoadFlag = false
                            loaderFlag = false


                            pbPaymentHistoryPagination.visibility = View.VISIBLE
                            if (remainingItems == 0) {
                                pbPaymentHistoryPagination.visibility = View.GONE
                            } else {
                                page++
                                PendingOrderAPI()
                            }
                        }
                    })


                }

                back!!.setSafeOnClickListener {
                    fragmentManager?.popBackStack()
                }
            } else if (flagSide=="service_provider") {
                service_back = activity?.findViewById(R.id.back)!!
                service_MenuClick = activity?.findViewById(R.id.MenuClick)!!
                service_back.visibility = View.VISIBLE
                service_MenuClick.visibility = View.GONE

                service_back.setSafeOnClickListener {
                   parentFragmentManager.popBackStack()
                }

                resetPagination()
                PaymentFromCustomerSPApi("SERVICE_PROVIDER")


                nsPaymentHistory.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                    if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                        dataLoadFlag = false
                        loaderFlag = false

                        pbPaymentHistoryPagination.visibility = View.VISIBLE
                        if (page == pages) {
                            pbPaymentHistoryPagination.visibility = View.GONE
                        } else {
                            page++
                            PaymentFromCustomerSPApi("SERVICE_PROVIDER")
                        }
                    }
                })


            }
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                val fm: FragmentManager = requireActivity().supportFragmentManager

                for (i in 0 until fm.backStackEntryCount){
                    fm.popBackStack()
                }


            }
        })

    }

    fun PendingOrderAPI() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            if (loaderFlag) {
                progressbar_payment.visibility = View.VISIBLE
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<RetailerOrderManagementResponse> =
                ApiCallBack<RetailerOrderManagementResponse>(object :
                    ApiResponseListener<RetailerOrderManagementResponse> {
                    override fun onApiSuccess(
                        response: RetailerOrderManagementResponse,
                        apiName: String?
                    ) {
                        progressbar_payment.visibility = View.GONE
                        println("pending order response:- $response")
                        if (response.responseCode == 200) {

                            try {
                                if (dataLoadFlag) {
                                    pendingOrder.clear()
                                }
                                remainingItems = response.result.remainingItems
                                page = response.result.page
                                pendingOrder.addAll(response.result.docs)
                                if (pendingOrder.size > 0) {
                                    setPaymentFromCustomerAdaptor(pendingOrder)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_payment.visibility = View.GONE
                        noData_tv.visibility = View.VISIBLE
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar_payment.visibility = View.GONE

                    }

                }, "PendingOrder", mContext)
            var jsonObject = JsonObject().apply {
                addProperty("page", page)
                addProperty("limit", limit)
            }

            try {
                serviceManager.paymentFromCustomerNew(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            noData_tv.visibility = View.GONE
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
        }
    }

    private fun setPaymentFromCustomerAdaptor(data: ArrayList<RetailerOrderManagementDoc>) {
        My_RecyclerView.layoutManager = LinearLayoutManager(activity)
        var Adapter = PaymentFromCustomerAdapter(mContext, data)
        My_RecyclerView.adapter = Adapter
    }

//    fun PaymentFromCustomerApi(Usertype:String) {
//        if (androidextention.isOnline(mContext)) {
//            internet_connection.visibility = View.GONE
//            lottie!!.initLoader(false)
//
//            progressbar_payment.visibility = View.VISIBLE
//            val serviceManager = ServiceManager(mContext)
//            val callBack: ApiCallBack<PaymentFromCustomer> =
//                ApiCallBack<PaymentFromCustomer>(object :
//                    ApiResponseListener<PaymentFromCustomer> {
//                    override fun onApiSuccess(
//                        response: PaymentFromCustomer,
//                        apiName: String?
//                    ) {
//                        progressbar_payment.visibility = View.GONE
////                        androidextention.disMissProgressDialog(requireContext())
//                        if (response.responseCode == 200) {
//                            try {
//
//                                var data = response.result?.docs as ArrayList<PaymentFromCustyomer_Docs>
//                                setAdapater(data)
//
//                            } catch (e : Exception) {
//                                e.printStackTrace()
//                            }
//                        }
//
//                    }
//
//                    override fun onApiErrorBody(response: String, apiName: String?) {
////                        androidextention.disMissProgressDialog(requireContext())
//                        progressbar_payment.visibility = View.GONE
//                        noData_tv.visibility = View.VISIBLE
//
//                        val gson = GsonBuilder().create()
//                        var pojo = response_modal_class()
//
//                        try {
////                            pojo = gson.fromJson(response, pojo::class.java)
////                            androidextention.alertBox(pojo.responseMessage, requireContext())
//
//                        } catch (e: Exception) {
//                            // handle failure at error parse
//                        }
//                    }
//
//                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
//                        progressbar_payment.visibility = View.GONE
//                        noData_tv.visibility = View.VISIBLE
//                    }
//
//                }, "PaymentFromCustomerApi", mContext)
////            val jsonObject=JsonObject()
////            jsonObject.addProperty("userType",Usertype)
//
//            try {
//                serviceManager.paymentRetailerCustomerApi(callBack)
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//        } else {
//            internet_connection.visibility = View.VISIBLE
//            lottie!!.initLoader(true)
//        }
//    }

    fun PaymentFromCustomerSPApi(Usertype:String) {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            if (loaderFlag) {
                progressbar_payment.visibility = View.VISIBLE
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<PaymentFromCustomer> =
                ApiCallBack<PaymentFromCustomer>(object :
                    ApiResponseListener<PaymentFromCustomer> {
                    override fun onApiSuccess(
                        response: PaymentFromCustomer,
                        apiName: String?
                    ) {
                        progressbar_payment.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                if (dataLoadFlag) {
                                    paymentFromCustomer.clear()
                                }
                                page = response.result.page
                                pages = response.result.pages
                                paymentFromCustomer.addAll(response.result.docs)
                                setServiceAdapater(paymentFromCustomer)

                            } catch (e : Exception) {
                                e.printStackTrace()
                            }
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_payment.visibility = View.GONE
                        noData_tv.visibility = View.VISIBLE
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar_payment.visibility = View.GONE
                        noData_tv.visibility = View.VISIBLE
                    }

                }, "PaymentFromCustomerApi", mContext)


            var jsonObject = JsonObject().apply {
                addProperty("page", page)
                addProperty("limit", limit)
            }
            try {
                serviceManager.Paymentfromcustomerapi(callBack,jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    private fun setServiceAdapater(paymentFromCustomer: ArrayList<PaymentFromCustyomer_Docs>) {
        My_RecyclerView.layoutManager = LinearLayoutManager(activity)
        paymentServiceAdapter = PaymentServiceAdapter(mContext, paymentFromCustomer)
        My_RecyclerView.adapter = paymentServiceAdapter

    }

    fun PaymentTowholesalerApi(userTYpe:String) {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            if (loaderFlag) {
                progressbar_payment.visibility = View.VISIBLE
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<PaymentFromCustomer> =
                ApiCallBack<PaymentFromCustomer>(object :
                    ApiResponseListener<PaymentFromCustomer> {
                    override fun onApiSuccess(
                        response: PaymentFromCustomer,
                        apiName: String?
                    ) {
                        progressbar_payment.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                if (dataLoadFlag) {
                                    paymentFromCustomer.clear()
                                }
                                page = response.result.page
                                pages = response.result.pages
                                paymentFromCustomer.addAll(response.result.docs)
                                setAdapater(paymentFromCustomer)

                            } catch (e : Exception) {
                                e.printStackTrace()
                            }
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_payment.visibility = View.GONE
                        noData_tv.visibility = View.VISIBLE
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
//                            pojo = gson.fromJson(response, pojo::class.java)
//                            androidextention.alertBox(pojo.responseMessage, requireContext())

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar_payment.visibility = View.GONE
                        noData_tv.visibility = View.VISIBLE
//                        androidextention.disMissProgressDialog(requireContext())
                    }

                }, "PaymentTowholesalerApi", mContext)
//            val jsonObject = JsonObject()
//            jsonObject.addProperty("userType", userTYpe)
            var jsonObject = JsonObject().apply {
                addProperty("page", page)
                addProperty("limit", limit)
            }

            try {
                serviceManager.transactionTowholesaler(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    fun setAdapater(data: ArrayList<PaymentFromCustyomer_Docs>) {
        My_RecyclerView.layoutManager = LinearLayoutManager(activity)
        Adapter = PaymentStatusAdapter(mContext, data)
        My_RecyclerView.adapter = Adapter
    }



    fun setToolbar() {
        title = activity?.findViewById(R.id.PreLoginTitle_TextView2)!!


        cart = activity?.findViewById(R.id.cart_icon)!!
        filter = activity?.findViewById(R.id.filter_icon)!!
        back = activity?.findViewById(R.id.imageView_back)!!
        MenuClick = activity?.findViewById(R.id.MenuClick)!!
        DealsImageView = activity?.findViewById(R.id.Deals_ImageView)!!
        greyBellImageView = activity?.findViewById(R.id.greyBell_ImageView)!!
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
        cartCount.visibility = View.GONE
        title!!.text = Subtitle
        cart!!.visibility = View.GONE
        filter!!.visibility = View.GONE
        back!!.visibility = View.VISIBLE

        MenuClick!!.visibility = View.GONE
        DealsImageView!!.visibility = View.GONE
        greyBellImageView!!.visibility = View.GONE
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE

    }

    private fun resetPagination() {
        page = 1
        dataLoadFlag = true
        loaderFlag = true
    }

}
