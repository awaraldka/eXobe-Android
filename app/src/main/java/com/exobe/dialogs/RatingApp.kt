package com.exobe.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.Progresss
import com.exobe.androidextention
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.AddAppRatingResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

class RatingApp : DialogFragment() {


    lateinit var etRatrThisAppDescription:EditText
    lateinit var rateThisApp:RatingBar
    lateinit var tvRatrThisAppDescription:TextView

    var ratingValue = 0.0
    var descValue = ""
    lateinit var mContext: Context
    lateinit var internet_connection: RelativeLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.rate_this_app, container, false)
        val no_btn = view.findViewById<Button>(R.id.btnCancel)
        val yes_btn = view.findViewById<Button>(R.id.btnSave)

        rateThisApp = view.findViewById(R.id.rateThisApp)
        etRatrThisAppDescription = view.findViewById(R.id.etRatrThisAppDescription)
        tvRatrThisAppDescription = view.findViewById(R.id.tvRatrThisAppDescription)
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        internet_connection.visibility = View.GONE



        mContext = activity?.applicationContext!!
//        val bodyTitle = dialog.findViewById<TextView>(R.id.TextView1)
//        bodyTitle.text = title

        no_btn.setSafeOnClickListener {
            dismiss()
        }
        yes_btn.setSafeOnClickListener {

            ratingValue = rateThisApp.rating.toDouble()
            descValue = etRatrThisAppDescription.text.toString()
            if (ratingValue == 0.0){
                tvRatrThisAppDescription.text = "*Please provide a rating of 1 star or more. We appreciate your feedback."
            } else if(descValue.isEmpty()) {
                tvRatrThisAppDescription.text = "*Please enter description."
            }else{
                tvRatrThisAppDescription.text = ""
                addAppRatingApi(ratingValue, descValue)
            }

        }
        return view
    }
    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }


    fun addAppRatingApi(ratingValue : Double, descValue : String) {

        if (androidextention.isOnline(mContext)) {
            Progresss.start(requireContext())
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<AddAppRatingResponse> =
                ApiCallBack<AddAppRatingResponse>(object :
                    ApiResponseListener<AddAppRatingResponse> {


                    override fun onApiSuccess(response: AddAppRatingResponse, apiName: String?) {
                        androidextention.disMissProgressDialog(mContext)
                        if (response.responseCode == 200) {
                            Progresss.stop()
                            try {
                                dismiss()
                                tvRatrThisAppDescription.text = ""
                                Toast.makeText(mContext, "Successfully Rated", Toast.LENGTH_SHORT).show()
                                dialog?.dismiss()
                            }catch (e:Exception){
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
                            androidextention.alertBox(pojo.responseMessage, requireContext())

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()

                    }

                }, "addAppRatingApi", mContext)



            try {
                val jsonObject = JsonObject()
                jsonObject.addProperty("ratingCount", ratingValue)
                jsonObject.addProperty("suggestion", descValue)
                serviceManager.addAppRating_Api(callBack,jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            androidextention.alertBox("Please check your internet connection." , requireContext())

        }
    }
}

