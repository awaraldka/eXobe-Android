package com.exobe.fragments.cart

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.fragments.address.ChooseDeliveryAddress
import com.exobe.fragments.products.ProductViewFragment
import com.exobe.Adapter.MyCartAdapter
import com.exobe.Adapter.MyCartAdapter2
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.utils.Progresss
import com.exobe.utils.SavedPrefManager
import com.exobe.fragments.allServices.DeliveryFeesFragment
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.*
import com.exobe.dialogs.deleteCartDialog
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.MightBeLikeRequest
import com.exobe.entity.request.PriceSizeDetailsRequest
import com.exobe.entity.request.UpdateQuantity
import com.exobe.entity.response.*
import com.exobe.entity.response.customer.UpdateCart
import com.exobe.entity.response.product.GuestProductDocs
import com.exobe.entity.response.product.GuestProductResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder

class MyCartFragment(var flag: String) : Fragment(), deleteCartApi,
    ApiResponseListener<MyCartListResponse>, CustomClick6, CartTotalAmount, DeleteCartItem,
    wishlistcustomclick, ProductViewListener {


    private lateinit var subTotalAmount:TextView
    private lateinit var discountAmount:TextView
    private lateinit var totalAmount:TextView
    private lateinit var shippingTotalAmount:TextView
    private lateinit var grandTotalAmount:TextView
    private lateinit var selectDeliveryDays:LinearLayout
    private lateinit var deliverytype:TextView
    private var shippingFee = ""
    private var totalAmountValue = ""
    private var deliveryAmount = ""
    private var deliveryTypeRequest = ""
    private var isDeliveryAmountChanged = false




    private lateinit var refreshCart: SwipeRefreshLayout
    private lateinit var recyclerLayout: RecyclerView
    private lateinit var recyclerLayout1: RecyclerView
    private lateinit var myCartAdapter: MyCartAdapter
    private lateinit var llMyCart: LinearLayout
    private lateinit var menuClick: LinearLayout
    private lateinit var proceedButton: Button
    private lateinit var mContext: Context
    private var data: ArrayList<MyCartList> = ArrayList()
    private var payOutAmount: PayOutAmount = PayOutAmount()
    private var price: ArrayList<Double> = ArrayList()
    private var suggestionsArray: ArrayList<String> = ArrayList()
    private var subCategoryId: ArrayList<String> = ArrayList()
    private var array: List<GuestProductDocs> = listOf()
    private lateinit var title: TextView
    private lateinit var mightLike: LinearLayout
    private lateinit var priceDetails: LinearLayout
    private lateinit var cart: ImageView
    private lateinit var filter: ImageView
    private lateinit var back: LinearLayout
    private lateinit var dealsImageView: ImageView
    private lateinit var greyBellImageView: ImageView
    private lateinit var redBellImageview: ImageView
    private lateinit var mainHeader: RelativeLayout
    private lateinit var progressbar: ProgressBar
    private lateinit var totalPrice: TextView
    private lateinit var noItemCart: LinearLayout
    private lateinit var cartView: LinearLayout
    private var counter = 1
    private var tempTextView: TextView? = null
    private var lottie: LottieAnimationView? = null
    private lateinit var internetConnection: RelativeLayout
    private var apiCallFlag = true
    private var loaderShow =  true
    private var productIdArray :List<String> = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener("requestKey", this) { _, bundle ->
            deliveryAmount = bundle.getString("deliveryAmount")!!
            isDeliveryAmountChanged  = bundle.getBoolean("isDeliveryAmountChanged")
            deliveryTypeRequest  = bundle.getString("deliveryTypeRequest")!!

        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.activity_my_cart, container, false)
        mContext = activity?.applicationContext!!
        setToolbar()

        internetConnection = activity?.findViewById(R.id.internet_connection)!!
        lottie = activity?.findViewById(R.id.lottie)!!
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        noItemCart = view.findViewById(R.id.no_item_cart)
        llMyCart = view.findViewById(R.id.ll_mycart)
        cartView = view.findViewById(R.id.cart_view)
        recyclerLayout = view.findViewById(R.id.orderReview_recyclerview)
        proceedButton = view.findViewById(R.id.proceed_button)
        progressbar = view.findViewById(R.id.progressbar)
        totalPrice = view.findViewById(R.id.TotalPrice)
        mightLike = view.findViewById(R.id.MightLike)
        priceDetails = view.findViewById(R.id.priceDetails)
        tempTextView = view.findViewById(R.id.temp)
        refreshCart = view.findViewById(R.id.refresh_cart)
        subTotalAmount = view.findViewById(R.id.subTotalAmount)
        discountAmount = view.findViewById(R.id.discountAmount)
        totalAmount = view.findViewById(R.id.totalAmount)
        shippingTotalAmount = view.findViewById(R.id.shippingTotalAmount)
        grandTotalAmount = view.findViewById(R.id.grandTotalAmount)
        selectDeliveryDays = view.findViewById(R.id.selectDeliveryDays)
        deliverytype = view.findViewById(R.id.deliverytype)







        selectDeliveryDays.setOnClickListener{

            val bundle = Bundle()
            bundle.putString("deliveryType", deliveryTypeRequest)
            val obj = DeliveryFeesFragment()
            obj.arguments = bundle

            parentFragmentManager.beginTransaction().
            replace(R.id.FrameLayout, obj, "DeliveryFeesFragment")
                .addToBackStack("DeliveryFeesFragment").commit()
        }



        mainHeader.visibility = View.VISIBLE



        if (flag == "SideMenu") {
            back.visibility = View.GONE
            menuClick.visibility = View.VISIBLE
        } else {
            back.visibility = View.VISIBLE
            menuClick.visibility = View.GONE
        }

        proceedButton.setSafeOnClickListener {
            proceedOrderForCheckOutApi()
        }



        recyclerLayout1 = view.findViewById(R.id.card_recycler)


        back.setSafeOnClickListener {
            parentFragmentManager.popBackStack()
        }

        refreshCart.setOnRefreshListener {
            myCartListApi()
            loaderShow = true
            refreshCart.isRefreshing = false

        }

        return view
    }

    override fun onResume() {
        super.onResume()
        if (apiCallFlag) {
            myCartListApi()
            apiCallFlag = false
        } else {
            if (androidextention.isOnline(mContext)) {
                internetConnection.visibility = View.GONE
                lottie!!.initLoader(false)


                setCartListData()
                if (array.size == 0) {
                    mightLike.visibility = View.GONE
                } else {
                    mightLike.visibility = View.VISIBLE
                    priceDetails.visibility = View.VISIBLE
                    suggestionsAdapter(array)
                }
            } else {
                noItemCart.visibility = View.GONE
                cartView.visibility = View.GONE
                llMyCart.visibility = View.GONE
                internetConnection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    val fm: FragmentManager = requireActivity().supportFragmentManager

                    for (i in 0 until fm.backStackEntryCount) {
                        fm.popBackStack()
                    }


                }
            })

    }

    fun setToolbar() {
        title = activity?.findViewById(R.id.PreLoginTitle_TextView2)!!


        cart = activity?.findViewById(R.id.cart_icon)!!
        filter = activity?.findViewById(R.id.filter_icon)!!
        menuClick = activity?.findViewById(R.id.MenuClick)!!
        back = activity?.findViewById(R.id.imageView_back)!!
        dealsImageView = activity?.findViewById(R.id.Deals_ImageView)!!
        greyBellImageView = activity?.findViewById(R.id.greyBell_ImageView)!!
        redBellImageview = activity?.findViewById(R.id.logoutButton)!!
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
        cartCount.visibility = View.GONE
        cart.visibility = View.GONE
        filter.visibility = View.GONE
        dealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        redBellImageview.visibility = View.GONE
        menuClick.visibility = View.VISIBLE
        back.visibility = View.GONE
        title.text = "My Cart"
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    fun myCartListApi() {
        if (androidextention.isOnline(mContext)) {

            if(loaderShow){
                progressbar.visibility = View.VISIBLE
            }

            internetConnection.visibility = View.GONE
            lottie!!.initLoader(false)


            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<MyCartListResponse> =
                ApiCallBack<MyCartListResponse>(this, "myCartListApi", mContext)
            try {
                serviceManager.MyCartListApi(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            noItemCart.visibility = View.GONE
            cartView.visibility = View.GONE
            llMyCart.visibility = View.GONE
            internetConnection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }

    }


    override fun onApiSuccess(response: MyCartListResponse, apiName: String?) {
        progressbar.visibility = View.GONE
        if (response.responseCode == 200) {
            try {
                loaderShow = false
                data = response.result.cartList
                payOutAmount = response.result.payOutAmount
                setCartListData()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onApiErrorBody(response: String, apiName: String?) {
        noItemCart.visibility = View.VISIBLE
        cartView.visibility = View.GONE
        progressbar.visibility = View.GONE
        llMyCart.visibility = View.GONE

        if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.USER_TYPE).equals("CUSTOMER")) {
            similarProductListRetailer("RETAILER")
        } else {
            similarProductListRetailer("WHOLE_SALER")
        }
        val gson = GsonBuilder().create()
        var pojo = response_modal_class()

        try {
            pojo = gson.fromJson(response, pojo::class.java)
        } catch (e: Exception) {
            // handle failure at error parse
        }
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        noItemCart.visibility = View.VISIBLE
        cartView.visibility = View.GONE
        progressbar.visibility = View.GONE
        llMyCart.visibility = View.GONE

        if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.USER_TYPE).equals("CUSTOMER")) {
            similarProductListRetailer("RETAILER")
        } else {
            similarProductListRetailer("WHOLE_SALER")
        }
    }

    private fun deleteCartApi(itemId: String, position: Int, price: Number?, quantity: String) {
        if (androidextention.isOnline(mContext)) {
            internetConnection.visibility = View.GONE
            lottie!!.initLoader(false)
//            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<MyCartDeleteResponse> =
                ApiCallBack<MyCartDeleteResponse>(
                    object : ApiResponseListener<MyCartDeleteResponse> {

                        override fun onApiSuccess(response: MyCartDeleteResponse, apiName: String?) {
                            progressbar.visibility = View.GONE
                            if (response.responseCode == 200) {
                                try {

                                    myCartListApi()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }

                        }


                        override fun onApiErrorBody(response: String, apiName: String?) {
                            progressbar.visibility = View.GONE
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
                            progressbar.visibility = View.GONE
                        }
                    }, "myCartDeleteApi", mContext
                )

            try {
                serviceManager.MyCartListDeleteApi(callBack, itemId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            noItemCart.visibility = View.GONE
            cartView.visibility = View.GONE
            llMyCart.visibility = View.GONE
            internetConnection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setCartListData() {
        try {
            if (data.size > 0) {
                noItemCart.visibility = View.GONE
                cartView.visibility = View.VISIBLE
                llMyCart.visibility = View.VISIBLE
                priceDetails.visibility = View.VISIBLE
                price.clear()
                var priceValue = 0.0
                for (i in data.indices) {
                    priceValue += data[i].totalPrice.toDouble() * data[i].quantity!!
                    price.add(data[i].totalPrice.toDouble())
                    data[i].productId!!.categoryId?._id?.let { suggestionsArray.add(it) }
                    data[i].productId!!.subCategoryId?._id?.let { subCategoryId.add(it) }
                }

                with(payOutAmount){


                    if (!isDeliveryAmountChanged){ // TODO Here What ever response comes all cases managed based on api response

                        subTotalAmount.text = "R ${CommonFunctions.currencyFormatter(subTotal.toDouble())}"
                        discountAmount.text = "- R ${CommonFunctions.currencyFormatter(totalDiscount.toDouble())}"
                        totalAmount.text = "R ${CommonFunctions.currencyFormatter(totalAmountRes.toDouble())}"
                        val grandAmount = deliveryFee.toDouble() + totalAmountRes.toDouble()
                        grandTotalAmount.text = "R ${CommonFunctions.currencyFormatter(grandAmount)}"
                        shippingTotalAmount.text =   if(deliveryFee == 0) "Free" else "R ${CommonFunctions.currencyFormatter(deliveryFee.toDouble())}"
                        totalPrice.text = "R ${CommonFunctions.currencyFormatter(grandAmount)}"
                        totalAmountValue = totalAmountRes.toInt().toString()
                        shippingFee = deliveryFee.toString()
                        deliveryTypeRequest = deliveryMode
                        val dM = deliveryMode.lowercase().capitalizeFirstLetter()
                        deliverytype.text = "Shipping Charge(${dM})"

                    }else{ // TODO Here Handled cases based on delivery fees if customer changes delivery type

                        subTotalAmount.text = "R ${CommonFunctions.currencyFormatter(subTotal.toDouble())}"
                        discountAmount.text = "- R ${CommonFunctions.currencyFormatter(totalDiscount.toDouble())}"
                        totalAmount.text = "R ${CommonFunctions.currencyFormatter(totalAmountRes.toDouble())}"
                        val grandAmount = deliveryAmount.toDouble() + totalAmountRes.toDouble()
                        grandTotalAmount.text = "R ${CommonFunctions.currencyFormatter(grandAmount)}"
                        shippingTotalAmount.text =   if(deliveryAmount == "0") "Free" else "R ${CommonFunctions.currencyFormatter(deliveryAmount.toDouble())}"
                        totalPrice.text = "R ${CommonFunctions.currencyFormatter(grandAmount)}"
                        val dM = deliveryTypeRequest.lowercase().capitalizeFirstLetter()
                        deliverytype.text = "Shipping Charge(${dM})"

                    }

                }





                for (productId in data.indices){
                    productIdArray =  productIdArray + data[productId].productId!!.Id.toString()
                }

//                TotalPrice.text = "R ${CommonFunctions.currencyFormatter(FINAL_AMOUNT.toDouble())}"
                setAdapater(data)
                if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.USER_TYPE).equals("CUSTOMER")) {
                    similarProductListRetailer("RETAILER")
                } else {
                    similarProductListRetailer("WHOLE_SALER")
                }
            } else {
                noItemCart.visibility = View.VISIBLE
                cartView.visibility = View.GONE
                llMyCart.visibility = View.GONE
                priceDetails.visibility = View.GONE

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun setAdapater(data: ArrayList<MyCartList>) {
        recyclerLayout.layoutManager = LinearLayoutManager(mContext)
        myCartAdapter = MyCartAdapter(requireActivity(), data, this, this, this, this)
        recyclerLayout.adapter = myCartAdapter

    }

    override fun deleteCartList(
        itemId: String,
        position: Int,
        price: Number?,
        quantity: String,
        quantity1: TextView,
        i: Int
    ) {
        deleteCartApi(itemId, position, price, quantity)

    }

    override fun incrementAmmount(
        count: Int?,
        price: Number?,
        id: String?,
        quantity: String,
        quantityTextView: TextView,
        priceTextView: TextView,
        inc_dec_rv: RelativeLayout,
        priceSizeDetails: PriceSizeDetailsRequest?
    ) {
        if (count != null) {
            counter = count
        }
        if (counter > 0) {

            updateCartApi(
                id!!,
                quantity,
                quantityTextView,
                count,
                price!!,
                "INCREMENT",
                priceTextView,
                inc_dec_rv,
                priceSizeDetails
            )

        }
    }

    override fun decrementAmmount(
        count: Int?,
        price: Number?,
        id: String?,
        quantity: String,
        quantityTextView: TextView,
        priceTextView: TextView,
        inc_dec_rv: RelativeLayout,
        priceSizeDetails: PriceSizeDetailsRequest?
    ) {
        if (counter > 0) {

            updateCartApi(
                id!!,
                quantity,
                quantityTextView,
                count,
                price!!,
                "DECREMENT",
                priceTextView,
                inc_dec_rv,
                priceSizeDetails
            )
        }
    }


    override fun deleteCartItem(
        id: String?,
        price: Number?,
        position: Int,
        quantity: String,
        quantityTextView: TextView,
        count: Int
    ) {
        deleteCartApi(id!!, position, price, quantity)
    }

    private fun similarProductListRetailer(userType: String) {
        if (androidextention.isOnline(mContext)) {
            internetConnection.visibility = View.GONE
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

                                    val responseData = response.result.docs
                                    array = responseData.filter { similarProductId ->
                                        !productIdArray.contains(similarProductId._id)
                                    }


                                    if (array.isEmpty()) {
                                        mightLike.visibility = View.GONE
                                    } else {
                                        mightLike.visibility = View.VISIBLE
                                        suggestionsAdapter(array)
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
                    }, "suggestionOfProducts", mContext
                )

            val suggestions = MightBeLikeRequest()
            suggestions.userType = userType
            suggestions.categoryIds = suggestionsArray
            suggestions.subCategoryIds = subCategoryId
            suggestions.lng = SavedPrefManager.getLongitudeLocation()
            suggestions.lat = SavedPrefManager.getLatitudeLocation()



            try {
                serviceManager.productMightBeLikeRetailer(callBack, suggestions)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            noItemCart.visibility = View.GONE
            cartView.visibility = View.GONE
            llMyCart.visibility = View.GONE
            internetConnection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }



    private fun suggestionsAdapter(array: List<GuestProductDocs>) {
        recyclerLayout1.layoutManager = LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
        recyclerLayout1.adapter = MyCartAdapter2(mContext, array, this, this)


    }


    private fun updateCartApi(
        id: String,
        quantity: String,
        quantityTextView: TextView,
        count: Int?,
        price: Number,
        flag: String,
        priceTextView: TextView,
        inc_dec_rv: RelativeLayout,
        priceSizeDetails: PriceSizeDetailsRequest?
    ) {
        if (androidextention.isOnline(mContext)) {
            internetConnection.visibility = View.GONE
            lottie!!.initLoader(false)
//            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<UpdateCart> =
                ApiCallBack(
                    object : ApiResponseListener<UpdateCart> {

                        @SuppressLint("SetTextI18n")
                        override fun onApiSuccess(
                            response: UpdateCart,
                            apiName: String?
                        ) {
                            progressbar.visibility = View.GONE
                            if (response.responseCode == 200) {
                                try {

                                    inc_dec_rv.isEnabled = true
                                    quantityTextView.text = count.toString()

                                    myCartListApi()

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }

                            }


                        }

                        override fun onApiErrorBody(response: String, apiName: String?) {
                            progressbar.visibility = View.GONE
                            inc_dec_rv.isEnabled = true

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
                            progressbar.visibility = View.GONE
                            inc_dec_rv.isEnabled = true
                            if (failureMessage != null) {
                                androidextention.alertBox(failureMessage, requireContext())
                            }

                        }
                    }, "UpdateCart", mContext
                )

//            requestObj.id = priceSizeDetails?.id
//            requestObj.unit = priceSizeDetails?.unit
//            requestObj.value = priceSizeDetails?.value
//            requestObj.price = priceSizeDetails?.price
            val finalCount = if (flag == "INCREMENT") {
                if (quantity != "") {
                    val countQty = Integer.parseInt(quantity)
                    countQty + 1
                } else {
                    0
                }
            } else {
                if (quantity != "") {
                    val countQty = Integer.parseInt(quantity)
                    countQty - 1
                } else {
                    0
                }
            }

            val requestObj = UpdateQuantity()
            requestObj.priceSizeDetails = priceSizeDetails
            requestObj.quantity = finalCount


            try {
                serviceManager.updatecart(callBack, id, requestObj)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            noItemCart.visibility = View.GONE
            cartView.visibility = View.GONE
            llMyCart.visibility = View.GONE
            internetConnection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }

    }

    override fun wishlist(_id: String?) {
        _id?.let { addToWishListApi(it) }
    }

    private fun addToWishListApi(productId: String) {
        if (androidextention.isOnline(mContext)) {
            internetConnection.visibility = View.GONE
            lottie!!.initLoader(false)

            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<MainAddToWishListResponse> =
                ApiCallBack(object :
                    ApiResponseListener<MainAddToWishListResponse> {
                    override fun onApiSuccess(
                        response: MainAddToWishListResponse,
                        apiName: String?
                    ) {

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    }

                }, "addToWishList", mContext)


            try {
                serviceManager.addTowishlistApi(callBack, productId)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            noItemCart.visibility = View.GONE
            cartView.visibility = View.GONE
            llMyCart.visibility = View.GONE
            internetConnection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    override fun click5(
        itemId: String,
        position: Int,
        price: Number?,
        quantity: String,
        quantity1: TextView,
        i: Int
    ) {
        parentFragmentManager.let {
            deleteCartDialog(
                "Are you sure you want to \ndelete this item",
                "DeleteItem",
                this,
                itemId,
                position,
                price,
                quantity,
                quantity1,
                i
            ).show(
                it,
                "MyCustomFragment"
            )
        }
    }
    override fun viewProduct(productId: String, dealId: String) {
            val bundle = Bundle()
            bundle.putString("productId2", productId)
            val fragObj = ProductViewFragment()
            fragObj.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.FrameLayout, fragObj, "productView")?.addToBackStack(null)?.commit()


    }

    private fun proceedOrderForCheckOutApi() {
        if (androidextention.isOnline(requireContext())) {
            Progresss.start(requireContext())

            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<CommonResponseForAll> =
                ApiCallBack(object : ApiResponseListener<CommonResponseForAll> {
                    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
                    override fun onApiSuccess(response: CommonResponseForAll, apiName: String?) {
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            try {
                                ChooseDeliveryAddress.apiDeliveryAddressCallFlag = true
                                val bundle = Bundle()
                                val fragObj = ChooseDeliveryAddress()
                                fragObj.arguments = bundle

                                parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragObj,
                                    "ChooseDeliveryAddress").addToBackStack(null).commit()

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()
                        Progresss.stop()
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

                }, apiName = "Proceed order Cart", requireContext())


            try {

                serviceManager.proceedToCheckOutApi(callBack,deliveryType =deliveryTypeRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }


        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }
    }


    }
