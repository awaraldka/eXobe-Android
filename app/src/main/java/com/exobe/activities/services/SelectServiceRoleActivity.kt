package com.exobe.activities.services

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.Progresss
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.customClicks.UpdateIsLoginListener
import com.exobe.databinding.ActivitySelectServiceRoleBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.CommonResponseForAll
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.extension.displayToast
import com.google.gson.GsonBuilder

class SelectServiceRoleActivity : AppCompatActivity(), UpdateIsLoginListener {

    private lateinit var binding: ActivitySelectServiceRoleBinding

    var fulfillment =  false
    var transportation =  false
    var standard =  false

    var isFrom = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectServiceRoleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.attributes.windowAnimations = R.style.Fade

        intent.getStringExtra("isFrom")?.let{ isFrom  = it}



        if (isFrom == "Profile"){
            fulfillment  = SavedPrefManager.getBooleanPreferences(this,SavedPrefManager.isFulfillmentService)
            transportation  = SavedPrefManager.getBooleanPreferences(this,SavedPrefManager.isTransportationService)
            standard  = SavedPrefManager.getBooleanPreferences(this,SavedPrefManager.isStandardService)

            if (fulfillment){
                binding.fulfillment.background = ColorDrawable(Color.WHITE)
                binding.PickupTick.isVisible = true
                binding.fulfillment.isEnabled = false

                binding.continueLogin.isVisible = true
                binding.continueLogin.isEnabled = true

                binding.continueLogin.setBackgroundResource(R.drawable.login_button)
            }

            if (transportation){
                binding.Transportation.background = ColorDrawable(Color.WHITE)
                binding.FieldEntityTick.isVisible = true
                binding.continueLogin.isVisible = true
                binding.continueLogin.isEnabled = true

                binding.Transportation.isEnabled = false

                binding.continueLogin.setBackgroundResource(R.drawable.login_button)
            }

            if (standard){
                binding.Standard.background = ColorDrawable(Color.WHITE)

                binding.BookingProviderTick.isVisible = true

                binding.continueLogin.isVisible = true
                binding.continueLogin.isEnabled = true

                binding.Standard.isEnabled = false


                binding.continueLogin.setBackgroundResource(R.drawable.login_button)
            }



        }


        binding.fulfillment.setOnClickListener {
            if (!fulfillment){
                binding.fulfillment.background = createBorder()
                binding.PickupTick.isVisible = true
                fulfillment = true
                binding.continueLogin.isEnabled = true
                binding.continueLogin.setBackgroundResource(R.drawable.login_button)
            }else{
                binding.fulfillment.background = ColorDrawable(Color.WHITE)
                binding.PickupTick.isVisible = false
                fulfillment = false
                if (!transportation  && !standard){
                    binding.continueLogin.isEnabled = false
                    binding.continueLogin.setBackgroundResource(R.drawable.cancel_button)
                }

            }

        }


        binding.Transportation.setOnClickListener {
            if (!transportation){
                binding.Transportation.background = createBorder()
                binding.FieldEntityTick.isVisible = true
                transportation = true
                binding.continueLogin.isEnabled = true
                binding.continueLogin.setBackgroundResource(R.drawable.login_button)
            }else{
                binding.Transportation.background = ColorDrawable(Color.WHITE)
                binding.FieldEntityTick.isVisible = false
                transportation = false

                if (!fulfillment  && !standard){
                    binding.continueLogin.isEnabled = false
                    binding.continueLogin.setBackgroundResource(R.drawable.cancel_button)
                }

            }

        }

        binding.Standard.setOnClickListener {
            if (!standard){
                binding.Standard.background = createBorder()
                binding.BookingProviderTick.isVisible = true
                standard = true
                binding.continueLogin.isEnabled = true
                binding.continueLogin.setBackgroundResource(R.drawable.login_button)
            }else{
                binding.Standard.background =  ColorDrawable(Color.WHITE)
                binding.BookingProviderTick.isVisible = false
                standard= false

                if (!transportation && !fulfillment){
                    binding.continueLogin.isEnabled = false
                    binding.continueLogin.setBackgroundResource(R.drawable.cancel_button)
                }


            }

        }


        binding.continueLogin.setOnClickListener {
            if (!standard && !transportation && !fulfillment){
                displayToast("Please select the services you want to provide.")
            }else{
                var requestData :List<String> = listOf()
                if (standard){
                    requestData = requestData + "STANDARD"
                }
                if(transportation){
                    requestData = requestData +"TRANSAPORTATION"
                }
                if(fulfillment){
                    requestData = requestData +"FULLFILLMENT"
                }
                selectServiceApi(requestData)
            }
        }



        this.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isFrom == "Profile") {
                    alertBoxFinish()
                }else{
                    finishAfterTransition()
                }
            }

        })



    }

    private fun createBorder(): GradientDrawable {
        val border = GradientDrawable()
        border.setColor(Color.WHITE) // Set the background color of the border
        border.setStroke(5, Color.RED)
        return border
    }

    override fun isLoginListener() {
        val name = findViewById<TextView>(R.id.name)
        val userProfile = findViewById<ImageView>(R.id.Profile_picture)
        CommonFunctions.getProfileApiApi(this, name, userProfile)
    }

    private fun selectServiceApi(dataRequest: List<String>) {
        if (androidextention.isOnline(this)) {
            Progresss.start(this)
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<CommonResponseForAll> =
                ApiCallBack(object : ApiResponseListener<CommonResponseForAll> {
                    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
                    override fun onApiSuccess(response: CommonResponseForAll, apiName: String?) {
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            try {

                                SavedPrefManager.savePreferenceBoolean(this@SelectServiceRoleActivity, SavedPrefManager.isStandardService, standard)
                                SavedPrefManager.savePreferenceBoolean(this@SelectServiceRoleActivity, SavedPrefManager.isTransportationService, transportation)
                                SavedPrefManager.savePreferenceBoolean(this@SelectServiceRoleActivity, SavedPrefManager.isFulfillmentService, fulfillment)

                                val intent = Intent(this@SelectServiceRoleActivity, UploadDocumentForServiceActivity::class.java)
                                intent.putExtra("standard", standard)
                                intent.putExtra("transportation", transportation)
                                intent.putExtra("fulfillment", fulfillment)
                                intent.putExtra("isFrom",isFrom)
                                startActivity(intent)

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()
                        Progresss.stop()
                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@SelectServiceRoleActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, apiName = "selectServiceApi", this)


            try {

                serviceManager.selectServiceApi(callBack, serviceArray = dataRequest, flag = 2)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }


    private fun alertBoxFinish() {
        runOnUiThread{
            var alertDialog: AlertDialog? = null
            var builder = AlertDialog.Builder(this)
            builder.setIcon(R.mipmap.ic_launcher)
            builder.setTitle("eXobe")
            builder.setMessage("Are you sure you want to exist with out completing this step?")
            builder.setPositiveButton("Ok") { dialogInterface, which ->
                alertDialog!!.dismiss()
                CommonFunctions.logoutApi(this)
                builder = AlertDialog.Builder(this, R.style.ProgressDialogStyle)

            }

            builder.setNegativeButton("Cancel"){dialogInterface, which ->
                alertDialog!!.dismiss()

            }


            alertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

    }



}