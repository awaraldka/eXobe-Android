package com.exobe.fragments.orderHistory

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.OrderTrackingAdapter
import com.exobe.adaptor.OrderViewitemAdapter
import com.exobe.adaptor.ServiceViewitemAdapter
import com.exobe.fragments.RetailerHomepage
import com.exobe.Model.LastOrderModel
import com.exobe.R
import com.exobe.androidextention
import com.exobe.utils.CommonFunctions
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.SetTitles
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.FeedBackData
import com.exobe.entity.request.FeedBackDataRequest
import com.exobe.entity.response.*
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.extension.diasplay_toast
import com.exobe.utils.Progresss
import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import com.example.validationpractice.utlis.FormValidation.getSystemService

class OrderHistoryViewFragment : Fragment(), SetTitles {
    lateinit var mainHeader: RelativeLayout
    lateinit var mContext: Context
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var orderId: TextView
    lateinit var idTitle: TextView
    lateinit var addressTitle: TextView
    lateinit var amount: TextView
    lateinit var deliveryFee: TextView
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var deals: TextView
    lateinit var name: TextView
    lateinit var address: TextView
    lateinit var phone: TextView
    lateinit var recyclerView_orderview: RecyclerView
    lateinit var progressBarOrderView: ProgressBar
    lateinit var LLOrder_review: LinearLayout
    lateinit var continue_shopping: Button
    lateinit var actual_amount: TextView
    lateinit var vatamount: TextView
    lateinit var zipcode: TextView
    lateinit var paymentStatus: TextView
    lateinit var deliveryStatus: TextView
    var productId = ""
    var screenFlag = ""
    var status = ""
    var invoiceUrl = ""
    var orderFileName = ""
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null

    lateinit var orderStatusTV:TextView
    lateinit var deliveryFeeRL:RelativeLayout
    lateinit var deliveryStatusRL:RelativeLayout
    lateinit var PaymentStatusRL:RelativeLayout
    lateinit var ratingLL:LinearLayout
    lateinit var orderTrackingRecyclerView:RecyclerView
    lateinit var orderTrackingAdapter: OrderTrackingAdapter
    lateinit var addFeedback: TextView
    lateinit var feedbackSecondEt: EditText
    lateinit var ratingBarSecond: RatingBar
    lateinit var feedbackFirstEt: EditText
    lateinit var ratingBar: RatingBar
    lateinit var titleRateSecondTV: TextView
    lateinit var titleRateFirstTV: TextView
    lateinit var invoice: TextView

    lateinit var data: ArrayList<OrderViewProductDetails>
    val itemList: ArrayList<LastOrderModel> = ArrayList()

