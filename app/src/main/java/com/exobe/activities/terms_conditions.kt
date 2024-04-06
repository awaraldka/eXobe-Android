package com.exobe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.androidextention
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.StaticContentResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager

class terms_conditions : AppCompatActivity() {
    lateinit var text_terms_and_conditions:TextView
    lateinit var title:TextView
    lateinit var back:ImageView
    lateinit var progressbar:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_conditions)
        window.attributes.windowAnimations = R.style.Fade
        text_terms_and_conditions=findViewById(R.id.text_terms_and_conditions)

        title=findViewById(R.id.title)
        back=findViewById(R.id.back)
        progressbar=findViewById(R.id.progressbar)
        Terms_and_conditionApi()
        title.text = "Terms & Conditions"

        back.setSafeOnClickListener {
            finish()
        }

    }


    fun Terms_and_conditionApi() {
        if (androidextention.isOnline(this)) {
            progressbar.visibility=View.VISIBLE
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<StaticContentResponse> =
                ApiCallBack<StaticContentResponse>(object :
                    ApiResponseListener<StaticContentResponse> {
                    override fun onApiSuccess(response: StaticContentResponse, apiName: String?) {
                        progressbar.visibility=View.GONE

                        if (response!!.responseCode == 200) {
                            try {
                                text_terms_and_conditions.text= HtmlCompat.fromHtml(response.result!!.description.toString(),HtmlCompat.FROM_HTML_MODE_LEGACY)

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar.visibility=View.GONE
                        androidextention.disMissProgressDialog(this@terms_conditions)

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility=View.GONE
                        androidextention.disMissProgressDialog(this@terms_conditions)

                    }


                }, "Terms_and_conditionApi", this)

            try {
                serviceManager.staticDataApi(callBack,"termsConditions")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            androidextention.alertBox("Please check your internet connection." , this)

        }
    }


}