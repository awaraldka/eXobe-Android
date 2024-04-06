package com.exobe.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.*
import com.exobe.activities.*
import com.exobe.activities.customer.CampaignListActivity
import com.exobe.activities.retailer.AddedCampaignActivity
import com.exobe.utils.CommonFunctions
import com.exobe.utils.SavedPrefManager
import com.exobe.utils.TabHandler
import com.exobe.bottomSheet.CustomerBottomSheet
import com.exobe.customClicks.DeleteAccount
import com.exobe.customClicks.UpdateIsLoginListener
import com.exobe.dialogs.RatingApp
import com.exobe.fragments.address.ChooseDeliveryAddress
import com.exobe.fragments.orderHistory.OrderHistoryFragment
import com.exobe.fragments.profile.MyProfileActivity


class SettingsFragment : Fragment(), UpdateIsLoginListener,DeleteAccount {

    private lateinit var llTerms: LinearLayout
    private lateinit var llPrivacy: LinearLayout
    private lateinit var llFaq: LinearLayout
    private lateinit var llAbout: LinearLayout
    private lateinit var resetPasswordLLTerms: LinearLayout
    private lateinit var llProductEnquiry: LinearLayout
    private lateinit var llOrderHistory: LinearLayout
    private lateinit var llMyProfile: LinearLayout
    private lateinit var llLegal: LinearLayout
    private lateinit var feedback: LinearLayout
    private lateinit var mContext: Context
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    private lateinit var dealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    private lateinit var menuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    private lateinit var llAddress: LinearLayout
    private lateinit var llCampaign: LinearLayout
    private lateinit var llSwitchRole: LinearLayout
    private lateinit var deleteAccount: LinearLayout
    private lateinit var switchRoleView: View
    private var isLoggedIn = false
    var userType = ""
    private var customerType = ""

    var url = "https://www.freeprivacypolicy.com/blog/privacy-policy-url/"

    var lottie: LottieAnimationView? = null
    private lateinit var internetConnection: RelativeLayout
    private lateinit var llCampaignAdded: LinearLayout
    private lateinit var viewCampaignAdded: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        mContext = activity?.applicationContext!!
        TabHandler.HandleTab(R.id.Setting_LinearLayout, requireActivity())
        setToolbar()
        llTerms = view.findViewById(R.id.llTerms)
        llCampaign = view.findViewById(R.id.llCampaign)
        llPrivacy = view.findViewById(R.id.llPrivacy)
        llFaq = view.findViewById(R.id.llFaq)
        llAbout = view.findViewById(R.id.llAbout)
        resetPasswordLLTerms = view.findViewById(R.id.Reset_PasswordllTerms)
        llProductEnquiry = view.findViewById(R.id.llProductEnquiry)
        llOrderHistory = view.findViewById(R.id.llOrderHistroy)
        llMyProfile = view.findViewById(R.id.llMyProfile)
        feedback = view.findViewById(R.id.Feedback)
        llLegal = view.findViewById(R.id.llLegal)
        switchRoleView = view.findViewById(R.id.switchRoleView)
        llAddress = view.findViewById(R.id.llAddress)
        deleteAccount = view.findViewById(R.id.DeleteAccount)
        llSwitchRole = view.findViewById(R.id.llSwitchRole)
        viewCampaignAdded = view.findViewById(R.id.viewCampaignAdded)
        llCampaignAdded = view.findViewById(R.id.llCampaignAdded)


        lottie = activity?.findViewById(R.id.lottie)!!
        internetConnection = activity?.findViewById(R.id.internet_connection)!!
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!


