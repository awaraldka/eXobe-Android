package com.exobe.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.permission.RequestPermission
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.LocationClass
import com.exobe.utils.SavedPrefManager
import com.exobe.bottomSheet.CustomerBottomSheet
import com.exobe.customClicks.UpdateIsLoginListener
import com.exobe.databinding.ActivityServicesBinding
import com.exobe.hostActivity.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging


object LoginPresent{
    var isForLogin = ""
}


class Services : AppCompatActivity(), UpdateIsLoginListener {
    private lateinit var binding: ActivityServicesBinding
    val LOCATION_PERMISSION_REQ_CODE = 1000;
    var navigationFlag = ""






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        locationpermission()
        initializedControl()

        RequestPermission.requestMultiplePermissions(this)
        binding.CustomerLogin.setSafeOnClickListener {
            navigationFlag = "CUSTOMER"
            binding.CustomerLogin.background = createBorder()
            binding.RetailerLogin.background = ColorDrawable(Color.WHITE)
            binding.ServiceProviderTextView.background = ColorDrawable(Color.WHITE)
            binding.customerTick.isVisible = true
            binding.retailerTick.isVisible = false
            binding.spTick.isVisible = false
            binding.continueLogin.isVisible = true
        }


        binding.RetailerLogin.setSafeOnClickListener {
            navigationFlag = "RETAILER"
            binding.RetailerLogin.background = createBorder()
            binding.CustomerLogin.background = ColorDrawable(Color.WHITE)
            binding.ServiceProviderTextView.background = ColorDrawable(Color.WHITE)
            binding.customerTick.isVisible = false
            binding.retailerTick.isVisible = true
            binding.spTick.isVisible = false
            binding.continueLogin.isVisible = true
        }

        binding.ServiceProviderTextView.setSafeOnClickListener {
            navigationFlag = "SERVICE_PROVIDER"
            binding.ServiceProviderTextView.background = createBorder()
            binding.CustomerLogin.background = ColorDrawable(Color.WHITE)
            binding.RetailerLogin.background = ColorDrawable(Color.WHITE)
            binding.customerTick.isVisible = false
            binding.retailerTick.isVisible = false
            binding.spTick.isVisible = true
            binding.continueLogin.isVisible = true
        }

        binding.continueLogin.setOnClickListener{


            when(navigationFlag) {
                "CUSTOMER" -> {
                    SavedPrefManager.saveStringPreferences(this, SavedPrefManager.USER_TYPE, "CUSTOMER")
                    SavedPrefManager.saveStringPreferences(this, SavedPrefManager.LOGIN_USER_TYPE, "CUSTOMER")

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("flag", "Customer")
                    startActivity(intent)
                }
                "RETAILER" -> {
                    SavedPrefManager.saveStringPreferences(this, SavedPrefManager.LOGIN_USER_TYPE, "RETAILER")
                    SavedPrefManager.saveStringPreferences(this, SavedPrefManager.USER_TYPE, "RETAILER")
                    CustomerBottomSheet("Retailer", this, this).show(supportFragmentManager, "ModalBottomSheet")
                }
                "SERVICE_PROVIDER" -> {

                    SavedPrefManager.saveStringPreferences(this, SavedPrefManager.LOGIN_USER_TYPE, "SERVICE_PROVIDER")
                    SavedPrefManager.saveStringPreferences(this, SavedPrefManager.USER_TYPE, "SERVICE_PROVIDER")
                    CustomerBottomSheet(userRole = "Service_Provider", this,this).show(supportFragmentManager, "ModalBottomSheet")


//                    val intent = Intent(this, SelectServiceRoleActivity::class.java)
//                    startActivity(intent)
                }
            }
        }


    }

    override fun onStart() {
        super.onStart()
        handleForgotPassword()

        if (LoginPresent.isForLogin == "Service_Provider"){
            LoginPresent.isForLogin = ""
            CustomerBottomSheet(userRole = "Service_Provider", this,this).show(supportFragmentManager, "ModalBottomSheet")
        }


    }
     private fun createBorder() : GradientDrawable{
         val border = GradientDrawable()
         border.setColor(Color.WHITE) // Set the background color of the border
         border.setStroke(5, Color.RED)
         return border
     }

    private fun handleForgotPassword() {
        val forgotPasswordFlag = SavedPrefManager.getBooleanPreferences(this, SavedPrefManager.FORGOT_PASSWORD)

        if (forgotPasswordFlag) {
            SavedPrefManager.savePreferenceBoolean(this, SavedPrefManager.FORGOT_PASSWORD, false)

            val userType = SavedPrefManager.getStringPreferences(this, SavedPrefManager.LOGIN_USER_TYPE)

            when (userType) {
                "RETAILER" -> showCustomerBottomSheet("Retailer")
                "CUSTOMER" -> showCustomerBottomSheet("Customer")
                "SERVICE_PROVIDER" -> showCustomerBottomSheet("Service_Provider")
            }
        }
    }

    private fun showCustomerBottomSheet(customerType: String) {
        supportFragmentManager.let { fragmentManager -> CustomerBottomSheet(customerType, this, this).show(fragmentManager, "ModalBottomSheet") }
    }

    private fun initializedControl() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            val token = task.result
            SavedPrefManager.saveStringPreferences(applicationContext, SavedPrefManager.KEY_DEVICE_TOKEN,token)
        })


    }


    private fun locationpermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQ_CODE
            )
        } else {
            LocationClass.getCurrentLocation(this)
            LocationClass.displayLocationSettingsRequest(this)
        }

    }

    override fun isLoginListener() {
        var name = findViewById<TextView>(R.id.name)
        var userProfile = findViewById<ImageView>(R.id.Profile_picture)
        CommonFunctions.getProfileApiApi(this, name, userProfile)
    }

}
