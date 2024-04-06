package com.exobe.bottomSheet

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import com.callisdairy.extension.setSafeOnClickListener
import com.example.validationpractice.utlis.FormValidation
import com.exobe.activities.*
import com.exobe.activities.retailer.NewUploadDocumentActivity
import com.exobe.hostActivity.ServicesMainActivity
import com.exobe.activities.services.FillDetailsServiceProviderActivity
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.activities.customer.RegisterCustomerActivity
import com.exobe.activities.retailer.RegisterRetailerActivity
import com.exobe.utils.SavedPrefManager
import com.exobe.activities.services.CommonBusinessFormActivity
import com.exobe.activities.services.CommonForServicesActivity
import com.exobe.activities.services.RegistrationServiceProvider
import com.exobe.activities.services.SelectServiceRoleActivity
import com.exobe.activities.services.UploadDocumentForServiceActivity
import com.exobe.androidextention
import com.exobe.customClicks.UpdateIsLoginListener
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.LoginResponse
import com.exobe.entity.response.SocialSignInResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.hostActivity.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

class CustomerBottomSheet(var userRole: String, var mContext: Context, var updateIsLoginListener : UpdateIsLoginListener) : BottomSheetDialogFragment() {



    private lateinit var mGoogleSignInClient: GoogleSignInClient
    var email = ""
    lateinit var customer: LinearLayout
    lateinit var retailer: LinearLayout
    lateinit var phoneLinear: LinearLayout
    lateinit var passwordLL: LinearLayout
    lateinit var retailerLinear: LinearLayout
    lateinit var register: LinearLayout
    lateinit var retailerTV: TextView
    lateinit var forgotPassword: TextView
    lateinit var tv: TextView
    lateinit var tvPassword: TextView
    lateinit var emailEditText: EditText
    lateinit var retailerET: EditText
    lateinit var passwordEditText: EditText
    lateinit var logInClick: Button
    lateinit var crossEye: ImageView
    lateinit var cross: LinearLayout
    lateinit var bottomCheckBox: CheckBox
    lateinit var bottomSheetProgressBar: ProgressBar
    var logoutRL: RelativeLayout?= null
    var loginRL: RelativeLayout?= null
    var logInUserType = ""

    lateinit var googleIcon: ImageView


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.peekHeight = ViewGroup.LayoutParams.MATCH_PARENT
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.login_bottom_sheet, null)

        var passwordNotVisible = 0

            logInUserType = when (userRole) {
                "Customer" -> { "CUSTOMER" }

                "Retailer" -> { "RETAILER" }

                "Service_Provider" -> { "SERVICE_PROVIDER" }

                "Pickup Driver" -> { "PICKUP_PARTNER" }

                "Field Entity" -> { "FIELD_ENTITY" }

                "Delivery Driver" -> { "DELIVERY_PARTNER" }

                else -> { ""  }
            }


        bottomSheetProgressBar = view.findViewById(R.id.bottomsheet_progress_bar)
        customer = view.findViewById(R.id.Customer_login)
        retailer = view.findViewById(R.id.Retailer_login)
        register = view.findViewById(R.id.Register)
        forgotPassword = view.findViewById(R.id.ForgotPassword)
        passwordEditText = view.findViewById(R.id.PasswordEditText)
        logInClick = view.findViewById(R.id.LogINClick)
        crossEye = view.findViewById(R.id.cross_eye_bottomsheet)
        cross = view.findViewById(R.id.cross)
        retailerET = view.findViewById(R.id.RetailerET)
        retailerTV = view.findViewById(R.id.Retailer_TV)
        bottomCheckBox = view.findViewById(R.id.bottom_check_box)
        logoutRL = activity?.findViewById(R.id.Logout_RelativeLayout)
        loginRL = activity?.findViewById(R.id.Login_RelativeLayout)
        googleIcon = view.findViewById(R.id.googleIcon)

