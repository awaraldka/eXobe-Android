package com.exobe.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.*
import com.callisdairy.extension.setSafeOnClickListener
import com.example.validationpractice.utlis.FormValidation
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.resetpassword_response
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

class ResetPassword : AppCompatActivity(), ApiResponseListener<resetpassword_response> {
    lateinit var PasswordET: EditText
    lateinit var ConfirmPasswordET: EditText
    lateinit var PasswordTV: TextView
    lateinit var ConfirmPasswordTv: TextView
    lateinit var cross_eye: ImageView
    lateinit var cross_eye2: ImageView
    lateinit var PasswordLL: LinearLayout
    lateinit var ConfirmPasswordLL: LinearLayout
    lateinit var ResetSubmit: Button
    private var passwordNotVisible = 0
    lateinit var backReset: ImageView
    lateinit var resetpassword_progress_bar: ProgressBar
    lateinit var mContext: Context
    var email = ""
    var password = ""
    var flag = ""
    var getUserType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        mContext = this?.applicationContext!!
        window.attributes.windowAnimations = R.style.Fade
        resetpassword_progress_bar = findViewById(R.id.resetpassword_progress_bar)
        backReset = findViewById(R.id.backReset)
        PasswordET = findViewById(R.id.PasswordET)
        ConfirmPasswordLL = findViewById(R.id.ConfirmPasswordLL)
        ConfirmPasswordET = findViewById(R.id.ConfirmPasswordET)
        PasswordTV = findViewById(R.id.PasswordTV)
        ConfirmPasswordTv = findViewById(R.id.ConfirmPasswordTv)
        cross_eye = findViewById(R.id.cross_eye)
        cross_eye2 = findViewById(R.id.cross_eye2)
        PasswordLL = findViewById(R.id.PasswordLL)
        ResetSubmit = findViewById(R.id.ResetSubmit)
        initializedControl()
        GET_INTENT()

        backReset.setSafeOnClickListener {
            finish()
        }

        ResetSubmit.setSafeOnClickListener {
            if (FormValidation.ResetPasswordValidations(
                    PasswordET,
                    PasswordTV,
                    ConfirmPasswordET,
                    ConfirmPasswordTv,
                    PasswordLL,
                    ConfirmPasswordLL
                )
            ) {
                password = PasswordET.text.toString()
                resetPasswordApi(email, password)
            }
        }

        passwordShow()

    }

    fun GET_INTENT() {
        if (intent != null) {
            if (intent?.getStringExtra("email") != null) {
                email = intent?.getStringExtra("email")!!
            }
            if (intent?.getStringExtra("UserType") != null) {
                getUserType = intent?.getStringExtra("UserType")!!
            }

        }
    }


    //     ShowPassword
    private fun passwordShow() {
        cross_eye.setSafeOnClickListener {
            if (passwordNotVisible == 0) {
                PasswordET.transformationMethod = HideReturnsTransformationMethod.getInstance()
                cross_eye.setImageDrawable(resources.getDrawable(R.drawable.eye))
                PasswordET.setSelection(PasswordET.length())
                passwordNotVisible = 1


            } else if (passwordNotVisible == 1) {
                PasswordET.transformationMethod = PasswordTransformationMethod.getInstance()
                cross_eye.setImageDrawable(resources.getDrawable(R.drawable.ic_eye_slash_solid))
                PasswordET.setSelection(PasswordET.length())
                passwordNotVisible = 0
            } else {
                PasswordET.transformationMethod = HideReturnsTransformationMethod.getInstance()
                cross_eye.setImageDrawable(resources.getDrawable(R.drawable.eye))
                PasswordET.setSelection(PasswordET.length())
                passwordNotVisible = 1
            }
        }
        cross_eye2.setSafeOnClickListener {
            if (passwordNotVisible == 0) {
                ConfirmPasswordET.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                cross_eye2.setImageDrawable(resources.getDrawable(R.drawable.eye))
                ConfirmPasswordET.setSelection(ConfirmPasswordET.length())
                passwordNotVisible = 1


            } else if (passwordNotVisible == 1) {
                ConfirmPasswordET.transformationMethod = PasswordTransformationMethod.getInstance()
                cross_eye2.setImageDrawable(resources.getDrawable(R.drawable.ic_eye_slash_solid))
                ConfirmPasswordET.setSelection(ConfirmPasswordET.length())
                passwordNotVisible = 0
            } else {
                ConfirmPasswordET.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                cross_eye2.setImageDrawable(resources.getDrawable(R.drawable.eye))
                ConfirmPasswordET.setSelection(ConfirmPasswordET.length())
                passwordNotVisible = 1
            }
        }
    }

    fun resetPasswordApi(email: String, password: String) {

        if (androidextention.isOnline(this)) {
            resetpassword_progress_bar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<resetpassword_response> =
                ApiCallBack<resetpassword_response>(this, "resetPasswordApi", mContext)



            try {

                var jsonObject = JsonObject()
                jsonObject.addProperty("email", email)
                jsonObject.addProperty("newPassword", password)
                jsonObject.addProperty("userType", getUserType)


                serviceManager.ResetPasswordApi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection.", this)

        }
    }

    override fun onApiSuccess(response: resetpassword_response, apiName: String?) {
        resetpassword_progress_bar.visibility = View.GONE
        if (response.responseCode == 200) {

            try {
                SavedPrefManager.savePreferenceBoolean(this, SavedPrefManager.FORGOT_PASSWORD, true)
                val intent = Intent(this, Services::class.java)
                startActivity(intent)
                finishAffinity()
                Toast.makeText(mContext, "Password Changed", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onApiErrorBody(response: String, apiName: String?) {
        resetpassword_progress_bar.visibility = View.GONE
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
        resetpassword_progress_bar.visibility = View.GONE
    }

    fun initializedControl() {


        PasswordET.addTextChangedListener(textWatcher)
        ConfirmPasswordET.addTextChangedListener(textWatcher)
    }


    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            FormValidation.ResetPasswordValidations(
                PasswordET,
                PasswordTV,
                ConfirmPasswordET,
                ConfirmPasswordTv,
                PasswordLL,
                ConfirmPasswordLL
            )
        }
    }


}