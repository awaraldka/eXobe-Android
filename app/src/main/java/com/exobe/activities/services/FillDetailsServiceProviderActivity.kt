package com.exobe.activities.services

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.example.validationpractice.utlis.FormValidation
import com.exobe.adaptor.*
import com.exobe.Model.Checkboxmodalclass
import com.exobe.Model.ServicesList
import com.exobe.modelClass.myModel
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.androidextention
import com.exobe.customClicks.switchValue
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.BusinessBankingDetails
import com.exobe.entity.request.BusinessDetails
import com.exobe.entity.request.SP_FillFormRequest
import com.exobe.entity.response.RetailerFillDeatilsViewResponse
import com.exobe.entity.response.RetailerFillformResponse
import com.exobe.entity.response.CommonResponseForAll
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import java.lang.Exception


class FillDetailsServiceProviderActivity : AppCompatActivity(), switchValue {
    lateinit var title: TextView


    lateinit var back: ImageView

    lateinit var business_form_submit: Button
    lateinit var ll_VAT: LinearLayout
    lateinit var RB_yes: RadioButton
    lateinit var RB_no: RadioButton
    lateinit var checkbox1: CheckBox
    lateinit var checkbox2: CheckBox
    lateinit var checkbox3: CheckBox
    lateinit var checkbox4: CheckBox
    lateinit var checkbox5: CheckBox
    lateinit var progressbar_filldetails: ProgressBar
    var array: ArrayList<ServicesList> = ArrayList()
    lateinit var adapter: PopupStateDialogRecyclerViewAdapter
    var flag = ""
    lateinit var dialog: Dialog
    lateinit var selectAllCheckBox: CheckBox
    var isCheckBoxSelected: Boolean = false


    //    var array1: ArrayList<ServicesList> = ArrayList()


    lateinit var FirstName_ET: EditText
    lateinit var LastName_ET: EditText
    lateinit var Email_ET: EditText
    lateinit var Companyname_ET: EditText
    lateinit var businessRegistrationET: EditText

    //    lateinit var Category_ET: EditText
    lateinit var Website_ET: EditText
    lateinit var Socialmedia_ET: EditText
    lateinit var Bankname_ET: EditText
    lateinit var Branchcode_ET: EditText
    lateinit var Branchname_ET: EditText
    lateinit var SWIFTcode_ET: EditText
    lateinit var Accountname_ET: EditText
    lateinit var Accountnumber_ET: EditText
    lateinit var Vat_ET: EditText
    var values: Boolean = false


    var data: java.util.ArrayList<Checkboxmodalclass> = java.util.ArrayList()
    lateinit var Adapter: fill_details_checkbox_Adapter


    lateinit var first_nametv: TextView
    lateinit var lastname_tv: TextView
    lateinit var emailaddress_tv: TextView
    lateinit var company_name_TV: TextView
    lateinit var businessregistration_TV: TextView
    lateinit var VAT_TV: TextView
    lateinit var Monthly_RevenueTextview: TextView
    lateinit var bank_name_TV: TextView
    lateinit var branch_name_TV: TextView
    lateinit var branch_code_TV: TextView
    lateinit var swiftcode_TV: TextView
    lateinit var accounttype_TV: TextView
    lateinit var account_name_TV: TextView
    lateinit var account_number_TV: TextView

    var keepStockValue = true
    var PhysicalStore = true
    var monthlyRevenue = ""

    var vatFlag = false
    var selectedCountryString = ""
    lateinit var operationalCountryAdapter: OperationalSubCategoryRecycler
    lateinit var selectedOperationalCountryAdapter: GetValueSubCategoryRecycler
    var arrayList: ArrayList<String> = ArrayList()
    lateinit var globalCompanyId: String
    lateinit var operationalCountryDialog: Dialog
    var countryListArray: ArrayList<myModel> = ArrayList()
    var selectedCountryListArray: ArrayList<myModel> = ArrayList()
    lateinit var selectedCountryRecycler: RecyclerView
    lateinit var operationalRecyclerView: RecyclerView
    lateinit var checkboxrecycler: RecyclerView
    lateinit var checkValue: String

