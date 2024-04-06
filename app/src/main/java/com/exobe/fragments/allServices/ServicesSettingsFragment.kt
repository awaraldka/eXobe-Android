package com.exobe.fragments.allServices

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.activities.*
import com.exobe.fragments.LegalFragment
import com.exobe.R
import com.exobe.activities.retailer.AddedCampaignActivity
import com.exobe.utils.CommonFunctions
import com.exobe.androidextention
import com.exobe.customClicks.DeleteAccount
import com.exobe.dialogs.RatingApp
import com.exobe.fragments.profile.MyProfileActivity


class ServicesSettingsFragment : Fragment(), DeleteAccount {

    lateinit var llTerms: LinearLayout
    lateinit var llPrivacy: LinearLayout
    lateinit var llFaq: LinearLayout
    lateinit var llLegal: LinearLayout
    lateinit var llAbout: LinearLayout
    lateinit var Logout: LinearLayout
    lateinit var Reset_PasswordllTerms: LinearLayout
    lateinit var llMyProfile: LinearLayout
    lateinit var Feedback: LinearLayout
    lateinit var llSwitchRole: LinearLayout
    var title: TextView? = null
    var back: ImageView? = null
     var MenuClick : ImageView? = null
    lateinit var edit_deal: LinearLayout
    var url = "https://www.freeprivacypolicy.com/blog/privacy-policy-url/"


    //added tab handle
    lateinit var home: ImageView
    lateinit var homeRed: ImageView
    lateinit var homeText: TextView

    lateinit var service_red_sp:ImageView
    lateinit var service_sp:ImageView
    lateinit var TVservuce_sp:TextView


    lateinit var settings: ImageView
    lateinit var settingsRed: ImageView
    lateinit var settingsText: TextView
    var lottie: LottieAnimationView? = null
    lateinit var internet_connection: RelativeLayout

    lateinit var DeleteAccount: LinearLayout
    private lateinit var llCampaignAdded: LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_services_settings, container, false)

        handleTab()

        llTerms = view.findViewById(R.id.llTerms)
        DeleteAccount = view.findViewById(R.id.DeleteAccount)
        llPrivacy = view.findViewById(R.id.llPrivacy)
        llFaq = view.findViewById(R.id.llFaq)
        Logout = view.findViewById(R.id.Logout)
        llAbout = view.findViewById(R.id.llAbout)
        Reset_PasswordllTerms = view.findViewById(R.id.Reset_PasswordllTerms)
        llMyProfile = view.findViewById(R.id.llMyProfile)
        Feedback = view.findViewById(R.id.Feedback)
        llLegal = view.findViewById(R.id.llLegal)
        llSwitchRole = view.findViewById(R.id.llSwitchRole)
        title = activity?.findViewById(R.id.title)
        back = activity?.findViewById(R.id.back)
        MenuClick = activity?.findViewById(R.id.MenuClick)!!
        lottie = activity?.findViewById(R.id.lottie)!!
        edit_deal = activity?.findViewById(R.id.edit_deal)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!

        llCampaignAdded = view.findViewById(R.id.llCampaignAdded)
        
        lottie!!.visibility = View.GONE
        internet_connection!!.visibility = View.GONE

        MenuClick!!.visibility = View.GONE
        edit_deal.visibility = View.GONE

        title!!.text = "Settings"
        
        back!!.visibility = View.GONE




        llCampaignAdded.setSafeOnClickListener {
            val intent  = Intent(requireContext(),AddedCampaignActivity::class.java)
            intent.putExtra("isUser", "Service")
            startActivity(intent)
        }
        
        
        
        llTerms.setSafeOnClickListener {
            val bundle = Bundle()
            bundle.putString("flag", "services")
            val fragObj = TermsAndConditionFragment()
            fragObj.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.service_main_container,
                fragObj,"Terms_and_condition").addToBackStack(null).commit()

        }

        llLegal.setSafeOnClickListener {
            val bundle = Bundle()
            bundle.putString("flag", "services")
            val fragObj = LegalFragment()
            fragObj.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.service_main_container,
                fragObj,"legal").addToBackStack(null).commit()

        }

        llMyProfile.setSafeOnClickListener {

            MyProfileActivity.apiProfileCallFlag = true
            val bundle = Bundle()
            bundle.putString("flag", "services")
            val fragObj = MyProfileActivity()
            fragObj.arguments = bundle

            parentFragmentManager.beginTransaction().replace(R.id.service_main_container,
                fragObj,"MyProfileActivity").addToBackStack(null).commit()
        }

        llPrivacy.setSafeOnClickListener {
            val bundle = Bundle()
            bundle.putString("flag", "services")
            val fragObj = PrivacyPolicyFragment()
            fragObj.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.service_main_container, fragObj,
                "Privacy_Policy").addToBackStack(null).commit()

        }
        Reset_PasswordllTerms.setSafeOnClickListener {
            val bundle = Bundle()
            bundle.putString("flag", "services")
            val fragObj = ChangePassword()
            fragObj.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.service_main_container,
                fragObj,"ChangePassword").addToBackStack(null).commit()
        }

        llFaq.setSafeOnClickListener {
            val bundle = Bundle()
            bundle.putString("flag", "services")
            val fragObj = FAQs()
            fragObj.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.service_main_container,
                fragObj,"FAQs").addToBackStack(null)?.commit()
        }

        llAbout.setSafeOnClickListener {
            val bundle = Bundle()
            bundle.putString("flag", "services")
            val fragObj = AboutUsFragment()
            fragObj.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.service_main_container,
                fragObj,"aboutUs").addToBackStack(null).commit()
        }

        Feedback.setSafeOnClickListener {

            RatingApp().show(childFragmentManager, "MyCustomFragment")
        }
        Logout.setSafeOnClickListener {
            androidextention.logOutDialog(requireContext())


        }

        DeleteAccount.setSafeOnClickListener {
            androidextention.deleteAccount(requireContext(),this)

        }


        llSwitchRole.setSafeOnClickListener {
            androidextention.switchRoleDialog(requireContext())
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

    fun handleTab(){

        homeRed = activity?.findViewById(R.id.home_grey_sp)!!
        home = activity?.findViewById(R.id.home_sp)!!
        homeText = activity?.findViewById(R.id.TVhome_sp)!!

        settings = activity?.findViewById(R.id.setting_grey_sp)!!
        settingsRed = activity?.findViewById(R.id.setting_red_sp)!!
        settingsText = activity?.findViewById(R.id.TVsetting_sp)!!

        service_red_sp = activity?.findViewById(R.id.service_red_sp)!!
        service_sp = activity?.findViewById(R.id.service_sp)!!
        TVservuce_sp = activity?.findViewById(R.id.TVservuce_sp)!!

        home.visibility = View.VISIBLE
        homeRed.visibility = View.GONE
        homeText.setTextColor(Color.parseColor("#FFFFFF"))

        service_sp.visibility = View.VISIBLE
        service_red_sp.visibility = View.GONE
        TVservuce_sp.setTextColor(Color.parseColor("#FFFFFF"))

        settingsRed.visibility = View.VISIBLE
        settings.visibility = View.GONE
        settingsText.setTextColor(Color.parseColor("#FFFFFF"))

    }

    override fun deleteAccount() {
        CommonFunctions.deleteAccountApi(requireContext())
    }

}