package com.exobe.activities.services

import  android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.activities.OtpVerification
import com.exobe.adaptor.servicesAdaptor.ViewDetailsOfServicesAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.LocationClass
import com.exobe.utils.Progresss
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.OrderProcessClick
import com.exobe.customClicks.OutForDelivery
import com.exobe.customClicks.RejectOrderClick
import com.exobe.databinding.ActivityServiceCommonViewBinding
import com.exobe.dialogs.RejectOrders
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.FeedBackData
import com.exobe.entity.request.FeedBackDataRequest
import com.exobe.entity.response.CommonResponseForAll
import com.exobe.entity.response.FeedbackResponse
import com.exobe.entity.response.serviceTab.Retailers
import com.exobe.entity.response.serviceTab.ViewAssignedOrderResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.util.DateFormat
import com.exobe.utils.SavedPrefManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.GsonBuilder

class ServiceCommonViewActivity : AppCompatActivity(), OrderProcessClick ,RejectOrderClick,OutForDelivery{

    private lateinit var binding: ActivityServiceCommonViewBinding

    var data = ArrayList<Retailers>()

    var orderIdRequest = ""
    var orderId = ""
    var fieldEntityId = ""
    private var isFrom = ""
    var customerId = ""

    var isOrderPickedUpFromFE = false

    private var fusedLocationClient: FusedLocationProviderClient? = null

    var lat = 0.0
    var long = 0.0
    var latCustomer = 0.0
    var longCustomer = 0.0
    private var startLat = 0.0
    private var startLong = 0.0

    var userRole = ""
    var isOrderOutForDelivery = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceCommonViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.attributes.windowAnimations = R.style.Fade
        fusedLocationClient = this.let { LocationServices.getFusedLocationProviderClient(it) }

        binding.backButtonClick.setOnClickListener {
            finishAfterTransition()
        }

        intent.getStringExtra("_id")?.let{ orderIdRequest = it}
        intent.getStringExtra("orderId")?.let{ orderId = it}
        intent.getStringExtra("isFrom")?.let{ isFrom = it}
        intent.getStringExtra("userType")?.let{ userRole = it}

        binding.titleText.text = orderId



        when(userRole){
            "DELIVERY_PARTNER" -> {
                if (isFrom =="Rejected"){
                    binding.findRoute.isVisible = false
                }

                binding.CustomerView.isVisible = true
                binding.llCustomer.isVisible = true
                binding.orderDetailsRecycler.isVisible = false
            }
            "FIELD_ENTITY" -> {
                binding.llAddDeals.isVisible = true
                binding.viewFieldEntity.isVisible = true
                binding.findRoute.isVisible = false
                binding.CustomerView.isVisible = false
                binding.llCustomer.isVisible = false
                binding.orderDetailsRecycler.isVisible = true
                if (isFrom =="PENDING"){
                    binding.OutForDelivery.isVisible = true
                }

            }
            "PICKUP_PARTNER" -> {
                if (isFrom =="Rejected"){
                    binding.findRoute.isVisible = false
                }


                binding.CustomerView.isVisible = false
                binding.llCustomer.isVisible = false
                binding.orderDetailsRecycler.isVisible = true
            }
        }



        if (isFrom == "COMPLETED"){
            getFeedBackByOrderIdApi()
            binding.ratingLL.isVisible = true


            when(userRole){
                "DELIVERY_PARTNER" -> {
                    binding.titleRateFirstTV.text = "Rate Customer Experience"
                    binding.titleRateSecondTV.text = "Rate Fulfilment Experience"
                }
                "FIELD_ENTITY" -> {
                    binding.titleRateFirstTV.text = "Rate Pickup Driver Experience"
                    binding.titleRateSecondTV.text = "Rate Delivery Driver Experience"
                }
                "PICKUP_PARTNER" -> {
                    binding.titleRateFirstTV.text = "Rate Retailer Experience"
                    binding.titleRateSecondTV.text = "Rate Fulfilment Experience"
                }
            }
            
        }
        