    lateinit var mContext: Context

    var countryCode: String = ""
    var state: String = ""
    var city: String = ""
    lateinit var recyclerView: RecyclerView

    lateinit var account_type: Spinner
    lateinit var account_type_ll: LinearLayout
    var requestAccountType = ""
    var loginflag = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filldetails_service_provider)
        window.attributes.windowAnimations = R.style.Fade
        mContext = application
        title = findViewById(R.id.title)
        account_type_ll = findViewById(R.id.account_type_ll)
        account_type = findViewById(R.id.account_type)
        business_form_submit = findViewById(R.id.business_form_submit_SV)
        back = findViewById(R.id.back)
        ll_VAT = findViewById(R.id.ll_VAT)
        RB_yes = findViewById(R.id.RB_yes)
        RB_no = findViewById(R.id.RB_no)
        checkbox1 = findViewById(R.id.Monthly_checkbox1)
        checkbox2 = findViewById(R.id.Monthly_checkbox2)
        checkbox3 = findViewById(R.id.Monthly_checkbox3)
        checkbox4 = findViewById(R.id.Monthly_checkbox4)
        checkbox5 = findViewById(R.id.Monthly_checkbox5)
        checkboxrecycler = findViewById(R.id.checkbox_recycler)
        progressbar_filldetails = findViewById(R.id.progressbar_filldetails)

        FirstName_ET = findViewById(R.id.FirstName_ET)
        LastName_ET = findViewById(R.id.LastName_ET)
        Email_ET = findViewById(R.id.Email_ET)
        Companyname_ET = findViewById(R.id.Companyname_ET)
        businessRegistrationET = findViewById(R.id.businessRegistrationET)
//        Category_ET = findViewById(R.id.Category_ET)
        Website_ET = findViewById(R.id.Website_ET)
        Socialmedia_ET = findViewById(R.id.Socialmedia_ET)
        Bankname_ET = findViewById(R.id.Bankname_ET)
        Branchcode_ET = findViewById(R.id.Branchcode_ET)
        Branchname_ET = findViewById(R.id.Branchname_ET)
        SWIFTcode_ET = findViewById(R.id.SWIFTcode_ET)
        Accountname_ET = findViewById(R.id.Accountname_ET)
        Accountnumber_ET = findViewById(R.id.Accountnumber_ET)
        Vat_ET = findViewById(R.id.Vat_ET)


        first_nametv = findViewById(R.id.first_nametv)
        lastname_tv = findViewById(R.id.lastname_tv)
        emailaddress_tv = findViewById(R.id.emailaddress_tv)
        company_name_TV = findViewById(R.id.company_name_TV)
        businessregistration_TV = findViewById(R.id.businessregistration_TV)
        VAT_TV = findViewById(R.id.VAT_TV)
        Monthly_RevenueTextview = findViewById(R.id.Monthly_RevenueTextview)
