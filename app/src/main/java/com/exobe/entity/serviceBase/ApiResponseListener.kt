package com.exobe.entity.serviceBase


interface ApiResponseListener<T> {
    fun onApiSuccess(response: T, apiName: String?)
    fun onApiErrorBody(response: String, apiName: String?)
    fun onApiFailure(failureMessage: String?, apiName: String?)

}