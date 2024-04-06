package com.exobe.activities.retailer

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.RequiresApi
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.androidextention
import com.exobe.bottomSheet.SelectSizeBottomSheet
import com.exobe.customClicks.SizeListener
import com.exobe.databinding.ActivityStartCampaignRetailerBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.AddCampaignOnProductRequest
import com.exobe.entity.request.CampaignDetail
import com.exobe.entity.response.CommonResponseForAll
import com.exobe.entity.response.DealDetails
import com.exobe.entity.response.PriceSizeDetails
import com.exobe.entity.response.ViewProductCampaignResponse
import com.exobe.entity.response.viewProductResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.extension.displayToast
import com.exobe.extension.setTextValue
import com.exobe.modelClass.response_modal_class
import com.exobe.util.DateFormat
import com.exobe.utils.CommonFunctions
import com.exobe.utils.Progresss
import com.exobe.validations.TimeClass
import com.google.gson.GsonBuilder
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class StartCampaignRetailerActivity : AppCompatActivity(), SizeListener {

    private lateinit var binding:ActivityStartCampaignRetailerBinding


    private var datePicker: DatePickerDialog? = null


    private val cal = Calendar.getInstance()
    private var yearset = 0
    private var monthset = 0
    var day = 0
    private var productPrice = ""
    private var productQuantity = ""
    private var unitRequest = ""
    private var productSizeId = ""
    private var productSizeValue = ""
    private var productId = ""
    private var productEdit = false
    private var idForView = ""
    private var priceSizeDetails = ArrayList<PriceSizeDetails>()
    private var DealDetails = ArrayList<DealDetails>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartCampaignRetailerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.attributes.windowAnimations = R.style.Fade
        productId = intent.getStringExtra("productId")!!
        productEdit = intent.getBooleanExtra("edit",false)
        idForView = intent.getStringExtra("id")!!

        if (!productEdit){
            viewProductDetailsApi(productId = productId)
        }else{
            binding.startCampaign.text = "Update Campaign"
            viewCampaignApi()
        }

        binding.backButtonClick.setSafeOnClickListener {
            finishAfterTransition()
        }





        binding.releaseStock.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable) {
                val enteredValue = binding.releaseStock.text.toString()
                val remainingStock = binding.remainingStockTV.text.toString()

                if (enteredValue.isNotEmpty() && remainingStock.isNotEmpty()) {
                    val enteredInt = enteredValue.toInt()
                    val remainingStockInt = remainingStock.toInt()

                    if (enteredInt > remainingStockInt) {
                        displayToast("You can not enter more value than remaining stock")
                        binding.releaseStock.setText("")
                    }
                }
            }
        })

        binding.discount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val discount = s.toString()
                val numericString = binding.actualPrice.text.toString().replace("[^\\d.]".toRegex(), "")
                val actualPrice = if (numericString.isNotEmpty()) numericString.toDouble() else 0.0
                if (discount.isNotEmpty()) {
                    if (discount.toInt() in 1..99){
                        calculateDiscountPrice(actualPrice, discount.toInt())
                    }else{
                        binding.discount.setTextValue("")
                        binding.discountedPrice.text = ""
                    }


                } else {
                    binding.discountedPrice.text = ""
                }
            }
        })


        binding.sizeValueTV.setOnClickListener {
            if (priceSizeDetails.size > 1) {
                supportFragmentManager.let { it1 ->
                    SelectSizeBottomSheet(this, "", DealDetails, priceSizeDetails, this).show(it1, "ModalBottomSheet")
                }
            }
        }



        binding.startCampaign.setSafeOnClickListener {
            if (binding.startDateTv.text.isEmpty()){
                androidextention.alertBox("Please select start date.",this)
            }else if (binding.startTimeTv.text.isEmpty()){
                androidextention.alertBox("Please select start time.",this)
            }else if (binding.endDateTV.text.isEmpty()){
                androidextention.alertBox("Please select end date.",this)
            }else if (binding.endTimeTv.text.isEmpty()){
                androidextention.alertBox("please select end time.",this)
            }else if (binding.discount.text.isEmpty()){
                androidextention.alertBox("please enter discount.",this)
            }else{
                addCampaignApi()
            }



        }


        val calendar: Calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val date = calendar.get(Calendar.DAY_OF_MONTH)

        binding.startdate.setSafeOnClickListener {
            datePicker = DatePickerDialog(
                this,
                R.style.DatePickerTheme,{ _, year, monthOfYear, dayOfMonth ->
                    cal.set(year, monthOfYear, dayOfMonth)
                    binding.endDateTV.text = ""
                    binding.startTimeTv.text = ""
                    binding.endTimeTv.text = ""
                    binding.startDateTv.text = "$year-${monthOfYear + 1}-$dayOfMonth"
                    TimeClass.showTimePickerDialog(this,binding.startTimeTv, binding.startDateTv,binding.endDateTV)
                    yearset = year
                    monthset = monthOfYear
                    day = dayOfMonth
                },
                year,
                month,
                date
            )
            datePicker!!.datePicker.minDate = System.currentTimeMillis() - 1000
            datePicker!!.show()
        }
        binding.startTime.setSafeOnClickListener { TimeClass.showTimePickerDialog(this,binding.startTimeTv, binding.startDateTv,binding.endDateTV) }
        binding.endTime.setSafeOnClickListener { TimeClass.showTimePickerDialog(this,binding.endTimeTv, binding.startDateTv,binding.endDateTV) }
        binding.endDateRL.setSafeOnClickListener {

            datePicker = DatePickerDialog(
                this,
                R.style.DatePickerTheme, { _, year, monthOfYear, dayOfMonth ->
                    binding.endTimeTv.text = ""
                    binding.endDateTV.text = "$year-${monthOfYear + 1}-$dayOfMonth"
                    TimeClass.showTimePickerDialog(this,binding.endTimeTv, binding.startDateTv,binding.endDateTV)
                },
                year,
                month,
                date
            )
            datePicker!!.datePicker.minDate = cal.timeInMillis


            datePicker!!.show()
        }
        cal.set(yearset, monthset, day)



    }

    private fun viewProductDetailsApi(productId: String) {
        if (androidextention.isOnline(this)) {
          
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<viewProductResponse> =
                ApiCallBack<viewProductResponse>(object :
                    ApiResponseListener<viewProductResponse> {
                    override fun onApiSuccess(response: viewProductResponse, apiName: String?) {
                        androidextention.disMissProgressDialog(this@StartCampaignRetailerActivity)
                        if (response.responseCode == 200) {
                            priceSizeDetails = response.result.priceSizeDetails
                            if (priceSizeDetails.isNotEmpty()){
                                binding.actualPrice.text = "R ${CommonFunctions.currencyFormatter(priceSizeDetails[0].price.toDouble())}"
                                binding.sizeValueTV.text = "${priceSizeDetails[0].value}  ${priceSizeDetails[0].unit.lowercase().takeIf { it != "other" } ?: ""}"
                                binding.remainingStockTV.text = "${priceSizeDetails[0].quantity}"
                                productSizeId = "${priceSizeDetails[0].id}"
                                productSizeValue = "${priceSizeDetails[0].value}"
                                productPrice = "${priceSizeDetails[0].price}"
                                unitRequest = "${priceSizeDetails[0].unit}"
                                productQuantity = "${priceSizeDetails[0].quantity}"
                            }


                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        androidextention.disMissProgressDialog(this@StartCampaignRetailerActivity)

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(
                                pojo.responseMessage,
                                this@StartCampaignRetailerActivity
                            )

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        androidextention.disMissProgressDialog(this@StartCampaignRetailerActivity)


                    }

                }, "viewProductDetailsApi", this)

            try {
                serviceManager.viewProductDetailsApi(callBack, productId)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun viewCampaignApi() {
        if (androidextention.isOnline(this)) {

            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<ViewProductCampaignResponse> =
                ApiCallBack<ViewProductCampaignResponse>(object :
                    ApiResponseListener<ViewProductCampaignResponse> {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onApiSuccess(response: ViewProductCampaignResponse, apiName: String?) {
                        androidextention.disMissProgressDialog(this@StartCampaignRetailerActivity)
                        if (response.responseCode == 200) {

                            for (data in response.result.campaignDetail.indices){
                                val inputData = response.result.campaignDetail[data]
                                priceSizeDetails.add(PriceSizeDetails(inputData.value, inputData.unit,inputData.price.toDouble(),inputData.quantity,inputData.id))
                            }

                            if (priceSizeDetails.isNotEmpty()){
                                binding.actualPrice.text = "R ${CommonFunctions.currencyFormatter(priceSizeDetails[0].price.toDouble())}"
                                binding.sizeValueTV.text = "${priceSizeDetails[0].value}  ${priceSizeDetails[0].unit.lowercase().takeIf { it != "other" } ?: ""}"
                                binding.remainingStockTV.text = priceSizeDetails[0].quantity
                                productSizeId = "${priceSizeDetails[0].id}"
                                productSizeValue = priceSizeDetails[0].value
                                productPrice = "${priceSizeDetails[0].price}"
                                unitRequest = priceSizeDetails[0].unit
                                productQuantity = priceSizeDetails[0].quantity

                                binding.releaseStock.setText(response.result.campaignDetail[0].releaseQuantity)
                                binding.discount.setText(response.result.campaignDetail[0].discountedPercentage)
                                binding.discountedPrice.text = response.result.campaignDetail[0].discountedPrice


                            }

                            val formattedDateTimePair = DateFormat.formatDateTime(DateFormat.convertAndAddTime(response.result.startDate))
                            val formattedEndDateTimePair = DateFormat.formatDateTime(DateFormat.convertAndAddTime(response.result.endDate))


                            if (formattedDateTimePair != null ) {
                                val (formattedDate, formattedTime) = formattedDateTimePair
                                binding.startDateTv.text =  formattedDate
                                binding.startTimeTv.text =  formattedTime

                            }
                            if (formattedEndDateTimePair != null ) {
                                val (formattedDate, formattedTime) = formattedEndDateTimePair
                                binding.endDateTV.text =  formattedDate
                                binding.endTimeTv.text =  formattedTime
                            }








                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        androidextention.disMissProgressDialog(this@StartCampaignRetailerActivity)

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(
                                pojo.responseMessage,
                                this@StartCampaignRetailerActivity
                            )

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        androidextention.disMissProgressDialog(this@StartCampaignRetailerActivity)


                    }

                }, "viewProductDetailsApi", this)

            try {
                serviceManager.viewCampaignApi(callBack, idForView)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    
    
    override fun getSize(
        name: String,
        id: String,
        price: Number,
        unit: String,
        value: String,
        dealPrice: Number?,
        quantity: String?
    ) {
        productSizeId =  id
        unitRequest =  unit
        productSizeValue =  value
        productQuantity =  "$quantity"
        productPrice =  price.toString()
//
//        if (responseData.isDealActive) {
//            binding.actualPrice.text = "R $price"
//            val result = CommonFunctions.subtractPercentage(price.toDouble(), responseData.dealDiscount.toDouble())
//            binding.PriceTag.text = "R $result  ${responseData.dealDiscount}% Off"
//            binding.actualPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
//        } else {
//            binding.actualPrice.setTextColor(ContextCompat.getColor(this, R.color.black))
//            binding.actualPrice.text = "R ${CommonFunctions.currencyFormatter(price.toDouble())}"
//        }

        binding.sizeValueTV.text = "$name ${unit.lowercase().takeIf { it != "other" }?:""}"
        binding.actualPrice.text = "R ${CommonFunctions.currencyFormatter(price.toDouble())}"
        binding.remainingStockTV.text = "$quantity"

    }


    @OptIn(DelicateCoroutinesApi::class)
    fun calculateDiscountPrice(
        actualAmount: Double,
        discountPercentage: Int,
    ) {
        val discountAmount = actualAmount * (discountPercentage.toDouble() / 100)
        val amount = (actualAmount - discountAmount)

        GlobalScope.launch(Dispatchers.Main) {
            binding.discountedPrice.text = amount.toString()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun addCampaignApi() {
        if (androidextention.isOnline(this)) {
            Progresss.start(this)
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<CommonResponseForAll> =
                ApiCallBack(object :
                    ApiResponseListener<CommonResponseForAll> {
                    override fun onApiSuccess(response: CommonResponseForAll, apiName: String?) {
                        androidextention.disMissProgressDialog(this@StartCampaignRetailerActivity)
                        Progresss.stop()
                        if (response.responseCode == 200) {

                            if (productEdit){
                                val data = Intent()
                                data.putExtra("editCampaign", true)
                                setResult(Activity.RESULT_OK, data)
                                finishAfterTransition()
                                return
                            }
                            androidextention.alertBoxFinish(response.responseMessage,this@StartCampaignRetailerActivity,this@StartCampaignRetailerActivity)


                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        Progresss.stop()
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(
                                pojo.responseMessage,
                                this@StartCampaignRetailerActivity
                            )

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()


                    }

                }, "viewProductDetailsApi", this)

            try {
                val request  = AddCampaignOnProductRequest()





                request.productId =  productId
                request.campaignOn = "PRODUCT"



                request.startDate = DateFormat.convertAndSubtractTime(DateFormat.formatToISOString(dateString = binding.startDateTv.text.toString(), timeString = binding.startTimeTv.text.toString()).toString())
                request.endDate = DateFormat.convertAndSubtractTime(DateFormat.formatToISOString(dateString = binding.endDateTV.text.toString(), timeString = binding.endTimeTv.text.toString()).toString())

                request.campaignDetail.clear()
                request.campaignDetail.add(CampaignDetail(id=productSizeId,
                    quantity = productQuantity,
                    value = productSizeValue,
                    price = productPrice,
                    unit = unitRequest,
                    discountedPrice = binding.discountedPrice.text.toString(),
                    discountedPercentage = binding.discount.text.toString(),
                    releaseQuantity = binding.releaseStock.text.toString()))


                serviceManager.addCampaignApi(callBack, request,idForView)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }








}