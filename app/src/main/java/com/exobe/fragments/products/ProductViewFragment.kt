package com.exobe.fragments.products

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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.Adapter.ProductDescAdapter
import com.exobe.adaptor.*
import com.exobe.fragments.cart.MyCartFragment
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.activities.Review
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.utils.CommonFunctions.hideKeyboard
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.bottomSheet.CustomerBottomSheet
import com.exobe.bottomSheet.SelectSizeBottomSheet
import com.exobe.customClicks.*
import com.exobe.dialogs.ImageShowDialog
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.AddToCartRequest
import com.exobe.entity.request.PriceSizeDetailsRequest
import com.exobe.entity.request.ProductMightBeLikeRequest
import com.exobe.entity.response.DealDetails
import com.exobe.entity.response.MainAddToWishListResponse
import com.exobe.entity.response.PriceSizeDetails
import com.exobe.entity.response.customer.*
import com.exobe.entity.response.product.GuestProductDocs
import com.exobe.entity.response.product.GuestProductResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.fragments.IntrestedProductCampaignFragment
import com.exobe.modalClass.ProductDescModelClass
import com.exobe.utils.Progresss
import com.google.gson.GsonBuilder
import me.relex.circleindicator.CircleIndicator3


class ProductViewFragment : Fragment(), CustomeClick4, wishlistcustomclick,
    SizeListener, UpdateIsLoginListener, ViewImages, ProductViewListener {
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


    lateinit var imageViewer: LinearLayout
    lateinit var lottieAddToCart: LottieAnimationView
    var images = ArrayList<String>()
    var subCategoryId = ArrayList<String>()
    var similarProductsimages = ArrayList<GuestProductDocs>()
    var suggestionId = ""
    var count = 0
    lateinit var multi_image: ViewPager2
    lateinit var imageAdaptor: ImageSliderAdaptor
    lateinit var txtDescription: TextView
    lateinit var txtMaterials: TextView
    lateinit var txtReview: TextView
    lateinit var addToCartButton: TextView
    lateinit var disable_AddToCart_BTN: TextView
    lateinit var indicator3: CircleIndicator3
    lateinit var similarProductAdapter: SimilarProductsAdapter
    lateinit var productName: TextView
    lateinit var PriceTag: TextView
    lateinit var actualPrice: TextView

    lateinit var txtDescriptionDetails: TextView
    lateinit var My_RecyclerView: RecyclerView

    lateinit var Adapter: ProductDescAdapter
    var array: ArrayList<ProductDescModelClass> = ArrayList()
    lateinit var SimilarPRo: TextView
    lateinit var MaterialText: TextView
    lateinit var SeeAll: TextView
    lateinit var review: LinearLayout
    lateinit var NoReview: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var heart: ImageView
    lateinit var solidHeart: ImageView

    //    lateinit var share: ImageView
    lateinit var mContext: Context
    private val sliderHandler: Handler = Handler()
    var isLike = false
    lateinit var reviewAdaptor: DetailViewReview
    var data = ArrayList<ReviewResult>()
    lateinit var reviewRV: RecyclerView
    var productId2 = ""
    var price = ""
    lateinit var progressbar: ProgressBar
    lateinit var ProductViewDetails: TextView
    lateinit var main_ll: RelativeLayout
    lateinit var loader_ll: RelativeLayout
    lateinit var size_spinner: LinearLayout
    lateinit var spinner_value_tv: TextView
    var responseData: ViewResult = ViewResult()
    var shareProductId = ""
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    var priceSizeDetails = ArrayList<PriceSizeDetails>()
    var priceSizeDetailsRequest = PriceSizeDetailsRequest()
    var requestPrice: Number = 0
    var DealDetails = ArrayList<DealDetails>()
    lateinit var interestedClick:TextView

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.activity_product_description, container, false)

        mContext = activity?.applicationContext!!
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        imageViewer = view.findViewById(R.id.imageViewer)
        loader_ll = view.findViewById(R.id.loader_ll)
        lottieAddToCart = view.findViewById(R.id.loader)
        disable_AddToCart_BTN = view.findViewById(R.id.disable_AddToCart_BTN)
        spinner_value_tv = view.findViewById(R.id.spinner_value_tv)
        size_spinner = view.findViewById(R.id.size_spinner)
        SimilarPRo = view.findViewById(R.id.SimilarPRo)
        MaterialText = view.findViewById(R.id.MaterialText)
        review = view.findViewById(R.id.Review)
        NoReview = view.findViewById(R.id.NoReview)
        progressbar = view.findViewById(R.id.progressbar)
        PriceTag = view.findViewById(R.id.PriceTag)
        actualPrice = view.findViewById(R.id.actualPrice)
        productName = view.findViewById(R.id.ProductName)
        My_RecyclerView = view.findViewById(R.id.recyclerViewProductDescription)
        txtDescription = view.findViewById(R.id.txtDescription)
        txtMaterials = view.findViewById(R.id.txtMaterials)
        txtReview = view.findViewById(R.id.txtReview)
        txtDescriptionDetails = view.findViewById(R.id.txtDescriptionDetails)
        addToCartButton = view.findViewById(R.id.AddToCart_BTN)
        heart = view.findViewById(R.id.AddToCart_ImageView)
        solidHeart = view.findViewById(R.id.AddToCart_fill)
        reviewRV = view.findViewById(R.id.reviewRV)
        SeeAll = view.findViewById(R.id.SeeAll)
        indicator3 = view.findViewById(R.id.indicator)
        multi_image = view.findViewById(R.id.multi_image)
        ProductViewDetails = view.findViewById(R.id.ProductViewDetails)
        main_ll = view.findViewById(R.id.main_ll)
        interestedClick = view.findViewById(R.id.Interested)
        mainHeader.visibility = View.VISIBLE
        setToolbar()


        if (requireArguments().getString("productId2") != null) {
            productId2 = requireArguments().getString("productId2").toString()
        }

        heart.setSafeOnClickListener {
            if (SavedPrefManager.getStringPreferences(
                    requireContext(),
                    SavedPrefManager.isLogin
                ) == "true"
            ) {
                heart.visibility = View.GONE
                solidHeart.visibility = View.VISIBLE
                Addtowishlist(productId2)
            } else {
                parentFragmentManager.let { it1 ->
                    CustomerBottomSheet("Customer", mContext, this).show(it1, "ModalBottomSheet")
                }
            }
        }


        solidHeart.setSafeOnClickListener {
            if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                heart.visibility = View.VISIBLE
                solidHeart.visibility = View.GONE
                Addtowishlist(productId2)
            } else {
                parentFragmentManager.let { it1 ->
                    CustomerBottomSheet("Customer", mContext, this).show(it1, "ModalBottomSheet")
                }
            }


        }

        shareIcon.setSafeOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            var shareBody: String = "www.exobe.viewdetails.com/exobe?productID=" + shareProductId
            var shareSubject: String = "Share link:-"
            i.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
            i.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(i, "Sharing using"))

        }


        addToCartButton.setSafeOnClickListener {
            if (SavedPrefManager.getStringPreferences(requireContext(),SavedPrefManager.isLogin) == "true") {
                addToCart()
            } else {
                parentFragmentManager.let {
                    CustomerBottomSheet("Customer", mContext, this).show(it, "ModalBottomSheet")
                }
            }

        }


        interestedClick.setSafeOnClickListener {
            if (SavedPrefManager.getStringPreferences(requireContext(),SavedPrefManager.isLogin) == "true") {

                val bundle =  Bundle()
                bundle.putString("productId",productId2)
                val obj  = IntrestedProductCampaignFragment()
                obj.arguments =  bundle
                parentFragmentManager.beginTransaction()
                    .replace(R.id.FrameLayout, obj, "IntrestedProductCampaignFragment")
                    .addToBackStack(null).commit()
            } else {
                parentFragmentManager.let {
                    CustomerBottomSheet("Customer", mContext, this).show(it, "ModalBottomSheet")
                }
            }

        }



        disable_AddToCart_BTN.setSafeOnClickListener {
            shareIcon.visibility = View.GONE
            val bundle = Bundle()
            val objCart = MyCartFragment("goToCart")
            objCart.setArguments(bundle)
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, objCart, "review").addToBackStack(null)
                .commit()
        }




        SeeAll.setSafeOnClickListener {
            val bundle = Bundle()
            bundle.putString("productId2", productId2)
            val obj = Review()
            obj.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, obj, "review").addToBackStack(null)
                .commit()
        }



        txtDescription.setSafeOnClickListener {

            txtDescription.setTextAppearance(mContext, R.style.boldText)
            txtMaterials.setBackgroundResource(0)
            txtMaterials.setTextAppearance(mContext, R.style.normalText)
            txtReview.setBackgroundResource(0)
            txtReview.setTextAppearance(mContext, R.style.normalText)
            txtDescription.setBackgroundResource(R.drawable.deal_id_background)
            txtMaterials.setBackgroundResource(R.drawable.lightgrey_background)
            MaterialText.visibility = View.GONE
            review.visibility = View.GONE
            txtDescriptionDetails.visibility = View.VISIBLE
            My_RecyclerView.visibility = View.VISIBLE
            SimilarPRo.visibility = View.VISIBLE

        }

        txtMaterials.setSafeOnClickListener {

            txtDescription.setBackgroundResource(0)
            txtDescription.setTextAppearance(mContext, R.style.normalText)
            txtMaterials.setTextAppearance(mContext, R.style.boldText)
            txtReview.setBackgroundResource(0)
            txtReview.setTextAppearance(mContext, R.style.normalText)
            txtDescription.setBackgroundResource(R.drawable.lightgrey_background)
            txtMaterials.setBackgroundResource(R.drawable.deal_id_background)
            review.visibility = View.GONE
            txtDescriptionDetails.visibility = View.VISIBLE
            My_RecyclerView.visibility = View.GONE
            SimilarPRo.visibility = View.GONE

        }

        txtReview.setSafeOnClickListener {
            txtDescription.setBackgroundResource(0)
            txtDescription.setTextAppearance(mContext, R.style.normalText)
            txtMaterials.setBackgroundResource(0)
            txtMaterials.setTextAppearance(mContext, R.style.normalText)
            txtReview.setBackgroundResource(R.drawable.deal_id_background)
            txtReview.setTextAppearance(mContext, R.style.boldText)
            review.visibility = View.VISIBLE
            txtDescriptionDetails.visibility = View.GONE
            My_RecyclerView.visibility = View.GONE
            SimilarPRo.visibility = View.GONE
            MaterialText.visibility = View.GONE

        }


        back.setSafeOnClickListener {
            parentFragmentManager.popBackStack()
        }


        size_spinner.setSafeOnClickListener {
            if (priceSizeDetails.size > 1) {
                parentFragmentManager.let { it1 ->
                    SelectSizeBottomSheet(
                        requireContext(),
                        "",
                        DealDetails,
                        priceSizeDetails,
                        this
                    ).show(it1, "ModalBottomSheet")
                }
            }
        }

        viewProductApi()

        return view

    }

    override fun onResume() {
        super.onResume()
        hideKeyboard(requireActivity())
    }

    private fun setImageAdaptor(imageList: ArrayList<String>) {
        imageAdaptor = ImageSliderAdaptor(imageList, requireContext(), "viewDeals", this)
        multi_image.adapter = imageAdaptor
        if (imageList.size > 1) {
            indicator3.setViewPager(multi_image)
        }


    }

    override fun click4(_id: String, _id1: String) {
    }


    fun viewProductApi() {

        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            Progresss.start(requireContext())
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<WishListProductDetails> =
                ApiCallBack(object :
                    ApiResponseListener<WishListProductDetails> {
                    override fun onApiSuccess(response: WishListProductDetails, apiName: String?) {
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            try {
                                responseData = response.result!!
                                setProductData()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        } else {
                            main_ll.visibility = View.VISIBLE
                            ProductViewDetails.visibility = View.VISIBLE
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        Progresss.stop()
                        main_ll.visibility = View.VISIBLE
                        ProductViewDetails.visibility = View.VISIBLE
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
                        main_ll.visibility = View.VISIBLE
                        ProductViewDetails.visibility = View.VISIBLE
                    }

                }, "viewProductApi", mContext)


            try {
                if (SavedPrefManager.getStringPreferences(
                        requireContext(),
                        SavedPrefManager.isLogin
                    ) == "true"
                ) {
                    serviceManager.viewProductDetails(callBack, productId2)
                } else {
                    serviceManager.viewProductWithoutLoginDetails(callBack, productId2)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            main_ll.visibility = View.GONE
            ProductViewDetails.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    @SuppressLint("SetTextI18n")
    fun setProductData() {
        try {
            subCategoryId.add(responseData.subCategoryId?._id!!)
            suggestionId = responseData.categoryId?._id!!
            shareProductId = responseData._id!!
            price = responseData.price.toString()
            main_ll.visibility = View.VISIBLE
            images = responseData.productImage!!
            productName.text = responseData.productName?.capitalizeFirstLetter()
            priceSizeDetails = responseData.priceSizeDetails
            if(priceSizeDetails.size == 1) {
                spinner_value_tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }
            spinner_value_tv.text = "${priceSizeDetails.getOrNull(0)!!.value} ${priceSizeDetails.getOrNull(0)!!.unit.lowercase().takeIf { it != "other" }?:""}"
            requestPrice = priceSizeDetails.getOrNull(0)!!.price

            for (i in 0 until priceSizeDetails.size) {
                if (priceSizeDetails[i].price.toDouble() == responseData.priceSizeDetails[0].price.toDouble()) {
                    priceSizeDetailsRequest.id = priceSizeDetails[i].id
                    priceSizeDetailsRequest.value = priceSizeDetails[i].value
                    priceSizeDetailsRequest.price = priceSizeDetails[i].price
                    priceSizeDetailsRequest.unit = priceSizeDetails[i].unit
                }
            }


            if (responseData.isDealActive) {
                actualPrice.text = "R ${CommonFunctions.currencyFormatter(responseData.priceSizeDetails[0].price.toDouble())}"
                PriceTag.text = "R ${CommonFunctions.currencyFormatter(responseData.dealPrice.toDouble())}  ${responseData.dealDiscount}% Off"
                actualPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                actualPrice.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                actualPrice.text = "R ${CommonFunctions.currencyFormatter(responseData.priceSizeDetails[0].price.toDouble())}"
            }



            setDescriptionFun(responseData.description)


            MaterialText.text = responseData.description

            setImageAdaptor(images)

            if (responseData.isLike!!) {
                heart.visibility = View.GONE
                solidHeart.visibility = View.VISIBLE
            } else {
                heart.visibility = View.VISIBLE
                solidHeart.visibility = View.GONE
            }

            if (priceSizeDetails[0].quantity == "0") {
                addToCartButton.visibility = View.GONE
                disable_AddToCart_BTN.visibility = View.VISIBLE
                disable_AddToCart_BTN.text = "Out of Stock"
                disable_AddToCart_BTN.setTextColor(resources.getColor(R.color.red))
                disable_AddToCart_BTN.isEnabled = false

            } else {
                addToCartButton.visibility = View.VISIBLE
                disable_AddToCart_BTN.visibility = View.GONE
            }
            if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.USER_TYPE)
                    .equals("CUSTOMER")
            ) {
                similarProductListCustomer("RETAILER")
            } else {
                similarProductListCustomer("WHOLE_SALER")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
                        txtDescriptionDetails.text = originalText
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

                txtDescriptionDetails.text = spannableString
                txtDescriptionDetails.movementMethod = LinkMovementMethod.getInstance()
            } else {
                txtDescriptionDetails.text = originalText
            }
        }

    }

    private fun addToCart() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            addToCartButton.visibility = View.GONE
            loader_ll.visibility = View.VISIBLE
            lottieAddToCart.initLoader(true)

//            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<AddToCart> =
                ApiCallBack<AddToCart>(object :
                    ApiResponseListener<AddToCart> {
                    override fun onApiSuccess(response: AddToCart, apiName: String?) {
//                        progressbar.visibility = View.GONE

                        loader_ll.visibility = View.GONE
                        lottieAddToCart.initLoader(false)
                        if (response.responseCode == 200) {
                            try {
                                disable_AddToCart_BTN.visibility = View.VISIBLE
                                addToCartButton.visibility = View.GONE

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
                        addToCartButton.visibility = View.VISIBLE
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
                        addToCartButton.visibility = View.VISIBLE
                        loader_ll.visibility = View.GONE
                        lottieAddToCart.initLoader(false)
                    }

                }, "addToCart", mContext)

            var request = AddToCartRequest()
            request.productId = productId2
            request.priceSizeDetails = priceSizeDetailsRequest
            request.quantity = 1
            request.totalPrice = requestPrice
            request.orderType = "PRODUCT"
            request.addType = "PRODUCT"


            try {
                serviceManager.addToCart(callBack, request)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            main_ll.visibility = View.GONE

            ProductViewDetails.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    private fun similarProductListCustomer(userType: String) {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<GuestProductResponse> =
                ApiCallBack<GuestProductResponse>(
                    object : ApiResponseListener<GuestProductResponse> {

                        override fun onApiSuccess(
                            response: GuestProductResponse,
                            apiName: String?
                        ) {
                            androidextention.disMissProgressDialog(activity)
                            if (response.responseCode == 200) {
                                try {
                                    similarProductsimages.clear()
                                    for (i in 0 until response.result.docs.size) {
                                        if (response.result.docs[i]._id != productId2) {
                                            similarProductsimages.add(response.result.docs[i])
                                        }
                                    }




                                    if (similarProductsimages.size == 0) {
                                        SimilarPRo.visibility = View.GONE
                                    } else {
                                        SimilarPRo.visibility = View.VISIBLE
                                        similarProduct(similarProductsimages)
                                    }

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }

                            } else {
                                androidextention.alertBox(
                                    response.responseMessage,
                                    requireContext()
                                )
                            }
                        }

                        override fun onApiErrorBody(response: String, apiName: String?) {
                            androidextention.disMissProgressDialog(activity)
                            val gson = GsonBuilder().create()
                            var pojo = response_modal_class()

                            try {
                                pojo = gson.fromJson(response, pojo::class.java)

                            } catch (e: Exception) {
                                // handle failure at error parse
                            }
                        }

                        override fun onApiFailure(failureMessage: String?, apiName: String?) {
                            androidextention.disMissProgressDialog(activity)
                            Toast.makeText(requireContext(), "onApiFailure", Toast.LENGTH_SHORT)
                                .show()

                        }
                    }, "suggestionofProducts", mContext
                )

            var suggestions = ProductMightBeLikeRequest()
            suggestions.userType = userType
            suggestions.categoryId = suggestionId
            suggestions.subCategoryIds = subCategoryId
            suggestions.lng = SavedPrefManager.getLongitudeLocation()
            suggestions.lat = SavedPrefManager.getLatitudeLocation()


            try {
                serviceManager.newProductMightBeLikeCustomer(callBack, suggestions)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            main_ll.visibility = View.GONE

            ProductViewDetails.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }



    fun similarProduct(similarProductsImages: ArrayList<GuestProductDocs>) {
        My_RecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        similarProductAdapter = SimilarProductsAdapter(requireContext(), similarProductsImages, this, this)
        My_RecyclerView.adapter = similarProductAdapter

    }


    override fun wishlist(_id: String?) {
        if (SavedPrefManager.getStringPreferences(
                requireContext(),
                SavedPrefManager.isLogin
            ) == "true"
        ) {
            Addtowishlist(_id!!)
        } else {
            if (SavedPrefManager.getStringPreferences(context, SavedPrefManager.USER_TYPE)
                    .equals("CUSTOMER")
            ) {
                fragmentManager?.let { it1 ->
                    CustomerBottomSheet("Customer", requireContext(), this).show(
                        it1,
                        "ModalBottomSheet"
                    )
                }
            } else if (SavedPrefManager.getStringPreferences(context, SavedPrefManager.USER_TYPE)
                    .equals("RETAILER")
            ) {
                fragmentManager?.let { it1 ->
                    CustomerBottomSheet("Retailer", requireContext(), this).show(
                        it1,
                        "ModalBottomSheet"
                    )
                }
            }
        }
    }

    fun Addtowishlist(productId: String) {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<MainAddToWishListResponse> =
                ApiCallBack<MainAddToWishListResponse>(object :
                    ApiResponseListener<MainAddToWishListResponse> {
                    override fun onApiSuccess(
                        response: MainAddToWishListResponse,
                        apiName: String?
                    ) {
                        if (response.responseCode == 200) {

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)


                        } catch (e: Exception) {
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    }

                }, "Addtowishlist", mContext)


            try {
                serviceManager.addTowishlistApi(callBack, productId)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            main_ll.visibility = View.GONE
            ProductViewDetails.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun getSize(
        name: String,
        id: String,
        price: Number,
        unit: String,
        value: String,
        dealPrice: Number?,
        quantity: String?
    ) {

        if (responseData.isDealActive) {
            actualPrice.text = "R $price"
            val result = CommonFunctions.subtractPercentage(price.toDouble(), responseData.dealDiscount.toDouble())
            PriceTag.text = "R $result  ${responseData.dealDiscount}% Off"
            actualPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            actualPrice.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            actualPrice.text = "R ${CommonFunctions.currencyFormatter(price.toDouble())}"
        }


        spinner_value_tv.text = "$name ${unit.lowercase().takeIf { it != "other" }?:""}"
        requestPrice = price
        priceSizeDetailsRequest.id = id
        priceSizeDetailsRequest.value = value
        priceSizeDetailsRequest.price = price
        priceSizeDetailsRequest.unit = unit
        if (quantity == "0") {
            addToCartButton.visibility = View.GONE
            disable_AddToCart_BTN.visibility = View.VISIBLE
            disable_AddToCart_BTN.text = "Out of Stock"
            disable_AddToCart_BTN.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
        } else {
            addToCartButton.visibility = View.VISIBLE
            disable_AddToCart_BTN.visibility = View.GONE
        }
    }

    override fun isLoginListener() {
        var name = activity?.findViewById<TextView>(R.id.name)
        var userProfile = activity?.findViewById<ImageView>(R.id.Profile_picture)
        CommonFunctions.getProfileApiApi(mContext, name, userProfile)
        viewProductApi()
    }

    override fun viewImage(imageList: ArrayList<String>) {
        ImageShowDialog(imageList).show(requireFragmentManager(), "ShowImage")
    }

    fun removeThisFragment() {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTag = "productView"
        val fragmentsToRemove = fragmentManager?.fragments?.filter { it.tag == fragmentTag }
        fragmentsToRemove?.drop(1)
            ?.forEach { fragmentManager.beginTransaction().remove(it).commitNow() }

    }

    override fun onDestroy() {
        super.onDestroy()
        shareIcon.visibility = View.GONE
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
        shareIcon.visibility = View.VISIBLE

        title.text = "Product Details"

    }

    override fun viewProduct(productId: String, dealId: String) {
        val bundle = Bundle()
        bundle.putString("productId2", productId)
        val fragObj = ProductViewFragment()
        fragObj.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.FrameLayout, fragObj, "productView")?.addToBackStack(null)
            ?.commit()

    }





}