//        Socialmedia_ET = findViewById(R.id.Socialmedia_ET)
        bank_name_TV = findViewById(R.id.bank_name_TV)
        branch_name_TV = findViewById(R.id.branch_name_TV)
        branch_code_TV = findViewById(R.id.branch_code_TV)
        swiftcode_TV = findViewById(R.id.swiftcode_TV)
        accounttype_TV = findViewById(R.id.accounttype_TV)
        account_name_TV = findViewById(R.id.account_name_TV)
        account_number_TV = findViewById(R.id.account_number_TV)
        Companyname_ET = findViewById(R.id.Companyname_ET)
        businessRegistrationET = findViewById(R.id.businessRegistrationET)
        Website_ET = findViewById(R.id.Website_ET)
        Socialmedia_ET = findViewById(R.id.Socialmedia_ET)
        Bankname_ET = findViewById(R.id.Bankname_ET)
        Branchcode_ET = findViewById(R.id.Branchcode_ET)
        Branchname_ET = findViewById(R.id.Branchname_ET)
        SWIFTcode_ET = findViewById(R.id.SWIFTcode_ET)
        Accountname_ET = findViewById(R.id.Accountname_ET)
        Accountnumber_ET = findViewById(R.id.Accountnumber_ET)
        Vat_ET = findViewById(R.id.Vat_ET)



        company_name_TV = findViewById(R.id.company_name_TV)
        businessregistration_TV = findViewById(R.id.businessregistration_TV)
        VAT_TV = findViewById(R.id.VAT_TV)
        Monthly_RevenueTextview = findViewById(R.id.Monthly_RevenueTextview)

        bank_name_TV = findViewById(R.id.bank_name_TV)
        branch_name_TV = findViewById(R.id.branch_name_TV)
        branch_code_TV = findViewById(R.id.branch_code_TV)
        swiftcode_TV = findViewById(R.id.swiftcode_TV)
        accounttype_TV = findViewById(R.id.accounttype_TV)
        account_name_TV = findViewById(R.id.account_name_TV)
        account_number_TV = findViewById(R.id.account_number_TV)

        title.text = "Business Details"
        accountTypeSpinner()

        if (intent.getStringExtra("loginflag") != null) {
            loginflag = intent.getStringExtra("loginflag").toString()
        }

        RB_yes.setSafeOnClickListener {
            vatFlag = true
            ll_VAT.visibility = View.VISIBLE

        }
        RB_no.setSafeOnClickListener {
            vatFlag = false
            ll_VAT.visibility = View.GONE

        }
        data.add(Checkboxmodalclass("Less than R20K", false))
        data.add(Checkboxmodalclass("R20K-R50K", false))
        data.add(Checkboxmodalclass("R50K-R100K", false))
        data.add(Checkboxmodalclass("R100K-R500K", false))
        data.add(Checkboxmodalclass("More than R500K", false))
        CheckboxAdaptor()


        if (loginflag != "loginflag") {
            GetFillFormDetails()
        }


        back.setSafeOnClickListener {
            finish()
        }


        account_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                var data = parent.getItemAtPosition(pos).toString()
                requestAccountType = data

                if (!data.equals("Select")) {
                    FormValidation.filldetails(
                        FirstName_ET,
                        first_nametv,
                        LastName_ET,
                        lastname_tv,
                        Email_ET,
                        emailaddress_tv,
                        Companyname_ET,
                        company_name_TV,
                        businessRegistrationET,
                        businessregistration_TV,
                        RB_yes,
                        RB_no,
                        Vat_ET,
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
                        accounttype_TV,
                        Accountname_ET,
                        account_name_TV,
                        Accountnumber_ET,
                        account_number_TV,
                        account_type_ll,
                        requestAccountType,
                    )

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        initializedControl()
        business_form_submit.setSafeOnClickListener {
            FormValidation.filldetails(
                FirstName_ET,
                first_nametv,
                LastName_ET,
                lastname_tv,
                Email_ET,
                emailaddress_tv,
                Companyname_ET,
                company_name_TV,
                businessRegistrationET,
                businessregistration_TV,
                RB_yes,
                RB_no,
                Vat_ET,
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
                accounttype_TV,
                Accountname_ET,
                account_name_TV,
                Accountnumber_ET,
                account_number_TV,
                account_type_ll,
                requestAccountType,
            )

            if (FirstName_ET.text.toString().isNotEmpty() &&
                LastName_ET.text.isNotEmpty() &&
                Email_ET.text.isNotEmpty() &&
                Email_ET.text.matches(Regex(FormValidation.emailPattern)) &&
                Companyname_ET.text.toString().isNotEmpty() &&
                businessRegistrationET.text.toString().isNotEmpty() &&
                Bankname_ET.text.toString().isNotEmpty() &&
                Branchname_ET.text.toString().isNotEmpty() &&
                Branchcode_ET.text.toString().isNotEmpty() &&
                SWIFTcode_ET.text.toString().isNotEmpty() &&
                Accountname_ET.text.toString().isNotEmpty() &&
                Accountnumber_ET.text.toString().isNotEmpty() && values

            ) {
                if (RB_yes.isChecked) {
                    if (
                        FirstName_ET.text.toString().isNotEmpty() &&
                        LastName_ET.text.isNotEmpty() &&
                        Email_ET.text.isNotEmpty() &&
                        Email_ET.text.matches(Regex(FormValidation.emailPattern)) &&
                        Companyname_ET.text.toString().isNotEmpty() &&
                        businessRegistrationET.text.toString().isNotEmpty() &&
                        Vat_ET.text.toString().isNotEmpty() &&
                        Bankname_ET.text.toString().isNotEmpty() &&
                        Branchname_ET.text.toString().isNotEmpty() &&
                        Branchcode_ET.text.toString().isNotEmpty() &&
                        !SWIFTcode_ET.text.toString().isEmpty() &&
                        Accountname_ET.text.toString().isNotEmpty() &&
                        Accountnumber_ET.text.toString().isNotEmpty() && values
                    ) {
                        if (loginflag == "loginflag") {
                            fillformServiceProviderAPI()
                        } else {
                            UpdateProfileAPI()
                        }
                    }
                }
                if (!RB_yes.isChecked) {

                    if (
                        FirstName_ET.text.toString().isNotEmpty() &&
                        LastName_ET.text.isNotEmpty() &&
                        Email_ET.text.isNotEmpty() &&
                        Email_ET.text.matches(Regex(FormValidation.emailPattern)) &&
                        Companyname_ET.text.toString().isNotEmpty() &&
                        businessRegistrationET.text.toString().isNotEmpty() &&
                        Bankname_ET.text.toString().isNotEmpty() &&
                        Branchname_ET.text.toString().isNotEmpty() &&
                        Branchcode_ET.text.toString().isNotEmpty() &&
                        SWIFTcode_ET.text.toString().isNotEmpty() &&
                        Accountname_ET.text.toString().isNotEmpty() &&
                        Accountnumber_ET.text.toString().isNotEmpty() && values
                    ) {
                        if (loginflag == "loginflag") {
                            fillformServiceProviderAPI()
                        } else {
                            UpdateProfileAPI()
                        }

                    }
                }



            }
        }





    }

    fun CheckboxAdaptor() {
        checkboxrecycler.layoutManager = LinearLayoutManager(mContext)
        Adapter = fill_details_checkbox_Adapter(mContext, data, this)
        checkboxrecycler.adapter = Adapter
    }

    fun UpdateProfileAPI() {
        if (androidextention.isOnline(this)) {
            progressbar_filldetails.visibility = View.VISIBLE

            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<CommonResponseForAll> =
                ApiCallBack<CommonResponseForAll>(object :
                    ApiResponseListener<CommonResponseForAll> {
                    override fun onApiSuccess(response: CommonResponseForAll, apiName: String?) {
                        progressbar_filldetails.visibility = View.GONE

                        if (response.responseCode == 200) {

                            try {

                                alertBox("Your Business details have been updated let's continue to next step.", "Profile")

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_filldetails.visibility = View.GONE

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
                        progressbar_filldetails.visibility = View.GONE

                    }

                }, "UpdateProfileAPI", mContext)


            var businessDetails = BusinessDetails()
            businessDetails.companyName = Companyname_ET.text.toString()
            businessDetails.businessRegNumber = businessRegistrationET.text.toString()
//            businessDetails.categoryId = ""
            businessDetails.websiteUrl = Website_ET.text.toString()
            businessDetails.socialMediaAccounts = Socialmedia_ET.text.toString()
            businessDetails.isVatRegistered = vatFlag
            if (vatFlag) {
                businessDetails.vatNumber = Vat_ET.text.toString()
            }
            businessDetails.monthlyRevenue = checkValue

            var businessBankingDetails = BusinessBankingDetails()
            businessBankingDetails.bankName = Bankname_ET.text.toString()
            businessBankingDetails.accountNumber = Accountnumber_ET.text.toString()
            businessBankingDetails.branchCode = Branchcode_ET.text.toString()
            businessBankingDetails.branchName = Branchname_ET.text.toString()
            businessBankingDetails.accountName = Accountname_ET.text.toString()
            businessBankingDetails.swiftCode = SWIFTcode_ET.text.toString()
            businessBankingDetails.accountType = requestAccountType

            var fillFormRequest = SP_FillFormRequest()
            fillFormRequest.ownerFirstName = FirstName_ET.text.toString()
            fillFormRequest.ownerLastName = LastName_ET.text.toString()
            fillFormRequest.ownerEmail = Email_ET.text.toString()
            fillFormRequest.keepStock = keepStockValue
            fillFormRequest.businessDetails = businessDetails
            fillFormRequest.businessBankingDetails = businessBankingDetails


            try {
                serviceManager.sp_Updateprofilefillformapi(callBack, fillFormRequest)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection.", this)

        }
    }


    fun GetFillFormDetails() {
        if (androidextention.isOnline(mContext)) {
            progressbar_filldetails.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<RetailerFillDeatilsViewResponse> =
                ApiCallBack<RetailerFillDeatilsViewResponse>(object :
                    ApiResponseListener<RetailerFillDeatilsViewResponse> {
                    override fun onApiSuccess(
                        response: RetailerFillDeatilsViewResponse,
                        apiName: String?
                    ) {
                        progressbar_filldetails.visibility = View.GONE
                        if (response.responseCode == 200) {
//                            try {
                            FirstName_ET.text = Editable.Factory.getInstance()
                                .newEditable(response.result.ownerFirstName)
                            LastName_ET.text = Editable.Factory.getInstance()
                                .newEditable(response.result.ownerLastName)
                            Email_ET.text = Editable.Factory.getInstance()
                                .newEditable(response.result.ownerEmail)


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
                                Vat_ET.text = Editable.Factory.getInstance()
                                    .newEditable(response.result.businessDetails.vatNumber)
                            }

                            for (i in 0 until data.size) {
                                if (data[i].Checkbox!!.contains(response.result.businessDetails.monthlyRevenue)) {
                                    data[i].flag = true
                                    values = true
                                    Adapter.notifyDataSetChanged()
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
                        progressbar_filldetails.visibility = View.GONE

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar_filldetails.visibility = View.GONE

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
        when (accountType) {
            "Savings Account" -> {
                account_type.setSelection(1)
            }
            "Current Account" -> {
                account_type.setSelection(2)
            }
            "Business Account" -> {
                account_type.setSelection(3)
            }
            "Cheque Account" -> {
                account_type.setSelection(4)
            }
            "Transmission Account" -> {
                account_type.setSelection(5)
            }
        }

    }

    override fun values(flag: Boolean, monthlyRevenue: String, position: Int) {
        values = true
        checkValue = monthlyRevenue

        for (i in 0 until data.size) {
            data.get(i).flag = i == position
        }

        Adapter.notifyDataSetChanged()
    }


    fun fillformServiceProviderAPI() {

        if (androidextention.isOnline(this)) {
            progressbar_filldetails.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<RetailerFillformResponse> =
                ApiCallBack<RetailerFillformResponse>(object :
                    ApiResponseListener<RetailerFillformResponse> {
                    override fun onApiSuccess(
                        response: RetailerFillformResponse,
                        apiName: String?
                    ) {
                        progressbar_filldetails.visibility = View.GONE
                        if (response.responseCode == 200) {

                            alertBox("Your Business details have been updated let's continue to next step.", "SignUp")



                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_filldetails.visibility = View.GONE

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
                        progressbar_filldetails.visibility = View.GONE


                    }

                }, "fillformAPI", mContext)


            var businessDetails = BusinessDetails()
            businessDetails.companyName = Companyname_ET.text.toString()
            businessDetails.businessRegNumber = businessRegistrationET.text.toString()
//            businessDetails.categoryId = ""
            businessDetails.websiteUrl = Website_ET.text.toString()
            businessDetails.socialMediaAccounts = Socialmedia_ET.text.toString()
            businessDetails.isVatRegistered = vatFlag
            businessDetails.vatNumber = Vat_ET.text.toString()
            businessDetails.monthlyRevenue = checkValue

            var businessBankingDetails = BusinessBankingDetails()
            businessBankingDetails.bankName = Bankname_ET.text.toString()
            businessBankingDetails.accountNumber = Accountnumber_ET.text.toString()
            businessBankingDetails.branchCode = Branchcode_ET.text.toString()
            businessBankingDetails.branchName = Branchname_ET.text.toString()
            businessBankingDetails.accountName = Accountname_ET.text.toString()
            businessBankingDetails.swiftCode = SWIFTcode_ET.text.toString()
            businessBankingDetails.accountType = requestAccountType

            var fillFormRequest = SP_FillFormRequest()
            fillFormRequest.flag = 1
            fillFormRequest.ownerFirstName = FirstName_ET.text.toString()
            fillFormRequest.ownerLastName = LastName_ET.text.toString()
            fillFormRequest.ownerEmail = Email_ET.text.toString()
            fillFormRequest.keepStock = keepStockValue
            fillFormRequest.businessDetails = businessDetails
            fillFormRequest.businessBankingDetails = businessBankingDetails


            try {
                serviceManager.sp_fillformapi(callBack, fillFormRequest)

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
        Companyname_ET.addTextChangedListener(textWatcher)
        businessRegistrationET.addTextChangedListener(textWatcher)
        Vat_ET.addTextChangedListener(textWatcher)
        Bankname_ET.addTextChangedListener(textWatcher)
        Branchname_ET.addTextChangedListener(textWatcher)
        Branchcode_ET.addTextChangedListener(textWatcher)
        SWIFTcode_ET.addTextChangedListener(textWatcher)
        SWIFTcode_ET.addTextChangedListener(textWatcher)
        Accountname_ET.addTextChangedListener(textWatcher)
        Accountnumber_ET.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (!s.toString().equals("")) {
                FormValidation.filldetails(
                    FirstName_ET,
                    first_nametv,
                    LastName_ET,
                    lastname_tv,
                    Email_ET,
                    emailaddress_tv,
                    Companyname_ET,
                    company_name_TV,
                    businessRegistrationET,
                    businessregistration_TV,
                    RB_yes,
                    RB_no,
                    Vat_ET,
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
                    accounttype_TV,
                    Accountname_ET,
                    account_name_TV,
                    Accountnumber_ET,
                    account_number_TV,
                    account_type_ll,
                    requestAccountType
                )
            }

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

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


    fun alertBox(message: String,from:String) {
        runOnUiThread{
            var alertDialog: AlertDialog? = null
            var builder = AlertDialog.Builder(this)
            builder.setIcon(R.mipmap.ic_launcher)
            builder.setTitle("eXobe")
            builder.setMessage(message)
            builder.setPositiveButton("ok") { dialogInterface, which ->
                val intent = Intent(this@FillDetailsServiceProviderActivity, SelectServiceRoleActivity::class.java)
                intent.putExtra("isFrom",from)
                startActivity(intent)

                alertDialog!!.dismiss()
                builder = AlertDialog.Builder(this, R.style.ProgressDialogStyle)

            }
            alertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

    }




}