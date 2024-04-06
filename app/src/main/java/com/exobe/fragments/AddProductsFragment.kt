package com.exobe.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
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
import com.exobe.activities.search_composer
import com.exobe.Adapter.DealImageCollectionAdaptor
import com.exobe.adaptor.ProductPackageAdaptor
import com.exobe.modelClass.CategoryModel
import com.exobe.modelClass.ProductPackModel
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.bottomSheet.SelectUnitBottomSheet
import com.exobe.bottomSheet.choosePhotoBottomSheet
import com.exobe.customClicks.*
import com.exobe.dialogs.EditProductSizeDialogBox
import com.exobe.dialogs.productDialog
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.AddProductRequest
import com.exobe.entity.request.EditProductRequest
import com.exobe.entity.response.*
import com.exobe.entity.response.addproduct.AddProductResponse
import com.exobe.entity.response.addproduct.MultipartImageResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class AddProductsFragment : Fragment(), ApiResponseListener<CategoryList_response>,
    AddProductListener,
    UnitListener, DeletePackageListener, PopUpEditPriceDetails, DealImageRemoveListener {

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
    var myHour = ""
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
    var ProductByAdminid = ""
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
    var productReference = ""
    lateinit var ProductTitle: EditText
    lateinit var description: EditText
    lateinit var search_button: TextView
    lateinit var Category_errortv: TextView
    lateinit var subCategory_error_tv: TextView
    lateinit var description_error: TextView
    lateinit var delivery_days_error: TextView
    lateinit var ll_subcategory: LinearLayout
    lateinit var ll_category: LinearLayout
    lateinit var deliverydays_spinner: Spinner
    var imageCounter = 0
    var addproductflag = ""
    var editproductId = ""
    var requestCategoryId = ""
    var requestSubCategoryId = ""
    var requestDeliveryDays = ""
    var thumbnailImage = ""
    var addProductByAdmin = false
    lateinit var internet_connection: RelativeLayout

    lateinit var add_button: TextView
    lateinit var value: EditText
    lateinit var value_error: TextView

    //    lateinit var spinner_ll: LinearLayout
    lateinit var unit_spinner_tv: TextView
    lateinit var unit_error: TextView
    lateinit var amount: EditText
    lateinit var amount_error: TextView
    lateinit var qty: EditText
    lateinit var qty_available_error: TextView
    lateinit var packages_error_tv: TextView
    lateinit var packages_rv: RecyclerView
    lateinit var productPackageAdaptor: ProductPackageAdaptor
    var productPackage = ArrayList<PriceSizeDetails>()
    var requestProductPackage = ArrayList<ProductPackModel>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_add_products, container, false)
        mContext = activity?.applicationContext!!


        addImage = view.findViewById(R.id.add_image)
        imagesRv = view.findViewById(R.id.imagesRv)

        packages_error_tv = view.findViewById(R.id.packages_error_tv)
        packages_rv = view.findViewById(R.id.packages_rv)
        value = view.findViewById(R.id.value)
        value_error = view.findViewById(R.id.value_error)
