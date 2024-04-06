package com.exobe.fragments.cart

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.Adapter.OrderReviewAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.fragments.address.AddAddressFragment
import com.exobe.activities.OzowPaymentActivity
import com.exobe.utils.CommonFunctions
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.bottomSheet.PaymentPreview
import com.exobe.customClicks.*
import com.exobe.dialogs.DialogBoxPayment
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.BuyProductRequest
import com.exobe.entity.response.*
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceConstant
import com.exobe.entity.serviceBase.ServiceConstant.Companion.BASE_OWZO_URL
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.fragments.orderHistory.OrderHistoryFragment
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject


class OrderReview : Fragment(), ApiResponseListener<MyCartListResponse>, PaymentForOrderClick,PaymentDoneListener {

    lateinit var RecyclerLayout: RecyclerView
    lateinit var Adapter: OrderReviewAdapter
    lateinit var continue_order: Button
    lateinit var edit: ImageView
    lateinit var Add: TextView
    var data: ArrayList<MyCartList> = ArrayList()
    lateinit var progressbar: ProgressBar
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mContext: Context
    lateinit var totalAmount: TextView
    lateinit var txtVatNumber: TextView
    lateinit var txtTotalPrice: TextView
    lateinit var deliveryFee: TextView
    lateinit var txtName: TextView
    lateinit var txtAddress: TextView
    lateinit var txtPhoneNumber: TextView
    lateinit var orderReviewNestedScrollView: NestedScrollView
    var pages = 0
    var page = 1
    var limit = 20
    var price: ArrayList<Double> = ArrayList()
    var position = 1
    var userId = ""
    var addressId = ""
    var orderItemCatId = ArrayList<String>()
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null

    lateinit var payFastClick:WebView


