package com.exobe.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.exobe.hostActivity.ServicesMainActivity
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.activities.services.CommonForServicesActivity
import com.exobe.hostActivity.MainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.attributes.windowAnimations = R.style.Fade
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        val isLogin = SavedPrefManager.getStringPreferences(this, SavedPrefManager.isLogin).toString()

        Handler().postDelayed({
            if (SavedPrefManager.getBooleanPreferences(this, SavedPrefManager.TutorialFlag)) {
                if (isLogin == "true") {
                    val value = SavedPrefManager.getStringPreferences(this, SavedPrefManager.USER_TYPE)
                    when (value.toString()) {
                        "CUSTOMER" -> {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("flag", "Customer")
                            startActivity(intent)
                            finish()
                        }
                        "RETAILER" -> {
                            var flag = SavedPrefManager.getStringPreferences(this, SavedPrefManager.RETAILER_FORM_FLAG)

                            if(flag.equals("2")) {
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("flag", "retailer")
                                startActivity(intent)
                                finish()
                            } else {
                                val intent = Intent(this, Services::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                        "SERVICE_PROVIDER" -> {
                            val flag = SavedPrefManager.getStringPreferences(this, SavedPrefManager.SERVICE_PROVIDER_FORM_FLAG)
                            if(flag.equals("3")) { // Account Is Approved
                                val intent = Intent(this, ServicesMainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                val intent = Intent(this, Services::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }

                        "PICKUP_PARTNER" -> {
                            val flag = SavedPrefManager.getStringPreferences(this, SavedPrefManager.PD_PROVIDER_FORM_FLAG)
                            if(flag.equals("2")) { // Account Is Approved.
                                val intent = Intent(this, CommonForServicesActivity::class.java)
                                startActivity(intent)
                                finishAfterTransition()
                            }else{
                                val intent = Intent(this, Services::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }

                        "FIELD_ENTITY" -> {
                            val flag = SavedPrefManager.getStringPreferences(this, SavedPrefManager.FE_PROVIDER_FORM_FLAG)
                            if(flag.equals("2")) { // Account Is Approved.
                                val intent = Intent(this, CommonForServicesActivity::class.java)
                                startActivity(intent)
                                finishAfterTransition()
                            }else{
                                val intent = Intent(this, Services::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }

                        "DELIVERY_PARTNER" -> {
                            val flag = SavedPrefManager.getStringPreferences(this, SavedPrefManager.DD_PROVIDER_FORM_FLAG)
                            if(flag.equals("2")) { // Account Is Approved.
                                val intent = Intent(this, CommonForServicesActivity::class.java)
                                startActivity(intent)
                                finishAfterTransition()
                            }else{
                                val intent = Intent(this, Services::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }

                        else -> {
                            val intent = Intent(this, Services::class.java)
                            startActivity(intent)
                            finish()
                        }

                    }
                } else {
                    val intent = Intent(this, Services::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                val intent = Intent(this, TutorialScreen::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000) // 2000 is the delayed time in milliseconds.
    }
}