package com.exobe.activities.services

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.exobe.fragments.profile.EditProfileActivity
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.androidextention
import com.exobe.databinding.ActivityProfileServiceProviderBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.MyProfileResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder

class ProfileServiceProviderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileServiceProviderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileServiceProviderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.attributes.windowAnimations = R.style.Fade

        binding.backButtonClick.setOnClickListener {
            finishAfterTransition()
        }

        binding.btnEditProfile.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        binding.editBusinessForm.setOnClickListener {
            val intent = Intent(this, CommonBusinessFormActivity::class.java)
            intent.putExtra("isFrom","Profile")
            startActivity(intent)
        }





        myProfileApi()
    }

    private fun myProfileApi() {
        if (androidextention.isOnline(this@ProfileServiceProviderActivity)) {
            binding.shimmerFrameLayout.isVisible = true
            binding.shimmerFrameLayout.startShimmerAnimation()
            binding.nestedScroll.isVisible = false
            val serviceManager = ServiceManager(this@ProfileServiceProviderActivity)
            val callBack: ApiCallBack<MyProfileResponse> =
                ApiCallBack(object : ApiResponseListener<MyProfileResponse> {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onApiSuccess(response: MyProfileResponse, apiName: String?) {
                        binding.shimmerFrameLayout.isVisible = false
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.nestedScroll.isVisible = true
                        if (response.responseCode == 200) {
                            try {

                                response.result.apply {

                                    if (!profilePic.equals("")) {
                                        Glide.with(this@ProfileServiceProviderActivity).load(profilePic)
                                            .placeholder(R.drawable.profile_placeholder_linearlayout).into(binding.showprofileImage)
                                    }
                                    val name = "$firstName $lastName"
                                    binding.fullName.text = name.takeIf { it.isNotBlank() } ?: "NA"
                                    binding.ContactNumberTextView.text = mobileNumber.toString().takeIf { it.isNotBlank() } ?: "NA"
                                    binding.PostCodeTextView.text = zipCode.toString().takeIf { it.isNotBlank() } ?: "NA"
                                    binding.AddressTv1.text = addressLine1.takeIf { it!!.isNotBlank() } ?: "NA"
                                    if(addressLine2 == "" || addressLine2 == null) {
                                        binding.addressLine1LL.isVisible = false
                                    } else {
                                        binding.addressLine1LL.isVisible = true
                                        binding.AddressTv2.text = addressLine2
                                    }
                                    binding.Country.text = country
                                    binding.State.text = state
                                    binding.City.text = city



                                }










                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()
                        binding.shimmerFrameLayout.stopShimmerAnimation()
                        binding.shimmerFrameLayout.isVisible = false
                        binding.nestedScroll.isVisible = true
                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@ProfileServiceProviderActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        binding.shimmerFrameLayout.stopShimmerAnimation()

                        binding.shimmerFrameLayout.isVisible = false
                        binding.nestedScroll.isVisible = true
                       
                    }

                }, "MyProfileResponse", this@ProfileServiceProviderActivity)


            try {

                serviceManager.MyProfileApi(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this@ProfileServiceProviderActivity)

        }
    }



}