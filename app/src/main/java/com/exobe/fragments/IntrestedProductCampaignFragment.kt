package com.exobe.fragments

import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.exobe.R
import com.exobe.adaptor.ImageSliderAdaptor
import com.exobe.androidextention
import com.exobe.bottomSheet.SelectSizeBottomSheet
import com.exobe.customClicks.SizeListener
import com.exobe.customClicks.ViewImages
import com.exobe.databinding.FragmentIntrestedProductCampainBinding
import com.exobe.dialogs.ImageShowDialog
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.CommonResponseForAll
import com.exobe.entity.response.DealDetails
import com.exobe.entity.response.PriceSizeDetails
import com.exobe.entity.response.customer.ViewResult
import com.exobe.entity.response.customer.WishListProductDetails
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.extension.diasplay_toast
import com.exobe.modelClass.response_modal_class
import com.exobe.utils.CommonFunctions
import com.exobe.utils.Progresss
import com.exobe.utils.SavedPrefManager
import com.google.gson.GsonBuilder


class IntrestedProductCampaignFragment : Fragment(), ViewImages, SizeListener {

    private var _binding: FragmentIntrestedProductCampainBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageAdaptor: ImageSliderAdaptor
    var productId2 = ""


    lateinit var MenuClick: LinearLayout
    lateinit var search: RelativeLayout
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var shareIcon: ImageView
    var priceSizeDetails = ArrayList<PriceSizeDetails>()
    private var DealDetails = ArrayList<DealDetails>()
    var responseData: ViewResult = ViewResult()

    var productSizeId = ""
    var unitRequest = ""
    var valueRequest = ""
    var participationIn = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentIntrestedProductCampainBinding.inflate(layoutInflater, container, false)
        setToolbar()
        arguments?.getString("productId")?.let { productId2= it }


        val data = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.CAMPAIGN_PREFERENCE)

        when(data){
            "MANUAL"->{
                binding.manualParticipation.isChecked = true
            }
            "SEMI_AUTOMATIC"->{
                binding.semiParticipation.isChecked = true
            }
            else->{
                binding.automaticParticipation.isChecked = true
            }
        }



        back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


        binding.sizeSpinner.setOnClickListener {
            if (priceSizeDetails.size > 1) {
                parentFragmentManager.let { it1 ->
                    SelectSizeBottomSheet(requireContext(), "", DealDetails, priceSizeDetails, this).show(it1, "ModalBottomSheet")
                }
            }
        }

        binding.decrement.setOnClickListener{
            var count = Integer.parseInt(binding.quantity.text.toString())

            if (binding.quantity.text.toString() > "1") {
                count--
                binding.quantity.text =  "$count"

            }
        }

        binding.increment.setOnClickListener{
            var count = Integer.parseInt(binding.quantity.text.toString())
            count++
            binding.quantity.text =  "$count"


        }


        binding.requestForInterest.setOnClickListener {
            if (binding.intrestedPriceET.text.isEmpty()){
                diasplay_toast("Please enter interested price")
                return@setOnClickListener
            }



            addInterestOnProductApi()
        }


        binding.intrestedPriceET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable) {
                val input = editable.toString()
                if (input == "0") {
                    binding.intrestedPriceET.setText("")
                }
            }
        })





        viewProductApi()
        return binding.root
    }

    private fun viewProductApi() {

        if (androidextention.isOnline(requireContext())) {
            Progresss.start(requireContext())
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<WishListProductDetails> =
                ApiCallBack(object :
                    ApiResponseListener<WishListProductDetails> {
                    override fun onApiSuccess(response: WishListProductDetails, apiName: String?) {
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            try {
                                responseData = response.result
                                priceSizeDetails = responseData.priceSizeDetails


                                binding.spinnerValueTv.text = "${priceSizeDetails.getOrNull(0)!!.value} ${priceSizeDetails.getOrNull(0)!!.unit.lowercase().takeIf { it != "other" }?:""}"
                                productSizeId =  priceSizeDetails.getOrNull(0)!!.id
                                unitRequest = priceSizeDetails.getOrNull(0)!!.unit
                                valueRequest = priceSizeDetails.getOrNull(0)!!.value

                                if (responseData.isDealActive) {
                                    binding.actualPrice.text = "R ${CommonFunctions.currencyFormatter(responseData.priceSizeDetails[0].price.toDouble())}"
                                    binding.PriceTag.text = "R ${CommonFunctions.currencyFormatter(responseData.dealPrice.toDouble())}  ${responseData.dealDiscount}% Off"
                                    binding.actualPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                                } else {
                                    binding.actualPrice.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                                    binding.actualPrice.text = "R ${CommonFunctions.currencyFormatter(responseData.priceSizeDetails[0].price.toDouble())}"
                                }
                                setProductData(response.result.productImage!!)
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

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, "viewProductApi", requireContext())


            try {
                 serviceManager.viewProductDetails(callBack, productId2)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



    fun setProductData(productImage: ArrayList<String>) {
        imageAdaptor = ImageSliderAdaptor(productImage, requireContext(), "viewDeals", this)
        binding.multiImage.adapter = imageAdaptor
        if (productImage.size > 1) {
            binding.indicator.setViewPager(binding.multiImage)
        }

    }

    override fun viewImage(imageList: ArrayList<String>) {
        ImageShowDialog(imageList).show(parentFragmentManager, "ShowImage")
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
        shareIcon = activity?.findViewById(R.id.shareIcon)!!
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
        cartCount.visibility = View.GONE
        cart.visibility = View.GONE
        filter.visibility = View.GONE
        back.visibility = View.VISIBLE

        MenuClick.visibility = View.GONE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        shareIcon.visibility = View.GONE

        title.text = "Product Details"

    }

    override fun getSize(
        name: String,
        id: String,
        price: Number,
        unit: String,
        value: String,
        dealPrice: Number?,
        quantity: String?
    ) {
        productSizeId =  id
        unitRequest =  unit
        valueRequest =  value

        if (responseData.isDealActive) {
            binding.actualPrice.text = "R $price"
            val result = CommonFunctions.subtractPercentage(price.toDouble(), responseData.dealDiscount.toDouble())
            binding.PriceTag.text = "R $result  ${responseData.dealDiscount}% Off"
            binding.actualPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            binding.actualPrice.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.actualPrice.text = "R ${CommonFunctions.currencyFormatter(price.toDouble())}"
        }

        binding.spinnerValueTv.text = "$name ${unit.lowercase().takeIf { it != "other" }?:""}"

    }


    private fun addInterestOnProductApi() {

        if (androidextention.isOnline(requireContext())) {
            Progresss.start(requireContext())
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<CommonResponseForAll> =
                ApiCallBack(object :
                    ApiResponseListener<CommonResponseForAll> {
                    override fun onApiSuccess(response: CommonResponseForAll, apiName: String?) {
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            try {
                                androidextention.alertBoxFragmentBack(response.responseMessage,requireContext(),parentFragmentManager)

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

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, "addInterestOnProduct", requireContext())


            try {
                participationIn =  if (binding.automaticParticipation.isChecked){
                    "AUTOMATIC"
                }else if (binding.semiParticipation.isChecked){
                    "SEMI_AUTOMATIC"
                }else{
                    "MANUAL"
                }
                serviceManager.addInterestPriceOnProduct(callBack = callBack,
                    interestedPrice = binding.intrestedPriceET.text.toString(),
                    quantity = binding.quantity.text.toString(),
                    productId = productId2,
                    productSizeId = productSizeId,
                    unit = unitRequest,
                    value = valueRequest,
                    spordicType = participationIn
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }





















}