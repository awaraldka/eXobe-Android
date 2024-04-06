package com.exobe.activities.services

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.example.validationpractice.utlis.FormValidation
import com.exobe.activities.Services
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.Progresss
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.customClicks.NavigationClick
import com.exobe.databinding.ActivityCommonBussinessFormBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.CommonBusinessFormRequest
import com.exobe.entity.response.RetailerFillDeatilsViewResponse
import com.exobe.entity.response.CommonResponseForAll
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder

class CommonBusinessFormActivity : AppCompatActivity(), NavigationClick {

    private lateinit var binding: ActivityCommonBussinessFormBinding

    var isFrom = ""
    var usertype =""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommonBussinessFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.attributes.windowAnimations = R.style.Fade


        intent?.getStringExtra("isFrom")?.let { isFrom = it }
        usertype = SavedPrefManager.getStringPreferences(this, SavedPrefManager.USER_TYPE).toString()


        if (usertype =="FIELD_ENTITY"){
            binding.fieldEntitylayout.isVisible = true
            binding.commonForPDandDD.isVisible = false
        }else{
            binding.fieldEntitylayout.isVisible = false
            binding.commonForPDandDD.isVisible = true

        }


        if (isFrom == "Profile") {
            viewDetailsApi()
        }


        binding.ProceedButton.setOnClickListener {

            if (usertype =="FIELD_ENTITY"){
                if( FormValidation.validationForBusinessFormRetailer(
                        binding.firstNameEt,
                        binding.firstNameTV,
                        binding.lastNameEt,
                        binding.lastNameTV,
                        binding.EmailEt,
                        binding.EmailTV,
                        binding.BusinessNameEt,
                        binding.BusinessNameTV,
                        binding.BusinessTypeEt,
                        binding.BusinessTypeTV,
                        binding.BusinessAddressEt,
                        binding.BusinessAddressTV,
                        binding.BusinessEmailEt,
                        binding.BusinessEmailTV,
                        binding.EINEt,
                        binding.EINTV,
                        binding.OrderVolumeET,
                        binding.OrderVolumeTV,
                        binding.BankNameEt,
                        binding.BankNameTV,
                        binding.BranchNameEt,
                        binding.BranchNameTV,
                        binding.BranchCodeEt,
                        binding.BranchCode,
                        binding.SwiftCodeEt,
                        binding.SiftCodeTV,
                        binding.AccountTypeEt,
                        binding.AccountTypeTV,
                        binding.AccountHolderEt,
                        binding.AccountHolderTV,
                        binding.AccountNumberEt,
                        binding.AccountNumberTV,
                        binding.MobileNumbeEt,
                        binding.MobileNumberTV,
                        binding.BusinessTypeSpinner

                    )){
                    fillBusinessFormApi()
                }


            }else{
                if (FormValidation.validationForBusinessFormCommon(
                        binding.firstNameEt,
                        binding.firstNameTV,
                        binding.lastNameEt,
                        binding.lastNameTV,
                        binding.EmailEt,
                        binding.EmailTV,
                        binding.DriverLicenseEt,
                        binding.riverLicenseTV,
                        binding.VehicleTypeEt,
                        binding.VehicleTypeTV,
                        binding.MakeAndModelEt,
                        binding.VehicleMakeAndModelTV,
                        binding.VehicleYearEt,
                        binding.VehicleYearTV,
                        binding.VehicleColorEt,
                        binding.VehicleColorTV,
                        binding.VehicleInsuranceInformationEt,
                        binding.VehicleInsuranceInformationTV,
                        binding.BankNameEt,
                        binding.BankNameTV,
                        binding.BranchNameEt,
                        binding.BranchNameTV,
                        binding.BranchCodeEt,
                        binding.BranchCode,
                        binding.SwiftCodeEt,
                        binding.SiftCodeTV,
                        binding.AccountTypeEt,
                        binding.AccountTypeTV,
                        binding.AccountHolderEt,
                        binding.AccountHolderTV,
                        binding.AccountNumberEt,
                        binding.AccountNumberTV,
                        binding.MobileNumbeEt,
                        binding.MobileNumberTV
                    )
                ) {
                    fillBusinessFormApi()
                }

            }













        }

    }

    private fun fillBusinessFormApi() {

        if (androidextention.isOnline(this)) {
            Progresss.start(this)
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<CommonResponseForAll> =
                ApiCallBack(object : ApiResponseListener<CommonResponseForAll> {
                    override fun onApiSuccess(response: CommonResponseForAll, apiName: String?) {
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            try {
                                if (isFrom == "Profile") {
                                    androidextention.alertBoxUploadDocument(response.responseMessage.toString(),this@CommonBusinessFormActivity,this@CommonBusinessFormActivity)
                                }else{
                                    val intent = Intent(this@CommonBusinessFormActivity, UploadDocumentForServiceActivity::class.java)
                                    startActivity(intent)
                                    finish()

                                }






                            } catch (e: Exception) {
                                e.printStackTrace()
                            }


                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        Progresss.stop()
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@CommonBusinessFormActivity)

                        } catch (e: Exception) {
                          e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, "UpdateBusinessForm", this)

            val request = CommonBusinessFormRequest()
            try {
                request.flag = 1





                request.firstName = binding.firstNameEt.text.toString()
                request.lastName = binding.lastNameEt.text.toString()
                request.email = binding.EmailEt.text.toString()
                request.mobileNumber = binding.MobileNumbeEt.text.toString()

                request.driverDetail!!.licenceNumber = binding.DriverLicenseEt.text.toString()
                request.driverDetail!!.vehicleRegistrationNumber = binding.VehicleRegistrationNumberEt.text.toString()
                request.driverDetail!!.vehicleType = binding.VehicleTypeEt.text.toString()
                request.driverDetail!!.vehicleModel = binding.MakeAndModelEt.text.toString()
                request.driverDetail!!.vehicleYear = binding.VehicleYearEt.text.toString()
                request.driverDetail!!.vehicleColour = binding.VehicleColorEt.text.toString()
                request.driverDetail!!.insuranceNumber = binding.VehicleInsuranceInformationEt.text.toString()


                request.businessBankingDetails!!.bankName = binding.BankNameEt.text.toString()
                request.businessBankingDetails!!.branchName = binding.BranchNameEt.text.toString()
                request.businessBankingDetails!!.branchCode = binding.BranchCodeEt.text.toString()
                request.businessBankingDetails!!.swiftCode = binding.SwiftCodeEt.text.toString()
                request.businessBankingDetails!!.accountType = binding.AccountTypeEt.text.toString()
                request.businessBankingDetails!!.accountName = binding.AccountHolderEt.text.toString()
                request.businessBankingDetails!!.accountNumber = binding.AccountNumberEt.text.toString()


                request.filedEnity!!.businessName = binding.BusinessNameEt.text.toString()
                request.filedEnity!!.businessType = binding.BusinessTypeEt.selectedItem.toString()
                request.filedEnity!!.businessAddress = binding.BusinessAddressEt.text.toString()
                request.filedEnity!!.businessWebsite = binding.BusinessWebSiteEt.text.toString()
                request.filedEnity!!.businessEmail = binding.BusinessEmailEt.text.toString()
                request.filedEnity!!.einNum = binding.EINEt.text.toString()
                request.filedEnity!!.salesTaxId = binding.taxIdt.text.toString()
                request.filedEnity!!.monthlyOrderVolume = binding.OrderVolumeET.text.toString()




                if (isFrom == "Profile") {
                    serviceManager.updateProfileCommonBusinessFormApi(callBack, request)
                }else{
                    serviceManager.commonBusinessFormApi(callBack, request)
                }




            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            androidextention.alertBox("Please check your internet connection.", this)

        }
    }



    private fun viewDetailsApi() {
        if (androidextention.isOnline(this)) {

            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<RetailerFillDeatilsViewResponse> = ApiCallBack(object :
                    ApiResponseListener<RetailerFillDeatilsViewResponse> {
                    override fun onApiSuccess(
                        response: RetailerFillDeatilsViewResponse,
                        apiName: String?) {
                        if (response.responseCode == 200) {
                            try {

                                response.result.apply {
                                    binding.firstNameEt.setText(firstName)
                                    binding.lastNameEt.setText(lastName)
                                    binding.MobileNumbeEt.setText(mobileNumber)
                                    binding.EmailEt.setText(email)
                                    binding.DriverLicenseEt.setText(driverDetail!!.licenceNumber)
                                    binding.VehicleRegistrationNumberEt.setText(driverDetail!!.vehicleRegistrationNumber)
                                    binding.VehicleTypeEt.setText(driverDetail!!.vehicleType)
                                    binding.MakeAndModelEt.setText(driverDetail!!.vehicleModel)
                                    binding.VehicleYearEt.setText(driverDetail!!.vehicleYear)
                                    binding.VehicleColorEt.setText(driverDetail!!.vehicleColour)
                                    binding.VehicleInsuranceInformationEt.setText(driverDetail!!.insuranceNumber)
                                    binding.BankNameEt.setText(businessBankingDetails.bankName)
                                    binding.BranchNameEt.setText(businessBankingDetails.branchName)
                                    binding.BranchCodeEt.setText(businessBankingDetails.branchCode)
                                    binding.SwiftCodeEt.setText(businessBankingDetails.swiftCode)
                                    binding.AccountTypeEt.setText(businessBankingDetails.accountType)
                                    binding.AccountHolderEt.setText(businessBankingDetails.accountName)
                                    binding.AccountNumberEt.setText(businessBankingDetails.accountNumber)

                                    binding.BusinessNameEt.setText(fieldEntityDetails!!.businessName)



                                    binding.BusinessAddressEt.setText(fieldEntityDetails!!.businessAddress)
                                    binding.BusinessWebSiteEt.setText(fieldEntityDetails!!.businessWebsite)
                                    binding.BusinessEmailEt.setText(fieldEntityDetails!!.businessEmail)
                                    binding.EINEt.setText(fieldEntityDetails!!.einNum)
                                    binding.taxIdt.setText(fieldEntityDetails!!.salesTaxId)
                                    binding.OrderVolumeET.setText(fieldEntityDetails!!.monthlyOrderVolume)


                                    val adapter = ArrayAdapter.createFromResource(this@CommonBusinessFormActivity, R.array.business_type, android.R.layout.simple_spinner_item)
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                    binding.BusinessTypeEt.adapter = adapter

                                    val spinnerValues = resources.getStringArray(R.array.business_type)
                                    val valueToSet = fieldEntityDetails!!.businessType
                                    var index = -1

                                    for (i in spinnerValues.indices) {
                                        if (spinnerValues[i] == valueToSet) {
                                            index = i
                                            break
                                        }
                                    }

                                    if (index != -1) {
                                        binding.BusinessTypeEt.setSelection(index)
                                    }




                                }


                            }catch (e:Exception){
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {


                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {


                    }
                }, "FillDetailsView", this)
            try {
                serviceManager.FillDetailsView(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection.", this)

        }

    }

    override fun navigateToMap() { // Here We upload document when come this screen from profile then ask user to do upload document
        val intent = Intent(this@CommonBusinessFormActivity, UploadDocumentForServiceActivity::class.java)
        intent.putExtra("isFrom",isFrom)
        startActivity(intent)
        finish()
    }

    override fun navigateToHome() { // here logout the user if he clicks on later
        SavedPrefManager.saveStringPreferences(this, SavedPrefManager.isLogin, "false")
        SavedPrefManager.saveStringPreferences(this, SavedPrefManager.USER_NAME,"Guest")
        SavedPrefManager.saveStringPreferences(this, SavedPrefManager.USER_PROFILE,"")
        SavedPrefManager.deleteAllKeysServicePD(this)
        val intent = Intent(this, Services::class.java)
        startActivity(intent)
        finishAffinity()
    }


}