    var userType = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_order_view_retailer, container, false)

        userType = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.USER_TYPE).toString()


        if (requireArguments().getString("flag") != null) {
            screenFlag = requireArguments().getString("flag").toString()
        }


        arguments?.getString("status")?.let { status =  it }
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        mainHeader.visibility = View.VISIBLE
        mContext = activity?.applicationContext!!

        zipcode = view.findViewById(R.id.zipcode)
        recyclerView_orderview = view.findViewById(R.id.recyclerView_orderview)
        orderId = view.findViewById(R.id.orderId)
        idTitle = view.findViewById(R.id.idTitle)
        addressTitle = view.findViewById(R.id.addressTitle)
        LLOrder_review = view.findViewById(R.id.LLOrder_review)
        amount = view.findViewById(R.id.amount)
        deliveryFee = view.findViewById(R.id.deliveryFee)
        address = view.findViewById(R.id.address)
        name = view.findViewById(R.id.name)
        phone = view.findViewById(R.id.phone)
        continue_shopping = view.findViewById(R.id.continue_shopping)
        progressBarOrderView = view.findViewById(R.id.progressBarOrderView)
        actual_amount = view.findViewById(R.id.actual_amount)
        vatamount = view.findViewById(R.id.vatamount)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        orderTrackingRecyclerView = view.findViewById(R.id.orderTrackingRecyclerView)
        deliveryStatus = view.findViewById(R.id.deliveryStatus)
        paymentStatus = view.findViewById(R.id.paymentStatus)
        orderStatusTV = view.findViewById(R.id.orderStatusTV)
        deliveryFeeRL = view.findViewById(R.id.deliveryFeeRL)
        deliveryStatusRL = view.findViewById(R.id.deliveryStatusRL)
        PaymentStatusRL = view.findViewById(R.id.PaymentStatusRL)
        ratingLL = view.findViewById(R.id.ratingLL)
        addFeedback = view.findViewById(R.id.add)
        feedbackSecondEt = view.findViewById(R.id.feedbackSecondEt)
        ratingBarSecond = view.findViewById(R.id.ratingBarSecond)
        feedbackFirstEt = view.findViewById(R.id.feedbackFirstEt)
        ratingBar = view.findViewById(R.id.ratingBar)
        titleRateSecondTV = view.findViewById(R.id.titleRateSecondTV)
        titleRateFirstTV = view.findViewById(R.id.titleRateFirstTV)
        invoice = view.findViewById(R.id.invoice)







        setToolbar()

        continue_shopping.setOnClickListener {
            SavedPrefManager.savePreferenceBoolean(
                requireContext(),
                SavedPrefManager.CALL_BACK_API, false
            )
            val fm: FragmentManager = requireActivity().supportFragmentManager

            for (i in 0 until fm.backStackEntryCount) {
                fm.popBackStack()
            }

            val bundle  = Bundle()
            bundle.putString("productId","")
            bundle.putString("serviceId","")
            bundle.putString("campainOn","")
            bundle.putString("notifyType","notifyType")
            val obj =  RetailerHomepage(this, "")
            obj.arguments = bundle


            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, obj, "continue_shopping")
                .addToBackStack(null).commit()
        }

        back.setOnClickListener {
            SavedPrefManager.savePreferenceBoolean(
                requireContext(),
                SavedPrefManager.CALL_BACK_API, true
            )
            parentFragmentManager.popBackStack()
        }

        if (screenFlag == "customerServiceDetails") {
            invoice.isVisible = true
            setTitle()
            serviceDetails()
        } else {
            invoice.isVisible = true
            if (requireArguments().getString("productId") != null || requireArguments().getString("productId") != "") {
                productId = requireArguments().getString("productId").toString()
            }
//            downloadInvoiceApi()
            setTitle()
            OrderListApi(productId)
        }


        invoice.setSafeOnClickListener {
            if(invoiceUrl.isEmpty()){
                diasplay_toast("Please try again later.")
                return@setSafeOnClickListener
            }

            val request = DownloadManager.Request(Uri.parse(invoiceUrl))
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Order_eXobe.pdf")
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            val downloadManager = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadId = downloadManager.enqueue(request)
            diasplay_toast("Invoice Downloading Start...")


        }


        addFeedback.setOnClickListener {
            if (ratingBar.rating.toDouble().equals(0.0) || feedbackFirstEt.text.toString().isEmpty()){
                if (userType.uppercase()=="CUSTOMER"){
                        androidextention.alertBox("Please give rating to retailer",requireContext())
                }else{
                        androidextention.alertBox("Please give rating to wholesaler.",requireContext())
                    }
            }else if (ratingBarSecond.rating.toDouble().equals(0.0) || feedbackSecondEt.text.toString().isEmpty()){
                    androidextention.alertBox("Please give rating to delivery partner.",requireContext())
                }else{
                    addFeedbackApi()
                }

        }

        getFeedBackByOrderIdApi()
        downloadInvoiceApi()
        return view

    }

    @SuppressLint("SetTextI18n")
    private fun serviceDetails() {

        if (requireArguments().getSerializable("serviceData") != null) {
            var serviceData = requireArguments().getSerializable("serviceData") as NewOrderServiceReqDoc
            LLOrder_review.visibility = View.VISIBLE
            orderStatusTV.visibility = View.GONE
            orderTrackingRecyclerView.visibility = View.VISIBLE
            deliveryFeeRL.visibility = View.GONE
            deliveryStatusRL.visibility = View.GONE
            PaymentStatusRL.visibility = View.GONE

            continue_shopping.text = "Continue"
            idTitle.text = "Booking Id:"
            addressTitle.text = "Booking Address"
            orderId.text = serviceData.orderId
            orderFileName = serviceData.orderId
            productId = serviceData.id
            amount.text = "R ${CommonFunctions.currencyFormatter(serviceData.orderPrice.toDouble())}"
            deliveryFee.text = "R ${CommonFunctions.currencyFormatter(serviceData.deliveryFee.toDouble())}"

            paymentStatus.text =  serviceData.paymentStatus
            deliveryStatus.text =  serviceData.deliveryStatus

            setupRecycler(serviceData.orderTracking)


            if (serviceData.paymentStatus.lowercase() == "pending") {
                paymentStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            } else {
                paymentStatus.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.delivered
                ))
            }
            if (serviceData.deliveryStatus.lowercase() == "pending") {
                deliveryStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            } else {
                deliveryStatus.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.delivered
                ))
            }




            actual_amount.text = "R ${CommonFunctions.currencyFormatter(serviceData.actualPrice.toDouble())}" // Grand Total

            val vatAmount = (15 / 100.0) * serviceData.actualPrice.toDouble()
            vatamount.text = "R ${CommonFunctions.currencyFormatter(vatAmount)}"
            name.text = "${serviceData.shippingFixedAddress.firstName} ${serviceData.shippingFixedAddress.lastName}"

            var fullAddress = StringBuffer()
            if (serviceData.shippingFixedAddress.addressLine1 != "" && serviceData.shippingFixedAddress.addressLine1 != null) {
                fullAddress.append("${serviceData.shippingFixedAddress.addressLine1},")
            }
