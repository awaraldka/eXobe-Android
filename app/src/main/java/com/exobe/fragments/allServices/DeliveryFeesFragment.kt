package com.exobe.fragments.allServices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.exobe.adaptor.DeliveryFeesAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.Progresss
import com.exobe.androidextention
import com.exobe.customClicks.ClickForDeliveryFees
import com.exobe.databinding.FragmentDeliveryFeesBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.DeliveryFessCustomerResponse
import com.exobe.entity.response.DeliveryFessCustomerResult
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder

class DeliveryFeesFragment : Fragment(), ClickForDeliveryFees {

    private var _binding: FragmentDeliveryFeesBinding? =  null
    private val binding get() = _binding!!

    var deliveryAmounts = ""
    var deliveryTypeRequest = ""
    var deliveryType = ""

    var result : List<DeliveryFessCustomerResult> = listOf()


    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var mainHeader: RelativeLayout
    lateinit var progressbar: ProgressBar
    lateinit var MenuClick: LinearLayout
    lateinit var title: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeliveryFeesBinding.inflate(layoutInflater, container, false)
        setToolbar()
        back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        arguments?.getString("deliveryType")?.let{ deliveryType = it }

        servicesListApi()


        binding.continueButton.setOnClickListener {
            sendDataToPreviousFragment()
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }



    private fun servicesListApi() {
        if (androidextention.isOnline(requireContext())) {
            binding.continueButton.isVisible = false
            Progresss.start(requireContext())
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<DeliveryFessCustomerResponse> =
                ApiCallBack(object :
                    ApiResponseListener<DeliveryFessCustomerResponse> {

                    override fun onApiSuccess(
                        response: DeliveryFessCustomerResponse,
                        apiName: String?
                    ) {
                        if (response.responseCode == 200) {
                            try {
                                Progresss.stop()
                                binding.continueButton.isVisible = response.result.isNotEmpty()
                                result = response.result
                                setAdapterDeliveryFees()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        Progresss.stop()
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()
                        binding.continueButton.isVisible = false
                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
//                            androidextention.alertBox(pojo.responseMessage, requireContext())

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                        binding.continueButton.isVisible = false
                    }

                }, "serviceListApi", requireContext())
            try {
                serviceManager.deliveryFeeListOption(callBack)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun setAdapterDeliveryFees() {
        binding.listAllDeliveryFees.layoutManager = LinearLayoutManager(requireContext())
        val adapterServiceList = DeliveryFeesAdapter(context = requireContext(), result =  result, click = this, deliveryTypeCheck = deliveryType)
        binding.listAllDeliveryFees.adapter = adapterServiceList
    }


    fun setToolbar() {
        title = activity?.findViewById(R.id.PreLoginTitle_TextView2)!!


        cart = activity?.findViewById(R.id.cart_icon)!!
        filter = activity?.findViewById(R.id.filter_icon)!!
        MenuClick = activity?.findViewById(R.id.MenuClick)!!
        back = activity?.findViewById(R.id.imageView_back)!!
        DealsImageView = activity?.findViewById(R.id.Deals_ImageView)!!
        greyBellImageView = activity?.findViewById(R.id.greyBell_ImageView)!!
        logoutButton = activity?.findViewById(R.id.logoutButton)!!
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
        cartCount.visibility = View.GONE
        cart.visibility = View.GONE
        filter.visibility = View.GONE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        MenuClick.visibility = View.GONE
        back.visibility = View.VISIBLE
        title.text = "Choose Delivery Options"
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    override fun deliveryFees(deliveryAmount: String, deliveryType: String) {
        deliveryAmounts = deliveryAmount
        deliveryTypeRequest = deliveryType

        val filterData = result.filter { it.isSelected }

        binding.continueButton.isVisible =  filterData.isNotEmpty()
    }


    private fun sendDataToPreviousFragment() {
        parentFragmentManager.setFragmentResult("requestKey", Bundle().apply {
            putString("deliveryAmount", deliveryAmounts)
            putString("deliveryTypeRequest", deliveryTypeRequest)
            putBoolean("isDeliveryAmountChanged",true)
        })
    }


}