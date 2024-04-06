package com.exobe.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.fragments.orderHistory.MyBookingsFragment
import com.exobe.adaptor.customeradaptor.CreateServiceAdaptor
import com.exobe.modelClass.ChooseServicesMyModel
import com.exobe.modelClass.response_modal_class
import com.exobe.modelClass.service_request
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.bottomSheet.CustomerBottomSheet
import com.exobe.bottomSheet.PaymentPreview
import com.exobe.customClicks.DeleteServiceItem
import com.exobe.customClicks.PaymentDoneListener
import com.exobe.customClicks.PaymentForOrderClick
import com.exobe.customClicks.ServiceDetailsAdd
import com.exobe.customClicks.ServicesTotalAmount
import com.exobe.customClicks.UpdateIsLoginListener
import com.exobe.dialogs.DialogBoxPayment
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.MakeServiceDetails
import com.exobe.entity.request.MakeServiceRequest
import com.exobe.entity.response.CommonResponseForAll
import com.exobe.entity.response.MyEarningResponse
import com.exobe.entity.response.OzowPaymentResponse
import com.exobe.entity.response.PayFastResponse
import com.exobe.entity.response.TimeSlotResponse
import com.exobe.entity.response.ViewAddressResponse
import com.exobe.entity.response.customerservices.CreateServiceResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceConstant
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.fragments.address.AddAddressFragment
import com.exobe.fragments.address.ChooseDeliveryAddress
import com.exobe.util.DateFormat
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.text.SimpleDateFormat
import java.util.*


