package com.exobe.entity.serviceBase

import android.annotation.SuppressLint
import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Remotedatasource {

    companion object {
        private var _service: ApiInterface? = null
        private val BASE_URL: String? = null
        private var _client: Remotedatasource? = null
        private var mContext: Context? = null
        private var retrofit: Retrofit? = null
        var condition: Boolean = false

        fun getApiService(): ApiInterface? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
//                    .baseUrl(com.exobe.entity.Service_Base.ServiceConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(ApiInterface::class.java)
        }

        fun current(Context: Context, flag: Boolean): ApiInterface? {
            condition = flag
            Remotedatasource!!.mContext = Context
            return getInstance().Remotedatasource()
        }

        fun currentHolidays(Context: Context): ApiInterface? {
            Remotedatasource!!.mContext = Context
            return getInstance().RemotedatasourceHoliday()
        }

        private fun getInstance(): Remotedatasource {
            _client = Remotedatasource()
            return _client as Remotedatasource
        }

        private fun getService(): ApiInterface? {
            return _service
        }
    }

    @SuppressLint("NotConstructor")
    fun Remotedatasource(): ApiInterface? {
            val interceptor = TokenInterceptor(mContext)

            if (condition) {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client: OkHttpClient = OkHttpClient.Builder()
                    .callTimeout(40, TimeUnit.MINUTES)
                    .connectTimeout(40, TimeUnit.SECONDS)
                    .readTimeout(80, TimeUnit.SECONDS)
                    .writeTimeout(80, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .addInterceptor(logging)
                    .build()
                retrofit = Retrofit.Builder()
                    .baseUrl(ServiceConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            } else {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client: OkHttpClient = OkHttpClient.Builder()
                    .callTimeout(40, TimeUnit.MINUTES)
                    .connectTimeout(40, TimeUnit.SECONDS)
                    .readTimeout(80, TimeUnit.SECONDS)
                    .writeTimeout(80, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build()
                retrofit = Retrofit.Builder()
                    .baseUrl(ServiceConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            }
            _service = retrofit!!.create(ApiInterface::class.java)


//            if (retrofit == null) {
//                retrofit = Retrofit.Builder()
//                    .baseUrl(com.exobe.entity.Service_Base.ServiceConstant.BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//            }
//            _service=retrofit!!.create(Api_interface::class.java)
//            return _service
        return _service
    }

    fun RemotedatasourceHoliday(): ApiInterface? {
        val interceptor = TokenInterceptor(mContext)
        val client: OkHttpClient = OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.MINUTES)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl("https://calendarific.com/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        _service = retrofit!!.create(ApiInterface::class.java)
        return _service
//            if (retrofit == null) {
//                retrofit = Retrofit.Builder()
//                    .baseUrl(com.exobe.entity.Service_Base.ServiceConstant.BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//            }
//            _service=retrofit!!.create(Api_interface::class.java)
//            return _service

    }
}