package com.exobe.entity

import android.content.Context
import com.exobe.R
import com.exobe.entity.serviceBase.ApiResponseListener

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class ApiCallBack<T>(private var apiListener: ApiResponseListener<T>, var apiName: String, var mContext: Context) : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {

        if (response.isSuccessful) {
            response.body()?.let { apiListener.onApiSuccess(it, apiName)
            }
        } else {
            apiListener.onApiErrorBody(response.errorBody()!!.string(), apiName)
        }
    }
    override fun onFailure(call: Call<T>, t: Throwable) {
        try {


             apiListener.onApiFailure(mContext!!.getString(R.string.server_not_responding), apiName)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}