//        CustomerIDs

        phoneLinear = view.findViewById(R.id.PhoneLinear)
        passwordLL = view.findViewById(R.id.PaswordLL)
        emailEditText = view.findViewById(R.id.PhoneEditText)
        tv = view.findViewById(R.id.TV)
        tvPassword = view.findViewById(R.id.TVPassword)
        retailerLinear = view.findViewById(R.id.RetalierLinear)
        initializedControl()
        cross.setSafeOnClickListener {
            SavedPrefManager.savePreferenceBoolean(
                requireContext(),
                SavedPrefManager.FORGOT_PASSWORD,
                false
            )
            dismiss()
        }


        crossEye.setSafeOnClickListener {
            when (passwordNotVisible) {
                0 -> {
                    passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    crossEye.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.eye))
                    passwordEditText.setSelection(passwordEditText.length())
                    passwordNotVisible = 1

                }
                1 -> {
                    passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
                    crossEye.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_eye_slash_solid))
                    passwordEditText.setSelection(passwordEditText.length())
                    passwordNotVisible = 0
                }
                else -> {
                    passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    crossEye.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.eye))
                    passwordEditText.setSelection(passwordEditText.length())
                    passwordNotVisible = 1
                }
            }


        }


        when (userRole) {
            "Customer" -> {
                customer.visibility = View.VISIBLE
                retailer.visibility = View.GONE

                //   getting and Saving Email password and Api Call for login for user Customer

                val rememberEmail = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.CUSTOMER_GMAIL_ADDRESS)
                val rememberPassword = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.CUSTOMER_PASSWORD)

                if (!rememberEmail.equals("") && !rememberPassword.equals("")) {
                    bottomCheckBox.isChecked = true
                    emailEditText.text = Editable.Factory.getInstance().newEditable(rememberEmail)
                    passwordEditText.text = Editable.Factory.getInstance().newEditable(rememberPassword)

                } else {
                    bottomCheckBox.isChecked = false
                    emailEditText.text = Editable.Factory.getInstance().newEditable("")
                    passwordEditText.text = Editable.Factory.getInstance().newEditable("")
                }

                logInClick.setSafeOnClickListener {
                    FormValidation.CustomerLogin(emailEditText, tv, passwordEditText, tvPassword, phoneLinear, passwordLL)


                    if (tvPassword.text.equals("") && tv.text.equals("")) {

                        val email = emailEditText.text.toString()
                        val password = passwordEditText.text.toString()
                        if (bottomCheckBox.isChecked) {
                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.CUSTOMER_GMAIL_ADDRESS, email)
                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.CUSTOMER_PASSWORD, password)
                        } else {
                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.CUSTOMER_GMAIL_ADDRESS, "")
                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.CUSTOMER_PASSWORD, "")
                        }
                        loginCustomerAPI(email, password)
                    }
                }

            }
            "Retailer" -> {
                customer.visibility = View.GONE
                retailer.visibility = View.VISIBLE


    //             getting and Saving Email password and Api Call for login for user Retailer

                val rememberEmail = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.RETAILER_GMAIL_ADDRESS)
                val rememberPassword = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.RETAILER_PASSWORD)


                if (!rememberEmail.equals("") && !rememberPassword.equals("")) {
                    bottomCheckBox.isChecked = true
                    retailerET.text = Editable.Factory.getInstance().newEditable(rememberEmail)
                    passwordEditText.text = Editable.Factory.getInstance().newEditable(rememberPassword)

                } else {
                    bottomCheckBox.isChecked = false
                    retailerET.text = Editable.Factory.getInstance().newEditable("")
                    passwordEditText.text = Editable.Factory.getInstance().newEditable("")
                }

                logInClick.setSafeOnClickListener {
                    FormValidation.RetailerLogin(retailerET, retailerTV, passwordEditText, tvPassword, retailerLinear, passwordLL)
                    if (tvPassword.text.equals("") && retailerTV.text.equals("")) {
                        val email = retailerET.text.toString()
                        val password = passwordEditText.text.toString()
                        if (bottomCheckBox.isChecked) {
                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.RETAILER_GMAIL_ADDRESS, email)
                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.RETAILER_PASSWORD, password)
                        } else {
                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.RETAILER_GMAIL_ADDRESS, "")
                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.RETAILER_PASSWORD, "")
                        }
                        loginCustomerAPI(email, password)


                    }
                }

            }

            "Service_Provider" -> {
                customer.visibility = View.VISIBLE
                retailer.visibility = View.GONE

    //             getting and Saving Email password and Api Call for login for user Service Provider


                val rememberEmail = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.SERVICE_GMAIL_ADDRESS)
                val rememberPassword = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.SERVICE_PASSWORD)


                if (!rememberEmail.equals("") && !rememberPassword.equals("")) {
                    bottomCheckBox.isChecked = true
                    emailEditText.text = Editable.Factory.getInstance().newEditable(rememberEmail)
                    passwordEditText.text = Editable.Factory.getInstance().newEditable(rememberPassword)

                } else {
                    bottomCheckBox.isChecked = false
                    emailEditText.text = Editable.Factory.getInstance().newEditable("")
                    passwordEditText.text = Editable.Factory.getInstance().newEditable("")
                }


                logInClick.setSafeOnClickListener {
                    FormValidation.CustomerLogin(emailEditText, tv, passwordEditText, tvPassword, phoneLinear, passwordLL)
                    if (tvPassword.text.equals("") && tv.text.equals("")) {
                        SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.isLogin, "true")
                        val emailService = emailEditText.text.toString()
                        val passwordService = passwordEditText.text.toString()

                        if (bottomCheckBox.isChecked) {
                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.SERVICE_GMAIL_ADDRESS, emailService)
                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.SERVICE_PASSWORD, passwordService)
                        } else {
                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.SERVICE_GMAIL_ADDRESS, "")
                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.SERVICE_PASSWORD, "")
                        }
                        loginCustomerAPI(emailService, passwordService)
                    }
                }
            }

            "Pickup Driver" -> {
                customer.visibility = View.VISIBLE
                retailer.visibility = View.GONE


                val rememberEmail = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.PP_EMAil)
                val rememberPassword = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.PP_PASSWORD)


                if (!rememberEmail.equals("") && !rememberPassword.equals("")) {
                    bottomCheckBox.isChecked = true
                    emailEditText.text = Editable.Factory.getInstance().newEditable(rememberEmail)
                    passwordEditText.text = Editable.Factory.getInstance().newEditable(rememberPassword)

                } else {
                    bottomCheckBox.isChecked = false
                    emailEditText.text = Editable.Factory.getInstance().newEditable("")
                    passwordEditText.text = Editable.Factory.getInstance().newEditable("")
                }



                logInClick.setSafeOnClickListener {
                    FormValidation.CustomerLogin(emailEditText, tv, passwordEditText, tvPassword, phoneLinear, passwordLL)
                    if (tvPassword.text.equals("") && tv.text.equals("")) {
                        val emailService = emailEditText.text.toString()
                        val passwordService = passwordEditText.text.toString()

                        if (bottomCheckBox.isChecked) {
                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.PP_EMAil, emailService)
                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.PP_PASSWORD, passwordService)
                        }
                        loginCustomerAPI(emailService, passwordService)
                    }
                }

            }

            "Field Entity" -> {
                customer.visibility = View.VISIBLE
                retailer.visibility = View.GONE


                val rememberEmail = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.FE_EMAil)
                val rememberPassword = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.FE_PASSWORD)


                if (!rememberEmail.equals("") && !rememberPassword.equals("")) {
                    bottomCheckBox.isChecked = true
                    emailEditText.text = Editable.Factory.getInstance().newEditable(rememberEmail)
                    passwordEditText.text = Editable.Factory.getInstance().newEditable(rememberPassword)

                } else {
                    bottomCheckBox.isChecked = false
                    emailEditText.text = Editable.Factory.getInstance().newEditable("")
                    passwordEditText.text = Editable.Factory.getInstance().newEditable("")
                }



                logInClick.setSafeOnClickListener {
                    FormValidation.CustomerLogin(emailEditText, tv, passwordEditText, tvPassword, phoneLinear, passwordLL)
                    if (tvPassword.text.equals("") && tv.text.equals("")) {
                        val emailService = emailEditText.text.toString()
                        val passwordService = passwordEditText.text.toString()

                        if (bottomCheckBox.isChecked) {
                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.FE_EMAil, emailService)
                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.FE_PASSWORD, passwordService)
                        }
                        loginCustomerAPI(emailService, passwordService)
                    }
                }



        }

            "Delivery Driver" -> {

                customer.visibility = View.VISIBLE
                retailer.visibility = View.GONE


                val rememberEmail = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.DP_EMAil)
                val rememberPassword = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.DP_PASSWORD)


                if (!rememberEmail.equals("") && !rememberPassword.equals("")) {
                    bottomCheckBox.isChecked = true
                    emailEditText.text = Editable.Factory.getInstance().newEditable(rememberEmail)
                    passwordEditText.text = Editable.Factory.getInstance().newEditable(rememberPassword)

                } else {
                    bottomCheckBox.isChecked = false
                    emailEditText.text = Editable.Factory.getInstance().newEditable("")
                    passwordEditText.text = Editable.Factory.getInstance().newEditable("")
                }



                logInClick.setSafeOnClickListener {
                    FormValidation.CustomerLogin(emailEditText, tv, passwordEditText, tvPassword, phoneLinear, passwordLL)
                    if (tvPassword.text.equals("") && tv.text.equals("")) {
                        val emailService = emailEditText.text.toString()
                        val passwordService = passwordEditText.text.toString()

                        if (bottomCheckBox.isChecked) {
                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.DP_EMAil, emailService)
                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.DP_PASSWORD, passwordService)
                        }
                        loginCustomerAPI(emailService, passwordService)
                    }
                }
            }




        }


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("127786420955-p1mb1atbqhq9b2hikvlbid70elgsdt52.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        googleIcon.setSafeOnClickListener {
                signIn()
        }











        register.setSafeOnClickListener {
            when (userRole) {
                "Customer" -> {
                    val intent  = Intent(mContext, RegisterCustomerActivity::class.java)
                    intent.putExtra("isSocial",false)
                    intent.putExtra("googleFirstName","")
                    intent.putExtra("googleLastName","")
                    intent.putExtra("googleEmail","")
                    intent.putExtra("googleProfilePicURL","")
                    startActivity(intent)
                }
                "Retailer" -> {
                    val intent  = Intent(mContext, RegisterRetailerActivity::class.java)
                    intent.putExtra("isSocial",false)
                    intent.putExtra("googleFirstName","")
                    intent.putExtra("googleLastName","")
                    intent.putExtra("googleEmail","")
                    intent.putExtra("googleProfilePicURL","")
                    startActivity(intent)
                }
                "Service_Provider" -> {
                    val intent  = Intent(mContext, RegistrationServiceProvider::class.java)
                    intent.putExtra("userRole", "Service Provider")
                    startActivity(intent)
                }

                "Pickup Driver" -> {
                    val intent  = Intent(mContext, RegistrationServiceProvider::class.java)
                    intent.putExtra("userRole", "Pickup Driver")
                    startActivity(intent)
                }

                "Field Entity" -> {
                    val intent  = Intent(mContext, RegistrationServiceProvider::class.java)
                    intent.putExtra("userRole", "Field Entity")
                    startActivity(intent)
                }

                "Delivery Driver" -> {
                    val intent  = Intent(mContext, RegistrationServiceProvider::class.java)
                    intent.putExtra("userRole", "Delivery Driver")
                    startActivity(intent)
                }


            }
        }

        forgotPassword.setSafeOnClickListener {
            when (userRole) {
                "Customer" -> {
                    val intent = Intent(mContext, ForgotPassword::class.java)
                    intent.putExtra("Forgot", "Customer")
                    startActivity(intent)
                }
                "Retailer" -> {
                    val intent = Intent(mContext, ForgotPassword::class.java)
                    intent.putExtra("Forgot", "Retailer")
                    startActivity(intent)

                }
                "Service_Provider" -> {
                    val intent = Intent(mContext, ForgotPassword::class.java)
                    intent.putExtra("Forgot", "Service_Provider")
                    startActivity(intent)
                }

                "Pickup Driver" -> {}

                "Field Entity" -> {}

                "Delivery Driver" -> {}

            }
        }

        return view
    }


    private fun loginCustomerAPI(email: String, password: String) {

        if (androidextention.isOnline(requireContext())) {
            bottomSheetProgressBar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<LoginResponse> =
                ApiCallBack(object : ApiResponseListener<LoginResponse> {
                    override fun onApiSuccess(response: LoginResponse, apiName: String?) {
                        bottomSheetProgressBar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {

                                when(response.result?.userType) {

                                    "CUSTOMER" -> {
                                        if (response.result.otpVerification == true) {


                                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.SERVICE_ID_Address, response.result.primaryAddressId)

                                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.TOKEN, response.result.token)
                                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.CAMPAIGN_PREFERENCE, response.result.campainPrefrences)
                                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.CUSTOMER_USER_ID, response.result._id)
                                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.USER_TYPE, response.result.userType)
                                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "true")
                                            logoutRL?.visibility = View.VISIBLE
                                            loginRL?.visibility = View.GONE
                                            updateIsLoginListener.isLoginListener()

                                        } else {
                                            val intent = Intent(requireContext(), OtpVerification::class.java)
                                            intent.putExtra("email", email)
                                            intent.putExtra("flag1", "customer_login")
                                            intent.putExtra("usertype", "CUSTOMER")
                                            requireContext().startActivity(intent)

                                        }
                                        dismiss()
                                    }

                                    "RETAILER" ->  {
                                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.SERVICE_ID_Address, response.result.primaryAddressId)
                                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.CAMPAIGN_PREFERENCE, response.result.campainPrefrences)
                                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.USER_ID, response.result._id)
                                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.RETAILER_FORM_FLAG, response.result.flag.toString())
                                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.USER_REQUEST_STATUS, response.result.userRequestStatus)
                                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.USER_TYPE, response.result.userType)
                                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.TOKEN, response.result.token)

                                        if (response.result.flag == 0) {
                                            dismiss()
                                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")

                                            val intent = Intent(requireContext(), fillDetails_retailer::class.java)
                                            intent.putExtra("loginflag", "loginflag")
                                            startActivity(intent)

                                        } else if (response.result.flag == 1) {
                                            dismiss()
                                            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")

                                            val intent = Intent(requireContext(), NewUploadDocumentActivity::class.java)
                                            intent.putExtra("flag", "retailer")
                                            startActivity(intent)
                                        } else if (response.result.flag == 2) {
                                            if (response.result.userRequestStatus.equals("APPROVED")) {
                                                dismiss()
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "true")
                                                updateIsLoginListener.isLoginListener()
                                                val intent = Intent(requireContext(), MainActivity::class.java)
                                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                intent.putExtra("flag", "retailer")
                                                startActivity(intent)
                                                (context as Activity).finishAffinity()
                                            } else if (response.result.userRequestStatus.equals("PENDING")) {
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")
                                                androidextention.alertBox("Thanks For Connecting With Us. We will verify your account and will send your approval soon.", requireContext())
                                            } else if (response.result.userRequestStatus.equals("REJECTED")) {
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")
                                                dismiss()

                                                SavedPrefManager.saveStringPreferences(
                                                    requireContext(),
                                                    SavedPrefManager.isLogin,
                                                    "false"
                                                )

//                                                androidextention.alertBox(response.result.rejectReason.toString(), requireContext())

                                                val intent = Intent(
                                                    requireContext(),
                                                    NewUploadDocumentActivity::class.java
                                                )
                                                intent.putExtra("flag", "retailer")
                                                intent.putExtra("rejectReason", response.result.rejectReason.toString())
                                                startActivity(intent)
                                            }

                                        }

                                    }
                                    "SERVICE_PROVIDER" ->  {


                                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.USER_ID, response.result._id)
                                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.SERVICE_PROVIDER_FORM_FLAG, response.result.flag.toString())
                                        SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.USER_TYPE, response.result.userType)
                                        SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.ID, response.result._id)
                                        SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.TOKEN, response.result.token)



                                        when (response.result.flag) {
                                            0 -> {
                                                dismiss()
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")

                                                val intent = Intent(requireContext(), FillDetailsServiceProviderActivity::class.java)
                                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                intent.putExtra("flag", "Service_Provider")
                                                intent.putExtra("loginflag", "loginflag")
                                                startActivity(intent)
                                            }
                                            1 -> {
                                                dismiss()
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")

                                                val intent = Intent(requireContext(), SelectServiceRoleActivity::class.java)
                                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                startActivity(intent)
                                            }
                                            2 -> {
                                                dismiss()
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")
                                                val intent = Intent(requireContext(), UploadDocumentForServiceActivity::class.java)
                                                intent.putExtra("standard", response.result.isStandardService)
                                                intent.putExtra("transportation", response.result.isTransportationService)
                                                intent.putExtra("fulfillment", response.result.isFullfillmentService)
                                                startActivity(intent)
                                            }
                                            3 -> {
                                                when(response.result.userRequestStatus!!.uppercase()){
                                                    "APPROVED" -> {
                                                        dismiss()
                                                        SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.isLogin, "true")
                                                        SavedPrefManager.savePreferenceBoolean(requireContext(), SavedPrefManager.isStandardService, response.result.isStandardService)
                                                        SavedPrefManager.savePreferenceBoolean(requireContext(), SavedPrefManager.isTransportationService, response.result.isTransportationService)
                                                        SavedPrefManager.savePreferenceBoolean(requireContext(), SavedPrefManager.isFulfillmentService, response.result.isFullfillmentService)


                                                        updateIsLoginListener.isLoginListener()
                                                        val intent = Intent(requireContext(), ServicesMainActivity::class.java)
                                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                        startActivity(intent)
                                                        (context as Activity).finishAffinity()
                                                    }
                                                    "PENDING" -> {
                                                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")
                                                        androidextention.alertBox("Thanks For Connecting With Us. We will verify your account and will send your approval soon.", requireContext())
                                                    }
                                                    "REJECTED" -> {
                                                        dismiss()
                                                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")
                                                        val intent = Intent(requireContext(), UploadDocumentForServiceActivity::class.java)
                                                        startActivity(intent)
                                                    }
                                                }

                                            }
                                        }

                                    }

                                    "PICKUP_PARTNER" , "FIELD_ENTITY", "DELIVERY_PARTNER" -> {

                                        SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.USER_TYPE, response.result.userType)
                                        SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.USER_ID, response.result._id)
                                        SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.TOKEN, response.result.token)

                                        when(response.result.userType) {
                                            "PICKUP_PARTNER" -> {
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.PD_PROVIDER_FORM_FLAG, response.result.flag.toString())
                                            }
                                            "FIELD_ENTITY" -> {
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.FE_PROVIDER_FORM_FLAG, response.result.flag.toString())
                                            }
                                            "DELIVERY_PARTNER" -> {
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.DD_PROVIDER_FORM_FLAG, response.result.flag.toString())

                                            }
                                        }




                                        when (response.result.flag) {
                                            0 -> {
                                                dismiss()

                                                val intent = Intent(requireContext(), CommonBusinessFormActivity::class.java)
                                                startActivity(intent)
                                            }

                                            1 -> {
                                                dismiss()
                                                val intent = Intent(requireContext(), UploadDocumentForServiceActivity::class.java)
                                                startActivity(intent)
                                            }

                                            2 -> {

                                                when(response.result.userRequestStatus!!.uppercase()) {
                                                    "APPROVED" -> {
                                                        dismiss()
                                                        SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.isLogin, "true")
                                                        updateIsLoginListener.isLoginListener()
                                                        val intent = Intent(requireContext(), CommonForServicesActivity::class.java)
                                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                        startActivity(intent)
                                                        (context as Activity).finishAffinity()
                                                    }

                                                    "PENDING" -> {
                                                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")
                                                        androidextention.alertBox("Thanks For Connecting With Us. We will verify your account and will send your approval soon.", requireContext())
                                                    }

                                                    "REJECTED" -> {
                                                        dismiss()
                                                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")
                                                        val intent = Intent(requireContext(), UploadDocumentForServiceActivity::class.java)
                                                        startActivity(intent)
                                                    }



                                                }





                                            }


                                        }
                                    }


                                }



                            } catch (e: Exception) {
                                e.printStackTrace()
                            }


                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        bottomSheetProgressBar.visibility = View.GONE
                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBoxCommon(pojo.responseMessage, requireContext())

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        bottomSheetProgressBar.visibility = View.GONE
                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")
                    }

                }, "LoginCustomer", requireContext())

            val jsonObject = JsonObject()
            jsonObject.addProperty("emailNumberId", email)
            jsonObject.addProperty("password", password)
            jsonObject.addProperty("userType", logInUserType)
            jsonObject.addProperty("deviceType", "android")
            jsonObject.addProperty(
                "deviceToken",
                SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.KEY_DEVICE_TOKEN)
                    .toString()
            )


            try {
                serviceManager.LoginApi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }
    }

    fun initializedControl() {

        emailEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)
        retailerET.addTextChangedListener(textWatcherRetailer)
        passwordEditText.addTextChangedListener(textWatcherRetailer)


    }


    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (!s.toString().equals("")) {
                FormValidation.CustomerLogin(
                    emailEditText,
                    tv, passwordEditText,
                    tvPassword, phoneLinear, passwordLL
                )
            }

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }
    private val textWatcherRetailer = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s.toString() != "") {
                FormValidation.RetailerLogin(
                    retailerET,
                    retailerTV,
                    passwordEditText,
                    tvPassword,
                    retailerLinear, passwordLL
                )
            }

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }


