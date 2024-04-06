package com.exobe.activities.services

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.adaptor.servicesAdaptor.AddCampaignServicesAdapter
import com.exobe.androidextention
import com.exobe.customClicks.CampaignOnServiceClick
import com.exobe.databinding.ActivityServiceCampaignAddBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.AddCampaignOnProductRequest
import com.exobe.entity.request.AddCampaignOnServiceRequest
import com.exobe.entity.request.CampaignDetail
import com.exobe.entity.response.CommonResponseForAll
import com.exobe.entity.response.PriceSizeDetails
import com.exobe.entity.response.ViewProductCampaignResponse
import com.exobe.entity.response.serviceTab.NewServicesResponseDoc
import com.exobe.entity.response.serviceTab.NewServicesResponseResponse
import com.exobe.entity.response.serviceTab.NewServicesResponseServiceArray
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.extension.displayToast
import com.exobe.extension.setTextValue
import com.exobe.modelClass.response_modal_class
import com.exobe.util.DateFormat
import com.exobe.utils.CommonFunctions
import com.exobe.utils.Progresss
import com.exobe.validations.DialogUtils
import com.exobe.validations.TimeClass
import com.google.gson.GsonBuilder
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar

class ServiceCampaignAddActivity : AppCompatActivity(), CampaignOnServiceClick {

    private lateinit var binding:ActivityServiceCampaignAddBinding

    private var categoryName  = ""
    private var categoryId  = ""
    private var subCategoryId  = ""
    private var serviceNameId  = ""
    var serviceSubCategoryData: ArrayList<NewServicesResponseDoc> = ArrayList()
    private var serviceNameData: List<NewServicesResponseServiceArray> = ArrayList()

    private lateinit var adapterCampaign: AddCampaignServicesAdapter
    var clickForPopUp = ""

    private lateinit var dialog: Dialog
    private lateinit var recyclerView: RecyclerView

    private var datePicker: DatePickerDialog? = null
    private val cal = Calendar.getInstance()
    private var yearset = 0
    private var monthset = 0
    var day = 0
    private var ServiceEdit = false


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityServiceCampaignAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.attributes.windowAnimations = R.style.Fade

        intent.getStringExtra("categoryName")?.let { categoryName=  it }
        intent.getStringExtra("categoryId")?.let { categoryId=  it }

        binding.backButtonClick.setOnClickListener {
            finishAfterTransition()
        }




        binding.serviceCategoryTV.text=  categoryName


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






        binding.serviceSubCategory.setSafeOnClickListener {
            clickForPopUp ="Category"
            openPopUpForCategories()
        }
        binding.serviceNameTV.setSafeOnClickListener {

            if (binding.serviceSubCategory.text.isEmpty()){
                displayToast("Please select bookings sub category.")
                return@setSafeOnClickListener
            }
            clickForPopUp ="Service Name"
            openPopUpForCategories()
        }