    var orderPriceRequest = 0.0
    var actualPriceRequest = 0.0
    var deliveryFeeRequest = 0.0
    var deliveryTypeRequest = ""
    private var walletAmount = ""
    private var paymentFrom = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_order_review, container, false)
        mContext = activity?.applicationContext!!
        setToolbar()
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        RecyclerLayout = view.findViewById(R.id.orderReview_recyclerview)
        totalAmount = view.findViewById(R.id.totalAmount)
        continue_order = view.findViewById(R.id.continue_order)
        progressbar = view.findViewById(R.id.progressbar)
        edit = view.findViewById(R.id.edit)
        Add = view.findViewById(R.id.Add)
        txtVatNumber = view.findViewById(R.id.txtVatNumber)
        txtTotalPrice = view.findViewById(R.id.txtTotalPrice)
        txtName = view.findViewById(R.id.txtName)
        txtAddress = view.findViewById(R.id.txtAddress)
        txtPhoneNumber = view.findViewById(R.id.txtPhoneNumber)
        orderReviewNestedScrollView = view.findViewById(R.id.orderReviewNestedScrollView)
        deliveryFee = view.findViewById(R.id.deliveryFee)
        payFastClick = view.findViewById(R.id.payFastClick)



        if (requireArguments().getString("_id") != null) {
            addressId = requireArguments().getString("_id")!!
        }

        back.setSafeOnClickListener {
            parentFragmentManager.popBackStack()
        }

        continue_order.setSafeOnClickListener {
            val bottomSheetFragment = PaymentPreview("CartPayment",this,txtTotalPrice.text.toString(),walletAmount)
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)

        }

        val userType =
            SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.USER_TYPE)
        if (userType.equals("RETAILER")) {

            viewAddressApi()
            myCartListApi()

        } else if (userType.equals("CUSTOMER")) {
            viewAddressApi()
            myCartListApi()
        } else {

        }

        return view
    }

    fun viewAddressApi() {

        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<ViewAddressResponse> =
                ApiCallBack(object :
                    ApiResponseListener<ViewAddressResponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onApiSuccess(response: ViewAddressResponse, apiName: String?) {
                        progressbar.visibility = View.GONE

                        if (response.responseCode == 200) {
                            try {

                                    txtName.text = "${response.result.firstName} ${
                                        response.result.lastName
                                    }"

                                    var finalAddress = StringBuffer()
                                    if (response.result.addressLine1 != null && !response.result.addressLine1.equals("")) {
                                        finalAddress.append("${response.result.addressLine1},")
                                    }
                                    if (response.result.addressLine2 != null && !response.result.addressLine2.equals("")) {
                                        finalAddress.append("${response.result.addressLine2},")
                                    }
                                    if (response.result.city != null && response.result.city != "") {
                                        finalAddress.append("${response.result.city},")
                                    }
                                    if (response.result.state != null && response.result.state != "") {
                                        finalAddress.append("${response.result.state},")
                                    }
                                    if (response.result.country != null && response.result.country != "") {
                                        finalAddress.append("${response.result.country}, ")
                                    }
                                    if (response.result.zipCode != null && response.result.zipCode != "") {
                                        finalAddress.append("Zipcode: ${response.result.zipCode}")
                                    }
                                    txtAddress.text = "$finalAddress"

                                    txtPhoneNumber.text =
                                        "+${response.result.countryCode}-${
                                            response.result.mobileNumber
                                        }"

                                    userId = response.result._id.toString()

                                    edit.setSafeOnClickListener {
                                        parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, AddAddressFragment("Edit", userId, ""), "addAddress").addToBackStack(null).commit()
                                    }

                                    Add.setSafeOnClickListener {
                                        parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, AddAddressFragment("", userId, ""), "addAddress").addToBackStack(null).commit()
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

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, requireActivity())

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                    }

                }, "choosedeliveryApi", mContext)
            var jsonObject = JsonObject()
            jsonObject.addProperty("page", page)
            jsonObject.addProperty("limit", limit)
            try {
                serviceManager.viewAddress(callBack, addressId)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            orderReviewNestedScrollView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }

    }


    private fun setAdapter(array: ArrayList<MyCartList>) {
        RecyclerLayout.layoutManager = LinearLayoutManager(mContext)
        Adapter = OrderReviewAdapter(requireActivity(), array)
        RecyclerLayout.adapter = Adapter

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
        title.text = "Order Review"
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }



    fun myCartListApi() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<MyCartListResponse> =
                ApiCallBack<MyCartListResponse>(
                    this,
                    "choosedeliveryApi",
                    mContext
                )

            try {
                serviceManager.MyCartListApi(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            orderReviewNestedScrollView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }

    }


    @SuppressLint("SetTextI18n")
    override fun onApiSuccess(response: MyCartListResponse, apiName: String?) {
        progressbar.visibility = View.GONE
        if (response.responseCode == 200) {

            try {
                walletAmount = "${response.result.payOutAmount.walletAmount}"
                orderReviewNestedScrollView.visibility = View.VISIBLE
                price.clear()
                data = response.result.cartList
                val payoutData = response.result.payOutAmount

                if (data.size > 0) {

                    for (i in data.indices) {
                        orderItemCatId.add(data[i].Id!!)
                    }
                    orderPriceRequest =  payoutData.totalAmountWithVat.toDouble()
                    actualPriceRequest = payoutData.totalAmountRes.toDouble()
                    deliveryFeeRequest   = payoutData.deliveryFee.toDouble()
                    deliveryTypeRequest   = payoutData.deliveryMode

                    totalAmount.text = " R ${CommonFunctions.currencyFormatter(payoutData.totalAmountRes.toDouble())}"
                    txtVatNumber.text = "+R ${CommonFunctions.currencyFormatter(payoutData.vat.toDouble())}"
                    txtTotalPrice.text = "R ${CommonFunctions.currencyFormatter(payoutData.totalAmountWithVat.toDouble())}"
                    deliveryFee.text = "R ${CommonFunctions.currencyFormatter(payoutData.deliveryFee.toDouble())}"

                    setAdapter(data)
                } else {
                    val fm: FragmentManager = parentFragmentManager

                    for (i in 0 until fm.backStackEntryCount) {
                        fm.popBackStack()
                    }
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
        data.clear()
        setAdapter(data)
        try {
            pojo = gson.fromJson(response, pojo::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        progressbar.visibility = View.GONE
    }







    private fun buyProductApi() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<BuyProductResponse> =
                ApiCallBack(
                    object : ApiResponseListener<BuyProductResponse> {

                        override fun onApiSuccess(
                            response: BuyProductResponse,
                            apiName: String?
                        ) {

                            if (response.responseCode == 200) {
                                try {

                                    if (paymentFrom == "ozow"){
                                        paymentApi(response.result?._id!!)
                                    }
                                    if (paymentFrom == "wallet"){
                                        checkOutWalletOrderApi(response.result?._id!!)
                                    }
                                    if (paymentFrom == "payFast"){
                                        paymentPayFastApi(response.result?._id!!)
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

                            try {
                                pojo = gson.fromJson(response, pojo::class.java)
                                androidextention.alertBox(
                                    pojo.responseMessage,
                                    requireContext()
                                )

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                        override fun onApiFailure(failureMessage: String?, apiName: String?) {
                            progressbar.visibility = View.GONE


                        }
                    }, "BuyProductApi", mContext
                )
            val buyProductRequest = BuyProductRequest()
            buyProductRequest.orderPrice = orderPriceRequest
            buyProductRequest.actualPrice = actualPriceRequest
            buyProductRequest.cartId = orderItemCatId
            buyProductRequest.address = addressId
            buyProductRequest.deliveryFee = deliveryFeeRequest
            buyProductRequest.deliveryType = deliveryTypeRequest


            try {
                serviceManager.BuyProductApi(callBack, buyProductRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            orderReviewNestedScrollView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }
    private fun checkOutWalletOrderApi(orderId: String) {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<CommonResponseForAll> =
                ApiCallBack(
                    object : ApiResponseListener<CommonResponseForAll> {

                        override fun onApiSuccess(
                            response: CommonResponseForAll,
                            apiName: String?
                        ) {

                            if (response.responseCode == 200) {
                                try {
                                    parentFragmentManager.let { DialogBoxPayment(this@OrderReview).show(it, "MyCustomFragment") }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }

                        }

                        override fun onApiErrorBody(response: String, apiName: String?) {
                            progressbar.visibility = View.GONE

                            val gson = GsonBuilder().create()
                            var pojo = response_modal_class()

                            try {
                                pojo = gson.fromJson(response, pojo::class.java)
                                androidextention.alertBox(
                                    pojo.responseMessage,
                                    requireContext()
                                )

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                        override fun onApiFailure(failureMessage: String?, apiName: String?) {
                            progressbar.visibility = View.GONE


                        }
                    }, "checkOutWalletOrderApi", mContext
                )



            try {
                serviceManager.checkOutWalletOrderApi(callBack, orderId = orderId,BASE_OWZO_URL,BASE_OWZO_URL,BASE_OWZO_URL,true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            orderReviewNestedScrollView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    private fun paymentApi(orderId: String) {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
//            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<OzowPaymentResponse> =
                ApiCallBack<OzowPaymentResponse>(object :
                    ApiResponseListener<OzowPaymentResponse> {
                    override fun onApiSuccess(response: OzowPaymentResponse, apiName: String?) {
                        progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            val url = response.result
                            val intent = Intent(requireActivity(), OzowPaymentActivity::class.java)
                            intent.putExtra("websiteUrl", url)
                            startActivityForResult(intent, 1)
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar.visibility = View.GONE

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, requireActivity())

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "Server not responding, Try again later!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }, "PaymentApi", mContext)

            var jsonObject = JsonObject().apply {
                addProperty("shippingAddress", addressId)
                addProperty("cancelUrl", BASE_OWZO_URL)
                addProperty("errorUrl", BASE_OWZO_URL)
                addProperty("SuccessUrl", BASE_OWZO_URL)
                addProperty("testMode", true)
            }
            try {
                serviceManager.ozowPayment(callBack, orderId, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            orderReviewNestedScrollView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                var fm: FragmentManager = fragmentManager!!
                for (i in 1 until fm.backStackEntryCount) {
                    fm.popBackStack()
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.FrameLayout, OrderHistoryFragment(""), "OrderHistory")
                    .addToBackStack(null).commit()
            }
        }
    }

    override fun ozow() {
        paymentFrom = "ozow"
        buyProductApi()
    }

    override fun payfast() {
        paymentFrom = "payFast"
        buyProductApi()
    }

    override fun walletClick() {
        paymentFrom = "wallet"
        buyProductApi()
    }

    override fun paymentDone() {
        val fm: FragmentManager = parentFragmentManager
        for (i in 1 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.FrameLayout, OrderHistoryFragment(""), "OrderHistory")
            .addToBackStack(null).commit()
    }

    override fun FailedDone() {

    }

    override fun cancelPayment() {

    }


    private fun paymentPayFastApi(orderId: String) {
        if (androidextention.isOnline(mContext)) {
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<PayFastResponse> =
                ApiCallBack(object :
                    ApiResponseListener<PayFastResponse> {
                    override fun onApiSuccess(response: PayFastResponse, apiName: String?) {
                        progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            if (response.payFastResult.file.isNotEmpty()){

                                continue_order.isVisible = false
                                payFastClick.isVisible = true
                                payFastClick.loadDataWithBaseURL(null, response.payFastResult.file, "text/html", "UTF-8", null)
                            }


                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar.visibility = View.GONE

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, requireActivity())

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {

                    }

                }, "paymentPayFastApi", mContext)


            val jsonObject = JsonObject().apply {

                addProperty("cancelUrl", BASE_OWZO_URL)
                addProperty("errorUrl", BASE_OWZO_URL)
                addProperty("SuccessUrl", BASE_OWZO_URL)
                addProperty("testMode", true)
                addProperty("trxFrom", "WEB")
                addProperty("webError", "https://ernestweb-release.mobiloitte.io/customer-payment-error")
                addProperty("webFailure", "https://ernestweb-release.mobiloitte.io/customer-payment-cancel")
                addProperty("webSuccess", "https://ernestweb-release.mobiloitte.io/customer-payment-success")

            }



            try {
                serviceManager.checkoutPayFastApi(callBack, orderId,jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            orderReviewNestedScrollView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }




}
