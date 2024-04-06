package com.exobe.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.dealviewimage_adaptor
import com.exobe.fragments.cart.MyCartFragment
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.bottomSheet.CustomerBottomSheet
import com.exobe.bottomSheet.SelectSizeBottomSheet
import com.exobe.customClicks.CustomeClick4
import com.exobe.customClicks.SizeListener
import com.exobe.customClicks.UpdateIsLoginListener
import com.exobe.dialogs.ImageShowDialog
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.AddToCartRequest
import com.exobe.entity.request.PriceSizeDetailsRequest
import com.exobe.entity.response.*
import com.exobe.entity.response.customer.AddToCart
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.util.DateFormat
import com.google.gson.GsonBuilder
import me.relex.circleindicator.CircleIndicator3

class ViewAddDeals : Fragment(), CustomeClick4, ApiResponseListener<viewdeals_response>,
    SizeListener, UpdateIsLoginListener {

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
    lateinit var offerValue1: TextView
    lateinit var dealView: RelativeLayout

    //    lateinit var Back: ImageView
//    lateinit var share: ImageView
    var images = ArrayList<viewdealsResult>()
    lateinit var viewAddDealsSubmit: Button
    lateinit var disable_AddToCart_BTN: Button

    lateinit var multi_image: ViewPager2
    lateinit var imageAdaptor: dealviewimage_adaptor
    lateinit var priceTag: TextView
    lateinit var deal_productname: TextView
    lateinit var viewAddDealsOfferValue1: TextView
    lateinit var price: TextView
    lateinit var tvDealID: TextView
    lateinit var tvDescription: TextView
    lateinit var noData_tv: TextView
    lateinit var dealTime: TextView

    //    lateinit var out_of_stock: TextView
    lateinit var indicator3: CircleIndicator3
    lateinit var loader_ll: RelativeLayout
    lateinit var viewDealNestedScrollView: NestedScrollView
    lateinit var lottieAddToCart: LottieAnimationView
    private val sliderHandler: Handler = Handler()
    var flag = ""
    var flag1 = ""
    var dealid = ""
    var WholeSellerFlag = ""
    var productid = ""
    var requestdealprice: Number = 0
    var Notification_deal_id = ""
    lateinit var mainHeader: RelativeLayout
    lateinit var progressbar: ProgressBar
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    lateinit var image_show: LinearLayout
    var imagesCollection = ArrayList<String>()
    lateinit var size_spinner: LinearLayout
    lateinit var spinner_value_tv: TextView
    var priceSizeDetails = ArrayList<PriceSizeDetails>()

    var dealDetails = ArrayList<DealDetails>()
    var priceSizeDetailsRequest = PriceSizeDetailsRequest()


    lateinit var mContext: Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.activity_view_add_deals, container, false)
        mContext = activity?.applicationContext!!

        spinner_value_tv = view.findViewById(R.id.spinner_value_tv)
        size_spinner = view.findViewById(R.id.size_spinner)
        viewDealNestedScrollView = view.findViewById(R.id.viewDealNestedScrollView)
        loader_ll = view.findViewById(R.id.loader_ll)
        lottieAddToCart = view.findViewById(R.id.loader)
        disable_AddToCart_BTN = view.findViewById(R.id.disable_AddToCart_BTN)
//        share = view.findViewById(R.id.share)
        indicator3 = view.findViewById(R.id.indicator)
        image_show = view.findViewById(R.id.image_show)

        multi_image = view.findViewById(R.id.multi_image)
        priceTag = view.findViewById(R.id.priceTag)
        deal_productname = view.findViewById(R.id.deal_productname)
        progressbar = view.findViewById(R.id.progressbar)
        viewAddDealsOfferValue1 = view.findViewById(R.id.viewAddDealsOfferValue1)
        price = view.findViewById(R.id.price)
        price = view.findViewById(R.id.price)
        tvDescription = view.findViewById(R.id.tvDescription)
        dealTime = view.findViewById(R.id.dealTime)
        tvDealID = view.findViewById(R.id.tvDealID)
        offerValue1 = view.findViewById(R.id.viewAddDealsOfferValue1)