        mainHeader.visibility = View.VISIBLE
        lottie!!.visibility = View.GONE
        internetConnection.visibility = View.GONE
        isLoggedIn = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true"
        userType = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.USER_TYPE).toString()
        customerType = if (userType == "CUSTOMER") "Customer" else "Retailer"


        if (customerType == "Customer"){
            llCampaignAdded.isVisible = false
            viewCampaignAdded.isVisible = false
        }else{
            llCampaignAdded.isVisible = true
            viewCampaignAdded.isVisible = true
        }

        llTerms.setSafeOnClickListener {
            val bundle = Bundle()
            bundle.putString("flag", "")
            val fragObj = TermsAndConditionFragment()
            fragObj.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragObj, "Terms_and_condition").addToBackStack(null).commit()

        }

        llCampaignAdded.setSafeOnClickListener {
            val intent  = Intent(requireContext(),AddedCampaignActivity::class.java)
            intent.putExtra("isUser", "")
            startActivity(intent)
        }

        llLegal.setSafeOnClickListener {
            val bundle = Bundle()
            bundle.putString("flag", "")
            val fragObj = LegalFragment()
            fragObj.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragObj, "legal").addToBackStack(null).commit()

        }

        llMyProfile.setSafeOnClickListener {
            if (isLoggedIn) {
                MyProfileActivity.apiProfileCallFlag = true
                val bundle = Bundle().apply {
                    putString("flag", "")
                }
                val fragObj = MyProfileActivity().apply {
                    arguments = bundle
                }
                parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragObj, "MyProfileActivity").addToBackStack(null).commit()
            } else {
                parentFragmentManager.let { it1 -> CustomerBottomSheet(customerType, requireContext(),this).show(it1, "ModalBottomSheet")
                }
            }
        }


        llProductEnquiry.setSafeOnClickListener {
            if (isLoggedIn) {
                parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, ProductEnquiry("SideMenu"), "ProductEnquiry").addToBackStack(null).commit()
            } else {
                parentFragmentManager.let { it1 ->
                    CustomerBottomSheet(customerType, requireContext(),this).show(it1, "ModalBottomSheet")
                }

            }
        }

        llOrderHistory.setSafeOnClickListener {
            if (isLoggedIn ) {

                parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, OrderHistoryFragment(""), "OrderHistory").addToBackStack(null).commit()
            } else {
                parentFragmentManager.let { it1 ->
                    CustomerBottomSheet(customerType, requireContext(),this).show(it1, "ModalBottomSheet")
                }

            }
        }

        llPrivacy.setSafeOnClickListener {

            val bundle = Bundle()
            bundle.putString("flag", "")
            val fragObj = PrivacyPolicyFragment()
            fragObj.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragObj, "Privacy_Policy").addToBackStack(null).commit()

        }
        resetPasswordLLTerms.setSafeOnClickListener {
            if (isLoggedIn) {

                val bundle = Bundle()
                bundle.putString("flag", "")
                val fragObj = ChangePassword()
                fragObj.arguments = bundle
                parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragObj, "ChangePassword").addToBackStack(null).commit()
            } else {
                parentFragmentManager.let { it1 ->
                    CustomerBottomSheet(customerType, requireContext(),this).show(it1, "ModalBottomSheet")
                }

            }

        }

        llFaq.setSafeOnClickListener {

            val bundle = Bundle()
            bundle.putString("flag", "")
            val fragObj = FAQs()
            fragObj.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragObj, "FAQs").addToBackStack(null).commit()
        }

        llAbout.setSafeOnClickListener {

            val bundle = Bundle()
            bundle.putString("flag", "")
            val fragObj = AboutUsFragment()
            fragObj.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragObj, "aboutUs").addToBackStack(null).commit()
        }

        feedback.setSafeOnClickListener {
            if (isLoggedIn ) {
                RatingApp().show(childFragmentManager, "MyCustomFragment")
            } else {
                parentFragmentManager.let { it1 ->
                    CustomerBottomSheet(customerType, requireContext(),this).show(it1, "ModalBottomSheet")
                }

            }
        }


        llAddress.setSafeOnClickListener {
            if (isLoggedIn) {
                ChooseDeliveryAddress.apiDeliveryAddressCallFlag = true
                val bundle = Bundle()
                bundle.putString("flag", "setting_address")
                val fragObj = ChooseDeliveryAddress()
                fragObj.arguments = bundle
                parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragObj, "ChooseDeliveryAddress").addToBackStack(null).commit()
            } else {
                parentFragmentManager.let { it1 -> CustomerBottomSheet(customerType, requireContext(),this).show(it1, "ModalBottomSheet")
                }

            }

        }


        llCampaign.setSafeOnClickListener {
            if (isLoggedIn) {
               startActivity(Intent(requireContext(), CampaignListActivity::class.java))
            } else {
                parentFragmentManager.let { it1 -> CustomerBottomSheet(customerType, requireContext(),this).show(it1, "ModalBottomSheet")
                }

            }
        }


        llSwitchRole.setSafeOnClickListener {
            if (isLoggedIn) {
                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.isLogin, "false")
                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.USER_NAME,"Guest")
                SavedPrefManager.saveStringPreferences(requireContext(), SavedPrefManager.USER_PROFILE,"")
                androidextention.switchRoleDialog(requireContext())
            }else{
                SavedPrefManager.saveStringPreferences(context, SavedPrefManager.isLogin, "false")
                SavedPrefManager.saveStringPreferences(context, SavedPrefManager.USER_NAME,"Guest")
                SavedPrefManager.saveStringPreferences(context, SavedPrefManager.USER_PROFILE,"")
                SavedPrefManager.deleteAllKeysServicePD(context)
                val intent = Intent(context, Services::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

        }

        deleteAccount.setSafeOnClickListener {
            if (isLoggedIn ) {
                androidextention.deleteAccount(requireContext(),this)
            } else {
                parentFragmentManager.let { it1 ->
                    CustomerBottomSheet(customerType, requireContext(),this).show(it1, "ModalBottomSheet")
                }

            }
        }


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                val fm: FragmentManager = requireActivity().supportFragmentManager

                for (i in 0 until fm.backStackEntryCount){
                    fm.popBackStack()
                }


            }
        })

    }

    override fun isLoginListener() {
        val name = activity?.findViewById<TextView>(R.id.name)
        val userProfile = activity?.findViewById<ImageView>(R.id.Profile_picture)
        isLoggedIn = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true"
        CommonFunctions.getProfileApiApi(requireContext(),name,userProfile)


    }

    fun setToolbar() {
        title = activity?.findViewById(R.id.PreLoginTitle_TextView2)!!


        cart = activity?.findViewById(R.id.cart_icon)!!
        filter = activity?.findViewById(R.id.filter_icon)!!
        back = activity?.findViewById(R.id.imageView_back)!!
        menuClick = activity?.findViewById(R.id.MenuClick)!!
        dealsImageView = activity?.findViewById(R.id.Deals_ImageView)!!
        greyBellImageView = activity?.findViewById(R.id.greyBell_ImageView)!!
        logoutButton = activity?.findViewById(R.id.logoutButton)!!
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
        logoutButton.visibility = View.VISIBLE
        cart.visibility = View.VISIBLE
    }

    override fun deleteAccount() {
        CommonFunctions.deleteAccountApi(requireContext())
    }
}