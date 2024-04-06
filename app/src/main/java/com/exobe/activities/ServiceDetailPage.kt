package com.exobe.activities

import android.annotation.SuppressLint
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
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.customeradaptor.CustomerServiceAdaptor
import com.exobe.modelClass.ChooseServicesMyModel
import com.exobe.modelClass.ServicesMyModel
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.bottomSheet.CustomerBottomSheet
import com.exobe.customClicks.UpdateIsLoginListener
import com.exobe.customClicks.chooseServicesListener
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.customerservices.OwnerDetails
import com.exobe.entity.response.customerservices.ServicesListNearMeDoc
import com.exobe.entity.response.customerservices.ServicesListNearMeResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.fragments.allServices.ServiceRequestInterestedFragment
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

class ServiceDetailPage : Fragment(), chooseServicesListener, UpdateIsLoginListener {
    lateinit var MenuClick: LinearLayout
    lateinit var search: RelativeLayout
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView

    lateinit var proceedtorequest: Button
    lateinit var interestRequest: Button
    lateinit var viewAddDealsImage: ImageView
    lateinit var serviceReview: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var tv_selectservices: TextView
    lateinit var service_provider_name: TextView
    lateinit var description: TextView
    lateinit var CategoryName: TextView
    lateinit var des_ll: LinearLayout

    lateinit var ServiceDetailsRecycler: RecyclerView
    lateinit var SD_NestedScrollView: NestedScrollView
    lateinit var mContext: Context
    lateinit var progressbar: ProgressBar
    var flag = ""
    var id = ""
    var userId = ""
    var comment = ""
    var chooseServicesMyModel = ArrayList<ChooseServicesMyModel>()

