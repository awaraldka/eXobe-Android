package com.exobe.fragments.allServices

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.activities.OtpVerification
import com.exobe.adaptor.servicesAdaptor.ViewServiceAdaptor
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.LocationClass
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.CommonResponseForAll
import com.exobe.entity.response.MarkAsDone_Response
import com.exobe.entity.response.ViewTransaction_Respomse
import com.exobe.entity.response.serviceTab.ServiceListViewPurchaseService
import com.exobe.entity.response.serviceTab.ServiceListViewResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.utils.Progresss
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject


class ServiceViewFragment : Fragment(

) {
    lateinit var serviceViewLL: LinearLayout
    lateinit var title: TextView
    lateinit var Total_TV: TextView
    lateinit var subTotal: TextView
    lateinit var taxPrice: TextView
    lateinit var TransactionTV: TextView

    lateinit var customername: TextView
    lateinit var Email: TextView
    lateinit var Phone: TextView
    lateinit var address: TextView
    lateinit var map: TextView
    var latitude: String = ""
    var longitude: String = ""
    lateinit var back: ImageView
    lateinit var markAsDone: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var transaction: LinearLayout
    lateinit var progressbar_serviceView_SP: ProgressBar
    var menuClick: ImageView? = null
    var order_id: String = ""
    var flag = ""
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    var startLong = ""
    var startLat = ""

    var manageServiceForTracking = ""




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_service_view, container, false)
        serviceViewLL = view.findViewById(R.id.serviceViewLL)
        taxPrice = view.findViewById(R.id.taxPrice)
        subTotal = view.findViewById(R.id.subTotal)
        recyclerView = view.findViewById(R.id.services_rv)
        markAsDone = view.findViewById(R.id.markAsDone)
        TransactionTV = view.findViewById(R.id.TransactionTV)
        Total_TV = view.findViewById(R.id.Total_TV)
        transaction = view.findViewById(R.id.transaction)
        title = activity?.findViewById(R.id.title)!!
        back = activity?.findViewById(R.id.back)!!
        menuClick = activity?.findViewById(R.id.MenuClick)!!
        title.text = "Service Details"
        back.visibility = View.VISIBLE
        menuClick!!.visibility = View.GONE
        progressbar_serviceView_SP = view.findViewById(R.id.progressbar_serviceView_SP)
        map = view.findViewById(R.id.map)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!

        customername = view.findViewById(R.id.customername)!!
        Email = view.findViewById(R.id.Email)!!
        Phone = view.findViewById(R.id.Phone)!!
        address = view.findViewById(R.id.address)!!
        LocationClass.displayLocationSettingsRequest(requireContext())
        getCurrentLocation()

        if (requireArguments().getString("flag") != null) {
            flag = requireArguments().getString("flag").toString()

        }

        if (requireArguments().getString("id") != null) {
            order_id = requireArguments().getString("id").toString()

        }
        if (requireArguments().getString("flag") != null) {
            flag = requireArguments().getString("flag").toString()

        }
        if (flag == "PENDING") {
            transaction.visibility = View.GONE
        } else {
            markAsDone.visibility = View.GONE
            TransactionTV.text = ""
        }



        markAsDone.setSafeOnClickListener {
            when(manageServiceForTracking){
                "PENDING" -> {
                    updateStatus("BOOKING_RECEIVED")
                }
                "BOOKING_RECEIVED" -> {
                    updateStatus("ONTHEWAY")
                }
                "ONTHEWAY" -> {
                    markAsDoneApi()
                }
                "EXECUTION" -> {
                    markAsDoneApi()
                }
            }

        }

        back.setSafeOnClickListener {
            parentFragmentManager.popBackStack()
        }


        map.setSafeOnClickListener {

            val srcAdd = "&origin=$startLat,$startLong"
            val desAdd = "&destination=$longitude,$latitude"
            val link = "https://www.google.com/maps/dir/?api=1&travelmode=driving$srcAdd$desAdd"
            Log.e("Url Polyline?", link)
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)
            } catch (ane: ActivityNotFoundException) {
                Toast.makeText(context, "Please Install Google Maps ", Toast.LENGTH_LONG).show()
            } catch (ex: java.lang.Exception) {
                ex.message
            }

        }
        viewServiceApi()
        return view
    }

    private fun viewServiceApi() {
        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar_serviceView_SP.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<ServiceListViewResponse> =
                ApiCallBack(object :
                    ApiResponseListener<ServiceListViewResponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onApiSuccess(
                        response: ServiceListViewResponse,
                        apiName: String?
                    ) {
                        progressbar_serviceView_SP.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                serviceViewLL.isVisible = true

                                val responseData =  response.result

                                val (markAsDoneText, manageServiceText) = when(responseData.serviceStatus) {
                                    "PENDING" -> {
                                        Pair("Booking Received", "PENDING")
                                    }
                                    "BOOKING_RECEIVED" -> {
                                        Pair("Mark as On The way", "BOOKING_RECEIVED")
                                    }
                                    "ONTHEWAY" -> {
                                        Pair("Start Service", "ONTHEWAY")
                                    }
                                    "EXECUTION" -> {
                                        Pair("Mark as Done", "EXECUTION")
                                    }
                                    else -> {
                                        Pair("", "")
                                    }
                                }


                                markAsDone.text= markAsDoneText
                                manageServiceForTracking =  manageServiceText









                                if (flag == "PENDING") {

                                }

                                val servicesList =  responseData.purchaseServices

                                if (flag == "ACCEPTED") {
                                    viewTransactionApi()
                                }

                                customername.text =  responseData.userId.firstName + " " + responseData.userId.lastName
                                Email.text = responseData.userId.email
                                Phone.text = responseData.userId.mobileNumber
                                val fullAddress = StringBuffer()
                                if ( responseData.shippingFixedAddress.addressLine1 != "") {
                                    fullAddress.append("${ responseData.shippingFixedAddress.addressLine1},")
                                }

                                if( responseData.shippingFixedAddress.city != "") {
                                    fullAddress.append("${ responseData.shippingFixedAddress.city},")
                                }
                                if( responseData.shippingFixedAddress.state != "") {
                                    fullAddress.append("${ responseData.shippingFixedAddress.state},")
                                }
                                if( responseData.shippingFixedAddress.country != "") {
                                    fullAddress.append("${ responseData.shippingFixedAddress.country},")
                                }
                                if( responseData.shippingFixedAddress.zipCode != "") {
                                    fullAddress.append( responseData.shippingFixedAddress.zipCode)
                                }

                                address.text="$fullAddress"

                                Total_TV.text =
                                    "R ${CommonFunctions.currencyFormatter( responseData.orderPrice.toDouble())}"
                                taxPrice.text = "R ${CommonFunctions.currencyFormatter( responseData.taxPrice.toDouble())}"
                                subTotal.text = "R ${CommonFunctions.currencyFormatter( responseData.actualPrice.toDouble())}"
                                latitude =  responseData.shippingFixedAddress.location.coordinates[0].toString()
                                longitude =  responseData.shippingFixedAddress.location.coordinates[1].toString()


                                setAdapter(servicesList)


                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_serviceView_SP.visibility = View.GONE
                        serviceViewLL.isVisible = false

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, requireContext())

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar_serviceView_SP.visibility = View.GONE
                        serviceViewLL.isVisible = false
                    }

                }, "OrderListApi", requireContext())


            try {
                serviceManager.orderlistViewapi(callBack, order_id)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    private fun viewTransactionApi() {
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
                        progressbar_serviceView_SP.visibility = View.GONE

                        if (response.responseCode == 200) {

                            try {
                                TransactionTV.text = response.result.trxId

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }


                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_serviceView_SP.visibility = View.GONE


                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
//                            androidextention.alertBox(pojo.responseMessage, requireContext())

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar_serviceView_SP.visibility = View.GONE

                    }

                }, "ViewTransactionApi", requireContext())


            try {
                serviceManager.ViewTransactionApi(callBack, order_id)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }


    private fun markAsDoneApi() {
        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar_serviceView_SP.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<MarkAsDone_Response> =
                ApiCallBack<MarkAsDone_Response>(object :
                    ApiResponseListener<MarkAsDone_Response> {
                    override fun onApiSuccess(
                        response: MarkAsDone_Response,
                        apiName: String?
                    ) {
                        progressbar_serviceView_SP.visibility = View.GONE

                        if (response.responseCode == 200) {
                            try {

                                val intent = Intent(activity, OtpVerification::class.java)
                                intent.putExtra("email", Email.text.toString())
                                intent.putExtra("flag_order_id", order_id)
                                intent.putExtra("flag", "ServiceManagement")
                                activity?.startActivity(intent)
                                fragmentManager?.popBackStack()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_serviceView_SP.visibility = View.GONE
                        androidextention.disMissProgressDialog(requireContext())


                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, requireContext())

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar_serviceView_SP.visibility = View.GONE
                    }

                }, "MarkAsDoneApi", requireContext())

            var jsonObject = JsonObject()
            jsonObject.addProperty("orderId", order_id)
            try {
                serviceManager.MarkAsDoneapi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }
    private fun updateStatus(status:String) {
        if (androidextention.isOnline(requireContext())) {
            Progresss.start(requireContext())
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<CommonResponseForAll> =
                ApiCallBack(object :
                    ApiResponseListener<CommonResponseForAll> {
                    override fun onApiSuccess(
                        response: CommonResponseForAll,
                        apiName: String?
                    ) {
                        Progresss.stop()

                        if (response.responseCode == 200) {
                            viewServiceApi()
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        Progresss.stop()
                        androidextention.disMissProgressDialog(requireContext())


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
                        Progresss.stop()
                    }

                }, "updateStatus", requireContext())

            try {
                serviceManager.updateServiceStatusApi(callBack, status=status, orderId = order_id)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            internet_connection.visibility = View.VISIBLE

        }
    }


    fun setAdapter(data: ArrayList<ServiceListViewPurchaseService>?) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        var viewServiceAdaptor = data?.let { ViewServiceAdaptor(requireContext(), it) }
        recyclerView.adapter = viewServiceAdaptor
    }

    fun getCurrentLocation() {
        try {
            val locationRequest = LocationRequest()
            locationRequest.interval = 10000
            locationRequest.fastestInterval = 3000
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            LocationServices.getFusedLocationProviderClient(requireContext())
                .requestLocationUpdates(locationRequest, object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)
                        try {
                            LocationServices.getFusedLocationProviderClient(requireActivity())
                                .removeLocationUpdates(this)
                            if (locationResult != null && locationResult.locations.size > 0) {
                                val latestlocIndex = locationResult.locations.size - 1
                                val lati = locationResult.locations[latestlocIndex].latitude
                                val longi = locationResult.locations[latestlocIndex].longitude
                                startLong = longi.toString()
                                startLat = lati.toString()

                            } else {

                            }
                        } catch (e : Exception) {
                            e.printStackTrace()
                        } catch (e : IllegalStateException) {
                            e.printStackTrace()
                        }

                    }
                }, Looper.getMainLooper())
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}