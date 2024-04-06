package com.exobe.activities

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.SearchProduct
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.CustomeClick2
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.*
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.JsonObject

class search_composer : Fragment(), CustomeClick2 {

    lateinit var RecyclerOfSearch: RecyclerView
    lateinit var adpter: SearchProduct
    lateinit var UMsearch: EditText
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var progressbar: ProgressBar
    lateinit var NoDataFound_tv: LinearLayout

    lateinit var save: Button
    lateinit var mContext: Context
    var data: ArrayList<AddProductByAdminDocs> = ArrayList()
    var pages = 0
    var page = 1
    var limit = 20
    var dataLoadFlag = false
    var loaderFlag = true
    var searchtext=""
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_composer, container, false)
        setToolbar()
        RecyclerOfSearch = view!!.findViewById(R.id.RecyclerOfSearch)
        UMsearch = view.findViewById(R.id.UMsearch)
        progressbar = view.findViewById(R.id.progressbar)
        NoDataFound_tv = view.findViewById(R.id.NoDataFound_tv)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!

        mContext = activity?.applicationContext!!

        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        mainHeader.visibility = View.VISIBLE
        RecyclerOfSearch.visibility = View.VISIBLE

        hideKeyboard()


        if (requireArguments().getString("SEARCH_TEXT") != null) {
            searchtext = requireArguments().getString("SEARCH_TEXT").toString()
        }


        back.setSafeOnClickListener {
            fragmentManager?.popBackStack()
        }

        productsearchlistApi()

        return view
    }

    fun setAdapter(data:ArrayList<AddProductByAdminDocs>) {
        RecyclerOfSearch.layoutManager = GridLayoutManager(context, 2)
        adpter = SearchProduct(requireContext(), this,data)
        RecyclerOfSearch.adapter = adpter
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
        title.setText("Product")
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    override fun click2(_id: String?,servicename:String?) {
        SavedPrefManager.saveStringPreferences(requireContext(),SavedPrefManager.ADMIN_PRODUCT_ID,_id)
        fragmentManager?.popBackStack()
    }

    fun productsearchlistApi() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            if (loaderFlag) {
                progressbar.visibility = View.VISIBLE
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<AddProductByAdminResponse> =
                ApiCallBack<AddProductByAdminResponse>(object :
                    ApiResponseListener<AddProductByAdminResponse> {
                    override fun onApiSuccess(response: AddProductByAdminResponse, apiName: String?) {
                        progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {

                                loaderFlag = false
                                if (!dataLoadFlag) {
                                    data.clear()
                                }
                                pages = response.result?.pages!!
                                data = response.result?.docs as ArrayList<AddProductByAdminDocs>
                                setAdapter(data)
                            }catch (e : Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar.visibility = View.GONE
                        NoDataFound_tv.visibility=View.VISIBLE

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                    }
                }, "productsearchlistApi", mContext)

            var jsonObject = JsonObject()
            if (searchtext != "") {
                jsonObject.addProperty("search",searchtext)
            }
            jsonObject.addProperty("page",page)
            jsonObject.addProperty("limit",limit)
            try {
                serviceManager.ProductListByAdminApi(callBack,jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }


    }

    fun hideKeyboard() {
        try {
            val `in`: InputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val view = requireActivity().findViewById<View>(android.R.id.content)
            assert(`in` != null)
            `in`.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        } catch (ignored: Throwable) {
        }
    }

}