//            if (serviceData.shippingFixedAddress.addressLine2 != "" && serviceData.shippingFixedAddress.addressLine2 != null) {
//                fullAddress.append("${serviceData.shippingFixedAddress.addressLine2},")
//            }
            if (serviceData.shippingFixedAddress.city != "" && serviceData.shippingFixedAddress.city != null) {
                fullAddress.append("${serviceData.shippingFixedAddress.city},")
            }
            if (serviceData.shippingFixedAddress.state != "" && serviceData.shippingFixedAddress.state != null) {
                fullAddress.append("${serviceData.shippingFixedAddress.state},")
            }
            if (serviceData.shippingFixedAddress.country != "" && serviceData.shippingFixedAddress.country != null) {
                fullAddress.append("${serviceData.shippingFixedAddress.country}")
            }
            address.text = Html.fromHtml("<b>${"Address:"}</b> $fullAddress")
            phone.text =
                Html.fromHtml("<b>${"Ph. no:"}</b> +${serviceData.shippingFixedAddress.countryCode.toString()}-${serviceData.shippingFixedAddress.mobileNumber.toString()}")
            zipcode.text =
                Html.fromHtml("<b>${"Zipcode:"}</b> ${serviceData.shippingFixedAddress.zipCode.toString()}")
            setServiceAdapter(
                serviceData.serviceDetails,
                serviceData.orderStatus,
                serviceData.orderPrice,
                serviceData.orderId

            )
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
        cart.visibility = View.GONE
        filter.visibility = View.GONE
        back.visibility = View.VISIBLE
        MenuClick.visibility = View.GONE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE

    }

    private fun setTitle() {
        if (productId == "") {
            title.text = "Booking Details"
        } else {
            title.text = "Order Details"
        }

    }

    fun setAdapater(Data: ArrayList<OrderViewProductDetails>?, createdAt: String?) {
        recyclerView_orderview.layoutManager = LinearLayoutManager(context)
        var adapter = Data?.let { OrderViewitemAdapter(mContext, it, createdAt) }
        recyclerView_orderview.adapter = adapter
    }

    fun setServiceAdapter(
        serviceData: List<NewOrderServiceReqServiceDetail>,
        orderStatus: String,
        orderPrice: Number,
        orderId: String
    ) {
        recyclerView_orderview.layoutManager = LinearLayoutManager(context)
        var adapter =
            ServiceViewitemAdapter(mContext, serviceData, orderStatus, orderPrice, orderId)
        recyclerView_orderview.adapter = adapter
    }

    fun OrderListApi(orderid: String) {
        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressBarOrderView.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<OrderViewResponse> =
                ApiCallBack(object :
                    ApiResponseListener<OrderViewResponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onApiSuccess(
                        response: OrderViewResponse,
                        apiName: String?
                    ) {
                        progressBarOrderView.visibility = View.GONE
                        if (response.responseCode == 200) {

                            setupRecycler(response.result!!.orderTracking)

                            if (status.uppercase() =="DELIVERED"){
                                ratingLL.isVisible =  true
                            }

                            LLOrder_review.visibility = View.VISIBLE
                            orderId.text = response.result.orderId
                            orderFileName= response.result.orderId!!
                            amount.text = "R ${CommonFunctions.currencyFormatter(response.result.orderPrice.toDouble())}"
                            actual_amount.text = "R ${CommonFunctions.currencyFormatter(response.result.actualPrice.toDouble())}"


                            deliveryFee.text = "R ${CommonFunctions.currencyFormatter(response.result.deliveryFee.toDouble())}"

                            paymentStatus.text =  response.result.paymentStatus
                            deliveryStatus.text =  response.result.deliveryStatus

                            if (response.result.paymentStatus.lowercase() == "pending") {
                                paymentStatus.setTextColor(ContextCompat.getColor(requireContext(),
                                    R.color.red
                                ))
                            } else {
                                paymentStatus.setTextColor(ContextCompat.getColor(requireContext(),
                                    R.color.delivered
                                ))
                            }
                            if (response.result.deliveryStatus.lowercase() == "pending") {
                                deliveryStatus.setTextColor(ContextCompat.getColor(requireContext(),
                                    R.color.red
                                ))
                            } else {
                                deliveryStatus.setTextColor(ContextCompat.getColor(requireContext(),
                                    R.color.delivered
                                ))
                            }





                            val vatAmount = (15 / 100.0) * response.result.actualPrice.toDouble()
                            vatamount.text = "R ${CommonFunctions.currencyFormatter(vatAmount)}"
                            name.text = "${response.result.shippingFixedAddress.firstName} ${response.result.shippingFixedAddress.lastName.toString()}"

                            val fullAddress = StringBuffer()
                            if (response.result.shippingFixedAddress.addressLine1 != "" && response.result.shippingFixedAddress.addressLine1 != null) {
                                fullAddress.append("${response.result.shippingFixedAddress.addressLine1},")
                            }

                            if (response.result.shippingFixedAddress.city != "" && response.result.shippingFixedAddress.city != null) {
                                fullAddress.append("${response.result.shippingFixedAddress.city},")
                            }
                            if (response.result.shippingFixedAddress.state != "" && response.result.shippingFixedAddress.state != null) {
                                fullAddress.append("${response.result.shippingFixedAddress.state},")
                            }
                            if (response.result.shippingFixedAddress.country != "" && response.result.shippingFixedAddress.country != null) {
                                fullAddress.append("${response.result.shippingFixedAddress.country}")
                            }
                            address.text = Html.fromHtml("<b>${"Address:"}</b> $fullAddress")
                            phone.text = Html.fromHtml("<b>${"Ph. no:"}</b> +${response.result.shippingFixedAddress.countryCode.toString()}-${response.result.shippingFixedAddress.mobileNumber.toString()}")
                            zipcode.text = Html.fromHtml("<b>${"Zipcode:"}</b> ${response.result.shippingFixedAddress.zipCode.toString()}")
                            var createdAt = response.result.createdAt
                            data = response.result.productDetails!!
                            setAdapater(data, createdAt)


                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressBarOrderView.visibility = View.GONE


                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressBarOrderView.visibility = View.GONE
                    }

                }, "OrderListApi", requireContext())


            try {
                serviceManager.orderlist2api(callBack, orderid)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }
    }

    override fun title(s: String) {
        TODO("Not yet implemented")
    }

    private fun setupRecycler(orderTracking: ArrayList<OrderTracking>) {
        val startingIndex = 0

        for (i in 0 until  orderTracking.size) {
            if (i == startingIndex) {
                orderTracking[i].progressValue = 50
            } else if (orderTracking[i].orderStatus) {
                orderTracking[i - 1].progressValue = 100
                orderTracking[i].progressValue = 50
            } else {

            }
        }



        orderTrackingRecyclerView.layoutManager = LinearLayoutManager(context)
        orderTrackingAdapter = OrderTrackingAdapter(requireContext(), orderTracking,status)
        orderTrackingRecyclerView.adapter = orderTrackingAdapter
    }


    private fun addFeedbackApi() {
        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            Progresss.start(requireContext())
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<CommonResponseForAll> =
                ApiCallBack<CommonResponseForAll>(object :
                    ApiResponseListener<CommonResponseForAll> {
                    @SuppressLint("SetTextI18n")
                    override fun onApiSuccess(
                        response: CommonResponseForAll,
                        apiName: String?
                    ) {
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            getFeedBackByOrderIdApi()
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        Progresss.stop()
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, "addFeedbackApi", requireContext())


            try {

                val request  = FeedBackDataRequest()
                request.feedBackData.clear()

                if (userType.uppercase()=="CUSTOMER"){
                    request.feedBackData.add(FeedBackData("RETAILER", orderId = productId,ratingBar.rating,feedbackFirstEt.text.toString()))
                    request.feedBackData.add(FeedBackData("DELIVERY_PARTNER", orderId = productId,ratingBarSecond.rating,feedbackSecondEt.text.toString()))
                }else{
                    request.feedBackData.add(FeedBackData("WHOLE_SALER", orderId = productId, ratingBar.rating,feedbackFirstEt.text.toString()))
                    request.feedBackData.add(FeedBackData("DELIVERY_PARTNER", orderId = productId,ratingBarSecond.rating,feedbackSecondEt.text.toString()))
                }




                serviceManager.submitFeedbackApi(callBack,request)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }
    }
    private fun getFeedBackByOrderIdApi() {
        if (androidextention.isOnline(requireContext())) {
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<FeedbackResponse> =
                ApiCallBack(object : ApiResponseListener<FeedbackResponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onApiSuccess(response: FeedbackResponse, apiName: String?) {
                        if (response.responseCode == 200) {

                            if(response.result.isNotEmpty()){
                                feedbackFirstEt.isEnabled = false
                                feedbackSecondEt.isEnabled = false
                                addFeedback.isVisible = false


                                if (userType.uppercase()=="CUSTOMER"){
                                        if (response.result[0].ratingToUserType =="RETAILER"){
                                            feedbackFirstEt.setText(response.result[0].review)
                                            ratingBar.rating =  response.result[0].rating.toFloat()
                                            ratingBarSecond.rating =  response.result[1].rating.toFloat()
                                            feedbackSecondEt.setText(response.result[1].review)


                                        }else{
                                            feedbackFirstEt.setText(response.result[1].review)
                                            feedbackFirstEt.setText(response.result[0].review)
                                            ratingBar.rating =  response.result[1].rating.toFloat()
                                            ratingBarSecond.rating =  response.result[0].rating.toFloat()
                                        }
                                    }else{

                                        if (response.result[0].ratingToUserType =="WHOLE_SALER"){
                                            feedbackFirstEt.setText(response.result[0].review)
                                            ratingBar.rating =  response.result[0].rating.toFloat()
                                            ratingBarSecond.rating =  response.result[1].rating.toFloat()
                                            feedbackSecondEt.setText(response.result[1].review)


                                        }else{
                                            feedbackFirstEt.setText(response.result[1].review)
                                            feedbackFirstEt.setText(response.result[0].review)
                                            ratingBar.rating =  response.result[1].rating.toFloat()
                                            ratingBarSecond.rating =  response.result[0].rating.toFloat()
                                        }

                                    }



                            }



                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    }

                }, "getFeedBackByOrderIdApi", requireContext())


            try {
                val type = if(userType.uppercase()=="CUSTOMER") "CUSTOMER" else "RETAILER"
                serviceManager.getFeedBackByOrderIdApi(callBack,userType =  type ,productId)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
    private fun downloadInvoiceApi() {
        if (androidextention.isOnline(requireContext())) {
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<GetInvoiceDownloadResponse> =
                ApiCallBack(object : ApiResponseListener<GetInvoiceDownloadResponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onApiSuccess(response: GetInvoiceDownloadResponse, apiName: String?) {
                        if (response.responseCode == 200) {

                            invoiceUrl = response.result
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    }

                }, "getFeedBackByOrderIdApi", requireContext())


            try {
                serviceManager.getInvoiceApi(callBack,productId)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }



}
