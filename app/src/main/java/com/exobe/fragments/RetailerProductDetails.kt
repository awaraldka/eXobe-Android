package com.exobe.fragments.retailr

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.ImageSliderAdaptor
import com.exobe.adaptor.ViewPackagesDetailsAdaptor
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.activities.retailer.StartCampaignRetailerActivity
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.ChangePriceListener
import com.exobe.customClicks.ViewImages
import com.exobe.dialogs.ImageShowDialog
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.PriceSizeDetails
import com.exobe.entity.response.viewProductResponse
import com.exobe.entity.response.viewdeals_response
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.util.DateFormat
import com.google.gson.GsonBuilder
import me.relex.circleindicator.CircleIndicator3

class ProductDetails : Fragment(), ChangePriceListener, ViewImages {
    lateinit var imageAdaptor: ImageSliderAdaptor
    lateinit var multi_image: ViewPager2
    lateinit var indicator3: CircleIndicator3
    lateinit var ProductName: TextView
    lateinit var productDetailsId: TextView
    lateinit var productPrice: TextView
    lateinit var productQuantity: TextView
    lateinit var productCreatedDate: TextView
    lateinit var productDescription: TextView
    lateinit var productDiscount: TextView
    lateinit var ProductExpiryOn: TextView
    lateinit var ProductExpiryTime: TextView
    lateinit var discountLL: LinearLayout
    lateinit var dealDetails: LinearLayout

    private var priceSizeDetails = ArrayList<PriceSizeDetails>()
    lateinit var progressBar: ProgressBar

    lateinit var layoutProduct: LinearLayout
    lateinit var nodata_tv: TextView
    lateinit var startCampaignClick: TextView

    var images = ArrayList<String>()
    var productId = ""
    var sizeValue = ""

    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var product_pack_ll: LinearLayout
    var flagdealmanagement = ""
    lateinit var internet_connection: RelativeLayout

    //    lateinit var image_slider_rl: RelativeLayout
    lateinit var product_pack_rv: RecyclerView
    lateinit var price_ll: LinearLayout
    lateinit var quantity_ll: LinearLayout

    private val sliderHandler: Handler = Handler()

