package com.exobe.activities

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.callisdairy.extension.setSafeOnClickListener
import com.example.validationpractice.utlis.FormValidation
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.Progresss
import com.exobe.androidextention
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.changepasswordresponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

class ChangePassword : Fragment(), ApiResponseListener<changepasswordresponse> {

    lateinit var cancel_button: Button
    lateinit var save_button: Button
    lateinit var ivgroup: TextView
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var cartCount: TextView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var service_back:ImageView
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var OldPasswordET: EditText
    lateinit var PasswordET: EditText
    lateinit var ConfirmPasswordET: EditText
    lateinit var ConfirmPasswordTv: TextView
    lateinit var OldPasswordTV: TextView
    lateinit var NewPasswordTV: TextView
    lateinit var OldPassword: LinearLayout
    lateinit var PasswordLL: LinearLayout
    lateinit var ConfirmPasswordLL: LinearLayout
    var flag = ""
    lateinit var oldcross_eye: ImageView
    lateinit var cross_eye: ImageView
    lateinit var cross_eye2: ImageView
    var oldpassword = ""
    var newpassword = ""
    var confirmpassword = ""
    lateinit var mContext: Context
    private var passwordNotVisible = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.activity_change_password, container, false)
        cancel_button = view.findViewById(R.id.cancel_button)
        save_button = view.findViewById(R.id.save_button)
        OldPasswordET = view.findViewById(R.id.OldPasswordET)
        PasswordET = view.findViewById(R.id.PasswordET)
        OldPasswordTV = view.findViewById(R.id.OldPasswordTV)
        NewPasswordTV = view.findViewById(R.id.NewPasswordTV)
        ConfirmPasswordET = view.findViewById(R.id.ConfirmPasswordET)
        ConfirmPasswordTv = view.findViewById(R.id.ConfirmPasswordTv)
        OldPassword = view.findViewById(R.id.OldPassword)
        PasswordLL = view.findViewById(R.id.PasswordLL)
        ConfirmPasswordLL = view.findViewById(R.id.ConfirmPasswordLL)
        oldcross_eye = view.findViewById(R.id.oldcross_eye)
        cross_eye = view.findViewById(R.id.cross_eye)
        cross_eye2 = view.findViewById(R.id.cross_eye2)
        mContext = activity?.applicationContext!!

        if (requireArguments().getString("flag") != null) {
            flag = requireArguments().getString("flag").toString()

        }
        if (flag.equals("services")) {

            title = activity?.findViewById(R.id.title)!!
            service_back = activity?.findViewById(R.id.back)!!
            title!!.setText("Change Password")
            service_back!!.visibility = View.VISIBLE
            service_back!!.setSafeOnClickListener {
                fragmentManager?.popBackStack()
            }

        } else {
            setToolbar()
            mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
            mainHeader.visibility = View.VISIBLE

            back.setSafeOnClickListener {
                fragmentManager?.popBackStack()
            }
        }

        cancel_button.setSafeOnClickListener {
            fragmentManager?.popBackStack()
        }
        save_button.setSafeOnClickListener {

            FormValidation.ChangePassword(
                OldPasswordET,
                OldPasswordTV,
                OldPassword,
                PasswordET,
                NewPasswordTV,
                PasswordLL,
                ConfirmPasswordET,
                ConfirmPasswordTv,
                ConfirmPasswordLL
            )
            if (OldPasswordET.text.isNotEmpty() && PasswordET.text.isNotEmpty() && ConfirmPasswordET.text.isNotEmpty()) {
                oldpassword = OldPasswordET.text.toString()
                newpassword = PasswordET.text.toString()
                confirmpassword = ConfirmPasswordET.text.toString()
                changepassword(oldpassword, newpassword, confirmpassword)
            }

        }

        passwordShow()
        initializedControl()


        return view
    }

    fun setToolbar() {
        title = activity?.findViewById(R.id.PreLoginTitle_TextView2)!!


        cart = activity?.findViewById(R.id.cart_icon)!!
        filter = activity?.findViewById(R.id.filter_icon)!!
        back = activity?.findViewById(R.id.imageView_back)!!
        MenuClick = activity?.findViewById(R.id.MenuClick)!!
        DealsImageView = activity?.findViewById(R.id.Deals_ImageView)!!
        greyBellImageView = activity?.findViewById(R.id.greyBell_ImageView)!!
        logoutButton = activity?.findViewById(R.id.logoutButton)!!
        cartCount = activity?.findViewById(R.id.cartCount)!!

        cartCount.visibility = View.GONE
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
cartCount.visibility = View.GONE
cart.visibility = View.GONE
        filter.visibility = View.GONE
        back.visibility = View.VISIBLE

        MenuClick.visibility = View.GONE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        title.setText("Change Password")
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }


    private fun passwordShow() {
        oldcross_eye.setSafeOnClickListener {
            if (passwordNotVisible == 0) {
                OldPasswordET.transformationMethod = HideReturnsTransformationMethod.getInstance()
                oldcross_eye.setImageResource(R.drawable.eye)
                OldPasswordET.setSelection(OldPasswordET.length())
                passwordNotVisible = 1


            } else if (passwordNotVisible == 1) {
                OldPasswordET.transformationMethod = PasswordTransformationMethod.getInstance()
                oldcross_eye.setImageResource(R.drawable.ic_eye_slash_solid)
                OldPasswordET.setSelection(OldPasswordET.length())
                passwordNotVisible = 0
            } else {
                OldPasswordET.transformationMethod = HideReturnsTransformationMethod.getInstance()
                oldcross_eye.setImageResource(R.drawable.eye)
                OldPasswordET.setSelection(OldPasswordET.length())
                passwordNotVisible = 1
            }
        }
        cross_eye.setSafeOnClickListener {
            if (passwordNotVisible == 0) {
                PasswordET.transformationMethod = HideReturnsTransformationMethod.getInstance()
                cross_eye.setImageResource(R.drawable.eye)
                PasswordET.setSelection(PasswordET.length())
                passwordNotVisible = 1


            } else if (passwordNotVisible == 1) {
                PasswordET.transformationMethod = PasswordTransformationMethod.getInstance()
                cross_eye.setImageResource(R.drawable.ic_eye_slash_solid)
                PasswordET.setSelection(PasswordET.length())
                passwordNotVisible = 0
            } else {
                PasswordET.transformationMethod = HideReturnsTransformationMethod.getInstance()
                cross_eye.setImageResource(R.drawable.eye)
                PasswordET.setSelection(PasswordET.length())
                passwordNotVisible = 1
            }
        }
        cross_eye2.setSafeOnClickListener {
            if (passwordNotVisible == 0) {
                ConfirmPasswordET.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                cross_eye2.setImageResource(R.drawable.eye)
                ConfirmPasswordET.setSelection(ConfirmPasswordET.length())
                passwordNotVisible = 1


            } else if (passwordNotVisible == 1) {
                ConfirmPasswordET.transformationMethod = PasswordTransformationMethod.getInstance()
                cross_eye2.setImageResource(R.drawable.ic_eye_slash_solid)
                ConfirmPasswordET.setSelection(ConfirmPasswordET.length())
                passwordNotVisible = 0
            } else {
                ConfirmPasswordET.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                cross_eye2.setImageResource(R.drawable.eye)
                ConfirmPasswordET.setSelection(ConfirmPasswordET.length())
                passwordNotVisible = 1
            }
        }
    }

    fun changepassword(password: String, newpassword: String, confirmpassword: String) {

        if (androidextention.isOnline(mContext)) {
            Progresss.start(requireContext())
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<changepasswordresponse> =
                ApiCallBack<changepasswordresponse>(this, "changepassword", mContext)

            var jsonObject = JsonObject()
            jsonObject.addProperty("password", password)
            jsonObject.addProperty("newPassword", newpassword)
            jsonObject.addProperty("confirmPassword", confirmpassword)


            try {
                serviceManager.changepasswordApi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection." , requireContext())

        }
    }

    override fun onApiSuccess(response: changepasswordresponse, apiName: String?) {
        Progresss.stop()
        if (response.responseCode == 200) {
            try {

                fragmentManager?.popBackStack()
                androidextention.alertBox("Password changed", requireContext())
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
            androidextention.alertBox(pojo.responseMessage, requireContext())

        } catch (e: Exception) {
            // handle failure at error parse
        }
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Progresss.stop()
    }
  fun initializedControl(){
      OldPasswordET.addTextChangedListener(textWatcher)
      PasswordET.addTextChangedListener(textWatcher)
      ConfirmPasswordET.addTextChangedListener(textWatcher)

  }
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            FormValidation.ChangePassword(
                OldPasswordET,
                OldPasswordTV,
                OldPassword,
                PasswordET,
                NewPasswordTV,
                PasswordLL,
                ConfirmPasswordET,
                ConfirmPasswordTv,
                ConfirmPasswordLL
            )
        }
    }


}