        binding.addFeedback.setSafeOnClickListener {
            if (binding.ratingBar.rating.toDouble().equals(0.0) || binding.feedbackFirstEt.text.toString().isEmpty()){
                if (userRole.uppercase()=="DELIVERY_PARTNER"){
                    androidextention.alertBox("Please give rating to customer",this)
                }else if (userRole.uppercase()=="FIELD_ENTITY"){
                    androidextention.alertBox("Please give rating to pickup driver",this)
                }else {
                    androidextention.alertBox("Please give rating to retailer",this)
                }
            }else if (binding.ratingBarSecond.rating.toDouble().equals(0.0) || binding.feedbackSecondEt.text.toString().isEmpty()){
                if (userRole.uppercase()=="DELIVERY_PARTNER"){
                    androidextention.alertBox("Please give rating to fulfilment",this)
                }else if (userRole.uppercase()=="FIELD_ENTITY"){
                    androidextention.alertBox("Please give rating to delivery driver",this)
                }else {
                    androidextention.alertBox("Please give rating to fulfilment",this)
                }
            }else{
                addFeedbackApi()
            }
        }


        



        LocationClass.getLocation(this, fusedLocationClient!!) { locationData ->
            if (locationData != null) {
                startLat = locationData.latitude
                startLong = locationData.longitude

            }
        }


        binding.rejectButton.setOnClickListener {
            RejectOrders(this, this).show(supportFragmentManager,"RejectOrders")

        }


        binding.findRoute.setSafeOnClickListener {
            openMapRoute(lat=lat,long=long)
        }

        binding.findRouteCustomerCustomer.setSafeOnClickListener {
            openMapRoute(lat=latCustomer,long=longCustomer)
        }


        binding.ProceedButton.setSafeOnClickListener {
            if (!isOrderPickedUpFromFE){
                sendOtpApi(retailerId = fieldEntityId, userFor = "Field Entity")
            }else{
                sendOtpApi(retailerId =customerId, userFor = "Customer")
            }

        }