class ServiceRequestReview() : Fragment(), ServicesTotalAmount, DeleteServiceItem, ServiceDetailsAdd,
    UpdateIsLoginListener , PaymentForOrderClick, PaymentDoneListener {
    lateinit var MenuClick: LinearLayout
    lateinit var search: RelativeLayout
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView

    lateinit var selectdate: LinearLayout
    lateinit var selecttime: LinearLayout
    lateinit var selecttimeLL: LinearLayout
    lateinit var dateselectTV: TextView
    lateinit var selecttimeTV: Spinner
    lateinit var requestforservice: Button
    lateinit var mContext: Context
    lateinit var mainHeader: RelativeLayout
    lateinit var tv_edit_address: TextView
    lateinit var tv_choose_address: TextView
    lateinit var detail_screen: RecyclerView
    lateinit var tv_selecttime: TextView
    lateinit var tv_selectdate: TextView
    lateinit var total_price: TextView
    lateinit var vat_price: TextView
    lateinit var grand_price: TextView

    lateinit var delivery_name: TextView
    lateinit var delivery_address: TextView

    //    lateinit var delivery_city: TextView
    lateinit var delivery_pincode: TextView
    lateinit var delivery_ph: TextView

    lateinit var CategoryName: TextView
    lateinit var spName: TextView
    lateinit var llAddDeals: LinearLayout
    lateinit var progressbar: ProgressBar
    var listSpinnertime = ""

    var itemList = ArrayList<service_request>()
    lateinit var createServiceAdaptor: CreateServiceAdaptor
    var yearset = 0
    var monthset = 0
    var day = 0
    val c = Calendar.getInstance()
    var chooseServicesMyModel = ArrayList<ChooseServicesMyModel>()
    lateinit var TotalPrice: TextView
    var FINAL_AMOUNT = 0.0
    var vatAmount = 0.0
    var GRAND_AMOUNT = 0.0
    var counter = 1
    var serviceProviderName = ""
    var serviceAddressId = ""

    var createMakeServiceDetails = ArrayList<MakeServiceDetails>()
    var durationRequest = ""
    var slotRequest = ""
    var userId = ""
    var dealId = ""
    var categoryName = ""
    lateinit var RS_nestedScrollView: NestedScrollView
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    var AVT_PER = 15

    var timeSlotArray = ArrayList<String>()
    var totalQuantity = 0
    lateinit var payFastClick: WebView
    companion object {
        var apiServiceRequestCallFlag = true
    }

    var isAddressChanged = false

    private var paymentFrom = ""
    private var walletAmount = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener("requestKey", this) { _, bundle ->
            val receivedData = bundle.getString("addressId")
            isAddressChanged = bundle.getBoolean("isAddressChanged")
            if (isAddressChanged){
                setAddressApi(receivedData)
            }

        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.activity_service_request, container, false)
        mContext = activity?.applicationContext!!
        delivery_name = view.findViewById(R.id.delivery_name)
        delivery_address = view.findViewById(R.id.delivery_address)
//        delivery_city = view.findViewById(R.id.delivery_city)
        delivery_pincode = view.findViewById(R.id.delivery_pincode)
        delivery_ph = view.findViewById(R.id.delivery_ph)

        selectdate = view.findViewById(R.id.selectdate)
        selecttime = view.findViewById(R.id.selecttime)
        selecttimeLL = view.findViewById(R.id.selecttimeLL)
        dateselectTV = view.findViewById(R.id.dateselectTV)
        selecttimeTV = view.findViewById(R.id.selecttimeTV)
        tv_selectdate = view.findViewById(R.id.tv_selectdate)
        tv_selecttime = view.findViewById(R.id.tv_selecttime)
        requestforservice = view.findViewById(R.id.requestforservice)
        tv_edit_address = view.findViewById(R.id.tv_edit_address)
        tv_choose_address = view.findViewById(R.id.tv_choose_address)
        detail_screen = view.findViewById(R.id.detail_screen)
        total_price = view.findViewById(R.id.total_price)

        grand_price = view.findViewById(R.id.grand_price)
        vat_price = view.findViewById(R.id.vat_price)
        spName = view.findViewById(R.id.spName)
        progressbar = view.findViewById(R.id.progressbar)
        llAddDeals = view.findViewById(R.id.llAddDeals)
        RS_nestedScrollView = view.findViewById(R.id.RS_nestedScrollView)
        CategoryName = view.findViewById(R.id.CategoryName)
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        mainHeader.visibility = View.VISIBLE
        llAddDeals.visibility = View.GONE
        payFastClick = view.findViewById(R.id.payFastClick)


        setToolbar()
        val calendar: Calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val date = calendar.get(Calendar.DAY_OF_MONTH)
        createMakeServiceDetails.clear()

        getMyWalletDetailsApi()

        arguments?.getString("dealId")?.let { dealId = it }

        arguments?.getString("userId").let {
            if (it != null) {
                userId = it
            }
        }

        arguments?.getString("categoryName").let {
            if (it != null) {
                categoryName = it
                CategoryName.text = categoryName
            }
        }

        setPrimaryAddress()

        tv_edit_address.setSafeOnClickListener {
            if (serviceAddressId == "") {
                androidextention.alertBox("*Please choose address", requireContext())
            } else {
                fragmentManager?.beginTransaction()?.replace(
                    R.id.FrameLayout,
                    AddAddressFragment("Edit", serviceAddressId, "service_request"), "addAddress"
                )?.addToBackStack(null)?.commit()
            }
        }

        tv_choose_address.setSafeOnClickListener {
            ChooseDeliveryAddress.apiDeliveryAddressCallFlag = true
            val bundle = Bundle()
            bundle.putString("flag", "service_request")
            val fragObj = ChooseDeliveryAddress()
            fragObj.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, fragObj, "ChooseDeliveryAddress").addToBackStack(null)
                .commit()

        }

        selecttimeTV.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                listSpinnertime = parent.getItemAtPosition(pos).toString()
                slotRequest = listSpinnertime

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        selectdate.setSafeOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                R.style.DatePickerTheme,
                DatePickerDialog.OnDateSetListener
                { view, year, monthOfYear, dayOfMonth ->
                    durationRequest =
                        DateFormat.serviceDateFormatter("$dayOfMonth-${monthOfYear + 1}-$year")
                    dateselectTV.text =
                        DateFormat.serviceDateFormatter("$dayOfMonth-${monthOfYear + 1}-$year")
                    tv_selectdate.text = ""
//                    createTimeSlots()
                    timeSlotApi(durationRequest)

                },
                year,
                month,
                date
            )

            datePickerDialog.getDatePicker().minDate = c.timeInMillis

            datePickerDialog.show()
        }

        back.setSafeOnClickListener {
            parentFragmentManager.popBackStack()
        }

        requestforservice.setSafeOnClickListener {

            if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {

                if (serviceAddressId == "") {
                    androidextention.alertBox("*Please choose address", requireContext())
                } else if (dateselectTV.text.equals("")) {
                    tv_selectdate.text = "*Please select date "
                } else if (listSpinnertime == "" || listSpinnertime == "Select Time Slots") {
                    tv_selectdate.text = ""
                    tv_selecttime.text = "*Please select time slot"
                } else {
                    tv_selectdate.text = ""
                    tv_selecttime.text = ""
                    val bottomSheetFragment = PaymentPreview("CartPayment",this,grand_price.text.toString(),walletAmount)
                    bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)


                }



            } else {
                parentFragmentManager.let { it1 ->
                    CustomerBottomSheet("Customer", mContext, this).show(it1, "ModalBottomSheet")
                }

            }
        }



        return view
    }

    private fun setPrimaryAddress() {
        try {
            serviceAddressId = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.SERVICE_ID_Address).toString()

            if (serviceAddressId.isNotEmpty() && !isAddressChanged) {
                setAddressApi(serviceAddressId)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun GET_PARSING_DATA() {
        if (requireArguments().getSerializable("CHOOSE_LIST") != null) {

            if (apiServiceRequestCallFlag) {
                chooseServicesMyModel.clear()
                chooseServicesMyModel = requireArguments().getSerializable("CHOOSE_LIST") as ArrayList<ChooseServicesMyModel>

            }
            createMakeServiceDetails.clear()
            FINAL_AMOUNT = 0.0

            for (i in 0 until chooseServicesMyModel.size) {
                for (j in 0 until chooseServicesMyModel[i].servicesMyModel.size) {
                    FINAL_AMOUNT += chooseServicesMyModel[i].servicesMyModel[j].price.toDouble()
                    var result = MakeServiceDetails()
                    result.serviceId = chooseServicesMyModel[i].servicesMyModel[j].mainId
                    result.quantity = 1
                    result.price = chooseServicesMyModel[i].servicesMyModel[j].price
                    createMakeServiceDetails.add(result)
                    totalQuantity += 1
                }
            }
            vatAmount = (AVT_PER * FINAL_AMOUNT) / 100
            GRAND_AMOUNT = FINAL_AMOUNT + vatAmount
            total_price.text = "R ${CommonFunctions.currencyFormatter(FINAL_AMOUNT)}"
            vat_price.text = "R ${CommonFunctions.currencyFormatter(vatAmount)}"
            grand_price.text = "R ${CommonFunctions.currencyFormatter(GRAND_AMOUNT)}"
            setAdaptor()
        }
        if (requireArguments().getString("name") != null) {
            serviceProviderName =
                requireArguments().getString("name").toString()

            spName.text = serviceProviderName


        }
    }


    override fun onResume() {
        super.onResume()
        if (apiServiceRequestCallFlag) {
            GET_PARSING_DATA()
            apiServiceRequestCallFlag = false
        } else {
            totalQuantity = 0
            selecttime.isVisible = false
            durationRequest = ""
            dateselectTV.text = ""
            tv_selectdate.text = ""
            GET_PARSING_DATA()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun incrementAmount(
        count: Int?,
        id: String?,
        title: String?,
        price: Double?,
        quantity: String
    ) {
        if (count != null) {
            counter = count
        }
        if (counter!! > 0) {
            timeSlotAdaptor(timeSlotArray)
            durationRequest = ""
            dateselectTV.text = ""
            tv_selectdate.text = ""
            if (count != null) {
                totalQuantity += 1
            }
            FINAL_AMOUNT += price!!
            vatAmount = (AVT_PER * FINAL_AMOUNT) / 100
            GRAND_AMOUNT = FINAL_AMOUNT + vatAmount
            total_price.text = "R ${CommonFunctions.currencyFormatter(FINAL_AMOUNT)}"
            vat_price.text = "R ${CommonFunctions.currencyFormatter(vatAmount)}"
            grand_price.text = "R ${CommonFunctions.currencyFormatter(GRAND_AMOUNT)}"


        }
    }

    @SuppressLint("SetTextI18n")
    override fun decrementAmount(
        count: Int?,
        id: String?,
        title: String?,
        price: Double?,
        quantity: String
    ) {
        if (counter!! > 0) {
            timeSlotAdaptor(timeSlotArray)
            durationRequest = ""
            dateselectTV.text = ""
            if (count != null) {
                totalQuantity -= 1
            }
            FINAL_AMOUNT -= price!!
            vatAmount = (AVT_PER * FINAL_AMOUNT) / 100
            GRAND_AMOUNT = FINAL_AMOUNT + vatAmount
            total_price.text = "R ${CommonFunctions.currencyFormatter(FINAL_AMOUNT)}"
            vat_price.text = "R ${CommonFunctions.currencyFormatter(vatAmount)}"
            grand_price.text = "R ${CommonFunctions.currencyFormatter(GRAND_AMOUNT)}"

        }
    }


    fun setAdaptor() {
        detail_screen.layoutManager = LinearLayoutManager(activity)
        createServiceAdaptor = CreateServiceAdaptor(requireContext(), chooseServicesMyModel, this, this, this)
        detail_screen.adapter = createServiceAdaptor

    }

    @SuppressLint("SetTextI18n")
    override fun deleteServiceItem(count: Int, id: String?, position: Int) {
        var price = 0.0
        var removeFlag = false
        if (chooseServicesMyModel.size > 1) {
            for (i in 0 until chooseServicesMyModel.size) {
                for (j in 0 until chooseServicesMyModel[i].servicesMyModel.size) {
                    if (chooseServicesMyModel[i].servicesMyModel[j].id == id) {
                        price = chooseServicesMyModel[i].servicesMyModel[j].price.toDouble()
                        chooseServicesMyModel.removeAt(i)
                        removeFlag = true
                        break
                    }
                }
                if (removeFlag) {
                    break
                }
            }

            if (count == 1) {
                timeSlotAdaptor(timeSlotArray)
                durationRequest = ""
                dateselectTV.text = ""
                totalQuantity -= 1
                FINAL_AMOUNT -= price
                vatAmount = (AVT_PER * FINAL_AMOUNT) / 100
                GRAND_AMOUNT = FINAL_AMOUNT + vatAmount
                total_price.text = "R ${CommonFunctions.currencyFormatter(FINAL_AMOUNT)}"
                vat_price.text = "R ${CommonFunctions.currencyFormatter(vatAmount)}"
                grand_price.text = "R ${CommonFunctions.currencyFormatter(GRAND_AMOUNT)}"
            }

            createServiceAdaptor.deleteItem(chooseServicesMyModel)
        } else {
            Toast.makeText(
                requireContext(),
                "Sorry, you can't remove because one service is mandatory to request.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

//    override fun deleteServiceItem(count: Int, id: String?, position: Int) {
//        var price = 0.0
//        var removeFlag = false
//        if (chooseServicesMyModel.size > 1) {
//            for (i in 0 until chooseServicesMyModel.size) {
//                for (j in 0 until chooseServicesMyModel[i].servicesMyModel.size) {
//                    if (chooseServicesMyModel[i].servicesMyModel[j].id == id) {
//                        price = chooseServicesMyModel[i].servicesMyModel[j].price.toDouble()
//                        chooseServicesMyModel.removeAt(i)
////                    break
//                    }
//                }
//            }
//            if (count == 1) {
//                timeSlotAdaptor(timeSlotArray)
//                durationRequest = ""
//                dateselectTV.text = ""
//                totalQuantity -= 1
//                FINAL_AMOUNT -= price
//                vatAmount = (AVT_PER * FINAL_AMOUNT) / 100
//                GRAND_AMOUNT = FINAL_AMOUNT + vatAmount
//                total_price.text = "R ${CommonFunctions.currencyFormatter(FINAL_AMOUNT)}"
//                vat_price.text = "R ${CommonFunctions.currencyFormatter(vatAmount)}"
//                grand_price.text = "R ${CommonFunctions.currencyFormatter(GRAND_AMOUNT)}"
//            }
//            createServiceAdaptor.deleteItem(chooseServicesMyModel)
//        } else {
//            Toast.makeText(
//                requireContext(),
//                "Sorry, you can't remove because one service is mandatory to request.",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//
//
//    }

    fun timeSlotApi(durationRequest: String) {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<TimeSlotResponse> =
                ApiCallBack<TimeSlotResponse>(object :
                    ApiResponseListener<TimeSlotResponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onApiSuccess(response: TimeSlotResponse, apiName: String?) {
                        progressbar.visibility = View.GONE

                        if (response.responseCode == 200) {
                            if(response.result.docs.isNotEmpty()) {
                                selecttime.visibility = View.VISIBLE
                                selecttimeLL.visibility = View.VISIBLE
                                timeSlotAdaptor(response.result.docs)
                            } else {
                                androidextention.alertBox("No slots available.",requireContext())
                            }
                        } else {

                        }


                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar.visibility = View.GONE

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            Toast.makeText(requireContext(), pojo.responseMessage.toString(), Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                    }

                }, "timeSlotApi", mContext)


            try {
                val sdf = SimpleDateFormat("HH:mm")
                val sdfDate = SimpleDateFormat("dd-MM-yyyy")
                var todayDate = sdfDate.format(Date())
                val currentTime = sdf.format(Date())


                var jsonObject = JsonObject().apply {
                    addProperty("userId", userId)
                    addProperty("quantity", totalQuantity)
                    addProperty("date", durationRequest)
                    if (todayDate == durationRequest) {
                        addProperty("startDate", currentTime)
                    } else {
                        addProperty("startDate", "09:00")
                    }
                }

                serviceManager.timeSlotApi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            RS_nestedScrollView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    fun setAddressApi(serviceAddressId: String?) {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<ViewAddressResponse> =
                ApiCallBack<ViewAddressResponse>(object :
                    ApiResponseListener<ViewAddressResponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onApiSuccess(response: ViewAddressResponse, apiName: String?) {
                        progressbar.visibility = View.GONE

                        if (response.responseCode == 200) {
                            llAddDeals.visibility = View.VISIBLE

                            delivery_name.text =
                                "${response.result.firstName} ${response.result.lastName}"
                            var finalAddress = StringBuffer()
                            if (response.result.addressLine1 != null && !response.result.addressLine1.equals(
                                    ""
                                )
                            ) {
                                finalAddress.append("${response.result.addressLine1},")
                            }
//                            if (response.result.addressLine2 != null && !response.result.addressLine2.equals(
//                                    ""
//                                )
//                            ) {
//                                finalAddress.append("${response.result.addressLine2},")
//                            }
                            if (response.result.city != null && response.result.city != "") {
                                finalAddress.append("${response.result.city},")
                            }
                            if (response.result.state != null && response.result.state != "") {
                                finalAddress.append("${response.result.state},")
                            }
                            if (response.result.country != null && response.result.country != "") {
                                finalAddress.append("${response.result.country}")
                            }
                            delivery_address.text =
                                "$finalAddress"
//                            delivery_city.text = response.result.city
                            delivery_pincode.text = response.result.zipCode
                            delivery_ph.text = response.result.mobileNumber

                        }


                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar.visibility = View.GONE

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, mContext)

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                    }

                }, "ViewAddressAPI", mContext)


            try {
                serviceManager.viewAddress(callBack, serviceAddressId!!)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            RS_nestedScrollView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    fun serviceRequestApi() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<CreateServiceResponse> =
                ApiCallBack<CreateServiceResponse>(object :
                    ApiResponseListener<CreateServiceResponse> {
                    override fun onApiSuccess(response: CreateServiceResponse, apiName: String?) {

                        if (response.responseCode == 200) {
                            try {

                                if (paymentFrom == "ozow"){
                                    paymentApi(response.result._id)
                                }
                                if (paymentFrom == "wallet"){
                                    checkOutWalletOrderApi(response.result._id)
                                }
                                if (paymentFrom == "payfast"){
                                    paymentPayFastApi(response.result._id)
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
                            androidextention.alertBox(pojo.responseMessage, requireContext())

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                    }

                }, "serviceRequestApi", mContext)


            var makeServiceRequest = MakeServiceRequest()
            makeServiceRequest.serviceDetails = createMakeServiceDetails
            makeServiceRequest.orderPrice = GRAND_AMOUNT
            makeServiceRequest.actualPrice = FINAL_AMOUNT
            makeServiceRequest.taxPrice = vatAmount
            makeServiceRequest.duration = durationRequest
            makeServiceRequest.slots = slotRequest
            makeServiceRequest.address = serviceAddressId
            makeServiceRequest.dealId = dealId
            try {
                serviceManager.serviceRequestApi(callBack, makeServiceRequest)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            RS_nestedScrollView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    override fun addServiceDetails(mainId: String, count: Int) {
        try {
            for (i in 0 until createMakeServiceDetails.size) {
                if (createMakeServiceDetails[i].serviceId.contains(mainId)) {
                    createMakeServiceDetails[i].quantity = count
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun paymentApi(orderId: String) {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<OzowPaymentResponse> =
                ApiCallBack<OzowPaymentResponse>(object :
                    ApiResponseListener<OzowPaymentResponse> {
                    override fun onApiSuccess(response: OzowPaymentResponse, apiName: String?) {
                        progressbar.visibility = View.GONE

                        if (response.responseCode == 200) {

                            try {
                                val url = response.result
                                val intent = Intent(requireActivity(), OzowPaymentActivity::class.java)
                                intent.putExtra("websiteUrl", url)
                                startActivityForResult(intent, 1)
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
                            androidextention.alertBox(pojo.responseMessage, mContext)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                    }

                }, "PaymentApi", mContext)

            var jsonObject = JsonObject().apply {
                addProperty("shippingAddress", serviceAddressId)
                addProperty("cancelUrl", ServiceConstant.BASE_OWZO_URL)
                addProperty("errorUrl", ServiceConstant.BASE_OWZO_URL)
                addProperty("SuccessUrl", ServiceConstant.BASE_OWZO_URL)
                addProperty("testMode", true)
            }

            try {
                serviceManager.ozowPayment(callBack, orderId, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            RS_nestedScrollView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                var fm: FragmentManager = parentFragmentManager
                for (i in 1 until fm.backStackEntryCount) {
                    fm.popBackStack()
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.FrameLayout, MyBookingsFragment("SideMenu"), "viewService")
                    .addToBackStack(null).commit()
            }
        }
    }

    private fun timeSlotAdaptor(list: List<String>) {
        val Product_category: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(), android.R.layout.simple_spinner_item,
            list
        )
        Product_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        selecttimeTV.setAdapter(Product_category)
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
        title.text = "Bookings Details"
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    override fun isLoginListener() {
        var name = activity?.findViewById<TextView>(R.id.name)
        var userProfile = activity?.findViewById<ImageView>(R.id.Profile_picture)
        CommonFunctions.getProfileApiApi(mContext, name, userProfile)
        setPrimaryAddress()
    }


    override fun ozow() {
        paymentFrom = "ozow"
        serviceRequestApi()
    }

    override fun payfast() {
        paymentFrom = "payfast"
        serviceRequestApi()
    }

    override fun walletClick() {
        paymentFrom = "wallet"
        serviceRequestApi()
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
                                    parentFragmentManager.let { DialogBoxPayment(this@ServiceRequestReview).show(it, "MyCustomFragment") }
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
                serviceManager.checkOutWalletOrderApi(callBack, orderId = orderId,
                    ServiceConstant.BASE_OWZO_URL,
                    ServiceConstant.BASE_OWZO_URL,
                    ServiceConstant.BASE_OWZO_URL,true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    override fun paymentDone() {
        val fm: FragmentManager = parentFragmentManager
        for (i in 1 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.FrameLayout, MyBookingsFragment("SideMenu"), "viewService")
            .addToBackStack(null).commit()
    }

    override fun FailedDone() {

    }

    override fun cancelPayment() {

    }

    private fun getMyWalletDetailsApi() {
        if (androidextention.isOnline(requireContext())) {
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<MyEarningResponse> =
                ApiCallBack(object : ApiResponseListener<MyEarningResponse> {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onApiSuccess(response: MyEarningResponse, apiName: String?) {
                        if (response.responseCode == 200) {
                            try {

                                walletAmount  = "R ${CommonFunctions.currencyFormatter(response.result.wallet.walletAmount.toDouble())}"



                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {

                    }

                }, "getAllPendingOrdersApi", requireContext())


            try {


                serviceManager.getCustomerWalletTransactionApi(callBack,  page =1, limit = 10)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }
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

                                requestforservice.isVisible = false
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

                addProperty("cancelUrl", ServiceConstant.BASE_OWZO_URL)
                addProperty("errorUrl", ServiceConstant.BASE_OWZO_URL)
                addProperty("SuccessUrl", ServiceConstant.BASE_OWZO_URL)
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

            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

}


