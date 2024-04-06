package com.exobe.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.callisdairy.extension.setSafeOnClickListener
import com.example.validationpractice.utlis.FormValidation
import com.example.validationpractice.utlis.FormValidation.LoginRetailerValidations
import com.exobe.activities.services.FillDetailsServiceProviderActivity
import com.exobe.R
import com.exobe.activities.customer.RegisterCustomerActivity
import com.exobe.hostActivity.MainActivity
import com.exobe.utils.SavedPrefManager

class LoginActivity : AppCompatActivity() {


    lateinit var register: LinearLayout
    lateinit var forgotPassword: TextView
    lateinit var PhoneEditText: EditText
    lateinit var PhoneTV: TextView
    lateinit var PasswordEditText: EditText
    lateinit var PasswordTextView: TextView
    lateinit var LogINClick: Button
    lateinit var PhoneLinear: LinearLayout
    lateinit var PaswordLL: LinearLayout
    lateinit var EmailId: LinearLayout
    lateinit var cross_eye: ImageView
    private var passwordNotVisible = 0
    var pageFlag = ""


    //    Retailer
    lateinit var RetailerET: EditText
    lateinit var RetalierLinear: LinearLayout
    lateinit var Retalierid: LinearLayout
    lateinit var ServiceProviderTV_LL: LinearLayout
    lateinit var ServiceProviderET_LL: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        window.attributes.windowAnimations = R.style.Fade

        register = findViewById(R.id.LogIN)
        forgotPassword = findViewById(R.id.ForgotPassword)
        PhoneEditText = findViewById(R.id.PhoneEditText)
        PhoneTV = findViewById(R.id.PhoneTV)
        PasswordEditText = findViewById(R.id.PasswordEditText)
        PasswordTextView = findViewById(R.id.PasswordTextView)
        LogINClick = findViewById(R.id.LogINClick)
        PhoneLinear = findViewById(R.id.PhoneLinear)
        PaswordLL = findViewById(R.id.PaswordLL)
        cross_eye = findViewById(R.id.cross_eye)
        RetalierLinear = findViewById(R.id.RetalierLinear)
        Retalierid = findViewById(R.id.Retalierid)
        EmailId = findViewById(R.id.EmailID)
        RetailerET = findViewById(R.id.RetailerET)
        ServiceProviderTV_LL = findViewById(R.id.ServiceProviderTV_LinearLayout)
        ServiceProviderET_LL = findViewById(R.id.ServiceProviderET_LinearLayout)


        pageFlag = intent.getStringExtra("flag").toString()


        register.setSafeOnClickListener {
            if (pageFlag.equals("Customer")) {
                startActivity(Intent(this, RegisterCustomerActivity::class.java))
            }
        }

        forgotPassword.setSafeOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }
        cross_eye.setSafeOnClickListener {
            if (passwordNotVisible == 0) {
                PasswordEditText.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                cross_eye.setImageDrawable(resources.getDrawable(R.drawable.eye))
                PasswordEditText.setSelection(PasswordEditText.length())
                passwordNotVisible = 1


            } else if (passwordNotVisible == 1) {
                PasswordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
                cross_eye.setImageDrawable(resources.getDrawable(R.drawable.ic_eye_slash_solid))
                PasswordEditText.setSelection(PasswordEditText.length())
                passwordNotVisible = 0
            } else {
                PasswordEditText.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                cross_eye.setImageDrawable(resources.getDrawable(R.drawable.eye))
                PasswordEditText.setSelection(PasswordEditText.length())
                passwordNotVisible = 1
            }
        }


        if (pageFlag.equals("Customer")) {
            EmailId.visibility = View.VISIBLE
            PhoneEditText.visibility = View.VISIBLE

            ServiceProviderTV_LL.visibility = View.GONE
            ServiceProviderET_LL.visibility = View.GONE
            Retalierid.visibility = View.GONE
            RetalierLinear.visibility = View.GONE


            LogINClick.setSafeOnClickListener {

                if (FormValidation.LoginValidations(
                        PhoneEditText,
                        PhoneTV,
                        PasswordEditText,
                        PasswordTextView,
                        PhoneLinear,
                        PaswordLL
                    )
                ) {
                    val intent = Intent(this, MainActivity::class.java)
                    SavedPrefManager.saveStringPreferences(this, SavedPrefManager.isLogin, "true")
                    startActivity(intent)
                }

            }

        } else if (pageFlag.equals("Retailer")) {
            Retalierid.visibility = View.VISIBLE
            RetalierLinear.visibility = View.VISIBLE

            EmailId.visibility = View.GONE
            PhoneLinear.visibility = View.GONE
            ServiceProviderTV_LL.visibility = View.GONE
            ServiceProviderET_LL.visibility = View.GONE


            LogINClick.setSafeOnClickListener {

                if (LoginRetailerValidations(
                        RetailerET,
                        PhoneTV,
                        PasswordEditText,
                        PasswordTextView,
                        RetalierLinear,
                        PaswordLL
                    )
                ) {
                    val intent = Intent(this, fillDetails_retailer::class.java)
                    startActivity(intent)
//                    val intent = Intent(this, MainActivity::class.java)
//                    SavedPrefManager.saveStringPreferences(this, SavedPrefManager.isLogin, "true")
//                    startActivity(intent)
                }
            }
        } else if (pageFlag.equals("Service_Provider")) {
            ServiceProviderTV_LL.visibility = View.VISIBLE
            ServiceProviderET_LL.visibility = View.VISIBLE

            EmailId.visibility = View.GONE
            PhoneLinear.visibility = View.GONE
            Retalierid.visibility = View.GONE
            RetalierLinear.visibility = View.GONE


            LogINClick.setSafeOnClickListener {

                if (LoginRetailerValidations(
                        RetailerET,
                        PhoneTV,
                        PasswordEditText,
                        PasswordTextView,
                        RetalierLinear,
                        PaswordLL
                    )
                ) {

                    val intent = Intent(this, FillDetailsServiceProviderActivity::class.java)
                    SavedPrefManager.saveStringPreferences(this, SavedPrefManager.isLogin, "true")
                    startActivity(intent)
                }
            }
        }



    }
}