    var Response = ArrayList<ServicesListNearMeDoc>()
    var userResponse = OwnerDetails()
    var lat = ""
    var long = ""
    var serviceImage = ""
    var serviceProviderName = ""
    var serviceFlag = false
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    private var apiCallFlag = true
    private lateinit var noDetailsFound : TextView
    private lateinit var detailsPage : RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_service_detail_page, container, false)
        mContext = activity?.applicationContext!!
        detailsPage = view.findViewById(R.id.detailsPage)
        noDetailsFound = view.findViewById(R.id.noDetailsFound)
        SD_NestedScrollView = view.findViewById(R.id.SD_NestedScrollView)
        des_ll = view.findViewById(R.id.des_ll)
        proceedtorequest = view.findViewById(R.id.proceedtorequest)
        ServiceDetailsRecycler = view.findViewById(R.id.ServiceDetailsRecycler)
        tv_selectservices = view.findViewById(R.id.tv_selectservices)
        serviceReview = view.findViewById(R.id.service_review)
        progressbar = view.findViewById(R.id.progressbar)
        viewAddDealsImage = view.findViewById(R.id.viewAddDealsImage)
        description = view.findViewById(R.id.description)
        CategoryName = view.findViewById(R.id.CategoryName)
        service_provider_name = view.findViewById(R.id.service_provider_name)
        interestRequest = view.findViewById(R.id.interestRequest)
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        mainHeader.visibility = View.VISIBLE
        setToolbar()
        try {
            lat = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.LAT)!!
            long = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.LONG)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
        getParsingData()

        chooseServicesMyModel.clear()

        back.setSafeOnClickListener {
            parentFragmentManager.popBackStack()
        }

        serviceReview.setSafeOnClickListener {
            val bundle = Bundle()
            bundle.putString("productId2", "")
            val obj = Review()
            obj.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayout, obj, "review").addToBackStack(null)
                .commit()

        }

        proceedtorequest.setSafeOnClickListener {
            if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                for (i in 0 until Response.size) {
                    val servicesMyModel = ArrayList<ServicesMyModel>()
                    for (j in 0 until Response[i].serviceArray.size) {
                        if (Response[i].serviceArray[j].isSelected) {
                            val dealPriceNew = if (Response[i].serviceArray[j].isDealActive)
                                "R ${CommonFunctions.currencyFormatter(Response[i].serviceArray[j].dealPrice.toDouble())}" else "R 0"

                            servicesMyModel.add(
                                ServicesMyModel(
                                    Response[i].serviceArray[j].id,
                                    Response[i].serviceArray[j].id,
                                    Response[i].serviceArray[j].serviceName,
                                    Response[i].serviceArray[j].price,
                                    "R ${CommonFunctions.currencyFormatter(Response[i].serviceArray[j].price.toDouble())}",
                                    dealPriceNew,
                                    "${Response[i].serviceArray[j].dealDiscount}% Off",
                                    Response[i].serviceArray[j].isDealActive
                                )
                            )
                            serviceFlag = true
                        }
                    }
                    if (serviceFlag) {
                        chooseServicesMyModel.add(
                            ChooseServicesMyModel(
                                Response[i].subCategory.subCategoryName,
                                Response[i].subCategory.id,
                                servicesMyModel
                            )
                        )
                        serviceFlag = false
                    }
                }

                if (chooseServicesMyModel.size > 0) {
                    ServiceRequestReview.apiServiceRequestCallFlag = true
                    var bundle = Bundle()
                    bundle.putSerializable("CHOOSE_LIST", chooseServicesMyModel)
                    bundle.putString("name", serviceProviderName)
                    bundle.putString("serviceImage", serviceImage)
                    bundle.putString("userId", userId)
                    bundle.putString("categoryName", CategoryName.text.toString())
                    val frgObj = ServiceRequestReview()
                    frgObj.arguments = bundle
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.FrameLayout, frgObj, "serviceRequest")
                        .addToBackStack(null).commit()
                } else {
                    androidextention.alertBox("Please select any one service", requireContext())
                }
            } else {
                parentFragmentManager.let { it1 ->
                    CustomerBottomSheet("Customer", mContext, this).show(it1, "ModalBottomSheet")
                }
            }
        }


        interestRequest.setSafeOnClickListener {
            if (SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.isLogin) == "true") {
                val bundle =  Bundle()
                bundle.putString("serviceId",id)
                bundle.putString("userId",userId)
                val obj  = ServiceRequestInterestedFragment()
                obj.arguments =  bundle
                parentFragmentManager.beginTransaction()
                    .replace(R.id.FrameLayout, obj, "ServiceRequestInterestedFragment")
                    .addToBackStack(null).commit()
            }else{
                parentFragmentManager.let { it1 ->
                    CustomerBottomSheet("Customer", mContext, this).show(it1, "ModalBottomSheet")
                }
            }
        }





        return view
    }

    override fun onResume() {
        super.onResume()
        if (apiCallFlag) {
            ListServiceNearMeApi()
            apiCallFlag = false
        } else {
            if (androidextention.isOnline(mContext)) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                setData()
            } else {
                SD_NestedScrollView.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
            }
        }
    }

    private fun getParsingData() {
        try {
            if (requireArguments().getString("userId") != null) {
                userId = requireArguments().getString("userId").toString()
            }
            if (requireArguments().getString("id") != null) {
                id = requireArguments().getString("id").toString()
            }
            if (requireArguments().getString("flag") != null) {
                flag = requireArguments().getString("flag").toString()
            }
            if (requireArguments().getString("comment") != null) {
                comment = requireArguments().getString("comment").toString()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun ListServiceNearMeApi() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)

            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<ServicesListNearMeResponse> =
                ApiCallBack<ServicesListNearMeResponse>(object :
                    ApiResponseListener<ServicesListNearMeResponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onApiSuccess(
                        response: ServicesListNearMeResponse,
                        apiName: String?
                    ) {
                        progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                detailsPage.isVisible = true
                                noDetailsFound.isVisible = false
                                Response.clear()
                                Response = response.result.docs
                                userResponse = response.result.userDetails
                                serviceImage = response.result.userDetails.profilePic
                                setData()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            detailsPage.isVisible = false
                            noDetailsFound.isVisible = true
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {

                        noDetailsFound.isVisible = true
                        progressbar.visibility = View.GONE
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
                        noDetailsFound.isVisible = true
                        progressbar.visibility = View.GONE
                    }

                }, "ServiceListApi", mContext)

            val jsonObject = JsonObject()
            jsonObject.addProperty("userId", userId)
            jsonObject.addProperty("categoryId", id)
//            jsonObject.addProperty("lat", lat)
//            jsonObject.addProperty("lng", long)
            try {
                serviceManager.ListServiceNearMe(callBack, jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            SD_NestedScrollView.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    private fun setData() {
        try {
            Glide.with(mContext)
                .load(serviceImage)
                .placeholder(R.drawable.dummyproduct)
                .into(viewAddDealsImage)

            serviceProviderName =
                "${userResponse.ownerFirstName} ${
                    userResponse.ownerLastName
                }"
            service_provider_name.text =
                "${userResponse.ownerFirstName} ${
                    userResponse.ownerLastName
                }"
            var des = Response.get(0).subCategory.categoryId.description
            if (des != "") {
                des_ll.visibility = View.VISIBLE
                description.text = des
            } else {
                des_ll.visibility = View.GONE
            }
            CategoryName.text =
                Response.get(0).subCategory.categoryId.categoryName
            setServicesAdapter(Response)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun setServicesAdapter(docs: ArrayList<ServicesListNearMeDoc>) {
        ServiceDetailsRecycler.layoutManager = LinearLayoutManager(context)
        var customerServiceAdaptor = CustomerServiceAdaptor(requireContext(), docs)
        ServiceDetailsRecycler.adapter = customerServiceAdaptor

    }


    override fun isCheckedChooseServices(
        mainId: String,
        id: String,
        title: String,
        price: Number,
        subCategoryName: String,
        subCategoryId: String
    ) {
        if (chooseServicesMyModel.size == 0) {
            var servicesMyModel = ArrayList<ServicesMyModel>()
            servicesMyModel.add(ServicesMyModel(mainId, id, title, price,"","","", false))
            chooseServicesMyModel.add(
                ChooseServicesMyModel(
                    subCategoryName,
                    subCategoryId,
                    servicesMyModel
                )
            )
        } else {
            for (i in 0 until chooseServicesMyModel.size) {
                if (chooseServicesMyModel[i].subServicesId.equals(subCategoryId)) {
                    chooseServicesMyModel[i].servicesMyModel.add(
                        ServicesMyModel(
                            mainId,
                            id,
                            title,
                            price,"","","", false
                        )
                    )
                    break
                } else {
                    var servicesMyModel = ArrayList<ServicesMyModel>()
                    servicesMyModel.add(ServicesMyModel(mainId, id, title, price,"","","", false))
                    chooseServicesMyModel.add(
                        ChooseServicesMyModel(
                            subCategoryName,
                            subCategoryId,
                            servicesMyModel
                        )
                    )
                }
            }
        }

        println("isCheckedChooseServices----------> ${chooseServicesMyModel.toString()}")
    }

    override fun deleteChooseItem(
        id: String,
        title: String,
        price: Number,
        subCategoryName: String,
        subCategoryId: String
    ) {
        try {
            for (i in 0 until chooseServicesMyModel.size) {
                for (j in 0 until chooseServicesMyModel[i].servicesMyModel.size) {
                    if (chooseServicesMyModel[i].servicesMyModel[j].id == id) {
                        if (chooseServicesMyModel[i].servicesMyModel.size == 1) {
                            chooseServicesMyModel.removeAt(i)
                        } else {
                            if (chooseServicesMyModel[i].subServicesId.equals(subCategoryId)) {
                                chooseServicesMyModel[i].servicesMyModel.removeAt(j)
                            }
                        }
                    }
                }

            }
            println("deleteChooseItem----------> ${chooseServicesMyModel.toString()}")
        } catch (e: Exception) {
            e.printStackTrace()
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
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
        cartCount.visibility = View.GONE
        cart.visibility = View.GONE
        filter.visibility = View.GONE
        back.visibility = View.VISIBLE

        MenuClick.visibility = View.GONE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        title.text = "Bookings Details"
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE

    }
}