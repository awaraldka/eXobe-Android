package com.exobe.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.callisdairy.extension.setSafeOnClickListener
import com.example.validationpractice.utlis.FormValidation
import com.exobe.activities.retailer.NewUploadDocumentActivity
import com.exobe.adaptor.*
import com.exobe.Model.Checkboxmodalclass
import com.exobe.modelClass.BrandList
import com.exobe.modelClass.WholesalerList
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.androidextention
import com.exobe.customClicks.deleteBrand
import com.exobe.customClicks.selectWholeSalerListener
import com.exobe.customClicks.switchValue
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.BusinessBankingDetails
import com.exobe.entity.request.BusinessDetails
import com.exobe.entity.request.FillFormRequest
import com.exobe.entity.response.*
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceConstant
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.validations.DialogUtils
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class fillDetails_retailer : AppCompatActivity(), deleteBrand, switchValue,
    selectWholeSalerListener {
    lateinit var add_product: TextView
    lateinit var add_suppliers: TextView
    lateinit var title: TextView
    lateinit var back: ImageView
    lateinit var et_suppliers_name: EditText
    lateinit var business_form_submit: Button
    lateinit var brandAdaptor: BrandAdaptor
    lateinit var supplierAdaptor: SupplierAdaptor
    lateinit var rv_add_product_brand: RecyclerView
    lateinit var rv_add_suppliers: RecyclerView
    lateinit var ll_VAT: LinearLayout
    lateinit var RB_yes: RadioButton
    lateinit var RB_no: RadioButton

    lateinit var adapter: fill_details_checkbox_Adapter

    lateinit var FirstName_ET: EditText
    lateinit var first_nametv: TextView
    lateinit var LastName_ET: EditText
    lateinit var lastname_tv: TextView
    lateinit var Email_ET: EditText
    lateinit var emailaddress_tv: TextView
    lateinit var unique_product: EditText
    lateinit var uniqueproduct_tv: TextView
    lateinit var et_brand_name: EditText
    lateinit var list_brands_tv: TextView
    var wholesalerdata: List<wholesaler_listDocs> = listOf()
    var flag: String = ""

    lateinit var Companyname_ET: EditText
    lateinit var company_name_TV: TextView
    lateinit var businessRegistrationET: EditText
    lateinit var businessregistration_TV: TextView

    lateinit var Bankname_ET: EditText
    lateinit var bank_name_TV: TextView

    lateinit var accounttype_TV: TextView
    lateinit var Accountname_ET: EditText
    lateinit var account_name_TV: TextView
    lateinit var Accountnumber_ET: EditText
    lateinit var account_number_TV: TextView
    lateinit var Branchname_ET: EditText
    lateinit var branch_name_TV: TextView
    lateinit var Branchcode_ET: EditText
    lateinit var branch_code_TV: TextView
    lateinit var SWIFTcode_ET: EditText
    lateinit var swiftcode_TV: TextView
    lateinit var Monthly_RevenueTextview: TextView
    lateinit var checkboxrecycler: RecyclerView
    var data: ArrayList<Checkboxmodalclass> = ArrayList()
    private lateinit var dialog: Dialog
    lateinit var VAT_TV: TextView
    lateinit var stock_yes: RadioButton
    lateinit var stock_no: RadioButton
    lateinit var physical_yes: RadioButton
    lateinit var physical_no: RadioButton
    lateinit var vat_edittext: EditText
    lateinit var Website_ET: EditText
    lateinit var Socialmedia_ET: EditText
    lateinit var comment_ET: EditText

    lateinit var mContext: Context
    var keepStockValue = false
    var PhysicalStore = false
    var vatFlag = false
    var VatNumber = ""
    var monthlyRevenue = ""
    var loginflag = ""
    var values: Boolean = false
    lateinit var checkValue: String
    lateinit var fillform_progressbar: ProgressBar
    lateinit var account_type: Spinner
    lateinit var account_type_ll: LinearLayout


    lateinit var store_ll: LinearLayout
    lateinit var store_name_ET: EditText
    lateinit var store_address_ET: TextView
    lateinit var store_contact_ET: EditText
    lateinit var store_name_tv: TextView
    lateinit var store_address_tv: TextView
    lateinit var store_contact_tv: TextView

    var BrandList = ArrayList<BrandList>()
    var RetailerList = ArrayList<BrandList>()
    var WholeSalerList = ArrayList<WholesalerList>()
    var requestAccountType = ""
    var storeLong =0.0
    var storeLat =0.0
    var cor = ArrayList<Double>()
    var PLACE_PICKER_REQUEST = 110
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.attributes.windowAnimations = R.style.Fade
        setContentView(R.layout.activity_fill_details_retailer)
        mContext = this.applicationContext!!
        store_ll = findViewById(R.id.store_ll)
        store_name_ET = findViewById(R.id.store_name_ET)
        store_address_ET = findViewById(R.id.store_address_ET)
        store_contact_ET = findViewById(R.id.store_contact_ET)
        store_name_tv = findViewById(R.id.store_name_tv)
        store_address_tv = findViewById(R.id.store_address_tv)
        store_contact_tv = findViewById(R.id.store_contact_tv)
        account_type_ll = findViewById(R.id.account_type_ll)
        account_type = findViewById(R.id.account_type)
        fillform_progressbar = findViewById(R.id.fillform_progressbar)
        comment_ET = findViewById(R.id.comment_ET)
        Socialmedia_ET = findViewById(R.id.Socialmedia_ET)
        Website_ET = findViewById(R.id.Website_ET)
        VAT_TV = findViewById(R.id.VAT_TV)
        stock_yes = findViewById(R.id.stock_yes)
        stock_no = findViewById(R.id.stock_no)
        physical_yes = findViewById(R.id.physical_yes)
        physical_no = findViewById(R.id.physical_no)
        add_product = findViewById(R.id.add_product)
        title = findViewById(R.id.title)
        business_form_submit = findViewById(R.id.business_form_submit)
        et_suppliers_name = findViewById(R.id.et_suppliers_name)
        et_brand_name = findViewById(R.id.et_brand_name)
        rv_add_product_brand = findViewById(R.id.rv_add_product_brand)
        rv_add_suppliers = findViewById(R.id.rv_add_suppliers)
        add_suppliers = findViewById(R.id.add_suppliers)
        back = findViewById(R.id.back)
        ll_VAT = findViewById(R.id.ll_VAT)
        RB_yes = findViewById(R.id.RB_yes)
        RB_no = findViewById(R.id.RB_no)
        Monthly_RevenueTextview = findViewById(R.id.Monthly_RevenueTextview)
        checkboxrecycler = findViewById(R.id.checkbox_recycler)
        vat_edittext = findViewById(R.id.VAT_ET)
        FirstName_ET = findViewById(R.id.FirstName_ET)
        first_nametv = findViewById(R.id.first_nametv)
        LastName_ET = findViewById(R.id.LastName_ET)
        lastname_tv = findViewById(R.id.lastname_tv)
        Email_ET = findViewById(R.id.Email_ET)
        emailaddress_tv = findViewById(R.id.emailaddress_tv)
        unique_product = findViewById(R.id.unique_product)
        uniqueproduct_tv = findViewById(R.id.uniqueproduct_tv)
        et_brand_name = findViewById(R.id.et_brand_name)
        list_brands_tv = findViewById(R.id.list_brands_tv)
        Companyname_ET = findViewById(R.id.Companyname_ET)
        company_name_TV = findViewById(R.id.company_name_TV)
        businessRegistrationET = findViewById(R.id.businessRegistrationET)
        businessregistration_TV = findViewById(R.id.businessregistration_TV)
        Bankname_ET = findViewById(R.id.Bankname_ET)
        bank_name_TV = findViewById(R.id.bank_name_TV)
        accounttype_TV = findViewById(R.id.accounttype_TV)
        Accountname_ET = findViewById(R.id.Accountname_ET)
        account_name_TV = findViewById(R.id.account_name_TV)
        Accountnumber_ET = findViewById(R.id.Accountnumber_ET)
        account_number_TV = findViewById(R.id.account_number_TV)
        Branchname_ET = findViewById(R.id.Branchname_ET)
        branch_name_TV = findViewById(R.id.branch_name_TV)
        Branchcode_ET = findViewById(R.id.Branchcode_ET)
        branch_code_TV = findViewById(R.id.branch_code_TV)
        SWIFTcode_ET = findViewById(R.id.SWIFTcode_ET)
        swiftcode_TV = findViewById(R.id.swiftcode_TV)
        rv_add_product_brand = findViewById(R.id.rv_add_product_brand)

        INITTILAZIGOOGLEPLACE()

        physical_yes.setSafeOnClickListener {
            initializedControl()
            callValidation()
            PhysicalStore = true
            store_ll.visibility = View.VISIBLE
        }

        physical_no.setSafeOnClickListener {
            callValidation()
            PhysicalStore = false
            store_ll.visibility = View.GONE
        }

        stock_yes.setSafeOnClickListener {
            keepStockValue = true
        }
        stock_no.setSafeOnClickListener {
            keepStockValue = false
        }

        title.setText("Business Details")
        RB_yes.setSafeOnClickListener {
            callValidation()
            ll_VAT.visibility = View.VISIBLE
            vatFlag = true

        }
        RB_no.setSafeOnClickListener {
            callValidation()
            ll_VAT.visibility = View.GONE
            vatFlag = false
        }

        if (intent.getStringExtra("loginflag") != null) {
            loginflag = intent.getStringExtra("loginflag").toString()
        }
        data.add(Checkboxmodalclass("Less than R20K", false))
        data.add(Checkboxmodalclass("R20K-R50K", false))
        data.add(Checkboxmodalclass("R50K-R100K", false))
        data.add(Checkboxmodalclass("R100K-R500K", false))
        data.add(Checkboxmodalclass("More than R500K", false))

        setCheckBoxAdapter()

        initializedControl()
        accountTypeSpinner()
        back.setSafeOnClickListener {
            finish()
        }


            business_form_submit.setSafeOnClickListener {
                callValidation()
                if (FirstName_ET.text.isNotEmpty() &&
                    LastName_ET.text.isNotEmpty() &&
                    Email_ET.text.isNotEmpty() &&
                    Email_ET.text.matches(Regex(FormValidation.emailPattern)) &&
                    unique_product.text.isNotEmpty() &&
                    BrandList.size > 0 &&
                    Companyname_ET.text.isNotEmpty() &&
                    businessRegistrationET.text.isNotEmpty() &&
                    Bankname_ET.text.isNotEmpty() &&
                    values == true &&
                    Branchname_ET.text.isNotEmpty() &&
                    Branchcode_ET.text.isNotEmpty() &&
                    SWIFTcode_ET.text.isNotEmpty() &&
                    Accountname_ET.text.isNotEmpty() &&
                    Accountnumber_ET.text.isNotEmpty()
                ) {
                    if (RB_yes.isChecked == true && physical_yes.isChecked == false) {
                        if (
                            !FirstName_ET.text.toString().isEmpty() &&
                            !LastName_ET.text.isEmpty() &&
                            !Email_ET.text.isEmpty() &&
                            Email_ET.text.matches(Regex(FormValidation.emailPattern)) &&
                            !Companyname_ET.text.toString().isEmpty() &&
                            !businessRegistrationET.text.toString().isEmpty() &&
                            !vat_edittext.text.toString().isEmpty() &&
                            !Bankname_ET.text.toString().isEmpty() &&
                            !Branchname_ET.text.toString().isEmpty() &&
                            !Branchcode_ET.text.toString().isEmpty() &&
                            !SWIFTcode_ET.text.toString().isEmpty() &&
                            !Accountname_ET.text.toString().isEmpty() &&
                            !Accountnumber_ET.text.toString().isEmpty() &&
                            values == true &&
                            !requestAccountType.equals("Select")
                        ) {

                            if (loginflag.equals("loginflag")) {
                                fillformAPI()
                            } else {
                                UpdateProfileAPI()
                            }

                        }
                    } else if (RB_yes.isChecked == false && physical_yes.isChecked == false) {
                        if (
                            !FirstName_ET.text.toString().isEmpty() &&
                            !LastName_ET.text.isEmpty() &&
                            !Email_ET.text.isEmpty() &&
                            Email_ET.text.matches(Regex(FormValidation.emailPattern)) &&
                            !Companyname_ET.text.toString().isEmpty() &&
                            !businessRegistrationET.text.toString().isEmpty() &&
                            !Bankname_ET.text.toString().isEmpty() &&
                            !Branchname_ET.text.toString().isEmpty() &&
                            !Branchcode_ET.text.toString().isEmpty() &&
                            !SWIFTcode_ET.text.toString().isEmpty() &&
                            !Accountname_ET.text.toString().isEmpty() &&
                            !Accountnumber_ET.text.toString().isEmpty() &&
                            values == true &&
                            !requestAccountType.equals("Select")
                        ) {

                            if (loginflag.equals("loginflag")) {
                                fillformAPI()
                            } else {
                                UpdateProfileAPI()
                            }

                        }
                    } else if (RB_yes.isChecked == false && physical_yes.isChecked == false) {
                        if (
                            !FirstName_ET.text.toString().isEmpty() &&
                            !LastName_ET.text.isEmpty() &&
                            !Email_ET.text.isEmpty() &&
                            Email_ET.text.matches(Regex(FormValidation.emailPattern)) &&
                            !Companyname_ET.text.toString().isEmpty() &&
                            !businessRegistrationET.text.toString().isEmpty() &&
                            !Bankname_ET.text.toString().isEmpty() &&
                            !Branchname_ET.text.toString().isEmpty() &&
                            !Branchcode_ET.text.toString().isEmpty() &&
                            !SWIFTcode_ET.text.toString().isEmpty() &&
                            !Accountname_ET.text.toString().isEmpty() &&
                            !Accountnumber_ET.text.toString().isEmpty() &&
                            values == true &&
                            !requestAccountType.equals("Select")

                        ) {

                            if (loginflag.equals("loginflag")) {
                                fillformAPI()
                            } else {
                                UpdateProfileAPI()
                            }

                        }
                    } else if (RB_yes.isChecked == true && physical_yes.isChecked == false) {
                        if (
                            !FirstName_ET.text.toString().isEmpty() &&
                            !LastName_ET.text.isEmpty() &&
                            !Email_ET.text.isEmpty() &&
                            Email_ET.text.matches(Regex(FormValidation.emailPattern)) &&
                            !Companyname_ET.text.toString().isEmpty() &&
                            !businessRegistrationET.text.toString().isEmpty() &&
                            !vat_edittext.text.toString().isEmpty() &&
                            !store_name_ET.text.toString().isEmpty() &&
                            !store_address_ET.text.toString().isEmpty() &&
                            !store_contact_ET.text.toString().isEmpty() &&
                            !Bankname_ET.text.toString().isEmpty() &&
                            !Branchname_ET.text.toString().isEmpty() &&
                            !Branchcode_ET.text.toString().isEmpty() &&
                            !SWIFTcode_ET.text.toString().isEmpty() &&
                            !Accountname_ET.text.toString().isEmpty() &&
                            !Accountnumber_ET.text.toString().isEmpty() &&
                            values == true &&
                            !requestAccountType.equals("Select")
                        ) {

                            if (loginflag.equals("loginflag")) {
                                fillformAPI()
                            } else {
                                UpdateProfileAPI()
                            }

                        }
                    }  else if (RB_yes.isChecked == false && physical_yes.isChecked == true) {
                        if (
                            !FirstName_ET.text.toString().isEmpty() &&
                            !LastName_ET.text.isEmpty() &&
                            !Email_ET.text.isEmpty() &&
                            Email_ET.text.matches(Regex(FormValidation.emailPattern)) &&
                            !store_name_ET.text.toString().isEmpty() &&
                            !store_address_ET.text.toString().isEmpty() &&
                            !store_contact_ET.text.toString().isEmpty() &&
                            !Companyname_ET.text.toString().isEmpty() &&
                            !businessRegistrationET.text.toString().isEmpty() &&
                            !Bankname_ET.text.toString().isEmpty() &&
                            !Branchname_ET.text.toString().isEmpty() &&
                            !Branchcode_ET.text.toString().isEmpty() &&
                            !SWIFTcode_ET.text.toString().isEmpty() &&
                            !Accountname_ET.text.toString().isEmpty() &&
                            !Accountnumber_ET.text.toString().isEmpty() &&
                            values == true &&
                            !requestAccountType.equals("Select")

                        ) {

                            if (loginflag.equals("loginflag")) {
                                fillformAPI()
                            } else {
                                UpdateProfileAPI()
                            }

                        }
                    } else if (RB_yes.isChecked == true && physical_yes.isChecked == true) {
                        if (
                            !FirstName_ET.text.toString().isEmpty() &&
                            !LastName_ET.text.isEmpty() &&
                            !Email_ET.text.isEmpty() &&
                            Email_ET.text.matches(Regex(FormValidation.emailPattern)) &&
                            !vat_edittext.text.toString().isEmpty() &&
                            !store_name_ET.text.toString().isEmpty() &&
                            !store_address_ET.text.toString().isEmpty() &&
                            !store_contact_ET.text.toString().isEmpty() &&
                            !Companyname_ET.text.toString().isEmpty() &&
                            !businessRegistrationET.text.toString().isEmpty() &&
                            !Bankname_ET.text.toString().isEmpty() &&
                            !Branchname_ET.text.toString().isEmpty() &&
                            !Branchcode_ET.text.toString().isEmpty() &&
                            !SWIFTcode_ET.text.toString().isEmpty() &&
                            !Accountname_ET.text.toString().isEmpty() &&
                            !Accountnumber_ET.text.toString().isEmpty() &&
                            values == true &&
                            !requestAccountType.equals("Select")

                        ) {

                            if (loginflag.equals("loginflag")) {
                                fillformAPI()
                            } else {
                                UpdateProfileAPI()
                            }

                        }
                    }

                }

            }


        if (!loginflag.equals("loginflag")) {  GetFillFormDetails() }

        account_type.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                var data = parent.getItemAtPosition(pos).toString()
                requestAccountType = data

                if (!data.equals("Select")) {

                    callValidation()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })



        add_product.setSafeOnClickListener {

            val brand = et_brand_name.text.toString()
            if (brand.isNotEmpty()) {
                BrandList.add(BrandList(brand))
                BarndDetails()
                brandAdaptor.notifyDataSetChanged()
                rv_add_product_brand.visibility = View.VISIBLE
                et_brand_name.text.clear()
            }

        }

        add_suppliers.setSafeOnClickListener {

            val suppliers = et_suppliers_name.text.toString()
            if (suppliers.isNotEmpty()) {
                wholesalerListApi(suppliers)
            } else {
                Toast.makeText(this, "Please search wholesaler", Toast.LENGTH_SHORT).show()
            }

        }

        store_address_ET.setSafeOnClickListener {
            val fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
            // Start the autocomplete intent.
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(mContext)
            startActivityForResult(
                intent,
                PLACE_PICKER_REQUEST
            )
        }


    }

    private fun INITTILAZIGOOGLEPLACE() {
        Places.initialize(mContext, ServiceConstant.API_KEY)
        val placesClient = Places.createClient(mContext)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == 110) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent != null) {
                    if (requestCode == PLACE_PICKER_REQUEST) {
                        when (resultCode) {
                            Activity.RESULT_OK -> {
                                val place = Autocomplete.getPlaceFromIntent(intent)
                                val latLng = place.latLng
                                GETLATLONG(latLng)
                            }
                            AutocompleteActivity.RESULT_ERROR -> {
                                val status = Autocomplete.getStatusFromIntent(intent)
                            }
                            Activity.RESULT_CANCELED -> {
                            }
                        }
                    }
                }
            }
        }
    }

    private fun GETLATLONG(latLng: LatLng) {
        val sb = StringBuffer()
        sb.append(latLng.latitude)
        sb.append(",")
        sb.append(latLng.longitude)
        val url = ("https://maps.googleapis.com/maps/api/geocode/json?latlng="
                + sb.toString() + "&key=${ServiceConstant.API_KEY}")
        val queue = Volley.newRequestQueue(mContext)
        val stateReq =
            JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                { response ->
                    var location: JSONObject
                    try {
                        try {
                            Log.d("tag","response.toString() => ${response.toString()}")
                            var addressLine1 = ArrayList<String>()

                            var objectData = JSONObject(response.toString())
                            val jsonArrayData: JSONArray = objectData.getJSONArray("results")
                            val jsonObject = jsonArrayData[0] as JSONObject

                            val jsonObjectWorkOrderInfo = JSONObject(jsonObject.getString("geometry"))
                            val locationdata = JSONObject(jsonObjectWorkOrderInfo.getString("location"))
                            storeLat=   locationdata.getString("lat").toDouble()
                            storeLong=  locationdata.getString("lng").toDouble()

                            if (jsonArrayData.length() > 0) {
                                val data = jsonArrayData[0] as JSONObject
                                val address_components = data.getJSONArray("address_components")
                                for (i in 0 until address_components.length()) {
                                    val jsonObject = address_components[i] as JSONObject
                                    val types = jsonObject.getJSONArray("types")
                                    for (j in 0 until types.length()) {
                                        if (types[j] == "route") {
                                            addressLine1.add(jsonObject.getString("long_name"))
                                        }
                                        if (types[j] == "sublocality_level_3") {
                                            addressLine1.add(jsonObject.getString("long_name"))
                                        }
                                        if (types[j] == "sublocality_level_2") {
                                            addressLine1.add(jsonObject.getString("long_name"))
                                        }
                                        if (types[j] == "sublocality_level_1") {
                                            addressLine1.add(jsonObject.getString("long_name"))
                                        }
                                        if (types[j] == "country") {
                                            addressLine1.add(jsonObject.getString("long_name"))
                                        }
                                        if (types[j] == "administrative_area_level_1") {
                                            addressLine1.add(jsonObject.getString("long_name"))
                                        }
                                        if (types[j] == "administrative_area_level_3") {
                                            addressLine1.add(jsonObject.getString("long_name"))
                                        }
                                        if (types[j] == "postal_code") {
                                            addressLine1.add(jsonObject.getString("long_name"))
                                        }
                                    }
                                }

                                store_address_ET.text = addressLine1.joinToString(",")
                            }

                        } catch (e: java.lang.Exception) {

                        }

                        location = response!!.getJSONArray("results").getJSONObject(0)
                            .getJSONObject("geometry").getJSONObject("location")
                        if (location.getDouble("lat") != 0.0 && location.getDouble("lng") != 0.0) {
                            val latLng =
                                LatLng(location.getDouble("lat"), location.getDouble("lng"))

                            //Do what you want
                        }
                    } catch (e1: JSONException) {
                        e1.printStackTrace()
                    }
                }
            ) { error -> Log.d("Error.Response", error.toString()) }

        queue.add(stateReq)


    }

    override fun deleteItem(position: Int) {
        BrandList.removeAt(position)
        brandAdaptor.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun deleteRetailer(position: Int) {
        try {
            RetailerList.removeAt(position)
            WholeSalerList.removeAt(position)
            supplierAdaptor.notifyItemChanged(position)
            supplierAdaptor.notifyDataSetChanged()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun values(flag: Boolean, monthlyRevenue: String, position: Int) {
        values = true
        checkValue = monthlyRevenue
        for (i in 0 until data.size) {
            data.get(i).flag = i == position
        }

        adapter.notifyDataSetChanged()
    }

    fun fillformAPI() {

        if (androidextention.isOnline(this)) {
            fillform_progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<RetailerFillformResponse> =
                ApiCallBack<RetailerFillformResponse>(object :
                    ApiResponseListener<RetailerFillformResponse> {
                    override fun onApiSuccess(
                        response: RetailerFillformResponse,
                        apiName: String?
                    ) {
                        fillform_progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                var intent = Intent(
                                    this@fillDetails_retailer,
                                    NewUploadDocumentActivity::class.java
                                )
                                intent.putExtra("flag", "retailer")
                                startActivity(intent)
                                finish()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        fillform_progressbar.visibility = View.GONE

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(
                                pojo.responseMessage,
                                this@fillDetails_retailer
                            )

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        fillform_progressbar.visibility = View.GONE
                    }

                }, "fillformAPI", mContext)


            var businessDetails = BusinessDetails()
            businessDetails.companyName = Companyname_ET.text.toString()
            businessDetails.businessRegNumber = businessRegistrationET.text.toString()
//            businessDetails.categoryId = ""
            businessDetails.websiteUrl = Website_ET.text.toString()
            businessDetails.socialMediaAccounts = Socialmedia_ET.text.toString()
            businessDetails.isVatRegistered = vatFlag
            businessDetails.vatNumber = vat_edittext.text.toString()
            businessDetails.monthlyRevenue = checkValue



            var businessBankingDetails = BusinessBankingDetails()
            businessBankingDetails.bankName = Bankname_ET.text.toString()
            businessBankingDetails.accountNumber = Accountnumber_ET.text.toString()
            businessBankingDetails.branchCode = Branchcode_ET.text.toString()
            businessBankingDetails.branchName = Branchname_ET.text.toString()
            businessBankingDetails.accountName = Accountname_ET.text.toString()
            businessBankingDetails.swiftCode = SWIFTcode_ET.text.toString()
            businessBankingDetails.accountType = requestAccountType

            var fillFormRequest = FillFormRequest()

            var value = unique_product.text.toString()
            fillFormRequest.flag = 1
            fillFormRequest.ownerFirstName = FirstName_ET.text.toString()
            fillFormRequest.ownerLastName = LastName_ET.text.toString()
            fillFormRequest.ownerEmail = Email_ET.text.toString()
            fillFormRequest.noOfUniqueProducts = Integer.parseInt(value)
            fillFormRequest.keepStock = keepStockValue
            fillFormRequest.isPhysicalStore = PhysicalStore
            fillFormRequest.comments = comment_ET.text.toString()
            fillFormRequest.businessDetails = businessDetails
            fillFormRequest.businessBankingDetails = businessBankingDetails

            var arrayFinalData = ArrayList<String>()
            for (i in 0 until BrandList.size) {
                arrayFinalData.add(BrandList.get(i).brandName.toString())
            }
            fillFormRequest.listOfBrandOrProducts = arrayFinalData

            var arrayRetailerData = ArrayList<String>()
            for (i in 0 until RetailerList.size) {
                arrayRetailerData.add(RetailerList.get(i).brandName.toString())
            }
            fillFormRequest.preferredSupplierOrWholeSalerId = arrayRetailerData
            Log.d("retailer form :${fillFormRequest.toString()}", "request")

//            if (physical_yes.isChecked == true) {

                fillFormRequest.storeName = store_name_ET.text.toString()
                fillFormRequest.storeAddress = store_address_ET.text.toString()
                fillFormRequest.storeContactNo = store_contact_ET.text.toString()
//                fillFormRequest.storeLocation = requestStoreLocation
//            }
            try {
                serviceManager.fillformapi(callBack, fillFormRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection.", this)

        }
    }

    fun initializedControl() {


        FirstName_ET.addTextChangedListener(textWatcher)
        LastName_ET.addTextChangedListener(textWatcher)
        Email_ET.addTextChangedListener(textWatcher)
        unique_product.addTextChangedListener(textWatcher)
        et_brand_name.addTextChangedListener(textWatcher)
        store_name_ET.addTextChangedListener(textWatcher)
        store_address_ET.addTextChangedListener(textWatcher)
        store_contact_ET.addTextChangedListener(textWatcher)
        vat_edittext.addTextChangedListener(textWatcher)
        Companyname_ET.addTextChangedListener(textWatcher)
        businessRegistrationET.addTextChangedListener(textWatcher)
        Bankname_ET.addTextChangedListener(textWatcher)
        Branchname_ET.addTextChangedListener(textWatcher)
        Branchcode_ET.addTextChangedListener(textWatcher)
        SWIFTcode_ET.addTextChangedListener(textWatcher)
        Accountname_ET.addTextChangedListener(textWatcher)
        Accountnumber_ET.addTextChangedListener(textWatcher)

    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            callValidation()
        }
    }

    fun GetFillFormDetails() {
        if (androidextention.isOnline(mContext)) {
            fillform_progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<RetailerFillDeatilsViewResponse> =
                ApiCallBack<RetailerFillDeatilsViewResponse>(object :
                    ApiResponseListener<RetailerFillDeatilsViewResponse> {
                    override fun onApiSuccess(
                        response: RetailerFillDeatilsViewResponse,
                        apiName: String?
                    ) {
                        fillform_progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
//                            try {
                            FirstName_ET.text = Editable.Factory.getInstance()
                                .newEditable(response.result.ownerFirstName)
                            LastName_ET.text = Editable.Factory.getInstance()
                                .newEditable(response.result.ownerLastName)
                            Email_ET.text = Editable.Factory.getInstance()
                                .newEditable(response.result.ownerEmail)
                            unique_product.text = Editable.Factory.getInstance()
                                .newEditable(response.result.noOfUniqueProducts.toString())

                            if (response.result.listOfBrandOrProducts.size > 0) {
                                rv_add_product_brand.visibility = View.VISIBLE
                                for(i in 0 until response.result.listOfBrandOrProducts.size) {
                                    BrandList.add(BrandList(response.result.listOfBrandOrProducts.get(i).toString()))
                                }
                            } else {
                                rv_add_product_brand.visibility = View.GONE
                            }
                            BarndDetails()

                            if (response.result.keepStock) {
                                stock_yes.isChecked = true
                                keepStockValue = true
                            }

                            if (response.result.isPhysicalStore) {
                                store_ll.visibility = View.VISIBLE
                                physical_yes.isChecked = true
                                PhysicalStore = true
                                store_name_ET.text = Editable.Factory.getInstance().newEditable(response.result.storeName)
                                store_address_ET.text = response.result.storeAddress
                                store_contact_ET.text = Editable.Factory.getInstance().newEditable(response.result.storeContactNo)
                            } else {
                                store_ll.visibility = View.GONE
                                physical_no.isChecked = true
                                PhysicalStore = false
                            }

                            if (response.result.preferredSupplierOrWholeSalerId.size > 0) {
                                rv_add_suppliers.visibility= View.VISIBLE
                                for (i in 0 until response.result.preferredSupplierOrWholeSalerId.size) {

                                    if (WholeSalerList.isEmpty()){
                                        WholeSalerList.add(
                                            WholesalerList(
                                                "${response.result.preferredSupplierOrWholeSalerId[i].firstName}" +
                                                        " ${response.result.preferredSupplierOrWholeSalerId[i].lastName}",
                                                response.result.preferredSupplierOrWholeSalerId[i]._id))
                                    }else{
                                        for (id in WholeSalerList.indices){
                                            if(WholeSalerList[id].id != response.result.preferredSupplierOrWholeSalerId[i]._id){
                                                WholeSalerList.add(
                                                    WholesalerList(
                                                        "${response.result.preferredSupplierOrWholeSalerId[i].firstName}" +
                                                                " ${response.result.preferredSupplierOrWholeSalerId[i].lastName}",
                                                        response.result.preferredSupplierOrWholeSalerId[i]._id))
                                            }else{
                                                androidextention.alertBox("This is already added", this@fillDetails_retailer)
                                                break
                                            }
                                        }
                                    }



                                    RetailerList.add(
                                        BrandList(
                                            "${response.result.preferredSupplierOrWholeSalerId[i]._id}"
                                        )
                                    )
                                }
                            } else {
                                rv_add_suppliers.visibility= View.GONE
                            }
                            SupplierDetails()

                            comment_ET.text = Editable.Factory.getInstance()
                                .newEditable(response.result.comments)
                            Companyname_ET.text = Editable.Factory.getInstance()
                                .newEditable(response.result.businessDetails.companyName)
                            businessRegistrationET.text = Editable.Factory.getInstance()
                                .newEditable(response.result.businessDetails.businessRegNumber)
                            Website_ET.text = Editable.Factory.getInstance()
                                .newEditable(response.result.businessDetails.websiteUrl)
                            Socialmedia_ET.text = Editable.Factory.getInstance()
                                .newEditable(response.result.businessDetails.socialMediaAccounts)

                            if (response.result.businessDetails.isVatRegistered) {
                                RB_yes.isChecked = true
                                ll_VAT.isVisible = true
                                vatFlag = true
                                vat_edittext.text = Editable.Factory.getInstance()
                                    .newEditable(response.result.businessDetails.vatNumber)
                            }

                            for (i in 0 until data.size) {
                                if (data.get(i).Checkbox.equals(response.result.businessDetails.monthlyRevenue)) {
                                    data.get(i).flag = true
                                    values = true
                                    adapter.notifyDataSetChanged()
                                }
                            }

                            checkValue = response.result.businessDetails.monthlyRevenue

//                                setCheckBoxAdapter()

                            Bankname_ET.text = Editable.Factory.getInstance()
                                .newEditable(response.result.businessBankingDetails.bankName)
                            Branchname_ET.text = Editable.Factory.getInstance()
                                .newEditable(response.result.businessBankingDetails.branchName)
                            Branchcode_ET.text = Editable.Factory.getInstance()
                                .newEditable(response.result.businessBankingDetails.branchCode)
                            SWIFTcode_ET.text = Editable.Factory.getInstance()
                                .newEditable(response.result.businessBankingDetails.swiftCode)
//                                Account_type_ET.text = Editable.Factory.getInstance()
//                                    .newEditable(response.result.businessBankingDetails.accountType)
                            Accountname_ET.text = Editable.Factory.getInstance()
                                .newEditable(response.result.businessBankingDetails.accountName)
                            Accountnumber_ET.text = Editable.Factory.getInstance()
                                .newEditable(response.result.businessBankingDetails.accountNumber)

                            SET_ACCOUNT_TYPE(response.result.businessBankingDetails.accountType)

//                            } catch (e: Exception) {
//                                e.printStackTrace()
//                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        fillform_progressbar.visibility = View.GONE

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        fillform_progressbar.visibility = View.GONE

                    }
                }, "FillDetailsView", mContext)
            try {
                serviceManager.FillDetailsView(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection.", this)

        }

    }

    fun SET_ACCOUNT_TYPE(accountType: String) {
        if(accountType == "Savings Account") {
            account_type.setSelection(1)
        } else if (accountType == "Current Account") {
            account_type.setSelection(2)
        } else if (accountType == "Business Account") {
            account_type.setSelection(3)
        } else if (accountType == "Cheque Account") {
            account_type.setSelection(4)
        } else if (accountType == "Transmission Account") {
            account_type.setSelection(5)
        }

    }

    fun setCheckBoxAdapter() {
        checkboxrecycler.layoutManager = LinearLayoutManager(mContext)
        adapter = fill_details_checkbox_Adapter(mContext, data, this)
        checkboxrecycler.adapter = adapter

    }


    fun UpdateProfileAPI() {
        if (androidextention.isOnline(this)) {
            fillform_progressbar.visibility = View.VISIBLE

            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<CommonResponseForAll> =
                ApiCallBack<CommonResponseForAll>(object :
                    ApiResponseListener<CommonResponseForAll> {
                    override fun onApiSuccess(response: CommonResponseForAll, apiName: String?) {
                        fillform_progressbar.visibility = View.GONE

                        if (response.responseCode == 200) {

                            try {
                                val intent = Intent(
                                    this@fillDetails_retailer,
                                    NewUploadDocumentActivity::class.java
                                )
                                intent.putExtra("flag", "retailer")
                                startActivity(intent)
                                finish()
//                                SavedPrefManager.saveStringPreferences(this@EditProfileActivity, SavedPrefManager.UPDATE_PROFILE, "TRUE")
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        fillform_progressbar.visibility = View.GONE

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
                        fillform_progressbar.visibility = View.GONE

                    }

                }, "UpdateProfileAPI", mContext)


            var businessDetails = BusinessDetails()
            businessDetails.companyName = Companyname_ET.text.toString()
            businessDetails.businessRegNumber = businessRegistrationET.text.toString()
//            businessDetails.categoryId = ""
            businessDetails.websiteUrl = Website_ET.text.toString()
            businessDetails.socialMediaAccounts = Socialmedia_ET.text.toString()
            businessDetails.isVatRegistered = vatFlag
            businessDetails.vatNumber = vat_edittext.text.toString()
            businessDetails.monthlyRevenue = checkValue


            var businessBankingDetails = BusinessBankingDetails()
            businessBankingDetails.bankName = Bankname_ET.text.toString()
            businessBankingDetails.accountNumber = Accountnumber_ET.text.toString()
            businessBankingDetails.branchCode = Branchcode_ET.text.toString()
            businessBankingDetails.branchName = Branchname_ET.text.toString()
            businessBankingDetails.accountName = Accountname_ET.text.toString()
            businessBankingDetails.swiftCode = SWIFTcode_ET.text.toString()
            businessBankingDetails.accountType = requestAccountType

            var fillFormRequest = FillFormRequest()

            var value = unique_product.text.toString()
            fillFormRequest.ownerFirstName = FirstName_ET.text.toString()
            fillFormRequest.ownerLastName = LastName_ET.text.toString()
            fillFormRequest.ownerEmail = Email_ET.text.toString()
            fillFormRequest.noOfUniqueProducts = Integer.parseInt(value)
            fillFormRequest.keepStock = keepStockValue
            fillFormRequest.isPhysicalStore = PhysicalStore
            fillFormRequest.comments = comment_ET.text.toString()
            fillFormRequest.businessDetails = businessDetails
            fillFormRequest.businessBankingDetails = businessBankingDetails

            var arrayFinalData = ArrayList<String>()
            for (i in 0 until BrandList.size) {
                arrayFinalData.add(BrandList.get(i).brandName.toString())
            }
            fillFormRequest.listOfBrandOrProducts = arrayFinalData

            var arrayRetailerData = ArrayList<String>()
            for (i in 0 until RetailerList.size) {
                arrayRetailerData.add(RetailerList.get(i).brandName.toString())
            }
            fillFormRequest.preferredSupplierOrWholeSalerId = arrayRetailerData

            if (physical_yes.isChecked == true) {

                fillFormRequest.storeName = store_name_ET.text.toString()
                fillFormRequest.storeAddress = store_address_ET.text.toString()
                fillFormRequest.storeContactNo = store_contact_ET.text.toString()
            }
            Log.d("retailer form :${fillFormRequest.toString()}", "request")
            try {
                serviceManager.Updateprofilefillformapi(callBack, fillFormRequest)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection.", this)

        }
    }

    fun BarndDetails() {
        rv_add_product_brand.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        brandAdaptor = BrandAdaptor(this, BrandList, this)
        rv_add_product_brand.adapter = brandAdaptor
    }

    fun SupplierDetails() {
        rv_add_suppliers.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        supplierAdaptor = SupplierAdaptor(this, WholeSalerList, this)
        rv_add_suppliers.adapter = supplierAdaptor
    }


    fun wholesalerListApi(search: String) {
        if (androidextention.isOnline(mContext)) {
            fillform_progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<wholesaler_list_response> =
                ApiCallBack<wholesaler_list_response>(object :
                    ApiResponseListener<wholesaler_list_response> {
                    override fun onApiSuccess(
                        response: wholesaler_list_response,
                        apiName: String?
                    ) {
                        fillform_progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                wholesalerdata = emptyList()
                                wholesalerdata = response.result?.docs!!
                                if (wholesalerdata.isNotEmpty()) {
                                    openWhileSalersPopUp()
                                } else {
                                    androidextention.alertBox("No data found.", this@fillDetails_retailer)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        fillform_progressbar.visibility = View.GONE

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        fillform_progressbar.visibility = View.GONE
                        Toast.makeText(
                            mContext,
                            "${failureMessage}",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                }, "wholesalerListApi", mContext)

            val jsonObject = JsonObject()
            jsonObject.addProperty("search", search)

            try {
                serviceManager.wholesaler_listApi(callBack, jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection.", this)

        }
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    fun openWhileSalersPopUp() {

        try {
            val binding = LayoutInflater.from(this).inflate(R.layout.pop_lists, null)
            dialog = DialogUtils().createDialog(this, binding.rootView, 0)!!
            val recyclerViewPopUp = binding.findViewById<RecyclerView>(R.id.popup_recyclerView)
            var no_notification = binding.findViewById<LinearLayout>(R.id.no_notification)

            var progressbarpopup = binding.findViewById<ProgressBar>(R.id.progressbar_pop)
            val dialougTitle = binding.findViewById<TextView>(R.id.popupTitle)
            val dialougbackButton = binding.findViewById<ImageView>(R.id.BackButton)
            val SearchEditText = binding.findViewById<EditText>(R.id.search_bar_edittext_popuplist)
            dialougTitle.setText("Suppliers/Wholesalers")

            recyclerViewPopUp.layoutManager = LinearLayoutManager(this)
            val wholeSalersListPopUpAdaptor =
                WholeSalersListPopUpAdaptor(this, wholesalerdata, this)
            recyclerViewPopUp.adapter = wholeSalersListPopUpAdaptor

            SearchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(text: Editable?) {
                    filterData(text.toString(), wholeSalersListPopUpAdaptor)
                }

            })
            dialougbackButton.setSafeOnClickListener { dialog.dismiss() }

            dialog.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun selectWholeSalerClick(fullName: String, _id: String) {
        dialog.dismiss()
        RetailerList.add(BrandList(_id))
        if (WholeSalerList.isEmpty()) {
            WholeSalerList.add(WholesalerList(fullName, _id))
            SupplierDetails()
            supplierAdaptor.notifyDataSetChanged()
            et_suppliers_name.text.clear()
        } else {
            var alreadyExists = false
            for (wholesaler in WholeSalerList) {
                if (wholesaler.id == _id) {
                    alreadyExists = true
                    break
                }
            }
            if (!alreadyExists) {
                WholeSalerList.add(WholesalerList(fullName, _id))
                SupplierDetails()
                supplierAdaptor.notifyDataSetChanged()
                et_suppliers_name.text.clear()
            } else {
                androidextention.alertBox("This is already added", this)
            }
        }






    }

    private fun filterData(
        searchText: String,
        wholeSalersListPopUpAdaptor: WholeSalersListPopUpAdaptor
    ) {
        var filteredList: ArrayList<wholesaler_listDocs> = ArrayList()


        if (wholesalerdata != null) {
            for (item in wholesalerdata) {
                try {
                    if (item.firstName.toLowerCase()
                            .contains(searchText.toLowerCase()) || item.lastName.toLowerCase()
                            .contains(searchText.toLowerCase())
                    ) {
                        filteredList.add(item)
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }


        try {

            wholeSalersListPopUpAdaptor.filterList(filteredList)


        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }


    fun accountTypeSpinner() {
        val accountTypeAdaptor: ArrayAdapter<String> = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.account_list)
        )
        accountTypeAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        account_type.setAdapter(accountTypeAdaptor)
    }

    private fun callValidation() {
        FormValidation.Retailer_fillform(
            FirstName_ET, first_nametv,
            LastName_ET, lastname_tv,
            Email_ET, emailaddress_tv,
            unique_product, uniqueproduct_tv,
            et_brand_name,
            BrandList, list_brands_tv,
            Companyname_ET,
            company_name_TV,
            businessRegistrationET,
            businessregistration_TV,
            RB_yes,
            RB_no,
            vat_edittext,
            VAT_TV,
            values,
            Monthly_RevenueTextview,
            Bankname_ET,
            bank_name_TV,
            Branchname_ET,
            branch_name_TV,
            Branchcode_ET,
            branch_code_TV,
            SWIFTcode_ET,
            swiftcode_TV,
            Accountname_ET,
            account_name_TV,
            Accountnumber_ET,
            account_number_TV,
            account_type_ll,
            accounttype_TV,
            requestAccountType,
            physical_yes,
            physical_no,
            store_name_ET,
            store_name_tv,
            store_address_ET,
            store_address_tv,
            store_contact_ET,
            store_contact_tv
        )
    }

}