        binding.discountedPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable) {
                val enteredValue = binding.discountedPrice.text.toString()
                val remainingStock = binding.actualPrice.text.toString()

                if (enteredValue.isNotEmpty() && remainingStock.isNotEmpty()) {
                    val enteredInt = enteredValue.toInt()
                    val remainingStockInt = remainingStock.toInt()

                    if (enteredInt > remainingStockInt) {
                        displayToast("You can not enter more value than actual price.")
                        binding.discountedPrice.setText("")
                    }
                }
            }
        })



        binding.startCampaign.setSafeOnClickListener {
            if (binding.serviceSubCategory.text.isEmpty()){
                androidextention.alertBox("Please select booking sub category.",this)
            }else if (binding.serviceSubCategory.text.isEmpty()){
                androidextention.alertBox("Please select booking name.",this)
            }else if (binding.discountedPrice.text.isEmpty()){
                androidextention.alertBox("please enter discounted price.",this)
            }else if (binding.startDateTv.text.isEmpty()){
                androidextention.alertBox("Please select start date.",this)
            }else if (binding.startTimeTv.text.isEmpty()){
                androidextention.alertBox("Please select start time.",this)
            }else if (binding.endDateTV.text.isEmpty()){
                androidextention.alertBox("Please select end date.",this)
            }else if (binding.endTimeTv.text.isEmpty()){
                androidextention.alertBox("please select end time.",this)
            }else{
                addCampaignApi()
            }
        }



    }





    @SuppressLint("InflateParams", "SetTextI18n")
    fun openPopUpForCategories() {

        try {
            val binding = LayoutInflater.from(this).inflate(R.layout.pop_lists, null)
            dialog = DialogUtils().createDialog(this, binding.rootView, 0)!!
            recyclerView = binding.findViewById(R.id.popup_recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)

            val dialogTitle = binding.findViewById<TextView>(R.id.popupTitle)
            val dialogBackButton = binding.findViewById<ImageView>(R.id.BackButton)
            val searchEditText = binding.findViewById<EditText>(R.id.search_bar_edittext_popuplist)

            searchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(text: Editable?) {
                    filterData(text.toString(),clickForPopUp)
                }

            })
            dialogBackButton.setSafeOnClickListener { dialog.dismiss() }

            if (clickForPopUp =="Category"){
                getServiceSubCategoriesApi()
            }else{
                setServicesAdapter()
            }

            dialog.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    private fun filterData(searchText: String, flag: String) {
        val filteredList = ArrayList<NewServicesResponseDoc>()
        val subCategoryFilter = ArrayList<NewServicesResponseServiceArray>()

        try {
            when (flag) {
                "Category" -> serviceSubCategoryData.forEach { item ->
                    if (item.subCategory.subCategoryName.lowercase().contains(searchText.lowercase())) {
                        filteredList.add(item)
                    }
                }
                "Service Name" -> serviceNameData.forEach { item ->
                    if (item.serviceDetails.serviceName.lowercase().contains(searchText.lowercase())) {
                        subCategoryFilter.add(item)
                    }
                }

            }

            when (flag) {
                "Category" -> adapterCampaign.filterListCategory(filteredList)
                "Service Name" -> adapterCampaign.filterListSubCategory(subCategoryFilter)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }




    private fun getServiceSubCategoriesApi() {

        if (androidextention.isOnline(this)) {
            Progresss.start(this)
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<NewServicesResponseResponse> =
                ApiCallBack<NewServicesResponseResponse>(object :
                    ApiResponseListener<NewServicesResponseResponse> {
                    override fun onApiSuccess(
                        response: NewServicesResponseResponse,
                        apiName: String?
                    ) {
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            try {
                               
                                serviceSubCategoryData.clear()
                                serviceSubCategoryData = response.result.docs
                                setServicesAdapter()

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
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }
                }, "getServiceSubCategoriesApi", this)
            try {
                serviceManager.serviceListByCategory(callBack, categoryId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setServicesAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapterCampaign = AddCampaignServicesAdapter(this, serviceSubCategoryData,serviceNameData,clickForPopUp,this)
        recyclerView.adapter = adapterCampaign
    }

    override fun categoryClick(subCategoryName: String, id: String, serviceArray: List<NewServicesResponseServiceArray>) {
        binding.serviceSubCategory.text =  subCategoryName
        binding.serviceNameTV.text = ""
        binding.actualPrice.text = ""
        subCategoryId =  id
        serviceNameId =  ""
        serviceNameData = emptyList()
        serviceNameData = serviceArray
        dialog.dismiss()
    }

    override fun subCategoryClick(
        serviceName: String,
        id: String,
        priceDaily: String
    ) {
        if (priceDaily.isEmpty()){
            displayToast("Please configure service first.")
            return
        }
        binding.serviceNameTV.text =  serviceName
        binding.actualPrice.text =  priceDaily
        serviceNameId =  id
        dialog.dismiss()
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
                        androidextention.disMissProgressDialog(this@ServiceCampaignAddActivity)
                        Progresss.stop()
                        if (response.responseCode == 200) {

                            if (ServiceEdit){
                                val data = Intent()
                                data.putExtra("editCampaign", true)
                                setResult(Activity.RESULT_OK, data)
                                finishAfterTransition()
                                return
                            }
                            androidextention.alertBoxFinish(response.responseMessage,this@ServiceCampaignAddActivity,this@ServiceCampaignAddActivity)


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
                                this@ServiceCampaignAddActivity
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
                val request  = AddCampaignOnServiceRequest()





                request.serviceId =  serviceNameId
                request.serviceDiscountedPrice =  binding.discountedPrice.text.toString()
                request.campaignOn = "SERVICE"
                request.startDate = DateFormat.convertAndSubtractTime(DateFormat.formatToISOString(dateString = binding.startDateTv.text.toString(), timeString = binding.startTimeTv.text.toString()).toString())
                request.endDate = DateFormat.convertAndSubtractTime(DateFormat.formatToISOString(dateString = binding.endDateTV.text.toString(), timeString = binding.endTimeTv.text.toString()).toString())

                serviceManager.addCampaignServiceApi(callBack, request)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }





}