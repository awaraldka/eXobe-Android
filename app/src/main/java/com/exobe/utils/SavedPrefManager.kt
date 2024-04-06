package com.exobe.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager

class SavedPrefManager(var context: Context) {
    private val preferences: SharedPreferences
    private val editor: SharedPreferences.Editor


    /**
     * Retrieving the value from the preference for the respective key.
     *
     * @param key : Key for which the value is to be retrieved
     * @return return value for the respective key as boolean.
     */
    private fun getBooleanValue(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    /**
     * Saving the preference
     *
     * @param key   : Key of the preference.
     * @param value : Value of the preference.
     */
    private fun setBooleanValue(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    /**
     * Retrieving the value from the preference for the respective key.
     *
     * @param key : Key for which the value is to be retrieved
     * @return return value for the respective key as string.
     */
    private fun getStringValue(key: String): String? {
        return preferences.getString(key, "")
    }

    /**
     * Saving the preference
     *
     * @param key   : Key of the preference.
     * @param value : Value of the preference.
     */
    private fun setStringValue(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    /**
     * Retrieving the value from the preference for the respective key.
     *
     * @param key : Key for which the value is to be retrieved
     * @return return value for the respective key as string.
     */
    private fun getIntValue(key: String): Int {
        return preferences.getInt(key, 0)
    }

    /**
     * Saving the preference
     *
     * @param key   : Key of the preference.
     * @param value : Value of the preference.
     */
    private fun setIntValue(key: String, value: Int) {
        editor.putInt(key, value)
        editor.commit()
    }

    /**
     * Retrieving the value from the preference for the respective key.
     *
     * @param key : Key for which the value is to be retrieved
     * @return return value for the respective key as string.
     */
    fun getLongValue(key: String?): Long {
        return preferences.getLong(key, 0L)
    }

    /**
     * Saving the preference
     *
     * @param key   : Key of the preference.
     * @param value : Value of the preference.
     */
    fun setLongValue(key: String?, value: Long) {
        editor.putLong(key, value)
        editor.commit()
    }

    /**
     * Remove the preference for the particular key
     *
     * @param key : Key for which the preference to be cleared.
     */
    fun removeFromPreference(key: String?) {
        editor.remove(key)
        editor.commit()
    }

    companion object {
        //preferences variables
        const val NOTIFICATION_VISIBLE = "NOTIFICATION_VISIBLE"
        const val isLogin = "login"
        const val priceTag = "price_tag"
        const val TOKEN = "token"
        const val CUSTOMER_USER_ID = "customer_user_id"
        const val USER_TYPE = "USER_TYPE"
        const val USER_ID = "USER_ID"
        const val TutorialFlag = "TutorialFlag"
        const val UPDATE_PROFILE = "UPDATE_PROFILE"
        const val EMAIL = "EMAIL"
        const val FORGOT_PASSWORD = "FORGOT_PASSWORD"
        const val CALL_BACK_API = "CALL_BACK_API"
        const val USER_REQUEST_STATUS = "USER_REQUEST_STATUS"
        const val ID = "ID"
        const val SERVICE_ID_Address = "SERVICE_ID_Address"
        const val ADMIN_PRODUCT_ID = ""
        private var lat: Double? = null
        private var long: Double? = null
        const val ADMIN_DEALS_ID = "ADMIN_DEALS_ID"
        const val RETAILER_FORM_FLAG = "RETAILER_FORM_FLAG"
        const val SERVICE_PROVIDER_FORM_FLAG = "SERVICE_PROVIDER_FORM_FLAG"
        const val PD_PROVIDER_FORM_FLAG = "PD_PROVIDER_FORM_FLAG"
        const val DD_PROVIDER_FORM_FLAG = "DD_PROVIDER_FORM_FLAG"
        const val FE_PROVIDER_FORM_FLAG = "FE_PROVIDER_FORM_FLAG"
        const val CUSTOMER_GMAIL_ADDRESS = "CUSTOMER_GMAIL_ADDRESS"
        const val CUSTOMER_PASSWORD = "CUSTOMER_PASSWORD"

        const val RETAILER_GMAIL_ADDRESS = "RETAILER_GMAIL_ADDRESS"
        const val RETAILER_PASSWORD = "RETAILER_PASSWORD"

        const val SERVICE_GMAIL_ADDRESS = "SERVICE_GMAIL_ADDRESS"
        const val SERVICE_PASSWORD = "SERVICE_PASSWORD"
        const val LOGIN_USER_TYPE = "LOGIN_USER_TYPE"
        const val KEY_DEVICE_TOKEN = "key_device_token"
        const val NOTIFICATION_ID = "NOTIFICATION_ID"
        const val LAT = "LAT"
        const val LONG = "LONG"
        const val USER_NAME = "USER_NAME"
        const val USER_PROFILE = "USER_PROFILE"

        const val PP_EMAil = "PP_EMAil"
        const val PP_PASSWORD = "PP_PASSWORD"

        const val DP_EMAil = "DP_EMAil"
        const val DP_PASSWORD = "DP_PASSWORD"

        const val FE_EMAil = "FE_EMAil"
        const val FE_PASSWORD = "FE_PASSWORD"

        const val isStandardService = "isStandardService"
        const val isTransportationService = "isTransportationService"
        const val isFulfillmentService = "isFulfillmentService"


        const val MOBILE = "MOBILE"
        const val CAMPAIGN_PREFERENCE = "CAMPAIGN_PREFERENCE"



        fun setLatitudeLocation(value: Double) {
            this.lat = value
        }

        fun getLatitudeLocation(): Double? {
            return lat
        }

        fun setLongitudeLocation(value: Double) {
            this.long = value
        }

        fun getLongitudeLocation(): Double? {
            return long
        }


        private var instance: SavedPrefManager? = null
        private const val PREF_HIGH_QUALITY = "pref_high_quality"


        fun getInstance(context: Context): SavedPrefManager? {
            if (instance == null) {
                synchronized(SavedPrefManager::class.java) {
                    if (instance == null) {
                        instance = SavedPrefManager(context)
                    }
                }
            }
            return instance
        }


        fun saveStringPreferences(context: Context?, key: String, value: String?): String {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString(key, value)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                editor.apply()
            }
            return key
        }

        fun saveIntPreferences(context: Context?, key: String?, value: Int?) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            if (value != null) {
                editor.putInt(key, value)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                editor.apply()
            }
        }

        fun saveFloatPreferences(context: Context?, key: String?, value: Float) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putFloat(key, value)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                editor.apply()
            }
        }

