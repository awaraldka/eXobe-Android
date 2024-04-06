package com.exobe.activities

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.fragments.PendingOrder
import com.exobe.fragments.allServices.CompletedServices
import com.exobe.fragments.allServices.PendingServices
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.bottomSheet.CustomerBottomSheet
import com.exobe.customClicks.NavigationClick
import com.exobe.customClicks.UpdateIsLoginListener
import com.exobe.dialogs.productDialog
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.*
import com.exobe.entity.response.serviceTab.VerifyOtpServicesResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.extension.displayToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.exobe.utils.LocationClass
import com.exobe.utils.Progresss
import com.exobe.activities.services.ServicePage


class OtpVerification : AppCompatActivity(), ApiResponseListener<otpverfication_response> , UpdateIsLoginListener,
    NavigationClick {

    lateinit var et1: EditText
    lateinit var et2: EditText
    lateinit var et3: EditText
    lateinit var et4: EditText
    lateinit var textView: TextView
    lateinit var title: TextView
    lateinit var submit: Button
    lateinit var Back: LinearLayout
    lateinit var ResendCode: TextView

    lateinit var OtpScreen: LinearLayout
    var flag = ""
    lateinit var mContext: Context
    var email = ""
    var flag1 = ""
    var service_view_orderid = ""
    lateinit var otp_progress_bar: ProgressBar
    var getUserType = ""
    var userType = ""
    var userRole = ""

    var retailerId = ""
    var orderIdRequest = ""
    var fieldEntityId = ""
    var customerId = ""
    var userFor = ""


    var lat = 0.0
    var long = 0.0
    var startLat = 0.0
    var startLong = 0.0
    private var fusedLocationClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)
        window.attributes.windowAnimations = R.style.Fade
        mContext = this.applicationContext!!

        fusedLocationClient = this.let { LocationServices.getFusedLocationProviderClient(it) }
        otp_progress_bar = findViewById(R.id.otp_progress_bar)
        et1 = findViewById(R.id.et_1)
        et2 = findViewById(R.id.et_2)
        et3 = findViewById(R.id.et_3)
        et4 = findViewById(R.id.et_4)
        textView = findViewById(R.id.textView)
        Back = findViewById(R.id.Back)
        submit = findViewById(R.id.submit)
        title = findViewById(R.id.title)
        ResendCode = findViewById(R.id.ResendCode)

        OtpScreen = findViewById(R.id.OtpScreen)
        getIntentData()
        LocationClass.getLocation(this, fusedLocationClient!!) { locationData ->
            if (locationData != null) {
                 startLat = locationData.latitude
                startLong = locationData.longitude

            }
        }

        intent?.getStringExtra("userRole")?.let { userRole = it }


        when(userFor){
            "Delivered To FE", "Field Entity" -> { // Message when otp send to Field Entity
                title.text = getString(R.string.otp_fe)
            }

            "Customer" -> { // Message when otp send to Field Entity
                title.text = getString(R.string.otp_customer)
            }
            "Retailer" -> { // Message when otp send to retailer
                title.text = getString(R.string.otp_retailer)
            }

        }






        Back.setSafeOnClickListener {
            finish()
        }


        et1.addTextChangedListener(GenericTextWatcher(et1, et2))
        et2.addTextChangedListener(GenericTextWatcher(et2, et3))
        et3.addTextChangedListener(GenericTextWatcher(et3, et4))
        et4.addTextChangedListener(GenericTextWatcher(et4, null))


        et1.setOnKeyListener(GenericKeyEvent(et1, null))
        et2.setOnKeyListener(GenericKeyEvent(et2, et1))
        et3.setOnKeyListener(GenericKeyEvent(et3, et2))
        et4.setOnKeyListener(GenericKeyEvent(et4, et3))


        submit.setSafeOnClickListener {
            if (et1.text.isEmpty()) {
                androidextention.alertBox("Please enter otp", this)

            } else if (et2.text.isEmpty()) {
                androidextention.alertBox("Please enter valid otp", this)
            } else if (et3.text.isEmpty()) {
                androidextention.alertBox("Please enter valid otp", this)
            } else if (et4.text.isEmpty()) {
                androidextention.alertBox("Please enter valid otp", this)
            } else {
                val et1 = et1.text.toString()
                val et2 = et2.text.toString()
                val et3 = et3.text.toString()
                val et4 = et4.text.toString()

                val otp = "${et1}${et2}${et3}${et4}"

                when (flag) {
                    "ServiceManagement" -> {
                        otpAfterService(otp)
                    }
                    "OrderManagement" -> {
                        otpProductDeliveryDone(otp)
                    }
                    "Service provider" -> {
                        verifyOtpServicesApi(otp = otp)
                    }
                    else -> {
                        otpVerification(otp, email)

                    }
                }


            }

        }


        ResendCode.setSafeOnClickListener {
            val et1 = et1.text.toString()
            val et2 = et2.text.toString()
            val et3 = et3.text.toString()
            val et4 = et4.text.toString()

            val otp = "${et1}${et2}${et3}${et4}"

            when (flag) {
                "ServiceManagement" -> {
                    markAsDoneApi()
                }
                "OrderManagement" -> {
                    markAsDoneProductApi()
                }
                "Service provider" -> {
                    resendOtpServicesApi()
                }
                else -> {
                    resendOtpAPI(otp, email)
                }
            }

            countdown()
        }

        if (flag == "ServiceManagement" || flag == "OrderManagement") {
            title.text =
//                "Please enter the 4-digit verification code that was sent to customer email. The code is valid for 1 minute."
                "The verification code has been sent via email to $email."
        }




        countdown()


    }

    private fun getIntentData() {
        if (intent != null) {
            if (intent?.getStringExtra("email") != null) {
                email = intent?.getStringExtra("email")!!
            }
            if (intent?.getStringExtra("flag") != null) {
                flag = intent?.getStringExtra("flag")!!
            }

            if (intent?.getStringExtra("flag1") != null) {
                flag1 = intent?.getStringExtra("flag1")!!
            }
            if (intent?.getStringExtra("flag_order_id") != null) {
                service_view_orderid = intent?.getStringExtra("flag_order_id")!!
            }
            if (intent?.getStringExtra("usertype") != null) {
                getUserType = intent?.getStringExtra("usertype")!!
            }

            if (flag == "Service provider"){
                intent.getStringExtra("retailerId")?.let { retailerId = it }
                intent.getStringExtra("fieldEntityId")?.let { fieldEntityId = it }
                intent.getStringExtra("viewId")?.let { orderIdRequest = it  }
                intent.getStringExtra("customerId")?.let { customerId = it  }
                intent.getStringExtra("userFor")?.let { userFor = it  }
            }



        }
    }

    private fun countdown() {
        val countdownTimeInMillis = 3 * 60 * 1000 // 1 minute in milliseconds

        object : CountDownTimer(countdownTimeInMillis.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                val formattedTime = String.format("%02d:%02d", minutes, seconds)
                textView.text = formattedTime
                ResendCode.isEnabled = false
                ResendCode.setTextColor(ContextCompat.getColor(this@OtpVerification,R.color.grey))
            }

            override fun onFinish() {
                // Timer has finished, implement your actions here
                textView.text = "00:00"
                ResendCode.isEnabled = true
                ResendCode.setTextColor(ContextCompat.getColor(this@OtpVerification,R.color.black))
                // Do something when the timer finishes, like enabling a button or showing a message.
            }
        }.start()
    }


    private fun otpVerification(otp: String, email: String) {

        if (androidextention.isOnline(this)) {
            otp_progress_bar.visibility = View.VISIBLE

            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<otpverfication_response> =
                ApiCallBack(this, "otpverfication", mContext)

            val jsonObject = JsonObject()
            jsonObject.addProperty("otp", otp)
            jsonObject.addProperty("email", email)
            jsonObject.addProperty("userType", getUserType)


            try {
                serviceManager.otpverficationApi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection.", this)

        }
    }

    override fun onApiSuccess(response: otpverfication_response, apiName: String?) {
        otp_progress_bar.visibility = View.GONE

        if (response.responseCode == 200) {
            try {
                val getUserType = SavedPrefManager.getStringPreferences(this, SavedPrefManager.USER_TYPE)

                if (flag1 == "customer_login") {
                    finish()
                    if (getUserType != null) {
                        showCustomerBottomSheet(getUserType)
                    }
                } else {
                    val intent = Intent(this, ResetPassword::class.java)
                    intent.putExtra("email", email)
                    intent.putExtra("UserType", getUserType)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    override fun onApiErrorBody(response: String, apiName: String?) {
        otp_progress_bar.visibility = View.GONE
        val gson = GsonBuilder().create()
        var pojo = response_modal_class()

        try {
            pojo = gson.fromJson(response, pojo::class.java)
            androidextention.alertBox(pojo.responseMessage, this)

        } catch (e: Exception) {
            // handle failure at error parse
        }
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        otp_progress_bar.visibility = View.GONE
    }

    private fun showCustomerBottomSheet(userType: String) {
        val customerType = when (userType) {
            "CUSTOMER" -> "Customer"
            "RETAILER" -> "Retailer"
            "SERVICE_PROVIDER" -> "Service_Provider"
            else -> return
        }

        CustomerBottomSheet(customerType, this,this).show(supportFragmentManager, "ModalBottomSheet")
    }

    private fun resendOtpAPI(otp: String, email: String) {

        if (androidextention.isOnline(this)) {
            otp_progress_bar.visibility = View.VISIBLE

            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<ResendOtp> =
                ApiCallBack<ResendOtp>(object : ApiResponseListener<ResendOtp> {
                    override fun onApiSuccess(response: ResendOtp, apiName: String?) {
                        otp_progress_bar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                ResendCode.setTextColor(resources.getColor(R.color.grey))
                                supportFragmentManager.let {
                                    productDialog(
                                        "OTP Resent \nSuccessfully",
                                        "OPPRESSED"
                                    ).show(it, "MyCustomFragment")
                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        otp_progress_bar.visibility = View.GONE
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(
                                pojo.responseMessage,
                                this@OtpVerification
                            )

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        otp_progress_bar.visibility = View.GONE
                    }
                }, "ResendOtpAPI", mContext)

            val jsonObject = JsonObject()
            jsonObject.addProperty("email", email)
            jsonObject.addProperty("userType", getUserType)



            try {
                serviceManager.ResendOtpApi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection.", this)

        }
    }


    private fun otpAfterService(otp: String) {
        if (androidextention.isOnline(mContext)) {
            otp_progress_bar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<MarkAsDoneOtpResponse> =
                ApiCallBack<MarkAsDoneOtpResponse>(object :
                    ApiResponseListener<MarkAsDoneOtpResponse> {

                    override fun onApiSuccess(
                        response: MarkAsDoneOtpResponse,
                        apiName: String?
                    ) {
                        if (response.responseCode == 200) {
                            try {
                                otp_progress_bar.visibility = View.GONE
                                Toast.makeText(this@OtpVerification, "Otp Verify Successfully", Toast.LENGTH_SHORT).show()
                                PendingServices.apiPendingServiceCallFlag = true
                                CompletedServices.apiCompleteServiceCallFlag = true

                                finish()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        otp_progress_bar.visibility = View.GONE


                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@OtpVerification)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        otp_progress_bar.visibility = View.GONE


                    }
                }, "OtpAfterService", mContext)

            val jsonObject = JsonObject()
            jsonObject.addProperty("orderId", service_view_orderid)
            jsonObject.addProperty("otpService", otp)


            try {
                serviceManager.service_requestapi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {

            androidextention.alertBox("Please check your internet connection.", this)

        }
    }

    private fun otpProductDeliveryDone(otp: String) {
        if (androidextention.isOnline(mContext)) {
            otp_progress_bar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<MarkAsDoneOtpResponse> =
                ApiCallBack<MarkAsDoneOtpResponse>(object :
                    ApiResponseListener<MarkAsDoneOtpResponse> {

                    override fun onApiSuccess(
                        response: MarkAsDoneOtpResponse,
                        apiName: String?
                    ) {
                        if (response.responseCode == 200) {

                            try {
                                otp_progress_bar.visibility = View.GONE
                                Toast.makeText(this@OtpVerification, "Otp Verify Successfully", Toast.LENGTH_SHORT).show()
                                PendingOrder.apiCallFlag = true
                                finish()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        otp_progress_bar.visibility = View.GONE

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@OtpVerification)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        otp_progress_bar.visibility = View.GONE


                    }
                }, "productDeliveredApi", mContext)

            val jsonObject = JsonObject()
            jsonObject.addProperty("orderId", service_view_orderid)
            jsonObject.addProperty("otpProduct", otp)


            try {
                serviceManager.productDeliveredApi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else{
            androidextention.alertBox("Please check your internet connection.", this)
        }
    }



    private fun markAsDoneApi() {
        if (androidextention.isOnline(this)) {
            otp_progress_bar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<MarkAsDone_Response> =
                ApiCallBack<MarkAsDone_Response>(object :
                    ApiResponseListener<MarkAsDone_Response> {
                    override fun onApiSuccess(
                        response: MarkAsDone_Response,
                        apiName: String?
                    ) {

                        if (response.responseCode == 200) {
                            try {
                                otp_progress_bar.visibility = View.GONE

                                ResendCode.setTextColor(resources.getColor(R.color.grey))
                                supportFragmentManager.let {
                                    productDialog(
                                        "OTP Resent \nSuccessfully",
                                        "OPPRESSED"
                                    ).show(it, "MyCustomFragment")
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        otp_progress_bar.visibility = View.GONE
                        androidextention.disMissProgressDialog(this@OtpVerification)


                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@OtpVerification)

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        otp_progress_bar.visibility = View.GONE
                    }

                }, "MarkAsDoneApi", this)

            val jsonObject = JsonObject()
            jsonObject.addProperty("orderId", service_view_orderid)
            try {
                serviceManager.MarkAsDoneapi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }

    private fun markAsDoneProductApi() {
        if (androidextention.isOnline(mContext)) {
            otp_progress_bar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<Any> =
                ApiCallBack<Any>(object :
                    ApiResponseListener<Any> {
                    override fun onApiSuccess(response: Any, apiName: String?) {
                        otp_progress_bar.visibility = View.GONE
                        try {
                            ResendCode.setTextColor(resources.getColor(R.color.grey))
                            supportFragmentManager.let {
                                productDialog(
                                    "OTP Resent \nSuccessfully",
                                    "OPPRESSED"
                                ).show(it, "MyCustomFragment")
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        otp_progress_bar.visibility = View.GONE
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()
                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@OtpVerification)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        otp_progress_bar.visibility = View.GONE
                    }

                }, "MarkAsDoneProductApi", mContext)

            val jsonObject = JsonObject()
            jsonObject.addProperty("orderId", service_view_orderid)

            try {
                serviceManager.markAsDoneProductApi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)
        }
    }

    override fun isLoginListener() {
    }


    private fun resendOtpServicesApi() {
        if (androidextention.isOnline(this)) {
            Progresss.start(this)
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<CommonResponseForAll> =
                ApiCallBack(object : ApiResponseListener<CommonResponseForAll> {
                    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
                    override fun onApiSuccess(response: CommonResponseForAll, apiName: String?) {

                        if (response.responseCode == 200) {
                            try {
                                Progresss.stop()
                                displayToast(response.responseMessage)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        Progresss.stop()
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@OtpVerification)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, apiName = "resendOtpApi", this)


            try {

                when(userRole){
                    "DELIVERY_PARTNER" -> {
                        if (userFor == "Field Entity"){  // Delivery partner is picked up item from field entity
                            serviceManager.sendOtpApi(callBack, _id =orderIdRequest, retailerId = fieldEntityId)
                        }else{ // Delivered to Customer
                            serviceManager.sendOtpApi(callBack, _id =orderIdRequest, retailerId = customerId)
                        }

                    }
                    "FIELD_ENTITY" -> {
                        serviceManager.sendOtpApi(callBack, _id =orderIdRequest, retailerId = retailerId)
                    }
                    "PICKUP_PARTNER" -> {
                        if (userFor =="Retailer"){
                            serviceManager.sendOtpApi(callBack, _id =orderIdRequest, retailerId = retailerId)
                        }else{
                            serviceManager.sendOtpApi(callBack, _id =orderIdRequest, retailerId = fieldEntityId)

                        }

                    }
                }




            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }
    private fun verifyOtpServicesApi(otp:String) {
        if (androidextention.isOnline(this)) {
            Progresss.start(this)
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<VerifyOtpServicesResponse> =
                ApiCallBack(object : ApiResponseListener<VerifyOtpServicesResponse> {
                    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
                    override fun onApiSuccess(response: VerifyOtpServicesResponse, apiName: String?) {
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            try {
                                ServicePage.isRefreshed = true
                                when(userRole){
                                    "DELIVERY_PARTNER" -> {
                                        if (userFor =="Customer"){

                                            androidextention.alertBoxFinish(message = response.responseMessage, context = this@OtpVerification, activity = this@OtpVerification)

                                        }else{
                                            with(response.result.storeLocation!!.coordinates){
                                                lat = this?.getOrNull(1)!!
                                                long = this.getOrNull(0)!!
                                            }

                                            androidextention.alertBoxNavigation(message = response.responseMessage, context = this@OtpVerification, click = this@OtpVerification)


                                        }



                                    }
                                    "FIELD_ENTITY" -> {

                                        androidextention.alertBoxFinish(message = response.responseMessage, context = this@OtpVerification, activity = this@OtpVerification)
                                    }
                                    "PICKUP_PARTNER" -> {
                                        if (userFor =="Delivered To FE"){

                                            finishAfterTransition()
                                        }else{
                                            with(response.result.storeLocation!!.coordinates){
                                                lat = this?.getOrNull(1)!!
                                                long = this.getOrNull(0)!!
                                            }

                                            androidextention.alertBoxNavigation(message = response.responseMessage, context = this@OtpVerification, click = this@OtpVerification)

                                        }

                                    }
                                }






                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        Progresss.stop()
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@OtpVerification)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, apiName = "verifyOtpServicesApi", this)


            try {




                when(userRole){
                    "DELIVERY_PARTNER" -> {
                        if (userFor == "Field Entity"){  // Delivery partner is picked up item from field entity
                            serviceManager.verifyOtpServiceApi(callBack, _id =orderIdRequest, retailerId = "", fieldEntityId = fieldEntityId,otp = otp, customerId = "")
                        }else{ // Delivered to Customer
                            serviceManager.verifyOtpServiceApi(callBack, _id =orderIdRequest, retailerId = "", fieldEntityId = "",otp = otp, customerId = customerId)
                        }

                    }
                    "FIELD_ENTITY" -> {
                        serviceManager.verifyOtpServiceApi(callBack, _id =orderIdRequest, retailerId = retailerId, fieldEntityId = fieldEntityId,otp = otp,customerId = "")
                    }
                    "PICKUP_PARTNER" -> {
                     serviceManager.verifyOtpServiceApi(callBack, _id =orderIdRequest, retailerId = retailerId, fieldEntityId = fieldEntityId,otp = otp, customerId ="")
                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }

    override fun navigateToMap() {
        finishAfterTransition()
        val srcAdd = "&origin=$startLat,$startLong"
        val desAdd = "&destination=$lat,$long"
        val link = "https://www.google.com/maps/dir/?api=1&travelmode=driving$srcAdd$desAdd"
        Log.e("Url Polyline?", link)
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        } catch (ane: ActivityNotFoundException) {
            Toast.makeText(this, "Please Install Google Maps ", Toast.LENGTH_LONG).show()
        } catch (ex: java.lang.Exception) {
            ex.message
        }


    }

    override fun navigateToHome() {
        finishAfterTransition()
    }


}