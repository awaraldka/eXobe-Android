package com.exobe.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import com.exobe.R
import com.exobe.customClicks.PaymentDoneListener
import com.exobe.dialogs.DialogBoxPayment
import com.exobe.dialogs.PaymentCancelDialogBox
import com.exobe.dialogs.PaymentFailedDialogBox
import com.exobe.entity.serviceBase.ServiceConstant

class OzowPaymentActivity : AppCompatActivity(), PaymentDoneListener {
    lateinit var mywebview: WebView
    lateinit var progressBar: ProgressBar
    var websiteUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_ozow)
        window.attributes.windowAnimations = R.style.Fade
        progressBar = findViewById(R.id.progressBar)
        mywebview = findViewById(R.id.mywebview)

        GET_PARSING_DATA()
        OPEN_URL()

    }

    private fun GET_PARSING_DATA() {
        intent.getStringExtra("websiteUrl")?.let {
            websiteUrl = it
        }
    }

    private fun OPEN_URL() {
        mywebview.loadUrl(websiteUrl)
        mywebview.webViewClient = MyWebViewClient(progressBar)
        mywebview.clearCache(true)
        mywebview.requestFocus()
        mywebview.settings.javaScriptEnabled = true
        mywebview.settings.lightTouchEnabled = true
        mywebview.settings.domStorageEnabled = true
        mywebview.isSoundEffectsEnabled = true
        mywebview.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        mywebview.settings.useWideViewPort = true
//        mywebview.settings.setAppCachePath(cacheDir.path)
//        mywebview.settings.setAppCacheEnabled(true)
        mywebview.settings.mediaPlaybackRequiresUserGesture = true

    }

    fun finishMethod() {
        Toast.makeText(this, "Cancel Payment.", Toast.LENGTH_SHORT).show()
        finish()
    }

    fun SuccessMethod() {
        supportFragmentManager.let { DialogBoxPayment(this).show(it, "MyCustomFragment") }
    }

    fun FailedMethod() {
        supportFragmentManager.let { PaymentFailedDialogBox(this).show(it, "MyCustomFragment") }
    }

    fun cancelMethod() {
        supportFragmentManager.let { PaymentCancelDialogBox(this).show(it, "MyCustomFragment") }
    }

    inner class MyWebViewClient(private val progressBar: ProgressBar) : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            progressBar.visibility = View.VISIBLE
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
            if (url.contains("${ServiceConstant.BASE_OWZO_URL}/api/v1/order/cancel")) {
                cancelMethod()
            } else if (url.contains("${ServiceConstant.BASE_OWZO_URL}/api/v1/order/success")) {
                SuccessMethod()
            } else if (url.contains("${ServiceConstant.BASE_OWZO_URL}/api/v1/order/failed")) {
                FailedMethod()
            }
        }


        init {
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun paymentDone() {
        val intent = Intent()
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun FailedDone() {
        finish()
    }

    override fun cancelPayment() {
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ServiceRequestReview.apiServiceRequestCallFlag = false
    }


}