//        spinner_ll = view.findViewById(R.id.spinner_ll)
        unit_spinner_tv = view.findViewById(R.id.unit_spinner_tv)
        unit_error = view.findViewById(R.id.unit_error)
        amount = view.findViewById(R.id.amount)
        amount_error = view.findViewById(R.id.amount_error)
        qty = view.findViewById(R.id.qty)
        qty_available_error = view.findViewById(R.id.qty_available_error)
        add_button = view.findViewById(R.id.add_button)
        delivery_days_error = view.findViewById(R.id.delivery_days_error)
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
        errorTvProductName = view.findViewById(R.id.errorTvProductName)
        progressbar = view.findViewById(R.id.progressbar)
        Category_errortv = view.findViewById(R.id.Category_errortv)
        subCategory_error_tv = view.findViewById(R.id.subCategory_error_tv)
        description_error = view.findViewById(R.id.description_error)
        ll_subcategory = view.findViewById(R.id.ll_subcategory)
        ll_category = view.findViewById(R.id.ll_category)
        deliverydays_spinner = view.findViewById(R.id.deliverydays_spinner)
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        internet_connection.visibility = View.GONE


        if (requireArguments().getString("productid") != null) {
            editproductId = requireArguments().getString("productid").toString()
        }
        if (requireArguments().getString("flag") != null) {
            flag = requireArguments().getString("flag").toString()

        }
        if (requireArguments().getString("productReference") != null) {
            productReference = requireArguments().getString("productReference").toString()

        }
        if (requireArguments().getString("addproduct") != null) {
            addproductflag = requireArguments().getString("addproduct").toString()
        }

        if (!SavedPrefManager.getStringPreferences(
                requireContext(),
                SavedPrefManager.ADMIN_PRODUCT_ID
            ).equals("")
        ) {
            ProductByAdminid = SavedPrefManager.getStringPreferences(
                requireContext(),
                SavedPrefManager.ADMIN_PRODUCT_ID
            ).toString()
        }
        try {
            setDeliveryDaysAdaptor()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        imageparts.clear()


        if (flag == "Edit Product") {
            ll_subcategory.visibility = View.GONE
            Category_tv.visibility = View.VISIBLE
            ll_category.visibility = View.GONE
            search_button.visibility = View.GONE
            SubCategory_tv.visibility = View.VISIBLE

            save.text = "Update Product"

            if (productReference == "AdminReference") {
                productPackage.clear()
                ProductByAdminid = editproductId
                viewProductbyAdminApi(ProductByAdminid)
            } else {
                productPackage.clear()
                viewProductDetailsApi(editproductId)
            }
        } else {
            if (ProductByAdminid.isEmpty()) {
                CategoryListApi()
                ll_subcategory.visibility = View.VISIBLE
                Category_tv.visibility = View.GONE
                ll_category.visibility = View.VISIBLE
                SubCategory_tv.visibility = View.GONE
                description.text.clear()
            } else {
                productPackage.clear()
                viewProductbyAdminApi(ProductByAdminid)
                ll_subcategory.visibility = View.GONE
                Category_tv.visibility = View.VISIBLE
                ll_category.visibility = View.GONE
                SubCategory_tv.visibility = View.VISIBLE
            }
        }


        unit_spinner_tv.setSafeOnClickListener{
            fragmentManager?.let { it1 ->
                SelectUnitBottomSheet(requireContext(), this).show(
                    it1,
                    "ModalBottomSheet"
                )
            }
        }

        deliverydays_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?, pos: Int, id: Long
                ) {
                    var item = parent.getItemAtPosition(pos)
                    if (item.equals("Select Expected Days")) {
                        requestDeliveryDays = ""
                    } else {
                        requestDeliveryDays = item.toString()
                    }
                }

                override fun onNothingSelected(arg0: AdapterView<*>?) {
                }
            }


        productCategory_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?, pos: Int, id: Long
                ) {
                    var item = parent.getItemAtPosition(pos)
                    if (item.equals("Select")) {
                        requestSubCategoryId = ""
                        requestCategoryId = ""
                        Sub_Category_spinner.setSelection(0)
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
                view: View?, pos: Int, id: Long
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

        save.setSafeOnClickListener {

            if (!ProductByAdminid.equals("")) {
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

                } else if (productPackage.size == 0) {
                    errorTvProductName.text = ""
                    Category_errortv.text = ""
                    subCategory_error_tv.text = ""
                    description_error.text = ""
                    packages_error_tv.text = "*Please enter Value/Unit/Price/Qty."
                } else if (requestDeliveryDays.equals("")) {
                    errorTvProductName.text = ""
                    Category_errortv.text = ""
                    subCategory_error_tv.text = ""
                    description_error.text = ""
                    packages_error_tv.text = ""
                    delivery_days_error.text = "*Please select Expected delivery days."
                } else {
                    delivery_days_error.text = ""
                    errorTvProductName.text = ""
                    Category_errortv.text = ""
                    subCategory_error_tv.text = ""
                    description_error.text = ""
                    packages_error_tv.text = ""
                    addProductByAdmin = true
                    addProductApi()
                }
            } else {
                if (flag.equals("Edit Product")) {
                    if (ProductTitle.text.isEmpty()) {
                        errorTvProductName.text = ""
                        errorTvProductName.text = "*Please enter product name."
                    } else if (imageArray.size == 0) {
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
                    } else if (productPackage.size == 0) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        packages_error_tv.text = "*Please enter Value/Unit/Price/Qty."
                    } else if (requestDeliveryDays.equals("")) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        packages_error_tv.text = ""
                        delivery_days_error.text = "*Please select Expected delivery days."
                    } else if (description.text.isEmpty()) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        delivery_days_error.text = ""
                        packages_error_tv.text = ""
                        description_error.text = "*Please enter the description."

                    } else {
                        delivery_days_error.text = ""
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        delivery_days_error.text = ""
                        packages_error_tv.text = ""
                        if (imageparts.size > 0) {
                            createMultiImageLinkApi()
                        } else {
                            editProductApi()
                        }
                    }
                } else {
                    if (ProductTitle.text.isEmpty()) {
                        errorTvProductName.text = "*Please enter product name."
                    } else if (imageparts.size == 0) {
                        errorTvProductName.text = ""
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
                    } else if (productPackage.size == 0) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        packages_error_tv.text = "*Please enter Value/Unit/Price/Qty."
                    } else if (requestDeliveryDays.equals("")) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        packages_error_tv.text = ""
                        delivery_days_error.text = "*Please select Expected delivery days."
                    } else if (description.text.isEmpty()) {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        packages_error_tv.text = ""
                        description_error.text = "*Please enter the description."

                    } else {
                        errorTvProductName.text = ""
                        Category_errortv.text = ""
                        subCategory_error_tv.text = ""
                        description_error.text = ""
                        delivery_days_error.text = ""
                        packages_error_tv.text = ""
                        addProductApi()
                    }
                }
            }


        }


        search_button.setSafeOnClickListener {

            if (ProductTitle.text.isEmpty()) {
                errorTvProductName.text = "*Please enter product name."
            } else {
                errorTvProductName.text = ""
                var bundle = Bundle()
                bundle.putString("SEARCH_TEXT", ProductTitle.text.toString().trim { it <= ' ' })
                var fragObj = search_composer()
                fragObj.arguments = bundle
                requireFragmentManager().beginTransaction()
                    .replace(R.id.FrameLayout, fragObj, "Search")?.addToBackStack("Search")
                    ?.commit()
            }

        }

        addImage.setSafeOnClickListener {
            RequestPermission.requestMultiplePermissions(requireContext())
            getImages()
        }



        add_button.setSafeOnClickListener {

            var priceRegex = "^\\d{0,8}(\\.\\d{1,4})?\$"
            if (value.text.isEmpty()) {
                value_error.text = "*Please enter value."
            } else if (unit_spinner_tv.text.isEmpty()) {
                value_error.text = ""
                unit_error.text = "*Please select unit."
            } else if (amount.text.isEmpty()) {
                value_error.text = ""
                unit_error.text = ""
                amount_error.text = "*Please enter amount."
            } else if (amount.text.startsWith("0")) {
                value_error.text = ""
                unit_error.text = ""
                amount_error.text = "*Please enter valid amount."
            } else if (!amount.text.toString().matches(Regex(priceRegex))) {
                value_error.text = ""
                unit_error.text = ""
                amount_error.text = "*Please enter valid amount."
            } else if (qty.text.isEmpty()) {
                value_error.text = ""
                amount_error.text = ""
                unit_error.text = ""
                qty_available_error.text = "*Please enter qty. available."
            } else if (qty.text.startsWith("0")) {
                value_error.text = ""
                amount_error.text = ""
                unit_error.text = ""
                qty_available_error.text = "*Please enter valid qty. available."
            } else {
                value_error.text = ""
                amount_error.text = ""
                unit_error.text = ""
                qty_available_error.text = ""
                packages_error_tv.text = ""
                productPackage.add(
                    PriceSizeDetails(
                        value.text.toString(),
                        unit_spinner_tv.text.toString(),
                        amount.text.toString().toDouble(),
                        qty.text.toString(),
                        ""
                    )
                )
                setPackagesAdaptor()
                value.text.clear()
                unit_spinner_tv.text = ""
                amount.text.clear()
                qty.text.clear()
            }
        }

        return view

    }


    private fun setPackagesAdaptor() {
        packages_rv.layoutManager = LinearLayoutManager(requireContext())
        productPackageAdaptor = ProductPackageAdaptor(requireContext(), productPackage, this, flag)
        packages_rv.adapter = productPackageAdaptor
    }

    private fun setDeliveryDaysAdaptor() {
        try {
            activity?.let {
                SpinnerAdapter1().mySpinner(
                    it,
                    deliverydays_spinner,
                    resources.getStringArray(R.array.delivery_slots)
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getImages() {
        var bottomsheet = choosePhotoBottomSheet("AddProduct", this)
        bottomsheet.show(requireFragmentManager(), "bottomsheet")
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
        title.setText(flag)
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    fun CategoryListApi() {
        if (androidextention.isOnline(mContext)) {
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<CategoryList_response> =
                ApiCallBack<CategoryList_response>(this, "CategoryListApi", mContext)
            val jsonObject = JsonObject()
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
            try {
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
            androidextention.alertBox(pojo.responseMessage, requireContext())

        } catch (e: Exception) {
            // handle failure at error parse
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
                            // handle failure at error parse
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


    fun addProductApi() {
        if (androidextention.isOnline(mContext)) {
            progressbar.visibility = View.VISIBLE
            if (addProductByAdmin) {
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<AddProductResponse> =
                ApiCallBack<AddProductResponse>(object : ApiResponseListener<AddProductResponse> {
                    override fun onApiSuccess(response: AddProductResponse, apiName: String?) {
                        if (response.responseCode == 200) {
                            progressbar.visibility = View.GONE
                            try {
                                ProductManagement.apiProductManagementCallFlag = true
                                addProductByAdmin = false
                                fragmentManager?.let {
                                    productDialog("Product Added Successfully.", "Product").show(
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
                            // handle failure at error parse
                        }

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                        Toast.makeText(requireContext(), "${failureMessage}", Toast.LENGTH_LONG)
                            .show()

                    }

                }, "AddProductApi", mContext)

            for (i in 0 until productPackage.size) {
                requestProductPackage.add(
                    ProductPackModel(
                        productPackage[i].value,
                        productPackage[i].unit,
                        productPackage[i].price,
                        productPackage[i].quantity
                    )
                )
            }
            var request = AddProductRequest()
            if (!ProductByAdminid.equals("")) {
                request.productName = ProductTitle.text.toString()
                request.productImage = imageArray
                request.priceSizeDetails = requestProductPackage
                request.description = description.text.toString()
                request.categoryId = requestCategoryId
                request.subCategoryId = requestSubCategoryId
                request.expectedDeliveryDays = requestDeliveryDays
                request.thumbnail = thumbnailImage
                request.productFor = "CUSTOMER"
                request.productReferenceId = ProductByAdminid

            } else {
                request.productName = ProductTitle.text.toString()
                request.productImage = imageArray
                request.thumbnail = thumbnailImage
                request.priceSizeDetails = requestProductPackage
                request.description = description.text.toString()
                request.categoryId = requestCategoryId
                request.subCategoryId = requestSubCategoryId
                request.expectedDeliveryDays = requestDeliveryDays
                request.productFor = "CUSTOMER"
            }




            try {
                serviceManager.addProductApi(callBack, request)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

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
                        progressbar.visibility = View.GONE

                        if (response.responseCode == 200) {
                            try {

                                for (i in 0 until response.result!!?.size!!) {
                                    imageArray.add(response.result?.get(i)?.mediaUrl.toString())
                                }
                                thumbnailImage = response.result[0].thumbnail.toString()
//                                }
//                                thumbnailImage = response.result?.get(0)?.thumbnail.toString()
                                if (imageArray.size == 0) {
                                    imagesRv.visibility = View.GONE
                                    addImage.visibility = View.VISIBLE
                                } else if (imageArray.size == 3) {
                                    imagesRv.visibility = View.VISIBLE
                                    addImage.visibility = View.GONE
                                } else {
                                    imagesRv.visibility = View.VISIBLE
                                    addImage.visibility = View.VISIBLE
                                }
                                setDealImagesAdaptor(imageArray)

//                                if (imageArray.size != 0) {
//                                    if (flag.equals("Edit Product")) {
//                                        editProductApi()
//                                    } else {
//                                        addProductApi()
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
                            // handle failure at error parse
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

    fun setDealImagesAdaptor(data: java.util.ArrayList<String>) {
        imagesRv.layoutManager = LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
        imagesRv.adapter =
            DealImageCollectionAdaptor(mContext, data, this)
    }

    override fun addProduct(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        choosePhotoBottomSheet: choosePhotoBottomSheet,
        imagePath: String
    ) {
        try {
            if (resultCode == Activity.RESULT_CANCELED) {
                choosePhotoBottomSheet.dismiss()
            } else {
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


                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }
                    }

                } else if (requestCode == CAMERA) {
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

    fun viewProductbyAdminApi(productReferenceId: String) {
        if (androidextention.isOnline(mContext)) {
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<ViewAddProductByAdminResponse> =
                ApiCallBack<ViewAddProductByAdminResponse>(object :
                    ApiResponseListener<ViewAddProductByAdminResponse> {
                    override fun onApiSuccess(
                        response: ViewAddProductByAdminResponse,
                        apiName: String?
                    ) {
                        if (response.responseCode == 200) {
                            try {
                                progressbar.visibility = View.GONE
                                SavedPrefManager.saveStringPreferences(
                                    requireContext(),
                                    SavedPrefManager.ADMIN_PRODUCT_ID,
                                    ""
                                )
                                thumbnailImage = response.result?.thumbnail.toString()
                                imageArray = response.result?.productImage!!
                                if (imageArray.size == 0) {
                                    imagesRv.visibility = View.GONE
                                    addImage.visibility = View.VISIBLE
                                } else if (imageArray.size == 3) {
                                    imagesRv.visibility = View.VISIBLE
                                    addImage.visibility = View.GONE
                                } else {
                                    imagesRv.visibility = View.VISIBLE
                                    addImage.visibility = View.VISIBLE
                                }
                                setDealImagesAdaptor(imageArray)
                                requestSubCategoryId =
                                    response.result?.subCategoryId?._id.toString()
                                requestCategoryId = response.result?.categoryId?._id.toString()

                                ProductTitle.setText(response.result?.productName?.capitalizeFirstLetter())
                                Category_tv.text = response.result?.categoryId?.categoryName
                                SubCategory_tv.text =
                                    response.result?.subCategoryId?.subCategoryName
                                description.setText(response.result?.description)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        SavedPrefManager.saveStringPreferences(
                            requireContext(),
                            SavedPrefManager.ADMIN_PRODUCT_ID,
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
                            SavedPrefManager.ADMIN_PRODUCT_ID,
                            ""
                        )
                        progressbar.visibility = View.GONE
                    }

                }, "viewProductApi", mContext)

            try {
                serviceManager.viewProductByAdmin(callBack, productReferenceId)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }
    }

    fun viewProductDetailsApi(productId: String) {
        if (androidextention.isOnline(requireContext())) {
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<viewProductResponse> =
                ApiCallBack<viewProductResponse>(object :
                    ApiResponseListener<viewProductResponse> {
                    override fun onApiSuccess(response: viewProductResponse, apiName: String?) {
                        progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {

                                thumbnailImage = response.result.thumbnail.toString()
                                for (i in 0 until response.result.priceSizeDetails.size) {
                                    productPackage.add(
                                        PriceSizeDetails(
                                            response.result.priceSizeDetails[i].value,
                                            response.result.priceSizeDetails[i].unit,
                                            response.result.priceSizeDetails[i].price,
                                            response.result.priceSizeDetails[i].quantity,
                                            response.result.priceSizeDetails[i].id,
                                        )
                                    )
                                }
                                setPackagesAdaptor()
                                imageArray = response.result.productImage
                                if (imageArray.size == 0) {
                                    imagesRv.visibility = View.GONE
                                    addImage.visibility = View.VISIBLE
                                } else if (imageArray.size == 3) {
                                    imagesRv.visibility = View.VISIBLE
                                    addImage.visibility = View.GONE
                                } else {
                                    imagesRv.visibility = View.VISIBLE
                                    addImage.visibility = View.VISIBLE
                                }
                                setDealImagesAdaptor(imageArray)
                                requestSubCategoryId = response.result.subCategoryId?.Id.toString()
                                requestCategoryId = response.result.categoryId?.Id.toString()

                                ProductTitle.setText(response.result.productName?.capitalizeFirstLetter())
                                Category_tv.text = response.result.categoryId?.categoryName
                                SubCategory_tv.text = response.result.subCategoryId?.subCategoryName
                                description.setText(response.result.description)

                                if (!response.result!!.expectedDeliveryDays.equals("")) {
                                    var deliveryArray =
                                        resources.getStringArray(R.array.delivery_slots)

                                    for (i in deliveryArray.indices) {
                                        if (deliveryArray.get(i)
                                                .equals(response.result!!.expectedDeliveryDays)
                                        ) {
                                            deliverydays_spinner.setSelection(i)
                                        }
                                    }
                                }

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
                            androidextention.alertBox(pojo.responseMessage, requireContext())

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
                serviceManager.viewProductDetailsApi(callBack, productId)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }
    }

    fun editProductApi() {
        if (androidextention.isOnline(mContext)) {
            progressbar.visibility = View.VISIBLE
            if (addProductByAdmin) {
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<EditProductResponse> =
                ApiCallBack<EditProductResponse>(object : ApiResponseListener<EditProductResponse> {
                    override fun onApiSuccess(response: EditProductResponse, apiName: String?) {
                        if (response.responseCode == 200) {
                            progressbar.visibility = View.GONE
                            try {
                                ProductManagement.apiProductManagementCallFlag = true
                                addProductByAdmin = false
                                fragmentManager?.let {
                                    productDialog("Product Updated Successfully.", "Product").show(
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
                            // handle failure at error parse
                        }

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                        Toast.makeText(requireContext(), "${failureMessage}", Toast.LENGTH_LONG)
                            .show()

                    }

                }, "AddProductApi", mContext)




            try {
                var request = EditProductRequest()
                request.productId = editproductId
                request.productName = ProductTitle.text.toString()
                request.productImage = imageArray
                request.thumbnail = thumbnailImage
                request.priceSizeDetails = productPackage
                request.description = description.text.toString()
                request.categoryId = requestCategoryId
                request.subCategoryId = requestSubCategoryId
                request.expectedDeliveryDays = requestDeliveryDays
                request.productFor = "CUSTOMER"

                serviceManager.editproductApi(callBack, request)
            } catch (e: Exception) {
                e.printStackTrace()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }
    }

    override fun getUnit(unitName: String) {
        unit_spinner_tv.setText(unitName)
    }

    override fun isDelete(value: String, unit: String, amount: Number, qty: String) {
        if (productPackage.size > 0) {
            for (i in 0 until productPackage.size) {
                if (productPackage[i].value == value && productPackage[i].unit == unit && productPackage[i].price == amount && productPackage[i].quantity == qty) {
                    productPackage.removeAt(i)
                    break
                }
            }
            productPackageAdaptor.notifyDataSetChanged()

        }
    }

    override fun isEdit(value: String, unit: String, amount: Number, qty: String, id: String) {
        fragmentManager?.let { it1 ->
            EditProductSizeDialogBox(requireContext(), value, unit, amount, qty, id, this).show(
                it1,
                "ModalBottomSheet"
            )
        }
    }

    override fun popupEditDetails(
        value: String,
        unit: String,
        amount: String,
        qty: String,
        id: String
    ) {
        if (productPackage.size > 0) {
            for (i in 0 until productPackage.size) {
                if (productPackage[i].id == id) {
                    productPackage[i].value = value
                    productPackage[i].unit = unit
                    productPackage[i].price = amount.toDouble()
                    productPackage[i].quantity = qty
                    break
                } else {
                    if (productPackage[i].value == value && productPackage[i].unit == unit && productPackage[i].price == amount.toDouble() && productPackage[i].quantity == qty) {
                        productPackage.removeAt(i)
                        break
                    }
                }
            }
            productPackageAdaptor.notifyDataSetChanged()

        }
    }

    override fun deleteImage(position: Int) {
        imageArray.removeAt(position)
        if (imageArray.size == 0) {
            imagesRv.visibility = View.GONE
            addImage.visibility = View.VISIBLE
        } else if (imageArray.size == 3) {
            imagesRv.visibility = View.VISIBLE
            addImage.visibility = View.GONE
        } else {
            imagesRv.visibility = View.VISIBLE
            addImage.visibility = View.VISIBLE
        }
        setDealImagesAdaptor(imageArray)
    }


}