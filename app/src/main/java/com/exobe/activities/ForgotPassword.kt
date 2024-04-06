package com.exobe.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.callisdairy.extension.setSafeOnClickListener
import com.example.validationpractice.utlis.FormValidation
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.androidextention
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.forgotpassword_response
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

class ForgotPassword : AppCompatActivity(), ApiResponseListener<forgotpassword_response> {

    lateinit var Forgotback: LinearLayout
    lateinit var ForgotSubmit: Button
    lateinit var EmailAddress_et: EditText
    lateinit var PhoneNumberTV: TextView
    lateinit var EmailAddress: LinearLayout
    lateinit var progressbarForgot_Password: ProgressBar
    lateinit var mContext: Context
    var forgotFlag = ""
    var userType=""
    val emailPattern =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    lateinit var email: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        mContext = this?.applicationContext!!
        Forgotback = findViewById(R.id.Forgotback)
        ForgotSubmit = findViewById(R.id.ForgotSubmit)
        EmailAddress_et = findViewById(R.id.EmailAddress_et)
        PhoneNumberTV = findViewById(R.id.PhoneNumberTV)
        EmailAddress = findViewById(R.id.EmailAddress)
        progressbarForgot_Password = findViewById(R.id.progressbarForgot_Password)
        GET_INTENT()
        initializedControl()


        if(forgotFlag.equals("Customer")){
            userType="CUSTOMER"
        }else if (forgotFlag.equals("Retailer")){
            userType="RETAILER"
        }else if (forgotFlag.equals("Service_Provider")){
            userType="SERVICE_PROVIDER"
        }


        Forgotback.setSafeOnClickListener {
            finish()
        }

        ForgotSubmit.setSafeOnClickListener {

            FormValidation.forgotPassword(EmailAddress_et,PhoneNumberTV,EmailAddress)
            if(EmailAddress_et.text.isNotEmpty() && EmailAddress_et.text.matches(Regex(FormValidation.emailPattern))){
                email = EmailAddress_et.text.toString()
                ForgotPasswordAPI(email)
            }

        }

    }
     fun initializedControl(){
         EmailAddress_et.addTextChangedListener(textWatcher)

     }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if(!s.toString().equals("")){
               FormValidation.forgotPassword(EmailAddress_et,PhoneNumberTV,EmailAddress)
            }

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {




        }
    }

    private fun GET_INTENT() {
        if (intent != null) {
            forgotFlag = intent?.getStringExtra("Forgot").toString()
        }

    }

    fun ForgotPasswordAPI(email: String) {

        if (androidextention.isOnline(this)) {
            progressbarForgot_Password.visibility= View.VISIBLE

            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<forgotpassword_response> =
                ApiCallBack<forgotpassword_response>(this, "ForgotPasswordAPI", mContext)

            var jsonObject = JsonObject()
            jsonObject.addProperty("email", email)
            jsonObject.addProperty("userType", userType)


            try {
                serviceManager.forgotpasswordApi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection." , this)

        }
    }

    override fun onApiSuccess(response: forgotpassword_response, apiName: String?) {
        progressbarForgot_Password.visibility= View.GONE
        if (response.responseCode == 200) {
            try {
                val intent = Intent(this, OtpVerification::class.java)
                intent.putExtra("email", email)
                intent.putExtra("flag", "ForgotPassword")
                intent.putExtra("flag1", forgotFlag)
                intent.putExtra("usertype", userType)

                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    override fun onApiErrorBody(response: String, apiName: String?) {
        progressbarForgot_Password.visibility= View.GONE
        val gson = GsonBuilder().create()
        var pojo = response_modal_class()

        try {
            pojo = gson.fromJson(response, pojo::class.java)
            androidextention.alertBox(pojo.responseMessage, this)

        } catch (e: Exception) {
            // handle failure at error parse
        }
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        progressbarForgot_Password.visibility= View.GONE
    }

}