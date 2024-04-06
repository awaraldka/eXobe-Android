package com.exobe.fragments.retailr

import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.activities.OtpVerification
import com.exobe.adaptor.ProductDetailsAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.adaptor.OrderTrackingAdapter
import com.exobe.utils.CommonFunctions
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.*
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.util.DateFormat
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.util.*

class RetailerOderDetails : Fragment() {

    lateinit var OderDetails: RecyclerView
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var orderid: TextView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var llcustomerordersview: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var CalenderClickFrom: RelativeLayout
    lateinit var mContext: Context
    lateinit var TransactionTV: TextView
    lateinit var PaymentMode: TextView
    lateinit var amount: TextView
    lateinit var customername: TextView
    lateinit var createdOn: TextView
    lateinit var Email: TextView
    lateinit var Phone: TextView
    lateinit var address: TextView

    lateinit var progressbar_orderview: ProgressBar
    lateinit var map: TextView
    var latitude: String = ""
    var longitude: String = ""
    var yearset = 0
    var monthset = 0
    var day = 0
    val c = Calendar.getInstance()
    lateinit var txtFromDate: TextView
    lateinit var save_button: Button
    var order_id = ""
    var flag = ""
    var orderId = ""
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null


    lateinit var orderTrackingRecyclerView:RecyclerView
    lateinit var orderTrackingAdapter: OrderTrackingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_retailer_oder_details, container, false)
        setToolbar()
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!

        OderDetails = view.findViewById(R.id.OderDetails)
        txtFromDate = view.findViewById(R.id.from_date_Text)
        TransactionTV = view.findViewById(R.id.TransactionTV)
        customername = view.findViewById(R.id.customername)
        llcustomerordersview = view.findViewById(R.id.llcustomerordersview)
        progressbar_orderview = view.findViewById(R.id.progressbar_orderview)
        Email = view.findViewById(R.id.Email)
        Phone = view.findViewById(R.id.Phone)
        address = view.findViewById(R.id.address)
        PaymentMode = view.findViewById(R.id.PaymentMode)
        createdOn = view.findViewById(R.id.createdOn)
        map = view.findViewById(R.id.map)
        amount = view.findViewById(R.id.amount)
        orderid = view.findViewById(R.id.orderid)
        CalenderClickFrom = view.findViewById(R.id.CalenderClickFrom)
        save_button = view.findViewById(R.id.save_button)
        mContext = activity?.applicationContext!!
        OderDetails.layoutManager = LinearLayoutManager(context)
        orderTrackingRecyclerView = view.findViewById(R.id.orderTrackingRetailer)


        getValues()


        back.setSafeOnClickListener {
            parentFragmentManager.popBackStack()
        }


        val calendar: Calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val date = calendar.get(Calendar.DAY_OF_MONTH)


        CalenderClickFrom.setSafeOnClickListener {

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                R.style.DatePickerTheme,
                DatePickerDialog.OnDateSetListener
                { view, year, monthOfYear, dayOfMonth ->
                    c.set(year, monthOfYear + 1, dayOfMonth)

                    txtFromDate.text = "$dayOfMonth-${monthOfYear + 1}-$year"
                    yearset = year
                    monthset = monthOfYear + 1
                    day = dayOfMonth


                },
                year,
                month,
                date
            )

            datePickerDialog.show()
        }
        map.setSafeOnClickListener {

            val srcAdd = "&origin=" + SavedPrefManager.getLatitudeLocation()
                .toString() + "," + SavedPrefManager.getLongitudeLocation().toString()
//
            val desAdd = "&destination=$longitude,$latitude"

            val link =
                "https://www.google.com/maps/dir/?api=1&travelmode=driving$srcAdd$desAdd"
            Log.e("Url Polyline?", link)


            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                //                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" +stringLatitude+ "," + stringLongitude + "&daddr=" +  Double.parseDouble(workOrderDetailsModelClass.getCoords_lat()) + "," + Double.parseDouble(workOrderDetailsModelClass.getCoords_lng())));
                startActivity(intent)
            } catch (ane: ActivityNotFoundException) {
                Toast.makeText(context, "Please Install Google Maps ", Toast.LENGTH_LONG).show()
            } catch (ex: java.lang.Exception) {
                ex.message
            }

        }

        save_button.setSafeOnClickListener {
            MarkAsDoneProductApi()
        }

        OrderListApi()
        ViewTransactionApi()
        return view
    }


    fun OrderListApi() {
        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar_orderview.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<ViewOrderResponse> =
                ApiCallBack<ViewOrderResponse>(object :
                    ApiResponseListener<ViewOrderResponse> {
                    override fun onApiSuccess(
                        response: ViewOrderResponse,
                        apiName: String?
                    ) {

                        if (response.responseCode == 200) {
                            try {


                                setupRecycler(response.result!!.orderTracking)

                                progressbar_orderview.visibility = View.GONE
                                llcustomerordersview.visibility = View.VISIBLE
                                amount.text =
                                    "R ${CommonFunctions.currencyFormatter(response.result?.myOrderPrice!!.toDouble())}"
                                orderId = response.result?.id!!
                                var Data = response.result.myOrders
                                setApapter(Data)
                                orderid.text = response.result.orderId
                                customername.text =
                                    response.result?.shippingFixedAddress?.firstName + " " + response.result?.shippingFixedAddress?.lastName.toString()
                                Email.text = response.result.shippingFixedAddress.email.toString()
                                Phone.text =
                                    response.result.shippingFixedAddress.mobileNumber.toString()
                                var fullAddress = StringBuffer()
                                if (response.result.shippingFixedAddress.addressLine1 != "") {
                                    fullAddress.append("${response.result.shippingFixedAddress.addressLine1},")
                                }
//                                if (response.result.shippingFixedAddress.addressLine2 != "") {
//                                    fullAddress.append("${response.result.shippingFixedAddress.addressLine1},")
//                                }
                                if (response.result.shippingFixedAddress.city != "") {
                                    fullAddress.append("${response.result.shippingFixedAddress.city},")
                                }
                                if (response.result.shippingFixedAddress.state != "") {
                                    fullAddress.append("${response.result.shippingFixedAddress.state},")
                                }
                                if (response.result.shippingFixedAddress.country != "") {
                                    fullAddress.append("${response.result.shippingFixedAddress.country}")
                                }
                                address.text = "$fullAddress"
//                                address.text =
//                                    "${response.result?.shippingFixedAddress?.addressLine1}, ${response.result?.shippingFixedAddress?.addressLine2}, ${response.result?.shippingFixedAddress?.city}, ${response.result?.shippingFixedAddress?.state}, ${response.result?.shippingFixedAddress?.country}, ${response.result?.shippingFixedAddress?.zipCode}"
                                latitude =
                                    response.result.shippingFixedAddress.location.coordinates[0]
                                        .toString()
                                longitude =
                                    response.result.shippingFixedAddress.location.coordinates[1]
                                        .toString()
                                createdOn.text =
                                    "${DateFormat.NotificationDate(response.result.createdAt)}"

                            } catch (e: Exception) {

                                e.printStackTrace()
                            }
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_orderview.visibility = View.GONE


                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar_orderview.visibility = View.GONE

                    }

                }, "OrderListApi", requireContext())


            try {
                serviceManager.orderViewapi(callBack, order_id)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            llcustomerordersview.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    fun ViewTransactionApi() {
        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<ViewTransaction_Respomse> =
                ApiCallBack<ViewTransaction_Respomse>(object :
                    ApiResponseListener<ViewTransaction_Respomse> {
                    override fun onApiSuccess(
                        response: ViewTransaction_Respomse,
                        apiName: String?
                    ) {
                        if (response.responseCode == 200) {
                            try {

                                TransactionTV.text = response.result.trxId
                                PaymentMode.text = response.result.transactionMethod

//                                amount.text = "R ${CommonFunctions.currencyFormatter(myOrderPrice.toDouble())}"
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }


                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
//
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    }

                }, "ViewTransactionApi", requireContext())


            try {
                serviceManager.ViewTransactionApi(callBack, order_id)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            llcustomerordersview.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }
    }

    fun MarkAsDoneProductApi() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar_orderview.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Any> =
                ApiCallBack<Any>(object :
                    ApiResponseListener<Any> {
                    override fun onApiSuccess(response: Any, apiName: String?) {
                        progressbar_orderview.visibility = View.GONE


                        try {
                            val intent = Intent(activity, OtpVerification::class.java)
                            intent.putExtra("email", Email.text.toString())
                            intent.putExtra("flag_order_id", order_id)
                            intent.putExtra("flag", "OrderManagement")
                            activity?.startActivity(intent)
                            parentFragmentManager.popBackStack()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_orderview.visibility = View.GONE
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()
                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, requireContext())

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar_orderview.visibility = View.GONE
                    }

                }, "MarkAsDoneProductApi", mContext)

            var jsonObject = JsonObject()
            jsonObject.addProperty("orderId", order_id)

            try {
                serviceManager.markAsDoneProductApi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            llcustomerordersview.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }
    }

    fun setApapter(data: ArrayList<RetailerOrderManagementMyOrder>?) {
        OderDetails.layoutManager = LinearLayoutManager(context)
        var adapter = data?.let { ProductDetailsAdapter(requireContext(), it) }
        OderDetails.adapter = adapter
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
        title.text = "Order Details"
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    private fun getValues() {
        order_id = requireArguments().getString("productId").toString()
        flag = requireArguments().getString("flag").toString()

        if (flag == "PENDING") {
            save_button.visibility = View.VISIBLE
        } else {
            save_button.visibility = View.GONE
        }

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
        orderTrackingAdapter = OrderTrackingAdapter(requireContext(), orderTracking,flag)
        orderTrackingRecyclerView.adapter = orderTrackingAdapter
    }



}