package com.exobe.fragments.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.activities.services.FillDetailsServiceProviderActivity
import com.exobe.R
import com.exobe.activities.fillDetails_retailer
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.MyProfileResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import de.hdodenhof.circleimageview.CircleImageView

class MyProfileActivity : Fragment(), ApiResponseListener<MyProfileResponse> {

    lateinit var backButtonMyProfile: ImageView
    lateinit var btnEditProfile: Button
    lateinit var mContext: Context

    lateinit var title: TextView
    lateinit var fullName: TextView
    lateinit var ContactNumber_TextView: TextView
    lateinit var PostCode_TextView: TextView
    lateinit var Address_tv1: TextView
    lateinit var Country: TextView
    lateinit var State: TextView
    lateinit var City: TextView

    //    lateinit var Address_tv2: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var service_back: ImageView
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var showprofile_image: CircleImageView
    lateinit var myprofile_progressbar: ProgressBar
    lateinit var editbusinessform: TextView
    lateinit var Address_tv2: TextView
    lateinit var internet_connection: RelativeLayout
    lateinit var addressLine1LL: LinearLayout

    lateinit var ll_my_profile: TextView

    var lottie: LottieAnimationView? = null
    var responseData = MyProfileResponse()
    var flag = ""

    companion object {
        var apiProfileCallFlag = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_my_profile, container, false)
        mContext = activity?.applicationContext!!




        Country = view.findViewById(R.id.Country)
        State = view.findViewById(R.id.State)
        City = view.findViewById(R.id.City)
        myprofile_progressbar = view.findViewById(R.id.myprofile_progressbar)
        Address_tv2 = view.findViewById(R.id.Address_tv2)
        btnEditProfile = view.findViewById(R.id.btnEditProfile)
        fullName = view.findViewById(R.id.fullName)
        ContactNumber_TextView = view.findViewById(R.id.ContactNumber_TextView)
        Address_tv1 = view.findViewById(R.id.Address_tv1)
        addressLine1LL = view.findViewById(R.id.addressLine1LL)
        PostCode_TextView = view.findViewById(R.id.PostCode_TextView)
        showprofile_image = view.findViewById(R.id.showprofile_image)
        editbusinessform = view.findViewById(R.id.editbusinessform)
        ll_my_profile = view.findViewById(R.id.ll_my_profile)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        if (requireArguments().getString("flag") != null) {
            flag = requireArguments().getString("flag").toString()

        }
        val userType = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.USER_TYPE)
        if (userType.equals("SERVICE_PROVIDER")) {

            title = activity?.findViewById(R.id.title)!!
            service_back = activity?.findViewById(R.id.back)!!
            title.text = "My profile"
            service_back.visibility = View.VISIBLE
            service_back.setSafeOnClickListener {
                parentFragmentManager.popBackStack()
            }


            btnEditProfile.setSafeOnClickListener {
                parentFragmentManager.beginTransaction().replace(
                    R.id.service_main_container,
                    EditProfileServiceProviderFragment(),
                    "profile_service_provider"
                ).addToBackStack("profile_service_provider").commit()
            }

            editbusinessform.setSafeOnClickListener {
                startActivity(Intent(requireContext(), FillDetailsServiceProviderActivity::class.java))

            }
        } else if (userType.equals("RETAILER")) {
            setToolbar()
            mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
            mainHeader.visibility = View.VISIBLE

            btnEditProfile.setSafeOnClickListener {
                startActivity(Intent(requireContext(), EditProfileActivity::class.java))
            }

            editbusinessform.setSafeOnClickListener {
                startActivity(Intent(requireContext(), fillDetails_retailer::class.java))

            }


            back.setSafeOnClickListener {
                parentFragmentManager.popBackStack()
            }

        } else if (userType.equals("CUSTOMER")) {
            setToolbar()
            editbusinessform.visibility = View.GONE
            mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
            mainHeader.visibility = View.VISIBLE

            btnEditProfile.setSafeOnClickListener {
                startActivity(Intent(requireContext(), EditProfileActivity::class.java))
            }

            back.setSafeOnClickListener {
                parentFragmentManager.popBackStack()
            }










        }

        return view
    }

    override fun onResume() {
        super.onResume()
        if (apiProfileCallFlag) {
            myprofileApi()
            apiProfileCallFlag = false
        } else {
            if (androidextention.isOnline(mContext)) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                setProfileData()
            } else {
                ll_my_profile.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
            }
        }
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
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
        cartCount.visibility = View.GONE
        cart.visibility = View.GONE
        filter.visibility = View.GONE
        back.visibility = View.VISIBLE

        MenuClick.visibility = View.GONE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        title.text = "My profile"
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }


    private fun myprofileApi() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            myprofile_progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<MyProfileResponse> = ApiCallBack(this, "myprofileApi", mContext)
            try {
                serviceManager.MyProfileApi(callBack)
            } catch (e: Exception) {

                e.printStackTrace()
            }
        } else {
            ll_my_profile.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }

    }

    override fun onApiSuccess(response: MyProfileResponse, apiName: String?) {
        myprofile_progressbar.visibility = View.GONE

        if (response.responseCode == 200) {
            try {
                responseData = response
                setProfileData()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setProfileData() {
        try {

            SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.CAMPAIGN_PREFERENCE, responseData.result.campainPrefrences)

            if (!responseData.result.profilePic.equals("")) {
                Glide.with(requireContext()).load(responseData.result.profilePic).placeholder(R.drawable.profile_placeholder_linearlayout).into(showprofile_image)
            }
            val name = "${responseData.result.firstName} ${responseData.result.lastName}"
            fullName.text = name.takeIf { it.isNotBlank() } ?: "NA"
            ContactNumber_TextView.text = responseData.result.mobileNumber.toString().takeIf { it.isNotBlank() } ?: "NA"
            PostCode_TextView.text = responseData.result.zipCode.toString().takeIf { it.isNotBlank() } ?: "NA"
            Address_tv1.text = responseData.result.addressLine1.takeIf { it!!.isNotBlank() } ?: "NA"
            if(responseData.result.addressLine2 == "" || responseData.result.addressLine2 == null) {
                addressLine1LL.isVisible = false
            } else {
                addressLine1LL.isVisible = true
                Address_tv2.text = responseData.result.addressLine2
            }
            Country.text = responseData.result.country
            State.text = responseData.result.state
            City.text = responseData.result.city



        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onApiErrorBody(response: String, apiName: String?) {
        myprofile_progressbar.visibility = View.GONE

    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        myprofile_progressbar.visibility = View.GONE

    }

}