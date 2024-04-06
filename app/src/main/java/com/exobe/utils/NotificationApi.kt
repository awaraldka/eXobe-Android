package com.exobe.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.exobe.androidextention
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.NotifiactionCountResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager

class NotificationApi {
    companion object {
        fun notifiactionCountApi(
            context: Context,
            greyBellImageView: ImageView,
            logoutButton: ImageView?
        ) {
            if (androidextention.isOnline(context)) {
                val serviceManager = ServiceManager(context)
                val callBack: ApiCallBack<NotifiactionCountResponse> =
                    ApiCallBack<NotifiactionCountResponse>(object :
                        ApiResponseListener<NotifiactionCountResponse> {
                        override fun onApiSuccess(
                            response: NotifiactionCountResponse,
                            apiName: String?
                        ) {
                            if (response.responseCode == 200) {
                                try {
                                    if (SavedPrefManager.getBooleanPreferences(context, SavedPrefManager.NOTIFICATION_VISIBLE)) {
                                        SavedPrefManager.savePreferenceBoolean(context, SavedPrefManager.NOTIFICATION_VISIBLE, false)

                                        if (response.result?.notificationCount!! > 0) {
                                            greyBellImageView.visibility = View.GONE

                                        } else {
                                            greyBellImageView.visibility = View.VISIBLE
                                            logoutButton?.visibility = View.GONE
                                        }
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }

                        override fun onApiErrorBody(response: String, apiName: String?) {

                        }

                        override fun onApiFailure(failureMessage: String?, apiName: String?) {

                        }

                    }, "notificationCountApi", context)
                try {
                    serviceManager.NotificationCountApi(callBack)
                } catch (e: Exception) {

                    e.printStackTrace()
                }
            }

        }
    }
}