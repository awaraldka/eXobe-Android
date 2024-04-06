package com.exobe.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.provider.OpenableColumns
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.validationpractice.utlis.FormValidation.runOnUiThread
import com.exobe.activities.Services
import com.exobe.R
import com.exobe.activities.services.ServicePage
import com.exobe.androidextention
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.MyProfileResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.JsonObject
import org.json.JSONObject
import java.io.File
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

object CommonFunctions {

    fun currencyFormatter(amount: Double): String {
        var finalValue = ""
        try {
            val COUNTRY = "US"
            val LANGUAGE = "en"
            val str: String = NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY)).format(amount)
            val replaceString = str.replace(",", " ")
            finalValue = replaceString.replace("$", "")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return finalValue

    }

    fun formatNumber(input: Double): String {
        val symbols = DecimalFormatSymbols(Locale.US)
        symbols.groupingSeparator = ' '

        val df = DecimalFormat("#,##0.00", symbols)
        return df.format(input)
    }


    fun logoutApi(context: Context) {
        if (androidextention.isOnline(context)) {
            Progresss.start(context)
            val serviceManager = ServiceManager(context)
            val callBack: ApiCallBack<JsonObject> =
                ApiCallBack(object : ApiResponseListener<JsonObject> {
                    override fun onApiSuccess(response: JsonObject, apiName: String?) {
                        Progresss.stop()

                        try {
                            var jsonObject = JSONObject(response.toString())
                            var responseCode = jsonObject.getInt("responseCode")
                            var responseMessage = jsonObject.getString("responseMessage")
                            if (responseCode == 200) {

                                ServicePage.isRefreshed = false
                                val userRole =  SavedPrefManager.getStringPreferences(context,SavedPrefManager.USER_TYPE)
                                if (userRole == "PICKUP_PARTNER" || userRole == "FIELD_ENTITY" || userRole == "DELIVERY_PARTNER"){
                                    SavedPrefManager.saveStringPreferences(context, SavedPrefManager.isLogin, "false")
                                    SavedPrefManager.saveStringPreferences(context, SavedPrefManager.USER_NAME,"Guest")
                                    SavedPrefManager.saveStringPreferences(context, SavedPrefManager.USER_PROFILE,"")
                                    SavedPrefManager.deleteAllKeysServicePD(context)
                                    val intent = Intent(context, Services::class.java)
                                    context.startActivity(intent)
                                    (context as Activity).finishAffinity()

                                }else{
                                    SavedPrefManager.saveStringPreferences(context, SavedPrefManager.USER_NAME,"Guest")
                                    SavedPrefManager.saveStringPreferences(context, SavedPrefManager.USER_PROFILE,"")
                                    SavedPrefManager.saveStringPreferences(context, SavedPrefManager.isLogin, "false")
                                    val intent = Intent(context, Services::class.java)
                                    context.startActivity(intent)
                                    (context as Activity).finishAffinity()
                                    SavedPrefManager.deleteAllKeys(context)
                                }



                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        Progresss.stop()
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, "logoutApi", context)
            var jsonObject = JsonObject().apply {
                addProperty("deviceType","android")
                addProperty("deviceToken", SavedPrefManager.getStringPreferences(context, SavedPrefManager.KEY_DEVICE_TOKEN)
                    .toString())
            }
            try {
                    serviceManager.logoutApi(callBack, jsonObject)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun getProfileApiApi(context: Context, name: TextView?, userProfile: ImageView?) {
        if (androidextention.isOnline(context)) {
            val serviceManager = ServiceManager(context)
            val callBack: ApiCallBack<MyProfileResponse> =
                ApiCallBack<MyProfileResponse>(object : ApiResponseListener<MyProfileResponse> {
                    override fun onApiSuccess(response: MyProfileResponse, apiName: String?) {
                        SavedPrefManager.saveStringPreferences(context, SavedPrefManager.USER_NAME,"${response.result.firstName} ${response.result.lastName}")
                        SavedPrefManager.saveStringPreferences(context, SavedPrefManager.USER_PROFILE,response.result.profilePic)
                        SavedPrefManager.saveStringPreferences(context, SavedPrefManager.MOBILE,response.result.mobileNumber)
                        if (name != null) {
                            name.text = SavedPrefManager.getStringPreferences(context, SavedPrefManager.USER_NAME.toString())
                        }
                        if (userProfile != null) {
                            Glide.with(context).load( SavedPrefManager.getStringPreferences(context, SavedPrefManager.USER_PROFILE).toString()).placeholder(R.drawable.side_menu_profile).into(userProfile)
                        }
                        SavedPrefManager.saveStringPreferences(context, SavedPrefManager.isLogin, "true")
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {

                    }

                }, "myprofileApi", context)
            try {
                serviceManager.MyProfileApi(callBack)
            } catch (e: Exception) {

                e.printStackTrace()
            }

        } else {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT)
                .show()
        }
    }


    fun hideKeyboard(requireActivity: Activity) {
        try {
            runOnUiThread {
                val inp: InputMethodManager =
                    requireActivity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                val view = requireActivity.findViewById<View>(android.R.id.content)
                inp.hideSoftInputFromWindow(
                    view.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }

        } catch (ignored: Throwable) {
        }
    }

    fun String.capitalizeFirstLetter(): String {
        if (isEmpty()) {
            return this
        }
        return substring(0, 1).uppercase() + substring(1)
    }

    fun formatPercentage(percentage: Double): String {
        return if (percentage % 1 == 0.0) {
            String.format("%.0f%%", percentage)
        } else {
            String.format("%.2f%%", percentage)
        }
    }


    fun subtractPercentage(value: Double, percentage: Double): Double {
        return (value * (1 - percentage / 100.0)).toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
    }


    fun setDescriptionFun(contetx:Context,originalText: String?,txtDescriptionDetails:TextView) {
        val maxLength = 150 // Maximum length of the visible text

        if (originalText != null) {
            if (originalText.length > maxLength) {
                val trimmedText = originalText.substring(0, maxLength) + "..."
                val spannableString = SpannableString("$trimmedText Read More")

                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(view: View) {
                        // Handle the "Read More" click event
                        txtDescriptionDetails.text = originalText
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        // Customize the appearance of the "Read More" link
                        ds.isUnderlineText = false
                        ds.color = ContextCompat.getColor(contetx,R.color.red)
                    }
                }

                spannableString.setSpan(
                    clickableSpan,
                    trimmedText.length + 1,
                    spannableString.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                txtDescriptionDetails.text = spannableString
                txtDescriptionDetails.movementMethod = LinkMovementMethod.getInstance()
            } else {
                txtDescriptionDetails.text = originalText
            }
        }

    }


    @SuppressLint("Range")
    fun getPDFPath(uri: Uri?, context: Context): String? {
        val uriString: String = uri.toString()
        val myFile = File(uriString)
        val path = myFile.absolutePath
        var displayName: String? = null

        if (uriString.startsWith("content://")) {
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver
                    .query(uri!!, null, null, null, null)
                if (cursor != null && cursor.moveToFirst()) {
                    displayName =
                        cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))

                    val finalDisplayName = displayName
                }
            } finally {
                cursor!!.close()
            }
        } else if (uriString.startsWith("file://")) {
            displayName = myFile.name
        }
        return displayName
    }



    fun deleteAccountApi(context: Context) {
        if (androidextention.isOnline(context)) {
            Progresss.start(context)
            val serviceManager = ServiceManager(context)
            val callBack: ApiCallBack<JsonObject> =
                ApiCallBack(object : ApiResponseListener<JsonObject> {
                    override fun onApiSuccess(response: JsonObject, apiName: String?) {
                        Progresss.stop()

                        try {
                            var jsonObject = JSONObject(response.toString())
                            var responseCode = jsonObject.getInt("responseCode")
                            var responseMessage = jsonObject.getString("responseMessage")
                            if (responseCode == 200) {

                                SavedPrefManager.saveStringPreferences(context, SavedPrefManager.USER_NAME,"Guest")
                                SavedPrefManager.saveStringPreferences(context, SavedPrefManager.USER_PROFILE,"")
                                SavedPrefManager.saveStringPreferences(context, SavedPrefManager.isLogin, "false")
                                val intent = Intent(context, Services::class.java)
                                context.startActivity(intent)
                                (context as Activity).finishAffinity()
                                SavedPrefManager.deleteAllKeys(context)




                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        Progresss.stop()
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, "deleteAccount", context)

            try {
                serviceManager.deleteUserApi(callBack)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT)
                .show()
        }
    }



    fun colorSubstring(inputString: String, targetSubstring: String): SpannableString {
        val spannableString = SpannableString(inputString)

        val startIndex = inputString.indexOf(targetSubstring)
        val endIndex = startIndex + targetSubstring.length

        if (startIndex != -1) {
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#BF1E2E")),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return spannableString
    }




}