        /*
  This method is used to get string values from shared preferences.
   */
        fun getStringPreferences(context: Context?, key: String?): String? {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getString(key, "")
        }

        /*
     This method is used to get string values from shared preferences.
      */
        fun getIntPreferences(context: Context?, key: String?): Int {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getInt(key, 0)
        }

        fun savePreferenceBoolean(context: Context?, key: String?, b: Boolean) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putBoolean(key, b)
            editor.commit()
        }

        /*
      This method is used to get string values from shared preferences.
       */
        fun getBooleanPreferences(context: Context?, key: String?): Boolean {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getBoolean(key, false)
        }

        /**
         * Removes all the fields from SharedPrefs
         */
        fun clearPrefs(context: Context?) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.clear()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                editor.apply()
            }
        }

        fun deleteAllKeys(context: Context?) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()

            editor.remove("USER_PROFILE")
            editor.remove("USER_NAME")
            editor.remove("login")
            editor.remove("priceTag")
            editor.remove("TOKEN")
            editor.remove("CUSTOMER_USER_ID")
            editor.remove("USER_TYPE")
            editor.remove("USER_ID")
            editor.remove("UPDATE_PROFILE")
            editor.remove("EMAIL")
            editor.remove("FORGOT_PASSWORD")
            editor.remove("CALL_BACK_API")
            editor.remove("USER_REQUEST_STATUS")
            editor.remove("ID")
            editor.remove("SERVICE_ID_Address")
            editor.remove("ADMIN_PRODUCT_ID")
            editor.remove("lat")
            editor.remove("long")
            editor.remove("ADMIN_DEALS_ID")
            editor.remove("RETAILER_FORM_FLAG")
            editor.remove("SERVICE_PROVIDER_FORM_FLAG")
            editor.remove("LOGIN_USER_TYPE")
            editor.remove("KEY_DEVICE_TOKEN")
            editor.remove("NOTIFICATION_ID")
            editor.remove("LAT")
            editor.remove("LONG")
            editor.remove("isStandardService")
            editor.remove("isTransportationService")
            editor.remove("isFulfillmentService")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                editor.apply()
                editor.commit()
            }
        }


        fun deleteAllKeysServicePD(context: Context?) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()


            editor.remove("PD_PROVIDER_FORM_FLAG")
            editor.remove("FE_PROVIDER_FORM_FLAG")
            editor.remove("DD_PROVIDER_FORM_FLAG")
            editor.remove("USER_ID")
            editor.remove("TOKEN")
            editor.remove("USER_TYPE")
            editor.remove("isLogin")

            editor.apply()



        }


    }


    init {
        preferences =
            context.getSharedPreferences("eXobe", Context.MODE_PRIVATE)
        editor = preferences.edit()
    }
}