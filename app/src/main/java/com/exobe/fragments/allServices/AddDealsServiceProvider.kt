package com.exobe.fragments.allServices

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.permission.RequestPermission
import com.example.validationpractice.utlis.FormValidation
import com.exobe.activities.subservices_fragment
import com.exobe.adaptor.servicesAdaptor.ServiceListOpenPopUp
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.CommonFunctions.hideKeyboard
import com.exobe.utils.ImageRotation
import com.exobe.androidextention
import com.exobe.customClicks.serviceNameClickLisntner
import com.exobe.dialogs.productDialog
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.EditDealRequest
import com.exobe.entity.response.*
import com.exobe.entity.response.customer.AddDealsOfServices
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.util.DateFormat
import com.exobe.validations.DialogUtils
import com.exobe.validations.TimeClass
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddDealsServiceProvider(var flagEdit: String, var _id: String) : Fragment(),serviceNameClickLisntner {

    lateinit var viewDealLL: LinearLayout
    lateinit var startdate: RelativeLayout
    lateinit var end_date: RelativeLayout
    lateinit var image_Relative: RelativeLayout
    lateinit var textstartdate: TextView
    lateinit var textenddate: TextView
    lateinit var AmmountDeals: EditText
    lateinit var MenuClick: ImageView
    lateinit var title: TextView
    lateinit var back: ImageView
    lateinit var edit_deal: LinearLayout
    lateinit var save: Button
    lateinit var edit: Button
    lateinit var mainHeader: RelativeLayout
    lateinit var subcategory_image: ImageView
    val c = Calendar.getInstance()
    var yearset = 0
    var monthset = 0
    var day = 0
    var datePicker: DatePickerDialog? = null
    lateinit var Category: TextView
    lateinit var SubCategory: TextView
    lateinit var selectService: TextView
    lateinit var Image_tv: TextView

    lateinit var Category_TV: TextView
    lateinit var SubCategory_TV: TextView
    lateinit var selectService_tv: TextView
    lateinit var Ammount_TV: TextView
    lateinit var Startdate_TV: TextView
    lateinit var Enddate_TV: TextView
    lateinit var imageAdding: LinearLayout
    lateinit var end_time_ll: RelativeLayout
    lateinit var start_time_ll: RelativeLayout
    lateinit var end_time: TextView
    lateinit var start_time: TextView
    lateinit var actualAmount: TextView
    lateinit var percent: EditText
    lateinit var percentError: TextView


    lateinit var progressBarAdddeals: ProgressBar
    var progressBarpop: ProgressBar? = null
    var flag = ""
    var CategoryIds = ""
    var SubCategoryIds = ""
    var serviceIdRequest = ""

    var no_notification: LinearLayout? = null

    private lateinit var dialog: Dialog
    private lateinit var recyclerView: RecyclerView
    lateinit var adapterServiceList: ServiceListOpenPopUp
    var data : List<ServiceProviderListData> = listOf()

    val CAMERA_PERM_CODE = 101
    var imageFile: File? = null
    var imagePath = ""
    var photoURI: Uri? = null
    lateinit var imageparts: MultipartBody.Part
    private val GALLERY = 1
    private var CAMERA: Int = 2
    lateinit var image: Uri
    var requestImage: String = ""
    var thumbnail = ""
    var USER_IMAGE_UPLOADED = false
    lateinit var internet_connection: RelativeLayout

    var startTime = ""
    var endTime = ""
    var startDate = ""
    var endDate = ""

    private var timer: Timer? = null
    var productPrice = 0.0


    private var isUpdatingFields = false

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add_deals_serviceprovider, container, false)
        internet_connection = activity?.findViewById(R.id.internet_connection)!!


        viewDealLL = view.findViewById(R.id.viewDealLL)
        imageAdding = view.findViewById(R.id.imageAdding)
        end_time_ll = view.findViewById(R.id.end_time_ll)
        start_time_ll = view.findViewById(R.id.start_time_ll)
        end_time = view.findViewById(R.id.end_time)
        start_time = view.findViewById(R.id.start_time)
        selectService_tv = view.findViewById(R.id.selectService_tv)
        selectService = view.findViewById(R.id.selectService)
        startdate = view.findViewById(R.id.startdate)
        end_date = view.findViewById(R.id.end_date)
        textstartdate = view.findViewById(R.id.textstartdate)
        textenddate = view.findViewById(R.id.textenddate)
        save = view.findViewById(R.id.save)
        title = activity?.findViewById(R.id.title)!!
        back = activity?.findViewById(R.id.back)!!
        edit_deal = activity?.findViewById(R.id.edit_deal)!!
        AmmountDeals = view.findViewById(R.id.AmmountDeals)
        MenuClick = activity?.findViewById(R.id.MenuClick)!!
        progressBarAdddeals = view.findViewById(R.id.adddeal_progressbar)
        image_Relative = view.findViewById(R.id.image_Relative)
        edit = view.findViewById(R.id.edit)
        Category = view.findViewById(R.id.Category)
        SubCategory = view.findViewById(R.id.SubCategory)
        subcategory_image = view.findViewById(R.id.subcategory_image)
        Category_TV = view.findViewById(R.id.Category_tv)
        SubCategory_TV = view.findViewById(R.id.SubCategory_tv)
        Ammount_TV = view.findViewById(R.id.AmmountDeals_tv)
        Startdate_TV = view.findViewById(R.id.startdate_tv)
        Enddate_TV = view.findViewById(R.id.enddate_tv)
        Image_tv = view.findViewById(R.id.Image_tv)
        actualAmount = view.findViewById(R.id.actualAmount)
        percent = view.findViewById(R.id.percent)
        percentError = view.findViewById(R.id.percentError)
        internet_connection.visibility = View.GONE


        SubCategory.isEnabled = false
        subcategory_image.isEnabled = false
        Category.isEnabled = false
        actualAmount.isEnabled = false




        back.visibility = View.VISIBLE
        MenuClick.visibility = View.GONE

        percent.imeOptions = EditorInfo.IME_ACTION_DONE
        AmmountDeals.imeOptions = EditorInfo.IME_ACTION_DONE


        percent.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (percent.text.isEmpty()){
                    AmmountDeals.setText("")
                }else{
                    calculateDiscountPrice(productPrice, percent.text.toString().toInt(), AmmountDeals)
                }

                hideKeyboard(requireActivity())
                true
            } else {
                false
            }
        }
        AmmountDeals.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (AmmountDeals.text.isEmpty()){
                    percent.setText("")
                }else{
                    calculateDiscountPercentage(productPrice, AmmountDeals.text.toString().toDouble(), percent)
                }
                hideKeyboard(requireActivity())
                true
            } else {
                false
            }
        }









        back.setSafeOnClickListener {
            edit_deal.visibility = View.GONE
            parentFragmentManager.popBackStack()

        }

        selectService.setSafeOnClickListener {
            flag = "SelectService"
            openPopUp(flag)
        }

        subcategory_image.setSafeOnClickListener {

            RequestPermission.requestMultiplePermissions(requireContext())
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERM_CODE
                )
            } else {
                selectImage()
            }

        }

        val calendar: Calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val date = calendar.get(Calendar.DAY_OF_MONTH)




        startdate.setSafeOnClickListener {

            textenddate.text = ""

            datePicker = DatePickerDialog(
                requireContext(),
                R.style.DatePickerTheme,
                { view, year, monthOfYear, dayOfMonth ->
                    c.set(year, monthOfYear, dayOfMonth)
                    textstartdate.text = DateFormat.dealTimeFormatter("$year-${monthOfYear + 1}-$dayOfMonth")
                    yearset = year
                    monthset = monthOfYear
                    day = dayOfMonth
                },
                year,
                month,
                date
            )
            datePicker!!.getDatePicker().setMinDate(System.currentTimeMillis() - 1000)
            datePicker!!.show()


        }
        start_time_ll.setSafeOnClickListener { TimeClass.showTimePickerDialog(requireContext(),start_time, textstartdate,textenddate) }
        end_time_ll.setSafeOnClickListener {TimeClass.showTimePickerDialog(requireContext(),end_time,textstartdate,textenddate) }

        if (textstartdate.text != null) {
            getNewStartTime()
        }


        end_date.setSafeOnClickListener {

            datePicker = DatePickerDialog(
                requireContext(),
                R.style.DatePickerTheme,
                DatePickerDialog.OnDateSetListener
                { view, year, monthOfYear, dayOfMonth ->
                    textenddate.text = DateFormat.dealTimeFormatter("$year-${monthOfYear + 1}-$dayOfMonth")
                },
                year,
                month,
                date
            )
            datePicker!!.getDatePicker().minDate = c.timeInMillis
            datePicker!!.show()


        }

        if (textenddate.text != null) {
            getNewEndTime()
        }


        save.setSafeOnClickListener {
            FormValidation.AddDeals(
                image_Relative,
                USER_IMAGE_UPLOADED,
                Image_tv,
                Category,
                Category_TV,
                SubCategory,
                SubCategory_TV,
                percent,
                percentError,
                startdate,
                textstartdate,
                Startdate_TV,
                end_date,
                textenddate,
                Enddate_TV,
                selectService,
                selectService_tv,
                start_time,
                end_time
            )
            TimeClass.validateFields(textstartdate,start_time,start_time_ll,
                textenddate,end_time,end_time_ll)


            if (Category.text.isNotEmpty() &&
                SubCategory.text.isNotEmpty() &&
                AmmountDeals.text.isNotEmpty() &&
                textstartdate.text.isNotEmpty() &&
                selectService.text.isNotEmpty() &&
                textenddate.text.isNotEmpty() &&
                start_time.text.isNotEmpty() &&
                end_time.text.isNotEmpty()

            ) {

                if (flagEdit == "edit") {
                    editDealsApi()
                } else {
                    AddDealsOfService()
                }
            }else{
                TimeClass.validateFields(textstartdate,start_time,start_time_ll,
                    textenddate,end_time,end_time_ll)
            }

        }

        edit.setSafeOnClickListener {
            if (requestImage != "") {

                FormValidation.editDeals(
                    image_Relative,
                    USER_IMAGE_UPLOADED,
                    Image_tv,
                    percent,
                    percentError,
                    startdate,
                    textstartdate,
                    Startdate_TV,
                    end_date,
                    textenddate,
                    Enddate_TV,
                    start_time,
                    end_time
                    )
                if (
                    !AmmountDeals.text.isEmpty() &&
                    !textstartdate.text.isEmpty() &&
                    !textenddate.text.isEmpty() &&
                    !start_time.text.isEmpty() &&
                    !end_time.text.isEmpty()

                ) {
                    editDealsApi()
                }


            } else {

                FormValidation.editDeals(
                    image_Relative,
                    USER_IMAGE_UPLOADED,
                    Image_tv,
                    AmmountDeals,
                    Ammount_TV,
                    startdate,
                    textstartdate,
                    Startdate_TV,
                    end_date,
                    textenddate,
                    Enddate_TV,
                    start_time,
                    end_time,

                    )
                if (
                    !AmmountDeals.text.isEmpty() &&
                    !textstartdate.text.isEmpty() &&
                    !textenddate.text.isEmpty() &&
                    !start_time.text.isEmpty() &&
                    !end_time.text.isEmpty()

                ) {
                    createImageLinkApi()
                }


            }
        }

        edit_deal.setSafeOnClickListener {
            title.text = "Edit Deal"
            edit.visibility = View.VISIBLE
        }

        if (flagEdit == "edit") {
            title.text = "View Deal"
            edit_deal.visibility = View.VISIBLE
            selectService.isEnabled = false
            percent.isEnabled = true
            AmmountDeals.isEnabled = true
            viewDealDetailsApi()
            save.visibility = View.GONE
        } else {
            viewDealLL.isVisible= true
            title.text = "Add Deal"

        }

        return view
    }



    private fun getNewStartTime() {
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val formattedTime: String = when {
                hourOfDay == 0 -> {
                    if (minute < 10) {
                        "${hourOfDay + 12}:0${minute} AM"
                    } else {
                        "${hourOfDay + 12}:${minute} AM"
                    }
                }

                hourOfDay > 12 -> {
                    if (minute < 10) {
                        "${hourOfDay - 12}:0${minute} PM"
                    } else {
                        "${hourOfDay - 12}:${minute} PM"
                    }
                }

                hourOfDay == 12 -> {
                    if (minute < 10) {
                        "${hourOfDay}:0${minute} PM"
                    } else {
                        "${hourOfDay}:${minute} PM"
                    }
                }

                else -> {
                    if (minute < 10) {
                        "${hourOfDay}:${minute} AM"
                    } else {
                        "${hourOfDay}:${minute} AM"
                    }
                }
            }

            start_time.text = formattedTime
            //                    startTime = DateFormat.getNewDealTime(formattedTime).toString()
        }


    }

    private fun getNewEndTime() {
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            // logic to properly handle
            // the picked timings by user
            val formattedTime: String = when {
                hourOfDay == 0 -> {
                    if (minute < 10) {
                        "${hourOfDay + 12}:0${minute} AM"
                    } else {
                        "${hourOfDay + 12}:${minute} AM"
                    }
                }

                hourOfDay > 12 -> {
                    if (minute < 10) {
                        "${hourOfDay - 12}:0${minute} PM"
                    } else {
                        "${hourOfDay - 12}:${minute} PM"
                    }
                }

                hourOfDay == 12 -> {
                    if (minute < 10) {
                        "${hourOfDay}:0${minute} PM"
                    } else {
                        "${hourOfDay}:${minute} PM"
                    }
                }

                else -> {
                    if (minute < 10) {
                        "${hourOfDay}:${minute} AM"
                    } else {
                        "${hourOfDay}:${minute} AM"
                    }
                }
            }
            end_time.text = formattedTime
            //                    endTime = DateFormat.getNewDealTime(formattedTime).toString()
        }


    }



    @SuppressLint("InflateParams", "SetTextI18n")
    fun openPopUp(flag: String) {

        try {
            val binding = LayoutInflater.from(requireContext()).inflate(R.layout.pop_lists, null)
            dialog = DialogUtils().createDialog(requireContext(), binding.rootView, 0)!!
            recyclerView = binding.findViewById(R.id.popup_recyclerView)
            no_notification = binding.findViewById(R.id.no_notification)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            progressBarpop = binding.findViewById(R.id.progressbar_pop)


            val dialogTitle = binding.findViewById<TextView>(R.id.popupTitle)
            val dialogBackButton = binding.findViewById<ImageView>(R.id.BackButton)
            dialogBackButton.setSafeOnClickListener { dialog.dismiss() }


            val searchEditText = binding.findViewById<EditText>(R.id.search_bar_edittext_popuplist)

            searchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(text: Editable?) {
                    filterData(text.toString())
                }

            })


            when (flag) {
                "SelectService" -> {
                    dialogTitle.text = "Services"
                    servicesListApi()
                }
            }



            dialog.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }




    private fun filterData(searchText: String) {
        val filteredList = data.filter { item ->
            try {
                item.serviceName.contains(searchText, ignoreCase = true)
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
        adapterServiceList.filterList(filteredList)
    }



    fun editDealsApi() {
        if (androidextention.isOnline(requireContext())) {
            progressBarAdddeals.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<EditDealsResponse> =
                ApiCallBack<EditDealsResponse>(object : ApiResponseListener<EditDealsResponse> {
                    override fun onApiSuccess(response: EditDealsResponse, apiName: String?) {
                        if (response.responseCode == 200) {
                            progressBarAdddeals.visibility = View.GONE
                            try {
                                edit_deal.visibility = View.GONE
                                subservices_fragment.apiCallFlag = true
                                parentFragmentManager.let {
                                    productDialog("Deals Edit Successfully", "Deal").show(
                                        it,
                                        "MyCustomFragment"
                                    )
                                }
                                parentFragmentManager.popBackStack()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressBarAdddeals.visibility = View.GONE
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
                        progressBarAdddeals.visibility = View.GONE
                        Toast.makeText(requireContext(), "${failureMessage}", Toast.LENGTH_LONG)
                            .show()
                    }

                }, "EditDealsApi", requireContext())


            val request = EditDealRequest()
            val images = ArrayList<String>()
            images.add(requestImage)
            request.dealId = _id
            request.dealImage = images
            request.dealDiscount = percent.text.toString().toInt()
            request.thumbnail = thumbnail
            val price = AmmountDeals.text.toString()
            request.dealPrice = price.toDouble()
            request.dealsFor = "CUSTOMER"
                startDate = "${textstartdate.text} ${start_time.text}"
                endDate = "${textenddate.text} ${end_time.text}"


            request.dealStartTime = DateFormat.createDealTimeToIos(startDate)
            request.dealEndTime = DateFormat.createDealTimeToIos(endDate)



            try {
                serviceManager.editDealApi(callBack, request)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }
    }

    private fun viewDealDetailsApi() {

        if (androidextention.isOnline(requireContext())) {
            progressBarAdddeals.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<viewdeals_response> =
                ApiCallBack(object :
                    ApiResponseListener<viewdeals_response> {
                    override fun onApiSuccess(response: viewdeals_response, apiName: String?) {
                        progressBarAdddeals.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                viewDealLL.isVisible = true
                                var data = response.result!!

                                imageAdding.visibility = View.GONE
                                subcategory_image.isVisible = true


                                requestImage = data.dealImage[0]
                                thumbnail = data.thumbnail
                                Glide.with(requireContext()).load(data.dealImage[0]).into(subcategory_image)
                                Category.text = data.serviceCategoryId?.categoryName
                                SubCategory.text = data.serviceSubCategoryId?.subCategoryName
                                selectService.text = data.serviceId?.serviceName
                                SubCategoryIds = data.serviceSubCategoryId?._id.toString()
                                serviceIdRequest = data.serviceId?._id.toString()
                                CategoryIds = data.serviceCategoryId?._id.toString()
                                actualAmount.text =if (data.serviceId?.price == data.serviceId!!.price.toInt().toDouble()) {
                                    data.serviceId!!.price.toInt().toString()
                                } else {
                                    data.serviceId!!.price.toString()
                                }
                                productPrice =  data.serviceId?.price?.toDouble()!!
                                AmmountDeals.text = Editable.Factory.getInstance().newEditable(data.dealPrice.toString())

                                percent.text  =  Editable.Factory.getInstance().newEditable(data.dealDiscount!!)
                                textstartdate.text = DateFormat.getDealDate(data.dealStartTime)
                                textenddate.text = DateFormat.getDealDate(data.dealEndTime)
                                start_time.text = DateFormat.showDealTime(data.dealStartTime)
                                end_time.text = DateFormat.showDealTime(data.dealEndTime)

                                startTime = DateFormat.showDealTime(data.dealStartTime).toString()
                                endTime = DateFormat.showDealTime(data.dealEndTime).toString()

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        androidextention.disMissProgressDialog(activity)
                        progressBarAdddeals.visibility = View.GONE
                        viewDealLL.isVisible = false
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        androidextention.disMissProgressDialog(activity)
                        progressBarAdddeals.visibility = View.GONE

                    }

                }, "viewServiceDetailsApi", requireContext())

            try {
                serviceManager.customerdealsViewApi(callBack, this._id)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }
    }



    private fun servicesListApi() {
        if (androidextention.isOnline(requireContext())) {
            progressBarpop?.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<ServiceProviderListResponse> =
                ApiCallBack(object :
                    ApiResponseListener<ServiceProviderListResponse> {

                    override fun onApiSuccess(
                        response: ServiceProviderListResponse,
                        apiName: String?
                    ) {
                        if (response.responseCode == 200) {
                            try {
                                progressBarpop?.visibility = View.GONE
                                data = data +  response.result
                                setAdapterServiceListName(response.result)


                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressBarpop?.visibility = View.GONE
                        no_notification?.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
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
                        progressBarpop?.visibility = View.GONE
                        no_notification?.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }

                }, "serviceListApi", requireContext())
            try {
                serviceManager.serviceListByUserId(callBack)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

        }
    }


    fun setAdapterServiceListName(result: ArrayList<ServiceProviderListData>) {
        adapterServiceList = ServiceListOpenPopUp(requireContext(), result, flag, this)
        recyclerView.adapter = adapterServiceList
    }



    fun createImageLinkApi() {
        if (androidextention.isOnline(requireContext())) {
            progressBarAdddeals.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<DocumentResponse> =
                ApiCallBack<DocumentResponse>(object :
                    ApiResponseListener<DocumentResponse> {
                    override fun onApiSuccess(response: DocumentResponse, apiName: String?) {
                        if (response.responseCode == 200) {
                            requestImage = response.result?.mediaUrl!!
                            thumbnail = response.result?.thumbnail.toString()

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressBarAdddeals.visibility = View.GONE
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
                        progressBarAdddeals.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "${failureMessage}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, "createMultiImageLinkApi", requireContext())
            try {
                serviceManager.singleUploadImageApiService(callBack, imageparts)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

        }
    }


    fun AddDealsOfService() {

        if (androidextention.isOnline(requireContext())) {

            progressBarAdddeals.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<AddDealsOfServices> =
                ApiCallBack<AddDealsOfServices>(object :
                    ApiResponseListener<AddDealsOfServices> {
                    override fun onApiSuccess(
                        response: AddDealsOfServices,
                        apiName: String?
                    ) {
                        progressBarAdddeals.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                Toast.makeText(
                                    requireContext(),
                                    "Added Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                parentFragmentManager.popBackStack()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressBarAdddeals.visibility = View.GONE
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
                        progressBarAdddeals.visibility = View.GONE
                    }
                }, "SubCategoryDeal", requireContext())
            val jsonObject = JsonObject()
            var price = AmmountDeals.text.toString()
            val dealPrice = price.toDouble()
            val subcategoryimage = requestImage
            startDate = "${textstartdate.text} ${start_time.text}"
            endDate = "${textenddate.text} ${end_time.text}"
            jsonObject.addProperty("dealImage", subcategoryimage)
            jsonObject.addProperty("thumbnail", thumbnail)
            jsonObject.addProperty("serviceCategoryId", CategoryIds)
            jsonObject.addProperty("serviceSubCategoryId", SubCategoryIds)
            jsonObject.addProperty("serviceId", serviceIdRequest)
            jsonObject.addProperty("dealPrice", dealPrice)
            jsonObject.addProperty("dealDiscount",  percent.text.toString().toInt())
            jsonObject.addProperty("dealStartTime", DateFormat.createDealTimeToIos(startDate))
            jsonObject.addProperty("dealEndTime", DateFormat.createDealTimeToIos(endDate))
            jsonObject.addProperty("dealsFor", "CUSTOMER")
            try {
                serviceManager.addDealServiceProviderApi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun selectImage() {
        // on below line we are creating a new bottom sheet dialog.
        val dialog = BottomSheetDialog(requireContext())

        // on below line we are inflating a layout file which we have created.
        val view = layoutInflater.inflate(R.layout.choose_camera_bottom_sheet, null)

        // below line is use to set cancelable to avoid
        // closing of dialog box when clicking on the screen.
        dialog.setCancelable(true)

        val CameraButton = view.findViewById<ImageView>(R.id.choose_from_camera)
        CameraButton.setSafeOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(requireContext().getPackageManager()) != null) {
                try {
                    imageFile = createImageFile()!!
                } catch (ex: IOException) {
                }
                // Continue only if the File was successfully created
                if (imageFile != null) {
                    photoURI = FileProvider.getUriForFile(
                        requireContext(),
                        "com.exobe.fileprovider",
                        imageFile!!
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, CAMERA)
                    dialog.dismiss()
                }
            }
        }

        val GalleryButton = view.findViewById<ImageView>(R.id.choose_from_gallery)
        GalleryButton.setSafeOnClickListener {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY)
            dialog.dismiss()
        }

        // on below line we are setting
        // content view to our view.
        dialog.setContentView(view)

        // on below line we are calling
        // a show method to display a dialog.
        dialog.show()
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )

        imagePath = image.absolutePath
        return image
    }

    private fun getPathFromURI(contentUri: Uri?): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            requireContext().getContentResolver().query(contentUri!!, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(column_index)
        }
        cursor.close()
        return res
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED) {
            return
        } else {
            if (requestCode == GALLERY) {
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    if (data != null) {
                        image = data.data!!
                        val path = getPathFromURI(image)

                        if (path != null) {
                            imageFile = File(path)
                            Glide.with(this).load(imageFile).into(subcategory_image)
                            subcategory_image.isEnabled = false
                        }
                        var requestGalleryImageFile: RequestBody =
                            RequestBody.create("image/*".toMediaTypeOrNull(), imageFile!!)
                        imageparts =
                            MultipartBody.Part.createFormData(
                                "uploaded_file",
                                imageFile!!.getName(),
                                requestGalleryImageFile
                            )
                        USER_IMAGE_UPLOADED = true
//                    if(USER_IMAGE_UPLOADED) {
//                        uploadUserImageApi()
//                    }
                    }

                }
            } else if (requestCode == CAMERA) {
                if (resultCode == AppCompatActivity.RESULT_OK) {

                    try {

                        imageFile = File(imagePath)
                        var finalBitmap =
                            ImageRotation.modifyOrientation(ImageRotation.getBitmap(imagePath)!!, imagePath)
                        var imageUri = ImageRotation.getImageUri(requireContext(), finalBitmap!!)
                        var getRealPath = ImageRotation.getRealPathFromURI(requireContext(), imageUri)
                        imageFile = File(getRealPath)
                        Glide.with(this).load(imageFile).into(subcategory_image)
                        subcategory_image.isEnabled = false
//                        ProfileImage.setImageURI(Uri.fromFile(imageFile))
                        var requestGalleryImageFile: RequestBody =
                            RequestBody.create("image/*".toMediaTypeOrNull(), imageFile!!)
                        imageparts =
                            MultipartBody.Part.createFormData(
                                "uploaded_file",
                                imageFile!!.getName(),
                                requestGalleryImageFile
                            )
                        USER_IMAGE_UPLOADED = true
//                    if(USER_IMAGE_UPLOADED) {
//                        uploadUserImageApi()
//                    }
                    } catch (e: java.lang.Exception) {
                    }
                }
            }
        }
    }


    fun calculateDiscountPrice(
        actualAmount: Double,
        discountPercentage: Int,
        dealPrice: EditText
    ) {
        val discountAmount = actualAmount * (discountPercentage.toDouble() / 100)
        val amount = (actualAmount - discountAmount).toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()

        GlobalScope.launch(Dispatchers.Main) {
            dealPrice.setText(amount.toString())
        }
    }

    fun calculateDiscountPercentage(actualAmount: Double, dealAmount: Double,dealPrice: EditText){
        val percentage = ((actualAmount - dealAmount) / actualAmount) * 100
        val discount  =  if (percentage == percentage.toInt().toDouble()) {
            percentage.toInt()
        } else {
            percentage
        }

        dealPrice.setText(discount.toString())
    }



    override fun serviceNameClick(
        categoryName: String, categoryId: String,
        subCategoryId: String,
        subCategoryName: String,
        price: Int,
        serviceName: String,
        image: String,
        serviceId: String
    ) {
        serviceIdRequest  = serviceId
        requestImage = image
        thumbnail = image
        CategoryIds = categoryId
        SubCategoryIds = subCategoryId
        selectService.text = serviceName
        Category.text = categoryName
        SubCategory.text = subCategoryName
        actualAmount.text = price.toString()
        productPrice = price.toDouble()
        imageAdding.visibility = View.GONE
        subcategory_image.isVisible = true
        percent.isEnabled = true
        AmmountDeals.isEnabled = true
        Glide.with(this).load(requestImage).placeholder(R.drawable.dummyproduct).into(subcategory_image)

        dialog.dismiss()




    }




}


