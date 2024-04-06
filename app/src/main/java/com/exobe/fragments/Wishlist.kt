package com.exobe.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.exobe.fragments.products.ProductViewFragment
import com.exobe.adaptor.WishListAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.NotificationApi
import com.exobe.utils.SavedPrefManager
import com.exobe.utils.TabHandler
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.bottomSheet.CustomerBottomSheet
import com.exobe.customClicks.LikeUnlikeClick
import com.exobe.customClicks.ProductViewListener
import com.exobe.customClicks.UpdateIsLoginListener
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.MainAddToWishListResponse
import com.exobe.entity.response.WishListDocs
import com.exobe.entity.response.WishlistResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder


class Wishlist(var flag: String) : Fragment(), ProductViewListener, LikeUnlikeClick, UpdateIsLoginListener {

    lateinit var Wishlist_Recyclerview: RecyclerView
    lateinit var mContext: Context
    var Productdata: ArrayList<WishListDocs> = ArrayList()
    lateinit var productAdpter: WishListAdapter
    lateinit var nestedscrollview_wishlist:NestedScrollView
    lateinit var title : TextView
    lateinit var cart : ImageView
    lateinit var filter : ImageView
    lateinit var back : LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var progressbar: ProgressBar
    lateinit var pbWishlistPagination: ProgressBar
    lateinit var NoReview: LinearLayout
    lateinit var pull_to_refresh: SwipeRefreshLayout
    var pages = 0
    var page = 1
    var limit = 20
    var dataLoadFlag = true
    var loaderFlag = true
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    private var apiCallFlag = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wishlist, container, false)
        mContext = activity?.applicationContext!!
        setToolbar()
        TabHandler.HandleTab(R.id.Wishlist_grey, requireActivity())
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        mainHeader.visibility = View.VISIBLE
        pbWishlistPagination = view.findViewById(R.id.pbWishlistPagination)
        pull_to_refresh = view.findViewById(R.id.pull_to_refresh)
        Wishlist_Recyclerview = view.findViewById(R.id.Wishlist_Recyclerview)
        progressbar = view.findViewById(R.id.progressbar)
        NoReview = view.findViewById(R.id.NoReview)
        nestedscrollview_wishlist = view.findViewById(R.id.nestedscrollview_wishlist)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        mainHeader.visibility = View.VISIBLE

        nestedscrollview_wishlist.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                dataLoadFlag = false
                loaderFlag = false

                pbWishlistPagination.visibility = View.VISIBLE
                if (page == pages) {
                    pbWishlistPagination.visibility = View.GONE
                } else {
                    page++
                    wishListApi()
                }
            }
        })

        pull_to_refresh.setOnRefreshListener {
            resetPagination()
            if (androidextention.isOnline(mContext)) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                wishListApi()

            } else {
                NoReview.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
            }
            pull_to_refresh.isRefreshing = false

        }

        return view
    }

    override fun onResume() {
        super.onResume()
        if(apiCallFlag) {
            resetPagination()
            wishListApi()
            apiCallFlag = false
        } else {
            if (androidextention.isOnline(mContext)) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                if (Productdata.size > 0) {
                    NoReview.visibility = View.GONE
                    setAdapterWishlist()
                } else {
                    setAdapterWishlist()
                    NoReview.visibility = View.VISIBLE
                }
            } else {
                NoReview.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
            }
        }
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


    fun wishListApi() {
        if (androidextention.isOnline(mContext)) {
            NoReview.visibility = View.GONE
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            if (loaderFlag) {
                progressbar.visibility = View.VISIBLE
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<WishlistResponse> =
                ApiCallBack<WishlistResponse>(object :
                    ApiResponseListener<WishlistResponse> {
                    override fun onApiSuccess(
                        response: WishlistResponse,
                        apiName: String?
                    ) {
                        progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                if (dataLoadFlag) {
                                    Productdata.clear()
                                }
                                page = response.result?.page!!
                                pages = response.result.pages
                                response.result.doc.let {
                                    if (it != null) {
                                        Productdata.addAll(it)
                                    }
                                }
                                if (Productdata.size > 0) {
                                    NoReview.visibility = View.GONE
                                    setAdapterWishlist()
                                } else {
                                    setAdapterWishlist( )
                                    NoReview.visibility = View.VISIBLE
                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        } else {
                            NoReview.visibility = View.VISIBLE
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar.visibility = View.GONE
                        NoReview.visibility = View.VISIBLE
                        try {
                            Productdata.clear()
                            productAdpter.notifyDataSetChanged()
                        } catch (e: Exception) {
                            e.printStackTrace()
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
                        progressbar.visibility = View.GONE
                        NoReview.visibility = View.VISIBLE

                    }

                }, "WishlistApi", mContext)



            try {
                serviceManager.WishListApi(callBack,page, limit)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            NoReview.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }
    }

    private fun setAdapterWishlist() {
        Wishlist_Recyclerview.layoutManager = GridLayoutManager(activity, 2)
        productAdpter = WishListAdapter(requireContext(), Productdata, this, flag, resources, this)
        Wishlist_Recyclerview.adapter = productAdpter

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun wishlistClickListener(id: String, position: Int) {
        if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {

            Productdata.removeAt(position)
            productAdpter.notifyItemChanged(position)
            productAdpter.notifyDataSetChanged()

            if (Productdata.size == 0){
                NoReview.visibility = View.VISIBLE
            }

            Addtowishlist(id)
        } else {
            val userType = SavedPrefManager.getStringPreferences(context, SavedPrefManager.USER_TYPE)

            when (userType) {
                "CUSTOMER" -> showCustomerBottomSheet("Customer")
                "RETAILER" -> showCustomerBottomSheet("Retailer")
            }
        }
    }

    private fun showCustomerBottomSheet(customerType: String) {
        fragmentManager?.let { fragmentManager ->
            CustomerBottomSheet(customerType, requireContext(), this).show(
                fragmentManager,
                "ModalBottomSheet"
            )
        }
    }

    private fun Addtowishlist(productId: String) {
        if (androidextention.isOnline(mContext)) {

            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<MainAddToWishListResponse> =
                ApiCallBack<MainAddToWishListResponse>(object : ApiResponseListener<MainAddToWishListResponse> {
                    override fun onApiSuccess(response: MainAddToWishListResponse, apiName: String?) {
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
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
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }
    }

    private fun resetPagination() {
        page = 1
        dataLoadFlag = true
        loaderFlag = true
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
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
        cartCount.visibility = View.GONE
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
        if (SavedPrefManager.getStringPreferences(activity, SavedPrefManager.isLogin) == "true") {
            NotificationApi.notifiactionCountApi(
                requireContext(),
                greyBellImageView,
                logoutButton
            )
            SavedPrefManager.savePreferenceBoolean(context, SavedPrefManager.NOTIFICATION_VISIBLE, true)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        DealsImageView.visibility = View.VISIBLE
        greyBellImageView.visibility = View.VISIBLE
    }

    override fun viewProduct(productId: String, dealId: String) {
//        if(dealId == "") {
            val bundle = Bundle()
            bundle.putString("productId2", productId)
            val fragobj = ProductViewFragment()
            fragobj.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.FrameLayout, fragobj, "productView")?.addToBackStack(null)?.commit()

//        } else {
//            val bundle = Bundle()
//            bundle.putString("flag", "")
//            bundle.putBoolean("flag1", false)
//            bundle.putString("deal_id", dealId)
//            bundle.putString("productid", productId)
//
//            val fragobj = ViewAddDeals()
//            fragobj.setArguments(bundle)
//            parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragobj, "viewAddDeals").addToBackStack(null).commit()
//
//        }
    }

}