    var lottie: LottieAnimationView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_details, container, false)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        quantity_ll = view.findViewById(R.id.quantity_ll)
        price_ll = view.findViewById(R.id.price_ll)
        startCampaignClick = view.findViewById(R.id.startCampaignClick)
        product_pack_rv = view.findViewById(R.id.product_pack_rv)
        multi_image = view.findViewById(R.id.multi_image)
        indicator3 = view.findViewById(R.id.indicator)
        ProductName = view.findViewById(R.id.productName)
        productDetailsId = view.findViewById(R.id.productId)
        productPrice = view.findViewById(R.id.productPrice)
        productQuantity = view.findViewById(R.id.productQuantity)
        productCreatedDate = view.findViewById(R.id.ProductCreatedDate)
        productDescription = view.findViewById(R.id.txtDescriptionDetails)
        productDiscount = view.findViewById(R.id.productDiscount)
        progressBar = view.findViewById(R.id.progressbar)
        layoutProduct = view.findViewById(R.id.layoutProduct)
        discountLL = view.findViewById(R.id.discountLL)
        dealDetails = view.findViewById(R.id.dealDetails)
        ProductExpiryOn = view.findViewById(R.id.ProductExpiryOn)
        ProductExpiryTime = view.findViewById(R.id.ProductExpiryTime)
        product_pack_ll = view.findViewById(R.id.product_pack_ll)
        nodata_tv = view.findViewById(R.id.nodata_tv)


        if (requireArguments().getString("productId") != null) {
            productId = requireArguments().getString("productId").toString()
        }
        if (requireArguments().getString("dealsmanagement") != null) {
            flagdealmanagement = requireArguments().getString("dealsmanagement").toString()
        }


        setToolbar()
        back.setSafeOnClickListener {
            parentFragmentManager.popBackStack()
        }

        if (flagdealmanagement == "dealsmanagement") {
            discountLL.visibility = View.VISIBLE
            CustomerdealViewApi(productId)
            dealDetails.visibility = View.VISIBLE
            price_ll.visibility = View.GONE
            quantity_ll.visibility = View.GONE
        } else {
            product_pack_ll.visibility = View.VISIBLE
            viewProductDetailsApi(productId)

        }



        startCampaignClick.setSafeOnClickListener {
            val intent = Intent(requireContext(), StartCampaignRetailerActivity::class.java)
            intent.putExtra("productId",productId)
            intent.putExtra("edit",false)
            intent.putExtra("id","")
            startActivity(intent)
        }

        return view
    }

    fun setImageAdaptor(imageList: ArrayList<String>) {
        imageAdaptor = ImageSliderAdaptor(imageList, requireContext(), "viewDeals", this)
        multi_image.adapter = imageAdaptor
        if (imageList.size > 1) {
            indicator3.setViewPager(multi_image)
        }
    }

    private fun setProductPackagesAdaptor() {
        product_pack_rv.layoutManager = GridLayoutManager(requireContext(), 2)
        val viewPackagesDetailsAdaptor = ViewPackagesDetailsAdaptor(requireContext(), priceSizeDetails, this)
        product_pack_rv.adapter = viewPackagesDetailsAdaptor
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

        title.text = "Product Details"
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    fun viewProductDetailsApi(productId: String) {
        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressBar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<viewProductResponse> =
                ApiCallBack<viewProductResponse>(object :
                    ApiResponseListener<viewProductResponse> {
                    override fun onApiSuccess(response: viewProductResponse, apiName: String?) {
                        androidextention.disMissProgressDialog(activity)
                        if (response.responseCode == 200) {
//                            try {
                            progressBar.visibility = View.GONE
                            nodata_tv.visibility = View.GONE
                            layoutProduct.visibility = View.VISIBLE
                            startCampaignClick.visibility = View.VISIBLE
                            images = response.result.productImage
                            setImageAdaptor(images)
                            priceSizeDetails = response.result.priceSizeDetails
                            setProductPackagesAdaptor()
                            val id = "#${response.result.Id}"
                            productDetailsId.text = id
                            ProductName.text =
                                response.result.productName.toString().capitalizeFirstLetter()
                            productPrice.text =
                                "R ${CommonFunctions.currencyFormatter(response.result.priceSizeDetails[0].price.toDouble())}"
                            productQuantity.text = response.result.priceSizeDetails[0].quantity
                            productCreatedDate.text =
                                DateFormat.NotificationDate(response.result.createdAt)
                            productDescription.text = response.result.description
//                            } catch (e: Exception) {
//                                e.printStackTrace()
//                            }
                        } else {
                            layoutProduct.visibility = View.GONE
                            nodata_tv.visibility = View.GONE

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        androidextention.disMissProgressDialog(activity)
                        progressBar.visibility = View.GONE
                        layoutProduct.visibility = View.GONE
                        nodata_tv.visibility = View.GONE
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(
                                pojo.responseMessage,
                                requireContext()
                            )

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        androidextention.disMissProgressDialog(activity)
                        progressBar.visibility = View.GONE
                        layoutProduct.visibility = View.GONE
                        nodata_tv.visibility = View.GONE

                    }

                }, "viewProductDetailsApi", requireContext())

            try {
                serviceManager.viewProductDetailsApi(callBack, productId)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            layoutProduct.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }
    }

    fun CustomerdealViewApi(id: String) {
        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressBar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<viewdeals_response> =
                ApiCallBack<viewdeals_response>(object : ApiResponseListener<viewdeals_response> {
                    @SuppressLint("SetTextI18n")
                    override fun onApiSuccess(response: viewdeals_response, apiName: String?) {
                        if (response.responseCode == 200) {
                            progressBar.visibility = View.GONE
                            layoutProduct.visibility = View.VISIBLE
                            startCampaignClick.visibility = View.VISIBLE

                            try {


                                images = response.result?.dealImage!!
                                if (images != null) {
                                    setImageAdaptor(images)
                                }

                                val id = response.result._id

                                productDetailsId.text = id
                                ProductName.text =
                                    response.result.productId?.get(0)?.productName.toString()
                                        .capitalizeFirstLetter()
                                productPrice.text =
                                    "R ${CommonFunctions.currencyFormatter(response.result.dealPrice.toDouble())}"
                                productDiscount.text = "${response.result.dealDiscount?.toDouble()
                                    ?.let { CommonFunctions.formatPercentage(it) }} Off"
                                productQuantity.text =
                                    response.result.productId?.get(0)?.quantity.toString()
                                productDescription.text = response.result.description
                                productCreatedDate.text =
                                    "${DateFormat.dealsdate(response.result.dealStartTime)}   "
                                ProductExpiryOn.text =
                                    DateFormat.dealsdate(response.result.dealEndTime)
                                ProductExpiryTime.text =
                                    DateFormat.showDealTime(response.result.dealEndTime)

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()
                        progressBar.visibility = View.GONE

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(
                                pojo.responseMessage,
                                requireContext()
                            )

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressBar.visibility = View.GONE
                    }

                }, "CustomerdealViewApi", requireContext())
            try {
                serviceManager.customerdealsViewApi(callBack, id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            layoutProduct.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }

    }

    override fun changePrice(price: Number, quantity: String, toString: String) {
        productPrice.text = "R ${CommonFunctions.currencyFormatter(price.toDouble())}"
        productQuantity.text = quantity
        sizeValue = toString
    }

    override fun viewImage(imageList: ArrayList<String>) {
        ImageShowDialog(imageList).show(parentFragmentManager, "ShowImage")
    }


}