//        Back = view.findViewById(R.id.viewAddDealsBack)
        viewAddDealsSubmit = view.findViewById(R.id.viewAddDealsSubmit)
        dealView = view.findViewById(R.id.dealView)
        noData_tv = view.findViewById(R.id.noData_tv)
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        mainHeader.visibility = View.VISIBLE
        setToolbar()
        offerValue1.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        try {
            flag = requireArguments().getString("flag").toString()
            flag1 = requireArguments().getBoolean("flag1").toString()
            dealid = requireArguments().getString("deal_id").toString()
            productid = requireArguments().getString("productid").toString()
            WholeSellerFlag = requireArguments().getBoolean("WholeSeller").toString()

            if (flag == "Customer") {
                viewAddDealsSubmit.text = "Add to cart"


            } else if (flag == "Retailer") {
                viewAddDealsSubmit.text = "Delete"
            } else {

            }

            if (WholeSellerFlag == "WholeSeller") {
                viewAddDealsSubmit.text = "Request For OFFER"
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        shareIcon.setSafeOnClickListener {

            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            var shareBody: String = "www.exobe.viewdetails.com/exobe?dealID=$dealid"
            var shareSubject: String = "Share link:-"
            i.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
            i.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(i, "Sharing using"))

        }

        back.setSafeOnClickListener {
            parentFragmentManager.popBackStack()

        }
        viewAddDealsSubmit.setSafeOnClickListener {

            if (SavedPrefManager.getStringPreferences(
                    requireContext(),
                    SavedPrefManager.isLogin
                ) == "true"
            ) {

                addToCart(productid)
            } else {
                parentFragmentManager.let { it1 ->
                    CustomerBottomSheet(
                        "Customer",
                        mContext,
                        this
                    ).show(it1, "ModalBottomSheet")
                }
            }


        }
        var page = 0
        val sliderRunnable =
            Runnable { multi_image.currentItem = page }

        val callback: ViewPager2.OnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
                page++
                if (position == 2) {
                    page = 0
                }
            }
        }
        multi_image.registerOnPageChangeCallback(callback)
        image_show.setSafeOnClickListener {
            ImageShowDialog(imagesCollection).show(requireFragmentManager(), "ShowImage")
        }

        size_spinner.setSafeOnClickListener {
            if (dealDetails.size > 1) {
                parentFragmentManager.let { it1 ->
                    SelectSizeBottomSheet(
                        requireContext(),
                        "Deal",
                        dealDetails,
                        priceSizeDetails,
                        this
                    ).show(it1, "ModalBottomSheet")
                }
            }

        }
        CustomerdealViewApi(dealid)

        disable_AddToCart_BTN.setSafeOnClickListener {
            shareIcon.isVisible = false
            var bundle = Bundle()
            //bundle.putString("productId2", productId2)
            var objCart = MyCartFragment("goToCart")
            objCart.setArguments(bundle)
            fragmentManager?.beginTransaction()
                ?.replace(R.id.FrameLayout, objCart, "review")?.addToBackStack(null)
                ?.commit()
        }


        return view
    }

    fun setImageAdaptor(images: ArrayList<String>) {
        imageAdaptor = dealviewimage_adaptor(images, requireContext(), this, "viewdeals1")
        multi_image.adapter = imageAdaptor
        if (images.size > 1) {
            indicator3.setViewPager(multi_image)
        }

    }

    override fun click4(_id: String, _id1: String) {
    }

    fun CustomerdealViewApi(id: String) {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<viewdeals_response> =
                ApiCallBack<viewdeals_response>(this, "CustomerdealViewApi", mContext)
            try {
                serviceManager.customerdealsViewApi(callBack, id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            viewDealNestedScrollView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }

    }

    @SuppressLint("SetTextI18n")
    override fun onApiSuccess(response: viewdeals_response, apiName: String?) {
        if (response.responseCode == 200) {
            progressbar.visibility = View.GONE
            dealView.visibility = View.VISIBLE
            noData_tv.isVisible = false
            try {
                imagesCollection.clear()
                imagesCollection = response.result?.dealImage!!
                if (imagesCollection != null) {
                    setImageAdaptor(imagesCollection)
                }
                dealDetails = response.result.dealDetails!!
                setDescriptionFun(response.result.description)
                var id = response.result?._id
                tvDealID.text = id
                deal_productname.text =
                    response.result.productId?.get(0)?.productName?.capitalizeFirstLetter()
                var minPrice = dealDetails[0].dealPrice
                if(dealDetails.size == 1) {
                    spinner_value_tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                }
                spinner_value_tv.text = "${dealDetails.getOrNull(0)!!.value} ${dealDetails.getOrNull(0)!!.unit!!.lowercase().takeIf { it != "other" }?:""}"
                requestdealprice = dealDetails.getOrNull(0)!!.dealPrice!!
//                for (i in 0 until dealDetails.size) {
//                    if (dealDetails[i].dealPrice!!.toDouble() == minPrice) {

                        priceSizeDetailsRequest.id = dealDetails.getOrNull(0)!!.id
                        priceSizeDetailsRequest.value = dealDetails.getOrNull(0)!!.value
                        priceSizeDetailsRequest.price = dealDetails.getOrNull(0)!!.price
                        priceSizeDetailsRequest.unit = dealDetails.getOrNull(0)!!.unit

//                    }
//                }
                if (dealDetails.size > 0) {
                    price.text =
                        "R ${CommonFunctions.currencyFormatter(dealDetails[0].dealPrice!!.toDouble())}"
                    viewAddDealsOfferValue1.text =
                        "R ${CommonFunctions.currencyFormatter(dealDetails[0].price!!.toDouble()!!)}"
                } else {
                    price.text =
                        "R 0"
                    viewAddDealsOfferValue1.text =
                        "R 0"
                }
                dealTime.text = "${DateFormat.dealsdate(response.result.dealStartTime)} ${DateFormat.showDealTime(response.result.dealStartTime)} - ${DateFormat.dealsdate(response.result.dealEndTime)} ${DateFormat.showDealTime(response.result.dealEndTime)}".toUpperCase()

                priceTag.text = "Get FLAT ${response.result.dealDiscount?.toDouble()
                    ?.let { CommonFunctions.formatPercentage(it) }} Off"
                if (dealDetails[0].quantity == "0") {
                    viewAddDealsSubmit.visibility = View.GONE
                    disable_AddToCart_BTN.visibility = View.VISIBLE
                    disable_AddToCart_BTN.text = "Out of Stock"
                    disable_AddToCart_BTN.setTextColor(resources.getColor(R.color.red))
                    disable_AddToCart_BTN.isEnabled = false

                } else {
                    viewAddDealsSubmit.visibility = View.VISIBLE
                    disable_AddToCart_BTN.visibility = View.GONE
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        else {
            progressbar.visibility = View.GONE
            noData_tv.isVisible = true
        }
    }

    override fun onApiErrorBody(response: String, apiName: String?) {
        val gson = GsonBuilder().create()
        var pojo = response_modal_class()
        progressbar.visibility = View.GONE
        noData_tv.isVisible = true
        try {
            pojo = gson.fromJson(response, pojo::class.java)
//            androidextention.alertBox(
//                pojo.responseMessage,
//                requireContext()
//            )

        } catch (e: Exception) {
            // handle failure at error parse
        }
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        progressbar.visibility = View.GONE
        noData_tv.isVisible = true
    }

    private fun setDescriptionFun(originalText: String?) {
        val maxLength = 150 // Maximum length of the visible text

        if (originalText != null) {
            if (originalText.length > maxLength) {
                val trimmedText = originalText.substring(0, maxLength) + "..."
                val spannableString = SpannableString("$trimmedText Read More")

                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(view: View) {
                        // Handle the "Read More" click event
                        tvDescription.text = originalText
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        // Customize the appearance of the "Read More" link
                        ds.isUnderlineText = false
                        ds.color = resources.getColor(R.color.red)
                    }
                }

                spannableString.setSpan(
                    clickableSpan,
                    trimmedText.length + 1,
                    spannableString.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                tvDescription.text = spannableString
                tvDescription.movementMethod = LinkMovementMethod.getInstance()
            } else {
                tvDescription.text = originalText
            }
        }

    }

    fun addToCart(product: String) {

        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            viewAddDealsSubmit.visibility = View.GONE
            loader_ll.visibility = View.VISIBLE
            lottieAddToCart.initLoader(true)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<AddToCart> =
                ApiCallBack<AddToCart>(object :
                    ApiResponseListener<AddToCart> {
                    override fun onApiSuccess(response: AddToCart, apiName: String?) {
                        loader_ll.visibility = View.GONE
                        lottieAddToCart.initLoader(false)
                        if (response.responseCode == 200) {
                            try {
                                disable_AddToCart_BTN.visibility = View.VISIBLE
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        } else {
                            androidextention.alertBox(
                                response.responseMessage.toString(),
                                requireContext()
                            )
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        viewAddDealsSubmit.visibility = View.VISIBLE
                        loader_ll.visibility = View.GONE
                        lottieAddToCart.initLoader(false)
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
                        viewAddDealsSubmit.visibility = View.VISIBLE
                        loader_ll.visibility = View.GONE
                        lottieAddToCart.initLoader(false)
                    }

                }, "addToCart", mContext)


            var request = AddToCartRequest()
            request.productId = product
            request.priceSizeDetails = priceSizeDetailsRequest
            request.quantity = 1
            request.totalPrice = requestdealprice
            request.orderType = "DEAL"
            request.addType = "DEAL"

            try {
                serviceManager.addToCart(callBack, request)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            viewDealNestedScrollView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }
    }

    override fun getSize(
        name: String,
        id: String,
        actualprice: Number,
        unit: String,
        value: String,
        dealPrice: Number?,
        quantity: String?
    ) {
        spinner_value_tv.text = name
        price.text = "R ${dealPrice?.let { CommonFunctions.currencyFormatter(it.toDouble()) }}"
        viewAddDealsOfferValue1.text = "R ${CommonFunctions.currencyFormatter(actualprice?.toDouble()!!)}"
        requestdealprice = dealPrice?.toDouble()!!
        priceSizeDetailsRequest.id = id
        priceSizeDetailsRequest.value = value
        priceSizeDetailsRequest.price = actualprice
        priceSizeDetailsRequest.unit = unit
        if (quantity == "0") {
            viewAddDealsSubmit.visibility = View.GONE
            disable_AddToCart_BTN.visibility = View.VISIBLE
            disable_AddToCart_BTN.text = "Out of Stock"
            disable_AddToCart_BTN.setTextColor(resources.getColor(R.color.red))
        } else {
            viewAddDealsSubmit.visibility = View.VISIBLE
            disable_AddToCart_BTN.visibility = View.GONE
        }
    }

    override fun isLoginListener() {
        var name = activity?.findViewById<TextView>(R.id.name)
        var userProfile = activity?.findViewById<ImageView>(R.id.Profile_picture)
        CommonFunctions.getProfileApiApi(mContext, name, userProfile)
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

        
        title.text = "Deal Details"

    }

//    override fun onPause() {
//        super.onPause()
//        shareIcon.visibility = View.GONE
//    }

    override fun onDestroy() {
        super.onDestroy()
        shareIcon.visibility = View.GONE
    }


}




