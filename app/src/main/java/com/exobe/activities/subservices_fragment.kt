package com.exobe.activities

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.fragments.allServices.AddDealsServiceProvider
import com.exobe.adaptor.SubCategoryCustomerAdapter
import com.exobe.adaptor.subservice_adaptor
import com.exobe.modelClass.ChooseServicesMyModel
import com.exobe.modelClass.ServicesMyModel
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.bottomSheet.CustomerBottomSheet
import com.exobe.customClicks.RequestServiceListener
import com.exobe.customClicks.UpdateIsLoginListener
import com.exobe.customClicks.ViewServiceDealListener
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.*
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder

class subservices_fragment(var flag: String, var tabFlag: String) : Fragment(),
    RequestServiceListener , ViewServiceDealListener , UpdateIsLoginListener{

    lateinit var DealsRecycler: RecyclerView
    lateinit var adapter1: subservice_adaptor
    lateinit var adapter2: SubCategoryCustomerAdapter
    var chooseServicesMyModel = ArrayList<ChooseServicesMyModel>()

    //    val data: ArrayList<SubCategoryView_SubCategoryDetails> = ArrayList()
    lateinit var mContext: Context
    var cart: ImageView? = null
    var filter: ImageView? = null
    var MenuClick: LinearLayout? = null
    lateinit var service_MenuClick: ImageView
    lateinit var title: TextView
    var back: LinearLayout? = null
    lateinit var service_back: ImageView
    var DealsImageView: ImageView? = null
    var greyBellImageView: ImageView? = null
    var mainHeader: RelativeLayout? = null
    var serviceCategoryId: String = ""
    lateinit var progressbar_subservice: ProgressBar
    var CategoryId: String = ""
    var CategoryName: String = ""
    var itemList = ArrayList<Customerdeals_Result>()
    var subCatItemList = ArrayList<Customerdeals_Result>()
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    companion object{
        var apiCallFlag = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_subservices_fragment, container, false)
        GET_PARSING_DATA()

        DealsRecycler = view.findViewById(R.id.recyclerview)
        progressbar_subservice = view.findViewById(R.id.progressbar_subservice)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!

        mContext = activity?.applicationContext!!

        if (tabFlag == "") {
            mainHeader = activity?.findViewById(R.id.RelativeLayout)!!

            mainHeader!!.visibility = View.VISIBLE
            setToolbar()
        }
        else {
            try {
                title = activity?.findViewById(R.id.title)!!
                service_back = activity?.findViewById(R.id.back)!!
                service_MenuClick = activity?.findViewById(R.id.MenuClick)!!
                service_back.visibility = View.VISIBLE
                service_MenuClick.visibility = View.GONE
                title.text = "Deals to customers"

                service_back.setSafeOnClickListener {
                    parentFragmentManager.popBackStack()
                }
            }catch (e : Exception) {
                e.printStackTrace()
            }
        }
        

        return view
    }

    override fun onResume() {
        super.onResume()
        if (apiCallFlag) {
            if (flag == "service") {
                title.text = CategoryName
                SubCategoryView()

            } else {
                title.text = CategoryName
                SubCategoryCustomer()
            }
            apiCallFlag = false
        } else {
            if (flag == "service") {
                title.text = CategoryName
                if (androidextention.isOnline(mContext)) {
                    internet_connection.visibility = View.GONE
                    lottie!!.initLoader(false)
                    try {
                        setApapter(itemList)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    DealsRecycler.visibility = View.GONE
                    internet_connection.visibility = View.VISIBLE
                    lottie!!.initLoader(true)
                }
            } else {
                title.text = CategoryName
                if (androidextention.isOnline(mContext)) {
                    internet_connection.visibility = View.GONE
                    lottie!!.initLoader(false)
                    try {
                        setCustomerApapter(subCatItemList)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    DealsRecycler.visibility = View.GONE
                    internet_connection.visibility = View.VISIBLE
                    lottie!!.initLoader(true)
                }
            }
        }
    }

    private fun GET_PARSING_DATA() {
        if (requireArguments().getString("CategoryId") != null) {
            CategoryId = requireArguments().getString("CategoryId").toString()
        }
        if (requireArguments().getString("CategoryName") != null) {
            CategoryName = requireArguments().getString("CategoryName").toString()
        }
    }

    fun setApapter(itemList: ArrayList<Customerdeals_Result>) {
        DealsRecycler.layoutManager = GridLayoutManager(mContext, 2)
        adapter1 = subservice_adaptor(mContext, itemList, "", this)
        DealsRecycler.adapter = adapter1
    }

    fun setCustomerApapter(itemList: ArrayList<Customerdeals_Result>) {
        DealsRecycler.layoutManager = GridLayoutManager(mContext, 2)
        adapter2 = SubCategoryCustomerAdapter(mContext, itemList, "", this)
        DealsRecycler.adapter = adapter2
    }

    fun setToolbar() {
        title = activity?.findViewById(R.id.PreLoginTitle_TextView2)!!


        cart = activity?.findViewById(R.id.cart_icon)!!
        filter = activity?.findViewById(R.id.filter_icon)!!
        back = activity?.findViewById(R.id.imageView_back)!!
        MenuClick = activity?.findViewById(R.id.MenuClick)!!
        DealsImageView = activity?.findViewById(R.id.Deals_ImageView)!!
        greyBellImageView = activity?.findViewById(R.id.greyBell_ImageView)!!
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
        cartCount.visibility = View.GONE
        cart!!.visibility = View.GONE
        filter!!.visibility = View.GONE
        MenuClick!!.visibility = View.GONE
        back!!.visibility = View.VISIBLE
        DealsImageView!!.visibility = View.GONE
        greyBellImageView!!.visibility = View.GONE
        title.text = "Deals on electrician"
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    fun SubCategoryView() {
        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar_subservice.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<CustomerdealsSubcategory> =
                ApiCallBack<CustomerdealsSubcategory>(object :
                    ApiResponseListener<CustomerdealsSubcategory> {
                    override fun onApiSuccess(
                        response: CustomerdealsSubcategory,
                        apiName: String?
                    ) {
                        if (response.responseCode == 200) {
                            progressbar_subservice.visibility = View.GONE
                            itemList.clear()
                            itemList = response.result
                            try {
                                setApapter(itemList)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_subservice.visibility = View.GONE


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
                        progressbar_subservice.visibility = View.GONE
                    }

                }, "SubCategoryViewApi", requireContext())


            try {
                serviceManager.subCategoryView(callBack, CategoryId)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            DealsRecycler.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }
    }


    fun SubCategoryCustomer() {
        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar_subservice.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<CustomerdealsSubcategory> =
                ApiCallBack<CustomerdealsSubcategory>(object :
                    ApiResponseListener<CustomerdealsSubcategory> {
                    override fun onApiSuccess(
                        response: CustomerdealsSubcategory,
                        apiName: String?
                    ) {
                        if (response.responseCode == 200) {
                            progressbar_subservice.visibility = View.GONE
                            subCatItemList.clear()
                            subCatItemList = response.result
                            try {
                                setCustomerApapter(subCatItemList)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_subservice.visibility = View.GONE


                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, requireContext())

                        } catch (e: Exception) {
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar_subservice.visibility = View.GONE
                    }

                }, "SubCategoryViewApi", requireContext())


            try {
//                serviceManager.subCategoryCustomer(callBack, CategoryId)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            DealsRecycler.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }
    }

    override fun RequestService(
        _id: String,
        serviceName: String,
        dealPrice: Double,
        firstName: String,
        lastName: String,
        categoryName: String,
        categoryImage: String,
        subCategoryName: String,
        _id1: String,
        priceTag:String,
        actualPrice:String,
        discount:String
    ) {

        if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
            ServiceRequestReview.apiServiceRequestCallFlag = true
            chooseServicesMyModel.clear()
            var servicesMyModel = ArrayList<ServicesMyModel>()
            servicesMyModel.add(ServicesMyModel(_id1, _id1, serviceName, dealPrice,priceTag,actualPrice,discount,true))
            chooseServicesMyModel.add(ChooseServicesMyModel(subCategoryName, _id1, servicesMyModel))
            var serviceProviderName = "$firstName $lastName"
            var bundle = Bundle()
            bundle.putSerializable("CHOOSE_LIST", chooseServicesMyModel)
            bundle.putString("name", serviceProviderName)
            bundle.putString("serviceImage", categoryImage)
            bundle.putString("userId", _id)
            val frgObj = ServiceRequestReview()
            frgObj.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, frgObj, "serviceRequest").addToBackStack(null).commit()

        } else {
            parentFragmentManager.let { it1 ->
                CustomerBottomSheet("Customer", mContext,this).show(it1, "ModalBottomSheet")
            }
        }
    }

    override fun viewServiceDeal(_id: String, categoryName: String) {
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.service_main_container, AddDealsServiceProvider("edit", _id), "adddeals")?.addToBackStack(null)?.commit()
    }

    override fun isLoginListener() {
        var name = activity?.findViewById<TextView>(R.id.name)
        var userProfile = activity?.findViewById<ImageView>(R.id.Profile_picture)
        CommonFunctions.getProfileApiApi(mContext, name, userProfile)
    }

}