        binding.OutForDelivery.setSafeOnClickListener {
            if (isOrderOutForDelivery){
                reAssignOrderApi()
            }else{
                outForDeliveryApi()
            }

        }





    }


    private fun openMapRoute(lat:Double,long: Double) {
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

    override fun onStart() {
        super.onStart()
        viewAllOrdersApi()
    }


    private fun viewAllOrdersApi() {
        if (androidextention.isOnline(this)) {
            binding.shimmerFrameLayout.isVisible = true
            binding.shimmerFrameLayout.startShimmerAnimation()
            binding.nestedScroll.isVisible = false
            
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<ViewAssignedOrderResponse> =
                ApiCallBack(object : ApiResponseListener<ViewAssignedOrderResponse> {
                    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
                    override fun onApiSuccess(response: ViewAssignedOrderResponse, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.nestedScroll.isVisible = true
                        binding.shimmerFrameLayout.isVisible = false
                        if (response.responseCode == 200) {
                            try {
                                with(response.result)  {
                                    binding.orderId.text =  "Order Id: $orderId"
                                    binding.oderDate.text =  "Order Date: ${DateFormat.getDateAndTime(createdAt)}"
                                    data= retailers
                                    setAdaptor()



                                    when (userRole) {
                                        "FIELD_ENTITY" -> {
                                            binding.rejectButtonLayout.isInvisible = true
                                        }
                                        "DELIVERY_PARTNER" -> {
                                            isOrderPickedUpFromFE = isOrderPickedUpFromFieldEntity
                                            if (requestStatus =="PENDING" && !isOrderPickedUpFromFieldEntity){
                                                binding.ProceedButton.isVisible = true
                                                binding.deliveryId.text = "Order collect from fulfillment entity"
                                                binding.rejectButtonLayout.isInvisible = false
                                            }else if ((requestStatus.uppercase() =="PENDING" ||
                                                        requestStatus.uppercase() =="PROCESS" ||
                                                        requestStatus.uppercase() =="ONTHEWAY")
                                                && isOrderPickedUpFromFieldEntity){
                                                binding.deliveryId.text = "Order deliver to customer"
                                                binding.ProceedButton.isVisible = true
                                                binding.rejectButtonLayout.isInvisible = true
                                            }else{
                                                binding.rejectButtonLayout.isInvisible = true
                                            }
                                            with(orderIdRes.shippingFixedAddress){
                                                customerId = orderIdRes.userId.id
                                                with(storeLocation!!.coordinates){
                                                    latCustomer = this?.getOrNull(1)!!
                                                    longCustomer = this.getOrNull(0)!!
                                                }



                                                binding.nameCustomer.text = "$firstName $lastName"
                                                val finalAddress = StringBuffer()
                                                if (addressLine1 != null && addressLine1 != "") {
                                                    finalAddress.append("${addressLine1},")
                                                }

                                                if (city != null && city != "") {
                                                    finalAddress.append("${city},")
                                                }
                                                if (state != null && state != "") {
                                                    finalAddress.append("${state},")
                                                }
                                                if (country != null && country != "") {
                                                    finalAddress.append(country)
                                                }
                                                binding.deliveryAddressCustomer.text = "$finalAddress"
                                                binding.deliveryPincodeCustomer.text = zipCode
                                                binding.deliveryPhCustomer.text = mobileNumber
                                                binding.emailCustomer.text = email
                                            }
                                        }

                                        else -> {
                                            binding.rejectButtonLayout.isInvisible = requestStatus.uppercase() != "PENDING" && userRole != "FIELD_ENTITY"
                                        }
                                    }

                                    with(filedEntity.assignedUser){

                                        with(storeLocation!!.coordinates){
                                            lat = this?.getOrNull(1)!!
                                            long = this.getOrNull(0)!!
                                        }

                                        fieldEntityId = id

                                        binding.deliveryName.text = "$firstName $lastName"
                                        val finalAddress = StringBuffer()
                                        if (addressLine1 != null && addressLine1 != "") {
                                            finalAddress.append("${addressLine1},")
                                        }

                                        if (city != null && city != "") {
                                            finalAddress.append("${city},")
                                        }
                                        if (state != null && state != "") {
                                            finalAddress.append("${state},")
                                        }
                                        if (country != null && country != "") {
                                            finalAddress.append(country)
                                        }
                                        binding.deliveryAddress.text = "$finalAddress"
                                        binding.deliveryPincode.text = zipCode
                                        binding.deliveryPh.text = mobileNumber
                                        binding.email.text = email
                                    }

                                    if(userRole == "FIELD_ENTITY"){  // set data for FE to show customers Details and deliveryDriver is assigened
                                        with(orderIdRes.userId){
                                            binding.viewFieldEntity.text = "Customer Details: "
                                            binding.deliveryName.text = "$firstName $lastName"
                                            val finalAddress = StringBuffer()
                                            if (addressLine1 != null && addressLine1 != "") {
                                                finalAddress.append("${addressLine1},")
                                            }

                                            if (city != null && city != "") {
                                                finalAddress.append("${city},")
                                            }
                                            if (state != null && state != "") {
                                                finalAddress.append("${state},")
                                            }
                                            if (country != null && country != "") {
                                                finalAddress.append(country)
                                            }
                                            binding.deliveryAddress.text = "$finalAddress"
                                            binding.deliveryPincode.text = zipCode
                                            binding.deliveryPh.text = mobileNumber
                                            binding.email.text = email
                                        }

                                        if (deliveryDriver != null){
                                            isOrderOutForDelivery = true
                                            binding.CustomerView.text = "Delivery Driver Details: "
                                            binding.CustomerView.isVisible = true
                                            binding.llCustomer.isVisible = true
                                            binding.findRouteCustomerCustomer.isVisible = false
                                            binding.OutForDeliveryTextView.text = "Re-Assign Order"
                                            with(deliveryDriver.assignedUser){
                                                binding.nameCustomer.text = "$firstName $lastName"
                                                val finalAddress = StringBuffer()
                                                if (addressLine1 != null && addressLine1 != "") {
                                                    finalAddress.append("${addressLine1},")
                                                }

                                                if (city != null && city != "") {
                                                    finalAddress.append("${city},")
                                                }
                                                if (state != null && state != "") {
                                                    finalAddress.append("${state},")
                                                }
                                                if (country != null && country != "") {
                                                    finalAddress.append(country)
                                                }
                                                binding.deliveryAddressCustomer.text = "$finalAddress"
                                                binding.deliveryPincodeCustomer.text = zipCode
                                                binding.deliveryPhCustomer.text = mobileNumber
                                                binding.emailCustomer.text = email
                                            }
                                        }else{
                                            isOrderOutForDelivery = false
                                            binding.CustomerView.text = "Customer Details:"
                                            binding.CustomerView.isVisible = false
                                            binding.llCustomer.isVisible = false
                                            binding.OutForDeliveryTextView.text = "Assign a Delivery Driver"
                                        }

                                    }



                                }
                                
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()
                        binding.shimmerFrameLayout.isVisible = false
                        binding.shimmerFrameLayout.stopShimmerAnimation()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@ServiceCommonViewActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.shimmerFrameLayout.isVisible = false

                    }

                }, apiName = "viewAssignedOrderApi", this)


            try {

                serviceManager.viewAssignedOrderApi(callBack, _id =orderIdRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }


    fun setAdaptor() {
        binding.orderDetailsRecycler.layoutManager = LinearLayoutManager(this)
        val adapter = ViewDetailsOfServicesAdapter(this, data,orderIdRequest,this,requestStatus = isFrom,
            click = this, userRole= userRole)
        binding.orderDetailsRecycler.adapter = adapter
    }

    override fun orderProcess() {

    }

    override fun sendOtpForDeliveredItemToFE(retailerId: String) {
        sendOtpApi(retailerId = retailerId,userFor ="Delivered To FE")
    }

    override fun mapOpen(lat: Double, long: Double) {
        openMapRoute(lat=lat,long=long)
    }


    override fun rejectOrder(reason: String) {
        rejectOrdersApi(rejectReason = reason)
    }


    private fun rejectOrdersApi(rejectReason:String) {
        if (androidextention.isOnline(this)) {
            Progresss.start(this)
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<CommonResponseForAll> =
                ApiCallBack(object : ApiResponseListener<CommonResponseForAll> {
                    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
                    override fun onApiSuccess(response: CommonResponseForAll, apiName: String?) {
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            try {
                                ServicePage.isRefreshed = true
                                finishAfterTransition()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()
                        Progresss.stop()
                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@ServiceCommonViewActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, apiName = "rejectOrdersApi", this)


            try {

                serviceManager.rejectOrderApi(callBack, _id =orderIdRequest, rejectedReason =  rejectReason)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }


    private fun sendOtpApi(retailerId: String,userFor:String) {
        if (androidextention.isOnline(this)) {
            Progresss.start(this@ServiceCommonViewActivity)

            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<CommonResponseForAll> =
                ApiCallBack(object : ApiResponseListener<CommonResponseForAll> {
                    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
                    override fun onApiSuccess(response: CommonResponseForAll, apiName: String?) {
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            try {

                                val intent = Intent(this@ServiceCommonViewActivity, OtpVerification::class.java)
                                intent.putExtra("flag","Service provider")
                                intent.putExtra("retailerId",retailerId)
                                intent.putExtra("userRole",userRole)
                                intent.putExtra("fieldEntityId",fieldEntityId)
                                intent.putExtra("viewId",orderIdRequest)
                                intent.putExtra("customerId",customerId)
                                intent.putExtra("userFor",userFor)
                                startActivity(intent)
                                if (userFor == "Customer"){
                                    finish()
                                }


                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()
                        Progresss.stop()
                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@ServiceCommonViewActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, apiName = "sendOtpApi", this)


            try {

                serviceManager.sendOtpApi(callBack, _id =orderIdRequest, retailerId = fieldEntityId)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }

    override fun outForDelivery() {

    }




    private fun outForDeliveryApi() {
        if (androidextention.isOnline(this)) {
            androidextention.showProgressDialog(this)

            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<CommonResponseForAll> =
                ApiCallBack(object : ApiResponseListener<CommonResponseForAll> {
                    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
                    override fun onApiSuccess(response: CommonResponseForAll, apiName: String?) {
                        androidextention.disMissProgressDialog(this@ServiceCommonViewActivity)
                        if (response.responseCode == 200) {
                            try {
                                ServicePage.isRefreshed = true
                                androidextention.alertBoxFinish(response.responseMessage,this@ServiceCommonViewActivity,this@ServiceCommonViewActivity)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()
                        androidextention.disMissProgressDialog(this@ServiceCommonViewActivity)
                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@ServiceCommonViewActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        androidextention.disMissProgressDialog(this@ServiceCommonViewActivity)
                    }

                }, apiName = "outForDeliveryApi", this)


            try {

                serviceManager.outForDeliveryApi(callBack, _id =orderIdRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }


    private fun reAssignOrderApi() {
        if (androidextention.isOnline(this)) {
            androidextention.showProgressDialog(this)

            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<CommonResponseForAll> =
                ApiCallBack(object : ApiResponseListener<CommonResponseForAll> {
                    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
                    override fun onApiSuccess(response: CommonResponseForAll, apiName: String?) {
                        androidextention.disMissProgressDialog(this@ServiceCommonViewActivity)
                        if (response.responseCode == 200) {
                            try {
                                ServicePage.isRefreshed = true
                                androidextention.alertBoxFinish(response.responseMessage,this@ServiceCommonViewActivity,this@ServiceCommonViewActivity)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()
                        androidextention.disMissProgressDialog(this@ServiceCommonViewActivity)
                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@ServiceCommonViewActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        androidextention.disMissProgressDialog(this@ServiceCommonViewActivity)
                    }

                }, apiName = "reAssignDeliveryApi", this)


            try {

                serviceManager.reAssignDeliveryApi(callBack, id =orderIdRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }

    private fun addFeedbackApi() {
        if (androidextention.isOnline(this)) {
            Progresss.start(this)
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<CommonResponseForAll> = ApiCallBack(object :
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

                }, "addFeedbackApi", this)


            try {
                val request  = FeedBackDataRequest()
                request.feedBackData.clear()


                var firstPartner = ""
                var secondPartner = ""

                when (userRole) {
                    "DELIVERY_PARTNER" -> {
                        firstPartner = "CUSTOMER"
                        secondPartner = "FIELD_ENTITY"
                    }
                    "FIELD_ENTITY" -> {
                        firstPartner = "PICKUP_PARTNER"
                        secondPartner = "DELIVERY_PARTNER"
                    }
                    "PICKUP_PARTNER" -> {
                        firstPartner = "RETAILER"
                        secondPartner = "FIELD_ENTITY"
                    }

                }

                request.feedBackData.add(FeedBackData(firstPartner, orderId = orderIdRequest, binding.ratingBar.rating, binding.feedbackFirstEt.text.toString()))
                request.feedBackData.add(FeedBackData(secondPartner, orderId = orderIdRequest, binding.ratingBarSecond.rating, binding.feedbackSecondEt.text.toString()))




                serviceManager.submitFeedbackApi(callBack,request)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun getFeedBackByOrderIdApi() {
        if (androidextention.isOnline(this)) {
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<FeedbackResponse> =
                ApiCallBack(object : ApiResponseListener<FeedbackResponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onApiSuccess(response: FeedbackResponse, apiName: String?) {
                        if (response.responseCode == 200) {

                            if(response.result.isNotEmpty()){
                                binding.feedbackFirstEt.isEnabled = false
                                binding.feedbackSecondEt.isEnabled = false
                                binding.addFeedback.isVisible = false

                                when(userRole){
                                    "DELIVERY_PARTNER" -> {
                                        if (response.result[0].ratingToUserType =="CUSTOMER"){
                                            binding.feedbackFirstEt.setText(response.result[0].review)
                                            binding.feedbackSecondEt.setText(response.result[1].review)

                                            binding.ratingBar.rating =  response.result[0].rating.toFloat()
                                            binding.ratingBarSecond.rating =  response.result[1].rating.toFloat()


                                        }else{
                                            binding.feedbackFirstEt.setText(response.result[1].review)
                                            binding.ratingBarSecond.rating =  response.result[0].rating.toFloat()

                                            binding.feedbackFirstEt.setText(response.result[0].review)
                                            binding.ratingBar.rating =  response.result[1].rating.toFloat()
                                        }
                                    }
                                    "FIELD_ENTITY" -> {
                                        if (response.result[0].ratingToUserType =="PICKUP_PARTNER"){
                                            binding.feedbackFirstEt.setText(response.result[0].review)
                                            binding.feedbackSecondEt.setText(response.result[1].review)

                                            binding.ratingBar.rating =  response.result[0].rating.toFloat()
                                            binding.ratingBarSecond.rating =  response.result[1].rating.toFloat()


                                        }else{
                                            binding.feedbackFirstEt.setText(response.result[1].review)
                                            binding.ratingBarSecond.rating =  response.result[0].rating.toFloat()

                                            binding.feedbackFirstEt.setText(response.result[0].review)
                                            binding.ratingBar.rating =  response.result[1].rating.toFloat()
                                        }
                                    }
                                    "PICKUP_PARTNER" -> {
                                        if (response.result[0].ratingToUserType =="RETAILER"){
                                            binding.feedbackFirstEt.setText(response.result[0].review)
                                            binding.feedbackSecondEt.setText(response.result[1].review)

                                            binding.ratingBar.rating =  response.result[0].rating.toFloat()
                                            binding.ratingBarSecond.rating =  response.result[1].rating.toFloat()


                                        }else{
                                            binding.feedbackFirstEt.setText(response.result[1].review)
                                            binding.ratingBarSecond.rating =  response.result[0].rating.toFloat()

                                            binding.feedbackFirstEt.setText(response.result[0].review)
                                            binding.ratingBar.rating =  response.result[1].rating.toFloat()
                                        }
                                    }
                                }

                            }



                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    }

                }, "getFeedBackByOrderIdApi", this)


            try {
                serviceManager.getFeedBackByOrderIdApi(callBack,"CUSTOMER",orderIdRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
    
    
    
    

}