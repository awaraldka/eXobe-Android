package com.exobe.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.example.complyany.SpinnerAdapter1
import com.exobe.permission.RequestPermission
import com.exobe.activities.search_deals_composer
import com.exobe.Adapter.DealImageCollectionAdaptor
import com.exobe.modelClass.CategoryModel
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.bottomSheet.choosePhotoBottomSheet
import com.exobe.customClicks.AddProductListener
import com.exobe.customClicks.DealImageRemoveListener
import com.exobe.dialogs.productDialog
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.AddDealRequest
import com.exobe.entity.request.EditDealRequest
import com.exobe.entity.response.*
import com.exobe.entity.response.addproduct.MultipartImageResponse
import com.exobe.entity.response.customer.WishListProductDetails
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.util.DateFormat
import com.exobe.util.DateFormat.Companion.dealsdate
import com.exobe.util.DateFormat.Companion.showDealTime
import com.exobe.validations.TimeClass
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*


class AddDealsFragments : Fragment(), ApiResponseListener<CategoryList_response>,
    AddProductListener,
    DealImageRemoveListener {
    lateinit var addImage: LinearLayout
    lateinit var imagesRv: RecyclerView

    lateinit var mContext: Context
    lateinit var tv_startdate: TextView
    lateinit var tv_end_date: TextView
    lateinit var start_time: TextView
    lateinit var end_time: TextView
    lateinit var errorTvProductName: TextView
    lateinit var productCategory_spinner: Spinner
    lateinit var Sub_Category_spinner: Spinner
    var datePicker: DatePickerDialog? = null
    var yearset = 0
    var monthset = 0
    var day = 0
    var latestProductList: ArrayList<String> = ArrayList()
    var categoryModel = ArrayList<CategoryModel>()
    var subCategoryListModal = ArrayList<CategoryModel>()
    var imageparts: ArrayList<MultipartBody.Part> = ArrayList()
    var imageArray: ArrayList<String> = ArrayList()
    private val GALLERY = 1
    private var CAMERA: Int = 2
    lateinit var image: Uri
    lateinit var imageFile: File
    lateinit var progressbar: ProgressBar

    val c = Calendar.getInstance()
    var dealsId = ""
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var addDealsField: LinearLayout
    lateinit var category_spinner: LinearLayout
    lateinit var subcategory_spinner: LinearLayout
    lateinit var Category_tv: TextView
    lateinit var SubCategory_tv: TextView
    lateinit var save: Button
    var flag = ""
    lateinit var ProductTitle: EditText
    lateinit var description: EditText

    //    lateinit var retail_price: EditText
    lateinit var search_button: TextView
    lateinit var Category_errortv: TextView
    lateinit var subCategory_error_tv: TextView
    lateinit var description_error: TextView

    //    lateinit var retailprice_error: TextView
//    lateinit var qty_available_error: TextView
    lateinit var ll_subcategory: LinearLayout
    lateinit var ll_category: LinearLayout
    lateinit var dealLayout_LL: LinearLayout

    //    lateinit var qty: EditText
    lateinit var dealDiscount: EditText
    lateinit var discount: TextView
    lateinit var endDateTime_error: TextView
    lateinit var startDateTime_error: TextView
    lateinit var actualAmount: EditText
    lateinit var dealPrice: EditText
    lateinit var dealAmountError: TextView

    var imageCounter = 0
    var requestCategoryId = ""
    var requestSubCategoryId = ""
    var addDealsByAdmid = false
    var addDealsByAdmin = false
    var flagForAdded = ""
    var edit_productId = ""
    var edit_dealId = ""
    var thumbnailImage = ""
    var startTime = ""
    var endTime = ""
    var startDate = ""
    var endDate = ""
    lateinit var dealImageCollectionAdaptor: DealImageCollectionAdaptor

    var dealImages = ArrayList<String>()
    lateinit var internet_connection: RelativeLayout
    var productPrice = 0.0
    private var timer: Timer? = null

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add_deals, container, false)
        mContext = activity?.applicationContext!!

        addImage = view.findViewById(R.id.add_image)
        imagesRv = view.findViewById(R.id.imagesRv)


        category_spinner = view.findViewById(R.id.category_spinner)
        subcategory_spinner = view.findViewById(R.id.subcategory_spinner)
        Category_tv = view.findViewById(R.id.Category_tv)
        SubCategory_tv = view.findViewById(R.id.SubCategory_tv)
        search_button = view.findViewById(R.id.search_button)
        tv_startdate = view.findViewById(R.id.tv_startdate)
        tv_end_date = view.findViewById(R.id.tv_end_date)
        start_time = view.findViewById(R.id.start_time)
        end_time = view.findViewById(R.id.end_time)
        addDealsField = view.findViewById(R.id.addDealsField)
        productCategory_spinner = view.findViewById(R.id.productCategory_spinner)
        save = view.findViewById(R.id.save)
        ProductTitle = view.findViewById(R.id.ProductTitle)
        Sub_Category_spinner = view.findViewById(R.id.SubCategory_spinner)
        description = view.findViewById(R.id.description)
        dealLayout_LL = view.findViewById(R.id.dealLayout_LL)
        errorTvProductName = view.findViewById(R.id.errorTvProductName)
        progressbar = view.findViewById(R.id.progressbar)
        Category_errortv = view.findViewById(R.id.Category_errortv)
        subCategory_error_tv = view.findViewById(R.id.subCategory_error_tv)
        description_error = view.findViewById(R.id.description_error)
        ll_subcategory = view.findViewById(R.id.ll_subcategory)
        ll_category = view.findViewById(R.id.ll_category)
        dealDiscount = view.findViewById(R.id.dealDiscount)
        discount = view.findViewById(R.id.discount)
        endDateTime_error = view.findViewById(R.id.endDateTime_error)
        startDateTime_error = view.findViewById(R.id.startDateTime_error)
        actualAmount = view.findViewById(R.id.actualAmount)
        dealPrice = view.findViewById(R.id.dealPrice)
        dealAmountError = view.findViewById(R.id.dealAmountError)
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        internet_connection.visibility = View.GONE

        SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.ADMIN_DEALS_ID)
            .let {
                dealsId = it.toString()
            }

        requireArguments().getString("flag").let {
            flagForAdded = it.toString()
        }

        dealDiscount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                timer?.cancel()

            }

            override fun afterTextChanged(s: Editable?) {
                val discount = s.toString()
                if (discount.isNotEmpty()) {
                    timer = Timer()
                    timer!!.schedule(object : TimerTask() {
                        override fun run() {
                            calculateDiscountPrice(productPrice, discount.toInt(), dealPrice)
                        }
                    }, 600)

                } else {
                    dealPrice.setText("")
                }
            }
        })


        if (requireArguments().getString("productid") != null) {
            edit_productId = requireArguments().getString("productid").toString()
        }
        if (requireArguments().getString("dealid") != null) {
            edit_dealId = requireArguments().getString("dealid").toString()
        }
        if (flagForAdded == "Add Deal") {

            search_button.setSafeOnClickListener {

                if (ProductTitle.text.isEmpty()) {
                    errorTvProductName.text = "*Please enter product name."

                } else {
                    errorTvProductName.text = ""
                    val bundle = Bundle()
                    bundle.putString(
                        "DEAL_SEARCH_TEXT",
                        ProductTitle.text.toString().trim { it <= ' ' })
                    val fragObj = search_deals_composer()
                    fragObj.arguments = bundle
                    requireFragmentManager().beginTransaction()
                        .replace(R.id.FrameLayout, fragObj, "Search").addToBackStack("Search")
                        .commit()
                }
            }

            save.setSafeOnClickListener {
                var priceRegex = "^\\d{0,8}(\\.\\d{1,4})?\$"
                if (!dealsId.equals("")) {

                    ll_subcategory.visibility = View.GONE
                    Category_tv.visibility = View.VISIBLE
                    ll_category.visibility = View.GONE
                    SubCategory_tv.visibility = View.VISIBLE

                    if (ProductTitle.text.isEmpty()) {
                        errorTvProductName.text = "*Please enter product name."
                    } else if (description.text.isEmpty()) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = "*Please enter the description."

                    } else if (dealDiscount.text.isEmpty()) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        discount.text = "*Please enter discount"

                    } else if (dealDiscount.text.trimStart().startsWith("0")) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        discount.text = "*Please enter valid deal percentage."

                    } else if (tv_startdate.text.isEmpty()) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        discount.text = ""
                        startDateTime_error.text = "*Please enter start date"
                    } else if (start_time.text.isEmpty()) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        discount.text = ""
                        startDateTime_error.text = "*Please enter start time"
                    } else if (tv_end_date.text.isEmpty()) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        discount.text = ""
                        startDateTime_error.text = ""
                        endDateTime_error.text = "*Please enter end date"
                    } else if (end_time.text.isEmpty()) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        discount.text = ""
                        startDateTime_error.text = ""
                        endDateTime_error.text = "*Please enter end time"
                    } else {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        discount.text = ""
                        endDateTime_error.text = ""
                        startDateTime_error.text = ""
                        addDealsByAdmid = true
                        addDealsApi()
                    }
                } else {
                    if (ProductTitle.text.isEmpty()) {
                        errorTvProductName.text = "*Please enter product name."
                    } else if (imageparts.size == 0) {
                        androidextention.alertBox(
                            "Please upload atleast one image.",
                            requireContext()
                        )
                    } else if (requestCategoryId.equals("")) {
                        errorTvProductName.text = ""
                        Category_errortv.text = "*Please select category."
                    } else if (requestSubCategoryId.equals("")) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = "*Please select subcategory."
                    } else if (description.text.isEmpty()) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = "*Please enter the description."

                    } else if (dealDiscount.text.isEmpty()) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        discount.text = "*Please enter discount"

                    } else if (dealDiscount.text.trimStart().startsWith("0")) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        discount.text = "*Please enter valid deal percentage."

                    } else if (tv_startdate.text.isEmpty()) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        discount.text = ""
                        startDateTime_error.text = "*Please enter start date"
                    } else if (start_time.text.isEmpty()) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        discount.text = ""
                        startDateTime_error.text = "*Please enter start time"
                    } else if (tv_end_date.text.isEmpty()) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        discount.text = ""
                        startDateTime_error.text = ""
                        endDateTime_error.text = "*Please enter end date"
                    } else if (end_time.text.isEmpty()) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        discount.text = ""
                        startDateTime_error.text = ""
                        endDateTime_error.text = "*Please enter end time"
                    } else {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        discount.text = ""
                        endDateTime_error.text = ""
                        startDateTime_error.text = ""
                        addDealsApi()
                    }
                }


            }

        } else {
            save.text = "Update Deal"

            viewDealDetailsApi(edit_dealId)
            var priceRegex = "^\\d{0,8}(\\.\\d{1,4})?\$"
            save.setSafeOnClickListener {

                if (ProductTitle.text.isEmpty()) {
                    errorTvProductName.text = "*Please enter product name."
                } else if (description.text.isEmpty()) {
                    errorTvProductName.text = ""
                    Category_errortv.text = ""
                    subCategory_error_tv.text = ""
                    description_error.text = "*Please enter the description."

                } else if (dealDiscount.text.isEmpty()) {
                    errorTvProductName.text = ""
                    Category_errortv.text = ""
                    subCategory_error_tv.text = ""
                    description_error.text = ""
                    discount.text = "*Please enter discount"

                } else if (dealDiscount.text.trimStart().startsWith("0")) {
                    errorTvProductName.text = ""
                    Category_errortv.text = ""
                    subCategory_error_tv.text = ""
                    description_error.text = ""
                    discount.text = "*Please enter valid deal percentage."

                } else if (tv_startdate.text.isEmpty()) {
                    errorTvProductName.text = ""
                    Category_errortv.text = ""
                    subCategory_error_tv.text = ""
                    description_error.text = ""
                    discount.text = ""
                    startDateTime_error.text = "*Please enter start date"
                } else if (start_time.text.isEmpty()) {
                    errorTvProductName.text = ""
                    Category_errortv.text = ""
                    subCategory_error_tv.text = ""
                    description_error.text = ""
                    discount.text = ""
                    startDateTime_error.text = "*Please enter start time"
                } else if (tv_end_date.text.isEmpty()) {
                    errorTvProductName.text = ""
                    Category_errortv.text = ""
                    subCategory_error_tv.text = ""
                    description_error.text = ""
                    discount.text = ""
                    startDateTime_error.text = ""
                    endDateTime_error.text = "*Please enter end date"
                } else if (end_time.text.isEmpty()) {
                    errorTvProductName.text = ""
                    Category_errortv.text = ""
                    subCategory_error_tv.text = ""
                    description_error.text = ""
                    discount.text = ""
                    startDateTime_error.text = ""
                    endDateTime_error.text = "*Please enter end time"
                } else if (dealPrice.text.toString().toDouble() > productPrice) {
                    errorTvProductName.text = ""
                    Category_errortv.text = ""
                    subCategory_error_tv.text = ""
                    description_error.text = ""
                    discount.text = ""
                    startDateTime_error.text = ""
                    endDateTime_error.text = ""
                    dealAmountError.text = ""
                } else {
                    errorTvProductName.text = ""
                    Category_errortv.text = ""
                    subCategory_error_tv.text = ""
                    description_error.text = ""
                    discount.text = ""
                    endDateTime_error.text = ""
                    startDateTime_error.text = ""
                    dealAmountError.text = ""
                    addDealsByAdmid = true
                    editDealsApi()
                }

            }

        }

        if (flagForAdded == "Edit Deal") {
            dealLayout_LL.visibility = View.VISIBLE
        }


        imageparts.clear()


        // category and subCategory list added
        productCategory_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, pos: Int, id: Long
                ) {
                    var item = parent.getItemAtPosition(pos)
                    if (item.equals("Select")) {
                        requestSubCategoryId = ""
                    } else {
                        for (i in 0 until categoryModel.size) {
                            if (item.equals(categoryModel.get(i).productName)) {
                                requestCategoryId = categoryModel.get(i).id
                                subCategoryListApi(categoryModel.get(i).id)
                            }
                        }
                    }
                }

                override fun onNothingSelected(arg0: AdapterView<*>?) {
                }
            }

        Sub_Category_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, pos: Int, id: Long
            ) {
                var item = parent.getItemAtPosition(pos)
                if (item.equals("Select")) {
                    requestSubCategoryId = ""
                } else {
                    for (i in 0 until subCategoryListModal.size) {
                        if (item.equals(subCategoryListModal.get(i).productName)) {
                            requestSubCategoryId = subCategoryListModal.get(i).id
                        }
                    }
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
            }
        }


        if (dealsId.isEmpty()) {

            CategoryListApi()
            ll_subcategory.visibility = View.VISIBLE
            Category_tv.visibility = View.GONE
            ll_category.visibility = View.VISIBLE
            SubCategory_tv.visibility = View.GONE

        } else {
            viewDealsApi(dealsId)
            ll_subcategory.visibility = View.GONE
            Category_tv.visibility = View.VISIBLE
            ll_category.visibility = View.GONE
            SubCategory_tv.visibility = View.VISIBLE
        }

        fun getImages() {
            val bottomsheet = choosePhotoBottomSheet("AddProduct", this)
            bottomsheet.show(requireFragmentManager(), "bottomsheet")
        }

        setToolbar()

        back.setSafeOnClickListener {
            imageCounter = 0
            imageparts.clear()
            SavedPrefManager.saveStringPreferences(
                requireContext(),
                SavedPrefManager.ADMIN_PRODUCT_ID,
                ""
            )
            fragmentManager?.popBackStack()
        }

        var calendar: Calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var date = calendar.get(Calendar.DAY_OF_MONTH)

        datePicker = DatePickerDialog(mContext)

        startDate = "$date-${month + 1}-$year"

        endDate = "$date-${month}-$year"


        tv_startdate.setSafeOnClickListener {

            datePicker = DatePickerDialog(
                requireContext(),
                R.style.DatePickerTheme,
                DatePickerDialog.OnDateSetListener
                { view, year, monthOfYear, dayOfMonth ->
                    c.set(year, monthOfYear, dayOfMonth)
                    tv_end_date.text = ""
                    tv_startdate.text = "$year-${monthOfYear + 1}-$dayOfMonth"

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
        c.set(yearset, monthset, day)


        tv_end_date.setSafeOnClickListener {


            datePicker = DatePickerDialog(
                requireContext(),
                R.style.DatePickerTheme,
                DatePickerDialog.OnDateSetListener
                { view, year, monthOfYear, dayOfMonth ->
                    tv_end_date.text = "$year-${monthOfYear + 1}-$dayOfMonth"

                },
                year,
                month,
                date
            )
            datePicker!!.getDatePicker().minDate = c.timeInMillis


            datePicker!!.show()

        }


        addImage.setSafeOnClickListener {
            RequestPermission.requestMultiplePermissions(requireContext())
            getImages()
        }


        start_time.setSafeOnClickListener {
            TimeClass.showTimePickerDialog(
                requireContext(),
                start_time,
                tv_startdate,
                tv_end_date
            )
        }
        end_time.setSafeOnClickListener {
            TimeClass.showTimePickerDialog(
                requireContext(),
                end_time,
                tv_startdate,
                tv_end_date
            )
        }

        return view
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
        title.text = if (flagForAdded == "Edit Deal") "Update Deal" else flagForAdded
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    fun CategoryListApi() {
        if (androidextention.isOnline(mContext)) {

            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<CategoryList_response> =
                ApiCallBack<CategoryList_response>(this, "CategoryListApi", mContext)
            val jsonObject = JsonObject()
//            jsonObject.addProperty("search", "")
            try {
                serviceManager.listCategoryApi(callBack, jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }

    }

    override fun onApiSuccess(response: CategoryList_response, apiName: String?) {
        if (response.responseCode == 200) {
            categoryModel.clear()
            latestProductList.clear()
            latestProductList.add("Select")
            for (i in 0 until response.result!!.docs!!.size) {
                latestProductList.add(response.result!!.docs!!.get(i).categoryName.toString())
                categoryModel.add(
                    CategoryModel(
                        response.result!!.docs!!.get(i)._id.toString(),
                        response.result!!.docs!!.get(i).categoryName.toString()
                    )
                )
            }

            activity?.let {
                SpinnerAdapter1().mySpinnerArray(
                    it,
                    productCategory_spinner,
                    latestProductList
                )
            }
        }
    }

    override fun onApiErrorBody(response: String, apiName: String?) {
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
        Toast.makeText(requireContext(), "${failureMessage}", Toast.LENGTH_LONG).show()
    }


    fun subCategoryListApi(id: String) {
        if (androidextention.isOnline(mContext)) {
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<SubCategoryResponse> =
                ApiCallBack<SubCategoryResponse>(object : ApiResponseListener<SubCategoryResponse> {
                    override fun onApiSuccess(response: SubCategoryResponse, apiName: String?) {

                        try {
                            var subCategoryList = ArrayList<String>()
                            subCategoryList.add("Select")
                            for (i in 0 until response.result?.subCategory?.size!!) {
                                subCategoryList.add(response.result?.subCategory.get(i).subCategoryName!!)
                                subCategoryListModal.add(
                                    (com.exobe.modelClass.CategoryModel(
                                        response.result?.subCategory.get(
                                            i
                                        )._id!!,
                                        response.result?.subCategory.get(i).subCategoryName!!
                                    ))
                                )

                                activity?.let {
                                    SpinnerAdapter1().mySpinnerArray(
                                        it,
                                        Sub_Category_spinner,
                                        subCategoryList
                                    )
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }


                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
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
                        Toast.makeText(requireContext(), "${failureMessage}", Toast.LENGTH_LONG)
                            .show()

                    }

                }, "SubCategoryListApi", mContext)
            try {
                serviceManager.listSubCategoryApi(callBack, id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

        }
    }


    fun createMultiImageLinkApi() {
        if (androidextention.isOnline(mContext)) {
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<MultipartImageResponse> =
                ApiCallBack<MultipartImageResponse>(object :
                    ApiResponseListener<MultipartImageResponse> {
                    override fun onApiSuccess(response: MultipartImageResponse, apiName: String?) {
                        if (response.responseCode == 200) {
                            progressbar.visibility = View.GONE
                            try {
//                                for (i in 0 until response.result!!?.size!!.toInt()) {
                                dealImages.add(response.result?.get(0)?.mediaUrl.toString())
//                                }
//                                thumbnailImage = response.result?.get(0)?.thumbnail.toString()
                                if (dealImages.size == 0) {
                                    imagesRv.visibility = View.GONE
                                    addImage.visibility = View.VISIBLE
                                } else if (dealImages.size == 3) {
                                    imagesRv.visibility = View.VISIBLE
                                    addImage.visibility = View.GONE
                                } else {
                                    imagesRv.visibility = View.VISIBLE
                                    addImage.visibility = View.VISIBLE
                                }
                                setDealImagesAdaptor(dealImages)
//                                if (imageArray.size != 0) {
//                                    if (flagForAdded.equals("Edit Deal")) {
//                                        editDealsApi()
//                                    } else {
//                                        addDealsApi()
//                                    }
//                                }
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
                        Toast.makeText(requireContext(), "${failureMessage}", Toast.LENGTH_LONG)
                            .show()

                    }

                }, "createMultiImageLinkApi", mContext)
            try {
                serviceManager.addMultiPartImageApi(callBack, imageparts)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }
    }

    override fun addProduct(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        choosePhotoBottomSheet: choosePhotoBottomSheet,
        imagePath: String
    ) {
        try {
            if (requestCode == GALLERY) {
                if (data != null && data!!.clipData == null) {
                    try {
                        imageparts.clear()
                        image = data.data!!

                        val path = getPathFromURI(image)
                        if (path != null) {
                            imageFile = File(path)
                        }


                        choosePhotoBottomSheet.dismiss()
                        val requestGalleryImageFile: RequestBody =
                            RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
                        imageparts.add(
                            MultipartBody.Part.createFormData(
                                "uploaded_file",
                                imageFile.getName(),
                                requestGalleryImageFile
                            )
                        )
                        createMultiImageLinkApi()

                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }

            } else if (requestCode == CAMERA) {
                if (imagePath.isNotEmpty()) {
                    imageparts.clear()
                    imageFile = File(imagePath)

                    choosePhotoBottomSheet.dismiss()
                    var requestGalleryImageFile: RequestBody =
                        RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
                    imageparts.add(
                        MultipartBody.Part.createFormData(
                            "uploaded_file",
                            imageFile.getName(),
                            requestGalleryImageFile
                        )
                    )
                    createMultiImageLinkApi()

                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getPathFromURI(contentUri: Uri?): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            requireActivity().getContentResolver().query(contentUri!!, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(column_index)
        }
        cursor.close()
        return res
    }


    fun viewDealsApi(dealsId: String) {
        if (androidextention.isOnline(mContext)) {
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<WishListProductDetails> =
                ApiCallBack<WishListProductDetails>(object :
                    ApiResponseListener<WishListProductDetails> {
                    override fun onApiSuccess(
                        response: WishListProductDetails,
                        apiName: String?
                    ) {
                        if (response.responseCode == 200) {
                            progressbar.visibility = View.GONE
                            dealLayout_LL.visibility = View.VISIBLE

                            try {

                                SavedPrefManager.saveStringPreferences(
                                    requireContext(),
                                    SavedPrefManager.ADMIN_DEALS_ID,
                                    ""
                                )

                                thumbnailImage = response.result!!.thumbnail.toString()
                                if (flagForAdded == "Add Deal") {
                                    //setImages
                                    dealImages = response.result.productImage!!
                                } else {
                                    dealImages = response.result.dealImage!!
                                }

                                if (dealImages.size == 0) {
                                    imagesRv.visibility = View.GONE
                                    addImage.visibility = View.VISIBLE
                                } else if (dealImages.size == 3) {
                                    imagesRv.visibility = View.VISIBLE
                                    addImage.visibility = View.GONE
                                } else {
                                    imagesRv.visibility = View.VISIBLE
                                    addImage.visibility = View.VISIBLE
                                }
                                setDealImagesAdaptor(dealImages)

                                requestSubCategoryId =
                                    response.result?.subCategoryId?._id.toString()
                                requestCategoryId = response.result?.categoryId?._id.toString()
//                                retail_price.setText(response.result!!.price.toString())
                                actualAmount.setText(
                                    response.result.priceSizeDetails[0].price.toDouble().toString()
                                )
                                productPrice = response.result.priceSizeDetails[0].price.toDouble()
                                ProductTitle.setText(response.result?.productName)
                                Category_tv.setText(response.result?.categoryId?.categoryName)
                                SubCategory_tv.setText(response.result?.subCategoryId?.subCategoryName)
                                description.setText(response.result?.description)

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        SavedPrefManager.saveStringPreferences(
                            requireContext(),
                            SavedPrefManager.ADMIN_DEALS_ID,
                            ""
                        )
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
                        SavedPrefManager.saveStringPreferences(
                            requireContext(),
                            SavedPrefManager.ADMIN_DEALS_ID,
                            ""
                        )
                        progressbar.visibility = View.GONE
                    }

                }, "viewDealsApi", mContext)

            try {
                serviceManager.viewProductDetails(callBack, dealsId)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }
    }

    fun setDealImagesAdaptor(data: ArrayList<String>) {
        imagesRv.layoutManager = LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
        imagesRv.adapter =
            DealImageCollectionAdaptor(mContext, data, this)
    }

    fun viewDealDetailsApi(productId: String) {

        if (androidextention.isOnline(requireContext())) {
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<viewdeals_response> =
                ApiCallBack<viewdeals_response>(object :
                    ApiResponseListener<viewdeals_response> {
                    override fun onApiSuccess(response: viewdeals_response, apiName: String?) {
                        progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                search_button.visibility = View.GONE
                                ll_subcategory.visibility = View.GONE
                                Category_tv.visibility = View.VISIBLE
                                ll_category.visibility = View.GONE
                                SubCategory_tv.visibility = View.VISIBLE
                                dealLayout_LL.visibility = View.VISIBLE

                                thumbnailImage = response.result!!.thumbnail.toString()

                                if (flagForAdded == "Add Deal") {
                                    //setImages
                                    dealImages = response.result?.productId?.get(0)?.productImage!!
                                } else {
                                    dealImages = response.result?.dealImage!!
                                }
                                if (dealImages.size == 0) {
                                    imagesRv.visibility = View.GONE
                                    addImage.visibility = View.VISIBLE
                                } else if (dealImages.size == 3) {
                                    imagesRv.visibility = View.VISIBLE
                                    addImage.visibility = View.GONE
                                } else {
                                    imagesRv.visibility = View.VISIBLE
                                    addImage.visibility = View.VISIBLE
                                }
                                setDealImagesAdaptor(dealImages)

                                requestSubCategoryId =
                                    response.result.productId?.get(0)?.subCategoryId?._id!!
                                requestCategoryId =
                                    response.result.productId[0].categoryId?._id!!

                                ProductTitle.setText(response.result.productId[0].productName)
                                Category_tv.text =
                                    response.result.productId[0].categoryId?.categoryName
                                SubCategory_tv.text =
                                    response.result.productId[0].subCategoryId?.subCategoryName
                                description.setText(response.result.description)
                                actualAmount.setText(
                                    response.result.productId.getOrNull(0)!!.priceSizeDetails[0].price.toDouble()
                                        .toString()
                                )
                                productPrice =
                                    response.result.productId.getOrNull(0)!!.priceSizeDetails[0].price.toDouble()
//                                qty.setText(response.result.productId[0].quantity.toString())
                                dealDiscount.setText(response.result.dealDiscount.toString())
                                dealPrice.setText(response.result.dealPrice.toDouble().toString())
                                tv_startdate.text =
                                    dealsdate(response.result.dealStartTime.toString())
                                tv_end_date.text = dealsdate(response.result.dealEndTime.toString())
                                start_time.text =
                                    showDealTime(response.result!!.dealStartTime.toString())
                                end_time.text =
                                    showDealTime(response.result!!.dealEndTime.toString())

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        androidextention.disMissProgressDialog(activity)
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
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        androidextention.disMissProgressDialog(activity)
                        progressbar.visibility = View.GONE

                    }

                }, "viewProductDetailsApi", requireContext())

            try {
                serviceManager.customerdealsViewApi(callBack, productId)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }
    }

    fun editDealsApi() {
        if (androidextention.isOnline(mContext)) {
            progressbar.visibility = View.VISIBLE
            if (addDealsByAdmin) {
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<EditDealsResponse> =
                ApiCallBack<EditDealsResponse>(object : ApiResponseListener<EditDealsResponse> {
                    override fun onApiSuccess(response: EditDealsResponse, apiName: String?) {
                        if (response.responseCode == 200) {
                            progressbar.visibility = View.GONE
                            try {
                                addDealsByAdmin = false

                                DealsManagement.apiDealManagementCallFlag = true;

                                fragmentManager?.let {
                                    productDialog(
                                        "Deal Updated Successfully",
                                        "Deal"
                                    ).show(it, "MyCustomFragment")
                                }
                                imageCounter = 0
                                imageparts.clear()
                                fragmentManager?.popBackStack()
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
                        Toast.makeText(requireContext(), "${failureMessage}", Toast.LENGTH_LONG)
                            .show()
                    }

                }, "EditDealsApi", mContext)

            val request = EditDealRequest()

            var productId = ArrayList<String>()
            productId.add(edit_productId)
            request.dealId = edit_dealId
            request.productId = productId
            request.dealImage = dealImages
            request.thumbnail = thumbnailImage
            request.dealName = ProductTitle.text.toString()

            request.dealPrice = dealPrice.text.toString().toDouble()
            request.description = description.text.toString()
            request.dealDiscount = dealDiscount.text.toString().toInt()
            request.dealsFor = "CUSTOMER"
            startDate = "${tv_startdate.text} ${start_time.text}"
            endDate = "${tv_end_date.text} ${end_time.text}"
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

    fun addDealsApi() {
        if (androidextention.isOnline(mContext)) {

            progressbar.visibility = View.VISIBLE
            if (addDealsByAdmin) {
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<AddDealResponse> =
                ApiCallBack<AddDealResponse>(object : ApiResponseListener<AddDealResponse> {
                    override fun onApiSuccess(response: AddDealResponse, apiName: String?) {
                        if (response.responseCode == 200) {
                            progressbar.visibility = View.GONE
                            try {
                                DealsManagement.apiDealManagementCallFlag = true;
                                addDealsByAdmin = false
                                fragmentManager?.let {
                                    productDialog("Deal Added Successfully", "Deal").show(
                                        it,
                                        "MyCustomFragment"
                                    )
                                }
                                imageCounter = 0
                                imageparts.clear()
                                fragmentManager?.popBackStack()
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
                        Toast.makeText(requireContext(), "${failureMessage}", Toast.LENGTH_LONG)
                            .show()
                    }

                }, "AddDealApi", mContext)

            val request = AddDealRequest()



            request.dealName = ProductTitle.text.toString()
            request.thumbnail = thumbnailImage
            request.dealImage = dealImages

            request.dealPrice = dealPrice.text.toString().toDouble()
            request.description = description.text.toString()
            request.dealDiscount = dealDiscount.text.toString().toInt()
            request.dealsFor = "CUSTOMER"
            request.productId = dealsId
            startDate = "${tv_startdate.text} ${start_time.text}"
            endDate = "${tv_end_date.text} ${end_time.text}"
            request.dealStartTime = DateFormat.createDealTimeToIos(startDate)
            request.dealEndTime = DateFormat.createDealTimeToIos(endDate)


            try {
                serviceManager.addDealApi(callBack, request)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }
    }

    override fun deleteImage(position: Int) {
        dealImages.removeAt(position)
        if (dealImages.size == 0) {
            imagesRv.visibility = View.GONE
            addImage.visibility = View.VISIBLE
        } else if (dealImages.size == 3) {
            imagesRv.visibility = View.VISIBLE
            addImage.visibility = View.GONE
        } else {
            imagesRv.visibility = View.VISIBLE
            addImage.visibility = View.VISIBLE
        }
        setDealImagesAdaptor(dealImages)
    }

//    fun calculateDealDiscount(actualPrice: Double, dealPrice: Double, dealDiscountET: EditText) {
//        val discount = actualPrice - dealPrice
//        val discountPercentage = (discount / actualPrice) * 100
//
//        // Format the discount percentage to two decimal places
//        val decimalFormat = DecimalFormat("0")
//        val formattedDiscountPercentage = decimalFormat.format(discountPercentage)
//        GlobalScope.launch(Dispatchers.Main) {
//            dealDiscountET.setText(formattedDiscountPercentage)
//        }
//    }

    fun calculateDiscountPrice(
        actualAmount: Double,
        discountPercentage: Int,
        dealPrice: EditText
    ) {
        val discountAmount = actualAmount * (discountPercentage.toDouble() / 100)
        val amount = (actualAmount - discountAmount)

        GlobalScope.launch(Dispatchers.Main) {
            dealPrice.setText(amount.toString())
        }
    }
}
