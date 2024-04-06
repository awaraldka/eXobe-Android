package com.exobe.fragments.address

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.Adapter.ChooseDeliveryAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.UpdatePrimaryAddress
import com.exobe.customClicks.addressDelete
import com.exobe.customClicks.delete
import com.exobe.dialogs.DeleteDialog
import com.exobe.dialogs.PrimaryAddressDialog
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.*
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.fragments.cart.OrderReview
import com.exobe.utils.Progresss
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.util.*

class ChooseDeliveryAddress : Fragment(), delete, ApiResponseListener<AddresslistResponse>,
    addressDelete, UpdatePrimaryAddress {
    lateinit var chooseDelivery_address: RecyclerView
    lateinit var adapter: ChooseDeliveryAdapter
    lateinit var add: TextView
    lateinit var title: TextView
    lateinit var nextButtonToAddress: TextView
    lateinit var cart: ImageView
    lateinit var cartCount: TextView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mContext: Context
    lateinit var mainHeader: RelativeLayout
    lateinit var progress_bar_pagination: ProgressBar
    lateinit var empty_address: TextView
    lateinit var nestedScrollView_chooseAddress: NestedScrollView
    var data: ArrayList<AddresslistDocs> = ArrayList()
    var id = ""
    var flag: String = ""
    var pages = 0
    var page = 1
    var limit = 20
    var dataLoadFlag = false
    var loaderFlag = true
    var items: ArrayList<String> = ArrayList()
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    var addressPosition = 0
    var addressId = ""

    companion object {
        var apiDeliveryAddressCallFlag = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_choose_delivery_address, container, false)
        mContext = activity?.applicationContext!!
        chooseDelivery_address = view.findViewById(R.id.chooseDelivery_address)
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        empty_address = view.findViewById(R.id.empty_address)
        add = view.findViewById(R.id.add)
        nextButtonToAddress = view.findViewById(R.id.nextButtonToAddress)
        nestedScrollView_chooseAddress = view.findViewById(R.id.nestedScrollView_chooseAddress)
        progress_bar_pagination = view.findViewById(R.id.progress_bar_pagination)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!

        setToolbar()

        if (requireArguments().getString("flag") != null) {
            flag = requireArguments().getString("flag").toString()
            when (flag) {
                "service_request" -> {
                    title.text = "Choose Service Address"
                    back.visibility = View.VISIBLE
                    mainHeader.visibility = View.VISIBLE

                }
                "setting_address" -> {
                    title.text = "Address"
                    back.visibility = View.VISIBLE
                    mainHeader.visibility = View.VISIBLE

                }
                else -> {
                    back.visibility = View.VISIBLE
                    mainHeader.visibility = View.VISIBLE
                }
            }
        }
        if (requireArguments().getString("id") != null) {
        }

        add.setSafeOnClickListener {
            fragmentManager?.beginTransaction()?.replace(
                R.id.FrameLayout,
                AddAddressFragment("", "", ""), "addAddress",
            )?.addToBackStack(null)?.commit()


        }

        back.setSafeOnClickListener {
            try {
                fragmentManager?.popBackStack();
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }



        nextButtonToAddress.setOnClickListener {
            val addressBundle = Bundle()
            addressBundle.putInt("position", addressPosition)
            addressBundle.putString("_id", addressId)
            val obj = OrderReview()
            obj.arguments = addressBundle
            fragmentManager?.beginTransaction()?.replace(R.id.FrameLayout, obj, "overView")
                ?.addToBackStack(null)?.commit()
        }


        nestedScrollView_chooseAddress.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                dataLoadFlag = true
                loaderFlag = false
                page++
                progress_bar_pagination.visibility = View.VISIBLE
                if (page > pages) {
                    progress_bar_pagination.visibility = View.GONE
                } else {

                    choosedeliveryApi()
                }
            }
        })


        return view
    }

    override fun onResume() {
        super.onResume()
        val userType =
            SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.USER_TYPE)
        if (apiDeliveryAddressCallFlag) {
            if (userType.equals("RETAILER")) {
                dataLoadFlag = false
                loaderFlag = true
                page = 1
                choosedeliveryApi()
            } else if (userType.equals("CUSTOMER")) {
                dataLoadFlag = false
                loaderFlag = true
                page = 1
                choosedeliveryApi()
            } else {
            }
            apiDeliveryAddressCallFlag = false
        } else {

            if (userType.equals("RETAILER")) {
                if (androidextention.isOnline(mContext)) {
                    internet_connection.visibility = View.GONE
                    lottie!!.initLoader(false)
                    setData()
                } else {
                    empty_address.visibility = View.GONE
                    internet_connection.visibility = View.VISIBLE
                    lottie!!.initLoader(true)
                }
            } else if (userType.equals("CUSTOMER")) {
                if (androidextention.isOnline(mContext)) {
                    internet_connection.visibility = View.GONE
                    lottie!!.initLoader(false)
                    setData()
                } else {
                    empty_address.visibility = View.GONE
                    internet_connection.visibility = View.VISIBLE
                    lottie!!.initLoader(true)
                }
            } else {
            }
        }
    }

    override fun delete(_id: String?) {
        id = _id.toString()
        parentFragmentManager.let { DeleteDialog(this).show(it, "MyCustomFragment") }

//
    }

    override fun updatePrimaryAddress(_id: String?) {
        parentFragmentManager.let { PrimaryAddressDialog(this, _id).show(it, "MyCustomFragment") }
    }

    override fun selectedAddress(position: Int, id: String) {

        val isFilter =  data.filter { it.isSelected }
        addressPosition = position
        addressId = id
        nextButtonToAddress.isVisible = isFilter.isNotEmpty() && flag != "setting_address"


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
        cartCount = activity?.findViewById(R.id.cartCount)!!

        cartCount.visibility = View.GONE

        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
        cartCount.visibility = View.GONE
        cart.visibility = View.GONE
        filter.visibility = View.GONE
        back.visibility = View.VISIBLE

        MenuClick.visibility = View.GONE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE

        title.text = "Choose Delivery Address"
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    fun setAdapater() {
        chooseDelivery_address.layoutManager = LinearLayoutManager(mContext)
        chooseDelivery_address.adapter = ChooseDeliveryAdapter(mContext, data, this, parentFragmentManager, flag)
    }

    fun choosedeliveryApi() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            if (loaderFlag) {
                Progresss.start(requireContext())
            }
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<AddresslistResponse> = ApiCallBack<AddresslistResponse>(
                this, "choosedeliveryApi", mContext
            )
            var jsonObject = JsonObject()
            jsonObject.addProperty("page", page)
            jsonObject.addProperty("limit", limit)
            try {
                serviceManager.addressListApi(callBack, jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            empty_address.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }

    }

    override fun onApiSuccess(response: AddresslistResponse, apiName: String?) {
        Progresss.stop()
        if (response.responseCode == 200) {
            try {
                pages = response.result?.pages!!
//                loaderFlag = false
                if (!dataLoadFlag) {
                    data.clear()
                }
                data.addAll(response.result!!.docs as ArrayList<AddresslistDocs>)

                if (data.size > 0) {
                    empty_address.visibility = View.GONE
                    setData()
                } else {
                    empty_address.visibility = View.VISIBLE
                    setData()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    override fun onApiErrorBody(response: String, apiName: String?) {
        empty_address.visibility = View.VISIBLE
        Progresss.stop()
        data.clear()
        setData()

        val gson = GsonBuilder().create()
        var pojo = response_modal_class()

        try {
            pojo = gson.fromJson(response, pojo::class.java)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        empty_address.visibility = View.VISIBLE
        Progresss.stop()

    }


    private fun setData() {
        if (data.size > 0) {
            empty_address.visibility = View.GONE
            setAdapater()
        } else {
            setAdapater()
            empty_address.visibility = View.VISIBLE
        }
    }

    fun deleteAddressAPI() {

        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            Progresss.start(requireContext())
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<DeleteAddressResponse> =
                ApiCallBack<DeleteAddressResponse>(object :
                    ApiResponseListener<DeleteAddressResponse> {
                    override fun onApiSuccess(response: DeleteAddressResponse, apiName: String?) {
                        Progresss.stop()

                        if (response.responseCode == 200) {
                            try {
                                dataLoadFlag = false
                                loaderFlag = true
                                page = 1
                                choosedeliveryApi()
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
                            androidextention.alertBox(pojo.responseMessage, requireActivity())

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, "deleteAddressAPI", mContext)


            try {
                serviceManager.deleteAddressApi(callBack, id)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }

    }

    override fun deleteAdd() {
        deleteAddressAPI()
    }

    override fun updateAddress(_id: String?) {
        updatePrimaryAddressApi(_id)
    }


    fun updatePrimaryAddressApi(_id: String?) {

        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            Progresss.start(requireContext())
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<UpdatePrimaryAddressResponse> =
                ApiCallBack<UpdatePrimaryAddressResponse>(object :
                    ApiResponseListener<UpdatePrimaryAddressResponse> {
                    override fun onApiSuccess(
                        response: UpdatePrimaryAddressResponse,
                        apiName: String?
                    ) {
                        Progresss.stop()

                        if (response.responseCode == 200) {
                            try {
                                dataLoadFlag = false
                                loaderFlag = true
                                page = 1
                                choosedeliveryApi()
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
                            androidextention.alertBox(pojo.responseMessage, requireActivity())

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, "updatePrimaryAddressApi", mContext)

            var jsonObject = JsonObject().apply {
                addProperty("addressId", _id)
            }
            try {

                serviceManager.updatePrimaryAddressApi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }

    }




}