//    Google signup

    private fun signIn() {


        signOutGoogleAccount()
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOutGoogleAccount() {
        val googleSignInClient = GoogleSignIn.getClient(requireContext(), GoogleSignInOptions.DEFAULT_SIGN_IN)
        googleSignInClient.signOut()
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // User signed out successfully
                } else {
                    // Handle sign-out failure
                    Log.e("Google Sign Out Error", task.exception.toString())
                }
            }
    }
    companion object {
        const val RC_SIGN_IN = 9001
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            val googleId = account?.id ?: ""
            val googleFirstName = account?.givenName ?: ""
            val googleLastName = account?.familyName ?: ""
            val googleEmail = account?.email ?: ""
            val googleProfilePicURL = account?.photoUrl.toString()
            val googleIdToken = account?.idToken ?: ""
            socialSignInApi(googleFirstName = googleFirstName, googleLastName = googleLastName, googleEmail = googleEmail, googleProfilePicURL = googleProfilePicURL, googleIdToken = googleIdToken, googleId = googleId)

        } catch (e: ApiException) {

            Log.e(
                "failed code=", e.toString()
            )
        }
    }


    private fun socialSignInApi(
        googleFirstName: String,
        googleLastName: String,
        googleEmail: String,
        googleProfilePicURL: String,
        googleIdToken: String,
        googleId: String
    ) {

        if (androidextention.isOnline(requireContext())) {
            bottomSheetProgressBar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<SocialSignInResponse> =
                ApiCallBack(object : ApiResponseListener<SocialSignInResponse> {
                    override fun onApiSuccess(response: SocialSignInResponse, apiName: String?) {
                        bottomSheetProgressBar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {

                                response.result.apply {
                                    if (isAlreadyUser){
                                        when(userType) {

                                            "CUSTOMER" -> {
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.SERVICE_ID_Address, primaryAddressId)
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.CAMPAIGN_PREFERENCE, response.result.campainPrefrences)
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.TOKEN, token)
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.CUSTOMER_USER_ID, _id)
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.USER_TYPE, userType)
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "true")
                                                logoutRL?.visibility = View.VISIBLE
                                                loginRL?.visibility = View.GONE
                                                updateIsLoginListener.isLoginListener()
                                                dismiss()
                                            }

                                            "RETAILER" ->  {
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.SERVICE_ID_Address, primaryAddressId)
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.CAMPAIGN_PREFERENCE, response.result.campainPrefrences)

                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.USER_ID, _id)
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.RETAILER_FORM_FLAG, flag.toString())
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.USER_REQUEST_STATUS, userRequestStatus)
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.USER_TYPE, userType)
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.TOKEN, token)

                                                when (flag) {
                                                    0 -> {
                                                        dismiss()
                                                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")

                                                        val intent = Intent(requireContext(), fillDetails_retailer::class.java)
                                                        intent.putExtra("loginflag", "loginflag")
                                                        startActivity(intent)

                                                    }
                                                    1 -> {
                                                        dismiss()
                                                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")

                                                        val intent = Intent(requireContext(), NewUploadDocumentActivity::class.java)
                                                        intent.putExtra("flag", "retailer")
                                                        startActivity(intent)
                                                    }
                                                    2 -> {
                                                        when (userRequestStatus.uppercase()) {
                                                            "APPROVED" -> {
                                                                dismiss()
                                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "true")
                                                                updateIsLoginListener.isLoginListener()
                                                                val intent = Intent(requireContext(), MainActivity::class.java)
                                                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                                intent.putExtra("flag", "retailer")
                                                                startActivity(intent)
                                                                (context as Activity).finishAffinity()
                                                            }

                                                            "PENDING" -> {
                                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")
                                                                androidextention.alertBox("Thanks For Connecting With Us. We will verify your account and will send your approval soon.", requireContext())
                                                            }

                                                            "REJECTED" -> {
                                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")
                                                                dismiss()

                                                                SavedPrefManager.saveStringPreferences(
                                                                    requireContext(),
                                                                    SavedPrefManager.isLogin,
                                                                    "false"
                                                                )

                                                                androidextention.alertBox(rejectReason, requireContext())

                                                                val intent = Intent(requireContext(), NewUploadDocumentActivity::class.java)
                                                                intent.putExtra("flag", "retailer")
                                                                intent.putExtra("rejectReason", rejectReason.toString())
                                                                startActivity(intent)
                                                            }
                                                        }

                                                    }
                                                }

                                            }


                                            "SERVICE_PROVIDER" ->  {


                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.USER_ID, _id)
                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.SERVICE_PROVIDER_FORM_FLAG, flag.toString())

                                                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.USER_TYPE, userType)
                                                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.ID, response.result._id)
                                                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.TOKEN, response.result.token)



                                                when (response.result.flag) {
                                                    0 -> {
                                                        dismiss()
                                                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")

                                                        val intent = Intent(requireContext(), FillDetailsServiceProviderActivity::class.java)
                                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                        intent.putExtra("flag", "Service_Provider")
                                                        intent.putExtra("loginflag", "loginflag")
                                                        startActivity(intent)
                                                    }
                                                    1 -> {
                                                        dismiss()
                                                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")

                                                        val intent = Intent(requireContext(), SelectServiceRoleActivity::class.java)
                                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                        startActivity(intent)
                                                    }
                                                    2 -> {
                                                        dismiss()
                                                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")
                                                        val intent = Intent(requireContext(), UploadDocumentForServiceActivity::class.java)
                                                        intent.putExtra("standard", response.result.isStandardService)
                                                        intent.putExtra("transportation", response.result.isTransportationService)
                                                        intent.putExtra("fulfillment", response.result.isFullfillmentService)
                                                        startActivity(intent)
                                                    }
                                                    3 -> {
                                                        when(response.result.userRequestStatus!!.uppercase()){
                                                            "APPROVED" -> {
                                                                dismiss()
                                                                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.isLogin, "true")
                                                                SavedPrefManager.savePreferenceBoolean(requireContext(), SavedPrefManager.isStandardService, response.result.isStandardService)
                                                                SavedPrefManager.savePreferenceBoolean(requireContext(), SavedPrefManager.isTransportationService, response.result.isTransportationService)
                                                                SavedPrefManager.savePreferenceBoolean(requireContext(), SavedPrefManager.isFulfillmentService, response.result.isFullfillmentService)


                                                                updateIsLoginListener.isLoginListener()
                                                                val intent = Intent(requireContext(), ServicesMainActivity::class.java)
                                                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                                startActivity(intent)
                                                                (context as Activity).finishAffinity()
                                                            }
                                                            "PENDING" -> {
                                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")
                                                                androidextention.alertBox("Thanks For Connecting With Us. We will verify your account and will send your approval soon.", requireContext())
                                                            }
                                                            "REJECTED" -> {
                                                                dismiss()
                                                                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")
                                                                val intent = Intent(requireContext(), UploadDocumentForServiceActivity::class.java)
                                                                startActivity(intent)
                                                            }
                                                        }

                                                    }
                                                }

                                            }



                                        }
                                    }else{
                                        when (userRole) {

                                            "Customer" -> {
                                                val intent  = Intent(mContext, RegisterCustomerActivity::class.java)
                                                intent.putExtra("isSocial",true)
                                                intent.putExtra("googleFirstName",googleFirstName)
                                                intent.putExtra("googleLastName",googleLastName)
                                                intent.putExtra("googleEmail",googleEmail)
                                                intent.putExtra("googleProfilePicURL",googleProfilePicURL)
                                                startActivity(intent)
                                                dismiss()
                                            }
                                    "Retailer" -> {
                                        val intent  = Intent(mContext, RegisterRetailerActivity::class.java)
                                        intent.putExtra("isSocial",true)
                                        intent.putExtra("googleFirstName",googleFirstName)
                                        intent.putExtra("googleLastName",googleLastName)
                                        intent.putExtra("googleEmail",googleEmail)
                                        intent.putExtra("googleProfilePicURL",googleProfilePicURL)
                                        startActivity(intent)
                                        dismiss()
                                    }


                                    "Service_Provider" -> {
                                        val intent  = Intent(mContext, RegistrationServiceProvider::class.java)
                                        intent.putExtra("isSocial",true)
                                        intent.putExtra("googleFirstName",googleFirstName)
                                        intent.putExtra("googleLastName",googleLastName)
                                        intent.putExtra("googleEmail",googleEmail)
                                        intent.putExtra("googleProfilePicURL",googleProfilePicURL)
                                        startActivity(intent)
                                        dismiss()
                                    }

                                        }
                                    }





                                }





                            } catch (e: Exception) {
                                e.printStackTrace()
                            }


                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        bottomSheetProgressBar.visibility = View.GONE
                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBoxCommon(pojo.responseMessage, requireContext())
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        bottomSheetProgressBar.visibility = View.GONE
                        SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")
                    }

                }, "socialSignInApi", requireContext())


            try {
                serviceManager.socialSignInApi(callBack,
                    userType = logInUserType,
                    email = googleEmail,
                    profilePic = googleProfilePicURL,
                    firstName = googleFirstName,
                    lastName = googleLastName,
                    deviceType = "Android",
                    deviceToken = SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.KEY_DEVICE_TOKEN).toString(),
                    googleId = googleId
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }
    }


}





